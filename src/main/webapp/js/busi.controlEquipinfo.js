$(function() {
	
});

/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function onSelectFuncAuth() {
	if (arguments[2].isParent == false) {
		var nodeTreeId = arguments[2].nodeTreeId;
		$("#houseId").val(arguments[2].nodeTreeId);
		$("#baseId").val(arguments[2].parentId);
		if (nodeTreeId == -10) {
			$("#title_tab").html("可用网关");
			$("#equiptype").val(0);
		} else if (nodeTreeId == -20) {
			$("#title_tab").html("可用气象站");
			$("#equiptype").val(2);
		} else {
			$("#title_tab").html("可用节点");
			$("#equiptype").val(1);
		}
		$('#contentPanel').show();
		$('#contentEquip').empty();
		$.getJSON(
				"busi/greenHouseAndPoint!getEquipByHouseId.action?greenHouseId="
						+ arguments[2].nodeTreeId + "&baseId="
						+ arguments[2].parentId, function(data) {
					$.each(data, function(i, item) {
						var checkItem = "<input type=\"checkbox\"  value=\""
								+ item.id + "\"";
						if (item.flag == true) {
							checkItem += " checked=true";
						}
						checkItem += ">" + item.name + "</input>";
						$('#contentEquip').append(checkItem);
					});
				});
	}
}