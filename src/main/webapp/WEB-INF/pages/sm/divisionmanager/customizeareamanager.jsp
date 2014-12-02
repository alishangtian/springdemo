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
<title>区域管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="tree,combox,input,dialog,button"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript">
	$(function() {
		$("#areaName").attr("disabled", "disabled");
		$("#areaCode").attr("disabled", "disabled");
		$("#areaLevel").attr("disabled", "disabled");
		$("#editCustomizeArea").attr("disabled", "disabled");
		/* [编辑]操作下[确定]按钮事件绑定 */
		$("#editCustomizeArea").click(function() {
			
			$.post("sm/customizeAreaManager!editCustomizeArea.action", {
				'customizeAreaObj.areaName' : $("#areaName").val(),
				'customizeAreaObj.areaCode' : $("#areaCode").val(),
				'customizeAreaObj.areaLevel' : $("#areaLevel").val(),
				'customizeAreaObj.areaId' : $("#areaId").val()
			}, function(data) {
				if (data.flag) {
					var node = customizeAreaTree.dttree("getSelectedNode");
					node.areaName = $('#areaName').val();
					node.areaCode = $('#areaCode').val();
					node.areaLevel = $('#areaLevel').val();
					node.areaId = $('#areaId').val();
					
					customizeAreaTree.dttree("updateNode",node);
					$.dtmessagebox.alert("修改成功");
					$("#areaName").attr("disabled", "disabled");
					$("#areaCode").attr("disabled", "disabled");
					$("#areaLevel").attr("disabled", "disabled");
					$("#editCustomizeArea").dtbutton("option", "disabled", true);
				} else {
					if (data.msg) {
						$.dtmessagebox.alert(data.msg);
					} else {
						$.dtmessagebox.alert("修改失败");
					}
				}
			});
		});
		/* [新增下级区域]事件绑定 */
		$("#addCustomizeAreaShow").bind("click", function() {
			addCustomizeAreaDialogShow();
		});
	});
	/* 树节点[点击]事件绑定*/
	function getCustomizeArea(event, treeId, treeNode) {
		/* 解绑上一次的绑定 */
		$("#editCustomizeAreaAble").unbind("click");
		$("#deleteCustomizeArea").unbind("click");
		/* 页面元素禁用 */
		$("#areaName").attr("disabled", "disabled");
		$("#areaCode").attr("disabled", "disabled");
		$("#areaLevel").attr("disabled", "disabled");
		$("#editCustomizeArea").dtbutton("option", "disabled", true);
		/* [编辑]事件绑定 */
		$("#editCustomizeAreaAble").bind("click", function() {
			if (null == treeNode.areaPid) {
				$.dtmessagebox.alert("根节点数据，不可编辑");
			} else {
				$("#areaName").removeAttr("disabled");
				$("#areaCode").removeAttr("disabled");
				$("#areaLevel").removeAttr("disabled");
				$("#editCustomizeArea").dtbutton("option", "disabled", false);
			}
		});
		/* [删除]事件绑定 */
		$("#deleteCustomizeArea").bind("click", function() {
			if (null == treeNode.areaPid) {
				$.dtmessagebox.alert("根节点数据，不可删除");
			} else {
				deleteCustomizeArea();
			}
		});
		$("#areaName").val(treeNode.areaName);
		$("#areaCode").val(treeNode.areaCode);
		$("#areaLevel").val(treeNode.areaLevel);
		$("#areaId").val(treeNode.areaId);
	}

	function deleteCustomizeArea() {
		var node = customizeAreaTree.dttree("getSelectedNode");
		if (null == node) {
			$.dtmessagebox.alert("请选择要删除的区域");
			return;
		}
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("sm/customizeAreaManager!deleteCustomizeArea.action", {
					'areaId' : node.areaId
				}, function(flag) {
					if (flag) {
						customizeAreaTree.dttree("removeNode",node);
						$("#areaName").val("");
						$("#areaCode").val("");
						$("#areaId").val("");
						$.dtmessagebox.alert("删除成功");
					} else {
						$.dtmessagebox.alert("删除失败");
					}
				});
			}
		});
	}

	function addCustomizeAreaDialogShow() {
		var node = customizeAreaTree.dttree("getSelectedNode");
		if (null == node) {
			$.dtmessagebox.alert("请选择上级区域");
			return;
		}
		$("#parentCustomizeArea").html(node.areaName);
		$("#areaPidAdd").val(node.areaId);
		addCustomizeAreaDialog.dtdialog("setData",{
			areaNameAdd : '',
			areaCodeAdd : ''
		});
		addCustomizeAreaDialog.dtdialog("showModal");
	}

	function addCustomizeArea() {
		if ($.dtvalidate("#addCustomizeAreaForm")) {
			var data = addCustomizeAreaDialog.dtdialog("getData");

			$.post("sm/customizeAreaManager!addCustomizeArea.action", {
				'customizeAreaObj.areaName' : data.areaNameAdd,
				'customizeAreaObj.areaCode' : data.areaCodeAdd,
				'customizeAreaObj.areaLevel' : data.areaLevelAdd,
				'customizeAreaObj.areaPid' : data.areaPidAdd
			}, function(data) {
				if (data.flag) {
					var newNode = data.customizeAreaObj;
					var parNode = customizeAreaTree.dttree("getSelectedNode");
					customizeAreaTree.dttree("addNodes",parNode, [ newNode ]);
					addCustomizeAreaDialog.dtdialog("close");
					$.dtmessagebox.alert("添加成功");
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

	function cancelAddCustomizeAreaDialog() {
		addCustomizeAreaDialog.dtdialog("close");
	}
</script>

</head>
<body>
	<table style="height: 500px; width: 100%;" border="0">
		<tr style="vertical-align: top;">
			<td width="300px"><dt:tree id="customizeAreaTree" width="180"
					height="500px"
					url="sm/customizeAreaManager!getCustomizeArea.action"
					urlParam="areaId" nameCol="areaName" isParentDefaultValue="true"
					onClick="getCustomizeArea">
				</dt:tree>
			</td>
			<td style="min-width: 600px;">
				<table border="1" width="50%" class="condition"
					style="background-color: #FFFFFF; border-collapse: collapse;">
					<tr>
						<th colspan="2"><span style="float: left;">详细信息</span> <span
							style="float: right;"> <a id="editCustomizeAreaAble"
								href="javascript:void(0)">编辑</a> <a id="deleteCustomizeArea"
								href="javascript:void(0)">删除</a> <a id="addCustomizeAreaShow"
								href="javascript:void(0)">新增下级区域</a> </span>
						</th>
					</tr>
					<tr>
						<th width="30%">区域名称：</th>
						<td><dt:input id="areaName" prompt="区域名称" required="true"
								length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<th>区域代码：</th>
						<td><dt:input id="areaCode" prompt="区域代码" required="true"
								length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<th>区域级别：</th>
						<td><select id="areaLevel" name="areaLevel">
								<core-tags:options domainCode="CUSTOMIZE_AREA.AREA_LEVEL" />
						</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="hidden"
							id="areaId" /> <dt:button id="editCustomizeArea" label="确定" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<dt:dialog id="addCustomizeAreaDialog" title="新增 区域" width="70%">
		<form id="addCustomizeAreaForm">
			<table border="1" width="100%"
				style="background-color: #FFFFFF; border-collapse: collapse; margin-top: 3px;">
				<tr>
					<td width="30%">区域名称：</td>
					<td><dt:input id="areaNameAdd" prompt="区域名称" required="true"
							length="30" immValidate="true" /></td>
				</tr>
				<tr>
					<td>区域代码：</td>
					<td><dt:input id="areaCodeAdd" prompt="区域代码" required="true"
							length="30" immValidate="true" /></td>
				</tr>
				<tr>
					<td>区域级别：</td>
					<td><select id="areaLevelAdd" name="areaLevelAdd">
							<core-tags:options domainCode="CUSTOMIZE_AREA.AREA_LEVEL" />
					</select>
					</td>
				</tr>
				<tr>
					<td>上级区域：</td>
					<td id="parentCustomizeArea"></td>
				</tr>
			</table>
		</form>
		<input type="hidden" id="areaPidAdd" name="areaPidAdd" />
		<dt:dialogbutton text="确定" onClick="addCustomizeArea"></dt:dialogbutton>
		<dt:dialogbutton text="取消" onClick="cancelAddCustomizeAreaDialog"></dt:dialogbutton>
	</dt:dialog>
</body>
</html>