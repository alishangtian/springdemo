<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页框架</title>
</head>
<body>
	<p>你进入了主页面</p>
	<p>
		HttpSession：<%= ((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName()%>
	</p>
	<p><s:label>Session Map：</s:label><s:property value="#session.LOGIN_USER.loginName" /></p>
	<a href="sm/Cache.action">缓存管理</a><br>
	<a href="sm/mainMenuAction">主页面</a><br>
	<a href="sm/SysUser!resetPasswordInit">密码重置</a><br>
	<a href="sm/SysUser!modifyPasswordInit">密码修改</a><br>
	<a href="sm/SysUser!removeSysUserInit">用户注销</a><br>
	<a href="sm/Logout.action">登出</a><br>
</body>
</html>