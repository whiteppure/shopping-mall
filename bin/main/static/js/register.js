//进度条
$(function () {
    //进度条
    $('.loading').animate({'width':'33%'},50); //第一个进度节点
    $('.loading').animate({'width':'55%'},50); //第二个进度节点
    $('.loading').animate({'width':'80%'},50); //第三个进度节点
    $('.loading').animate({'width':'100%'},50); //第四个进度节点
});
//隐藏进度条
$(document).ready(function(){
    $('.loading').fadeOut();
});


//用户名
var username = /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
var usernameTips = $("span[name='usernameTips']");
var usernameRemove = $("span[name='usernameRemove']");
var usernameOk = $("span[name='usernameOk']");

check($("#username"), username, usernameTips, usernameRemove, usernameOk);
//密码
var password = /^[a-zA-Z]\w{5,17}$/;
var passwordTips = $("span[name='passwordTips']");
var passwordRemove = $("span[name='passwordRemove']");
var passwordOk = $("span[name='passwordOk']");

check($("#password"), password, passwordTips, passwordRemove, passwordOk);

//确认密码
var repasswordTips = $("span[name='repasswordTips']");
var repasswordRemove = $("span[name='repasswordRemove']");
var repasswordOk = $("span[name='repasswordOk']");
$("#passwordConfirm").blur(function () {
    if ($("#password").val() == "" || $("#password").val() == undefined) {
        repasswordOk.css("display", "none");
        repasswordRemove.css("display", "block");
        repasswordTips.css("display", "block");
        repasswordTips.text("密码不能为空");
    } else {
        if ($("#password").val() == $("#passwordConfirm").val()) {
            repasswordOk.css("display", "block");
            repasswordRemove.css("display", "none");
            repasswordTips.css("display", "none");
        } else {
            repasswordOk.css("display", "none");
            repasswordRemove.css("display", "block");
            repasswordTips.css("display", "block");
            repasswordTips.text("两次密码输入不同");
        }
    }
});

//验证手机号
var phoneNum = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
var phoneNumTips = $("span[name='phoneNumTips']");
var phoneNumRemove = $("span[name='phoneNumRemove']");
var phoneNumOk = $("span[name='phoneNumOk']");


check($("#phoneNum"), phoneNum, phoneNumTips, phoneNumRemove, phoneNumOk);


//验证邮箱
var email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var emailTips = $("span[name='emailTips']");
var emailRemove = $("span[name='emailRemove']");
var emailOk = $("span[name='emailOk']");

check($("#email"), email, emailTips, emailRemove, emailOk);


//提交表单
$("#submit").click(function () {
    var userName = $("#username").val();
    var password = $("#password").val();
    var phone = $("#phoneNum").val();
    var email = $("#email").val();

    var user = {
        "userName": userName,
        "password": password,
        "phone": phone,
        "email" : email
    };
    if(
        checkEmpty(userName,usernameTips,usernameRemove,usernameOk)&&
        checkEmpty(password,passwordTips,passwordRemove,passwordOk)&&
        checkEmpty(phone,phoneNumTips,phoneNumRemove,phoneNumOk)&&
        checkEmpty(email,emailTips,emailRemove,emailOk)
    ){
        $.ajax({
            type: "POST",
            url: "/user/register",
            data: JSON.stringify(user),
            dataType: "json",
            contentType:'application/json;charset=utf-8',
            success: function (data) {
                console.log(data.data);
                if (data.status == "200"){
                    // var url = "/index?name="+data.data.userName+"&email="+data.data.email
                    //     +"&data=GFHJKL2#asdfghjhgf456766asdfghjhgf5GFHJKL2#asdfghjhgf456766asdfghjhgf54324564432344543245644323445";
                    window.location.href = "/login.html";
                }
            },
            error: function (msg) {
                console.log(msg);
            }
        });
        return false;
    }else {
        return  false;
    }
});


