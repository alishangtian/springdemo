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
		<script type="text/javascript" src="<%=path%>/js/busi.purchaseIntention.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body>
		<form action="busi/produceWorksInfoManager!saveProduceWorksInfo.action" id="addProduceWorksInfoForm" method="post">
		<table class="query-form" width="100%">
		<caption>采购意向</caption>
			<tr>
				<th>基地:</th>
				<td><dt:input id="base"  width="176px" height="30px"   disabled="true" value="大兴农业示范区"></dt:input></td>
				<th>温室:</th>
				<td><dt:input id="house"  width="176px" height="30px"   disabled="true" value="长春温室"></dt:input></td>
			</tr>
			<tr>
				<th>购买种类:</th>
				<td><dt:input id="goods"  width="176px" height="30px"   disabled="true" value="苹果"></dt:input></td>
				<th>数量:</th>
				<td><dt:input id="brand"  width="176px" height="30px"   disabled="true" value="50"></dt:input>（公斤）</td>
			</tr>
			<tr>
				<th>联系电话:</th>
				<td><dt:input id="mobile"  width="176px" height="30px"   disabled="true" value="13332365216"></dt:input></td>
				<th>地址:</th>
				<td><dt:input id="address"  width="176px" height="30px"   disabled="true" value="北京市大兴区广顺桥"></dt:input></td>
			</tr>
			<tr>
				<th>邮箱:</th>
				<td><dt:input id="email"  width="176px" height="30px"   disabled="true" value="daxingWS@yahoo.com.cn"></dt:input></td>
				<th>联系人:</th>
				<td><dt:input id="dealingUser" name="msgAlarm.detail"   width="176px" height="30px"   value='<%=((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName() %>'  disabled="true"></dt:input></td>
			</tr>
			<tr>
				<th>备注:</th>
				<td colspan="3">
					<textarea rows="5" cols="80" id="remark" name="msgAlarm" readonly>大兴农业示范区</textarea>
				</td>
			</tr>
		</table>
		</form>
	<div class="query-form-btns">
			<dt:button id="cancelBtn" label="返回" type="ok" icon=""/>
		</div>
</body>
</html>