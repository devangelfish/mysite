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
		<c:set var='availablePage' value='${lastPage}' />
		<c:set var='currentPage' value='${page}' />

		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${ pageContext.request.contextPath}/board/search"
					method="post">
					<input type="text" id="kwd" name="keyword" value=""> <input
						type="hidden" name="page" value="1"> <input
						type="submit" value="찾기">
				</form>

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
								href="${ pageContext.request.contextPath}/board/${currentPage}/view/${board.no}/${board.userName}">
									<c:if test='${ board.depth > 1 }'>
										<img
											src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
									</c:if> ${ board.title }
							</a></td>
							<td>${ board.userName }</td>
							<td>${ board.hit }</td>
							<td>${ board.regDate }</td>
							<td><c:if test='${ not empty authUser }'>
									<c:if test='${ authUser.name == board.userName }'>
										<a
											href="${ pageContext.request.contextPath}/board/${currentPage}/delete/${board.no}/${board.userName}"
											class="del">삭제</a>
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
								<c:choose>
									<c:when test="${ not empty keyword }">
										<li><a
											href="${pageContext.request.contextPath}/board?a=keywordlist&keyword=${param.keyword}&page=${currentPage-1}">◀</a>
										</li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${ pageContext.request.contextPath}/board/${currentPage-1}">◀</a></li>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<li></li>
							</c:otherwise>
						</c:choose>

						<c:forEach begin='${ index }' end='${ index + 4 }' var='i'
							step='1'>
							<c:choose>
								<c:when test="${ i <= availablePage }">
									<c:choose>
										<c:when test="${ currentPage == i }">
											<li class="selected">${ i }</li>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${ not empty keyword }">
													<li><a
														href="${pageContext.request.contextPath}/board?a=keywordlist&keyword=${param.keyword}&page=${i}">${ i }</a>
													</li>
												</c:when>
												<c:otherwise>
													<li><a
														href="${ pageContext.request.contextPath}/board/${i}">${i}</a>
													</li>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<li>${ i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:choose>
							<c:when test="${ currentPage < availablePage }">
								<c:choose>
									<c:when test="${ not empty keyword }">
										<li><a
											href="${pageContext.request.contextPath}/board?a=keywordlist&keyword=${param.keyword}&page=${currentPage + 1}">▶</a>
										</li>
									</c:when>
									<c:otherwise>
										<li><a
											href="${ pageContext.request.contextPath}/board/${currentPage+1}">▶</a></li>
									</c:otherwise>
								</c:choose>
								<!-- <li><a
									href="${ pageContext.request.contextPath}/board?page=${currentPage + 1}">▶</a></li> -->
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
							href="${pageContext.servletContext.contextPath }/board/write"
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