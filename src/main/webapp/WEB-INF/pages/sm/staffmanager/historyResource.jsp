<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工历史资料</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" type="text/css" charset="utf-8"
	href="<%=request.getContextPath()%>/css/func.css" />
<link rel='stylesheet' type='text/css'
	href='<%=request.getContextPath()%>/css/func.common.css' />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css"
	charset="utf-8" />
<script>
	function queryStaffList() {
		var param = {};
		param.staffName = $("#queryStaffName").val();
		param.staffCode = $("#queryStaffCode").val();
		var url = "sm/staffManagerAction!getStaffPagingHistory.action";
		staffQueryResultHistory.dtpaginggrid("option","postData", param);
		staffQueryResultHistory.dtpaginggrid("option","mtype", "POST");
		staffQueryResultHistory.dtpaginggrid("option","url", url);
		staffQueryResultHistory.dtpaginggrid("option","page", 1);
		staffQueryResultHistory.dtpaginggrid("reload");
	}

	function transformLink(cellvalue, options, rowObject) {
		if (cellvalue != null && cellvalue != "") {
			var staffId = rowObject.staffId;
			var staffName = rowObject.staffName;
			cellvalue = "<a href='javascript:void(0);' onclick='getSysUsersInfo("
					+ staffId
					+ ",\""
					+ staffName
					+ "\");'>"
					+ cellvalue
					+ "</a>";
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	function getSysUsersInfo(staffId, staffName) {
		var param = {};
		param.staffIdHistory = staffId;
		var url = "sm/staffManagerAction!loadStaffUsers.action";
		staffUsersResult.dtpaginggrid("option","postData", param);
		staffUsersResult.dtpaginggrid("option","mtype", "POST");
		staffUsersResult.dtpaginggrid("option","url", url);
		staffUsersResult.dtpaginggrid("reload");
		$("#staffNameSpan").html("[" + staffName + "]");
	}
</script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<div class="ui-widget-header func-header">员工历史资料</div>
		<table class="condition" width="100%" align="center">
			<tr>
				<th>员工名称</th>
				<td><dt:input width="100" id="queryStaffName"></dt:input></td>
				<th>员工代码</th>
				<td><dt:input width="100" id="queryStaffCode"></dt:input></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><dt:button
						id="queryStaffListBtn" label="查询" click="queryStaffList" /></td>
			</tr>
		</table>
		<dt:grid id="staffQueryResultHistory"
			url="sm/staffManagerAction!getStaffPagingHistory.action"
			shrinkToFit="true" jsonRoot="resultList" dataType="json"
			showPager="true" width="100%" height="150px">
			<dt:gridColumn name="staffId" hidden="true" key="true" />
			<dt:gridColumn name="staffCode" label="员工代码" />
			<dt:gridColumn name="staffName" label="员工名称"
				formatter="transformLink" />
			<dt:gridColumn name="sysDept.deptName" label="所属部门" />
			<dt:gridColumn name="mobileNbr" label="移动电话" />
			<dt:gridColumn name="fixNbr" label="固定电话" />
			<dt:gridColumn name="email" label="电子邮箱" />
		</dt:grid>
		<div style="color: red;">说明：点击“员工名称”链接，查看该员工使用过的用户信息。</div>
		<div class="ui-widget-header func-header">
			员工
			<span id="staffNameSpan" style="color: red"></span>
			使用过的用户
		</div>
		<dt:grid id="staffUsersResult" shrinkToFit="true" jsonRoot="_self"
			dataType="json" showPager="false" width="100%" height="150px">
			<dt:gridColumn name="userId" label="用户编号" />
			<dt:gridColumn name="loginName" label="登录名称" />
			<dt:gridColumn name="userExpTime" label="用户失效时间" />
			<dt:gridColumn name="pwdChgTime" label="密码设置时间" />
			<dt:gridColumn name="pwdExpTime" label="密码失效时间" />
		</dt:grid>
	</div>
</body>
</html>