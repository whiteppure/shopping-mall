//进度条
$(function () {
    //进度条
    $('.loading').animate({'width': '33%'}, 50); //第一个进度节点
    $('.loading').animate({'width': '55%'}, 50); //第二个进度节点
    $('.loading').animate({'width': '80%'}, 50); //第三个进度节点
    $('.loading').animate({'width': '100%'}, 50); //第四个进度节点

});
//隐藏进度条
$(document).ready(function () {
    $('.loading').fadeOut();

    //验证函数
    check_user_name();
    check_password();
    confirm_pwd_plus();
    check_phone();
    check_email();

});

var username = /^[a-zA-Z][a-zA-Z0-9_]{4,15}$/;
var usernameTips = $("span[name='usernameTips']");
var usernameRemove = $("span[name='usernameRemove']");
var usernameOk = $("span[name='usernameOk']");

//用户名
function check_user_name() {
    check($("#username"), username, usernameTips, usernameRemove, usernameOk);
}

var password = /^[a-zA-Z]\w{5,17}$/;
var passwordTips = $("span[name='passwordTips']");
var passwordRemove = $("span[name='passwordRemove']");
var passwordOk = $("span[name='passwordOk']");

function check_password() {
    //密码
    check($("#password"), password, passwordTips, passwordRemove, passwordOk);
}

var repasswordTips = $("span[name='repasswordTips']");
var repasswordRemove = $("span[name='repasswordRemove']");
var repasswordOk = $("span[name='repasswordOk']");

//确认密码
function confirm_pwd_plus() {
    $("#passwordConfirm").blur(function () {
        confirm_password();
    });
}

function confirm_password() {
    repasswordTips.css("margin-bottom", "-15px");
    if ($("#password").val() == "" || $("#password").val() == undefined) {
        repasswordOk.css("display", "none");
        repasswordRemove.css("display", "block");
        repasswordTips.css("display", "block");
        repasswordTips.text("密码不能为空");
        return false;
    } else {
        if ($("#password").val() == $("#passwordConfirm").val()) {
            repasswordOk.css("display", "block");
            repasswordRemove.css("display", "none");
            repasswordTips.css("display", "none");
            return true;
        } else {
            repasswordOk.css("display", "none");
            repasswordRemove.css("display", "block");
            repasswordTips.css("display", "block");
            repasswordTips.text("两次密码输入不同");
            return false;
        }
    }
}

var phoneNum = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
var phoneNumTips = $("span[name='phoneNumTips']");
var phoneNumRemove = $("span[name='phoneNumRemove']");
var phoneNumOk = $("span[name='phoneNumOk']");

function check_phone() {
    //验证手机号

    check($("#phoneNum"), phoneNum, phoneNumTips, phoneNumRemove, phoneNumOk);
}

var email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var emailTips = $("span[name='emailTips']");
var emailRemove = $("span[name='emailRemove']");
var emailOk = $("span[name='emailOk']");

function check_email() {
    //验证邮箱
    check($("#email"), email, emailTips, emailRemove, emailOk);
}


