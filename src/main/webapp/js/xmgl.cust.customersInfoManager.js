var valiFlag=[false,true,true,true];
$(function() {
	
	$("#queryDomainBtn").click(function() {
		
		var keyValue = {};
		
		keyValue['customer.name'] = $("#queryCustomerName").val();
		
		keyValue['customer.state'] =$("#state").val();
		keyValue['customer.contacts'] =$("#queryContacts").val();
		customersInfoResult.dtpaginggrid("option","postData", keyValue);
		customersInfoResult.dtpaginggrid("option","mtype", "POST");
		customersInfoResult.dtpaginggrid("option","url", "customerInfoManager!getCustomersByQueryBean.action");
		customersInfoResult.dtpaginggrid("option","page", 1);
		customersInfoResult.dtpaginggrid("reload");
	});
	
	
	$("#testDialogShow").click(function(){
		
	});
	
	
	//客户名校验
	$("#castomerName").blur(function(){
		var custName = $("#castomerName").val();
		
		$.post("customerInfoManager!valiName.action",{'custName':custName},function(json){
			if(custName==""){
				$("#valiName").html("客户名称不为空").css("color","red");
			}else{
				if(json.flag==true){
					$("#valiName").html("客户名已存在").css("color","red");
				}else{
					valiFlag[0]=true;
					$("#valiName").html("√").css("color","green");
					
				}
			}
			
		});
		
	});
	$("#castomerName").focus(function(){
		$("#valiName").html("");
	});
	//电话校验
	$("#queryCustomerPhone").blur(function(){
		var custPhone=$("#queryCustomerPhone").val();
		var myreg = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		
		if(custPhone!=""){
			if(custPhone.length!=11||(!myreg.test(custPhone))){
				$("#custPhoneW").html("电话号码格式不正确").css("color","red");
			}else{
				valiFlag[1]=true;
				$("#custPhoneW").html("√").css("color","green");
			}
		}else{
			valiFlag[1]=true;
		}
		
		
	});
	$("#queryCustomerPhone").focus(function(){
		$("#custPhoneW").html("");
	});
	
	//手机校验
	$("#queryCustomerMobile").blur(function(){
		var custPhone=$("#queryCustomerMobile").val();
		var myreg = /^(((13[0-9]{1})|159|153)+\d{8})$/;
		if(custPhone!=""){
			if(custPhone.length!=11||!myreg.test(custPhone)||custPhone==""){
				$("#custMob").html("手机号码格式不正确").css("color","red");
			}else{
				valiFlag[2]=true;
				$("#custMob").html("√").css("color","green");
				
			}
		}else{
			valiFlag[2]=true;
		}
		
	});
	$("#queryCustomerMobile").focus(function(){
		$("#custMob").html("");
	});
	//邮箱校验
	$("#queryCustomerEmail").blur(function(){
		var custEmail=$("#queryCustomerEmail").val();
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(custEmail!=""){
			if(!myreg.test(custEmail)||custEmail==""){
				$("#custEmailW").html("邮箱格式不正确").css("color","red");
			}else{
				valiFlag[3]=true;
				$("#custEmailW").html("√").css("color","green");
				
			}
		}else{
			valiFlag[3]=true;
		}
		
	});
	$("#queryCustomerEmail").focus(function(){
		$("#custEmailW").html("");
	});
	
	
	/*if(valiFlag==false){
		window.location.href="javascript:void(0)";
		alert("输入信息不正确");
	}*/
	
	$("#createCustomerBtn").click(function() {
//		alert(valiFlag[0]);
//		alert(valiFlag[1]);
//		alert(valiFlag[2]);
//		alert(valiFlag[3]);
//		alert(valiFlag[0]&&valiFlag[1]&&valiFlag[2]&&valiFlag[3]);
		if(valiFlag[0]&&valiFlag[1]&&valiFlag[2]&&valiFlag[3]){
			window.location.href="customerInfoManager!createCustomer.action";
			$("#createCustomerForm").attr("action","customerInfoManager!createCustomer.action");
			$("#createCustomerForm").submit();
			
		}
		else{
			alert("输入信息不正确");
		}
	});
	
	
	
	$("#addCustomersInfoBtn").click(function() {
		$.dtdialog.showModal({
			title : '添加客户',
			url : 'busi/customerInfoManager!createCustomerURL.action',
			width : 1000,
			height : 700,
			buttons : {},
			resizable : false
			});
	});
	
	
	$("#cancelBtn4Add").click(function() {
		window.location.href="customerInfoManager.action";
	});
	$("#cancelBtn4Edit").click(function() {
		window.location.href="customerInfoManager.action";
	});
	$("#cancelBtn4View").click(function() {
		window.location.href="customerInfoManager.action";
	});
	
	$("#createCustomerBtn").click(function() {
		$("#createCustomerForm").submit();
	});
	$("#editCustomerBtn").click(function() {
		$("#editCustomerForm").submit();
	});
	
	//重置表单
	$("#resetCustomerBtn").click(function() {
		$("#state").val("");
		$("#queryCustomerName").val("");
		$("#queryContacts").val("");
	});
	
	/*$("#delCustomersInfoBtn").click(function() {
		deleteDomain();
	});*/
	
	
	
});

