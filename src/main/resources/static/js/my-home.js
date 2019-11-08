//进度条
$(function () {
    $('.loading').animate({'width':'33%'},50); //第一个进度节点
    $('.loading').animate({'width':'55%'},50); //第二个进度节点
    $('.loading').animate({'width':'80%'},50); //第三个进度节点
    $('.loading').animate({'width':'100%'},50); //第四个进度节点
});
//加载完成隐藏进度条
$(document).ready(function(){
    $('.loading').fadeOut();
});