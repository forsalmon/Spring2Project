package kr.or.ddit.controller.crud.notice;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeFileVO;

@Controller
@RequestMapping("/notice")
public class NoticeDownloadController {
	
	@Inject 
	private INoticeService noticeService;
	
	// 파일 다운로드
	@RequestMapping(value="/download.do", method = RequestMethod.GET)
	public View noticeDownload(int fileNo, ModelMap model) {
		// 선택한 파일을 다운로드하기 위한 정보로 파일번호에 해당하는 파일 정보를 얻어온다.
		NoticeFileVO noticeFileVO = noticeService.noticeDownload(fileNo);
		
		// 데이터 전달자를 통해 파일정보를 전달하기 위한 Map 선언
		Map<String, Object> noticeFileMap = new HashMap<String, Object>();
		noticeFileMap.put("fileName", noticeFileVO.getFileName());
		noticeFileMap.put("fileSize", noticeFileVO.getFileSize());
		noticeFileMap.put("fileSavepath", noticeFileVO.getFileSavepath());
		model.addAttribute("noticeFileMap", noticeFileMap);
		
		// 리턴되는 NoticeDownloadView는 jsp페이지로 존재하는 페이지Name을 요청하는 게 아니라
		// 클래스를 요청하는 것인데 해당 클래스는 스프링에서 제공하는 AbstractView 클래스를 상속받은 클래스이다.
		// 	== AbstractView를 상속받아 renderMergedOutputModel함수를 재정의하면 View로 취급된다.
		return new NoticeDownloadView(); // View 
		
	}

}
