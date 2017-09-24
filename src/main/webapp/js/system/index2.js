//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    var myHour = time.getHours();
    var myMinu = time.getMinutes();
    var mySecond = time.getSeconds();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."
        + myDay +"  " +myHour + ":" + myMinu +":" +mySecond;
}

/**
 * 隐藏或者显示侧边栏
 *
 */
function switchSysBar(flag) {
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if (flag == true) { // flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({
            width: '280px'
        });
        $('#top_nav').css({
            width: '77%',
            left: '304px'
        });
        $('#main').css({
            left: '280px'
        });
    } else {
        if (left_menu_cnt.is(":visible")) {
            left_menu_cnt.hide(10, 'linear');
            side.css({
                width: '60px'
            });
            $('#top_nav').css({
                width: '100%',
                left: '60px',
                'padding-left': '28px'
            });
            $('#main').css({
                left: '60px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({
                width: '280px'
            });
            $('#top_nav').css({
                width: '77%',
                left: '304px',
                'padding-left': '0px'
            });
            $('#main').css({
                left: '280px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_hide.png');
        }
    }
}

// =====================================
//zTree
var setting = {
    data: {
        simpleData: {
            enable: true
        }
    },callback:{
        onClick:function(event, treeId, treeNode){
           if(treeNode.action){
               $("#rightMain").prop("src",treeNode.action+".action");
               $("#here_area").html("当前位置："+treeNode.name);
           }
        }
    },
    async: {
        enable: true,
        url: "/systemMenu_loadMenusByParentSn.action",
        autoParam: ["sn=qo.parentSn"]
    }
};

var zNodes ={
    business: {id:1,pId:0,name:"业务模块",isParent:true,sn:"business"},
    system:{id:1,pId:0,name:"系统模块",isParent:true,sn:"system"},
    chart:{id:1,pId:0,name:"报表模块",isParent:true,sn:"chart"}

};


function loadMenu(menuName){
    $.fn.zTree.init($("#dleft_tab1"), setting, zNodes[menuName]);
}
// =====================================
$(function () {
    // 加载日期
    loadDate();
    loadMenu("business");
    // ======================================
    // ======================================
    // 切换菜单按钮样式和标题
    $("#TabPage2 li").click(function () {
        //每次都删除全部样式
        $.each($("#TabPage2 li"), function (index, item) {
            $(item).removeClass("selected").children("img").prop("src", "/images/common/" + ($(item).index() + 1) + ".jpg");
        });
        var index = $(this).index() + 1;//第N个菜单,从0开始
        $(this).addClass("selected");
        $(this).children("img").prop("src", "/images/common/" + index + "_hover.jpg");

        //切换标题
        $("#nav_module").children("img").prop("src","/images/common/module_"+index+".png");
        //====
        loadMenu($(this).data("rootmenu"));
    });

    // ======================================
    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function () {
        switchSysBar();
    });
});