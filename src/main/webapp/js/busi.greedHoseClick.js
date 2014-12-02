var flagGreenHouse="";
var o="";
$(function() {
	$("#cycle").change(function(){
		var cycleData = $("#cycle").val();
		var tid = $("#tid").val();
		var tname = $("#tname").val();
		$.post("busi/greedHoseClickAction!testAjax.action?hour="+cycleData+"&tid="+tid,function(data){
			var x = jQuery.parseJSON( data.testArrayX);
			var jd = jQuery.parseJSON( data.testArrayD);
			var mm = jQuery.parseJSON( data.equipMM);
			getChart(jd,x,mm,tname);
		});
		
	});
	
	$("#showCam").click(function(){
		window.open('busi/greedHoseClickAction!videosInfo.action','newwindow',
		'height=400,width=900,top=200,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,toolbar=no');	
		//$("#shipin").attr("style", "display:block;");  
			//$('.cam-win').show();
	});
 
});
 
function clearInfo(){
	$("#tr_Temperature").val("");
	$("#tr_Humidity").val("");
	$("#SoilConductivity").val("");
	$("#kq_Temperature").val("");
	$("#kq_Humidity").val("");
}
//设备图表
function DataInfo(a,b){
	var id = a;
	$("#tname").val(b);
	$.post("busi/greedHoseClickAction!testAjax.action?tid="+id,function(data){
		var x = jQuery.parseJSON( data.testArrayX);
		var jd = jQuery.parseJSON( data.testArrayD);
		var mm = jQuery.parseJSON( data.equipMM);
		getChart(jd,x,mm,b);
	}); 
}
//点击开关事件
function onOffInfo(a){
	var aid="a"+a;
	var sta = document.getElementById(aid).innerText;
	if(sta=="打开"){
		 document.getElementById(aid).innerText="关闭";
	}else{
		 document.getElementById(aid).innerText="打开";
	}
}
/**
 * 选择功能树时触发事件，加载功能菜单对应的功能
 */
