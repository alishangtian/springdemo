<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>组织部门管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="main" />
<dt:comppath></dt:comppath>
<link rel='stylesheet' type='text/css' href="css/func.common.css" />
<link rel='stylesheet' type='text/css' href="css/func.cache.css" />
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/func.depttree.js"></script>
<script type="text/javascript" src="js/func.sysdeptmanager.js"></script>
<link rel="stylesheet" href="css/func.css" type="text/css"
	charset="utf-8" />
</head>
<body>
	<table style="width: 100%;">
		<tr style="vertical-align: top;">
			<td style="width: 300px;"><dt:tabs
					id="sysDeptManager_Query_Tabs" width="300px"
					height="100%; min-height: 420px;">
					<dt:tabsheet label="按部门名称" id="sysDept_Query_By_Name_Tab">
						<table class="condition" style="width: 100%; height: 60px"
							align="center">
							<tr>
								<th>部门名称</th>
								<td><dt:input id="query_DeptName" required="true"
										prompt="部门名称" />
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<dt:button id="querySysDeptListBtn" label="查询" click="querySysDeptList" /> 
									<func-tags:funcItemAuth funcItemId="1010010101">
										<dt:button id="addSysDeptBtn" label="添加" click="openAddSysDept" />
									</func-tags:funcItemAuth>
								</td>
							</tr>
						</table>
						<dt:grid id="sysDeptQueryResult"
							url="sm/sysDeptManagerAction!getSysDeptPaging.action"
							jsonRoot="resultList" onSelectRow="getSysDeptDetail"
							dataType="json" showPager="true" sortable="true" height="240px"
							width="100%" shrinkToFit="true">
							<dt:gridColumn name="deptId" hidden="true" key="true" />
							<dt:gridColumn name="deptName" sortable="true" label="部门名称" />
							<dt:gridColumn name="deptCode" sortable="true" width="100"
								label="部门代码" />
						</dt:grid>
					</dt:tabsheet>
					<dt:tabsheet label="按部门结构" id="sysDept_Query_Tree_Tab">
						<div id="query_SysDeptTree" style="width: 95%; height: 356px"></div>
					</dt:tabsheet>
				</dt:tabs>
			</td>
			<td style="min-width: 600px;">
				<dt:tabs id="sysDeptManager" height="100%; min-height: 420px;" width="100%;">
					<dt:tabsheet label="部门信息" id="sysDeptInfo">&nbsp;</dt:tabsheet>
					<dt:tabsheet label="部门员工" id="sysDeptStaffInfo">&nbsp;</dt:tabsheet>
					<dt:tabsheet label="部门用户" id="sysDeptUserInfo">&nbsp;</dt:tabsheet>
				</dt:tabs>
			</td>
		</tr>
	</table>
	<input type="hidden" id="deptIdHid" value="" />
</body>
</html>