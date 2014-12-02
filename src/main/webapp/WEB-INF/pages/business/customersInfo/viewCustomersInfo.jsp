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
<title>客户查看</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/xmgl.cust.customersInfoManager.js"></script>
</head>
<body>
<!-- <div id="topmenu">客户查看</div> -->
	<div class="ui-widget-content func-panel">
	<form id="createCustomerForm">
		<table class="condition" width="100%" align="center">
		<dt:input id="viewCustomerCustId" name="customer.custId"  value="${customer.custId }" inputType="hidden" disabled="true"/>
		<caption>
				<span>基本信息：</span>
		</caption>
			<tr>
				<th width="10%">客户名称：</th>
				<td width="25%">
					<dt:input id="viewCustomerName" name="customer.name"  value="${customer.name }" disabled="true"/>
				</td>
				<th width="10%">联系人：</th>
				<td width="25%">
					<dt:input id="viewCustomerContacts" name="customer.contacts" value="${customer.contacts }" disabled="true" />
				</td>
			</tr>
			<tr>
				<th>办公电话：</th>
				<td>
					<dt:input id="viewCustomerPhone" name="customer.phone"  inputType="tel" value="${customer.phone }" disabled="true"/>
				</td>
				<th>移动电话：</th>
				<td>
					<dt:input id="viewCustomerMobile" name="customer.mobile"  inputType="mobile" value="${customer.mobile }" disabled="true"/>
				</td>
			</tr>
			<tr>
				<th>电子邮箱：</th>
				<td>
					<dt:input id="viewCustomerEmail" name="customer.email" inputType="mail" value="${customer.email }" disabled="true"/>
				</td>
				<th>主页地址：</th>
				<td>
					<dt:input id="viewCustomerHomePage" name="customer.homePageAddress" inputType="mail" value="${customer.homePageAddress }" disabled="true"/>
				</td>
				
			</tr>
			<tr>
				<th>地址：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="address" name="customer.address"  disabled="true" >${customer.address }</textarea>
				</td>
			</tr>
<!-- 			<tr>
				<th>备注：</th>
				<td colspan="3">
				<textarea rows="3" cols="130" id="remark" name="customer.remark" value="${customer.remark }" disabled="true"></textarea>
				</td>
			</tr> -->
			<tr>
				<td colspan="5" align="center"><dt:button id="cancelBtn4View"  label="取消" type="ok" icon="" />
				</td>
			</tr>
		</table>
		<%-- <table class="condition" width="100%" align="center" >
		<caption>
				<span>数据库信息：</span>
		</caption>
		<tr>
				<th width="10%">IP地址：</th>
				<td width="25%">
					<dt:input id="viewIp" name=""  disabled="true"/>
				</td>
				<th width="10%">数据库名：</th>
				<td width="25%">
					<dt:input id="viewDbName" name=""  disabled="true"/>
				</td>
		</tr>
		<tr>
				<th>用户名：</th>
				<td>
					<dt:input id="viewUserName" name=""  disabled="true"/>
				</td>
				<th>密码：</th>
				<td>
					<dt:input id="viewPassword" name=""  disabled="true"/>
				</td>
			</tr>
			<tr>
				<th>类型：</th>
				<td>
					<div>
						<select id="wzType" name="customer.wzType" disabled="true">
							<option value="0">同表</option>
							<option value="1">同库</option>
							<option value="3">分库</option>
						</select>
					</div>
				</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>	
			<tr>
				<td colspan="4" align="center"><dt:button id="cancelBtn4View"  label="取消" type="ok" icon="" />
				</td>
			</tr>
		</table> --%>
		</form>
	</div>
</body>
</html>