<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>公告信息列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />

<dt:comppath></dt:comppath>
<%-- <script type="text/javascript" src="js/dt.datatag.js"></script> --%>
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>

<script type="text/javascript">
			$(function() {
				function publishSubmit(){
					var rangeStr = "1000:";
					if(typeof publishTagDept!='undefined'){
						rangeStr += getSingleIds(publishTagDept);
					}
					rangeStr += ";2000:";
					if(typeof publishTagRole!='undefined'){
						rangeStr += getSingleIds(publishTagRole);
					}
					rangeStr += ";3000:";
					if(typeof publishTagUser!='undefined'){
						rangeStr += getSingleIds(publishTagUser);
					}
					$('#bulletinPublishRangeStr').val(rangeStr);
				}
				function getSingleIds(publishTagSingle){
					var publishData = publishTagSingle.datatag("getData");
					for(var i=0,deptArr = []; i<publishData.length; i++){
						deptArr.push(publishData[i].id);
					}
					return deptArr.join(',');
				}
				
				$('#createRowBtn').click(function() {
					var url = '<%=request.getContextPath()%>/sm/BulletinAction!prepareCreate.action';
					bulletinCreateDialog = $.dtdialog.showModal({title: '新增公告信息',
					width:800,
					height:450,
					url:  url,
					buttons:{
						'确定' : function() {
							 if (!$.dtvalidate("#bulletinCreateForm", "caption")) {
								return;
							} 
							if($('#revokeTime').val()<=$('#publishTime').val()){
								$.dtmessagebox.error("撤销时间不得晚于发布时间");
								return false;
							}
							publishSubmit();
							//$('#bulletinContentStr').dtckeditor('updateElement');
							var bulletinInfo = $("#bulletinCreateForm").serializeArray(), dialog = this;
							$.post("sm/BulletinAction!create.action",
									bulletinInfo, function(data) {
										$(dialog).dtdialog("close");
										$.dtmessagebox.success("新增成功", "提示");
										bulletingrid.dtpaginggrid("reload");
									});
						},
						'取消' : function() {
							$(this).dtdialog("close");
						}
					},
					open:function() {},
					close:function() {
						if (CKEDITOR.instances['bulletinContentStr']) {
							CKEDITOR.instances['bulletinContentStr'].destroy();
						}
					}
					});
				});
	//			startTimeClose();
			});
			function formatAct(cellvalue, options, rowObject) {
				var result = "";
				var actDivStart = "<div align = 'center'>",
			 	viewHtml = "<func-tags:funcItemAuth funcItemId='1050010103'><a href='javascript:void(0);' onclick='viewBulletinDetail(\""+rowObject.bulletinId+"\")'>查看</a>&nbsp;</func-tags:funcItemAuth>",
			 	editHtml = "<func-tags:funcItemAuth funcItemId='1050010104'><a href='javascript:void(0);' onclick='editBulletinDetail(\""+rowObject.bulletinId+"\")'>编辑</a>&nbsp;</func-tags:funcItemAuth>",
			 	delHtml =  "<func-tags:funcItemAuth funcItemId='1050010105'><a href='javascript:void(0);' onclick='deleteBulletinDetail(\""+rowObject.bulletinId+"\")'>删除</a>&nbsp;</func-tags:funcItemAuth>",
			 	actDivEnd = "</div>";
			 	
			 	var publishDate =new Date(Date.parse((rowObject.publishTime).replace(/-/g,'/')));
			 	var nowDate = new Date();
			 	if(publishDate<nowDate){
			 		result += viewHtml;
			 	}else{
			 		result += viewHtml + editHtml + delHtml;
			 	}
				return actDivStart + result + actDivEnd;	
			}
			//查看
			function viewBulletinDetail(bulletinId){
				var url = '<%=request.getContextPath()%>/sm/BulletinAction!view.action?id='+bulletinId;
				$.dtdialog.showModal({title: '公告查看',
				width:800,
				height:400,
				url:  url,
				buttons:{},
				open:function() {},
				close:function() {}
				});
			}
			//删除
			function deleteBulletinDetail(bulletinId){
				$.post("sm/BulletinAction!remove.action?id="+bulletinId,
						bulletinId, function(data) {
						$.dtmessagebox.success("删除成功", "提示");
							bulletingrid.dtpaginggrid("reload");
						});
			}
			//编辑
			function editBulletinDetail(bulletinId){
				var url = '<%=request.getContextPath()%>/sm/BulletinAction!prepareEdit.action?id='+bulletinId;
				bulletinEditDialog = $.dtdialog.showModal({title: '公告编辑',
				width:800,
				height:400,
				url:  url,
				buttons:{
					'确定' : function() {
						function publishSubmit(){
							var publishDeptData = publishTagDept.datatag("getData");
							var publishRoleData = publishTagRole.datatag("getData");
							var publishUserData = publishTagUser.datatag("getData");
							var rangeStr = "1000:";
							rangeStr += getSingleIds(publishDeptData);
							rangeStr += ";2000:";
							rangeStr += getSingleIds(publishRoleData);
							rangeStr += ";3000:";
							rangeStr += getSingleIds(publishUserData);
							$('#bulletinPublishRangeStr').val(rangeStr);
						}
						function getSingleIds(publishData){
							for(var i=0,deptArr = []; i<publishData.length; i++){
								deptArr.push(publishData[i].id);
							}
							return deptArr.join(',');
						}

						if (!$.dtvalidate("#bulletinEditForm", "caption")) {
							return;
						}
						if($('#revokeTimeEdit').val()<=$('#publishTimeEdit').val()){
							$.dtmessagebox.error("撤销时间不得晚于发布时间");
							return false;
						}
						publishSubmit();
						var bulletinInfo = $("#bulletinEditForm").serializeArray(), dialog = this;
						$.post("sm/BulletinAction!edit.action",
								bulletinInfo, function(data) {
									$(dialog).dtdialog("close");
									$.dtmessagebox.success("编辑成功", "提示");
									bulletingrid.dtpaginggrid("reload");
								});
					},
					'取消' : function() {
						$(this).dtdialog("close");
					}
				},
				open:function() {},
				close:function() {
					if (CKEDITOR.instances['bulletinEditContentStrEdit']) {
						CKEDITOR.instances['bulletinEditContentStrEdit'].destroy();
					}
				}
				});
			}
			//查询
			function queryBtnClick() {
				var publishTime = $('#queryPublishTime').val();
				var revokeTime = $('#queryRevokeTime').val();
				var keyword = $('#queryKeyword').val();
				var queryCondition = "";
				if(publishTime != null && publishTime!=""){
					queryCondition += "&pqb.queryBean.publishTime="+publishTime;
				}
				if(revokeTime!=null && revokeTime!=""){
					queryCondition += "&pqb.queryBean.revokeTime="+revokeTime;
				}
				if(keyword != null && keyword!=""){
					queryCondition += "&pqb.queryBean.keyword="+keyword;
				}
				
				if(queryCondition.length>1){
					queryCondition = queryCondition.substring(1,queryCondition.length);
				}
				
				if(queryCondition==""){
					bulletingrid.dtpaginggrid("option",{url:'sm/BulletinAction!list.action', postData:{xxx:'yyy'}, page:1});
				}else{
					bulletingrid.dtpaginggrid("option",{url:'sm/BulletinAction!list.action?'+queryCondition, postData:{xxx:'yyy'}, page:1});
				}
				bulletingrid.dtpaginggrid("reload");
			}
			function startTimeClose() {
				queryRevokeTime.dtdatepicker('option','minDate', queryPublishTime.dtdatepicker('getDate'));
			}
			
		</script>
