<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<table width="100%" class="query-form">
		<tr>
			<th width="30%">角色名称：</th>
			<td><label>${sysRole.roleName}</label></td>
		</tr>
			<th>创建时间：</th>
			<td><label>${sysRole.createTime}</label></td>
		</tr>
		<tr>
			<th>状态时间：</th>
			<td><label>${sysRole.stsTime}</label></td>
		</tr>
		<tr>
			<th>角色描述：</th>
			<td><label>${sysRole.roleDesc}</label></td>
		</tr>
	</table>
<div class="query-form-btns">
		<func-tags:funcItemAuth funcItemId="1020020102">
			<dt:button id="changeRoleInfoBtn" label="修改" click="openChangeRole"></dt:button>
		</func-tags:funcItemAuth>
		<func-tags:funcItemAuth funcItemId="1020020105">
			<dt:button id="removeRoleBtn" label="注销" click="removeRole"></dt:button>
		</func-tags:funcItemAuth>
</div>