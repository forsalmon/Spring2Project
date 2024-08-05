package kr.or.ddit.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/chat")
@Controller
public class ChatController2 {
	
	private static Logger logger = LoggerFactory.getLogger(ChatController2.class);
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String chat () {
		return "websocket/multiChatMain";
	}
	
	@RequestMapping(value = "/room", method = RequestMethod.GET)
	public String chatWindow () {
		return "websocket/chatWindow";
	}
	
	
	@RequestMapping(value = "/invite", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String sendGet(@RequestBody List<Map<String, String>> data) throws Exception{
		logger.info("sendGet 실행...!");
		String telchat_id = "5682356712";
		String urlName = "https://api.telegram.org/bot5863713710:AAGtb99DU-a1nW-Ba9oL13lp0QudeWYVdfw/sendMessage";
		String text = "";
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String time1 = format1.format(time);
		
		String chatId = data.get(0).get("id");
		String managerId = data.get(1).get("id");
		text = chatId + "님께서 " + managerId + "님에게 대화를 요청합니다!\n" +
			   "다음의 링크를 통해 채팅방에 접속해주십시오. http://192.168.144.10/chat/room?chatId=" + chatId + "&managerId=" + managerId + "\n" + 
			   "[접속시간] " + time1 + "\n";
		
		URL url = new URL(urlName + "?chat_id=" + telchat_id + "&text=" + URLEncoder.encode(text, "UTF-8"));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int respCode = con.getResponseCode();
		
		return URLDecoder.decode(managerId, "UTF-8");
	}
}