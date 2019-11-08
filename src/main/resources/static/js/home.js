//进度条
/*$(function () {
    $('.loading').animate({'width':'33%'},50); //第一个进度节点
    $('.loading').animate({'width':'55%'},50); //第二个进度节点
    $('.loading').animate({'width':'80%'},50); //第三个进度节点
    $('.loading').animate({'width':'100%'},50); //第四个进度节点
});
//加载完成隐藏进度条
$(document).ready(function(){
    $('.loading').fadeOut();


//监听屏幕滚动 显示盒子阴影
    $(document).scroll(function () {
        if ($(document).scrollTop() > 200) {
            $("#header").css("box-shadow", "0 28px 50px rgba(25,24,40,.35)");
            $("#header").css("opacity",".95");
        } else {
            $("#header").css("box-shadow", "none");
            $("#header").css("opacity","1");
        }

        //滚动到底部 请求数据
        if ($(window).scrollTop() > $("#footer").offset().top - $("#footer").get(0).offsetHeight) {
            //请求数据
            // getGoods();
        }

    });
});*/


//点击购物车
/*document.getElementsByName('cart')[0].onclick = function () {
    var userId = $("input[name='userId']").val();
    if (userId == "undefined" || userId == undefined || userId == null || userId == ""){
        alert("请先登录~~")
        window.location.href = "/login";
    }else{
        window.location.href = "/cart-product";
    }
};*/


/*//模拟搜索
$("button[name='search-product']").click(function () {
    var keyword = $("input[name='keyWord']").val();
    $.ajax({
        type: "GET",
        url: "/productInfo/getProductByName",
        data: {
            keyWord: keyword
        },
        dataType: "json",
        success: function (data) {
            console.log(data);
            $("#search-goods").empty();
            if (data.status == "200") {

                //清空商品信息
                if (data.data.length != 0){
                    $("#search-goods").append(
                        '<div class="panel panel-heading">\n' +
                        '<h2>为您找到相关商品信息</h2>\n' +
                        '</div>\n'
                    );
                    for(var i=0;i<data.data.length;i++){
                        var product = data.data[i];
                        $("#search-goods").append(
                            '<div class="col-lg-3 col-sm-3 col-md-3 col-xs-3">\n' +
                            '<div class="ct-product">\n' +
                            '<div class="image"><img src="'+product.productImg +'" alt=""></div>\n' +
                            '<div class="inner">\n' +
                            '<a href="/shopping-mall/product?id='+product.id+'" class="btn btn-motive ct-product-button"><i class="fa fa-shopping-cart"></i></a>\n' +
                            '<h2 class="ct-product-title">'+product.productName+'</h2>\n' +
                            '<p class="ct-product-description">'+product.description+'</p><span class="ct-product-price">$'+product.productPrice+'</span>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>');
                    }
                }
            } else {
                console.log(data);
                $("#search-goods").append(
                    '<div class="panel panel-heading">\n' +
                    '<h2>暂无相关商品信息</h2>\n' +
                    '</div>\n'
                );
            }
            //返回距离顶部1000px
            $('body,html').animate({scrollTop:$(".carousel").get(0).offsetHeight},100);
        },
        error: function (data) {
            console.log(data)
        }
    });
    return false;
});*/


//页面加载时请求数据
$(function () {

    //商品推荐
    getGoods();
    //电子商品类
    elect();
    //生活用品类
    life();
    //书籍类
    book();
    //服饰类
    clothes();
    //乐器类
    music();
    //宠物类
    pet();
});

//电子商品类
function elect() {
    getTypeGoods($("#type-elect"), 1, 3, $("#goods-right-elect"));
    page(1, $("#goods-left-elect"), $("#goods-right-elect"), $("#type-elect"), 3, $("div[name='elect-spinner']"));
}

//生活用品类
function life() {
    getTypeGoods($("#type-life"), 1, 4, $("#goods-right-life"));
    page(1, $("#goods-left-life"), $("#goods-right-life"), $("#type-life"), 4, $("div[name='life-spinner']"));
}


//书籍类
function book() {
    getTypeGoods($("#type-book"), 1, 1, $("#type-book"));
    page(1, $("#goods-left-book"), $("#goods-right-book"), $("#type-book"), 1, $("div[name='book-spinner']"));
}

//服装类
function clothes() {
    getTypeGoods($("#type-clothes"), 1, 2, $("#goods-right-clothes"));
    page(1, $("#goods-left-clothes"), $("#goods-right-clothes"), $("#type-clothes"), 2, $("div[name='clothes-spinner']"));
}

//乐器类
function music() {
    getTypeGoods($("#type-music"), 1, 10, $("#goods-right-music"));
    page(1, $("#goods-left-music"), $("#goods-right-music"), $("#type-music"), 10, $("div[name='music-spinner']"));
}

