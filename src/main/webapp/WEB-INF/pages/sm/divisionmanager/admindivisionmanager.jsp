<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行政区划管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="tree,combox,input,dialog,button"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript">
	$(function() {
		$("#divisionName").attr("disabled", "disabled");
		$("#divisionCode").attr("disabled", "disabled");
		$("#divisionLevel").attr("disabled", "disabled");
		$("#editAdminDivision").attr("disabled", "disabled");
		/* [编辑]操作下[确定]按钮事件绑定 */
		$("#editAdminDivision").click(function() {
			$.post("sm/adminDivisionManager!editAdminDivision.action", {
				'adminDivisionObj.divisionName' : $("#divisionName").val(),
				'adminDivisionObj.divisionCode' : $("#divisionCode").val(),
				'adminDivisionObj.divisionLevel' : $("#divisionLevel").val(),
				'adminDivisionObj.divisionId' : $("#divisionId").val()
			}, function(data) {
				/* 前台请求成功，回调处理 */
				if (data.flag == true) {
					var node = adminVisionTree.dttree("getSelectedNode");
					node.divisionName = $('#divisionName').val();
					adminVisionTree.dttree("updateNode",node);
					$.dtmessagebox.alert("修改成功");
					$("#divisionName").attr("disabled", "disabled");
					$("#divisionCode").attr("disabled", "disabled");
					$("#divisionLevel").attr("disabled", "disabled");
					$("#editAdminDivision").dtbutton("option", "disabled", true);
				} else {
					if (data.msg) {
						$.dtmessagebox.alert(data.msg);
					} else {
						$.dtmessagebox.alert("修改失败");
					}
				}
			});
		});
		/* [新增下级区划]事件绑定 */
		$("#addAdminDivisionShow").bind("click", function() {
			addAdminDivisionDialogShow();
		});
	});
	/* 树节点[点击]事件绑定*/
	function getAdminVision(event, treeId, treeNode) {
		/* 解绑上一次的绑定 */
		$("#editAdminDivisionAble").unbind("click");
		$("#deleteAdminDivision").unbind("click");
		/* 页面元素禁用 */
		$("#divisionName").attr("disabled", "disabled");
		$("#divisionCode").attr("disabled", "disabled");
		$("#divisionLevel").attr("disabled", "disabled");
		$("#editAdminDivision").dtbutton("option", "disabled", true);
		/* [编辑]事件绑定 */
		$("#editAdminDivisionAble").bind("click", function() {
			if (null == treeNode.divisionPid) {
				$.dtmessagebox.alert("根节点数据，不可编辑");
			} else {
				$("#divisionName").removeAttr("disabled");
				$("#divisionCode").removeAttr("disabled");
				$("#divisionLevel").removeAttr("disabled");
				$("#editAdminDivision").dtbutton("option", "disabled", false);
			}
		});
		/* [删除]事件绑定 */
		$("#deleteAdminDivision").bind("click", function() {
			// 			alert(treeNode.divisionPid);
			if (null == treeNode.divisionPid) {
				$.dtmessagebox.alert("根节点数据，不可删除");
			} else {
				deleteAdminDivision();
			}
		});
		$("#divisionName").val(treeNode.divisionName);
		$("#divisionCode").val(treeNode.divisionCode);
		$("#divisionLevel").val(treeNode.divisionLevel);
		$("#divisionId").val(treeNode.divisionId);
	}

	function deleteAdminDivision() {
		var node = adminVisionTree.dttree("getSelectedNode");
		if (null == node) {
			$.dtmessagebox.alert("请选择要删除的行政区划");
			return;
		}
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("sm/adminDivisionManager!deleteAdminDivision.action", {
					'divisionId' : node.divisionId
				}, function(flag) {
					if (flag) {
						adminVisionTree.dttree("removeNode",node);
						$("#divisionName").val("");
						$("#divisionCode").val("");
						$("#divisionId").val("");
						$.dtmessagebox.alert("删除成功");
					} else {
						$.dtmessagebox.alert("删除失败");
					}
				});
			}
		});
	}

	function addAdminDivisionDialogShow() {
		var node = adminVisionTree.dttree("getSelectedNode");
		if (null == node) {
			$.dtmessagebox.alert("请选择上级行政区划");
			return;
		}
		$("#parentAdminDivision").html(node.divisionName);
		$("#divisionPidAdd").val(node.divisionId);
		addAdminDivisionDialog.dtdialog("setData",{
			divisionNameAdd : '',
			divisionCodeAdd : ''
		});
		addAdminDivisionDialog.dtdialog("showModal");
	}

	function addAdminDivision() {
		if ($.dtvalidate("#addAdminDivisionForm")) {
			var data = addAdminDivisionDialog.dtdialog("getData");

			$.post("sm/adminDivisionManager!addAdminDivision.action", {
				'adminDivisionObj.divisionName' : data.divisionNameAdd,
				'adminDivisionObj.divisionCode' : data.divisionCodeAdd,
				'adminDivisionObj.divisionLevel' : data.divisionLevelAdd,
				'adminDivisionObj.divisionPid' : data.divisionPidAdd
			}, function(data) {
				if (data.flag) {
					var newNode = data.adminDivisionObj;
					var parNode = adminVisionTree.dttree("getSelectedNode");
					adminVisionTree.dttree("addNodes", parNode, [ newNode ]);
					$.dtmessagebox.alert("添加成功");
					addAdminDivisionDialog.dtdialog("close");
				} else {
					if (data.msg) {
						$.dtmessagebox.alert(data.msg);
					} else {
						$.dtmessagebox.alert("添加失败");
					}
				}
			});
		}
	}

	function cancelAddAdminDivisionDialog() {
		addAdminDivisionDialog.dtdialog("close");
	}
