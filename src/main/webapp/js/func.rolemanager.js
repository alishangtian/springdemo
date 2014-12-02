/**
 * 查询角色列表
 */
function queryRoleList() {
	sysRoleQueryResult.dtpaginggrid("option","url", 'sm/roleManagerAction!getSysRolePaging.action?roleName='+$('#query_roleName').val());
	sysRoleQueryResult.dtpaginggrid("option","page", 1);
	sysRoleQueryResult.dtpaginggrid("reload");
}
/**
 * 获取角色详细信息，同时为其余三个tab页赋值
 */
function getSysRoleDetail() {
	var roleId = arguments[0], oldRoleId = $("#roleIdHid").val();
	$("#roleIdHid").val(roleId);
	var url = "sm/roleManagerAction!loadSysRolePage.action?roleId="
			+ roleId + "&random=" + Math.random();
	sysRoleManager.dttabs("url",0, url);
	var sysRoleFuncInfo = "sm/roleManagerAction!loadFuncInfoPage.action";
	sysRoleManager.dttabs("url",1, sysRoleFuncInfo);
	var sysRoleUserInfo = "sm/roleManagerAction!loadSysUserQueryPage.action";
	sysRoleManager.dttabs("url",2, sysRoleUserInfo);
	if(oldRoleId == ""){
		sysRoleManager.dttabs("load",0);
	}else if(roleId != oldRoleId){
		sysRoleManager.dttabs("load",sysRoleManager.dttabs("option","active"));
	}
}
/**
 * 打开新增角色窗口
 */
function openAddSysRolePage() {
	$.dtdialog.showModal({
		title : '新建角色',
		url : 'sm/roleManagerAction!loadSysRoleDetail.action',
		minWidth : 600,
		minHeight : 300,
		buttons : {
			'确定' : function() {
				if (!$.dtvalidate("#add_new_role_form", "caption")) {
					return;
				}
				var roleInfo = $("#add_new_role_form").serializeArray();
				$.post("sm/roleManagerAction!doCreateSysRole.action",
						roleInfo, function(data) {
							doResult(data, "保存成功，角色编号为：" + data.msg, function(){
								queryRoleList();
								getSysRoleDetail(data.msg);
								$.dtdialog.closeDialog("#add_new_role_form");
							});
						});
			},
			'取消' : function() {
				$(this).dtdialog('close');
			}
		}
	});
}
/**
 * 打开修改角色信息窗口
 */
function openChangeRole() {
	$.dtdialog.showModal({
		title : '修改角色',
		url : 'sm/roleManagerAction!loadSysRoleDetail.action?roleId=' + $("#roleIdHid").val(),
		minWidth : 600,
		minHeight : 300,
		buttons : {
			'确定' : function() {
				if (!$.dtvalidate("#add_new_role_form", "caption")) {
					return;
				}
				var roleInfo = $("#add_new_role_form").serializeArray();
				$.post("sm/roleManagerAction!doModifySysRole.action",
						roleInfo, function(data) {
							doResult(data, "更新成功", function(){
								var roleId = $("#roleIdHid").val();
								$("#roleIdHid").val("");
								getSysRoleDetail(roleId);
								$.dtdialog.closeDialog("#add_new_role_form");
							});
						});
			},
			'取消' : function() {
				$(this).dtdialog('close');
			}
		}
	});
}
/**
 * 删除角色
 */
function removeRole(){
	$.dtmessagebox.confirm("确定要注销吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("sm/roleManagerAction!removeSysRole.action", {
				"roleId": $("#roleIdHid").val()
			}, function(data) {
				doResult(data, "注销成功", function(){
					queryRoleList();
					getSysRoleDetail();
				});
			});
		}
	});
}
/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function onSelectFuncAuth() {
	if (arguments[2].isParent == false) {
		var roleId = $("#roleIdHid").val();
		$.post("sm/roleManagerAction!loadFuncAuth.action", {
			"roleId" : roleId,
			"nodeTreeId" : arguments[2].nodeTreeId
		}, function(data) {
			if($.isArray(data)){
				$("#funcDetail_Div").FuncAuth();
				$("#funcDetail_Div").FuncAuth("load", {
					data : data
				});
				$("#funcDetail_Div").data("isLoad", true);
			}else{
				$.dtmessagebox.alert(data);
			}
		});
	}
}
/**
 * 保存功能信息
 */
function saveSysRoleFuncInfo() {
	if($("#funcDetail_Div").data("isLoad") != true){
		$.dtmessagebox.alert("请选择功能!");
	}else{
		var data = $("#funcDetail_Div").FuncAuth("getData");
		$.post("sm/roleManagerAction!saveRoleFuncAuth.action", {
			"funcAuthInfos" : JSON.stringify(data),
			"roleId": $("#roleIdHid").val()
		}, function(data) {
			doResult(data, "保存成功");
		});
	}
}
/**
 * 查询用户列表
 */
function queryRoleUserList() {
	sysUserQueryResult.dtpaginggrid("option","url", 'sm/roleManagerAction!getSysUserPaging.action?'+ $('#queryForm1').serialize());
	sysUserQueryResult.dtpaginggrid("reload");
}
function authChange(){
	if($('#query_isauth:checked').length>0)
		$('#authId').val(true);
	else
		$('#authId').val(false);
	
}
function roleUserLoaded(){
	var datalist = sysUserQueryResult.getRowData();
	sysUserQueryResult.gridData = [];
	$.each(datalist, function(i, prop){
		var data = {"rowId": prop["sysUser.userId"],
				"userHas": false
				}
		if(prop.checked == true || prop.checked == "true"){
			data.userHas = true;
			sysUserQueryResult.setSelection(prop["sysUser.userId"], true);
		}
		sysUserQueryResult.gridData.push(data);
	});
}
/**
 * 保存用户角色授权信息
 */
function saveSysRoleUserInfo(){
	if(!sysUserQueryResult.gridData){
		$.dtmessagebox.alert("请选择用户!");
	}else{
		var checkedUsers = sysUserQueryResult.dtpaginggrid("option",'selarrrow'), gridData = sysUserQueryResult.gridData, retakeUsers = [], grantUsers = [];
		$.each(gridData, function(i, data){
			if(data.userHas == true){//已经授权的用户
				if($.inArray(data.rowId, checkedUsers) < 0){//删除的用户
					retakeUsers.push(data.rowId);
				}
			}else{//没授权的用户
				if($.inArray(data.rowId, checkedUsers) >= 0){//新授权的用户
					grantUsers.push(data.rowId);
				}
			}
		});
		$.post("sm/roleManagerAction!saveRoleUserAuth.action", {
			"retakeUsers" : JSON.stringify(retakeUsers),
			"grantUsers" : JSON.stringify(grantUsers),
			"roleId": $("#roleIdHid").val()
		}, function(data) {
			doResult(data, "保存成功");
		});
	}
	
}