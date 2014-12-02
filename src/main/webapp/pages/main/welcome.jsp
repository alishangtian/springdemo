<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
  <head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>主页面</title>
	<dt:comppath></dt:comppath>
	<link rel="stylesheet" href="css/func.css" type="text/css"
		charset="utf-8" />
	<link rel="stylesheet" href="css/func.welcome.layout.css"
		type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="pages/main/css/func.welcom.css"
		type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
	<!--<script src="pages/main/js/func.ui.panel.js"></script>
	<script src="pages/main/js/welcome.js"></script>
	<script src="pages/main/js/json2.js"></script>-->
</head>
<body>
	<!--<div class="wrap">
		<div class="operbar">
			<div id="layOutOption" class="layOut"></div>
			<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only save">保存</button>
			<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only add">添加</button>
			<span class="ui-icon ui-icon-close ui-icon-close-default operbar-close">close</span>
		</div>
	</div>
	<dt:dialog id="addFuncDialog" title="添加功能组件" onOpen="initFuncDialog" >
			<table>
				<tr>
					<td>功能组件列表</td>
					<td><dt:comboBox keyField="pageComponentId" valueField="title" id="pageComponentList" url="sm/welcomeAction!doGetPageComponents.action" selectIndex="0"></dt:comboBox>
					</td>
				</tr>
				<tr>
					<td>添加位置</td>
					<td><select id="componentLocation"></select></td>
				</tr>
			</table>
			<dt:dialogbutton text="确定" onClick="confirmClick"></dt:dialogbutton>
			<dt:dialogbutton text="取消" onClick="cancelClick"></dt:dialogbutton>
	</dt:dialog>!-->
</body>
</html>