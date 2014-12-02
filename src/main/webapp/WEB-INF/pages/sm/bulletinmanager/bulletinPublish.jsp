<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告发布信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
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
		$("#bulletinPublishCancel").click(function(){
			bulletinCreateDialog.close();
		});
	});
	function selectDeptDialog(){
		var url = 'sm/BulletinAction!prepareDept.action';
		bulletinPublishDeptDialog = $.dtdialog.showModal({title: '发布范围>>部门',
		width:500,
		height:300,
		url:  url,
		buttons:{
			'确定' : function() {
				publishTagDept = $("#publishDeptDiv").datatag( {
					"data":[],
					"namefield": "name",
					"classname": "datatag",
					"width": null,
					"onclose": function(data){}
				});
				var retValue = bulletinPublishDeptTag.datatag("getData");
				publishTagDept.datatag("reset",retValue);
				$.dtdialog.closeDialog('#selectDeptBullPubRangeId', {retValue:bulletinPublishDeptTag.datatag("getData")});
			},
			'取消' : function() {
				$(this).dtdialog("close");
			}
		},
		open:function() {},
		close: function() {}
		});
	}
	function selectRoleDialog(){
		var url = 'sm/BulletinAction!prepareRole.action';
		bulletinPublishRoleDialog = $.dtdialog.showModal({title: '发布范围>>角色',
		width:500,
		height:350,
		url:  url,
		buttons:{
			'确定' : function() {
				publishTagRole = $("#publishRoleDiv").datatag( {
					"data":[],
					"namefield": "name",
					"classname": "datatag",
					"width": null,
					"onclose": function(data){}
				});
				var retValue = bulletinPublishRoleTag.datatag("getData");
				publishTagRole.datatag("reset",retValue);
				$.dtdialog.closeDialog('#selectRoleBullPubRangeId', {retValue:bulletinPublishRoleTag.datatag("getData")});
			},
			'取消' : function() {
				$(this).dtdialog("close");
			}
		},
		open:function() {},
		close: function() {}
		});
	}
	function selectUserDialog(){
		var url = 'sm/BulletinAction!prepareUser.action';
		bulletinPublishUserDialog = $.dtdialog.showModal({title: '发布范围>>用户',
		width:600,
		height:350,
		showButtons:true,
		url:  url,
		buttons:{
			'确定' : function() {
				publishTagUser = $("#publishUserDiv").datatag( {
					"data":[],
					"namefield": "name",
					"classname": "datatag",
					"width": null,
					"onclose": function(data){}
				});
				var retValue = bulletinPublishUserTag.datatag("getData");
				publishTagUser.datatag("reset",retValue);
				$.dtdialog.closeDialog('#selectUserBullPubRangeId', {retValue:bulletinPublishUserTag.datatag("getData")});
			},
			'取消' : function() {
				$(this).dtdialog("close");
			}
		},
		open:function() {},
		close: function() {}
		});
	}
	
</script>
</head>
<body>
	<table width="100%" border="0" cellpadding="2" cellspacing="0">
		<tr>
			<td><table width="100%" align="center" class="condition">
					<s:hidden id="bulletinPublishRangeStr" name="rangeStr"></s:hidden>
					<tr>
						<th width="10%" align="left">发布方式：</th>
						<td width="90%">
							<s:radio list="#{'1000':'页面发布','2000':'邮件发布','3000':'短信发布'}" value="'1000'" id="bullPubMethodId" required="true" name="bulletin.bulletinPublishMethodInst.bullPubMethodId"></s:radio>
						</td>
					</tr>
					<tr>
						<th width="10%" align="left">发布范围：</th>
						<td width="90%" valign="middle">
							<div style="float: left;margin: 4px;"><dt:button  id="selectDeptBtn" label="部门" click="selectDeptDialog"></dt:button></div>
							<div id="publishDeptDiv" style="margin: 4px;float: left;width: 85%;height: 80px;border: 1px solid rgb(204, 204, 204);"></div>
						</td>
					</tr>
					<tr>
						<th width="10%" align="left"></th>
						<td width="90%" valign="middle">
							<div style="float: left;margin: 4px;"><dt:button id="selectRoleBtn" label="角色" click="selectRoleDialog"></dt:button></div>
							<div id="publishRoleDiv" style="margin: 4px;float: left;width: 85%;height: 80px;border: 1px solid rgb(204, 204, 204);"></div>
						</td>
					</tr>
					<tr>
						<th width="10%" align="left"></th>
						<td width="90%" valign="middle">
							<div style="float: left;margin: 4px;"><dt:button id="selectUserBtn" label="用户" click="selectUserDialog"></dt:button></div>
							<div id="publishUserDiv" style="margin: 4px;float: left;width: 85%;height: 80px;border: 1px solid rgb(204, 204, 204);"></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>