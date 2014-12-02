<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="dt" uri="/dttag"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="t" uri="/cacheTag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<title>用户管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="js/json2.js"></script>
		<script type="text/javascript" src="<%=path%>/js/func.depttree.js"></script>
		<script type="text/javascript" src="<%=path%>/js/func.ui.Auth.js"></script>
		<script type="text/javascript" src="<%=path%>/js/func.sysusermanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body class="in-body">
	<div class="tree-left" style="width:300px;">
		<form id="queryForm">
		<table class="query-form" width="100%">
			<caption>查询条件</caption>
			<tr>
				<th>帐号类型</th>
				<td>
					<t:dmComboBox id="queryOwnerType" prompt="帐号类型" name="sysUserQuery.ownerType" domainCode="SYS_USER.OWNER_TYPE" hasNull="false" selectIndex="0"></t:dmComboBox>
				</td>
			</tr>
			<tr>
				<th>登录名称</th>
				<td><dt:input id="queryLoginName" name="sysUserQuery.loginName" height="25px" width="150px"/>
				</td>
			</tr>
			<tr>
				<th>员工姓名</th>
				<td><dt:input id="queryOwnerName" name="sysUserQuery.ownerName" height="25px" width="150px"/>
				</td>
			</tr>
		</table>
		</form>
		<div class="query-form-btns user-btns">
			<dt:button id="querySysUserListBtn" label="查询" type="ok" click="querySysUserList"/>
			<func-tags:funcItemAuth funcItemId="1020010101">
				<dt:button id="addSysUserBtn" label="添加" click="openAddSysUser"/>
			</func-tags:funcItemAuth>
		</div>
		<dt:grid id="sysUserQueryResult" url="sm/sysUserManagerAction!getSysUserPaging.action?'+$('#queryForm').serialize()+'" onSelectRow="getSysUserDetail"
			jsonRoot="resultList" dataType="json" showPager="true" sortable="true"
			width="100%" height="210px">
			<dt:gridColumn name="userId" hidden="true" key="true" />
			<dt:gridColumn name="loginName" sortable="true"
				label="登录名称" />
			<dt:gridColumn name="userOwnerName" sortable="true"
				label="员工姓名" />
		</dt:grid>
	</div>
	<div class="con-right" style="margin-left:315px;">
		<dt:tabs id="sysUserManager" heightStyle="fill">
			<dt:tabsheet label="用户信息" id="sysUserInfo">&nbsp;</dt:tabsheet>
			<dt:tabsheet label="角色授权" id="sysUserRoleInfo">&nbsp;</dt:tabsheet>
			<dt:tabsheet label="功能授权" id="sysUserFuncInfo">&nbsp;</dt:tabsheet>
			<dt:tabsheet label="权限视图" id="sysUserAuthView">&nbsp;</dt:tabsheet>
		</dt:tabs>
	</div>
	<div style="clear:both"></div>
	<input type="hidden" id="userIdHid" value="" />
</body>
</html>