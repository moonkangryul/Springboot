$(document).ready(function () {
    const category = $(".category").data("category");
    const address1 = $(".address1").val();

    let sort = "기본순";
    $(".option li[data-sort='기본순']").addClass("active");

    $("li[data-category = '" + category + "'] > span").css("border-bottom", "3px solid #333333");
    $("li[data-category = '" + category + "'] > span").css("color", "#333333");

    let winHeight = 0;
    let docHeight = 0;
    let page = 1;
    let run = false;

    $(window).scroll(function () {
        winHeight = $(window).height();
        docHeight = $(document).height();
        const top = $(window).scrollTop();

        if (docHeight <= winHeight + top + 10) {
            if (run) {
                return;
            }
            
            console.log("페이지 추가");
            console.log("sort= " + sort);
            page++;
            run = true;

            const data = {
                category: category,
                address1: address1,
                sort: sort,
                page: page
            }

            $.ajax({
                url: "/api/store/storeList",
                type: "GET",
                data: data
            })
                .done(function (result) {
                    const storeHtml = storeList(result);
                    $(".store").append(storeHtml);
                    if (storeHtml != "") {
                        run = false;
                    }
                })
                .fail(function (data, textStatus, errorThrown) {
                    swal("다시 시도해주세요");
                })
        }
    })

    // 가게 정렬 
    $(".option li").click(function () {
        sort = $(this).data("sort");
        page = 1;

        $(".option li").removeClass("active");
        $(this).addClass("active");

        const data = {
            category: category,
            address1: address1,
            sort: sort,
            page: page
        }

        $.ajax({
            url: "/api/store/storeList",
            type: "get",
            data: data
        })
            .done(function (result, textStatus, xhr) {
                // 페이지 초기화
                run = false;
                const storeHtml = storeList(result);
                $(".box ul.store").html(storeHtml);
            })
            .fail(function (data, textStatus, errorThrown) {
                swal("다시 시도해주세요");
            })
    });

    function storeList(result) {
        console.log("sort = " + sort);

        let html = "";
        for (var i = 0; i < result.length; i++) {
            const store = result[i];
            console.log(store);
            const id = store.id;
            const storeImg = store.storeImg;
            const storeThumb = store.storeThumb;
            const storeName = store.storeName;
            const deliveryTime = store.deliveryTime;
            const minDelivery = store.minDelivery;
            const deliveryTip = store.deliveryTip;
            const score = store.score.toFixed(1);
            const reviewCount = store.reviewCount;
            const bossCommentCount = store.bossCommentCount;
            const openingTime = store.openingTime;
            const closingTime = store.closingTime;

            let scoreHtml = "";
            for (var j = 0; j < 5; j++) {
                if (Math.round(score) > j) {
                    scoreHtml += "<i class='fas fa-star'></i> ";
                } else {
                    scoreHtml += "<i class='far fa-star'></i> ";
                }
            }
            
            let isOpenHtml = "";
            if (store.isOpen == "false") {
                isOpenHtml = `<div class="is_open">
								<a href="/store/${id}/detail">지금은 준비중입니다</a>
							</div>`;
            }

            html +=
                `<li >
				<div class="img_box">
					<a href="/store/${id}/detail"><img src="${storeImg}" alt="/img/none.gif"></a>
				</div>
				<div class="info_box">
					<h2><a href="/store/${id}/detail">${storeName}</a></h2>
					<a href="/store/${id}/detail">
						<span>
							<span>평점 ${score}</span>
							<span class="score_box">
								${scoreHtml}
							</span>
						</span>
						
						<span>
							<span>리뷰 ${reviewCount}</span>
							<span>사장님 댓글 ${bossCommentCount}</span>
						</span>
						
						<span>
							<span>최소주문금액 ${minDelivery}원</span>
							<span>배달팁 ${deliveryTip}원</span>
						</span>
						<span>배달시간 ${deliveryTime}분</span>
					</a>
				</div>
        	${isOpenHtml}
        </li>`;
        }
        return html;
    }
});

//나중에 페이징 기능 추가
/*
let page = 0;
function storeLoad() {
    let data = {
        category: $("#category").val(),
        address: $("#address").val(),
        page: page
    }

    data2 = JSON.stringify(data);
    console.log(data2);
    
    $.ajax({
        type: "get",
        url: "/api/store",
        data: data,
        contentType: "application/json; charset=utf-8",
    }).done(res => {
        res.forEach((store) => {
            console.log(store);
            let storeItem = getStoreItem(store);
            $(".store").append(storeItem);
        });
    }).fail(error => {
        console.log("오류", error);
    });
}

storeLoad();

function getStoreItem(store) {
    let item = `<%@ include file="/WEB-INF/views/store/store-li.jsp" %>`;
    return item;
}

$(window).scroll(() => {
    let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
    //console.log(checkNum);

    if (checkNum < 10 && checkNum > -10) {
        page++;
        popularLoad();
    }
});
*/