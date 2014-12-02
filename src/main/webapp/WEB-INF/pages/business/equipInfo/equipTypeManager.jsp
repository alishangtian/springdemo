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
<title>主页</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" />
<link rel="stylesheet" href="<%=contextPath%>/css/new.style.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.equipTypemanager.js"></script>
</head>
<body>
	<div class="tree-left">
		<dt:tree id="funcInfoTree" width="180px" height="400px"
			dataSource="${etts}" isSimpleData="true" treeNodeKey="nodeTreeId"
			treeNodeParentKey="parentId" nameCol="nodeTreeName"
			onClick="onSelectFuncAuth">
		</dt:tree>
	</div>
	<div class="con-right">
		<dt:input id="custId" name="custId" inputType="hidden"></dt:input>
		<dt:input id="tl" name="tl" inputType="hidden"></dt:input>
		<dt:input id="tpd" name="tpd" inputType="hidden"></dt:input>
		<div class="query-form-btns" style="align: center">
			<dt:button id="addEquipType" label="新增设备类型" type="ok" icon="" />
		</div>
		<dt:grid id="equipTypeResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="300px" onSelectRow="status" caption="结果列表">
			<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="type" label="设备类型"></dt:gridColumn>
			<dt:gridColumn name="description" label="设备描述"></dt:gridColumn>
			<dt:gridColumn name="opretion" label="操作" formatter="operFormat"
				align="center"></dt:gridColumn>
		</dt:grid>
	</div>
</body>
</html>