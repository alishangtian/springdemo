<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>首页</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" href="<%=path%>/css/new.style.css"
	type="text/css" />
<script type="text/javascript" src="<%=path%>/js/bmap.js"></script>
<script type="text/javascript" src="<%=path%>/js/busi.welcome.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.vticker.min.js"></script>

</head>
<body>
	<div class="tree-left" style="margin-top: 8px">
		<dt:tree id="dttreen" width="180px" height="400px"
			onAsyncSuccess="expandAsyn"
			url="busi/baseInfoManager!getBaseHouseTree.action"
			urlParam="nodeTreeId" nameCol="nodeTreeName"
			isParentDefaultValue="true" onClick="clickOper">
		</dt:tree>
	</div>
	<div class="con-right">
		<div class="news-right">
			<div class="news-title">
				<span class="news-tip"></span>通知
			</div>
			<ul class="news-content">
				<s:iterator value="bulletinList">
					<li onclick="showBulletin(this)" class="operationClass"
						value="<s:property value='bulletinId'/>"><a
						href="javascript:void(0)"><s:property value="bulletinTitle" />
							消息</a></li>
				</s:iterator>
			</ul>
			<div class="news-title">
				<span class="equip-exp"></span>设备异常消息
			</div>

			<ul class="news-content">
				<s:iterator value="msgAlarmList">
					<li class="operationClass" value="<s:property value='alarmId'/>"><a
						href="javascript:void(0)" onclick="clickmsgAlarm(this)"><s:property
								value='alarmId' />
							<s:property value="detail" /></a></li>
				</s:iterator>
			</ul>
			<div class="news-title">
				<span class="produce-inf"></span>生产信息
			</div>
			<ul class="news-content">
				<s:iterator value="newsList">
					<li class="operationClass" value="<s:property value='pickId'/>"><a
						href="javascript:void(0)" onclick="clickOperation(this)"
						title=" <s:property value="houseName" /> <s:property
							value="baseName" /> 生产 <s:property value="item" /> <s:property value="figure" />吨"><s:property
								value="houseName" /> <s:property value="baseName" />生产...</a></li>
				</s:iterator>
			</ul>
			<div class="news-title">
				<span class="buy-inf"></span>采购意向
			</div>
			<ul class="news-content">
				<li class="operationClass" value="1"><a
					href="javascript:void(0)" onclick="clickPurchaseIntention(this)">购买大兴农业示范区长春温室购买苹果</a></li>
			</ul>
		</div>
		<div class="map-left">
			<div id="basemap"></div>
		</div>
		<div class="alerm-area">
			<script type="text/javascript">
				var houseName = "所有"
			</script>
			<div style="margin-top: 5px;">
				<dt:grid id="baseInfoResultList" shrinkToFit="true"
					multiSelect="false" jsonRoot="resultList" dataType="json"
					showPager="true" height="200px" onSelectRow="status" rowNum="5"
					requestType="post" caption="查询结果:">
					<dt:gridColumn name="houseId" hidden="true" key="true"></dt:gridColumn>
					<dt:gridColumn width="90px" name="houseName" label="温室名称"></dt:gridColumn>
					<dt:gridColumn width="90px" name="cgqCount" label="节点数量"></dt:gridColumn>
					<dt:gridColumn width="90px" name="kzqCount" label="控制器数量"></dt:gridColumn>
				</dt:grid>
			</div>
		</div>
		<script type="text/javascript">
			(
							function(w) {
								var treeHeight = document
										.getElementsByTagName('body')[0].clientHeight;
								if (parent != w)
									treeHeight = parent.document
											.getElementById('worktab').clientHeight;
								document.getElementById('basemap').style.height = (treeHeight - 55)
										+ 'px';
							})(window);
		</script>
	</div>
</body>
</html>