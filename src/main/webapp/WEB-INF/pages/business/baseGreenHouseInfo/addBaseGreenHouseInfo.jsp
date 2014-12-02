<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.pickWorksInfomanager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
		<script type="text/javascript" src="<%=path%>/js/bmap.js"></script>
		<script type="text/javascript" src="<%=path%>/js/DrawingManager_min.js"></script>
		<link rel="stylesheet" href="<%=path%>/css/DrawingManager_min.css" />
		<script type="text/javascript" src="<%=path%>/js/busi.baseGreenHouseInfomanager.js"></script>
		<script type="text/javascript" src="<%=path%>/js/busi.geomap.js"></script>
	</head>
<body>
	<form action="busi/baseGreenHouseInfoAction!saveBaseGreenHouseInfo.action" id="addBaseGreenHouseInfoForm" method="post">
		<table class="query-form" width="100%">
		<caption>添加温室</caption>  
			<tr>
				<th>温室名称：</th>
				<td>
					<dt:input id="name" name="baseGreenHouseInfo.name"  width="176px" height="30px" ></dt:input>
				</td>
				<th>种植作物：</th>
				<td>
				<dt:input id="crops" name="baseGreenHouseInfo.crops"  width="176px" height="30px" ></dt:input>
				</td>
			  <%--	<th>基地名称：</th>	
				<td>
					 <div>
						<dt:combox id="baseId" name="baseGreenHouseInfo.baseId" 
						datasource="${baseInfoList}" keyField="id" valueField="name" 
						hasAll="true" allKey="" allValue=""></dt:combox>
					</div> 
				  
				</td>  --%>
				<dt:input id="baseId" inputType="hidden" name="baseGreenHouseInfo.baseId"  value="${b.id }"></dt:input>
				
			</tr>
			<tr>
			
			<th>地址：</th>
				<td>
				<dt:input id="address" name="baseGreenHouseInfo.gps"  width="176px" height="30px"></dt:input>
				</td>
				<th>面积：</th>
				<td>
				<dt:input id="acreage" name="baseGreenHouseInfo.acreage"  width="176px" height="30px"></dt:input>
				</td>
			</tr>
			
			<tr>
			<%-- <th>经度：</th>
				<td>
				 <dt:input id="gpsJD" name="gpsJD"  width="100"></dt:input>
				</td> --%>
			<th>地理信息：</th>
				<td>
				<!--<dt:input id="geoinfo" disabled="true" name="geoInfo"  width="300"></dt:input>-->
				<dt:input  width="176px" height="30px" id="geoinfo"  value="" name="baseGreenHouseInfo.geoInfo" ></dt:input>
				</td>
			<th>所属基地：</th>
				<td  >
				<dt:input id="ssjd" name="ssjd"  width="176px" height="30px" readonly="true" value="${b.name }"></dt:input>
				</td>
			</tr>
			
			<dt:input  width="176px" height="30px" id="gps"  inputType="hidden" value="" name="baseGreenHouseInfo.gps"></dt:input>		
			</table>
		</form>
		<div style="margin:5px;backgroud:white">
		<dt:input id="key"  width="200"></dt:input>
			<button onclick="searchMap()">搜索</button>
			<button onclick="startdraw()">开始绘制</button>
			<button onclick="cleardraw()">清除</button>
		    <div style="height:300px" id="basemap"></div>
		</div>
		<div class="query-form-btns">
			<dt:button id="saveBaseGreenHouseInfo" label="确定" type="ok" icon=""></dt:button>
			<dt:button id="cancelBtn" label="取消" type="ok" icon=""></dt:button>
		</div>
</body>
<script type="text/javascript">
$(function() {
	initMap.initMap();
	initMap.initDrawTools();
	// 绑定回车键
	$("#key").keydown(function(event) {
		if (event.keyCode == 13) {
			searchMap();
		}
	});
});
</script>
</html>