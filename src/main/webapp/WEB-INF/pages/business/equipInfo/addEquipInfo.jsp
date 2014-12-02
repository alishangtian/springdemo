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
<title>设备管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.equipInfomanager.js"></script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<table height="400px" width="100%">
			<tr>
				<td
					style="width: 200px; height: 400px; vertical-align: top; overflow: auto;">
					<dt:tree id="funcInfoTree" width="180px" height="400px"
						dataSource="${baseGreenHouseTreeList}" isSimpleData="true"
						treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
						nameCol="nodeTreeName" onClick="onSelectFuncAuth">
					</dt:tree>
				</td>
				<td
					style="height: 400px; vertical-align: top; border: 1px solid #AAAAAA;">
					<form action="equipInfoAction!saveEquipInfo.action"
						id="addEquipInfoForm" method="post">
						<input id="type" name="equipInfo.type" type="hidden" />
						<table class="condition" width="100%" align="center">
							<dt:input width="100" id="custId" inputType="hidden"
								name="equipInfo.custId"></dt:input>
							<tr>
								<th>设备基本信息</th>
							</tr>
							<tr>
								<th>物理设备ID：</th>
								<td><dt:input id="deviceId" name="equipInfo.deviceId"></dt:input>
								</td>
								<th>设备名称：</th>
								<td><dt:input id="name" name="equipInfo.name"></dt:input></td>
							<tr>
								<th>设备型号：</th>
								<td><dt:input id="model" name="equipInfo.model"></dt:input>
								</td>
								<th>设备类型：</th>
								<td>
									<div id="select_container">
										<select id="type_first" onchange="getNextLevelEquipType()">
											<option value="-20">-选择类型-</option>
											<c:forEach items="${ets}" var="et">
												<option value="${et.id}">${et.type}</option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th>设备厂家：</th>
								<td><select id="factory" name="equipInfo.factory">
										<c:forEach items="${factorys}" var="fa">
											<option value="${fa.valueName}">${fa.valueName}</option>
										</c:forEach>
								</select></td>
								<th>备注：</th>
								<td><dt:input id="remark" name="equipInfo.remark"></dt:input>
								</td>
							</tr>
							<tr>
								<th>设备属性信息</th>
							</tr>
							<c:forEach items="${values}" var="al">
								<tr>
									<th>${al.valueName}：</th>
									<td colspan="4"><dt:input id="${al.valueCode}"
											name="${al.valueCode}" value=""></dt:input></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="4" align="center"><dt:button
										id="addEquipInfosave" label="确定" type="ok" icon=""></dt:button>
									<dt:button id="cancelBtn" label="取消" type="ok" icon=""></dt:button>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>