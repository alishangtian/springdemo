$(function(){
	var tid = $("#tid").val();
	$.post("busi/greedHoseClickAction!testAjax.action?tid="+tid,function(data){
		
		var x = jQuery.parseJSON( data.testArrayX);
		var jd = jQuery.parseJSON( data.testArrayD);
		var mm = jQuery.parseJSON( data.equipMM);
//		alert(mm[0].min);
		getChart(jd,x,mm);
		
		
	});
	
	$("#cycle").change(function(){
		
		var cycleData = $("#cycle").val();
//		$.post("busi/greedHoseClickAction!getEquipDataInfo.action?hour="+cycleData+"&tid="+tid,function(data){
//			DataInfoResult.dtpaginggrid("reload");
//			
//		});
		$.post("busi/greedHoseClickAction!testAjax.action?hour="+cycleData+"&tid="+tid,function(data){
			var x = jQuery.parseJSON( data.testArrayX);
			var jd = jQuery.parseJSON( data.testArrayD);
			var mm = jQuery.parseJSON( data.equipMM);
			getChart(jd,x,mm);
		});
		
	});
	
});


function getChart(jd,x,mm){
	require(
	        [
	            'echarts',
	            'echarts/chart/line'
	        ],
	        DrawEChart
	        );

	        function DrawEChart(ec) {
	        	 var myChart = ec.init(document.getElementById('main'));
	        	 var word1 = "最近1小时";
	        	 var word2 = "空气温度";
	        	 
	        	 var option = {
	        			    title : {
	        			        text: word1+word2+'变化',
	        			        subtext: ''
	        			    },
	        			    tooltip : {
	        			        trigger: 'axis'
	        			    },
	        			    legend: {
	        			        data:[word2]
	        			    },
//	        			    toolbox: {
//	        			        show : true,
//	        			        feature : {
//	        			            mark : {show: true},
//	        			            dataView : {show: true, readOnly: false},
//	        			            magicType : {show: true, type: ['line', 'bar']},
//	        			            restore : {show: true},
//	        			            saveAsImage : {show: true}
//	        			        }
//	        			    },
	        			    calculable : true,
	        			    xAxis : [
	        			             {
	        			                 type : 'category',
	        			                 boundaryGap : false,
	        			                 data : x
	        			             }
	        			         ],
	        			         yAxis : [
	        			             {
	        			                 type : 'value',
	        			                 min : mm[0].min,
	        			                 max : mm[0].max,
	        			                 axisLabel : {
	        			                     formatter: '{value} °C'
	        			                 }
	        			    
	        			             }
	        			         ],
	        			         series : [
	        			             {
	        			                 name:word2,
	        			                 type:'line',
	        			                 data:jd,
	        			                 markLine : {
	        			                     data : [
	        			                         {type : 'average', name: '平均值'}
	        			                     ]
	        			                 }
	        			             }
	        			         ]
	        			};
	            myChart.setOption(option);
	        }
}

