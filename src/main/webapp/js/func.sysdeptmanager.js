/**
 * 查询部门列表
 */
function querySysDeptList() {
	var param = {};
	param.deptName = $("#query_DeptName").val();
	var url = "sm/sysDeptManagerAction!getSysDeptPaging.action";
//	sysDeptQueryResult.option("postData", param);
//	sysDeptQueryResult.option("mtype", "POST");
//	sysDeptQueryResult.option("url", url);
//	sysDeptQueryResult.option("page", 1);
//	sysDeptQueryResult.reload();
	sysDeptQueryResult.dtpaginggrid('option',{
		postData:param,
		mtype:'POST',
		url: url,
		page: 1
	});
	sysDeptQueryResult.dtpaginggrid('reload');
}
/**
 * 点击部门时，为右边三个tab页赋值
 */
function getSysDeptDetail() {
	var deptId = arguments[0], oldDeptId = $("#deptIdHid").val();
	$("#deptIdHid").val(deptId);
	var sysDeptInfoUrl = "sm/sysDeptManagerAction!getSysDeptDetail.action?deptId="
			+ deptId;
	//sysDeptManager.url(0, sysDeptInfoUrl);
	sysDeptManager.dttabs('url', 0, sysDeptInfoUrl);
	var staffsUrl = "sm/sysDeptManagerAction!loadStaffs.action?deptId="
			+ deptId;
	//sysDeptManager.url(1, staffsUrl);
	sysDeptManager.dttabs('url', 1, staffsUrl);
	var userInfoUrl = "sm/sysDeptManagerAction!loadSysUsers.action?deptId="
			+ deptId;
	//sysDeptManager.url(2, userInfoUrl);
	sysDeptManager.dttabs('url', 2, userInfoUrl);
	if (oldDeptId == "") {
		//sysDeptManager.load(0);
		sysDeptManager.dttabs('load', 0);
	} else if (deptId != oldDeptId) {
		//sysDeptManager.load(sysDeptManager.option("selected"));
		sysDeptManager.dttabs('load',sysDeptManager.dttabs('option','active'));
	}
}
/**
 * 注销用户
 */
function removeSysUsers() {
	//var reomoveUsers = sysDeptUserResultList.option('selarrrow');
	var reomoveUsers = sysDeptUserResultList.dtpaginggrid('option','selarrrow');
	if (reomoveUsers.length == 0) {
		$.dtmessagebox.alert("请选择需要注销的用户!");
	} else {
		$.dtmessagebox.confirm("确定要注销吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				var userIds = "[" + reomoveUsers.join(",") + "]";
				$.post("sm/sysDeptManagerAction!removeSysUsers.action", {
					"userIds" : userIds
				}, function(data) {
					doResult(data, "注销成功");
					//sysDeptManager.load(sysDeptManager.option("selected"));//add by whc 08/31 for refresh grid
					sysDeptManager.dttabs('load',sysDeptManager.dttabs('option','active'));
				});
			}
		});
	}
}
/**
 * 打开新增部门窗口
 */
function openAddSysDept() {
	$.dtdialog.showModal({
		title : '新建部门',
		url : 'sm/sysDeptManagerAction!loadSysDeptDetail.action',
		minWidth : 700,
		minHeight : 300,
		buttons : {
			'确定' : function() {
				if (!$.dtvalidate("#add_new_dept_form", "caption")) {
					return;
				}
				var deptInfo = $("#add_new_dept_form").serializeArray(), dialog = this;
				$.post("sm/sysDeptManagerAction!createSysDept.action",
						deptInfo, function(data) {
							doResult(data, "保存成功，部门编号为：" + data.msg, function(){
								$("#query_DeptName").val($("#new_deptName").val());
								querySysDeptList();
								getSysDeptDetail(data.msg);
								$(dialog).dtdialog('close');
								
								//$('#query_SysDeptTree').html('');
								$('#query_SysDeptTree').dttree('destroy');
								initDeptTree("#query_SysDeptTree", function(){
									getSysDeptDetail(arguments[2].deptId);
								});
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
 * 修改部门信息
 */
function openChangeSysDept() {
	var deptId = $("#deptIdHid").val();
	$.dtdialog.showModal({
		title : '修改部门',
		url : 'sm/sysDeptManagerAction!loadSysDeptDetail.action?deptId=' + deptId,
		minWidth : 600,
		minHeight : 300,
		buttons : {
			'确定' : function() {
				if (!$.dtvalidate("#add_new_dept_form", "caption")) {
					return;
				}
				var deptInfo = $("#add_new_dept_form").serializeArray(), dialog = this;
				$.post("sm/sysDeptManagerAction!modifySysDept.action",
						deptInfo, function(data) {
							doResult(data, "修改成功", function(){
								$("#deptIdHid").val("");
								querySysDeptList();
								getSysDeptDetail(deptId);
								$(dialog).dtdialog('close');
								
								//$('#query_SysDeptTree').html('');
								$('#query_SysDeptTree').dttree('destroy');
								initDeptTree("#query_SysDeptTree", function(){
									getSysDeptDetail(arguments[2].deptId);
								});
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
 * 注销
 */
function removeDept(){
	if ($("#deptIdHid").val() == 0) {
		$.dtmessagebox.alert("该部门不允许注销");
		return ;
	}
	$.dtmessagebox.confirm("确定要注销吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("sm/sysDeptManagerAction!removeSysDept.action", {
				"deptId" : $("#deptIdHid").val()
			}, function(data) {
				doResult(data, "注销成功", function(){
					querySysDeptList();
					$("#deptIdHid").val("-1");
					getSysDeptDetail();
					
					//$('#query_SysDeptTree').html('');
					$('#query_SysDeptTree').dttree('destroy');
					initDeptTree("#query_SysDeptTree", function(){
						getSysDeptDetail(arguments[2].deptId);
					});
				});
			});
		}
	});
}
/**
 * 初始化部门页面
 */
function initAddNewDeptPage() {
	
	if($("#new_deptId").val() == ""){//新增部门
		$("#parent_dept_icon").click(function() {
			showDeptTree(function(deptInfo) {
				$("#new_super_deptName").val(deptInfo.deptName);
				$("#new_superDeptId").val(deptInfo.deptId);
			});
		});
	}else{
		$("#parent_dept_icon").css("display","none");
		$("#new_deptCode").attr("disabled", true);
	}
}
$(function() {
	initDeptTree("#query_SysDeptTree", function(){
		getSysDeptDetail(arguments[2].deptId);
	});
});