<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="dt" uri="/dttag"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="t" uri="/cacheTag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
		<title>用户启用停用</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript">
			var path='<%=path%>';
			function querySysUserList() {
				sysUserQueryResult.dtpaginggrid("option","url", 'sm/sysUserManagerAction!getSysUserPaging.action?'+$('#queryForm').serialize());
				sysUserQueryResult.dtpaginggrid("option","page", 1);
				sysUserQueryResult.dtpaginggrid("reload");
			}

			function changeExptime() {
				var users = sysUserQueryResult.dtpaginggrid('option','selarrrow');
				if (users.length == 0) {
					$.dtmessagebox.alert('请选择需要冻结/解冻的用户!');
				} else {
					var userIds = '[' + users.join(',') + ']';
					var userExpTime = $.trim($('#changeExpTime').val());
					if (userExpTime == null || userExpTime == '') {
						$.dtmessagebox.alert('用户失效时间不能为空!');
						return false;
					}
					$.post('sm/SysUser!changeExptimeFin.action', {
						'userIds' : userIds,
						'userExpTime' : userExpTime
					}, function(data) {
						if (data.result == true) {
							$.dtmessagebox.alert('用户失效时间更改成功!', '提示', function() {
								querySysUserList();
							});
						} else if (data.result == false) {
							$.dtmessagebox.alert(data.msg);
						} else {
							$.dtmessagebox.alert(data);
						}
					});
				}
			}
		</script>
	</head>
	<body class="in-body">
	<form id="queryForm">
	<table class="query-form" width="100%">
		<caption>查询用户</caption>
		<tr>
			<th>帐号类型</th>
			<td>
				<t:dmComboBox id="queryOwnerType" prompt="帐号类型" name="sysUserQuery.ownerType" domainCode="SYS_USER.OWNER_TYPE" hasNull="false" selectIndex="0"></t:dmComboBox>
			</td>
			<th>登录名称</th>
			<td><dt:input id="queryLoginName" name="sysUserQuery.loginName" height="25px" width="150px"/>
			</td>
			<th>员工姓名</th>
			<td><dt:input id="queryOwnerName" name="sysUserQuery.ownerName" height="25px" width="150px"/>
			</td>
		</tr>
	</table>
	</form>
	<div class="query-form-btns">
		<dt:button id="querySysUserListBtn" label="查询" click="querySysUserList" />
	</div>
	<dt:grid id="sysUserQueryResult" multiSelect="true" shrinkToFit="true"
		url="sm/sysUserManagerAction!getSysUserPaging.action?'+$('#queryForm').serialize()+'"
		jsonRoot="resultList" dataType="json" showPager="true" width="100%"
		height="250px">
		<dt:gridColumn name="userId" hidden="true" key="true" />
		<dt:gridColumn name="loginName" label="登录名称" />
		<dt:gridColumn name="userOwnerName" label="员工姓名" />
		<dt:gridColumn name="userExpTime" label="用户失效时间" />
		<dt:gridColumn name="pwdChgTime" label="密码设置时间" />
		<dt:gridColumn name="pwdExpTime" label="密码失效时间" />
	</dt:grid>
	<table class="query-form sumit-form" width="100%">
		<caption>用户启用停用</caption>
		<tr>
			<th style="width:100px">新失效时间</th>
			<td style="width:160px;border-right:0 none;" ><dt:dateInput id="changeExpTime" name="changeExpTime" showTime="true" showOnbutton="true" minDate="0d" height="25px" width="150px"></dt:dateInput></td>
			<td style="border:0 none;">
				<div class="query-form-btns" style="text-align:left;"><dt:button id="changeExptimeBtn" label="确定" click="changeExptime" /></div>
			</td>
		</tr>
		<tr>
			<th style="width:100px">新失效时间说明</th>
			<td colspan="2" class="red">如果用户当前的失效时间&lt;新的失效时间，则该用户将被解冻，新的失效时间到期时自动冻结该用户。
				<br /> 如果用户当前的失效时间&gt;新的失效时间，则该用户将比原来时间提前冻结，新的失效时间到期时自动冻结该用户。</td>
		</tr>
	</table>
</body>
</html>