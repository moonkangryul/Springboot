<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<link rel="stylesheet" href="/css/modal/modal.css">
<link rel="stylesheet" href="/css/layout/nav.css">
<link rel="stylesheet" href="/css/order/orderList.css">
<link rel="stylesheet" href="/css/user/modal_review.css">

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<div class="wrap">

	<c:if test="${!empty orderList }">	
	<style>
		body {
			background: #fff;
		}
	</style>
		<section class="title">
			<h1>주문 내역</h1>
		</section>
	</c:if>

	<c:if test="${empty orderList }">
		<img alt="/img/none.gif" src="/img/temp.png" class="temp_img">
	</c:if>

	<c:if test="${fn:length(orderList) >0 }">
		<main>
			<div class="order_list">
				<ul id="order_list">
					<c:forEach begin="0" end="${fn:length(orderList)-1 }" var="i">
						<li>
							<div class="img_box">
								<a href="/store/${orderList[i].storeId }/detail">
									<img src="${orderList[i].storeImg }" alt="/img/none.gif">
								</a>
							</div>

							<div class="info_box">
								<span> <fm:formatDate value="${orderList[i].orderDate }" pattern="M월d일" />

								</span>

								<h2>
									<a href="/store/${orderList[i].storeId }/detail"> ${orderList[i].storeName } </a>
									<a href=${orderList[i].storeId }></a>
								</h2>

								<span class="info">
									<a href="/store/${orderList[i].storeId }/detail">
										<c:set value="${fn:length(cartList[i] )}" var="cart" />
										<c:if test="${cart > 1 }">
											<span>${cartList[i][0].foodName } 외 ${cart -1 }개</span>
											
										</c:if>
										<c:if test="${cart <= 1 }">
											<span>${cartList[i][0].foodName } ${cartList[i][0].amount }개 </span>
										</c:if>
										<span><fm:formatNumber value="${orderList[i].totalPrice + orderList[i].deliveryTip - orderList[i].usedPoint }" pattern="###,###" /> 원</span>
									</a>
								</span>
							</div>

							<div class="review_btn_box">
								<button class="order_detail">상세보기</button>
								<c:if test="${!empty principal }">
									<input type="hidden" class="order_num" value="${orderList[i].orderNum }">
									<input type="hidden" class="store_id" value="${orderList[i].storeId }">
									<c:if test="${empty orderList[i].reviewContent }">
										<button class="review regi">리뷰쓰기</button>
									</c:if>
									<c:if test="${!empty orderList[i].reviewContent }">
										<button class="review modify">리뷰 수정하기</button>
										<input type="hidden" value="${orderList[i].reviewContent }" class="review_content">
										<input type="hidden" value="${orderList[i].score }" class="review_score">
										<input type="hidden" value="${orderList[i].reviewImg}" class="review_img" name="store_img">
									</c:if>
								</c:if>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<%@ include file="/WEB-INF/views/layout/pageBox.jsp"%>
		</main>
	</c:if>
</div>

<%@ include file="/WEB-INF/views/layout/nav.jsp"%>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>
<%@ include file="/WEB-INF/views/modal/modal_review.jsp"%>

<script type="text/javascript" src="/js/order/orderList.js"></script>
<script type="text/javascript" src="/js/utils/util.js"></script>

</body>
</html>