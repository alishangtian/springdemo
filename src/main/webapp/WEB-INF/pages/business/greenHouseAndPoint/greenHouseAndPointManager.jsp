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
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>节点管理</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" />
<link rel="stylesheet" href="<%=contextPath%>/css/new.style.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.greenHouseAndPoint.js"></script>
</head>
<body>
	<div style="margin-top: 8px">
		<div class="tree-left">
			<dt:tree id="funcInfoTree" width="180px" height="400px"
				dataSource="${baseGreenHouseTreeList}" isSimpleData="true"
				treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
				nameCol="nodeTreeName" onClick="onSelectFuncAuth">
			</dt:tree>
		</div>
		<div class="con-right">
			<form id="queryForm">
				<table class="query-form">
					<caption>查询条件</caption>
					<tr>
						<th>设备名称：</th>
						<td><dt:input id="equipName" name="equipName" width="176px"
								height="30px"></dt:input>
						<th>选择类型：</th>
						<td><select onclick="setQueryParam()" id="queryType"
							style="width: 176px; height: 30px; border: 1px solid #429aa8"><option
									value="1">可用的</option>
								<option value="0">全部</option>
						</select>
					</tr>
				</table>
			</form>
			<div class="query-form-btns">
				<dt:button id="queryBtn" label="查询" type="ok" />
				<dt:button id="resetBtn" label="重置" type="cancel" />
			</div>
			<dt:input width="100" id="houseId" inputType="hidden"></dt:input>
			<dt:input width="100" id="baseId" inputType="hidden"></dt:input>
			<dt:input width="100" id="equiptype" inputType="hidden"></dt:input>
			<div id="contentPanel">
				<table class="query-form" style="height: 100%">
					<caption id="title_tab">查询结果</caption>
					<tr>
						<td height="50px">
							<div id="contentEquip"></div>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<div class="query-form-btns">
								<dt:button id="saveEquipBtn" label="保存" type="ok" icon="" />
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>