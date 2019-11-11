//======================页面加载执行事件 请求数据======================
$(document).ready(function () {
    let userId = $("input[name='userId']").val();
    if (userId == "undefined" || userId == undefined) {
        window.location.href = "/shopping-mall/login";
    } else {
        //请求用户购物车信息 数据
        $.ajax({
            type: "POST",
            url: "/cart/getCartByCartId",
            data: {
                "userId": userId
            },
            dataType: "json",
            success: function (data) {
                let product = data.data;
                if (product == null || product == "" || product == undefined) {
                    $(".spinner").fadeOut("fast");
                    $("#order").fadeOut("slow");
                    $(".table").fadeOut("slow");
                    $("#cart-empty").append(
                        '                    <div class="font-20px">\n' +
                        '                       <div class="title cart-empty">购物车竟然是空的,去<a href="/shopping-mall">首页</a>看看~~</div>\n' +
                        '                    </div>\n'
                    );
                } else {
                    for (let item = 0; item < product.length; item++) {
                        $("#product-table-body").append(' <tr>\n' +
                            '                            <td class="customer-table-td">' + product[item].id + '</td>\n' +
                            '                            <td class="customer-table-td">' + product[item].productName + '</td>\n' +
                            '                            <td class="customer-table-td">' + product[item].productPrice + '元</td>\n' +
                            '                            <td class="customer-table-td"><img src="' + product[item].productImg + '" style="width:70px; height: 40px;"></td>\n' +
                            '                        <td class="customer-table-td">暂无描述~~</td>\n' +
                            '                            <td class="customer-table-td">' + product[item].productCount + '</td>\n' +
                            '                            <td class="customer-table-td">\n' +
                            '                                <a href="/cartProduct/deleteCartProductById?productId=' + product[item].productId + '&userId=' + userId + '">' +
                            '                              <button   type="button" class="btn" >\n' +
                            '                                    <span>删除</span>\n' +
                            '                                  </button>' +
                            '                           </a>\n' +
                            '                            </td>\n' +
                            '                        </tr>')

                    }
                    //获取购物车总价
                    $.ajax({
                        type: "POST",
                        url: "/cartProduct/getPriceInCart",
                        data: {
                            "userId": userId
                        },
                        dataType: "json",
                        success: function (data) {
                            $("#price").text(data.data);
                            $("#totalPrice").text(data.data);
                        },
                        error: function (data) {
                            console.log(data)
                        }
                    });
                    $(".spinner").fadeOut("fast");
                    $("#cart").fadeIn("slow");
                    $(".checkout-count").fadeIn("slow");
                    $(".checkout-confirm").fadeIn("slow");
                }

            },
            error: function (data) {
                console.log(data);
                window.location.href = "/error/error";
            },
        });

    }
});

