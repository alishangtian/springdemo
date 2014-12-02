<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<form id="add_new_role_form" name="add_new_role_form">
	<input type="hidden" id="new_roleId" name="new_roleId"
		value="${sysRole.roleId}" />
	<table style="width: 100%; height: auto;" class="view-table"
		cellpadding="0" cellspacing="0">
		<tr>
			<td>角色名称：</td>
			<td><dt:input id="new_roleName" name="new_roleName"
					required="true" prompt="角色名称" length="30" immValidate="true"
					value="${sysRole.roleName}"></dt:input>
			</td>
		</tr>
		<tr>
			<td>角色描述：</td>
			<td><dt:input id="new_roleDesc" name="new_roleDesc" length="255"
					immValidate="true" value="${sysRole.roleDesc}"></dt:input>
			</td>
		</tr>
	</table>
</form>