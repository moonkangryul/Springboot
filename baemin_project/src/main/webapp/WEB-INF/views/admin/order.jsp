<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.2/sockjs.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>

<link rel="stylesheet" href="/css/admin/order.css">
<link rel="stylesheet" href="/css/modal/modal.css">

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<div class="move_top">
	<div></div>
</div>

<div class="wrap">
	<input type="hidden" value="${id }" id="store_id">
	
	<section class="tab">
		<ul class="box">
			<li><a href="/admin/myStore">운영중인 가게</a></li>
			<li><a href="/admin/management/sales/${id }">매출 확인</a></li>
			<li><a href="/">홈으로</a></li>
		</ul>
	</section>

	<aside>
		<ul class="aside_tab">
			<li class="active">
				<div>접수 대기</div>
				<!-- <div class="wait_count"></div> -->
			</li>
			<li>
				<div>처리 중</div>
				<!-- <div class="processing_count"></div> -->
			</li>
			<li>
				<div>주문 취소</div>
				<!-- <div class="cancel_count"></div> -->
			</li>
			<li>
				<div>배달 완료</div>
				<!-- <div class="complete_count"></div> -->
			</li>
			<!-- <li>주문 조회</li> -->
		</ul>
	</aside>

	<main>
		<div id="cont_box">
			<ul class="order_list">
				<!-- 주문접수 > 접수 대기 -->
				<!-- <li> -->
				<!-- <ul> -->
				<!-- 주문목록 1개 -->
				<!-- <li class="order_box">
						</li> -->

				<!-- 주문목록 1개 -->
				<!-- </ul> -->
				<!-- </li> -->
			</ul>
		</div>
	</main>
</div>

<%-- <%@ include file="/WEB-INF/view/admin/modal_adminMain.jsp" %> --%>

<script type="text/javascript" src="/js/utils/util.js"></script>
<script type="text/javascript" src="/js/admin/adminOrder.js"></script>

</body>
</html>