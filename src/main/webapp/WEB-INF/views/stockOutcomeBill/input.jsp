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
        $(function () {
            /*日历*/
            $(".Wdate").click(function () {
                WdatePicker();
            });

            $("#edit_table_body").on("click", ".searchproduct", function () {//打开产品选择窗口
                //在打开窗口之前找到当前行
                var currentTr = $(this).closest("tr");
                //获取到跳转到产品列表选择界面的url
                var productListUrl = "product_selectProductList.action";
                //窗口弹出之后获取action中传递到
                $.dialog.open(productListUrl, {
                    title: "选择产品列表",
                    width: 980,
                    height: 680,
                    close: function () {
                        var data = $.dialog.data("data");//获取选择列表中的json数据
                        console.log(data);
                        if (data != null) {
                            currentTr.find("[tag=pid]").val(data["id"]);//产品id
                            currentTr.find("[tag=name]").val(data["name"]);//产品名称
                            currentTr.find("[tag=brand]").html(data["brandName"]);//品牌名称
                            currentTr.find("[tag=salePrice]").val(data["salePrice"]);//销售价
                        }
                    }
                });
            }).on("change", "[tag=salePrice],[tag=number]", function () {//计算amount
                //获取当前行,具有单独的一行
                var currentTr = $(this).closest("tr");
                var salePrice = currentTr.find("[tag=salePrice]").val();
                var number = currentTr.find("[tag=number]").val();
                //获取到事件的值,需要进行判断是否有值,有值再进行计算操作
                if (salePrice && number) {
                    var amount = (salePrice * number).toFixed(2);
                    currentTr.find("[tag=amount]").html(amount);
                }
            }).on("click", ".removeItem", function () {//删除当行数据
                //选择当前的点击链接对象,找到点击的tr对象
                var currentTr = $(this).closest("tr");
                //判断当前的tr是否为tbody的first元素,是,删除tr中的文本或者值的信息
                if ($("#edit_table_body tr").size() == 1) {
                    currentTr.find("[tag =name]").val("");
                    currentTr.find("[tag =pid]").val("");
                    currentTr.find("[tag = brand]").html("");
                    currentTr.find("[tag = salePrice]").val("");
                    currentTr.find("[tag = number]").val("");
                    currentTr.find("[tag = amount]").html("");
                    currentTr.find("[tag = remark]").val("");
                } else {
                    //简单粗暴,点击直接删除
                    currentTr.remove();
                }
            });

            /*/!*点击添加明细按钮*!/*/
            $(".appendRow").click(function () {
                var cloneTr = $("#edit_table_body tr:first").clone(true);
                cloneTr.find("[tag =pid]").val("");
                cloneTr.find("[tag = name]").val("");
                cloneTr.find("[tag = brand]").html("");
                cloneTr.find("[tag = salePrice]").val("");
                cloneTr.find("[tag = number]").val("");
                cloneTr.find("[tag = amount]").html("");
                cloneTr.find("[tag = remark]").val("");
                cloneTr.appendTo($("#edit_table_body"))
            });

            //添加多条数据
            $(".submit_btn").click(function(){
                $("#edit_table_body tr").each(function(index,item){
                    var _item = $(item);
                    _item.find("[tag=pid]").prop("name","stockOutcomeBill.items["+index+"].product.id");
                    _item.find("[tag=salePrice]").prop("name","stockOutcomeBill.items["+index+"].salePrice");
                    _item.find("[tag=number]").prop("name","stockOutcomeBill.items["+index+"].number");
                    _item.find("[tag=remark]").prop("name","stockOutcomeBill.items["+index+"].remark");
                });
                $("#editForm").submit();
            });
        });

    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/common_msg.jsp"></jsp:include>
<s:form name="editForm" action="stockOutcomeBill_saveOrUpdate" method="post" id="editForm">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">采购出库单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <s:hidden name="stockOutcomeBill.id"/>
                <tr>
                    <td class="ui_text_rt" width="140">采购出库单编号</td>
                    <td class="ui_text_lt">
                        <s:textfield name="stockOutcomeBill.sn" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">仓库</td>
                    <td class="ui_text_lt">
                        <s:select list="#depots" listKey="id" listValue="name" name="stockOutcomeBill.depot.id"
                                  class="ui_select03"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">客户</td>
                    <td class="ui_text_lt">
                        <s:select list="#clients" listKey="id" listValue="name" name="stockOutcomeBill.client.id"
                                  class="ui_select03"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <s:date name="stockOutcomeBill.vdate" format="yyyy-MM-dd" var="vdate"/>
                        <input class="ui_input_txt02 Wdate" name="stockOutcomeBill.vdate"
                               value="<s:property value='#vdate'/> "/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">采购入库明细</td>
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
                            <s:if test="stockOutcomeBill.id == null">
                                <tr>
                                    <td></td>
                                    <td>
                                        <s:textfield disabled="true" readonly="true" cssClass="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <s:hidden name="stockOutcomeBill.items[0].product.id" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><s:textfield tag="salePrice" name="stockOutcomeBill.items[0].salePrice"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td><s:textfield tag="number" name="stockOutcomeBill.items[0].number"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><s:textfield tag="remark" name="stockOutcomeBill.items[0].remark"
                                                     cssClass="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </s:if>
                            <s:else>
                                <s:iterator value="stockOutcomeBill.items">
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
                                        <td><s:textfield tag="salePrice" name="salePrice" cssClass="ui_input_txt02"/></td>
                                        <td><s:textfield tag="number" name="number" cssClass="ui_input_txt02"/></td>
                                        <td>
                                            <span tag="amount">
                                                <s:property value="amount"/>
                                            </span>
                                        </td>
                                        <td><s:textfield tag="remark" name="remark" cssClass="ui_input_txt02"/></td>
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