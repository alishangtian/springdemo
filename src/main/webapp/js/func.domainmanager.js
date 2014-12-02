$(function() {
	$("#queryDomainBtn").click(function() {
		var domain = {};
		domain['domainObj.domainType'] = $("#queryDomainType").val();
		domain['domainObj.domainCode'] = $("#queryDomainCode").val();
		domain['domainObj.domainName'] = $("#queryDomainName").val();
		domainResult.dtpaginggrid("option","postData", domain);
		domainResult.dtpaginggrid("option","mtype", "POST");
		domainResult.dtpaginggrid("option","url", "sm/domainManager!getDomain.action");
		domainResult.dtpaginggrid("option","page", 1);
		domainResult.dtpaginggrid("reload");
	});

});

/**
 * 显示值域添加对话框
 */
function addDomainDialogShow() {
	addDomainDialog.dtdialog("setData",{
		domainName : '',
		domainCode : '',
		maxValue : '',
		minValue : '',
		defValue : '',
		remark : ''
	});
	addDomainDialog.dtdialog("showModal");
}

/**
 * 添加值域
 */
function addDomain() {
	if ($.dtvalidate("#addDomainForm")) {
		var data = addDomainDialog.dtdialog("getData");

		$.post("sm/domainManager!addDomain.action", {
			'domainObj.domainName' : data.domainName,
			'domainObj.domainCode' : data.domainCode,
			'domainObj.domainType' : data.domainType,
			'domainObj.maxValue' : data.maxValue,
			'domainObj.minValue' : data.minValue,
			'domainObj.defValue' : data.defValue,
			'domainObj.remark' : data.remark
		}, function(data) {
			if (data.flag) {
				$.dtmessagebox.alert("添加成功");
				addDomainDialog.dtdialog("close");
				domainResult.dtpaginggrid("option","page", 1);
				domainResult.dtpaginggrid("reload");
			} else {
				if (data.msg) {
					$.dtmessagebox.alert(data.msg);
				} else {
					$.dtmessagebox.alert("添加失败");
				}
			}
		});
	}
}

/**
 * 取消值域添加对话框
 */
function cancelAddDomainDialog() {
	addDomainDialog.dtdialog("close");
}

/**
 * 显示值域编辑对话框
 */
function editDomainDialogShow() {
	var domainCode = domainResult.dtpaginggrid("option",'selarrrow');
	if (domainCode.length != 1) {
		$.dtmessagebox.alert("必须选择且只能选择单条记录");
	} else {
		$.post("sm/domainManager!getDomainDetail.action", {
			'domainObj.domainCode' : domainCode[0]
		}, function(domain) {
			editDomainDialog.dtdialog("setData",{
				domainIdEdit : domain.domainId,
				domainNameEdit : domain.domainName,
				domainCodeEdit : domain.domainCode,
				domainTypeEdit : domain.domainType,
				maxValueEdit : domain.maxValue,
				minValueEdit : domain.minValue,
				defValueEdit : domain.defValue,
				remarkEdit : domain.remark
			});
			editDomainDialog.dtdialog("showModal");
		});
	}
}

/**
 * 编辑值域
 */
function editDomain() {
	if ($.dtvalidate("#editDomainForm")) {
		var data = editDomainDialog.dtdialog("getData");

		$.post("sm/domainManager!editDomain.action", {
			'domainObj.domainId' : data.domainIdEdit,
			'domainObj.domainName' : data.domainNameEdit,
			'domainObj.domainCode' : data.domainCodeEdit,
			'domainObj.domainType' : data.domainTypeEdit,
			'domainObj.maxValue' : data.maxValueEdit,
			'domainObj.minValue' : data.minValueEdit,
			'domainObj.defValue' : data.defValueEdit,
			'domainObj.remark' : data.remarkEdit
		}, function(flag) {
			if (flag) {
				$.dtmessagebox.alert("修改成功");
				domainResult.dtpaginggrid("reload");
				editDomainDialog.dtdialog("close");
			} else {
				$.dtmessagebox.alert("修改失败");
			}
		});
	}
}

/**
 * 取消值域编辑对话框
 */
function cancelEditDomainDialog() {
	editDomainDialog.dtdialog("close");
}

/**
 * 删除值域
 */
function deleteDomain() {
	var selrow = domainResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("sm/domainManager!deleteDomain.action",{'domainCodeStr' : selrow}, function(data) {														
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						domainResult.dtpaginggrid("reload");
						domainValueResult.dtpaginggrid("reload");
					} else {
						if (data.msg) {
							$.dtmessagebox.alert(data.msg);
						} else {
							$.dtmessagebox.alert("删除失败");
						}
					}
				});
			}
		});
	} else {
		$.dtmessagebox.alert("请选择要删除的数据");
	}
}

/**
 * 编辑值域取值
 */
function getDomainValue() {
	var domainCode = domainResult.dtpaginggrid("option",'selarrrow');
	if (domainCode.length != 1) {
		$.dtmessagebox.alert("必须选择且只能选择单条记录");
	} else {
		var domain = {};
		domain['domainObj.domainCode'] = domainCode[0];
		domain['domainObj.domainName'] = domainResult.getRowData(domainCode[0])["entity.domainName"];
		domainValueResult.dtpaginggrid("option","postData", domain);
		domainValueResult.dtpaginggrid("option","url",
				"sm/domainManager!getDomainValue.action");
		domainValueResult.dtpaginggrid("reload");
		$("#domainCodeUsed").val(domainCode[0]);
		$("#domainValueDiv").show();
	}
}
/**
 * 隐藏值域取值信息DIV
 */
