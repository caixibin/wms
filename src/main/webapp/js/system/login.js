$(function () {
    $("#login_sub").click(function () {
        $("#submitForm").submit();
    });
    //使用回车键能提交表单
    $(window).keydown(function(event) {
        console.log(event.keyCode);
        if (13==event.keyCode){
            $("#submitForm").submit();
        }
    })
});