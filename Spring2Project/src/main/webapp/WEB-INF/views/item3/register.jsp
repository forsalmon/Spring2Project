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
	<h2>REGISTER</h2>
	<hr>
	
	<form action="/item3/register" method="post" enctype="multipart/form-data" id="item">
		<table border="1">
			<tr>
				<td>상품명</td>
				<td>
					<input type="text" name="itemName" id="itemName" />
				</td>
			</tr>
			<tr>
				<td>파일</td>
				<td>
					<input type="file" id="inputFile" />
					<!-- 썸네일 or 파일명 -->
					<div class="uploadedList">
					
					</div>
				</td>
			</tr>
			<tr>
				<td>개요</td>
				<td>
					<textarea rows="10" cols="30" name="description"></textarea>
				</td>
			</tr>
		</table>
		<div>
			<button type="submit">Register</button>
			<button type="button" onclick="javascript:location.href='/item3/list'">List</button>
		</div>
	</form>
</body>
<script type="text/javascript">
$(function(){
	
	let inputFile = $("#inputFile");
	let uploadedList = $(".uploadedList");
	let item = $("#item");

	item.submit(function(event){
		event.preventDefault();
		
		let that = $(this); //form
		let str = "";
		$(".uploadedList a").each(function(index){
			let value = $(this).attr("href");
			value = value.substr(28); // ?fileName= 다음에 나오는 값
			str += "<input type='hidden' name='files["+index+"]' value='"+value+"'/>";
		});
		console.log("str = " + str);
		that.append(str);
		that.get(0).submit();
	});
	
	inputFile.on("change", function(event){
		console.log("change...!"); 
		
		let files = event.target.files;
		let file = files[0];
		
		let formData = new FormData();
		formData.append("file", file);
		
		$.ajax({
			url : "/item3/uploadAjax",
			type : "post",
			// contentType : application/json;charset=utf-8" => false
			contentType : false, // form 기반 인증을 해야하는데 > 파일을 formData로 넘기려면 미디어 타입 multipart/form-data로 바꿔줘야 하기 때문에 > contentType을 꺼주는 것
			data : formData,
			dataType : "text",
			processData: false, // form을 &변수=값 형식(쿼리스트링형식)으로 자동으로 변경되는 것을 막는 요소
			success : function(data){
				console.log(data);
				let str = "";
				if (checkImageType(data)){ // 이미지면 이미지태그를 이용한 출력
					str = "<div>";
					str += "	<a href='/item3/displayFile?fileName="+data+"'>";
					str += "		<img src='/item3/displayFile?fileName="+getThumbnailName(data)+"'/>";
					str += "	</a>";
					str += "	<span>X</span>";
					str += "</div>";
				}else{ // 파일이면 파일명에 대한 링크로만 출력
					str = "<div>"; 
					str += "	<a href='/item3/displayFile?fileName="+data+"'>"; 
					str += getOriginalName(data);
					str += "	</a>";
					str += "	<span>X</span>";
					str += "</div>"; 
				}
				$(".uploadedList").append(str);
			}
		});
	});
	
	// [ X ] 클릭 시 div영역 삭제하기
	uploadedList.on("click", "span", function(){
		$(this).parent("div").remove();
	});
	
	// 임시 파일로 섬네일 이미지 만들기
	function getThumbnailName(fileName){
		let front = fileName.substr(0, 12); // /2024/05/29/ 폴더
		let end = fileName.substr(12);  // 뒤 파일명
		console.log(front + " :::: " + end);
		return front + "s_" + end;
	}
	
	// 파일명 추출
	function getOriginalName(fileName){
		if(checkImageType(fileName)){
			return;
		}
		let idx = fileName.indexOf("_")+1;
		return fileName.substr(idx);
	}
	
	// 이미지 파일인지 확인
	function checkImageType(fileName){
		let pattern = /jpg|gif|png|jpeg/i; //i<=대소문자구분 안한다는 의미의 정규표현식
		return fileName.match(pattern); //패턴과일치하면 true반환
	}
	
});
</script>
</html>