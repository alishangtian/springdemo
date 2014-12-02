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
		<script type="text/javascript" src="<%=path%>/js/busi.baseInfomanager.js"></script>
		<script type="text/javascript" src="<%=path%>/js/busi.geomap.js"></script>
	</head>
<body>
	<form action="busi/baseInfoManager!saveBaseInfo.action" id="addBaseInfoForm" method="post">
		<table class="query-form" width="100%">
		<caption>添加基地</caption> 
				<tr>
					<%-- <th>所属用户：</th>
					
					<td><select name="baseInfo.custId" id="custId" disabled="disabled">
							<c:forEach items="${customerList}" var="al">
								<option value="${al.custId}">${al.name}</option>
							</c:forEach>
					</select></td> --%>
					<th>基地名称：</th>
					<td><dt:input id="name" name="baseInfo.name" width="176px" height="30px"></dt:input>
					</td>
					<th>地理信息：</th>
					<td><dt:input id="geoinfo" name="baseInfo.geoInfo" width="176px" height="30px"></dt:input>
					</td>
					<%-- <th>行政区：</th>
					<td><dt:input id="regionId" name="baseInfo.regionId" width="176px" height="30px"></dt:input></td> --%>
				</tr>
				<tr>
					<%-- <th>状态：</th>
					<td><select id="state" name="baseInfo.state">
							<option value="0">在用</option>
							<option value="1">停用</option>
					</select></td> --%>
					<th>基地面积：</th>
					<td><dt:input id="acreage" name="baseInfo.area" width="176px" height="30px"></dt:input>
					</td>
					<th>地址：</th>
					<td><dt:input id="address" name="baseInfo.address" width="176px" height="30px"></dt:input></td>
				</tr>

				<tr>
				<th>基地介绍</th>
				<td colspan="3">
					<textarea rows="5" cols="80" id="remark" name="baseInfo.introduction">${baseInfo.introduction }</textarea>
				</td>
			</tr>
			</table>
		</form>
		<div style="margin: 5px; backgroud: white">
			<dt:input id="key" width="200"></dt:input>
			<button onclick="searchMap()">搜索</button>
			<button onclick="startdraw()">开始绘制</button>
			<button onclick="cleardraw()">清除</button>
			<div style="height: 300px" id="basemap"></div>
		</div>
		
		<div class="query-form-btns">
			<dt:button id="saveBaseInfoBtn"label="确定" type="ok" icon="" /> 
			<dt:button id="cancelBtn" label="取消" type="ok" icon=""/>
		</div>

</body>
<script type="text/javascript">
	$(function() {
		initMap.initMap();
		initMap.initDrawTools();
		$("#key").keydown(function(event) {
			if (event.keyCode == 13) {
				searchMap();
			}
		});
	});
</script>
</html>