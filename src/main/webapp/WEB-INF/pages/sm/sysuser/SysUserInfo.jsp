<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<script>
	$(function() {
		/**
		 * "重置"按钮执行的功能
		 */
		$("#BTN_RESET").bind(
				"click",
				function() {
					// 1.清空上次操作的执行结果
					$("#retMsg").empty();
					// 2.ajax重置密码
					/* var email=$.trim($("#email").val()); */
					if ($.dtvalidate("#email")) {
						$.ajax({
							url : "sm/SysUser!resetPassword.action",
							type : 'post',
							async : false,
							data : {
								/* sysUserId : $("#userId").val() */
								'userEmail.userId' : $("#userId").val(),
								'userEmail.emailAddress' : $("#email").val()
							},
							success : function(retMsg) {
								alert(retMsg.Msg);
								// 2.1.反馈执行结果
								$("#retMsg").html(
										"<font color='red'>" + retMsg.Msg
												+ "</font>");
								if ("Success" == retMsg.Code) {
									// 2.2.重置成功后即禁用"重置"按钮，避免再次重置
									$("BTN_RESET").dtbutton("disable");
									//BTN_RESET.disable();
								}
							},
							error : function(e) {
								alert(e);
							}
						});
					}
				});
	});
</script>
</head>
<body>
	<div style="height: 100%">
		<div class="ui-widget-header func-header">用户资料</div>
		<s:if test="sysUser!=null">
			<table class="condition" style="width: 100%;" align="center">
				<tr>
					<th width="25%">登录名称</th>
					<td width="25%"><s:property value="sysUser.loginName" /></td>
					<th width="25%">用户失效时间</th>
					<td width="25%" id="tdUserExpTime"><s:date
							name="sysUser.userExpTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th width="25%">密码设置时间</th>
					<td width="25%"><s:date name="sysUser.pwdChgTime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
					<th width="25%">密码失效时间</th>
					<td width="25%"><s:date name="sysUser.pwdExpTime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<!-- 				<tr> -->
				<!-- 					<th width="25%">用户状态</th> -->
				<%-- 					<td width="25%"><s:property value="sysUser.sts" /> --%>
				<!-- 					</td> -->
				<!-- 					<th width="25%">状态时间</th> -->
				<%-- 					<td width="25%"><s:date name="sysUser.stsTime" --%>
				<!-- 							format="yyyy-MM-dd HH:mm:ss" /> -->
				<!-- 					</td> -->
				<!-- 				</tr> -->
			</table>
			<div class="ui-widget-header func-header">所属者资料</div>
			<s:if test="sysUser!=null && sysUser.staff!=null">
				<table class="condition" style="width: 100%;" align="center">
					<tr>
						<th width="25%">员工代码</th>
						<td width="25%"><s:property value="sysUser.staff.staffCode" />
						</td>
						<th width="25%">员工姓名</th>
						<td width="25%"><s:property value="sysUser.staff.staffName" />
						</td>
					</tr>
					<tr>
						<th width="25%">固定电话</th>
						<td width="25%"><s:property value="sysUser.staff.fixNbr" />
						</td>
						<th width="25%">移动电话</th>
						<td width="25%"><s:property value="sysUser.staff.mobileNbr" />
						</td>
					</tr>
					<tr>
						<th width="25%">所属部门</th>
						<td width="25%"><s:property
								value="sysUser.staff.sysDept.deptName" /></td>
						<th width="25%">电子邮箱</th>
						<td width="25%"><s:property value="sysUser.staff.email" /></td>
					</tr>
				</table>
			</s:if>
			<s:if test="sysUser!=null && sysUser.sysDept!=null">
				<table class="condition" style="width: 100%;" align="center">
					<tr>
						<th width="25%">部门代码</th>
						<td width="25%"><s:property value="sysUser.sysDept.deptCode" />
						</td>
						<th width="25%">部门名称</th>
						<td width="25%"><s:property value="sysUser.sysDept.deptName" />
						</td>
					</tr>
					<tr>
						<th width="25%">邮编</th>
						<td width="25%"><s:property value="sysUser.sysDept.postCode" />
						</td>
						<th width="25%">地址</th>
						<td width="25%"><s:property value="sysUser.sysDept.address" />
						</td>
					</tr>
					<tr>
						<th width="25%">联系人</th>
						<td width="25%"><s:property value="sysUser.sysDept.linkMan" />
						</td>
						<th width="25%">联系电话</th>
						<td width="25%"><s:property value="sysUser.sysDept.telNbr" />
						</td>
					</tr>
					<tr>
						<th width="25%">传真</th>
						<td width="25%"><s:property value="sysUser.sysDept.fixNbr" />
						</td>
						<th width="25%">电子邮箱</th>
						<td width="25%"><s:property value="sysUser.sysDept.emailAddr" />
						</td>
					</tr>
					<tr>
						<th width="25%">网站地址</th>
						<td width="25%"><s:property value="sysUser.sysDept.webSite" />
						</td>
						<th width="25%">备注</th>
						<td width="25%"><s:property value="sysUser.sysDept.remarks" />
						</td>
					</tr>
				</table>
			</s:if>
			<input type="hidden" id="userId" name="userId"
				value="${sysUser.userId}"></input>
		</s:if>
		<s:else>
			<div style="color: red; height: 20px">
				没有找到有效的用户，登录名称输入有误或者用户已过期。</div>
		</s:else>
	</div>
</body>
</html>