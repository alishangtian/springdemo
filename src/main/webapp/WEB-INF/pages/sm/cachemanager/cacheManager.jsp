<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>缓存管理</title>
<link rel="stylesheet" href="<%=contextPath%>/css/func.css"
	type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=contextPath%>/css/func.cache.css"
	type="text/css" charset="utf-8" />
<dt:comppath include="tabs,grid,button,message"></dt:comppath>
<script type="text/javascript">
	function tableCacheOperateTransformLink(cellvalue, options, rowObject) {
		return "<a href='javascript:void(0)' onclick='javascript:reloadTableCache(\""
				+ rowObject.mapName + "\")'>刷新</a>";
	}

	function reloadTableCache(mapName) {
		$.post("sm/cacheManager!reloadTableCache.action", {
			'mapName' : mapName
		}, function(data) {
			if (data = true) {
				$.dtmessagebox.alert("刷新成功。");
			} else {
				$.dtmessagebox.alert("刷新失败。");
			}
		});
	}
	

	$(function() {
		$("#reloadAllTableCache").click(
				function() {
					$.post("sm/cacheManager!reloadAllTableCache.action",
							function(data) {
								if (data = true) {
									$.dtmessagebox.alert("表数据刷新成功。");
								} else {
									$.dtmessagebox.alert("表数据刷新失败。");
								}
							});
				});

		$("#reloadDomainCache").click(function() {
			$.post("sm/cacheManager!reloadDomainCache.action", function(data) {
				if (data = true) {
					$.dtmessagebox.alert("值域刷新成功。");
				} else {
					$.dtmessagebox.alert("值域刷新失败。");
				}
			});
		});

		$("#reloadSysParamCache").click(
				function() {
					$.post("sm/cacheManager!reloadSysParamCache.action",
							function(data) {
								if (data = true) {
									$.dtmessagebox.alert("系统参数刷新成功。");
								} else {
									$.dtmessagebox.alert("系统参数刷新失败。");
								}
							});
				});

		$("#reloadBusiParamCache").click(
				function() {
					$.post("sm/cacheManager!reloadBusiParamCache.action",
							function(data) {
								if (data = true) {
									$.dtmessagebox.alert("业务参数刷新成功。");
								} else {
									$.dtmessagebox.alert("业务参数刷新失败。");
								}
							});
				});
	});
</script>

</head>
<body>
	<dt:tabs height="450px;" width="100%" id="cacheMangertabs" >
		<dt:tabsheet label="表数据缓存" id="tableCacheManagerId">
			<dt:grid id="tableCacheResult"
				url="sm/cacheManager!getTableCache.action" jsonRoot="_self"
				showPager="false" shrinkToFit="true" dataType="json" width="97%"
				height="330px">
				<dt:gridColumn name="mapName" hidden="true" key="true" />
				<dt:gridColumn name="mapName" label="缓存标识" />
				<dt:gridColumn name="tableName" label="表名" />
				<dt:gridColumn name="keyColumn" label="键列名" />
				<dt:gridColumn name="valueColumn" label="值列名" />
				<dt:gridColumn name="conditionClause" label="查询条件" />
				<dt:gridColumn name="orderColumn" label="排序条件" />
				<dt:gridColumn name="" label="备注" />
				<dt:gridColumn name="" label="操作"
					formatter="tableCacheOperateTransformLink" />
			</dt:grid>
			<div align="center">
				<dt:button id="reloadAllTableCache" label="刷新全部" />
			</div>
		</dt:tabsheet>
		<dt:tabsheet label="配置参数缓存" id="configParamCacheManagerId">
			<table class="condition" style="width: 100%" align="center">
				<tr>
					<th>值域数据缓存</th>
					<td>对值域及值域取值信息进行数据缓存；在值域管理功能中进行数据的维护；本功能下只负责<a
						id="reloadDomainCache" href="javascript:void(0)"
						style="color: red;">刷新</a></td>
				</tr>
				<tr>
					<th>系统参数缓存</th>
					<td>对系统参数信息进行数据缓存；在系统管理功能中进行数据的维护；本功能下只负责<a
						id="reloadSysParamCache" href="javascript:void(0)"
						style="color: red;">刷新</a></td>
				</tr>
				<tr>
					<th>业务参数缓存</th>
					<td>对业务参数信息进行数据缓存；在业务管理功能中进行数据的维护；本功能下只负责<a
						id="reloadBusiParamCache" href="javascript:void(0)"
						style="color: red;">刷新</a>
					</td>
				</tr>
			</table>
		</dt:tabsheet>
	</dt:tabs>
</body>
</html>