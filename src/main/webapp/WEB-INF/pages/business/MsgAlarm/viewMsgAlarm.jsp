<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
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
	</head>
	<body>
		<form action="busi/produceWorksInfoManager!saveProduceWorksInfo.action" id="addProduceWorksInfoForm" method="post">
		<table class="query-form">
			<caption>告警详情:</caption>
			<tr>
				<th>告警类型:</th>
				<td><dt:input id="alarmTypeName" name="msgAlarm.alarmTypeName"   width="176px" height="30px"   disabled="true"></dt:input></td>
				<th>设备名称:</th>
				<td><dt:input id="sensorName" name="msgAlarm.sensorName"   width="176px" height="30px"   disabled="true"></dt:input></td>
			</tr>
			<tr>
				<th>温室名称:</th>
				<td><dt:input id="houseName" name="msgAlarm.houseName"   width="176px" height="30px"   disabled="true"></dt:input></td>
				<th>设备状态类型:</th>
				<td><dt:input id="equipStateTypeName" name="msgAlarm.equipStateTypeName"   width="176px" height="30px"   disabled="true"></dt:input></td>
			</tr>
			<tr>
				<th>告警级别:</th>
				<td><dt:input id="alarmLevelName" name="msgAlarm.alarmLevelName"   width="176px" height="30px"   disabled="true"></dt:input></td>
				<th>处理人:</th>
				<td>
					<dt:input id="dealingUser" name="msgAlarm.detail"   width="176px" height="30px"   value='<%=((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName() %>'  disabled="true"></dt:input>
				</td>
			</tr>
			<tr>
				<th>发生时间:</th>
				<td><dt:dateInput id="time" name="msgAlarm.time"  showTime="true" showOnbutton="true" readonly="false"  width="176px" height="30px"  showOnbutton="false" /></td>
				<th>清除时间:</th>
				<td><dt:dateInput id="ctime" name="msgAlarm.ctime"  showTime="true" showOnbutton="true" readonly="false"  width="176px" height="30px" showOnbutton="false" /></td>
			</tr>
			<tr>
				<th>详情:</th>
				<td colspan="3"><dt:input id="detail" name="msgAlarm.detail"   width="306px" height="30px"   disabled="true"></dt:input></td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3">
					<textarea rows="5" cols="80" id="remark" name="msgAlarm" readonly>${msgAlarm.remark }</textarea>
				</td>
			</tr>
		</table>
		</form>
	<div class="query-form-btns">
			<dt:button id="cancelBtn" label="返回" type="ok" icon=""/>
		</div>
</body>
</html>