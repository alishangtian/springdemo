/**
 * 查询用户列表
 */
function querySysUserList() {
	sysUserQueryResult.dtpaginggrid("option","url", 'sm/sysUserManagerAction!getSysUserPaging.action?'+$('#queryForm').serialize());
	sysUserQueryResult.dtpaginggrid("option","page", 1);
	sysUserQueryResult.dtpaginggrid("reload");
}
/**
 * 获取用户详细信息，同时为其余三个tab页赋值
 */
function getSysUserDetail() {
	var userId = arguments[0], oldUserId = $("#userIdHid").val();
	$("#userIdHid").val(userId);
	var url = "sm/sysUserManagerAction!getSysUserDetail.action?userId=" + userId;
	sysUserManager.dttabs("url",0, url);
	var sysUserRoleInfo = "sm/sysUserManagerAction!loadRoleAuthPage.action";
	sysUserManager.dttabs("url",1, sysUserRoleInfo);
	var sysUserFuncInfo = "sm/sysUserManagerAction!loadFuncInfoPage.action";
	sysUserManager.dttabs("url",2, sysUserFuncInfo);
	var sysUserAuthView = "sm/sysUserManagerAction!loadUserFuncInfosPage.action?userId="
			+ userId;
	sysUserManager.dttabs("url",3, sysUserAuthView);
	if (oldUserId == "") {
		sysUserManager.dttabs("load",0);
	} else if (userId != oldUserId) {
		sysUserManager.dttabs("load",sysUserManager.dttabs("option","active"));
	}
}
/**
 * 加载用户角色信息
 * 
 * @param userId
 */
function loadSysUserRoleInfo(userId) {
	$.post("sm/sysUserManagerAction!loadRoleAuth.action", {"userId" : userId}, function(data) {
		$("#roleInfo_RoleList_Div").RoleAndFuncAuth({
			classname : "role-selected",
			data : data,
			roleclick : function(roleData) {
				// 点击角色时的响应事件
				$("#userrole_checked_roleId").val(roleData.id);
				$("#roletitle").html("角色<font color='red'>["+roleData.text+"]</font>对应的权限");
				$("#role_funcDetail_Div").html("");
			}
		});
	});
}

/**
 * 选择功能树时触发事件，加载指定角色下的功能菜单对应的功能
 */
function onShowFuncAuth() {
	if (arguments[2].isParent == false) {
		//var userId = dt$.obj("#userIdHid").val();
		$.post("sm/roleManagerAction!viewFuncAuth.action", {
			"roleId" : $("#userrole_checked_roleId").val(),
			"nodeTreeId" : arguments[2].nodeTreeId
		}, function(data) {
			if ($.isArray(data)) {
				$("#role_funcDetail_Div").FuncAuth();
				$("#role_funcDetail_Div").FuncAuth("load", {
					disabled : true,
					data : data
				});
			} else {
				$.dtmessagebox.alert(data);
			}
		});
	}
}
/**
 * 修改用户失效时间
 */
function onChangeSysUserExpTime() {
	var dialog = $.dtdialog.showModal({
		title : '修改用户失效时间',
		url : 'sm/sysUserManagerAction!doChangeUserExpTime.action',
		minWidth : 200,
		minHeight : 150,
		buttons : {
			"确定" : function() {
				saveSysUserExpTime(dialog);
			}
		}
	});
}
/**
 * 保存失效时间
 */
function saveSysUserExpTime(dialog) {
	if ($("#change_userExpTime").val() == "") {
		$.dtmessagebox.alert("请选择失效时间");
	} else {
		$.post("sm/sysUserManagerAction!doModifyUserExpTime.action", {
			"userExpTime" : $("#change_userExpTime").val(),
			"userId" : $("#userIdHid").val()
		}, function(data) {
			doResult(data, "保存成功", function() {
				$("#tdUserExpTime").text($("#change_userExpTime").val());
				$(dialog).dtdialog('close');
			});
		});
	}
}
/**
 * 保存用户角色信息
 */
function saveSysUserRoleInfo() {
	var data = $("#roleInfo_RoleList_Div").RoleAndFuncAuth("getData");
	$.post("sm/sysUserManagerAction!doSaveUserRoleAuth.action", {
		"roleAuthInfos" : JSON.stringify(data),
		"userId" : $("#userIdHid").val()
	}, function(data) {
		doResult(data, "保存成功");
	});
}
/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function onSelectFuncAuth() {
	if (arguments[2].isParent == false) {
		var userId = $("#userIdHid").val();
		$.post("sm/sysUserManagerAction!loadFuncAuth.action", {
			"userId" : userId,
			"nodeTreeId" : arguments[2].nodeTreeId
		}, function(data) {
			if ($.isArray(data)) {
				$("#funcDetail_Div").FuncAuth();
				$("#funcDetail_Div").FuncAuth("load", {
					data : data
				});
				$("#funcDetail_Div").data("isLoad", true);
			} else {
				$.dtmessagebox.alert(data);
			}
		});
	}
}
/**
 * 加载权限视图
 */
