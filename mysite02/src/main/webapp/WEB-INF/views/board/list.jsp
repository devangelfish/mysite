<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>

				<c:set var='limitPage' value='${pageLimit}' />
				<c:set var='currentPage' value='${param.page}' />

				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach var='board' items='${boardList}' varStatus='status'>
						<tr>
							<td>${ status.count + ( (currentPage - 1) * 5 ) }</td>
							<td style='text-align: left; padding-left: ${board.depth * 20}px'><a
								href="${ pageContext.request.contextPath}/board?a=view&no=${board.no}&name=${board.userName}"> <c:if test='${ board.depth > 1 }'>
										<img
											src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
									</c:if> ${ board.title }
							</a></td>
							<td>${ board.userName }</td>
							<td>${ board.hit }</td>
							<td>${ board.regDate }</td>
							<td><c:if test='${ not empty authUser }'>
									<c:if test='${ authUser.name == board.userName }'>
										<a href="${ pageContext.request.contextPath}/board?a=delete" class="del">삭제</a>
									</c:if>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${ currentPage > 1 }">
								<li><a
									href="${ pageContext.request.contextPath}/board?page=${currentPage - 1}">◀</a></li>
							</c:when>
							<c:otherwise>
								<li></li>
							</c:otherwise>
						</c:choose>

						<c:forEach begin='${ index }' end='${ index + 4 }' var='i' step='1'>
							<c:choose>
								<c:when test="${ i <= limitPage }">
									<c:choose>
										<c:when test="${ currentPage == i }">
											<li class="selected">${ i }</li>
										</c:when>
										<c:otherwise>
											<li><a
												href="${ pageContext.request.contextPath}/board?page=${ i }">${ i }</a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<li>${ i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:choose>
							<c:when test="${ currentPage < limitPage }">
								<li><a
									href="${ pageContext.request.contextPath}/board?page=${currentPage + 1}">▶</a></li>
							</c:when>
							<c:otherwise>
								<li></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
				<!-- pager 추가 -->

				<div class="bottom">
					<c:if test="${ not empty authUser }">
						<a
							href="${pageContext.servletContext.contextPath }/board?a=writeform"
							id="new-book">글쓰기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>