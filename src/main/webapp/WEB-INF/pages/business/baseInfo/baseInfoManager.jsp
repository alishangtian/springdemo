<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基地管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.baseInfomanager.js"></script>
</head>
<body>
	<div class="ui-widget-content func-panel" id="base">
		<table class="condition" width="100%" align="center">
			<caption>
				<span>jid查询</span>
			</caption>
			<tr>
				<th>基地名称：</th>
				<td><dt:input id="name" name="baseInfo.name" width="100"></dt:input>
				</td>
				<th>状态：</th>
				<td><select id="state" name="baseInfo.state">
						<option value="0">在用</option>
						<option value="1">停用</option>
				</select></td>
			</tr>
			<tr>

				<td colspan="6" align="center"><dt:button id="queryBaseInfoBtn"
						label="查询" type="ok" icon="" /> <dt:button id="resetBaseInfoBtn"
						label="重置" type="cancel" icon="" /> <dt:button
						id="addBaseInfoBtn" label="新增" type="ok" icon="" /> <dt:button
						id="delBaseInfoBtn" label="删除" type="ok" icon="" /></td>
			</tr>
		</table>
		<dt:grid id="baseInfoResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="300px" onSelectRow="status">
			<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="name" label="名称"></dt:gridColumn>
			<%-- <dt:gridColumn name="regionId" label="行政区"></dt:gridColumn> --%>
			<dt:gridColumn name="state" label="状态" formatter="在用"></dt:gridColumn>
			<dt:gridColumn name="ctime" label="创建时间"></dt:gridColumn>
			<dt:gridColumn name="opretion" label="操作" formatter="operFormat"
				align="center"></dt:gridColumn>
		</dt:grid>
	</div>
</body>
</html>