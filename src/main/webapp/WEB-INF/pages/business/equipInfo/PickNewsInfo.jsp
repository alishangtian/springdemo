<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.welcome.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
	<body>
		<table class="query-form" width="100%">
			<s:iterator value="newsList">
				<s:property value="time" /> 
				<s:property value="baseName" />
				<s:property value="houseName" />生产...
				<s:property value="item" />
				<s:property value="figure" />吨
			</s:iterator>
			

		</table>

</body>
</html>