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
<title>客户录入</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/busi.common.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/xmgl.cust.customersInfoManager.js"></script>
</head>
<body>

	<div class="ui-widget-content func-panel">
	<table height="400px" width="100%">
		<tr>
			<td
				style="height: 400px; vertical-align: top; border: 1px solid #AAAAAA;">
		<form action="javascript:void(0)" id="createCustomerForm" method="post">
		<table class="condition" width="100%" align="center" style="height: 496px; ">
<%-- 		<dt:input width="100" id="houseId" inputType="hidden"  name="produceWorksInfo.houseId"></dt:input>
 --%>			<tr>
				<th style="width:100px">客户名称：</th>
				<td style="width:100px">
					<dt:input id="castomerName" name="customer.name" />*
					<span id="valiName"></span>
				</td>
				
				
				<th style="width:100px">联系人：</th>
				<td  style="width:100px">
					<dt:input id="queryCustomerContacts" name="customer.contacts" />
				</td>
			</tr>
			<tr>
				<th>办公电话：</th>
				<td>
					<dt:input id="queryCustomerPhone" name="customer.phone"  inputType="tel"/>
					<span id="custPhoneW"></span>
				</td>
				
				<th>移动电话：</th>
				<td>
					<dt:input id="queryCustomerMobile" name="customer.mobile"  inputType="mobile"/>
					<span id="custMob"></span>
				</td>
				
			</tr>
			
			<tr>
			<th>电子邮箱：</th>
				<td>
					<dt:input id="queryCustomerEmail" name="customer.email" inputType="mail"/>
					<span id="custEmailW"></span>
				</td>
				
				<th>主页地址：</th>
				<td>
					<dt:input id="queryCustomerHomePage" name="customer.homePageAddress" inputType="homePageAddress"/>
				</td>
			</tr>
			<tr>
				<th>地址：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="address" name="customer.address" ></textarea>
				</td>
		 
			</tr>
			
			<tr>
				<!-- <th>备注：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="remark" name="customer.remark" ></textarea>
				</td> -->
			</tr>
			<tr>
				<td colspan="4" align="center"><dt:button id="createCustomerBtn" label="确定" type="ok" icon=""/>
				<dt:button id="cancelBtn4Add"  label="取消" type="ok" icon="" />
				</td>
			</tr>
		</table>
		</form>
			</td>
		</tr>
	</table>
	
	</div>
</body>
</html>