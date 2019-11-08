
//请求数据 加载商品信息  根据店铺id查询商品信息
$(document).ready(function(){
    var shopId =  $("input[name='shopId']").val();
    if (shopId == "undefined" || shopId == undefined){
        window.location.href = "/home";
    }else{
        //请求用户购物车信息 数据
        $.ajax({
            type: "POST",
            url:"/productInfo/getProductsByShopId",
            data: {
                "shopId":shopId
            },
            dataType:"json",
            success: function (data) {
                var product = data.data;
                if (product == null ||product == "" || product == undefined){
                    $("#my-table").fadeOut("fast");
                    $("#product-search").fadeOut("fast");
                    $(".container").append(
                        '                    <div class="">\n' +
                        '                       <h2 class="title cart-empty">竟然是空的,去<a href="/home">添加商品吧~~</a></h2>\n' +
                        '                    </div>\n'
                    );
                }else{
                    for (var item=0; item<product.length; item++){
                        $("#product-table-body").append(' <tr>\n' +
                            '                            <td>'+product[item].id+'</td>\n' +
                            '                            <td>'+product[item].productName+'</td>\n' +
                            '                            <td><img src="'+product[item].productImg+'" style="width:70px; height: 60px;"></td>\n' +
                            '                            <td>'+product[item].productPrice+'</td>                            ' +
                            '                            <td><img src="'+product[item].qrcode+'" style="width:70px; height: 60px;"></td> ' +
                            '                            <td>'+product[item].productStock+'</td>' +
                            '                            <td style="overflow: hidden;width: 40%;"> '+product[item].description+'</td>\n' +
                            '                            <td>\n' +
                            '                                <a href="/productInfo/updProductLowerShelf?productId='+product[item].id+'">' +
                            '                                   <button   type="button" class="btn btn-danger" >\n' +
                            '                                       <span>删除</span>\n' +
                            '                                   </button>' +
                            '                               </a>\n' +
                            '                               <a href="/shop/update-goods?productId='+product[item].id+'">' +
                            '                                   <button type="button" class="btn btn-default">编辑</button>' +
                            '                               </a>\n'+
                            '                            </td>\n' +
                            '                        </tr>');

                    }


                }

            },
            error : function (data) {
                console.log(data)
            }
        });

    }
});


$("#search-product").click(function () {
    var kw = $("input[name='productName']").val();
    console.log(kw);
    $.ajax({
        type:"GET",
        url:"/productInfo/getProductByName",
        data:{
            "keyWord":kw
        },
        dataType:"json",
        success:function (data) {
            console.log(data);
            var product = data.data;
            //清空表格
            $("#search-table-body").empty();
            $("#empty").remove();
            if (product == null ||product == "" || product == undefined){
                $("#search-table").css('display','none');
                $("#product-search").append(
                    '                    <div id="empty">\n' +
                    '                       <h2 class="title cart-empty">暂无商品信息~~</h2>\n' +
                    '                    </div>\n'
                );
            }else {
                $("#search-table").css('display',"");
                for (var item = 0; item < product.length; item++) {
                    $("#search-table-body").append(' <tr>\n' +
                        '                            <td>' + product[item].id + '</td>\n' +
                        '                            <td>' + product[item].productName + '</td>\n' +
                        '                            <td><img src="' + product[item].productImg + '" style="width:70px; height: 60px;"></td>\n' +
                        '                            <td>' + product[item].productPrice + '</td>                            ' +
                        '                            <td><img src="' + product[item].qrcode + '" style="width:70px; height: 60px;"></td> ' +
                        '                            <td>' + product[item].productStock + '</td>' +
                        '                            <td>' + product[item].description + '</td>\n' +
                        '                            <td>\n' +
                        '                                <a href="/productInfo/updProductLowerShelf?productId=' + product[item].id + '">' +
                        '                                   <button   type="button" class="btn btn-danger" >\n' +
                        '                                       <span>删除</span>\n' +
                        '                                   </button>' +
                        '                               </a>\n' +
                        '                               <a href="/shop/update?productId=' + product[item].id + '">' +
                        '                                   <button type="button" class="btn btn-default">编辑</button>' +
                        '                               </a>\n' +
                        '                            </td>\n' +
                        '                        </tr>');

                }
            }
            //提示信息5s后消失
            setInterval(function () {
                $("#empty").remove();
            }, 6000);
        },
        error:function (data) {
            console.log(data);
        }
    });
});












