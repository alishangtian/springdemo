<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>系统日志查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<%=contextPath%>/css/func.css" type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css" type="text/css" charset="utf-8" />
	<dt:comppath include="combox,input,date,grid,dialog,button,message"></dt:comppath>
	<%-- <script type="text/javascript" src="<%=contextPath%>/js/dt.datatag.js"></script> --%>
	<script type="text/javascript" src="<%=contextPath%>/js/jquery.ui.datatag.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/func.syslogmanager.js"></script>
</head>
<body>
	<s:form id="sysLogQueryForm">
		<table width="100%" align="center" class="condition">
			<caption>
				<span>查询</span>
			</caption>
			<tr>
				<th width="10%">日志分类</th>
				<td width="23%">
					<select id="logCategory" name="pqb.queryBean.logCategory">
						<!-- 构建"全部"选项 -->
						<core-tags:options domainCode="SYS_LOG.LOG_CATEGORY" optionalValue="all" />
					</select>
				</td>
				<th width="10%">日志级别</th>
				<td width="23%">
					<select id="logLevel" name="pqb.queryBean.logLevel">
						<!-- 构建"全部"选项 -->
						<core-tags:options domainCode="SYS_LOG.LOG_LEVEL" optionalValue="all" />
					</select>
				</td>
				<th width="10%">日志生产者</th>
				<td width="23%">
					<dt:input id="logOperator" name="pqb.queryBean.logOperator" />
				</td>
			</tr>
			<tr>
				<th width="10%">开始时间</th>
				<td width="23%">
					<dt:dateInput id="startTime" name="pqb.queryBean.startTime"  showTime="true" showOnbutton="true" onClose="startTimeClose()" />
				</td>
				<th width="10%">结束时间</th>
				<td width="23%">
					<dt:dateInput id="endTime" name="pqb.queryBean.endTime"  showTime="true" showOnbutton="true" />
				</td>
				<th width="10%">&nbsp;</th>
				<td width="23%">&nbsp;</td>
			</tr>
			<!-- 日志查询操作 -->
			<func-tags:funcItemAuth funcItemId="1040010101">
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
	<dt:grid id="sysLogGrid" showPager="true" rowNum="10" caption="日志列表"
		width="100%" height="250" requestType="post">
		<dt:gridColumn name="entity.sysLogId" width="60" label="日志编号" key="true"></dt:gridColumn>
		<dt:gridColumn name="idMap.logCategory" width="80" label="日志分类"></dt:gridColumn>
		<dt:gridColumn name="idMap.logLevel" width="80" label="日志级别"></dt:gridColumn>
		<dt:gridColumn name="entity.logOperator" width="80" label="日志生产者"></dt:gridColumn>
		<dt:gridColumn name="entity.logTime" width="120" label="日志时间"></dt:gridColumn>
		<dt:gridColumn name="entity.logContent" width="500" label="日志内容"></dt:gridColumn>
	</dt:grid>
</body>
<script type="text/javascript">
</script>
</html>