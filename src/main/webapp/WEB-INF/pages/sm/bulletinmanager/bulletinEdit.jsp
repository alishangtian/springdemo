<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告编辑</title>
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
		$('#bulletinEditCancel').click(function(){
			bulletinEditDialog.dtdialog("close");
		});
		//初始化公告范围
		publishTagDept = $("#publishEditDeptDiv").datatag( {
			"data":[],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){}
		});
		publishTagRole = $("#publishEditRoleDiv").datatag( {
			"data":[],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){}
		});
		publishTagUser = $("#publishEditUserDiv").datatag( {
			"data":[],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){}
		});
		var rangeStr = $("#bulletinViewRangeHidden").val();
		var rangeArr = rangeStr.split('!');
		for(var i=0; i<rangeArr.length; i++){
			var singleRange = rangeArr[i].split('#');
			if(singleRange[0]=='1000'){
				publishTagDept.datatag("reset",eval(singleRange[1]));
			}else if(singleRange[0]=='2000'){
				publishTagRole.datatag("reset",eval(singleRange[1]));
			}else if(singleRange[0]=='3000'){
				publishTagUser.datatag("reset",eval(singleRange[1]));
			}
		}
		startTimeClose();
	});
	function startTimeClose() {
		$("#revokeTimeEdit").dtdatepicker('option','minDate', $("#publishTimeEdit").dtdatepicker('getDate'));
	}
	function selectDeptDialog(){
		var url = 'sm/BulletinAction!prepareDept.action';
		bulletinPublishDeptDialog = $.dtdialog.showModal({title: '发布范围>>部门',
		width:500,
		height:300,
		url:  url,
		buttons:{
			'确定' : function() {
				publishTagDept = $("#publishEditDeptDiv").datatag( {
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
				bulletinPublishDeptDialog.close();
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
		height:300,
		url:  url,
		buttons:{
			'确定' : function() {
				publishTagRole = $("#publishEditRoleDiv").datatag( {
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
		bulletinPublishUserDialog = $.dtdialog.showModalDialog({title: '发布范围>>用户',
		width:600,
		height:350,
		showButtons:true,
		url:  url,
		buttons:{
			'确定' : function() {
				publishTagUser = $("#publishEditUserDiv").datatag( {
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
	<form id="bulletinEditForm" method="post">
		<dt:tabs height="400" id="bulletinEditTab" switchMode ="mouseover" active="0">
			<dt:tabsheet label="基本信息" id="bulletinEditBaseInfoTab">
				<table width="100%" border="0" cellpadding="2" cellspacing="0">
					<tr>
						<td><table width="100%" align="center" class="condition">
								<s:hidden id="bulletinEditBulletinId" name="bulletin.bulletinId"></s:hidden>
								<tr>
									<th width="10%" align="left">公告标题：</th>
									<td width="90%">
										<dt:input id="bulletinEditTitle"   name="bulletin.bulletinTitle" required="true" prompt="公告标题" length="128"></dt:input>
									</td>
								</tr>
								<tr>
									<th width="10%" align="left">公告内容：</th>
									<td width="90%">
										 <dt:ckeditor id="bulletinEditContentStrEdit" name="bulletin.bulletinContentStr" required="true" prompt="公告内容" browseUrl="ckeditorBrowse.action" uploadUrl="ckeditorUpload.action" width="95%" height="300" skin="moono"></dt:ckeditor>
									</td>
								</tr>
								<tr>
									<th width="10%" align="left">发布时间：</th>
									<td width="90%">
										<dt:dateInput id="publishTimeEdit" name="bulletin.publishTime"  required="true" prompt="发布时间" minDate="0d" showTime="true" onClose="startTimeClose()"></dt:dateInput>
									</td>
								</tr>
								<tr>
									<th width="10%" align="left">撤销时间：</th>
									<td width="90%">
										<dt:dateInput id="revokeTimeEdit" name="bulletin.revokeTime"  required="true" prompt="撤销时间" minDate="0d" showTime="true"></dt:dateInput>
									</td>
								</tr>
								<tr>
									<th width="10%" align="left">优先级：</th>
									<td width="90%">
										<s:radio id="priorityEdit" list="#{'X':'一般','Y':'加急','Z':'特急'}" required="true" name="bulletin.priority"></s:radio>
									</td>
								</tr>
								<tr>
									<th width="10%" align="left">关键字：</th>
									<td width="90%">
										<dt:input id="keywordEdit" name="bulletin.keyword"  length="255"></dt:input>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</dt:tabsheet>
			<dt:tabsheet label="公告发布" id="bulletinEditPublishTab">
				<table width="100%" border="0" cellpadding="2" cellspacing="0">
					<tr>
						<td><table width="100%" align="center" class="condition">
								<s:hidden id="bulletinPublishRangeStr" name="rangeStr"></s:hidden>
								<s:hidden id="bulletinViewRangeHidden" name="viewRangeStr"></s:hidden>
								<tr>
									<th width="10%" align="left">发布方式：</th>
									<td width="90%">
										<s:radio list="#{'1000':'页面发布','2000':'邮件发布','3000':'短信发布'}" id="bullPubMethodId" required="true" name="bulletin.bulletinPublishMethodInst.bullPubMethodId"></s:radio>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left">发布范围：</th>
								<td width="90%" valign="middle">
									<div style="float: left;margin: 4px;"><dt:button  id="selectDeptBtn" label="部门" click="selectDeptDialog"></dt:button></div>
									<div id="publishEditDeptDiv" style="margin: 10px;float: left;width: 85%;height: 80px;border: 1px solid rgb(204, 204, 204);"></div>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left"></th>
								<td width="90%" valign="middle">
									<div style="float: left;margin: 4px;"><dt:button id="selectRoleBtn" label="角色" click="selectRoleDialog"></dt:button></div>
									<div id="publishEditRoleDiv" style="margin: 10px;float: left;width: 85%;height: 80px;border: 1px solid rgb(204, 204, 204);"></div>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left"></th>
								<td width="90%" valign="middle">
									<div style="float: left;margin: 4px;"><dt:button id="selectUserBtn" label="用户" click="selectUserDialog"></dt:button></div>
									<div id="publishEditUserDiv" style="margin: 10px;float: left;width: 85%;height: 80px;border: 1px solid rgb(204, 204, 204);"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</dt:tabsheet>
	</dt:tabs>
</form>
</body>
</html>