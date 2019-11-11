
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

let orderId;
$(function () {
    let  userIdUrl = getUrlParam("userId");
    let  addressId = getUrlParam("addressId");
    let  orderPrice = getUrlParam("orderPrice");
    let userId =  $("input[name='userId']").val();
    if (userId === undefined || userId === "undefined" || userId === "" || userId == null)
        window.location.href = "/shopping-mall";
    let  productCount = getUrlParam("productCount");
    // 购物车提交 订单
    if (productCount === undefined || productCount === null || productCount === '') {
        // 请求 购物车 订单信息
        $.ajax({
            url: "/pay/settleCart",
            dataType: "json",
            type: "POST",
            async:false,
            data: {
                "userId": userIdUrl,
                "addressId": addressId,
                "orderPrice": orderPrice
            },
            success:function (data) {
                console.log(data)
                orderId = data.data.id;
                $("span[name='orderPrice']").html(data.data.orderPrice+"元");
                $("span[name='orderNum']").html(data.data.orderNum);
            }
        });
    }else {
        let productId = getUrlParam("productId");
        // 直接下单 购买商品
        $.ajax({
            url: "/pay/payOrder",
            dataType: "json",
            type: "GET",
            async:false,
            data: {
                "userId": userIdUrl,
                "productId": productId,
                "productCount": productCount,
                "addressId": addressId
            },
            success:function (data) {
                console.log(data)
                orderId = data.data.id;
                $("span[name='orderPrice']").html(data.data.orderPrice+"元");
                $("span[name='orderNum']").html(data.data.orderNum);
            }
        });
    }


    //请求地址信息
   $.ajax({
       url: "/address/getAddressById",
       dataType: "json",
       type: "GET",
       data: {
           "id": addressId
       },
       success:function (data) {
           console.log(data);
           $("span[name='userName']").text(data.data.userName);
           let phone = data.data.phone;
           $("span[name='phone']").text(phone.substr(0,3)+"****"+phone.substr(7));
           $("span[name='address']").text(data.data.address);
           $("span[name='comment']").text(data.data.comment);

       },
       error:function (data) {

       }
   });
});


//============点击支付订单========
$("#pay-order").click(()=>{
    window.location.href="/aliPay/pay?orderId="+orderId;
});
