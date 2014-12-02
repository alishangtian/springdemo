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
		<script type="text/javascript" src="<%=path%>/js/busi.insercticidesWorksInfomanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body>
		<form action="busi/insercticidesWorksInfoAction!updateInsercticidesWorksInfo.action" id="addInsercticidesWorksInfoForm" method="post">
		<table class="query-form" width="100%">
		<caption>用药编辑</caption>
			<dt:input width="100" id="id" inputType="hidden" value="${insercticidesWorksInfo.id}" name="insercticidesWorksInfo.id"></dt:input>
			<tr>
				<th>种植季：</th>
				<td>
					<dt:comboBox datasource="${produceSeasonListajax }"id="produceSeasonId" height="28px" keyField="id" valueField="name" name="insercticidesWorksInfo.produceSeasonId" hasNull="true" selectValue="${insercticidesWorksInfo.produceSeasonId}"></dt:comboBox>
						
						
				</td>
				
					<th>负责人：</th>
				<td>
					<%=((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName() %>
					<dt:input id="userId" name="insercticidesWorksInfo.userId" value='<%=((SysUser) request.getSession().getAttribute("LOGIN_USER")).getUserId()+"" %>' inputType="hidden"></dt:input>
					
				</td>
			</tr>
			<tr>
			<th>开始时间：</th>
				<td>
				<dt:dateInput id="beginTime" name="insercticidesWorksInfo.beginTime"  showTime="true" showOnbutton="true"  width="176px" height="30px"/>
				</td>
				<th>结束时间：</th>
				<td>
				<dt:dateInput id="endTime" name="insercticidesWorksInfo.endTime"  showTime="true" showOnbutton="true"  width="176px" height="30px"/>
				</td>
			</tr>
			
			<tr>
			<th>品类：</th>
				<td>
				<div>
						
						<dt:comboBox datasource="${domainValues }" id="prdcSeasion" height="28px" keyField="valueCode" valueField="valueName" name="insercticidesWorksInfo.type" hasNull="true" selectValue="${insercticidesWorksInfo.type}"></dt:comboBox>
					</div>
				</td>
				<th>用量：</th>
				<td>
				<dt:input id="amount" name="insercticidesWorksInfo.amount"  width="100"  width="176px" height="30px"></dt:input>（克）
				</td>
			</tr>
			<tr>
				<th>其他：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="remark" name="insercticidesWorksInfo.remark">${insercticidesWorksInfo.remark }</textarea>
				</td>
			</tr>

		</table>
		</form>
	<div class="query-form-btns">
			<dt:button id="saveInsercticidesWorksInfoBtn" label="确定" type="ok" icon=""/>
				<dt:button id="cancelBtn" label="取消" type="ok" icon=""/>
		</div>
</body>
</html>