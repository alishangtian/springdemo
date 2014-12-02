<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
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
	</head>
	<body>
		<form action="busi/pickWorksInfoAction!updatePickWorksInfo.action" id="addPickWorksInfoForm" method="post">
		<table class="query-form" width="100%">
		<caption>采摘编辑</caption>
			<dt:input width="100" id="id" inputType="hidden" value="${pickWorksInfo.id}" name="pickWorksInfo.id"></dt:input>
			<tr>
				<th>种植季：</th>
				<td>
					<div>
						
						<%-- <dt:input id="produceSeasonId" name="pickWorksInfo.produceSeasonId"  width="176px" height="30px" disabled="false" value="${prdcSeason.name }"></dt:input> --%>
						<dt:input width="100" id="userId" inputType="hidden" value="${prdcSeason.id }" name="pickWorksInfo.userId" id="bbb"></dt:input>
						<dt:comboBox datasource="${produceSeasonListajax }"id="produceSeasonId" height="28px" keyField="id" valueField="name" name="pickWorksInfo.produceSeasonId" hasNull="true" selectValue="${pickWorksInfo.produceSeasonId}" onChange="prdcSeasonChange(this)"></dt:comboBox>
					</div>
				</td>
				
					<th>负责人：</th>
				<td>
					<%=((SysUser) request.getSession().getAttribute("LOGIN_USER")).getLoginName() %>
					<dt:input id="userId" name="pickWorksInfo.userId" value='<%=((SysUser) request.getSession().getAttribute("LOGIN_USER")).getUserId()+"" %>' inputType="hidden"></dt:input>
				</td>
			</tr>
			<tr>
			<th>开始时间：</th>
				<td>
				<dt:dateInput id="beginTime" name="pickWorksInfo.beginTime"  showTime="true" showOnbutton="true"  width="176px" height="30px"/>
				</td>
				<th>结束时间：</th>
				<td>
				<dt:dateInput id="endTime" name="pickWorksInfo.endTime"  showTime="true" showOnbutton="true"  width="176px" height="30px"/>
				</td>
			</tr>
			
			<tr>
			<th>作物：</th>
				<td>

					<dt:input id="crops" name="crops" width="176px" height="30px"  value="${ prdcSeason.crops}" disabled="true"></dt:input>（公斤）
				
				</td>
				<th>数量：</th>
				<td>
				<dt:input id="amount" name="pickWorksInfo.amount"  width="100"  width="176px" height="30px"></dt:input>
				</td>
			</tr>
			<tr>
				<th>其他：</th>
				<td colspan="3">
				<textarea rows="5" cols="80" id="remark" name="pickWorksInfo.remark">${pickWorksInfo.remark }</textarea>
				</td>
			</tr>

		</table>
		</form>
	<div class="query-form-btns">
			<dt:button id="savePickWorksInfoBtn" label="确定" type="ok" icon=""/>
				<dt:button id="cancelBtn" label="取消" type="ok" icon=""/>
		</div>
		
		<script type="text/javascript">
		
		var addPickCropId = produceSeasonId.dtcombobox("data");
		
		$("#crops").val(addPickCropId.crops);
		

		function prdcSeasonChange(o){
			
			var addPickCropId = produceSeasonId.dtcombobox("data");
			
			$("#crops").val(addPickCropId.crops);
		}
		
		
		
		</script>
</body>
</html>