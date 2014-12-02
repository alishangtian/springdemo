<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生产数据统计</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/busi.Statistics.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/esl.js"></script>
	<style type="text/css">
		.base{
			float:left;
			margin-left:10px;
			background-color:#F2F5F7;
			
			height:30px;
			font-size:20px;
			text-align:center
		}
		.baseC{
			float:left;
			margin-left:10px;
			background-color:#E3E3E3;
			color:#1C1C1C;
			height:30px;
			font-size:20px;
			text-align:center;
			cursor:pointer;
			
		}
		.allBase{
			float:left;
			background-color:#E3E3E3;
			cursor:pointer;
			height:30px;
			font-size:20px;
			text-align:center
		}
	</style>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<form action="statisticsAction!queryPickData.action" method="post" id="queryPick">
		<table>
			
			<tr>
				<th>选择时间段：</th>
				<td>开始时间</td>
				<td>
					<dt:dateInput id="beginTime" name="pickWorksInfo.beginTime"  showTime="true" showOnbutton="true"/>
				</td>
				<td>结束时间</td>	
				<td>
					<dt:dateInput id="endTime" name="pickWorksInfo.endTime"  showTime="true" showOnbutton="true" />
					<dt:button id="queryDateBtn" label="查询" type="ok" icon=""/>
				</td>
				<td>	<input type="text" value="${begin_Time } "id="bt" style="display:none">
					<input type="text" value="${end_Time }" id="et" style="display:none">
				</td>
			</tr>
			<tr>
				<th>统计项</th>
				<td>
					
				</td>
				<td><div id="allBase" class="allBase">
						所有基地
					</div>
					<s:iterator value="allBaseList">
						
						
						<div id='<s:property value="id"/>' class="base" onmouseover="overColor(this)" onmouseout="outColor(this)">
							<s:property value="name"/>
						</div>
						
					</s:iterator>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div id="main" style="height: 300px; width:960px; border: 1px solid #ccc; padding: 10px;">
   	<script type="text/javascript" language="javascript">
	     require.config({
	        paths: {
	            echarts: '<%=contextPath%>/js/echarts' //echarts.js的路径
	        }
	    });    
	    
	   
	    function overColor(o){
	    	o.className="baseC";
	    }
	    function outColor(o){
	    	o.className="base";
	    }
	    
    </script>
		
	</div>
	
	
	
</body>
</html>