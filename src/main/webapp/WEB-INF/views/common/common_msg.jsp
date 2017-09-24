<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
    $(function () {
        //列表页面出现异常信息时弹出
      /*  <s:if test="hasErrors()">
            var msg = "<s:property value="actionErrors[0]"/>";
            showArtDialog("",msg,"true");
        </s:if>*/
        var msg = "<s:property value="actionErrors[0]"/>";
        if (msg) {
            $.artDialog({
                title: "温馨提示",
                content: msg,
                icon: "face-smile",
                ok: "true"
            })
        }
        //正常保存或者更新提示框
        <s:if test="hasActionMessages()">
        var msg1 = "<s:property value="actionMessages[0]"/>";
        showArtDialog("",msg1,"true");
        </s:if>
    })

</script>