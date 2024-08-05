package kr.or.ddit.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {

	@RequestMapping("/chat.do")
	public String chat(Model model) {
		log.info("[controller] : chat.do");
		return "chat";
	}
}
