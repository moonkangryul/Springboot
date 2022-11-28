<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/layout/link.jsp"%>

<link rel="stylesheet" href="/css/user/point.css">

<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<section class="title">
		<h1>포인트</h1>
</section>

<main>
		<div class="my_point">
				<div>
						<span>보유 포인트</span>
						<span id="my_point" data-point="${point }">
							<fm:formatNumber value="${point }" />
						</span>
				</div>

				<div class="point_regi">
						<div>
								<button>상품권 등록하기</button>
						</div>

						<div class="point_number_area">
								<div>
										<input type="text" class="point_number" maxlength="20" name="giftCardNum" placeholder="상품권 번호를 입력해주세요">
										<input type="button" value="등록">
								</div>
						</div>
				</div>
		</div>

		<h2>포인트 이용 내역</h2>
		<hr>
		<ul class="point_his">
				<c:forEach items="${myPoint }" var="myPoint">
						<li>
								<div>
										<div>${myPoint.info }</div>
										<div>
												<fm:formatDate value="${myPoint.usedDate }" pattern="yyyy.MM.dd" />
										</div>
								</div>

								<div>
										<c:if test="${myPoint.point > 0 }">
												<div class="plus">
														<fm:formatNumber value="${myPoint.point }" pattern="###,###" />
												</div>
										</c:if>

										<c:if test="${myPoint.point < 0 }">
												<div class="used">
														<fm:formatNumber value="${myPoint.point }" pattern="###,###" />
												</div>
										</c:if>
								</div>
						</li>
						<hr>
				</c:forEach>
		</ul>
</main>

<%@ include file="/WEB-INF/views/layout/nav.jsp"%>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>

<script src="/js/user/point.js"></script>

</body>
</html>