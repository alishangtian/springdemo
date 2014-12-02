$(function() {
	/*$("#queryDomainBtnO").click(function() {
		var optnWorksInfo = {};
		optnWorksInfo['operationWorksInfo.produceSeasonId'] = $("#queryProduceSeasonId").val();
		optnWorksInfo['operationWorksInfo.beginTime'] = $("#queryStartTime").val();
		optnWorksInfo['operationWorksInfo.beginTime0'] = $("#queryStartTime0").val();
		optnWorksInfo['operationWorksInfo.endTime'] = $("#queryEndTime").val();
		optnWorksInfo['operationWorksInfo.endTime0'] = $("#queryEndTime0").val();
		optnWorksInfo['operationWorksInfo.houseId'] = $("#houseId").val();
		equipStateDataResult.dtpaginggrid("option","postData", optnWorksInfo);
		equipStateDataResult.dtpaginggrid("option","mtype", "POST");
		
		equipStateDataResult.dtpaginggrid("option","url", "dataAcquisitionDisplayAction!getDataAcquisitionDisplay.action");
		equipStateDataResult.dtpaginggrid("option","page", 1);
		
		equipStateDataResult.dtpaginggrid("reload");
		
	});*/
	$("#queryDomainBtnO").click(function() {
		var typeinfo = $("#typeinfo").val();
		window.location.href="dataAcquisitionDisplayAction!getDataAcquisitionDisplay.action?typeinfo="+typeinfo;
	});
	 
	
});
 
