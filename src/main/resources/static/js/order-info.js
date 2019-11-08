
//请求数据 加载商品信息  根据店铺id查询商品信息
$(document).ready(function(){
    var userId =  $("input[name='userId']").val();
    if (userId == "undefined" || userId == undefined){
        window.location.href = "/home";
    }else{
        //请求用户购物车信息 数据
        $.ajax({
            type: "GET",
            url:"/orderinfo/getAllOrderByUserId",
            data: {
                "userId":userId
            },
            dataType:"json",
            success: function (data) {
                var order = data.data;
                if (order == null ||order == "" || order == undefined){
                    $("#my-table").fadeOut("fast");
                    $("#order-search").fadeOut("fast");
                    $(".container").append(
                        '                    <div class="">\n' +
                        '                       <h2 class="title cart-empty">暂无订单信息!</h2>\n' +
                        '                    </div>\n'
                    );
                }else{
                    for (var item=0; item<order.length; item++){
                        var orderStatus;
                        if (order[item].orderInfoStatus == "2"){
                            orderStatus = "已完成";
                        }
                        if (order[item].orderInfoStatus == "1"){
                            orderStatus = "待发货";
                        }
                        if (order[item].orderInfoStatus == "0"){
                            orderStatus = "已发货";
                        }
                        $("#order-table-body").append(' <tr>\n' +
                            '                            <td>'+order[item].id+'</td>\n' +
                            '                            <td>'+order[item].orderNum+'</td>\n' +
                            '                            <td>'+order[item].orderPrice+'元</td>' +
                            '                            <td name="orderStatus">'+orderStatus+'</td>\n' +
                            '                            <td>\n' +
                            '                                <a href="/orderinfo/getOrderByOrderId?orderId='+order[item].id+'">' +
                            '                                   <button   type="button" class="btn btn-danger" >\n' +
                            '                                       <span>查看详情</span>\n' +
                            '                                   </button>' +
                            '                               </a>\n' +
                            '                            </td>\n' +
                            '                        </tr>');

                    }


                }

            },
            error : function (data) {
                console.log(data)
            },
        });

    }
});


$("#search-product").click(function () {
    var orderNum = $("input[name='orderNum']").val();
    $.ajax({
        type:"GET",
        url:"/orderinfo/getOrderInfoByNum",
        data:{
            "orderNum":orderNum
        },
        dataType:"json",
        success:function (data) {
            var order = data.data;
            console.log("data.data="+typeof(order));
            //清空表格
            $("#search-table-body").empty();
            $("#empty").remove();
            if (order == null ||order == "" || order == undefined){
                console.log("order为null")
                $("#search-table").css('display','none');
                $("#order-search").append(
                    '                    <div id="empty">\n' +
                    '                       <h2 class="title cart-empty">暂无订单信息~~</h2>\n' +
                    '                    </div>\n'
                );
            }else {
                    $("#search-table").css('display',"");
                    var orderStatus;
                    if (order.orderInfoStatus == "2"){
                        orderStatus = "已完成";
                    }
                    if (order.orderInfoStatus == "1"){
                        orderStatus = "待发货";
                    }
                    if (order.orderInfoStatus == "0"){
                        orderStatus = "已发货";
                    }
                    $("#search-table-body").append(' <tr>\n' +
                        '                            <td>'+order.id+'</td>\n' +
                        '                            <td>'+order.orderNum+'</td>\n' +
                        '                            <td>'+order.orderPrice+'元</td>' +
                        '                            <td name="orderStatus">'+orderStatus+'</td>\n' +
                        '                            <td>\n' +
                        '                                <a href="/orderinfo/getOrderByOrderId?orderId='+order.id+'">' +
                        '                                   <button   type="button" class="btn btn-danger" >\n' +
                        '                                       <span>查看详情</span>\n' +
                        '                                   </button>' +
                        '                               </a>\n' +
                        '                            </td>\n' +
                        '                        </tr>');

            }
            //提示信息5s后消失
           /* setInterval(function () {
                $("#empty").remove();
            }, 6000);*/
        },
        error:function (data) {
            console.log(data);
        }
    });
});