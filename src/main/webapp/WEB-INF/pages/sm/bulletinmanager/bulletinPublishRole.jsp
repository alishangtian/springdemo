<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>发布范围角色</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<%-- <script type="text/javascript" src="js/dt.datatag.js"></script> --%>
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>
<style type="text/css">
	.datatag{
		float: left;
		background-color: #A6C9E2;
		margin: 2px;
	}
	.datatag .ui-icon-close{
		padding: 0px;
		margin: 0px;
	}
</style>
<script type="text/javascript">
	$(function() {
		bulletinPublishRoleTag = $("#selectRoleBullPubRangeId").datatag( {
			"data": [],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){}
		});
		//赋值
		if(typeof publishTagRole != 'undefined'){
			bulletinPublishRoleTag.datatag("reset",publishTagRole.getData());
		}
	});
	function setValueBySelectRole(rowid, iRow, iCol, e){
		var rowInfo = selectRoleGrid.dtpaginggrid("getRowData",rowid);
		bulletinPublishRoleTag.datatag("add",{"name": rowInfo.roleName,'id': rowInfo.roleId});
	}
</script>
</head>
<body>
	<dt:grid id="selectRoleGrid" onSelectRow="setValueBySelectRole" jsonRoot="resultList" rowNum="5" url="sm/roleManagerAction!getSysRolePaging.action" dataType="json" showPager="true" sortable="true" width="100%" shrinkToFit="true" height="50%">
			<dt:gridColumn name="roleId" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="roleName" sortable="true"label="角色名称" editable="false"></dt:gridColumn>
	</dt:grid>
	<hr>
	<table width="100%" align="center" class="condition">
		<tr>
			<th width="10%" align="left">发布给：</th>
			<td width="90%" valign="middle">
				<div id="selectRoleBullPubRangeId" style="width: 95%; height: 60px"></div>
			</td>
		</tr>
	</table>
</body>
</html>