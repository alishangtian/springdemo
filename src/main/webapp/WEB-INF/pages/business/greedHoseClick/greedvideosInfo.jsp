<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	<dt:comppath></dt:comppath>
	<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
	<script type="text/javascript">var path = '<%=path%>';</script>
	<script type="text/javascript" src="<%=path%>/js/busi.greedHoseClick.js"></script>
	<script src="<%=path%>/js/esl.js" type="text/javascript"></script>
	<script type="text/javascript">
		var contextPath = "<%=path%>";
		function vudeiFun(){
			$("#shipin").attr("style", "display:block;");  
			$("#imgId").attr("style", "display:none;");
		}
	</script>
</head>
<body>
<!-- 	<div class="con-right">
		<div class="alerm-area">
			<div  id="shipin" style="display:none;" >
				<embed src= "http://player.video.qiyi.com/0d0233fb6945e8bfdf61515af45bfc1f/0/39/w_19rs7o5yip.swf-albumId=2904044909-tvId=2904044909-isPurchase=0-cnId=25"  quality="high" width="600" height="400" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed> 
			</div>

		</div> 

	</div> -->
	<div class="cam-left">
		<h3>监控探头</h3>
		<p onclick="vudeiFun();"><i class="cam-icon" id="vudeiId"></i>视频1</p>
		<p onclick="vudeiFun();"><i class="cam-icon cam-on"></i>视频1</p>
	</div>
	<div class="cam-right">
		<div  id="shipin" style="display:none;" >
<!-- 				<embed src= "http://player.video.qiyi.com/0d0233fb6945e8bfdf61515af45bfc1f/0/39/w_19rs7o5yip.swf-albumId=2904044909-tvId=2904044909-isPurchase=0-cnId=25"  quality="high" width="600" height="400" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed> 
 -->		
 	    </div>
		<div>
		<video id="video1" src="<%=path%>/wav/wenshi.mp4" 
		style="position: absolute; z-index: 10;  preload="none" controls="controls"
		top: 132px;" width="600" height="400"   >
        <source src="logo_animation_FullView_v3.ogv" type="video/ogg">
        <source src="logo animation FullView_v3.mp4" type="video/mp4">
         </video>
		</div>
		<%-- <img id="imgId" src="<%=path%>/images/cam-1.jpg"> --%>
	</div>	
</body>
</html>