/**
 * 批量删除
 */
/*function deleteDomain() {
	var selrow = customersInfoResult.dtpaginggrid("option",'selarrrow') + "";
	if (selrow != null && selrow.length > 0) {
		$.dtmessagebox.confirm("确定要删除吗？", "询问", function(msgBtn) {
			if (msgBtn == $.dtdialog.DR_OK) {
				$.post("customerInfoManager!deleteProduceWorksInfo.action",{'produceWorksInfoCodeStr' : selrow}, function(data) {														
					if (data.flag) {
						$.dtmessagebox.alert("删除成功");
						customersInfoResult.dtpaginggrid("reload");
						customersInfoResult.dtpaginggrid("reload");
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
	$.dtdialog.showModal({
		title : '查看详情',
		url : 'customerInfoManager!getCustomerByCustId.action?customer.custId='+id,
		width : 1000,
		height : 400,
		buttons : {},
		resizable : false
		});
	//window.location.href="customerInfoManager!getCustomerByCustId.action?customer.custId="+id;
}

/**
 * 跳转修改页面
 * @param id
 */
function toUpdate(id){
	var d =$.dtdialog.showModal({
		title : '查看详情',
		url : 'customerInfoManager!updateCustomerURL.action?customer.custId='+id,
		width : 1000,
		height : 400,
		buttons : {},
		resizable : false
		});
	$.dtdialog.closeDialog(d);
	//window.location.href="customerInfoManager!updateCustomerURL.action?customer.custId="+id;
}

//删除一个 一般是状态作废
function deleteOne(id,sta){
	$.dtmessagebox.confirm("确定要停用/启用吗？", "询问", function(msgBtn) {
		if (msgBtn == $.dtdialog.DR_OK) {
			$.post("customerInfoManager!updateCustomer2Unable.action",{'customer.custId' : id,'customer.state':sta}, function(data) {														
				if (data.flag) {
					$.dtmessagebox.alert("操作成功!");
					customersInfoResult.dtpaginggrid("reload");
				} else {
					if (data.msg) {
						$.dtmessagebox.alert(data.msg);
					} else {
						$.dtmessagebox.alert("操作失败,请重试!");
					}
				}
			});
		}
	});
}

//操作单元格返回的链接
function operFormat(cellvalue, options, rowObject){
	var id = rowObject.custId;
	var state = rowObject.state;
	if(state=="1"){
		var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detail('" + id + "');\">查看</a>&nbsp;&nbsp;"
	    + "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdate('" + id + "');\">编辑</a>&nbsp;&nbsp;"
		+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('" + id + "','0');\">停用</a>&nbsp;&nbsp;";
	}
	if(state=="0"){
		var mid = "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:detail('" + id + "');\">查看</a>&nbsp;&nbsp;"
	    + "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:toUpdate('" + id + "');\">编辑</a>&nbsp;&nbsp;"
		+ "<a style=\"text-decoration:underline\" href=\"javascript:void(0);\" onclick=\"javascript:deleteOne('" + id + "','1');\">启用</a>&nbsp;&nbsp;";
	}
	
	var start = "<div align = 'center'>";
	var end = "</div>";
	return start + mid + end;
}


