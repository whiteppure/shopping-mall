//jQuery 正则 获取url路径上的参数
function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

//根据路径获取商品id进行查询
function init() {
    let productId = GetQueryString("id");
    $.ajax({
        url: "/productInfo/getAllInfoById",
        data: {
            id: productId
        },
        type:"GET",
        dataType:"json",
        success:function (data) {
            // ===============商品信息===============
            let product = data.data.product;
            $("#goods_content").append(' ' +
                '' +
                '<div class="thumb-box fl img-zoom">\n' +
                '                <input type="hidden" id="productId"  value="'+product.id+'">\n' +
                '                <img src="'+product.productImg+'" title="'+product.productName+'" id="productImg" class="img-responsive img goods-img " alt="'+product.productName+'">\n' +
                '                <!-- 图片预览示例 -->\n' +
                '                <div class="main-content ">\n' +
                '                    <div class="image-list row">\n' +
                '                        <div class="cover col-lg-3 col-md-3 col-xs-3 col-sm-3">\n' +
                '                            <img src="'+product.productImgPic1+'" alt="'+product.productName+'" class="preView_img">\n' +
                '                        </div>\n' +
                '                        <div class="cover col-lg-3 col-md-3 col-xs-3 col-sm-3">\n' +
                '                            <img src="'+product.productImgPic2+'" alt="'+product.productName+'" class="preView_img">\n' +
                '                        </div>\n' +
                '                        <div class="cover col-lg-3 col-md-3 col-xs-3 col-sm-3">\n' +
                '                             <img src="'+product.productImgPic3+'" alt="'+product.productName+'" class="preView_img">\n' +
                '                        </div>\n' +
                '                        <div class="cover col-lg-3 col-md-3 col-xs-3 col-sm-3">\n' +
                '                          <img src="'+product.productImgPic4+'" alt="'+product.productName+'" class="preView_img">\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '            <div class="info-box fl">\n' +
                '                <div class="dev-issue-item-title" id="productName">'+product.productName+'</div>\n' +
                '                <div class="dev-issue-item-summary-content">'+product.description+'</div>\n' +
                '                <div class="info-item">\n' +
                '                    <div class="t-title">\n' +
                '                        <i class="fa fa-money" aria-hidden="true"></i>\n' +
                '                    </div>\n' +
                '                    <div  class="price">\n' +
                '                        <span id="productPrice">'+product.productPrice+'</span>元\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '                <div  class="info-item">\n' +
                '                    <div class="t-title">\n' +
                '                        <i class="fa fa-tag" aria-hidden="true"></i>\n' +
                '                    </div>\n' +
                '                    <div class="dev-issue-item-footer">\n' +
                '                        <div  class="dev-issue-item-tags">\n' +
                '                            <div  class="ivu-tag ivu-tag-blue ivu-tag-checked">\n' +
                '                                <span class="ivu-tag-text ivu-tag-color-white">'+product.productType+'</span>\n' +
                '                            </div>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '                <div  class="info-item">\n' +
                '                    <div  class="t-title">\n' +
                '                        <i class="fa fa-home" aria-hidden="true"></i>\n' +
                '                    </div>\n' +
                '                    <div>\n' +
                '                       '+product.shopName+'\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '                <div class="info-item">\n' +
                '                    <div  class="t-title">\n' +
                '                        <i class="fa fa-clock-o" aria-hidden="true"></i>\n' +
                '                    </div>\n' +
                '                    <div>\n' +
                '                       '+product.createDate+'\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '                <div class="info-item">\n' +
                '                    <div  class="t-title">\n' +
                '                        <i class="fa fa-cart-plus" aria-hidden="true"></i>\n' +
                '                    </div>\n' +
                '                    <div class="gw_num">\n' +
                '                        <em class="jian">-</em>\n' +
                '                        <input type="text" id="product-count" value="1" class="num" readonly="readonly"/>\n' +
                '                        <em class="add">+</em>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '                <!-- 按钮 -->\n' +
                '                <div class="row">\n' +
                '                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' +
                '                        <button type="button" class="btn" id="buy-product-btn">购买商品</button>\n' +
                '                        <button type="button" class="btn" id="join-cart-btn">加入购物车</button>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>');

            //商品二维码
            $("#goods_code").append(' <img src="'+product.qrcode+'" class="img-responsive" style="width: 20em' +
                '">');
            $("#spinner_goods_code").css("display","none");
            //隐藏加载效果
            $("#spinner_goods_content").css("display","none");
            // ===============商品信息===============

            //=================评论信息=================
            let comments = data.data.comments;

            $("#comment-area").append('<div class="navbar-form" id="comment-area">\n' +
                '                    <div>\n' +
                '                        <textarea required type="text" class="comment-textarea form-control "\n' +
                '                                  placeholder="说点什么吧~" id="comment-product-content"></textarea>\n' +
                '                    </div>\n' +
                '                    <button class="btn comment-btn" id="comment">发布评论</button>\n' +
                '                    </div>');

            //遍历评论信息
            $.each(comments,function (index,comment) {
                $(".comment-list").append('<div class="row border-bottom user-comment">\n' +
                    '                <div class="col-md-10">\n' +
                    '                    <span>'+comment.commentContent+'</span>\n' +
                    '                </div>\n' +
                    '                <div class="col-md-2">\n' +
                    '                    <div class="inline-block">\n' +
                    '                        <a href="javascript:void(0);">'+comment.userName+'</a>\n' +
                    '                    </div>\n' +
                    '                    <div>'+comment.createTime+'</div>\n' +
                    '                </div>\n' +
                    '            </div>');
            });


            //=================评论信息=================
        }
    });
}


$(function () {
    init();
    recommend(1);
    page(1);
});


//商品翻页
function page(current) {
    if (current <= 1) {
        $("#goods-left").attr('disabled', 'disabled');
        $("#goods-left").css('cursor', 'not-allowed');
    }
    //上一页
    $("#goods-left").click(function () {
        $("#recommend-goods").empty();
        current = current - 1;
        $("#spinner_recommend_goods").css('display','block');
        recommend(current);
        if (current == 1) {
            $("#goods-left").attr('disabled', 'disabled');
            $("#goods-left").css('cursor', 'not-allowed');
        }
    });

    //下一页
    $("#goods-right").click(function () {
        $("#recommend-goods").empty();
        current = current + 1;
        $("#spinner_recommend_goods").css('display','block');
        recommend(current);
        $("#goods-left").removeAttr('disabled');
        $("#goods-left").css('cursor', 'pointer');
    });

}

//推荐商品
function recommend(current) {
    let productId = GetQueryString("id");
    $.ajax({
        url: "/productInfo/getTypeProductsByPage",
        type: "GET",
        data: {
            "productId": productId,
            "current": current,
            "size": 4
        },
        dataType: "json",
        success: function (data) {
            let goods = data.data.records;
            if (goods == '' || goods == null) {
                $("#recommend-goods").append('<h2>暂无推荐~~</h2>');
            } else {
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
            if ($(".recommend-goods").length > 0) {
                $("#recommend-goods").fadeIn("slow");
                $("#spinner_recommend_goods").css("display", "none");
            }


            //尾页
            if (current == data.data.pages) {
                $("#goods-right").attr('disabled', 'disabled');
                $("#goods-right").css('cursor', 'not-allowed');
            }
            if (current < data.data.pages) {
                $("#goods-right").removeAttr("disabled");
                $("#goods-right").css('cursor', 'pointer');
            }

        }
    });
}