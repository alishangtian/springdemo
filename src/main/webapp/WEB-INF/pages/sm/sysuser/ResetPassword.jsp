<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<title>修改密码</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type="text/javascript">
			var path='<%=path%>';
			function queryUser() {
				// 1.清空上次操作的执行结果
				$("#retMsg").empty();
				if (!$.dtvalidate("#query")) {
					return;
				}
				$("#userDetailDiv").show();
				// 2.ajax加载用户信息
				$.ajax({
					url : "sm/SysUser!getSysUserByNameResetPassword.action",
					type : 'post',
					async : false,
					data : {
						queryLoginName : $("#QUERY_LOGIN_NAME").val()
					},
					success : function(response) {
						// 2.1.加载用户信息
						$("#SysUserDiv").html(response);
						if ($("#userId").val() != "") {
							// 2.2.用户存在，则启动"重置"操作按钮
							$("#BTN_RESET").dtbutton("enable");
							//enable();
						} else {
							// 2.2.用户不存在，则禁用"重置"操作按钮
							$("#BTN_RESET").dtbutton("disable");
						}
					},
					error : function(e) {
						$("#BTN_RESET").dtbutton("disable");
						//BTN_RESET.disable();
					}
				});
			}
		</script>
	</head>
<body class="in-body">
	<table class="query-form" width="100%">
		<caption>查询用户</caption>
		<tr>
			<th>登录名称</th>
			<td>
				<dt:input id="QUERY_LOGIN_NAME" required="true" prompt="登录名称" />
				<dt:button id="BTN_QUERY" label="查询"/>
				<span>注：请输入完整的登录名称</span>
			</td>
		</tr>
	</table>
	<s:if test="sysUser!=null">
	<table class="query-form" width="100%">
		<caption>用户资料</caption>
		<tr>
			<th>登录名称</th>
			<td><s:property value="sysUser.loginName" />
			</td>
			<th>用户失效时间</th>
			<td id="tdUserExpTime"><s:date name="sysUser.userExpTime" format="yyyy-MM-dd HH:mm:ss" />
			</td>
		</tr>
		<tr>
			<th>密码设置时间</th>
				<td><s:date name="sysUser.pwdChgTime"format="yyyy-MM-dd HH:mm:ss" /></td>
				<th>密码失效时间</th>
				<td><s:date name="sysUser.pwdExpTime"format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
	</table>
	<s:if test="sysUser.staff!=null">
		<table class="query-form" width="100%">
			<caption>员工资料</caption>
			<tr>
				<th>员工代码</th>
				<td><s:property value="sysUser.staff.staffCode" />
				</td>
				<th>员工姓名</th>
				<td><s:property value="sysUser.staff.staffName" />
				</td>
			</tr>
			<tr>
				<th>固定电话</th>
				<td><s:property value="sysUser.staff.fixNbr" />
				</td>
				<th>移动电话</th>
				<td><s:property value="sysUser.staff.mobileNbr" />
				</td>
			</tr>
			<tr>
				<th>所属部门</th>
				<td><s:property
						value="sysUser.staff.sysDept.deptName" />
				</td>
				<th>电子邮箱</th>
				<td><s:property value="sysUser.staff.email" />
				</td>
			</tr>
		</table>
		</s:if>
		<s:if test="sysUser.sysDept!=null">
			<table class="query-form" width="100%">
			<caption>所属客户资料</caption>
			<tr>
				<th>部门代码</th>
				<td><s:property value="sysUser.sysDept.deptCode" />
				</td>
				<th>部门名称</th>
				<td><s:property value="sysUser.sysDept.deptName" />
				</td>
			</tr>
			<tr>
				<th>邮编</th>
				<td><s:property value="sysUser.sysDept.postCode" />
				</td>
				<th>地址</th>
				<td><s:property value="sysUser.sysDept.address" />
				</td>
			</tr>
			<tr>
				<th>联系人</th>
				<td><s:property value="sysUser.sysDept.linkMan" />
				</td>
				<th>联系电话</th>
				<td><s:property value="sysUser.sysDept.telNbr" />
				</td>
			</tr>
			<tr>
				<th>传真</th>
				<td><s:property value="sysUser.sysDept.faxNbr" />
				</td>
				<th>电子邮箱</th>
				<td><s:property value="sysUser.sysDept.emailAddr" />
				</td>
			</tr>
			<tr>
				<th>网站地址</th>
				<td><s:property value="sysUser.sysDept.webSite" />
				</td>
				<th>备注</th>
				<td><s:property value="sysUser.sysDept.remarks" />
				</td>
			</tr>
		</table>
		</s:if>
		<table class="query-form" width="100%">
			<caption>新密码接收邮箱</caption>
			<tr>
				<th>电子邮箱</th>
				<td><s:if
						test="sysUser!=null && sysUser.sysDept!=null">
						<dt:input inputType="mail" id="email" prompt="电子邮箱"
							required="true" value="${sysUser.sysDept.emailAddr}" />
					</s:if> <s:if test="sysUser!=null && sysUser.staff!=null">
						<dt:input inputType="mail" id="email" prompt="电子邮箱"
							required="true" value="${sysUser.staff.email}" />
					</s:if></td>
			</tr>
		</table>
		<div class="query-form-btns">
			<dt:button id="BTN_RESET" label="重置" disabled="true"></dt:button>
			<div id="retMsg"></div>
		</div>
		<input type="hidden" id="userId" name="userId" value="${sysUser.userId}"></input>
		</s:if>
		<s:else>
			<div style="color: red; height: 20px">
				没有找到有效的用户，登录名称输入有误或者用户已过期。
			</div>
		</s:else>
</body>
</html>