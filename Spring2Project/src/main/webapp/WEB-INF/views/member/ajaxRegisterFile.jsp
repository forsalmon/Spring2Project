<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>10. 파일 업로드 Ajax 방식 요청 처리</h1>
	<hr>
	<p>Ajax 방식으로 전달할 파일 요소값을 스프링 MVC가 지원하는 MultipartFile 매개변수로 처리한다.</p>
	<div>
		<input type="file" id="inputFile"><br>
		<hr>
		<img id="preview"/>
	</div>
</body>
<script>
$(function(){
	
	let inputFile = $("#inputFile");
	
	inputFile.on("change", function(event){
		console.log("change Event...! ");
		
		let files = event.target.files;
		let file = files[0];  // 선택한 파일을 꺼낸다.
		
		console.log(file);
		
		// formData : key/value형식으로 데이터가 저장된다.
		// dataType : 응답(response)데이터를 내보낼 때 보내줄 데이터 타입
		// processData : 데이터 파라미터를 data라는 속성으로 넣는데 jquery 내부적으로 쿼리스트링을 구성한다.
		//               파일 전송의 경우 쿼리 스트링을 사용하지 않으므로 해당 설정을 false로 비활성화 한다.
		// contentType : Content-Type을 설정시 사용하는데 해당 설정의 기본값은
		// 				 'application/x-www-form-urlencoded; charset=utf-8'이다.
		//               때문에 기본값으로 나가지 않고 'multipart/form-data'로 나갈 수 있도록 설정을 false로 비활성화 한다.
		//				 request 요청에서 Content-Type을 확인해보면
		//               'multipart/form-data; boundary ===== WebkitFormBoundary[Hashkey]'와 같은 값으로
		//               전송된 것을 확인할 수 있다. 
		
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
							$("#preview").attr("src", e.target.result);
						}
						reader.readAsDataURL(file);
					}
				}
				
			});
			
		}else{  // 이미지 파일이 아닐 때
			alert("이미지 파일을 선택해주세요 !");	
		}
		
	});

});

// Change 이벤트가 발생할 때 선택된 파일이 이미지인지 검증
function isImageFile(file){
	// .pop() : 배열의 마지막 요소 꺼내기
	let ext = file.name.split(".").pop().toLowerCase(); // 확장자에 해당하는 문자열을 소문자로 변경
	// 확장자 중 이미지가 아닌 경우 아래의 확장자에 해당하는 문자를 가지고 있지 않을테니 false 반환
	// 확장자 중 이미지인 경우 0보다 큰 수일테니 true가 반환
	return ($.inArray(ext, ["jpg", "jpeg", "png", "gif"]) === -1) ? false : true;
}
</script>
</html>