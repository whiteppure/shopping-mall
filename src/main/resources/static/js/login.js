//进度条
$(function () {
    // isLogin();

});


/*function isLogin(){
    var user =  $("input[name='user']").val();
    if (user != null && user != '' && user != undefined)
        window.location.href = "/shopping-mall/home";
}*/

//加载完成隐藏进度条
$(document).ready(function () {
    $('.loading').animate({'width': '33%'}, 50); //第一个进度节点
    $('.loading').animate({'width': '55%'}, 50); //第二个进度节点
    $('.loading').animate({'width': '80%'}, 50); //第三个进度节点
    $('.loading').animate({'width': '100%'}, 50); //第四个进度节点
    //判断用户是否已经登录
    // isLogin();
    $('.loading').fadeOut();
    //提交表单
    $("form[name='login-form']").submit(function () {
        login();
        return false;
    });
    //用户登录
    $("#login").click(function () {
        login();
    });
});

function login() {
    var account = $("#account").val();
    var password = $("#password").val();
    var code = $("#code").val();


    if (account == null || account == "" || account == undefined) {
        $(".msgFiled").fadeIn("slow");
        $("span[name='error-message']").html("账号不能为空!");
        setTimeout(function () {
            $(".msgFiled").fadeOut();
        }, 1000);
        return false;
    }
    if (password == null || password == "" || account == undefined) {
        $(".msgFiled").fadeIn("slow");
        $("span[name='error-message']").text("密码不能为空!");
        setTimeout(function () {
            $(".msgFiled").fadeOut();
        }, 1000);
        return false;
    }
    if (code == null || code == "" || code == undefined) {
        $(".msgFiled").fadeIn("slow");
        $("span[name='error-message']").text("验证码不能为空!");
        setTimeout(function () {
            $(".msgFiled").fadeOut();
        }, 1000);
        return false;
    }

    $.ajax({
        cache: false,
        type: "POST",
        url: "/user/login",
        data: {
            account: account,
            password: password,
            code: code
        },
        dataType: "json",
        success: function (data) {
            console.log(data)
            if (data.status == "200") {
                $("#msg").text("");
                window.location.href = "/shopping-mall/home";
            } else {
                $(".msgFiled").fadeIn("slow");
                $("span[name='error-message']").text(data.message);
                setTimeout(function () {
                    $(".msgFiled").fadeOut();
                }, 1000);
            }
        },
        error: function (msg) {
            console.log(msg);
        }
    });
    return false;
}


//点击验证码 刷新
$(".check-code").click(function () {

    var xhr = new XMLHttpRequest();
    xhr.open("get", "/images/code", true);
    xhr.responseType = "blob";
    xhr.onload = function () {
        if (this.status == 200) {
            var blob = this.response;
            $(".check-code-img").attr('src', window.URL.createObjectURL(blob));
        }
    };
    xhr.send();

});
