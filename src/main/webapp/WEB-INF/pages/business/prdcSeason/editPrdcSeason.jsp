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
	<form action="busi/prdcSeasonManager!updatePrdcSeason.action" id="editPrdcSeasonForm" method="post">
		<table class="query-form">
		<caption>种植季修改</caption>
			<dt:input id="editId" name="prdcSeason.id"  inputType="hidden" value="${prdcSeason.id}" />
			<dt:input id="editHouseId" name="prdcSeason.houseId"  inputType="hidden"/>
			<tr>
				<th>种植季名称：</th>
				<td  >
					<dt:input id="editPrdcSeasonName" name="prdcSeason.name" width="176px" height="30px" value="${prdcSeason.name}"/>
					<%-- <dt:comboBox datasource="${produceSeasonListajax }"id="produceSeasonId" height="28px" keyField="id" valueField="name" name="prdcSeason.id" hasNull="true" selectValue="${prdcSeason.id }" onChange="prdcSeasonChange(this)"></dt:comboBox> --%>
				</td>
				<th  >安全生产配置：</th>
				<td  >
					<dt:input id="a" value="${prdcSeason.thresholdInfoId }" inputType="hidden"></dt:input>
					<select name="prdcSeason.thresholdInfoId" id="selectEdit">
						<s:iterator value="ptilist" >
							<option value="<s:property value="id"/>" ><s:property value="name"/></option>
						</s:iterator>
					</select>
				</td>
			</tr>
			<tr>
				<th>起始时间：</th>
				<td>
					<dt:dateInput id="editPrdcSeasonBeginTime" name="prdcSeason.beginTime"  showTime="true" showOnbutton="true"   width="176px" height="30px"/>
				</td>
				<th>结束时间：</th>
				<td>
				<dt:dateInput id="editPrdcSeasonEndTime" name="prdcSeason.endTime"  showTime="true" showOnbutton="true"   width="176px" height="30px"/>
				</td>
			</tr>
			<tr>
				<th>种植作物：</th>
				<td>
					<dt:input id="editPrdcSeasonCrops" name="prdcSeason.crops" value="${ prdcSeason.crops}" width="176px" height="30px"/>
				</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>种子来源：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="editPrdcSeasonSeedSource" name="prdcSeason.seedSource">${prdcSeason.seedSource}</textarea>
				</td>
			</tr>
		</table>
		<div class="query-form-btns">
			<dt:button id="editPrdcSeasonBtn" label="确定" type="ok" icon=""/>
			<dt:button id="cancelBtn4Edit" label="取消" type="ok" icon=""/>
		</div>
		</form>

		
</body>
</html>