//验证不为空
function checkEmpty(val,tips,remove,ok) {
    if (val == null || val == "" || val == undefined){
        switch (tips) {
            case usernameTips:
                tips.css("display","block");
                tips.text("用户名不能为空");
                remove.css("display","block");
                ok.css("display","none");
                break;
            case passwordTips:
                tips.css("display","block");
                tips.text("密码不能为空");
                remove.css("display","block");
                ok.css("display","none");
                break;
            case phoneNumTips:
                console.log("手机号码:"+val);
                tips.css("display","block");
                tips.text("手机号码不能为空");
                remove.css("display","block");
                ok.css("display","none");
                break;
            case emailTips:
                tips.css("display","block");
                tips.text("邮箱不能为空");
                remove.css("display","block");
                ok.css("display","none");
                break;
            default:
                break;
        }
        return false;
    }else {
        return true;
    }

}


//验证函数
function check(selector, reg, tips, remove, ok) {
    selector.blur(function () {
        if (selector.val().match(reg)) {
            ok.css("display", "block");
            remove.css("display", "none");
            tips.css("display", "none");
            //异步验证 用户名是否重复
            if (tips == usernameTips) {
                $.ajax({
                    type: "POST",
                    url: "/user/checkUserName",
                    data: {
                        userName: $("#username").val()
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.status == "000310") {
                            ok.css("display", "none");
                            usernameTips.css("display", "block");
                            usernameTips.text(data.message);
                            remove.css("display", "block");
                            $("#submit").attr('disabled','disabled');
                        }
                        if (data.status == "200"){
                            ok.css("display", "block");
                            usernameTips.css("display", "none");
                            usernameTips.css("display", "none");
                            $("#submit").removeAttr('disabled');
                        }

                    },
                    error: function (msg) {
                        console.log(msg);
                    }
                });
            }else if (tips == phoneNumTips){
                $.ajax({
                    type: "POST",
                    url: "/user/checkPhone",
                    data: {
                        phone: $("#phoneNum").val()
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.status == "000310") {
                            ok.css("display", "none");
                            phoneNumTips.css("display", "block");
                            phoneNumTips.text(data.message);
                            remove.css("display", "block");
                            $("#submit").attr('disabled','disabled');
                        }
                        if (data.status == "200"){
                            ok.css("display", "block");
                            phoneNumTips.css("display", "none");
                            phoneNumTips.css("display", "none");
                            $("#submit").removeAttr('disabled');
                        }
                    },
                    error: function (msg) {
                        console.log(msg);
                    }
                });
            }else if (tips == emailTips){
                $.ajax({
                    type: "POST",
                    url: "/user/checkEmail",
                    data: {
                        email: $("#email").val()
                    },
                    dataType: "json",
                    success: function (data) {
                        console.log(data)
                        if (data.status == "000310") {
                            ok.css("display", "none");
                            emailTips.css("display", "block");
                            emailTips.text(data.message);
                            emailRemove.css("display", "block");
                            $("#submit").attr('disabled','disabled');
                        }
                        if (data.status == "200"){
                            ok.css("display", "block");
                            emailTips.css("display", "none");
                            emailRemove.css("display", "none");
                            $("#submit").removeAttr('disabled');
                        }
                    },
                    error: function (msg) {
                        console.log(msg);
                    }
                });
            }

        } else {
            ok.css("display", "none");
            remove.css("display", "block");
            tips.css("display", "block");
            switch (tips) {
                case passwordTips:
                    tips.text("密码必须字母开头，长度6~18之间，允许字母、数字和下划线");
                    break;
                case usernameTips:
                    tips.text("用户名必须字母开头，长度5-16，允许字母数字下划线");
                    break;
                case phoneNumTips:
                    tips.text("请输入正确的手机号码");
                    break;
                case emailTips:
                    tips.text("请输入正确的邮箱");
                    break;
                default:
                    console.log(tips);
                    break;
            }
        }
    });
}



