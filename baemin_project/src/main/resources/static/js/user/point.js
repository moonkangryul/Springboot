$(document).ready(function () {
    $(".point_regi button").click(function () {
				swal("업데이트 준비 중 입니다");
        /*$(".point_number_area").fadeToggle(100);*/
    })

    $(".point_number_area input[type=button]").click(function () {
        const giftCardNum = $(".point_number").val().replaceAll(" ", "");

        if (!giftCardNum) {
            return;
        }

        $.ajax({
            url: "/user/pointRegist",
            data: { giftCardNum: giftCardNum },
            type: "POST"
        })
            .done(function (result) {
                const point = result.point;
                swal(point.toLocaleString() + "원이 적립되었습니다");
                const newPoint = Number($("#my_point").data("point") + point);
                $("#my_point").text(newPoint.toLocaleString());
                $("#my_point").data("point", newPoint);
                $(".point_number_area").fadeToggle(100);
                htmlWrite(result);
            })
            .fail(function (result) {
                if (!result.responseJSON) {
                    alert("에러");
                } else {
                    swal(result.responseJSON.errorMsg);
                }
            })
    })

    function htmlWrite(result) {
        const date = new Date();
        const now = date.getFullYear() + "." + (date.getMonth() + 1) + "." + date.getDate();
        const html =
            `<li>
			   <div>
					<div>${result.info}</div>
					<div>${now}</div>
			   </div>
 
			   <div>
				   <div class="plus">${result["point"].toLocaleString()}</div>
			   </div>
		   </li><hr>`;
        $(".point_his").prepend(html);
    }
})