function clickOper(e,treeId,node,nodeId) {
	 //alert(e+"--"+treeId+"--"+node+"--"+nodeId);
	 //alert(arguments[2].levelTree);
	 if(arguments[2].levelTree=="2"){
		 $("#eqinfo").empty();
		 $("#controlInfo").empty();
		 var equipId= arguments[2].nodeTreeId;
		 findHoseBaseName(arguments[2].nodeTreeId);
		 $("#listConl").attr("style", "display:none;"); 
		 $.getJSON("busi/greedHoseClickAction!selectEquipData.action?equipId="
				 + equipId+"&houseId="+equipId, function(data) {
					var checkItem ="";	 
					$.each(data, function(i, item) {
								checkItem +="<h3 class='equip-title'>"+item.equipInfoName+"</h3>";
								checkItem+="<ul class='alerm-list'>";
								var list = item.equipDataEnvdataTypeInfo;
						$.each(list,function(j,itemj){
								checkItem +="<li>";
								/*checkItem +=	"<span class='alerm-icon'></span>";*/
								checkItem += "<em>"+itemj.value +itemj.units+"</em>";
								if(itemj.name=="空气湿度"){
									checkItem +="<span class='one-equip'></span>";
								}else if(itemj.name=="空气温度"){
									checkItem +="<span class='one-equip two-equip'></span>";
								/*}else if(itemj.name=="相对水率"){
									checkItem +="<span class='one-equip three-equip'></span>";*/
								}else if(itemj.name=="土壤湿度"){
									checkItem +="<span class='one-equip four-equip'></span>";
								}else if(itemj.name=="土壤温度"){
									checkItem +="<span class='one-equip five-equip'></span>";
								}else if(itemj.name=="co2含量"){
									checkItem +="<span class='one-equip six-equip'></span>";
								}else if(itemj.name=="土壤PH值"){
									checkItem +="<span class='one-equip two-equip'></span>";
								}else if(itemj.name=="光照度"){
									checkItem +="<span class='one-equip six-equip'></span>";
								}else if(itemj.name=="土壤电导率"){
									checkItem +="<span class='one-equip two-equip'></span>";
								}
								checkItem +=	"<i>"+itemj.name+"</i>";
								checkItem += "</li>";	 
							})
							checkItem+="<li class='li-clear'></li>";
							checkItem+="</ul>";
					});
						$("#eqinfo").html(checkItem); 
		});
	   $.getJSON("busi/greedHoseClickAction!selectControlInfo.action?equipId="
			+ equipId+"&houseId="+equipId, function(data) {
					var checkItem ="";
				 $.each(data, function(i, item) {
					    checkItem +="<h3 class='equip-title'>";
						checkItem +=item.parentName+"--"+item.controName+"</h3>";
						var list = item.equipControlInfoList;
						checkItem +="<ul class='equip-list fl'>";
					$.each(list,function(j,itemj){
						checkItem +="<li onClick=\"onOffInfo('"+itemj.id+"')\" value=''>";
						if(itemj.name=="风机"){
							checkItem +="<img src="+contextPath+"/images/equip-1.png>";
						}else if(itemj.name=="遮阳帘"){
							checkItem +="<img src="+contextPath+"/images/equip-2.png>";
						}else if(itemj.name=="顶部通风"){
							checkItem +="<img src="+contextPath+"/images/equip-3.png>";
						}else if(itemj.name=="水泵"){
							checkItem +="<img src="+contextPath+"/images/equip-4.png>";
						}else if(itemj.name=="电磁阀"){
							checkItem +="<img src="+contextPath+"/images/equip-5.png>";
						}else if(itemj.name=="二氧化碳罐"){
							checkItem +="<img src="+contextPath+"/images/equip-6.png>";
						}else if(itemj.name=="灯光"){
							checkItem +="<img src="+contextPath+"/images/equip-7.png>";
						}
						checkItem += "<em>"+itemj.name+":</em><a href='javascript:void(0)' class='open'><div id='a"+itemj.id+"'>打开</div></a>";
					})
						checkItem+="<li class='li-clear'></li>";
						checkItem+="</ul>";
				});
				$("#controlInfo").html(checkItem+""); 
				$("#maginfo").attr("style", "display:none;");  
			    $("#kzqinfo").attr("style", "display:block;"); 
		}); 
	 }
	 if(arguments[2].levelTree=="3"){
		 $("#listConl").attr("style", "display:none;"); 
		var edtId = "";
		var edtName="";
		var equipId = arguments[2].nodeTreeId;
		$.ajax({
			url:"busi/greedHoseClickAction!queryEquipNetTopology.action",
			type:'post',
			data:{"equipId":equipId},
			success:function(datasource){
				 if("1" == datasource.flag){
					 //查询控制器
					 $("#eqinfo").empty();
					 $("#maginfo").attr("style", "display:none;");  
					 $("#kzqinfo").attr("style", "display:block;");  
					 $("#listConl").attr("style", "display:block;");  
					 showHouseBase(equipId);
					 $.getJSON("busi/greedHoseClickAction!selectControlInfo.action?equipId="+ equipId, 
							 function(data) {
							$("#controlInfo").empty();
							var checkItem ="";	 
						 $.each(data, function(i, item) {
							    checkItem +="<h3 class='equip-title'>";
								checkItem +=item.parentName+"--"+item.controName+"</h3>";
								var list = item.equipControlInfoList;
								checkItem +="<ul class='equip-list fl'>";
							$.each(list,function(j,itemj){
								checkItem +="<li onClick=\"onOffInfo('"+itemj.id+"')\" value=''>";
								if(itemj.name=="风机"){
									checkItem +="<img src="+contextPath+"/images/equip-1.png>";
								}else if(itemj.name=="遮阳帘"){
									checkItem +="<img src="+contextPath+"/images/equip-2.png>";
								}else if(itemj.name=="顶部通风"){
									checkItem +="<img src="+contextPath+"/images/equip-3.png>";
								}else if(itemj.name=="水泵"){
									checkItem +="<img src="+contextPath+"/images/equip-4.png>";
								}else if(itemj.name=="电磁阀"){
									checkItem +="<img src="+contextPath+"/images/equip-5.png>";
								}else if(itemj.name=="二氧化碳罐"){
									checkItem +="<img src="+contextPath+"/images/equip-6.png>";
								}else if(itemj.name=="灯光"){
									checkItem +="<img src="+contextPath+"/images/equip-7.png>";
								}
								checkItem += "<em>"+itemj.name+":</em><a href='javascript:void(0)' class='open'><div id='a"+itemj.id+"'>打开</div></a>";
							})
								checkItem+="<li class='li-clear'></li>";
								checkItem+="</ul>";
						});
						$("#controlInfo").html(checkItem+""); 
					}); 
				 }else{
					 $("#eqinfo").empty();
					 //查询传感器
					 $.getJSON("busi/greedHoseClickAction!selectEquipData.action?equipId="+ equipId, 
							 function(data) {
							var checkItem =	"";
							$.each(data, function(i, item) {
								 checkItem +="<h3 class='equip-title'>"+item.equipInfoName+"</h3>";
								 checkItem+="<ul class='alerm-list'>";
								 var list = item.equipDataEnvdataTypeInfo;
								$.each(list,function(j,itemj){
									if(edtId==""){
										edtId=""+itemj.equipDataTypeId+"";
										edtName=""+itemj.name+"";
									}	
								checkItem +="<li onClick=\"DataInfo('"+itemj.equipDataTypeId+"','"+itemj.name+"')\" value="+itemj.equipDataTypeId+" >";
								/*checkItem +=	"<span class='alerm-icon'></span>";*/
								checkItem += "<em>"+itemj.value +itemj.units+"</em>";
									if(itemj.name=="空气湿度"){
										checkItem +="<span class='one-equip'></span>";
									}else if(itemj.name=="空气温度"){
										checkItem +="<span class='one-equip two-equip'></span>";
									/*}else if(itemj.name=="相对水率"){
										checkItem +="<span class='one-equip three-equip'></span>";*/
									}else if(itemj.name=="土壤湿度"){
										checkItem +="<span class='one-equip four-equip'></span>";
									}else if(itemj.name=="土壤温度"){
										checkItem +="<span class='one-equip five-equip'></span>";
									}else if(itemj.name=="co2含量"){
										checkItem +="<span class='one-equip six-equip'></span>";
									}else if(itemj.name=="土壤PH值"){
										checkItem +="<span class='one-equip two-equip'></span>";
									}else if(itemj.name=="光照度"){
										checkItem +="<span class='one-equip six-equip'></span>";
									}else if(itemj.name=="土壤电导率"){
										checkItem +="<span class='one-equip two-equip'></span>";
									}
								checkItem +=	"<i>"+itemj.name+"</i>";
								checkItem += "</li>";
							})
							checkItem+="<li class='li-clear'></li>";
							checkItem+="</ul>";
						});
							$("#eqinfo").html(checkItem+""); 
							$.post("busi/greedHoseClickAction!testAjax.action?tid="+edtId,function(data){
								var x = jQuery.parseJSON( data.testArrayX);
								var jd = jQuery.parseJSON( data.testArrayD);
								var mm = jQuery.parseJSON( data.equipMM);
								getChart(jd,x,mm,edtName);
							});
							$("#tid").val(edtId);
					});
			    $("#kzqinfo").attr("style", "display:none;");  
				$("#maginfo").attr("style", "display:block;");  
				 }
			} 
		});
	 }
}

function getChart(jd,x,mm,edtName){
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
	        	 var word2 = edtName;
	        	 
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

function showHouseBase(nodeId){
	econInfoHResult.dtpaginggrid("setGridParam",{url:'busi/greedHoseClickAction!equipControlInfoHisList.action?equipId='+nodeId});
	econInfoHResult.dtpaginggrid("option","page", 1);
	econInfoHResult.dtpaginggrid("reload");
}
function findHoseBaseName(houseId){
	$.ajax({
		url:"busi/greedHoseClickAction!ajaxBaseHoseName.action",
		type:'post',
		data:{"houseId":houseId},
		success:function(datasource){
			var h3= document.getElementsByTagName("h3")[0];
		    h3.innerHTML = datasource.baseName +":"+datasource.houseName;
		    var h4= document.getElementsByTagName("h4")[0];
		    h4.innerHTML = datasource.crops;
		}
		});
}
