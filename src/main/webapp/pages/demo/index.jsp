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
	<base href="<%=basePath%>">
	<title>基础平台demo演示</title>
	<dt:comppath></dt:comppath>
	<link rel="stylesheet" href="css/func.css" type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="./css/index.css" type="text/css" charset="utf-8" />
	<script type="text/javascript" src="./js/index.js"></script>
</head>
<body>
	<table style="height: 500px; width: 100%;vertical-align: top;" border="0">
		<tr style="vertical-align: top;"> 
			<td style="width: 60%; height: 100px;">
				<jsp:include page="customer.jsp" />
			</td>
			<td rowspan="2" height="100%">
				<jsp:include page="book.jsp" />
			</td>
		</tr>
		<tr style="vertical-align: top;">
			<td>
				<jsp:include page="content.jsp" />
			</td>
		</tr>
	</table>
</body>
</html>