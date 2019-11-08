//图片上传预览功能
let userAgent = navigator.userAgent; //用于判断浏览器类型

//图片预览
$('body').on('change','.file',function () {

    //每次上传图片前 要清空input
    let img_count = $(".imageDiv").length;

    //获取选择图片的对象
    let docObj = $(this)[0];
    let picDiv = $(this).parents(".picDiv");
    //得到所有的图片文件
    let fileList = docObj.files;

    if (fileList.length > 0)
    // 每次上传图片先 删除以前上传的图片
        $(".delbtn").parents(".imageDiv").remove();

    if (fileList.length + img_count > 5) {
        alert("最多上传5张图片");
        //清空以前上传的内容
        $(this).val("");
        return false;
    }
    //循环遍历  上传图片总数不能多于 5个
    for (let i = 0; i < fileList.length; i++) {
        //动态添加html元素
        let picHtml = "<div class='imageDiv' > <input type='image'  id='img" + fileList[i].name + "' /> <div class='cover'><i class='delbtn'>删除</i></div></div>";
        console.log(picHtml);
        picDiv.prepend(picHtml);
        //获取图片imgi的对象
        let imgObjPreview = document.getElementById("img" + fileList[i].name);
        if (fileList && fileList[i]) {
            //图片属性
            imgObjPreview.style.display = 'block';
            imgObjPreview.style.width = '100px';
            imgObjPreview.style.height = '100px';
            //imgObjPreview.src = docObj.files[0].getAsDataURL();
            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要以下方式
            if (userAgent.indexOf('MSIE') == -1) {
                //IE以外浏览器
                imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]); //获取上传图片文件的物理路径;
                console.log(imgObjPreview.src);
                // var msgHtml = '<input type="file" id="fileInput" multiple/>';
            } else {
                //IE浏览器
                if (docObj.value.indexOf(",") != -1) {
                    let srcArr = docObj.value.split(",");
                    imgObjPreview.src = srcArr[i];
                } else {
                    imgObjPreview.src = docObj.value;
                }
            }
        }
    }


});




//删除 回显的图片
$('body').on('click','.delbtn',function () {
    let _this = $(this);
    _this.parents(".imageDiv").remove();
});





function isEmpty(val){
    if (val === ''|| val === undefined || val === null)
        alert("所填的值不能为空")
}





//=====================点击添加商品======================
$("#add_goods").click(() => {
    let formData = new FormData($("#goods_form")[0]);

    let productName = $("input[name='productName']").val();
    let productPrice = $("input[name='productPrice']").val();
    let productStock = $("input[name='productStock']").val();
    let productType = $("select[name='productType']").val();
    let description = $("textarea[name='description']").val();

    //不为空校验
    if (isEmpty(productName) || isEmpty(productPrice) || isEmpty(productStock) || isEmpty(productType) || isEmpty(description))
        return false;
    
    for (let i = 0; i < $("#fileInput")[0].files.length; i++) {
        formData.append('files',$("#fileInput")[0].files[i]);
    }
    $.ajax({
        url: "/productInfo/addProduct",
        type: "post",
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
        cache:false,
        success: function (data) {
            if (data.data.status === '200')
                alert("添加商品成功!")
            else
                alert("添商品失败!")

        }, error: function (data) {
            console.log(data)
        }
    });

});