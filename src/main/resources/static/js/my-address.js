//根据用户id查询收货地址信息
($(function () {
    var userId = $("input[name='userId']").val();
    if (userId == undefined || userId == "undefined") {
        window.location.href = "/shopping-mall/login";
    }
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
                '                        <h2 class=" text-left title">我的收货地址</h2>\n' +
                '                    </div>');
            var userAddress = data.data;
            for (var i = 0; i < userAddress.length; i++) {
                $("#address").append('<div class="box-bd text-left">\n' +
                    '                        <div class="xm-address-list">\n' +
                    '                            <dl class="item">\n' +
                    '                                <dt>\n' +
                    '                                    <strong class="itemConsignee">' + userAddress[i].userName + '</strong>\n' +
                    '                                    <span class="itemTag tag">' + userAddress[i].addressTag + '</span>\n' +
                    '                                </dt>\n' +
                    '                                <dd>\n' +
                    '                                    <p class="tel itemTel">' + userAddress[i].phone + '</p>\n' +
                    '                                    <p class="itemRegion">' + userAddress[i].address + '</p>\n' +
                    '                                    <p class="itemStreet">' + userAddress[i].comment + '</p>\n' +
                    '                                    <button name="edit-btn"  type="button" class="edit-btn J_editAddr" style="display: block" data-toggle="modal" data-target="#exampleModal">编辑</button>\n' +
                    '                                </dd>\n' +
                    '                            </dl>\n' +
                    '                        </div>\n' +
                    '                    </div>' +
                    '');

            }



            //编辑收货地址
            for (var i = 0; i < userAddress.length; i++) {
                $('button[name="edit-btn"]')[i].i = i;
                $('button[name="edit-btn"]')[i].onclick = function () {
                    var index = this.i;
                    console.log(index);
                    var id = userAddress[index].id;
                    $("#address-tag").val(userAddress[index].addressTag);
                    $("#address-user-name").val(userAddress[index].userName);
                    $("#address-user-phone").val(userAddress[index].phone);
                    $("#address-info").val(userAddress[index].address);
                    $("#address-comment").val(userAddress[index].comment);
                    $("#address-save").click(function () {
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

}));

setTimeout(function () {
    $(".message").fadeOut("slow");
    $(".msgFiled").fadeOut("slow");
}, 1200);
