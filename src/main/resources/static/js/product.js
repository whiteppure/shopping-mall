
$(()=>  {
    //盖章效果
    setTimeout(()=>  {
        $('.container').addClass('loaded')
    }, 500);

});


//控制商品数量
$('body').on('click','.add',()=>  {
    let n=$(this).prev().val();
    let num=parseInt(n)+1;
    if(num===0) return;
    $(this).prev().val(num);
});
$('body').on('click','.jian',()=>  {
    let n=$(this).next().val();
    let num=parseInt(n)-1;
    if(num===0)return;
    $(this).next().val(num);
});




//=====================发布评论==================
$('body').on('click','#comment',()=>  {
    let userId = $("input[name='userId']").val();
    if (userId === undefined || userId === '' || userId === undefined){
        $(".msgFiled").fadeIn("slow");
        $("span[name='msgFiled']").text("您还未登录!");
        setTimeout(function () {
            $(".msgFiled").fadeOut();
        }, 1000);
    }else{
        let productName = $("#productName").html();
        let commentContent = $("#comment-product-content").val();
        let productId = $("#productId").val();
        let userName = $("input[name='userName']").val();


        if(commentContent == "" || commentContent == null){
            $(".msgFiled").fadeIn("slow");
            $("span[name='msgFiled']").text("评价内容不能为空!");
            setTimeout(function () {
                $(".msgFiled").fadeOut();
            }, 1000);
            return false;
        }
        let userComment = {
            "productName":productName,
            "commentContent":commentContent,
            "productId":productId,
            "userName":userName,
            "userId":userId
        };
        $.ajax({
            type: "POST",
            url: "/comment/addComment",
            dataType: "json",
            contentType:'application/json;charset=utf-8',
            data: JSON.stringify(userComment),
            success: function (data) {
                if (data.status === "200"){
                    window.location.reload();
                }
            },
            error: function (data) {
                console.log(data);
                $(".msgFiled").fadeIn("slow");
                $("span[name='msgFiled']").text("登录已过期,请重新登录!");
                setTimeout(function () {
                    $(".msgFiled").fadeOut();
                }, 1000);
            }
        });
    }

});







//======================加入购物车====================
$('body').on('click','#join-cart-btn',()=>  {
    let userId = $("input[name='userId']").val();
    if (userId == undefined){
        $(".msgFiled").fadeIn("slow");
        $("span[name='msgFiled']").text("您还未登录!");
        setTimeout(function () {
            $(".msgFiled").fadeOut();
        }, 1000);

    }else{
        let productId = $("#productId").val();
        let productName = $("#productName").html();
        let productImg = $("#productImg")[0].src;
        let productPrice = $("#productPrice").text();
        let productCount = $("#product-count").val();

        $.ajax({
            type:"POST",
            url: "/cartProduct/addProductToCart",
            dataType: "json",
            data:{
                "userId":userId,
                "productId":productId,
                "productName":productName,
                "productImg":productImg,
                "productPrice":productPrice,
                "productCount":productCount
            },
            success: function (data) {
                if (data.status == "200"){
                    $(".message").fadeIn("slow");
                    $("span[name='message']").text("商品加入到购物车成功!");
                    setTimeout(function () {
                        $(".message").fadeOut();
                    }, 1200);
                }else {
                    $(".msgFiled").fadeIn("slow");
                    $("span[name='msgFiled']").text("商品加入到购物车失败!");
                    setTimeout(function () {
                        $(".msgFiled").fadeOut();
                    }, 1200);
                }
            },
            error:function (data) {
                console.log(data);
                $(".msgFiled").fadeIn("slow");
                $("span[name='msgFiled']").text("商品加入到购物车失败!");
                setTimeout(function () {
                    $(".msgFiled").fadeOut();
                }, 1200);
            }
        }) ;
    }

});



//===================商品预览图片==========
$('body').on('click', '.cover', function () {
    let this_ = $(this);
    let images = this_.parents('.image-list').find('.cover');
    let imagesArr = new Array();
    $.each(images, function (i, image) {
        imagesArr.push($(image).children('img').attr('src'));
    });
    $.pictureViewer({
        images: imagesArr, //需要查看的图片，数据类型为数组
        initImageIndex: this_.index() + 1, //初始查看第几张图片，默认1
        scrollSwitch: true //是否使用鼠标滚轮切换图片，默认false
    });
});





//===================购买商品===================
$('body').on('click','#buy-product-btn', ()=> {
    let userId = $("input[name='userId']").val();
    if (userId === undefined || userId === "" || userId == null){
        //提示用户先登录,才可以购买商品
        $(".msgFiled").fadeIn("slow");
        $("span[name='msgFiled']").text("您还未登录!");
        // window.location.href="/login";
        setTimeout(function () {
            $(".msg").fadeOut();
            $(".msgFiled").fadeOut();
        }, 1200);
    }else{
        let productId = $("#productId").val();
        let productCount = $("#product-count").val();

        //跳转到订单支付页面
        let url = "/shopping-mall/pay-order?" +
            "data=123456789"+"&pId=GUIV1234567"+"&cId=DFGHJKfghhujklrtyuixcvbnertyuixcvbn"+
            "&sId="+productId+"&dId="+productCount+"&aId=GUIV1234567"+"&bId=DFGHJKfghhujklrtyuixcvbnertyuixcvbn"+
            //给模拟字符串加密
            "&userId="+userId+"&productId="+productId+"&productCount="+productCount;
        window.location.href = url;
    }
});



//=================上一个商品 下一个商品=========
//jQuery 正则 获取url路径上的参数
function GetQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

//上一个
$('body').on('click','#pre-goods',() =>  {
    let id = parseInt(GetQueryString("id"))-1 +"";
    let pre =  nextId(id,"pre");
    window.location.href = "/shopping-mall/product?id="+pre;
});
//下一个
$('body').on('click','#next-goods',() =>  {
    let id = parseInt(GetQueryString("id"))+1 +"";
    let next =  nextId(id,"next");
    window.location.href = "/shopping-mall/product?id="+next;
});







// ========用来返回 下一个id 的值=======
 let nextId  = (id, direction) => {

     let result = '';

    $.ajax({
        url:"/productInfo/productIsExistById",
        data:{
            "id":id,
            "direction":direction
        },
        async:false,
        dataType:"json",
        type:"GET",
        success:function (data) {
            result = data.data;
        }

    });
     return result;
 };