//点击下一步
$("#next").click(function () {


    var userName = $("#username").val();
    var password = $("#password").val();
    var phone = $("#phoneNum").val();
    var email = $("#email").val();

    if (
        checkEmpty(userName, usernameTips, usernameRemove, usernameOk) &&
        checkEmpty(password, passwordTips, passwordRemove, passwordOk) &&
        confirm_password() &&
        checkEmpty(phone, phoneNumTips, phoneNumRemove, phoneNumOk) &&
        checkEmpty(email, emailTips, emailRemove, emailOk)
    ) {
        //显示上一步按钮
        $("#back-step").css('display', 'block');
        //将登陆按钮隐藏
        $("#login").css('display', 'none');
        //
        $(".send-code").css('display', 'inline-block');
        //隐藏第一步提交的信息
        $("div[name='first']").css('display', 'none');
        //显示第二步提交的信息
        $("div[name='second']").css('display', 'block');

        $("div[name='phone-code']").css('display', "inline-block");
        //显示注册按钮
        $("div[name='register']").css('display', 'block');
        //隐藏下一步按钮
        $("div[name='next']").css('display', 'none');
        //上一步输入的手机号码
        $("span[name='phone-num']").text("手机号码:   " + phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2'));
        $("#phone-num").css('display', "block");


        return false;
    } else {
        return false;
    }
});


//点击上一步
$("#back-step").click(function () {
    //将第二步隐藏
    $("div[name='second']").css("display", 'none');
    //显示第一步提交的信息
    $("div[name='first']").css("display", 'block');
    //显示登陆按钮
    $("#login").css('display', 'block');
    //隐藏上一步按钮
    $("#back-step").css('display', 'none');
    //显示下一步按钮
    $("div[name='next']").css('display', 'block');
});

//短信验证码
let phone_code;
//点击发送短信按钮
$("input[name='sendCode']").click(() => {
    $.ajax({
        url: "/user/sendCode",
        type: "post",
        dataType: 'json',
        async: false,
        data:{
            phone:$("#phoneNum").val()
        },
        success: function (data) {
            console.log(data);
            phone_code = data.data;

        }, error: function (data) {

        }

    });
});


//点击注册
$("#register").click(() => {

    //获取输入的短信验证码
    let code =  $("#code").val();
    if (code !== null || code !== '' || code !== undefined){
        if (code === phone_code+""){
            let userName = $("#username").val();
            let password = $("#password").val();
            let phone = $("#phoneNum").val();
            let email = $("#email").val();

            let user = {
                "userName": userName,
                "password": password,
                "phone": phone,
                "email": email
            };

            $.ajax({
                type: "POST",
                url: "/user/register",
                data: JSON.stringify(user),
                dataType: "json",
                contentType: 'application/json;charset=utf-8',
                success: function (data) {
                    console.log(data.data);
                    if (data.status === "200") {
                        // var url = "/index?name="+data.data.userName+"&email="+data.data.email
                        //     +"&data=GFHJKL2#asdfghjhgf456766asdfghjhgf5GFHJKL2#asdfghjhgf456766asdfghjhgf54324564432344543245644323445";
                        window.location.href = "/shopping-mall/login";
                    }
                },
                error: function (msg) {
                    console.log(msg);
                }
            });

        } else{
            alert("输入的验证码不正确!")
        }
    }else {
        alert("验证码为空")
    }

});


//验证不为空
let userName_count = 0;
let phone_count = 0;
let email_count = 0;

function checkEmpty(val, tips, remove, ok) {
    tips.css("margin-bottom", "-15px");
    if (val == null || val === "" || val === undefined) {
        switch (tips) {
            case usernameTips:
                tips.css("display", "block");
                tips.text("用户名不能为空");
                remove.css("display", "block");
                ok.css("display", "none");
                break;
            case passwordTips:
                tips.css("display", "block");
                tips.text("密码不能为空");
                remove.css("display", "block");
                ok.css("display", "none");
                break;
            case phoneNumTips:
                tips.css("display", "block");
                tips.text("手机号码不能为空");
                remove.css("display", "block");
                ok.css("display", "none");
                break;
            case emailTips:
                tips.css("display", "block");
                tips.text("邮箱不能为空");
                remove.css("display", "block");
                ok.css("display", "none");
                break;
            default:
                break;
        }
        return false;
    } else {
        let userName_flag = false;
        let email_flag = false;
        let phone_flag = false;

        $(function () {

            $.ajax({
                async: false,
                type: "POST",
                url: "/user/checkUserName",
                data: {
                    userName: $("#username").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.status === "200") {
                        ok.css("display", "block");
                        usernameTips.css("display", "none");
                        userName_flag = true;
                        return true;
                    } else {
                        ok.css("display", "none");
                        usernameTips.css("display", "block");
                        usernameTips.css("margin-bottom", "-15px");
                        usernameTips.text(data.message);
                        remove.css("display", "block");
                        // $("#next").attr('disabled','disabled');
                        return false;
                    }
                },
                error: function (msg) {
                    console.log(msg);
                    return false;
                }
            });
            $.ajax({
                async: false,
                type: "POST",
                url: "/user/checkPhone",
                data: {
                    phone: $("#phoneNum").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.status === "200") {
                        ok.css("display", "block");
                        phoneNumTips.css("display", "none");
                        phone_flag = true;
                        return true;
                    } else {
                        ok.css("display", "none");
                        phoneNumTips.css("display", "block");
                        phoneNumTips.css("margin-bottom", "-15px");
                        phoneNumTips.text(data.message);
                        remove.css("display", "block");
                        // $("#next").attr('disabled','disabled');
                        return false;
                    }
                },
                error: function (msg) {
                    console.log(msg);
                    return false;
                }
            });
            $.ajax({
                async: false,
                type: "POST",
                url: "/user/checkEmail",
                data: {
                    email: $("#email").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.status === "200") {
                        ok.css("display", "block");
                        emailTips.css("display", "none");
                        emailRemove.css("display", "none");
                        email_flag = true;
                        return true;
                    } else {
                        ok.css("display", "none");
                        emailTips.css("display", "block");
                        emailTips.css("margin-bottom", "-15px");
                        emailTips.text(data.message);
                        emailRemove.css("display", "block");
                        // $("#next").attr('disabled','disabled');
                        return false;
                    }
                },
                error: function (msg) {
                    console.log(msg);
                    return false;
                }
            });

        });

        //验证其他
        if (userName_flag) {
            userName_count = userName_count + 1;
            if (phone_flag) {
                phone_count = phone_count + 1;
                if (email_flag)
                    email_count = email_count + 1;

            }
        }
        return userName_count !== 0 && phone_count !== 0 && email_count !== 0;
    }

}


