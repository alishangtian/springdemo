<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>合同管理信息系统</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="css/func.css" type="text/css"
	charset="utf-8" />
<link rel="stylesheet" href="pages/main/css/func.ui.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="pages/main/css/func.css.main.css"
	type="text/css" charset="utf-8" />

</head>
<body>
	<div class="wrap">
		<!--头部，包含logo,一级功能页签，二级功能导航和小功能部分--->
		<jsp:include page="top.jsp" />
		<!--身体--包含左导航和操作区----------------------------->
		<!-- <div class="body" style="position:absolute;display:inline;width:100%;"> -->
		<div class="body" style="position:absolute;display:inline;width:1260px;">
		    <!-- <div style="float:left;position:relative;width:19%;_width:15%;"> -->
		    <div id="leftDivId" style="float:left;position:relative;width:250px;_width:245px;">
		       <jsp:include page="left.jsp" />
		    </div>
		    <!-- <div style="float:left;width:80%;_width:75%;"> -->
		    <div id="rightDivId" style="float:left;position:relative;width:1005px;_width:1000px;">
		       <jsp:include page="right.jsp" />
		    </div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>