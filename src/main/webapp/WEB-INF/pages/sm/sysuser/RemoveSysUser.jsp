<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>用户注销</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" type="text/css" charset="utf-8"
	href="<%=request.getContextPath()%>/css/func.css" />
<link rel='stylesheet' type='text/css' charset="utf-8"
	href='<%=request.getContextPath()%>/css/func.common.css' />
<link rel="stylesheet" type="text/css" charset="utf-8"
	href="<%=request.getContextPath()%>/css/func.cache.css" />
<script>
	$(function() {
		/**
		 * "查询"按钮执行的功能
		 */
		$("#BTN_QUERY").bind('click', function() {
			// 1.清空上次操作的执行结果
			$("#retMsg").empty();
			if (!$.dtvalidate("#query")) {
				return;
			}
			$("#userDetailDiv").show();
			// 2.ajax加载用户信息
			$.ajax({
				url : "sm/SysUser!getSysUserByName.action",
				type : 'post',
				async : false,
				data : {
					queryLoginName : $("#QUERY_LOGIN_NAME").val()
				},
				success : function(response) {
					// 2.1.加载用户信息
					$("#SysUserDiv").html(response);
					if ($("#userId").val() != "") {
						// 2.2.用户存在，则启动"注销"操作按钮
						$("#BTN_REMOVE").dtbutton("enable");
						//BTN_REMOVE.enable();
					} else {
						$("#BTN_REMOVE").dtbutton("disable");
						//BTN_REMOVE.disable();
						// 2.2.用户不存在，则禁用"注销"操作按钮
					}
				},
				error : function(e) {
					$("#BTN_REMOVE").dtbutton("disable");
					//BTN_REMOVE.disable();
				}
			});
		});
		/**
		 * "注销"按钮执行的功能
		 */
		$("#BTN_REMOVE").bind(
				"click",
				function() {
					// 1.清空上次操作的执行结果
					$("#retMsg").empty();
					$.dtmessagebox.confirm("确定要注销吗？", "询问", function(msgBtn) {
						if (msgBtn == $.dtdialog.DR_OK) {
							// 2.ajax用户注销
							$.ajax({
								url : "sm/SysUser!removeSysUser.action",
								type : 'post',
								async : false,
								data : {
									sysUserId : $("#userId").val()
								},
								success : function(retMsg) {
									// 2.1.反馈执行结果
									$("#retMsg").html(
											"<font color='red'>" + retMsg.Msg
													+ "</font>");
									if ("Success" == retMsg.Code) {
										// 2.2.注销成功后即禁用"注销"按钮，避免再次注销
										$("#BTN_REMOVE").dtbutton("disable");
									}
								},
								error : function(e) {
									alert(e);
								}
 							});
						}
					});
				});
	}); 
</script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<div class="ui-widget-header func-header">查询用户</div>
		<table id="query" class="condition" style="width: 100%;"
			align="center">
			<tr>
				<th>登录名称</th>
				<td>
					<dt:input id="QUERY_LOGIN_NAME" required="true" prompt="登录名称" /> &nbsp;&nbsp; 
					<dt:button id="BTN_QUERY" label="查询" />
					<span>注：请输入完整的登录名称</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="ui-widget-content func-panel" id="userDetailDiv"
		style="display: none;">
		<div id="SysUserDiv" style="height: auto"></div>
	</div>
	<div>
		<dt:button id="BTN_REMOVE" label="注销" disabled="true"></dt:button>
		<div id="retMsg"></div>
	</div>
</body>
</html>