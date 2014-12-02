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
<title>设备管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.equipInfomanager.js"></script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<table height="400px" width="100%">
	<tr>
	  	<td style="width: 200px; height: 400px; vertical-align: top; overflow: auto;">
				<dt:tree id="funcInfoTree" width="180px" height="400px"
					dataSource="${baseGreenHouseTreeList}" isSimpleData="true"
					treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
					nameCol="nodeTreeName" onClick="onSelectFuncAuth">
				</dt:tree></td>  
		<td>
		
		<table class="condition" width="100%" align="center">
			<dt:input id="custId" name="custId" inputType="hidden"></dt:input>
			<tr>
				<th>设备名称：</th>
				<td>
				<dt:input id="name" name="name"  ></dt:input>
				</td>
				<th>设备型号：</th>
				<td>
				<dt:input id="model" name="model"  ></dt:input>
				</td>
			  
			</tr>
 
			<tr>
				<td colspan="6" align="center"><dt:button id="queryEquipInfo" label="查询" type="ok" icon=""/>
				<dt:button id="resetEquipInfo" label="重置" type="cancel" icon=""/>
				<dt:button id="addEquipInfo" label="新增" type="ok" icon=""/>
				</td>
			</tr>
		</table>
		<dt:grid id="equipInfoResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="300px" onSelectRow="status">
			<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="deviceId" label="物理设备ID"></dt:gridColumn>
			<dt:gridColumn name="name" label="设备名称"></dt:gridColumn>
			<dt:gridColumn name="model" label="设备型号"></dt:gridColumn>
			<dt:gridColumn name="state" label="运行状态"></dt:gridColumn>
			<dt:gridColumn name="type" label="设备类型"></dt:gridColumn>
			<dt:gridColumn name="factory" label="设备厂家"></dt:gridColumn>
			<dt:gridColumn name="remark" label="备注"></dt:gridColumn>
			<dt:gridColumn name="opretion" label="操作" formatter="operFormat" align="center"></dt:gridColumn>
		</dt:grid>
		</td>
		</tr>
		</table>
	</div>
</body>
</html>