function hideDomainValueDiv(){
	$("#domainValueDiv").hide();
}

/**
 * 启用/停用值域取值
 */
function editDomainValueSts(enabledFlag) {
	var selrow = domainValueResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		var valueCodeStr = selrow;

		if (valueCodeStr != null) {
			$.post("sm/domainManager!editDomainValueSts.action", {
				'valueCodeStr' : valueCodeStr,
				'domainCode' : $("#domainCodeUsed").val(),
				'enabledFlag' : enabledFlag
			}, function(flag) {
				var msg = "启用";
				if (enabledFlag == "F") {
					msg = "停用";
				}
				if (flag) {
					$.dtmessagebox.alert(msg + "成功");
					domainValueResult.dtpaginggrid("reload");
				} else {
					$.dtmessagebox.alert(msg + "失败");
				}
			});
		}
	} else {
		$.dtmessagebox.alert("选择值域取值");
	}
}

/**
 * 显示值域取值添加对话框
 */
function addDomainValueDialogShow() {
	addDomainValueDialog.dtdialog("setData",{
		valueCode : '',
		valueName : '',
		displayOrder : ''
	});
	addDomainValueDialog.dtdialog("showModal");
}

/**
 * 添加值域取值
 */
function addDomainValue() {
	if ($.dtvalidate("#addDomainValueForm")) {
		var data = addDomainValueDialog.dtdialog("getData");

		$.post("sm/domainManager!addDomainValue.action", {
			'domainValueObj.valueName' : data.valueName,
			'domainValueObj.valueCode' : data.valueCode,
			'domainValueObj.enabledFlag' : data.enabledFlag,
			'domainValueObj.displayOrder' : data.displayOrder,
			'domainCode' : $("#domainCodeUsed").val()
		}, function(data) {
			if (data.flag) {
				$.dtmessagebox.alert("添加成功");
				addDomainValueDialog.dtdialog("close");
				domainValueResult.dtpaginggrid("reload");
			} else {
				if (data.msg) {
					$.dtmessagebox.alert(data.msg);
				} else {
					$.dtmessagebox.alert("添加失败");
				}
			}
		});
	}
}

/**
 * 取消值域取值添加对话框
 */
function cancelAddDomainValueDialog() {
	addDomainValueDialog.dialog("close");
}

/**
 * 显示值域取值编辑对话框
 */
function editDomainValueDialogShow() {
	var valueCode = domainValueResult.dtpaginggrid("option",'selarrrow');
	if (valueCode.length != 1) {
		$.dtmessagebox.alert("必须选择且只能选择单条记录");
	} else {
		$.post("sm/domainManager!getDomainValueDetail.action", {
			'domainCode' : $("#domainCodeUsed").val(),
			'valueCodeStr' : valueCode[0]
		}, function(domainValue) {
			editDomainValueDialog.dtdialog("setData",{
				valueCodeEdit : domainValue.valueCode,
				valueNameEdit : domainValue.valueName,
				enabledFlagEdit : domainValue.enabledFlag,
				displayOrderEdit : domainValue.displayOrder,
				domainIdUsedEdit : domainValue.domainId
			});
			editDomainValueDialog.dtdialog("showModal");
		});
	}
}

/**
 * 编辑值域取值
 */
function editDomainValue() {
	if ($.dtvalidate("#editDomainValueForm")) {
		var data = editDomainValueDialog.dtdialog("getData");
		$.post("sm/domainManager!editDomainValue.action", {
			'domainValueObj.valueCode' : data.valueCodeEdit,
			'domainValueObj.valueName' : data.valueNameEdit,
			'domainValueObj.enabledFlag' : data.enabledFlagEdit,
			'domainValueObj.displayOrder' : data.displayOrderEdit,
			'domainValueObj.domainId' : data.domainIdUsedEdit
		}, function(data) {
			if (data.flag) {
				$.dtmessagebox.alert("修改成功");
				domainValueResult.dtpaginggrid("reload");
				editDomainValueDialog.dtdialog("close");
			} else {
				if (data.msg) {
					$.dtmessagebox.alert(data.msg);
				} else {
					$.dtmessagebox.alert("修改失败");
				}
			}
		});
	}
}

/**
 * 取消值域取值编辑对话框
 */
function cancelEditDomainValueDialog() {
	editDomainValueDialog.dtdialog("close");
}

/**
 * 删除值域取值
 */
function deleteDomainValue() {
	var selrow = domainValueResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				var valueCodeStr = selrow;
				if (valueCodeStr != null && valueCodeStr.length > 0) {
					$.post("sm/domainManager!deleteDomainValue.action", {
						'domainCode' : $("#domainCodeUsed").val(),
						'valueCodeStr' : valueCodeStr
					}, function(flag) {
						if (flag) {
							$.dtmessagebox.alert("删除成功");
							domainValueResult.dtpaginggrid("reload");
						} else {
							$.dtmessagebox.alert("删除失败");
						}
					});
				}
			}
		});
	} else {
		$.dtmessagebox.alert("请选择要删除的数据");
	}
}