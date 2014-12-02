<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>生产事故监测</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="<%=contextPath%>/css/new.style.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.produceAccidentManager.js"></script>
</head>
<body style="margin-top: 8px; width: 970px; height: 450px">
	<div class="tree-left">
		<dt:tree id="funcInfoTree" width="180px" height="400px"
			dataSource="${baseAndHouseTree}" isSimpleData="true"
			treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
			nameCol="nodeTreeName" onClick="onSelectFuncAuth">
		</dt:tree>
	</div>
	<div class="con-right">
		<form id="queryForm">
			<dt:input id="houseId" name="produceAccident.houseId" inputType="hidden" />
			<table class="query-form">
				<caption>查询条件</caption>
				<tr>
					<th>发生时间：</th>
					<td><dt:dateInput id="startTime" name="produceAccident.startTime"
							showTime="true" showOnbutton="true" width="176px" height="30px" />
					</td>
					<th>至</th>
					<td><dt:dateInput id="endTime" name="produceAccident.endTime" showTime="true"
							showOnbutton="true" width="176px" height="30px" /></td>
				</tr>
				<tr>
					<th>事故描述：</th>
					<td colspan="9"><dt:input id="detail" name="produceAccident.detail"
							width="159px" height="30px" /></td>
				</tr>
			</table>
		</form>
		<div class="query-form-btns">
			<dt:button id="queryBtn" label="查询" type="ok" />
			<dt:button id="resetBtn" label="重置" type="reset" />
			<dt:button id="addBtn" label="新增" type="ok" />
		</div>
		<dt:grid id="produceAccidents" shrinkToFit="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="170px" onSelectRow="status" caption="查询结果"
			shrinkToFit="false">
			<dt:gridColumn name="detail" label="描述" width="80px"></dt:gridColumn>
			<dt:gridColumn name="occurDate" label="发生时间" width="121px"></dt:gridColumn>
			<dt:gridColumn name="createDate" label="记录时间" width="90px"></dt:gridColumn>
			<dt:gridColumn name="state" label="状态" width="90px"></dt:gridColumn>
			<dt:gridColumn name="opretion" label="操作" formatter="operFormat"
				align="center" width="105px"></dt:gridColumn>
		</dt:grid>
	</div>
</body>
</html>