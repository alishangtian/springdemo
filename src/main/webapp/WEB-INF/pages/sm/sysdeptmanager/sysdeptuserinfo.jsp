<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<dt:grid id="sysDeptUserResultList" jsonRoot="resultList" width="100%"
	height="310px" dataSource="${sysUserList}" multiSelect="true"
	shrinkToFit="true" dataType="json" showPager="false" sortable="true">
	<dt:gridColumn name="userId" hidden="true" key="true" />
	<dt:gridColumn name="userId" sortable="true" width="80" label="用户编号" />
	<dt:gridColumn name="loginName" sortable="true" width="80" label="登录名称" />
	<dt:gridColumn name="userExpTime" sortable="true" width="120"
		label="用户失效时间" />
	<dt:gridColumn name="pwdExpTime" sortable="true" width="120"
		label="密码失效时间" />
</dt:grid>
<div class="tab-foot">
	<func-tags:funcItemAuth funcItemId="1010010104">
		<dt:button id="removeSysUserBtn" label="注销用户" click="removeSysUsers" />&nbsp;&nbsp;&nbsp;
	</func-tags:funcItemAuth>
</div>
