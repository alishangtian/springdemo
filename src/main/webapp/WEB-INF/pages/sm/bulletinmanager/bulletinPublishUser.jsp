<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>发布范围用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css"
	charset="utf-8" />
<%-- <script type="text/javascript" src="js/dt.datatag.js"></script> --%>
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>
<style type="text/css">
.datatag {
	float: left;
	background-color: #A6C9E2;
	margin: 2px;
}

.datatag .ui-icon-close {
	padding: 0px;
	margin: 0px;
}
</style>
<script type="text/javascript">
	/**
	 * 查询用户列表
	 */
	function querySysUserList() {
		var param = {};
		param.loginName = $("#queryLoginName").val();
		param.ownerType = $("#queryOwnerType").val();
		param.ownerName = $("#queryOwnerName").val();
		var url = "sm/sysUserManagerAction!getSysUserPaging.action";
		selectUserGrid.dtpaginggrid("option","postData", param);
		selectUserGrid.dtpaginggrid("option","mtype", "POST");
		selectUserGrid.dtpaginggrid("option","url", url);
		selectUserGrid.dtpaginggrid("reload");
	}
	function setValueBySelectUser(rowid, iRow, iCol, e) {
		var rowInfo = selectUserGrid.dtpaginggrid("getRowData",rowid);
		bulletinPublishUserTag.datatag("add",{
			"name" : rowInfo['staff.staffName'],
			'id' : rowInfo.userId
		});
	}
	$(function() {
		bulletinPublishUserTag = $("#selectUserBullPubRangeId").datatag({
		//bulletinPublishUserTag = dt$.datatag("#selectUserBullPubRangeId", {
			"data" : [],
			"namefield" : "name",
			"classname" : "datatag",
			"width" : null,
			"onclose" : function(data) {
			}
		});
		//赋值
		if (typeof publishTagUser != 'undefined') {
			//bulletinPublishUserTag.reset(publishTagUser.getData());
			bulletinPublishUserTag.datatag('reset', publishTagUser.datatag("getData"));
		}
		//querySysUserList();
	});
</script>
</head>
<body>
	<table width="100%" align="center" class="condition">
		<tr>
			<th width="20%" align="left">登录名：</th>
			<td width="15%" valign="middle"><dt:input width="100"
					id="queryLoginName"></dt:input></td>
			<th width="20%" align="left">使用者类型：</th>
			<td width="10%" valign="middle"><select id="queryOwnerType"
				name="queryOwnerType">
					<core-tags:options domainCode="SYS_USER.OWNER_TYPE" />
			</select></td>
			<th width="20%" align="left">使用者名称：</th>
			<td width="15%" valign="middle"><dt:input width="100"
					id="queryOwnerName"></dt:input></td>
		</tr>
		<tr>
			<td align="center" colspan="6"><dt:button
					id="querySysUserListBtn" label="查询" type="ok"
					click="querySysUserList"></dt:button></td>
		</tr>
	</table>
	<dt:grid id="selectUserGrid" shrinkToFit="true" rowNum="5"
		url="sm/sysUserManagerAction!getSysUserPaging.action"
		onSelectRow="setValueBySelectUser" jsonRoot="resultList"
		dataType="json" showPager="true" sortable="true" width="100%"
		height="50%">
		<dt:gridColumn name="userId" hidden="true" key="true"></dt:gridColumn>
		<dt:gridColumn name="loginName" width="90" sortable="true" label="登录名"></dt:gridColumn>
		<dt:gridColumn name="staff.staffName" sortable="true" label="使用者名称"></dt:gridColumn>
	</dt:grid>
	<hr>
	<table width="100%" align="center" class="condition">
		<tr>
			<th width="10%" align="left">发布给：</th>
			<td width="90%" valign="middle">
				<div id="selectUserBullPubRangeId" style="width: 95%; height: 60px"></div>
			</td>
		</tr>
	</table>

</body>

</html>