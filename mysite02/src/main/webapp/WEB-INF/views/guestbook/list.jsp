<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bitacademy.mysite.repository.GuestbookRepository"%>
<%@ page import="com.bitacademy.mysite.vo.GuestbookVo"%>
<%
	List<GuestbookVo> list = new GuestbookRepository().findAll();
	int size = list.size();
	int count = 0;
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />	
		<div id="content">
			<div id="guestbook">
				<form action="<%=request.getContextPath() %>/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="message" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE="확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
				<% for(GuestbookVo vo : list) { %>
					<li>
						<table>
							<tr>
								<td><%= size - count++ %></td>
								<td><%= vo.getName() %></td>
								<td><%= vo.getReg_date() %></td>
								<td><a href="<%=request.getContextPath() %>/guestbook?a=deleteform&no=<%=vo.getNo()%>">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4>
								<%= vo.getMessage().replaceAll("\r\n", "<br/>") %>
								</td>
							</tr>
						</table>
						<br>
					</li>
					<% } %>
				</ul>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>