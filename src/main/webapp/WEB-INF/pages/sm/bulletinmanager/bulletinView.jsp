<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css"
	charset="utf-8" />
<dt:comppath></dt:comppath>
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>
<style type="text/css">
.datatag {
	float: left;
	background-color: #A6C9E2;
	margin: 2px;
}

.datatag .ui-icon-close {
	padding: 0px;
	margin: 0px;
}
</style>
<script type="text/javascript">
	$(function() {
		//初始化公告内容
		var contentStr = $('#bulletinContentStr').val();
		$('#bulletinViewContentStr').html(contentStr);
	});
</script>
</head>
<body>
	<input type="hidden" id="bulletinContentStr"
		value="<s:property value="bulletin.bulletinContent" />"></input>
	<table width="100%" border="0" cellpadding="2" cellspacing="0">
		<tr>
			<td><table width="100%" align="center" class="condition">
					<tr>
						<th width="10%" align="left">公告标题：</th>
						<td width="90%"><s:property value="bulletin.bulletinTitle" /></td>
					</tr>
					<tr>
						<th width="10%" align="left">公告内容：</th>
						<td width="90%">
							<div id="bulletinViewContentStr"
								style="width: 180px; height: 150px;"></div>
						</td>
					</tr>
					<tr>
						<th width="10%" align="left">发布时间：</th>
						<td width="90%"><s:property value="bulletin.publishTime" /></td>
					</tr>
					<tr>
						<th width="10%" align="left">撤销时间：</th>
						<td width="90%"><s:property value="bulletin.revokeTime" /></td>
					</tr>
					<tr>
						<th width="10%" align="left">优先级：</th>
						<td width="90%"><s:if test="bulletin.priority==\"X\"">一般</s:if>
							<s:if test="bulletin.priority==\"Y\"">加急</s:if> <s:if
								test="bulletin.priority==\"Z\"">特急</s:if></td>
					</tr>
					<tr>
						<th width="10%" align="left">关键字：</th>
						<td width="90%"><s:property value="bulletin.keyword" /></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>