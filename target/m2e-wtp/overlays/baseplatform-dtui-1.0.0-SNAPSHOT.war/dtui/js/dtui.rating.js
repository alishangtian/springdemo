//如果 要添加新的提示消息 如 很好 好 一般 差 很差等 选择星星后的提示信息
//可做如下修改：
//1.options 属性中 应该添加 一个类数组 的变量用于 记录取值范围 和对应的 提示信息 如 5颗星 很好 4颗星 好.....
//2.在 mouseenter和mouseover 事件中 修改 他们的html内容即可     最好可以调节一下提示信息的位置。

(function($,undefined){
	
	$.widget("dtui.dtRating",{
		
		options : {
			/** String vars **/
			bigStarsPath : '../../dtui/theme/default/rating/icons/stars.png', // 设置大星星（默认显示）的相对路径
			smallStarsPath :'../../dtui/theme/default/rating/icons/small.png', // 设置小星星（默认显示）的相对路径
			phpPath : 'php/jRating.php',// 点击鼠标，评分确定后，将POST数据的地址
			type : 'big', // 可以看出，默认是使用大星星

			/** 布尔变量 **/
			step:false, // if true,  mouseover binded star by star,// 如果设置为True，则星星要么全变色，要么不全变，当然这也适和选择分数是同步的。
			disabled:false,//如果设置为True，则插件不能编辑，当点击鼠标过后，默认是True的状态
			showRateInfo: true,//当鼠标放到星星上时，是否在鼠标下方显示选择比例信息，例如16/20
			canRateAgain : true,//是否支持多次点击星星 评分

			/** 整数变量 **/
			length:5, // 显示的星星的个数
			decimalLength : 0, // 选择的数字其后的小数位，最多为3位，如果设置为1，可能出现的情况为16.3/20  但是你完全可以利用函数 'getNote'来设值
			rateMax : 20, //   比例中的分母，整数0-9999(或更大)        默认的总分 
			rateInfosX : -45, // 信息提示框相对于鼠标位置的横坐标位置
			rateInfosY : 5,   // 信息提示框相对于鼠标位置的纵坐标位置
			nbRates : 100,      //可以重复点击时 可以重复的次数
			
			dataaverage:0,     //默认选中的星星

			/** 函数 **/
			onSuccess : null, //成功后的回调函数
			onError : null ,    //出错处理函数
			
			/**控件内部使用的变量 用户不必关注**/
			newWidth : 0,
			starWidth : 0,
			starHeight : 0,
			bgPath : '',
			hasRated : false,
			globalWidth: 0,
			nbOfRates:100
			
		},
		_create:function(){
			this._handle();
			
		},
		
		_handle:function(){
			var self=this;
			var opts=self.options;
			if(opts.length>0)
				return self.element.each(function() {
					opts.nbOfRates =opts.nbRates;
					if($(self.element).hasClass('jDisabled') || opts.disabled)
						var jDisabled = true;
					else
						var jDisabled = false;

					self._getStarWidth();
					$(self.element).height(opts.starHeight);

					var average = parseFloat(opts.dataaverage), // get the average of all rates
					idBox = parseInt($(self.element).attr('data-id')), // get the id of the box
					widthRatingContainer = opts.starWidth*opts.length, // Width of the Container
					widthColor = average/opts.rateMax*widthRatingContainer, // Width of the color Container

					quotient = 
					$('<div>', 
					{
						'class' : 'jRatingColor',
						css:{
							width:widthColor
						}
					}).appendTo($(self.element)),

					average = 
					$('<div>', 
					{
						'class' : 'jRatingAverage',
						css:{
							width:0,
							top:- opts.starHeight
						}
					}).appendTo($(self.element)),

					 jstar =
					$('<div>', 
					{
						'class' : 'jStar',
						css:{
							width:widthRatingContainer,
							height:opts.starHeight,
							top:- (opts.starHeight*2),
							background: 'url('+opts.bgPath+') repeat-x'
						}
					}).appendTo($(self.element));
					

					$(this).css({width: widthRatingContainer,overflow:'hidden',zIndex:1,position:'relative'});

					if(!jDisabled)
					$(this).unbind().bind({
						mouseenter : function(e){
							var realOffsetLeft = self._findRealLeft(this);
							var relativeX = e.pageX - realOffsetLeft;
							
							
							
							if (opts.showRateInfo)
							var tooltip = 
							$('<p>',{
								'class' : 'jRatingInfos',
								html : self._getNote(relativeX)+' <span class="maxRate">/ '+opts.rateMax+'</span>',
								css : {
									top: (e.pageY + opts.rateInfosY),
									left: (e.pageX + opts.rateInfosX)
								}
							}).appendTo('body').show();
							
						},
						mouseover : function(e){
							$(this).css('cursor','pointer');	
						},
						mouseout : function(){
							$(this).css('cursor','default');
							if(opts.hasRated) average.width(opts.globalWidth);
							else average.width(0);
						},
						mousemove : function(e){
							var realOffsetLeft = self._findRealLeft(this);
							var relativeX = e.pageX - realOffsetLeft;
							if(opts.step) opts.newWidth = Math.floor(relativeX/opts.starWidth)*opts.starWidth + opts.starWidth;
							else opts.newWidth = relativeX;
							average.width(opts.newWidth);					
							if (opts.showRateInfo)
							$("p.jRatingInfos")
							.css({
								left: (e.pageX + opts.rateInfosX)
							})
							.html(self._getNote(opts.newWidth) +' <span class="maxRate">/ '+opts.rateMax+'</span>');
							
							
							
						},
						mouseleave : function(){
							$("p.jRatingInfos").remove();
						},
						click : function(e){
		                    var element = this;
							
							/*set vars*/
							opts.hasRated = true;
							opts.globalWidth = opts.newWidth;
							opts.nbOfRates--;
							
							if(!opts.canRateAgain || parseInt(opts.nbOfRates) <= 0) $(this).unbind().css('cursor','default').addClass('jDisabled');
							
							if (opts.showRateInfo) $("p.jRatingInfos").fadeOut('fast',function(){$(this).remove();});
							e.preventDefault();
							var rate = self._getNote(opts.newWidth);
							average.width(opts.newWidth);
							

							/** ONLY FOR THE DEMO, YOU CAN REMOVE THIS CODE **/
								$('.datasSent p').html('<strong>idBox : </strong>'+idBox+'<br /><strong>rate : </strong>'+rate+'<br /><strong>action :</strong> rating');
								$('.serverResponse p').html('<strong>Loading...</strong>');
							/** END ONLY FOR THE DEMO **/

							$.post(opts.phpPath,{
									idBox : idBox,
									rate : rate,
									action : 'rating'
								},
								function(data) {
									if(!data.error)
									{
										/** ONLY FOR THE DEMO, YOU CAN REMOVE THIS CODE **/
											$('.serverResponse p').html(data.server);
										/** END ONLY FOR THE DEMO **/


										/** Here you can display an alert box, 
											or use the jNotify Plugin :) http://www.myqjqueryplugins.com/jNotify
											exemple :	*/
										if(opts.onSuccess) opts.onSuccess( element, rate );
									}
									else
									{

										/** ONLY FOR THE DEMO, YOU CAN REMOVE THIS CODE **/
											$('.serverResponse p').html(data.server);
										/** END ONLY FOR THE DEMO **/

										/** Here you can display an alert box, 
											or use the jNotify Plugin :) http://www.myqjqueryplugins.com/jNotify
											exemple :	*/
										if(opts.onError) opts.onError( element, rate );
									}
								},
								'json'
							);
							
							
							
							
							
							
							
						}
					});
				}	
			);
		},
		
		
		_getNote : function(relativeX) {
			var opts=this.options;
			var noteBrut = parseFloat((relativeX*100/(opts.starWidth*opts.length))*opts.rateMax/100);

			switch(opts.decimalLength) {
				case 1 :
					var note = Math.round(noteBrut*10)/10;
					break;
				case 2 :
					var note = Math.round(noteBrut*100)/100;
					break;
				case 3 :
					var note = Math.round(noteBrut*1000)/1000;
					break;
				default :
					var note = Math.round(noteBrut*1)/1;
			}
			
			return note;
		},
		
		_getStarWidth:function (){
			var opts=this.options;
			switch(opts.type) {
				case 'small' :
					opts.starWidth = 12; // width of the picture small.png
					opts.starHeight = 10; // height of the picture small.png
					opts.bgPath = opts.smallStarsPath;
				break;
				default :
					opts.starWidth = 23; // width of the picture stars.png
					opts.starHeight = 20; // height of the picture stars.png
					opts.bgPath = opts.bigStarsPath;
			}
		},
		
		
	    _findRealLeft:function (obj) {
			  if( !obj ) return 0;
			  return obj.offsetLeft + this._findRealLeft( obj.offsetParent );
		}
		
		
	});
	
})(jQuery);