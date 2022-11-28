$(document).ready(function() {
	const pathArr = location.pathname.split("/");
	const storeId = pathArr[pathArr.length - 1];
	const listView = 10; // 주문 목록 최대 갯수

	$(".move_top").click(function() {
		$("html").animate({ scrollTop: 0 }, 200);
	})

	function errMsg(status) {
		if (status.status == 401) {
			alert("권한이 없습니다");
		}
		/*else {
				alert("에러");
		}*/
	}

	const listInfo = (function() {
		const listArr = ["주문접수 대기 중", "배달 준비 중", "주문 취소", "배달 완료"];
		let nowList = listArr[0];
		let page = 1;
		let runNextPage = false; // false일 때만 다음 페이지를 불러올 수 있다.
		let waitCount = 0;
		let procCount = 0;
		let cancelCount = 0;
		let completeCount = 0;
		let orderList = [];
		let cartList = [];

		const getNowList = function() {
			return nowList;
		}
		const setNowList = function(set) {
			nowList = listArr[set];
		}
		const resetPage = function() {
			page = 1;
		}
		const nextPage = function() {
			page++;
		}
		const setPage = function(set) {
			page = set;
		}
		const nowPage = function() {
			return page;
		}
		const getRunNextPage = function() {
			return runNextPage;
		}
		const setRunNextPage = function(set) {
			runNextPage = set;
		}
		const setWaitCount = function(set) {
			waitCount = set;
		}
		const getWaitcount = function() {
			return waitCount;
		}
		const setProcCount = function(set) {
			procCount = set;
		}
		const getProcCount = function() {
			return procCount;
		}
		const setCancelCount = function(set) {
			cancelCount = set;
		}
		const getCancelCount = function() {
			return cancelCount;
		}
		const setCompleteCount = function(set) {
			completeCount = set;
		}
		const getCompleteCount = function() {
			return completeCount;
		}
		const getOrderList = function(index) {
			return orderList[index];
		}
		const setOrderList = function(set) {
			orderList = set;
		}
		const concatOrderList = function(set) {
			orderList = orderList.concat(set);
		}
		const getCartList = function(index) {
			return cartList[index];
		}
		const setCartList = function(set) {
			cartList = set;
		}
		const concatCartList = function(set) {
			cartList = cartList.concat(set);
		}
		const resetList = function() {
			cartList = [];
			orderList = [];
		}
		const removeCartList = function(index) {
			cartList.splice(index, 1);
		}
		const removeOrderList = function(index) {
			orderList.splice(index, 1);
		}

		return {
			getNowList: getNowList,
			setNowList: setNowList,
			resetPage: resetPage,
			nextPage: nextPage,
			setPage: setPage,
			nowPage: nowPage,
			getRunNextPage: getRunNextPage,
			setRunNextPage: setRunNextPage,
			setWaitCount: setWaitCount,
			getWaitcount: getWaitcount,
			setProcCount: setProcCount,
			getProcCount: getProcCount,
			setCancelCount: setCancelCount,
			getCancelCount: getCancelCount,
			setCompleteCount: setCompleteCount,
			getCompleteCount: getCompleteCount,
			getOrderList: getOrderList,
			setOrderList: setOrderList,
			getCartList: getCartList,
			setCartList: setCartList,
			concatOrderList: concatOrderList,
			concatCartList: concatCartList,
			resetList: resetList,
			removeCartList: removeCartList,
			removeOrderList: removeOrderList
		}
	})();

	function htmlWrite(result) {
		let html = "";
		for (var i = 0; i < result.cartList.length; i++) {

			const orderList = result.orderList[i];
			const cartList = result.cartList[i];

			let foodInfo = [];
			for (var j = 0; j < cartList.length; j++) {
				foodInfo.push(foodHtml(cartList[j]));
			}

			let btnValue = "";
			let btnClass = "";
			let btnValue2 = "주문 취소";
			let btnClass2 = "order_cancel";

			if (listInfo.getNowList() == '주문접수 대기 중') {
				btnValue = "주문 접수";
				btnClass = "order_accept";
			} else {
				btnValue = "완료";
				btnClass = "complete";
			}

			html +=
				`<li class="order_box">
					<div class="time">
		    			<div>${moment(orderList.orderDate).format("MM월 DD일")}</div>
		    			<div>${moment(orderList.orderDate).format("HH시 mm분")}</div>
		    		</div>
	   	
		    		<div class="info">
	              		<div style="font-weight: bold;">
	               			<span>
	              				<span>[메뉴  ${cartList.length}개] ${orderList.totalPrice}원</span> 
	              				<span class="payMethod"> ${orderList.payMethod}</span>
	            			</span>
	           			</div>
	                        		
	               		<div>${foodInfo} </div>
	               		<div>${orderList.deliveryAddress2}</div>
	               		
	               		<div>${orderList.storeName}</div> 
		            </div>`

			if (listInfo.getNowList() == '주문접수 대기 중' || listInfo.getNowList() == '배달 준비 중') {
				html += `<div class="button_box">
							<input type="button" value="${btnValue}" class="${btnClass} btn">
						</div>
						<div class="button_box">
							<input type="button" value="${btnValue2}" class="${btnClass2} btn">
						</div>
				</li>`;
			}
		}

		return html;
	}

	function foodHtml(cart) {
		let food = cart.foodName;
		let option = [];

		if (cart.optionName != null) {
			for (var i = 0; i < cart.optionName.length; i++) {
				option.push(cart.optionName[i]);
			}
		}

		if (option != "") {
			option = '[' + option + ']';
		}

		return food + option;
	}

	function orderList() {
		const page = listInfo.nowPage();
		const list = listInfo.getNowList();
		//listInfo.setRunNextPage(true);
		
		
		$.ajax({
			url: "/api/admin/management/orderList",
			type: "get",
			data: {
				storeId: storeId,
				list: list,
				page: page
			}
		})
			.done(function(result) {
				console.log(result);

				const count1 = result.orderList[0].count1;
				const count2 = result.orderList[0].count2;
				const count3 = result.orderList[0].count3;
				const count4 = result.orderList[0].count4;

				listInfo.setWaitCount(count1);
				listInfo.setProcCount(count2);
				listInfo.setCancelCount(count3);
				listInfo.setCompleteCount(count4);

				$(".wait_count").text(count1);
				$(".processing_count").text(count2);
				$(".cancel_count").text(count3);
				$(".complete_count").text(count4);

				const html = htmlWrite(result, list);

				$(".order_list").html(html);
				listInfo.setCartList(result.cartList);
				listInfo.setOrderList(result.orderList);
			})
			.fail(function(data) {
				errMsg(data);
			})
	}

	$(".aside_tab li").click(function() {
		$(".order_list").html("");
		$(".aside_tab li").removeClass("active");
		$(this).addClass("active");

		const index = $(this).index();
		listInfo.setNowList(index);
		listInfo.resetPage();
		listInfo.setRunNextPage(false);

		orderList();
	})

	// 스크롤시 다음 페이지
	$(window).scroll(function() {
		const winHeight = $(window).height();
		const docHeight = $(document).height();
		const top = $(window).scrollTop();

		if (docHeight <= winHeight + top + 10) {
			if (!listInfo.getRunNextPage()) {
				// listInfo.nextPage();
				const list = $(".order_list li").length;

				if (list == 0) {
					listInfo.resetPage();
				} else {
					if (list == (listView * listInfo.nowPage())) {
						listInfo.setPage(Math.floor((list - 1) / listView) + 2);
					}
				}

				orderList();
				listInfo.setRunNextPage(true);

				setTimeout(function() {
					listInfo.setRunNextPage(false);
				}, 2000);

				return;
			}
		}
	})

	orderList();

	function listRefresh(index, count) {
		listInfo.removeCartList(index);
		listInfo.removeOrderList(index);
		$(".order_box").eq(index).remove();
		const list = $(".order_list li").length;

		if (list == 0) {
			listInfo.resetPage();
		} else {
			listInfo.setPage(Math.floor((list - 1) / listView) + 1);
		}

		if (list < count) {
			console.log("lsit : " + list + " + count : " + count);
			orderList();
		}
	}

	// 주문수락 시 
	function accept(index) {
		const waitCount = listInfo.getWaitcount() - 1;
		const procCount = listInfo.getProcCount() + 1;
		$(".wait_count").text(waitCount);
		$(".processing_count").text(procCount);
		listInfo.setWaitCount(waitCount);
		listInfo.setProcCount(procCount);
		const count = listInfo.getWaitcount();
		listRefresh(index, count);
	}

	// 주문취소 시
	function cancle(index, list) {
		if (list == '배달 준비 중') {
			const procCount = listInfo.getProcCount() - 1;
			const cancelCount = listInfo.getCancelCount() + 1;
			$(".processing_count").text(procCount);
			$(".cancel_count").text(cancelCount);
			listInfo.setProcCount(procCount);
			listInfo.setCancelCount(cancelCount);
			const count = listInfo.getProcCount();
			listRefresh(index, count);
		} else {
			const waitCount = listInfo.getWaitcount() - 1;
			const cancelCount = listInfo.getCancelCount() + 1;
			$(".wait_count").text(waitCount);
			$(".cancel_count").text(cancelCount);
			listInfo.setWaitCount(waitCount);
			listInfo.setCancelCount(cancelCount);
			const count = listInfo.getWaitcount();
			listRefresh(index, count);
		}
	}

	// 주문완료 시
	function complete(index) {
		const procCount = listInfo.getProcCount() - 1;
		const completeCount = listInfo.getCompleteCount() + 1;
		$(".processing_count").text(procCount);
		$(".complete_count").text(completeCount);
		listInfo.setProcCount(procCount);
		listInfo.setCompleteCount(completeCount);
		const count = listInfo.getProcCount();
		listRefresh(index, count);
	}

	$(document).on("click", ".order_accept", function() {
		const orderIndex = $(this).parents("li").index();
		console.log("orderIndex = " + orderIndex);
		const orderInfo = listInfo.getOrderList(orderIndex);
		const orderNum = orderInfo.orderNum;
		const userId = orderInfo.userId;

		const data = {
			orderNum: orderNum,
			userId: userId
		}

		$.ajax({
			url: "/api/admin/management/orderAccept",
			data: data,
			type: "PATCH"
		})
			.done(function() {
				accept(orderIndex);
				swal("주문접수완료");
			})
			.fail(function(data) {
				errMsg(data);
			})
	})

	$(document).on("click", ".order_cancel", function() {
		const orderIndex = $(this).parents("li").index();
		console.log("orderIndex = " + orderIndex);
		const orderInfo = listInfo.getOrderList(orderIndex);
		const list = listInfo.getNowList();
		const orderNum = orderInfo.orderNum;
		const userId = orderInfo.userId;
		const impUid = orderInfo.impUid;
		const totalPrice = orderInfo.totalPrice;
		const usedPoint = orderInfo.usedPoint;
		const deliveryTip = orderInfo.deliveryTip;

		const data = {
			orderNum: orderNum,
			userId: userId,
			impUid: impUid,
			totalPrice: totalPrice,
			usedPoint: usedPoint,
			deliveryTip: deliveryTip
		}

		$.ajax({
			url: "/api/admin/management/orderCancle",
			type: "PATCH",
			data: data
		})
			.done(function() {
				cancle(orderIndex, list);
				swal("취소완료");
			})
			.fail(function(data) {
				errMsg(data);
			})
	})

	// 배달 완료	
	$(document).on("click", ".complete", function() {
		const orderIndex = $(this).parents("li").index();
		const orderInfo = listInfo.getOrderList(orderIndex);
		const orderNum = orderInfo.orderNum;
		const userId = orderInfo.userId;

		const data = {
			userId: userId,
			orderNum: orderNum
		}

		swal("배달 완료 후 눌러주세요", {
			buttons: ["취소", "완료"],
		})
			.then(function(value) {
				if (!value) {
					return;
				}

				$.ajax({
					url: "/api/admin/management/orderComplete",
					type: "PATCH",
					data: data
				})
					.done(function(result) {
						complete(orderIndex);
					})
					.error(function() {
						swal("에러");
					})
			})
	})

	// 주문 완료 메세지 받기
	const socket = new SockJS('/websocket');
	const stompClient = Stomp.over(socket);
	stompClient.connect({}, function() {

		stompClient.subscribe('/topic/order-complete/' + storeId, function(message) {
			// 화면에 출력중인 view 갯수 
			const list = $(".order_list li").length;
			if (list == listInfo.getWaitcount()) {
				orderList();
			}
		});
	});
})