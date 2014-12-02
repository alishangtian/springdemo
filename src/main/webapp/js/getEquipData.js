var flagGreenHouse="";
var o="";
$(function() {
	$("#queryBtn").click(function() {
		var keyValue = {};
		keyValue['prdcSeason.houseId'] = $("#queryHouseId").val();
		keyValue['prdcSeason.beginTime'] = $("#startTime").val();
		keyValue['prdcSeason.beginTime0'] = $("#startTime0").val();
		keyValue['prdcSeason.endTime'] = $("#endTime").val();
		keyValue['prdcSeason.endTime0'] = $("#endTime0").val();
		keyValue['prdcSeason.name'] = $("#queryPrdcSeasonName").val();
		
		prdcSeasonResult.dtpaginggrid("option","postData", keyValue);
		prdcSeasonResult.dtpaginggrid("option","mtype", "POST");
		prdcSeasonResult.dtpaginggrid("option","url", "greedHoseClickAction!getPrdcSeasonsByQueryBean.action");
		prdcSeasonResult.dtpaginggrid("option","page", 1);
		prdcSeasonResult.dtpaginggrid("reload");
	});
	//alert($("#a").val());
	$("#selectEdit option[value='"+$("#a").val()+"']").attr("selected",true);
	$("#addBtn").click(function() {
		
		if($("#queryHouseId").val()==""||flagGreenHouse==null||flagGreenHouse==""){
			alert("请选择温室");
		}else{
			$.dtdialog.showModal({
			title : '新增种植季',
			url : 'busi/greedHoseClickAction!createPrdcSeasonURL.action?prdcSeason.houseId='+$("#queryHouseId").val(),
			width : 1000,
			height : 800,
			buttons : {},
			resizable : false,
			//autoDestroy : true
			//showCloseIcon:false
			});
		}

		//window.location.href="greedHoseClickAction!createPrdcSeasonURL.action?prdcSeason.houseId="+$("#queryHouseId").val();
	});
	
	$("#createPrdcSeasonBtn").click(function() {
		$("#createPrdcSeasonForm").submit();
	});
	$("#cancelBtn4Add").click(function() {
		//$.dtdialog.closeDialog().closeDialog("#greenHouse",o);
		window.location.href="greedHoseClickAction.action";
	});
	$("#cancelBtn4Edit").click(function() {
		window.location.href="greedHoseClickAction.action";
	});
	$("#cancelBtn4View").click(function() {
		window.location.href="greedHoseClickAction.action";
	});
	
	$("#editPrdcSeasonBtn").click(function() {
		$("#editPrdcSeasonForm").submit();
	});
	
	//重置表单
	$("#resetBtn").click(function() {
		$("#startTime").val("");
		$("#endTime").val("");
		$("#queryPrdcSeasonName").val("");
	});
	
	/*$("#delPrdcSeasonBtn").click(function() {
		deleteDomain();
	});*/
	
	
	
});

/**
 * 批量删除
 */
/*function deleteDomain() {
	var selrow = prdcSeasonResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("greedHoseClickAction!deleteProduceWorksInfo.action",{'produceWorksInfoCodeStr' : selrow}, function(data) {														
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						prdcSeasonResult.dtpaginggrid("reload");
						prdcSeasonResult.dtpaginggrid("reload");
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
}*/

/**
 * 跳转查看页面
 */
function detail(id){
	o = $.dtdialog.showModal({
		title : '新增种植季',
		url : 'busi/greedHoseClickAction!getPrdcSeasonById.action?prdcSeason.id='+id,
		width : 1000,
		height : 800,
		buttons : {},
		resizable : false
		});
	

	//window.location.href="greedHoseClickAction!getPrdcSeasonById.action?prdcSeason.id="+id;
}

/**
 * 跳转修改页面
 * @param id
 */
function toUpdate(id){
	$.dtdialog.showModal({
		title : '新增种植季',
		url : 'busi/greedHoseClickAction!updatePrdcSeasonURL.action?prdcSeason.id='+id,
		width : 1000,
		height : 800,
		buttons : {},
		resizable : false
		});
	//window.location.href="greedHoseClickAction!updatePrdcSeasonURL.action?prdcSeason.id="+id;
}

//删除一个 一般是状态作废
function deleteOne(id){
	$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("greedHoseClickAction!updatePrdcSeason2Unable.action",{'prdcSeason.id' : id}, function(data) {														
				if (data.flag) {
					$.dtmessagebox.alert("操作成功");
					prdcSeasonResult.dtpaginggrid("reload");
				} else {
					if (data.msg) {
						$.dtmessagebox.alert(data.msg);
					} else {
						$.dtmessagebox.alert("操作失败");
					}
				}
			});
		}
	});
}

//操作单元格返回的链接
function operFormat(cellvalue, options, rowObject){
	var id = rowObject.id;
	var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detail('" + id + "');\">查看</a>&nbsp;&nbsp;"
		    + "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdate('" + id + "');\">编辑</a>&nbsp;&nbsp;"
			+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('" + id + "');\">删除</a>&nbsp;&nbsp;";
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}

function onSelectFuncAuth() {
//	alert(arguments[2].isParent+"---"+arguments[1].isParent);
//	alert(arguments[2].nodeTreeId+","+arguments[2].parentId+","+arguments[2].nodeTreeName);
	if (arguments[2].isParent == false) {
		if(arguments[2].nodeTreeName == "传感器"){
			$.getJSON(
					"busi/greedHoseClickAction!selectEquipData.action?equipId="
							+ arguments[2].nodeTreeId, function(data) {
								clearInfo();
							$.each(data, function(i, item) {
								
								if(item.name == "土壤温度"){
									$("#tr_Temperature").val(item.value );
								}
								if(item.name == "土壤湿度"){
									$("#tr_Humidity").val(item.value );
								}
								if(item.name == "相对水率"){
									$("#SoilConductivity").val(item.value );
								}
								if(item.name == "空气温度"){
									$("#kq_Temperature").val(item.value );
								}
								if(item.name == "空气湿度"){
									$("#kq_Humidity").val(item.value );
								}
								
							});
					});
		}
		if(arguments[2].nodeTreeName == "控制器"){
			alert("查询控制器信息");
		}
		$("#queryHouseId").val(arguments[2].nodeTreeId);
		flagGreenHouse=arguments[2].parentId;
		
	}
	
	//alert($("#queryHouseId").val());
}
function clearInfo(){
	$("#tr_Temperature").val("");
	$("#tr_Humidity").val("");
	$("#SoilConductivity").val("");
	$("#kq_Temperature").val("");
	$("#kq_Humidity").val("");
}

//设备图表
function DataInfo(o){
	var x = $(o);
	alert(x.prev().val());
	$.post("greedHoseClickAction!getEquipData.action",{},function(data){
		
	})
}