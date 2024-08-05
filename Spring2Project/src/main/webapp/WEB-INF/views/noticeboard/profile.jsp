<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>마이페이지</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">Home</a></li>
					<li class="breadcrumb-item active">User Profile</li>
				</ol>
			</div>
		</div>
	</div>
</section>

<section class="content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<div class="card card-dark card-outline">
					<div class="card-header">
						<div class="card-title">
							<h4>내정보</h4>
						</div>
					</div>
					<div class="card-body">
						<div class="position-relative">
							<!-- 프로필 이미지 띄우기 -->
							<!-- 
								서버 clean을 하게 되면 이미지는 엑박이 뜨는데 상관 없음
								서버는 사용자가 사용하는 서버랑 개발 서버가 나뉘게 되는데 사용자가 사용하는 서버는 clean될 일이 없기 때문에
								발생가능성 없음
							-->
							<img src="${member.memProfileimg } " alt="${member.memProfileimg }" class="img-fluid">
							<div class="ribbon-wrapper ribbon-lg">
								<div class="ribbon bg-success text-lg">Profile</div>
							</div>
						</div>
						<div class="row mt-4">
							<div class="col-md-4 text-bold">아이디</div>
							<div class="col-md-8">
							${member.memId }
							</div>
						</div>
						<div class="row mt-2">
							<div class="col-md-4 text-bold">비밀번호</div>
							<div class="col-md-8">
							${member.memPw }
							</div>
						</div>
						<div class="row mt-2">
							<div class="col-md-4 text-bold">이름</div>
							${member.memName }
							<div class="col-md-8">
							</div>
						</div>
						<div class="row mt-2">
							<div class="col-md-4 text-bold">성별</div>
							<c:if test="${member.memGender eq 'M'}">남성</c:if>
							<c:if test="${member.memGender eq 'F'}">여성</c:if>
							<div class="col-md-8">
							</div>
						</div>
						<div class="row mt-2">
							<div class="col-md-4 text-bold">이메일</div>
							${member.memEmail }
							<div class="col-md-8">
							</div>
						</div>
						<div class="row mt-2">
							<div class="col-md-4 text-bold">전화번호</div>
							${member.memPhone }
							<div class="col-md-8">
							</div>
						</div>
						<div class="row mt-2">
							<div class="col-md-4 text-bold">주소</div>
							${member.memAddress1 } ${member.memAddress2 }
							<div class="col-md-8">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-9">
				<div class="card card-dark card-outline">
					<div class="card-header">
						<div class="row">
							<div class="col-md-10">
								<h4>내정보 수정</h4>
							</div>
							<div class="col-md-2" align="right">
								<button type="button" class="btn btn-info" id="myInfoUpdBtn">수정하기</button>
							</div>
						</div>
					</div>
					<div class="card-body">
						<div class="tab-content">
							<div class="tab-pane active">
								<form class="form-horizontal" id="profileForm" action="/notice/profileUpdate.do" method="post" enctype="multipart/form-data">
									<div class="form-group row">
										<label class="col-sm-2 col-form-label">프로필이미지</label>
										<div class="col-md-10">
											<div class="custom-file">
												<input type="file" class="custom-file-input" id="imgFile" name="imgFile"> 
												<label class="custom-file-label" for="imgFile">프로필 이미지를 선택해주세요</label>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<label for="memId" class="col-sm-2 col-form-label">아이디</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="memId" name="memId" value="${member.memId }" placeholder="아이디를 입력해주세요." readonly="readonly">
											<div id="profileIdCheck"></div>	
											<span id="is_exist" style="color:green; display:none;">맞는 회원정보입니다. 잘 입력했습니다. </span> 
											<span id="is_notexist" style="color:red; display:none;">등록되지 않은 회원정보입니다. 확인해주세요</span>
										</div>
										
									</div>
									<div class="form-group row">
										<label for="memPw" class="col-sm-2 col-form-label">비밀번호</label>
										<div class="col-sm-10">
											<input type="password" class="form-control" id="memPw" name="memPw" value="${member.memPw }" placeholder="비밀번호를 입력해주세요.">
										</div>
									</div>
									<div class="form-group row">
										<label for="memName" class="col-sm-2 col-form-label">이름</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="memName" name="memName" value="${member.memName }" placeholder="이름을 입력해주세요.">
										</div>
									</div>
									<div class="form-group row">
										<label for="" class="col-sm-2 col-form-label">성별</label>
										<div class="col-sm-10">
											<div class="icheck-primary d-inline">
												
												<input type="radio" id="memGenderM" name="memGender" value="M" <c:if test="${member.memGender == 'M'}">checked</c:if>> 
												<label for="memGenderM">남자</label>
											</div>
											<div class="icheck-primary d-inline">
												<input type="radio" id="memGenderF" name="memGender" value="F" <c:if test="${member.memGender == 'F'}">checked</c:if>> 
												<label for="memGenderF">여자</label>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<label for="memEmail" class="col-sm-2 col-form-label">이메일</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="memEmail" name="memEmail" value="${member.memEmail }" placeholder="이메일을 입력해주세요.">
										</div>
									</div>
									<div class="form-group row">
										<label for="memPhone" class="col-sm-2 col-form-label">전화번호</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="memPhone" name="memPhone" value="${member.memPhone }" placeholder="전화번호를 입력해주세요.">
										</div>
									</div>
									<div class="input-group mb-3">
										<label for="inputSkills" class="col-sm-2 col-form-label">주소</label>
										<div class="col-sm-10">
											<div class="input-group mb-3">
												<input type="text" class="form-control" name="memPostcode" id="memPostcode" placeholder="우편번호를 입력해주세요">
												<span class="input-group-append">
													<button type="button" class="btn btn-secondary btn-flat" onclick="DaumPostcode()">우편번호 찾기</button>
												</span>
											</div>
											<div class="input-group mb-3">
												<input type="text" class="form-control" name="memAddress1" id="memAddress1"	value="${member.memAddress1 }" placeholder="주소를 입력해주세요">
											</div>
											<div class="input-group mb-3">
												<input type="text" class="form-control" name="memAddress2" id="memAddress2"	value="${member.memAddress2 }" placeholder="상세주소를 입력해주세요">
											</div>
											<input type="hidden" name="memNo" id="memNo" value="${member.memNo }" />
										</div>
									</div>
									<sec:csrfInput />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>    
