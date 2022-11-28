function upload(event) {

    event.preventDefault();
    let formData = new FormData();
    formData.append("file", $('#file')[0].files[0]);

    /*private String orderNum; 
    private long storeId;
    private String storeName;
    private String reviewContent;
    private String bossComment;
    private Date regiDate;
    private float score;
    private String reviewImg;
    private long userId;
    private String username;
    private String nickname;

    $.ajax({
        type: "post",
        url: `/api/image`,
        data: formData,
        contentType: false,
        processData: false,
        enctype: "multipart/form-data",
        dataType: "text"
    }).done(res => {
        alert(res);
        location.href = `/user/profile`
    }).fail(error => {
        console.log(error);
        alert("게시글 등록에 실패하였습니다");
    });
    */
}