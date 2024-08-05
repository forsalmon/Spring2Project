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
	<h2>REMOVE</h2>
	<hr>
	<form action="/item3/remove" method="post" enctype="multipart/form-data">
		<input type="hidden" name="itemId" value="${item.itemId }" />
		<table border="1">
			<tr>
				<td>상품명</td>
				<td>
					<input type="text" name="itemName" id="itemName" value="${item.itemName }"
					disabled="disabled"/>
					
				</td>
			</tr>
			<tr>
				<td>파일</td>
				<td>
					<div class="uploadedList"></div>
				</td>
			</tr>
			<tr>
				<td>개요</td>
				<td>
					<textarea rows="10" cols="30" name="description" disabled="disabled">${item.description }</textarea>
				</td>
			</tr>
		</table>
		<div>
			<button type="submit">Remove</button>
			<button type="button" onclick="javascript:location.href='/item3/list'">List</button>
		</div>
	</form>
</body>
<script type="text/javascript">
$(function(){

	let itemId = ${item.itemId};
	console.log("item : " + itemId);
	
	$.getJSON("/item3/getAttach/" + itemId, function(list){
		$(list).each(function(){
			console.log("data : " + this);
			let data = this;
			let str = "";
			
			if (checkImageType(data)){ // 이미지면 이미지태그를 이용한 출력
				str = "<div>";
				str += "	<a href='/item3/displayFile?fileName="+data+"'>";
				str += "		<img src='/item3/displayFile?fileName="+getThumbnailName(data)+"'/>";
				str += "	</a>";
				str += "</div>";
			}else{ // 파일이면 파일명에 대한 링크로만 출력
				str = "<div>"; 
				str += "	<a href='/item3/displayFile?fileName="+data+"'>"; 
				str += getOriginalName(data);
				str += "	</a>";
				str += "</div>"; 
			}
			$(".uploadedList").append(str);
		});
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