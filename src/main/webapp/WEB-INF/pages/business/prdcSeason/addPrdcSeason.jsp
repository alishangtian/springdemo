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

	<form action="busi/prdcSeasonManager!createPrdcSeason.action" id="createPrdcSeasonForm" method="post">
		<table class="query-form" width="100%">
		<caption>添加种植季</caption>
			<dt:input id="queryHouseId" name="prdcSeason.houseId"  inputType="hidden"/>
			<tr>
				<th>种植季名称：</th>
				<td>
					<dt:input id="queryPrdcSeasonName" name="prdcSeason.name"  width="176px" height="30px"/> 
					<%-- <s:select list="produceSeasonListajax" listValue="name" id="selectSeason" listKey="id" name="prdcSeason.id"></s:select> --%>
					
				</td>
				<th>安全生产配置：</th>
				<td>
					<select name="prdcSeason.thresholdInfoId">
						<s:iterator value="ptilist">
							<option value="<s:property value="id"/>" ><s:property value="name"/></option>
						</s:iterator>
					</select>
					
				</td>
			</tr>
			<tr>
				<th>起始时间：</th>
				<td>
					<dt:dateInput id="queryPrdcSeasonBeginTime" name="prdcSeason.beginTime"  showTime="true" showOnbutton="true" width="176px" height="30px"/>
				</td>
				<th>结束时间：</th>
				<td>
				<dt:dateInput id="queryPrdcSeasonEndTime" name="prdcSeason.endTime"  showTime="true" showOnbutton="true" width="176px" height="30px"/>
				</td>
			</tr>
			<tr>
				<th>种植作物：</th>
				<td>
					<dt:input id="queryPrdcSeasonCrops" name="prdcSeason.crops" width="176px" height="30px"/>
				</td>
				<th>所属温室</th>
				<td><dt:input id="greenHouse" value="${houseName}" readonly="true" width="176px" height="30px"></dt:input></td>
			</tr>
			<tr>
				<th>种子来源：</th>
				<td colspan="3">
				<textarea rows="8" cols="80" id="address" name="prdcSeason.seedSource"  ></textarea>
				</td>
			</tr>
		</table>
		</form>
	<div class="query-form-btns">
			<dt:button id="createPrdcSeasonBtn" label="确定" type="ok" icon=""/>
				<dt:button id="cancelBtn4Add" label="取消" type="ok" icon="" />
		</div>

</body>
</html>