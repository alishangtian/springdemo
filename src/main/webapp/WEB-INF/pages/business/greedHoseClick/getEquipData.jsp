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
		<title>施肥管理</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript" src="<%=path%>/js/busi.greedHoseClick.js"></script>
		<script type="text/javascript" src="<%=path%>/js/busi.greenHouseEquipData.js"></script>
 		<script src="<%=path%>/js/esl.js" type="text/javascript"></script>
		<script type="text/javascript">var path='<%=path%>';</script>
	</head>
<body>
	<div class="ui-widget-content func-panel"></div>
		<input id="tid" value="${tid }" style="display:none"></input>
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
	
	<div id="main" style="height: 220px; width:1000px; border: 1px solid #ccc; padding: 10px;">
		<span id="loading" style="font-size:30px">载入中...</span>
	</div>
  		 <dt:grid id="DataInfoResult" shrinkToFit="true" multiSelect="true" 
				url="busi/greedHoseClickAction!getEquipDataInfo.action?tid='+$('#tid').val()+'"
				jsonRoot="resultList" dataType="json" showPager="true" onSelectRow="status" width="100%" height="150px" caption="查询结果" shrinkToFit="false" rowNum="6">
				<dt:gridColumn name="value" label="值"></dt:gridColumn>
				<dt:gridColumn name="ctime" label="创建时间"></dt:gridColumn>
				<dt:gridColumn name="equipDataTypeId" label="设备id"></dt:gridColumn>
			</dt:grid>
    <script type="text/javascript" language="javascript">

        require.config({
            paths: {
                echarts: '<%=path%>/js/echarts' //echarts.js的路径
            }
        });

        
    </script>
</body>
</html>