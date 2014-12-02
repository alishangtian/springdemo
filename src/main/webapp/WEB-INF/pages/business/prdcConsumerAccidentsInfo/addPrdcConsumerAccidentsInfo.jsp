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
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.produceWorksInfomanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
		<style>
			.input_dx{
			width:176px;height:30px;
			} 
			
		</style>
	</head>
	<body>
		<form action="busi/produceWorksInfoManager!saveProduceWorksInfo.action" id="addProduceWorksInfoForm" method="post">
		<table class="query-form" width="100%">
		<caption>消费安全事故录入</caption>
			<tr>
				<th >温室：</th>
				<td >
					<dt:input  id="houseId"  width="176px" height="30px" value="${houseId }"  name="pcaQB.houseId"></dt:input>
					<dt:input  id="houseName" name="houseNmae" value="${houseName }" />
				</td>
				<th >种植季：</th>
				<td >
				<dt:comboBox id="crops" url="busi/prdcConAcciAction!listPrdSea.action"></dt:comboBox>
				<%-- <dt:comboBox id="seasonSelect" height="28px" keyField="id" data="${listPrdSea }" 
				valueField="crops" name="pcaQB.crops" hasNull="true" selectIndex="-1"></dt:comboBox>
					<dt:input  id="produceSeasonId"  width="176px" height="30px"  name="pcaQB.produceSeasonId"></dt:input> --%>
				</td>
			</tr>
			<tr>
				<th>报告人：</th>
				<td >
					<dt:input  id="complainant"  width="176px" height="30px"   name="pcaQB.complainant"></dt:input>
				</td>
				<th>报告人电话：</th>
				<td  >
					<dt:input id="phone"  width="176px" height="30px"   name="pcaQB.phone"></dt:input>
				</td>
			</tr>
			<tr>
				<th>邮箱：</th>
				<td >
					<dt:input id="email"  width="176px" height="30px"  name="pcaQB.email"></dt:input>
				</td>
				<th>事故时间：</th>
				<td  >
					<dt:dateInput id="endTime" name="pcaQB.accidentsDate"  width="176px" height="30px"  showTime="false" showOnbutton="true"  />
				</td>
			</tr>
			<tr>
				<th>描述：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="detail" name="pcaQB.detail"></textarea>
				</td>
			</tr>
		</table>
		</form>
		<div class="query-form-btns">
			<dt:button id="saveProduceWorksInfoBtn" label="确定" type="ok"/>
		</div>
</body>
</html>