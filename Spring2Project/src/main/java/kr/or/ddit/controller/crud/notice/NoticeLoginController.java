package kr.or.ddit.controller.crud.notice;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeMemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeLoginController {

	@Inject
	private INoticeService noticeService;
	
	
	// 로그인 페이지
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public String noticeLogin(Model model) {
		model.addAttribute("bodyText","login-page");
		return "conn/login";
	}
	
	@RequestMapping(value="/loginCheck.do", method=RequestMethod.POST)
	public String noticeLoginCheck(
			HttpSession session, RedirectAttributes ra,
			NoticeMemberVO memberVO, Model model) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>(); 
		// 넘겨받은 아이디가 비어있을 때
		if(StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요!");
		}
		// 넘겨받은 비밀번호가 비어있을 때
		if(StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요!");
		}
		if(errors.size() > 0) {
			model.addAttribute("errors", errors);
			model.addAttribute("memberVO", memberVO);
			model.addAttribute("bodyText", "login-page");
			goPage = "conn/login";
		}else {
			NoticeMemberVO member = noticeService.loginCheck(memberVO);
			if(member != null) { // 로그인 성공
				// 로그인 성공 시 세션에 회원정보 등록(key: SessionInfo)
				session.setAttribute("SessionInfo", member);
				ra.addFlashAttribute("message", memberVO.getMemId() + "님, 환영합니다!");
				goPage = "redirect:/notice/list.do";
			}else {
				model.addAttribute("message", "서버에러, 로그인 정보를 정확하게 입력해주세요!");
				model.addAttribute("memberVO", memberVO);
				model.addAttribute("bodyText", "login-page");
				goPage = "conn/login";
			}
		}
		return goPage;
	}
	
	// 회원가입 페이지
	@RequestMapping(value="/signup.do", method = RequestMethod.GET)
	public String noticeSignup(Model model) {
		model.addAttribute("bodyText", "register-page");
		return "conn/register";
	}
	
	// 아이디 중복확인
	@ResponseBody
	@RequestMapping(value = "/idCheck.do", method = RequestMethod.POST)
	public ResponseEntity<ServiceResult> idCheck(@RequestBody Map<String, String> map){
		log.info("[아이디 중복확인] 넘겨받은 아이디" + map.get("memId"));
		
		/*
		브라우저로부터 넘겨받은 단일 데이터를 꺼내는 방법
			1) ajax에서 ContentType설정 없이(XX) => 데이터만 {memId : id}설정해서 넘길 때
				- String memId로 꺼낼 수 있다.
				## jsp스크립트란에 방법 2가지 중 2에 해당
			2) ajax에서 ContentType설정 없이(XX) => 데이터만 JSON.stringify(data) 설정해서 넘길 때
				- @RequestBody로 String memId로 꺼내면 '%7B%22memId%22%3A%22...=' 이런 인코딩된 데이터가 넘어옴
			3) ajax에서 ContentType설정하고 => 데이터만 JSON.stringify(data) 설정해서 넘길 때
				- @RequestBody로 String memId로 꺼내면 '{memId : a001}' 데이터가 넘어옴
			4) ajax에서 ContentType설정하고 => 데이터만  JSON.stringify(data) 설정해서 넘길 때
				- @RequestParam로 String memId로 꺼내면 400에러가 발생한다.
			5) ajax에서 ContentType설정하고 => 데이터만  JSON.stringify(data) 설정해서 넘길 때
				- @RequestBody Map<String, String> map을 꺼내면 'a001'데이터가 넘어옴
				## jsp스크립트란에 방법 2가지 중 1에 해당
		*/
		ServiceResult result = noticeService.idCheck(map.get("memId"));
		return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);
	}

	// 아이디 & 비밀번호 찾기 페이지
	@RequestMapping(value="/forget.do", method = RequestMethod.GET)
	public String noticeForgetIdAndPw(Model model) {
		model.addAttribute("bodyText","login-page");
		return "conn/forget";
	}
	
	@RequestMapping(value = "/signup.do", method = RequestMethod.POST)
	public String signup(
			HttpServletRequest req,
			NoticeMemberVO memberVO, Model model,
			RedirectAttributes ra
			) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		// 유효성검사를 하고 넘어왔지만 서버 단에서 한번 더 체크를 해준다.
		if(StringUtils.isBlank(memberVO.getMemId())) {
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
			goPage = "conn/register";
		}else { // 넘겨받은 데이터가 정상일 경우
			ServiceResult result = noticeService.signup(req, memberVO); // req : 파일업로드를 진행할 수 있는 경로설정을 위해, memberVO : 등록을 위해
			if (result.equals(ServiceResult.OK)) { // 회원가입 성공
				ra.addFlashAttribute("message", "회원가입을 완료하였습니다!"); // 페이지 이동방식이 redirect이기 때문에 ra전달자를 이용해 메세지 전달
				goPage = "redirect:/notice/login.do"; 
			}else { // 회원가입 실패
				model.addAttribute("bodyText", "register-page");
				model.addAttribute("message", "서버 에러, 다시 시도하세요~");
				model.addAttribute("member", memberVO);
				return "conn/register";
			}
		}
		return goPage;
	}
	
	
	// 아이디 찾기
	@ResponseBody
	@RequestMapping(value = "/idForget.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public ResponseEntity<String> idForgetProcess(@RequestBody NoticeMemberVO memberVO) {
		
		String id = null;
		NoticeMemberVO member = noticeService.idForgetProcess(memberVO);
		if(member.getMemId() != null && !member.getMemId().equals("")) {
			id =  member.getMemId();
		}else {
			id = null;
		}
		
		return new ResponseEntity<String>(id, HttpStatus.OK);
	}
	
	// 비밀번호 찾기
	@RequestMapping(value = "/pwForget.do", method =RequestMethod.POST)
	public ResponseEntity<String> pwForgetProcess(@RequestBody NoticeMemberVO memberVO) {
		
		String pw = null;
		NoticeMemberVO member = noticeService.pwForgetProcess(memberVO);
		if(member.getMemId() != null && !member.getMemId().equals("")) {
			if(member.getMemId().equals(memberVO.getMemId())) {
				pw = member.getMemPw();
			}else {
				pw = null;
			}
		}else {
			pw = null;
		}
		return new ResponseEntity<String>(pw, HttpStatus.OK);
	}
	
}
