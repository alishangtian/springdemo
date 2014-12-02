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
<title>缓存管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/func.domainmanager.js"></script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<div class="ui-widget-header func-header">
			值域管理 <span style="float: right;"> <a id="addDomain"
				href="javascript:void(0)" onclick="addDomainDialogShow();">新增</a> <a
				id="editDomain" href="javascript:void(0)"
				onclick="editDomainDialogShow();">编辑</a> <a id="deleteDomain"
				href="javascript:void(0)" onclick="deleteDomain();">删除</a> <a
				id="getDomainValue" href="javascript:void(0)"
				onclick="getDomainValue();">编辑取值</a> </span>
		</div>
		<table class="condition" width="100%" align="center">
			<tr>
				<th>值域类型：</th>
				<td>
					<div>
						<select id="queryDomainType" name="queryDomainType">
							<!-- 构建"全部"选项 -->
							<core-tags:options domainCode="DOMAIN.DOMAIN_TYPE"
								optionalValue="all" />
						</select>
					</div>
				</td>
				<th>值域代码：</th>
				<td><dt:input width="100" id="queryDomainCode"></dt:input>
				</td>
				<th>值域名称：</th>
				<td><dt:input width="100" id="queryDomainName"></dt:input>
				</td>
			</tr>
			<tr>
				<td colspan="6" align="center"><dt:button id="queryDomainBtn"
						label="查询" type="ok" />
				</td>
			</tr>
		</table>
		<dt:grid id="domainResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="300px" onSelectRow="hideDomainValueDiv">
			<dt:gridColumn name="entity.domainCode" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="idMap.domainType" label="值域类型"></dt:gridColumn>
			<dt:gridColumn name="entity.domainName" label="值域名称"></dt:gridColumn>
			<dt:gridColumn name="entity.domainCode" label="值域代码"></dt:gridColumn>
			<dt:gridColumn name="entity.maxValue" label="最大值"></dt:gridColumn>
			<dt:gridColumn name="entity.minValue" label="最小值"></dt:gridColumn>
			<dt:gridColumn name="entity.defValue" label="缺省值"></dt:gridColumn>
			<dt:gridColumn name="entity.remark" label="备注"></dt:gridColumn>
		</dt:grid>

		<dt:dialog id="addDomainDialog" title="新增值域" width="70%">
			<form id="addDomainForm">
				<table border="1" width="100%"
					style="background-color: #FFFFFF; border-collapse: collapse; margin-top: 3px;">
					<tr>
						<td>值域名称</td>
						<td><dt:input id="domainName" name="domainName" prompt="值域名称"
								required="true" length="64" immValidate="true" /></td>
					</tr>
					<tr>
						<td>值域代码</td>
						<td><dt:input id="domainCode" name="domainCode" prompt="值域代码"
								required="true" length="60" immValidate="true" /></td>
					</tr>
					<tr>
						<td>值域类型</td>
						<td>
							<div>
								<select id="domainType" name="domainType">
									<core-tags:options domainCode="DOMAIN.DOMAIN_TYPE" />
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>最大值</td>
						<td><dt:input id="maxValue" name="maxValue" prompt="最大值"
								length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<td>最小值</td>
						<td><dt:input id="minValue" name="minValue" prompt="最小值"
								length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<td>缺省值</td>
						<td><dt:input id="defValue" name="defValue" prompt="缺省值"
								length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<td>备注</td>
						<td><dt:input id="remark" name="remark" prompt="备注"
								length="256" immValidate="true" /></td>
					</tr>
				</table>
			</form>
			<dt:dialogbutton text="确定" onClick="addDomain"></dt:dialogbutton>
			<dt:dialogbutton text="取消" onClick="cancelAddDomainDialog"></dt:dialogbutton>
		</dt:dialog>

		<dt:dialog id="editDomainDialog" title="编辑值域" width="70%">
			<form id="editDomainForm">
				<table border="1" width="100%"
					style="background-color: #FFFFFF; border-collapse: collapse; margin-top: 3px;">
					<tr>
						<td>值域名称</td>
						<td><dt:input id="domainNameEdit" name="domainNameEdit"
								prompt="值域名称" required="true" length="64" immValidate="true" />
						</td>
					</tr>
					<tr>
						<td>值域代码</td>
						<td><dt:input id="domainCodeEdit" name="domainCodeEdit"
								prompt="值域代码" required="true"  length="60"
								immValidate="true" /></td>
					</tr>
					<tr>
						<td>值域类型</td>
						<td>
							<div>
								<select id="domainTypeEdit" name="domainTypeEdit">
									<core-tags:options domainCode="DOMAIN.DOMAIN_TYPE" />
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>最大值</td>
						<td><dt:input id="maxValueEdit" name="maxValueEdit"
								prompt="最大值" length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<td>最小值</td>
						<td><dt:input id="minValueEdit" name="minValueEdit"
								prompt="最小值" length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<td>缺省值</td>
						<td><dt:input id="defValueEdit" name="defValueEdit"
								prompt="缺省值" length="30" immValidate="true" /></td>
					</tr>
					<tr>
						<td>备注</td>
						<td><dt:input id="remarkEdit" name="remarkEdit" prompt="备注"
								length="256" immValidate="true" /></td>
					</tr>
				</table>
			</form>
			<input type="hidden" id="domainIdEdit" name="domainIdEdit" />
			<dt:dialogbutton text="确定" onClick="editDomain"></dt:dialogbutton>
			<dt:dialogbutton text="取消" onClick="cancelEditDomainDialog"></dt:dialogbutton>
		</dt:dialog>
	</div>
	<font color="red">说明：点击“编辑取值”链接，查看值域取值信息。</font>
	<br />
	<br />
	<div id="domainValueDiv" style="display: none;"
		class="ui-widget-content func-panel">
		<div class="ui-widget-header func-header">
			值域取值信息 <span style="float: right;"> <input type="hidden"
				id="domainIdUsed" /> <input type="hidden" id="domainCodeUsed" /> <a
				id="enableDomainValue" href="javascript:void(0)"
				onclick="editDomainValueSts('T');">启用</a> <a id="disableDomainValue"
				href="javascript:void(0)" onclick="editDomainValueSts('F');">停用</a>
				<a id="addDomainValue" href="javascript:void(0)"
				onclick="addDomainValueDialogShow();">新增</a> <a id="editDomainValue"
				href="javascript:void(0)" onclick="editDomainValueDialogShow();">编辑</a>
				<a id="deleteDomainValue" href="javascript:void(0)"
				onclick="deleteDomainValue();">删除</a> </span>
		</div>
		<dt:grid id="domainValueResult" shrinkToFit="true" multiSelect="true"
			jsonRoot="_self" dataType="json" showPager="false" width="100%"
			height="100%">
			<dt:gridColumn name="entity.valueCode" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="entity.domainName" label="值域名称"></dt:gridColumn>
			<dt:gridColumn name="entity.domainCode" label="值域代码"></dt:gridColumn>
			<dt:gridColumn name="entity.valueName" label="取值名称"></dt:gridColumn>
			<dt:gridColumn name="entity.valueCode" label="取值代码"></dt:gridColumn>
			<dt:gridColumn name="idMap.enabledFlag" label="启用状态"></dt:gridColumn>
			<dt:gridColumn name="entity.displayOrder" label="显示顺序"></dt:gridColumn>
		</dt:grid>
		<dt:dialog id="addDomainValueDialog" title="新增值域取值" width="70%">
			<form id="addDomainValueForm">
				<table border="1" width="100%"
					style="background-color: #FFFFFF; border-collapse: collapse; margin-top: 3px;">
					<tr>
						<td>取值名称</td>
						<td><dt:input id="valueName" name="valueName" prompt="取值名称"
								required="true" length="64" immValidate="true" /></td>
					</tr>
					<tr>
						<td>取值代码</td>
						<td><dt:input id="valueCode" name="valueCode" prompt="取值代码"
								required="true" length="64" immValidate="true" /></td>
					</tr>
					<tr>
						<td>启用标识</td>
						<td>
							<div>
								<select id="enabledFlag" name="enabledFlag">
									<core-tags:options domainCode="COMMON.ENABLED_FLAG" />
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>显示顺序</td>
						<td><dt:input id="displayOrder" name="displayOrder"
								prompt="显示顺序" required="true" length="3" immValidate="true" />
						</td>
					</tr>
				</table>
			</form>
			<dt:dialogbutton text="确定" onClick="addDomainValue"></dt:dialogbutton>
			<dt:dialogbutton text="取消" onClick="cancelAddDomainValueDialog"></dt:dialogbutton>
		</dt:dialog>
		<dt:dialog id="editDomainValueDialog" title="编辑值域取值" width="70%">
			<form id="editDomainValueForm">
				<table border="1" width="100%"
					style="background-color: #FFFFFF; border-collapse: collapse; margin-top: 3px;">
					<tr>
						<td>取值名称</td>
						<td><dt:input id="valueNameEdit" name="valueNameEdit"
								prompt="取值名称" required="true" length="64" immValidate="true" />
						</td>
					</tr>
					<tr>
						<td>取值代码</td>
						<td><dt:input id="valueCodeEdit" name="valueCodeEdit"
								prompt="取值代码" required="true"  length="64"
								immValidate="true" /></td>
					</tr>
					<tr>
						<td>启用标识</td>
						<td>
							<div>
								<select id="enabledFlagEdit" name="enabledFlagEdit">
									<core-tags:options domainCode="COMMON.ENABLED_FLAG" />
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>显示顺序</td>
						<td><dt:input id="displayOrderEdit" name="displayOrderEdit"
								prompt="显示顺序" required="true" length="3" immValidate="true" />
						</td>
					</tr>
				</table>
			</form>
			<input type="hidden" id="domainIdUsedEdit" name="domainIdUsedEdit" />
			<dt:dialogbutton text="确定" onClick="editDomainValue"></dt:dialogbutton>
			<dt:dialogbutton text="取消" onClick="cancelEditDomainValueDialog"></dt:dialogbutton>
		</dt:dialog>
	</div>
</body>
</html>