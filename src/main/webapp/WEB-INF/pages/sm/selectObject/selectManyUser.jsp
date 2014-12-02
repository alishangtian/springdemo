<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择多用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<script type="text/javascript" src="js/dt.datatag.js"></script>
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>js/json2.js"></script>
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
	initAlarmFormulaDataTag();	
	/**
	 * 查询用户列表
	 */
	function querySysUserList() {
		var param = {};
		param.loginName = $("#queryLoginName").val();
		param.ownerType = $("#queryOwnerType").val();
		param.ownerName = $("#queryOwnerName").val();
		var url = "sm/sysUserManagerAction!getSysUserPaging.action";
		selectManyUserGrid.dtpaginggrid("option","postData", param);
		selectManyUserGrid.dtpaginggrid("option","mtype", "POST");
		selectManyUserGrid.dtpaginggrid("option","url", url);
		selectManyUserGrid.dtpaginggrid("reload");
	}
	function initAlarmFormulaDataTag(){
		selectObjectDataTag = $("#selectUserObjectDiv").datatag( {
			"data":[],
			"namefield": "staffName",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){}
		});
	}
	//复选框触发事件
	function selectRowClick(){
		//保留其他页已选信息
		var page = selectManyUserGrid.dtpaginggrid("option","page");
		var selectDataArr = [];
		var selectData = selectObjectDataTag.datatag("getData");
		for(var i=0; i<selectData.length; i++){
			var data = selectData[i];
			if(data.page!=page){
				selectDataArr.push(data);
			}
		}
		$("#selectUserObjectDiv").empty();
		selectObjectDataTag.datatag("reset",selectDataArr);
		//设置当前页选择信息
		var selRowArr = selectManyUserGrid.dtpaginggrid("option","selarrrow");
		for(var i=0; i<selRowArr.length; i++){
			var rowData = selectManyUserGrid.dtpaginggrid("getRowData",selRowArr[i]);
			var obj = new Object();
			obj.userId = rowData['userId'];
			obj.loginName = rowData['loginName'];
			obj.staffName = rowData['staff.staffName'];
			obj.page = page;
			selectObjectDataTag.datatag("add",obj);
		}
	}
	//解决分页前面选择框丢失问题
	function selectManyUserGridLoadComplete(){
		var page = selectManyUserGrid.dtpaginggrid("option","page");
		var selectData = selectObjectDataTag.datatag("getData");
		for(var i=0; i<selectData.length; i++){
			var data = selectData[i];
			if(data.page==page){
				selectManyUserGrid.dtpaginggrid("setSelection",data.userId);
			}
		}
	}
	//提供获取数据函数
	function getSelectObjectData(){
		var selectDaraArr = [];
		var selectData = selectObjectDataTag.datatag("getData");
		for(var i=0; i<selectData.length; i++){
			var selectDataObj = new Object();
			selectDataObj.id = selectData[i].loginName;
			selectDataObj.name = selectData[i].staffName;
			selectDaraArr.push(selectDataObj);
		}
		return JSON.stringify(selectDaraArr);
  	}
</script>
</head>
<body>
	<table width="100%" align="center" class="condition">
		<tr>
			<th width="20%" align="left">登录名：</th>
			<td width="15%" valign="middle">
				<dt:input width="100" id="queryLoginName"></dt:input>
			</td>
			<th width="20%" align="left">使用者类型：</th>
			<td width="10%" valign="middle">
				<select id="queryOwnerType" name="queryOwnerType">
						<core-tags:options domainCode="SYS_USER.OWNER_TYPE" />
				</select>
			</td>
			<th width="20%" align="left">使用者名称：</th>
			<td width="15%" valign="middle">
				<dt:input width="100" id="queryOwnerName"></dt:input>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="6">
				<dt:button id="querySysUserListBtn" label="查询" type="ok" click="querySysUserList"></dt:button>
			</td>
		</tr>
		<table width="100%" align="center" class="condition">
			<tr>
				<th width="10%" align="left">已选对象：</th>
				<td width="90%" valign="middle">
					<div id="selectUserObjectDiv" style="width: 95%; height: 60px"></div>
				</td>
			</tr>
		</table>
	</table>
	<dt:grid id="selectManyUserGrid" shrinkToFit="true" rowNum="5" multiSelect="true" onSelectRow="selectRowClick"
		url="sm/sysUserManagerAction!getSysUserPaging.action" onLoadComplete="selectManyUserGridLoadComplete"
		jsonRoot="resultList" dataType="json" showPager="true" sortable="true" width="100%" height="50%">
		<dt:gridColumn name="userId" hidden="true" key="true" ></dt:gridColumn>
		<dt:gridColumn name="loginName" width="90" sortable="true" label="登录名"></dt:gridColumn>
		<dt:gridColumn name="staff.staffName" sortable="true" label="使用者名称"></dt:gridColumn>
	</dt:grid>
</body>
</html>