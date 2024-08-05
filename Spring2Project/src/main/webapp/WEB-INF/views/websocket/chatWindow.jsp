<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹소켓 채팅</title>
</head>
<body>
	<script type="text/javascript">
		// 웹소켓 객체 생성
		let url = "ws://192.168.35.32/ChatingServer";
		let webSocket = new WebSocket(url);
		let chatWindow, chatMessage, chatId, managerId;
		let status = false;
		
		// 채팅창이 열리면 대화창, 메시지 입력창, 대화명 표시란으로 사용할 돔 객체에 변수 저장
		window.onload = function(){
			chatWindow = document.getElementById("chatWindow");
			chatMessage = document.getElementById("chatMessage");
			chatId = document.getElementById("chatId").value;
			managerId = document.getElementById("managerId").value;
		}
		
		// 입장시 알림
		 function apperMessage(chatId){
			webSocket.send(chatId + "님이 입장하였습니다.");
		 }
		
		// 메시지 전송
		function sendMessage(){
			chatWindow.innerHTML += "<div class='myMsg' style='color:blue;'>나: " + chatMessage.value + "</div>";
			webSocket.send(chatId + '|' + chatMessage.value); // 서버로 전송
			chatMessage.value = ""; // 메시지 입력창 내용 지우기
			chatWindow.scrollTop = chatWindow.scrollHeight; // 대화창 스크롤
		}
		
		// 서버와의 연결 종료
		function disconnect(){
			webSocket.close();
		}
		
		// 엔터 키 입력 처리
		function enterKey(){
			if(window.event.keyCode == 13){
				sendMessage();
			}
		}
		
		// 웹소켓 서버에 연결될 때
		webSocket.onopen = function(event){
			if(webSocket.onopen){
			chatWindow.innerHTML += "<font style='color:red;'>서버 연결 완료!</font>";
			//chatWindow.innerHTML += "<p style='color:red;'>${param.managerId }님이 접속하였습니다.</p>";
			}else{
			//webSocket.send("<p style='color:red;'>${param.managerId }님이 접속하였습니다.</p>");
			 //apperMessage(chatId + "님이 입장하셨습니다.");
				console.log("로딩중....");
			}
		}
		
		// 웹소켓이 닫혔을 때	
		webSocket.onclose = function(event){
			chatWindow.innerHTML += "<p style='color:red;'>서버가 종료되었습니다.</p><br/>";
		}
		
		// 에러 발생시 실행
		webSocket.onerror = function(event){
			alert(event.data)
			chatWindow.innerHTML += "<p style='color:red;'>채팅 중 에러가 발생하였습니다. 다시 접속해주십시오.</p><br/>";
		}
		
		// 메시지를 받았을 때 실행
		webSocket.onmessage = function(event){
			var message = event.data.split("|"); // 대화명과 메시지 분리
			var sender = message[0]; // 보낸 사람의 대화명
			var content = message[1]; // 메시지 내용
			if(content != ""){
				if(content.match("/")){
					if(content.match(("/" + chatId))){ // 나에게 보낸 메시지만 출력
						var temp = content.replace(("/" + chatId), "[귓속말] : ");
						chatWindow.innerHTML += "<div>" + sender+ "" + temp + "</div>";
					}
				}else{ // 일반대화일 때
					chatWindow.innerHTML += "<div>" + sender + " : " + content + "</div>";
				}
			}
			chatWindow.scrollTop = chatWindow.scrollHeight;
		};
		
	</script>
	
	대화를 요청한 사람 : <input type="text" id="chatId" value="${param.chatId }" disabled="disabled"/><br/>
	초대 받아 채팅방에 들어온 사람 : <input type="text" id="managerId" value="${param.managerId }" disabled="disabled"/><br/>
	<button id="closeBtn" onclick="disconnect();">채팅 종료</button><br/>
	<div id="chatWindow">
	</div>
	<div>
		<input type="text" id="chatMessage" onkeyup="enterKey();"/>
		<button id="sendBtn" onclick="sendMessage();">전송</button>
	</div>
</body>
</html>