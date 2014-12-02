<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>公告管理</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="<%=basePath%>css/func.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>css/func.cache.css"
	type="text/css" />
<link rel="stylesheet" href="<%=basePath%>css/new.style.css"
	type="text/css" />
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>
<script type="text/javascript">
	$(function() {
		function publishSubmit() {
			var rangeStr = "1000:";
			if (typeof publishTagDept != 'undefined') {
				rangeStr += getSingleIds(publishTagDept);
			}
			rangeStr += ";2000:";
			if (typeof publishTagRole != 'undefined') {
				rangeStr += getSingleIds(publishTagRole);
			}
			rangeStr += ";3000:";
			if (typeof publishTagUser != 'undefined') {
				rangeStr += getSingleIds(publishTagUser);
			}
			$('#bulletinPublishRangeStr').val(rangeStr);
		}
		function getSingleIds(publishTagSingle) {
			var publishData = publishTagSingle.datatag("getData");
			for ( var i = 0, deptArr = []; i < publishData.length; i++) {
				deptArr.push(publishData[i].id);
			}
			return deptArr.join(',');
		}

		$('#createRowBtn')
				.click(
						function() {
							window
									.open(
											'sm/BulletinAction!prepareCreate.action',
											'newwindow',
											'height=500px,width=900px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
							return;
							var url = 'sm/BulletinAction!prepareCreate.action';
							bulletinCreateDialog = $.dtdialog
									.showModal({
										title : '新增公告信息',
										width : 800,
										height : 450,
										url : url,
										buttons : {
											'确定' : function() {
												if (!$.dtvalidate(
														"#bulletinCreateForm",
														"caption")) {
													return;
												}
												if ($('#revokeTime').val() <= $(
														'#publishTime').val()) {
													$.dtmessagebox
															.error("撤销时间不得晚于发布时间");
													return false;
												}
												var bulletinInfo = $(
														"#bulletinCreateForm")
														.serializeArray(), dialog = this;
												$
														.post(
																"sm/BulletinAction!create.action",
																bulletinInfo,
																function(data) {
																	$(dialog)
																			.dtdialog(
																					"close");
																	$.dtmessagebox
																			.success(
																					"新增成功",
																					"提示");
																	bulletingrid
																			.dtpaginggrid("reload");
																});
											},
											'取消' : function() {
												$(this).dtdialog("close");
											}
										},
										close : function() {
											if (CKEDITOR.instances['bulletinContentStr']) {
												CKEDITOR.instances['bulletinContentStr']
														.destroy();
											}
										}
									});
						});
	});
	function formatAct(cellvalue, options, rowObject) {
		var result = "";
		var actDivStart = "<div align = 'center'>", viewHtml = "<func-tags:funcItemAuth funcItemId='1050010103'><a href='javascript:void(0);' onclick='viewBulletinDetail(\""
				+ rowObject.bulletinId
				+ "\")'>查看</a>&nbsp;</func-tags:funcItemAuth>", delHtml = "<func-tags:funcItemAuth funcItemId='1050010105'><a href='javascript:void(0);' onclick='deleteBulletinDetail(\""
				+ rowObject.bulletinId
				+ "\")'>删除</a>&nbsp;</func-tags:funcItemAuth>", actDivEnd = "</div>";
		var publishDate = new Date(Date.parse((rowObject.publishTime).replace(
				/-/g, '/')));
		var nowDate = new Date();
		if (publishDate < nowDate) {
			result += viewHtml;
		} else {
			result += viewHtml + delHtml;
		}
		return actDivStart + result + actDivEnd;
	}
	//查看
	function viewBulletinDetail(bulletinId) {
		window
				.open(
						'busi/bulletinAction!viewBulletin.action?bulletinId='
								+ bulletinId,
						'newwindow',
						'height=350px,width=800px,top=200px,left=250px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
	}
	//删除
	function deleteBulletinDetail(bulletinId) {
		$.post("sm/BulletinAction!remove.action?id=" + bulletinId, bulletinId,
				function(data) {
					$.dtmessagebox.success("删除成功", "提示");
					bulletingrid.dtpaginggrid("reload");
				});
	}
	//编辑
	function editBulletinDetail(bulletinId) {
		var url = 'sm/BulletinAction!prepareEdit.action?id=' + bulletinId;
		bulletinEditDialog = $.dtdialog
				.showModal({
					title : '公告编辑',
					width : 800,
					height : 400,
					url : url,
					buttons : {
						'确定' : function() {
							function publishSubmit() {
								var publishDeptData = publishTagDept
										.datatag("getData");
								var publishRoleData = publishTagRole
										.datatag("getData");
								var publishUserData = publishTagUser
										.datatag("getData");
								var rangeStr = "1000:";
								rangeStr += getSingleIds(publishDeptData);
								rangeStr += ";2000:";
								rangeStr += getSingleIds(publishRoleData);
								rangeStr += ";3000:";
								rangeStr += getSingleIds(publishUserData);
								$('#bulletinPublishRangeStr').val(rangeStr);
							}
							function getSingleIds(publishData) {
								for ( var i = 0, deptArr = []; i < publishData.length; i++) {
									deptArr.push(publishData[i].id);
								}
								return deptArr.join(',');
							}

							if (!$.dtvalidate("#bulletinEditForm", "caption")) {
								return;
							}
							if ($('#revokeTimeEdit').val() <= $(
									'#publishTimeEdit').val()) {
								$.dtmessagebox.error("撤销时间不得晚于发布时间");
								return false;
							}
							publishSubmit();
							var bulletinInfo = $("#bulletinEditForm")
									.serializeArray(), dialog = this;
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
					open : function() {
					},
					close : function() {
						if (CKEDITOR.instances['bulletinEditContentStrEdit']) {
							CKEDITOR.instances['bulletinEditContentStrEdit']
									.destroy();
						}
					}
				});
	}
	//查询
	function queryBtnClick() {
		var queryData = {};
		var publishTime = $('#queryPublishTime').val();
		var revokeTime = $('#queryRevokeTime').val();
		var keyword = $('#queryKeyword').val();
		if (publishTime != null && publishTime != "") {
			queryData.publishTime = publishTime;
		}
		if (revokeTime != null && revokeTime != "") {
			queryData.revokeTime = revokeTime;
		}
		if (keyword != null && keyword != "") {
			queryData.keyword = keyword;
		}
		bulletingrid.dtpaginggrid("option", {
			url : 'sm/BulletinAction!list.action?',
			postData : queryData,
			page : 1,
		});
		bulletingrid.dtpaginggrid("reload");
	}
	function startTimeClose() {
		queryRevokeTime.dtdatepicker('option', 'minDate', queryPublishTime
				.dtdatepicker('getDate'));
	}
</script>
</head>

<body>
	<table width="100%">
		<tr>
			<td><form action="sm/BulletinAction!list.action" method="post">
					<table class="query-form">
						<caption>查询条件</caption>
						<tr>
							<th>关键字</th>
							<td><dt:input width="159px" height="30px" id="queryKeyword"
									name="pqb.queryBean.keyword"></dt:input></td>
							<th>发布时间</th>
							<td><dt:dateInput id="queryPublishTime"
									name="pqb.queryBean.publishTime" showTime="true"
									showOnbutton="true" width="176px" height="30px" /></td>
							<th>撤销时间</th>
							<td><dt:dateInput id="queryRevokeTime"
									name="pqb.queryBean.revokeTime" showTime="true"
									showOnbutton="true" width="176px" height="30px" /></td>
						</tr>
					</table>
				</form>
				<div class="query-form-btns">
					<dt:button id="btnQuery" label="查询" click="queryBtnClick"></dt:button>
					<dt:button id="createRowBtn" label="新增"></dt:button>
				</div></td>
		</tr>
		<tr>
			<td><dt:grid id="bulletingrid"
					url="sm/BulletinAction!list.action" showPager="true" rowNum="10"
					caption="公告列表" height="245" requestType="post" shrinkToFit="true"
					showHideButton="false" caption="查询结果">
					<dt:gridColumn name="bulletinId" width="60" label="公告标识"
						editable="true" editRules="{edithidden:true}" key="true"></dt:gridColumn>
					<dt:gridColumn name="bulletinTitle" width="80" label="公告标题"
						editable="true"></dt:gridColumn>
					<dt:gridColumn name="bulletinContentStr" width="100" label="公告内容"
						editable="true"></dt:gridColumn>
					<dt:gridColumn name="keyword" width="60" label="关键字"
						editable="true"></dt:gridColumn>
					<dt:gridColumn name="publishTime" width="100" label="发布时间"
						editable="true"></dt:gridColumn>
					<dt:gridColumn name="revokeTime" width="100" label="撤销时间"
						editable="true"></dt:gridColumn>
					<dt:gridColumn name="createTime" width="100" label="创建时间"
						editable="true"></dt:gridColumn>
					<dt:gridColumn name="act" width="100" hidedlg="true"
						editable="false" label="操作" formatter="formatAct"></dt:gridColumn>
				</dt:grid></td>
		</tr>
	</table>
</body>
<script type="text/javascript">
	$(function() {
		queryBtnClick();
	});
</script>
</html>