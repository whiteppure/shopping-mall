



//请求 商品类型
$(function () {
    $.ajax({
       url:"/productType/getAllTypes",
       type:"GET",
       dataType:"json",
       success:function (data) {
           console.log(data.data);
           var types = data.data;
           for (var i=0; i<types.length;i++){
               $("select[name='productType']").append('<option  value="'+types[i].typeName+'">'+types[i].typeName+'</option>');
           }
       },error:function (data) {
            console.log(data)
       }

    });
});


//判断商品名称是否重复
$("input[name='productName']").blur(function () {
    $.ajax({
        url: "/productInfo/getProductInfoByName",
        type: "GET",
        dataType: "json",
        data:{
          "productName": $("input[name='productName']").val()
        },
        success:function (data) {
            if (data.status === "001000"){
                $("#msg").text(data.message);
                $("button[type='button']").attr('disabled','disabled');
            }else if (data.status === "200") {
                $("#msg").text("");
                $("button[type='button']").removeAttr('disabled');
            }

        },
        error:function (data) {
            console.log(data)
        }
    });
});


























