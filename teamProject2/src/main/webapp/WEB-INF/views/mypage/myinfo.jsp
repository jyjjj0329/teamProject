<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/layout/nav.jsp" />
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
</head>
<body>
	<%--나중에 고쳐야할 부분 --%>
	<c:if test="${sessionScope.seller_pw == pw}">
		<table border="1">
		<tr>
			<th>${buyerVO.id}님</th>
			<th>이  름 : ${buyerVO.name}</th>
			<th>닉네임: ${buyerVO.nickname}</th>
			<th><button type="button" onclick="location.href='pwchange'">비밀번호변경</button></th>
			<th>성  별 :  ${buyerVO.gender}</th>
			<th>나  이 : ${buyerVO.age}</th>
			<th>이메일: ${buyerVO.email}</th>
			<th>휴대폰: ${buyerVO.phonenum}</th>
			<th>카  드 : ${buyerVO.cardNum}</th>
			<th>지  역 : ${buyerVO.area}</th>
			<th>주  소 : ${buyerVO.address}</th>
		</tr>
		</table>
		<button	type="button" onclick="location.href='infochange'">수 정</button>	
		<button	type="button" onclick="location.href='infodelete'">탈 퇴</button>	
	
	
	</c:if>
	<c:if test="${sessionScope.seller_pw != pw}">
		alert("비밀번호가 일치하지 않습니다.");
	
	
	</c:if>
</body>
</html>