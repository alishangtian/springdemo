<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>登录日志查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<%=contextPath%>/css/func.css" type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css" type="text/css" charset="utf-8" />
	<dt:comppath include="combox,input,date,grid,dialog,button,message"></dt:comppath>
	<%-- <script type="text/javascript" src="<%=contextPath%>/js/dt.datatag.js"></script> --%>
	<script type="text/javascript" src="<%=contextPath%>/js/jquery.ui.datatag.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/func.loginlogmanager.js"></script>
</head>
<body>
	<s:form id="loginLogQueryForm">
		<table width="100%" align="center" class="condition">
			<caption>
				<span>查询</span>
			</caption>
			<tr>
				<th width="10%">员工姓名</th>
				<td width="23%">
					<dt:input id="userName" name="pqb.queryBean.userName" />
				</td>
				<th width="10%">登录名称</th>
				<td width="23%">
					<dt:input id="loginName" name="pqb.queryBean.loginName" />
				</td>
				<th width="10%">登录IP</th>
				<td width="23%">
					<dt:input id="loginIp" name="pqb.queryBean.loginIp" />
				</td>
			</tr>
			<tr>
				<th width="10%">开始时间</th>
				<td width="23%">
					<dt:dateInput id="startTime" name="pqb.queryBean.startTime"  showTime="true" showOnbutton="true" onClose='startTimeClose()'/>
				</td>
				<th width="10%">结束时间</th>
				<td width="23%">
					<dt:dateInput id="endTime" name="pqb.queryBean.endTime"  showTime="true" showOnbutton="true"/>
				</td>
				<th width="10%">&nbsp;</th>
				<td width="23%">&nbsp;</td>
			</tr>
			<!-- 日志查询操作 -->
			<func-tags:funcItemAuth funcItemId="1040010201">
				<tr>
					<td align="center" colspan="6">
						<dt:button id="btnQuery" label="查询" click="queryBtnClick" />
					</td>
				</tr>
			</func-tags:funcItemAuth>
		</table>
	</s:form>
	<br>
	<!-- 日志数据表格 -->
	<dt:grid id="loginLogGrid" showPager="true" rowNum="10" caption="日志列表"
		width="100%" height="250" requestType="post">
		<dt:gridColumn name="loginLogId" width="80" label="日志编号" key="true"></dt:gridColumn>
		<dt:gridColumn name="sessionId" width="160" label="会话标识"></dt:gridColumn>
		<dt:gridColumn name="userName" width="80" label="员工姓名"></dt:gridColumn>
		<dt:gridColumn name="loginName" width="80" label="登录名称"></dt:gridColumn>
		<dt:gridColumn name="loginIp" width="100" label="登录IP"></dt:gridColumn>
		<dt:gridColumn name="loginTime" width="150" label="登入时间"></dt:gridColumn>
		<dt:gridColumn name="logoutTime" width="150" label="登出时间"></dt:gridColumn>
	</dt:grid>
</body>
<script type="text/javascript">
</script>
</html>