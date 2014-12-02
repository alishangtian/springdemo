<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>主页</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" />
<link rel="stylesheet" href="<%=contextPath%>/css/new.style.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.greenHouseAndPoint.js"></script>
</head>
<body>
	<div class="tree-left">
		<dt:tree id="funcInfoTree" width="180px" height="380px"
			dataSource="${controlTree}" isSimpleData="true"
			treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
			nameCol="nodeTreeName" onClick="controlTreeClick">
		</dt:tree>
	</div>
	<div class="con-right">
		<div id="nodeEquipManagerDiv" style="display: none">
			<div class="query-form-btns" style="align: center">
				<dt:button id="addNodeEquip" label="添加控制器" type="ok" icon="" />
			</div>
			<dt:grid id="equipNodeInfoResult" shrinkToFit="true"
				multiSelect="true" jsonRoot="resultList" dataType="json"
				showPager="true" width="100%" height="280px" onSelectRow="status"
				caption="查询结果">
				<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
				<dt:gridColumn name="parentPort" label="端口号"></dt:gridColumn>
				<dt:gridColumn name="name" label="控制器名称"></dt:gridColumn>
				<dt:gridColumn name="opretion" label="操作" formatter="operNodeFormat"
					align="center"></dt:gridColumn>
			</dt:grid>
		</div>
		<div id="controlEquipManagerDiv" style="display: none">
			<div class="query-form-btns" style="align: center">
				<dt:button id="addEquipControl" label="新增端口配置" type="ok" icon="" />
			</div>
			<dt:grid id="equipControlInfoResult" shrinkToFit="true"
				multiSelect="true" jsonRoot="resultList" dataType="json"
				showPager="true" width="100%" height="280px" onSelectRow="status"
				caption="查询结果">
				<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
				<dt:gridColumn name="port" label="端口号"></dt:gridColumn>
				<dt:gridColumn name="name" label="控制名称"></dt:gridColumn>
				<dt:gridColumn name="opretion" label="操作" formatter="operFormat"
					align="center"></dt:gridColumn>
			</dt:grid>
		</div>
	</div>
	<div id="addControlInfoDiv" style="display: none">
		<table class="condition" width="100%" align="center">
			<tr>
				<th>端口号：</th>
				<td><dt:input id="port" name="port"></dt:input></td>
				<th>控制名称：</th>
				<td><dt:input id="name" name="name"></dt:input></td>
			</tr>
			<tr>
				<td colspan="6" align="center"><dt:button
						id="saveAddControlInfo" label="保存" type="ok" icon="" /> <dt:button
						id="cancelSave" label="取消" type="cancel" icon="" /></td>
			</tr>
		</table>
	</div>
	<div id="addNodePortDiv" style="display: none">
		<table class="condition" width="100%" align="center">
			<tr>
				<th>端口号：</th>
				<td><dt:input id="nodeport" name="port"></dt:input></td>
				<th>控制器：</th>
				<td><select id="nodecontrolid"></select></td>
			</tr>
			<tr>
				<td colspan="6" align="center"><dt:button id="saveAddNodeInfo"
						label="保存" type="ok" icon="" /> <dt:button id="cancelSaveNode"
						label="取消" type="cancel" icon="" /></td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#addControlInfoDiv").on("dialogclose", function(event, ui) {
			hideDialog();
		});
		$("#addNodePortDiv").on("dialogclose", function(event, ui) {
			hideNodeDialog();
		});
		$("#addEquipControl").click(function() {
			show();
		});
		$("#addNodeEquip").click(function() {
			showNode();
		});
		$("#cancelSave").click(function() {
			$(".ui-widget-overlay").hide();
			clearInput();
		});
		$("#cancelSaveNode").click(function() {
			hideNodeDialog();
		});
		$("#saveAddControlInfo").click(function() {
			saveAddControlInfo();
		});
		$("#saveAddNodeInfo").click(function() {
			saveAddNodeInfo();
		});
	});
	function show() {
		$(".ui-widget-overlay").show();
		$("#addControlInfoDiv").dialog({
			width : 650,
			height : 160
		});
	}
	function showNode() {
		initControlEquipList();
		$(".ui-widget-overlay").show();
		$("#addNodePortDiv").dialog({
			width : 650,
			height : 160
		});
	}
	function clearInput() {
		$("#port").val("");
		$("#name").val("");
	}
	function clearNodeInput() {
		$("#portport").val("");
	}
	function hideDialog() {
		$(".ui-widget-overlay").hide();
		clearInput();
		$("#addControlInfoDiv").dialog('close');
	}
	function hideNodeDialog() {
		$(".ui-widget-overlay").hide();
		clearNodeInput();
		$("#addNodePortDiv").dialog('close');
	}
	function saveAddControlInfo() {
		var name = $.trim($("#name").val());
		var port = $.trim($("#port").val());
		if (!name) {
			$.dtmessagebox.alert("请输入控制名称");
			return;
		}
		if (!port) {
			$.dtmessagebox.alert("请输入端口号");
			return;
		}
		if (isNaN(port)) {
			$.dtmessagebox.alert("端口号为数字");
			return;
		}
		$.post("busi/greenHouseAndPoint!saveControlPortInfo.action", {
			'name' : name,
			'port' : port,
			'equipId' : selectNodeId
		}, function(data) {
			if (data.flag) {
				$.dtmessagebox.alert("添加成功");
				equipControlInfoResult.dtpaginggrid("reload");
				hideDialog();
			} else {
				$.dtmessagebox.alert(data.msg);
			}
		});
	}
	function saveAddNodeInfo() {
		var port = $.trim($("#nodeport").val());
		var controlId = $("#nodecontrolid").val();
		if (!port) {
			$.dtmessagebox.alert("请输入端口号");
			return;
		}
		if (isNaN(port)) {
			$.dtmessagebox.alert("端口号为数字");
			return;
		}
		if (controlId == "-1") {
			$.dtmessagebox.alert("请选择控制器");
			return;
		}
		$.post("busi/greenHouseAndPoint!saveNodeControlInfo.action", {
			'port' : port,
			'nodeId' : selectNodeId,
			'equipId' : controlId
		}, function(data) {
			if (data.flag) {
				$.dtmessagebox.alert("添加成功");
				equipNodeInfoResult.dtpaginggrid("reload");
				hideNodeDialog();
			} else {
				$.dtmessagebox.alert(data.msg);
			}
		});
	}
</script>
</html>