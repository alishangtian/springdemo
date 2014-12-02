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
<title>设备编辑</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/busi.common.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.equipInfomanager.js"></script>
</head>
<body>
	<div id="topmenu" class="ui-query-topmenu">设备编辑</div>
	<div class="ui-widget-content func-panel">
		<table height="400px" width="100%">
			<tr>
				<%--  <td
				style="width: 200px; height: 400px; vertical-align: top; overflow: auto;">
				<dt:tree id="funcInfoTree" width="180px" height="400px"
					dataSource="${baseGreenHouseTreeList}" isSimpleData="true"
					treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
					nameCol="nodeTreeName" onClick="onSelectFuncAuth">
				</dt:tree></td>  --%>
				<td
					style="height: 400px; vertical-align: top; border: 1px solid #AAAAAA;">
					<form action="equipInfoAction!updateEquipInfo.action"
						id="updateEquipInfoForm" method="post">
						<dt:input width="100" id="id" inputType="hidden"
							value="${equipInfo.id}" name="equipInfo.id"></dt:input>
						<table class="condition" width="100%" align="center">
							<tr>
								<th>设备基本信息</th>
							</tr>
							<tr>
								<th>所属用户：</th>
								<td><select name="equipInfo.custId" id="custId">
										<c:forEach items="${customerList}" var="al">
											<option value="${al.custId}"
												<c:if test="${al.custId==equipInfo.custId}">selected='selected'</c:if>>${al.name}</option>
										</c:forEach>
								</select></td>
								<dt:input width="100" id="equipSensorsGhouseid"
									inputType="hidden" value="${esglist.id}"
									name="equipSensorsGhouseid"></dt:input>
							</tr>
							<tr>
								<th>物理设备ID：</th>
								<td><dt:input id="deviceId" name="equipInfo.deviceId"></dt:input>
								</td>
								<th>设备名称：</th>
								<td><dt:input id="name" name="equipInfo.name"></dt:input>
								</td>
							<tr>
								<th>设备型号：</th>
								<td><dt:input id="model" name="equipInfo.model"></dt:input>
								</td>
								<th>设备类型：</th>
								<td>
									<div>
										<dt:input id="type" value="${equipInfo.type}" disabled="true"></dt:input>
									</div>
								</td>
							</tr>
							<tr>
								<th>设备厂家：</th>
								<td><dt:input id="factory" disabled="true"
										name="equipInfo.factory"></dt:input></td>
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
											name="${al.valueCode}" value="${al.domainCode}"></dt:input></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="4" align="center"><dt:button
										id="saveEquipInfo" label="确定" type="ok" icon=""></dt:button> <dt:button
										id="cancelBtn" label="取消" type="ok" icon=""></dt:button></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>