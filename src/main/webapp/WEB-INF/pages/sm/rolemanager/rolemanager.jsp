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
		<title>角色管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="js/json2.js"></script>
		<script type="text/javascript" src="js/func.depttree.js"></script>
		<script type="text/javascript" src="js/func.ui.Auth.js"></script>
		<script type="text/javascript" src="js/func.rolemanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body class="in-body">
	<div class="tree-left" style="width:300px;">
		<table class="query-form">
			<tr>
				<th>角色名称</th>
				<td><dt:input  height="25px" width="150px" id="query_roleName"></dt:input>
				</td>
			</tr>
		</table>
		<div class="query-form-btns user-btns">
			<dt:button id="queryRoleListBtn" label="查询" type="ok" click="queryRoleList"></dt:button>
			<func-tags:funcItemAuth funcItemId="1020020101">
				<dt:button id="addRoleBtn" label="添加" click="openAddSysRolePage"></dt:button>
			</func-tags:funcItemAuth></td>
		</div>
		<dt:grid id="sysRoleQueryResult" onSelectRow="getSysRoleDetail" url="sm/roleManagerAction!getSysRolePaging.action?roleName='+$('#query_roleName').val()+'"
			jsonRoot="resultList" dataType="json" showPager="true" sortable="true" width="100%" shrinkToFit="true" height="100%">
			<dt:gridColumn name="roleId" hidden="true" key="true" ></dt:gridColumn>
			<dt:gridColumn name="roleName" sortable="true"
				label="角色名称"></dt:gridColumn>
		</dt:grid>
	</div>
		
	<div class="con-right" style="margin-left:315px;">
		<dt:tabs id="sysRoleManager">
			<dt:tabsheet label="角色信息" id="sysRoleInfo">&nbsp;
			</dt:tabsheet>
			<dt:tabsheet label="功能授权" id="sysRoleFuncInfo">&nbsp;
			</dt:tabsheet>
			<dt:tabsheet label="用户授权" id="sysUserAuthView">&nbsp;
			</dt:tabsheet>
		</dt:tabs>
	</div>
	<input type="hidden" id="roleIdHid" value="" />
</body>
</html>