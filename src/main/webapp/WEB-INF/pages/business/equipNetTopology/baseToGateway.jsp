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
	src="<%=contextPath%>/js/busi.produceWorksInfomanager.js"></script>
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
		<dt:input width="100" id="houseId" inputType="hidden"  name="produceWorksInfo.houseId"></dt:input>
			
			<tr>
				<td colspan="6" align="center"><dt:button id="queryDomainBtn" label="查询" type="ok" icon=""/>
				<dt:button id="resetProduceWorksInfoBtn" label="重置" type="cancel" icon=""/>
				<dt:button id="addProduceWorksInfoBtn" label="新增" type="ok" icon=""/>
				<dt:button id="delProduceWorksInfoBtn" label="删除" type="ok" icon=""/>
				</td>
			</tr>
		</table>
		<dt:grid id="produceWorksInfoResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="300px" onSelectRow="status">
			<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="type" label="品类"></dt:gridColumn>
			<dt:gridColumn name="amount" label="用量"></dt:gridColumn>
			<dt:gridColumn name="userId" label="负责人"></dt:gridColumn>
			<dt:gridColumn name="beginTime" label="开始时间"></dt:gridColumn>
			<dt:gridColumn name="endTime" label="结束时间"></dt:gridColumn>
			<dt:gridColumn name="opretion" label="操作" formatter="operFormat" align="center"></dt:gridColumn>
		</dt:grid>
		</td>
		</tr>
		</table>
	</div>
</body>
</html>