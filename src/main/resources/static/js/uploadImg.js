let imgSrc = [];
let imgFile = [];
let imgName = [];


//删除 回显的图片
$('body').on('click','.delbtn',function () {
    let _this = $(this);
    _this.parents(".imageDiv").remove();
});


//========方法 入口==============
imgUpload({
    inputId:'fileInput', //input框id
    imgBox:'imgBox', //图片容器id
    buttonId:'update_goods', //提交按钮id
    upUrl:'/productInfo/updProduct',  //提交地址
    data:'files', //参数名
    num:"5"//上传个数
})


function imgUpload(obj) {
    let oInput = '#' + obj.inputId;
    let imgBox = '#' + obj.imgBox;
    let btn = '#' + obj.buttonId;

    // 需要上传图片总数
    let imgCount;
    //新上传图片数量
    let fileListLength;
    //页面回显图片数量
    let oldImgLength;

    $(oInput).on("change", function () {
        let fileImg = $(oInput)[0];
        // 新上传文件个数
        let  fileList =  fileImg.files;
        fileListLength = fileList.length;
        // 回显图片 个数
        oldImgLength =  $("#upd_area").children(".imageDiv").length;
        // 需要上传的图片总数
        imgCount = fileList.length + oldImgLength;

        //============ 修改图片判断时  删除了几个图片就要上传几张图片============


        if (imgCount > 5){
            alert("上传图片最多为5张!");
            return false;
        }
        for (var i = 0; i < fileList.length; i++) {
            var imgSrcI = getObjectURL(fileList[i]);
            imgName.push(fileList[i].name);
            imgSrc.push(imgSrcI);
            imgFile.push(fileList[i]);
        }
        addNewContent(imgBox);
    });

    //=======点击修改 商品信息=======
    $(btn).on('click', function () {
        // 上传个数
        if (!limitNum(obj.num)) {
            alert("超过限制");
            return false;
        }
        // 数据 参数 表单 id
        let fd = new FormData($('#goods_form')[0]);
        fd.append("id",getUrlParam("productId"));
        // 图片路径  获取未被删除的图片路径
        fd.append("productImg",$("input[name='productImg']").attr("src"));
        fd.append("productImgPic1",$("input[name='productImgPic1']").attr("src"));
        fd.append("productImgPic2",$("input[name='productImgPic2']").attr("src"));
        fd.append("productImgPic3",$("input[name='productImgPic3']").attr("src"));
        fd.append("productImgPic4",$("input[name='productImgPic4']").attr("src"));

  /*      fd.append("imgCount",imgCount);
        fd.append("fileListLength",fileListLength);
        fd.append("oldImgLength",oldImgLength);*/

        for (let i = 0; i < imgFile.length; i++) {
            // 穿过来的 参数 名称 拼接[]  变成数组
            fd.append(obj.data + "[]", imgFile[i]);
        }
        // ajax  修改  Ctrl + 鼠标单机 方法名
        submitPicture(obj.upUrl, fd);
    })
}

function addNewContent(obj) {
    $(imgBox).html("");
    for (let a = 0; a < imgSrc.length; a++) {
        let oldBox = $(obj).html();
        $(obj).html(oldBox + '<div class="imageDiv"><img style="width: 100px; height: 100px;" title=' + imgName[a] + ' alt=' + imgName[a] + ' src=' + imgSrc[a] + ' onclick="imgDisplay(this)"><div class="cover"><i onclick="removeImg(this,' + a + ')" class="delbtn">删除</i></div></div>');
    }
}

function removeImg(obj, index) {
    imgSrc.splice(index, 1);
    imgFile.splice(index, 1);
    imgName.splice(index, 1);
    var boxId = "#" + $(obj).parent(".cover").parent('.imageDiv').parent().attr("id");
    addNewContent(boxId);
}

function limitNum(num) {
    if (!num) {
        return true;
    } else if (imgFile.length > num) {
        return false;
    } else {
        return true;
    }
}

function submitPicture(url, data) {
    for (let p of data) {
        console.log(p);
    }
    if (url && data) {
        $.ajax({
            type: "post",
            url: url,
            async: true,
            data: data,
            processData: false,
            contentType: false,
            success: function (data) {
                console.log(data);
                if (data.status === "200"){
                    alert("修改商品成功");
                    window.location.href = "/shop/my-goods";
                }else {
                    alert("修改商品信息失败");
                }
            }
        });
    } else {
        alert('请打开控制台查看传递参数！');
    }
}

function imgDisplay(obj) {
    var src = $(obj).attr("src");
    var imgHtml = '<div style="width: 100%;height: 100vh;overflow: auto;background: rgba(0,0,0,0.5);text-align: center;position: fixed;top: 0;left: 0;z-index: 1000;"><img src=' + src + ' style="margin-top: 100px;width: 70%;margin-bottom: 100px;"/><p style="font-size: 50px;position: fixed;top: 30px;right: 30px;color: white;cursor: pointer;" onclick="closePicture(this)">×</p></div>'
    $('body').append(imgHtml);
}

function closePicture(obj) {
    $(obj).parent("div").remove();
}

function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}