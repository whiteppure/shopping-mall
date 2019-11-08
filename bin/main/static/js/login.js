//进度条
$(function () {
    $('.loading').animate({'width':'33%'},50); //第一个进度节点
    $('.loading').animate({'width':'55%'},50); //第二个进度节点
    $('.loading').animate({'width':'80%'},50); //第三个进度节点
    $('.loading').animate({'width':'100%'},50); //第四个进度节点
});
//加载完成隐藏进度条
$(document).ready(function(){
    $('.loading').fadeOut();
});



$("#login").click(function () {
    var account =  $("#account").val();
    var password =  $("#password").val();
    var code =  $("#code").val();

    $.ajax({
        type: "POST",
        url: "/user/login",
        data: {
            account: account,
            password: password,
            code: code
        },
        dataType: "json",
        success: function (data) {
            if(data.status == "200"){
                $("#msg").text("");
                window.location.href = "/shopping-mall/home";
            }else {
                $("#msg").text(data.message);
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
    return false;
});


//点击验证码
$("#checkCode").click(function () {
    var src =  $("#checkCode").attr('src');
    console.log("type="+typeof (src)+"src="+src);
    $.ajax({
       url:"/images/code",
       type:"GET",
        error:function (data) {
            console.log(data)
        },
        success:function (data) {
            console.log(data);
            // $("#checkCode").attr("src","image/png;"+data);
        }
    });
});
