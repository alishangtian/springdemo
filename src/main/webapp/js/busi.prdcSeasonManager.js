var flagGreenHouse="";
var o="";
$(function() {
	
	$("#queryBtn").click(function() {
		prdcSeasonResult.dtpaginggrid("setGridParam",{url:'busi/prdcSeasonManager!getPrdcSeasonsByQueryBean.action?'+$('#queryForm').serialize()});
		prdcSeasonResult.dtpaginggrid("option","page", 1);
		prdcSeasonResult.dtpaginggrid("reload");
	});
	//alert($("#a").val());
	$("#selectEdit option[value='"+$("#a").val()+"']").attr("selected",true);
	$("#addBtn").click(function() {
		
		if($("#queryHouseId").val()==""||flagGreenHouse==null||flagGreenHouse==""){
			alert("请选择温室");
		}else{
			window.open('busi/prdcSeasonManager!createPrdcSeasonURL.action?prdcSeason.houseId='+$("#queryHouseId").val(),'newwindow',
					'height=350,width=800,top=200,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
		}

	});
	
	$("#createPrdcSeasonBtn").click(function() {
		var BTime = $("#queryPrdcSeasonBeginTime").val();
		var ETime =  $("#queryPrdcSeasonEndTime").val();
		$.post("busi/prdcSeasonManager!findPrdcSeasonByTime.action",{'BTime':BTime,'ETime':ETime},function(data){
			if(data.prdcSeasionTimeFlag==true){
				$.dtmessagebox.alert("种植季时间不符合");
			}else{
				
				alert("添加成功");
				
				$("#createPrdcSeasonForm").submit();	
				
				window.opener.prdcSeasonResult.dtpaginggrid("reload");
				window.opener.prdcSeasonResult.dtpaginggrid("reload");
				window.close();
			}
		})

	});
	$("#cancelBtn4Add").click(function() {
		//$.dtdialog.closeDialog().closeDialog("#greenHouse",o);
		window.close();
	});
	$("#cancelBtn4Edit").click(function() {
		window.close();
	});
	$("#cancelBtn4View").click(function() {
		window.close();
	});
	$("#cancelBtn").click(function() {
		window.close();
	});
	$("#editPrdcSeasonBtn").click(function() {
		
		$("#editPrdcSeasonForm").submit();	
		
		alert("修改成功");
		
		window.opener.prdcSeasonResult.dtpaginggrid("reload");
		window.opener.prdcSeasonResult.dtpaginggrid("reload");
		window.close();
		
		
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
				$.post("prdcSeasonManager!deleteProduceWorksInfo.action",{'produceWorksInfoCodeStr' : selrow}, function(data) {														
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
	window.open('busi/prdcSeasonManager!getPrdcSeasonById.action?prdcSeason.id='+id,
			'newwindow',
	'height=300,width=800,top=200,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	

}

/**
 * 跳转修改页面
 * @param id
 */
function toUpdate(id){
	window.open('busi/prdcSeasonManager!updatePrdcSeasonURL.action?prdcSeason.id='+id,'newwindow',
			'height=300,width=800,top=200,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
}

//删除一个 一般是状态作废
function deleteOne(id){
	$.post("busi/prdcSeasonManager!findPrdcSeasonRelation.action",{'prdcSeason.id':id},function(data){
		if(data.prdcSeasonFlag){
			$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
				if (msgBtn == $.dtdialog.DR_OK) {
					$.post("busi/prdcSeasonManager!updatePrdcSeason2Unable.action",{'prdcSeason.id' : id}, function(data) {														
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
		}else{
			$.dtmessagebox.alert("种植季有关联，不能删除");
		}
	
	})
	
	

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
	var houseName = arguments[2].nodeTreeName; //给caption赋值
	prdcSeasonResult.dtpaginggrid('setCaption','查询结果：'+houseName);
	
	if (arguments[2].isParent == false) {
		
		$("#queryHouseId").val(arguments[2].nodeTreeId);
		flagGreenHouse=arguments[2].parentId;
		prdcSeasonResult.dtpaginggrid("setGridParam",{url:'busi/prdcSeasonManager!getPrdcSeasonsByQueryBean.action?'+$('#queryForm').serialize()});
		prdcSeasonResult.dtpaginggrid("option","page", 1);
		prdcSeasonResult.dtpaginggrid("reload");
	}
	
	//alert($("#queryHouseId").val());
}

