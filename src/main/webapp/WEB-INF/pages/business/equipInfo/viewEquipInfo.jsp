<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备查看</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/busi.common.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.equipInfomanager.js"></script>

</head>
<body>
	<div id="topmenu" class="ui-query-topmenu">设备查看</div>
	<div class="ui-widget-content func-panel">
		<form action="" id="addBaseGreenHouseInfoForm">
			<table class="condition" width="100%" align="center">
				<tr>
					<th>设备基本信息</th>
				</tr>
				<tr>
					<th>物理设备ID：</th>
					<td><dt:input id="deviceId" disabled="true"
							name="equipInfo.deviceId"></dt:input></td>
					<th>设备名称：</th>
					<td><dt:input id="name" disabled="true" name="equipInfo.name"></dt:input>
					</td>
				<tr>
					<th>设备型号：</th>
					<td><dt:input id="model" disabled="true"
							name="equipInfo.model"></dt:input></td>
					<th>设备类型：</th>
					<td>
						<div>
							<%-- <select id="type" disabled="true" name="equipInfo.type">
								<c:if test="${equipInfo.type==0}">
									<option value="0" selected="selected">网关</option>
									<option value="1">节点</option>
									<option value="2">气象站</option>
								</c:if>
								<c:if test="${equipInfo.type==1}">
									<option value="0">网关</option>
									<option value="1" selected="selected">节点</option>
									<option value="2">气象站</option>
								</c:if>
								<c:if test="${equipInfo.type==2}">
									<option value="0">网关</option>
									<option value="1">节点</option>
									<option value="2" selected="selected">气象站</option>
								</c:if>
							</select> --%>
							<dt:input id="type" disabled="true" name="equipInfo.type"></dt:input>
						</div>
					</td>
				</tr>
				<tr>
					<th>设备厂家：</th>
					<td><dt:input id="factory" disabled="true"
							name="equipInfo.factory"></dt:input></td>
					<th>备注：</th>
					<td><dt:input id="remark" disabled="true"
							name="equipInfo.remark"></dt:input></td>
				</tr>
				<tr>
					<th>设备属性信息</th>
				</tr>
				<c:forEach items="${showValues}" var="sv">
					<tr>
						<th>${sv.name}：</th>
						<td colspan="4"><dt:input disabled="true" id="${sv.id}"
								value="${sv.value}"></dt:input></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4" align="center"><dt:button id="cancelBtn"
							label="返回" type="ok" icon=""></dt:button></td>
				</tr>

			</table>

		</form>

	</div>

</body>
</html>