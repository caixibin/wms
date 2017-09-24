$(function () {
    $("#editForm").validate({
        rules: {
            'employee.name': {
                required: true,
                minlength: 2
            },
            'employee.password':{
                required:true,
                minlength:2,
                digits:true
            },
            'repassword':{
                required:true,
                minlength:2,
                digits:true,
                equalTo:"#password"
            },
            'employee.email':{
                required:true,
                email:true
            },
            'employee.age':{
                required:true,
                digits:true,
                range:[18,60]
            }
        },
        messages: {
            'employee.name': {
                required: "名字需要输入",
                minlength: "名字长度必须大于等于两个汉字"
            },
            'employee.password':{
                required:"密码需要输入",
                minlength:"密码长度需要大于等于2个数字",
                digits:"密码输入必须为数字"
            },
            'repassword':{
                required:"验证密码需要输入",
                minlength:"验证密码长度需要大于等于2个数字",
                digits:"验证密码输入必须为数字",
                equalTo:"请输入一致的密码"
            },
            'employee.email':{
                required:"邮箱需要输入",
                email:"邮箱输入格式必须为@xx.com"
            },
            'employee.age':{
                required:"年龄必须输入",
                digits:"年龄输入必须是数字",
                range:"年龄范围是18-60"
            }
        }
    })
/****************************input角色选择**************************************/
//1选中所有角色
    $("#selectAll").click(function(){
        $(".roleAll option").appendTo($(".roleSelected"));
    });
    //全部不选
    $("#deselectAll").click(function(){
        $(".roleSelected option").appendTo($(".roleAll"));
    });
    //角色选中的移动到用户选择列表
    $("#select").click(function(){
        $(".roleAll option:selected").appendTo($(".roleSelected"))
    });
    //用户列表选择的角色移除
    $("#deselect").click(function(){
        $(".roleSelected option:selected").appendTo($(".roleAll"))
    });

    //解决重复编辑丢失角色选择问题
    //需求,在提交表单之前将用户的角色选择进行选中操作
    $("#editForm").submit(function(){
        $(".roleSelected option").prop("selected",true);
    });

    //解决用户选中的角色,在选择列表中还存在
    //1.获取两个选择列表所有的元素的值value
    var idVals = $.map($(".roleSelected option"),function(item){
        return $(item).val();
    });
    //循环角色列表并与用户的选中角色进行对比
    //2.遍历两个数组的值并对比,将选择列表中用户已经选择的进行移除处理

    $.each($(".roleAll option"),function(index,item){
        if($.inArray($(item).val(),idVals) != -1){
            item.remove();
        }
    });
});