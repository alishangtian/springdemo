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
<title>新增拓扑</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="<%=contextPath%>/css/busi.common.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.equipNetTopology.js"></script>
</head>
<body>
<div id="topmenu" class="ui-query-topmenu">添加设备</div>
	<div class="ui-widget-content func-panel">
	<table height="400px" width="100%">
		<tr>
			<td style="width: 200px; height: 400px; vertical-align: top; overflow: auto;">
						<dt:tree id="funcInfoTree" width="180px" height="400px"
							dataSource="${eList}" isSimpleData="true"
							treeNodeKey="id" treeNodeParentKey="parentNodeId"
							nameCol="name" onClick="onSelectFuncAuth" >
						</dt:tree>
					</td>
			<td
				style="height: 400px; vertical-align: top; border: 1px solid #AAAAAA;">
		<form action="equipNetTopologyAction!saveEquipNetTopology.action" id="addEquipNetTopologyForm" method="post">
		<table class="condition" width="100%" align="center">
		<dt:input width="100" id="houseId" inputType="hidden"  name="parentId"></dt:input>
			<tr>
				<th>请选择设备</th>
				<td>
					<div>
						<select name="equipNetTopology.id">
							<s:iterator value="eiList">
								<option value="<s:property value="id"/>"><s:property value="name"/></option>
							</s:iterator>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center"><dt:button id="saveEquipNetTopologyBtn" label="确定" type="ok" icon=""/>
				</td>
			</tr>
			
		</table>
		</form>
			</td>
		</tr>
	</table>
	
	</div>
</body>
</html>