var custId = "";
var selectNodeId = "";
var jsonQuery = {range:"0"};
$(function() {
	$("#queryBtn").click(function() {
		if(!jsonQuery.greenHouseId){
			$.dtmessagebox.alert("请先选择温室或者设备类型");
			return;
		}
		if ($("#equiptype").val() == 1) {
			$("#title_tab").html("网关查询结果");
		} else if ($("#equiptype").val() == 2) {
			$("#title_tab").html("气象站查询结果");
		} else {
			$("#title_tab").html("节点查询结果");
		}
		jsonQuery.range = $("#queryType").val();
		jsonQuery.equipName = $("#equipName").val();
		loadEquipInfo(jsonQuery);
	});
	$("#resetBtn").click(function() {
		$("#queryType").val(0);
		$("#equipName").val("");
		jsonQuery.range = $("#queryType").val();
		jsonQuery.equipName = $("#equipName").val();
	});
	$("#saveEquipBtn").click(function() {
		var selitems = $("input[type='checkbox']:checked");
		var selItemIds = [];
		selitems.each(function() {
			selItemIds.push($(this).val());
		});
		var houseId = $("#houseId").val();
		var baseId = $("#baseId").val();
		var equipType = $("#equiptype").val();
		$.post("busi/greenHouseAndPoint!saveGreenHousePoinInfo.action", {
			'equipIds' : selItemIds.toString(),
			'greenHouseId' : houseId,
			'equipType' : equipType,
			'baseId' : baseId,
			'custId' : custId
		}, function(data) {
			if (data.flag) {
				$.dtmessagebox.alert("添加成功");
			} else {
				if (data.msg) {
					$.dtmessagebox.alert(data.msg);
				} else {
					$.dtmessagebox.alert("添加失败");
				}
			}
		});
	});
	$("#saveEquipControlBtn").click(function() {
		var selitems = $("input[type='checkbox']:checked");
		var selItemIds = [];
		selitems.each(function() {
			selItemIds.push($(this).val());
		});
		$.post("busi/greenHouseAndPoint!saveNodeControlInfo.action", {
			'equipIds' : selItemIds.toString(),
			'nodeId' : selectNodeId,
			'custId' : custId
		}, function(data) {
			if (data.flag) {
				$.dtmessagebox.alert("添加成功");
			} else {
				$.dtmessagebox.alert("添加失败");
			}
		});
	});
});
function setQueryParam(){
	jsonQuery.range = $("#queryType").val();
}

/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function onSelectFuncAuth() {
	if (arguments[2].isParent == false) {
		var nodeTreeId = arguments[2].nodeTreeId;
		$("#houseId").val(arguments[2].nodeTreeId);
		$("#baseId").val(arguments[2].parentId);
		if (nodeTreeId == -10) {
			$("#equiptype").val(1);
			$("#title_tab").html("网关查询结果");
		} else if (nodeTreeId == -20) {
			$("#equiptype").val(2);
			$("#title_tab").html("气象站查询结果");
		} else {
			$("#equiptype").val(3);
			$("#title_tab").html("节点查询结果");
		}
		jsonQuery.greenHouseId = arguments[2].nodeTreeId;
		jsonQuery.baseId = arguments[2].parentId;
		loadEquipInfo(jsonQuery);
	}
}

function loadEquipInfo(queryJson) {
	$
			.ajax({
				url : 'busi/greenHouseAndPoint!getEquipByHouseId.action',
				type : 'post',
				dataType : 'json',
				data : jsonQuery,
				beforeSend : function() {
					$('#contentEquip').html("加载中...");
				},
				success : function(data) {
					$('#contentEquip').html("");
					if(data){
						if(data.length == 0){
							$('#contentEquip').append("暂无符合条件的结果");
							return;
						}
						$.each(
								data,
								function(i, item) {
									var checkItem = "<span style=\"display:inline-block;width:350px;\"><input type=\"checkbox\" style=\"margin-top:5px;margin-bottom:5px;margin-left:10px;\" value=\""
											+ item.id + "\"";
									if (item.flag == true) {
										checkItem += " checked=true";
									}
									checkItem += "> 名称：" + item.name
											+ " | 设备id： " + item.deviceId
											+ "</input><span>";
									if ((i + 1) % 2 == 0) {
										checkItem += "<br/>";
									}
									$('#contentEquip').append(checkItem);
									custId = item.custId;
								});
					}
				}
			});
}

