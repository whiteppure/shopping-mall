$(function () {
   $.ajax({
     url:"/user/getUserById",
     data:{
         "id":$("input[name='userId']").val()
     },
     type:"GET",
     dataType:"json",
     success:function (data) {
         console.log(data.data);
         //给input 赋值
         $("#username").val(data.data.userName);
         $("#phoneNum").val(data.data.phone);
         $("#email").val(data.data.email);

     },error:function () {

     }
   });
});