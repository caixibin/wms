$.ajaxSettings.traditional = true;
//在页面加载完毕之后
$(function () {
    //增加操作
    //1.获取元素并添加事件
    $(".btn_input").click(function () {
        //点击新增按钮跳转页面
        window.location.href = $(this).data("inputurl");
    });

    //获取分页按钮
    $(".btn_page").click(function () {
        //获取按钮的值
        var pageNo = $(this).data("page") || $("input[name='qo.currentPage']").val();
        //获取当前页的元素并将按钮的值赋给当前页
        $("input[name='qo.currentPage']").val(pageNo);
        $("#searchForm").submit();
    });

    //页面容量选择
    $(":input[name='qo.pageSize']").change(function () {
        //跳到首页
        $(".input[name='qo.currentPage']").val(1);
        //提交表单
        $("#searchForm").submit();
    });
    /********************删除单行****************************************/
    $(".btn_delete").click(function () {
        //获取url
        var deleUrl = $(this).data("deleurl");
        //弹出提示框
        showArtDialog("", "请问确定删除吗?", function () {
            $.get(deleUrl, function (data) {
                showArtDialog("", data, function () {
                    window.location.reload();
                })
            })
        })
    });
    /********************审核操作****************************************/
    $(".btn_audit").click(function () {
        //获取url
        var auditUrl = $(this).data("auditurl");
        //弹出提示框
        showArtDialog("", "请问确定审核吗?", function () {
            $.get(auditUrl, function (data) {
                showArtDialog("", data, function () {
                    window.location.reload();
                })
            })
        })
    });
    /********************批量删除操作****************************************/

        //删除所有
    $(".btn_all").click(function () {
        //1.获取选中的行,使用过滤选择
        //console.log($(".acb:checked"));
        var selectIds = $.map($(".acb:checked"), function (item, index) {
            //console.log(item);
            return $(item).data("oid");
        });
        //2.获取选中行的id
        //console.log(selectIds);
        if (selectIds.length == 0) {
            showArtDialog("", "请选择需要删除的行", "true", "")
        } else {
            var deleUrl = $(this).data("deleurl");
            showArtDialog("", "请问确定批量删除吗?",
                function () {
                    //3.发送ajax请求删除数据
                    $.get(deleUrl, {'ids': selectIds}, function (data) {
                        showArtDialog("", data, function () {
                            //刷新页面
                            window.location.reload();
                        })
                    });
                })
        }
    });
    //让页面初始状态为不选中
    $(".acb").prop("checked", false);
    $("#all").prop("checked", false);
    //表头选中之后所有数据都同时选中
    $("#all").change(function () {
        $(".acb").prop("checked", this.checked);
    });
});
/****************************抽取模板artDialog**************************************/
function showArtDialog(title, content, ok, cancel) {
    $.artDialog({
        title: title || "温馨提示",
        content: content || "",
        cancel: cancel || "false",
        ok: ok || "true",
        icon: "face-smile"
    })
}
