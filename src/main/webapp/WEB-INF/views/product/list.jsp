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
<script type="text/javascript" src="js/commonAll.js"></script>
<title>PSS-商品管理</title>
<style>
	.alt td{ background:black !important;}
</style>
	<script type="text/javascript">
		$(function(){
			$('.fancybox').fancybox();
		})
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/common_msg.jsp"></jsp:include>
	<s:form id="searchForm" action="product" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<%--<div id="box_center">--%>
							<%--编码/名称--%>
								<%--<s:textfield name="qo.keyword" class="ui_input_txt02"/>--%>
								<%--&lt;%&ndash;<s:textfield name="qo.endDdate" class="ui_input_txt02 Wdate"/>&ndash;%&gt;--%>
							<%--品牌--%>
							<%--<s:select list="#brands" name="qo.brandId" listKey="id" listValue="name" class="ui_select01"--%>
									  <%--headerKey="-1" headerValue="所有品牌"/>--%>
							<%--<input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>--%>
						<%--</div>--%>
						<div id="box_bottom">
							<input type="button" value="新增" class="ui_input_btn01 btn_input" data-inputurl="<s:url action="product_input"/>"/>
							<input type="button" value="删除所有" class="ui_input_btn01 btn_all" data-deleurl="<s:url action="product_batchDelete"/>"/>
						</div>
					</div>
				</div>
			</div>
			<div class="ui_content">
				<div class="ui_tb">
					<table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
						<tr>
							<th width="30"><input type="checkbox" id="all" /></th>
							<th>商品图片</th>
							<th>商品名称</th>
							<th>商品编码</th>
							<th>商品品牌</th>
							<th>成本价格</th>
							<th>销售价格</th>
							<th>操作</th>
						</tr>
						<tbody>
							<s:iterator value="#pageResult.data">
								<tr>
									<td><input type="checkbox" name="IDCheck" class="acb" data-oid="<s:property value="id"/>"/></td>
									<td>
										<%--<img src="<s:property value="smallImagePath"/>" >--%>
										<a class="fancybox" href="<s:property value="imagePath"/>"
										   title="<s:property value="name"/>">
											<img src="<s:property value="smallImagePath"/>" alt="" width="50px" /></a>
									</td>
									<td><s:property value="name"/></td>
									<td><s:property value="sn"/></td>
									<td><s:property value="brand.name"/></td>
									<td><s:property value="costPrice"/></td>
									<td><s:property value="salePrice"/></td>
									<td>
										<s:a action="product_input">编辑
											<s:param name="product.id" value="id"/>
										</s:a>
										<%--<s:a action="product_delete">删除
											<s:param name="product.id" value="id"/>
										</s:a>--%>
										<s:url namespace="/" action="product_delete" var="deleUrl" escapeAmp="false">
											<s:param name="product.id" value="id"/>
											<s:param name="product.imagePath" value="imagePath"/>
										</s:url>
										<a href="javascript:;" class="btn_delete" data-deleurl="<s:property value="#deleUrl"/>">删除</a>
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
