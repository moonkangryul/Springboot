<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<link rel="stylesheet" href="/css/user/myReview.css">
<link rel="stylesheet" href="/css/modal/modal.css">
<link rel="stylesheet" href="/css/user/modal_review.css">

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<main>
		<section id="main">
				<h1>리뷰 관리</h1>
				<h2>내가 쓴 리뷰</h2>
				<ul class="comment">
						<c:forEach items="${myReviewList }" var="myReviewList">
								<li><input type="hidden" value="${myReviewList }" class="review">
										<div class="myReview">
												<div class="btn_box">
														<button class="modify_btn">수정하기</button>
														<button class="delete_btn">삭제</button>
															<input type="hidden" value="${myReviewList.orderNum }" class="review_order_num">
															<input type="hidden" value="${myReviewList.score }" class="review_score">
															<input type="hidden" value="${myReviewList.reviewImg }" class="review_img">
															<input type="hidden" value="${myReviewList.storeId }" class="review_store_id">
															<input type="hidden" value="${myReviewList.reviewContent }" class="review_review_content">
												</div>

												<div class="title">
														<h3>${myReviewList.storeName }</h3>
														<c:forEach begin="0" end="4" var="i">
																<c:if test="${myReviewList.score >= i}">
																		<i class="fas fa-star"></i>
																</c:if>
																<c:if test="${myReviewList.score < i}">
																		<i class="far fa-star"></i>
																</c:if>
														</c:forEach>
														<span><fm:formatDate value="${myReviewList.regiDate }" pattern="yyyy-MM-dd" /></span>
												</div>

												<c:if test="${!empty myReviewList.reviewImg }">
														<div class="img_box">
																<img alt="/img/none.gif" src="${myReviewList.reviewImg }">
														</div>
												</c:if>

												<div class="text">${myReviewList.reviewContent }</div>
										</div>
										
										<c:if test="${!empty myReviewList.bossComment }">
												<div class="boss_comment">
														<h3>사장님</h3>
														<div>${myReviewList.bossComment }</div>
												</div>
										</c:if>
								</li>
						</c:forEach>
				</ul>
		</section>
</main>

<%@ include file="/WEB-INF/views/layout/nav.jsp"%>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>
<%@ include file="/WEB-INF/views/modal/modal_review.jsp"%>

<script type="text/javascript" src="/js/utils/util.js"></script>
<script src="/js/user/myReview.js"></script>

</body>
</html>