function controlTreeClick() {
	var type = arguments[2].type;
	if (type) {
		selectNodeId = arguments[2].nodeTreeId;
		if (type == 1) {
			handleSelectControlEquip(arguments[2]);
		} else if (type == 2) {
			handleControlEquipPort(arguments[2]);
		}
	}
}
/**
 * 处理点击节点事件
 * 
 * @param treeNode
 */
function handleSelectControlEquip(treeNode) {
	initNodeEquipGrid(equipNodeInfoResult, treeNode);
	slideDiv($("#nodeEquipManagerDiv"), $("#controlEquipManagerDiv"));
}
/**
 * 处理点击控制器事件
 * 
 * @param treeNode
 */
function handleControlEquipPort(treeNode) {
	initControlEquipGrid(equipControlInfoResult, treeNode);
	slideDiv($("#controlEquipManagerDiv"), $("#nodeEquipManagerDiv"));
}
/**
 * 
 * @param showDiv（dom对象，可以是$object）
 * @param hideDiv（dom对象，可以是$object）
 */
function slideDiv(showDiv, hideDiv) {
	$(hideDiv).hide();
	$(showDiv).show();
}
/**
 * 初始化控制设备端口列表
 * 
 * @param nodeId
 */
function initControlEquipGrid(gridObj, treeNode) {
	var param = {
		nodeId : selectNodeId
	};
	gridObj.dtpaginggrid("option", "postData", param);
	gridObj.dtpaginggrid("option", "mtype", "POST");
	gridObj.dtpaginggrid("option", "url",
			"greenHouseAndPoint!getEquipControlInfoQuery.action");
	gridObj.dtpaginggrid("option", "page", 1);
	gridObj.dtpaginggrid("reload");
}
/**
 * 初始化节点设备端口列表
 * 
 * @param nodeId
 */
function initNodeEquipGrid(gridObj, treeNode) {
	var param = {
		nodeId : selectNodeId
	};
	gridObj.dtpaginggrid("option", "postData", param);
	gridObj.dtpaginggrid("option", "mtype", "POST");
	gridObj.dtpaginggrid("option", "url",
			"greenHouseAndPoint!getNodeControlInfoQuery.action");
	gridObj.dtpaginggrid("option", "page", 1);
	gridObj.dtpaginggrid("reload");
}
// 控制设备端口列表操作项
function operFormat(cellvalue, options, rowObject) {
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('"
			+ id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}
// 节点端口列表操作项
function operNodeFormat(cellvalue, options, rowObject) {
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOneNodePort('"
			+ id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}
// 删除一个控制设备端口信息
function deleteOne(id) {
	if (id) {
		$.ajax({
			url : 'greenHouseAndPoint!deleteControlPortInfo.action',
			data : {
				id : id
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data && data.flag) {
					$.dtmessagebox.alert("删除成功");
					initControlEquipGrid(equipControlInfoResult, arguments[2]);
				} else {
					$.dtmessagebox.alert("删除失败");
				}
			}
		});
	}
}
// 删除一个控制设备端口信息
function deleteOneNodePort(id) {
	if (id) {
		$.ajax({
			url : 'greenHouseAndPoint!deleteNodePortInfo.action',
			data : {
				id : id
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data && data.flag) {
					$.dtmessagebox.alert("删除成功");
					initNodeEquipGrid(equipNodeInfoResult, arguments[2]);
				} else {
					$.dtmessagebox.alert("删除失败");
				}
			}
		});
	}
}

function initControlEquipList() {
	$.getJSON("busi/greenHouseAndPoint!getFreeControlEquip.action", function(
			data) {
		var checkItem = "<option value=\"-1\">-请选择控制器-</option>";
		$.each(data, function(i, item) {
			checkItem += "<option  value=\"" + item.id + "\"";
			checkItem += ">" + item.name + "</option>";
		});
		$("#nodecontrolid").html(checkItem);
	});
}
