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
		<script type="text/javascript" src="<%=path%>/js/busi.geomap_view.js"></script>
	</head>
<body>
<form action="baseGreenHouseInfoAction!editBaseGreenHouseInfo.action" id="addBaseGreenHouseInfoForm" method="post">
		<table class="query-form" width="100%">
		<caption>温室查看</caption> 
		<tr>
				<th>基地名称：</th>
				<td>
					<div>
						<select id="baseId" name="baseGreenHouseInfo.baseId" width="100">
						<c:forEach items="${baseInfoList}"  var="al">
		                  <option value="${al.id}" <c:if test="${al.id==baseGreenHouseInfo.baseId}">selected='selected'</c:if>>${al.name}</option>
		   				</c:forEach>
						</select>
					</div>
				</td>
				
				<th>温室名称：</th>
				<td>
					<dt:input id="name" name="baseGreenHouseInfo.name" width="176px" height="30px" readonly="true"></dt:input>
				</td>
			</tr>
			<tr>
			<th>种植作物：</th>
				<td>
				<dt:input id="crops" name="baseGreenHouseInfo.crops" width="176px" height="30px" readonly="true"></dt:input>
				</td>
			<th>地理信息：</th>
						<td>
						<dt:input id="geoInfo" name="baseGreenHouseInfo.geoInfo" width="176px" height="30px" readonly="true"></dt:input>
						</td>
			</tr>
			
			<tr>
		<%-- 	<th>经度：</th>
				<td>
				 <dt:input id="gpsJD" name="gpsJD"  width="100"></dt:input>
				</td> --%>
				<th>面积：</th>
					<td>
					<dt:input id="acreage" name="baseGreenHouseInfo.acreage"  width="176px" height="30px" readonly="true"></dt:input>
						</td>
				<th>地址：</th>
					<td>
					<dt:input id="gps" name="baseGreenHouseInfo.gps"  width="176px" height="30px" readonly="true"></dt:input>
					</td>
			</tr>
			<tr>
				
			</tr>
			
		
		</table>
	 </form>
	 <div style="height:300px" id="basemap"></div>
			<div class="query-form-btns">
			<dt:button id="cancelBtn" label="返回" type="ok" icon=""></dt:button>
		</div>
</body>
<script type="text/javascript">
$(function(){
	var geo_info = $("#geoInfo").val();
	var points = initMap_view.parsePolygonPoints(geo_info);
	initMap_view.initMap(points[0]);
	initMap_view.newPolygon(map_view, points, initMap_view.styleoption);
});
</script>
</html>