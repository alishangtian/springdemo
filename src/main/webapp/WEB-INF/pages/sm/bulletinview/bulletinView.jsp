<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<dt:comppath></dt:comppath>
<script type="text/javascript">
	$(function() {
		var contentStr = $('#bulletinViewShowContent').val();
		$('#bulletinViewShowContentDiv').html(contentStr);
	});
</script>
</head>
<body>
	<table width="100%" border="0" cellpadding="2" cellspacing="0">
		<s:hidden id="bulletinViewId" name="bulletin.bulletinId"></s:hidden>
		<tr>
			<th id="bulletinViewShowTitle" align="center">
				关于
				<s:property value="bulletin.staffName"/>
				的
				<span style="color:red;"><s:if test="priority==\"Z\""><特急></s:if></span>
					<span style="color:#A00000;"><s:if test="priority==\"Y\""><加急></s:if></span>
				<s:property value="bulletin.bulletinTitle"/>
				公告
			</th>
		</tr>
		<tr>
			<th id="bulletinViewShowCreateInfo" align="center">
				<s:property value="bulletin.staffName"/>
				<s:property value="bulletin.publishTime"/>
			</th>
		</tr>
		<tr>
			<th align="left">
				<s:hidden id="bulletinViewShowContent" name="bulletin.bulletinContentStr"></s:hidden>
				<div id="bulletinViewShowContentDiv" style="height: 200px; width: 480px;"></div>
			</th>
		</tr>
		<tr>
			<th align="center">
				<s:property value="bulletin.keyword"/>
			</th>
		</tr>
	</table>
</body>
</html>