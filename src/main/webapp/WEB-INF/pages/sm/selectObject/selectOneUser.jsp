<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/dttag" prefix="dt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/func-tags" prefix="func-tags"%>
<%@ taglib uri="/core-tags" prefix="core-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择单用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.css" type="text/css" charset="utf-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/func.cache.css" type="text/css" charset="utf-8" />
<script type="text/javascript">
	/**
	 * 查询用户列表
	 */
	function querySysUserList() {
		var param = {};
		param.loginName = $("#queryLoginName").val();
		param.ownerType = $("#queryOwnerType").val();
		param.ownerName = $("#queryOwnerName").val();
		var url = "sm/sysUserManagerAction!getSysUserPaging.action";
		selectOneUserGrid.dtpaginggrid("option","postData", param);
		selectOneUserGrid.dtpaginggrid("option","mtype", "POST");
		selectOneUserGrid.dtpaginggrid("option","url", url);
		selectOneUserGrid.dtpaginggrid("reload");
	}
	//添加单选触发事件
	function formatRadio(cellvalue, options, rowObject) {
	     var result=new Array();
	     result.push("<div align = 'center'>");
		 result.push("<input type=\"radio\" name=\"radioId\" value=\"\" onclick=\"checkedradio('"+rowObject.staff.staffName+"','"+rowObject.userId+"')\";");
		 result.push("</div>");
		 return result.join(''); 
	}
   var selectUser = null;
   //单选触发事件
   function checkedradio(paramStaffName,paramUserId){
	    selectUser = '{id:'+paramUserId+',name:'+paramStaffName+'}';
	}
   //提供获取数据函数
   function getSelectObjectData(){
		return selectUser==null?'':selectUser;
  	}
</script>
</head>
<body>
	<table width="100%" align="center" class="condition">
		<tr>
			<th width="20%" align="left">登录名：</th>
			<td width="15%" valign="middle">
				<dt:input width="100" id="queryLoginName"></dt:input>
			</td>
			<th width="20%" align="left">使用者类型：</th>
			<td width="10%" valign="middle">
				<select id="queryOwnerType" name="queryOwnerType">
						<core-tags:options domainCode="SYS_USER.OWNER_TYPE" />
				</select>
			</td>
			<th width="20%" align="left">使用者名称：</th>
			<td width="15%" valign="middle">
				<dt:input width="100" id="queryOwnerName"></dt:input>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="6">
				<dt:button id="querySysUserListBtn" label="查询" type="ok" click="querySysUserList"></dt:button>
			</td>
		</tr>
	</table>
	<dt:grid id="selectOneUserGrid" shrinkToFit="true" rowNum="5"
		url="sm/sysUserManagerAction!getSysUserPaging.action"
		jsonRoot="resultList" dataType="json" showPager="true" sortable="true" width="100%" height="50%">
		<dt:gridColumn name="userId" hidden="true" key="true" ></dt:gridColumn>
		<dt:gridColumn name="radio" width="50" label="选择" formatter="formatRadio"></dt:gridColumn>
		<dt:gridColumn name="loginName" width="90" sortable="true" label="登录名"></dt:gridColumn>
		<dt:gridColumn name="staff.staffName" sortable="true" label="使用者名称"></dt:gridColumn>
	</dt:grid>
</body>
</html>