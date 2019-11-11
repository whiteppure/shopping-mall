//jQuery 正则 获取url路径上的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}



//购买商品  支付订单
$("#pay-order").click(function () {
    let _this = $(this);

    var userId = GetQueryString("userId");
    var productId = GetQueryString("productId");
    var productCount = GetQueryString("productCount");

    //判断是否选择收货地址信息
    if ($(".item").hasClass("border-1-black")) {//选中收货地址
        //判断选中class数量
        if ($(".border-1-black").length > 1 && $(".address-id-flag").length > 1) {
            $(".msgFiled").fadeIn("slow");
            $("span[name='msgFiled']").text("请选中一个收货地址!");
            setTimeout(function () {
                $(".msgFiled").fadeOut();
            }, 1200);
        } else {
            //获取地址id
            var addressId = $(".address-id-flag").val();
            //根据收货地址id查询收货地址   判断收货地址信息是否完整
            $.ajax({
                url: "/address/getAddressById",
                dataType: "json",
                type: "GET",
                data: {"id": addressId},
                success: function (data) {
                    var addressInfo = data.data;
                    if (addressInfo.address == "" || addressInfo.phone == "" || addressInfo.userName == "") {
                        $(".msgFiled").fadeIn("slow");
                        $("span[name='msgFiled']").text("请填写完整的收货地址信息!");
                        setTimeout(function () {
                            $(".msgFiled").fadeOut();
                        }, 1200);
                    } else {
                        if (data.status === "200"){
                            _this.attr('disabled','disabled');
                            _this.text("提交订单中...");
                            //提交订单信息 ----> 写入数据库 后台 选择付款方式
                            let url = "/shopping-mall/pay-way?"+"data=123456789"+"&pId=GUIV1234567"+"&cId=DFGHJKfghhujklrtyuixcvbnertyuixcvbn" +
                                "&userId="+userId+"&productId="+productId+"&productCount="+productCount+"&addressId="+addressId;
                            window.location.href=url;
                        }

                    }

                }, error: function (data) {
                    console.log(data)
                }
            });
        }

    } else {
        //未选中收货地址
        $(".msgFiled").fadeIn("slow");
        $("span[name='msgFiled']").text("收货地址未选择!");
        setTimeout(function () {
            $(".msgFiled").fadeOut();
        }, 1200);

    }

});