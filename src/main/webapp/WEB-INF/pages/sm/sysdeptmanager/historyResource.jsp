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
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>部门历史资料</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" type="text/css" charset="utf-8"
	href="<%=request.getContextPath()%>/css/func.css" />
<link rel='stylesheet' type='text/css'
	href='<%=request.getContextPath()%>/css/func.common.css' />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css"
	charset="utf-8" />
<script>
	function queryDeptList() {
		var param = {};
		param.deptName = $("#queryDeptName").val();
		param.deptCode = $("#queryDeptCode").val();
		var url = "sm/sysDeptManagerAction!getSysDeptPagingHistory.action";
		deptQueryResultHistory.dtpaginggrid("option","postData", param);
		deptQueryResultHistory.dtpaginggrid("option","mtype", "POST");
		deptQueryResultHistory.dtpaginggrid("option","url", url);
		deptQueryResultHistory.dtpaginggrid("option","page", 1);
		deptQueryResultHistory.dtpaginggrid("reload");
	}

	function transformLink(cellvalue, options, rowObject) {
		if (cellvalue != null && cellvalue != "") {
			var deptId = rowObject.deptId;
			var deptName = rowObject.deptName;
			cellvalue = "<a href='javascript:void(0);' onclick='getSysUsersInfo("
					+ deptId + ",\"" + deptName + "\");'>" + cellvalue + "</a>";
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	function getSysUsersInfo(deptId, deptName) {
		var param = {};
		param.deptIdHistory = deptId;
		var url = "sm/sysDeptManagerAction!loadDeptUsers.action";
		deptUsersResult.dtpaginggrid("option","postData", param);
		deptUsersResult.dtpaginggrid("option","mtype", "POST");
		deptUsersResult.dtpaginggrid("option","url", url);
		deptUsersResult.dtpaginggrid("reload");
		$("#deptNameSpan").html("[" + deptName + "]");
	}
</script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<div class="ui-widget-header func-header">部门历史资料</div>
		<table class="condition" width="100%" align="center">
			<tr>
				<th>部门名称：</th>
				<td><dt:input width="100" id="queryDeptName"></dt:input>
				</td>
				<th>部门代码：</th>
				<td><dt:input width="100" id="queryDeptCode"></dt:input>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center"><dt:button id="queryDeptListBtn"
						label="查询" click="queryDeptList" />
				</td>
			</tr>
		</table>
		<dt:grid id="deptQueryResultHistory"
			url="sm/sysDeptManagerAction!getSysDeptPagingHistory.action"
			shrinkToFit="true" jsonRoot="resultList" dataType="json"
			showPager="true" width="100%" height="150px">
			<dt:gridColumn name="deptId" hidden="true" key="true" />
			<dt:gridColumn name="deptCode" label="部门代码" />
			<dt:gridColumn name="deptName" label="部门名称" formatter="transformLink" />
			<dt:gridColumn name="postCode" label="邮编" />
			<dt:gridColumn name="telNbr" label="联系电话" />
			<dt:gridColumn name="faxNbr" label="传真号码" />
			<dt:gridColumn name="linkMan" label="联系人" />
			<dt:gridColumn name="emailAddr" label="邮箱" />
			<dt:gridColumn name="webSite" label="部门网址" />
			<dt:gridColumn name="address" label="部门地址" />
			<dt:gridColumn name="remarks" label="备注" />
		</dt:grid>
		<div style="color: red;">说明：点击“部门名称”链接，查看该部门使用过的用户信息。</div>
		<div class="ui-widget-header func-header">
			部门 <span id="deptNameSpan" style="color: red"></span> 使用过的用户
		</div>
		<dt:grid id="deptUsersResult" shrinkToFit="true" jsonRoot="_self"
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