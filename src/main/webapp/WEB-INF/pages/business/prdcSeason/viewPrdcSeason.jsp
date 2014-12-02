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
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.prdcSeasonManager.js"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
<body>
	<form action="busi/pickWorksInfoAction!savePickWorksInfo.action" id="addPickWorksInfoForm" method="post">
	<table class="query-form">
		<caption>种植季查看</caption>
			<dt:input id="viewHouseId" name="prdcSeason.houseId"  inputType="hidden"/>
			<tr  >
				<th  >种植季名称：</th>
				<td  >
					<dt:input id="viewPrdcSeasonName" name="prdcSeason.name" width="176px" height="30px" value="${prdcSeason.name}" disabled="false"  />
				</td>
				<th  >安全生产配置：</th>
				<td  >
					<dt:input id="PrdcSeasonThresholdInfoId" name="prdcSeason.thresholdInfoId" width="176px" height="30px" value="${PTIName}" disabled="false"  />
				</td>
			</tr>
			<tr>
				<th>起始时间：</th>
				<td>
					<dt:dateInput id="viewPrdcSeasonBeginTime" name="prdcSeason.beginTime"  showTime="true" width="176px" height="30px" showOnbutton="true" readonly="true"  />
				</td>
				<th>结束时间：</th>
				<td>
				<dt:dateInput id="viewPrdcSeasonEndTime" name="prdcSeason.endTime"  showTime="true" width="176px" height="30px" showOnbutton="true"  readonly="true"  />
				</td>
			</tr>
			<tr>
				<th>种植作物：</th>
				<td>
					<dt:input id="viewPrdcSeasonCrops" name="prdcSeason.crops" value="${ prdcSeason.crops}" disabled="disabled" width="176px" height="30px"/>
				</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>种子来源：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="viewPrdcSeasonSeedSource" name="prdcSeason.seedSource"  disabled="disabled">${prdcSeason.seedSource}</textarea>
				</td>
			</tr>
			 
		</table>
		</form>
		<div class="query-form-btns">
			<dt:button id="cancelBtn" label="返回" type="ok" icon=""/>
		</div>
</body>
</html>