<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>发布范围部门</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<script type="text/javascript" src="js/func.depttree.js"></script>
<%-- <script type="text/javascript" src="js/dt.datatag.js"></script> --%>
<script type="text/javascript" src="js/jquery.ui.datatag.js"></script>
<style type="text/css">
	.datatag{
		float: left;
		background-color: #A6C9E2;
		margin: 2px;
	}
	.datatag .ui-icon-close{
		padding: 0px;
		margin: 0px;
	}
</style>
<script type="text/javascript">
	$(function() {
		bulletinPublishDeptTag = $("#selectDeptBullPubRangeId").datatag( {
			"data": [],
			"namefield": "name",
			"classname": "datatag",
			"width": null,
			"onclose": function(data){}	
		});
		//赋值
		if(typeof publishTagDept != 'undefined'){
			if(publishTagDept.getData().selector==null){
				bulletinPublishDeptTag.datatag("reset",publishTagDept.getData());
			}
		}
		initDeptTree("#selectDeptDiv", function(){
			var deptName = arguments[2].deptName;
			bulletinPublishDeptTag.datatag("add",{"name": deptName,'id': arguments[2].deptId});
		});
	});
	
</script>
</head>
<body>
	<div id="selectDeptDiv" style="height:55%;"></div>
	<hr>
	<table width="100%" align="center" class="condition">
		<tr>
			<th width="10%" align="left">发布给：</th>
			<td width="90%" valign="middle">
				<div id="selectDeptBullPubRangeId" style="width: 95%; height: 60px"></div>
			</td>
		</tr>
	</table>
</body>
</html>