<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.cattsoft.baseplatform.func.sm.entity.SysUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据展示</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="combox,input,grid,dialog,button,message"></dt:comppath>
<script type="text/javascript" src="<%=contextPath%>/js/json2.js"></script>
<script type="text/javascript" 	src="<%=contextPath%>/js/busi.dataAcquisitionDisplay.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/flot/jquery.js"></script>
</head>
<body>
	<div class="ui-widget-content func-panel">
	<table height="400px" width="100%">
	<tr>
	<td>
		 <table class="condition" width="100%" align="center">
			<tr>
				<th>设备类型：</th>
				<td>
					<div>
<%-- 						<dt:combox id="queryProduceSeasonId" name="produceSeasonId" datasource="${produceSeasonList}" keyField="id" valueField="crops" hasAll="true" allKey="" allValue="--全部--"></dt:combox>
 --%>				
 						<select id="typeinfo">
 							<option value="1">节点</option>
 							<option value="2">空气传感器</option>
 							<option value="3">土壤传感器</option>
 						</select>
 					</div>
				</td>
			</tr>
			<tr>
				<td colspan="6" align="center">
				<input type="button" id="queryDomainBtnO" class="button"  value="查询" />
<!-- 				<button id="queryDomainBtnO" label="查询" type="ok" icon=""/>
 -->				</td>
			</tr>
		</table> 
		<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
			<tr>
				<th width="5%" >id</th>
				<th width="20%" >名称</th>
				<th width="20%" >值</th>
				<th width="20%" >单位</th>
				<th width="15%"  >时间</th>
			</tr>
			<c:if test="${!empty equipStateDataResult }">
				<c:forEach var="esdrinfo" items="${equipStateDataResult}" varStatus="stat">
					<tr>
						<td>
						${esdrinfo.id }
						</td>
						<td>
						${esdrinfo.name }
						</td>
						<td>
						${esdrinfo.value }
						</td>
						<td>
						${esdrinfo.units }
						</td>
						<td>
						${esdrinfo.time }
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${!empty edes }">
				<c:forEach var="esdrinfo" items="${edes}" varStatus="stat">
					<tr>
						<td>
						${esdrinfo.dataId }
						</td>
						<td>
						${esdrinfo.name }
						</td>
						<td>
						${esdrinfo.value }
						</td>
						<td>
						${esdrinfo.units }
						</td>
						<td>
						${esdrinfo.ctime }
						</td>
					</tr>
				</c:forEach>
			</c:if>
			
		</table>
		
		
		</td>
		</tr>
		</table>
	</div>
</body>
</html>