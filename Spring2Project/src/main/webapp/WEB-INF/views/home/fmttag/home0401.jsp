<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<p>dateValueShort : ${dateValueShort }</p>
	<fmt:parseDate value="${dateValueShort }" type="date" dateStyle="short" var="dateShort" />
	<p>dateShort : ${dateShort}</p><br>
	<hr>

	<p>dateValueMedium : ${dateValueMedium }</p>
	<fmt:parseDate value="${dateValueMedium }" type="date" dateStyle="medium" var="dateMedium" />
	<p>dateMedium : ${dateMedium}</p><br>
	<hr>

	<p>dateValueLong : ${dateValueLong }</p>
	<fmt:parseDate value="${dateValueLong }" type="date" dateStyle="long" var="dateLong" />
	<p>dateLong : ${dateLong}</p><br>
	<hr>

	<p>dateValueFull : ${dateValueFull }</p>
	<fmt:parseDate value="${dateValueFull }" type="date" dateStyle="full" var="dateValueFull" />
	<p>dateValueFull : ${dateValueFull}</p><br>
	<hr>
	
</body>
</html>