<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/store/likes.css">
<link rel="stylesheet" href="/css/store/store-li.css">

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<div class="wrap">
	<c:if test="${!empty likesList }">
		<style>
			body {
				background: #fff;
			}
		</style>
		<section class="title">
			<h1>찜</h1>
		</section>
	</c:if>

	<main>
		<div class="box">
			<c:if test="${empty likesList }">
				<div class="temp">
					<img alt="/img/none.gif" src="/img/jjim.png">
				</div>
			</c:if>

			<ul class="store">
				<c:set var="store_admin" value="/store" />
				<c:forEach items="${likesList }" var="storeList">
					<%@ include file="/WEB-INF/views/store/store-li.jsp"%>
				</c:forEach>
			</ul>
		</div>
	</main>
</div>

<%@ include file="/WEB-INF/views/layout/nav.jsp"%>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>

<script type="text/javascript" src="/js/store/likes.js"></script>

</body>
</html>