</script>

</head>
<body>
	<table style="height: 500px; width: 100%;" border="0">
		<tr style="vertical-align: top;">
			<td width="300px"><dt:tree id="adminVisionTree" width="180"
					height="500px"
					url="sm/adminDivisionManager!getAdminDivision.action"
					urlParam="divisionId" nameCol="divisionName"
					isParentDefaultValue="true" onClick="getAdminVision">
				</dt:tree></td>
			<td style="min-width: 600px;">
				<table border="1" width="50%" class="condition"
					style="background-color: #FFFFFF; border-collapse: collapse;">
					<tr>
						<th colspan="2"><span style="float: left;">详细信息</span> <span
							style="float: right;"> <a id="editAdminDivisionAble"
								href="javascript:void(0)">编辑</a> <a id="deleteAdminDivision"
								href="javascript:void(0)">删除</a> <a id="addAdminDivisionShow"
								href="javascript:void(0)">新增下级区划</a> </span></th>
					</tr>
					<tr>
						<th width="30%">行政区划名称：</th>
						<td><dt:input id="divisionName" prompt="行政区划名称"
								required="true" length="30" immValidate="true" />
						</td>
					</tr>
					<tr>
						<th>行政区划代码：</th>
						<td><dt:input id="divisionCode" prompt="政区划代码"
								required="true" length="30" immValidate="true" />
						</td>
					</tr>
					<tr>
						<th>行政区划级别：</th>
						<td><select id="divisionLevel" name="divisionLevel">
								<core-tags:options domainCode="ADMIN_DIVISION.DIVISION_LEVEL" />
						</select></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="hidden"
							id="divisionId" /> <dt:button id="editAdminDivision" label="确定" />
						</td>
					</tr>
				</table>
				<div style="margin-top: 50px;">
					行政区划级别说明：<br /> 省级：直辖市、省、自治区、特别行政区<br /> 地级：地级市、地区、自治州、盟<br />
					县级：市辖区、县级市、县、自治县、旗、自治旗、特区、林区<br /> 乡镇级：镇、乡、街道办事处、区公所<br />
					村级：村民委员会、居民委员会
				</div>
			</td>
		</tr>
	</table>
	<dt:dialog id="addAdminDivisionDialog" title="新增行政区划" width="70%">
		<form id="addAdminDivisionForm">
			<table border="1" width="100%"
				style="background-color: #FFFFFF; border-collapse: collapse; margin-top: 3px;">
				<tr>
					<td width="30%">行政区划名称：</td>
					<td><dt:input id="divisionNameAdd" prompt="行政区划名称"
							required="true" length="30" immValidate="true" />
					</td>
				</tr>
				<tr>
					<td>行政区划代码：</td>
					<td><dt:input id="divisionCodeAdd" prompt="行政区划代码"
							required="true" length="30" immValidate="true" />
					</td>
				</tr>
				<tr>
					<td>行政区划级别：</td>
					<td><select id="divisionLevelAdd" name="divisionLevelAdd">
							<core-tags:options domainCode="ADMIN_DIVISION.DIVISION_LEVEL" />
					</select></td>
				</tr>
				<tr>
					<td>上级行政区划：</td>
					<td id="parentAdminDivision"></td>
				</tr>
			</table>
		</form>
		<input type="hidden" id="divisionPidAdd" name="divisionPidAdd" />
		<dt:dialogbutton text="确定" onClick="addAdminDivision"></dt:dialogbutton>
		<dt:dialogbutton text="取消" onClick="cancelAddAdminDivisionDialog"></dt:dialogbutton>
		<div style="margin-top: 50px;">
			行政区划级别说明：<br /> 省级：直辖市、省、自治区、特别行政区<br /> 地级：地级市、地区、自治州、盟<br />
			县级：市辖区、县级市、县、自治县、旗、自治旗、特区、林区<br /> 乡镇级：镇、乡、街道办事处、区公所<br />
			村级：村民委员会、居民委员会
		</div>
	</dt:dialog>
</body>
</html>