<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
<link href="/style/common_style.css" rel="stylesheet" type="text/css">
<link href="/js/plugins/fancyBox/jquery.fancybox.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=green"></script>
	<script type="text/javascript" src="/js/plugins/fancyBox/jquery.fancybox.pack.js"></script>
	<script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
<title>PSS-销售出库管理</title>
<style>
	.alt td{ background:black !important;}
</style>
	<script type="text/javascript">
		$(function(){
			$("[name='qo.beginDate']").click(function(){
				WdatePicker({
					//设置该选择条件最大的日期
					maxDate:$("[name='qo.endDate']").val()
				});
			});
			$("[name='qo.endDate']").click(function(){
				WdatePicker({
					//设置最小时间为第一个选择日期
					minDate:$("[name='qo.beginDate']").val()

				});
			});
		})
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/common_msg.jsp"></jsp:include>
	<s:form id="searchForm" action="stockOutcomeBill" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_center">
							业务时间
							<s:date name="qo.beginDate" format="yyyy-MM-dd" var="beginDate"/>
							<input name="qo.beginDate" class="ui_input_txt02 Wdate" value="<s:property value='#beginDate'/>"/> ~
							<s:date name="qo.endDate" format="yyyy-MM-dd" var="endDate"/>
							<input name="qo.endDate" class="ui_input_txt02 Wdate" value="<s:property value='#endDate'/>"/>

							入仓仓库
							<s:select list="#depots" name="qo.depotId" listKey="id" listValue="name" class="ui_select01"
									  headerKey="-1" headerValue="全部"/>
							客户
							<s:select list="#clients" name="qo.clientId" listKey="id" listValue="name" class="ui_select01"
									  headerKey="-1" headerValue="全部"/>
							状态
							<s:select list='#{-1:"全部",0:"未审核",1:"已审核"}' name="qo.status" class="ui_select01"/>
						</div>
						<div id="box_bottom">
							<input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
							<input type="button" value="新增" class="ui_input_btn01 btn_input" data-inputurl="<s:url action="stockOutcomeBill_input"/>"/>
							<%--<input type="button" value="删除所有" class="ui_input_btn01 btn_all" data-deleurl="<s:url action="stockOutcomeBill_batchDelete"/>"/>--%>
						</div>
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tr>
							<th width="30"><input type="checkbox" id="all" /></th>
							<th>出库单编号</th>
							<th>业务时间</th>
							<th>仓库</th>
							<th>客户</th>
							<th>出库总数量</th>
							<th>出库总金额</th>
							<th>录入人</th>
							<th>审核人</th>
							<th>审核状态</th>
							<th>操作</th>
						</tr>
						<tbody>
							<s:iterator value="#pageResult.data">
								<tr>
									<td><input type="checkbox" name="IDCheck" class="acb" data-oid="<s:property value="id"/>"/></td>
									<td><s:property value="sn"/></td>
									<td>
										<s:date name="vdate" format="yyyy-MM-dd" var="vdateFormat"/>
										<s:property value="#vdateFormat" />
									</td>
									<td><s:property value="depot.name"/></td>
									<td><s:property value="client.name"/></td>
									<td><s:property value="totalNumber"/></td>
									<td><s:property value="totalAmount"/></td>
									<td><s:property value="inputUser.name"/></td>
									<td><s:property value="auditor.name"/></td>
									<td>
										<s:if test="status == 0">
											<span style="color: green">未审核</span>
										</s:if>
										<s:else>
											<span style="color: red">已审核</span>
										</s:else>
									</td>
									<td>
										<s:if test="status == 0">
											<s:url namespace="/" action="stockOutcomeBill_audit" var="auditUrl" escapeAmp="false">
												<s:param name="stockOutcomeBill.id" value="id"/>
											</s:url>
											<a href="javascript:;" class="btn_audit" data-auditurl="<s:property value="#auditUrl"/>">审核</a>
											<s:a action="stockOutcomeBill_input">编辑
												<s:param name="stockOutcomeBill.id" value="id"/>
											</s:a>
											<s:url namespace="/" action="stockOutcomeBill_delete" var="deleUrl" escapeAmp="false">
												<s:param name="stockOutcomeBill.id" value="id"/>
											</s:url>
											<a href="javascript:;" class="btn_delete" data-deleurl="<s:property value="#deleUrl"/>">删除</a>
										</s:if>
										<s:else>
											<s:a action="stockOutcomeBill_view">查看
												<s:param name="stockOutcomeBill.id" value="id"/>
											</s:a>
										</s:else>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<jsp:include page="/WEB-INF/views/common/common_page/page.jsp"/>
			</div>
		</div>
	</s:form>
</body>
</html>
