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
		<title>施肥管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.produceWorksInfomanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body>
		<div class="tree-left" style="margin-left:2px">
			<%@ include file="/WEB-INF/pages/common/zyTree.jsp"%>
		</div>
		<div class="con-right">
			<form id="queryForm">
				<table class="query-form">
					<caption>查询条件</caption>
					<tr>
						<th>种植季：</th>
						<td>
							<dt:comboBox id="seasonSelect" height="28px" keyField="id" valueField="name" name="pwQB.produceSeasonId" hasNull="true" selectIndex="-1"></dt:comboBox>
							
						</td>
						<th>开始时间：</th>
						<td>
							<dt:dateInput id="queryStartTime" name="pwQB.beginTime" width="176px" height="30px" showTime="true" showOnbutton="true"/>
						</td>
						<th>结束时间：</th>
						<td>
							<dt:dateInput id="queryEndTime" name="pwQB.endTime"  width="176px" height="30px" showTime="true" showOnbutton="true"/>
						</td>
					</tr>
					<input name="pwQB.houseId"  id="houseId"  type="hidden"/>
				</table>
			</form>
			<div class="query-form-btns">
				<dt:button id="queryDomainBtn" label="查询" type="ok"/>
				<dt:button id="resetProduceWorksInfoBtn" label="重置" type="cancel"/>
				<dt:button id="addProduceWorksInfoBtn" label="新增" type="ok"/>
				<%-- <dt:button id="delProduceWorksInfoBtn" label="删除" type="ok"/> --%>
			</div>
			<dt:grid id="produceWorksInfoResult" shrinkToFit="true" multiSelect="false"
				
				jsonRoot="resultList" dataType="json" showPager="true" width="100%" caption="查询结果:所有温室"
				height="300px" onSelectRow="status" shrinkToFit="false" rowNum="8">
				<dt:gridColumn name="id" hidden="true" key="true" width="10px"></dt:gridColumn>	
				<dt:gridColumn name="typeName" label="品类"  width="90px"></dt:gridColumn>
				<dt:gridColumn name="amount" label="用量"  width="70px"></dt:gridColumn>
				<dt:gridColumn name="userName" label="负责人"  width="70px"></dt:gridColumn>
				<dt:gridColumn name="beginTime" label="开始时间"  width="150px"></dt:gridColumn>
				<dt:gridColumn name="endTime" label="结束时间"  width="150px"></dt:gridColumn>
				<dt:gridColumn name="opretion" label="操作" formatter="operFormat" align="center"  width="150px"></dt:gridColumn>
			</dt:grid>
	
		</div>
	</body>
</html>