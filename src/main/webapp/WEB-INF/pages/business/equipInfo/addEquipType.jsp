<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备类型管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<table height="200px" width="100%">
			<tr>
				<td
					style="height: 200px; vertical-align: top; border: 1px solid #AAAAAA;">
					<form action="equipInfoAction!saveEquipType.action"
						id="addEquipTypeForm" method="post">
						<dt:input id="level" inputType="hidden" name="equipType.level"></dt:input>
						<dt:input id="parentId" inputType="hidden"
							name="equipType.parentId"></dt:input>
						<table class="condition" width="100%" align="center">
							<tr>
								<th>设备类型名称：</th>
								<td><dt:input id="type" name="equipType.type"></dt:input></td>
							</tr>
							<tr>
								<th>设备类型描述：</th>
								<td><dt:input id="description" name="equipType.description"></dt:input>
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center"><dt:button
										id="addEquipTypeSub" label="确定" type="ok" icon=""></dt:button>
									<dt:button id="cancelEquipTypeSub" label="取消" type="ok" icon=""></dt:button>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#level").val($("#tl").val());
		$("#parentId").val($("#tpd").val());
		$("#addEquipTypeSub").click(function() {
			$("#addEquipTypeForm").submit();
		}); 
		$("#cancelEquipTypeSub").click(function() {
			window.location.href="<%=contextPath%>"+"busi/equipInfoAction!equipTypeManager.action";
		});
	});
</script>
</html>