//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."
        + myDay;
}
$(function () {

    // 加载日期
    loadDate();
    //loadMenu("business");
    //没有被选中的
    $("#TabPage2 li").click(function () {
        $("#TabPage2 li").each(function (index, item) {
            $(item).find("img").prop("src", "/images/common/" + (index + 1) + ".jpg");
            $(item).removeClass("selected")
        })
        //选中的
        $(this).find("img").prop("src", "/images/common/" + ($(this).index() + 1) + "_hover.jpg");
        $(this).addClass("selected")
        //修改菜单标题
        $("#nav_module").find("img").prop("src", "/images/common/module_" + ($(this).index() + 1) + ".png")
        //zTree初始化
        loadMenu($(this).data("rootmenu"));

    })
    $.fn.zTree.init($("#dleft_tab1"), setting, zNodes["business"]);//
})
//=============================================================================
//zTree设置
var setting = {
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: function (event, treeId, treeNode) {
            console.log(event,treeId,treeNode);
            if (treeNode.action) {
                $("#rightMain").prop("src", treeNode.action + ".action");
                //当前位置的设置显示
                $("#here_area").html("当前位置:&nbsp;"+treeNode.getParentNode().name+"&nbsp;>&nbsp;"+treeNode.name)
            }
        }
    },
    async: {
        enable: true,
        url: "/systemMenu_loadMenusBySn.action",
        autoParam: ["sn=qo.parentSn"]
    }
};
//======================简单格式===========================================
var business = [{id: 1, pId: 0, name: "业务模块",sn:"business",isParent:true}];
var systemManage = [{id: 2, pId: 0, name: "系统管理",sn:"system",isParent:true}];
var charts = [{id: 3, pId: 0, name: "报表",sn:"chart",isParent:true}];
var zNodes = {
    "business": business,
    "systemManage": systemManage,
    "charts": charts
};
//创建一个函数赋值给初始化zTree
function loadMenu(parentSn) {
    $.fn.zTree.init($("#dleft_tab1"), setting, zNodes[parentSn]);
}
//点击事件

//======================标准格式===========================================
/*var zNodes =[
 { name:"业务模块 ",
 children: [
 { name:"父节点11 - 折叠"},
 { name:"父节点12 - 折叠"},
 { name:"父节点13 - 没有子节点"}
 ]},
 { name:"系统管理",
 children: [
 { name:"部门管理"},
 { name:"员工管理"},
 { name:"权限管理"},
 { name:"角色管理"},
 { name:"系统菜单管理"}
 ]},
 { name:"报表",
 children: [
 { name:"父节点11 - 折叠"},
 { name:"父节点12 - 折叠"},
 { name:"父节点13 - 没有子节点"}
 ]
 }
 ];*/
