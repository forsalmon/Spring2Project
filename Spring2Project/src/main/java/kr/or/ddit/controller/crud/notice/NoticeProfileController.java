package kr.or.ddit.controller.crud.notice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.CustomUser;
import kr.or.ddit.vo.crud.NoticeMemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeProfileController {
	
	@Resource(name="uploadPath")
	private String resourcePath;
	
	@Inject
	private INoticeService noticeService;
	
	@RequestMapping(value="/profile.do", method = RequestMethod.GET)
	   public String noticeProfile(
	         RedirectAttributes ra, 
	         HttpSession session, Model model
	         ) {
	      String goPage = "";
	      
	      // 첫번째 방법) HttpSession을 이용한 방법
	      // NoticeMemberVO memberVO = (NoticeMemberVO) session.getAttribute("sessionInfo");
	      
	      // 두번째 방법) 시큐리티 인증 시
	      CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	      NoticeMemberVO memberVO = user.getMember();
	      
	      if(memberVO != null) {   // 로그인 사용자
	         NoticeMemberVO member = noticeService.selectMember(memberVO.getMemId());
	         if(member != null) {
	            model.addAttribute("member", member);
	            goPage = "notice/profile";
	         }else {
	            ra.addFlashAttribute("message", "로그인 후 이용가능합니다!");
	            goPage = "redirect:/notice/login.do";
	         }
	      }else {   // 로그인 하고 와
	         ra.addFlashAttribute("message", "로그인 후 사용 가능합니다!");
	         return "redirect:/notice/login.do";
	      }
	      
	      return goPage;
	   }
	
	/*
	@RequestMapping(value="/profile.do", method = RequestMethod.GET)
	public String noticeProfile(
			HttpServletRequest req, 
			Model model, RedirectAttributes ra
			) { 
		String goPage = "";
		
		HttpSession session = req.getSession();
		NoticeMemberVO memberVO = (NoticeMemberVO) session.getAttribute("SessionInfo");
		String memId = memberVO.getMemId();
		NoticeMemberVO DBmemberVO = noticeService.selectMember(memId); // 세션에서 꺼낸 아이디로 MemberVO 얻기
		
		if(memberVO != null) {
			model.addAttribute("member", memberVO);
			goPage = "notice/profile";
		}else {
			ra.addFlashAttribute("msg", "다시 로그인하세요");
			goPage = "redirect:/notice/login.do";
		}
		return goPage;
	}
	*/
	
	@ResponseBody
	@RequestMapping("/display")
	public ResponseEntity<byte[]> displayFile(String memId){
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		// itemId에 해당하는 이미지 파일명을 얻어온다.
		String fileName = noticeService.getPicture(memId);
		
		try {
			// 확장자 추출
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType mType = getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			
			// File.separator = /
			// in <- 안에 있는 데이터는 이진데이터
			in = new FileInputStream(resourcePath + File.separator + fileName);
			
			if(mType != null) {
				headers.setContentType(mType);
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), 
						headers, HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	// 마이페이지 수정 
	// 같은 페이지로 요청(동기방식)
	// 서비스를 통해 요청할 마이페이지 수정 이벤트 기능명은 'profileUpdate()'로 한다.
	// 수정완료 후 "회원정보 수정이 완료되었습니다." 메시지 출력
	@RequestMapping(value="/profileUpdate.do", method=RequestMethod.POST)
	public String noticeProfileUpdate(
			HttpServletRequest req, RedirectAttributes ra,
			NoticeMemberVO memberVO, Model model) {
		String goPage = "";
		
		Map<String, String> errors = new HashMap<String, String>();
		if(StringUtils.isBlank(memberVO.getMemId())) { // StringUtils 문자열 처리 메서드
			errors.put("memId", "아이디를 입력해주세요!");
		}
		if(StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요!");
		}
		if(StringUtils.isBlank(memberVO.getMemName())) {
			errors.put("memName", "이름을 입력해주세요!");
		}
		if(errors.size() > 0) { // 넘겨받은 데이터가 비정상(에러 발생)
			model.addAttribute("bodyText", "register-page");
			model.addAttribute("errors", errors); 
			model.addAttribute("member", memberVO); // 사용자가 다시 입력하지 않게끔 다시 전달해준다.
			goPage = "notice/profile";
		}else { // 넘겨받은 데이터가 정상일 경우
			ServiceResult result = noticeService.profileUpdate(req, memberVO); 
			if (result.equals(ServiceResult.OK)) { // 정보 수정 성공
				ra.addFlashAttribute("message", "회원정보 수정을 완료하였습니다!"); // 페이지 이동방식이 redirect이기 때문에 ra전달자를 이용해 메세지 전달
				goPage = "redirect:/notice/profile.do"; 
			}else { // 회원가입 실패
				model.addAttribute("bodyText", "register-page");
				model.addAttribute("message", "서버 에러입니다. 다시 시도하세요.");
				model.addAttribute("member", memberVO);
				goPage = "notice/profile";
			}
		}	
		return goPage;
	}
	
	/*
		비동기통신을 위해서는
		클라이언트에서 서버로 요청 메세지를 보낼 때 본문에 데이터를 담아서 보내야 하고
		서버에서 클라이언트로 응답을 보낼 때에도 본문에 데이터를 담아서 보내야 한다.
		이 본문이 바로 body
		요청본문 = requestBody, 응답본문 = responseBody
	*/
	@ResponseBody
	@RequestMapping(value="/profileIdCheck.do", method=RequestMethod.POST)
	public ResponseEntity<ServiceResult> profileIdCheck(
			@RequestBody Map<String, String> map
			) {
		String memId = map.get("memId");
		ServiceResult result = noticeService.profileIdCheck(memId);
		return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}
	
	
	private MediaType getMediaType(String formatName) {
		if(formatName != null) {
			if(formatName.toUpperCase().equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if(formatName.toUpperCase().equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}
			if(formatName.toUpperCase().equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}
		return null;
	}
	
}
