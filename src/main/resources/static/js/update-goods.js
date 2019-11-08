

//============自执行============
$(()=> {
    //获取商品类型
    getType();

    //根据id获取商品信息
    getGoods();

});


//===================获取路径上的 参数===================
function getUrlParam(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    let r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//==============获取商品类型===========
let productType;
function getType(){
    $.ajax({
        url:"/productType/getAllTypes",
        type:"GET",
        dataType:"json",
        cache:false,
        async:false,
        success:function (data) {
            console.log(data.data);
            let types = data.data;
            for (let i=0; i<types.length;i++){
                $("select[name='productType']").append('<option  value="'+types[i].typeName+'">'+types[i].typeName+'</option>');
            }
            productType = $("select[name='productType']").val();
        },error:function (data) {
            console.log(data)
        }

    });

}


//============根据商品id查询该商品 回显================
function getGoods(){

    let id = getUrlParam("productId");
    console.log("========>"+id);
    if (id === null || id === undefined)  window.location.href="/error/500";
    $.ajax({
        url:"/productInfo/getGoodsInfoById",
        data: {"productId":id},
        type:"GET",
        success:function (data) {
            console.log(data);
            if (data.data === null) window.location.href="/error/500";
            //赋值
            let goods = data.data;
            $("input[name='productName']").val(goods.productName);
             $("input[name='productPrice']").val(goods.productPrice);
            $("input[name='productStock']").val(goods.productStock);

            $("select[name='productType']").val(goods.productType);

            $("textarea[name='description']").val(goods.description);
            $("input[name='productWeight']").val(goods.productWeight);



            $("#upd_area").append('<div class="imageDiv">' +
                ' <input type="image" name="productImg" id="img1.jpg" src="'+goods.productImg+'" style="display: block; width: 100px; height: 100px;">' +
                ' <div class="cover">' +
                '<i class="delbtn">删除</i>' +
                '</div>' +
                '</div>');
            if (goods.productImgPic1 != null){
                $("#upd_area").append('<div class="imageDiv">' +
                    ' <input type="image" name="productImgPic1" id="img2.jpg" src="'+goods.productImgPic1+'" style="display: block; width: 100px; height: 100px;">' +
                    ' <div class="cover">' +
                    '<i class="delbtn">删除</i>' +
                    '</div>' +
                    '</div>')
            }
            if (goods.productImgPic2 != null){
                $("#upd_area").append('<div class="imageDiv">' +
                    ' <input type="image" name="productImgPic2" id="img3.jpg" src="'+goods.productImgPic2+'" style="display: block; width: 100px; height: 100px;">' +
                    ' <div class="cover">' +
                    '<i class="delbtn">删除</i>' +
                    '</div>' +
                    '</div>')
            }
            if (goods.productImgPic3 != null){
                $("#upd_area").append('<div class="imageDiv">' +
                    ' <input type="image" name="productImgPic3" id="img4.jpg" src="'+goods.productImgPic3+'" style="display: block; width: 100px; height: 100px;">' +
                    ' <div class="cover">' +
                    '<i class="delbtn">删除</i>' +
                    '</div>' +
                    '</div>')
            }
            if (goods.productImgPic4 != null){
                $("#upd_area").append('<div class="imageDiv">' +
                    ' <input type="image" name="productImgPic4" id="img5.jpg" src="'+goods.productImgPic4+'" style="display: block; width: 100px; height: 100px; onclick="imgDisplay(this)">' +
                    ' <div class="cover">' +
                    '<i class="delbtn">删除</i>' +
                    '</div>' +
                    '</div>')
            }


        } ,error:function () {

        }
    });

}


