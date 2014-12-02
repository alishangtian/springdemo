<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<div class="tree-left">
			<dt:tree id="funcInfoTree" width="180px" height="400px"
				dataSource="${funcTreeList}" isSimpleData="true"
				treeNodeKey="nodeTreeId" treeNodeParentKey="parentId"
				nameCol="nodeTreeName" onClick="onSelectFuncAuth">
			</dt:tree>
</div>
<div class="func-item-oper">
	<table class="query-form" width="100%">
		<thead>
			<tr>
				<th>功能</th>
				<th>功能操作</th>
			</tr>
		</thead>
		<tbody id="funcDetail_Div">
		</tbody>
	</table>
</div>
<div class="query-form-btns">
	<func-tags:funcItemAuth funcItemId="1020010104">
		<dt:button id="saveSysUserFuncInfoBtn" label="保存" width="100"
			height="30" click="saveSysUserFuncInfo"></dt:button>
	</func-tags:funcItemAuth>
</div>
