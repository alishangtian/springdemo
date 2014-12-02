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
		<title>密码修改</title>
		<dt:comppath></dt:comppath>
		<link rel="stylesheet" href="<%=path%>/css/new.style.css" type="text/css" charset="utf-8" />
		<script type='text/javascript' src='<%=request.getContextPath()%>/js/func.md5.js'></script>
		<script type="text/javascript">
			var path='<%=path%>',md5 = new $.Md5();;
			function resetPW() {
				// 1.清空上次操作的执行结果
				$("#retMsg").empty();
				$("#pwdMsgDiv").empty();
				$("#newPwd1MsgDiv").empty();
				$("#newPwd2MsgDiv").empty();
				// 2.密码信息验证
				var pwd = $("#pwd").val(),password = $("#password").val(),oldPwd = $("#oldPwd").val(),newPwd1 = $("#newPwd1").val(),newPwd2 = $("#newPwd2").val(),
				regExp = /[-\da-zA-Z`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]*((\d+[a-zA-Z]+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+)|(\d+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+[a-zA-Z]+)|([a-zA-Z]+\d+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+)|([a-zA-Z]+[-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+\d+)|([-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+\d+[a-zA-Z]+)|([-`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]+[a-zA-Z]+\d+))[-\da-zA-Z`=\\\[\];',./~!@#$%^&*()_+|{}:"<>?]*/;
				if (!pwd) {
					$("#pwdMsgDiv").html("<font color='red'>请输入旧密码</font>");
					return false;
				} else if (md5.hex_md5(pwd) != password) {
					$("#pwdMsgDiv").html("<font color='red'>旧密码错误</font>");
					$("#pwd").focus();
					return false;
				}
				if (!newPwd1) {
					$("#newPwd1MsgDiv").html("<font color='red'>请输入新密码</font>");
					$("#newPwd1").focus();
					return false;
				} else if (newPwd1.length<6 || newPwd1.length>8 || !regExp.test(newPwd1)) {
					$("#newPwd1MsgDiv").html("<font color='red'>新密码不满足要求</font>");
					$("#newPwd1").focus();
					return false;
				} else if (newPwd1 == pwd) {
					$("#newPwd1MsgDiv").html("<font color='red'>新密码不能与旧密码相同</font>");
					$("#newPwd1").focus();
					return false;
				} else if (md5.hex_md5(newPwd1) == oldPwd) {
					$("#newPwd1MsgDiv").html("<font color='red'>新密码不能与上次密码相同</font>");
					$("#newPwd1").focus();
					return false;
				}
				if (!newPwd2) {
					$("#newPwd2MsgDiv").html("<font color='red'>请确认新密码</font>");
					$("#newPwd2").focus();
					return false;
				} else if (newPwd2 != newPwd1) {
					$("#newPwd2MsgDiv").html("<font color='red'>确认新密码出错</font>");
					$("#newPwd2").focus();
					return false;
				}
				// 3.ajax修改用户密码
				$.ajax({
					url : "sm/SysUser!modifyPassword.action",
					type : 'post',
					async : false,
					data : {
						password : md5.hex_md5($("#pwd").val()),
						newPassword : md5.hex_md5($("#newPwd1").val())
					},
					success : function(retMsg) {
						// 2.1.反馈执行结果
						$("#retMsg").html("<font color='red'>" + retMsg.Msg + "</font>");
						if ("Success" == retMsg.Code) {
							// 2.2.更新隐藏域内的信息
							$("#password").val(md5.hex_md5($("#newPwd1").val()));
							$("#oldPwd").val(md5.hex_md5($("#pwd").val()));
							$("#pwd").val("");
							$("#newPwd1").val("");
							$("#newPwd2").val("");
						}
					},
					error : function(e) {
						alert(e);
					}
				});
			}
	</script>
</head>
<body class="in-body">
	<input type="hidden" id="password" name="password" value='<s:property value="#session.LOGIN_USER.password"/>' />
	<input type="hidden" id="oldPwd" name="oldPwd" value='<s:property value="#session.LOGIN_USER.oldPwd"/>' />
	<table class="query-form fl" style="width:450px;">
		<tr>
			<th>登录名称</th>
			<td><s:property value="#session.LOGIN_USER.loginName" /></td>
		</tr>
		<tr>
			<th>旧密码</th>
			<td><dt:input id="pwd" name="pwd" inputType="password" width="150px" height="25px"></dt:input><span></span></td>
		</tr>
		<tr>
			<th>新密码</th>
			<td><dt:input id="newPwd1" name="newPwd1" inputType="password" width="150px" height="25px"></dt:input><span></span></td>
		</tr>
		<tr>
			<th>确认新密码</th>
			<td><dt:input id="newPwd2" name="newPwd2" inputType="password" width="150px" height="25px"></dt:input><span></span></td>
		</tr>
	</table>
	<div style="margin-left:460px;color:#f00;">
		<p>新密码必须满足下列复杂性要求：</p>
		<p>密码长度为6-8个字符；</p>
		<p>必须包含字母、数字、特殊符号；</p>
		<p>不能使用前2次使用过的密码；</p>
		<p>密码最多只能使用90天，在90天过期前，请务必进行更改。</p>
	</div>
	<div class="clear"></div>
	<div class="query-form-btns" style="width:450px;">
		<dt:button id="BTN_MODIFY" click="resetPW" label="修改"/>
		<div id="retMsg"></div>
	</div>
</body>
</html>