<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
<body>
	<h2>실시간 채팅</h2>
	<hr>
	<div id="messageArea"></div>
	<input type="text" id="message" />
	<input type="button" id="sendBtn" value="submit" />
</body>
<script type="text/javascript">
	$("#sendBtn").click(function(){
		sendMessage();
		$("#message").val("");		
	});
	
	// 여기 맞나 확인해야 함 내 포트 번호와...
	// 블로그 > "http://localhost:8787/ex/echo/"
	// localhost의 url과 ChatHandler를 매핑한 주소 적기 ... 
	let sock = new SockJS("http://localhost/chat");
	sock.onmessage = onMessage;
	sock.onclose = onClose;
	
	// 메시지 전송
	function sendMessage(){
		sock.send($("#message").val());
	}
	
	// 서버로부터 메세지를 받았을 때 
	function onMessage(msg){
		let data = msg.data;
		$("#messageArea").append(data + "<br>");
	}
	
	// 서버와 연결을 끊었을 때 
	function onClose(evt){
		$("#messageArea").append("연결 끊김");
	}

</script>
</html>