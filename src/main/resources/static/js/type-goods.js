function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
$(function () {
    var typeId = getUrlParam('typeId');
    if (typeId == null || typeId == "" || typeId== undefined)
        window.location.href = "/shopping-mall/home";
    //加载商品类型
    goodsByTypeId(typeId);
    //猜你喜欢
    like();

});

//换一换
$("#goods-switch").click(function () {
    like();
});

function  goodsByTypeId(typeId){
    $.ajax({
        type: "GET",
        url: "/productInfo/getAllProductByTypeIdNotPage",
        dataType: "json",
        data: {"typeId": typeId},
        success: function (data) {
            console.log(data.data)
            //请求成功
            if (data.status == "200") {
                if (data.data.total != 0) {
                    var total = data.data.total;
                    $("#search-total").text(total)
                    for (var i = 0; i < total; i++) {
                        var product = data.data.goods[i];
                        $("#goods").append(
                            '<div class="col-lg-3 col-sm-3 col-md-3 col-xs-3">\n' +
                            '<div class="ct-product">\n' +
                            '<div class="image"><img src=' + product.productImg + ' alt="" name="lazy-load-mark"></div>\n' +
                            '<div class="inner">\n' +
                            '<a href="/shopping-mall/product?id=' + product.id + '"    class="btn btn-motive ct-product-button">' +
                            '<i class="fa fa-shopping-cart"></i>' +
                            '</a>\n' +
                            '<h2 class="ct-product-title">' + product.productName + '</h2>\n' +
                            '<p class="ct-product-description product-desc">' + product.description + '</p><span style="color: red;" class="ct-product-price">' + product.productPrice + '￥</span>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>');
                    }
                    lazy();
                    $(".spinner").css("display", "none");
                }
            } else {
                console.log(data);
                $("#product-wrapper").append(
                    '<div class="panel panel-heading">\n' +
                    '<h2>暂无商品信息</h2>\n' +
                    '</div>\n'
                );
            }
        },
        error: function (data) {
            console.log(data)
        }
    });
}


//猜你喜欢
function like(){
    $.ajax({
        url: "/productInfo/randomNumByCount",
        type: "GET",
        data: {
            "count": 4
        },
        dataType: "json",
        success: function (data) {
            var goods = data.data;
            if (goods == '' || goods == null) {
                $("#recommend-goods").append('<h2 class="h2">暂无推荐~~</h2>');
            } else {
                $("#recommend-goods").empty();
                for (var i = 0; i < goods.length; i++) {
                    $("#recommend-goods").append(
                        '                <div class="col-lg-3 col-sm-3 col-md-3 col-xs-3 recommend-goods">\n' +
                        '                    <a href="/shopping-mall/product?id=' + goods[i].id + '">\n' +
                        '                        <img src="' + goods[i].productImg + '" alt="" class="img-responsive" style="height: 10em;width: 100%;">\n' +
                        '                        <div class="inner">\n' +
                        '                            <h2 class="ct-product-title">' + goods[i].productName + '</h2>\n' +
                        '                            <span class="ct-product-price" style="color: red">' + goods[i].productPrice + '￥</span>\n' +
                        '                            <p class="ct-product-description product-desc">' + goods[i].description + '</p>\n' +
                        '                        </div>\n' +
                        '                    </a>\n' +
                        '                </div>');
                }
            }
            //加载效果
            $("#recommend-goods").fadeIn("slow");
            $(".spinner").css("display", "none");
        }
    });

}


//监听滚动  当数据过多时滚动 加载数据  不分页 append
$(document).scroll(function () {
    //当滚动到指定位置时 加载数据
    if ($(document).scrollTop() > 700){

    }

});

//懒加载
function lazy() {
    $("img[name='lazy-load-mark']").lazyload({
        placeholder: "/static/image/loading.gif", //用图片提前占位
        // placeholder,值为某一图片路径.此图片用来占据将要加载的图片的位置,待图片加载时,占位图则会隐藏
        effect: "fadeIn", // 载入使用何种效果
        // effect(特效),值有show(直接显示),fadeIn(淡入),slideDown(下拉)等,常用fadeIn
        threshold: -100// 提前开始加载
        // threshold,值为数字,代表页面高度.如设置为200,表示滚动条在离目标位置还有200的高度时就开始加载图片,可以做到不让用户察觉
        //event: 'click',  // 事件触发时才加载
        // event,值有click(点击),mouseover(鼠标划过),sporty(运动的),foobar(…).可以实现鼠标莫过或点击图片才开始加载,后两个值未测试…
        //container: $("#container"),  // 对某容器中的图片实现效果
        // container,值为某容器.lazyload默认在拉动浏览器滚动条时生效,这个参数可以让你在拉动某DIV的滚动条时依次加载其中的图片
        //failurelimit : 10 // 图片排序混乱时
        // failurelimit,值为数字.lazyload默认在找到第一张不在可见区域里的图片时则不再继续加载,但当HTML容器混乱的时候可能出现可见区域内图片并没加载出来的情况,failurelimit意在加载N张可见区域外的图片,以避免出现这个问题.
    });

}