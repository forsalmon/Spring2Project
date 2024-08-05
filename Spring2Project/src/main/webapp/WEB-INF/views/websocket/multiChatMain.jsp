<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹소켓으로 채팅하기</title>
<script src="https://code.jquery.com/jquery-3.6.2.js"></script>
</head>
<body>
	<script type="text/javascript">
		function chatWinOpen(){ // 채팅창을 팝업으로 여는 함수
			var chatId = document.getElementById("chatId"); // 대화명 입력상자의 dom객체를 얻어온다.
			var managerId = document.getElementById("managerId"); // 초대를 원하는 교수를 소환한다.
			if(chatId.value == ""){
				alert("대화명이 입력되지 않았습니다. 대화명을 입력 후 채팅에 참여해주세요.");
				chatId.focus();
				return;
			}
			
			if(managerId.value == ""){
				alert("대화 요청 상대가 선택되지 않았습니다. 상대 선택 후 채팅에 참여해주세요.");
				managerId.focus();
				return;
			}
			
			inviteManager(chatId.value, managerId.value);
			window.open("/chat/room?chatId=" + chatId.value + "&managerId=" + managerId.value , "_blank" , "대화창" , "width=500,height=600");
		}
		
		// 초대 메시지 전송
		function inviteManager(chatId, managerId){
			var data = [
								{id : chatId},
								{id : managerId}
						];
			
			// console.log(JSON.stringify(data));

			$.ajax({
				type: "POST",
				url : "/chat/invite",
				data : JSON.stringify(data),
				contentType : "application/json; charset=utf-8",
				dataType : "text",
				success : function(result){
					console.log("하이", result);
				}
			}) 
		}
	</script>
	<h2>404 3조 채팅방</h2>
	<p>대화명을 입력한 후 입장하십시오.</p>
	<p>/대화명 대화내용 입력시 귓속말이 가능합니다.</p>
	<table border="1">
		<tr>
			<td>대화명</td>
			<td><input type="text" id="chatId" value="도리"/></td>
			<td>대화를 요청할 상대를 선택하세요.</td>
			<td>
				<select name="managerId" id="managerId">
					<option value="" selected disabled hidden="hidden">==선택==</option>
					<option value="고재일">고재일</option>
					<option value="조은혁">조은혁</option>
					<option value="고재일">인승주</option>
				</select>
			</td>
			<td><button onclick="chatWinOpen();">채팅 참여하기</button></td>
		</tr>
	</table>
</body>
</html>