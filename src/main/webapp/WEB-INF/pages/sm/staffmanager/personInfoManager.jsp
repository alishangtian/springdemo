<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>个人信息</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="main" />
<dt:comppath include="input,button"></dt:comppath>
<link rel='stylesheet' type='text/css' href="css/func.common.css" />
<link rel='stylesheet' type='text/css' href="css/func.cache.css" />
<link rel="stylesheet" type="text/css" href="css/func.css" />
<script type="text/javascript" src="js/json2.js"></script>
</head>
<body>
	<table id="personInfoTable" class="condition" style="width: 400px"
		align="center">
		<s:if test="staff!=null">
			<tr>
				<td colspan="2">个人信息 <a id="editPersonInfoAble"
					style="float: right;" href="javascript:void(0)">编辑</a></td>
			</tr>
			<tr>
				<th width="25%">员工代码</th>
				<td width="75%"><dt:input id="staffCode" disabled="true"
						length="10" immValidate="true" required="true" prompt="员工代码"
						value="${staff.staffCode}" /></td>
			</tr>
			<tr>
				<th>员工名称</th>
				<td><dt:input id="staffName" disabled="true" length="128"
						immValidate="true" required="true" prompt="员工名称"
						value="${staff.staffName}" /></td>
			</tr>
			<tr>
				<th>所属部门</th>
				<td><dt:input id="deptName" disabled="true" required="true"
						value="${staff.sysDept.deptName}" /></td>
			</tr>
			<tr>
				<th>移动电话</th>
				<td><dt:input id="mobileNbr" disabled="true" length="30"
						immValidate="true" inputType="mobile" prompt="移动电话"
						value="${staff.mobileNbr}" /></td>
			</tr>
			<tr>
				<th>固定电话</th>
				<td><dt:input id="fixNbr" disabled="true" length="30"
						immValidate="true" inputType="tel" prompt="固定电话"
						value="${staff.fixNbr}" /></td>
			</tr>
			<tr>
				<th>电子邮箱</th>
				<td><dt:input id="email" disabled="true" length="128"
						immValidate="true" inputType="email" prompt="电子邮箱"
						value="${staff.email}" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><dt:button id="editPersonInfo"
						label="确定" /> <input type="hidden" id="staffId"
					value="${staff.staffId}" /></td>
			</tr>
		</s:if>
		<s:else>
			<tr>
				<td colspan="2" style="color: red;">该用户没有员工资料信息。</td>
			</tr>
		</s:else>
	</table>
	<script type="text/javascript">
		$(function() {
			editPersonInfo.dtbutton("disable");
			$("#editPersonInfoAble").click(function() {
				$("#staffName").removeAttr("disabled");
				$("#fixNbr").removeAttr("disabled");
				$("#mobileNbr").removeAttr("disabled");
				$("#email").removeAttr("disabled");
				editPersonInfo.dtbutton("enable");
			});
			$("#editPersonInfo").click(function() {
				if (!$.dtvalidate("#personInfoTable", "caption")) {
					return;
				}
				$.post("sm/staffManagerAction!editPersonInfo.action", {
					'staff.staffId' : $("#staffId").val(),
					'staff.staffName' : $("#staffName").val(),
					'staff.fixNbr' : $("#fixNbr").val(),
					'staff.mobileNbr' : $("#mobileNbr").val(),
					'staff.email' : $("#email").val()
				}, function(data) {
					if (data == true) {
						$("#staffName").attr("disabled", "disabled");
						$("#fixNbr").attr("disabled", "disabled");
						$("#mobileNbr").attr("disabled", "disabled");
						$("#email").attr("disabled", "disabled");
						editPersonInfo.dtbutton("disable");
						$.dtmessagebox.alert("修改成功");
					} else {
						$.dtmessagebox.alert("修改失败");
					}
				});
			});
		});
	</script>
</body>
</html>