</head>

<body>
<table width="100%" border="0" cellpadding="2" cellspacing="0">
	<tr>
		<td>
		<s:form action="sm/BulletinAction!list.action" method="post">
			<table width="100%" align="center" class="condition">
					<tr>
						<th width="15%" align="right">关键字</th>
						<td width="15%">
							<dt:input id="queryKeyword" name="pqb.queryBean.keyword" length="255"></dt:input>
						</td>
						<th width="15%" align="right">发布时间</th>
						<td width="20%">
							<dt:dateInput id="queryPublishTime" name="pqb.queryBean.publishTime" readonly="true" showTime="true" showOnbutton="true" onClose="startTimeClose()"></dt:dateInput>
						</td>
						<th width="15%" align="right">撤销时间</th>
						<td width="20%">
							<dt:dateInput id="queryRevokeTime" name="pqb.queryBean.revokeTime" readonly="true" showTime="true" showOnbutton="true"></dt:dateInput>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="6">
							<func-tags:funcItemAuth funcItemId="1050010101">
							<dt:button id="btnQuery" label="查询" click="queryBtnClick"></dt:button>
							</func-tags:funcItemAuth>
							<func-tags:funcItemAuth funcItemId="1050010102">
							<dt:button id="createRowBtn" label="新增"></dt:button>
							</func-tags:funcItemAuth>
						</td>
					</tr>
				</table>
			</s:form>
		</td>
	</tr>
	<tr>
		<td>
						<dt:grid id="bulletingrid" url="sm/BulletinAction!list.action" showPager="true" rowNum = "10"
							caption="公告列表"  height="245" requestType="post" shrinkToFit="true" showHideButton="false">
							<dt:gridColumn name="bulletinId" width="60" label="公告标识" editable="true" editRules="{edithidden:true}" key="true" ></dt:gridColumn>
							<dt:gridColumn name="bulletinTitle" width="80" label="公告标题" editable="true"></dt:gridColumn>
							<dt:gridColumn name="bulletinContentStr" width="100" label="公告内容" editable="true"></dt:gridColumn>
							<dt:gridColumn name="keyword" width="60" label="关键字" editable="true"></dt:gridColumn>
							<dt:gridColumn name="publishTime" width="100" label="发布时间" editable="true"></dt:gridColumn>
							<dt:gridColumn name="revokeTime" width="100" label="撤销时间" editable="true"></dt:gridColumn>
							<dt:gridColumn name="createTime" width="100" label="创建时间" editable="true"></dt:gridColumn>
							<dt:gridColumn name="act" width="100" hidedlg="true" editable="false" label="操作" formatter="formatAct"></dt:gridColumn>
						</dt:grid>
				
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
$(function() {
	//初始化查询日期
	var now= new Date();
	var upMonthNow = new Date(now.getFullYear(),now.getMonth()-1,'01','00','00','01');
	var downMonthNow = new Date(now.getFullYear(),now.getMonth()+1,'01','23','59','59');
	queryPublishTime.dtdatepicker("setDate",upMonthNow);
	queryRevokeTime.dtdatepicker("setDate",new Date(downMonthNow-24*60*60*1000));
	queryBtnClick();
});
</script>
</html>