<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="dt" uri="/dttag"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户历史资料</title>
<dt:comppath></dt:comppath>
<link rel="stylesheet" type="text/css" charset="utf-8"
	href="<%=request.getContextPath()%>/css/func.css" />
<link rel='stylesheet' type='text/css' charset="utf-8"
	href='<%=request.getContextPath()%>/css/func.common.css' />
<link rel="stylesheet" type="text/css" charset="utf-8"
	href="<%=request.getContextPath()%>/css/func.cache.css" />
<script>
	function querySysUserList() {
		$("#staffTable").hide();
		$("#deptTable").hide();
		var param = {};
		param.loginName = $("#queryLoginName").val();
		param.ownerType = $("#queryOwnerType").val();
		param.ownerName = $("#queryOwnerName").val();
		var url = "sm/SysUser!getSysUserPagingHistory.action";
		sysUserQueryResultHistory.dtpaginggrid('option',"postData", param);
		sysUserQueryResultHistory.dtpaginggrid('option',"mtype", "POST");
		sysUserQueryResultHistory.dtpaginggrid('option',"url", url);
		sysUserQueryResultHistory.dtpaginggrid('option',"page", 1);
		sysUserQueryResultHistory.dtpaginggrid('reload');
	}

	function transformLink(cellvalue, options, rowObject) {
		if (cellvalue != null && cellvalue != "") {
			var userId = rowObject.userId;
			cellvalue = "<a href='javascript:void(0)' onclick='return getOwnerInfo("
					+ userId + ");'>" + cellvalue + "</a>";
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	function getOwnerInfo(userId) {
		$.post("sm/SysUser!getSysUserById.action", {
			"sysUserIdHistory" : userId
		}, function(sysUserHistory) {
			$("#staffTable").hide();
			$("#deptTable").hide();
			if (sysUserHistory != null) {
				if (sysUserHistory.staff != null) {
					$("#ownerNameSpan").html(
							"[" + sysUserHistory.staff.staffName + "]");
					$("#staffCode").html(sysUserHistory.staff.staffCode);
					$("#staffName").html(sysUserHistory.staff.staffName);
					$("#fixNbr").html(sysUserHistory.staff.fixNbr);
					$("#mobileNbr").html(sysUserHistory.staff.mobileNbr);
					$("#email").html(sysUserHistory.staff.email);
					if (sysUserHistory.staff.sysDept != null) {
						$("#deptNameStaff").html(
								sysUserHistory.staff.sysDept.deptName);
					}
					$("#staffTable").show();
				}
				if (sysUserHistory.sysDept != null) {
					$("#ownerNameSpan").html(
							"[" + sysUserHistory.sysDept.deptName + "]");
					$("#deptCode").html(sysUserHistory.sysDept.deptCode);
					$("#deptName").html(sysUserHistory.sysDept.deptName);
					$("#postCode").html(sysUserHistory.sysDept.postCode);
					$("#address").html(sysUserHistory.sysDept.address);
					$("#linkMan").html(sysUserHistory.sysDept.linkMan);
					$("#telNbr").html(sysUserHistory.sysDept.telNbr);
					$("#fixNbrDept").html(sysUserHistory.sysDept.fixNbr);
					$("#emailAddr").html(sysUserHistory.sysDept.emailAddr);
					$("#webSite").html(sysUserHistory.sysDept.webSite);
					$("#remarks").html(sysUserHistory.sysDept.remarks);
					$("#deptTable").show();
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="ui-widget-content func-panel">
		<div class="ui-widget-header func-header">用户历史资料</div>
		<table class="condition" width="100%" align="center">
			<tr>
				<th>帐号类型</th>
				<td><select id="queryOwnerType" name="queryOwnerType">
						<core-tags:options domainCode="SYS_USER.OWNER_TYPE" />
				</select></td>
				<th>登录名称</th>
				<td><dt:input width="100" id="queryLoginName" /></td>
				<th>员工姓名</th>
				<td><dt:input width="100" id="queryOwnerName" /></td>
			</tr>
			<tr>
				<td colspan="6" align="center"><dt:button
						id="querySysUserListBtn" label="查询" click="querySysUserList" /></td>
			</tr>
		</table>
		<dt:grid id="sysUserQueryResultHistory" shrinkToFit="true"
			jsonRoot="resultList" dataType="json" showPager="true" width="100%"
			height="200px">
			<dt:gridColumn name="userId" hidden="true" key="true"></dt:gridColumn>
			<dt:gridColumn name="loginName" label="登录名称" />
			<dt:gridColumn name="userOwnerName" label="员工姓名"
				formatter="transformLink" />
			<dt:gridColumn name="userExpTime" label="用户失效时间" />
			<dt:gridColumn name="pwdChgTime" label="密码设置时间" />
			<dt:gridColumn name="pwdExpTime" label="密码失效时间" />
		</dt:grid>
		<div style="color: red;">说明：点击“员工姓名”链接，查看其完整信息。</div>
		<div class="ui-widget-header func-header">
			所属者
			<span id="ownerNameSpan" style="color: red"></span>
			的信息
		</div>
		<table id="staffTable" class="condition" width="100%" align="center"
			style="display: none">
			<tr>
				<th width="25%">员工代码</th>
				<td width="25%" id="staffCode"></td>
				<th width="25%">员工名称</th>
				<td width="25%" id="staffName"></td>
			</tr>
			<tr>
				<th>固定电话</th>
				<td id="fixNbr"></td>
				<th>移动电话</th>
				<td id="mobileNbr"></td>
			</tr>
			<tr>
				<th>所属部门</th>
				<td id="deptNameStaff"></td>
				<th>电子邮箱</th>
				<td id="email"></td>
			</tr>
		</table>
		<table id="deptTable" class="condition" width="100%" align="center"
			style="display: none">
			<tr>
				<th width="25%">部门代码</th>
				<td width="25%" id="deptCode"></td>
				<th width="25%">部门名称</th>
				<td width="25%" id="deptName"></td>
			</tr>
			<tr>
				<th>邮编</th>
				<td id="postCode"></td>
				<th>地址</th>
				<td id="address"></td>
			</tr>
			<tr>
				<th>联系人</th>
				<td id="linkMan"></td>
				<th>联系电话</th>
				<td id="telNbr"></td>
			</tr>
			<tr>
				<th>传真</th>
				<td id="fixNbrDept"></td>
				<th>电子邮箱</th>
				<td id="emailAddr"></td>
			</tr>
			<tr>
				<th>网站地址</th>
				<td id="webSite"></td>
				<th>备注</th>
				<td id="remarks"></td>
			</tr>
		</table>
	</div>
</body>
</html>