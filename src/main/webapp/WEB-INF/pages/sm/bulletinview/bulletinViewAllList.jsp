<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>公告查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<dt:comppath></dt:comppath>
<script type="text/javascript">
	function bulletinView(bulletinId){
		var url = 'sm/BulletinAction!ownerView.action?id='+bulletinId;
		$.dtdialog.showModal({title: '公告查看',
		width:800,
		height:400,
		url:  url,
		buttons:{},
		open:function() {},
		close:function() {}
		});
	}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="2" cellspacing="0">
	<s:iterator value="ownerBulletinList">
		<tr height="20">
			<td>
				<a style="color:blue;" href="javascript:void(0);" onclick="bulletinView(${bulletinId})">
					<div style="font-size:15px;">
						<s:hidden id="bulletinViewId" name="bulletinId"></s:hidden>
						<span style="color:red;"><s:if test="priority==\"Z\"">特急</s:if></span>
						<span style="color:#A00000;"><s:if test="priority==\"Y\"">加急</s:if></span>
						<s:property value="bulletinTitle" />
					</div>
				</a>
			</td>
		</tr>
	</s:iterator>
</table>
</body>
</html>