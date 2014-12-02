/**
 * 员工列表
 */
function queryStaffList(deptId) {
	var param = {};
	if(deptId != undefined && (typeof deptId === "string" || typeof deptId === "number")){
		param.deptId = deptId;
		param.staffName = "";
	}else{
		param.staffName = $("#query_staffName").val();
		param.deptId = "";
	}
	var url = "sm/staffManagerAction!getStaffPaging.action";
	staffQueryResult.dtpaginggrid("option","postData", param);
	staffQueryResult.dtpaginggrid("option","mtype", "POST");
	staffQueryResult.dtpaginggrid("option","autowidth", true);
	staffQueryResult.dtpaginggrid("option","url", url);
	staffQueryResult.dtpaginggrid("option","page", 1);
	staffQueryResult.dtpaginggrid("reload");
}
/**
 * 点击员工时，为右边三个tab页赋值
 */
function getStaffDetail() {
	var staffId = arguments[0], oldStaffId = $("#staffIdHid").val();
	if(staffId == ""){
		// TODO 清空tab中的内容，需要tab组件提供相关接口，目前的方法可能导致浏览器内存泄漏
		$("#staff_tab", $("#staffManager")).html("");
		$("#staff_user_tab", $("#staffManager")).html("");
	}else{
		$("#staffIdHid").val(staffId);
		var staffInfoUrl = "sm/staffManagerAction!getStaffDetail.action?staffId="+ staffId + "&random=" + Math.random();
		staffManager.dttabs("url",0,staffInfoUrl);
		var usersUrl = "sm/staffManagerAction!loadSysUsers.action?staffId="+ staffId + "&random=" + Math.random();
		staffManager.dttabs("url",1,usersUrl);
		if (oldStaffId == "") {
			staffManager.dttabs("load",0);
		} else{
			staffManager.dttabs("load",staffManager.dttabs("option","active"));
		}
	}
}
/**
 * 注销用户
 */
function removeSysUsers() {
	var reomoveUsers = sysUserResultList.dtpaginggrid("option",'selarrrow');
	if (reomoveUsers.length == 0) {
		$.dtmessagebox.alert("请选择需要注销的用户!");
	} else {
		var userIds = "[" + reomoveUsers.join(",") + "]";
		$.post("sm/staffManagerAction!removeSysUsers.action", {
			"userIds" : userIds
		}, function(data) {
			doResult(data, "注销成功", function(){
				var staffId = $("#staffIdHid").val();
				getStaffDetail(staffId);
			});
		});
	}
}
/**
 * 打开新建员工窗口
 */
function openAddStaff() {
	$.dtdialog.showModal({
		title : '新建员工',
		url : 'sm/staffManagerAction!loadStaffDetail.action',
		minWidth : 600,
		minHeight : 300,
		buttons : {
			'确定' : function() {
				if (!$.dtvalidate("#add_new_staff_form", "caption")) {
					return;
				}
				var staffInfo = $("#add_new_staff_form").serializeArray();
				$.post("sm/staffManagerAction!createStaff.action",
						staffInfo, function(data) {
							doResult(data, "保存成功，员工编号为：" + data.msg, function(){
								$("#query_staffName").val($("#new_staffName").val());
								queryStaffList();
								getStaffDetail(data.msg);
								$.dtdialog.closeDialog("#add_new_staff_form");
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
 * 修改员工信息
 */
function openChangeStaff() {
	var staffId = $("#staffIdHid").val();
	$.dtdialog.showModal({
		title : '修改员工',
		url : 'sm/staffManagerAction!loadStaffDetail.action?staffId=' + staffId,
		minWidth : 600,
		minHeight : 300,
		buttons : {
			'确定' : function() {
				if (!$.dtvalidate("#add_new_staff_form", "caption")) {
					return;
				}
				var staffInfo = $("#add_new_staff_form").serializeArray();
				$.post("sm/staffManagerAction!modifyStaff.action",
						staffInfo, function(data) {
							doResult(data, "修改成功", function(){
								$("#staffIdHid").val("");
								queryStaffList();
								getStaffDetail(staffId);
								$.dtdialog.closeDialog("#add_new_staff_form");
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
 * 离职
 */
function removeStaff(){
	$.dtmessagebox.confirm("确定要离职吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("sm/staffManagerAction!removeStaff.action", {
				"staffId" : $("#staffIdHid").val()
			}, function(data) {
				doResult(data, "操作成功", function(){
					queryStaffList();
					getStaffDetail("");
				});
			});
		}
	
	});
}
/**
 * 停薪留职
 */
function retainStaff(){
	$.dtmessagebox.confirm("确定要停薪留职吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("sm/staffManagerAction!retainStaff.action", {
				"staffId" : $("#staffIdHid").val()
			}, function(data) {
				//doResult(data, "操作成功");
				doResult(data, "操作成功",function(){
					getStaffDetail($("#staffIdHid").val());
				});
			});
		}
	});
}
/**
 * 初始化员工页面
 */
function initStaffDetailPage() {
	if($("#new_staffId").val() != ""){
		$("#new_staffCode").attr("readonly", true);
	}
	$("#new_dept_icon").click(function() {
		showDeptTree(function(deptInfo) {
			$("#new_deptName").val(deptInfo.deptName);
			$("#new_deptId").val(deptInfo.deptId);
		});
	});
}
