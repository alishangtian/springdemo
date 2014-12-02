/**
 * 折线图
 */
(function($,undefined){
	$.widget("ui.line", {
				options: {
					data:[],
					container:'',
					toolTipId : 'tooltip',
					plot : null,
					previousPoint : null,
					mode:null,
					xmin:null,
					xmax:null,
					xshow:true,
					ymin:null,
					ymax:null,
					lineGraph:null
				},
				_create: function(){
					var _this = this;
					_this.drawLineGraph();
					//_this.bindEvents();
				},
				bindEvents : function() {
							var _this = this;
							this.element.bind("plothover", function (event, pos, item) {
						        if (item) {
						        	if (_this.options.previousPoint != item.dataIndex) {
						        		_this.options.previousPoint = item.dataIndex;
						        		$("#"+_this.options.tooltip).remove();
						        		var x = item.datapoint[0].toFixed(2),
						        			y = item.datapoint[1].toFixed(2);
						        		if(_this.options.mode=="time"){
						        			x=new Date(Number(x)+new Date().getTimezoneOffset()*60*1000).format("yyyy-MM-dd hh:mm:ss");
						        		}
						        		_this.showTooltip(item.pageX, item.pageY, item.series.label + "(" + x + "," + y + ")");
						        	}
						        } else {
						        	$("#"+_this.options.tooltip).remove();
						        	_this.options.previousPoint = null;            
					            }
						    });
							this.element.bind("plotclick", function (event, pos, item) {
						        if (item) {
									var x = item.datapoint[0].toFixed(2),
						        		y = item.datapoint[1].toFixed(2);
									if(_this.options.mode=="time"){
					        			x=new Date(Number(x)+new Date().getTimezoneOffset()*60*1000).format("yyyy-MM-dd hh:mm:ss");
					        		}
									dt$.alert(item.series.label + "(" + x + "," + y + ")");
						        }
						    });
						},
						showTooltip : function(x,y,contents) {
							var _this = this;
							$('<div id="' + _this.options.tooltip + '">' + contents + '</div>').css( {
								position: 'absolute',
								display: 'none',
								top: y + 5,
								left: x + 5,
								border: '1px solid #fdd',
								padding: '2px',
								'background-color': '#fee',
								opacity: 0.80
							}).appendTo("body").fadeIn(200);
						},
						drawLineGraph : function() {
							var _this = this;
							_this.options.lineGraph=$.plot(_this.element, _this.options.data, {
								series: {
									shadowSize: 0,
									lines: {
										show: true
									}
								},
								xaxis: {
									mode: _this.options.mode,
									min:_this.options.xmin,
									max:_this.options.xmax,
									show:_this.options.xshow
								},
								yaxis: { 
									min: _this.options.ymin, 
									max: _this.options.ymax
								},
								legend: {
				                	container: $(_this.options.container)
				                }
								/*,
				                grid: { 
				                	hoverable: true,
									clickable: true
				                }*/
							});
						},
						setData:function(datasource){
							this.options.lineGraph.setData(datasource);
						},
						draw:function(){
							this.options.lineGraph.draw();
						}
					
			});
		})(jQuery);