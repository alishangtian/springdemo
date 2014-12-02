<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<title>消费安全事故管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.prdcConsAccInfomanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
		<script type="text/javascript">
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
				url="busi/greedHoseClickAction!findBaseGreenTree.action"
				urlParam="levelTree,nodeTreeId" nameCol="nodeTreeName"
				isParentDefaultValue="true" onClick="clickOper">
			</dt:tree>
			<script type="text/javascript">
				(function(w){
					var treeHeight = document.getElementsByTagName('body')[0].clientHeight;
					if(parent!=w)
						treeHeight = parent.document.getElementById('worktab').clientHeight;
					document.getElementById('dttreen').style.height=(treeHeight-55)+'px';
					//equipDataEnvdataTypeInfo equipControlInfoList
				})(window);
			</script>
		</div>
		<div class="con-right">
			<form id="queryForm">
				<table class="query-form">
					<caption>查询条件</caption>
					<tr>
						<th>状态：</th>
						<td>
							<%-- <dt:comboBox id="seasonSelect" height="28px" keyField="id" valueField="name" name="pcaQB.state" hasNull="true" selectIndex="-1">
							</dt:comboBox> --%>
							<select id="seasonSelect" name="pcaQB.state" >
								    <option value="">全部</option>
									<option value="1">未处理</option>
									<option value="2">已处理</option>
							</select>
						</td>
						<th>事故时间：</th>
						<td>
							<dt:dateInput id="accidentsDate" name="pcaQB.accidentsDate" width="176px" height="30px" showTime="false" showOnbutton="true"/>
						</td>
					</tr>
					<input name="pcaQB.houseId" type="hidden" id="houseId"/>
					<input name="houseName" type="hidden" id="houseName"/>
				</table>
			</form>
			<div class="query-form-btns">
				<dt:button id="queryDomainBtn" label="查询" type="ok"/>
				<dt:button id="resetPcaInfoBtn" label="重置" type="cancel"/>
				<dt:button id="addPcaInfoBtn" label="新增" type="ok"/>
				<dt:button id="delPcaInfoBtn" label="删除" type="ok"/>
			</div>
			<dt:grid id="prdcConsAccResult" shrinkToFit="true" multiSelect="true"
				url="busi/prdcConAcciAction!selectPrdcConsAccInfo.action?'+$('#queryForm').serialize()+'"
				jsonRoot="resultList" dataType="json" showPager="true" width="100%"
				height="270px" onSelectRow="status" shrinkToFit="false" rowNum="8">
				<dt:gridColumn name="id" hidden="true"  key="true" ></dt:gridColumn>
				<dt:gridColumn name="houseId" label="温室"  width="70px"></dt:gridColumn>
				<dt:gridColumn name="phone" label="电话"  width="90px"></dt:gridColumn>
				<dt:gridColumn name="emall" label="邮箱"  width="70px"></dt:gridColumn>
				<dt:gridColumn name="state" label="状态"  width="70px"></dt:gridColumn>
				<dt:gridColumn name="detail" label="描述"  width="150px"></dt:gridColumn>
				<dt:gridColumn formatter="date" name="accidentsDate" label="事故事件时间"  width="120px"></dt:gridColumn>
				<dt:gridColumn name="opretion" label="操作" formatter="operFormat" align="center"  width="130px"></dt:gridColumn>
			</dt:grid>
	
		</div>
	</body>
</html>