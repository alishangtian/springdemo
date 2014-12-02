<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<title>温室管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.baseGreenHouseInfomanager.js"></script>
		<script type="text/javascript" src="<%=path%>/js/busi.baseInfomanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
		<script type="text/javascript">
				var using = 111111;
			function expandAsyn(){
				var nodes = dttreen.dttree('getNodes');
				if(nodes != null && nodes.length > 0){
					dttreen.expandNode(nodes[0], true,false ,true);
				}
			}
		</script>
	</head>
	<body>
		<div class="tree-left">
			<dt:tree id="dttreen" width="180px" height="400px"
				onAsyncSuccess="expandAsyn"
				url="busi/baseGreenHouseInfoAction!GetbaseGreenHouseTreeList.action"
				urlParam="levelTree,nodeTreeId" nameCol="nodeTreeName"
				isParentDefaultValue="true" onClick="onSelectFuncAuth">
			</dt:tree>
			<script type="text/javascript">
				(function(w){
					var treeHeight = document.getElementsByTagName('body')[0].clientHeight;
					if(parent!=w)
						treeHeight = parent.document.getElementById('worktab').clientHeight;
					document.getElementById('dttreen').style.height=(treeHeight-55)+'px';
				})(window);
			</script>
		</div>
<!-- 温室div -->
		<div class="con-right" style="display: none" id="greenHouse">
			<form id="queryFormH" method="post">
				<table class="query-form">
					<caption>查询条件</caption>
					<tr>
						<dt:input id="baseId" inputType="hidden" name="baseGreenHouseInfoQB.baseId" width="176px" height="30px"></dt:input>
						<dt:input id="crops" name="baseGreenHouseInfoQB.crops" inputType="hidden"></dt:input>
						<th>温室名称：</th>
						<td><dt:input id="nameH" name="baseGreenHouseInfoQB.name" width="176px" height="30px"></dt:input>
					</tr>
				</table>
			</form>
			<div class="query-form-btns">
				<dt:button id="queryBaseGreenHouseInfo" label="查询" type="ok"/>
				<dt:button id="resetBaseGreenHouseInfo" label="重置" type="cancel"/>
				<dt:button id="addBaseGreenHouseInfo" label="新增温室" type="ok"/>
			</div>
			<dt:grid id="baseGreenHouseInfoResult" shrinkToFit="false"
				multiSelect="true" jsonRoot="resultList" dataType="json"
				caption="查询结果:所有基地"
				showPager="true" width="400%" height="300px" onSelectRow="status"  shrinkToFit="false" rowNum="8">
				<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
				<dt:gridColumn name="name" label="温室名称" width="90px"></dt:gridColumn>
				<dt:gridColumn name="baseId" label="基地编号" width="70px"></dt:gridColumn>
				<dt:gridColumn name="crops" label="种植作物" width="70px"></dt:gridColumn>
				<dt:gridColumn name="ctime" label="创建时间" width="150px"></dt:gridColumn>
				<dt:gridColumn name="acreage" label="面积" width="150px"></dt:gridColumn>
				<dt:gridColumn name="opretion" label="操作" formatter="operFormatGreenHouse" align="center" width="170px"></dt:gridColumn>
			</dt:grid>
		</div>
<!-- 基地div -->
		<div class="con-right" style="display: block" id="base">
			<form id="queryFormB"  method="post">
				<table class="query-form">
					<caption>查询条件</caption>
					<tr>
						<th>基地名称：</th>
		
						<td><dt:input id="nameB" name="baseQB.name" width="176px" height="30px"></dt:input></td>
						<th>状态：</th>
						<td>
							<select id="state" name="baseQB.state">
								<option value="0">在用</option>
								<option value="1">停用</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
			<div class="query-form-btns">
				<dt:button id="queryBaseInfoBtn" label="查询" type="ok" icon="" /> 
				<dt:button id="resetBaseInfoBtn" label="重置" type="cancel" icon="" /> 
				<dt:button id="addBaseInfoBtn" label="新增基地" type="ok" icon="" /> 
				<%-- <dt:button id="delBaseInfoBtn" label="删除" type="ok" icon="" /> --%>
			</div>
			<dt:grid id="baseInfoResult" shrinkToFit="true" multiSelect="false"
				jsonRoot="resultList" dataType="json" showPager="true"
				 caption="查询结果:所有基地"
				width="400%" height="300px" onSelectRow="status"  shrinkToFit="false" rowNum="8">
				<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn> 
				<dt:gridColumn name="name" label="名称"></dt:gridColumn>
				<%-- <dt:gridColumn name="regionId" label="行政区"></dt:gridColumn>--%>
				<dt:gridColumn name="address" label="地址"></dt:gridColumn> 
				<dt:gridColumn name="stateName" label="状态"></dt:gridColumn> 
				<dt:gridColumn name="ctime" label="创建时间"></dt:gridColumn>
				<dt:gridColumn name="opretion" label="操作" formatter="operFormatBase" align="center"></dt:gridColumn>
			</dt:grid>
			
		</div>
	</body>
</html>