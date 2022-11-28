function menuReset() {
    $(".temp_img_box").show();
    $("main").remove();
    $("section").remove();
}

function deleteCartOne(index) {
    $.ajax({
        url: "/cartOne",
        type: "DELETE",
        data: { index: index }
    })
        .done(function (result) {
            priceModify(result);
        })
        .fail(function () {
            alert("에러가 발생했습니다");
        })
}

function deleteCartAll() {
    $.ajax({
        url: "/cartAll",
        type: "DELETE"
    })
        .done(function () {
            menuReset();
        })
        .fail(function () {
            alert("에러가 발생했습니다");
        })
}

function priceModify(cartList) {
    if (!cartList) return;
    const total = cartList.cartTotal;
    const deliveryTip = cartList.deliveryTip;
    $(".order_price").text("주문금액 : " + total.toLocaleString() + "원");
    $(".total").text((total + deliveryTip).toLocaleString() + "원 결제하기");
    $("#total").val(total + deliveryTip);
}

function payment() {
    const data = {
        payMethod: $("input[type='radio']:checked").val(),
        orderNum: createOrderNum(),
        name: $(".order_info li").eq(0).find(".food_name").text(),
        amount: Number($("#total").val()) - Number($(".point_input").val()),
        phone: $("input[name='phone']").val(),
        request: $("textarea[name='request']").val(),
        usedPoint: $("input[name='usedPoint']").val(),
        deliveryAddress1: $("#deliveryAddress1").val(),
        deliveryAddress2: $("#deliveryAddress2").val(),
        deliveryAddress3: $("#deliveryAddress3").val(),
        totalPrice: $("#total").val()
    }

    if (!data.deliveryAddress1 || !data.deliveryAddress2) {
        swal('배달 받으실 주소를 입력해 주세요')
        return;
    }

    if ($(".order_info li").length < 1) {
        return;
    }

    if (!data.phone) {
        swal('전화번호를 입력해주세요');
        return;
    }

    if (data.payMethod == "현장결제") {
        paymentCash(data);
        return;
    }

    paymentCard(data);
}


// 현장에서 결제
function paymentCash(data) {
    $.ajax({
        url: "/api/order/payment-cash",
        method: "POST",
        data: data,
    })
        .done(function () {
            messageSend();
            swal({
                text: "주문이 완료되었습니다",
                closeOnClickOutside: false
            })
                .then(function () {
                    location.replace("/orderList");
                })

        })
        .fail(function () {
            alert("에러");
            location.replace("/");
        })
}

// 계산 완료
function paymentComplete(data) {
    $.ajax({
        url: "/order/payment/complete",
        method: "POST",
        data: data,
    })
        .done(function (result) {
            messageSend();
            swal({
                text: result,
                closeOnClickOutside: false
            })
                .then(function () {
                    location.replace("/orderList");
                })
        })
        .fail(function () {
            alert("에러");
            location.replace("/");
        })
}

// 관리자 페이지로 주문요청 메세지
function messageSend() {
    let socket = new SockJS('/websocket');
    let stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        const message = {
            message: "새 주문이 들어왔습니다"
        }
        stompClient.send("/message/order-complete-message", {}, JSON.stringify(message));
        stompClient.disconnect();
    });
}

if ($("#user_id").val() != "") {
    $(".point_click").click(function () {
        $(".point_input_box").fadeToggle(200);
    });
    
    $(".use_point").click(function () {
        const point = Number($(".point_input").val());
        /*const deliveryTip = Number($("#delivery_tip").val());*/
        const total = Number($("#total").val());

        if (point != 0) {
            $(".total").html("");
            const html = (total - point).toLocaleString() + "원 결제하기";
            $(".total").html(html);
            $(".point_dis").show();
            $(".point_dis").html("포인트 할인 -" + point.toLocaleString() + "원");
        }
    });

    $(".point_input").focusout(function () {
        const total = Number($("#total").val());
        const userPoint = Number($("#point").val());
        const deliveryTip = Number($("#delivery_tip").val());

        if ($(this).val() > userPoint)
            $(this).val(userPoint);
        if ($(this).val() > total - deliveryTip)
            $(this).val(total - deliveryTip);
        if ($(this).val() < 0)
            $(this).val(null);
    });
} else {
    swal("", {
        buttons: ["비회원으로 주문하기", "로그인"],
    })
        .then((value) => {
            if (value == true) {
                location.href = "/auth/signin";
            }
        });
    $(".point_area .point").css("border", "1px solid #ddd");
    $(".point_area .point").css("cursor", "default");
    $(".point_area span").css("color", "#ddd");
    $(".point_area span").css("cursor", "default");
}

