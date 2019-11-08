//jQuery 正则 获取url路径上的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}


//请求订单数据
$(function () {
    //让页面只刷新一次
    $(document).ready(function () {
        if(location.href.indexOf("#reloaded")==-1){
            location.href=location.href+"#reloaded";
            location.reload();
        }
    });
    //添加判断 防止 userId 失效
    var userId =  $("input[name='userId']").val();
    if (userId == undefined || userId == "undefined" || userId == "" || userId == null){
        window.location.href = "/shopping-mall";
    }

    var productId = GetQueryString("productId");
    var productCount = GetQueryString("productCount");

    $.ajax({
        type: "POST",
        url: "/productInfo/buyProduct",
        dataType: "json",
        data: {
            "userId": userId,
            "productId": productId,
            "productCount": productCount

        },
        success: function (data) {
            console.log(data);
            $(".spinner").fadeOut("slow");
            $(".checkout-count").fadeIn("slow");
            $(".checkout-confirm").fadeIn("slow");
            $(".table").fadeIn("slow");

            var product = data.data.userProduct;
            var userAddress = data.data.userAddress;
            $("#product-table-body").append(' <tr>\n' +
                '                            <td>' + product.id + '</td>\n' +
                '                            <td>' + product.productName + '</td>\n' +
                '                            <td>' + product.productPrice + '</td>\n' +
                '                            <td><img src="' + product.productImg + '" style="width:70px; height: 40px;"></td>\n' +
                '                            <td>' + productCount + '</td>\n' +
                '                        </tr>');
            //商品价格
            $("#price").text(productCount * product.productPrice);
            $("#totalPrice").text(productCount * product.productPrice);

            $("#address").append(' <div class="box-hd ">\n' +
                '                        <h2 class=" text-left title">收货地址</h2>\n' +
                '                    </div>');
            for (var i = 0; i < userAddress.length; i++) {
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


            //选中收货地址
            for (var i = 0; i < userAddress.length; i++) {
                //默认选中第一个收货地址
                document.getElementsByName('item')[0].classList.add("border-1-black");
                var addressId = $("input[name='addressId']")[0].value;
                var addressFlag =  document.getElementsByName("addressId");
                console.log(addressId)
                addressFlag[0].classList.add("address-id-flag");
                $("div[name='address']")[i].i = i;
                $("div[name='address']")[i].onclick = function () {
                    var index = this.i;
                    var item = document.getElementsByName('item')[index];
                    //判断是否选中
                    if (item.className.indexOf("border-1-black") > -1){
                        //选中
                        item.classList.remove("border-1-black");
                        addressFlag[index].classList.remove("address-id-flag");
                        addressId = 0;
                    }
                    else{
                        //未选中
                        item.classList.add("border-1-black");
                        addressFlag[index].classList.add("address-id-flag");
                        addressId = $("input[name='addressId']")[index].value;
                    }
                };

            }


            //编辑收货地址
            for (var i = 0; i < userAddress.length; i++) {
                $('button[name="edit-btn"]')[i].i = i;
                $('button[name="edit-btn"]')[i].onclick = function () {
                    var index = this.i;
                    var id = userAddress[index].id;
                    $("#address-tag").val(userAddress[index].addressTag);
                $("#address-user-name").val(userAddress[index].userName);
                $("#address-user-phone").val(userAddress[index].phone);
                $("#address-info").val(userAddress[index].address);
                $("#address-comment").val(userAddress[index].comment);
                $("#address-save").click(function () {
                    console.log(id);
                    //修改收货地址信息

                    var userAddress = {
                        "id": id,
                        "userName": $("#address-user-name").val(),
                        "phone": $("#address-user-phone").val(),
                        "address": $("#address-info").val(),
                        "comment": $("#address-comment").val(),
                        "addressTag": $("#address-tag").val()
                    };
                    $.ajax({
                        cache:"false",
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

        },
        error: function (data) {
            console.log(data)
        }
    });

});

