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
		<title>客户管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/xmgl.cust.customersInfoManager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>	
	</head>
	<body>
		<div class="tree-left">
			<%@ include file="/WEB-INF/pages/common/zyTree.jsp"%>
		</div>
		
		<div class="con-right">
			<form id="queryForm">
				<table class="query-form">
					<caption>查询条件</caption>
					<tr>
						<th>客户名称：</th>
						<td>
							<dt:input id="queryCustomerName" name="customer.name" />
						</td>
						<th>联系人：</th>
						<td>
							<dt:input id="queryContacts" name="customer.contacts" />
						</td>
						<th>状态：</th>
						<td>
							<div>
								<select id="state" name="customer.state">
								    <option value="">全部</option>
									<option value="0">停用</option>
									<option value="1" selected>启用</option>
								</select>
							</div>
							<dt:comboBox id="state" height="28px" keyField="id" valueField="name" name="customer.state" hasNull="true" selectIndex="-1"></dt:comboBox>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<table class="condition" width="100%" align="center">
		<caption>
				<span>查询</span>
		</caption>
			<tr>
				<th width="10%">客户名称：</th>
				<td width="23%">
					<dt:input id="queryCustomerName" name="customer.name" />
				</td>
				<th width="10%">联系人：</th>
				<td width="23%">
					<dt:input id="queryContacts" name="customer.contacts" />
				</td>
				<th width="10%">状态：</th>
				<td width="23%">
					<div>
						<select id="state" name="customer.state">
						    <option value="">全部</option>
							<option value="0">停用</option>
							<option value="1" selected>启用</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				
				
			</tr>
			<tr>
				<td colspan="6" align="center">
				<dt:button id="queryDomainBtn" label="查询" type="ok" />
				<dt:button id="resetCustomerBtn" label="重置" type="reset" />
				<dt:button id="addCustomersInfoBtn" label="新增" type="ok"/>
				</td>
			</tr>
		</table>
		<dt:grid id="customersInfoResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="300px" onSelectRow="status">
			<dt:gridColumn name="custId" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="state" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="name" label="客户名称"></dt:gridColumn>
			<dt:gridColumn name="uuid" label="客户编码"></dt:gridColumn>
			<dt:gridColumn name="phone" label="手机号"></dt:gridColumn>
			<dt:gridColumn name="contacts" label="联系人"></dt:gridColumn>
			<dt:gridColumn name="ctime" label="创建时间"></dt:gridColumn>
			<dt:gridColumn name="opretion" label="操作" formatter="operFormat" align="center"></dt:gridColumn>
		</dt:grid>
	</div>
	
	
	
</body>
</html>