<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/user/myPage.css">

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<div class="wrap">
		<section class="title">
				<h1>마이 페이지</h1>
		</section>

		<main>
				<div class="container">
						<div class="grid_box">
								<div class="login_box">
										<c:if test="${empty principal }">
												<a href="/auth/signin">
														<span>로그인을 해주세요</span>
												</a>
										</c:if>

										<c:if test="${!empty principal }">
												<c:set var="nickname" value="${principal.nickname }" />
														<a href="/user/myInfo">
																<span class="nickname" data-nickname=${nickname }>${nickname }</span>
														</a>
												<button type="button" class="logout">로그아웃</button>
										</c:if>
								</div>

								<div>
										<a href="/user/point" onclick="return loginCheck();">
												<span class="img_box">
														<img src="/img/icon11.png" alt="포인트">
												</span>
												<span>포인트</span>
										</a>
								</div>

								<div>
										<a class="updating" href="/myPage/coupon" onclick="return false;">
												<span class="img_box">
														<img src="/img/icon22.png" alt="쿠폰함">
												</span>
												<span>쿠폰함</span>
										</a>
								</div>

								<div>
										<a class="updating" href="/myPage/gift" onclick="return false;">
												<span class="img_box">
														<img src="/img/icon33.png" alt="선물함">
												</span>
												<span>선물함</span>
										</a>
								</div>

								<div>
										<a href="/likes/store">
												<span class="img_box">
														<img src="/img/icon44.png" alt="찜한가게">
												</span>
												<span>찜한가게</span>
										</a>
								</div>

								<div>
										<a href="/orderList">
												<span class="img_box">
														<img src="/img/icon55.png" alt="주문내역">
												</span>
												<span>주문내역</span>
										</a>
								</div>

								<div>
										<a href="/user/myReview" onclick="return loginCheck()">
												<span class="img_box">
														<img src="/img/icon66.png" alt="리뷰관리">
												</span>
												<span>리뷰관리</span>
										</a>
								</div>
						</div>
				</div>
		</main>
</div>

<%@ include file="/WEB-INF/views/layout/nav.jsp"%>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>

<script type="text/javascript">
	$(".updating").click(function() {
		swal("업데이트 준비 중 입니다");
	})

	$(".logout").click(function() {
		location.href = "/logout";
	})

	function loginCheck() {
		const nickname = $(".nickname").data("nickname");
		if (!nickname) {
			swal("로그인 후 이용 가능합니다");
			return false;
		}
		return true;
	}
</script>
</body>
</html>