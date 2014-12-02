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
<title>施肥编辑</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="<%=contextPath%>/css/busi.common.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.produceWorksInfomanager.js"></script>
</head>
<body>
<div id="topmenu" class="ui-query-topmenu">施肥编辑</div>
	<div class="ui-widget-content func-panel">
	<form action="produceWorksInfoManager!updateProduceWorksInfo.action" id="addProduceWorksInfoForm">
		<table class="condition" width="100%" align="center">
			<dt:input width="100" id="id" inputType="hidden" value="${produceWorksInfo.id}" name="produceWorksInfo.id"></dt:input>
			<tr>
				<th>种植季：</th>
				<td>
					<div>
						<select id="produceSeasonId" name="produceWorksInfo.produceSeasonId">
							<option value="0">种植季一</option>
							<option value="1">种植季二</option>
						</select>
					</div>
				</td>
				
					<th>负责人：</th>
				<td>
					<dt:input width="100" id="userId" inputType="hidden" value="1" name="produceWorksInfo。userId"></dt:input>
					<%= ((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName()%>
				</td>
			</tr>
			<tr>
			<th>开始时间：</th>
				<td>
				<dt:dateInput id="beginTime" name="produceWorksInfo.beginTime"  showTime="true" showOnbutton="true"/>
				</td>
				<th>结束时间：</th>
				<td>
				<dt:dateInput id="endTime" name="produceWorksInfo.endTime"  showTime="true" showOnbutton="true"/>
				</td>
			</tr>
			
			<tr>
			<th>品类：</th>
				<td>
				<div>
						<select id="type" name="produceWorksInfo.type">
							<option value="0">品类一</option>
							<option value="1">品类二</option>
						</select>
					</div>
				</td>
				<th>用量：</th>
				<td>
				<dt:input id="amount" name="produceWorksInfo.amount"  width="100"></dt:input>
				</td>
			</tr>
			<tr>
				<th>其他：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="remark" name="produceWorksInfo.remark"></textarea>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" align="center"><dt:button id="saveProduceWorksInfoBtn" label="确定" type="ok" icon=""/>
				<dt:button id="cancelBtn" label="取消" type="ok" icon=""/>
				</td>
			</tr>
			
		</table>
		</form>
	</div>
</body>
</html>