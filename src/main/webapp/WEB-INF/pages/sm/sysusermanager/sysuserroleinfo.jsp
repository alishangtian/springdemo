<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<input type="hidden" id="userrole_checked_roleId"
	name="userrole_checked_roleId" />
<div class="role-names two-b fl">
	<h3>用户角色</h3>
	<div id="roleInfo_RoleList_Div"></div>
	<div class="red">点击角色名称，可以查看角色对应的权限信息</div>
</div>
<div class="role-auth two-b">
	<h3><i id="roletitle"></i>用户角色</h3>
	<div class="tree-left">
	<dt:tree id="role_funcInfoTree" width="175px" height="360px" dataSource="${funcTreeList}" isSimpleData="true"
		treeNodeKey="nodeTreeId" treeNodeParentKey="parentId" nameCol="nodeTreeName" onClick="onShowFuncAuth" />
	</div>
	<div class="func-item-oper">
		<table class="query-form" width="100%">
			<thead>
				<tr>
					<th>功能</th>
					<th>功能操作</th>
				</tr>
			</thead>
			<tbody id="role_funcDetail_Div">
			</tbody>
		</table>
	</div>
</div>
<div class="clear"></div>
<div class="query-form-btns">
	<func-tags:funcItemAuth funcItemId="1020010103">
		<dt:button id="saveSysUserRoleInfoBtn" label="保存" width="100"
			click="saveSysUserRoleInfo" height="30"></dt:button>
	</func-tags:funcItemAuth>
</div>
<script type="text/javascript">
	loadSysUserRoleInfo($("#userIdHid").val());
</script>