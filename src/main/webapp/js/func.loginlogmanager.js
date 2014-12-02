$(function() {
	/* 初始化开始、结束时间 */
	var now = new Date();
	/* 开始时间：本月第一天 */
	startTime.dtdatepicker("setDate",(new Date(now.getFullYear(), now.getMonth(), '01', '00', '00', '00')));
	/* 下月第一天 */
	var nextMonth = new Date(now.getFullYear(), now.getMonth() +1 , '01', '23', '59', '59');
	/* 结束时间：本月最后一天 */
	endTime.dtdatepicker("setDate",(new Date(nextMonth - 24 * 60 * 60 * 1000)));
	
	startTimeClose();
});

/*function onSelect1(){
	if($("#endTime").val() < $("#startTime").val()){
		alert("结束日期不能小于开始日期");
		endTime.dtdatepicker("setDate",null);
	}
}*/

function queryBtnClick() {
	var ip = $.trim($("#loginIp").val()); 
	if (ip != ''){
		$patten = /^([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.){2}([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
		if (!$patten.test(ip)){
			$.dtmessagebox.alert('ip地址格式不正确！');
			return;
		}
	}
	
	var log={};
	log["pqb.queryBean.userName"]=$.trim($("#userName").val());
	log["pqb.queryBean.loginName"]=$.trim($("#loginName").val());
	log["pqb.queryBean.loginIp"]=$.trim($("#loginIp").val());
	log["pqb.queryBean.startTime"]=$.trim($("#startTime").val());
	log["pqb.queryBean.endTime"]=$.trim($("#endTime").val());
	
	loginLogGrid.dtpaginggrid("option",{
		url: 'sm/loginLogAction!doQuery.action',
		postData: log, 
		page:1
	});
	loginLogGrid.dtpaginggrid("reload");
}

function startTimeClose() {
	endTime.dtdatepicker('option','minDate', startTime.dtdatepicker('getDate'));
}