function loadFuncDetailView() {
	var userId = $("#userIdHid").val();
	$.post("sm/sysUserManagerAction!loadUserFuncInfos.action", {
		"userId" : userId
	}, function(data) {
		showFuncDetailView(data);
	});
}
/**
 * 展现权限视图
 * 
 * @param data
 */
function showFuncDetailView(data) {
	$.each(data, function(i, prop) {
		var funcTd = $("<td></td>");
		funcTd.html(prop.funcNodeName);
		var operTd = $("<td></td>");
		$.each(prop.funcItemList, function(k, obj) {
			var span = $("<span></span>");
			span.text(obj.funcItemName);
			operTd.append(span);
		});
		var tr = $("<tr></tr>");
		tr.append(funcTd).append(operTd);
		$("#funcDetailView_Div").append(tr);
	});
}
/**
 * 保存功能信息
 */
function saveSysUserFuncInfo() {
	if ($("#funcDetail_Div").data("isLoad") != true) {
		$.dtmessagebox.alert("请选择功能!");
	} else {
		var data = $("#funcDetail_Div").FuncAuth("getData");
		$.post("sm/sysUserManagerAction!doSaveUserFuncAuth.action", {
			"funcAuthInfos" : JSON.stringify(data),
			"userId" : $("#userIdHid").val()
		}, function(data) {
			doResult(data, "保存成功");
		});
	}
}
/**
 * 打开新增用户窗口
 */
function openAddSysUser() {
	$.dtdialog.showModal({
		title : '新建用户',
		url : 'sm/sysUserManagerAction!initAddNewUserPage.action',
		minWidth : 700,
		minHeight : 180,
		buttons : {}
	});
}
function addUserNext1(){
	if (!$.dtvalidate("#new_user_table", "caption")) {
		return false;
	}
	var userInfo = $("#add_new_user_form").serializeArray();
	$.post("sm/sysUserManagerAction!valiNewLoginName.action", userInfo, function(data) {
		if (data.result) {
			$("#new_user_table").hide();
			$("#new_staff_table").fadeIn("fast");
			if ($("#new_ownerType_S").prop("checked") == true) {// 选择员工
				$("#new_staff_new_radio").prop("checked", true);
				$("#new_staff_select", $("#new_staff_table")).show();
				$("#new_staff_info_block", $("#new_staff_table")).show();
				$("#new_dept_info_block", $("#new_staff_table")).hide();
				$("#new_old_staff_info_block", $("#new_staff_table")).hide();
			} else {// 选择部门
				$("#new_old_staff_info_block", $("#new_staff_table")).hide();
				$("#new_staff_select", $("#new_staff_table")).hide();
				$("#new_staff_info_block", $("#new_staff_table")).hide();
				$("#new_dept_info_block", $("#new_staff_table")).show();
			}
		} else {
			if (data.msg) {
				$.dtmessagebox.alert(data.msg);
			} else {
				$.dtmessagebox.alert("验证登录名称失败");
			}
		}
	});
}
function addUserPre1(){
	$("#new_staff_table").hide();
	$("#new_user_table").fadeIn("fast");
}
function hide1(){
	$("#new_staff_info_block").hide();
	$("#new_old_staff_info_block").show();
}
function hide2(){
	$("#new_staff_info_block").hide();
	$("#new_old_staff_info_block").show();
}
function deptTreewin(){
	showDeptTree(function(deptInfo) {
			$("#new_dept_deptId").val(deptInfo.deptName);
			$("#new_dept_deptId_hid").val(deptInfo.deptId);
	});
}
/**
 * 查询员工信息
 */
function queryStaffList() {
	var param = {};
	param.staffName = $("#new_oldStaff_query").val();
	var url = "sm/staffManagerAction!getStaffPaging.action";
	staffQueryResult.dtpaginggrid("option","postData", param);
	staffQueryResult.dtpaginggrid("option","mtype", "POST");
	staffQueryResult.dtpaginggrid("option","url", url);
	staffQueryResult.dtpaginggrid("option","page", 1);
	staffQueryResult.dtpaginggrid("reload");
}
/**
 * 选择员工时，为隐藏域赋值
 */
function selectStaffInfo() {
	$("#new_oldStaffId").val(arguments[0]);
}
/**
 * 提交新用户信息
 */
function createSysUser() {
	var checkItem = "";
	if ($("#new_ownerType_S").prop("checked") == true) {// 选择员工
		if ($("#new_staff_new_radio").prop("checked") == true) {// 新员工
			checkItem = "#new_staff_info_block";
		} else {// 在职员工
			if ($("#new_oldStaffId").val() == "") {
				$.dtmessagebox.alert("请选择员工");
				return;
			}
		}
	} else {// 选择部门
		checkItem = "#new_dept_info_block";
	}
	if (checkItem != "") {
		if (!$.dtvalidate(checkItem, "caption")) {
			return;
		}
	}
	var userInfo = $("#add_new_user_form").serializeArray();
	$.post("sm/sysUserManagerAction!doCreateSysUser.action", userInfo,
			function(data) {
				doResult(data, "保存成功", function() {
					querySysUserList();
					$.dtdialog.closeDialog("#add_new_user_form");
				});
			});
}