$(function () {

    var userId =  $("input[name='userId']").val();
    if (userId == undefined || userId == "undefined" || userId == "" || userId == null){
        window.location.href = "/shopping-mall";
    }

   $.ajax({
       url: "/address/getAddressById",
       dataType: "json",
       type: "GET",
       data: {
           "id": $("input[name='addressId']").val()
       },
       success:function (data) {
           console.log(data);
           $("span[name='userName']").text(data.data.userName);
           var phone = data.data.phone;
           $("span[name='phone']").text(phone.substr(0,3)+"****"+phone.substr(7));
           $("span[name='address']").text(data.data.address);
           $("span[name='comment']").text(data.data.comment);

       },
       error:function (data) {

       }
   });
});