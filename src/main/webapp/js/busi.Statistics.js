$(function(){
	getAllBassChart();
	var beginTime=$("#beginTime").val();
	var endTime=$("#endTime").val();
	var myDate = new Date();
	var m = myDate.getMonth()+1;
//	$("#endTime").val(myDate.getFullYear()+"-"+m+"-"+myDate.getDate());
//	$("#beginTime").val(myDate.getFullYear()-1+"-"+m+"-"+myDate.getDate())
	$("#queryDateBtn").click(function(){

		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		
//    	$("#queryPick").submit();
//		$.post("statisticsAction!queryPickData.action",{"beginTime":beginTime,"endTime":endTime},function(data){
//			window.location.href="statisticsAction.action?beginTime="+beginTime+"&endTime"+endTime;
//			 
//		});
		window.location.href="statisticsAction.action?beginTime="+beginTime+"&endTime="+endTime;
    });
    
	//获取所有基地图表
	function getAllBassChart(){
		var beginTime=$("#bt").val();
		var endTime=$("#et").val();
		  $.post("busi/statisticsAction!getDateForStatistics.action",{"beginTime":beginTime,"endTime":endTime},function(data){
		    	jsonData=data;
		    	var baseName = [];//所有基地
		    	var cropName=[];//所有作物名称
		    	var jd = jQuery.parseJSON( data.jsonData);
		    	
		    	for(var i=0;i<jd.length;i++){
		    		baseName[i]=jd[i].name;
		    	}
		    	var prdcSeasonList = jQuery.parseJSON( data.prdcSeasonListJson);
		    	for(var i=0;i<prdcSeasonList.length;i++){
		    		cropName[i]=prdcSeasonList[i].crops;
		    	}
		    	
		    	var cropAmount = jQuery.parseJSON( data.cropAmountJson);
		    	getCharts(cropName,baseName,cropAmount);

		    });
	}
	//通过id获取基地下所有温室图表
	
	
    function getBaseChart(id){
    	var beginTime=$("#bt").val();
		var endTime=$("#et").val();
     	$.post("busi/statisticsAction!getGreenHouseDateForStatistics.action",{"beginTime":beginTime,"endTime":endTime,"id":id},function(data){
        	var hl = jQuery.parseJSON( data.hl);
        	var cl = jQuery.parseJSON( data.cl);
        	var houseCropJson = jQuery.parseJSON( data.houseCropJson);
        	getCharts(cl,hl,houseCropJson);
     	});
     }
    
    //点击基地div
    $(".base").click(function(){
    	
    	getBaseChart($(this).attr("id"));
    });
    
    $(".allBase").click(function(){
    	
    	getAllBassChart();
    });
    
    //按时间条件查询
    

     

});


//生成图表
function getCharts(clas,xz,ob){
	 require(
		    [
		        'echarts',
		        'echarts/chart/bar',
		        'echarts/chart/line'
		    ],
    function(ec) {
		        var myChart = ec.init(document.getElementById('main'));
		        var option = {
		            tooltip : {
		                trigger: 'axis'
		            },
		            legend: {
		                data:clas    //参数1
		               
		            },
		            toolbox: {
		                show : true,
		                feature : {
		                    mark : true,
		                    dataView : {readOnly: false},
		                    magicType:['line', 'bar'],
		                    restore : true,
		                    saveAsImage : true
		                }
		            },
		            calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    data : xz      //参数2
		                }
		            ],
		            yAxis : [
		                {
		                    type : 'value',
		                    splitArea : {show : true}
		                }
		            ],
		            series :
		            	ob           //参数3
	
		        };                   
		        myChart.setOption(option);
		    }
		);
}

