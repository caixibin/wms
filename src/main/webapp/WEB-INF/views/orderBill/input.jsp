<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function(){
            $("#edit_table_body").on("click",".searchproduct",function(){
                /*获取当前行*/
                var currentTr = $(this).closest("tr");
                var url = "product_selectProductList.action";
                $.dialog.open(url, {
                    title: "货品选择",
                    width: 980,
                    height: 680,
                    close: function () {
                        var data = $.dialog.data("data");
                        /*当前行的文本框数据进行数据封装*/
                        if(data != null){
                            currentTr.find("[tag=name]").val(data["name"]);
                            currentTr.find("[tag=pid]").val(data["id"]);
                            currentTr.find("[tag=costPrice]").val(data["costPrice"]);
                            currentTr.find("[tag=brand]").html(data["brandName"]);
                        }
                    }
                });
            }).on("change","[tag=number],[tag=costPrice]",function(){
                var currentTr = $(this).closest("tr");
                var productCostPrice = currentTr.find("[tag=costPrice]").val();
                var productNumber = currentTr.find("[tag=number]").val();
                //判断是否获取到的数据是否为null
                if(productCostPrice&&productNumber){
                    var amout = (productCostPrice*productNumber).toFixed(2);
                    currentTr.find("[tag=amount]").html(amout);
                }
            }).on("click",".removeItem",function(){
                var currentTr = $(this).closest("tr");
                if($("#edit_table_body tr").size() == 1){
                    currentTr.find("[tag=name]").val("");
                    currentTr.find("[tag=pid]").val("");
                    currentTr.find("[tag=costPrice]").val("");
                    currentTr.find("[tag=number]").val("");
                    currentTr.find("[tag=brand]").html("");
                    currentTr.find("[tag=amout]").html("");
                    currentTr.find("[tag=remark]").val("");
                }else {
                    currentTr.remove();
                }

            });

            //添加明细的按钮,会自动添加一行数据
            $(".appendRow").click(function(){
                var newTr = $("#edit_table_body tr:first").clone(true);
                newTr.find("[tag=name]").val("");
                newTr.find("[tag=pid]").val("");
                newTr.find("[tag=costPrice]").val("");
                newTr.find("[tag=number]").val("");
                newTr.find("[tag=brand]").html("");
                newTr.find("[tag=amout]").html("");
                newTr.find("[tag=remark]").val("");
                newTr.appendTo("#edit_table_body");
            });

            //添加多条数据
            $(".submit_btn").click(function(){
                $("#edit_table_body tr").each(function(index,item){
                    var _item = $(item);
                    _item.find("[tag=pid]").prop("name","orderBill.items["+index+"].product.id");
                    _item.find("[tag=costPrice]").prop("name","orderBill.items["+index+"].costPrice");
                    _item.find("[tag=number]").prop("name","orderBill.items["+index+"].number");
                    _item.find("[tag=remark]").prop("name","orderBill.items["+index+"].remark");
                });
                $("#editForm").submit();
            });

            $("[name='orderBill.vdate']").click(function(){
                WdatePicker();
            });
        });
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/common_msg.jsp"></jsp:include>
<s:form name="editForm" action="orderBill_saveOrUpdate" method="post" id="editForm">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">订单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <s:hidden name="orderBill.id"/>
                <tr>
                    <td class="ui_text_rt" width="140">订单编号</td>
                    <td class="ui_text_lt">
                        <s:textfield name="orderBill.sn" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <s:select list="#suppliers" listKey="id" listValue="name" name="orderBill.supplier.id" class="ui_select03"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <%--格式化日期--%>
                        <s:date name="orderBill.vdate" format="yyyy-MM-dd" var="vdate"/>
                            <input  class="ui_input_txt02 Wdate" name="orderBill.vdate" value="<s:property value='#vdate'/> "/>
                        <%--<s:textfield cssClass="ui_select03" name="orderBill.vdate"/>--%>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <s:if test="orderBill.id == null">
                                <tr>
                                    <td></td>
                                    <td>
                                        <s:textfield disabled="true" readonly="true" cssClass="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <s:hidden name="orderBill.items[0].product.id" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><s:textfield tag="costPrice" name="orderBill.items[0].costPrice"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td><s:textfield tag="number" name="orderBill.items[0].number"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><s:textfield tag="remark" name="orderBill.items[0].remark"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </s:if>
                            <s:else>
                                <s:iterator value="orderBill.items">
                                        <tr>
                                            <td></td>
                                            <td>
                                                <s:textfield disabled="true" name="product.name" readonly="true" cssClass="ui_input_txt02" tag="name"/>
                                                <img src="/images/common/search.png" class="searchproduct"/>
                                                <s:hidden name="product.id" tag="pid"/>
                                            </td>
                                            <td><span tag="brand">
                                                <s:property value="product.brand.name"/>
                                            </span></td>
                                            <td><s:textfield tag="costPrice" name="costPrice"
                                                             cssClass="ui_input_txt02"/></td>
                                            <td><s:textfield tag="number" name="number"
                                                             cssClass="ui_input_txt02"/></td>
                                            <td><span tag="amount"></span></td>
                                            <td><s:textfield tag="remark" name="remark"
                                                             cssClass="ui_input_txt02"/></td>
                                            <td>
                                                <a href="javascript:;" class="removeItem">删除明细</a>
                                            </td>
                                        </tr>
                                </s:iterator>
                            </s:else>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="button" value="确定保存" class="ui_input_btn01 submit_btn"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</s:form>
</body>
</html>