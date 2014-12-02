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
		function expandAsyn(){
			var nodes = dttreen.dttree('getNodes');
			if(nodes != null && nodes.length > 0){
				dttreen.expandNode(nodes[0], true,false ,true);
			}
		}
		var contextPath = "<%=path%>";
		var url ="<%=path%>/wav/wenshi.f4v";
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
			<div class="base-bg fr">
					<h3>${baseName}：${houseName }</h3>
					<h4>作物：${crops }</h4>
				<p>风力：2级</p>
				<p>风向：东南</p>
				<p>气压：10000m</p>
				<p>降水：0.5L</p>
				<div class="clear"></div>
 				<img src="<%=path%>/images/top2.jpg">
 				<div class="query-form-btns">
 					<dt:button id="showCam" width="180" height="30" label="温室视频"></dt:button>
 				</div>
			</div>
			<div class="alerm-area">
				<div id="listConl"  style="display:none;">
					<dt:grid id="econInfoHResult" shrinkToFit="true" 
						multiSelect="false" jsonRoot="resultList" dataType="json"
						showPager="true" height="200px" width="760px" onSelectRow="status"  rowNum="5"
						requestType="post" >
						<dt:gridColumn name="id" hidden="true" key="true"></dt:gridColumn>
						<dt:gridColumn width="140px" name="name" label="设备名称"></dt:gridColumn>
						<dt:gridColumn width="140px" name="content" label="操作名称"></dt:gridColumn>
						<dt:gridColumn width="120px" name="result" label="结果"></dt:gridColumn>
						<dt:gridColumn width="140px" name="time" label="操作时间"></dt:gridColumn>
					</dt:grid>
				</div>
			
				<div id="eqinfo">
			<s:iterator value="edataETypeInfo" id="edtiL" status='st'>
				<h3 class="equip-title"><s:property value="#edtiL.equipInfoName"/></h3>
				<ul class="alerm-list">
					 	<s:iterator value="#edtiL.equipDataEnvdataTypeInfo">
							<li value="${equipDataTypeId }">
								<em>${value} ${units}</em>
								<i>${name}</i>
								 <%-- <span class="alerm-icon"></span>  --%>
								<s:if test="name == '空气湿度'"><span class="one-equip"></span></s:if>
								<s:elseif test="name == '空气温度'"><span class="one-equip two-equip"></span></s:elseif>
								<%-- <s:elseif test="name == '相对水率'"><span class="one-equip three-equip"></span></s:elseif> --%>
								<s:elseif test="name == '土壤湿度'"><span class="one-equip four-equip"></span></s:elseif>
								<s:elseif test="name == '土壤温度'"><span class="one-equip five-equip"></span></s:elseif>
								<s:elseif test="name == 'co2含量'"><span class="one-equip six-equip"></span></s:elseif>
								<s:elseif test="name == '土壤PH值'"><span class="one-equip two-equip"></span></s:elseif>
								<s:elseif test="name == '光照度'"><span class="one-equip six-equip"></span></s:elseif>
								<s:elseif test="name == '土壤电导率'"><span class="one-equip two-equip"></span></s:elseif>
							</li>
						</s:iterator>
					<li class="li-clear"></li>
				</ul>
				</s:iterator>
				</div>
			</div>
	
	<div class="equip-contol" id="maginfo"  style="display:none;" >
			<input id="tid" value="" type="hidden"></input>
			<input id="tname" value="" type="hidden"></input>
		<div>
		<table>
			<tr>
				<th>数据周期:</th>
				<td >
					<select id="cycle">
						<option value="1">最近1小时
						<option value="6">最近6小时
						<option value="24">最近24小时
					</select>
				</td>
				<th>采集频率</th>
				<td >
					<select>
						<option>1分钟
						<option>2分钟
						<option>3分钟
					</select>
				</td>
				
			</tr>
		</table>
		</div>
	
		<div id="main" style="height: 220px; width:768px; border: 1px solid #ccc; padding: 10px;">
			<span id="loading" style="font-size:30px">载入中...</span>
		</div>
	</div>
	<div class="equip-contol" id="kzqinfo">
		<div id="controlInfo">
		  <s:iterator value="equipControlList" id="ecl" status='e'>
			<h3 class="equip-title"><s:property value="#ecl.parentName"/> -- <s:property value="#ecl.controName"/></h3>
			<s:set name="quipInfoListone" value="#ecl.equipControlInfoList.size()"></s:set>
			<ul class="equip-list fl">
				 	<s:iterator value="#ecl.equipControlInfoList">
						<li onClick="onOffInfo(this)" value="1">
							<s:if test="name == '风机'">
							<img src="<%=path %>/images/equip-1.png">
							</s:if>
							<s:elseif test="name == '遮阳帘'">
							<img src="<%=path %>/images/equip-2.png">
							</s:elseif>
							<s:elseif test="name == '顶部通风'">
							<img src="<%=path %>/images/equip-3.png">
							</s:elseif>
							<s:elseif test="name == '水泵'">
							<img src="<%=path %>/images/equip-4.png">
							</s:elseif>
							<s:elseif test="name == '电磁阀'">
							<img src="<%=path %>/images/equip-5.png">
							</s:elseif>
							<s:elseif test="name == '二氧化碳罐'">
							<img src="<%=path %>/images/equip-6.png">
							</s:elseif>
							<s:elseif test="name == '灯光'">
							<img src="<%=path %>/images/equip-7.png">
							</s:elseif>
						   <em>${name} </em>:<a href="javascript:void(0)" class="open">打开</a>
							
						</li>
					</s:iterator>
				<li class="li-clear"></li>
			</ul>
			<div class="clear"></div>
		  </s:iterator>	
		  </div>
	</div>
</div>
<div class="cam-win">
	<div class="cam-left">
		<h3>监控探头</h3>
		<p><i class="cam-icon"></i>视频1</p>
		<p><i class="cam-icon cam-on"></i>视频1</p>
	</div>
	<div class="cam-right">
		<img src="<%=path%>/images/cam-1.jpg">
	</div>	
</div>

<script type="text/javascript" language="javascript">
   require.config({
       paths: {
           echarts: '<%=path%>/js/echarts' //echarts.js的路径
       }
   });
   
</script>
</body>
</html>