<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="">
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p class="h4">
				<b>아이디찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">아이디 찾기는 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="/notice/idForget.do" method="post" id="idSearchForm">
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="memEmail" id="memEmail" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="memName" id="memName" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p>
						회원님의 아이디는<span id="searchIdValue"></span> 입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="idSearchBtn">아이디찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<br />
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p href="" class="h4">
				<b>비밀번호찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">비밀번호 찾기는 아이디, 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="memId" id="memId" placeholder="아이디를 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="memEmail" id="memEmail2" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="memName" id="memName2" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p>
						회원님의 비밀번호는 <span id="searchPwValue"></span>입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="pwSearchBtn">비밀번호찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<br/>
	<div class="card card-outline card-secondary">
		<div class="card-header text-center">
			<h4>MAIN MENU</h4>
			<button type="button" class="btn btn-secondary btn-block">로그인</button>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	
	let idSearchBtn = $("#idSearchBtn");
	let pwSearchBtn = $("#pwSearchBtn");
	let idSearchForm = $("#idSearchForm");
	let searchIdValue = $("#searchIdValue");
	let searchPwValue = $("#searchPwValue");
	
	idSearchBtn.on("click", function(){
		
		let memEmail = $("#memEmail").val();
		let memName = $("#memName").val();
		
		if (memEmail == null || memEmail == ""){
			alert("이메일을 입력해주세요!");
			return false;
		}
		if (memName == null || memName == ""){
			alert("이름을 입력해주세요!");
			return false;
		}
		
		let data = {
			memEmail : memEmail,	
			memName : memName	
		};
		
		$.ajax({
			url : "/notice/idForget.do",
			type : "post",
			data : JSON.stringify(data),
			dataType : "text", // controller의 produces와 일치시켜야 함
			contentType : "application/json;charset=utf-8",
			success : function(res){
				console.log(res);
				if (res !== null){
					searchIdValue.text(res);
				}else{
					searchIdValue.text("해당하는 정보의 아이디가 없습니다.");
				}
				
			}
		});
		
	});
	
	pwSearchBtn.on("click", function(){
		
		let memId = $("#memId").val();
		let memEmail = $("#memEmail2").val();
		let memName = $("#memName2").val();
		
		if (memId == null || memId == ""){
			alert("아이디를 입력해주세요!");
			return false;
		}
		if (memEmail == null || memEmail == ""){
			alert("이메일을 입력해주세요!");
			return false;
		}
		if (memName == null || memName == ""){
			alert("이름을 입력해주세요!");
			return false;
		}
		
		let data = {
			memId : memId,	
			memEmail : memEmail,	
			memName : memName	
		};
		
		$.ajax({
			url : "/notice/pwForget.do",
			type : "post",
			data : JSON.stringify(data),
			contentType : "application/json;charset=utf-8",
			success : function(res){
				console.log(res);
				if (res !== null){
					searchPwValue.text(res);
				}else{
					searchPwValue.text("해당하는 정보가 없습니다.");
				}
			}
		});
	});
	
	
	
});
</script>