<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>	
	
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Board List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<style>
.pc td {
	height: 100px;
	padding-top: 24px;
}
</style>
<body>
	<!-- 
		피시방 사장이 되어, 손님을 받는다.
		1) 손님이 이용할 PC를 선택하고 이름, 이용시간을 설정 후 등록을 진행한다.
			> 이때, 이용 시간으로 설정되어 있는 시간만큼 타이머가 해당 PC자리에 설정된다.
		2) 이용중인 PC를 종료 버튼을 클릭 하면 타이머가 설정되어 있는 PC가 종료되고
			아래 매출현황에 이용시간 만큼의 매출이 기록된다.
		
		*** 손님을 받을 때마다 PC 이용 현황판은 이용 시간만큼 타이머가 동시에 동작해야한다.
	 -->
	<div class="container">
		<h3 class="mt-3">피시방 카운터</h3>
		<div class="row">
			<div class="col-md-4">
				<div class="card">
					<div class="card-header"></div>
					<div class="card-body">
						<table class="table table-bordered">
							<tr>
								<td width="30%">PC번호</td>
								<td id="pcNo">PC-2</td>
							</tr>
							<tr>
								<td>이름</td>
								<td><input type="text" class="form-control" id="userName" name="userName"/></td>
							</tr>
							<tr>
								<td>시간</td>
								<td><input type="number" class="form-control" id="userTime" name="userTime" min="1" max="12"  /></td>
							</tr>
						</table>
					</div>
					<div class="card-footer">
						<button type="button" class="btn btn-primary" id="addBtn">등록</button>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<table class="table table-bordered pc">
					<tr align="center">
						<td data-order="1" >PC-1<br /><span id="timer"></span><br />
						<button type="button" class="btn btn-sm btn-danger">종료</button></td>
						<td data-order="2">PC-2<br /> </td>
						<td data-order="3">PC-3<br /> </td>
						<td data-order="4">PC-4<br /> </td>
						<td data-order="5">PC-5<br /> </td>
						<td data-order="6">PC-6<br /> </td>
						<td data-order="7">PC-7<br /> </td>
						<td data-order="8">PC-8<br /> </td>
					</tr>
					<tr align="center">
						<td data-order="9">PC-9<br /> </td>
						<td data-order="10">PC-10<br /> </td>
						<td data-order="11">PC-11<br /> </td>
						<td data-order="12">PC-12<br /> </td>
						<td data-order="13">PC-13<br /> </td>
						<td data-order="14">PC-14<br /> </td>
						<td data-order="15">PC-15<br /> </td>
						<td data-order="16">PC-16<br /> </td>
					</tr>
					<tr align="center">
						<td data-order="17">PC-17<br /> </td>
						<td data-order="18">PC-18<br /> </td>
						<td data-order="19">PC-19<br /> </td>
						<td data-order="20">PC-20<br /> </td>
						<td data-order="21">PC-21<br /> </td>
						<td data-order="22">PC-22<br /> </td>
						<td data-order="23">PC-23<br /> </td>
						<td data-order="24">PC-24<br /> </td>
					</tr>
					<tr align="center">
						<td data-order="25">PC-25<br /> </td>
						<td data-order="26">PC-26<br /> </td>
						<td data-order="27">PC-27<br /> </td>
						<td data-order="28">PC-28<br /> </td>
						<td data-order="29">PC-29<br /> </td>
						<td data-order="30">PC-30<br /> </td>
						<td data-order="31">PC-31<br /> </td>
						<td data-order="32">PC-32<br /> </td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="card">
					<div class="card-header">이용 안내</div>
					<div class="card-body">
						<ul>
							<li>1시간 이용 시 1,000원 입니다.</li>
							<li>이용 후, 의자를 꼭 넣어주세요.</li>
							<li>화장실은 입구 오른쪽 끝입니다.</li>
							<li>각 흡연실, 비흡연실 구역이 나뉘어져있습니다.</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="card">
					<div class="card-header">매출 현황</div>
					<div class="card-body">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>번호</th>
									<th>PC 번호</th>
									<th>이용 시간</th>
									<th>금액</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>PC-21</td>
									<td>2시간 30분</td>
									<td>2,500원</td>
								</tr>
								<tr>
									<td>2</td>
									<td>PC-9</td>
									<td>3시간 30분</td>
									<td>3,500원</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="card-footer">
						<h5>총 매출 : 45,980,500원</h5>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	
	var addBtn = $("#addBtn");
	var usePcArray = [];
	var order = 0;
	var selectEle = null; // 현재 선택한 Pc자리의 element
	
	$(".pc").on("click", "td", function(){
		selectEle = $(this);
		order = $(this).data("order");
		
		var classNames = $(this).prop("class");
		if(classNames.includes("use")){
			var usePc = usePcArray[order-1];
		}else{
			$("#pcNo").text("PC-"+order);
		}
	});
	
	addBtn.on("click", function(){
		var userName = $("#userName").val();
		var useTime = $("#useTime").val();
		
		if(userName == null || userName == ""){
			alert("이름을 입력해주세요!");
			return false;
		}
		if(useTime == null || useTime == ""){
			alert("시간을 입력해주세요!");
			return false;
		}
		
		if(useTime > 12){
			useTime = 12;
		}
		
		var usePrice = parseInt(useTime) * 1000;
		var useTime = parseInt(useTime) * 60 * 60;	// 시 > 초
		var data = {
			pcUser : userName,
			pcTime : useTime,
			pcPrice : usePrice
		}
		
		usePcArray[order-1] = data;
		selectEle.addClass("use");
		selectEle.css("background-color", "silver");
	});
	
	setInterval(function(){
		for(var i = 0; i < 32; i++){
			if(usePcArray[i] != null){
				var html = "<p>PC-"+(i+1)+"</p>";
				html += "<p>";
				html += usePcArray[i].pcUser + "<br>";
				
				var pTime = usePcArray[i].pcTime;
				html += flowTime(pTime);
				usePcArray[i].pcTime = pTime - 1;
				html += "/<p>";
				$(".pc td:eq("+i+")").html(html);
			}else{
				console.log("PC" + (i+1) + ": 미사용중");
			}
		}
	}, 1000);
	
});

function flowTime(time){
	var timeStr = "";
	
	hour = Math.floor(time/3600);
	var ts = time % 3600;
	min = Math.floor(ts / 60);
	sec = Math.fllor(ts % 60);
	
	timeStr = hour + ":" + min + ":" + s;
	return timeStr;
}
</script>
</html>