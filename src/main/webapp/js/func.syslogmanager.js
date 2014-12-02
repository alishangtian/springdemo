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

function queryBtnClick() {
	var log={};
	log["pqb.queryBean.logCategory"]=$.trim($("#logCategory").val());
	log["pqb.queryBean.logLevel"]=$.trim($("#logLevel").val());
	log["pqb.queryBean.logOperator"]=$.trim($("#logOperator").val());
	log["pqb.queryBean.startTime"]=$.trim($("#startTime").val());
	log["pqb.queryBean.endTime"]=$.trim($("#endTime").val());	
	sysLogGrid.dtpaginggrid("option",{
		url: 'sm/sysLogAction!doQuery.action',
		postData:log, 
		page:1
	});
	sysLogGrid.dtpaginggrid("reload");
}

function startTimeClose() {
	endTime.dtdatepicker('option','minDate', startTime.dtdatepicker('getDate'));
}