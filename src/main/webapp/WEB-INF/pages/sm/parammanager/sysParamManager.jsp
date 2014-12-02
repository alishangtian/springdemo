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
<title>系统参数管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/func.sysParammanager.js"></script>

</head>
<body>
	<div class="ui-widget-content func-panel">
		<div class="ui-widget-header func-header">
			系统参数管理 <span style="float: right;"> <a id="enableSysParamSts"
				href="javascript:void(0)" onclick="editSysParamSts('T');">启用</a> <a
				id="disableSysParamSts" href="javascript:void(0)"
				onclick="editSysParamSts('F');">停用</a> <a id="editSysParam"
				href="javascript:void(0)" onclick="editSysParamDialogShow();">编辑</a>
			</span>
		</div>
		<table class="condition" width="100%" align="center">
			<tr>
				<th>启用状态：</th>
				<td>
					<div>
						<select id="queryEnabledFlag" name="queryEnabledFlag">
							<!-- 构建"全部"选项 -->
							<core-tags:options domainCode="COMMON.ENABLED_FLAG"
								optionalValue="all" />
						</select>
					</div>
				</td>
				<th>参数代码：</th>
				<td><dt:input width="100" id="queryParamCode"></dt:input>
				</td>
				<th>参数名称：</th>
				<td><dt:input width="100" id="queryParamName"></dt:input>
				</td>
			</tr>
			<tr>
				<td colspan="6" align="center"><dt:button id="querySysParamBtn"
						label="查询" type="ok" />
				</td>
			</tr>
		</table>
		<dt:grid id="sysParamResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="300px">
			<dt:gridColumn name="entity.paramCode" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="entity.paramName" label="参数名称"></dt:gridColumn>
			<dt:gridColumn name="entity.paramCode" label="参数代码"></dt:gridColumn>
			<dt:gridColumn name="entity.paramValue" label="参数值"></dt:gridColumn>
			<dt:gridColumn name="idMap.enabledFlag" label="启用状态"></dt:gridColumn>
		</dt:grid>

		<dt:dialog id="editSysParamDialog" title="编辑系统参数" width="70%">
			<form id="editSysParamForm">
				<table border="1" width="100%"
					style="background-color: #FFFFFF; border-collapse: collapse; margin-top: 3px;">
					<tr>
						<td>参数名称</td>
						<td><dt:input id="paramNameEdit" name="paramNameEdit"
								prompt="参数名称"  required="true" length="30"></dt:input>
						</td>
					</tr>
					<tr>
						<td>参数代码</td>
						<td><dt:input id="paramCodeEdit" name="paramCodeEdit"
								prompt="参数代码"  required="true" length="30"
								immValidate="true"></dt:input></td>
					</tr>
					<tr>
						<td>参数值</td>
						<td><dt:input id="paramValueEdit" name="paramValueEdit"
								prompt="参数值" required="true" length="30" immValidate="true"></dt:input>
						</td>
					</tr>
				</table>
			</form>
			<input type="hidden" id="sysParamIdEdit" name="sysParamIdEdit" />
			<dt:dialogbutton text="确定" onClick="editSysParam"></dt:dialogbutton>
			<dt:dialogbutton text="取消" onClick="cancelEditSysParamDialog"></dt:dialogbutton>
		</dt:dialog>
	</div>
</body>
</html>