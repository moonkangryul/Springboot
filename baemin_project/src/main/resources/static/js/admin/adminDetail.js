$(document).ready(function() {
	const pathArr = location.pathname.split("/");
	const storeId = pathArr[pathArr.length - 2];

	let addOptionNum = 1;

	function optionHtml() {
		let html =
			`<div class="option">
				<div>
					<div class="option_num">옵션 ${addOptionNum} </div> 
					<div>가격</div> 
				</div>
				
				<div>
					<input type="hidden" name="optionId" >
					<input type="text" maxlength="30" name="foodOption" required >
					<input type="number" onkeypress="return lengthCheck(this,8);" pattern="\\d*" name="foodOptionPrice" required >
				</div>
				
				<div>
					<button type="button" class="add_option_cancle" ><i class="fas fa-times"></i></button>
				</div>
			</div> `;

		addOptionNum++;
		return html;
	}

	function foodHtml(food) {
		let html =
			`<li>
        	<label class="menu_delete_label">
            	<i class="fas fa-check-square" ></i>
            	<input type="checkbox" class="menu_delete_checkbox" name="deleteNumber" value="${food.id}">
        	</label>
            
            <div class="menu_box">
                <div>
					<h2 class="store_food_name">${food.foodName} </h2>
                    <span class="store_food_price">${Number(food.foodPrice).toLocaleString()}</span><span>원</span>
		            <input type="hidden" value="${food.id}" name="foodId" class="food_id"   >
		            <input type="hidden" value="${food.foodName}" name="foodName" class="food_name" >
		            <input type="hidden" value="${food.foodPrice}" name="foodPrice" class="food_price"   >
		            <input type="hidden" value="${food.foodDec}" name="foodDec" class="food_dec"   >
		            <input type="hidden" value="${food.foodImg}" name="foodImg" class="food_img"   >
		            <input type="hidden" value="${food.foodThumb}" name="foodThumb" class="food_thumb"   >
                </div>
                
            	<div><img src="/${food.foodImg}" alt="/img/none.gif"></div>
            </div>
         </li>`
		return html;
	}

	function resetOptionBox() {
		addOptionNum = 1;
		$(".option_box_div").find(".option").remove();
	}

	function resetModal(modal) {
		resetOptionBox();
		modal.find("input[type='text']").val("");
		modal.find("input[type='number']").val("");
		modal.find("textarea").val("");
		imgClose();
	}

	// 모달 빈값 체크
	function isSubmit(form) {
		const ipt = "input[type='text']";
		const ipn = "input[type='number']";

		for (i = 0; i < form.find(ipt).length; i++) {
			const target = form.find(ipt).eq(i);
			if (target.attr('required') == 'required') {
				const value = target.val().replace(" ", "");
				if (!value) {
					return false;
				}
			}
		}

		for (i = 0; i < form.find(ipn).length; i++) {
			const target = form.find(ipn).eq(i);
			if (target.attr('required') == 'required') {
				const value = target.val();
				if (!value) {
					return false;
				}
			}
		}

		return true;
	}

	function errMsg(status) {
		if (status.status == 401) {
			alert("권한이 없습니다");
		} else {
			alert("에러");
		}
	}

	// 답장하기 버튼
	$(document).on("click", ".review_btn", function() {
		console.log($(this));
		$(this).parents(".boss_comment_box").css("display", "none");
		console.log($(this).parents().siblings(".boss.input"));
		$(this).parents().siblings(".boss.input").stop().fadeToggle(0, function() {
			const top = $(this).offset().top;
			if ($(this).css("display") == 'block') {
				$("html").animate({ scrollTop: top - 100 }, 200);
			}
		});
	})

	// 답장 등록하기
	$(".boss_comment_btn").off().click(function() {
		const bossComment = $(this).parent().siblings().find(".comment_area").val();
		const orderNum = $(this).siblings(".order_num").val();
		const inputTarget = $(this).parents().siblings(".boss");
		const closeTarget = $(this).parents(".boss.input");
		console.log(orderNum + "			" + bossComment + "				" + storeId);
		const data = {
			bossComment: bossComment,
			orderNum: orderNum,
			storeId: storeId
		}

		$.ajax({
			url: "/api/admin/management/bossComment",
			type: "PATCH",
			data: data
		})
			.done(function(result) {
				let html = `<div class="boss_comment_box">
							<div class="nickname">사장님</div>
	                		<div class="boss_comment">${result}</div>
	                		<button class="review_btn">답글수정</button>
	                		</div>`;
				closeTarget.fadeToggle(0);
				inputTarget.html(html);
			})
			.fail(function(data) {
				errMsg(data);
			})
	})

	// 메뉴 추가 모달
	$(".add_menu").click(function() {
		const modal = $(".add_menu_modal.menu_add");
		resetModal(modal);
		openModal(modal);

		// 추가 버튼 클릭
		$(".add_btn").click(function() {
			if (!isSubmit(modal)) {
				swal("빈칸을 채워주세요");
				return;
			}
			swal({
				text: "메뉴를 추가합니다",
				buttons: ['취소', '확인'],
			})
				.then(function(value) {
					if (!value) {
						return;
					}
					const form = $("form")[1];
					const formdata = new FormData(form);
					formdata.append("storeId", storeId);
					$.ajax({
						url: "/api/admin/management/menu",
						type: "POST",
						data: formdata,
						processData: false,
						contentType: false,
					})
						.done(function(result) {
							$(".menu").append(foodHtml(result));
							closeModal();
						})
						.fail(function(data) {
							errMsg(data);
						})
				})
		})
	})

	// 메뉴 추가, 수정 모달 옵션 추가하기
	$(".add_option").click(function() {
		const html = optionHtml();
		$(this).parents(".modal").find(".option_box_div").append(html);
	})

	// 메뉴 추가, 수정 모달 옵션 삭제하기
	$(document).on("click", ".add_option_cancle", function() {
		const target = $(this).parents(".option_box_div");
		$(this).parents(".option").remove();
		const option = target.find(".option");
		for (i = 0; i < option.length; i++) {
			option.eq(i).find(".option_num").text("옵션 " + (i + 1));
		}
		addOptionNum--;
	})

	// 매장 정보 수정
	$(".inf_modify").click(function() {
		const modal = $(".store_reg_modal");
		openModal(modal);

		const category = $("#store_category").val();
		const openingTime = $("#store_opening_time").data("opening_time");
		const closingTime = $("#store_closing_time").data("closing_time");

		$("#category").val(category).prop("selected", true);
		$("#opening_time").val(openingTime).prop("selected", true);
		$("#closing_time").val(closingTime).prop("selected", true);

		// 수정하기 버튼 클릭
		$(".store_update_btn").click(function() {
			if (!isSubmit(modal)) {
				swal("빈칸을 채워주세요");
				return;
			}

			swal({
				text: "가게정보를 수정할까요?",
				buttons: ['취소', '확인'],
			})
				.then(function(value) {
					if (!value) {
						return;
					}
					const form = $("form")[0];
					const formdata = new FormData(form);
					formdata.append("id", storeId);
					console.log(formdata);
					$.ajax({
						url: "/api/admin/management/storeInfo",
						type: "PATCH",
						data: formdata,
						processData: false,
						contentType: false,
					})
						.done(function(result) {
							$("#store_name").text(result.storeName);
							$("#min_delivery").text("최소주문금액 " + result.mindelivery.toLocaleString());
							$("#delivery_tip").text("배달팁 " + result.deliveryTip.toLocaleString());
							$("#store_address").text(result.storeAddress2 + " " + result.storeAddress3);
							$("#store_des").text(result.storeDes);
							$("#store_opening_time").text(String(result.openingTime).padStart(2, 0) + "시");
							$("#store_opening_time").data("opening_time", result.openingTime);
							$("#store_closing_time").text(String(result.closingTime).padStart(2, 0) + "시");
							$("#store_closing_time").data("closing_time", result.closingTime);
							$("#store_phone").text(result.storePhone);
							$("#store_category").val(result.category);
							closeModal();
						})
						.fail(function(data) {
							errMsg(data);
						})
				})
		})
	})

	// 메뉴 삭제 체크박스 
	$(document).on("change", ".menu_delete_checkbox", function() {
		if ($(this).is(":checked")) {
			$(this).siblings("i").css("color", "#2AC1BC");
		} else {
			$(this).siblings("i").css("color", "unset");
		}
	})

	// 메뉴 삭제
	$(document).on("click", ".delete_menu", function() {
		const deleteNumber = []; // 삭제할 메뉴 번호
		const deleteIndex = []; // 삭제후 remove()할 인덱스

		$("input[name='deleteNumber']:checked").each(function() {
			deleteNumber.push($(this).val());
			deleteIndex.push($(this).parents("li").index());
		})

		console.log("삭제 메뉴 = " + deleteNumber);
		console.log("삭제 인덱스 = " + deleteIndex);

		if (deleteNumber == "") {
			swal("삭제 할 메뉴를 선택해주세요");
		} else {
			swal("삭제 할까요?", {
				buttons: ["취소", "삭제"],
			})
				.then(function(value) {
					if (value) {
						$.ajax({
							url: "/api/admin/management/menu",
							type: "DELETE",
							traditional: true,
							data: { deleteNumber: deleteNumber, storeId: storeId }
						})
							.done(function() {
								for (var i = deleteIndex.length - 1; i >= 0; i--) {
									$(".menu li").eq(deleteIndex[i]).remove();
								}
							})
							.fail(function(data) {
								errMsg(data);
							})
					}
				});
		}
	})

	function getOption(foodId, modal) {
		$.ajax({
			url: `/api/food/${foodId}/option`,
			type: "get"
		})
			.done(function(result) {
				addOptionNum = 1;
				let html = "";
				for (var i = 0; i < result.length; i++) {
					html += optionHtml();
				}
				modal.find(".option_box_div").html(html);
				for (var i = 0; i < result.length; i++) {
					modal.find(".option").eq(i).find("input[name=foodOption]").val(result[i].optionName);
					modal.find(".option").eq(i).find("input[name=foodOptionPrice]").val(result[i].optionPrice);
					modal.find(".option").eq(i).find("input[name=optionId]").val(result[i].id);
				}
			})
			.fail(function(data) {
				errMsg(data);
			})
	}

	// 메뉴 수정하기
	$(document).on("click", ".menu > li .menu_box", function() {
		imgClose();
		resetOptionBox();
		const modal = $(".add_menu_modal.menu_modify");
		const target = $(this).parents("li");
		const foodId = $(this).find(".food_id").val();
		const data = { foodId: foodId };
		getOption(foodId, modal);

		const foodName = $(this).find(".food_name").val();
		let foodPrice = Number($(this).find(".food_price").val());
		const foodDec = $(this).find(".food_dec").val();
		const foodImg = $(this).find(".food_img").val();
		const foodThumb = $(this).find(".food_thumb").val();

		modal.find("input[name=id]").val(foodId);
		modal.find("input[name=foodName]").val(foodName);
		modal.find("input[name=foodPrice]").val(foodPrice);
		modal.find("input[name=foodDec]").val(foodDec);
		modal.find("input[name=foodImg]").val(foodImg);
		modal.find("input[name=foodThumb]").val(foodThumb);

		openModal(modal);

		// 수정하기 버튼 클릭
		$(".menu_update_btn").click(function() {
			if (!isSubmit(modal)) {
				swal("빈칸을 채워주세요");
				return;
			}

			swal({
				text: "메뉴정보를 수정할까요?",
				buttons: ['취소', '확인'],
			})
				.then(function(value) {
					if (!value) {
						return;
					}
					const form = $("form")[2];
					const formdata = new FormData(form);
					formdata.append("storeId", storeId);

					$.ajax({
						url: "/api/admin/management/menu",
						type: "PATCH",
						data: formdata,
						processData: false,
						contentType: false,
					})
						.done(function(result) {
							getOption(foodId, modal);
							closeModal();
							location.reload();
							/*  target.find("[name=foodName]").val(result.foodName);
	target.find(".store_food_price").text(Number(result.foodPrice).toLocaleString());
	target.find(".food_name").val(result.foodName);
	target.find(".food_price").val(result.foodPrice);
	target.find(".food_dec").val(result.foodDec); */
						})
						.fail(function(data) {
							errMsg(data);
						})
				})
		})
	})

	$("#img").change(function(e) {
		imgPreview(e, $(this));
	})

	$("#img2").change(function(e) {
		imgPreview(e, $(this));
	})

	$("#img3").change(function(e) {
		imgPreview(e, $(this));
	})

	$(".img_close").click(function() {
		imgClose();
	})
})

/* 다음 주소 연동 */
function execution_daum_address() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일 때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', '
						+ data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 추가해야할 코드
				// 주소변수 문자열과 참고항목 문자열 합치기
				addr += extraAddr;
			} else {
				addr += ' ';
			}

			$("#address1").val(data.zonecode);
			$("#address2").val(addr);
			$("#address3").val("");
		}
	}).open();
}