//==========================点击结算购物车========================
$("#settleShoppingCart").click(function () {
    let userId = $("input[name='userId']").val();
    $.ajax({
        url: "/address/getAllAddressByUserId",
        type: "GET",
        dataType: "json",
        data: {
            "userId": userId
        },
        success: function (data) {
            console.log(data);
            $("#address").append(' <div class="box-hd ">\n' +
                '                        <h2 class=" text-left title">收货地址</h2>\n' +
                '                    </div>');
            let userAddress = data.data;
            for (let i = 0; i < userAddress.length; i++) {
                $("#address").append('<div class="box-bd text-left"  name="address">\n' +
                    '                        <input value="' + userAddress[i].id + '" type="hidden" name="addressId">' +
                    '                        <div class="xm-address-list">\n' +
                    '                            <dl class="item" name="item">\n' +
                    '                                <dt>\n' +
                    '                                    <strong class="itemConsignee">' + userAddress[i].userName + '</strong>\n' +
                    '                                    <span class="itemTag tag">' + userAddress[i].addressTag + '</span>\n' +
                    '                                </dt>\n' +
                    '                                <dd>\n' +
                    '                                    <p class="tel itemTel">' + userAddress[i].phone + '</p>\n' +
                    '                                    <p class="itemRegion">' + userAddress[i].address + '</p>\n' +
                    '                                    <p class="itemStreet">' + userAddress[i].comment + '</p>\n' +
                    '                                     <button name="edit-btn" type="button" class="edit-btn J_editAddr" data-toggle="modal" data-target="#exampleModal">编辑</button>\n' +
                    '                                </dd>\n' +
                    '                            </dl>\n' +
                    '                        </div>\n' +
                    '                    </div>');
            }

            $("#settleShoppingCart").remove();
            $("#delete-cart").remove();
            $("#pay-order").fadeIn("slow");
            $("#user-cart").text("订单列表");


            //选中收货地址
            for (let i = 0; i < userAddress.length; i++) {
                //默认选中第一个收货地址
                document.getElementsByName('item')[0].classList.add("border-1-black");
                let addressId = $("input[name='addressId']")[0].value;
                let addressFlag = document.getElementsByName("addressId");
                addressFlag[0].classList.add("address-id-flag");
                $("div[name='address']")[i].i = i;
                $("div[name='address']")[i].onclick = function () {
                    let index = this.i;
                    let item = document.getElementsByName('item')[index];
                    //判断是否选中
                    if (item.className.indexOf("border-1-black") > -1) {
                        //选中
                        item.classList.remove("border-1-black");
                        addressFlag[index].classList.remove("address-id-flag");
                        addressId = 0;
                    } else {
                        //未选中
                        item.classList.add("border-1-black");
                        addressFlag[index].classList.add("address-id-flag");
                        addressId = $("input[name='addressId']")[index].value;
                    }
                };

            }

            //编辑收货地址
            for (let i = 0; i < userAddress.length; i++) {
                $('button[name="edit-btn"]')[i].i = i;
                $('button[name="edit-btn"]')[i].onclick = function () {
                    let index = this.i;
                    console.log(index);
                    let id = userAddress[index].id;
                    $("#address-tag").val(userAddress[index].addressTag);
                    $("#address-user-name").val(userAddress[index].userName);
                    $("#address-user-phone").val(userAddress[index].phone);
                    $("#address-info").val(userAddress[index].address);
                    $("#address-comment").val(userAddress[index].comment);
                    $("#address-save").click(function () {
                        //修改收货地址信息

                        let userAddress = {
                            "id": id,
                            "userName": $("#address-user-name").val(),
                            "phone": $("#address-user-phone").val(),
                            "address": $("#address-info").val(),
                            "comment": $("#address-comment").val(),
                            "addressTag": $("#address-tag").val()
                        };
                        $.ajax({
                            type: "POST",
                            url: "/address/updateAddressById",
                            dataType: "json",
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify(userAddress),
                            success: function (data) {
                                if (data.status == "200"){
                                    $(".message").fadeIn("slow");
                                    $("span[name='message']").text("修改收货地址成功!");
                                    setTimeout(function () {
                                        $(".message").fadeOut();
                                        window.location.reload();
                                    }, 1200);
                                }else{
                                    $(".msgFiled").fadeIn("slow");
                                    $("span[name='msgFiled']").text("修改收货地址失败!");
                                    setTimeout(function () {
                                        window.location.reload();
                                    }, 1200);
                                }
                            },
                            error: function (data) {
                                console.log(data)
                            }
                        });

                    });
                };


            }


        }, error: function (data) {
            console.log(data);
        }
    });
});



//=======================点击提交订单====================
$("#pay-order").click(function () {


    let userId = $("input[name='userId']").val();
    let orderPrice = $("#totalPrice").text();


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
            let addressId = $(".address-id-flag").val();
            //根据收货地址id查询收货地址   判断收货地址信息是否完整
            $.ajax({
                url: "/address/getAddressById",
                dataType: "json",
                type: "GET",
                data: {"id": addressId},
                success: function (data) {
                    let addressInfo = data.data;
                    if (addressInfo.address == "" || addressInfo.phone == "" || addressInfo.userName == "") {
                        $(".msgFiled").fadeIn("slow");
                        $("span[name='msgFiled']").text("请填写完整的收货地址信息!");
                        setTimeout(function () {
                            $(".msgFiled").fadeOut();
                        }, 1200);
                    } else {

                        if (data.status === "200") {
                            $("#pay-order").attr("disabled","disabled");
                            $("#pay-order").text("生成订单中...");
                            //提交订单信息 ----> 写入数据库 后台 选择付款方式 可改用post传数据
                            let url = "/shopping-mall/pay-way?" +"data=123456789"+"&pId=GUIV12HJKGHJK34567"+"&cId=DFGHJKfghhujklrtyuixcvbnertyuixcvbn"
                                +"&userId=" + userId + "&orderPrice=" + orderPrice + "&addressId=" + addressId;
                            window.location.href = url;
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


//==============清空用户购物车里的商品==================
$("#delete-cart").click(function () {
    $("#settleShoppingCart").attr("disabled","disabled");
    $(this).attr("disabled","disabled");
    $(this).text("正在清空...");
    $.ajax({
        type: "POST",
        url: "/cartProduct/deleteCartProductByUserId",
        data: {"userId":$("input[name='userId']").val()},
        dataType: "json",
        cache: "false",
        success: function (data) {
            if (data.status == "200"){
                $(".message").fadeIn("slow");
                $("span[name='message']").text("清空购物车成功!");
                setTimeout(function () {
                    $(".message").fadeOut();
                    window.location.reload();
                }, 1200);
            }else{
                $(".msgFiled").fadeIn("slow");
                $("span[name='msgFiled']").text("清空购物车失败!");
                setTimeout(function () {
                    $(".msgFiled").fadeOut();
                }, 1200);
            }
        }, error: function () {

        }
    });

});



