<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>

<style>
header .admin_page_btn {
	font-size: 13px;
	position: absolute;
	right: 10px;
	top: 10px;
	font-weight: bold;
}

header .admin_page_btn a {
	border: 1px solid #ddd;
	border-radius: 10px;
	padding: 5px;
	background: #fff;
	font-size: 13px;
	display: block;
}

.right_box {
	position: absolute;
	right: 10%;
	bottom: 0px;
}

.nav_font {
	font-size: 1.8rem;
	color: white;
	font-weight: lighter;
}
</style>

</head>
<body>
	<header>
		<div id="header">
			<ul>
				<c:choose>
					<c:when test="${empty principal }">
						<li class="right_box">
							<a class="nav_font" href="/auth/signin">로그인</a>
							<span class="nav_font"> | </span> 
							<a class="nav_font"	href="/auth/signup">회원가입</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="right_box">
							<a class="nav_font" href="/logout">[${principal.nickname }님	반갑습니다.] 로그아웃</a>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>

			<a href="/"> <img src="/img/baemin.jpg" alt="/img/none.gif">
			</a>

			<c:if test="${principal.role eq 'ROLE_ADMIN' }">
				<c:set var="uri"
					value="${requestScope['javax.servlet.forward.request_uri']}" />

				<div class="menu_tab_box">
					<div class="menu_tab">
						<span> </span> <span> </span> <span> </span>
					</div>
				</div>

				<div id="menu">
					<div class="box">
						<c:choose>
							<c:when test="${fn:contains(uri, '/admin/management') }">
								<div>
									<a href="/admin/myStore">운영중인 가게</a>
								</div>
								<div>
									<a href="/admin/management/order/${id}">주문 접수</a>
								</div>
								<div>
									<a href="/admin/management/sales/${id}">매출 확인</a>
								</div>
							</c:when>

							<c:otherwise>
								<div>
									<a href="/admin/myStore">운영중인 가게</a>
								</div>
							</c:otherwise>
						</c:choose>

						<div>
							<a href="/">홈으로</a>
						</div>
					</div>
				</div>

				<div id="menu_bg"></div>

				<script>
					console

					$(".menu_tab").click(function() {
						const menuTab = $(this);
						const menu = $("#menu");
						const menuBg = $("#menu_bg");

						function hide() {
							menuTab.removeClass("active");
							menu.removeClass("active");
							menuBg.hide();
						}

						function show() {
							menuTab.addClass("active");
							menu.addClass("active");
							menuBg.show();
						}

						if ($(this).hasClass("active")) {
							hide();
						} else {
							show();
						}

						if (menuBg.css("display") != "none") {
							menuBg.click(function() {
								hide();
							})
						}
					});
				</script>
			</c:if>
		</div>
	</header>