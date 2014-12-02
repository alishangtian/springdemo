<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="dt" uri="/dttag"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="t" uri="/cacheTag"%>
<script type="text/javascript">
function formatTime(cellvalue, options, rowObject){
	if(cellvalue!=null&&cellvalue!=""){
		cellvalue=cellvalue.replace("T"," ");
	}else{
		cellvalue="";
	}
	return cellvalue;
}
</script>
	<form id="queryForm1">
	<table class="query-form" width="100%">
		<tr>
			<th>使用者类型：</th>
			<td>
				<t:dmComboBox id="query_OwnerType" prompt="帐号类型" name="sysUserQuery.ownerType" domainCode="SYS_USER.OWNER_TYPE" hasNull="false" selectIndex="0"></t:dmComboBox>
			</td>
			<th>使用者名称：</th>
			<td><dt:input id="query_OwnerName" name="sysUserQuery.ownerName" height="25px" width="150px"></dt:input>
			</td>
			<th>登录名：</th>
			<td><dt:input id="query_LoginName" name="sysUserQuery.loginName" height="25px" width="150px"></dt:input>
			</td>
		</tr>
	</table>
	<input id="authId" type="hidden" name="sysUserQuery.auth" value="true"/>
	</form>
	<div class="query-form-btns">
		<input type="checkbox" id="query_isauth" checked onclick="authChange()"/>已授权
		<dt:button id="querySysUserListBtn" label="查询" type="ok" click="queryRoleUserList"></dt:button>
	</div>
	<dt:grid id="sysUserQueryResult" jsonRoot="resultList" 
		dataType="json" onLoadComplete="roleUserLoaded" url="sm/roleManagerAction!getSysUserPaging.action?'+$('#queryForm1').serialize()+'"  
		multiSelect="true" showPager="true" sortable="true" width="100%" height="250px">
		<dt:gridColumn name="sysUser.userId" hidden="true" key="true"></dt:gridColumn>
		<dt:gridColumn name="checked" hidden="true"></dt:gridColumn>
		<dt:gridColumn name="sysUser.loginName" sortable="true" label="登录名称"></dt:gridColumn>
		<dt:gridColumn name="sysUser.userOwnerName" sortable="true"
			label="使用者名称"></dt:gridColumn>
		<dt:gridColumn name="sysUser.userExpTime" sortable="true"
			label="用户失效时间" formatter="formatTime"></dt:gridColumn>
		<dt:gridColumn name="sysUser.pwdChgTime" sortable="true"
			label="密码设置时间" formatter="formatTime"></dt:gridColumn>
		<dt:gridColumn name="sysUser.pwdExpTime" sortable="true"
			label="密码失效时间" formatter="formatTime"></dt:gridColumn>
	</dt:grid>
	<br/><br/>
	<div class="query-form-btns">
		<func-tags:funcItemAuth funcItemId="1020020103">
			<dt:button id="saveSysRoleUserInfoBtn" label="保存"
				click="saveSysRoleUserInfo"></dt:button>
		</func-tags:funcItemAuth>
	</div>
