$(window).load(function() {
    //判断用户是否已经登录
    $.ajax({
        url:"/user/isLogin",
        type:"POST",
        dataType:"json",
        success:function (data) {
            // console.log(data);
            //200 已经登录
            //其他未登录
            if (data.status == "200")
                window.location.href = "/shopping-mall/home";
                // window.location.href = "/shopping-mall/login";
        },error:function (data) {

        }
    });
});