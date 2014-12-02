<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<title>主页</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
		<script type="text/javascript" src="<%=path%>/js/bmap.js"></script>
		<script type="text/javascript" src="<%=path%>/js/busi.welcome.js"></script>
		<style type="text/css">
		    #basemap{height:100%}
		    .anchorBL{display:none !important;}
		</style>
</head>
<body>
	<div class="tree-left">
		<dt:tree id="funcInfoTree" width="180px" height="400px" dataSource="${baseGreenHouseTree}" isSimpleData="true"
						treeNodeKey="nodeTreeId" treeNodeParentKey="parentId" nameCol="nodeTreeName" onClick="treeClick">
		</dt:tree>
	</div>
	<div class="con-right">
		<div id="basemap"></div>
	</div>
	<script type="text/javascript">
			(function(w){
				var treeHeight = document.getElementsByTagName('body')[0].clientHeight;
				if(parent!=w)
					treeHeight = parent.document.getElementById('worktab').clientHeight;
				document.getElementById('funcInfoTree').style.height=(treeHeight-55)+'px';
				document.getElementById('basemap').style.height=(treeHeight-55)+'px';
			})(window);
		
	</script>
	
</body>
</html>