<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>农业智能</title>
	<link rel="stylesheet" href="<%=path%>/dtui/theme/default/dtui.all.css" type="text/css" charset="utf-8" />
	<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
	<script type="text/javascript">var path = '<%=path%>';</script>
</head>
<body class="main-body">
		<div class="main">
			<div class="head-top">
				<span class="logo-text">智慧温室农产品健康生产云服务平台</span>
				<ul class="head-tip"><li><a href="javascript:void(0)">我的门户</a></li><li><a href="javascript:void(0);">设为主页</a></li><li style="border:0 none"><a href="javascript:void(0)">添加收藏</a></li><li class="li-clear"></li><li class="two-row"><a href="javascript:void(0)"><%=((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName() %>,欢迎你</a></li><li class="two-row" style="border:0 none"><a href="javascript:Main.exitSys();">退出</a></li></ul>
			</div>
			<div id="topmenu" class="top-menu"></div>
			<div id="worktab" style="display:none;">
				<ul>
					<li><a href="#tabs-1"></a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>
				</ul>
				<div id="tabs-1">
					<p></p>
				</div>
			</div>
		</div>
	<script type="text/javascript" src="<%=path %>/js/core/dt.moduleloader.js"></script>
	<script type="text/javascript">
		(function(w){
			dtloader.define('jquery,jqueryUi,dtTab,dtNav,dtButton,main',
				function(){
					Main.init();
			});
		})(window);
	</script>
	</body>
</html>