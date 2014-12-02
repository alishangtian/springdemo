<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<title>种植季管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.prdcSeasonManager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body>
		<div class="tree-left">
			<dt:tree id="funcInfoTree" width="180px" height="400px"
					dataSource="${baseGreenHouseTreeList}" isSimpleData="true"
					treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
					nameCol="nodeTreeName" onClick="onSelectFuncAuth">
			</dt:tree>
		</div>
		<div class="con-right">
			<form id="queryForm">
				<dt:input id="queryHouseId" name="prdcSeasonQB.houseId"  inputType="hidden"/>
				<table class="query-form">
					<caption>查询条件</caption>
					<tr>
						<th>开始时间：</th>
						<td>
							<dt:dateInput id="startTime" name="prdcSeasonQB.beginTime"  showTime="true" showOnbutton="true" width="176px" height="30px"/>
						</td>
						<th>至</th>
						<td> 
							<dt:dateInput id="startTime0" name="prdcSeasonQB.beginTime0"  showTime="true" showOnbutton="true" width="176px" height="30px"/>
						</td>
					</tr>
					<tr>
						<th>结束时间：</th>
						<td>
							<dt:dateInput id="endTime" name="prdcSeasonQB.endTime"  showTime="true" showOnbutton="true" width="176px" height="30px"/>
						</td>
						<th>至</th>
						<td> 
							<dt:dateInput id="endTime0" name="prdcSeasonQB.endTime0"  showTime="true" showOnbutton="true" width="176px" height="30px"/>
						</td>
					</tr>
					<tr>
						<th>种植季名称：</th>
						<td colspan="9">
							<dt:input id="queryPrdcSeasonName" name="prdcSeasonQB.name" width="159px" height="30px"/>
						</td>
					</tr>
				</table>
			</form>
			<div class="query-form-btns">
				<dt:button id="queryBtn" label="查询" type="ok" />
				<dt:button id="resetBtn" label="重置" type="reset" />
				<dt:button id="addBtn" label="新增" type="ok"/>
			</div>
			<dt:grid id="prdcSeasonResult" shrinkToFit="true" multiSelect="false"
				jsonRoot="resultList" dataType="json" showPager="true" width="100%"
				  caption="查询结果:所有温室"
				height="300px" onSelectRow="status" caption="查询结果" shrinkToFit="false" rowNum="8">
				<dt:gridColumn name="name" label="种植季名称" width="80px"></dt:gridColumn>
				<dt:gridColumn name="BDate" label="开始时间" width="121px"></dt:gridColumn>
				<dt:gridColumn name="EDate" label="结束时间" width="121px"></dt:gridColumn>
				<dt:gridColumn name="crops" label="作物" width="121px"></dt:gridColumn>
				<dt:gridColumn name="thresholdInfoName" label="安全生产配置" width="90px"></dt:gridColumn>
				<%-- <dt:gridColumn name="lrr" label="录入人" width="65px"></dt:gridColumn> --%>
				<dt:gridColumn name="CDate" label="录入时间" width="121px"></dt:gridColumn>
				<dt:gridColumn name="opretion" label="操作" formatter="operFormat" align="center" width="105px"></dt:gridColumn>
			</dt:grid>
		</div>
	</body>
</html>