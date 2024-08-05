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
		1) 로그인에 성공한 학생 정보를 아래에 출력해주세요!
		2) 로그인페이지로 버튼을 클릭 시 로그인 페이지로 이동해주세요.
		3) 파일 선택을 통해서 이미지 파일을 선택 후, 프로필 이미지를 설정할 수 있게 해주세요.
		  > 프로필 이미지가 바뀌면 됩니다. (서버 업로드 할 필요 없음)
	 -->
	<c:set value="${thisStu }" var="thisStu" />
	<div class="row">
		<c:forEach items="${studentList}" var="student">
			<c:choose>
				<c:when test="${student == thisStu }">
				<div class="col-md-4">
				<div class="card">
					<div class="card-header">
						${thisStu.memName }님! 환영합니다!
					</div>
					<div class="card-body" style="background-color: red">
						<table class="table table-bordered">
							<tr>
								<td colspan="2">
									<img class="prev" src="https://lh4.googleusercontent.com/proxy/yuhEMCPI6rFy7LulnQK68A-hRwZytbhAkvzkACazt24gH8gGfqEFsOS2bBCDIXe9WgF0d51SlNcSyi8vqxBwA0cUW6P6DUSv28JovHIbosJuj2C3JYlYBILWrh0MInjHj8wgYET8H6hynOI8aJ7B-0MQae3ALnCmTSQuLGZqwdiEujP-WkPcHvR_MRnlFhy5XUVnhO9fRdg" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="file" class="imageFile"/>
								</td>
							</tr>
							<tr>
								<td>아이디</td>
								<td>${thisStu.memId}</td>
							</tr>
							<tr>
								<td>비밀번호</td>
								<td>${thisStu.memPw }</td>
							</tr>
							<tr>
								<td>이름</td>
								<td>${thisStu.memName }</td>
							</tr>
							<tr>
								<td>이메일</td>
								<td>${thisStu.memEmail }</td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td>${thisStu.memPhone }</td>
							</tr>
							
						</table>
					</div>
					<div class="card-footer">
						<button type="button" class="btn btn-primary mb-2" id="golgnPagebtn2">로그인페이지로></button>
					</div>
				</div>
			</div>
				</c:when>
				<c:otherwise>
				<div class="col-md-4">
				<div class="card">
					<div class="card-header">
						${student.memName }님! 환영합니다!
					</div>
					<div class="card-body">
						<table class="table table-bordered">
							<tr>
								<td colspan="2">
									<img class="prev" src="https://lh4.googleusercontent.com/proxy/yuhEMCPI6rFy7LulnQK68A-hRwZytbhAkvzkACazt24gH8gGfqEFsOS2bBCDIXe9WgF0d51SlNcSyi8vqxBwA0cUW6P6DUSv28JovHIbosJuj2C3JYlYBILWrh0MInjHj8wgYET8H6hynOI8aJ7B-0MQae3ALnCmTSQuLGZqwdiEujP-WkPcHvR_MRnlFhy5XUVnhO9fRdg" style="width:100%;"/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="file" class="imageFile"/>
								</td>
							</tr>
							<tr>
								<td>아이디</td>
								<td>${student.memId}</td>
							</tr>
							<tr>
								<td>비밀번호</td>
								<td>${student.memPw }</td>
							</tr>
							<tr>
								<td>이름</td>
								<td>${student.memName }</td>
							</tr>
							<tr>
								<td>이메일</td>
								<td>${student.memEmail }</td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td>${student.memPhone }</td>
							</tr>
							
						</table>
					</div>
					<div class="card-footer">
						<button type="button" class="btn btn-primary mb-2" id="golgnPagebtn1">로그인페이지로></button>
					</div>
				</div>
			</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
</body>
<script>
$(function(){
	
	// 파일 선택 이벤트
	$(".imageFile").on("change", function(event){
		//console.log("change Event...! ");
		
		let imageFile = $(this);
		
		let files = event.target.files;
		let file = files[0];  // 선택한 파일을 꺼낸다.
		//console.log(file);
		
		if(isImageFile(file)){ // 이미지 파일일 때
			// 비동기로 파일 데이터를 전송할 경우 formData()를 이용하여 데이터를 전송한다.
			let formData = new FormData();
			formData.append("file", file);
			
			$.ajax({
				url : "/ajax/uploadAjax",
				type : "post",
				data : formData,
				dataType : "text",
				processData : false, // ?
				contentType : false, // application/json을 false로 바꾸는 것
				success : function(data){
					alert(data);
					if(data === "UPLOAD SUCCESS"){
						let file = event.target.files[0];
						let reader = new FileReader(); // 파일을 읽을 객체 정보
						reader.onload = function(e){
							imageFile.parents("table").find(".prev").attr("src", e.target.result);
						}
						reader.readAsDataURL(file);
					}
				}
				
			});
			
		}else{  // 이미지 파일이 아닐 때
			alert("이미지 파일을 선택해주세요 !");	
		}
		
	});
	
	function isImageFile(file){
		let ext = file.name.split(".").pop().toLowerCase(); // 확장자에 해당하는 문자열을 소문자로 변경
		return ($.inArray(ext, ["jpg", "jpeg", "png", "gif"]) === -1) ? false : true;
	}
	
	
	$(document).on("click", "#golgnPagebtn1", function(){
		location.href= "/test03/login.do";
	});
	
	$(document).on("click", "#golgnPagebtn2", function(){
		console.log("dfdf");
		location.href= "/test03/login.do";
	});
	
});
</script>

</html>