var using = "启用";
var m = {};
$(function() {

	$("#queryBaseInfoBtn").click(function() {
		var params = jQuery("#queryFormB").serialize();
		baseInfoResult.dtpaginggrid("setGridParam", {
			url : 'busi/baseInfoManager!getBaseInfoHome.action?' + params
		});
		baseInfoResult.dtpaginggrid("option", "page", 1);
		baseInfoResult.dtpaginggrid("reload");
	});
	$("#addBaseInfoBtn")
			.click(
					function() {
						window
								.open(
										'busi/baseInfoManager!addBaseInfo.action',
										'newwindow',
										'height=650px,width=900px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');
					});

	$("#saveBaseInfoBtn").click(function() {
		$("#addBaseInfoForm").submit();
		alert("添加成功");
		window.opener.baseInfoResult.dtpaginggrid("reload");
		window.opener.baseInfoResult.dtpaginggrid("reload");
		window.close();

	});

	$("#updateBaseInfoBtn").click(function() {
		$("#editBaseInfoForm").submit();
		alert("修改成功");
		window.opener.baseInfoResult.dtpaginggrid("reload");
		window.opener.baseInfoResult.dtpaginggrid("reload");
		window.close();

	});

	$("#delBaseInfoBtn").click(function() {
		deleteDomain();
	});

	$("#cancelBtn").click(function() {
		window.close();
	});

	$("#resetBaseInfoBtn").click(function() {
		$("#nameB").val("");
		funcInfoTree.dttree("refresh");
	});
});

/**
 * 删除值域
 */
function deleteDomain() {
	var selrow = baseInfoResult.dtpaginggrid("option", 'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要停用吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("busi/baseInfoManager!deleteBaseInfo.action", {
					'baseInfoCodeStr' : selrow
				}, function(data) {
					if (data.flag) {
						$.dtmessagebox.alert("停用成功");
						baseInfoResult.dtpaginggrid("reload");
						baseInfoResult.dtpaginggrid("reload");
					} else {
						if (data.msg) {
							$.dtmessagebox.alert(data.msg);
						} else {
							$.dtmessagebox.alert("停用失败");
						}
					}
				});
			}
		});
	} else {
		$.dtmessagebox.alert("请选择要停用的数据");
	}
}

/**
 * 查看
 */
function detailBase(id) {
	window
			.open(
					'busi/baseInfoManager!viewBaseInfo.action?baseInfoId=' + id,
					'newwindow',
					'height=600px,width=900px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');

}

function toUpdateBase(id) {
	window
			.open(
					'busi/baseInfoManager!editBaseInfo.action?baseInfoId=' + id,
					'newwindow',
					'height=600px,width=900px,top=100px,left=150px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');

}

function deleteOneBase(id) {
	$.dtmessagebox.confirm("确定要停用/启用吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("busi/baseInfoManager!deleteBaseInfo.action", {
				'baseInfoCodeStr' : id
			}, function(data) {
				if (data.flag) {
					$.dtmessagebox.alert("停用/启用成功");
					baseInfoResult.dtpaginggrid("reload");
					baseInfoResult.dtpaginggrid("reload");
				} else {
					if (data.msg) {
						$.dtmessagebox.alert(data.msg);
					} else {
						$.dtmessagebox.alert("停用/启用失败");
					}
				}
			});
		}
	});
}

function operFormatBase(cellvalue, options, rowObject) {
	var id = rowObject.id;
	var state = rowObject.state;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detailBase('"
			+ id
			+ "');\">基地查看</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdateBase('"
			+ id + "');\">编辑</a>&nbsp;&nbsp;";
	if (state == "0") {
		mid = mid
				+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOneBase('"
				+ id + "');\">停用</a>&nbsp;&nbsp;";
	} else {
		mid = mid
				+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOneBase('"
				+ id + "');\">启用</a>&nbsp;&nbsp;";
	}

	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}
