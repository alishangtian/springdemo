<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<%
	String contextPath = request.getContextPath();
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
<style type="text/css">
	#msgDownDiv{
		left: 242px; 
		display: none; 
		float:right;
		position: relative;
		z-index:100;
		width: 100px;
		top: 43px;
		background-color: rgb(244,244,244);
	}
</style>
<div id="headcontent" class="head">
	<a href="#"><img src="<%=contextPath%>/pages/main/images/logo.png" title="云运维管理平台" class="logo" />
	</a>
	<!--logo-->
	<div class="littleF">
		<!--顶部小功能-->
		<ul>
			<li><span class="user"><%= ((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName()%>
			</span>
			</li>
			<li><span class="otherF"><a id="mainPageLink" href="#" class="selected">主页</a>
			</span>
			</li>
			<li id="msgDownLi">
				<span id="msg_span" class="otherF" style="position: relative;">
					<span id="msg_image_span" class="otherF" style=" background-image:url(<%=basePath%>pages/main/image/no_msg.gif);padding-bottom: 8px;background-position: center center;">
				</span>
			<span id="msgCount">(0)</span>
			</span>
			</li>
			<li><span class="otherF"><a id="modifyPasswordLink" href="sm/SysUser!modifyPasswordInit.action">修改密码</a>
			</span>
			</li>
			<li><span class="otherF">帮助
			</span>
			</li>
			<li><span class="otherF"><a id="logOutLink" href="sm/Logout.action">退出</a>
			</span>
			</li>
		</ul>
	</div>
		<div id="msgDownDiv">
			<ul id="msgDownUl">
			</ul>
		</div>
	<div class="clear"></div>
	<div class="topmenu"></div>
</div>
<script src="pages/main/js/func.ui.topmenu.js"></script>
<script src="pages/main/js/func.top.js"></script>