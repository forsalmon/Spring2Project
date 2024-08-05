<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' integrity='sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN' crossorigin='anonymous'>
<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js'></script>
</head>
<body>
	<!-- 
		아이디찾기, 비밀번호찾기를 진행해주세요.
		
		# 아이디찾기
		1) 이름, 이메일을 입력 후, 아이디 찾기 버튼을 클릭 시 비동기 통신을 활용해 아이디를 출력해주세요.
			> 조회된 아이디 정보는 아이디 찾기 안에 있는 card-body 클래스명을 가진 div안에 출력해주세요.
			> 존재하지 않는 정보라면 "존재하지 않습니다"를 출력해주세요.
			
		# 비밀번호 찾기
		1) 아이디, 이름, 이메일을 입력 후, 비밀번호 찾기 버튼을 클릭 시 비동기 통신을 활용해 비밀번호를 출력해주세요.
			> 조회된 비밀번호 정보는 비밀번호 찾기 안에 있는 card-body 클래스명을 가진 div안에 출력해주세요.
			> 존재하지 않는 정보라면 "존재하지 않습니다"를 출력해주세요.	
	 -->
	 
	<!--  비동기로 ... ! -->
	<div class="row">
		<div class="col-md-6">
			<div class="card">
				<div class="card-header">
					아이디 찾기
				</div>
				<div class="card-body">
					<form action="" method="post" id="searchIdForm">
						<div class="input-group mb-3">
							<input class="form-control" type="text" name="memName" id="memName" placeholder="이름을 입력해주세요."/>
						</div>
						<div class="input-group mb-3">
							<input class="form-control" type="text" name="memEmail" id="memEmail" placeholder="이메일을 입력해주세요."/>
						</div>
						<div><font id="userIdView"></font></div>
					</form>
				</div>
				<div class="card-footer">
					<button type="button" class="btn btn-primary mb-2" id="searchIdBtn">아이디찾기</button>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="card">
				<div class="card-header">
					비밀번호 찾기
				</div>
				<div class="card-body">
					<form action="" method="post">
						<div class="input-group mb-3">
							<input class="form-control" type="text" name="memId" id="pw_memId" placeholder="아이디를 입력해주세요."/>
						</div>
						<div class="input-group mb-3">
							<input class="form-control" type="text" name="memName" id="pw_memName" placeholder="이름을 입력해주세요."/>
						</div>
						<div class="input-group mb-3">
							<input class="form-control" type="text" name="memEmail" id="pw_memEmail" placeholder="이메일을 입력해주세요."/>
						</div>
						<div><font id="userPwView"></font></div>
					</form>
				</div>
				<div class="card-footer">
					<button type="button" class="btn btn-primary mb-2" id="searchPwBtn">비밀번호찾기</button>
				</div>
			</div>
		</div>
		<div class="col-md-6 mb-2">
			<button type="button" class="btn btn-primary mb-2" id="backToLoginFormBtn">뒤로가기</button>
		</div>
	</div>
</body>
<script>
$(function(){
	
	let searchIdBtn = $("#searchIdBtn");
	let searchIdForm = $("#searchIdForm");
	
	let searchPwBtn = $("#searchPwBtn");
	let backToLoginFormBtn = $("#backToLoginFormBtn");
	
	backToLoginFormBtn.on("click", function(){
		location.href= "/test03/login.do";
	});
	
	// 비밀번호 찾기 버튼 클릭 이벤트
	searchPwBtn.on("click", function(){
		
		let memId = $("#pw_memId").val();
		let memName = $("#pw_memName").val();
		let memEmail = $("#pw_memEmail").val();
		
		let userObject = {
				memId : memId,
				memName : memName,
				memEmail : memEmail,
		}
		$.ajax({
			url : "/test03/findPassword.do",
			type : "post",
			data : JSON.stringify(userObject),
			contentType : "application/json;charset=utf-8",
			success : function(res){
				$("#userPwView").text("비밀번호 : " + res);
				$("#userPwView").css("color", "red");
			},
			error : function(res){
				let code = "일치하는 계정이 없습니다.";
				$("#userPwView").text(code);
				$("#userPwView").css("color", "red");
			}
		});
	});
	
	// 아이디 찾기 버튼 클릭 이벤트
	searchIdBtn.on("click", function(){
		
		let memName = $("#memName").val();
		let memEmail = $("#memEmail").val();
		let userObject = {
				memName : memName,
				memEmail : memEmail,
		}
		
		$.ajax({
			url : "/test03/findId.do",
			type : "post",
			data : JSON.stringify(userObject),
			contentType : "application/json;charset=utf-8",
			success : function(res){
				$("#userIdView").text("아이디 : " + res);
				$("#userIdView").css("color", "red");
			},
			error : function(res){
				let code = "일치하는 계정이 없습니다.";
				$("#userIdView").text(code);
				$("#userIdView").css("color", "red");
			}
		});
	});
	
	
});


</script>
</html>