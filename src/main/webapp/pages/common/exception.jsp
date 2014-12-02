<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String contextPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统异常</title>
<link href="<%=contextPath%>/css/func.exception.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="abnormal">
		<h4>系统运行出现异常</h4>
		<div class="text">
			<label>异常信息</label>
			<s:property value="exception" />
		</div>
	</div>
</body>
</html>