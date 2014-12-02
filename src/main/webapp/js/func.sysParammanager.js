$(function() {
	$("#querySysParamBtn").click(function() {
		var sysParam = {};
		sysParam['sysParamObj.enabledFlag'] = $("#queryEnabledFlag").val();
		sysParam['sysParamObj.paramCode'] = $("#queryParamCode").val();
		sysParam['sysParamObj.paramName'] = $("#queryParamName").val();
		sysParamResult.dtpaginggrid("option","postData", sysParam);
		sysParamResult.dtpaginggrid("option","mtype", "POST");
		sysParamResult.dtpaginggrid("option","url", "sm/sysParamManager!getSysParam.action");
		sysParamResult.dtpaginggrid("option","page", 1);
		sysParamResult.dtpaginggrid("reload");
	});
});

/**
 * 启用/停用系统参数
 */
function editSysParamSts(enabledFlag) {
	var selrow = sysParamResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		var paramCodeStr = selrow;

		if (paramCodeStr != null) {
			$.post("sm/sysParamManager!editSysParamSts.action", {
				'paramCodeStr' : paramCodeStr,
				'enabledFlag' : enabledFlag
			}, function(flag) {
				var msg = "启用";
				if (enabledFlag == "F") {
					msg = "停用";
				}
				if (flag) {
					$.dtmessagebox.alert(msg + "成功");
					sysParamResult.dtpaginggrid("reload");
				} else {
					$.dtmessagebox.alert(msg + "失败");
				}
			});
		}
	} else {
		$.dtmessagebox.alert("选择系统参数");
	}
}

/**
 * 显示编辑系统参数对话框
 */
function editSysParamDialogShow() {
	var paramCode = sysParamResult.dtpaginggrid("option",'selarrrow');
	if (paramCode.length != 1) {
		$.dtmessagebox.alert("必须选择且只能选择单条记录");
	} else {
		$.post("sm/sysParamManager!getSysParamDetail.action", {
			'paramCodeStr' : paramCode[0]
		}, function(sysParam) {
			editSysParamDialog.dtdialog("setData",{
				paramCodeEdit : sysParam.paramCode,
				paramNameEdit : sysParam.paramName,
				paramValueEdit : sysParam.paramValue,
				sysParamIdEdit : sysParam.sysParamId
			});
			editSysParamDialog.dtdialog("showModal");
		});
	}
}

/**
 * 编辑系统参数
 */
function editSysParam() {
	if ($.dtvalidate("#editSysParamForm")) {
		var data = editSysParamDialog.dtdialog("getData");
		$.post("sm/sysParamManager!editSysParam.action", {
			'sysParamObj.paramCode' : data.paramCodeEdit,
			'sysParamObj.paramValue' : data.paramValueEdit,
			'sysParamObj.sysParamId' : data.sysParamIdEdit
		}, function(flag) {
			if (flag) {
				$.dtmessagebox.alert("修改成功");
				sysParamResult.dtpaginggrid("reload");
				editSysParamDialog.dtdialog("close");
			} else {
				$.dtmessagebox.alert("修改失败");
			}
		});
	}
}

/**
 * 关闭系统参数对话框
 */
function cancelEditSysParamDialog() {
	editSysParamDialog.dtdialog("close");
}