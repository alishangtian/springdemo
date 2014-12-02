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
		<title>基地和网关</title>
		<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
			type="text/css" charset="utf-8" />
		<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
			type="text/css" charset="utf-8" />
		<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
		<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
		<script type="text/javascript"
			src="<%=contextPath%>/js/busi.equipNetTopology.js">
		</script>
	</head>
	<body>
		<div class="ui-widget-content func-panel">
			<table height="400px" width="100%">
				<tr>
					<td style="width: 200px; height: 400px; vertical-align: top; overflow: auto;">
						<dt:tree id="funcInfoTree" width="180px" height="400px"
							dataSource="${eList}" isSimpleData="true"
							treeNodeKey="equipInfoId" treeNodeParentKey="parentNodeId"
							nameCol="name" onClick="onSelectFuncAuth" >
						</dt:tree>
					</td>
					<td>
						<table class="condition" width="100%" align="center">
							<dt:input width="100" id="parentId" inputType="hidden" name="parentId"></dt:input>
							<dt:input width="100" id="id" inputType="hidden" name="id"></dt:input>
							<tr>
								<td colspan="6" align="center"><dt:button id="queryDomainBtn" label="查询" type="ok" icon=""/>
								<dt:button id="resetEquipNetTopologyBtn" label="重置" type="cancel" icon=""/>
								<dt:button id="addEquipNetTopologyBtn" label="新增" type="ok" icon=""/>
								<dt:button id="delEquipNetTopologyBtn" label="删除" type="ok" icon=""/>
								</td>
							</tr>
						</table>
						<dt:grid id="equipNetTopologyResult" shrinkToFit="true" multiSelect="true"
							jsonRoot="resultList" dataType="json" showPager="true" width="100%"
							height="300px" onSelectRow="status">
							<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
							<dt:gridColumn name="name" label="基地"></dt:gridColumn>
							<!--<dt:gridColumn name="base" label="基地"></dt:gridColumn>
							<dt:gridColumn name="gateway" label="网关"></dt:gridColumn>
							<dt:gridColumn name="node" label="节点"></dt:gridColumn>
							<dt:gridColumn name="transducer" label="传感器"></dt:gridColumn>-->
						</dt:grid>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>