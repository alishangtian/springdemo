/*!
 * jQuery dtutil
 */
(function( $, undefined ) {
	$.fn.extend({
		construtorName: function(){
			var cname = null;
			var data = $.data(this[0]);
			$.each(data, function(key, value){
				//由于IE没有__proto__属性，所以使用判断widgetName和widgetFullName属性是否存在，来确定是否是界面控件
				//if (value.__proto__ instanceof $.Widget) {
				if (value.widgetName && value.widgetFullName){
					cname = value.widgetName || value.__proto__.widgetName;
					return false;
				}
				//console.log(key);
				//console.log(value.__proto__);
			});
			return cname;
		},

		callUIMethod: function(methodName) {
			var cname = this.construtorName();
			if (cname) {
				return this[cname].apply(this, arguments);
			}
			return null;
		}
	});
	
	$.browser = {
			mozilla : /firefox/.test(navigator.userAgent.toLowerCase()),
			webkit : /webkit/.test(navigator.userAgent.toLowerCase()),
			opera : /opera/.test(navigator.userAgent.toLowerCase()),
			msie : /msie/.test(navigator.userAgent.toLowerCase())
	};
	
	if (!$.dtutil) $.dtutil = {};
	if (!$.dtui) $.dtui = {};
	$.dtui.util = {
		createStarElem: function(required){
			var starContainer = $("<div></div>").addClass("input-star-container"),
				starElem = $("<span class = 'input-star'>*</span>").appendTo(starContainer);
			if(required==true){
				starElem.show();
			}
			else {
				starElem.hide();
			}
			return starContainer;
		},
		
		createTip: function(target, msg, appendTo){
			//var par = appendTo ? appendTo : document.body;
			$(target).css('position','relative');
			var $tip =  $('<div></div>').addClass('dtui-tip ui-corner-all').appendTo(target),
				$tipContent = $('<div></div>').text(msg).appendTo($tip);
			//IE8以下版本不需要预留三角形的位置
			var offset = $.support.leadingWhitespace?10:0;
			$tip.position({
				of: $(target),
				my: "left bottom-"+offset,
				at: "right-15% top",
				collision: "none",
				using: function( position, feedback ) {
					$( this ).css( position );
					//IE8及以下版本不处理三角
					if ($.support.leadingWhitespace){
						$( "<div>" )
							.addClass( "dtui-tip-arrow" )
							.addClass( feedback.vertical )
							.addClass( feedback.horizontal )
							.appendTo( this );
					}
				}
			});
			return $tip;
		}
	};
	
	$.dtutil.strutil = {
		getLength: function(str){
			if (str){
				return str.replace(/[^\x00-\xff]/g,"**").length;				
			}
			else {
				return 0;
			}
		}
	};

	window.$console = {
		log: function(o){
			if (window.console){
				console.log(o);
			}
		}
	};
})( jQuery );
