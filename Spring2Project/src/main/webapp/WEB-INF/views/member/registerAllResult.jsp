<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set value="${member }" var="member"/>
	<table border="1">
			<tr>
				<td>유저 ID</td>
				<td>${member.userId }</td>
			</tr>
			<tr>
				<td>패스워드</td>
				<td>${member.password }</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>${member.username }</td>
			</tr>
			<tr>
				<td>E-mail</td>
				<td>${member.email }</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td><fmt:formatDate value="${member.dateOfbirth }" pattern="yyyy년 MM월 dd일" /></td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
				<c:choose>
					<c:when test="${member.gender == 'female' }">여성</c:when>
					<c:when test="${member.gender == 'male' }">남성</c:when>
					<c:when test="${member.gender == 'other' }">기타</c:when>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td>개발자 여부</td>
				<td>
				<c:choose>
					<c:when test="${member.developer == 'Y' }">개발자</c:when>
					<c:otherwise>일반인</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td>외국인 여부</td>
				<td>
				<c:choose>
					<c:when test="${member.foreigner == true }">외국인</c:when>
					<c:otherwise>내국인</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<td>국적</td>
				<td>
					<c:choose>
						<c:when test="${member.nationality == 'korea'}">대한민국</c:when>
						<c:when test="${member.nationality == 'germany'}">독일</c:when>
						<c:when test="${member.nationality == 'austrailia'}">호주</c:when>
						<c:when test="${member.nationality == 'canada'}">캐나다</c:when>
						<c:when test="${member.nationality == 'usa'}">미국</c:when>
					</c:choose>
				</td>
			</tr>
 			<tr>
				<td>소유차량</td>
				<td>
					<c:forEach items="${member.cars }" var="car">
					${car.toUpperCase() } 
					</c:forEach>
				</td>
			</tr>
 			<tr>
				<td>취미</td>
				<td>
				<c:forEach items="${member.hobbies }" var="hobby">
					<c:if test="${hobby == 'sports' }">운동</c:if>
					<c:if test="${hobby == 'music' }">음악</c:if>
					<c:if test="${hobby == 'movie' }">영화</c:if>
				</c:forEach>
				</td>
			</tr>
			<tr>
				<td>우편번호</td>
				<td>${member.address.postCode }</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>${member.address.location }</td>
			</tr>
			<tr>
				<td>카드1(번호)</td>
				<td>${member.cardList[0].no } </td>
			</tr>
				<tr>
				<td>카드1(유효년월)</td>
				<td><fmt:formatDate value="${member.cardList[0].validMonth }" pattern="yyyy년 MM월 dd일" /></td>
			</tr>
			<tr>
			<td>카드2(번호)</td>
				<td>${member.cardList[1].no }</td>
			</tr>
			<tr>
				<td>카드2(유효년월)</td>
				<td><fmt:formatDate value="${member.cardList[1].validMonth }" pattern="yyyy년 MM월 dd일" /></td>
			</tr> 
	</table>
</body>
</html>