//宠物类
function pet() {
    getTypeGoods($("#type-pet"), 1, 5, $("#goods-right-pet"));
    page(1, $("#goods-left-pet"), $("#goods-right-pet"), $("#type-pet"), 5, $("div[name='pet-spinner']"));
}

//分页效果
/**
 *
 * @param current 当前页
 * @param left 下一页
 * @param right 上一页
 * @param content 内容
 * @param typeId 类型id
 * @param spinner 加载效果
 */
function page(current, left, right, content, typeId, spinner) {
    //如果是首页禁用按钮
    if (current <= 1) {
        left.attr('disabled', 'disabled');
        left.css('cursor', 'not-allowed');
    }
    //上一页
    left.click(function () {
        content.empty();
        current = current - 1;
        spinner.fadeIn("slow");
        content.css("display", "none");
        getTypeGoods(content, current, typeId, right);
        if (current == 1) {
            left.attr('disabled', 'disabled');
            left.css('cursor', 'not-allowed');
        }
    });

    //下一页
    right.click(function () {
        content.empty();
        current = current + 1;
        spinner.fadeIn("slow");
        content.css("display", "none");
        getTypeGoods(content, current, typeId, right, spinner);
        left.removeAttr('disabled');
        left.css('cursor', 'pointer');
    });

}

//商品类型
function getTypeGoods(content, current, typeId, right, spinner) {
    $.ajax({
        url: "/productInfo/getAllProductByTypeId",
        type: "GET",
        data: {
            "typeId": typeId,
            "current": current,
            "size": 4
        },
        dataType: "json",
        success: function (data) {
            // console.log(data);
            var goods = data.data.records;
            if (goods == '' || goods == null) {
                content.append('<div class="col-lg-3 col-sm-3 col-md-3 col-xs-3"><h2>暂无该类型商品~~</h2></div>');
                right.attr('disabled', 'disabled');
                right.css('cursor', 'not-allowed');
            } else {
                for (var i = 0; i < goods.length; i++) {
                    content.append(
                        '                <div class="col-lg-3 col-sm-3 col-md-3 col-xs-3 recommend-goods">\n' +
                        '                    <a href="/shopping-mall/product?id=' + goods[i].id + '">\n' +
                        '                        <img src="' + goods[i].productImg + '" alt="" class="img-responsive" style="height: 10em;width: 100%;">\n' +
                        '                        <div class="inner">\n' +
                        '                            <h2 class="ct-product-title">' + goods[i].productName + '</h2>\n' +
                        '                            <span class="ct-product-price" style="color: red">' + goods[i].productPrice + '￥</span>\n' +
                        '                            <p class="ct-product-description">' + goods[i].description + '</p>\n' +
                        '                        </div>\n' +
                        '                    </a>\n' +
                        '                </div>');
                }
            }
            //隐藏加载效果
            $(".spinner").css("display","none");
            content.fadeIn();
            //尾页
            if (current == data.data.pages) {
                right.attr('disabled', 'disabled');
                right.css('cursor', 'not-allowed');
            }
            if (current < data.data.pages) {
                right.removeAttr("disabled");
                right.css('cursor', 'pointer');
            }

        }, error: function (data) {
            console.log(data);
        }
    });
}


//商品推荐
function getGoods() {
    $.ajax({
        type: "GET",
        url: "/productInfo/getProducts",
        dataType: "json",
        success: function (data) {
            // console.log(data);
            //请求成功
            if (data.status == "200") {
                if (data.data.length != 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        var product = data.data[i];
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
                    $(".spinner").fadeOut("slowly");
                }
            } else {
                // console.log(data);
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


//懒加载
function lazy() {
    $("img[name='lazy-load-mark']").lazyload({
        placeholder: "/static/image/loading.gif", //用图片提前占位
        // placeholder,值为某一图片路径.此图片用来占据将要加载的图片的位置,待图片加载时,占位图则会隐藏
        effect: "fadeIn", // 载入使用何种效果
        // effect(特效),值有show(直接显示),fadeIn(淡入),slideDown(下拉)等,常用fadeIn
        threshold: -200// 提前开始加载
        // threshold,值为数字,代表页面高度.如设置为200,表示滚动条在离目标位置还有200的高度时就开始加载图片,可以做到不让用户察觉
        //event: 'click',  // 事件触发时才加载
        // event,值有click(点击),mouseover(鼠标划过),sporty(运动的),foobar(…).可以实现鼠标莫过或点击图片才开始加载,后两个值未测试…
        //container: $("#container"),  // 对某容器中的图片实现效果
        // container,值为某容器.lazyload默认在拉动浏览器滚动条时生效,这个参数可以让你在拉动某DIV的滚动条时依次加载其中的图片
        //failurelimit : 10 // 图片排序混乱时
        // failurelimit,值为数字.lazyload默认在找到第一张不在可见区域里的图片时则不再继续加载,但当HTML容器混乱的时候可能出现可见区域内图片并没加载出来的情况,failurelimit意在加载N张可见区域外的图片,以避免出现这个问题.
    });

}

