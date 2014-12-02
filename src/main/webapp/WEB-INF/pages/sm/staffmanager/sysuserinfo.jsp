<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<dt:grid id="sysUserResultList" jsonRoot="resultList"
	dataSource="${sysUserList}" multiSelect="true" shrinkToFit="true"
	width="100%" height="320px" dataType="json" showPager="false"
	sortable="true">
	<dt:gridColumn name="userId" hidden="true" key="true" />
	<dt:gridColumn name="userId" sortable="true" label="用户编号" width="80" />
	<dt:gridColumn name="loginName" sortable="true" label="登录名称" width="80" />
	<dt:gridColumn name="userExpTime" sortable="true" label="用户失效时间"
		width="120" />
	<dt:gridColumn name="pwdExpTime" sortable="true" label="密码失效时间"
		width="120" />
</dt:grid>
<div class="query-form-btns">
	<func-tags:funcItemAuth funcItemId="1010020105">
		<dt:button id="removeSysUserBtn" label="注销用户" click="removeSysUsers" />&nbsp;&nbsp;&nbsp;
	</func-tags:funcItemAuth>
</div>