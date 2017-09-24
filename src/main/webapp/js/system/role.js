/****************************角色选择**************************************/
$(function () {
    //全选
    $("#selectAll").click(function () {
        $(".permissionAll option").appendTo($(".permissionSelected"));
    });
    //全不选
    $("#deselectAll").click(function () {
        $(".permissionSelected option").appendTo($(".permissionAll"));
    });
    //选中右移
    $("#select").click(function(){
        $(".permissionAll option:selected").appendTo($(".permissionSelected"));
    });
    //选中左移
    $("#deselect").click(function(){
        $(".permissionSelected option:selected").appendTo($(".permissionAll"));
    });

    //编辑时,出现选择列表中显示了所有的权限,应该消除被选择的权限
    //1.获取角色的所有权限
    var rolePermissions = $(".permissionSelected option");
    //1.1将所有的option元素中的value值取出,还是数组
    var idVals = $.map(rolePermissions,function(item){
        return $(item).val();
    });
    //2.获取权限列表所有的权限,
    var permisssions = $(".permissionAll option");
   // console.log(permisssions);
    //3.将所有权限对比角色拥有的权限,出现相同则将权限列表的权限remove
    $.each(permisssions,function(index,item){
        var trage = $.inArray($(item).val(),idVals);
        if(trage != -1){
            item.remove();
        }
    });
    //在input页面提交表单时,选择权限如果没有被选中会出现没有提交的错误情况
    //需求,在提交表单之前,选中已经选择的角色所有权限
    $("#editForm").submit(function(){
        $(".permissionSelected option").prop("selected",true);
        $(".menuSelected option").prop("selected",true);
    });
});
$(function(){
    //==========================================================
    //系统菜单input视图选择框js操作
    //全选
    $("#mselectAll").click(function () {
        $(".menuAll option").appendTo($(".menuSelected"));
    });
    //全不选
    $("#mdeselectAll").click(function () {
        $(".menuSelected option").appendTo($(".menuAll"));
    });
    //选中右移
    $("#mselect").click(function(){
        $(".menuAll option:selected").appendTo($(".menuSelected"));
    });
    //选中左移
    $("#mdeselect").click(function(){
        $(".menuSelected option:selected").appendTo($(".menuAll"));
    });
    //消除重复
    var roleMenus = $(".menuSelected option");
    var idVals = $.map(roleMenus,function(item){
        return $(item).val();
    });
    //2.获取权限列表所有的权限,
    var menus = $(".menuAll option");
    //3.将所有权限对比角色拥有的权限,出现相同则将权限列表的权限remove
    $.each(menus,function(index,item){
        var trage = $.inArray($(item).val(),idVals);
        if(trage != -1){
            item.remove();
        }
    });

})