
//监听屏幕滚动 显示盒子阴影
$(document).scroll(function () {
    if ($(document).scrollTop() > 120) {
        $("#header").css("box-shadow", "0 28px 50px rgba(25,24,40,.35)");
        $("#header").css("opacity", ".95");
    } else {
        $("#header").css("box-shadow", "none");
        $("#header").css("opacity", "1");
    }
    if ($(document).scrollTop() > 800)
        $("#menu").fadeIn("slow");
    else
        $("#menu").fadeOut("slow");
});
//进度条
$(function () {
    $('.loading').animate({'width': '33%'}, 50); //第一个进度节点
    $('.loading').animate({'width': '55%'}, 50); //第二个进度节点
    $('.loading').animate({'width': '80%'}, 50); //第三个进度节点
    $('.loading').animate({'width': '100%'}, 50); //第四个进度节点
});
//加载完成隐藏进度条
$(document).ready(function () {
    $('.loading').fadeOut();
});


$("#search-form").submit(function () {
    search();
    return false;
});

//搜索
$("#search-product").click(function () {
    search();
});

function search() {
    var q = $("#q").val();
    if (q == null || q == undefined || q == "") {
        // alert("关键字为空!")
        $("span[name='error-message']").text("关键字不能为空");
        $("#filed-message").fadeIn("slow");
        setTimeout(function () {
            $("#filed-message").fadeOut();
        }, 1000);
        return false;
    } else {
        var url = "http://localhost:8082/shopping-mall/search?kw=" + q;
        window.location.href = url;
    }
}

//回到顶部
$("#back-top").click(function () {
    //兼容
    $('body,html').animate({scrollTop:0},800);
    return false;
});
