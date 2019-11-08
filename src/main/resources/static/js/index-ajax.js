(function ($) {
    $.extend({
        //1、取值使用    $.Request("name")
        Request: function (name) {
            var sValue = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]*)(\&?)", "i"));
            //decodeURIComponent解码
            return sValue ? decodeURIComponent(sValue[1]) : decodeURIComponent(sValue);

        },
        //2、给url加参数    $.UrlUpdateParams(url, "add", 11111);
        UrlUpdateParams: function (url, name, value) {
            var r = url;
            if (r != null && r != 'undefined' && r != "") {
                value = encodeURIComponent(value);
                var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
                var tmp = name + "=" + value;
                if (url.match(reg) != null) {
                    r = url.replace(eval(reg), tmp);
                } else {
                    if (url.match("[\?]")) {
                        r = url + "&" + tmp;
                    } else {
                        r = url + "?" + tmp;
                    }
                }
            }
            return r;
        }


    });
})(jQuery);

//进度条
$('.loading').animate({'width':'33%'},50); //第一个进度节点
$('.loading').animate({'width':'55%'},50); //第二个进度节点
$('.loading').animate({'width':'80%'},50); //第三个进度节点
$('.loading').animate({'width':'100%'},50); //第四个进度节点
$(document).ready(function(){
    $('.loading').fadeOut();
});


//换肤
$(".tab-btn").click(function(){
    $(".bg-list").slideToggle();
});
$(".img-item").click(function(){
    var src = $(this).attr("data-src");
    $("body").css({
        "background-image":"url(" + src + ")"
    })
});


/*
$(function () {
    var username = $.Request("name");
    var email = $.Request("email");
    if(username == "null"){
        $("#join").css("display","block");
        $("#user").css("display","none");
    }else {
        $("#user").css("display","block");
        $("#join").css("display","none");
        $("#user").text(username);
    }
});
*/
