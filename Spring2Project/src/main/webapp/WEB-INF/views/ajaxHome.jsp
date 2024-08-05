<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<h1>Ajax Home</h1>
	<hr>
	
	<form>
		boardNo : <input type="text" name="boardNo" id="boardNo"><br>
		title : <input type="text" name="title" id="title"><br>
		content : <input type="text" name="content" id="content"><br>
		writer : <input type="text" name="writer" id="writer"><br>
		<input type="button" id="btn" value="전송">
	</form>
	
	<div>
		<h3>Headers 매핑 </h3>
		<button type="button" id="putBtn">MODIFY(PUT)</button>
		<button type="button" id="putHeaderBtn">MODIFY(PUT with Headers)</button>
		
		<h3>Content Type 매핑</h3>
		<button type="button" id="postBtn">MODIFY(POST)</button>
		<button type="button" id="putJsonBtn">MODIFY(PUT JSON)</button>
		<button type="button" id="putXmlBtn">MODIFY(PUT XML)</button>

		<h3>Accept 매핑</h3>
		<button type="button" id="getBtn">READ</button>
		<button type="button" id="getJsonBtn">READ(JSON)</button>
		<button type="button" id="getXmlBtn">READ(XML)</button>
	</div>

</body>
<script>
$(function(){
	
	let putBtn = $("#putBtn");
	let putHeaderBtn = $("#putHeaderBtn");

	let postBtn = $("#postBtn");
	let putJsonBtn = $("#putJsonBtn");
	let putXmlBtn = $("#putXmlBtn");

	let getBtn = $("#getBtn");
	let getJsonBtn = $("#getJsonBtn");
	let getXmlBtn = $("#getXmlBtn");
	
	
	// Headers 매핑 Modify(put) 버튼 이벤트
	putBtn.on("click", function(){
		let boardNo = $("#boardNo").val();
		let title = $("#title").val();
		let content = $("#content").val();
		let writer = $("#writer").val();
		
		let boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		}
		
		$.ajax({
			url : "/board/" + boardNo, // 요청 목적지 주소
			type : "put", // method 방식
			data : JSON.stringify(boardObject),  // 전송할 데이터 값
			contentType : "application/json;charset=utf-8", // 요청을 보낼 때의 contentType
			success : function(res){
				console.log("result : " + res);
				if (res === "SUCCESS"){
					alert(res);
				}
			}
		});
	});
	
	// Headers 매핑 Modify(put with headers) 버튼 이벤트
	putHeaderBtn.on("click", function(){
		
		let boardNo = $("#boardNo").val();
		let title = $("#title").val();
		let content = $("#content").val();
		let writer = $("#writer").val();
		
		let boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		}
		
		$.ajax({
			url : "/board/"+boardNo,
			type : "put",
			headers : {
				"X-HTTP-Method-Override" : "PUT"				
			},
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8", 
			success : function(res){
				console.log("res : " + res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
		
	});
	
	// Content-Type 매핑 postBtn 이벤트
	postBtn.on("click", function(){
		
		let boardNo = $("#boardNo").val();
		let title = $("#title").val();
		let content = $("#content").val();
		let writer = $("#writer").val();
		
		let boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		}
		
		$.ajax({
			url : "/board/" + boardNo,
			type : "post",
			data : JSON.stringify(boardObject),
			contentType : "application/json;charset=utf-8",
			success: function(res){
				console.log("res : " + res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
			
		});
		
	});

	
	// Content-Type 매핑 putJsonBtn 이벤트
	putJsonBtn.on("click", function(){
		
		let boardNo = $("#boardNo").val();
		let title = $("#title").val();
		let content = $("#content").val();
		let writer = $("#writer").val();
		
		let boardObject = {
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer
		}
		
		$.ajax({
			url : "/board/" + boardNo,
			type : "put",
			data : JSON.stringify(boardObject),
			contentType : "application/json;charset=utf-8",
			success: function(res){
				console.log("res : " + res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
			
		});
		
	});
	
	// Content-Type 매핑 putXmlBtn 이벤트
	putXmlBtn.on("click", function(){
		
		let boardNo = $("#boardNo").val();
		let title = $("#title").val();
		let content = $("#content").val();
		let writer = $("#writer").val();
		
		let xmlData = "";
		xmlData += "<Board>";
		xmlData += "<boardNo>"+boardNo+"</boardNo>";
		xmlData += "<title>"+title+"</title>";
		xmlData += "<content>"+content+"</content>";
		xmlData += "<writer>"+writer+"</writer>";
		xmlData += "</Board>";
		
		$.ajax({
			url : "/board/" + boardNo, 
			type : "put",
			contentType : "application/xml; charset=utf-8",
			data : xmlData,
			success : function(res){
				console.log(res);
				if(res === "SUCCESS"){
					alert(res);
				}
			}
		});
		
	});
	
	// Accept 매핑 getBtn 클릭 이벤트
	getBtn.on("click", function(){
		let boardNo = $("#boardNo").val();
		
		// get방식 비동기
		$.get("/board/"+boardNo, function(data){
			console.log("data : " + data);
			alert(JSON.stringify(data));
		});
		
	});
	
	// Accept 매핑 getJsonBtn 클릭 이벤트
	getJsonBtn.on("click", function(){

		let boardNo = $("#boardNo").val();
		
		$.ajax({
			url : "/board/" + boardNo,
			type : "get",
			headers : {
				"Accept" : "application/json"
			},
			success : function(res){
				console.log(res);
				alert(JSON.stringify(res));
			}
		});
		
	});
	
	
	// Accept 매핑 getXmlBtn 클릭 이벤트
	getXmlBtn.on("click", function(){
		
		let boardNo = $("#boardNo").val();
		
		$.ajax({
			url : "/board/" + boardNo,
			type : "get",
			headers : {
				"Accept" : "application/xml"
			},
			success : function(res){
				console.log(res);
				alert(xmlToString(res));
			}
		});
		
	});
})


function xmlToString(xmlData){
	
	let xmlString;
	
	// window.ActiveObject는 ActiveObject를 지원하는 브라우저면
	// Object를 리턴하고 그렇지 않으면 null을 리턴한다.
	if(window.ActiveXObject){
		xmlString = xmlDate.xml;
	}else{
		xmlString = (new XMLSerializer()).serializeToString(xmlData);
	}
	return xmlString;
	
}
</script>
</html>