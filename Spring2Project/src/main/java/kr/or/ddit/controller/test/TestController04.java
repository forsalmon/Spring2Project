package kr.or.ddit.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/pc")
public class TestController04 {

	@RequestMapping(value="/main.do", method=RequestMethod.GET)
	public String main() {
		return "test/test04/main";
	}
	
		
}
