/****************************加载权限**************************************/
$(function(){
    $(".btn_reload").click(function(){
        var url = $(this).data("inputurl");
        showArtDialog("","亲,重新加载权限可能需要耗费很长的时间,你确定加载吗?",function(){
            $.get(url,function(data){
                showArtDialog("",data,function(){
                    window.location.reload();
                })
            })
        },"true");
    });
});
