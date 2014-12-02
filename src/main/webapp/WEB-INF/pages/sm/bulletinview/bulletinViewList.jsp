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
	function bulletinViewOwnerAll(){
		var url = 'sm/BulletinAction!ownerAllList.action';
		$.dtdialog.showModal({title: '全部公告查看',
		width:500,
		height:300,
		url:  url,
		buttons:{},
		open:function() {},
		close:function() {}
		});
	}
	function bulletinView(bulletinId){
		//var url = 'sm/BulletinAction!ownerView.action?id='+bulletinId;
		//dt$.showModalDialog({title: '公告查看',
		//width:500,
		//height:300,
		//url:  url,
		//buttons:{},
		//open:function() {},
		//close:function() {}
		//});
		var height = 300;
		var width = 500;
		var cleft=(screen.availWidth-width)*0.5; 
		var ctop=(screen.availHeight-height)*0.5;
		window.open ('sm/BulletinAction!ownerView.action?id='+bulletinId, '_blank', 'height='+height+', width='+width+', top='+ctop+', left='+cleft+', toolbar=no, menubar=no,scrollbars=yes,resizable=no, titlebar=no, location=no, status=yes');
		
	}
	function formatAct(cellvalue, options, rowObject) {
		var result = [];
		result.push('<div style="border: 1px none; float: left; margin: 4px;">');
		result.push("&nbsp;&nbsp;<img src='pages/main/images/icons/arrow4.png'>&nbsp;&nbsp;&nbsp;&nbsp;");
		result.push('</div>');
		result.push('<div style="font-size: 15px; float: left; border: 1px none; margin: 4px;">');
	 	result.push("<a href='javascript:void(0);' style='text-decoration:none' onclick='bulletinView(\""+rowObject.bulletinId+"\")'>");
	 	//result.push("<a href='<%=request.getContextPath()%>/sm/BulletinAction!view.action?id="+rowObject.bulletinId+"'>");
	 	if(rowObject.priority=='Z'){
	 		result.push('<span style="color:red;"><特急\>&nbsp;&nbsp;</span>');
	 	}else if(rowObject.priority=='Y'){
	 		result.push('<span style="color:#A00000;"><加急\>&nbsp;&nbsp;</span>');
	 	}
	 	result.push(rowObject.bulletinTitle);
	 	
	 	
	 	result.push('</a>');
	 	result.push('</div>');
	 	
		return result.join("");
	}
</script>
</head>
<body>
<body>
	<dt:grid id="bulletinViewListGrid" url="sm/BulletinAction!loginBulletinList.action" showHideButton="false" altRows="false" showPager="true" rowNum="10"
		 width="100%" height="245" shrinkToFit="true" requestType="post">
		<dt:gridColumn name="公告标题" editable="false" formatter="formatAct"></dt:gridColumn>
	</dt:grid>
</body>
</body>
</html>