//验证函数
function check(selector, reg, tips, remove, ok) {
    return selector.blur(function () {
        tips.css("margin-bottom", "-15px");
        if (selector.val().match(reg)) {
            ok.css("display", "block");
            remove.css("display", "none");
            tips.css("display", "none");
            //异步验证 用户名是否重复
            if (tips == usernameTips) {
                $.ajax({
                    async: false,
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
                            // $("#next").attr('disabled','disabled');
                            return false;
                        }
                        if (data.status == "200") {
                            ok.css("display", "block");
                            usernameTips.css("display", "none");
                            return true;
                        }
                        return false;
                    },
                    error: function (msg) {
                        console.log(msg);
                        return false;
                    }
                });
            } else if (tips == phoneNumTips) {
                $.ajax({
                    async: false,
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
                            // $("#next").attr('disabled','disabled');
                            return false;
                        }
                        if (data.status == "200") {
                            ok.css("display", "block");
                            phoneNumTips.css("display", "none");
                            return true;
                        }
                        return false;
                    },
                    error: function (msg) {
                        console.log(msg);
                        return false;
                    }
                });
            } else if (tips == emailTips) {
                $.ajax({
                    async: false,
                    type: "POST",
                    url: "/user/checkEmail",
                    data: {
                        email: $("#email").val()
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.status == "000310") {
                            ok.css("display", "none");
                            emailTips.css("display", "block");
                            emailTips.text(data.message);
                            emailRemove.css("display", "block");
                            // $("#next").attr('disabled','disabled');
                            return false;
                        }
                        if (data.status == "200") {
                            ok.css("display", "block");
                            emailTips.css("display", "none");
                            emailRemove.css("display", "none");
                            return true;
                        }
                        return false;
                    },
                    error: function (msg) {
                        console.log(msg);
                        return false;
                    }
                });
            }

            //如果都不重复  去除属性按钮的禁用属性
            //为了防止用户恶意篡改 后台双重判断
            /*if (userName_flag && phone_flag && email_flag)
                $("#register").removeAttr('disabled');*/

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
            return false;
        }


    });
}



