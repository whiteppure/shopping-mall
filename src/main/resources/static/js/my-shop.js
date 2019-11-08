$(function () {
    $.ajax({
        url:"/shop/getShopInfoByUserId",
        data:{
            "userId":$("input[name='userId']").val()
        },
        type:"GET",
        dataType:"json",
        success:function (data) {
            console.log(data.data);
            //给input 赋值
            $("input[name='shopNum']").val(data.data.shopNum);
            $("input[name='shopName']").val(data.data.shopName);
            $("input[name='shopLevel']").val(data.data.shopLevel);
            $("textarea[name='shopDesc']").text(data.data.shopDesc);

        },error:function () {

        }
    });
});