if (!$("input[name='phone']").val()) {
    $("input[name='phone']").attr("readonly", false);
}

$(".order_btn").click(function () {
    payment();
})

// 메뉴 1개 삭제
$(".order_info li .delete").click(function () {
    const index = $(this).parents("li").index();
    deleteCartOne(index);

    if ($(".order_info li").length > 1) {
        $(".order_info li").eq(index).remove();
    } else {
        menuReset();
    }
})

//메뉴 전체삭제
$(".order_info .delete_all").click(function () {
    deleteCartAll();
})

$(".amount_box button").click(function () {
    const amount = $(this).siblings(".amount_text");
    const index = $(this).parents("li").index();
    let foodPrice = $(this).parent().siblings(".sum");
    let clickBtn = "";

    console.log(index);
    if ($(this).hasClass("plus")) {
        clickBtn = "plus";
    } else {
        if (amount.val() <= 1) {
            return;
        }
        clickBtn = "minus";
    }
    $.ajax({
        url: "/cartAmount",
        type: "PATCH",
        data: { cartNum: index, clickBtn: clickBtn }
    })
        .done(function (result) {
            const cart = result.cart[index];
            foodPrice.text(cart.totalPrice.toLocaleString() + "원");
            amount.val(cart.amount);
            priceModify(result);
        })
        .fail(function () {
            alert("다시 시도해주세요");
        })
})

const pathArr = location.pathname.split("/");
const storeId = pathArr[pathArr.length - 1];

// 관리자 페이지로 주문요청 메세지
function messageSend() {
    console.log("아이디" + storeId);
    let socket = new SockJS('/websocket');
    let stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        const message = {
            message: "새 주문이 들어왔습니다"
        }
        stompClient.send("/message/order-complete-message/" + storeId, {}, JSON.stringify(message));
        stompClient.disconnect();
    });
}

// 주문번호 만들기
function createOrderNum() {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    let orderNum = year + month + day;
    for (let i = 0; i < 10; i++) {
        orderNum += Math.floor(Math.random() * 8);
    }
    return orderNum;
}

// 카드 결제
function paymentCard(data) {
    // 모바일로 결제시 이동페이지
    const pathName = location.pathname;
    const href = location.href;
    const m_redirect = href.replaceAll(pathName, "");

    /*IMP.init("가맹점 식별코드");*/
    IMP.init("imp32626323");

    IMP.request_pay({ // param
        pg: "html5_inicis",
        pay_method: data.payMethod,
        merchant_uid: data.orderNum,
        name: data.name,
        amount: data.amount,
        buyer_email: "",
        buyer_name: "",
        buyer_tel: data.phone,
        buyer_addr: data.deleveryAddress2 + " " + data.deleveryAddress3,
        buyer_postcode: data.deleveryAddress1,
        m_redirect_url: m_redirect,
    },
        function (rsp) { // callback
            if (rsp.success) {
                // 결제 성공 시 로직,
                data.impUid = rsp.imp_uid;
                data.merchant_uid = rsp.merchant_uid;
                paymentComplete(data);
            } else {
                // 결제 실패 시 로직
            }
        });
}

// 계산 완료
function paymentComplete(data) {
    $.ajax({
        url: "/api/order/payment/complete",
        method: "POST",
        data: data,
    })
        .done(function (result) {
            messageSend();
            swal({
                text: result,
                closeOnClickOutside: false
            })
                .then(function () {
                    location.replace("/orderList");
                })
        })
        .fail(function () {
            alert("에러");
            location.replace("/");
        })
}