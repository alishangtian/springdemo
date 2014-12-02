<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<title>用户登录</title>
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/new.style.css">
	<script type="text/javascript">var path='<%=path%>',errorMsg = '${actionErrors[0]}';;</script>
</head>
<body class="login-bg">
	<div class="login-form">
		<p><label for="userName">用户名</label><input type="text" class="input" name="userName" id="userName" value="admin"/></p>
		<p><label for="password">密　码</label><input type="password" class="input" name="password" id="password" value="12ab!@"/></p>
		<p class="verify-input"><label for="verifyCode">验证码</label><input class="input" id="verifyCode" type="text" name="verifyCode" value="1234"/>
		<img id="verifycodeId" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAAAeCAIAAAAekf9JAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAADdJREFUeNrszwEBAAAEAzD073w92Bqsk9QtU+coKSkpKSkpKSkpKSkpKSkpKSkpKSkpfSmtAAMA4NIDOQ/H250AAAAASUVORK5CYII="/>
		<a href="javascript:void(0);" id="refreshVefy" title="刷新">刷新</a></p>
	<%-- 	<p class="other">
			<input type="checkbox"/><span class="">记住密码</span>
			<a href="javascript:void(0)" class="fr">忘记密码？</a>
		</p> --%>
		<p class="login-btns">
			<a href="javascript:void(0)" class="login-btn" title="登录" id="btnLogin">登录</a>
		</p>
	</div>
	<script type='text/javascript' src='<%=path %>/js/core/dt.moduleloader.js'></script>
	<script type="text/javascript">
		(function(w){
			dtloader.define('jquery,jqueryUi,verifycode,md5,login',function(){
				login.init();
				$('.login-bg').show();
			});
		})(window);
		if(errorMsg) alert(errorMsg);
	</script>
</body>
</html>