<script type="text/javascript">
$(function(){
	
	let myInfoUpdBtn = $("#myInfoUpdBtn");
	let imgFile = $("#imgFile");
	let profileIdCheck = $("#profileIdCheck"); // 메시지 띄울 div요소
	let memId = $("#memId"); // 정규식 & 중복체크 때 사용할 요소
	let profileForm = $("#profileForm"); // 수정하기 버튼 클릭 후 제출할 form요소
	
	let idCheckFlag = false;           	 // 중복확인 여부 flag

	// 프로필 이미지 선택 이벤트(Open파일을 통해 이미지 파일을 선택하면 이벤트 발생)
	imgFile.on("change", function(event){
		let file = event.target.files[0];	// 선택한 파일(지금은 이미지)
		if(isImageFile(file)){ // 이미지 파일이라면
			let reader = new FileReader();
			reader.onload = function(e){
				// 프로필 이미지 Element에 src경로로  result를 세팅한다.
				// 이미지 파일 데이터가 base64인코딩 형태로 변형된 데이터가 src경로에 설정된다.
				imgFile.attr("src", e.target.result);
				console.log(e.target.result); 
			}
			reader.readAsDataURL(file);
		}else{ // 이미지 파일이 아니라면
			alert("이미지 파일을 선택해주세요 !");
		}
	});

	/*
	memId.on("keyup", function(){
		
		let regExp = /^[a-zA-Z0-9]{4,}$/g;
		if(!regExp.test(memId.val())){ // 아이디가 공백인 경우
			profileIdCheck.html("<span class='spanMsg'>영문과 숫자로만, 4자 이상 입력해주세요.</span>");
			$(".spanMsg").css("color", "red");
		}else{ // 공백이 아니면 중복 체크 진행
			let memIdValue = memId.val();
			let data = {
					memId : memIdValue	
			}
			
			$.ajax({
				url : "/notice/profileIdCheck.do",
				type : "post",
				data : JSON.stringify(data),
				contentType : "application/json;charset=utf-8",
				success : function(res){
					if(res === "NOTEXIST"){ // 해당하는 아이디가 없는 경우
						profileIdCheck.html("<span class='spanMsg'>존재하지 않는 아이디입니다. 확인해주세요</span>");
						$(".spanMsg").css("color", "red");
					}else{ // 아이디 중복
						profileIdCheck.html("<span class='spanMsg'>제대로 입력하셨습니다. 존재하는 회원입니다.</span>");
						$(".spanMsg").css("color", "blue");
					}
				}
			});
		}
	}); 
	*/
	
	// 수정하기 버튼 클릭 이벤트
	myInfoUpdBtn.on("click", function(){
		console.log("수정하기 버튼 클릭..! ");
		
		let imgFile = $("#imgFile").val();
		let memNo = $("#memNo").val();
		let memId = $("#memId").val();
		let memPw = $("#memPw").val();
		let memName = $("#memName").val();
		let memGender = $("input[name=memGender]:checked").val();
		let memEmail = $("#memEmail").val();
		let memPhone = $("#memPhone").val();
		let memPostcode = $("#memPostcode").val();
		let memAddress1 = $("#memAddress1").val();
		let memAddress2 = $("#memAddress2").val();
		
		profileForm.submit();
		
	});
	
	
});

//이미지 파일인지 여부 확인
function isImageFile(file){
	let ext = file.name.split(".").pop().toLowerCase(); //파일에서 확장자를 가져온다.
	return ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) ? false : true;
}


// 주소 검색 api
function DaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
            } 
            
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('memPostcode').value = data.zonecode;
            document.getElementById("memAddress1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("memAddress2").focus();
        }
    }).open();
}
</script>