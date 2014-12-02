$(function() {
	$("#queryBusiParamBtn").click(function() {
		var busiParam = {};
		busiParam['busiParamObj.enabledFlag'] = $("#queryEnabledFlag").val();
		busiParam['busiParamObj.paramCode'] = $("#queryParamCode").val();
		busiParam['busiParamObj.paramName'] = $("#queryParamName").val();
		busiParamResult.dtpaginggrid("option","postData", busiParam);
		busiParamResult.dtpaginggrid("option","mtype", "POST");
		busiParamResult.dtpaginggrid("option","url","sm/busiParamManager!getBusiParam.action");
		busiParamResult.dtpaginggrid("option","page", 1);
		busiParamResult.dtpaginggrid("reload");
	});
});

/**
 * 启用/停用系统参数
 */
function editBusiParamSts(enabledFlag) {
	var selrow = busiParamResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		var paramCodeStr = selrow;

		if (paramCodeStr != null) {
			$.post("sm/busiParamManager!editBusiParamSts.action", {
				'paramCodeStr' : paramCodeStr,
				'enabledFlag' : enabledFlag
			}, function(flag) {
				var msg = "启用";
				if (enabledFlag == "F") {
					msg = "停用";
				}
				if (flag) {
					$.dtmessagebox.alert(msg + "成功");
					busiParamResult.dtpaginggrid("reload");
				} else {
					$.dtmessagebox.alert(msg + "失败");
				}
			});
		}
	} else {
		$.dtmessagebox.alert("选择业务参数");
	}
}

/**
 * 显示编辑业务参数对话框
 */
function editBusiParamDialogShow() {
	var paramCode = busiParamResult.dtpaginggrid("option",'selarrrow');
	if (paramCode.length != 1) {
		$.dtmessagebox.alert("必须选择且只能选择单条记录");
	} else {
		$.post("sm/busiParamManager!getBusiParamDetail.action", {
			'paramCodeStr' : paramCode[0]
		}, function(busiParam) {
			editBusiParamDialog.dtdialog("setData",{
				paramCodeEdit : busiParam.paramCode,
				paramNameEdit : busiParam.paramName,
				paramValueEdit : busiParam.paramValue,
				busiParamIdEdit : busiParam.busiParamId
			});
			editBusiParamDialog.dtdialog("showModal");
		});
	}
}

/**
 * 编辑业务参数
 */
function editBusiParam() {
	if ($.dtvalidate("#editBusiParamForm")) {
		var data = editBusiParamDialog.dtdialog("getData");
		$.post("sm/busiParamManager!editBusiParam.action", {
			'busiParamObj.paramCode' : data.paramCodeEdit,
			'busiParamObj.paramValue' : data.paramValueEdit,
			'busiParamObj.busiParamId' : data.busiParamIdEdit
		}, function(flag) {
			if (flag) {
				$.dtmessagebox.alert("修改成功");
				busiParamResult.dtpaginggrid("reload");
				editBusiParamDialog.dtdialog("close");
			} else {
				$.dtmessagebox.alert("修改失败");
			}
		});
	}
}

/**
 * 关闭业务参数对话框
 */
function cancelEditBusiParamDialog() {
	editBusiParamDialog.dtdialog("close");
}