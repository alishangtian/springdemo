<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/func-tags" prefix="func-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公告查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<%-- <script type="text/javascript" src="js/dt.datatag.js"></script>  --%>
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
		//初始化公告范围
		bulletinViewTagDept = $("#bulletinViewDeptDiv").datatag( {
			"data":[],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){
				return false;
			}
		});
		bulletinViewTagRole = $("#bulletinViewRoleDiv").datatag( {
			"data":[],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){
				return false;
			}
		});
		bulletinViewTagUser = $("#bulletinViewUserDiv").datatag( {
			"data":[],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){
				return false;
			}
		});
		var rangeStr = $("#bulletinViewRangeHidden").val();
		var rangeArr = rangeStr.split('!');
		for(var i=0; i<rangeArr.length; i++){
			var singleRange = rangeArr[i].split('#');
			if(singleRange[0]=='1000'){
				bulletinViewTagDept.datatag("reset",eval(singleRange[1]));
			}else if(singleRange[0]=='2000'){
				bulletinViewTagRole.datatag("reset",eval(singleRange[1]));
			}else if(singleRange[0]=='3000'){
				bulletinViewTagUser.datatag("reset",eval(singleRange[1]));
			}
		}
		//公告变更
		$('#bulletinViewSubmit').click(function(){
			var url = '<%=request.getContextPath()%>/sm/BulletinAction!prepareUpdate.action?id='+$('#bulletinId').val();
			bulletinUpdateDialog = $.dtdialog.showModalDialog({title: '公告变更',
			width:800,
			height:400,
			url:  url,
			buttons:{
				'确定' : function() {
					if (!$.dtvalidate("#bulletinUpdateForm", "caption")) {
						return false;
					}
					if($('#revokeTimeUpdate').val()<=$('#publishTimeUpdate').val()){
						$.dtmessagebox.error("撤销时间不得晚于发布时间");
						return false;
					}
					var bulletinInfo = $("#bulletinUpdateForm").serializeArray(), dialog = this;
					$.post("sm/BulletinAction!update.action",
							bulletinInfo, function(data) {
								$.dialog(dialog).close();
								$.dtmessagebox.success("变更成功", "提示");
								bulletingrid.dtpaginggrid("reload");
							});
				},
				'取消' : function() {
					$(this).dtdialog("close");
				}
			},
			open:function() {},
			close:function() {
				if (CKEDITOR.instances['bulletinUpdateContentStrUpdate']) {
					CKEDITOR.instances['bulletinUpdateContentStrUpdate'].destroy();
				}
			}
			});
		});
		//初始化公告内容
		var contentStr = $('#bulletinContentStr').val();
		$('#bulletinViewContentStr').html(contentStr);
		//对于已发布生效、且未撤消的公告，可以执行公告信息的变更操作
		var publishTime = '<%=request.getAttribute("bulletin.publishTime")%>';
		publishTime = publishTime.substring(0, 19);
		var publishDate =new Date(Date.parse(publishTime.replace(/-/g,'/')));
	 	var nowDate = new Date();
	 	if(publishDate>nowDate){
	 		$('#bulletinViewSubmit').remove();
	 	}
	});
</script>
</head>
<body>
	<dt:tabs height="400" id="bulletinViewTab" switchMode ="mouseover" canDuplicate="false" active="0">
		<dt:tabsheet label="基本信息" id="bulletinViewBaseInfoTab">
			<table width="100%" border="0" cellpadding="2" cellspacing="0">
				<s:hidden id="bulletinViewRangeHidden" name="viewRangeStr"></s:hidden>
				<tr>
					<td><table width="100%" align="center" class="condition">
							<s:hidden id="bulletinId" name="bulletin.bulletinId"></s:hidden>
							<s:hidden id="bulletinContentStr" name="bulletin.bulletinContentStr"></s:hidden>
							<tr>
								<th width="10%" align="left">公告标题：</th>
								<td width="90%">
									<s:label id="bulletinTitleView" name="bulletin.bulletinTitle"></s:label>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left">公告内容：</th>
								<td width="90%">
									 <div id="bulletinViewContentStr" style="width:180px; height:150px;"></div>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left">发布时间：</th>
								<td width="90%">
									<s:label id="publishTimeView" name="bulletin.publishTime"></s:label>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left">撤销时间：</th>
								<td width="90%">
									<s:label id="revokeTimeView" name="bulletin.revokeTime"></s:label>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left">优先级：</th>
								<td width="90%">
									<s:if test="bulletin.priority==\"X\"">一般</s:if>
									<s:if test="bulletin.priority==\"Y\"">加急</s:if>
									<s:if test="bulletin.priority==\"Z\"">特急</s:if>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left">关键字：</th>
								<td width="90%">
									<s:label name="bulletin.keyword"></s:label>
								</td>
							</tr>
							<tr>
								<td align="right" colspan="4">
									<func-tags:funcItemAuth funcItemId='1050010106'>
									<input id="bulletinViewSubmit"  type="submit" value="公告变更"/>
									</func-tags:funcItemAuth>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</dt:tabsheet>
		<dt:tabsheet label="公告发布" id="bulletinViewPublishTab">
			<table width="100%" border="0" cellpadding="2" cellspacing="0">
				<tr>
					<td><table width="100%" align="center" class="condition">
							<tr>
								<th width="10%" align="left">发布方式：</th>
								<td width="90%">
									<s:if test="bulletin.bulletinPublishMethodInst.bullPubMethodId==1000">页面发布</s:if>
									<s:if test="bulletin.bulletinPublishMethodInst.bullPubMethodId==2000">邮件发布</s:if>
									<s:if test="bulletin.bulletinPublishMethodInst.bullPubMethodId==3000">短信发布</s:if>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left">发布范围：</th>
								<td width="90%" valign="middle">
									<div id="bulletinViewDeptDiv" style="margin: 10px;float: left;width: 95%;height:50px;"></div>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left"></th>
								<td width="90%" valign="middle">
									<div id="bulletinViewRoleDiv" style="margin: 10px;float: left;width: 95%;height: 50px;"></div>
								</td>
							</tr>
							<tr>
								<th width="10%" align="left"></th>
								<td width="90%" valign="middle">
									<div id="bulletinViewUserDiv" style="margin: 10px;float: left;width: 95%;height: 50px;"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</dt:tabsheet>
	</dt:tabs>
</body>
</html>