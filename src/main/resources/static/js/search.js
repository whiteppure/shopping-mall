
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

$(function () {
    var kw = getUrlParam('kw');
    if (kw == null || kw == "" || kw== undefined){
        window.location.href = "/shopping-mall/home";
    }
    //获取url路径上的参数
    var kw = window.location.href.split("?")[1].split("=")[1];//拆分url得到”=”后面的参数
    var q =  decodeURI(kw);//中文转码
    $.ajax({
        url: "http://49.235.206.214:9200/shopping-mall-product/productInfo/_search",
        data: {"q": "*"+q+"*"},
        type: "GET",
        success: function (data) {
            $("#q").val(q);
            $("#kw").text("'  "+q+"   '  ");
            var hits =  data.hits;
            $("#search-total").text(hits.hits.length);
            if (hits.total == 0){
                $(".search-title").css("display","none");
                $(".wrapper-main").append('<div class="search-result-empty">\n' +
                    '                           <h2 class="h2">没有找到<em class="color-red">'+'" '+  q +' " '+'</em>的相关商品信息</h2>\n' +
                    '                       </div>');
            } else {
                for (var i=0; i< hits.hits.length; i++){
                    var product =  hits.hits[i]._source;
                    $(".wrapper-main").append('<div class="search-result">\n' +
                        '                <a href="/productInfo/getAllInfoById?id='+product.id+'" class="figure result-figure">\n' +
                        '                    <img class="figure-pic" src="'+product.productImg+'">\n' +
                        '                </a>\n' +
                        '                <h2 class="result-title">\n' +
                        '                    <a href="/productInfo/getAllInfoById?id='+product.id+'">\n' +
                        '                        '+product.productName+'\n' +
                        '                    </a>\n' +
                        '                </h2>\n' +
                        '                <div class="result-info">\n' +
                        '                    <div class="info-line">\n' +
                        '                        <div class="info-item info-item-odd">\n' +
                        '                            <span class="label">时　间：</span>\n' +
                        '                            <span class="content">'+product.createDate+'</span>\n' +
                        '                        </div>\n' +
                        '                        <div class="info-item info-item-even shop-info">\n' +
                        '                            <span class="label">商　家：</span>\n' +
                        '                            <span class="content"><a href="#">'+product.shopName+'</a></span>\n' +
                        '                        </div>\n' +
                        '                        <div class="info-item info-type">\n' +
                        '                            <span class="label">类　型：</span>\n' +
                        '                            <span class="content">'+product.productType+'</span>\n' +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '                    <div class="info-item info-item-desc">\n' +
                        '                        <span class="label">描　述：</span>\n' +
                        '                        <span class="desc-text" title="'+product.description+'">'+product.description+'</span>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '            </div>');
                }
            }
        },
        error: function (data) {
            console.log(data)
        }
    });
});