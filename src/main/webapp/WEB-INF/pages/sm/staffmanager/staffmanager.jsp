<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="dt" uri="/dttag"%>
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
		<title>员工管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript" src="<%=path%>/js/func.depttree.js"></script>
		<script type="text/javascript" src="<%=path%>/js/func.staffmanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body class="in-body">
	<div class="tree-left" style="width:300px;">
		<table class="query-form" width="100%">
			<caption>查询条件</caption>
			<tr>
				<th>员工名称</th>
				<td><dt:input id="query_staffName" height="25px" width="150px"/>
				</td>
			</tr>
			<tr>
				<th>所属客户</th>
				<td><dt:input id="query_dptName" height="25px" width="150px"/>
				</td>
			</tr>
		</table>
		<div class="query-form-btns user-btns">
			<dt:button id="queryStaffListBtn" label="查询" click="queryStaffList" />
			<func-tags:funcItemAuth funcItemId="1010020101">
				<dt:button id="addStaffBtn" label="添加" click="openAddStaff" />
			</func-tags:funcItemAuth>
		</div>
		<dt:grid id="staffQueryResult" jsonRoot="resultList" url="sm/staffManagerAction!getStaffPaging.action" 
				width="300px" height="180px" onSelectRow="getStaffDetail" dataType="json"
					showPager="true" sortable="true">
					<dt:gridColumn name="staffId" hidden="true" key="true"/>
					<dt:gridColumn name="staffCode" width="70" sortable="true" label="员工代码" />
					<dt:gridColumn name="staffName" width="90" sortable="true" label="员工名称" />
					<dt:gridColumn name="sysDept.deptName" width="120" sortable="true" label="所属客户" />
		</dt:grid>
	</div>
	<div class="con-right" style="margin-left:315px;">
			<dt:tabs id="staffManager">
				<dt:tabsheet label="员工信息" id="staff_tab">&nbsp;</dt:tabsheet>
				<dt:tabsheet label="用户信息" id="staff_user_tab">&nbsp;</dt:tabsheet>
			</dt:tabs>
	</div>
	<input type="hidden" id="staffIdHid" value="" />
</body>
</html>