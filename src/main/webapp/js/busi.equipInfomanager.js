$(function() {
	$("#queryEquipInfo").click(
			function() {
				var equipInfos = {};
				equipInfos['equipInfo.name'] = $("#name").val();
				equipInfos['equipInfo.model'] = $("#model").val();
				equipInfos['equipInfo.custId'] = $("#custId").val();
				equipInfoResult.dtpaginggrid("option", "postData", equipInfos);
				equipInfoResult.dtpaginggrid("option", "mtype", "POST");
				equipInfoResult.dtpaginggrid("option", "url",
						"equipInfoAction!forwardEquipInfo.action");
				equipInfoResult.dtpaginggrid("option", "page", 1);
				equipInfoResult.dtpaginggrid("reload");
			});

	$("#addEquipInfo").click(function() {
		window.location.href = "equipInfoAction!addEquipInfo.action";
	});
	$("#addEquipInfosave").click(function() {
		var custId = $("#custId").val();
		if ("" == custId) {
			alert("请选择用户!");
			return;
		}
		$("#addEquipInfoForm").submit();
	});
	/**
	 * 返回
	 */
	$("#cancelBtn").click(function() {
		window.location.href = "equipInfoAction.action";
	});
	/**
	 * 重置
	 */
	$("#resetEquipInfo").click(function() {
		$("#name").val("");
		$("#model").val("");
	});
	/**
	 * 插入 设备 确定
	 */
	$("#saveEquipInfo").click(function() {
		var custId = $("#custId").val();
		if ("" == custId) {
			alert("请选择用户!");
			return;
		}
		$("#updateEquipInfoForm").submit();
	});

	$("#delEquipInfo").click(function() {

		deleteDomainInfo();

	});

});

/**
 * 删除值域
 */
function deleteDomainInfo() {
	var selrow = pickWorksInfoResult.dtpaginggrid("option", 'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("equipInfoAction!deleteEquipInfo.action", {
					'equipInfoCodeStr' : selrow
				}, function(data) {
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						equipInfoResult.dtpaginggrid("reload");
						equipInfoResult.dtpaginggrid("reload");
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
 * 查看
 */
function detail(id) {
	window.location.href = "equipInfoAction!viewEquipInfo.action?equipInfoId="
			+ id;
}

function toUpdate(id) {
	window.location.href = "equipInfoAction!editEquipInfo.action?equipInfoId="
			+ id;
}

function deleteOne(id) {
	// window.location.href="pickWorksInfoAction!deleteProduceWorksInfo.action?produceWorksInfoCodeStr="+id;
	$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("equipInfoAction!updateEquipInfoUnable.action", {
				'equipInfoCodeStr' : id
			}, function(data) {
				if (data.flag) {
					$.dtmessagebox.alert("删除成功");
					equipInfoResult.dtpaginggrid("reload");
					equipInfoResult.dtpaginggrid("reload");
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
}

function operFormat(cellvalue, options, rowObject) {
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detail('"
			+ id
			+ "');\">查看</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdate('"
			+ id
			+ "');\">编辑</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('"
			+ id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}

/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function onSelectFuncAuth() {

	if (arguments[2].isParent == false) {
		$("#custId").val(arguments[2].nodeTreeId);
		// window.location.href="equipInfoAction!forwardEquipInfo.action?custId="+arguments[2].nodeTreeId;
		// alert(arguments[2].nodeTreeId+","+arguments[2].parentId);
	}
}
function initEquipAttributeTable(domainId) {
	$("table.condition tr:last")
			.before(
					"<tr>"
							+ "<th>设备厂家：</th>"
							+ "<td><input class=\"ui-input ui-corner-all ui-widget-content input-init\" id=\"factory\" name=\"equipInfo.factory\" ></dt>"
							+ "</td><th>备注：</th><td>"
							+ "<input id=\"remark\" class=\"ui-input ui-corner-all ui-widget-content input-init\" name=\"equipInfo.remark\"  ></input>"
							+ "</td></tr>");
}
/**
 * 获取下一级别的设备列表
 */
function getNextLevelEquipType() {
	var $this = $("#type_first");
	var value = $this.val();
	$("#type").val(value);
	$("#select_container").find("select").eq(1).remove();
	if (value != -20) {
		loadNextLevelData($("#select_container"), value);
	}
}
/**
 * 加载下一级设备类型列表
 * 
 * @param container
 * @param pId
 */
function loadNextLevelData(container, pId) {
	$
			.post(
					"equipInfoAction!loadNextEquipTypes.action",
					{
						pId : pId
					},
					function(json) {
						var datas = jQuery.parseJSON(json.datas);
						if (datas.length > 0) {
							var html = "<select id=\"type_second\" onchange=\"selecSecondLevelEquipType()\">";
							var option = "<option value=\"-1\">-请选择类型-</option>";
							html += option;
							for ( var i = 0; i < datas.length; i++) {
								var $this = datas[i];
								var name = $this.type;
								var id = $this.id;
								option = "<option value=\"" + id + "\">" + name
										+ "</option>";
								html += option;
							}
							html += "</select>";
							container.append(html);
						}
					});
}
function selecSecondLevelEquipType() {
	var $this = $("#type_second");
	var value = $this.val();
	$("#type").val(value);
}
