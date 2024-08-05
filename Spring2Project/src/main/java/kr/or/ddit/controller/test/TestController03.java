package kr.or.ddit.controller.test;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.controller.test.dao.Test03Repository;
import kr.or.ddit.controller.test.vo.StudentVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/test03")
public class TestController03 {
	
	@Inject
	private Test03Repository repository;
	
	// 로그인 페이지
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public String loginPage() {
		return "test/test03/login";
	}

	// 아이디/비밀번호 찾기 페이지
	@RequestMapping(value="/findInfo.do", method = RequestMethod.GET)
	public String findInfo() {
		return "test/test03/findInfo";
	}
	
	// 아이디 찾기 -- 찾는 아이디가 없는 경우 처리
	@ResponseBody
	@RequestMapping(value="/findId.do", method=RequestMethod.POST)
	public ResponseEntity<String> findId(@RequestBody StudentVO student){
		
		ResponseEntity<String> entity = null;
		String memName = student.getMemName();
		String memEmail = student.getMemEmail();
		String memId = repository.getStudentIdByNameNMail(memName, memEmail);
		
		if (memId == null || memId == "") {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}else {
			entity = new ResponseEntity<String>(memId, HttpStatus.OK);
		}
		return entity;
	}
	
	// 아이디 찾기
	/*
	@ResponseBody
	@RequestMapping(value="/findId.do", method = RequestMethod.POST)
	public String findInfo(
			@RequestBody StudentVO student){
		
		String memName = student.getMemName();
		String memEmail = student.getMemEmail();
		log.info(memName + ", " + memEmail);
		
		String memId = repository.getStudentIdByNameNMail(memName, memEmail);
		log.info(memId);
		
		return memId;
	}
	*/
	
	// 비밀번호 찾기 -- 찾는 비밀번호가 없는 경우 처리
	@ResponseBody
	@RequestMapping(value="/findPassword.do", method=RequestMethod.POST)
	public ResponseEntity<String> findPassword(@RequestBody StudentVO student){
		
		ResponseEntity<String> entity = null;
		String memId = student.getMemId();		
		String memName = student.getMemName();
		String memEmail = student.getMemEmail();
		String memPw = repository.getPasswordBy(memId, memName, memEmail);
		
		if (memPw == null || memPw == "") {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}else {
			entity = new ResponseEntity<String>(memPw, HttpStatus.OK);
		}
		return entity;
	}
	
	/*
	// 비밀번호 찾기
	@ResponseBody
	@RequestMapping(value="/findPassword.do", method=RequestMethod.POST)
	public String findPassword(
			@RequestBody StudentVO student){
		String memId = student.getMemId();		
		String memName = student.getMemName();
		String memEmail = student.getMemEmail();
		log.info(memId + ", " + memName + ", " + memEmail);
		String memPw = repository.getPasswordBy(memId, memName, memEmail);
		log.info(memPw);
		return memPw;
	}
	*/
	
	// 로그인 후 정보 페이지
	@RequestMapping(value="/info.do", method = RequestMethod.POST)
	public String info(String memId, String memPw, Model model) {
		//log.info(memId + ":" + memPw);
		StudentVO thisStu = new StudentVO();
		List<StudentVO> studentList = repository.getStudentAll();
		for(StudentVO stu : studentList) {
			if (stu.getMemId().equals(memId) && stu.getMemPw().equals(memPw)) {
				thisStu = stu;
				model.addAttribute("thisStu", thisStu);
				model.addAttribute("studentList", studentList);
				model.addAttribute("msg", "Y");
				return "test/test03/info";
			}
		}
		model.addAttribute("msg", "N");
		return "test/test03/login";
	}
	

}
