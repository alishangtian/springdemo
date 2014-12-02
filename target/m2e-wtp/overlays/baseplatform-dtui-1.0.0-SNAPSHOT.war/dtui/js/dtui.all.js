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
/**
 * 树形下拉框和表格下拉框 多选下拉框 的超类
 */
(function($,undefined){

	var $doc = $(document);
	$.widget("dtui.dtabscombobox", {
		options: {
			name : "",	        //隐藏域的名字
			label: "",            //输入框前面的显示文本
			width:'150px',
			height:'18px',
			dropPanelWidth: "",
			dropPanelHeight: "200px",
			initValue: '',
			initText: '',
			valueField: 'value',
			textField: 'text',
			required:false,      //是否必填
			readonly: false,	//是否只读
			disabled: false,	//是否禁用
			displaySplitChar: ',',
			prompt:"",
			change:null			//回调函数
		},
		_create: function(){
			this._setDefaultOptions();
			this._createElement();
			this._buildHead();
			this._buildContent();
			this._bindEvent();
		},
		
		_init: function(){
			var initValue = this.options.initValue,
				initText = this.options.initText;
			
			if (initValue && initText){
				if ($.isArray(initValue)){
					this._e.valueText.val(initValue.join(this.options.displaySplitChar));
				}
				else {
					this._e.valueText.val(initValue);
				}
				
				if ($.isArray(initText)){
					this._e.inputText.val(initText.join(this.options.displaySplitChar));				
				}
				else {
					this._e.inputText.val(initText);
				}
			}
			this._setupDisabled(this.options.disabled);
		},
		_setDefaultOptions: function() {
			if(!this.options["width"]){
				this.options["width"] = 150;
			}
			if(!this.options["height"]){
				this.options["height"] = 20;
			}
			if(!this.options["dropPanelWidth"]){
				this.options["dropPanelWidth"] = this.options["width"];
			}
			if(!this.options["dropPanelHeight"]){
				this.options["dropPanelHeight"] = 200;
			}			
		},
		
		/**
		 * 创建构成元素
		 * selectOption:下拉框
		 * inputText:显示框
		 * valueText:隐藏域，用于存放键值
		 * selectBtn:下拉按钮
		 * selectHead:下拉框初始显示部分
		 */
		_createElement: function(){
			this._e = {
					selectOption: $('<div class="ui-combobox-select ui-corner-bottom"></div>'),
					inputText: $('<input class="ui-combobox-text ui-corner-left input-init" type="text" />'),
					valueText: $('<input   type="hidden" />'),
					selectBtn: $('<a class="ui-icon ui-icon-triangle-1-s ui-corner-right"></a>'),
					selectHead: $('<div class="ui-combobox-head ui-corner-all"  style="vertical-align:middle;margin:0 auto"></div>')
				};
		},
		/**
		 * 构建下拉框初始显示部分
		 */
		_buildHead: function(){
			var self = this;
			//如果this.element   是div 则直接使用 包在最外层容器
			//如果this.element 不是div 则重新创建 包在最外层容器
			if(self.element.is("DIV")){
				self.totalContainer=self.element;
			}else{
				self.totalContainer=$("<div></div>").insertAfter(this.element);
			}
			self.totalContainer.addClass('ui-combobox')
								.css({"height":this.options.height,"width":this.options.width,"overflow":"visible"});

			this._repack();
			
			//this._e.selectHead.css({"height":"100%"});
			var marginRight = '0px';
			//if(this.options.required)
				marginRight = this._starDiv.outerWidth();
			$console.log("starWidth:"+marginRight);
			$('<div></div>').css('margin-right',marginRight)
				.append(this._e.selectHead)
				.appendTo(this.totalContainer);
			
			
			var divpadding=$("<div></div>").addClass("input-rightpadding")
							.append(this._e.selectBtn).appendTo(this._e.selectHead);
			
			//如果 this.element   是input标签则直接     作为输入框以供使用
			//如果 this.element 不是input标签则重新     创建输入框以供使用(使用默认的就好)
			if(self.element.is("INPUT")){
				this._e.inputText=this.element
							.addClass("ui-combobox-text ui-corner-left input-init");
			}
			var divinput=$("<div></div>").addClass("input-Container")
							.append(this._e.inputText).appendTo(this._e.selectHead)
							.addClass('ui-corner-all').addClass('ui-widget-content');
			divinput.css("margin-right",divpadding.outerWidth());
			//如果既不是IDV也不是INPUT,则无法改造使用,直接干掉
			if(!self.element.is("DIV")&&!self.element.is("INPUT")){
				self.element.hide();
			}
			//创建 完成后 根据总体效果 微调下总体样式
			divinput.css("border-width","0px");
			//值域隐藏
			this._e.valueText.appendTo(this._e.selectHead);
			this.options.name ? null : this.options.name = this.options.id;
			this._e.valueText.attr("name", this.options.name);	
			
			
			if(this.options.readyOnly === "true" || this.options.readyOnly === true){
				this._e.inputText.attr("readonly" , true);
			}
			
			this._e.selectOption.width(this.options["dropPanelWidth"]);
			this._e.selectOption.height('auto');
			this._e.selectOption.css('maxHeight', this.options["dropPanelHeight"]);
			
			this._e.selectOption.css({"padding-left":"0px","padding-right":"0px"});
			
			this._e.selectOption.appendTo(this.totalContainer);
			
			if(this.options.label!=""&&this.options.label!=null){
				$("<label></label>").text(self.options.label).insertBefore(self.totalContainer);
			}
		},
		
		//星号 包装
		_repack:function(){
			this._starDiv = $.dtui.util.createStarElem(this.options.required);
			this._starDiv.appendTo(this.totalContainer);
			this._starElem = this._starDiv.find('span.input-star');
		},
		
		
		_buildContent: function() {
			//console.log('抽象类的_buildContent');
		},
		
		_bindEvent: function() {
			//console.log('抽象类的_bindEvent');
			var self = this;
			this._e.inputText.click(function() {
				if(!self.options.readonly){
					self._toggleSelectBox(!self._e.selectOption.is(":visible"));
				}
            });
            
			this._e.selectBtn.click(function() {
				//$('td', self._e.selectOption).show();
				if(!self.options.readonly){
					self._toggleSelectBox(!self._e.selectOption.is(":visible"));
				}
            });
			
			this._e.inputText.keydown(function(event){
				if (event.keyCode == 8) {
					var clearStatus = self._clear();
					if (clearStatus){
						self._trigger('change');
					}
				}
				event.keyCode = 0;
				return false;
			});
			//add  by wangdexing  2013/4/25  aim : click blank area to close selectOption
			$doc.click(function(event){
		        var target = event.target;
		        if (!$.contains(self._e.selectHead[0], target)
		            && !$.contains(self._e.selectOption[0], target)
		            && target !== self._e.selectHead
		            && target !== self._e.selectOption){
		        		self._hideOption();
		           }
			});
		},
        /**
         * 显示/隐藏下拉框
         * @param isHide
         */
        _toggleSelectBox: function(isHide) {
        	if (isHide) {
        		this._showOption();
        	}else{
        		this._hideOption();
        	}
        },
        /**
         * 显示下拉框
         */
        _showOption: function(){
        	this._e.selectHead.removeClass("ui-corner-all").addClass("ui-corner-top");
        	//this._e.selectHead.css('borderBottomWidth',0);
        	/*//下面两句拿到外面 写成css样式最好
    		this._e.selectOption.css("border-color","#aecaf0");
    		this._e.selectOption.css("border-top","0px");*/
    		
    		this._e.selectOption.addClass('ui-combobox-content');
        	if(this._e.valueText.data("selectItem")){
        		this._e.valueText.data("selectItem").addClass("ui-combobox-option-select");
        	}
        	
        	if (this.options.width && this.options.width.indexOf('%')>=0 && !this.options.dropPanelWidth){
        		this._e.selectOption.width(this._e.selectHead.width());
        	}
        	
        	this._e.selectOption.slideDown("fast");
        	this._e.selectOption.position({
				of: this.totalContainer,
				my: "left top",
				at: "left bottom",
				collision: "flipfit",
				using: function( position, feedback ) {
					$( this ).css( position );
				}
			});
        },
        /**
         * 隐藏下拉列表
         */
        _hideOption: function(){
        	this._e.selectHead.removeClass("ui-corner-top").addClass("ui-corner-all");
        	//this._e.selectHead.css('borderBottomWidth','1px');
        	this._e.selectOption.slideUp("fast");
        },
        
        _clear: function() {
        	
        },
        
        /**
         * 根据当前的值刷新内容区域的选择状态，在子类中实现
         */
        _refreshContentState: function() {
        	
        },
        
        /**
         * 根据值获取对应的文本,在子类中实现
         * @param value
         */
        _getText: function(value){
        	
        },
        
	    value:function(s){
	    	var self=this;
	    	if(s==undefined){
	    		return this._e.valueText.val();
	    	}else{
	    		var v = s;
	    		if ($.isArray(s)){
	    			v = s.join(",");
	    		}
	    		this._e.valueText.val(v);
	    		var txt = this._getText(v);
	    		this._e.inputText.val(txt);
	    		this._refreshContentState();
	    	}
	    },
	    
		text:function(s){
			if(s==undefined){
				return this._e.inputText.val();
			}else{
				this._e.inputText.val(s);
			}
		},     
                
		_setupDisabled: function(disabled){
			if ( disabled ) {
				$('*', this.totalContainer).prop("disabled", true );
			} else {
				$('*', this.totalContainer).prop("disabled", false );
			}
		},
		
		_setupWidth: function(width){
			this.totalContainer.width(width);
		},
		_setupHeight: function(height){
			this.totalContainer.height(height);
		},
		_setupRequired: function(required){
			if(required){
				this._starElem.show();
			}
			else {
				this._starElem.hide();
			}
		},

		_setOption: function( key, value ) {
    		this._super( key, value );
    		if ( key === "disabled" ) {
    			this._setupDisabled(value);
    			return;
    		}
			if (key === "width") {
				this._setupWidth(value);
				return;
			}
			if (key === "height") {
				this._setupHeight(value);
				return;
			}
			if (key === "required") {
				this._setupRequired(value);
				return;
			}
    	},
        
        /**
         * 获取选中的值
         * @param value
         * @returns
         */
        val:function(){
        	if(this._e.valueText.data("data")){
    			return this._e.valueText.data("data")[this.options.keyField];
    		}
        	return null;
        },
        /**
         * 获取选中的数据
         * @param value
         * @returns
         */
        data: function(){
        	if(this._e.valueText.data("data")){
    			return this._e.valueText.data("data");
    		}
        	return null;
        }
	});
})(jQuery);/*!
 * jQuery UI Accordion 1.10.1
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/accordion/
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

$.widget( "dtui.dtaccordion", $.ui.accordion, {
	version: "1.10.1",
	options: {
		active: 0,
		animate: {},
		collapsible: false,
		event: "click",
		header: "> li > :first-child,> :not(li):even",
		heightStyle: "auto",
		icons: {
			activeHeader: "ui-icon-triangle-1-s",
			header: "ui-icon-triangle-1-e"
		},

		// callbacks
		activate: null,
		beforeActivate: null
	},

	_create: function() {
		this._super();
	},

	_destroy: function() {
		this._super();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
	},

	refresh: function() {
		this._super();
	}

});

})( jQuery );
/*!
 * jQuery UI Autocomplete 1.10.1
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/autocomplete/
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	jquery.ui.position.js
 *	jquery.ui.menu.js
 */
(function( $, undefined ) {

// used to prevent race conditions with remote data sources
var requestIndex = 0;

$.widget( "dtui.dtautocomplete", {
	version: "1.10.1",
	defaultElement: "<input>",
	options: {
		label:"",
		appendTo: null,
		autoFocus: false,
		delay: 300,
		minLength: 1,
		position: {
			my: "left top",
			at: "left bottom",
			collision: "none"
		},
		source: null,
		searchProperty:"",  //add by wdx 13/04/17   source为对象时  的 key 也是搜索条件
		searchValue:"",     //add by wdx 13/04/17   source为对象时  的 value 只为显示在key之后

		// callbacks
		change: null,
		close: null,
		focus: null,
		open: null,
		response: null,
		search: null,
		select: null,
		width: 150
	},

	pending: 0,

	_createElement: function(){
		
		this._e = {
				inputText: $('<input class="ui-combobox-text" type="text" />'),
				selectBtn: $('<div class="ui-icon ui-icon-search ui-corner-right"></div>'),
				selectHead: $('<div class="ui-combobox-head ui-corner-all " style="display:inline-block;vertical-align:middle;"></div>')
			};
	},
	
	
	/**
	 * 构建下拉框初始显示部分及绑定相应事件
	 */
	_buildHead: function(o){
		
		this._e.selectHead.width(this.options["width"]);
		this._e.inputText.width(this.options["width"] - 22);
		this._e.inputText.css("float", "left");
		this._e.inputText.css("padding","0px");
		this._e.inputText.css("margin-left","2px");
		this._e.inputText.css("margin-top","3px");
		this._e.inputText.appendTo(this._e.selectHead);
		if(this.options.readyOnly === "true" || this.options.readyOnly === true){
			this._e.inputText.attr("readonly" , true);
		}
		this._e.selectBtn.css("margin-top","2px");
		
		this._e.selectBtn.appendTo(this._e.selectHead);
		
		this._e.selectHead.insertAfter(o);

		this._e.selectBtn.click(function() {
			
			alert("我要搜索");
			
        });
		
		$("<label></label>").text(this.options.label).insertBefore(this._e.selectHead);
		///this._e.selectHead.css("display","inline-block");
		//this._e.selectHead.css("vertical-align","text-bottom");
	},
	
	
	_create: function() {
		
		var e1=this.element.hide();
		this._createElement();//创建对象
		this._buildHead(e1);    //组装对象
		
		
		
		
		
		// Some browsers only repeat keydown events, not keypress events,
		// so we use the suppressKeyPress flag to determine if we've already
		// handled the keydown event. #7269
		// Unfortunately the code for & in keypress is the same as the up arrow,
		// so we use the suppressKeyPressRepeat flag to avoid handling keypress
		// events when we know the keydown event was used to modify the
		// search term. #7799
		var suppressKeyPress, suppressKeyPressRepeat, suppressInput,
			nodeName = this._e.inputText[0].nodeName.toLowerCase(),
			isTextarea = nodeName === "textarea",
			isInput = nodeName === "input";

		this.isMultiLine =
			// Textareas are always multi-line
			isTextarea ? true :
			// Inputs are always single-line, even if inside a contentEditable element
			// IE also treats inputs as contentEditable
			isInput ? false :
			// All other element types are determined by whether or not they're contentEditable
			this._e.inputText.prop( "isContentEditable" );

		this.valueMethod = this._e.inputText[ isTextarea || isInput ? "val" : "text" ];
		this.isNewMenu = true;

		this._e.inputText
			.addClass( "ui-autocomplete-input" )
			.attr( "autocomplete", "off" );

		this._on( this._e.inputText, {
			keydown: function( event ) {
				/*jshint maxcomplexity:15*/
				if ( this._e.inputText.prop( "readOnly" ) ) {
					suppressKeyPress = true;
					suppressInput = true;
					suppressKeyPressRepeat = true;
					return;
				}

				suppressKeyPress = false;
				suppressInput = false;
				suppressKeyPressRepeat = false;
				var keyCode = $.ui.keyCode;
				switch( event.keyCode ) {
				case keyCode.PAGE_UP:
					suppressKeyPress = true;
					this._move( "previousPage", event );
					break;
				case keyCode.PAGE_DOWN:
					suppressKeyPress = true;
					this._move( "nextPage", event );
					break;
				case keyCode.UP:
					suppressKeyPress = true;
					this._keyEvent( "previous", event );
					break;
				case keyCode.DOWN:
					suppressKeyPress = true;
					this._keyEvent( "next", event );
					break;
				case keyCode.ENTER:
				case keyCode.NUMPAD_ENTER:
					// when menu is open and has focus
					if ( this.menu.active ) {
						// #6055 - Opera still allows the keypress to occur
						// which causes forms to submit
						suppressKeyPress = true;
						event.preventDefault();
						this.menu.select( event );
					}
					break;
				case keyCode.TAB:
					if ( this.menu.active ) {
						this.menu.select( event );
					}
					break;
				case keyCode.ESCAPE:
					if ( this.menu.element.is( ":visible" ) ) {
						this._value( this.term );
						this.close( event );
						// Different browsers have different default behavior for escape
						// Single press can mean undo or clear
						// Double press in IE means clear the whole form
						event.preventDefault();
					}
					break;
				default:
					suppressKeyPressRepeat = true;
					// search timeout should be triggered before the input value is changed
					this._searchTimeout( event );
					break;
				}
				
				
				
			},
			keypress: function( event ) {
				if ( suppressKeyPress ) {
					suppressKeyPress = false;
					event.preventDefault();
					return;
				}
				if ( suppressKeyPressRepeat ) {
					return;
				}

				// replicate some key handlers to allow them to repeat in Firefox and Opera
				var keyCode = $.ui.keyCode;
				switch( event.keyCode ) {
				case keyCode.PAGE_UP:
					this._move( "previousPage", event );
					break;
				case keyCode.PAGE_DOWN:
					this._move( "nextPage", event );
					break;
				case keyCode.UP:
					this._keyEvent( "previous", event );
					break;
				case keyCode.DOWN:
					this._keyEvent( "next", event );
					break;
				}
				
				alert("press");
			},
			input: function( event ) {
				if ( suppressInput ) {
					suppressInput = false;
					event.preventDefault();
					return;
				}
				this._searchTimeout( event );
			},
			focus: function() {
				this.selectedItem = null;
				this.previous = this._value();
			},
			blur: function( event ) {
				if ( this.cancelBlur ) {
					delete this.cancelBlur;
					return;
				}

				clearTimeout( this.searching );
				this.close( event );
				this._change( event );
			}
		});

		this._initSource();
		this.menu = $( "<ul>" )
			.addClass( "ui-autocomplete ui-front" )
			.appendTo( this._appendTo() )
			.menu({
				// custom key handling for now
				input: $(),
				// disable ARIA support, the live region takes care of that
				role: null
			})
			.hide()
			.data( "ui-menu" );
		

		this._on( this.menu.element, {
			mousedown: function( event ) {
				// prevent moving focus out of the text field
				event.preventDefault();

				// IE doesn't prevent moving focus even with event.preventDefault()
				// so we set a flag to know when we should ignore the blur event
				this.cancelBlur = true;
				this._delay(function() {
					delete this.cancelBlur;
				});

				// clicking on the scrollbar causes focus to shift to the body
				// but we can't detect a mouseup or a click immediately afterward
				// so we have to track the next mousedown and close the menu if
				// the user clicks somewhere outside of the autocomplete
				var menuElement = this.menu.element[ 0 ];
				if ( !$( event.target ).closest( ".ui-menu-item" ).length ) {
					this._delay(function() {
						var that = this;
						this.document.one( "mousedown", function( event ) {
							if ( event.target !== that.element[ 0 ] &&
									event.target !== menuElement &&
									!$.contains( menuElement, event.target ) ) {
								that.close();
							}
						});
					});
				}
			},
			menufocus: function( event, ui ) {
				// #7024 - Prevent accidental activation of menu items in Firefox
				if ( this.isNewMenu ) {
					this.isNewMenu = false;
					if ( event.originalEvent && /^mouse/.test( event.originalEvent.type ) ) {
						this.menu.blur();

						this.document.one( "mousemove", function() {
							$( event.target ).trigger( event.originalEvent );
						});

						return;
					}
				}

				var item = ui.item.data( "ui-autocomplete-item" );
				if ( false !== this._trigger( "focus", event, { item: item } ) ) {
					// use value to match what will end up in the input, if it was a key event
					if ( event.originalEvent && /^key/.test( event.originalEvent.type ) ) {
						this._value( item.value );
					}
				} else {
					// Normally the input is populated with the item's value as the
					// menu is navigated, causing screen readers to notice a change and
					// announce the item. Since the focus event was canceled, this doesn't
					// happen, so we update the live region so that screen readers can
					// still notice the change and announce it.
					this.liveRegion.text( item.value );
				}
			},
			//给 input 赋选择的值
			menuselect: function( event, ui ) {
				var self=this; //add by wdx
				var item = ui.item.data( "ui-autocomplete-item" ),
					previous = this.previous;

				// only trigger when focus was lost (click on menu)
				if ( this._e.inputText[0] !== this.document[0].activeElement ) {
					this._e.inputText.focus();
					this.previous = previous;
					// #6109 - IE triggers two focus events and the second
					// is asynchronous, so we need to reset the previous
					// term synchronously and asynchronously :-(
					this._delay(function() {
						this.previous = previous;
						this.selectedItem = item;
					});
				}

				if ( false !== this._trigger( "select", event, { item: item } ) ) {
					this._value( item.value+(item[self.options.searchValue]==undefined?"":"|"+item[self.options.searchValue]) );//modefiy by wdx 13/04/17
				}
				// reset the term after the select event
				// this allows custom select handling to work properly
				this.term = this._value();

				this.close( event );
				this.selectedItem = item;
				//modify  corner 2013-7-16
				//this._e.selectHead.removeClass('ui-corner-top').addClass('ui-corner-all');
			}
		});

		this.liveRegion = $( "<span>", {
				role: "status",
				"aria-live": "polite"
			})
			.addClass( "ui-helper-hidden-accessible" )
			.insertAfter( this._e.inputText );

		// turning off autocomplete prevents the browser from remembering the
		// value when navigating through history, so we re-enable autocomplete
		// if the page is unloaded before the widget is destroyed. #7790
		this._on( this.window, {
			beforeunload: function() {
				this._e.inputText.removeAttr( "autocomplete" );
			}
		});
		//##################################################################//
		$('ul.ui-autocomplete').removeClass('ui-corner-all').addClass('ui-corner-bottom');
		$('ul.ui-autocomplete').css('border-color','#aecaf0');
		$('ul.ui-autocomplete').css('border-top','0px');
	},

	
	
	_destroy: function() {
		clearTimeout( this.searching );
		this._e.inputText
			.removeClass( "ui-autocomplete-input" )
			.removeClass("ui-corner-all")
			.removeClass("ui-widget-content")
			.removeAttr( "autocomplete" );
		
		this.menu.element.remove();
		this.liveRegion.remove();
		
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "source" ) {
			this._initSource();
		}
		if ( key === "appendTo" ) {
			this.menu.element.appendTo( this._appendTo() );
		}
		if ( key === "disabled" && value && this.xhr ) {
			this.xhr.abort();
		}
	},

	_appendTo: function() {
		var element = this.options.appendTo;

		if ( element ) {
			element = element.jquery || element.nodeType ?
				$( element ) :
				this.document.find( element ).eq( 0 );
		}

		if ( !element ) {
			element = this._e.inputText.closest( ".ui-front" );
		}

		if ( !element.length ) {
			element = this.document[0].body;
		}

		return element;
	},

	_initSource: function() {
		var array, url,
			that = this;
		//add by wdx 11/06/30
		var searchProperty=this.options.searchProperty;

		if ( $.isArray(this.options.source) ) {
			array = this.options.source;
			/*this.source = function( request, response ) {
				response( $.ui.autocomplete.filter(array,request.term) );
			};*/
			
			//modify by wdx 11/06/30
			this.source = function( request, response ) {
				response( $.ui.autocomplete.filter(array,searchProperty,request.term) );
			};
			
		} else if ( typeof this.options.source === "string" ) {
			url = this.options.source;
			this.source = function( request, response ) {
				if ( that.xhr ) {
					that.xhr.abort();
				}
				that.xhr = $.ajax({
					url: url,
					data: request,
					dataType: "json",
					success: function( data ) {
						//response( data );
						//modify by wdx 13/04/17
						response( $.ui.autocomplete.filter(data,searchProperty,request.term) );
					},
					error: function() {
						response( [] );
					}
				});
			};
		} else {
			this.source = this.options.source;
		}
	},

	_searchTimeout: function( event ) {
		clearTimeout( this.searching );
		this.searching = this._delay(function() {
			// only search if the value has changed
			if ( this.term !== this._value() ) {
				this.selectedItem = null;
				this.search( null, event );
			}
		}, this.options.delay );
	},

	search: function( value, event ) {
		value = value != null ? value : this._value();

		// always save the actual value, not the one passed as an argument
		this.term = this._value();

		if ( value.length < this.options.minLength ) {
			return this.close( event );
		}

		if ( this._trigger( "search", event ) === false ) {
			return;
		}

		return this._search( value );
	},

	_search: function( value ) {
		
		this.pending++;
		this._e.inputText.addClass( "ui-autocomplete-loading" );
		this.cancelSearch = false;

		this.source( { term: value }, this._response() );
	},

	_response: function() {
		var that = this,
			index = ++requestIndex;

		return function( content ) {
			if ( index === requestIndex ) {
				that.__response( content );
			}

			that.pending--;
			if ( !that.pending ) {
				that.element.removeClass( "ui-autocomplete-loading" );
			}
		};
	},

	__response: function( content ) {
		if ( content ) {
			content = this._normalize( content );
		}
		this._trigger( "response", null, { content: content } );
		if ( !this.options.disabled && content && content.length && !this.cancelSearch ) {
			this._suggest( content );
			this._trigger( "open" );
			this._e.selectHead.removeClass('ui-corner-all').addClass('ui-corner-top');
			
		} else {
			// use ._close() instead of .close() so we don't cancel future searches
			this._close();
		}
	},

	close: function( event ) {
		this.cancelSearch = true;
		this._close( event );
	},

	_close: function( event ) {
		if ( this.menu.element.is( ":visible" ) ) {
			this.menu.element.hide();
			//modify to  close corner
			this._e.selectHead.removeClass('ui-corner-top').addClass('ui-corner-all');
			
			this.menu.blur();
			this.isNewMenu = true;
			this._trigger( "close", event );
		}
	},

	_change: function( event ) {
		if ( this.previous !== this._value() ) {
			this._trigger( "change", event, { item: this.selectedItem } );
		}
	},

	_normalize: function( items ) {
		// assume all items have the right format when the first item is complete
		if ( items.length && items[0].label && items[0].value ) {
			return items;
		}
		return $.map( items, function( item ) {
			if ( typeof item === "string" ) {
				return {
					label: item,
					value: item
				};
			}
			return $.extend({
				label: item.label || item.value,
				value: item.value || item.label
			}, item );
		});
	},

	_suggest: function( items ) {
		var ul = this.menu.element.empty();
		this._renderMenu( ul, items );
		this.menu.refresh();

		// size and position menu
		ul.show();
		this._resizeMenu();
		ul.position( $.extend({
			of: this._e.selectHead                         //modify  position  by wangdx
		}, this.options.position ));

		if ( this.options.autoFocus ) {
			this.menu.next();
		}
	},

	_resizeMenu: function() {
		var ul = this.menu.element;
		ul.outerWidth( Math.max(
			// Firefox wraps long text (possibly a rounding bug)
			// so we add 1px to avoid the wrapping (#7513)
			ul.width( "" ).outerWidth() + 1,
			this._e.selectHead.outerWidth()               //modify    size    by  wangdx
		) );
	},

	_renderMenu: function( ul, items ) {
		var that = this;
		$.each( items, function( index, item ) {
			that._renderItemData( ul, item );
		});
	},

	_renderItemData: function( ul, item ) {
		return this._renderItem( ul, item ).data( "ui-autocomplete-item", item );
	},

	_renderItem: function( ul, item ) {
		//modify by wdx source为对象形式时 组装text value
		var searchValue=this.options.searchValue;
		if(searchValue!=""&&searchValue!=null){
			return $( "<li>" )
				.append( $( "<a>" ).text( item.label +"|"+item[searchValue]) )
				.appendTo( ul );
		}else{
			return $( "<li>" )
				.append( $( "<a>" ).text( item.label ) )
				.appendTo( ul );
		}

	},

	_move: function( direction, event ) {
		if ( !this.menu.element.is( ":visible" ) ) {
			this.search( null, event );
			return;
		}
		if ( this.menu.isFirstItem() && /^previous/.test( direction ) ||
				this.menu.isLastItem() && /^next/.test( direction ) ) {
			this._value( this.term );
			this.menu.blur();
			return;
		}
		this.menu[ direction ]( event );
	},

	widget: function() {
		return this.menu.element;
	},

	_value: function() {
		return this.valueMethod.apply( this._e.inputText, arguments );
	},

	_keyEvent: function( keyEvent, event ) {
		if ( !this.isMultiLine || this.menu.element.is( ":visible" ) ) {
			this._move( keyEvent, event );

			// prevents moving cursor to beginning/end of the text field in some browsers
			event.preventDefault();
			
		}
	}
});

$.extend( $.ui.autocomplete, {
	escapeRegex: function( value ) {
		return value.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, "\\$&");
	},
	/*filter: function(array, term) {
		var matcher = new RegExp( $.ui.autocomplete.escapeRegex(term), "i" );
		return $.grep( array, function(value) {
			return matcher.test( value.label || value.value || value );
		});
	}*/
	
	//modify by wdx 13/04/17
	filter: function(array, searchProperty, term) {
	 var matcher = new RegExp( $.ui.autocomplete.escapeRegex(term), "i" );
	
	  return $.grep( array, function(value) {
		return matcher.test(value[searchProperty]||value||value.value||value.label);
	 });
    }
	
});


// live region extension, adding a `messages` option
// NOTE: This is an experimental API. We are still investigating
// a full solution for string manipulation and internationalization.
$.widget( "ui.autocomplete", $.ui.autocomplete, {
	options: {
		messages: {
			noResults: "No search results.",
			results: function( amount ) {
				return amount + ( amount > 1 ? " results are" : " result is" ) +
					" available, use up and down arrow keys to navigate.";
			}
		}
	},

	__response: function( content ) {
		var message;
		this._superApply( arguments );
		if ( this.options.disabled || this.cancelSearch ) {
			return;
		}
		if ( content && content.length ) {
			message = this.options.messages.results( content.length );
		} else {
			message = this.options.messages.noResults;
		}
		this.liveRegion.text( message );
	}
});

}( jQuery ));/**
 * 
 */

(function( $, undefined ) {
//alert('a');

	var lastActive, startXPos, startYPos, clickDragged,
	baseClasses = "ui-button ui-widget ui-state-default ui-corner-all",
	stateClasses = "ui-state-hover ui-state-active ",
	typeClasses = "ui-button-icons-only ui-button-icon-only ui-button-text-icons ui-button-text-icon-primary ui-button-text-icon-secondary ui-button-text-only",
	formResetHandler = function() {
		var buttons = $( this ).find( ":ui-button" );
		setTimeout(function() {
			buttons.button( "refresh" );
		}, 1 );
	},
	radioGroup = function( radio ) {
		var name = radio.name,
			form = radio.form,
			radios = $( [] );
		if ( name ) {
			name = name.replace( /'/g, "\\'" );
			if ( form ) {
				radios = $( form ).find( "[name='" + name + "']" );
			} else {
				radios = $( "[name='" + name + "']", radio.ownerDocument )
					.filter(function() {
						return !this.form;
					});
			}
		}
		return radios;
	};
	
$.widget( "dtui.dtbutton", {
	version: "1.10.1",
	defaultElement: "<button>",
	options: {
		width: null,
		height: null,
		disabled: null,
		text: true,
		label: null,
		icons: {
			primary: null,
			secondary: null
		},
		//add by xychun 2013/04/27
		type:null,
		//callback
		click:null
	},
	_create: function() {
		this.element.closest( "form" )
			.unbind( "reset" + this.eventNamespace )
			.bind( "reset" + this.eventNamespace, formResetHandler );

		if ( typeof this.options.disabled !== "boolean" ) {
			this.options.disabled = !!this.element.prop( "disabled" );
		} else {
			this.element.prop( "disabled", this.options.disabled );
		}

		this._determineButtonType();
		//button元素type属性的默认值是submit，这里修改为button add by xychun 2013-10-25
		if (this.type==='button' && this.element.attr('type')==undefined){
			this.element.attr('type', 'button');
		}
		this.hasTitle = !!this.buttonElement.attr( "title" );

		var that = this,
			options = this.options,
			toggleButton = this.type === "checkbox" || this.type === "radio",
			activeClass = !toggleButton ? "ui-state-active" : "",
			focusClass = "ui-state-focus";
		//add by xychun 2013/04/27
		if (options.type){
			if(options.type.toLowerCase()=='ok'){
				options.icons.primary= "ui-icon-check";
			}
			if(options.type.toLowerCase()=='cancel'){
				options.icons.primary= "ui-icon-cancel";
			}
			if(options.type.toLowerCase()=='close'){
				options.icons.primary= "ui-icon-close";
			}
		}

		if ( options.label === null ) {
			options.label = (this.type === "input" ? this.buttonElement.val() : this.buttonElement.html());
		}

		this._hoverable( this.buttonElement );

		this.buttonElement
			.addClass( baseClasses )
			.attr( "role", "button" )
			.bind( "mouseenter" + this.eventNamespace, function() {
				if ( options.disabled ) {
					return;
				}
				if ( this === lastActive ) {
					$( this ).addClass( "ui-state-active" );
				}
			})
			.bind( "mouseleave" + this.eventNamespace, function() {
				if ( options.disabled ) {
					return;
				}
				$( this ).removeClass( activeClass );
			})
			.bind( "click" + this.eventNamespace, function( event ) {
				if ( options.disabled ) {
					event.preventDefault();
					event.stopImmediatePropagation();
				}
				//add by xychun 2013/04/27
				else {
					that._trigger('click');
				}
			});

		this.element
			.bind( "focus" + this.eventNamespace, function() {
				// no need to check disabled, focus won't be triggered anyway
				that.buttonElement.addClass( focusClass );
			})
			.bind( "blur" + this.eventNamespace, function() {
				that.buttonElement.removeClass( focusClass );
			});

		if ( toggleButton ) {
			this.element.bind( "change" + this.eventNamespace, function() {
				if ( clickDragged ) {
					return;
				}
				that.refresh();
			});
			// if mouse moves between mousedown and mouseup (drag) set clickDragged flag
			// prevents issue where button state changes but checkbox/radio checked state
			// does not in Firefox (see ticket #6970)
			this.buttonElement
				.bind( "mousedown" + this.eventNamespace, function( event ) {
					if ( options.disabled ) {
						return;
					}
					clickDragged = false;
					startXPos = event.pageX;
					startYPos = event.pageY;
				})
				.bind( "mouseup" + this.eventNamespace, function( event ) {
					if ( options.disabled ) {
						return;
					}
					if ( startXPos !== event.pageX || startYPos !== event.pageY ) {
						clickDragged = true;
					}
			});
		}

		if ( this.type === "checkbox" ) {
			this.buttonElement.bind( "click" + this.eventNamespace, function() {
				if ( options.disabled || clickDragged ) {
					return false;
				}
			});
		} else if ( this.type === "radio" ) {
			this.buttonElement.bind( "click" + this.eventNamespace, function() {
				if ( options.disabled || clickDragged ) {
					return false;
				}
				$( this ).addClass( "ui-state-active" );
				that.buttonElement.attr( "aria-pressed", "true" );

				var radio = that.element[ 0 ];
				radioGroup( radio )
					.not( radio )
					.map(function() {
						return $( this ).dtbutton( "widget" )[ 0 ];
					})
					.removeClass( "ui-state-active" )
					.attr( "aria-pressed", "false" );
			});
		} else {
			this.buttonElement
				.bind( "mousedown" + this.eventNamespace, function() {
					if ( options.disabled ) {
						return false;
					}
					$( this ).addClass( "ui-state-active" );
					lastActive = this;
					that.document.one( "mouseup", function() {
						lastActive = null;
					});
				})
				.bind( "mouseup" + this.eventNamespace, function() {
					if ( options.disabled ) {
						return false;
					}
					$( this ).removeClass( "ui-state-active" );
				})
				.bind( "keydown" + this.eventNamespace, function(event) {
					if ( options.disabled ) {
						return false;
					}
					if ( event.keyCode === $.ui.keyCode.SPACE || event.keyCode === $.ui.keyCode.ENTER ) {
						$( this ).addClass( "ui-state-active" );
					}
				})
				// see #8559, we bind to blur here in case the button element loses
				// focus between keydown and keyup, it would be left in an "active" state
				.bind( "keyup" + this.eventNamespace + " blur" + this.eventNamespace, function() {
					$( this ).removeClass( "ui-state-active" );
				});

			if ( this.buttonElement.is("a") ) {
				this.buttonElement.keyup(function(event) {
					if ( event.keyCode === $.ui.keyCode.SPACE ) {
						// TODO pass through original event correctly (just as 2nd argument doesn't work)
						$( this ).click();
					}
				});
			}
		}

		// TODO: pull out $.Widget's handling for the disabled option into
		// $.Widget.prototype._setOptionDisabled so it's easy to proxy and can
		// be overridden by individual plugins
		
		this._applyOption(); 
		this._resetButton();
	},

	_applyOption: function() {
		var o = this.options;
		this._setupDisabled(o.disabled);
		this._setupWidth(o.width);
		this._setupHeight(o.height);
	},
	
	_determineButtonType: function() {
		var ancestor, labelSelector, checked;

		if ( this.element.is("[type=checkbox]") ) {
			this.type = "checkbox";
		} else if ( this.element.is("[type=radio]") ) {
			this.type = "radio";
		} else if ( this.element.is("input") ) {
			this.type = "input";
		} else {
			this.type = "button";
		}

		if ( this.type === "checkbox" || this.type === "radio" ) {
			// we don't search against the document in case the element
			// is disconnected from the DOM
			ancestor = this.element.parents().last();
			labelSelector = "label[for='" + this.element.attr("id") + "']";
			this.buttonElement = ancestor.find( labelSelector );
			if ( !this.buttonElement.length ) {
				ancestor = ancestor.length ? ancestor.siblings() : this.element.siblings();
				this.buttonElement = ancestor.filter( labelSelector );
				if ( !this.buttonElement.length ) {
					this.buttonElement = ancestor.find( labelSelector );
				}
			}
			this.element.addClass( "ui-helper-hidden-accessible" );

			checked = this.element.is( ":checked" );
			if ( checked ) {
				this.buttonElement.addClass( "ui-state-active" );
			}
			this.buttonElement.prop( "aria-pressed", checked );
		} else {
			this.buttonElement = this.element;
		}
	},

	widget: function() {
		return this.buttonElement;
	},

	_destroy: function() {
		this.element
			.removeClass( "ui-helper-hidden-accessible" );
		this.buttonElement
			.removeClass( baseClasses + " " + stateClasses + " " + typeClasses )
			.removeAttr( "role" )
			.removeAttr( "aria-pressed" )
			.html( this.buttonElement.find(".ui-button-text").html() );

		if ( !this.hasTitle ) {
			this.buttonElement.removeAttr( "title" );
		}
	},

	_setupDisabled: function(disabled){
		if ( disabled ) {
			this.element.prop( "disabled", true );
		} else {
			this.element.prop( "disabled", false );
		}
	},

	_setupWidth: function(width){
		if ( this.type === "input" ) {
			this.element.outerWidth(width);
		}
		else {
			this.element.width(width);		
		}
	},
	_setupHeight: function(height){
		if ( this.type === "input" ) {
			this.element.outerHeight(height);
		}
		else {
			this.element.height(height);			
		}
	},	
	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			this._setupDisabled(value);
			return;
		}
		if ( key === "width") {
			this._setupWidth(value);
			return;
		}
		if ( key === "height") {
			this._setupHeight(value);
			return;
		}
		//设置label和icon
		this._resetButton();
	},

	refresh: function() {
		//See #8237 & #8828
		var isDisabled = this.element.is( "input, button" ) ? this.element.is( ":disabled" ) : this.element.hasClass( "ui-button-disabled" );

		if ( isDisabled !== this.options.disabled ) {
			this._setOption( "disabled", isDisabled );
		}
		if ( this.type === "radio" ) {
			radioGroup( this.element[0] ).each(function() {
				if ( $( this ).is( ":checked" ) ) {
					$( this ).dtbutton( "widget" )
						.addClass( "ui-state-active" )
						.attr( "aria-pressed", "true" );
				} else {
					$( this ).dtbutton( "widget" )
						.removeClass( "ui-state-active" )
						.attr( "aria-pressed", "false" );
				}
			});
		} else if ( this.type === "checkbox" ) {
			if ( this.element.is( ":checked" ) ) {
				this.buttonElement
					.addClass( "ui-state-active" )
					.attr( "aria-pressed", "true" );
			} else {
				this.buttonElement
					.removeClass( "ui-state-active" )
					.attr( "aria-pressed", "false" );
			}
		}
	},

	_resetButton: function() {
		if ( this.type === "input" ) {
			if ( this.options.label ) {
				this.element.val( this.options.label );
			}
			return;
		}
		var buttonElement = this.buttonElement.removeClass( typeClasses ),
			buttonText = $( "<span></span>", this.document[0] )
				.addClass( "ui-button-text" )
				.html( this.options.label )
				.appendTo( buttonElement.empty() )
				.text(),
			icons = this.options.icons,
			multipleIcons = icons.primary && icons.secondary,
			buttonClasses = [];

		if ( icons.primary || icons.secondary ) {
			if ( this.options.text ) {
				buttonClasses.push( "ui-button-text-icon" + ( multipleIcons ? "s" : ( icons.primary ? "-primary" : "-secondary" ) ) );
			}

			if ( icons.primary ) {
				//modify by xychun 2013/04/27 
				//buttonElement.prepend( "<span class='ui-button-icon-primary ui-icon " + icons.primary + "'></span>" );
				var reg = /^.+\.(gif|jpg|png)$/g;
				if(reg.test(icons.primary)){
				   buttonElement.prepend( "<span class='ui-button-icon-primary ui-icon' style='background-image: url("+icons.primary+");'></span>" );
			   }else{
				   buttonElement.prepend( "<span class='ui-button-icon-primary ui-icon " + icons.primary + "'></span>" );
			   }
				
			}

			if ( icons.secondary ) {
				buttonElement.append( "<span class='ui-button-icon-secondary ui-icon " + icons.secondary + "'></span>" );
			}

			if ( !this.options.text ) {
				buttonClasses.push( multipleIcons ? "ui-button-icons-only" : "ui-button-icon-only" );

				if ( !this.hasTitle ) {
					buttonElement.attr( "title", $.trim( buttonText ) );
				}
			}
		} else {
			buttonClasses.push( "ui-button-text-only" );
		}
		buttonElement.addClass( buttonClasses.join( " " ) );
	}
});

$.widget( "dtui.dtbuttonset", {
	
	version: "1.10.1",
	options: {
		items: "button, input[type=button], input[type=submit], input[type=reset], input[type=checkbox], input[type=radio], a, :data(ui-button)"
	},

	_create: function() {
		this.element.addClass( "ui-buttonset" );
	},

	_init: function() {
		this.refresh();
	},

	_setOption: function( key, value ) {
		if ( key === "disabled" ) {
			this.buttons.dtbutton( "option", key, value );
		}

		this._super( key, value );
	},

	/**
	 * 覆盖父类的同名方法，解决splitbutton.html样式显示不正确的问题，
	 * 将该方法中的buttion()该为dtbutton()
	 */
	refresh: function() {
		var rtl = this.element.css( "direction" ) === "rtl";

		this.buttons = this.element.find( this.options.items )
			.filter( ":ui-button" )
				.dtbutton( "refresh" )
			.end()
			.not( ":ui-button" )
				.dtbutton()
			.end()
			.map(function() {
				return $( this ).dtbutton( "widget" )[ 0 ];
			})
				.removeClass( "ui-corner-all ui-corner-left ui-corner-right" )
				.filter( ":first" )
					.addClass( rtl ? "ui-corner-right" : "ui-corner-left" )
				.end()
				.filter( ":last" )
					.addClass( rtl ? "ui-corner-left" : "ui-corner-right" )
				.end()
			.end();
	},
	
	_destroy: function() {
		this.element.removeClass( "ui-buttonset" );
		this.buttons
			.map(function() {
				return $( this ).dtbutton( "widget" )[ 0 ];
			})
				.removeClass( "ui-corner-left ui-corner-right" )
			.end()
			.dtbutton( "destroy" );
	}	
	
});

})( jQuery );/**
 * 
 */

(function( $, undefined ) {
//alert('a');


$.widget( "dtui.dtcheck", {
	version: "1.10.1",
	options: {
		disabled: null,
		name:'',
		text: '',
		value: '',
		checked: false,
		height: null,
		width: null,
		click:null
	},
	_create: function() {
		var self = this,
			o = this.options;
		var checkElement = $('<input type="checkbox">').attr({value:o.value, checked:o.checked, name:o.name}).bind('click', function(event){
				self._trigger('click',event);
			}),
			labelElement = $('<span></span>').text(o.text).bind('click',function(event){
				if (o.disabled) return;
				checkElement.prop('checked',!checkElement.prop('checked'));
				self._trigger('click',event);
			}),
			linkElement = $('<a href="javascript:void(0)"></a>').append(labelElement),
			spanElement = $('<span></span>').addClass('dtui-check').append(checkElement).append(linkElement);
		
		//保证单选按钮垂直居中
		this.element.addClass('dtui-check-container');
		
		this.element.append(spanElement);

		if (o.height) {
			this.element.height(o.height);
		}
		if (o.width) {
			this.element.width(o.width);
		}
	},


	widget: function() {
		return this.element;
	},
	
	value: function(v){
		if (arguments.length ===0) {
			return this.options.value;
		}
		else {
			this.options.value = v;
		}
	},
	
	check: function(checked){
		if (arguments.length ===0) {
			return $(':checkbox', this.element).prop('checked');
		}
		else {
			$(':checkbox', this.element).prop('checked', checked);
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});

$.widget( "dtui.dtcheckgroup", {
	version: "1.10.1",
	options: {
		disabled: null,
		name:'',
		required: false,
		items:[],
		col: 1,
		value: null,
		title: '',
		height: null,
		width: null,
		keyField:"id",
		valueField:"name",
		padding_left:15,
		change:null
	},
		
	_create: function() {
		var self = this,
			o = this.options;
		
		this._checkControls = [];
		this._starDiv = $.dtui.util.createStarElem(this.options.required);
		 
		var setElement = $('<fieldset></fieldset>').addClass('dtui-checkgroup-fieldset').append($('<legend></legend>').text(o.title));
		//setElement.css('float','left');
		setElement.css('margin-right', '8px');
		if ($.isArray(o.items)) {
			$.each(o.items, function(i, item){
				self._createCheck(setElement, i, item[self.options.keyField], item[self.options.valueField]);
			});
		}
		else {
			var i=0;
			$.each(o.items, function(value, text){
				self._createCheck(setElement, i++, value, text);
			});
		}
		
		this.element.addClass('dtui-checkgroup');
		this.element.append(this._starDiv);
		this.element.append(setElement);
		
		//this.element.css("display","inline-block");//整体外结构是行结构元素
		if (o.height) {
			this.element.height(o.height);
			setElement.outerHeight(o.height);
		}
		if (o.width) {
			this.element.width(o.width);
		}	
		if (o.value){
			this.value(o.value);
		}
	},

	_createCheck: function(setElement, i, value, text) {
		var self = this,
			o = this.options;
		var checkClick = function(event, ui) {
			self._trigger('change', event, $(this));
		};
		
		var checkControl = $('<div></div').addClass('dtui-checkgroup-item').prop('id', self.element.prop('id')+'_'+value).dtcheck({
			value:value,
			checked: false,
			name:o.name,
			text:text,
			click: checkClick
		});
		
		
		if ((i+1)%o.col !==1)
			checkControl.css('padding-left', o.padding_left);
		
		this._checkControls.push(checkControl);
		setElement.append(checkControl);
		if ((i+1)%o.col ===0)
			setElement.append('<p></p>');
	},

	widget: function() {
		return this.element;
	},
	
	value: function(v){
		var that = this;
		if (arguments.length ===0) {
			var result = '';
			$.each(this._checkControls, function(i, checkControl){
				if (checkControl.dtcheck('check')){
					if (result)
						result += ',';
					result += checkControl.dtcheck('value');
				}
			});
			return result;
		}
		else {
			$.each(this._checkControls, function(i, checkControl){
				checkControl.dtcheck('check',false);
			});
			if (v){
				var valueArray = v.split(',');
				$.each(valueArray, function(i, val){
					that.element.find('input[value="'+val+'"]').prop('checked', true);
				});
			}
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});


})( jQuery );/*----------------------------------------------
//文件名：                                                                              多选下拉框
//文件功能描述：                                            下拉框显示多选按钮
//             
//----------------------------------------------------*/
(function($,undefined){

	$.widget("dtui.dtcheckcombobox",$.dtui.dtabscombobox,{
		options:{
			items : [],         //可选值得数据项
			url   : ""          //数据项加载地址
		},
		
		_init: function() {
			this._super();
			
			this._genSelectionObject();
			
			if (!this.options.initValue || !this.options.initText){
				return;
			}			
			
			this._setCheckState();			
		},
		
		_genSelectionObject: function() {
			this.selectValues = this._e.valueText.val().split(",");
			this.selectTexts = this._e.inputText.val().split(",");
			if (this.selectValues.length != this.selectTexts.length){
				if(window.console) console.log('多选下拉框值和显示文本长度不一致！');
			}
		},
		
		_setCheckState: function() {
			this.inputs.prop('checked', false);
			for (var i=0; i<this.selectValues.length; i++){
				var chk = this._e.selectOption.find('input[value="'+this.selectValues[i]+'"]');
				if (chk.length > 0)
					chk.prop('checked', true);
			}
		},
		
		
		//initialize  menu
		_buildContent:function(){
			//添加校验时的标识;  组件整体标识-- 文本框标识
			this.totalContainer.addClass("dtui-checkcombobox");
			this._e.inputText.addClass("dtui-checkcombobox-text");
			if(this.options.url!=""){
				this.loadItems();
			}
			
			 var self=this;
		   	 var html='';
			 var items=this.options.items;
			 var menu=this._e.selectOption;
			 //var checkedValue=this.options.value;
			 
			 if ($.isArray(items)){
				 $.each(items,function(n,obj){
					 var value = obj[self.options.valueField];
					 var text  = obj[self.options.textField];
					 html+='<label>';
					 html += '<input type="checkbox" value="' + value + '" ';
					 html +=$.inArray(value,self.selectValues)>-1?'checked':'';
//				     html +=(',' + checkedValue + ',').search(',' + value +',') > -1 ? 'checked' : '';
					 html += ' /><span >' + text + '</span></label><br/>';
				 });
			 }
			 else {
				 $.each(items,function(n,obj){
					 var value = n;
					 var text  = obj;
					 html+='<label>';
					 html += '<input type="checkbox" value="' + value + '" ';
					 html +=$.inArray(value,self.selectValues)>-1?'checked':'';
//					 html +=(',' + checkedValue + ',').search(',' + value +',') > -1 ? 'checked' : '';
					 html += ' /><span >' + text + '</span></label><br/>';
				 });
			 }
			 menu.html(html);
		     this.labels = menu.find('label');
		     this.inputs = this.labels.children('input');
		},
		
		_refreshInputValue: function() {
			if(!this.selectValues || this.selectValues.length == 0) {
				this._e.inputText.val("");
				this._e.valueText.val("");
				return;
			}
			this._e.inputText.val(this.selectTexts.join(","));
			this._e.valueText.val(this.selectValues.join(","));
		},
		
		_processCheckBoxChange: function($chk) {
			var text=$chk.next('span').text();
			var value=$chk.val();
			var checked = !!$chk.prop('checked');
			if (checked) {
				this.selectValues.push(value);
				this.selectTexts.push(text);
			}
			else {
				var index = $.inArray(value, this.selectValues);
				this.selectValues.splice(index, 1);
				this.selectTexts.splice(index, 1);
			}
			this._refreshInputValue();
			this._trigger("change");
		},
		
		//binds checkbox events
		_bindEvent:function(){
			this._super();
			var self=this;
			var $input=self.inputs;
			/*checkbox  Event*/
			$($input).bind("click",function(){
				self._processCheckBoxChange($(this));
			 });

		},
		//after addItem  and removeItem rebuild menu  and rebinds checkbox  event
		refresh:function(){
			var self=this;
			 this._buildContent();
			 $(self.inputs).bind("click",function(){
				 self._processCheckBoxChange($(this));
			 });
		},
		
		//use url way to load  Items
		loadItems:function(){
			var self=this;
			$.ajax({
		        url:self.options.url,
		        type:'post',
		        async:false,
		        //dataType: "json",
		        success:function (data, status)  
		        {
		        	self.options.items=data;
		        },
		        error:function (data, status, e)
		        {
		            //alert(e);
		        }
		    }); 
		},
		
		//add menu Item(参数格式{value:"14",text:"乒乓球"}或{"14":"乒乓球"})
		addItem:function(item){
			var self=this;
			var length=self.options.items.length;
			if ($.isArray(self.options.items)){
				self.options.items[length]=item;
			}else{
				self.options.items=jQuery.extend(self.options.items,item);
			}
			self.refresh();
		},
		
		//remove menu Item(参数格式{value:"14",text:"乒乓球"}或{"14":"乒乓球"})
		removeItem:function(item){
			var self=this;
			var arr=self.options.items;
			if($.isArray(arr)){
				self.options.items=[];
				var i=0;
				$.each(arr,function(n,obj){
					if(item[self.options.valueField]!=obj[self.options.valueField]){
						self.options.items[i++]=obj;
					}
				});
			}else{
				$.each(item,function(n,obj){
					delete self.options.items[n];
				});
			}
			/*//当删除的值 是 初始化的值时 需要重新初始化
			var k=0;
			$.each(self.options.initValue,function(n,obj){
				if(item[self.options.valueField]!=obj){
					self.options.initValue[k++]=obj;
					self.options.initValue[k++]=self.options.initText[n];
				}
			});*/
//			self._initHead();
			var index  = $.inArray(item[self.options.valueField], this.selectValues); 
			if (index >= 0) {
				this.selectValues.splice(index, 1);
				this.selectTexts.splice(index, 1);
			}
			self.refresh();
			//self.rebuildContent();
		},
		
        /**
         * 根据当前的值刷新内容区域的选择状态
         */
        _refreshContentState: function() {
			//使valueText和inputText中的内容与selection对象的内容保持一致
			this._genSelectionObject();
        	this._setCheckState();
        },
        
        /**
         * 根据值获取对应的文本
         * @param value
         */
        _getText: function(value){
        	if (!value){
        		return "";
        	}
        	var valueArr = value.split(this.options.displaySplitChar),
        		textArr = [];
        	
			for (var i=0; i<valueArr.length; i++){
				var $chk = this._e.selectOption.find('input[value="'+valueArr[i]+'"]');
				var text = '';
				if ($chk.length > 0)
					text = $chk.next('span').text();
				textArr.push(text);
			}
        	
        	return textArr.join(this.options.displaySplitChar);
        }
	});
})(jQuery);/*!
 * 
 * 
 */
(function( $, undefined ) {

	var serializeArray  = $.fn.serializeArray;
	$.fn.serializeArray = function(){
		this.trigger('dt-form-pre-serialize');
		return serializeArray.call(this);
	};
	
var 
	slice = Array.prototype.slice;

$.widget('dtui.dtckeditor', {
	version:'1.0.1',
	options: {
		autoUpdateElement: true,
		baseFloatZIndex: 10000,
		baseHref: '',
		bodyClass: '',
		bodyId: '', 
		browserContextMenuOnCtrl: true,	//内容区上下文菜单是否显示
		clipboard_defaultContentType: 'html', //剪贴板默认格式，html 或者是 text
		contentsCss: CKEDITOR.basePath + 'contents.css', //内容区样式
		customConfig: CKEDITOR.basePath + 'config.js',		//自定义config文件地址
		devtools_styles: '<!DOCTYPE html>',			//开发工具样式
		enterMode: CKEDITOR.ENTER_BR, // 回车键模式 取值：CKEDITOR.ENTER_P ,CKEDITOR.ENTER_BR ,CKEDITOR.ENTER_DIV (3)
		devtools_textCallback:  false,   //返回一个字符串，显示在开发者工具中
		docType: '',
		filebrowserBrowseUrl: false, //文件浏览url，如：browse.jsp
	    filebrowserUploadUrl: false, //文件上传url，如：upload.jsp
	    filebrowserWindowHeight: '70%',
	    filebrowserWindowWidth: '80%',
	    htmlEncodeOutput: false,
	    newpage_html: '<p>请输入内容</p>',
	    readOnly: false,
	    resize_dir: 'vertical',   //调整大小的方向，取值：both, vertical,和 horizontal.
	    resize_enabled: false,
	    
	    toolbar: 'Basic', //设置工具条， 字符串或者是数组
	    /*使用数组设置
		 *		toolbar: [
		 *			[ 'Source', '-', 'Bold', 'Italic' ]
		 *		]
		 * 
		 *使用字符串设置
		 *      先定义一个名称
		 *	 	toolbar_Basic: [
		 *			[ 'Source', '-', 'Bold', 'Italic' ]
		 *		],
		 *      使用名称定义
		 * 		toolbar: 'Basic'
		 */
		
		toolbarGroups:'',
		/*设置例子
		toolbarGroups = [
		    { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
		    { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		    { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		    { name: 'forms' },
		    '/',
		    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
		    { name: 'links' },
		    { name: 'insert' },
		    '/',
		    { name: 'styles' },
		    { name: 'colors' },
		    { name: 'tools' },
		    { name: 'others' },
		    { name: 'about' }
		]
		*/
		toolbarLocation: 'top', //工具条的位置 top 或者是 bottom
    	uiColor: '',
    	templates_replaceContent: false,
    	disableAutoInline: false,		//默认为false，为true时，editor为行内元素
    	required: false,
    	height: '100px',				//高度，单位像素或者是百分比
    	width: '100%'					//宽度，单位像素或者是百分比
	},
	_create: function(){
		//在element外增加新的包装层，以便添加starElem
		this.element.addClass('ui-CKediter');
		this.element.wrap("<div style='width:"+this.options.width+";display:inline-block'></div>");
		this.wrapper = this.element.parent();
		
		var element = this.element[0],
			options = this.options,
			editor = CKEDITOR.replace(element, options),
			$element = $(element);
		
		if (this.options.name)
			this.element.find('textarea').attr('name', this.options.name);
		
		this.editor = editor;
		
		var onSubmit = function(){
			editor.updateElement();
		};

		$element.parents( 'form' ).submit( onSubmit );
		$element.parents( 'form' ).bind( 'dt-form-pre-serialize', onSubmit );

		$element.bind( 'destroy.ckeditor', function()		{
			$element.parents( 'form' ).unbind( 'submit', onSubmit );
			$element.parents( 'form' ).unbind( 'form-pre-serialize', onSubmit );
		});
		
		var that = this;
		function _createStarElem() {
			that._starDiv = $.dtui.util.createStarElem(that.options.required);
			//原ckeditor的最外层
			var $cke = $('#cke_'+that.element[0].id);
			$cke.removeAttr('style');
			//设置为左浮动
			//$('<div></div>').append(that._starDiv).insertBefore($cke).css('float','right');
			that._starDiv.insertBefore($cke);
			
			//if(that.options.required){
				$cke.css('margin-right','8px');
			//}
		}
		//ckeditor控件的创建过程似乎是异步执行，如果不使用延迟，会出错
		setTimeout(_createStarElem, 2000);		
	},
	_destroy: function(){
		
	},
	_callMethod: function(action){
		var
			input = slice.call( arguments, 1 ),
			editor = this.editor;
		return editor[action].apply(editor, input);
	},
	getData: function(){
		return this._callMethod('getData');
	},
	setData: function(data, callback){
		this._callMethod('setData', data, callback);
	},
	//根据option的name返回options的值
	getOption: function(name){
		return this.editor.config[name];
	},
	setReadOnly: function(isReadOnly){
		this._callMethod('setReadOnly', isReadOnly);
	},
	/**
	 * mode取值： html  unfiltered_html 或者 text
	 * 
	 */
	insertHtml: function(html, mode){
		this._callMethod('insertHtml', html, mode);
	},
	resize: function(width, height){
		this._callMethod('resize', width, height);
	},
	updateElement: function(){
		this._callMethod('updateElement');
	}
});

})( jQuery );
(function($,undefined){
	var $doc = $(document);
	$.widget("dtui.dtcombobox", {
		options: {
			label:"",
			required:false,
	    	width:'150px',
	    	height:'18px',
			//id: "",                               //valueText 隐藏域 ID
			name: "",                             //valueText 隐藏域name
			url: "",                              //url
			datasource: null,                     //数据源
			keyField: "",                          //                      
			valueField: "",                        //
			dataKeyProp: "option_key",				//存储在option元素上数据对象的key属性
			dataValueProp: "option_value",			//存储在option元素上的数据对象的value属性
			selectIndex: -1,                         //
			selectValue: '',                        //
			hasAll: false,                        //
			allKey: "all",                        //
			allValue: "--全部--",                 //
			hasNull: false,                       //
			nullKey: "",                           //
			nullValue: "--请选择--",                //
			relaId: "",
			refreshUrl: "",                         
			sourceTargetId: "",
			readonly: false,
			disabled: false,
			paramkey: "",
			dropPanelHeight: '200px',
			prompt:"下拉框",
			//_starWidth: '8px',	//星号div的宽度，内部使用 		
			onChangeCallBack: function(){},
			onChange: function(){}
		},
		_create: function(){
			this.element.addClass('ui-combobox ui-link-combobox');
			this._createParam();
			if(this.options.url){
				this._getDatasource(this.options);
			}
			if(!this.options["width"]){
				this.options["width"] = 120;
			}
			
			//支持百分比;
			//把百分比 数字转换成像素
			//this.options.width=this.element.width(this.options["width"]).width();
			//this.element.width(this.options.width+2);
			
			this._createElement();
			this._buildHead();
			this._buildContent();
			this._processChange();
			this._setupDisabled(this.options.disabled);
		},
		/**
		 * 根据options的其它参数生成options.param
		 * 移植1.0版的代码，有待优化
		 */
		_createParam: function(){
			var o = this.options;
	    	if(!o.datasource && (o.refreshUrl || o.url)){
				o.url = o.refreshUrl ? o.refreshUrl : o.url;
				if(o.sourceTargetId && $('#'+o.sourceTargetId).length>0){
					var param = {};
					var key = o.sourceTargetId;
					if(o.paramkey){
						key = o.paramkey;
					}
					param[key] = $('#'+o.sourceTargetId).dtcombobox('selectValue');
					o.param = param;
				}
	    	}			
		},
		
		/**
		 * 处理change事件，实现联动
		 * 移植1.0版的代码，有待优化
		 */
		_processChange: function(){
			var that = this,
				id = this.element.attr('id');
	    	if(this.options.relaId){
	    		var relaBoxs = this.options.relaId.split(",");
	    		this.options["onChangeCallBack"] = function(){
	    			//alert("callback"+that.options.refreshUrl+"sss"+that.options.url);
	    			var param = {};
	    			param[id] = that.val();
	    			$.each(relaBoxs, function(i, value){
	    				var relaCombobox = $('#'+value); 
	    				if(relaCombobox.length>0){
	    					var paramkey = relaCombobox.dtcombobox('option','paramkey');
	    					if (paramkey)
	    						param[paramkey] =  that.val();
	    					relaCombobox.dtcombobox('refresh', {param: param});
	    				}
	    			});
	    		};
	    	}
		},
		
		/**
		 * 创建构成元素
		 * selectOption:下拉框
		 * inputText:显示框
		 * valueText:隐藏域，用于存放键值
		 * selectBtn:下拉按钮
		 * selectHead:下拉框初始显示部分
		 */
		_createElement: function(){
			var self=this,
				defaultValue = '',
				defaultText = '',
				id = this.element.attr('id');
			if (this.options.selectValue) {
				defaultValue = this.options.selectValue;
				defaultText = this._getTextByValue(defaultValue);
			}
			else if(self.options.selectIndex==-1&&self.options.hasNull==true){
				defaultValue=this.options.nullKey;
				defaultText=this.options.nullValue;
			}else if(self.options.selectIndex==-2&&self.options.hasAll==true){
				defaultValue=this.options.allKey;
				defaultText=this.options.allValue;
			}
			else {
				if (this.options.selectIndex < 0) {
					this.options.selectIndex = 0;
				}
				var o = this._getObjectByIndex(this.options.selectIndex);
				if (o){
					defaultValue=o[this.options.keyField];
					defaultText=o[this.options.valueField];
				}
			}
			this._e = {
					selectOption: $('<div class="ui-combobox-select  ui-corner-bottom"><table cellpadding="0" cellspacing="0" border="0" class="ui-combobox-select-inner"></table></div>'),
					inputText: $('<input id="'+id+'_comboboxinput" class="ui-combobox-text ui-corner-left" readonly="readonly" type="text" value="'+defaultText+'"/>'),
					valueText: $('<input  value="'+defaultValue+'" type="text" class="ui-combobox-hidden"/>'),
					selectBtn: $('<a class="ui-icon ui-icon-triangle-1-s ui-corner-right"></a>'),
					selectHead: $('<div class="ui-combobox-head ui-corner-all" style="vertical-align:middle;"></div>')
				};
		},
		/**
		 * 构建下拉框初始显示部分及绑定相应事件
		 */
		_buildHead: function(){
			var self = this;
			
			this._e.inputText.addClass("input-init");
			//this.element.hide();
			this._e.selectHead.css({"margin":"0 auto"})
							.insertAfter(this.element);
			//$console.log('initheight:'+this.options.height);
			//$console.log('realheight:'+this._e.selectHead.css('height'));
			var divpadding=$("<div></div>").addClass("input-rightpadding").append(this._e.selectBtn).appendTo(this._e.selectHead);
			var divinput=$("<div></div>").addClass("input-Container").append(this._e.inputText).appendTo(this._e.selectHead)
							.addClass('ui-corner-all').addClass('ui-widget-content');                       //set corner all
			divinput.css("margin-right",divpadding.outerWidth()||16);
			
			//再利用公共属性后    总体样式调整
			divinput.css("border-width","0px");
			//值域隐藏
			this._e.valueText.appendTo(this._e.selectHead);
			//this._e.valueText.attr("id", this.options.id);
			this.options.name ? null : this.options.name = this.element.attr('id');
			this._e.valueText.attr("name", this.options.name);	
			
			this._starDiv = $.dtui.util.createStarElem(this.options.required);
			this._starElem = this._starDiv.find('span.input-star');
			
			//this.element.after(this._starElem);
			//添加 控件名字
			$("<label></label>").text(this.options.label).insertBefore(this.element);
			var totalWidth=this._e.selectHead.outerWidth();
			this.options.required?totalWidth+=this._starDiv.outerWidth():"";
			//var container=this.element.css({"height":this._e.selectHead.outerHeight(),"width":totalWidth,"margin":"0 auto 2 auto","display":"inline-block"});
			
			var elemWidth = this.options.width;
			
			/*
			if (this.options.required){
				if ($.isNumeric(elemWidth)) {
					elemWidth += this.options._starWidth;
				}
				else {
					elemWidth = elemWidth.substr(0, elemWidth.indexOf('px'));
					elemWidth += this.options._starWidth;
				}
			}
			*/
			
			var container=this.element.css({"height":this.options.height,"width":elemWidth,"margin":"0 auto 2 auto","display":"inline-block","overflow":"visible"});
			
			this._starDiv.appendTo(container);
				//.css("padding-top",this.options.height/2);//set star middle
			var element_input=$("<div></div>").append(this._e.selectHead).appendTo(container);   //set corner all
			
			//if(this.options.required)
			//$console.log("_starDivWidth:"+this._starDiv.width());
				element_input.css("margin-right",this._starDiv.width());
			
			this._e.inputText.click(function() {
				if (self.options.readonly){
					return false;
				}
				self._toggleSelectBox(!self._e.selectOption.is(":visible"));  //关的 返回真
            });
            if(this.options.readonly !== "true" && this.options.readonly !== true){
            	/*不支持按键搜索
            	this._e.inputText.keyup(function(event){
                	//回车事件
                	if(event.keyCode === 13){
                		var result = self._search($(this).val());
                		if(result.length == 1){
                			self._setSelected(result[0]);
                		}
                	}else{//搜索事件
                		self._search($(this).val());
                	}
                });
            	*/
			};
			this._e.selectBtn.click(function() {
				if ($(this).prop('disabled') || self.options.readonly)
					return false;
				clearTimeout(self.closing);//add by whc 2012/11/21
				$('td', self._e.selectOption).show();
				self._e.inputText.focus();//add by whc 2012/11/21
				self._toggleSelectBox(!self._e.selectOption.is(":visible"));
            });
			
			//处理是否可选
			/*
			if(this.options.readonly=="true"||this.options.readonly == true){
				this._e.inputText.unbind('click');
				this._e.selectBtn.unbind('click');
			}
			*/
			
			//add  by wangdexing  2013/4/23  aim : click blank area close selectOption
			$doc.click(function(event){
		        var target = event.target;
		        if (!$.contains(self._e.selectHead[0], target)
		            && !$.contains(self._e.selectOption[0], target)
		            && target !== self._e.selectHead
		            && target !== self._e.selectOption){
		        		self._hideOption();
		           }
			});
			
			
			//add focusout by whc 2012/11/20
			/* 增加该事件处理后，点击滚动条将隐藏下拉框
			this._e.inputText.bind("focusout",function() {
				self.closing = setTimeout(function() {
					self._toggleSelectBox(false);
				}, 300 );
            });
			*/
		},
		
		/**
		 * 构建下拉列表内容
		 */
		_buildContent: function(){
			var self = this;
			//用于标识 组件的输入框 便于校验    慎重修改
			this._e.inputText.addClass("dtui-combobox");
			//this._e.selectOption.width(this.options["width"]);
			this._e.selectOption.width(this._e.selectHead.width());
			//this._e.selectOption.css("margin-right",this._starDiv.outerWidth());
			this._e.selectOption.css("margin-right",this._starDiv.width());
			
			this._e.selectOption.height('auto');
			if(this.options["dropPanelHeight"]){
				//this._e.selectOption.height(this.options["height"]);
				this._e.selectOption.css('maxHeight', this.options["dropPanelHeight"]);
				
			}
			this._e.selectOption_table = $("table:first", this._e.selectOption);
			this._clearContent();
			//处理空选项
			if(this.options.hasNull == true){
				var nullData = {};
				if(this.options.nullKey != ""){
					nullData[this.options.dataKeyProp] = this.options.nullKey;
				}else{
					nullData[this.options.dataKeyProp] = "";
				}
				nullData[this.options.dataValueProp] = this.options.nullValue;
				var option = this._buildOption(-1, nullData);
				option.appendTo(this._e.selectOption_table);
			}
			//处理全部选项
			if(this.options.hasAll == true){
				var allData = {};
				if(this.options.allKey != ""){
					allData[this.options.dataKeyProp] = this.options.allKey;
				}else{
					allData[this.options.dataKeyProp] = "";
				}
				allData[this.options.dataValueProp] = this.options.allValue;
				var option = this._buildOption(-2, allData);
				option.appendTo(this._e.selectOption_table);
			}
			if($.isArray(this.options.datasource) || $.isPlainObject(this.options.datasource)){
				var index = 0;
				$.each(this.options.datasource, function(i, prop){
					var data = prop;
					if($.isPlainObject(self.options.datasource)){
						data = {};
						data[self.options.dataValueProp] = prop;
						data[self.options.dataKeyProp] = i;
					}
					else {
						//为data对象添加属性
						data[self.options.dataKeyProp] = data[self.options.keyField];
						data[self.options.dataValueProp] = data[self.options.valueField];
					}
					var option = self._buildOption(index++, data);
					option.appendTo(self._e.selectOption_table);
				});
			};
			this._e.selectOption.appendTo(this.element);
			/*this._e.selectOption.bind("mouseenter", function(){
				self._showOption();
			}).bind("mouseleave", function(){
				self._hideOption();
			});*/

			if(this.options.selectValue){
				this.selectedByValue(this.options.selectValue, false);
				if($.isFunction(self.options["onChangeCallBack"])){
        			self.options["onChangeCallBack"].call(this);
        		}
			}else {
				this.selectedByIndex(this.options.selectIndex, false);
				if($.isFunction(self.options["onChangeCallBack"])){
        			self.options["onChangeCallBack"].call(this);
        		}
			}
		},
		
		/**
		 * 根据值获取对应的文本
		 * @param value
		 * @returns
		 */
		_getTextByValue: function(value){
			var ds = this.options.datasource;
			if ($.isPlainObject(ds)){
				return ds[value];
			}
			else if($.isArray(ds)){
				for (var i=0; i<ds.length; i++){
					if (ds[i][this.options.keyField] == value){
						return ds[i][this.options.valueField];
					}
				}
			}
			return '';
		},
		_getObjectByIndex: function(index){
			var that = this;
			var result = null;
			var ds = this.options.datasource;
			if ($.isPlainObject(ds)){
				var i=0;
				$.each(ds, function(key, value){
					if (i==index){
						result = {};
						result[that.options.keyField] = key;
						result[that.options.valueField] = value;
						return false;
					}
					i++;
				});
			}
			else if($.isArray(ds)){
				if (index >=0 && index < ds.length){
					result = ds[index];
				}
			}
			return result;
		},
		/**
		 * 构建下拉列表某一项
		 * @param index
		 * @param optionData
		 * @returns
		 */
		_buildOption: function(index, optionData){
			var self = this, option = $("<tr><td class='td' index='" + index + "' KeyValue='"+optionData[this.options.dataKeyProp]+"'>" + optionData[this.options.dataValueProp] + "</td></tr>");
			var td = $("td", option);
			td.attr('title', optionData[this.options.dataValueProp]);
			td.data("data", optionData);
			td.hover(function() {
				$(".ui-combobox-option-select", self._e.selectOption).removeClass("ui-combobox-option-select");
				$(".ui-combobox-option-over", self._e.selectOption).removeClass("ui-combobox-option-over");
                $(this).addClass("ui-combobox-option-over");
            }, function() {
                $(this).removeClass("ui-combobox-option-over");
            }).click(function(){
            	clearTimeout(self.closing);//add by whc  2012/11/22
            	if(self._e.inputText.val() != $(this).data("data")[self.options.dataValueProp]){
            		self._setSelected($(this), false);
            		if($.isFunction(self.options["onChange"])){// onchange 方法和  onChangeCallBack方法交换位置 达到先执行onchange在执行回调
                		//self.options["onChange"].call(self);
                		self._trigger('onChange');
                	}if($.isFunction(self.options["onChangeCallBack"])){
            			self.options["onChangeCallBack"].call(this);
            		}
            		else if(self.options["onChange"] && typeof self.options["onChange"] === "string"){
                		new Function("return " + self.options["onChange"] + "();").call(self);
                	}
            		
            		//add by wangdexing  2013/4/24  rebuild Sublist
//            		if(self.options.relaId!==""||self.options.relaId!=null){
//                		var check_value=$(this).attr("KeyValue");
//                		$('#'+self.options.relaId+'_comboboxdiv').dtcombobox('refresh',check_value);
//                	}
            	}else{
            		self._e.inputText.val($(this).data("data")[self.options.dataValueProp]);
        			self._e.valueText.val($(this).data("data")[self.options.dataKeyProp]);
        			self._e.valueText.data("data", $(this).data("data"));
        			self._e.valueText.data("selectItem", $(this));
            		self._hideOption();
            	}
            });
			return option;
		},
		/**
		 * 清空下拉列表内容
		 */
		_clearContent: function() {
			this._e.selectOption_table.html("");
			this._e.inputText.val("");
			this._e.valueText.val("");
        },
        /**
         * 显示/隐藏下拉列表
         * @param isHide
         */
        _toggleSelectBox: function(isHide) {
        	if (isHide) {
        		this._showOption();
        	}else{
        		this._hideOption();
        	}
        },
        /**
         * 显示下拉列表
         */
        _showOption: function(){
        	this._e.selectHead.removeClass("ui-corner-all").addClass("ui-corner-top");
    		
    		this._e.selectOption.addClass('ui-combobox-content');
			this._e.selectOption.width(this._e.selectHead.width());
   		
        	clearTimeout(self.closing);// add by whc 2012/11/21
        	if(this._e.valueText.data("selectItem")){
        		this._e.valueText.data("selectItem").addClass("ui-combobox-option-select");
        	}
        	
        	//if (this.options.width && this.options.width.indexOf('%')>=0){
        		//this._e.selectOption.width(this._e.selectHead.width());
        	//}
        	
        	this._e.selectOption.width(this._e.selectHead.width());
        	
        	this._e.selectOption.show();
        	this._e.selectOption.position({
				of: this.element,
				my: "left top",
				at: "left bottom",
				collision: "flipfit"
			});
        },
        /**
         * 隐藏下拉列表
         */
        _hideOption: function(){
        	this._e.selectHead.removeClass("ui-corner-top").addClass("ui-corner-all");
        	
        	clearTimeout(self.closing);// add by whc 2012/11/21
        	this._e.selectOption.hide();
        },
        /**
         * 获取并设定构成下拉列表的数据
         * @param prop
         */
        _getDatasource: function(prop){
        	var self=this;
        	//var url = this.options.url;//返回url值
        	//alert(url);
        	$.ajax({
				url:prop.url,
				type:'post',
				async:false,
				dataType:"JSON",
				data: prop.param,
				success:function(datasource){
        		   /* $.each(datasource,function(i,arr){
        		    	//alert(arr[self.options.keyField]);
        		    	//alert(arr[self.options.valueField]);
        		    });*/
					prop.datasource=datasource;
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					/*alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                    alert(errorThrown);*/
				}
			});
        },
        
        /**
         * 重新加载下拉列表
         * @param option
         */
       /* refresh: function(option){
        	if(option){
        		if(option.param){
        			this.options.selectValue="";//add by whc 2011/11/25
            		var url = this.options.refreshUrl ? this.options.refreshUrl : this.options.url;
            		this.options.url = url;
            		this.options.param = option.param;
            		this._getDatasource(this.options);
            	}else if(option.datasource){
            		this.options.datasource=option.datasource;
            	}
        	}
        	this._buildContent();
        },*/
        
        //alter by wangdexing by 2013/4/24
        refresh: function(option){
        	/*
        	if(option!=""||option!=null){
        		if(this.options.refreshUrl!=""||this.options.refreshUrl!=null){
        			this.options.selectValue="";//add by whc 2011/11/25
        			var url=this.options.refreshUrl+"?id="+option;
        			//alert(url);
        			this.options.url = url;
        			this._getDatasource(this.options);
        			this._buildContent();
        		}
            }
        	*/
        	//alert("alert"+this.options.refreshUrl+"ddddddddd"+this.options.url);
        	if(option){
        		if(option.param){
        			this.options.selectValue="";//add by whc 2011/11/25
            		var url = this.options.refreshUrl ? this.options.refreshUrl : this.options.url;
            		//alert("sssssssssss"+url);
            		this.options.url = url;
            		this.options.param = option.param;
            		this._getDatasource(this.options);
            	}else if(option.datasource){
            		this.options.datasource=option.datasource;
            	}
        	}
        	this._buildContent();        	
        },
        
        /**
         * 选定下拉列表某一项设定显示框的值
         */
        onChange: function(){
        	if(this._e.valueText.data("selectItem")){
        		this._setSelected(this._e.valueText.data("selectItem"));
        	}
        },
        /**
         * 处理选定值
         * @param item
         * @param isTriggerClick
         */
        _setSelected: function(item, isTriggerClick){
        	if (item.hasClass("ui-combobox-option-over")) {
        		this._hideOption();
            }
        	$(".ui-combobox-option-over", this._e.selectOption).removeClass("ui-combobox-option-over");
        	$(".ui-combobox-option-select", this._e.selectOption).removeClass("ui-combobox-option-select");
        	item.addClass("ui-combobox-option-over").addClass("ui-combobox-option-select");
        	if(isTriggerClick !== false){
        		item.trigger("click");
        	}else{
        		this._e.inputText.val(item.data("data")[this.options.dataValueProp]);
        		this._e.valueText.val(item.data("data")[this.options.dataKeyProp]);
        		this._e.valueText.data("data", item.data("data"));
        		this._e.valueText.data("selectItem", item);
        		this._hideOption();
        	}
        },
        /**
         * 依据下拉列表项的位置设定值 新版本中用selectIndex方法代替
         * @param index
         * @param isTriggerClick
         */
        selectedByIndex: function(index, isTriggerClick){
        	var self = this;
        	$('td', this._e.selectOption).each(function() {
        		if(index == $(this).attr('index')){
        			self._setSelected($(this), isTriggerClick);
        		}
        	});
        },
        /**
         * 依据下拉列表项的键值设定显示框的值 新版本中用selectValue方法代替
         * @param value
         * @param isTriggerClick
         */
        selectedByValue: function(value, isTriggerClick){
        	var self = this;
        	$('td', this._e.selectOption).each(function() {
        		if(value == $(this).data("data")[self.options.dataKeyProp]){
        			self._setSelected($(this), isTriggerClick);
        		}
        	});
        },
        /**
         * 依据下拉列表项的文本设定显示框的值 新版本中用selectText方法代替
         * @param text
         * @param isTriggerClick
         */
        selectedByText: function(text, isTriggerClick){
        	var self = this;
        	$('td', this._e.selectOption).each(function() {
        		if(text == $(this).data("data")[self.options.dataValueProp]){
        			self._setSelected($(this), isTriggerClick);
        		}
        	});
        },
        /**
         * 获取/设定选中的值 新版本中用selectValue方法代替
         * @param value
         * @returns
         */
        val: function(value){
        	if(value){
        		this.selectedByValue(value);
        	}else{
        		if(this._e.valueText.data("data")){
        			return this._e.valueText.data("data")[this.options.dataKeyProp];
        		}
//        		var selectedItem = $(".ui-combobox-option-select", this._e.selectOption);
//            	if(selectedItem && selectedItem.length == 1){
//            		return selectedItem.data("data")[this.options.dataKeyProp];
//            	}
            	return "";
        	}
        },
        /**
         * 获取选中的文本 新版本中用selectText方法代替
         * @returns
         */
        text: function(){
        	if(this._e.valueText.data("data")){
    			return this._e.valueText.data("data")[this.options.dataValueProp];
    		}
        	return null;
        },
        /**
         * 获取选中的数据
         * @returns
         */
        data: function(){
        	if(this._e.valueText.data("data")){
    			return this._e.valueText.data("data");
    		}
//        	var selectedItem = $(".ui-combobox-option-select", this._e.selectOption);
//        	if(selectedItem && selectedItem.length == 1){
//        		return selectedItem.data("data");
//        	}
        	return null;
        },
        /**
         * 搜索符合条件的列表项
         * @param text
         * @returns {Array}
         */
        _search: function(text){
        	var result = [], self = this;
        	$('td', this._e.selectOption).each(function() {                              //下拉列表 过滤td  [td 集合]
        		if(!$.trim(text)){                                                       //text不为空则显示下拉列表
        			$(this).show();
        		}else{
        			$(this).hide();
        		}
        		if($(this).data("data")[self.options.dataValueProp].indexOf(text) >= 0){
        			result.push($(this));                                                
        			$(this).show();
        		}
        	});
        	return result;                                                                   //返回 td集合
        },
        
        selectValue: function(value){
        	if(value){
        		this.selectedByValue(value);
        	}else{
        		/*if(this._e.valueText.data("data")){
        			return this._e.valueText.data("data")[this.options.dataKeyProp];
        		}
            	return "";*/
        		return this._e.valueText.val();
        	}
        	
        },
        selectIndex: function(index){
        	var self=this;
        	var Item=this._e.valueText.data("selectItem");//选中的行
        	if (typeof(index) == 'number')
        		this.selectedByIndex(index);
        	else {
        		if (Item){
        			if(Item.data("data")[self.options.dataValueProp]!=self._e.inputText.val()){
        			     return  self.options.selectIndex;
        			}
        			else{
        				 return this._e.valueText.data("selectItem").attr('index');
        			}
        		}else{
        				 return self.options.selectIndex;
        		}
        	}
        },
        selectText: function(text){
        	if (text){
        		this.selectedByText(text);
        	}
        	else {
        		/*if(this._e.valueText.data("data")){
        			return this._e.valueText.data("data")[this.options.dataValueProp];
        		}
        		return null;*/
        		return this._e.inputText.val();
        	}
        },
        widget: function() {
        	return this.element;
        },
		_setupDisabled: function(disabled){
			if ( disabled ) {
				$('*', this.element).prop( "disabled", true );
				//this.element.prop( "disabled", true );
			} else {
				//this.element.prop( "disabled", false );
				$('*', this.element).prop( "disabled", false );
			}
		},

		_setupWidth: function(width){
			//this._e.selectHead.width(width);
			//this._e.selectOption.width(width);
			this.element.width(width);
		},
		_setupHeight: function(height){
			this.elementheight(height);
		},
		_setupDropPanelHeight: function(height){
			this._e.selectOption.height(height);
		},
		_setupRequired: function(required){
			if(required){
				this._starElem.show();
			}
			else {
				this._starElem.hide();
			}
		},
		
		_setOption : function(key, value) {
			this._super(key, value);
			if (key === "disabled") {
				this._setupDisabled(value);
				return;
			}
			if (key === "width") {
				this._setupWidth(value);
				return;
			}
			if (key === "height") {
				this._setupHeight(value);
				return;
			}
			if (key === "dropPanelHeight") {
				this._setupDropPanelHeight(value);
				return;
			}
			if (key === "required") {
				this._setupRequired(value);
				return;
			}
		},
		setRefreshUrl:function(url){
			this.options.refreshUrl=url;
		}

	});
})(jQuery);/**
 * 下拉框(原来的 没有改动) 与html明显区别 可以接收 json 数据(key,value)
 */
(function($, undefined) {
	$.widget("dtui.dtcombox", {
		options : {
			width : null,
			height : null,
			name: null,
			datasource : null,
			url : '',
			keyField : '',
			valueField : '',
			selectIndex : '',
			selectValue : '',
			hasAll : false,
			allKey : '',
			allValue : '--全部--',
			hasNull : false,
			nullKey : '',
			nullValue : '--请选择--',
			onChange : ''
		},
		_create : function() {
			if (this.options.datasource){
				this._createOptionByDatasource();
			}
			else if (this.options.url){
				this._getDatasourceByUrl();	
				this._createOptionByDatasource();
			}

			if (this.options.onChange != null && this.options.onChange != "") {
				this.element.bind("change", this.options.onChange);
			}
			
			if (this.options.name){
				this.element.attr('name', this.options.name);
			}
			else {
				this.options.name = this.element.attr('name');
			}
			this._applyOption();
		},
		_init: function(){
			if (this.options.selectIndex != null
					&& this.options.selectIndex != "") {
				this.selectIndex(this.options.selectIndex);
			}
			if (this.options.selectValue != null
					&& this.options.selectValue != "") {
				this.selectValue(this.options.selectValue);
			}
		},
		_applyOption: function() {
			var o = this.options;
			this._setupDisabled(o.disabled);
			this._setupWidth(o.width);
			this._setupHeight(o.height);
		},

		_setupDisabled: function(disabled){
			if ( disabled ) {
				this.element.prop( "disabled", true );
			} else {
				this.element.prop( "disabled", false );
			}
		},

		_setupWidth: function(width){
			this.element.width(width);
		},
		_setupHeight: function(height){
			this.element.height(height);
		},
		_setOption : function(key, value) {
			this._super(key, value);
			if (key === "disabled") {
				this._setupDisabled(value);
				return;
			}
			if (key === "width") {
				this._setupWidth(value);
				return;
			}
			if (key === "height") {
				this._setupHeight(value);
				return;
			}
		},
		/**
		 * 设定构建下拉列表的数据
		 * 
		 * @param obj
		 */
		_getDatasource : function(obj) {
			$.ajax({
				url : this.options.url,
				type : 'post',
				async : false,
				success : function(datasource) {
					obj.options.datasource = datasource;
				}
			});
		},
		/**
		 * 依据请求地址获取构建下拉列表的数据
		 */
		_getDatasourceByUrl : function() {
			var obj = this;
			if (this.options.datasource == null) {
				if (this.options.url != null && this.options.url != "") {
					this._getDatasource(obj);
				}
			}
		},
		/**
		 * 创建下拉列表项
		 */
		_createOptionByDatasource : function() {
			if (this.options.datasource != null) {
				if (this.options.hasNull == true) {
					this.element.append("<option value='"
							+ this.options.nullKey + "'>"
							+ this.options.nullValue + "</option>");
				}
				if (this.options.hasAll == true) {
					this.element.append("<option value='" + this.options.allKey
							+ "'>" + this.options.allValue + "</option>");
				}
				var datasource = eval(this.options.datasource);
				if (this.options.keyField != null
						&& this.options.keyField != ''
						&& this.options.valueField != null
						&& this.options.valueField != '') {
					for ( var i = 0; i < datasource.length; i++) {
						this.element.append("<option value="
								+ datasource[i][this.options.keyField] + ">"
								+ datasource[i][this.options.valueField]
								+ "</option>");
					}
				} else {
					for ( var key in datasource) {
						this.element.append("<option value=" + key + ">"
								+ datasource[key] + "</option>");
					}
				}
			}
		},
		/**
		 * 依据请求地址重新创建下拉列表项
		 * 
		 * @param url
		 */
		refreshUrl : function(url) {
			this.element.html("");
			this.options.url = url;
			this._getDatasourceByUrl();
			this._createOptionByDatasource();
		},
		/**
		 * 依据数据重新创建下拉列表项
		 * 
		 * @param datasource
		 */
		refreshDatasource : function(datasource) {
			this.element.html("");
			this.options.datasource = datasource;
			this._createOptionByDatasource();
		},
		/**
		 * 依据下拉列表项的位置设定值或获取选中项的位置
		 * 
		 * @param index
		 * @returns
		 */
		selectIndex : function(index) {
			if (arguments.length) {
				if (index != null && index != "" && !isNaN(index)) {
					this.element.get(0).selectedIndex = index;
				}
			} else {
				return this.element.get(0).selectedIndex;
			}
		},
		/**
		 * 依据下拉列表项的键值设定显示框的值或获取选中项的值
		 * 
		 * @param value
		 * @returns
		 */
		selectValue : function(value) {
			if (arguments.length) {
				if (value != null && value != "") {
					this.element.get(0).value = value;
				}
			} else {
				return this.element.val();
			}
		},
		/**
		 * 获取选中的文本
		 * 
		 * @returns
		 */
		selectText : function() {
			return this.element.find("option:selected").text();
		}
	});
})(jQuery);/*!
 * jQuery UI Datepicker 1.10.1
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/datepicker/
 *
 * Depends:
 *	jquery.ui.core.js
 */
(function( $, undefined ) {
var uuid = 0,
	runiqueId = /^ui-id-\d+$/;
// prevent duplicate loading
// this is only a problem because we proxy existing functions
// and we don't want to double proxy them
$.ui = $.ui || {};
if ( $.ui.version ) {
	return;
}
$.extend( $.ui, {
	version: "1.10.1",
	keyCode: {
		BACKSPACE: 8,
		COMMA: 188,
		DELETE: 46,
		DOWN: 40,
		END: 35,
		ENTER: 13,
		ESCAPE: 27,
		HOME: 36,
		LEFT: 37,
		NUMPAD_ADD: 107,
		NUMPAD_DECIMAL: 110,
		NUMPAD_DIVIDE: 111,
		NUMPAD_ENTER: 108,
		NUMPAD_MULTIPLY: 106,
		NUMPAD_SUBTRACT: 109,
		PAGE_DOWN: 34,
		PAGE_UP: 33,
		PERIOD: 190,
		RIGHT: 39,
		SPACE: 32,
		TAB: 9,
		UP: 38
	}
});

// plugins
$.fn.extend({
	_focus: $.fn.focus,
	focus: function( delay, fn ) {
		return typeof delay === "number" ?
			this.each(function() {
				var elem = this;
				setTimeout(function() {
					$( elem ).focus();
					if ( fn ) {
						fn.call( elem );
					}
				}, delay );
			}) :
			this._focus.apply( this, arguments );
	},

	scrollParent: function() {
		var scrollParent;
		if (($.ui.ie && (/(static|relative)/).test(this.css('position'))) || (/absolute/).test(this.css('position'))) {
			scrollParent = this.parents().filter(function() {
				return (/(relative|absolute|fixed)/).test($.css(this,'position')) && (/(auto|scroll)/).test($.css(this,'overflow')+$.css(this,'overflow-y')+$.css(this,'overflow-x'));
			}).eq(0);
		} else {
			scrollParent = this.parents().filter(function() {
				return (/(auto|scroll)/).test($.css(this,'overflow')+$.css(this,'overflow-y')+$.css(this,'overflow-x'));
			}).eq(0);
		}

		return (/fixed/).test(this.css('position')) || !scrollParent.length ? $(document) : scrollParent;
	},

	zIndex: function( zIndex ) {
		if ( zIndex !== undefined ) {
			return this.css( "zIndex", zIndex );
		}

		if ( this.length ) {
			var elem = $( this[ 0 ] ), position, value;
			while ( elem.length && elem[ 0 ] !== document ) {
				// Ignore z-index if position is set to a value where z-index is ignored by the browser
				// This makes behavior of this function consistent across browsers
				// WebKit always returns auto if the element is positioned
				position = elem.css( "position" );
				if ( position === "absolute" || position === "relative" || position === "fixed" ) {
					// IE returns 0 when zIndex is not specified
					// other browsers return a string
					// we ignore the case of nested elements with an explicit value of 0
					// <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
					value = parseInt( elem.css( "zIndex" ), 10 );
					if ( !isNaN( value ) && value !== 0 ) {
						return value;
					}
				}
				elem = elem.parent();
			}
		}

		return 0;
	},

	uniqueId: function() {
		return this.each(function() {
			if ( !this.id ) {
				this.id = "ui-id-" + (++uuid);
			}
		});
	},

	removeUniqueId: function() {
		return this.each(function() {
			if ( runiqueId.test( this.id ) ) {
				$( this ).removeAttr( "id" );
			}
		});
	}
});

// selectors
function focusable( element, isTabIndexNotNaN ) {
	var map, mapName, img,
		nodeName = element.nodeName.toLowerCase();
	if ( "area" === nodeName ) {
		map = element.parentNode;
		mapName = map.name;
		if ( !element.href || !mapName || map.nodeName.toLowerCase() !== "map" ) {
			return false;
		}
		img = $( "img[usemap=#" + mapName + "]" )[0];
		return !!img && visible( img );
	}
	return ( /input|select|textarea|button|object/.test( nodeName ) ?
		!element.disabled :
		"a" === nodeName ?
			element.href || isTabIndexNotNaN :
			isTabIndexNotNaN) &&
		// the element and all of its ancestors must be visible
		visible( element );
}

function visible( element ) {
	return $.expr.filters.visible( element ) &&
		!$( element ).parents().andSelf().filter(function() {
			return $.css( this, "visibility" ) === "hidden";
		}).length;
}

$.extend( $.expr[ ":" ], {
	data: $.expr.createPseudo ?
		$.expr.createPseudo(function( dataName ) {
			return function( elem ) {
				return !!$.data( elem, dataName );
			};
		}) :
		// support: jQuery <1.8
		function( elem, i, match ) {
			return !!$.data( elem, match[ 3 ] );
		},

	focusable: function( element ) {
		return focusable( element, !isNaN( $.attr( element, "tabindex" ) ) );
	},

	tabbable: function( element ) {
		var tabIndex = $.attr( element, "tabindex" ),
			isTabIndexNaN = isNaN( tabIndex );
		return ( isTabIndexNaN || tabIndex >= 0 ) && focusable( element, !isTabIndexNaN );
	}
});

// support
$(function() {
	var body = document.body,
		div = body.appendChild( div = document.createElement( "div" ) );

	// access offsetHeight before setting the style to prevent a layout bug
	// in IE 9 which causes the element to continue to take up space even
	// after it is removed from the DOM (#8026)
	div.offsetHeight;

	$.extend( div.style, {
		minHeight: "100px",
		height: "auto",
		padding: 0,
		borderWidth: 0
	});

	$.support.minHeight = div.offsetHeight === 100;
	$.support.selectstart = "onselectstart" in div;

	// set display to none to avoid a layout bug in IE
	// http://dev.jquery.com/ticket/4014
	body.removeChild( div ).style.display = "none";
});

// support: jQuery <1.8
if ( !$( "<a>" ).outerWidth( 1 ).jquery ) {
	$.each( [ "Width", "Height" ], function( i, name ) {
		var side = name === "Width" ? [ "Left", "Right" ] : [ "Top", "Bottom" ],
			type = name.toLowerCase(),
			orig = {
				innerWidth: $.fn.innerWidth,
				innerHeight: $.fn.innerHeight,
				outerWidth: $.fn.outerWidth,
				outerHeight: $.fn.outerHeight
			};

		function reduce( elem, size, border, margin ) {
			$.each( side, function() {
				size -= parseFloat( $.css( elem, "padding" + this ) ) || 0;
				if ( border ) {
					size -= parseFloat( $.css( elem, "border" + this + "Width" ) ) || 0;
				}
				if ( margin ) {
					size -= parseFloat( $.css( elem, "margin" + this ) ) || 0;
				}
			});
			return size;
		}

		$.fn[ "inner" + name ] = function( size ) {
			if ( size === undefined ) {
				return orig[ "inner" + name ].call( this );
			}

			return this.each(function() {
				$( this ).css( type, reduce( this, size ) + "px" );
			});
		};

		$.fn[ "outer" + name] = function( size, margin ) {
			if ( typeof size !== "number" ) {
				return orig[ "outer" + name ].call( this, size );
			}

			return this.each(function() {
				$( this).css( type, reduce( this, size, true, margin ) + "px" );
			});
		};
	});
}

// support: jQuery 1.6.1, 1.6.2 (http://bugs.jquery.com/ticket/9413)
if ( $( "<a>" ).data( "a-b", "a" ).removeData( "a-b" ).data( "a-b" ) ) {
	$.fn.removeData = (function( removeData ) {
		return function( key ) {
			if ( arguments.length ) {
				return removeData.call( this, $.camelCase( key ) );
			} else {
				return removeData.call( this );
			}
		};
	})( $.fn.removeData );
}





// deprecated

(function() {
	var uaMatch = /msie ([\w.]+)/.exec( navigator.userAgent.toLowerCase() ) || [];
	$.ui.ie = uaMatch.length ? true : false;
	$.ui.ie6 = parseFloat( uaMatch[ 1 ], 10 ) === 6;
})();

$.fn.extend({
	disableSelection: function() {
		return this.bind( ( $.support.selectstart ? "selectstart" : "mousedown" ) +
			".ui-disableSelection", function( event ) {
				event.preventDefault();
			});
	},

	enableSelection: function() {
		return this.unbind( ".ui-disableSelection" );
	}
});

$.extend( $.ui, {
	// $.ui.plugin is deprecated.  Use the proxy pattern instead.
	plugin: {
		add: function( module, option, set ) {
			var i,
				proto = $.ui[ module ].prototype;
			for ( i in set ) {
				proto.plugins[ i ] = proto.plugins[ i ] || [];
				proto.plugins[ i ].push( [ option, set[ i ] ] );
			}
		},
		call: function( instance, name, args ) {
			var i,
				set = instance.plugins[ name ];
			if ( !set || !instance.element[ 0 ].parentNode || instance.element[ 0 ].parentNode.nodeType === 11 ) {
				return;
			}

			for ( i = 0; i < set.length; i++ ) {
				if ( instance.options[ set[ i ][ 0 ] ] ) {
					set[ i ][ 1 ].apply( instance.element, args );
				}
			}
		}
	},

	contains: $.contains,

	// only used by resizable
	hasScroll: function( el, a ) {

		//If overflow is hidden, the element might have extra content, but the user wants to hide it
		if ( $( el ).css( "overflow" ) === "hidden") {
			return false;
		}

		var scroll = ( a && a === "left" ) ? "scrollLeft" : "scrollTop",
			has = false;

		if ( el[ scroll ] > 0 ) {
			return true;
		}

		// TODO: determine which cases actually cause this to happen
		// if the element doesn't have the scroll set, see if it's possible to
		// set the scroll
		el[ scroll ] = 1;
		has = ( el[ scroll ] > 0 );
		el[ scroll ] = 0;
		return has;
	},

	// these are odd functions, fix the API or move into individual plugins
	isOverAxis: function( x, reference, size ) {
		//Determines when x coordinate is over "b" element axis
		return ( x > reference ) && ( x < ( reference + size ) );
	},
	isOver: function( y, x, top, left, height, width ) {
		//Determines when x, y coordinates is over "b" element
		return $.ui.isOverAxis( y, top, height ) && $.ui.isOverAxis( x, left, width );
	}
});

})( jQuery );

//-----------------------------------------------------------------------//



(function( $, undefined ) {

$.extend($.dtui, {dtdatepicker: { version: "1.10.1" } });

var PROP_NAME = "dtdatepicker",
	dpuuid = new Date().getTime(),
	instActive;

/* Date picker manager.
   Use the singleton instance of this class, $.datepicker, to interact with the date picker.
   Settings for (groups of) date pickers are maintained in an instance object,
   allowing multiple different settings on the same page. */

function DtDatepicker() {
	this._curInst = null; // The current instance in use
	this._keyEvent = false; // If the last event was a key event
	this._disabledInputs = []; // List of date picker inputs that have been disabled
	this._datepickerShowing = false; // True if the popup picker is showing , false if not
	this._inDialog = false; // True if showing within a "dialog", false if not
	this._mainDivId = "ui-datepicker-div"; // The ID of the main datepicker division
	this._inlineClass = "ui-datepicker-inline"; // The name of the inline marker class
	this._appendClass = "ui-datepicker-append"; // The name of the append marker class
	this._triggerClass = "ui-datepicker-trigger"; // The name of the trigger marker class
	this._dialogClass = "ui-datepicker-dialog"; // The name of the dialog marker class
	this._disableClass = "ui-datepicker-disabled"; // The name of the disabled covering marker class
	this._unselectableClass = "ui-datepicker-unselectable"; // The name of the unselectable cell marker class
	this._currentClass = "ui-datepicker-current-day"; // The name of the current day marker class
	this._dayOverClass = "ui-datepicker-days-cell-over"; // The name of the day hover marker class
	this.regional = []; // Available regional settings, indexed by language code
	this.regional[""] = { // Default regional settings
		//confirmText:"confirm",  //add by wangdexing 
		//required:true,
		closeText: "Done", // Display text for close link
		prevText: "", // Display text for previous month link
		nextText: "", // Display text for next month link
		currentText: "Today", // Display text for current month link
		monthNames: ["January","February","March","April","May","June",
			"July","August","September","October","November","December"], // Names of months for drop-down and formatting
		monthNamesShort: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"], // For formatting
		dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"], // For formatting
		dayNamesShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"], // For formatting
		dayNamesMin: ["Su","Mo","Tu","We","Th","Fr","Sa"], // Column headings for days starting at Sunday
		weekHeader: "Wk", // Column header for week of the year
		dateFormat: "mm/dd/yy", // See format options on parseDate
		firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
		isRTL: false, // True if right-to-left language, false if left-to-right
		showMonthAfterYear: false, // True if the year select precedes month, false for month then year
		yearSuffix: "" // Additional text to append to the year in the month headers
	};
	this._defaults = { // Global defaults for all the date picker instances
		height:'20px',
		width:'149px',
		required:false,
		formatNeed:false,
		prompt:'日历',
		label:"",
		showOn: "focus", // "focus" for popup on focus,
			// "button" for trigger button, or "both" for either
		showAnim: "fadeIn", // Name of jQuery animation for popup
		showOptions: {}, // Options for enhanced animations
		defaultDate: null, // Used when field is blank: actual date,
			// +/-number for offset from today, null for today
		appendText: "", // Display text following the input box, e.g. showing the format
		buttonText: "...", // Text for trigger button
		buttonImage: "", // URL for trigger button image
		buttonImageOnly: false, // True if the image appears alone, false if it appears on a button
		hideIfNoPrevNext: false, // True to hide next/previous month links
			// if not applicable, false to just disable them
		navigationAsDateFormat: false, // True if date formatting applied to prev/today/next links
		gotoCurrent: false, // True if today link goes back to current selection instead
		changeMonth: false, // True if month can be selected directly, false if only prev/next
		changeYear: false, // True if year can be selected directly, false if only prev/next
		yearRange: "c-10:c+10", // Range of years to display in drop-down,
			// either relative to today's year (-nn:+nn), relative to currently displayed year
			// (c-nn:c+nn), absolute (nnnn:nnnn), or a combination of the above (nnnn:-n)
		showOtherMonths: false, // True to show dates in other months, false to leave blank
		selectOtherMonths: false, // True to allow selection of dates in other months, false for unselectable
		showWeek: false, // True to show week of the year, false to not show it
		calculateWeek: this.iso8601Week, // How to calculate the week of the year,
			// takes a Date and returns the number of the week for it
		shortYearCutoff: "+10", // Short year values < this are in the current century,
			// > this are in the previous century,
			// string value starting with "+" for current year + value
		minDate: null, // The earliest selectable date, or null for no limit
		maxDate: null, // The latest selectable date, or null for no limit
		duration: "fast", // Duration of display/closure
		beforeShowDay: null, // Function that takes a date and returns an array with
			// [0] = true if selectable, false if not, [1] = custom CSS class name(s) or "",
			// [2] = cell title (optional), e.g. $.datepicker.noWeekends
		beforeShow: null, // Function that takes an input field and
			// returns a set of custom settings for the date picker
		onSelect: null, // Define a callback function when a date is selected
		onChangeMonthYear: null, // Define a callback function when the month or year is changed
		onClose: null, // Define a callback function when the datepicker is closed
		numberOfMonths: 1, // Number of months to show at a time
		showCurrentAtPos: 0, // The position in multipe months at which to show the current month (starting at 0)
		stepMonths: 1, // Number of months to step back/forward
		stepBigMonths: 12, // Number of months to step back/forward for the big links
		altField: "", // Selector for an alternate field to store selected dates into
		altFormat: "", // The date format to use for the alternate field
		constrainInput: true, // The input is constrained by the current date format
		showButtonPanel: false, // True to show button panel, false to not show it
		autoSize: true, // True to size the input for the date format, false to leave as is
		disabled: false, // The initial disabled state
		showTimePanel: false, // 真:显示时间面板,假:不显示   add by wangdexing
		setSelectDay:null      //add by wangdexing
	};
	$.extend(this._defaults, this.regional[""]);
	this.dpDiv = bindHover($("<div id='" + this._mainDivId + "' class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>"));
}

$.extend(DtDatepicker.prototype, {
	//dateTimeStr:"",   //add by wangdexing
	/* Class name added to elements to indicate already configured with a date picker. */
	markerClassName: "hasDatepicker",

	//Keep track of the maximum number of rows displayed (see #7043)
	maxRows: 4,

	// TODO rename to "widget" when switching to widget factory
	_widgetDatepicker: function() {
		return this.dpDiv;
	},

	/* Override the default settings for all instances of the date picker.
	 * @param  settings  object - the new settings to use as defaults (anonymous object)
	 * @return the manager object
	 */
	setDefaults: function(settings) {
		extendRemove(this._defaults, settings || {});
		return this;
	},

	/* Attach the date picker to a jQuery selection.
	 * @param  target	element - the target input field or division or span
	 * @param  settings  object - the new settings to use for this date picker instance (anonymous)
	 */
	_attachDatepicker: function(target, settings) {
		var nodeName, inline, inst;
		nodeName = target.nodeName.toLowerCase();
		inline = (nodeName === "div" || nodeName === "span");
		if (!target.id) {
			this.uuid += 1;
			target.id = "dp" + this.uuid;
		}
		inst = this._newInst($(target), inline);
		inst.settings = $.extend({}, settings || {});
		// add by xychun 2013/06/26 如果显示时间则显示按钮面板
		if (inst.settings.showTimePanel == true){
			inst.settings.showButtonPanel = true;
		}
		if (nodeName === "input") {
			this._connectDatepicker(target, inst);
		} else if (inline) {
			this._inlineDatepicker(target, inst);
		}
	},

	/* Create a new instance object. */
	_newInst: function(target, inline) {
		var id = target[0].id.replace(/([^A-Za-z0-9_\-])/g, "\\\\$1"); // escape jQuery meta chars
		return {
			//为了支持callUIMethod方法，增加两个属性
			widgetFullName: "ui-dtdatepicker",
			widgetName: "dtdatepicker",
			id: id, input: target, // associated target
			selectedDay: 0, selectedMonth: 0, selectedYear: 0, // current selection
			selectedHour: 0, selectedMinute: 0, selectedSecond: 0,
			drawMonth: 0, drawYear: 0, // month being drawn
			inline: inline, // is datepicker inline or not
			dpDiv: (!inline ? this.dpDiv : // presentation div
			bindHover($("<div class='" + this._inlineClass + " ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>")))
		};
	},

	/* Attach the date picker to an input field. */
	_connectDatepicker: function(target, inst) {
		var input = $(target);
		
		inst.append = $([]);
		inst.trigger = $([]);
		if (input.hasClass(this.markerClassName)) {
			return;
		}
		this._wrapInput(input, inst);
		this._attachments(input, inst);
		input.addClass('ui-corner-all').addClass('ui-widget-content');
		input.addClass(this.markerClassName).keydown(this._doKeyDown).
			keypress(this._doKeyPress).keyup(this._doKeyUp);
		this._autoSize(inst);
		$.data(target, PROP_NAME, inst);
		//If disabled option is true, disable the datepicker once it has been attached to the input (see ticket #5665)
		if( inst.settings.disabled ) {
			this._disableDatepicker( target );
		}
	},
	
	_wrapInput: function(input, inst) {
		var showOn, buttonText, buttonImage,
			isRTL = this._get(inst, "isRTL");
		if (inst.trigger) {
			inst.trigger.remove();
		}

		showOn = this._get(inst, "showOn");
		if (showOn === "focus" || showOn === "both") { // pop-up date picker when in the marked field
			input.focus(this._showDatepicker);
		}
		if (showOn === "button" || showOn === "both") { // pop-up date picker when button clicked
			buttonText = this._get(inst, "buttonText");
			buttonImage = this._get(inst, "buttonImage");
			//注释掉title属性，以去除“...”提示，modify by xychun 2013-08-14
			inst.trigger = $(this._get(inst, "buttonImageOnly") ?
				$("<img/>").addClass(this._triggerClass).
					attr({ src: buttonImage, alt: buttonText/*, title: buttonText*/ }).css("vertical-align","middle") :
				$("<button type='button'></button>").addClass(this._triggerClass).
					html(!buttonImage ? buttonText : $("<img/>").attr(
					{ src:buttonImage, alt:buttonText, title:buttonText }).css("vertical-align","middle")));
			input[isRTL ? "before" : "after"](inst.trigger);
			inst.trigger.click(function() {
				if ($.dtdatepicker._datepickerShowing && $.dtdatepicker._lastInput === input[0]) {
					$.dtdatepicker._hideDatepicker();
				} else if ($.dtdatepicker._datepickerShowing && $.dtdatepicker._lastInput !== input[0]) {
					$.dtdatepicker._hideDatepicker();
					$.dtdatepicker._showDatepicker(input[0]);
				} else {
					$.dtdatepicker._showDatepicker(input[0]);
				}
				return false;
			});
		}
		
		////add by xychun 2013/06/07 增加required处理
		var starDiv = inst.starDiv = $.dtui.util.createStarElem(inst.settings.required);

		input.addClass("input-init");
		var totalwidth=0;
	    $("<label></label>").text(this._get(inst, "label")).insertBefore(input);
	    var container=$("<div style='display:inline-block'></div>")
							.css({"height":this._get(inst, "height"),"width":this._get(inst, "width"),"margin":"0 auto 2px auto"})
							.insertAfter(input);
	   
    	starDiv.appendTo(container);
    	totalwidth += 8;
	    
	    //if(this._get(inst, "buttonImageOnly")){
	    if (showOn === "button" || showOn === "both") { 
	    	var divtrigger=$("<div></div>").addClass("input-rightpadding").append(inst.trigger).appendTo(container);//datepicker button
	    	totalwidth+=16;
	    }
	    
	    var divinput=$("<div></div>").addClass("input-Container").append(input).appendTo(container)
									.addClass('ui-corner-all').addClass('ui-widget-content');                       //set corner all
	    
	    divinput.css("margin-right",totalwidth);		
	},

	/* Make attachments based on settings. */
	_attachments: function(input, inst) {
		var showOn, buttonText, buttonImage,
			appendText = this._get(inst, "appendText"),
			isRTL = this._get(inst, "isRTL"),
			defaultDate;

		if (inst.append) {
			inst.append.remove();
		}
		if (appendText) {
			inst.append = $("<span class='" + this._appendClass + "'>" + appendText + "</span>");
			input[isRTL ? "before" : "after"](inst.append);
		}
		//add by xychun 2013/05/27 增加宽度、高度和name处理
		if (inst.settings.name){
			input.attr('name', inst.settings.name);
		}
		if (inst.settings.defaultDate) {
			defaultDate = this._getDefaultDate(inst);
			input.attr('value', this.formatDate(this._get(inst, 'dateFormat'), defaultDate, this._getFormatConfig(inst)));
		}
		input.unbind("focus", this._showDatepicker);

		showOn = this._get(inst, "showOn");
		if (showOn === "focus" || showOn === "both") { // pop-up date picker when in the marked field
			input.focus(this._showDatepicker);
		}
		
		////add by xychun 2013/06/07 增加required处理
		if (inst.settings.required) {
			inst.starDiv.find('span.input-star').show();
		}
		else {
			inst.starDiv.find('span.input-star').hide();
		}

	    //需要更新divinput的margin-right
		//divinput.css("margin-right",totalwidth);
				
	},

	/* Apply the maximum length for the date format. */
	_autoSize: function(inst) {
		if (this._get(inst, "autoSize") && !inst.inline) {
			var findMax, max, maxI, i,
				date = new Date(2009, 12 - 1, 20), // Ensure double digits
				dateFormat = this._get(inst, "dateFormat");

			if (dateFormat.match(/[DM]/)) {
				findMax = function(names) {
					max = 0;
					maxI = 0;
					for (i = 0; i < names.length; i++) {
						if (names[i].length > max) {
							max = names[i].length;
							maxI = i;
						}
					}
					return maxI;
				};
				date.setMonth(findMax(this._get(inst, (dateFormat.match(/MM/) ?
					"monthNames" : "monthNamesShort"))));
				date.setDate(findMax(this._get(inst, (dateFormat.match(/DD/) ?
					"dayNames" : "dayNamesShort"))) + 20 - date.getDay());
			}
			inst.input.attr("size", this._formatDate(inst, date).length);
		}
	},

	/* Attach an inline date picker to a div. */
	_inlineDatepicker: function(target, inst) {
		var divSpan = $(target);
		if (divSpan.hasClass(this.markerClassName)) {
			return;
		}
		divSpan.addClass(this.markerClassName).append(inst.dpDiv);
		$.data(target, PROP_NAME, inst);
		this._setDate(inst, this._getDefaultDate(inst), true);
		this._updateDatepicker(inst);
		this._updateAlternate(inst);
		//If disabled option is true, disable the datepicker before showing it (see ticket #5665)
		if( inst.settings.disabled ) {
			this._disableDatepicker( target );
		}
		// Set display:block in place of inst.dpDiv.show() which won't work on disconnected elements
		// http://bugs.jqueryui.com/ticket/7552 - A Datepicker created on a detached div has zero height
		inst.dpDiv.css( "display", "block" );
	},

	/* Pop-up the date picker in a "dialog" box.
	 * @param  input element - ignored
	 * @param  date	string or Date - the initial date to display
	 * @param  onSelect  function - the function to call when a date is selected
	 * @param  settings  object - update the dialog date picker instance's settings (anonymous object)
	 * @param  pos int[2] - coordinates for the dialog's position within the screen or
	 *					event - with x/y coordinates or
	 *					leave empty for default (screen centre)
	 * @return the manager object
	 */
	_dialogDatepicker: function(input, date, onSelect, settings, pos) {
		var id, browserWidth, browserHeight, scrollX, scrollY,
			inst = this._dialogInst; // internal instance

		if (!inst) {
			this.uuid += 1;
			id = "dp" + this.uuid;
			this._dialogInput = $("<input type='text' id='" + id +
				"' style='position: absolute; top: -100px; width: 0px;'/>");
			this._dialogInput.keydown(this._doKeyDown);
			$("body").append(this._dialogInput);
			inst = this._dialogInst = this._newInst(this._dialogInput, false);
			inst.settings = {};
			$.data(this._dialogInput[0], PROP_NAME, inst);
		}
		extendRemove(inst.settings, settings || {});
		date = (date && date.constructor === Date ? this._formatDate(inst, date) : date);
		this._dialogInput.val(date);

		this._pos = (pos ? (pos.length ? pos : [pos.pageX, pos.pageY]) : null);
		if (!this._pos) {
			browserWidth = document.documentElement.clientWidth;
			browserHeight = document.documentElement.clientHeight;
			scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
			scrollY = document.documentElement.scrollTop || document.body.scrollTop;
			this._pos = // should use actual width/height below
				[(browserWidth / 2) - 100 + scrollX, (browserHeight / 2) - 150 + scrollY];
		}

		// move input on screen for focus, but hidden behind dialog
		this._dialogInput.css("left", (this._pos[0] + 20) + "px").css("top", this._pos[1] + "px");
		inst.settings.onSelect = onSelect;
		this._inDialog = true;
		this.dpDiv.addClass(this._dialogClass);
		this._showDatepicker(this._dialogInput[0]);
		if ($.blockUI) {
			$.blockUI(this.dpDiv);
		}
		$.data(this._dialogInput[0], PROP_NAME, inst);
		return this;
	},

	/* Detach a datepicker from its control.
	 * @param  target	element - the target input field or division or span
	 */
	_destroyDatepicker: function(target) {
		var nodeName,
			$target = $(target),
			inst = $.data(target, PROP_NAME);

		if (!$target.hasClass(this.markerClassName)) {
			return;
		}

		nodeName = target.nodeName.toLowerCase();
		$.removeData(target, PROP_NAME);
		if (nodeName === "input") {
			inst.append.remove();
			inst.trigger.remove();
			$target.removeClass(this.markerClassName).
				unbind("focus", this._showDatepicker).
				unbind("keydown", this._doKeyDown).
				unbind("keypress", this._doKeyPress).
				unbind("keyup", this._doKeyUp);
		} else if (nodeName === "div" || nodeName === "span") {
			$target.removeClass(this.markerClassName).empty();
		}
	},

	/* Enable the date picker to a jQuery selection.
	 * @param  target	element - the target input field or division or span
	 */
	_enableDatepicker: function(target) {
		var nodeName, inline,
			$target = $(target),
			inst = $.data(target, PROP_NAME);

		if (!$target.hasClass(this.markerClassName)) {
			return;
		}

		nodeName = target.nodeName.toLowerCase();
		if (nodeName === "input") {
			target.disabled = false;
			inst.trigger.filter("button").
				each(function() { this.disabled = false; }).end().
				filter("img").css({opacity: "1.0", cursor: ""});
		} else if (nodeName === "div" || nodeName === "span") {
			inline = $target.children("." + this._inlineClass);
			inline.children().removeClass("ui-state-disabled");
			inline.find("select.ui-datepicker-month, select.ui-datepicker-year").
				prop("disabled", false);
		}
		this._disabledInputs = $.map(this._disabledInputs,
			function(value) { return (value === target ? null : value); }); // delete entry
	},

	/* Disable the date picker to a jQuery selection.
	 * @param  target	element - the target input field or division or span
	 */
	_disableDatepicker: function(target) {
		var nodeName, inline,
			$target = $(target),
			inst = $.data(target, PROP_NAME);

		if (!$target.hasClass(this.markerClassName)) {
			return;
		}

		nodeName = target.nodeName.toLowerCase();
		if (nodeName === "input") {
			target.disabled = true;
			inst.trigger.filter("button").
				each(function() { this.disabled = true; }).end().
				filter("img").css({opacity: "0.5", cursor: "default"});
		} else if (nodeName === "div" || nodeName === "span") {
			inline = $target.children("." + this._inlineClass);
			inline.children().addClass("ui-state-disabled");
			inline.find("select.ui-datepicker-month, select.ui-datepicker-year").
				prop("disabled", true);
		}
		this._disabledInputs = $.map(this._disabledInputs,
			function(value) { return (value === target ? null : value); }); // delete entry
		this._disabledInputs[this._disabledInputs.length] = target;
	},

	/* Is the first field in a jQuery collection disabled as a datepicker?
	 * @param  target	element - the target input field or division or span
	 * @return boolean - true if disabled, false if enabled
	 */
	_isDisabledDatepicker: function(target) {
		if (!target) {
			return false;
		}
		for (var i = 0; i < this._disabledInputs.length; i++) {
			if (this._disabledInputs[i] === target) {
				return true;
			}
		}
		return false;
	},

	/* Retrieve the instance data for the target control.
	 * @param  target  element - the target input field or division or span
	 * @return  object - the associated instance data
	 * @throws  error if a jQuery problem getting data
	 */
	_getInst: function(target) {
		try {
			return $.data(target, PROP_NAME);
		}
		catch (err) {
			throw "Missing instance data for this datepicker";
		}
	},

	/* Update or retrieve the settings for a date picker attached to an input field or division.
	 * @param  target  element - the target input field or division or span
	 * @param  name	object - the new settings to update or
	 *				string - the name of the setting to change or retrieve,
	 *				when retrieving also "all" for all instance settings or
	 *				"defaults" for all global defaults
	 * @param  value   any - the new value for the setting
	 *				(omit if above is an object or to retrieve a value)
	 */
	_optionDatepicker: function(target, name, value) {
		var settings, date, minDate, maxDate,
			inst = this._getInst(target);

		if (arguments.length === 2 && typeof name === "string") {
			return (name === "defaults" ? $.extend({}, $.dtdatepicker._defaults) :
				(inst ? (name === "all" ? $.extend({}, inst.settings) :
				this._get(inst, name)) : null));
		}

		settings = name || {};
		if (typeof name === "string") {
			settings = {};
			settings[name] = value;
		}

		if (inst) {
			if (this._curInst === inst) {
				this._hideDatepicker();
			}

			date = this._getDateDatepicker(target, true);
			minDate = this._getMinMaxDate(inst, "min");
			maxDate = this._getMinMaxDate(inst, "max");
			extendRemove(inst.settings, settings);
			// reformat the old minDate/maxDate values if dateFormat changes and a new minDate/maxDate isn't provided
			if (minDate !== null && settings.dateFormat !== undefined && settings.minDate === undefined) {
				inst.settings.minDate = this._formatDate(inst, minDate);
			}
			if (maxDate !== null && settings.dateFormat !== undefined && settings.maxDate === undefined) {
				inst.settings.maxDate = this._formatDate(inst, maxDate);
			}
			if ( "disabled" in settings ) {
				if ( settings.disabled ) {
					this._disableDatepicker(target);
				} else {
					this._enableDatepicker(target);
				}
			}
			this._attachments($(target), inst);
			this._autoSize(inst);
			this._setDate(inst, date);
			this._updateAlternate(inst);
			this._updateDatepicker(inst);
		}
	},

	// change method deprecated
	_changeDatepicker: function(target, name, value) {
		this._optionDatepicker(target, name, value);
	},

	/* Redraw the date picker attached to an input field or division.
	 * @param  target  element - the target input field or division or span
	 */
	_refreshDatepicker: function(target) {
		var inst = this._getInst(target);
		if (inst) {
			this._updateDatepicker(inst);
		}
	},

	/* Set the dates for a jQuery selection.
	 * @param  target element - the target input field or division or span
	 * @param  date	Date - the new date
	 */
	_setDateDatepicker: function(target, date) {
		var inst = this._getInst(target);
		if (inst) {
			this._setDate(inst, date);
			this._updateDatepicker(inst);
			this._updateAlternate(inst);
		}
	},

	/* Get the date(s) for the first entry in a jQuery selection.
	 * @param  target element - the target input field or division or span
	 * @param  noDefault boolean - true if no default date is to be used
	 * @return Date - the current date
	 */
	_getDateDatepicker: function(target, noDefault) {
		var inst = this._getInst(target);
		if (inst && !inst.inline) {
			this._setDateFromField(inst, noDefault);
		}
		return (inst ? this._getDate(inst) : null);
	},

	/* Handle keystrokes. */
	_doKeyDown: function(event) {
		var onSelect, dateStr, sel,
			inst = $.dtdatepicker._getInst(event.target),
			handled = true,
			isRTL = inst.dpDiv.is(".ui-datepicker-rtl");

		inst._keyEvent = true;
		if ($.dtdatepicker._datepickerShowing) {
			switch (event.keyCode) {
				case 9: $.dtdatepicker._hideDatepicker();
						handled = false;
						break; // hide on tab out
				case 13: sel = $("td." + $.dtdatepicker._dayOverClass + ":not(." +
									$.dtdatepicker._currentClass + ")", inst.dpDiv);
						if (sel[0]) {
							$.dtdatepicker._selectDay(event.target, inst.selectedMonth, inst.selectedYear, sel[0]);
						}

						onSelect = $.dtdatepicker._get(inst, "onSelect");
						if (onSelect) {
							dateStr = $.dtdatepicker._formatDate(inst);

							// trigger custom callback
							onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);
						} else {
							$.dtdatepicker._hideDatepicker();
						}

						return false; // don't submit the form
				case 27: $.dtdatepicker._hideDatepicker();
						break; // hide on escape
				case 33: $.dtdatepicker._adjustDate(event.target, (event.ctrlKey ?
							-$.dtdatepicker._get(inst, "stepBigMonths") :
							-$.dtdatepicker._get(inst, "stepMonths")), "M");
						break; // previous month/year on page up/+ ctrl
				case 34: $.dtdatepicker._adjustDate(event.target, (event.ctrlKey ?
							+$.dtdatepicker._get(inst, "stepBigMonths") :
							+$.dtdatepicker._get(inst, "stepMonths")), "M");
						break; // next month/year on page down/+ ctrl
				case 35: if (event.ctrlKey || event.metaKey) {
							$.dtdatepicker._clearDate(event.target);
						}
						handled = event.ctrlKey || event.metaKey;
						break; // clear on ctrl or command +end
				case 36: if (event.ctrlKey || event.metaKey) {
							$.dtdatepicker._gotoToday(event.target);
						}
						handled = event.ctrlKey || event.metaKey;
						break; // current on ctrl or command +home
				case 37: if (event.ctrlKey || event.metaKey) {
							$.dtdatepicker._adjustDate(event.target, (isRTL ? +1 : -1), "D");
						}
						handled = event.ctrlKey || event.metaKey;
						// -1 day on ctrl or command +left
						if (event.originalEvent.altKey) {
							$.dtdatepicker._adjustDate(event.target, (event.ctrlKey ?
								-$.dtdatepicker._get(inst, "stepBigMonths") :
								-$.dtdatepicker._get(inst, "stepMonths")), "M");
						}
						// next month/year on alt +left on Mac
						break;
				case 38: if (event.ctrlKey || event.metaKey) {
							$.dtdatepicker._adjustDate(event.target, -7, "D");
						}
						handled = event.ctrlKey || event.metaKey;
						break; // -1 week on ctrl or command +up
				case 39: if (event.ctrlKey || event.metaKey) {
							$.dtdatepicker._adjustDate(event.target, (isRTL ? -1 : +1), "D");
						}
						handled = event.ctrlKey || event.metaKey;
						// +1 day on ctrl or command +right
						if (event.originalEvent.altKey) {
							$.dtdatepicker._adjustDate(event.target, (event.ctrlKey ?
								+$.dtdatepicker._get(inst, "stepBigMonths") :
								+$.dtdatepicker._get(inst, "stepMonths")), "M");
						}
						// next month/year on alt +right
						break;
				case 40: if (event.ctrlKey || event.metaKey) {
							$.dtdatepicker._adjustDate(event.target, +7, "D");
						}
						handled = event.ctrlKey || event.metaKey;
						break; // +1 week on ctrl or command +down
				default: handled = false;
			}
		} else if (event.keyCode === 36 && event.ctrlKey) { // display the date picker on ctrl+home
			$.dtdatepicker._showDatepicker(this);
		} else {
			handled = false;
		}

		if (handled) {
			event.preventDefault();
			event.stopPropagation();
		}
	},

	/* Filter entered characters - based on date format. */
	_doKeyPress: function(event) {
		var chars, chr,
			inst = $.dtdatepicker._getInst(event.target);

		if ($.dtdatepicker._get(inst, "constrainInput")) {
			chars = $.dtdatepicker._possibleChars($.dtdatepicker._get(inst, "dateFormat"));
			chr = String.fromCharCode(event.charCode == null ? event.keyCode : event.charCode);
			return event.ctrlKey || event.metaKey || (chr < " " || !chars || chars.indexOf(chr) > -1);
		}
	},

	/* Synchronise manual entry and field/alternate field. */
	_doKeyUp: function(event) {
		var date,
			inst = $.dtdatepicker._getInst(event.target);

		if (inst.input.val() !== inst.lastVal) {
			try {
				date = $.dtdatepicker.parseDate($.dtdatepicker._get(inst, "dateFormat"),
					(inst.input ? inst.input.val() : null),
					$.dtdatepicker._getFormatConfig(inst));

				if (date) { // only if valid
					$.dtdatepicker._setDateFromField(inst);
					$.dtdatepicker._updateAlternate(inst);
					$.dtdatepicker._updateDatepicker(inst);
				}
			}
			catch (err) {
			}
		}
		return true;
	},

	/**
	* add by xychun 2013/06/26 初始化时间
	**/
	_initTimeInput: function(inst){
		var now= new Date();
		var hour=now.getHours();
		var minute=now.getMinutes();
		var second=now.getSeconds();
		var val=$.trim(inst.input.val());
		
		if(val!=null&&val!=''&&val.indexOf(":")>-1){
			var secondIndexBegin=val.lastIndexOf(":");
			second=val.substr(secondIndexBegin+1,2);
			var minuteIndexBegin=(val.substr(0,secondIndexBegin)).lastIndexOf(":");
			minute=val.substr(minuteIndexBegin+1,2);
			hour=val.substr(minuteIndexBegin-2,2);
		}
		
		$("#date_hour").val(hour);
		$("#date_minutes").val(minute);
		$("#date_seconds").val(second);
	},

	/* Pop-up the date picker for a given input field.
	 * If false returned from beforeShow event handler do not show.
	 * @param  input  element - the input field attached to the date picker or
	 *					event - if triggered by focus
	 */
	_showDatepicker: function(input) {
		input = input.target || input;
		if (input.nodeName.toLowerCase() !== "input") { // find from button/image trigger
			input = $("input", input.parentNode)[0];
		}

		if ($.dtdatepicker._isDisabledDatepicker(input) || $.dtdatepicker._lastInput === input) { // already here
			return;
		}

		var inst, beforeShow, beforeShowSettings, isFixed,
			offset, showAnim, duration;

		inst = $.dtdatepicker._getInst(input);
		if ($.dtdatepicker._curInst && $.dtdatepicker._curInst !== inst) {
			$.dtdatepicker._curInst.dpDiv.stop(true, true);
			if ( inst && $.dtdatepicker._datepickerShowing ) {
				$.dtdatepicker._hideDatepicker( $.dtdatepicker._curInst.input[0] );
			}
		}

		beforeShow = $.dtdatepicker._get(inst, "beforeShow");
		beforeShowSettings = beforeShow ? beforeShow.apply(input, [input, inst]) : {};
		if(beforeShowSettings === false){
			return;
		}
		extendRemove(inst.settings, beforeShowSettings);

		inst.lastVal = null;
		$.dtdatepicker._lastInput = input;
		$.dtdatepicker._setDateFromField(inst);

		if ($.dtdatepicker._inDialog) { // hide cursor
			input.value = "";
		}
		if (!$.dtdatepicker._pos) { // position below input
			$.dtdatepicker._pos = $.dtdatepicker._findPos(input);
			$.dtdatepicker._pos[1] += input.offsetHeight; // add the height
		}

		isFixed = false;
		$(input).parents().each(function() {
			isFixed |= $(this).css("position") === "fixed";
			return !isFixed;
		});

		offset = {left: $.dtdatepicker._pos[0], top: $.dtdatepicker._pos[1]};
		$.dtdatepicker._pos = null;
		//to avoid flashes on Firefox
		inst.dpDiv.empty();
		// determine sizing offscreen
		inst.dpDiv.css({position: "absolute", display: "block", top: "-1000px"});
		$.dtdatepicker._updateDatepicker(inst);
		// fix width for dynamic number of date pickers
		// and adjust position before showing
		offset = $.dtdatepicker._checkOffset(inst, offset, isFixed);
		inst.dpDiv.css({position: ($.dtdatepicker._inDialog && $.blockUI ?
			"static" : (isFixed ? "fixed" : "absolute")), display: "none",
			left: offset.left + "px", top: offset.top + "px"});

		if (!inst.inline) {
			showAnim = $.dtdatepicker._get(inst, "showAnim");
			duration = $.dtdatepicker._get(inst, "duration");
			inst.dpDiv.zIndex($(input).zIndex()+1);
			$.dtdatepicker._datepickerShowing = true;

			if ( $.effects && $.effects.effect[ showAnim ] ) {
				inst.dpDiv.show(showAnim, $.dtdatepicker._get(inst, "showOptions"), duration);
			} else {
				inst.dpDiv[showAnim || "show"](showAnim ? duration : null);
			}

			if (inst.input.is(":visible") && !inst.input.is(":disabled")) {
				inst.input.focus();
			}
			$.dtdatepicker._curInst = inst;
		}
		//add by xychun 2013/06/26 初始化时间
		var showTimePanel = $.dtdatepicker._get(inst, 'showTimePanel');
		if(showTimePanel){
			$.dtdatepicker._initTimeInput(inst);
		}
		
	},

	/* Generate the date picker content. */
	_updateDatepicker: function(inst) {
		this.maxRows = 4; //Reset the max number of rows being displayed (see #7043)
		instActive = inst; // for delegate hover events
		inst.dpDiv.empty().append(this._generateHTML(inst));
		this._attachHandlers(inst);
		inst.dpDiv.find("." + this._dayOverClass + " a").mouseover();

		var origyearshtml,
			numMonths = this._getNumberOfMonths(inst),
			cols = numMonths[1],
			width = 17;

		inst.dpDiv.removeClass("ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4").width("");
		if (cols > 1) {
			inst.dpDiv.addClass("ui-datepicker-multi-" + cols).css("width", (width * cols) + "em");
		}
		inst.dpDiv[(numMonths[0] !== 1 || numMonths[1] !== 1 ? "add" : "remove") +
			"Class"]("ui-datepicker-multi");
		inst.dpDiv[(this._get(inst, "isRTL") ? "add" : "remove") +
			"Class"]("ui-datepicker-rtl");

		// #6694 - don't focus the input if it's already focused
		// this breaks the change event in IE
		if (inst === $.dtdatepicker._curInst && $.dtdatepicker._datepickerShowing && inst.input &&
			inst.input.is(":visible") && !inst.input.is(":disabled") && inst.input[0] !== document.activeElement) {
			inst.input.focus();
		}

		// deffered render of the years select (to avoid flashes on Firefox)
		if( inst.yearshtml ){
			origyearshtml = inst.yearshtml;
			setTimeout(function(){
				//assure that inst.yearshtml didn't change.
				if( origyearshtml === inst.yearshtml && inst.yearshtml ){
					inst.dpDiv.find("select.ui-datepicker-year:first").replaceWith(inst.yearshtml);
				}
				origyearshtml = inst.yearshtml = null;
			}, 0);
		}
	},

	/* Retrieve the size of left and top borders for an element.
	 * @param  elem  (jQuery object) the element of interest
	 * @return  (number[2]) the left and top borders
	 */
	_getBorders: function(elem) {
		var convert = function(value) {
			return {thin: 1, medium: 2, thick: 3}[value] || value;
		};
		return [parseFloat(convert(elem.css("border-left-width"))),
			parseFloat(convert(elem.css("border-top-width")))];
	},

	/* Check positioning to remain on screen. */
	_checkOffset: function(inst, offset, isFixed) {
		var dpWidth = inst.dpDiv.outerWidth(),
			dpHeight = inst.dpDiv.outerHeight(),
			inputWidth = inst.input ? inst.input.outerWidth() : 0,
			inputHeight = inst.input ? inst.input.outerHeight() : 0,
			viewWidth = document.documentElement.clientWidth + (isFixed ? 0 : $(document).scrollLeft()),
			viewHeight = document.documentElement.clientHeight + (isFixed ? 0 : $(document).scrollTop());

		offset.left -= (this._get(inst, "isRTL") ? (dpWidth - inputWidth) : 0);
		offset.left -= (isFixed && offset.left === inst.input.offset().left) ? $(document).scrollLeft() : 0;
		offset.top -= (isFixed && offset.top === (inst.input.offset().top + inputHeight)) ? $(document).scrollTop() : 0;

		// now check if datepicker is showing outside window viewport - move to a better place if so.
		offset.left -= Math.min(offset.left, (offset.left + dpWidth > viewWidth && viewWidth > dpWidth) ?
			Math.abs(offset.left + dpWidth - viewWidth) : 0);
		offset.top -= Math.min(offset.top, (offset.top + dpHeight > viewHeight && viewHeight > dpHeight) ?
			Math.abs(dpHeight + inputHeight) : 0);

		return offset;
	},

	/* Find an object's position on the screen. */
	_findPos: function(obj) {
		var position,
			inst = this._getInst(obj),
			isRTL = this._get(inst, "isRTL");

		while (obj && (obj.type === "hidden" || obj.nodeType !== 1 || $.expr.filters.hidden(obj))) {
			obj = obj[isRTL ? "previousSibling" : "nextSibling"];
		}

		position = $(obj).offset();
		return [position.left, position.top];
	},

	/* Hide the date picker from view.
	 * @param  input  element - the input field attached to the date picker
	 */
	_hideDatepicker: function(input) {
		var showAnim, duration, postProcess, onClose,
			inst = this._curInst;

		if (!inst || (input && inst !== $.data(input, PROP_NAME))) {
			return;
		}

		if (this._datepickerShowing) {
			showAnim = this._get(inst, "showAnim");
			duration = this._get(inst, "duration");
			postProcess = function() {
				$.dtdatepicker._tidyDialog(inst);
			};

			// DEPRECATED: after BC for 1.8.x $.effects[ showAnim ] is not needed
			if ( $.effects && ( $.effects.effect[ showAnim ] || $.effects[ showAnim ] ) ) {
				inst.dpDiv.hide(showAnim, $.dtdatepicker._get(inst, "showOptions"), duration, postProcess);
			} else {
				inst.dpDiv[(showAnim === "slideDown" ? "slideUp" :
					(showAnim === "fadeIn" ? "fadeOut" : "hide"))]((showAnim ? duration : null), postProcess);
			}

			if (!showAnim) {
				postProcess();
			}
			this._datepickerShowing = false;

			onClose = this._get(inst, "onClose");
			if (onClose) {
				onClose.apply((inst.input ? inst.input[0] : null), [(inst.input ? inst.input.val() : ""), inst]);
			}

			this._lastInput = null;
			if (this._inDialog) {
				this._dialogInput.css({ position: "absolute", left: "0", top: "-100px" });
				if ($.blockUI) {
					$.unblockUI();
					$("body").append(this.dpDiv);
				}
			}
			this._inDialog = false;
		}
	},

	/* Tidy up after a dialog display. */
	_tidyDialog: function(inst) {
		inst.dpDiv.removeClass(this._dialogClass).unbind(".ui-datepicker-calendar");
	},

	/* Close date picker if clicked elsewhere. */
	_checkExternalClick: function(event) {
		if (!$.dtdatepicker._curInst) {
			return;
		}

		var $target = $(event.target),
			inst = $.dtdatepicker._getInst($target[0]);

		if ( ( ( $target[0].id !== $.dtdatepicker._mainDivId &&
				$target.parents("#" + $.dtdatepicker._mainDivId).length === 0 &&
				!$target.hasClass($.dtdatepicker.markerClassName) &&
				!$target.closest("." + $.dtdatepicker._triggerClass).length &&
				$.dtdatepicker._datepickerShowing && !($.dtdatepicker._inDialog && $.blockUI) ) ) ||
			( $target.hasClass($.dtdatepicker.markerClassName) && $.dtdatepicker._curInst !== inst ) ) {
				$.dtdatepicker._hideDatepicker();
		}
	},

	/* Adjust one of the date sub-fields. */
	_confirm:function(id){
		if(this.dateTimeStr==""){
			$(".ui-state-highlight").trigger("click");
		}
		var target = $(id);
		var inst = this._getInst(target[0]);
		var time="";
		$(".hhmmssDiv").find("select").find("option:selected").each(function(){
			time+=":"+$(this).text();
		});
		time=time.substring(1, time.length);
		inst.input.val(this.dateTimeStr+" "+time);
		this._hideDatepicker();
	},
	
	/* Adjust one of the date sub-fields. */
	_adjustDate: function(id, offset, period) {
		var target = $(id),
			inst = this._getInst(target[0]);

		if (this._isDisabledDatepicker(target[0])) {
			return;
		}
		this._adjustInstDate(inst, offset +
			(period === "M" ? this._get(inst, "showCurrentAtPos") : 0), // undo positioning
			period);
		this._updateDatepicker(inst);
		// add by xychun 2013/06/26 初始化 日期输入框
		var showTimePanel = this._get(inst, 'showTimePanel');
		if(showTimePanel){
			this._initTimeInput(inst);
		}
	},

	/* Action for current link. */
	_gotoToday: function(id) {
		var date,
			target = $(id),
			inst = this._getInst(target[0]);

		if (this._get(inst, "gotoCurrent") && inst.currentDay) {
			inst.selectedDay = inst.currentDay;
			inst.drawMonth = inst.selectedMonth = inst.currentMonth;
			inst.drawYear = inst.selectedYear = inst.currentYear;
			inst.selectedHour = inst.currentHour;
			inst.selectedMinute = inst.currentMinute;
			inst.selectedSecond = inst.currentSecond;
		} else {
			date = new Date();
			inst.selectedDay = date.getDate();
			inst.drawMonth = inst.selectedMonth = date.getMonth();
			inst.drawYear = inst.selectedYear = date.getFullYear();
			inst.selectedHour = date.getHours();
			inst.selectedMinute = date.getMinutes();
			inst.selectedSecond = date.getSeconds();
		}
		this._notifyChange(inst);
		this._adjustDate(target);
	},

	/* Action for selecting a new month/year. */
	_selectMonthYear: function(id, select, period) {
		var target = $(id),
			inst = this._getInst(target[0]);

		inst["selected" + (period === "M" ? "Month" : "Year")] =
		inst["draw" + (period === "M" ? "Month" : "Year")] =
			parseInt(select.options[select.selectedIndex].value,10);

		this._notifyChange(inst);
		this._adjustDate(target);
	},

	_getHour: function(){
		var hour =  $.trim($("#date_hour").val());
		return $.isNumeric(hour)?parseInt(hour):0;
	},
	_getMinute: function(){
		var minute =  $.trim($("#date_minutes").val());
		return $.isNumeric(minute)?parseInt(minute):0;
	},
	_getSecond: function(){
		var second =  $.trim($("#date_seconds").val());
		return $.isNumeric(second)?parseInt(second):0;
	},
	
	/* Action for selecting a day. */
	_selectDay: function(id, month, year, td) {
		var inst,
			target = $(id);

		if ($(td).hasClass(this._unselectableClass) || this._isDisabledDatepicker(target[0])) {
			return;
		}

		$('table.ui-datepicker-calendar').find('a').removeClass('ui-state-active');
		$("a", td).addClass('ui-state-active');
		
		inst = this._getInst(target[0]);
		inst.selectedDay = inst.currentDay = $("a", td).html();
		inst.selectedMonth = inst.currentMonth = month;
		inst.selectedYear = inst.currentYear = year;
		var showTimePanel = this._get(inst, 'showTimePanel');
		if(showTimePanel){
			inst.selectedHour = inst.currentHour = this._getHour();
			inst.selectedMinute = inst.currentMinute = this._getMinute();
			inst.selectedSecond = inst.currentSecond = this._getSecond();
		}
		else {
			inst.selectedHour = inst.currentHour = 0;
			inst.selectedMinute = inst.currentMinute = 0;
			inst.selectedSecond = inst.currentSecond = 0;
		}
		
		//this._selectDate(id, this._formatDate(inst,inst.currentDay, inst.currentMonth, inst.currentYear));
		this._selectDate(id, this._formatDate(inst,inst.currentDay, inst.currentMonth, inst.currentYear,
				inst.currentHour, inst.currentMinute, inst.currentSecond));
		//modify by xychun 2013/06/26
		/*
		var dateStr=this._formatDate(inst,inst.currentDay, inst.currentMonth, inst.currentYear);
		if(showTimePanel){
			var hour = $("#date_hour").val();
			hour = $.trim(hour);
			var minutes = $("#date_minutes").val();
			minutes = $.trim(minutes);
			var seconds = $("#date_seconds").val();
			seconds = $.trim(seconds);
			if(hour==""){
				hour = "00";
			}
			if(minutes==""){
				minutes="00";
			}
			if(seconds==""){
				seconds="00";
			}
			
			if(isNaN(hour) || isNaN(minutes)|| isNaN(seconds)){
				alert("时间格式不正确！");
				return;
			}
			
			if(hour >= 24){
				hour = 23;
			}
			
			
			if(minutes >= 60){
				minutes = 59;
			}
			
			if(seconds >= 60){
				seconds = 59;
			}
			
			if(hour.length == 1){
				hour = "0" + hour;	
			}
			
			if(minutes.length == 1){
				minutes = "0" + minutes;
			}
			if(seconds.length == 1){
				seconds = "0" + seconds;
			}
			dateStr+=" " + hour + ":" + minutes + ":" + seconds;
		}
		this._selectDate(id, dateStr);
		*/
	},
	
	_hmsChange: function(id){
		var inst,
		target = $(id);
	
		inst = this._getInst(target[0]);
		inst.selectedHour = inst.currentHour = this._getHour();
		inst.selectedMinute = inst.currentMinute = this._getMinute();
		inst.selectedSecond = inst.currentSecond = this._getSecond();
		this._selectDate(id, this._formatDate(inst,inst.currentDay, inst.currentMonth, inst.currentYear,
				inst.currentHour, inst.currentMinute, inst.currentSecond));
		
		//modify by xychun 2013/06/26
		/*
		var dateStr=this._formatDate(inst,inst.currentDay, inst.currentMonth, inst.currentYear);
		var showTimePanel = this._get(inst, 'showTimePanel');
		if(showTimePanel){
			var hour = $("#date_hour").val();
			hour = $.trim(hour);
			var minutes = $("#date_minutes").val();
			minutes = $.trim(minutes);
			var seconds = $("#date_seconds").val();
			seconds = $.trim(seconds);
			if(hour==""){
				hour = "00";
			}
			if(minutes==""){
				minutes="00";
			}
			if(seconds==""){
				seconds="00";
			}
			
			if(isNaN(hour) || isNaN(minutes)|| isNaN(seconds)){
				alert("时间格式不正确！");
				return;
			}
			
			if(hour >= 24){
				hour = 23;
			}
			
			
			if(minutes >= 60){
				minutes = 59;
			}
			
			if(seconds >= 60){
				seconds = 59;
			}
			
			if(hour.length == 1){
				hour = "0" + hour;	
			}
			
			if(minutes.length == 1){
				minutes = "0" + minutes;
			}
			if(seconds.length == 1){
				seconds = "0" + seconds;
			}
			dateStr+=" " + hour + ":" + minutes + ":" + seconds;
		}
		this._selectDate(id, dateStr);
		*/
	},

	/* Erase the input field and hide the date picker. */
	_clearDate: function(id) {
		var target = $(id);
		this._selectDate(target, "");
	},

	/* Update the input field with the selected date. */
	_selectDate: function(id, dateStr) {
		/*
		var onSelect,
			target = $(id),
			inst = this._getInst(target[0]);

		var selectedDom=$("a[text='"+inst.selectedDay+"']");//add by wangdeixng
		var showTimePanel = this._get(inst, 'showTimePanel');//add by wangdeixng
		
		dateStr = (dateStr != null ? dateStr : this._formatDate(inst));
		this.dateTimeStr=dateStr;//add by wangdeixng
		
		if (inst.input&&!showTimePanel) {//add by wangdexing
			inst.input.val(dateStr);
		}
		this._updateAlternate(inst);

		onSelect = this._get(inst, "onSelect");
		if (onSelect) {
			onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);  // trigger custom callback
		} else if (inst.input) {
			inst.input.trigger("change"); // fire the change event
		}

		if (inst.inline){
			this._updateDatepicker(inst);
		} else {
			if(!showTimePanel){//add by wangdexing
				this._hideDatepicker();
			}
			this._lastInput = inst.input[0];
			if (typeof(inst.input[0]) !== "object") {
				inst.input.focus(); // restore focus
			}
			this._lastInput = null;
		}
		*/
		var onSelect,
		target = $(id),
		inst = this._getInst(target[0]);

		dateStr = (dateStr != null ? dateStr : this._formatDate(inst));
		if (inst.input) {
			inst.input.val(dateStr);
		}
		this._updateAlternate(inst);
	
		onSelect = this._get(inst, "onSelect");
		if (onSelect) {
			onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);  // trigger custom callback
		} else if (inst.input) {
			inst.input.trigger("change"); // fire the change event
		}
	
		if (inst.inline){
			this._updateDatepicker(inst);
		} else {
			//modify by xychun 2013/06/26 显示关闭按钮时 点击日不关闭日历控件
			var showButtonPanel = this._get(inst, 'showButtonPanel');
			if (!showButtonPanel) {
				this._hideDatepicker();
				this._lastInput = inst.input[0];
				if (typeof(inst.input[0]) !== "object") {
					inst.input.focus(); // restore focus
				}
				this._lastInput = null;
			}
		}		
	},

	/* Update any alternate field to synchronise with the main field. */
	_updateAlternate: function(inst) {
		var altFormat, date, dateStr,
			altField = this._get(inst, "altField");

		if (altField) { // update alternate field too
			altFormat = this._get(inst, "altFormat") || this._get(inst, "dateFormat");
			date = this._getDate(inst);
			dateStr = this.formatDate(altFormat, date, this._getFormatConfig(inst));
			$(altField).each(function() { $(this).val(dateStr); });
		}
	},

	/* Set as beforeShowDay function to prevent selection of weekends.
	 * @param  date  Date - the date to customise
	 * @return [boolean, string] - is this date selectable?, what is its CSS class?
	 */
	noWeekends: function(date) {
		var day = date.getDay();
		return [(day > 0 && day < 6), ""];
	},

	/* Set as calculateWeek to determine the week of the year based on the ISO 8601 definition.
	 * @param  date  Date - the date to get the week for
	 * @return  number - the number of the week within the year that contains this date
	 */
	iso8601Week: function(date) {
		var time,
			checkDate = new Date(date.getTime());

		// Find Thursday of this week starting on Monday
		checkDate.setDate(checkDate.getDate() + 4 - (checkDate.getDay() || 7));

		time = checkDate.getTime();
		checkDate.setMonth(0); // Compare with Jan 1
		checkDate.setDate(1);
		return Math.floor(Math.round((time - checkDate) / 86400000) / 7) + 1;
	},

	/* Parse a string value into a date object.
	 * See formatDate below for the possible formats.
	 *
	 * @param  format string - the expected format of the date
	 * @param  value string - the date in the above format
	 * @param  settings Object - attributes include:
	 *					shortYearCutoff  number - the cutoff year for determining the century (optional)
	 *					dayNamesShort	string[7] - abbreviated names of the days from Sunday (optional)
	 *					dayNames		string[7] - names of the days from Sunday (optional)
	 *					monthNamesShort string[12] - abbreviated names of the months (optional)
	 *					monthNames		string[12] - names of the months (optional)
	 * @return  Date - the extracted date value or null if value is blank
	 */
	parseDate: function (format, value, settings) {
		if (format == null || value == null) {
			throw "Invalid arguments";
		}

		value = (typeof value === "object" ? value.toString() : value + "");
		if (value === "") {
			return null;
		}

		var iFormat, dim, extra,
			iValue = 0,
			shortYearCutoffTemp = (settings ? settings.shortYearCutoff : null) || this._defaults.shortYearCutoff,
			shortYearCutoff = (typeof shortYearCutoffTemp !== "string" ? shortYearCutoffTemp :
				new Date().getFullYear() % 100 + parseInt(shortYearCutoffTemp, 10)),
			dayNamesShort = (settings ? settings.dayNamesShort : null) || this._defaults.dayNamesShort,
			dayNames = (settings ? settings.dayNames : null) || this._defaults.dayNames,
			monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort,
			monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames,
			year = -1,
			month = -1,
			day = -1,
			doy = -1,
			hour = 0,
			minute = 0,
			second = 0,
			literal = false,
			date,
			// Check whether a format character is doubled
			lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
				if (matches) {
					iFormat++;
				}
				return matches;
			},
			isMinute = function(){
				return (iFormat + 1 < format.length && format.charAt(iFormat + 1) === 'i');
			},
			// Extract a number from the string value
			getNumber = function(match) {
				var isDoubled = lookAhead(match),
					size = (match === "@" ? 14 : (match === "!" ? 20 :
					(match === "y" && isDoubled ? 4 : (match === "o" ? 3 : 2)))),
					digits = new RegExp("^\\d{1," + size + "}"),
					num = value.substring(iValue).match(digits);
				if (!num) {
					throw "Missing number at position " + iValue;
				}
				iValue += num[0].length;
				return parseInt(num[0], 10);
			},
			// Extract a name from the string value and convert to an index
			getName = function(match, shortNames, longNames) {
				var index = -1,
					names = $.map(lookAhead(match) ? longNames : shortNames, function (v, k) {
						return [ [k, v] ];
					}).sort(function (a, b) {
						return -(a[1].length - b[1].length);
					});

				$.each(names, function (i, pair) {
					var name = pair[1];
					if (value.substr(iValue, name.length).toLowerCase() === name.toLowerCase()) {
						index = pair[0];
						iValue += name.length;
						return false;
					}
				});
				if (index !== -1) {
					return index + 1;
				} else {
					throw "Unknown name at position " + iValue;
				}
			},
			// Confirm that a literal character matches the string value
			checkLiteral = function() {
				if (value.charAt(iValue) !== format.charAt(iFormat)) {
					throw "Unexpected literal at position " + iValue;
				}
				iValue++;
			};

		for (iFormat = 0; iFormat < format.length; iFormat++) {
			if (literal) {
				if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
					literal = false;
				} else {
					checkLiteral();
				}
			} else {
				switch (format.charAt(iFormat)) {
					case "d":
						day = getNumber("d");
						break;
					case "D":
						getName("D", dayNamesShort, dayNames);
						break;
					case "o":
						doy = getNumber("o");
						break;
					case "m":
						if (isMinute()){
							minute = getNumber("i");
						}
						else {
							month = getNumber("m");
						}
						//month = getNumber("m");
						break;
					case "M":
						month = getName("M", monthNamesShort, monthNames);
						break;
					case "y":
						year = getNumber("y");
						break;
					case "H":
						hour = getNumber("H");
						break;
					case "s":
						second = getNumber("s");
						break;
					case "@":
						date = new Date(getNumber("@"));
						year = date.getFullYear();
						month = date.getMonth() + 1;
						day = date.getDate();
						break;
					case "!":
						date = new Date((getNumber("!") - this._ticksTo1970) / 10000);
						year = date.getFullYear();
						month = date.getMonth() + 1;
						day = date.getDate();
						break;
					case "'":
						if (lookAhead("'")){
							checkLiteral();
						} else {
							literal = true;
						}
						break;
					default:
						checkLiteral();
				}
			}
		}

		if (iValue < value.length){
			extra = value.substr(iValue);
			if (!/^\s+/.test(extra)) {
				throw "Extra/unparsed characters found in date: " + extra;
			}
		}

		if (year === -1) {
			year = new Date().getFullYear();
		} else if (year < 100) {
			year += new Date().getFullYear() - new Date().getFullYear() % 100 +
				(year <= shortYearCutoff ? 0 : -100);
		}

		if (doy > -1) {
			month = 1;
			day = doy;
			do {
				dim = this._getDaysInMonth(year, month - 1);
				if (day <= dim) {
					break;
				}
				month++;
				day -= dim;
			} while (true);
		}

		date = this._daylightSavingAdjust(new Date(year, month - 1, day, hour, minute, second));
		if (date.getFullYear() !== year || date.getMonth() + 1 !== month || date.getDate() !== day) {
			throw "Invalid date"; // E.g. 31/02/00
		}
		return date;
	},

	/* Standard date formats. */
	ATOM: "yy-mm-dd", // RFC 3339 (ISO 8601)
	COOKIE: "D, dd M yy",
	ISO_8601: "yy-mm-dd",
	RFC_822: "D, d M y",
	RFC_850: "DD, dd-M-y",
	RFC_1036: "D, d M y",
	RFC_1123: "D, d M yy",
	RFC_2822: "D, d M yy",
	RSS: "D, d M y", // RFC 822
	TICKS: "!",
	TIMESTAMP: "@",
	W3C: "yy-mm-dd", // ISO 8601

	_ticksTo1970: (((1970 - 1) * 365 + Math.floor(1970 / 4) - Math.floor(1970 / 100) +
		Math.floor(1970 / 400)) * 24 * 60 * 60 * 10000000),

	/* Format a date object into a string value.
	 * The format can be combinations of the following:
	 * d  - day of month (no leading zero)
	 * dd - day of month (two digit)
	 * o  - day of year (no leading zeros)
	 * oo - day of year (three digit)
	 * D  - day name short
	 * DD - day name long
	 * m  - month of year (no leading zero)
	 * mm - month of year (two digit)
	 * M  - month name short
	 * MM - month name long
	 * y  - year (two digit)
	 * yy - year (four digit)
	 * @ - Unix timestamp (ms since 01/01/1970)
	 * ! - Windows ticks (100ns since 01/01/0001)
	 * "..." - literal text
	 * '' - single quote
	 *
	 * @param  format string - the desired format of the date
	 * @param  date Date - the date value to format
	 * @param  settings Object - attributes include:
	 *					dayNamesShort	string[7] - abbreviated names of the days from Sunday (optional)
	 *					dayNames		string[7] - names of the days from Sunday (optional)
	 *					monthNamesShort string[12] - abbreviated names of the months (optional)
	 *					monthNames		string[12] - names of the months (optional)
	 * @return  string - the date in the above format
	 */
	formatDate: function (format, date, settings) {
		if (!date) {
			return "";
		}

		var iFormat,
			dayNamesShort = (settings ? settings.dayNamesShort : null) || this._defaults.dayNamesShort,
			dayNames = (settings ? settings.dayNames : null) || this._defaults.dayNames,
			monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort,
			monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames,
			// Check whether a format character is doubled
			lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
				if (matches) {
					iFormat++;
				}
				return matches;
			},
			// Format a number, with leading zero if necessary
			formatNumber = function(match, value, len) {
				var num = "" + value;
				if (lookAhead(match)) {
					while (num.length < len) {
						num = "0" + num;
					}
				}
				return num;
			},
			// Format a name, short or long as requested
			formatName = function(match, value, shortNames, longNames) {
				return (lookAhead(match) ? longNames[value] : shortNames[value]);
			},
			isMinute = function(){
				return (iFormat + 1 < format.length && format.charAt(iFormat + 1) === 'i');
			},
			output = "",
			literal = false;

		if (date) {
			for (iFormat = 0; iFormat < format.length; iFormat++) {
				if (literal) {
					if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
						literal = false;
					} else {
						output += format.charAt(iFormat);
					}
				} else {
					switch (format.charAt(iFormat)) {
						case "d":
							output += formatNumber("d", date.getDate(), 2);
							break;
						case "D":
							output += formatName("D", date.getDay(), dayNamesShort, dayNames);
							break;
						case "o":
							output += formatNumber("o",
								Math.round((new Date(date.getFullYear(), date.getMonth(), date.getDate()).getTime() - new Date(date.getFullYear(), 0, 0).getTime()) / 86400000), 3);
							break;
						case "m":
							
							if (isMinute()){
								output += formatNumber("i", date.getMinutes(), 2);
							}
							else {
								output += formatNumber("m", date.getMonth() + 1, 2);
							}
							
							//output += formatNumber("m", date.getMonth() + 1, 2);
							break;
						case "M":
							output += formatName("M", date.getMonth(), monthNamesShort, monthNames);
							break;
						case "y":
							output += (lookAhead("y") ? date.getFullYear() :
								(date.getYear() % 100 < 10 ? "0" : "") + date.getYear() % 100);
							break;
						case "H":
							output += formatNumber("H", date.getHours(), 2);
							break;
						case "s":
							output += formatNumber("s", date.getSeconds(), 2);
							break;
						case "@":
							output += date.getTime();
							break;
						case "!":
							output += date.getTime() * 10000 + this._ticksTo1970;
							break;
						case "'":
							if (lookAhead("'")) {
								output += "'";
							} else {
								literal = true;
							}
							break;
						default:
							output += format.charAt(iFormat);
					}
				}
			}
		}
		return output;
	},

	/* Extract all possible characters from the date format. */
	_possibleChars: function (format) {
		var iFormat,
			chars = "",
			literal = false,
			// Check whether a format character is doubled
			lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) === match);
				if (matches) {
					iFormat++;
				}
				return matches;
			};

		for (iFormat = 0; iFormat < format.length; iFormat++) {
			if (literal) {
				if (format.charAt(iFormat) === "'" && !lookAhead("'")) {
					literal = false;
				} else {
					chars += format.charAt(iFormat);
				}
			} else {
				switch (format.charAt(iFormat)) {
					case "d": case "m": case "y": case "@":
						chars += "0123456789";
						break;
					case "D": case "M":
						return null; // Accept anything
					case "'":
						if (lookAhead("'")) {
							chars += "'";
						} else {
							literal = true;
						}
						break;
					default:
						chars += format.charAt(iFormat);
				}
			}
		}
		return chars;
	},

	/* Get a setting value, defaulting if necessary. */
	_get: function(inst, name) {
		return inst.settings[name] !== undefined ?
			inst.settings[name] : this._defaults[name];
	},

	/* Parse existing date and initialise date picker. */
	_setDateFromField: function(inst, noDefault) {
		if (inst.input.val() === inst.lastVal) {
			return;
		}

		var dateFormat = this._get(inst, "dateFormat"),
			dates = inst.lastVal = inst.input ? inst.input.val() : null,
			defaultDate = this._getDefaultDate(inst),
			date = defaultDate,
			settings = this._getFormatConfig(inst);

		try {
			date = this.parseDate(dateFormat, dates, settings) || defaultDate;
		} catch (event) {
			dates = (noDefault ? "" : dates);
		}
		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		inst.selectedHour = date.getHours();
		inst.selectedMinute = date.getMinutes();
		inst.selectedSecond = date.getSeconds();
		
		inst.currentDay = (dates ? date.getDate() : 0);
		inst.currentMonth = (dates ? date.getMonth() : 0);
		inst.currentYear = (dates ? date.getFullYear() : 0);
		inst.currentHour = (dates ? date.getHours() : 0);
		inst.currentMinute = (dates ? date.getMinutes() : 0);
		inst.currentSecond = (dates ? date.getSeconds() : 0);
		this._adjustInstDate(inst);
	},

	/* Retrieve the default date shown on opening. */
	_getDefaultDate: function(inst) {
		return this._restrictMinMax(inst,
			this._determineDate(inst, this._get(inst, "defaultDate"), new Date()));
	},

	/* A date may be specified as an exact value or a relative one. */
	_determineDate: function(inst, date, defaultDate) {
		var offsetNumeric = function(offset) {
				var date = new Date();
				date.setDate(date.getDate() + offset);
				return date;
			},
			offsetString = function(offset) {
				try {
					return $.dtdatepicker.parseDate($.dtdatepicker._get(inst, "dateFormat"),
						offset, $.dtdatepicker._getFormatConfig(inst));
				}
				catch (e) {
					// Ignore
				}

				var date = (offset.toLowerCase().match(/^c/) ?
					$.dtdatepicker._getDate(inst) : null) || new Date(),
					year = date.getFullYear(),
					month = date.getMonth(),
					day = date.getDate(),
					pattern = /([+\-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g,
					matches = pattern.exec(offset);

				while (matches) {
					switch (matches[2] || "d") {
						case "d" : case "D" :
							day += parseInt(matches[1],10); break;
						case "w" : case "W" :
							day += parseInt(matches[1],10) * 7; break;
						case "m" : case "M" :
							month += parseInt(matches[1],10);
							day = Math.min(day, $.dtdatepicker._getDaysInMonth(year, month));
							break;
						case "y": case "Y" :
							year += parseInt(matches[1],10);
							day = Math.min(day, $.dtdatepicker._getDaysInMonth(year, month));
							break;
					}
					matches = pattern.exec(offset);
				}
				return new Date(year, month, day);
			},
			newDate = (date == null || date === "" ? defaultDate : (typeof date === "string" ? offsetString(date) :
				(typeof date === "number" ? (isNaN(date) ? defaultDate : offsetNumeric(date)) : new Date(date.getTime()))));

		newDate = (newDate && newDate.toString() === "Invalid Date" ? defaultDate : newDate);
		/*
		if (newDate) {
			newDate.setHours(0);
			newDate.setMinutes(0);
			newDate.setSeconds(0);
			newDate.setMilliseconds(0);
		}
		return this._daylightSavingAdjust(newDate);
		*/
		return newDate;
	},

	/* Handle switch to/from daylight saving.
	 * Hours may be non-zero on daylight saving cut-over:
	 * > 12 when midnight changeover, but then cannot generate
	 * midnight datetime, so jump to 1AM, otherwise reset.
	 * @param  date  (Date) the date to check
	 * @return  (Date) the corrected date
	 */
	_daylightSavingAdjust: function(date) {
		if (!date) {
			return null;
		}
		//comment by Xychun 2013-11-19
		//date.setHours(date.getHours() > 12 ? date.getHours() + 2 : 0);
		return date;
	},

	/* Set the date(s) directly. */
	_setDate: function(inst, date, noChange) {
		var clear = !date,
			origMonth = inst.selectedMonth,
			origYear = inst.selectedYear,
			newDate = this._restrictMinMax(inst, this._determineDate(inst, date, new Date()));

		inst.selectedDay = inst.currentDay = newDate.getDate();
		inst.drawMonth = inst.selectedMonth = inst.currentMonth = newDate.getMonth();
		inst.drawYear = inst.selectedYear = inst.currentYear = newDate.getFullYear();
		inst.selectedHour = inst.currentHour = newDate.getHours();
		inst.selectedMinute = inst.currentMinute = newDate.getMinutes();
		inst.selectedSecond = inst.currentSecond = newDate.getSeconds();
		
		if ((origMonth !== inst.selectedMonth || origYear !== inst.selectedYear) && !noChange) {
			this._notifyChange(inst);
		}
		this._adjustInstDate(inst);
		if (inst.input) {
			inst.input.val(clear ? "" : this._formatDate(inst));
			/*
			//modify by xychun 2013/06/26
			
			var showTimePanel=this._get(inst, 'showTimePanel');
			if(showTimePanel){
				var dateStr=this._formatDate(inst);
				var hours=newDate.getHours()+"";
				var minutes=newDate.getMinutes()+"";
				var seconds=newDate.getSeconds()+"";
				
				if(hours.length == 1){
					hours = "0" + hours;	
				}
				if(minutes.length == 1){
					minutes = "0" + minutes;
				}
				if(seconds.length == 1){
					seconds = "0" + seconds;
				}
				dateStr+=" "+hours+":"+minutes+":"+seconds;
				inst.input.val(clear ? '' : dateStr);
				//inst.input.attr("value",clear ? '': dateStr);
				
			}else{
				inst.input.val(clear ? '' : this._formatDate(inst));
				//inst.input.attr("value",clear ? '': this._formatDate(inst));
				
			}
			*/
		}
	},

	/* Retrieve the date(s) directly. */
	_getDate: function(inst) {
		var startDate = (!inst.currentYear || (inst.input && inst.input.val() === "") ? null :
			this._daylightSavingAdjust(new Date(
			inst.currentYear, inst.currentMonth, inst.currentDay, inst.currentHour, inst.currentMinute, inst.currentSecond)));
		//add by xychun 2013/06/26
		/*
		var showTimePanel=this._get(inst, 'showTimePanel');
		if(showTimePanel){
			var now= new Date();
			var hour=now.getHours();
			var minute=now.getMinutes();
			var second=now.getSeconds();
			if(inst.input!=null){
				var val=$.trim(inst.input.val());
				if(val!=null&&val!=''&&val.indexOf(":")>-1){
					var secondIndexBegin=val.lastIndexOf(":");
					second=val.substr(secondIndexBegin+1,2);
					var minuteIndexBegin=(val.substr(0,secondIndexBegin)).lastIndexOf(":");
					minute=val.substr(minuteIndexBegin+1,2);
					hour=val.substr(minuteIndexBegin-2,2);
				}
			}
			startDate = (!inst.currentYear || (inst.input && inst.input.val() == '') ? null :
				new Date(
				inst.currentYear, inst.currentMonth, inst.currentDay,hour,minute,second));
		}
		*/
		return startDate;
	},

	/* Attach the onxxx handlers.  These are declared statically so
	 * they work with static code transformers like Caja.
	 */
	_attachHandlers: function(inst) {
		var stepMonths = this._get(inst, "stepMonths"),
			id = "#" + inst.id.replace( /\\\\/g, "\\" );
		inst.dpDiv.find("[data-handler]").map(function () {
			var handler = {
//				confirm:function(){//add by wangdexing
//					window['DP_jQuery_' + dpuuid].dtdatepicker._confirm(id);
//				},
				prev: function () {
					window["DP_jQuery_" + dpuuid].dtdatepicker._adjustDate(id, -stepMonths, "M");
				},
				next: function () {
					window["DP_jQuery_" + dpuuid].dtdatepicker._adjustDate(id, +stepMonths, "M");
				},
				hide: function () {
					window["DP_jQuery_" + dpuuid].dtdatepicker._hideDatepicker();
				},
				today: function () {
					window["DP_jQuery_" + dpuuid].dtdatepicker._gotoToday(id);
				},
				selectDay: function () {
					//$("a").removeClass("ui-state-active");//add by wangdexing
					//$(this).find("a").attr('class',$(this).find("a").attr('class')+" ui-state-active");//add by wangdexing
					window["DP_jQuery_" + dpuuid].dtdatepicker._selectDay(id, +this.getAttribute("data-month"), +this.getAttribute("data-year"), this);
					return false;
				},
				hmsChange: function() {
					window["DP_jQuery_" + dpuuid].dtdatepicker._hmsChange(id);
					return false;
				},
				selectMonth: function () {
					window["DP_jQuery_" + dpuuid].dtdatepicker._selectMonthYear(id, this, "M");
					return false;
				},
				selectYear: function () {
					window["DP_jQuery_" + dpuuid].dtdatepicker._selectMonthYear(id, this, "Y");
					return false;
				}
			};
			$(this).bind(this.getAttribute("data-event"), handler[this.getAttribute("data-handler")]);
		});
	},
	
	_dateEqual: function(date1, date2, compareTime){
		if (compareTime){
			return date1.getTime() === date2.getTime();
		}
		else {
			return date1.getFullYear() === date2.getFullYear() && date1.getMonth() === date2.getMonth() && date1.getDate() === date2.getDate();
		}
	},

	/* Generate the HTML for the current state of the date picker. */
	_generateHTML: function(inst) {
		var maxDraw, prevText, prev, nextText, next, currentText, gotoDate,
			controls, buttonPanel, firstDay, showWeek, dayNames, dayNamesMin,
			monthNames, monthNamesShort, beforeShowDay, showOtherMonths,
			selectOtherMonths, defaultDate, html, dow, row, group, col, selectedDate,
			cornerClass, calender, thead, day, daysInMonth, leadDays, curRows, numRows,
			printDate, dRow, tbody, daySettings, otherMonth, unselectable,
			tempDate = new Date(),
			today = this._daylightSavingAdjust(
				new Date(tempDate.getFullYear(), tempDate.getMonth(), tempDate.getDate())), // clear time
			isRTL = this._get(inst, "isRTL"),
			showTimePanel = this._get(inst, 'showTimePanel');
			showButtonPanel = this._get(inst, "showButtonPanel"),
			hideIfNoPrevNext = this._get(inst, "hideIfNoPrevNext"),
			navigationAsDateFormat = this._get(inst, "navigationAsDateFormat"),
			numMonths = this._getNumberOfMonths(inst),
			showCurrentAtPos = this._get(inst, "showCurrentAtPos"),
			stepMonths = this._get(inst, "stepMonths"),
			isMultiMonth = (numMonths[0] !== 1 || numMonths[1] !== 1),
			currentDate = this._daylightSavingAdjust((!inst.currentDay ? new Date(9999, 9, 9) :
				new Date(inst.currentYear, inst.currentMonth, inst.currentDay, inst.currentHour, inst.currentMinute, inst.currentSecond))),
			minDate = this._getMinMaxDate(inst, "min"),
			maxDate = this._getMinMaxDate(inst, "max"),
			drawMonth = inst.drawMonth - showCurrentAtPos,
			drawYear = inst.drawYear;

		if (drawMonth < 0) {
			drawMonth += 12;
			drawYear--;
		}
		if (maxDate) {
			maxDraw = this._daylightSavingAdjust(new Date(maxDate.getFullYear(),
				maxDate.getMonth() - (numMonths[0] * numMonths[1]) + 1, maxDate.getDate()));
			maxDraw = (minDate && maxDraw < minDate ? minDate : maxDraw);
			while (this._daylightSavingAdjust(new Date(drawYear, drawMonth, 1)) > maxDraw) {
				drawMonth--;
				if (drawMonth < 0) {
					drawMonth = 11;
					drawYear--;
				}
			}
		}
		inst.drawMonth = drawMonth;
		inst.drawYear = drawYear;

		prevText = this._get(inst, "prevText");
		prevText = (!navigationAsDateFormat ? prevText : this.formatDate(prevText,
			this._daylightSavingAdjust(new Date(drawYear, drawMonth - stepMonths, 1)),
			this._getFormatConfig(inst)));

		//删除span里的prevText文本
		prev = (this._canAdjustMonth(inst, -1, drawYear, drawMonth) ?
			"<a class='ui-datepicker-prev ui-corner-all' data-handler='prev' data-event='click'" +
			" title='" + prevText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "e" : "w") + "'>" + /*prevText +*/ "</span></a>" :
			(hideIfNoPrevNext ? "" : "<a class='ui-datepicker-prev ui-corner-all ui-state-disabled' title='"+ prevText +"'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "e" : "w") + "'>" + /*prevText +*/ "</span></a>"));

		nextText = this._get(inst, "nextText");
		nextText = (!navigationAsDateFormat ? nextText : this.formatDate(nextText,
			this._daylightSavingAdjust(new Date(drawYear, drawMonth + stepMonths, 1)),
			this._getFormatConfig(inst)));
		//删除span里的nextText文本
		next = (this._canAdjustMonth(inst, +1, drawYear, drawMonth) ?
			"<a class='ui-datepicker-next ui-corner-all' data-handler='next' data-event='click'" +
			" title='" + nextText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "w" : "e") + "'>" + /*nextText +*/ "</span></a>" :
			(hideIfNoPrevNext ? "" : "<a class='ui-datepicker-next ui-corner-all ui-state-disabled' title='"+ nextText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "w" : "e") + "'>" + /*nextText +*/ "</span></a>"));

		currentText = this._get(inst, "currentText");
		gotoDate = (this._get(inst, "gotoCurrent") && inst.currentDay ? currentDate : today);
		currentText = (!navigationAsDateFormat ? currentText :
			this.formatDate(currentText, gotoDate, this._getFormatConfig(inst)));

		controls = (!inst.inline ? "<button type='button' class='ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all' data-handler='hide' data-event='click'>" +
			this._get(inst, "closeText") + "</button>" : "");

		buttonPanel = (showButtonPanel) ? "<div class='ui-datepicker-buttonpane ui-widget-content'>" + (isRTL ? controls : "") +
			(this._isInRange(inst, gotoDate) ? "<button type='button' class='ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all' data-handler='today' data-event='click'" +
			">" + currentText + "</button>" : "") + (isRTL ? "" : controls) + "</div>" : "";
	    //add by xychun 2013/06/26
		var timePanel=(showTimePanel) ?'<div class="ui-datepicker-buttonpane ui-widget-content">时:<input type="text" style="width:20px;" value="" id="date_hour" maxlength="2" data-handler="hmsChange" data-event="change">'+
				'&nbsp;&nbsp;&nbsp;&nbsp;分:<input type="text" style="width:20px;" value="" id="date_minutes" maxlength="2" data-handler="hmsChange" data-event="change">&nbsp;&nbsp;&nbsp;&nbsp;秒:<input type="text" style="width:20px;" value="" id="date_seconds" maxlength="2" data-handler="hmsChange" data-event="change"></div>':'';

			//update by wangdexing

		//----add by wangdexing--------- 主要界面样式-----//	
/*		var selectHtml='<div class="ui-datepicker-timepanel ui-widget-content hhmmssDiv">';
			selectHtml+='<select class="ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all" data-event="click">';
		var currentDateTime=new Date();
		var hh=currentDateTime.getHours(),mm=currentDateTime.getMinutes(),ss=currentDateTime.getSeconds();
		for(var h=0;h<24;h++){
			selectHtml+='<option '+(hh==h?'selected="selected"':'')+'>'+(((h+"").length<2)?("0"+h):h)+'</option>';
		}
		selectHtml+='</select>:<select class="ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all" data-event="click">';
		for(var m=0;m<60;m++){
			selectHtml+='<option '+(mm==m?'selected="selected"':'')+'>'+((m+"").length<2?"0"+m:m)+'</option>';
		}	
		selectHtml+='</select>:<select class="ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all" data-event="click">';
		for(var s=0;s<60;s++){
			selectHtml+='<option '+(ss==s?'selected="selected"':'')+'>'+((s+"").length<2?"0"+s:s)+'</option>';
		}	
		selectHtml+='</select>&nbsp;&nbsp;<button type="button" class="ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all" data-handler="confirm" data-event="click">' +this._get(inst, 'confirmText') + '</button></div>';
		var timePanel = (showTimePanel&&!showButtonPanel)? selectHtml:'';
*/			
		//----------------------------------//	
			
			
		firstDay = parseInt(this._get(inst, "firstDay"),10);
		firstDay = (isNaN(firstDay) ? 0 : firstDay);

		showWeek = this._get(inst, "showWeek");
		dayNames = this._get(inst, "dayNames");
		dayNamesMin = this._get(inst, "dayNamesMin");
		monthNames = this._get(inst, "monthNames");
		monthNamesShort = this._get(inst, "monthNamesShort");
		beforeShowDay = this._get(inst, "beforeShowDay");
		showOtherMonths = this._get(inst, "showOtherMonths");
		selectOtherMonths = this._get(inst, "selectOtherMonths");
		defaultDate = this._getDefaultDate(inst);
		html = "";
		dow;
		for (row = 0; row < numMonths[0]; row++) {
			group = "";
			this.maxRows = 4;
			for (col = 0; col < numMonths[1]; col++) {
				selectedDate = this._daylightSavingAdjust(new Date(drawYear, drawMonth, inst.selectedDay, inst.selectedHour, inst.selectedMinute, inst.selectedSecond));
				cornerClass = " ui-corner-all";
				calender = "";
				if (isMultiMonth) {
					calender += "<div class='ui-datepicker-group";
					if (numMonths[1] > 1) {
						switch (col) {
							case 0: calender += " ui-datepicker-group-first";
								cornerClass = " ui-corner-" + (isRTL ? "right" : "left"); break;
							case numMonths[1]-1: calender += " ui-datepicker-group-last";
								cornerClass = " ui-corner-" + (isRTL ? "left" : "right"); break;
							default: calender += " ui-datepicker-group-middle"; cornerClass = ""; break;
						}
					}
					calender += "'>";
				}
				calender += "<div class='ui-datepicker-header ui-widget-header ui-helper-clearfix" + cornerClass + "'>" +
					(/all|left/.test(cornerClass) && row === 0 ? (isRTL ? next : prev) : "") +
					(/all|right/.test(cornerClass) && row === 0 ? (isRTL ? prev : next) : "") +
					this._generateMonthYearHeader(inst, drawMonth, drawYear, minDate, maxDate,
					row > 0 || col > 0, monthNames, monthNamesShort) + // draw month headers
					"</div><table class='ui-datepicker-calendar'><thead>" +
					"<tr>";
				thead = (showWeek ? "<th class='ui-datepicker-week-col'>" + this._get(inst, "weekHeader") + "</th>" : "");
				for (dow = 0; dow < 7; dow++) { // days of the week
					day = (dow + firstDay) % 7;
					thead += "<th" + ((dow + firstDay + 6) % 7 >= 5 ? " class='ui-datepicker-week-end'" : "") + ">" +
						"<span title='" + dayNames[day] + "'>" + dayNamesMin[day] + "</span></th>";
				}
				calender += thead + "</tr></thead><tbody>";
				daysInMonth = this._getDaysInMonth(drawYear, drawMonth);
				if (drawYear === inst.selectedYear && drawMonth === inst.selectedMonth) {
					inst.selectedDay = Math.min(inst.selectedDay, daysInMonth);
				}
				leadDays = (this._getFirstDayOfMonth(drawYear, drawMonth) - firstDay + 7) % 7;
				curRows = Math.ceil((leadDays + daysInMonth) / 7); // calculate the number of rows to generate
				numRows = (isMultiMonth ? this.maxRows > curRows ? this.maxRows : curRows : curRows); //If multiple months, use the higher number of rows (see #7043)
				this.maxRows = numRows;
				printDate = this._daylightSavingAdjust(new Date(drawYear, drawMonth, 1 - leadDays));
				for (dRow = 0; dRow < numRows; dRow++) { // create date picker rows
					calender += "<tr>";
					tbody = (!showWeek ? "" : "<td class='ui-datepicker-week-col'>" +
						this._get(inst, "calculateWeek")(printDate) + "</td>");
					for (dow = 0; dow < 7; dow++) { // create date picker days
						daySettings = (beforeShowDay ?
							beforeShowDay.apply((inst.input ? inst.input[0] : null), [printDate]) : [true, ""]);
						otherMonth = (printDate.getMonth() !== drawMonth);
						unselectable = (otherMonth && !selectOtherMonths) || !daySettings[0] ||
							(minDate && printDate < minDate) || (maxDate && printDate > maxDate);
						tbody += "<td class='" +
							((dow + firstDay + 6) % 7 >= 5 ? " ui-datepicker-week-end" : "") + // highlight weekends
							(otherMonth ? " ui-datepicker-other-month" : "") + // highlight days from other months
							((printDate.getTime() === selectedDate.getTime() && drawMonth === inst.selectedMonth && inst._keyEvent) || // user pressed key
							(defaultDate.getTime() === printDate.getTime() && defaultDate.getTime() === selectedDate.getTime()) ?
							// or defaultDate is current printedDate and defaultDate is selectedDate
							" " + this._dayOverClass : "") + // highlight selected day
							(unselectable ? " " + this._unselectableClass + " ui-state-disabled": "") +  // highlight unselectable days
							(otherMonth && !showOtherMonths ? "" : " " + daySettings[1] + // highlight custom dates
							(printDate.getTime() === currentDate.getTime() ? " " + this._currentClass : "") + // highlight selected day
							(printDate.getTime() === today.getTime() ? " ui-datepicker-today" : "")) + "'" + // highlight today (if different)
							((!otherMonth || showOtherMonths) && daySettings[2] ? " title='" + daySettings[2].replace(/'/g, "&#39;") + "'" : "") + // cell title
							(unselectable ? "" : " data-handler='selectDay' data-event='click' data-month='" + printDate.getMonth() + "' data-year='" + printDate.getFullYear() + "'") + ">" + // actions
							(otherMonth && !showOtherMonths ? "&#xa0;" : // display for other months
							(unselectable ? "<span class='ui-state-default'>" + printDate.getDate() + "</span>" : "<a class='ui-state-default" +
							//(printDate.getTime() === today.getTime() ? " ui-state-highlight" : "") +
							//(printDate.getTime() === currentDate.getTime() ? " ui-state-active" : "") + // highlight selected day
							(this._dateEqual(printDate, today, false) ? " ui-state-highlight" : "") +
							(this._dateEqual(printDate, currentDate, false) ? " ui-state-active" : "") + // highlight selected day
							(otherMonth ? " ui-priority-secondary" : "") + // distinguish dates from other months
							"' href='#'>" + printDate.getDate() + "</a>")) + "</td>"; // display selectable date
						printDate.setDate(printDate.getDate() + 1);
						printDate = this._daylightSavingAdjust(printDate);
					}
					calender += tbody + "</tr>";
				}
				drawMonth++;
				if (drawMonth > 11) {
					drawMonth = 0;
					drawYear++;
				}
				calender += "</tbody></table>" + (isMultiMonth ? "</div>" +
							((numMonths[0] > 0 && col === numMonths[1]-1) ? "<div class='ui-datepicker-row-break'></div>" : "") : "");
				group += calender;
			}
			html += group;
		}
		html = html+timePanel+buttonPanel;
		inst._keyEvent = false;
		return html;
	},

	/* Generate the month and year header. */
	_generateMonthYearHeader: function(inst, drawMonth, drawYear, minDate, maxDate,
			secondary, monthNames, monthNamesShort) {

		var inMinYear, inMaxYear, month, years, thisYear, determineYear, year, endYear,
			changeMonth = this._get(inst, "changeMonth"),
			changeYear = this._get(inst, "changeYear"),
			showMonthAfterYear = this._get(inst, "showMonthAfterYear"),
			html = "<div class='ui-datepicker-title'>",
			monthHtml = "";

		// month selection
		if (secondary || !changeMonth) {
			monthHtml += "<span class='ui-datepicker-month'>" + monthNames[drawMonth] + "</span>";
		} else {
			inMinYear = (minDate && minDate.getFullYear() === drawYear);
			inMaxYear = (maxDate && maxDate.getFullYear() === drawYear);
			monthHtml += "<select class='ui-datepicker-month' data-handler='selectMonth' data-event='change'>";
			for ( month = 0; month < 12; month++) {
				if ((!inMinYear || month >= minDate.getMonth()) && (!inMaxYear || month <= maxDate.getMonth())) {
					monthHtml += "<option value='" + month + "'" +
						(month === drawMonth ? " selected='selected'" : "") +
						">" + monthNamesShort[month] + "</option>";
				}
			}
			monthHtml += "</select>";
		}

		if (!showMonthAfterYear) {
			html += monthHtml + (secondary || !(changeMonth && changeYear) ? "&#xa0;" : "");
		}

		// year selection
		if ( !inst.yearshtml ) {
			inst.yearshtml = "";
			if (secondary || !changeYear) {
				html += "<span class='ui-datepicker-year'>" + drawYear + "</span>";
			} else {
				// determine range of years to display
				years = this._get(inst, "yearRange").split(":");
				thisYear = new Date().getFullYear();
				determineYear = function(value) {
					var year = (value.match(/c[+\-].*/) ? drawYear + parseInt(value.substring(1), 10) :
						(value.match(/[+\-].*/) ? thisYear + parseInt(value, 10) :
						parseInt(value, 10)));
					return (isNaN(year) ? thisYear : year);
				};
				year = determineYear(years[0]);
				endYear = Math.max(year, determineYear(years[1] || ""));
				year = (minDate ? Math.max(year, minDate.getFullYear()) : year);
				endYear = (maxDate ? Math.min(endYear, maxDate.getFullYear()) : endYear);
				inst.yearshtml += "<select class='ui-datepicker-year' data-handler='selectYear' data-event='change'>";
				for (; year <= endYear; year++) {
					inst.yearshtml += "<option value='" + year + "'" +
						(year === drawYear ? " selected='selected'" : "") +
						">" + year + "</option>";
				}
				inst.yearshtml += "</select>";

				html += inst.yearshtml;
				inst.yearshtml = null;
			}
		}

		html += this._get(inst, "yearSuffix");
		if (showMonthAfterYear) {
			html += (secondary || !(changeMonth && changeYear) ? "&#xa0;" : "") + monthHtml;
		}
		html += "</div>"; // Close datepicker_header
		return html;
	},

	/* Adjust one of the date sub-fields. */
	_adjustInstDate: function(inst, offset, period) {
		var year = inst.drawYear + (period === "Y" ? offset : 0),
			month = inst.drawMonth + (period === "M" ? offset : 0),
			day = Math.min(inst.selectedDay, this._getDaysInMonth(year, month)) + (period === "D" ? offset : 0),
			//date = this._restrictMinMax(inst, this._daylightSavingAdjust(new Date(year, month, day)));
			date = this._restrictMinMax(inst, this._daylightSavingAdjust(new Date(year, month, day, 
					inst.selectedHour, inst.selectedMinute, inst.selectedSecond)));

		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		inst.selectedHour = date.getHours();
		inst.selectedMinute = date.getMinutes();
		inst.selectedSecond = date.getSeconds();
		if (period === "M" || period === "Y") {
			this._notifyChange(inst);
		}
	},

	/* Ensure a date is within any min/max bounds. */
	_restrictMinMax: function(inst, date) {
		var minDate = this._getMinMaxDate(inst, "min"),
			maxDate = this._getMinMaxDate(inst, "max"),
			newDate = (minDate && date < minDate ? minDate : date);
		return (maxDate && newDate > maxDate ? maxDate : newDate);
	},

	/* Notify change of month/year. */
	_notifyChange: function(inst) {
		var onChange = this._get(inst, "onChangeMonthYear");
		if (onChange) {
			onChange.apply((inst.input ? inst.input[0] : null),
				[inst.selectedYear, inst.selectedMonth + 1, inst]);
		}
	},

	/* Determine the number of months to show. */
	_getNumberOfMonths: function(inst) {
		var numMonths = this._get(inst, "numberOfMonths");
		return (numMonths == null ? [1, 1] : (typeof numMonths === "number" ? [1, numMonths] : numMonths));
	},

	/* Determine the current maximum date - ensure no time components are set. */
	_getMinMaxDate: function(inst, minMax) {
		return this._determineDate(inst, this._get(inst, minMax + "Date"), null);
	},

	/* Find the number of days in a given month. */
	_getDaysInMonth: function(year, month) {
		return 32 - this._daylightSavingAdjust(new Date(year, month, 32)).getDate();
	},

	/* Find the day of the week of the first of a month. */
	_getFirstDayOfMonth: function(year, month) {
		return new Date(year, month, 1).getDay();
	},

	/* Determines if we should allow a "next/prev" month display change. */
	_canAdjustMonth: function(inst, offset, curYear, curMonth) {
		var numMonths = this._getNumberOfMonths(inst),
			date = this._daylightSavingAdjust(new Date(curYear,
			curMonth + (offset < 0 ? offset : numMonths[0] * numMonths[1]), 1));

		if (offset < 0) {
			date.setDate(this._getDaysInMonth(date.getFullYear(), date.getMonth()));
		}
		return this._isInRange(inst, date);
	},

	/* Is the given date in the accepted range? */
	_isInRange: function(inst, date) {
		var yearSplit, currentYear,
			minDate = this._getMinMaxDate(inst, "min"),
			maxDate = this._getMinMaxDate(inst, "max"),
			minYear = null,
			maxYear = null,
			years = this._get(inst, "yearRange");
			if (years){
				yearSplit = years.split(":");
				currentYear = new Date().getFullYear();
				minYear = parseInt(yearSplit[0], 10);
				maxYear = parseInt(yearSplit[1], 10);
				if ( yearSplit[0].match(/[+\-].*/) ) {
					minYear += currentYear;
				}
				if ( yearSplit[1].match(/[+\-].*/) ) {
					maxYear += currentYear;
				}
			}

		return ((!minDate || date.getTime() >= minDate.getTime()) &&
			(!maxDate || date.getTime() <= maxDate.getTime()) &&
			(!minYear || date.getFullYear() >= minYear) &&
			(!maxYear || date.getFullYear() <= maxYear));
	},

	/* Provide the configuration settings for formatting/parsing. */
	_getFormatConfig: function(inst) {
		var shortYearCutoff = this._get(inst, "shortYearCutoff");
		shortYearCutoff = (typeof shortYearCutoff !== "string" ? shortYearCutoff :
			new Date().getFullYear() % 100 + parseInt(shortYearCutoff, 10));
		return {shortYearCutoff: shortYearCutoff,
			dayNamesShort: this._get(inst, "dayNamesShort"), dayNames: this._get(inst, "dayNames"),
			monthNamesShort: this._get(inst, "monthNamesShort"), monthNames: this._get(inst, "monthNames")};
	},

	/* Format the given date for display. */
	_formatDate: function(inst, day, month, year, hour, minute, second) {
		if (!day) {
			inst.currentDay = inst.selectedDay;
			inst.currentMonth = inst.selectedMonth;
			inst.currentYear = inst.selectedYear;
			inst.currentHour = inst.selectedHour;
			inst.currentMinute = inst.selectedMinute;
			inst.currentSecond = inst.selectedSecond;
		}
		var date = (day ? (typeof day === "object" ? day :
			this._daylightSavingAdjust(new Date(year, month, day, hour, minute, second))) :
			this._daylightSavingAdjust(new Date(inst.currentYear, inst.currentMonth, inst.currentDay,inst.currentHour, inst.currentMinute, inst.currentSecond)));
		return this.formatDate(this._get(inst, "dateFormat"), date, this._getFormatConfig(inst));
	}
});

/*
 * Bind hover events for datepicker elements.
 * Done via delegate so the binding only occurs once in the lifetime of the parent div.
 * Global instActive, set by _updateDatepicker allows the handlers to find their way back to the active picker.
 */
function bindHover(dpDiv) {
	var selector = "button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a";
	return dpDiv.delegate(selector, "mouseout", function() {
			$(this).removeClass("ui-state-hover");
			if (this.className.indexOf("ui-datepicker-prev") !== -1) {
				$(this).removeClass("ui-datepicker-prev-hover");
			}
			if (this.className.indexOf("ui-datepicker-next") !== -1) {
				$(this).removeClass("ui-datepicker-next-hover");
			}
		})
		.delegate(selector, "mouseover", function(){
			if (!$.dtdatepicker._isDisabledDatepicker( instActive.inline ? dpDiv.parent()[0] : instActive.input[0])) {
				$(this).parents(".ui-datepicker-calendar").find("a").removeClass("ui-state-hover");
				$(this).addClass("ui-state-hover");
				if (this.className.indexOf("ui-datepicker-prev") !== -1) {
					$(this).addClass("ui-datepicker-prev-hover");
				}
				if (this.className.indexOf("ui-datepicker-next") !== -1) {
					$(this).addClass("ui-datepicker-next-hover");
				}
			}
		});
}

/* jQuery extend now ignores nulls! */
function extendRemove(target, props) {
	$.extend(target, props);
	for (var name in props) {
		if (props[name] == null) {
			target[name] = props[name];
		}
	}
	return target;
}

/* Invoke the datepicker functionality.
   @param  options  string - a command, optionally followed by additional parameters or
					Object - settings for attaching new datepicker functionality
   @return  jQuery object */
$.fn.dtdatepicker = function(options){

	/* Verify an empty collection wasn't passed - Fixes #6976 */
	if ( !this.length ) {
		return this;
	}

	/* Initialise the date picker. */
	if (!$.dtdatepicker.initialized) {
		$(document).mousedown($.dtdatepicker._checkExternalClick);
		$.dtdatepicker.initialized = true;
	}

	/* Append datepicker main container to body if not exist. */
	if ($("#"+$.dtdatepicker._mainDivId).length === 0) {
		$("body").append($.dtdatepicker.dpDiv);
	}

	var otherArgs = Array.prototype.slice.call(arguments, 1);
	if (typeof options === "string" && (options === "isDisabled" || options === "getDate" || options === "widget")) {
		return $.dtdatepicker["_" + options + "Datepicker"].
			apply($.dtdatepicker, [this[0]].concat(otherArgs));
	}
	if (options === "option" && arguments.length === 2 && typeof arguments[1] === "string") {
		return $.dtdatepicker["_" + options + "Datepicker"].
			apply($.dtdatepicker, [this[0]].concat(otherArgs));
	}
	return this.each(function() {
		typeof options === "string" ?
			$.dtdatepicker["_" + options + "Datepicker"].
				apply($.dtdatepicker, [this].concat(otherArgs)) :
			$.dtdatepicker._attachDatepicker(this, options);
	});
};

$.dtdatepicker = new DtDatepicker(); // singleton instance
$.dtdatepicker.initialized = false;
$.dtdatepicker.uuid = new Date().getTime();
$.dtdatepicker.version = "1.10.1";

// Workaround for #4055
// Add another global to avoid noConflict issues with inline event handlers
window["DP_jQuery_" + dpuuid] = $;

})(jQuery);/**
 * 
 */

(function($, undefined) {
	var sizeRelatedOptions = {
			buttons: true,
			height: true,
			maxHeight: true,
			maxWidth: true,
			minHeight: true,
			minWidth: true,
			width: true
		},
		resizableRelatedOptions = {
			maxHeight: true,
			maxWidth: true,
			minHeight: true,
			minWidth: true
		};

	$.widget( "dtui.dtdialog", {
		version: "1.10.1",
		options: {
			appendTo: "body",
			autoOpen: true,
			buttons: [],
			closeOnEscape: true,
			closeText: "close",
			dialogClass: "",
			draggable: true,
			hide: null,
			height: "auto",
			maxHeight: null,
			maxWidth: null,
			minHeight: 150,
			minWidth: 150,
			modal: false,
			position: {
				my: "center",
				at: "center",
				of: window,
				collision: "fit",
				// Ensure the titlebar is always visible
				using: function( pos ) {
					var topOffset = $( this ).css( pos ).offset().top;
					if ( topOffset < 0 ) {
						$( this ).css( "top", pos.top - topOffset );
					}
				}
			},
			resizable: true,
			show: null,
			title: null,
			width: 300,
			//add by xychun 2013-04-23
			url: null,
			//关闭对话框是否释放对话框占用资源
			autoDestroy:false,
			//释放资源时是否删除对话框中的dom元素
			removeDomElem:false,
			//是否显示右上角的关闭图标
			showCloseIcon: true,

			// callbacks
			beforeClose: null,
			close: null,
			drag: null,
			dragStart: null,
			dragStop: null,
			focus: null,
			open: null,
			resize: null,
			resizeStart: null,
			resizeStop: null,
			load: null
		},

		_create: function() {
			var that = this;
			
			this.originalCss = {
				display: this.element[0].style.display,
				width: this.element[0].style.width,
				minHeight: this.element[0].style.minHeight,
				maxHeight: this.element[0].style.maxHeight,
				height: this.element[0].style.height
			};
			this.originalPosition = {
				parent: this.element.parent(),
				index: this.element.parent().children().index( this.element )
			};
			this.originalTitle = this.element.attr("title");
			this.options.title = this.options.title || this.originalTitle;

			this._createWrapper();

			this.element
				.show()
				.removeAttr("title")
				.addClass("ui-dialog-content ui-widget-content")
				.appendTo( this.uiDialog );
			
/*			if (this.options.url){
				this.element.load(this.options.url,this.options.param, function(){
					that._trigger( "load");
				});
			}
*/
			this._createTitlebar();
			this._createButtonPane();

			if ( this.options.draggable && $.fn.draggable ) {
				this._makeDraggable();
			}
			if ( this.options.resizable && $.fn.resizable ) {
				this._makeResizable();
			}

			this._isOpen = false;
		},

		_init: function() {
			if ( this.options.autoOpen ) {
				this.open();
			}
			var that = this;
			if (this.options.url){
				this.element.load(this.options.url,this.options.param, function(){
					that._trigger( "load");
				});
				//同步请求
				/*
				jQuery.ajax({
					url: this.options.url,
					type: "GET",
					dataType: "html",
					async: false
				}).done(function( responseText ) {
					that.element.html(responseText);
					that._trigger( "load");
				});
				*/
			}

		},

		_appendTo: function() {
			var element = this.options.appendTo;
			if ( element && (element.jquery || element.nodeType) ) {
				return $( element );
			}
			return this.document.find( element || "body" ).eq( 0 );
		},

		_destroy: function() {
			var next,
				originalPosition = this.originalPosition;

			this._destroyOverlay();

			this.element
				.removeUniqueId()
				.removeClass("ui-dialog-content ui-widget-content")
				.css( this.originalCss )
				// Without detaching first, the following becomes really slow
				.detach();

			this.uiDialog.stop( true, true ).remove();

			if (this.options.removeDomElem !== true) {
				if ( this.originalTitle ) {
					this.element.attr( "title", this.originalTitle );
				}
	
				next = originalPosition.parent.children().eq( originalPosition.index );
				// Don't try to place the dialog next to itself (#8613)
				if ( next.length && next[0] !== this.element[0] ) {
					next.before( this.element );
				} else {
					originalPosition.parent.append( this.element );
				}
			}
		},

		widget: function() {
			return this.uiDialog;
		},

		disable: $.noop,
		enable: $.noop,
		
		hide: function(){
			this._isOpen = false;
			this._destroyOverlay();

			if ( !this.opener.filter(":focusable").focus().length ) {
				// Hiding a focused element doesn't trigger blur in WebKit
				// so in case we have nothing to focus on, explicitly blur the active element
				// https://bugs.webkit.org/show_bug.cgi?id=47182
				$( this.document[0].activeElement ).blur();
			}
			//this.uiDialog.hide();
			this._hide( this.uiDialog, this.options.hide);
		},
		
		close: function( event ) {
			var that = this;

			if ( !this._isOpen || this._trigger( "beforeClose", event ) === false ) {
				return;
			}

			this._isOpen = false;
			this._destroyOverlay();

			if ( !this.opener.filter(":focusable").focus().length ) {
				// Hiding a focused element doesn't trigger blur in WebKit
				// so in case we have nothing to focus on, explicitly blur the active element
				// https://bugs.webkit.org/show_bug.cgi?id=47182
				$( this.document[0].activeElement ).blur();
			}

			this._hide( this.uiDialog, this.options.hide, function() {
				that._trigger( "close", event );
			});
			
			//add by xychun 2011-07-13
			if (that.options.autoDestroy)
				that.destroy();
			
		},

		isOpen: function() {
			return this._isOpen;
		},

		moveToTop: function() {
			this._moveToTop();
		},

		_moveToTop: function( event, silent ) {
			var moved = !!this.uiDialog.nextAll(":visible").insertBefore( this.uiDialog ).length;
			if ( moved && !silent ) {
				this._trigger( "focus", event );
			}
			return moved;
		},

		open: function() {
			var that = this;
			if ( this._isOpen ) {
				if ( this._moveToTop() ) {
					this._focusTabbable();
				}
				return;
			}

			this._isOpen = true;
			this.opener = $( this.document[0].activeElement );

			this._size();
			this._position();
			this._createOverlay();
			this._moveToTop( null, true );
			this._show( this.uiDialog, this.options.show, function() {
				that._focusTabbable();
				that._trigger("focus");
			});

			this._trigger("open");
		},
		
		show: function(){
			this.option('modal',false);
			this.open();
		},
		
		showModal: function(){
			this.option('modal',true);
			this.open();
		},

		_focusTabbable: function() {
			// Set focus to the first match:
			// 1. First element inside the dialog matching [autofocus]
			// 2. Tabbable element inside the content element
			// 3. Tabbable element inside the buttonpane
			// 4. The close button
			// 5. The dialog itself
			var hasFocus = this.element.find("[autofocus]");
			if ( !hasFocus.length ) {
				hasFocus = this.element.find(":tabbable");
			}
			if ( !hasFocus.length ) {
				hasFocus = this.uiDialogButtonPane.find(":tabbable");
			}
			if ( !hasFocus.length ) {
				hasFocus = this.uiDialogTitlebarClose.filter(":tabbable");
			}
			if ( !hasFocus.length ) {
				hasFocus = this.uiDialog;
			}
			hasFocus.eq( 0 ).focus();
		},

		_keepFocus: function( event ) {
			function checkFocus() {
				var activeElement = this.document[0].activeElement,
					isActive = this.uiDialog[0] === activeElement ||
						$.contains( this.uiDialog[0], activeElement );
				if ( !isActive ) {
					this._focusTabbable();
				}
			}
			event.preventDefault();
			checkFocus.call( this );
			// support: IE
			// IE <= 8 doesn't prevent moving focus even with event.preventDefault()
			// so we check again later
			this._delay( checkFocus );
		},

		_createWrapper: function() {
			this.uiDialog = $("<div>")
				.addClass( "ui-dialog ui-widget ui-widget-content ui-corner-all ui-front " +
					this.options.dialogClass )
				.hide()
				.attr({
					// Setting tabIndex makes the div focusable
					tabIndex: -1,
					role: "dialog"
				})
				.appendTo( this._appendTo() );

			this._on( this.uiDialog, {
				keydown: function( event ) {
					if ( this.options.closeOnEscape && !event.isDefaultPrevented() && event.keyCode &&
							event.keyCode === $.ui.keyCode.ESCAPE ) {
						event.preventDefault();
						this.close( event );
						return;
					}

					// prevent tabbing out of dialogs
					if ( event.keyCode !== $.ui.keyCode.TAB ) {
						return;
					}
					var tabbables = this.uiDialog.find(":tabbable"),
						first = tabbables.filter(":first"),
						last  = tabbables.filter(":last");

					if ( ( event.target === last[0] || event.target === this.uiDialog[0] ) && !event.shiftKey ) {
						first.focus( 1 );
						event.preventDefault();
					} else if ( ( event.target === first[0] || event.target === this.uiDialog[0] ) && event.shiftKey ) {
						last.focus( 1 );
						event.preventDefault();
					}
				},
				mousedown: function( event ) {
					if ( this._moveToTop( event ) ) {
						this._focusTabbable();
					}
				}
			});

			// We assume that any existing aria-describedby attribute means
			// that the dialog content is marked up properly
			// otherwise we brute force the content as the description
			if ( !this.element.find("[aria-describedby]").length ) {
				this.uiDialog.attr({
					"aria-describedby": this.element.uniqueId().attr("id")
				});
			}
		},

		_createTitlebar: function() {
			var uiDialogTitle;

			this.uiDialogTitlebar = $("<div>")
				.addClass("ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix")
				.prependTo( this.uiDialog );
			this._on( this.uiDialogTitlebar, {
				mousedown: function( event ) {
					// Don't prevent click on close button (#8838)
					// Focusing a dialog that is partially scrolled out of view
					// causes the browser to scroll it into view, preventing the click event
					if ( !$( event.target ).closest(".ui-dialog-titlebar-close") ) {
						// Dialog isn't getting focus when dragging (#8063)
						this.uiDialog.focus();
					}
				}
			});

			if (this.options.showCloseIcon) {
				this.uiDialogTitlebarClose = $("<button></button>")
					.button({
						label: this.options.closeText,
						icons: {
							primary: "ui-icon-closethick"
						},
						text: false
					})
					.addClass("ui-dialog-titlebar-close")
					.appendTo( this.uiDialogTitlebar );
				this._on( this.uiDialogTitlebarClose, {
					click: function( event ) {
						event.preventDefault();
						this.close( event );
					}
				});
			};

			uiDialogTitle = $("<span>")
				.uniqueId()
				.addClass("ui-dialog-title")
				.prependTo( this.uiDialogTitlebar );
			this._title( uiDialogTitle );

			this.uiDialog.attr({
				"aria-labelledby": uiDialogTitle.attr("id")
			});
		},

		_title: function( title ) {
			if ( !this.options.title ) {
				title.html("&#160;");
			}
			title.text( this.options.title );
		},

		_createButtonPane: function() {
			this.uiDialogButtonPane = $("<div>")
				.addClass("ui-dialog-buttonpane ui-widget-content ui-helper-clearfix");

			this.uiButtonSet = $("<div>")
				.addClass("ui-dialog-buttonset")
				.appendTo( this.uiDialogButtonPane );

			this._createButtons();
		},

		_createButtons: function() {
			var that = this,
				buttons = this.options.buttons;

			// if we already have a button pane, remove it
			this.uiDialogButtonPane.remove();
			this.uiButtonSet.empty();

			if ( $.isEmptyObject( buttons ) || ($.isArray( buttons ) && !buttons.length) ) {
				this.uiDialog.removeClass("ui-dialog-buttons");
				return;
			}

			$.each( buttons, function( name, props ) {
				var click, buttonOptions;
				props = $.isFunction( props ) ?
					{ click: props, text: name } :
					props;
				// Default to a non-submitting button
				props = $.extend( { type: "button" }, props );
				// Change the context for the click callback to be the main element
				click = props.click;
				props.click = function() {
					click.apply( that.element[0], arguments );
				};
				buttonOptions = {
					icons: props.icons,
					text: props.showText
				};
				delete props.icons;
				delete props.showText;
				$( "<button></button>", props )
					.button( buttonOptions )
					.appendTo( that.uiButtonSet );
			});
			this.uiDialog.addClass("ui-dialog-buttons");
			this.uiDialogButtonPane.appendTo( this.uiDialog );
		},

		_makeDraggable: function() {
			var that = this,
				options = this.options;

			function filteredUi( ui ) {
				return {
					position: ui.position,
					offset: ui.offset
				};
			}

			this.uiDialog.draggable({
				cancel: ".ui-dialog-content, .ui-dialog-titlebar-close",
				handle: ".ui-dialog-titlebar",
				containment: "document",
				start: function( event, ui ) {
					$( this ).addClass("ui-dialog-dragging");
					that._blockFrames();
					that._trigger( "dragStart", event, filteredUi( ui ) );
				},
				drag: function( event, ui ) {
					that._trigger( "drag", event, filteredUi( ui ) );
				},
				stop: function( event, ui ) {
					options.position = [
						ui.position.left - that.document.scrollLeft(),
						ui.position.top - that.document.scrollTop()
					];
					$( this ).removeClass("ui-dialog-dragging");
					that._unblockFrames();
					that._trigger( "dragStop", event, filteredUi( ui ) );
				}
			});
		},

		_makeResizable: function() {
			var that = this,
				options = this.options,
				handles = options.resizable,
				// .ui-resizable has position: relative defined in the stylesheet
				// but dialogs have to use absolute or fixed positioning
				position = this.uiDialog.css("position"),
				resizeHandles = typeof handles === "string" ?
					handles	:
					"n,e,s,w,se,sw,ne,nw";

			function filteredUi( ui ) {
				return {
					originalPosition: ui.originalPosition,
					originalSize: ui.originalSize,
					position: ui.position,
					size: ui.size
				};
			}

			this.uiDialog.resizable({
				cancel: ".ui-dialog-content",
				containment: "document",
				alsoResize: this.element,
				maxWidth: options.maxWidth,
				maxHeight: options.maxHeight,
				minWidth: options.minWidth,
				minHeight: this._minHeight(),
				handles: resizeHandles,
				start: function( event, ui ) {
					$( this ).addClass("ui-dialog-resizing");
					that._blockFrames();
					that._trigger( "resizeStart", event, filteredUi( ui ) );
				},
				resize: function( event, ui ) {
					that._trigger( "resize", event, filteredUi( ui ) );
				},
				stop: function( event, ui ) {
					options.height = $( this ).height();
					options.width = $( this ).width();
					$( this ).removeClass("ui-dialog-resizing");
					that._unblockFrames();
					that._trigger( "resizeStop", event, filteredUi( ui ) );
				}
			})
			.css( "position", position );
		},

		_minHeight: function() {
			var options = this.options;

			return options.height === "auto" ?
				options.minHeight :
				Math.min( options.minHeight, options.height );
		},

		_position: function() {
			// Need to show the dialog to get the actual offset in the position plugin
			var isVisible = this.uiDialog.is(":visible");
			if ( !isVisible ) {
				this.uiDialog.show();
			}
			this.uiDialog.position( this.options.position );
			if ( !isVisible ) {
				this.uiDialog.hide();
			}
		},

		_setOptions: function( options ) {
			var that = this,
				resize = false,
				resizableOptions = {};

			$.each( options, function( key, value ) {
				that._setOption( key, value );

				if ( key in sizeRelatedOptions ) {
					resize = true;
				}
				if ( key in resizableRelatedOptions ) {
					resizableOptions[ key ] = value;
				}
			});

			if ( resize ) {
				this._size();
				this._position();
			}
			if ( this.uiDialog.is(":data(ui-resizable)") ) {
				this.uiDialog.resizable( "option", resizableOptions );
			}
		},

		_setOption: function( key, value ) {
			/*jshint maxcomplexity:15*/
			var isDraggable, isResizable,
				uiDialog = this.uiDialog;

			if ( key === "dialogClass" ) {
				uiDialog
					.removeClass( this.options.dialogClass )
					.addClass( value );
			}

			if ( key === "disabled" ) {
				return;
			}

			this._super( key, value );

			if ( key === "appendTo" ) {
				this.uiDialog.appendTo( this._appendTo() );
			}

			if ( key === "buttons" ) {
				this._createButtons();
			}

			if ( key === "closeText" ) {
				this.uiDialogTitlebarClose.button({
					// Ensure that we always pass a string
					label: "" + value
				});
			}

			if ( key === "draggable" ) {
				isDraggable = uiDialog.is(":data(ui-draggable)");
				if ( isDraggable && !value ) {
					uiDialog.draggable("destroy");
				}

				if ( !isDraggable && value ) {
					this._makeDraggable();
				}
			}

			if ( key === "position" ) {
				this._position();
			}

			if ( key === "resizable" ) {
				// currently resizable, becoming non-resizable
				isResizable = uiDialog.is(":data(ui-resizable)");
				if ( isResizable && !value ) {
					uiDialog.resizable("destroy");
				}

				// currently resizable, changing handles
				if ( isResizable && typeof value === "string" ) {
					uiDialog.resizable( "option", "handles", value );
				}

				// currently non-resizable, becoming resizable
				if ( !isResizable && value !== false ) {
					this._makeResizable();
				}
			}

			if ( key === "title" ) {
				this._title( this.uiDialogTitlebar.find(".ui-dialog-title") );
			}
		},

		_size: function() {
			// If the user has resized the dialog, the .ui-dialog and .ui-dialog-content
			// divs will both have width and height set, so we need to reset them
			var nonContentHeight, minContentHeight, maxContentHeight,
				options = this.options;

			// Reset content sizing
			this.element.show().css({
				width: "auto",
				minHeight: 0,
				maxHeight: "none",
				height: 0
			});

			if ( options.minWidth > options.width ) {
				options.width = options.minWidth;
			}

			// reset wrapper sizing
			// determine the height of all the non-content elements
			nonContentHeight = this.uiDialog.css({
					height: "auto",
					width: options.width
				})
				.outerHeight();
			minContentHeight = Math.max( 0, options.minHeight - nonContentHeight );
			maxContentHeight = typeof options.maxHeight === "number" ?
				Math.max( 0, options.maxHeight - nonContentHeight ) :
				"none";

			if ( options.height === "auto" ) {
				this.element.css({
					minHeight: minContentHeight,
					maxHeight: maxContentHeight,
					height: "auto"
				});
			} else {
				this.element.height( Math.max( 0, options.height - nonContentHeight ) );
			}

			if (this.uiDialog.is(":data(ui-resizable)") ) {
				this.uiDialog.resizable( "option", "minHeight", this._minHeight() );
			}
		},

		_blockFrames: function() {
			this.iframeBlocks = this.document.find( "iframe" ).map(function() {
				var iframe = $( this );

				return $( "<div>" )
					.css({
						position: "absolute",
						width: iframe.outerWidth(),
						height: iframe.outerHeight()
					})
					.appendTo( iframe.parent() )
					.offset( iframe.offset() )[0];
			});
		},

		_unblockFrames: function() {
			if ( this.iframeBlocks ) {
				this.iframeBlocks.remove();
				delete this.iframeBlocks;
			}
		},

		_createOverlay: function() {
			if ( !this.options.modal ) {
				return;
			}

			var widgetFullName = this.widgetFullName;
			if ( !$.dtui.dtdialog.overlayInstances ) {
				// Prevent use of anchors and inputs.
				// We use a delay in case the overlay is created from an
				// event that we're going to be cancelling. (#2804)
				this._delay(function() {
					// Handle .dialog().dialog("close") (#4065)
					if ( $.dtui.dtdialog.overlayInstances ) {
						this.document.bind( "focusin.dialog", function( event ) {
							if ( !$( event.target ).closest(".ui-dialog").length &&
									// TODO: Remove hack when datepicker implements
									// the .ui-front logic (#8989)
									!$( event.target ).closest(".ui-datepicker").length ) {
								event.preventDefault();
								$(".ui-dialog:visible:last .ui-dialog-content")
									.data(widgetFullName)._focusTabbable();
							}
						});
					}
				});
			}

			this.overlay = $("<div>")
				.addClass("ui-widget-overlay ui-front")
				.appendTo( this._appendTo() );
			this._on( this.overlay, {
				mousedown: "_keepFocus"
			});
			$.dtui.dtdialog.overlayInstances++;
		},

		_destroyOverlay: function() {
			if ( !this.options.modal ) {
				return;
			}

			if ( this.overlay ) {
				$.dtui.dtdialog.overlayInstances--;

				if ( !$.dtui.dtdialog.overlayInstances ) {
					this.document.unbind( "focusin.dialog" );
				}
				this.overlay.remove();
				this.overlay = null;
			}
		},
		
		/**
		 * 获取表单数据
		 * @param nameIsId 是否使用元素的id属性作为name返回
		 * @returns
		 */
		getData: function(nameIsId) {
			/*
			return $.map($(":input", this.element), function(elem){
				return {name:nameIsId?elem.id:elem.name, value: $(elem).val()};
			});
			*/
			var result = {};
			$.each($(":input", this.element),function(i, elem){
				if ($(elem).prop('disabled')!=true){
					var key = nameIsId?elem.id:elem.name;
					result[key] = $(elem).val();
				}
			});
			return result;
		},
		
		setData: function(data, nameIsId) {
			function assignValue(name, value){
				if (nameIsId){
					$('#'+name, this.element).val(value);								
				}
				else {
					$("[name='"+name+"']", this.element).val(value);	
				}
			};
			
			if ( jQuery.isArray( data ) ) {
				jQuery.each( data, function() {
					assignValue( this.name, this.value );
				});

			} else {
				for ( prefix in data ) {
					assignValue( prefix, data[ prefix ]);
				}
			}
		}
	});

	$.dtui.dtdialog.overlayInstances = 0;

	// DEPRECATED
	if ( $.uiBackCompat !== false ) {
		// position option with array notation
		// just override with old implementation
		$.widget( "dtui.dtdialog", $.dtui.dtdialog, {
			_position: function() {
				var position = this.options.position,
					myAt = [],
					offset = [ 0, 0 ],
					isVisible;

				if ( position ) {
					if ( typeof position === "string" || (typeof position === "object" && "0" in position ) ) {
						myAt = position.split ? position.split(" ") : [ position[0], position[1] ];
						if ( myAt.length === 1 ) {
							myAt[1] = myAt[0];
						}

						$.each( [ "left", "top" ], function( i, offsetPosition ) {
							if ( +myAt[ i ] === myAt[ i ] ) {
								offset[ i ] = myAt[ i ];
								myAt[ i ] = offsetPosition;
							}
						});

						position = {
							my: myAt[0] + (offset[0] < 0 ? offset[0] : "+" + offset[0]) + " " +
								myAt[1] + (offset[1] < 0 ? offset[1] : "+" + offset[1]),
							at: myAt.join(" ")
						};
					}

					position = $.extend( {}, $.ui.dialog.prototype.options.position, position );
				} else {
					position = $.ui.dialog.prototype.options.position;
				}

				// need to show the dialog to get the actual offset in the position plugin
				isVisible = this.uiDialog.is(":visible");
				if ( !isVisible ) {
					this.uiDialog.show();
				}
				this.uiDialog.position( position );
				if ( !isVisible ) {
					this.uiDialog.hide();
				}
			}
		});
	};	
	
	$.dtdialog = {
		DR_OK : "drOk",
		DR_CANCEL : "drCancel",
		show : function(options, win) {
			var opt={autoDestroy:true, removeDomElem:true, modal:false};
			opt = $.extend(options || {}, opt);
			if (win){
				return win.$('<div></div>').dtdialog(opt);
			}
			else {
				return $('<div></div>').dtdialog(opt);
			}
		},
		showModal : function(options, win) {
			var opt={autoDestroy:true, removeDomElem:true, modal:true};
			opt = $.extend(options || {}, opt);
			if (win){
				return win.$('<div></div>').dtdialog(opt);
			}
			else {
				return $('<div></div>').dtdialog(opt);
			}
		},
		closeDialog: function(selector,retObject) {
			var dlg;
			if ($(selector).hasClass('ui-dialog')) {
				dlg = $(selector);
			}
			else {
				//parentsUntil返回selector所有父对象
				dlg = $(selector).parentsUntil('.ui-dialog');
				//最后一个是对话框对象
				dlg = $(dlg[dlg.length-1]);
			}
			if (retObject) {
				dlg.dtdialog('option', retObject);
			}
			dlg.dtdialog('close');
		}	
	};

})(jQuery);(function($,undefined){

	$.widget("dtui.dtfileupload",{
		options:{
			total:'',//文件总数
			files:{},
			maxNumber:3,
			action:'Upload',
		    lastSuffix:1,
		    fileControlName:'file',
		    label:'添加附件'
		},
		
		_create:function(){
			var suffix=1;
			this._e={
					/*container:$('<form  id="form_action" action="" method="multipart/form-data" class="upload-container"></form>'),*/
						/*upload_header:$('<div class="upload-header-extend" ><span  id="ext-gen72">文件上传</span></div>'),*/
						upload_center:$('<div class="x-panel-bwrap" ></div>'),
							upload_center_header:$('<div class="upload-header-extend upload-header"></div>'),
								upload_center_header_child1:$('<input id="file_hidden1" type="file" size=1 name="'+this.options.fileControlName+'" class="upload-file-hidden transparent_class" />'),
								upload_center_header_child2:$('<a id="add_file"  class="upload-file-button"><img id="fileimage" class="upload-file-add"/></a>').text(this.options.label),
								upload_center_header_child3:$('<p  class="upload-file-button"></p>'),
								
								upload_center_header_child6:$('<a id="delete_file" class="upload-file-button"><img class="upload-file-clean"/>清空列表</a>'),
							upload_center_center:$('<div ></div>'),
								upload_center_center_header:$('<div class="upload-file-contenttitle upload-header"></div>'),
									upload_center_center_header_child1:$('<label class="upload-file-label1  upload-head">文件名</label>'),
									upload_center_center_header_child2:$('<label class="upload-file-label2  upload-head">状态</label>'),
								upload_center_center_content:$('<div class="upload-file-table" id="file_content"></div>'),
						upload_bottom:$('<div class="upload-header-extend" style="text-align:right;"><span id="leftNum">99</span>/<span id="MaxNum">99</span></div>')
			      };
			
			this._createElement();
			this._setFilePosition(suffix);
			this._bindEvent(suffix);
			
		},
		
		_createElement:function(){
			var container$=$(this.element);
			container$.addClass("upload-container");
			/*container$.append(this._e.upload_header);*/
			container$.append(this._e.upload_center);
			container$.append(this._e.upload_bottom);
			this._e.upload_center.append(this._e.upload_center_header);
			this._e.upload_center.append(this._e.upload_center_center);
			this._e.upload_center_header.append(this._e.upload_center_header_child1);
			this._e.upload_center_header.append(this._e.upload_center_header_child2);
			this._e.upload_center_header.append(this._e.upload_center_header_child3);
			
			this._e.upload_center_header.append(this._e.upload_center_header_child6);
			this._e.upload_center_center.append(this._e.upload_center_center_header);
			this._e.upload_center_center.append(this._e.upload_center_center_content);
			this._e.upload_center_center_header.append(this._e.upload_center_center_header_child1);
			this._e.upload_center_center_header.append(this._e.upload_center_center_header_child2);

			$("#MaxNum").html(this.options.maxNumber);
			$("#leftNum").html(this.options.maxNumber);
			
			
		},
		//给 file 定位 覆盖掉 image
		_setFilePosition:function(suffix){
			var target =  document.getElementById('add_file');
			
			var x=target.offsetLeft;
			var y=target.offsetTop;
	        
			var target = target.offsetParent;
	        while (target)
	        {
	            x += target.offsetLeft;
	            y += target.offsetTop;
	            
	            target = target.offsetParent
	        }
	       //alert("x=:"+x+"y=:"+y);
	        $("#file_hidden"+suffix).css("top",y);
	        $("#file_hidden"+suffix).css("left",x);
	        
	        if(!+[1,]){//IE
	        	 $("#file_hidden"+suffix).css("top",8);
	 	        $("#file_hidden"+suffix).css("left",5);
	        }
	       
		},
		_bindEvent:function(suffix){
			var self=this;
			//选择一个文件
			$("#file_hidden"+suffix).change(function(){
				/*if (navigator.userAgent.indexOf('Firefox') >= 0){
					alert(window.URL.createObjectURL($("#file_hidden1")[0].files.item(0)));
				}*/
				var path=$("#file_hidden"+suffix).val();
				var filename=path.substr(path.lastIndexOf('\\')+1);
			    var str$=$('<div id="divcontent'+suffix+'"class="upload-file-contenttitle upload-header"><label id="hidden_value" style="display:none">'+path+'</label><label class="upload-file-label1">'+filename+'</label><label id="removeID" class="upload-file-label2 upload-file-clean"  style="cursor:pointer">  </label></div>');
			    
			    //过滤 重名
			  if(self.options.files[path]!=1){
			    
			    $("#file_content").append(str$);
			    $("#leftNum").html(parseInt($("#leftNum").html())-1);
			    
				if(parseInt($("#leftNum").html())==0){
		    		$("#file_hidden1").css("display","none");
		    	}
				
				self.options.files[path]=1;
				
				$(this).hide();
				//create new file
				self._addFile(suffix);
				
				

			    //删除一个文件
			    str$.find('label[id="removeID"]').click(function(){
			    	str$.remove();
			    	$("#leftNum").html(parseInt($("#leftNum").html())+1);
			    	$("#file_hidden"+suffix).remove();
			    	delete self.options.files[path];
			    	$("#file_hidden"+self.options.lastSuffix).css("display","block");
				});
			  }else{
				  alert("文件已经存在了,换一个其他的吧。");
			  }
			});
			//
			
			
			//clear
			$("#delete_file").click(function(){
				$("#file_content").html("");
				$(":file[name='"+self.options.fileControlName+"']:not(#file_hidden"+self.options.lastSuffix+")").remove();
				$("#leftNum").html(self.options.maxNumber);
				$("#file_hidden"+self.options.lastSuffix).css("display","block");
				self.options.files={};
			});
		},
		_addFile:function(suffix){
			suffix=suffix+1;
			this.options.lastSuffix=suffix;
			this._e.upload_center_header.prepend($('<input id="file_hidden'+suffix+'" size=1 type="file" name="'+this.options.fileControlName+'"class="upload-file-hidden transparent_class" />'));
			//this._e.upload_center_header_child2.append($('<input id="file_hidden'+suffix+'" type="file" name="'+this.options.fileControlName+'"class="upload-file-hidden transparent_class" />'));
			this._bindEvent(suffix);
	        if(parseInt($("#leftNum").html())==0){
	    		$("#file_hidden"+suffix).css("display","none");
	    	}
	        this._setFilePosition(suffix);
	        
		}
	});
})(jQuery);(function($,undefined){

	$.widget("dtui.dtfileuploader",{
		options:{
			width: 600,
			height: 200,
			maxNumber:10,
		    name:'file',	//内部input的name属性
		    prompt: null,
		    addLabel:'添加附件',
		    clearLabel:'清空附件'
		},
		
	    _create:function(){
	    	this.files = {};
	    	this.total = 0;
	    	this.element.width(this.options.width);
	    	//this.element.height(this.options.height);
	    	this._createOperatorBar();
	    	this._createFileTable();
	    	this._bindEvent();
	    },
	      
	      _createOperatorBar: function() {
	    	  var o = this.options;
	    	  this._e = {};
	    	  
	    	  function getLabel() {
	    		  return o.addLabel.substring(0, 4);
	    	  }
	    	  function getPrompt() {
	    		  if (o.prompt){
	    			  return o.prompt;
	    		  }
	    		  else {
	    			  return '最多允许添加'+o.maxNumber+'个文件！';	    			  
	    		  }
	    	  }
	    	  var operBar = this._e.operBar = $("<div></div>");
	    	  this._e.addBtn = $("<a href='javascript:void(0)'></a>").dtbutton({label: getLabel()}).appendTo(operBar);
	    	  this._e.clearBtn = $("<a href='javascript:void(0)'></a>").dtbutton({label: o.clearLabel}).appendTo(operBar);
	    	  this._e.prompt = $("<span class='prompt'></span>").text(getPrompt()).appendTo(operBar);
	    	  this._addFileControl();
	    	  this.element.append(operBar);
	      },
	      
	      _createFileTable: function() {
	    	  var that = this;
	    	  var tableDiv = $("<div/>").addClass("ui-upload ui-widget ui-helper-reset"),
	    	  	  titleTableDiv = $("<div/>").addClass("ui-state-default ui-upload-header ui-corner-all pstyle"),
	    	  	  titleTable = $("<table><tr><td>文件</td><td class='remove'>移除文件</td></tr></table>")
	    	  	  	.addClass("pstyle")
	    	  	  	.appendTo(titleTableDiv),
	    	  
	    	      fileTableDiv1 = $("<div/>").addClass("ui-upload-content ui-widget-content"),
	    	  	  fileTableDiv2 = $("<div/>")
	    	  	  	.addClass("ui-upload-line")
	    	  	  	.appendTo(fileTableDiv1),
	    	  	  fileTable = this._e.fileTable = $("<table></table>")
	    	  	  	.addClass("pstyle")
	    	  	  	.appendTo(fileTableDiv2),
	    	  
	    	      footTableDiv = $("<div/>").addClass("ui-state-default ui-upload-foot ui-corner-all pstyle"),
	    	  	  footTable = $("<table class='pstyle'>"+
						   "<tr>"+
							"<td class='file-number'>共<span class='spanstyle'>0</span>个文件</td>"+
						   "</tr>"+
						"</table>").appendTo(footTableDiv);
	    	  tableDiv.append(titleTableDiv).append(fileTableDiv1).append(footTableDiv);
	    	  this.element.append(tableDiv);
	    	  
	    	  if (this.options.height) {
	    		  fileTableDiv1.height(function(i, h){
	    			  //alert(that.options.height +','+ that._e.addBtn.height() +','+ titleTableDiv.height() +','+ footTableDiv.height());
	    			  return that.options.height - that._e.addBtn.height() - titleTableDiv.height() - footTableDiv.height();
	    		  });	    		  
	    	  }
	    	  
	    	  this._e.fileNumSpan = footTable.find('td.file-number span');
	      },
	      
	      
	      _bindEvent:function(suffix,obj){
	    	  var that = this;
	     	   //依据maxNumber判断是否可继续添加上传组件
	    	  this.element.delegate('.filecontrol', 'click', function(){
	     		   if(that.options.maxNumber!=null&&that.options.maxNumber!=""&&that.total>=that.options.maxNumber){
	                    alert("添加的附件不能超过"+that.options.maxNumber+"个！");
	                    $(this).val('');
	                    return false;
	                 }
	     	   })
	     	   //添加附件，判断上传列表中是否已存在此附件。若不存在，则将信息显示在上传列表中。
	     	   .delegate('.filecontrol', 'change', function(){
	         	   /*
	     		   if($.browser.mozilla){
	         		   netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
	         	   }
	         	   */
	         	   var path=$(this).val();
	         	   if(that.files[path]){
	         		   alert("添加的附件已存在！");
	         		   return;
	         	   }
	         	   
	               that.files[path]=$(this);
	               var $fileRow = that._addFileInfoToTable(path);
	               
	                $(this).hide();
	                that.total=that.total+1;
	                that._e.fileNumSpan.text(that.total);
	                var $fileInput = that._addFileControl(suffix,that);
	     	   })
	     	   .delegate('.remove > span', 'click', function() {
	     		   var $fileRow = $(this).parent().parent();
	     		   that._deleteFileRow($fileRow);
	     		   
	     	   });

	    	  this._e.clearBtn.on('click', function() {
	    		  that._clearFiles();
	    		  return false;
	    	  });
	    	  this._e.addBtn.on('click', function() {
	    		  //this.element.find('input.filecontrol :visible').trigger('click');
	    		  //return false;
	    	  });
	      },
	      //添加file控件
	      _addFileControl:function(){
	    	  var o = this.options;
	    	  var $fileInput = $("<input title='' type='file'/>").addClass("filecontrol").attr('name', o.name).appendTo(this._e.addBtn);
	    	  return $fileInput;
	      },
	      _addFileInfoToTable: function(fileName) {
        	   var val="";
               if(fileName.indexOf("\\")>-1){
                 val=fileName.substr(fileName.lastIndexOf("\\")+1);
               }
               var $fileRow = $("<tr>"+
       				"<td class='file'><span class='spanstyle'>"+val+"</span></td>"+
    				"<td class='remove'><span class='ui-icon ui-icon-close spanstyle'></span></td>"+
    			    "</tr>");
               $fileRow.attr('fullFileName', fileName);
               this._e.fileTable.append($fileRow);
               return $fileRow;
	      },
	      //删除某个file控件
	      _deleteFileRow:function($fileRow){
     		  var $fileInput = this.files[$fileRow.attr('fullFileName')];
    		   var path =  $fileInput.val();
    		   delete this.files[path];
    		   $fileRow.removeData('inputControl');
    		   $fileRow.remove();
    		   $fileInput.remove();
    		   this.total=this.total-1;
    		   this._e.fileNumSpan.text(this.total);
	      },
	      //删除全部file控件
	      _clearFiles: function() {
	    	  this._e.fileTable.children().remove();
	    	  this.total = 0;
	    	  this.files = {};
	    	  this.element.find('input.filecontrol').remove();
	    	  this._addFileControl();
	    	  this._e.fileNumSpan.text(this.total);
	      },
	      //设定/获取可上传的最大文件个数
	      maxNumber:function(maxNumber){
	     	 if(arguments.length){
	              if(maxNumber!=null&&maxNumber!=""&&!isNaN(maxNumber)){
	                 this.options.maxNumber=maxNumber;
	              }
	          }else{
	                return this.options.maxNumber;
	          }
	      },
	      //设定/获取提示信息
	      prompt:function(prompt){
	     	 if(arguments.length){
	              if(prompt!=null&&prompt!=""){
	             	 this.options.prompt=prompt;
	                 $("#promptId").text(prompt);
	              }
	          }else{
	                return this.options.prompt;
	          }
	      },
	      //获取上传的文件数
	      getFilesNumber:function(){
	     	 return total;
	      }
	});
})(jQuery);/**
 * 未完成
 * 1、事件为封装完成
 * 2、导入导出方法 未测试完成 js不能读写本地文件可能是导致测试失败的根本原因
 * 3、url 测试未测试
 */

(function( $, undefined ) {

   var gridPrototype = {};
	
   $.extend(gridPrototype,$.fn.jgrid, {
		options:{
			width: '100%',
			height: 150,
			caption: "",
			titleAlign:"left",                //解决了
			//是否显示隐藏表格按钮
			hidegrid: false,
			forceFit : false,
    		colModel:[],
    		url:"",
    		requestType:"get",                  //解决了
    		datatype: "json",
    		rownumbers:false,                   //是否显示行号
    		showPager:true,                     //是否分页 已解决
     		
    		rowNum:20,
            multiselect: false,
            cellEdit: false,
            editurl: null,

            altRows: true,
            shrinkToFit: false,
            sortable:false,
            sortname:null,
            sortorder:"asc",
            jsonReader: {},
            prmNames:{page:"page",rows:"rows", sort: "sidx",order: "sord", search:"_search", nd:"nd", id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del", subgridid:"id", npage: null, totalrows:"totalrows"},
            
            //callback
            afterInsertRow: null,
            beforeSelectRow: null,
            gridComplete: null,
            loadComplete: null,
            loadError: null,
            onSelectRow: null,
            onCellSelect: null,
            dblClickRow: null,
            rightClickRow: null
		   },
		
		proxyObj:undefined,
		
		_setOption:function(){
			
			this.options.mtype=this.options.requestType;
			//this.options.showPager==false?(this.options.height="100%"):"";
			if (this.options.showPager){
				this.options.pager = '#'+this.pagerId;
			}
		},
		
		_create:function(){
			this._createResizeDiv();
			this._createPager();
			this._setOption();
			this.proxyObj=this.element.jqGrid(this.options);
			this._bindResizeEvent();
		},
		
		_createPager: function() {
			this.pagerId = this.element.attr('id') + '_pager';
			this.element.after("<div id='"+this.pagerId+"'></div>");
		},
		_createResizeDiv: function() {
			if (typeof this.options.width == 'string' && this.options.width.indexOf('%')>0) {
				this.resizeDiv = $('<div style="width:'+this.options.width+'"></div>')
					.attr('id', this.element.attr('id') + '_div');
				this.resizeDiv.insertAfter(this.element);
			}
		},
		_bindResizeEvent: function() {
			var that = this,
				resizeDiv = this.resizeDiv;  
			if (typeof this.options.width == 'string' && this.options.width.indexOf('%')>0) {
				$(window).on('resize', function() {
					//alert(resizeDiv.prop('clientWidth')+','+resizeDiv.prop('clientWidth')+
					//		','+resizeDiv.attr('clientWidth')+','+resizeDiv.attr('clientWidth'));
					var width = resizeDiv.prop('clientWidth');
					if (width == null || width < 1){width = resizeDiv.prop('offsetWidth');}
					width = width - 2; 					
					//alert(that.proxyObj.width());
					if (width > 0 &&  Math.abs(width - that.proxyObj.width()) > 5){
						that.setGridWidth(width);
					}
				})
				.trigger('resize');
			}
		},
		_init:function(){
			//set  titleAlign  location
			this.options.titleAlign!="left"?this.setTitleAlign(this.options.titleAlign):"";
			this.options.showHideButton==false?this.showHideButton():"";
		},
		_setOptions: function(options){
			this._super(options);
			this.proxyObj.setGridParam(options);
		},
		option: function( key, value ){
			this._super(key, value);
			if ( typeof key === "string" && value === undefined) {
				return this.proxyObj.getGridParam(key);
			}
		},
		addRowData : function(rowid,rdata,pos,src){
			this.proxyObj.addRowData(rowid,rdata,pos,src);
		},
		
		delRowData : function(rowid){
			this.proxyObj.delRowData(rowid);
		},
		
		getRowData : function( rowid ){
			return this.proxyObj.getRowData(rowid);
		},
		
		setRowData : function(rowid, data, cssp) {
			this.proxyObj.setRowData(rowid, data, cssp);
		},
		
		clearGridData : function(clearfooter) {
			this.proxyObj.clearGridData(clearfooter);
		},
		
		getCell : function(rowid,col) {
			this.proxyObj.getCell(rowid,col);
		},
		
		getCol : function (col, obj, mathopr) {
			this.proxyObj.getCol(col, obj, mathopr);
		},
		
		getDataIDs : function (){
			this.proxyObj.getDataIDs();
		},
		
		getInd : function(rowid,rc){
			this.proxyObj.getInd();
		},
		
		hideCol : function (colname) {
			this.proxyObj.hideCol(colname);
		},
		
		showCol : function(colname) {
			this.proxyObj.showCol(colname);
		},
		
		setCaption : function (newcap){
			this.proxyObj.setCaption(newcap);
		},
		
		setCell : function(rowid,colname,nData,cssp,attrp, forceupd){
			this.proxyObj.setCell(rowid,colname,nData,cssp,attrp, forceupd);
		},
		
		setGridHeight : function (nh) {
			this.proxyObj.setGridHeight(nh);
		},
		
		setGridWidth : function(nwidth, shrink) {
			this.proxyObj.setGridWidth(nwidth, shrink);
		},
		
		setLabel : function(colname, nData, prop, attrp ){
			this.proxyObj.setLabel(colname, nData, prop, attrp);
		},
		
		resetSelection : function(){
			this.proxyObj.resetSelection();
		},
		setSelection : function(selection,onsr, e){
			this.proxyObj.setSelection(selection,onsr, e);
		},
		
		viewGridRow : function(rowid, p){
			this.proxyObj.viewGridRow(rowid, p);
		},
		
		getGridParam : function(pName){
			return this.proxyObj.getGridParam(pName);
		},
		
		setGridParam : function (newParams){
			this.proxyObj.setGridParam(newParams);
		},
		
		editGridRow : function(rowid, p){
			this.proxyObj.editGridRow(rowid, p);
		},
		
		delGridRow : function(rowids,p) {
			this.proxyObj.delGridRow(rowids, p);
		},
		
		//update selection row data
		updateRowData : function(rowid, data, cssp){
			return this.proxyObj.setRowData(rowid, data, cssp);
		},
		
		//return selection row
		getSelection : function(){
			var id=this.getGridParam('selrow');
			//console.log(id);
			return this.proxyObj.getRowData(id);
		},
		
	    //edit row data
		editRow : function(rowid,keys,oneditfunc,successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc){
			this.proxyObj.editRow(rowid,keys,oneditfunc,successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc);
		},
		//save row data
		saveRow : function(rowid, successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc){
			this.proxyObj.saveRow(rowid, successfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc);
		},
		//cancel row data
		restoreRow : function(rowid, afterrestorefunc){
			this.proxyObj.restoreRow(rowid, afterrestorefunc);
		},
		reload: function() {
			this.proxyObj.trigger('reloadGrid');
		},
		excelExport: function(o){
			this.proxyObj.excelExport(o);
		},
        exportData:function(o){
        	//alert("aas");
        	this.proxyObj.jqGridExport(o);
		},
		
		importData : function(o){
			this.proxyObj.jqGridImport(o);
		},
		
		//set caption location
		setTitleAlign: function(){
			if(this.options.titleAlign=="center"){
				$(this.proxyObj).closest("div.ui-jqgrid-view")
		           .children("div.ui-jqgrid-titlebar")
		           .css("text-align", "center")
		           .children("span.ui-jqgrid-title")
		           .css("float", "none");
			}else if(this.options.titleAlign=="right"){
				$(this.proxyObj).closest("div.ui-jqgrid-view")
		           .children("div.ui-jqgrid-titlebar")
		           .css("height","18.578125px")
		           .children("span.ui-jqgrid-title")
		           .css({"float":"none","position":"absolute","right":"24px"});
			}
       },
       
       //set button show or hide
       showHideButton:function(){
    	   $(this.proxyObj).closest("div.ui-jqgrid-view")
           .children("div.ui-jqgrid-titlebar")
           .children("a")
           .css("display","none");
       }
	});
	
	$.widget( "dtui.dtgrid", gridPrototype);

	$.widget( "dtui.dtpaginggrid", $.dtui.dtgrid, {
		_create:function(){
			var o = this.options;
			
			o.datatype = o.datatype||'json';
			var jsonRoot = o.jsonReader.root?o.jsonReader.root:"resultList";
			$.extend(o.jsonReader, {
				"root": jsonRoot,
				"page": 'pagingInfo.pageNum',
				"total": 'pagingInfo.totalPages',
				"records": 'pagingInfo.totalRows',
				"repeatitems": false,
				"id": '0'
			});
			
			$.extend(o.prmNames, {
				page:'pqb.pagingInfo.pageNum', 
				rows:'pqb.pagingInfo.pageRows', 
				id:this._getKeyFieldName()||'id'
			});
			this._super();
		},
		
		_getKeyFieldName: function() {
			for (var i=0; i< this.options.colModel.length; i++) {
				var col = this.options.colModel[i];
				if (col.key)
					return col.jsonmap?col.jsonmap:col.name;
			}
			return null;
		}
	});
})( jQuery );/**
 * 
 */
(function($,undefined){
	$.widget("dtui.dtgridcombobox", $.dtui.dtabscombobox,{
		options: {
			disabled: false,
			dropPanelWidth: "400px",	//覆盖父类的值
			//gridWidth: '400px',
			gridId:"",
			gridOption:{}
		},
		_init: function() {
			this._super();
			
			this._genSelectionObject();
			//this._refreshInputValue();
			this._selectGridRow();
		},
		/**
		 * 根据输入框的值生成_selection对象
		 */
		_genSelectionObject: function() {
			
			var valueArray = this._e.valueText.val().split(",");
			var textArray = this._e.inputText.val().split(",");
			if (valueArray.length != textArray.length){
				if (window.console) console.log('表格下拉框值和显示文本长度不一致！');
				return;
			}
			this._selection = [];
			for (var i=0; i<valueArray.length; i++){
				var rowObj = {};
				rowObj[this.options.valueField] = valueArray[i];
				rowObj[this.options.textField] = textArray[i];
				this._selection.push(rowObj);
			};			
		},
		_buildContent: function(){
			this._super();
			var self = this;
			//添加校验时的标识;  组件整体标识-- 文本框标识
			self.totalContainer.addClass("dtui-gridcombobox");
			self._e.inputText.addClass("dtui-gridcombobox-text");
			this._e.selectOption.height('auto');
			if(this.options.gridId){
				$("#gbox_"+this.options.gridId+"").appendTo(this._e.selectOption);
				this._e.table = $("#"+this.options.gridId+"");
			}
			else{
				this._e.table = $('<table></table>').attr('id', this.element.attr('id')+'_table')
					.appendTo(this._e.selectOption)
					.dtpaginggrid(this.options.gridOption);
			}
			this._e.table.dtpaginggrid('setGridWidth', this._e.selectOption.width() - 2, true);
			this.isMultiSelect = this._e.table.dtpaginggrid('option', 'multiselect');
		 },
		 
		_bindEvent: function() {
			this._super();
			var self = this;
			this._e.table.dtpaginggrid('option','onSelectRow',function(rowid,status,e){
				self.change(rowid,status);
			});
			
			this._e.table.dtpaginggrid('option','onSelectAll',function(rowids,status){
				//console.log(status);
				var rowObj = null;
				var t = self._e.table;
				var valueField = self.options.valueField;

				if (!self._selection) {
					self._selection = [];
				}
				for (var j = 0; j<rowids.length; j++){
					rowObj = t.dtpaginggrid('getRowData', rowids[j]);
					var index = self._findSelection(rowObj[valueField]);
					if (index >= 0){
						if (!status){
							self._selection.splice(index, 1);
						}
					}
					else {
						if (status) {
							self._selection.push(rowObj);
						}
					}										
				}
				self._refreshInputValue();
				self._trigger('change');
			});
			
			//用来处理翻页后的勾选状态
			this._e.table.dtpaginggrid('option','gridComplete',function(data){
				self._selectGridRow();
			});
			
			this._e.inputText.keydown(function(event){
				if (event.keyCode == 8) {
					if (self._selection && self._selection.length>0){
						self._selection = [];
						self._refreshInputValue();
						self._selectGridRow();
						self._trigger('change');
					}
				}
				event.keyCode = 0;
				return false;
			});

		},
		
		/**
		 * 根据_selection对象内容，设置表格的选择行
		 */
		_selectGridRow: function() {
			var self = this;
			var t = self._e.table;
			t.dtpaginggrid('resetSelection');			
			if (!self._selection || self._selection.length == 0) {
				return ;
			}
			//遍历数据，将已经选中的数据行设置为勾选状态
			
			var valueField = self.options.valueField;
			rowData = t.dtpaginggrid('getRowData');
			//console.log(self._selection);

			for (var i=0; i<this._selection.length; i++){
				t.dtpaginggrid('setSelection', this._selection[i][valueField], false);
			}
			
			/*
			$.each(rowData, function(i, rowObj){
				//console.log(rowObj);
				var index = self._findSelection(rowObj[valueField]);
				if (index >= 0){
					t.dtpaginggrid('setSelection', rowObj[valueField], false);
				}
			});
			*/
		},
		
		_findSelection: function(value){
			if (!this._selection || this._selection.length == 0){
				return -1;
			}
			var valueField = this.options.valueField;
			for (var i=0; i<this._selection.length; i++){
				if (value == this._selection[i][valueField]) {
					return i;
				}
			}			
		},
		
		change:function(rowid, status){
			var self=this,
				input_text=this._e.inputText,
				table = this._e.table,
				options = self.options;
			
			//console.log('rowid:'+rowid);
			//console.log(table.dtpaginggrid('getRowData', rowid));
			if(status){
				if(!this._selection || !this.isMultiSelect){
					this._selection = [];
				}
				self._selection.push(table.dtpaginggrid('getRowData', rowid));
			}else{
				if(self._selection){
					var result = [];
					$.each(this._selection, function(index, item){
						//alert(item[options.valueField]);
						if(rowid != item[options.valueField]){
							result.push(item);
						}
					});
					self._selection = result;
				}
			}
			this._refreshInputValue();
			//console.log(this._e.table.dtpaginggrid('option', 'multiselect'));
			if (!this.isMultiSelect) 
				this._hideOption();
			self._trigger('change');
		},
		/**
		 * 根据_selection对象刷新valueText和inputText文本框的值
		 */
		_refreshInputValue: function(){
			if(!this._selection || this._selection.length == 0) {
				this._e.inputText.val("");
				this._e.valueText.val("");
				return;
			}
			var textArr = [],
				valueArr = [],
				options = this.options;
			
			$.each(this._selection, function(index, item){
				textArr.push(item[options.textField]);
				valueArr.push(item[options.valueField]);
			});
			//console.log(this._e.inputText.val("tmd"));
			this._e.inputText.val(textArr.join(","));
			this._e.valueText.val(valueArr.join(","));
		},
		
		_clear: function(){
			if (this._selection && this._selection.length>0){
				this._selection = [];
				this._refreshInputValue();
				this._selectGridRow();
				return true;
			}
			return false;
		},
		
        /**
         * 根据当前的值刷新内容区域的选择状态
         */
        _refreshContentState: function() {
			//使valueText和inputText中的内容与selection对象的内容保持一致
			this._genSelectionObject();
        	this._selectGridRow();
        },
        
        /**
         * 根据值获取对应的文本
         * @param value
         */
        _getText: function(value){
        	if (!value){
        		return "";
        	}
        	var valueArr = value.split(this.options.displaySplitChar),
        		textArr = [],
        		t = this._e.table;
        	for(var i = 0; i< valueArr.length; i++){
        		var rowObj = t.dtpaginggrid('getRowData', valueArr[i]);
        		var txt = rowObj ? rowObj[this.options.textField] : '';
        		textArr.push(txt);
        	}
        	return textArr.join(this.options.displaySplitChar);
        }
        
		
	});
})(jQuery);/*
 * 
 * 横向菜单
 */

(function( $, undefined ) {

var 
	main_menu_class = 'main_menu',
	sub_menu_class = 'sub_menu';
	
$.widget('dtui.dthmenu', {
	version:'1.0.1',
	options: {
		url: false,
		activeMenu: 0,
		hoverSwitch: false,
		childrenField: 'children',
		parentField: 'parent',
		textField: 'title',
		idField: 'id',
		main_menu: '.' + main_menu_class,
		sub_menu: '.' + sub_menu_class,
		select: null
	},
	_create: function(){
		var options = this.options,
			url = options.url;
		
		if(url){
			this._load(url);
		}else{
			this._initNav();
		}
	},
	_initBarItem: function(elements){
		var items = elements.children('a');
		
		elements.addClass('ui-menubar-item')
			.attr( "role", "presentation" );
		this._focusable( items );
		this._hoverable( items );
		
		items.addClass('ui-button ui-widget ui-button-text-only ui-menubar-link')
			.attr('role', 'menuitem')
			.wrapInner('<span class="ui-button-text"></span>');
	},
	_initSubItem: function(items){
		var options = this.options;
		
		items.menu({
			direction: 'horizontal',
			select: function(event){
				if(options.select){
					options.select(event);
				}
			}
		})
			.hide()
			.addClass('ui-helper-clearfix');
	},
	_initNav: function(){
		var self = this,
			element = self.element,
			options = self.options,
			enable_index = options.activeMenu,
			main_element = $(options.main_menu, element),
			sub_element = $(options.sub_menu, element),
			main_enable = $('li:eq('+enable_index+')', main_element),
			sub_enable = $('ul:eq('+enable_index+')', sub_element);
			
		element.addClass('dtui-nav');
		main_element.addClass('ui-menubar ui-widget-header ui-helper-clearfix');
		
		self._initBarItem($('li', main_element));
		self._initSubItem($('ul', sub_element));
		
		self._open($('a', main_enable), sub_enable);
		
		main_element.on('mouseenter', 'li', function(event){
			var target = $(this);
			if(target.hasClass('ui-state-disabled')){
				return;
			}
			var	index = target.index(),
				on_sub = $('ul:eq('+index+')', sub_element);
			self._open($('a', target), on_sub);
		});
	},
	_open: function(target, menu){
		// 如果是已经打开的菜单，什么也不做
		if ( this.active && this.active[0] == menu[0] ) {
			return;
		}
		// 隐藏以显示的菜单
		if ( this.active ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
				
			this.activeBar
				.removeClass( "ui-state-active" );
		}
		
		this.active = menu
			.show()
			.removeAttr( "aria-hidden" )
			.attr( "aria-expanded", "true" )
			.menu("focus", event, menu.children( "li" ).first() )
			.focus()
			.focusin();
			
		this.activeBar = target
			.addClass('ui-state-active')
			.attr( "tabIndex", -1 );
		
	},
	_createElement: function(data){
		var o = this.options,
			main_element = $('<ul class="'+ main_menu_class +'">'),
			sub_element = $('<div class="'+ sub_menu_class +'">'),
			item, i, j;
		
		for(i=0; i<data.length; i++){
			item = data[i];
			main_element.append(this._createItem(item));
			sub_element.append(this._createItems(item[o.childrenField]));
		}
		
		this.element.append(main_element);
		this.element.append(sub_element);
	},
	_createItems: function(items){
		var o = this.options,
			subitem, buf, i;
			
		//创建子节点DOM元素
		buf = [];
		buf.push('<ul>');
		for(i=0; i<items.length; i++){
			subitem = items[i];
			buf.push(this._createItem(subitem));
		}
		buf.push('</ul>');
		return buf.join('');
	},
	_createItem: function(item){
		var buf = [],
			o = this.options,
			url = item.url ? item.url : '#';
		
		buf.push('<li>');
		buf.push('<a href="' + url + '">');
		buf.push(item[o.textField]);
		buf.push('</a></li>');
		return buf.join('');
	},
	_load: function(url){
		var self = this,
			o = this.options;
		
		$.getJSON(url, function(data) {
			var result = [],
				temp = [],
				item, i, j;
				
			//取得所有的根节点	
			for(i=0; i<data.length; i++){
				item = data[i];
				if(item[o.parentField] == undefined){
					if(!item[o.childrenField]){
						item[o.childrenField] = [];
					}
					result.push(item);
				}else{
					temp.push(item);
				}
			}
			
			//把所有的子节点添加到根节点上面
			for(i=0; i<temp.length; i++){
				item = temp[i];
				for(j=0; j<result.length; j++){
					if(item[o.parentField] == result[j][o.idField]){
						result[j][o.childrenField].push(item);
					}
				}
			}
			
			self._createElement(result);
			self._initNav();
		});
	},
	_query: function(ele){
		return ele.jquery ? ele : $(ele);
	},
	enable: function(item){
		this._query(item).removeClass('ui-state-disabled');
	},
	disable: function(item){
		this._query(item).addClass('ui-state-disabled');
	},
	addMenu: function(data){
		var self = this,
			element = self.element,
			o = self.options,
			main_element = $(o.main_menu, element),
			sub_element = $(o.sub_menu, element);
		
		var item = this._createItem(data);
		$item = $(item);
		self._initBarItem($item);
		$item.appendTo(main_element);
		
		var subItems = this._createItems(data[o.childrenField]);
		$subItems = $(subItems);
		$subItems.appendTo(sub_element);
		this._initSubItem($subItems);
	},
	removeMenu: function(menu){
		var $menu = this._query(menu);
		
		if($menu.hasClass('ui-menubar-item')){
			this._removeMenuBar($menu);
		}else if($menu.hasClass('ui-menu-item')){
			$menu.remove();
		}
	},
	_removeMenuBar: function($menu){
		var element = this.element,
			o = this.options;
		
			index = $menu.index(),
			$subItem = element.find(o.sub_menu+' ul:eq('+ index +')');
		
		if ( this.active && this.active[0] == $subItem[0] ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
			
			this.active = null;
		}
			
		$menu.remove();
		$subItem.remove();
	},
	getMenuInfo: function(ele){
		var element = this.element,
			o = this.options,
			$ele = this._query(ele),
			$aele = $ele.find('a:eq(0)'),
			info = {
				text: $aele.text(),
				url: $aele.attr('href')
			};
		if($ele.hasClass('ui-menubar-item')){
			var index = $ele.index(),
				sub_element = $(o.sub_menu, element);
			info.child = $('ul:eq('+index+')', sub_element);;
			
		}
		return info;
	},
	option: function(name){
		if(this.options[name]){
			return this.options[name];
		}else{
			return null;
		}
	}
});

})( jQuery );/**
 * 输入框组件
 * ctrl + c 各个浏览器 不同实现方式 谷歌浏览器没找到实现方式 暂时只在IE下 copy好使
 * 此js 主要整合intbox和 floatbox 其他没动 有任何错误 参考intbox.js 和 floatbox.js
 */
(function( $,undefined ) {
	$.widget("dtui.dtinput", {
		options: {
			width:"150px",
			height:"20px",
			readonly: false,
			disabled: false,
			label:"",
			inputType:"text",
			name: null,
			value:null,
			required:true,
			length:"",
			maxVal:null,
			minVal:null,
			displaySplitChar:false,
			immValidate:true,
			decimalLength:2,//小数的位数
			prompt:""
		},
		
		_create:function(){
		    //定义最近次输入正确值
	        this.oldValue="";
	        //判断开始字符是否是0
	        this.obj0="";
	        //判断是否有"."值输入
	        this.objD="";
		
		    var obj=this;
		    this.element.addClass('ui-input');	//表单验证会使用该属性，谨慎修改
		    //添加圆角和背景色
		    this.element.addClass('ui-corner-all').addClass('ui-widget-content');
			this._starDiv = $.dtui.util.createStarElem(this.options.required);
			this._starElem =  this._starDiv.find('span.input-star'); 
			this.element.after(this._starDiv);
			this.element.data("options",this.options);
			this._labelElem = $("<label></label>")
			.insertBefore(this.element);
			if(this.options.label){
				this._labelElem.text(this.options.label);
			}
			var inputType = this.options.inputType;
			if(this.options.immValidate==true){
			   this.element.bind("blur",function(){
				 //输入框不能为空
				   if(obj.options.required&&obj.element.val()==""){
					   $.dtmessagebox.alert(obj.options.prompt+ "不能为空！"); 
				   }
				   if(obj.options.length!=null&&obj.options.length!=""){
				       obj._checkLength(obj);
				   }
				   if (inputType == "int" ||inputType == "float"){
				       obj._checkFormatKeyup(obj);
				   }
				   
			   });
			   
			   if (inputType!="int"&&inputType!="float" ){
				   this.element.bind("focusout",function(){
					   obj._checkFormatFocusout(obj);
				   });
			   }
			}
			
			if (this.options.name){
				this.element.attr('name', this.options.name);
			}
			else {
				this.options.name = this.element.attr('name');
			}
			if (this.options.value){
				this.element.val(this.options.value);
			}
			else {
				this.options.value = this.element.val();
			}
			this._applyOptions();
			
			this.element.addClass("input-init");
			var container=$("<div style='display:inline-block'></div>")
							.css({"height":this.options.height,"width":this.options.width,"margin":"0 auto 2px auto"})
							.insertAfter(this.element);
			this._starDiv.appendTo(container);
							//.css("padding-top",(this.options.height-16)/2);//set star middle
			var divinput=$("<div></div>").addClass("input-Container").append(this.element).appendTo(container)
							.addClass('ui-corner-all').addClass('ui-widget-content');                       //set corner all
			//if(this.options.required)
				divinput.css("margin-right", this._starDiv.width());
			if (inputType === "hidden"){
				container.hide();
			}
		},
		
		_applyOptions: function(){
			var o=this.options;
			this._setupDisabled(o.disabled);
			this._setupReadOnly(o.readonly);
			this._setupRequired(o.required);
		},

		//检测长度
		_checkLength:function(obj){
		  var length=obj.options.length;
		  var decimalLength=obj.options.decimalLength;//小数长度
		  var value=obj.element.val();
		  var valueStr = String(value);
	      var valueArr = valueStr.split(",");
		  value=valueArr.join("");
		  if(length!=null&&length!=""){
		      //if(value.length>length){
			  if($.dtutil.strutil.getLength(value)>length){
		    	  this.hinting = true;
		    	  var prompt=obj.options.prompt;
		    	  $.dtmessagebox.alert(prompt+"长度不能超过"+length+"！");
		    	  obj.element.val(obj.oldValue);
		    	  this.hinting = false;
		    	  return false;
		      }
		  }
		  if(obj.options.inputType=="float"){
			  var valArr = value.split(".");
			  if(valArr.length == 2 && valArr[1].length > decimalLength){
		    	  this.hinting = true;
		    	  var prompt=obj.options.prompt;
		    	  $.dtmessagebox.alert(prompt+"小数长度不能超过"+decimalLength+"！");
		    	  obj.element.val(obj.oldValue);
		    	  this.hinting = false;
		    	  return false;
		      }
		  }
		  return true;
		},
		//检测最大值
		_checkMax:function(obj){			
			  var maxVal=Number(obj.options.maxVal);
			  var value=obj.element.val();
			  var valueStr = value+"";
			  valueStr = valueStr.split(",").join("");
			  value=parseFloat(valueStr);
			  if(maxVal!=null){
			    if(value*1>maxVal*1){
			    	var prompt=obj.options.prompt;
			    	$.dtmessagebox.alert(prompt+"的值不能超过"+maxVal+"!");
			      obj.element.val(obj.options.maxVal);
			      obj.oldValue=obj.options.maxVal;
			      if(obj.options.displaySplitChar==true){
		        	     this._displaySplitChar();
		        	}
			      return false;
			    }
			  }
		},
		//检测最小值
		_checkMin:function(obj){
			  var minVal=Number(obj.options.minVal);
			  var value=obj.element.val();
			  var valueStr = value+"";
			  valueStr = valueStr.split(",").join("");
			  value=parseFloat(valueStr);
			  if(minVal!=null){
			    if(value*1<minVal*1){
			    	var prompt=obj.options.prompt;
			    	$.dtmessagebox.alert(prompt+"的值不能小于"+minVal+"!");
			      obj.element.val(obj.options.minVal);
			      obj.oldValue=obj.options.minVal;
			      if(obj.options.displaySplitChar==true){
		        	     this._displaySplitChar();
		        	}
			      return false;
			    }
			  }
		},
		//keyup绑定的检测事件
		_checkFormatKeyup:function(obj){
		    var inputType=obj.options.inputType;
		    if(inputType=="int"||inputType=="float"){
			    var flag=obj._checkIntFloatFormatKeyup(obj);
			    if(flag!=false){
			        var maxVal=obj.options.maxVal;
			        var minVal=obj.options.minVal;
			        var value=obj.element.val().split(",").join("");
			        if(maxVal!=null && !isNaN(value)){
			        	obj._checkMax(obj);
			        }
			        if(minVal!=null && !isNaN(value)){
			        	obj._checkMin(obj);
			        }
			        obj.oldValue=obj.element.val();///修改 oldValue
			    }
		    }
		},
		
		//keyup绑定的IntFloat类型检测事件
		_checkIntFloatFormatKeyup: function(obj) {
			var value=obj.element.val();
			var displaySplitChar=obj.options.displaySplitChar;
			
			//获得键盘值
			var iekey=event.keyCode;
			//判断输入值：第一个字符是负号或0-9；
			var re;
			if(this.options.inputType=='int'){
				re = /^-?[0-9]*(\,\d*)*$/;
				if(iekey==190){
					$.dtmessagebox.alert(prompt+"格式不正确！");
					obj.element.val(obj.oldValue);
					return false;
				}
			}else{
				re = /^-?[0-9]*(\,\d*)*(\.\d*)?(\,\d*)*$/;
			}
			if(!re.exec(value)==true){
				obj.element.val(obj.oldValue);
			 }else{
				 if(this.options.inputType=='int'){//合法输入（合法是针对正则表达式而言 ，不合法是针对数字要求而言）	
					    //过滤掉不合法情况
				     	if(value.substr(0,1)=="0"){//第一个数字 为零吗
				     		if(value.length==1){
				     			obj.oldValue="0";
				     			return true;
				     		}else{
				     			obj.element.val(obj.oldValue);
				     			return false;
				     		}
				     	}
				     	if(value.substr(0,2)=="-0"){
				     		obj.element.val(obj.oldValue);
				     		return false;
				     	}
				     	//没看出 有什么用处？？？先留着吧
				        if((event.ctrlKey)&&(iekey==65)) {return;}
				     	if(iekey==36||iekey==33||iekey==37||iekey==39||iekey==35||iekey==17||iekey==16) return;
				     	
				        //如果是true则千分位显示
				        if(obj.options.displaySplitChar==true){
			        	     this._displaySplitChar();
			        	}
				        //ctrl + c
				        if((event.ctrlKey)&&(iekey==67)){
				        	//alert("ctrl+c");
				        	 if (window.clipboardData) {
				                 var text=window.clipboardData.getData('Text');
						     	 var obj_text = text.split(",").join("");
						     	 window.clipboardData.setData('Text',obj_text);
				             }
				        }
				        
				        //ctrl+x
				        if((event.ctrlKey)&&(iekey==88)){
				        	 if (window.clipboardData) {
				                 var text=window.clipboardData.getData('Text');
						     	 var obj_text = text.split(",").join("");
						     	 window.clipboardData.setData('Text',obj_text);
				             }
				        }
				 }else if(this.options.inputType=='float'){//合法输入
			    	 //过滤掉 不合法的点 
			    	if(value=="."||value.substr(0,2)=="-."){
			    	   obj.element.val(obj.oldValue);
			    	   return false;
			    	}
			    	//过滤0.以外不合法字符串
			     	if(value.substr(0,1)=="0"){
			     		if(value.length==1){
			     			obj.oldValue="0";
			     			return true;
			     		}
			     		if(value.substr(1,1)=="."){
			     			obj.oldValue=obj.element.val();
			     			return true;
			     		}else{
			     			obj.element.val(obj.oldValue);
			     			return false;
			     		}
			     	}
			     	//过滤-0.以外不合法字符串
			     	if(value.substr(0,2)=="-0"){
			     		if(value.length==2){
			     			obj.oldValue="-0";
			     			return true;
			     		}
			     		else if(value.substr(2,1)=="."){
			     			obj.oldValue=obj.element.val();
			     			return true;
			     		}else{
			     			obj.element.val(obj.oldValue);
			     			return false;
			     		}
			     	}
			     	//没看出 有什么用处？？？先留着吧
			        if((event.ctrlKey)&&(iekey==65)) {return;}
			     	if(iekey==36||iekey==33||iekey==37||iekey==39||iekey==35||iekey==17||iekey==16) return;
			        
			        //如果是true则千分位显示
			        if(obj.options.displaySplitChar==true){
		        	     this._displaySplitChar();
		        	}
			        
			        //ctrl + c
			        if((event.ctrlKey)&&(iekey==67)){
			        	 if (window.clipboardData) {
			                 var text=window.clipboardData.getData('Text');
					     	 var obj_text = text.split(",").join("");
					     	 window.clipboardData.setData('Text',obj_text);
			             }
			        }
			        //ctrl+x
			        if((event.ctrlKey)&&(iekey==88)){
			        	 if (window.clipboardData) {
			                 var text=window.clipboardData.getData('Text');
					     	 var obj_text = text.split(",").join("");
					     	 window.clipboardData.setData('Text',obj_text);
			        	 }
			        }
				 }
			 }
		},
		
		//显示千位符
		_displaySplitChar:function(){
			var obj=this;
			var value=obj.element.val();
			var arr=value.split(",").join("").split(".");
			objNew =arr[0];
 			var res=/(-?\d+)(\d{3})/;
 			while(res.test(objNew)){
 				flag=true;
 				objNew=objNew.replace(res,"$1,$2");
 			}
 			if(arr[1]!=undefined){
 				objNew=objNew+"."+arr[1];
 			}
	        obj.oldValue=objNew;
	        obj.element.val(objNew);
		},
		
		
		
		//focusout绑定的检测事件
		_checkFormatFocusout:function(obj){
			//在其它校验函数中可能会弹出提示框，从而触发focusout事件而调用该函数，这时不做校验，直接返回
			if (this.hinting) return;
		     var inputType=obj.options.inputType;
		     if(inputType!="text"){
		    	 var value=obj.element.val();
		    	 var re="";
		    	  var JsonObject={
		    	        "email":/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
		    	        //"mobile":"/^[1][358]\d{9}$/",
		    	        "mobile":/^(1(([35][0-9])|(47)|[8][0126789]))\d{8}$/,
		    	        "tel":/(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{7,15}$)/,
		    	        "lxdh":/(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{7,15}$)|(^[1][358]\d{9}$)/,
		    	        "url":/(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i,
		    	        "password":/^[a-zA-Z]{1}[0-9a-zA-Z_]{5,17}$/,
		    	        "zipcode":/^\d{6}$/,
		    	        "identity":/^([1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3})|([1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z]))$/
		    	        };
		    	  
		    	        //用户自定义校验方式
		    	  		re= eval('(' + JsonObject[inputType] + ')');
		    	  		if(!re){
		    	  			re=inputType;
		    	  		}
		    	  
				     if(inputType!="int"&&inputType!="float"&&value!=null&&value!=""&&!re.test(value)){
				    	 $.dtmessagebox.alert(obj.options.prompt+"格式不正确！");
					        return false;
					 }
		     }
		},
		widget: function(){
			return this.element;
		},
		
		_setupDisabled: function(disabled){
			if ( disabled ) {
				this.element.prop( "disabled", true );
			} else {
				this.element.prop( "disabled", false );
			}
		},
		_setupReadOnly: function(readonly){
			if(readonly){
    			this.element.attr("readonly","readonly");
    		}else{
    			this.element.removeAttr("readonly");
    		}
		},
		_setupValue: function(value){
			this.element.val(value);
			var o = this.options;
			if(o.displaySplitChar==true && (o.inputType=='int' || o.inputType=='float')){
        	     this._displaySplitChar();
        	}
		},
		_setupRequired: function(required){
			if(required){
				this._starElem.show();
			}
			else {
				this._starElem.hide();
			}
		},

		_setOption: function( key, value ) {
			this._super(key, value);		
			if ( key === "disabled") {
				this._setupDisabled(value);
			}
			if ( key === "readonly") {
				this._setupReadOnly(value);
			}
			if ( key === "value" ) {
				this._setupValue(value);
			}
			if ( key === "label" ) {
				this._labelElem.text(value);
			}
			if ( key === "name") {
				this.element.attr('name', value);
			}
			if (key === "required"){
				this._setupRequired(value);
			}
		}
		
	});
})(jQuery); /*
 * ipaddress 升级老版本 
 * */
(function($,undefined){
  $.widget("dtui.dtipaddress",{
     options:{
    	 id:'ip',
    	 name:null,
    	 value:null,
    	 inputType:'ipaddress',
    	 required:false
     },
     _create:function(){
    	 this.element.hide();
    	 this._createElement();
    	 this._createContent();
    	 this._bindEvent();
    	 if (this.options.value){
    		 this.val(this.options.value);
    	 }
     },
     
     //创建组件
     _createElement:function(){
    	 this._e={
    			 container:$('<div class="ipdiv" id="ip"></div>'),
    			 ipInput_1:$('<input  class="ipinput" id="ip1" type="text" maxlength="3"/>'),
    			 ipInput_2:$('<input  class="ipinput" id="ip2" type="text" maxlength="3"/>'),
    			 ipInput_3:$('<input  class="ipinput" id="ip3" type="text" maxlength="3"/>'),
    			 ipInput_4:$('<input  class="ipinput" id="ip4" type="text" maxlength="3"/>'),
    			 ipInput_hidden:$('<input type="hidden" />')
    	 };
    	 
    	 if (this.options.name)
    		 this._e.ipInput_hidden.attr('name', this.options.name);
    	 
     },
     //组装组件
     _createContent:function(){
    	
    	this._e.container.append(this._e.ipInput_1);
    	this._e.container.append($('<b><sub>.</sub></b>'));
    	this._e.container.append(this._e.ipInput_2);
    	this._e.container.append($('<b><sub>.</sub></b>'));
    	this._e.container.append(this._e.ipInput_3);
    	this._e.container.append($('<b><sub>.</sub></b>'));
    	this._e.container.append(this._e.ipInput_4);
    	this._e.container.append(this._e.ipInput_hidden);
    	this._e.container.insertAfter(this.element);
    	
    	this._e.ipInput_1.nextNode=this._e.ipInput_2;
    	this._e.ipInput_2.nextNode=this._e.ipInput_3;
    	this._e.ipInput_3.nextNode=this._e.ipInput_4;
    	this._e.ipInput_4.nextNode=this._e.ipInput_1;
    	
    	
    	this._e.ipInput_1.preNode=this._e.ipInput_4;
    	this._e.ipInput_2.preNode=this._e.ipInput_1;
    	this._e.ipInput_3.preNode=this._e.ipInput_2;
    	this._e.ipInput_4.preNode=this._e.ipInput_3;
    	
     },
     //事件绑定
     _bindEvent:function(){
    	 var obj=this;
    	 this._e.ipInput_1.bind("keyup",function(e){
    		 obj._mask(e,$(this),obj);
    	 });
    	 this._e.ipInput_2.bind("keyup",function(e){
    		 obj._mask(e,$(this),obj);
    	 });
    	 this._e.ipInput_3.bind("keyup",function(e){
    		 obj._mask(e,$(this),obj);
    	 });
    	 this._e.ipInput_4.bind("keyup",function(e){
    		 obj._mask(e,$(this),obj);
    	 });
    	 
    	 //ip 地址存到隐藏域（可以用$(".ininput").change();代替下面的注释代码;但多个控件 会出现 混乱 但最终结果 不会出错）
    	 $(".ininput").change(function(){
    		 obj._e.ipInput_hidden.val(obj.val());
    	 });
    	 
    	 /*this._e.ipInput_1.bind("change",function(e){
    		 this._e.ipInput_hidden.val(obj.val());
    	 });
    	 this._e.ipInput_2.bind("change",function(e){
    		 
    	 });
    	 this._e.ipInput_3.bind("change",function(e){
    		 
    	 });
    	 this._e.ipInput_4.bind("change",function(e){
    		 
    	 });*/
     },
     /**
      * 验证是否合法
      * @param obj
      * @returns {Boolean}
      */
     _mask:function(event,obj,self){
    	 
    	 obj.val(obj.val().replace(/[^\d]/g,''));
    	 
    	 var key1=event.which;
    	//按Tab键 和 " → "在输入框间切换
    	 if (key1==39){
    		//动态获得 当前input对象   轻易不要改动 容易改错
    		eval("this._e.ipInput_"+obj.attr('id').substr(2,1)).nextNode.focus();
    	 }
    	 //按"←"在输入框间切换
    	 else if(key1==37){
    		eval("this._e.ipInput_"+obj.attr('id').substr(2,1)).preNode.focus();
    	 }
    	 //验证是否合法
    	 else if(obj.val().length>=3 && key1>=48 && key1<=57){                        
    		 if(parseInt(obj.val())>=256 || parseInt(obj.val())<=0){
    			 alert(parseInt(obj.val())+"IP地址错误！");   
    			 obj.val("");                                      
    			 obj.focus();                             
    			 return false;
    		 }
    		 else{
    			 //动态获得 当前input对象   轻易不要改动 容易改错
    			 eval("this._e.ipInput_"+obj.attr('id').substr(2,1)).nextNode.focus();
    		 }
    	 }
    	 
      },
     /**
      * 获取设置IP值
      * @param value
      * @returns {String}
      */
     val:function(value){
         if(arguments.length){
           if(value!=null&&value!=""){
        	   var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式 
        	   if(re.test(value)){ 
        		   if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256){
        			   var ips=value.split(".");
            	       this._e.ipInput_1.val(ips[0]);
            	       this._e.ipInput_2.val(ips[1]);
            	       this._e.ipInput_3.val(ips[2]);
            	       this._e.ipInput_4.val(ips[3]);
        		   }
        	   } 
           }
         }else{
             return this._e.ipInput_1.val()+"."+
                    this._e.ipInput_2.val()+"."+
                    this._e.ipInput_3.val()+"."+
                    this._e.ipInput_4.val();
         }
      }
    	
  });
})(jQuery);/*
 * 布局控件
 */
(function($, undefined) {
	var slice = Array.prototype.slice;
		//有header的时候的选项，不能修改
		defaultBarOptions = {
			spacing_closed:			24,							//关闭后打开按钮所占区域的间隔，不建议修改
			togglerLength_closed:	21,							//面板关闭后打开按钮的位置，不建议修改
			togglerLength_open:		0,							//面板打开时按钮的位置，不建议修改
			resizerClass: 'ui-layout-resizer-bar',						//调整大小的按钮的class
			togglerClass : 'ui-layout-toggler-bar'						//打开关闭按钮的class
		};
	$.widget('dtui.dtlayout',{
		version : '1.0.1',
		_layout : null,
		
		/*collapsable：每一个pane都可以收合，而且还可以加上jQuery特效 
		resizable：每一个pane都可以缩放，而且还可以限制缩放的大小 
		hotkeys：每一个pane都可以用键盘的方向键来操作，甚至自订快速键(这个最酷！) */
		options: {
			//default options for 'all panes' - will be overridden by 'per-pane settings'
			togglerTip_open : "关闭面板",	//打开按钮文字提示
			togglerTip_closed : "打开面板",	//关闭按钮文字提示
			resizerTip : "调整面板大小",		//调整按钮文字提示
			hideTogglerOnSlide:		true,
			//------------------------------------------//
			applyDemoStyles: 		false,
			closable:				true,
			resizable:				true,
			slidable:				true,
			initClosed:				false,
			initHidden: 			false,
			//ELEMENT SIZE & SPACING
			minSize:				0,
			maxSize:				0,
			spacing_open:			6,
			spacing_closed:			6,
			togglerLength_open:		50,
			togglerLength_closed: 	50,
			togglerAlign_open:		"center",
			togglerAlign_closed:	"center",
			togglerContent_open:	"",
			togglerContent_closed:	"",
			//SLIDING OPTIONS
			sliderCursor:			"pointer",
			slideTrigger_open:		"click"	,
			slideTrigger_close:		"mouseleave",
			//多重组合布局
			children:               null,//详细例子见Nested.html
			//callback
			onshow_start:			null,		
			onshow_end:				null,		
			onhide_start:			null,		
			onhide_end:				null,		
			onopen_start:			null,		
			onopen_end:				null,		
			onclose_start:			null,		
			onclose_end:			null,		
			onresize_start:			null,		
			onresize_end:			null,		
			onsizecontent_start:	null,		
			onsizecontent_end:		null,		
			onswap_start:			null,		
			onswap_end:				null,		
			ondrag_start:			null,		
			ondrag_end:				null,		
			//------------------------------------------//
			//North面板配置选项
			north:{
				paneSelector:".ui-layout-north",
				size: 'auto',		//尺寸 数字或者是auto
				minSize : 50,		//最小尺寸
				maxSize : 200,		//最大尺寸	
				title : null,		//如果title不为空，在面板添加一个header
				pinBtn : false,		//是否在header添加钉子按钮
				toggleBtn : false	//是否在header添加切换按钮
			},
			//South面板配置选项
			south: {
				paneSelector:".ui-layout-south",
				size: 'auto',
				minSize : 50,
				maxSize : 200,
				title : null,
				pinBtn : false,
				toggleBtn : false
			},
			//West面板配置选项
			west: {
				paneSelector:".ui-layout-west",
				size: 200,
				minSize : 100,
				maxSize : 300,
				title : null,
				pinBtn : false,
				toggleBtn : false
			},
			//East面板配置选项
			east: {
				paneSelector:".ui-layout-east",
				size: 200,
				minSize : 100,
				maxSize : 300,
				title : null,
				pinBtn : false,
				toggleBtn : false
			},
			//center
			center: {
				paneSelector:".ui-layout-center",	
				minWidth:0,	
				minHeight:0
			}
		},
		_create : function() {
			var o = this.options;
			/*this._setOptions('north');
			this._setOptions('south');
			this._setOptions('west');
			this._setOptions('east');*/
			var layout = this.element.layout(o);
			this._setLayout(layout);
			/*this._initPanel('north');
			this._initPanel('south');
			this._initPanel('west');
			this._initPanel('east');*/
		},
		_setOptions : function(pos){
			var $panel = $('> .ui-layout-' + pos, this.element);
			if($panel.length < 1){
				return;
			}
			var options = this.options,
				$header = $('>.header', $panel),
				hasTitle = $header.length > 0,
				o = options[pos];
				
			this['$' + pos] = $panel;
			this['has_' + pos + '_title'] = hasTitle;
			
			if(o.title || o.pinBtn || o.toggleBtn){
				if(!hasTitle){
					$header = $('<div class="header"></div>').prependTo( $panel );
				}
			}
			if(o.pinBtn || o.toggleBtn){
				$.extend(options[pos], defaultBarOptions);
			}
			if(o.toggleBtn){
				if(pos == 'west' || pos == 'east'){
					o.togglerAlign_closed = 'top';
				}else{
					o.togglerAlign_closed = 'right';
				}
			}
			if(o.title){
				$header.html(o.title);
			}
		},
		_initPanel: function(pos){
			var $panel = $('> .ui-layout-' + pos, this.element);
			if($panel.length < 1){
				return;
			}
			var options = this.options,
				layout = this._getLayout(),
				pinSelector = '.ui-layout-' + pos + ' .pin-button',
				closeId = pos + '-closer',
				panelSelector = '> .ui-layout-' + pos,
				closeSelector = '#' + pos + '-closer',
				o = options[pos];
			
			if(o.pinBtn){
				$("<span></span>").addClass("pin-button").prependTo( $panel );
				layout.addPinBtn($(pinSelector, this.element), pos);
			}
			
			if(o.toggleBtn){
				$("<span></span>").attr("id", closeId).prependTo( $(panelSelector, this.element) );
				layout.addCloseBtn(closeSelector, pos);
			}
		},
		_destroy : function() {
			this._callMethod('destroy');
		},
		_getLayout : function() {
			return this._layout;
		},
		_setLayout : function(layout) {
			this._layout = layout;
		},
		_callMethod : function(action) {
			var input = slice.call(arguments, 1), layout = this
					._getLayout();
	
			layout[action].apply(layout, input);
		},
		resizeContent: function(pane) {
			this._callMethod('resizeContent', pane);
		},
		resizeAll: function() {
			this._callMethod('resizeAll');
		},
		//打开指定面板
		open : function(pane) {
			this._callMethod('open', pane);
		},
		//关闭指定面板
		close : function(pane) {
			this._callMethod('close', pane);
		},
		//显示指定面板
		show : function(pane,noAnimation) {
			this._callMethod('show', pane,noAnimation);
		},
		//隐藏指定面板
		hide : function(pane,noAnimation) {
			this._callMethod('hide', pane,noAnimation);
		},
		//切换指定面板
		toggle : function(pane){
			$console.log('toggle');
			this._callMethod('toggle', pane);
		},
		//-----------------------------------------------------------
		
		toggleslide:function(){
			this._callMethod('toggle', pane, true);
		},
		openslide:function(){
			this._callMethod('open', pane, true);
		},
		//设置指定面板的尺寸
		sizePane : function(pane, size_in_pixels){
			this._callMethod('sizePane', pane, size_in_pixels);
		},
		
		//menuBar 添加方式
		addToggleBtn:function(sel,pane){
			this._layout.addToggleBtn( sel,pane );
		},
		addOpenBtn:function(sel,pane){
			this._layout.addOpenBtn( sel,pane );
		},
		addCloseBtn:function(sel,pane){
			this._layout.addCloseBtn( sel,pane );
		},
		addPinBtn:function(sel,pane){
			this._layout.addPinBtn( sel,pane );
		},
		getWidget:function(){
			return this._layout;
		},
		
		//-----------------------------------------------------------
		//返回指定面板
		get_pane : function(pane) {
			if ($.inArray(pane, [ 'west', 'east', 'center', 'south',
					'north' ]) > -1) {
				var layout = this._getLayout();
				return layout[pane];
			}
			return undefined;
		}
	});
})(jQuery);
/*
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Menubar
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	jquery.ui.position.js
 *	jquery.ui.menu.js
 */
(function( $ ) {

// TODO when mixing clicking menus and keyboard navigation, focus handling is broken
// there has to be just one item that has tabindex
$.widget( "dtui.dtmenubar", {
	options: {
      	menuIcon: false,    //按钮下箭头
      	url: null,
		hoverSwitch: false,
		childrenField: 'children',
		parentField: 'parent',
		textField: 'title',
		idField: 'id',
		select: null
   	},
   	
   	_createMenuItem: function(element, items, createul) {
   		var that = this,
   			o = this.options;
   		
   		if (items && items.length>0) {
	   		var parElem = element;
	   		if (createul) {
	   			parElem = $("<ul></ul>").appendTo(element);
	   		}
	   		$.each(items, function(i, item){
	   			var targetStr="";
	   			if (item.target) targetStr = " target='"+item.target+"'";
	   			var menuItem = $("<li><a href='" +item.url+ "'"+targetStr+">"+item[o.textField]+"</a></li>").appendTo(parElem);
	   			that._createMenuItem(menuItem, item[o.childrenField], true);
	   		})
   		}
   	},
   	addMenu: function(element, items){
   		if(!items){
   			items = element;
   			element = this.element;
   		}
   		this._createMenuItem(element, items, false);
   		var menubarItems = this.element.find('> li');
   		var item = menubarItems.filter( ":not(.ui-menubar-item)" )
   			.addClass( "ui-menubar-item" )
			.attr( "role", "presentation" )
			.children( "button, a" )
   	},
   	_load: function(){
   		var self = this,
			o = this.options,
			url = o.url;
		
		$.getJSON(url, function(data) {
			var result = [],
				temp = [],
				item, i, j;
				
			//取得所有的根节点	
			for(i=0; i<data.length; i++){
				item = data[i];
				if(item[o.parentField] == undefined){
					if(!item[o.childrenField]){
						item[o.childrenField] = [];
					}
					result.push(item);
				}else{
					temp.push(item);
				}
			}
			
			//把所有的子节点添加到根节点上面
			for(i=0; i<temp.length; i++){
				item = temp[i];
				for(j=0; j<result.length; j++){
					if(item[o.parentField] == result[j][o.idField]){
						result[j][o.childrenField].push(item);
					}
				}
			}
			self._createMenuItem(self.element, result, false);
			self._initMenuBar();
		});
    },
    _create: function(){
    	if(this.options.url){
    		this._load();
		}else{
			this._initMenuBar();
		}
    },
	_initMenuBar: function() {
		var that = this;
		
		var items = this.items = this.element.children( "li" )
			.addClass( "ui-menubar-item" )
			.attr( "role", "presentation" )
			.children( "button, a" );
		// let only the first item receive focus
		items.slice(1).attr( "tabIndex", -1 );
	
		this.element
			.addClass( "ui-menubar ui-widget-header ui-helper-clearfix" )
			.attr( "role", "menubar" );
		this._focusable( items );
		this._hoverable( items );
		items.next( "ul" )
			.menu({
				direction: that.options.secHorizontal?"horizontal":"vertical",
				select: function( event, ui ) {
					ui.item.parents( "ul.ui-menu:last" ).hide();
					that._trigger( "select", event, ui );
					that._close();
					// TODO what is this targetting? there's probably a better way to access it
					$(event.target).prev().focus();
				}
			})
			.hide()
			.attr( "aria-hidden", "true" )
			.attr( "aria-expanded", "false" )
			.bind( "keydown.menubar", function( event ) {
				var menu = $( this );
				if ( menu.is( ":hidden" ) )
					return;
				switch ( event.keyCode ) {
				case $.ui.keyCode.LEFT:
					that._left( event );
					event.preventDefault();
					break;
				case $.ui.keyCode.RIGHT:
					that._right( event );
					event.preventDefault();
					break;
				};
			});
		items.each(function() {
			var input = $(this),
				// TODO menu var is only used on two places, doesn't quite justify the .each
				menu = input.next( "ul" );
			
			input.bind( "click.menubar focus.menubar mouseenter.menubar", function( event ) {
				// ignore triggered focus event
				if ( event.type == "focus" && !event.originalEvent ) {
					return;
				}
   				event.preventDefault();
				// TODO can we simplify or extractthis check? especially the last two expressions
				// there's a similar active[0] == menu[0] check in _open
				if ( event.type == "click" && menu.is( ":visible" ) && that.active && that.active[0] == menu[0] ) {
					that._close();
					return;
				}
				if ( ( that.open && event.type == "mouseenter" ) || event.type == "click" ) {
					that._open( event, menu );
				}
   			})
			.bind( "keydown", function( event ) {
				switch ( event.keyCode ) {
				case $.ui.keyCode.SPACE:
				case $.ui.keyCode.UP:
				case $.ui.keyCode.DOWN:
					that._open( event, $( this ).next() );
					event.preventDefault();
					break;
				case $.ui.keyCode.LEFT:
					that._prev( event, $( this ) );
					event.preventDefault();
					break;
				case $.ui.keyCode.RIGHT:
					that._next( event, $( this ) );
					event.preventDefault();
					break;
				}
			})
			.addClass( "ui-button ui-widget ui-button-text-only ui-menubar-link" )
			.attr( "role", "menuitem" )
			.attr( "aria-haspopup", "true" )
			.wrapInner( "<span class='ui-button-text'></span>" );

			// TODO review if these options are a good choice, maybe they can be merged
			if ( that.options.menuIcon ) {
				input.addClass( "ui-state-default" ).append( "<span class='ui-button-icon-secondary ui-icon ui-icon-triangle-1-s'></span>" );
				input.removeClass( "ui-button-text-only" ).addClass( "ui-button-text-icon-secondary" );
			}
   			
			if ( !that.options.buttons ) {
				// TODO ui-menubar-link is added above, not needed here?
				input.addClass( "ui-menubar-link" ).removeClass( "ui-state-default" );
			};			
			
		});
		
		
		that._on( {
			keydown: function( event ) {
				if ( event.keyCode == $.ui.keyCode.ESCAPE && that.active && that.active.menu( "left", event ) !== true ) {
					var active = that.active;
					that.active.blur();
					that._close( event );
					active.prev().focus();
				}
			},
			focusin: function( event ) {
				clearTimeout( that.closeTimer );
			},
			focusout: function( event ) {
				that.closeTimer = setTimeout( function() {
					that._close( event );
				}, 100);
			}
		});
	},
	
	_destroy : function() {
		var items = this.element.children( "li" )
			.removeClass( "ui-menubar-item" )
			.removeAttr( "role", "presentation" )
			.children( "button, a" );
		
		this.element
			.removeClass( "ui-menubar ui-widget-header ui-helper-clearfix" )
			.removeAttr( "role", "menubar" )
			.unbind( ".menubar" );
		
		items
			.unbind( ".menubar" )
			.removeClass( "ui-button ui-widget ui-button-text-only ui-menubar-link ui-state-default" )
			.removeAttr( "role", "menuitem" )
			.removeAttr( "aria-haspopup", "true" )
			// TODO unwrap?
			.children( "span.ui-button-text" ).each(function( i, e ) {
				var item = $( this );
				item.parent().html( item.html() );
			})
			.end()
			.children( ".ui-icon" ).remove();

		this.element.find( ":ui-menu" )
			.menu( "destroy" )
			.show()
			.removeAttr( "aria-hidden", "true" )
			.removeAttr( "aria-expanded", "false" )
			.removeAttr( "tabindex" )
			.unbind( ".menubar" );
	},
	
	_close: function() {
		if ( !this.active || !this.active.length )
			return;
		this.active
			.menu( "collapseAll" )
			.hide()
			.attr( "aria-hidden", "true" )
			.attr( "aria-expanded", "false" );
		this.active
			.prev()
			.removeClass( "ui-state-active" )
			.removeAttr( "tabIndex" );
		this.active = null;
		this.open = false;
	},
	
	_open: function( event, menu ) {
		// on a single-button menubar, ignore reopening the same menu
		if ( this.active && this.active[0] == menu[0] ) {
			return;
		}
		// TODO refactor, almost the same as _close above, but don't remove tabIndex
		if ( this.active ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
			this.active
				.prev()
				.removeClass( "ui-state-active" );
		}
		// set tabIndex -1 to have the button skipped on shift-tab when menu is open (it gets focus)
		var button = menu.prev().addClass( "ui-state-active" ).attr( "tabIndex", -1 );
		this.active = menu
			.show()
			.position( {
				my: "left top",
				at: "left bottom",
				of: button
			})
			.removeAttr( "aria-hidden" )
			.attr( "aria-expanded", "true" )
			.menu("focus", event, menu.children( "li" ).first() )
			// TODO need a comment here why both events are triggered
			.focus()
			.focusin();
		this.open = true;
	},
	
	// TODO refactor this and the next three methods
	_prev: function( event, button ) {
		button.attr( "tabIndex", -1 );
		var prev = button.parent().prevAll( "li" ).children( ".ui-button" ).eq( 0 );
		if ( prev.length ) {
			prev.removeAttr( "tabIndex" )[0].focus();
		} else {
			var lastItem = this.element.children( "li:last" ).children( ".ui-button:last" );
			lastItem.removeAttr( "tabIndex" )[0].focus();
		}
	},
	
	_next: function( event, button ) {
		button.attr( "tabIndex", -1 );
		var next = button.parent().nextAll( "li" ).children( ".ui-button" ).eq( 0 );
		if ( next.length ) {
			next.removeAttr( "tabIndex")[0].focus();
		} else {
			var firstItem = this.element.children( "li:first" ).children( ".ui-button:first" );
			firstItem.removeAttr( "tabIndex" )[0].focus();
		}
	},

	// TODO rename to parent
	_left: function( event ) {
		var prev = this.active.parent().prevAll( "li:eq(0)" ).children( ".ui-menu" ).eq( 0 );
		if ( prev.length ) {
			this._open( event, prev );
		} else {
			var lastItem = this.element.children( "li:last" ).children( ".ui-menu:first" );
			this._open( event, lastItem );
		}
	},
	
	// TODO rename to child (or something like that)
	_right: function( event ) {
		var next = this.active.parent().nextAll( "li:eq(0)" ).children( ".ui-menu" ).eq( 0 );
		if ( next.length ) {
			this._open( event, next );
		} else {
			var firstItem = this.element.children( "li:first" ).children( ".ui-menu:first" );
			this._open( event, firstItem );
		}
	}
});

}( jQuery ));
/*
 * 弹出面板菜单
 */

(function( $, undefined ) {
	
$.widget('dtui.dtmenupanel', {
	version:'1.0.1',
	options: {
		width: "160px",
		panelWidth: "320px",
		subHeaderWidth: "120px"
	},
	_create: function(){
		var self = this,
			element = this.element,
			options = this.options;
		
		element
			.css({width: options.width})
			.addClass('ui-widget dtui-menupanel ui-widget-content ui-helper-reset')
			.children('div')
				.addClass('dtui-menupanel-item ui-helper-clearfix')
				.filter(':last')
					.addClass('dtui-menupanel-item-last')
				.end()
				.children('h4')
					.addClass('dtui-menupanel-header ui-widget-header ui-helper-reset')
					.on('mouseover', function(){
						var $ele = $(this);
						self._clearTimer();
						self._timer = setTimeout(function(){
							self._active($ele.parent());
						}, 100);
					})
					.append('<span class="ui-icon ui-icon-carat-1-e"></span>')
				.end()
				.children('div')
					.addClass('dtui-menupanel-panel  ui-widget-content ui-helper-reset')
					.css({left: options.width, width: options.panelWidth})
					.on('mouseleave ', function(){
						self._timer = setTimeout(function(){
							self._hideActive();
						}, 200);
					})
					.find('ul')
						.addClass('ui-helper-reset')
						.find('li')
							.addClass('dtui-menupanel-group ui-helper-clearfix')
					.end()
					.find('li h5')
						.addClass('dtui-menupanel-subheader ui-helper-reset')
						.css({width: options.subHeaderWidth})
					.end()
					.find('li div')
						.addClass('dtui-menupanel-subpanel')
				.end();
	},
	_active: function(item){
		var self = this;
		this._hideActive();
		self.activeItem = item;
		item
			.children('.dtui-menupanel-header')
				.addClass('ui-state-active')
			.end()
			.children('.dtui-menupanel-panel')
				.show();
	},
	_hideActive: function(){
		var item = this.activeItem;
		if(!item) return;
		item
			.children('.dtui-menupanel-header')
				.removeClass('ui-state-active')
			.end()
			.children('.dtui-menupanel-panel')
				.hide()
				.off('mouseover');
	},
	_clearTimer: function(){
		if(this._timer){
			clearTimeout(this._timer);
		}
	}
});

})( jQuery );/**
 * 
 */

(function($, undefined) {
	$.dtmessagebox = {
			show: function(msg, title, icon, buttons, callback, defaultButton) {
				var msgHtml = '<div><div style="margin:15px">';
				if (icon) {
					//icon可以是样式或url
					if (icon.charAt(0) == '.' && (icon.charAt(1) != '.' ||icon.charAt(1) != '/'))
						msgHtml += '<div style="float:left; width:48px; height:48px;" class="'+ icon.slice(1)+'"></div>';
					else
						//msgHtml += '<img style="float:left;" src="'+ icon+'"/>';
						msgHtml += '<div style="float:left; width:48px; height:48px;background:url('+icon+') no-repeat;"></div>';
						//msgHtml += '<div style="float:left; width:48px; height:48px;" class="ui-messagebox-image-warn"></div>';
				}
				msgHtml += '<div style="margin-left:5px; float:left;">'+msg+'</div>';
				msgHtml += '</div>';		
				msgHtml += 	'</div>';
				
//				var d=dt$.dialog(dt$.obj(msgHtml).jqobj, {"title":title, modal:true, resizable:false, "buttons": buttons, close: function(){
//					if (callback) callback(dt$.dialog(this).option('dialogResult'));
//				}});
//				d.showModal();
				$(msgHtml).dtdialog({"title":title, modal:true, resizable:false, "buttons": buttons, close: function(){
					if (callback) callback($(this).dtdialog('option','dialogResult'));
				}});
			},
			alert: function(msg, title, callback) {
				this.show(msg, title||'提示', null, {'确定': function(){
					$(this).dtdialog('close');			
				}}, callback);
			},
			warn: function(msg, title, callback) {
				this.show(msg, title||'警告', '.ui-messagebox-image-warn', {'确定': function(){
					$(this).dtdialog('close');	
				}}, callback);
			},
			error: function(msg, title, callback) {
				this.show(msg, title||'错误', '.ui-messagebox-image-error', {'确定': function(){
					$(this).dtdialog('close');	
				}}, callback);
			},
			success: function(msg, title, callback) {
				this.show(msg, title||'成功', '.ui-messagebox-image-donne', {'确定': function(){
					$(this).dtdialog('close');	
				}},callback);
			},
			confirm: function(msg, title, callback) {
				this.show(msg, title||'询问', '.ui-messagebox-image-question', {
					'确定': function(){
						$(this).dtdialog('option', 'dialogResult', $.dtdialog.DR_OK);	
						$(this).dtdialog('close');	
					},
					'取消': function() {
						$(this).dtdialog('option', 'dialogResult', $.dtdialog.DR_CANCEL);	
						$(this).dtdialog('close');	
					}
				}, callback);
			}
		};
})(jQuery);/*----------------------------------------------
//文件名：        年月选择下拉框
//文件功能描述：   只选择年份和月份的日期下拉框
//             
//----------------------------------------------------*/
(function($,undefined){

	$.widget("dtui.dtmonthpicker",$.dtui.dtabscombobox,{
		options:{
			dropPanelHeight: 'auto',
			dropPanelWidth: '220px',
			months:['一月','二月','三月','四月','五月','六月',
			        '七月','八月','九月','十月','十一月','十二月'],
			valueFormat: 'yyyymm',
			textFormat: 'yyyy年m月'
		},
		
		
		_init: function() {
			this._super();
						
			if (this.options.initValue){
				var ym = this._parseYearMonth(this.options.initValue);
				this.selectYear = ym.year;
				this.selectMonth = ym.month;
				this._adjustYearPanelContent();
				this._refreshInputValue();
				this._e.valueText.attr('value', this._e.valueText.val());
				this._e.inputText.attr('value', this._e.inputText.val());
			}
			else {
				var date = new Date();
				this.selectYear = date.getFullYear();
				this.selectMonth = date.getMonth()+1;
			}
			this._refreshSelection();
		},
		
		_adjustYearPanelContent: function() {
			if (this.selectYear >= this.beginYear && this.selectYear < this.beginYear + 10){
				return;
			}
			if (this.selectYear >= this.beginYear + 10) {
				while(this.selectYear >= this.beginYear + 10){
					this.beginYear += 10;
				}
				this._refreshYearPanel();
			}
			else if (this.selectYear < this.beginYear){
				while(this.selectYear < this.beginYear){
					this.beginYear -= 10;
				}
				this._refreshYearPanel();
			}
		},
		
		_parseYearMonth: function(yearMonth) {
			if (yearMonth instanceof Date){
				//由于getMonth返回的值是0~11，所以要+1
				return {year:yearMonth.getFullYear(), month: yearMonth.getMonth()+1};
			}
			else {
				var vf = this.options.valueFormat,
					yStartPos = vf.indexOf('yyyy'),
					mStartPos = vf.indexOf('mm'),
					mLength = 2;
				if (mStartPos >= 0) {
					monthStr = yearMonth.substr(mStartPos, 2);
					mLength = 2;
				}
				else {
					mStartPos = vf.indexOf('m');
					var nextChar = yearMonth[mStartPos+1],
						reg = /^\d+$/;
				    if (!nextChar.match(reg)){
				    	mLength = 1;
				    }else{    
				        mLength = 2;        
				    }    					
				}
				return {year:Number(yearMonth.substr(yStartPos,4)), month: Number(yearMonth.substr(mStartPos,mLength))};
			}
		},
		
		_refreshMonthSelection: function() {
			var month = this.selectMonth;
		
			this.monthPanel.find('li.item').removeClass('ui-state-active');
			//根据月份获取该月份所在的li的位置
			var index = ((month-1)%6) * 2 +  Math.floor((month-1)/6);
			this.monthPanel.find('li.item').eq(index).addClass('ui-state-active');
			
		},
		
		_refreshYearSelection: function() {
			this.yearPanel.find('li.item').removeClass('ui-state-active');
			//年份相对于当前页第一年的偏移量
			var yearOffset = this.selectYear - this.beginYear;
			if (yearOffset>=0 && yearOffset<10){
				//根据年份获取该年份所在的li的位置
				var index = (yearOffset%5) * 2 +  Math.floor(yearOffset/6);
				this.yearPanel.find('li.item').eq(index).addClass('ui-state-active');
			}
		},
		
		/**
		 * 根据年月值设置年月面板对应年月的选中状态
		 * @param yearMonth
		 */
		_refreshSelection: function() {
			this._refreshMonthSelection();
			this._refreshYearSelection();
		},
						
		
		_buildContent:function(){
			 var dropPanel=this._e.selectOption;
			 dropPanel.css('minHeight', 230);
			 this.totalContainer.addClass("ui-monthpicker");
			 //dropPanel.addClass('ui-monthpicker');
			 this.yearMonthContainer = $('<div/>').addClass('yearmonthcontainer').appendTo(dropPanel);
			 this.monthPanel = $('<ul/>').addClass("monthpanel").appendTo(this.yearMonthContainer);
			 this.yearContainer = $('<div/>').addClass("yearcontainer").appendTo(this.yearMonthContainer);
			 //清除浮动
			 $('<div style="clear:both;"></div>').appendTo(this.yearMonthContainer);
			 this._buildMonthElem();
			 this._buildYearElem();
			 this._buildButtonPanel();
		},
		
		_buildMonthElem: function(){
			var months = this.options.months; 
			for (var i=0; i<months.length/2; i++){
				$('<li/>').addClass('item').text(months[i]).data('value', i+1).appendTo(this.monthPanel);
				$('<li/>').addClass('item').text(months[i+6]).data('value', i+7).appendTo(this.monthPanel);
			}
		},
		
		_buildYearElem: function() {
			var buttonPanel = this.buttonPanel = $('<div/>').addClass('pagingpanel').appendTo(this.yearContainer);
			this.prevButton = $('<a class="ui-datepicker-prev ui-corner-all" data-handler="prev" data-event="click" title=""><span class="ui-icon ui-icon-circle-triangle-w"></span></a>')
			.appendTo(buttonPanel);
			this.nextButton = $('<a class="ui-datepicker-next ui-corner-all" data-handler="next" data-event="click" title=""><span class="ui-icon ui-icon-circle-triangle-e"></span></a>')
			.appendTo(buttonPanel);
			
			var yearPanel = this.yearPanel = $('<ul/>').addClass("yearpanel").appendTo(this.yearContainer);
			
			var date = new Date();
			var year = date.getFullYear();
			var beginYear = this.beginYear = year - 4;
			for (var i=beginYear; i <= year; i++){
				$('<li/>').addClass('item').text(i).appendTo(this.yearPanel);
				$('<li/>').addClass('item').text(i+5).appendTo(this.yearPanel);
			}
		},
		
		_buildButtonPanel: function() {
			this.buttonPanel = $('<div/>').addClass('buttonpanel ui-widget ui-widget-content').appendTo(this._e.selectOption);
			var btnOpt = {width: 60, height:30};
			this.btnOk = $('<button type="button">确定</button>').dtbutton(btnOpt).appendTo(this.buttonPanel);
			this.btnClear = $('<button type="button">清空</button>').dtbutton(btnOpt).appendTo(this.buttonPanel);
			this.btnCurMonth = $('<button type="button">本月</button>').dtbutton(btnOpt).appendTo(this.buttonPanel);
		},
		
		_refreshInputValue: function() {
			if(!this.selectYear || !this.selectMonth) {
				this._e.inputText.val("");
				this._e.valueText.val("");
				return;
			}
			var valueText = this._FormatYearMonth(this.selectYear, this.selectMonth, this.options.valueFormat),
				inputText = this._FormatYearMonth(this.selectYear, this.selectMonth, this.options.textFormat);
			this._e.valueText.val(valueText);
			this._e.inputText.val(inputText);
		},
				
		/*
		 * 根据beginYear的值刷新年面板中显示的年份
		 */
		_refreshYearPanel: function() {
			var self = this;
			self.yearPanel.find('li.item').each(function(i){
				//var year = Number($(this).text());
				//$(this).text(year-10);
				var offset = Math.floor(i/2)+(i%2)*5;
				$(this).text(self.beginYear + offset);
			});
		},
		
		//binds checkbox events
		_bindEvent:function(){
			this._super();
			this._hoverable(this._e.selectOption.find('li.item'));
			var self=this;
			this.prevButton.click(function(){
				self.beginYear = self.beginYear - 10;
				self._refreshYearPanel();
				self._refreshYearSelection();
			});
			this.nextButton.click(function(){
				self.beginYear = self.beginYear + 10;
				self._refreshYearPanel();
				self._refreshYearSelection();
			});
			this.yearPanel.find('li.item').click(function(){
				self.yearPanel.find('li.item').removeClass('ui-state-active');
				$(this).addClass('ui-state-active');
				self.selectYear = Number($(this).text());
				self._refreshInputValue();
				self._trigger("change");
			});
			this.monthPanel.find('li.item').click(function(){
				self.monthPanel.find('li.item').removeClass('ui-state-active');
				$(this).addClass('ui-state-active');	
				self.selectMonth = Number($(this).data('value'));
				self._refreshInputValue();
				self._trigger("change");
			});			
			this.btnOk.click(function() {
				self._refreshInputValue();
				self._hideOption();				
			});
			this.btnClear.click(function() {
				self._clear();
				self._hideOption();
				self._trigger("change");
			});
			this.btnCurMonth.click(function() {
				var date = new Date();
				self.selectYear = date.getFullYear();
				self.selectMonth = date.getMonth()+1;
				self._adjustYearPanelContent();
				self._refreshSelection();
				self._refreshInputValue();
				self._trigger("change");
			});
		},
		
		_showOption: function(){
			this._adjustYearPanelContent();
			this._refreshYearSelection();
			this._super();
		},
		//after addItem  and removeItem rebuild menu  and rebinds checkbox  event
		refresh:function(){
			var self=this;
		},
		_clear: function() {
			var self = this;
			if (self._e.inputText.val() && self._e.valueText.val()){
				var date = new Date();
				self.selectYear = date.getFullYear();
				self.selectMonth = date.getMonth()+1;
				self._adjustYearPanelContent();
				self._refreshSelection();
				self._e.inputText.val('');
				self._e.valueText.val('');
				return true;
			}
			return false;
		},
				
        /**
         * 根据当前的值刷新内容区域的选择状态
         */
        _refreshContentState: function() {
			//使valueText和inputText中的内容与selection对象的内容保持一致
        	//需要处理空值
			var ym = this._parseYearMonth(this._e.valueText.val());
			this.selectYear = ym.year;
			this.selectMonth = ym.month;
			this._adjustYearPanelContent();
			this._refreshSelection();
        },
        
        /**
         * 根据值获取对应的文本
         * @param value
         */
        _getText: function(value){
        	if(value){
        		var ym = this._parseYearMonth(value);
    			return this._FormatYearMonth(ym.year, ym.month, this.options.textFormat);
        	}
        	else {
        		return '';
        	}
        },
        _FormatYearMonth: function(year, month, format){
			var v = format;
			v = v.replace('yyyy', year);
			v = v.replace('mm', (month<10?'0':'') + month);
			v = v.replace('m', month);
			return v;
        }
        
	});
})(jQuery);/*
 * 
 * 横向菜单
 */

(function( $, undefined ) {

var 
	main_menu_class = 'dt-navmain',
	sub_menu_class = 'dt-navsub';
	
$.widget('dtui.dtnavbar', {
	version:'1.0.1',
	options: {
		url: false,
		activeMenu: 0,
		hoverSwitch: false,
		childrenField: 'children',
		parentField: 'parent',
		textField: 'title',
		idField: 'id',
		main_menu: '.' + main_menu_class,
		sub_menu: '.' + sub_menu_class,
		select: null,
		showSubMenu: false,
		menuItems: null
	},
	_create: function(){
		var options = this.options,
			url = options.url,
			menuItems = options.menuItems;
		
		if(url){
			this._load(url);
		}else if(menuItems){
			this._createElement(menuItems);
			this._initNav();
		}else{
			this._initNav();
		}
	},
	_initBarItem: function(elements){
		var items = elements.children('a'),
			self = this;
		
		elements.addClass('ui-menubar-item')
			.attr( "role", "presentation" );
		
		items.addClass('ui-button ui-button-text-only dt-navbar-item ui-corner-all ui-state-default')
			.attr('role', 'menuitem')
			.wrapInner('<span class="ui-button-text"></span>')
			.click(function(){
				var $link = $(this);
				self._trigger('select', null, {item: $link.parent()[0], itemlink:$link[0]});
			});
	},
	_initSubItem: function(items){
		var self = this,
			options = this.options;
		
		items
			.hide()
			.addClass('ui-helper-clearfix');
		
		items
			.children('li')
				.children('a').click(function(event){
					event.stopPropagation();
					if(options.showSubMenu){
						if(self.activeSubMenu
								&& self.activeSubMenu.parent()[0] != this){
							self.activeSubMenu.hide();
						}
						self.activeSubMenu = $(this).parent().children('.dt-navsub-menu').toggle();
					}
					self._trigger('select', null, {item: $(this).parent()[0], itemlink:this});
				}).end()
				.addClass('dt-navsub-item')
				.children('ul')
					.addClass('dt-navsub-menu')
					.menu({
						select: function(event, obj){
							var $link = $(obj.item).children('a');
							self._trigger('select', null, {item: obj.item, itemlink:$link[0]});
						}
					})
					.hide()
					.end();
		items.each(function(){
			$(this)
				.children('li:not(:last)')
					.after('<li class="dt-navsub-gap"><span>|</span></li>');
		});
					
		$(document).click(function(){
			if(self.activeSubMenu){
				self.activeSubMenu.hide();
			}
		});
	},
	_initNav: function(){
		var self = this,
			element = self.element,
			options = self.options,
			enable_index = options.activeMenu,
			main_element = this.main_element = $(options.main_menu, element),
			sub_element = this.sub_element = $(options.sub_menu, element),
			main_enable = $('li:eq('+enable_index+')', main_element),
			sub_enable = $('ul:eq('+enable_index+')', sub_element);
			this._hasSub = false;
			
		element.addClass('dt-nav ui-widget');
		main_element.addClass('dt-navbar ui-widget-header ui-helper-clearfix');
		$('ul', main_element).addClass('ui-helper-reset');
		
		self._initBarItem($('li', main_element));
		if(sub_element.length > 0){
			self._initSubItem(sub_element.children('ul'));
			element.addClass('dt-navbar-hassub');
			this._hasSub = true;
		}
		sub_element.addClass('ui-widget-content');
		
		self._open($('a', main_enable), sub_enable);
		main_element.on('mouseenter', 'li', function(event){
			var target = $(this);
			if(target.hasClass('ui-state-disabled')){
				return;
			}
			var	index = target.index(),
				on_sub = sub_element.children(':eq('+index+')');
			self._open($('a', target), on_sub);
		});
	},
	_open: function(target, menu){
		target
			.addClass('dt-navbar-item-active ui-state-active');
			
		if(this._hasSub){
			target
				.addClass('ui-corner-top')
				.removeClass('ui-corner-all');
		}
		// 如果是已经打开的菜单，什么也不做
		if ( this.active && this.active[0] && this.active[0] == menu[0] ) {
			return;
		}
		// 隐藏以显示的菜单
		if ( this.active ) {
			this.active.hide();
		}
		if(this.activeBar){
			this.activeBar.removeClass('dt-navbar-item-active ui-state-active');
			if(this._hasSub){
				this.activeBar
					.addClass('ui-corner-all')
					.removeClass( 'ui-corner-top' );
			}
		}
		this.active = menu.show();
		this.activeBar = target
			.attr( "tabIndex", -1 );
		
	},
	_createElement: function(data){
		var o = this.options,
			$nav_main = $('<div class="'+ main_menu_class +'"><ul></ul></div'),
			main_element = $('ul', $nav_main),
			sub_element = $('<div class="'+ sub_menu_class +'"></div>'),
			item, i;
		
		for(i=0; i<data.length; i++){
			item = data[i];
			this._createItem(main_element, item, 0);
			sub_element.append(this._createItems(item[o.childrenField], 1, true));
		}
		
		this.element.append($nav_main);
		this.element.append(sub_element);
	},
	_createItems: function(items, level, depth){
		var $list = $('<ul></ul>'),
			subitem, i;
		for(i=0; i<items.length; i++){
			subitem = items[i];
			this._createItem($list, subitem, level, depth);
		}
		return $list;
	},
	_createItem: function($list, item, level, depth){
		var o = this.options,
			url = item.url ? item.url : 'javascript:void(0)',
			level = level ? level : 0;
		
		$listItem = $('<li></li>').appendTo($list)
			.data('menudata', item)
			.data('level', level);
		$link = $('<a href="' + url + '">'+item[o.textField]+'</a>')
			.appendTo($listItem);
		
		if(depth && item[o.childrenField]){
			$listItem.append(this._createItems(item[o.childrenField], level+1));
		}
	},
	_load: function(url){
		var self = this,
			o = this.options;
		
		$.getJSON(url, function(data) {
			var result = [],
				temp = [],
				item, i, j;
				
			//取得所有的根节点	
			for(i=0; i<data.length; i++){
				item = data[i];
				if(item[o.parentField] == undefined){
					if(!item[o.childrenField]){
						item[o.childrenField] = [];
					}
					result.push(item);
				}else{
					temp.push(item);
				}
			}
			
			//把所有的子节点添加到根节点上面
			for(i=0; i<temp.length; i++){
				item = temp[i];
				for(j=0; j<data.length; j++){
					if(item[o.parentField] == data[j][o.idField]){
						data[j][o.childrenField].push(item);
					}
				}
			}
			self._createElement(result);
			self._initNav();
		});
	},
	_query: function(ele){
		return ele.jquery ? ele : $(ele);
	},
	enable: function(item){
		this._query(item).removeClass('ui-state-disabled');
	},
	disable: function(item){
		this._query(item).addClass('ui-state-disabled');
	},
	addMenu: function(data){
		var self = this,
			element = self.element,
			o = self.options,
			main_element = $(o.main_menu, element),
			sub_element = $(o.sub_menu, element);
		
		var item = this._createItem(main_element, data);
		self._initBarItem(item);
		
		var subItems = this._createItems(data[o.childrenField], 0, true);
		$subItems = $(subItems);
		$subItems.appendTo(sub_element);
		this._initSubItem($subItems);
	},
	removeMenu: function(menu){
		var $menu = this._query(menu);
		
		if($menu.hasClass('ui-menubar-item')){
			this._removeMenuBar($menu);
		}else if($menu.hasClass('ui-menu-item')){
			$menu.remove();
		}
	},
	_removeMenuBar: function($menu){
		var element = this.element,
			o = this.options;
		
			index = $menu.index(),
			$subItem = element.find(o.sub_menu+' ul:eq('+ index +')');
		
		if ( this.active && this.active[0] == $subItem[0] ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
			
			this.active = null;
		}
			
		$menu.remove();
		$subItem.remove();
	},
	getMenuInfo: function(ele){
		var element = this.element,
			o = this.options,
			$ele = this._query(ele),
			$aele = $ele.find('a:eq(0)'),
			info = {
				text: $aele.text(),
				url: $aele.attr('href')
			};
		if($ele.hasClass('ui-menubar-item')){
			var index = $ele.index(),
				sub_element = $(o.sub_menu, element);
			info.child = $('ul:eq('+index+')', sub_element);;
			
		}
		return info;
	},
	option: function(name){
		if(this.options[name]){
			return this.options[name];
		}else{
			return null;
		}
	},
	addExtBar: function(content){
		var $extBar = $('.dt-nav-extbar', this.main_element);
		if(!$extBar.length){
			$extBar = $('<div class="dt-nav-extbar"></div>').appendTo(this.main_element)
		}
		$(content).appendTo($extBar);
	}
});

})( jQuery );(function($, undefined) {
	$.widget( "dtui.dtoverlay", {
		version: "1.10.1",
		options: {
			appendTo: "body",
		    color: '#000',
		    opacity: 0.5,
		    show:true,
		    text: '',
		    showIcon: true,
		    focusElem:null
		},

		_create: function() {
			var maskElem = this._appendTo();
			//var maskElem = this.element;
			//this.overlay = $("<div>")
			this.overlay = this.element
				//.addClass("ui-widget-overlay ui-front")
				.addClass("loadmask")
				.appendTo( maskElem );
			
			var msgcontainer = this.msgContainer = $("<div>")
				.addClass("loadmask-msg")
				.appendTo( maskElem );
			this.msg =$("<div>")
				.addClass(this.options.showIcon ? "mask_lading" : "normal")
				.text(this.options.text)
				.appendTo( this.msgContainer );
			
			msgcontainer.css({
				left: (maskElem.width()-msgcontainer.width())/2,
				top : (maskElem.height()-msgcontainer.height())/2,
			});
			
			if (this.options.focusElem){
				$(this.options.focusElem).appendTo(this.overlay);
			}
			this._on( this.overlay, {
				mousedown: "_keepFocus"
			});
		},
		
		_init: function() {
			if (this.options.show){
				this.show();
			}
			else {
				this.hide();
			}
		},

		_keepFocus: function( event ) {
			var focusElem = this.options.focusElem;
			function checkFocus() {
				var activeElement = this.document[0].activeElement,
					isActive = focusElem === activeElement ||
						$.contains( focusElem, activeElement );
				if ( !isActive ) {
					if (this.options.focusElem)
						$(this.options.focusElem).focus();
				}
			}
			event.preventDefault();
			checkFocus.call( this );
			// support: IE
			// IE <= 8 doesn't prevent moving focus even with event.preventDefault()
			// so we check again later
			this._delay( checkFocus );
		},
		
		_appendTo: function() {
			var element = this.options.appendTo;
			if ( element && (element.jquery || element.nodeType) ) {
				return $( element );
			}
			return this.document.find( element || "body" ).eq( 0 );
		},

		_destroy: function() {
			this.overlay.remove();
			this.msgContainer.remove();
			this.overlay = null;
		},

		widget: function() {
			return this.overlay;
		},

		disable: $.noop,
		enable: $.noop,
		show: function() {
			this.overlay.show();
			this.msgContainer.show();
		},
		hide: function() {
			this.overlay.hide();
			this.msgContainer.hide();
		},
		close: function() {
			this.hide();
			this._destroy();
		}
	});
	
	$.fn.extend({
		mask: function(text, showIcon, color){
			var o = {text: text, appendTo: this},
				overlay  = this.find('div.loadmask');
			if (showIcon !== undefined){
				o.showIcon = showIcon;
			}
			
			if (color !== undefined){
				o.color = color;
			}
			
			if (overlay.length === 0){
				overlay = $('<div>').dtoverlay(o);
			}
			else {
				overlay.dtoverlay('option', o);
			}
			//overlay.dtoverlay('show');
		},

		unmask: function() {
			var overlay = this.find('div.loadmask');
			if (overlay.length > 0){
				overlay.dtoverlay('close');
			}
		}
	});

})(jQuery);/**
 * 
 */

(function( $, undefined ) {
	var defClass = 'dtui-panel',
		defHeaderClass = 'dtui-panel-header',
		defTitleClass = 'dtui-panel-title',
		defContentClass = 'dtui-panel-content';
	
	$.widget("dtui.dtpanel", {
		options: {
			title: null,
			height: null,
			width: null,
			url: null,
			open : null,
			close : null,
			min : null,
			extend : null,
			ajax: false
		},
		_create: function(){
			this.refresh();
		},
		getData: function(){
			return this.options;
		},
		refresh: function(){
			this.element
				.addClass(defClass + ' ui-widget ui-helper-reset');
			if(this.options.width){
				this.element.width(this.options.width);
			}
			this._createHead();
			this._createContent();
		},
		/**
		 * 创建面板头部
		 */
		_createHead: function(){
			var self = this;
			
			if(this.options.url != null && this.options.url.length > 0){
				this._head = $("<div></div>")
					.addClass(defHeaderClass)
					.appendTo(this.element);
				this._title = $("<span></span>")
					.addClass(defTitleClass)
					.appendTo(this._head);
			}else{
				this._head = this.element.children("." + defHeaderClass);
				this._title = this.element.children("." + defTitleClass);
			}
			this._title.html(this.options.title);
			this._head.append(this._title)
				.addClass('ui-widget-header ui-helper-reset ui-helper-clearfix ui-corner-top');
			
			if(!this.options.buttons){
				this.options.buttons = [{
					type: "close"
				},{
					type: "min"
				}];
			}
			$.each(this.options.buttons, function(i, prop){
				self._head.append(self._createButton(prop));
			});
		},
		/**
		 * 创建最小化和关闭按钮
		 * @param prop
		 * @returns
		 */
		_createButton: function(config){
			var prop = this['_' + config.type]();
			var button = $('<a></a>')
				.addClass('dtui-panel-btn ui-corner-all '+prop.className);
			$('<span></span>')
				.addClass('ui-icon ' + prop.iconClass)
				.appendTo(button);
			
			button.click(function(event){
				prop.click.call(this, event);
			});
			this._hoverable(button);
			this[config.type+'Btn'] = button;
			return button;
		},
		/**
		 * 创建面板内容
		 */
		_createContent: function(){
			if(this.options.url!=null&&this.options.url.length>0){
				this._content = $("<div></div>")
					.addClass(defContentClass)
					.appendTo(this.element);
			}else{
				this._content = this.element.children("." + defContentClass);
			}
			if(this.options.height){
				this._content.height(this.options.height);
			}
			this._content.addClass('ui-widget-content ui-helper-reset ui-corner-bottom');
			if(this.options.url){
				if(this.ajax){
					this._content.load(this.options.url);
				}else{
					var html  = '<iframe frameborder="0" src="' + this.options.url + '" scrolling="auto" />';
					this.frame = $(html).appendTo(this._content);
				}
			}
		},
		/**
		 * 销毁面板
		 * @returns
		 */
		destroy: function(){
			this._content.unbind().removeClass().remove();
			$("*", this.element).unbind().removeClass().remove();
			this.element.unbind().removeClass().remove();
			return $.Widget.prototype.destroy.call( this );
		},
		/**
		 * 关闭
		 */
		_close: function(){
			var self = this;
			return {
				className: 'dtui-panel-close',
				iconClass: "ui-icon-close",
				click: function(){
					self.close();
				}
			};
		},
		/**
		 * 最小化
		 */
		_min: function(){
			var self = this;
			return {
				className: 'dtui-panel-min',
				iconClass: "ui-icon-minus",
				click: function(){
					if(self._mined){
						self.extend();
						self._mined = false;
					}else{
						self.min();
						self._mined = true;
					}
				}
			};
		},
		open: function(){
			this.element.slideDown("fast");
			this._trigger('open', null, this.element);
		},
		close: function(){
			this.element.slideUp("fast");
			this._trigger('close', null, this.element);
		},
		min: function(event){
			this._content.slideUp("fast");
			this._head.addClass('ui-corner-bottom');
			this._trigger('min', null, this.element);
		},
		extend: function(){
			this._content.slideDown("fast");
			this._head.removeClass('ui-corner-bottom');
			this._trigger('extend', null, this.element);
		},
		load: function(url){
			if(this.ajax){
				this._content.load(url);
			}else{
				this.frame.attr('src', url);
			}
		}
	});
})( jQuery );/*!
 * jQuery UI Progressbar 1.10.1
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/progressbar/
 *
 * Depends:
 *   jquery.ui.core.js
 *   jquery.ui.widget.js
 */
(function( $, undefined ) {

$.widget( "ui.dtprogressbar", {
	version: "1.10.1",
	options: {
		max: 100,
		value: 0,
		showLabel: true, 	//是否显示进度值
		startText: '加载中...',
		completeText: '完成！',

		change: null,
		complete: null
	},

	min: 0,

	_create: function() {
		// Constrain initial value
		this.oldValue = this.options.value = this._constrainedValue();

		this.element
			.addClass( "ui-progressbar ui-widget ui-widget-content ui-corner-all" )
			.attr({
				// Only set static values, aria-valuenow and aria-valuemax are
				// set inside _refreshValue()
				role: "progressbar",
				"aria-valuemin": this.min
			});
		
		this.labelDiv = $("<div class='ui-progressbar-label'></div>")
			.appendTo( this.element );
		
		this.labelDiv.css('line-height', this.labelDiv.height()+'px');
		
		if (!this.options.showLabel)
			this.labelDiv.hide();
		
		this.valueDiv = $( "<div class='ui-progressbar-value ui-widget-header ui-corner-left'></div>" )
			.appendTo( this.element );

		this._applyOptions();
		this._refreshValue();
	},

	_applyOptions: function(){
		var o=this.options;
		if(this.options.width){
		   this._setupWidth(this.options.width);
		}
		if(this.options.height){
		   this._setupHeight(this.options.height);
		}
	},
	
	_destroy: function() {
		this.element
			.removeClass( "ui-progressbar ui-widget ui-widget-content ui-corner-all" )
			.removeAttr( "role" )
			.removeAttr( "aria-valuemin" )
			.removeAttr( "aria-valuemax" )
			.removeAttr( "aria-valuenow" );

		this.valueDiv.remove();
	},

	value: function( newValue ) {
		if ( newValue === undefined ) {
			return this.options.value;
		}

		this.options.value = this._constrainedValue( newValue );
		this._refreshValue();
	},

	_constrainedValue: function( newValue ) {
		if ( newValue === undefined ) {
			newValue = this.options.value;
		}

		this.indeterminate = newValue === false;

		// sanitize value
		if ( typeof newValue !== "number" ) {
			newValue = 0;
		}

		return this.indeterminate ? false :
			Math.min( this.options.max, Math.max( this.min, newValue ) );
	},
	
	_setupWidth: function(width){
		this.element.width(width);
	},
	_setupHeight: function(height){
		this.element.height(height);
		this.labelDiv.css('line-height', this.labelDiv.height()+'px');
	},

	_setOptions: function( options ) {
		// Ensure "value" option is set after other values (like max)
		var value = options.value;
		delete options.value;

		this._super( options );

		this.options.value = this._constrainedValue( value );
		this._refreshValue();
	},

	_setOption: function( key, value ) {
		if ( key === "max" ) {
			// Don't allow a max less than min
			value = Math.max( this.min, value );
		}
		else if ( key === "showLabel"){
			value?this.labelDiv.show():this.labelDiv.hide();
		}
		else if ( key === "height") {
			this._setupHeight(value);
		}
		else if (key === "width") {
			this._setupWidth(value);
		}

		this._super( key, value );
	},

	_percentage: function() {
		return this.indeterminate ? 100 : 100 * ( this.options.value - this.min ) / ( this.options.max - this.min );
	},

	_refreshValue: function() {
		var value = this.options.value,
			percentage = this._percentage();

		this.valueDiv
			.toggle( this.indeterminate || value > this.min )
			.toggleClass( "ui-corner-right", value === this.options.max )
			.width( percentage.toFixed(0) + "%" );

		this.element.toggleClass( "ui-progressbar-indeterminate", this.indeterminate );

		if ( this.indeterminate ) {
			this.element.removeAttr( "aria-valuenow" );
			if ( !this.overlayDiv ) {
				this.overlayDiv = $( "<div class='ui-progressbar-overlay'></div>" ).appendTo( this.valueDiv );
			}
			this.labelDiv.text(this.options.startText);
		} else {
			this.element.attr({
				"aria-valuemax": this.options.max,
				"aria-valuenow": value
			});
			if ( this.overlayDiv ) {
				this.overlayDiv.remove();
				this.overlayDiv = null;
			}
			this.labelDiv.text(value+'%');
		}

		if ( this.oldValue !== value ) {
			this.oldValue = value;
			this._trigger( "change" );
		}
		if ( value === this.options.max ) {
			this.labelDiv.text(this.options.completeText);
			this._trigger( "complete" );
		}
	}
});

})( jQuery );
/**
 * 
 */

(function( $, undefined ) {
//alert('a');


$.widget( "dtui.dtradio", {
	version: "1.10.1",
	options: {
		disabled: null,
		name:'',
		text: '',
		value: '',
		checked: false,
		height: null,
		width: null,
		click:null
	},
	_create: function() {
		var self = this,
			o = this.options;
		var radioElement = $('<input type="radio">').attr({value:o.value, checked:o.checked, name:o.name}).bind('click', function(event){
				self._trigger('click',event);
			}),
			labelElement = $('<span></span>').text(o.text).bind('click',function(event){
				if (o.disabled) return;
				radioElement.prop('checked',true);
				self._trigger('click',event);
			}),
			linkElement = $('<a href="javascript:void(0)"></a>').append(labelElement),
			spanElement = $('<span></span>').addClass('dtui-radio').append(radioElement).append(linkElement);
		
		if (o.height) {
			this.element.height(o.height);
		}
		if (o.width) {
			this.element.width(o.width);
		}
		
		//保证单选按钮垂直居中
		this.element.addClass('dtui-radio-container');
		
		this.element.append(spanElement);
	},


	widget: function() {
		return this.element;
	},
	
	value: function(v){
		if (arguments.length ===0) {
			return this.options.value;
		}
		else {
			this.options.value = v;
		}
	},
	
	check: function(checked){
		if (arguments.length ===0) {
			return $(':radio', this.element).prop('checked');
		}
		else {
			$(':radio', this.element).prop('checked', checked);
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});

$.widget( "dtui.dtradiogroup", {
	version: "1.10.1",
	options: {
		label:"",
		disabled: null,
		name:'',
		required: false,
		items:[],
		col: 1,
		value: null,
		title: '',
		height: null,
		width: null,
		keyField:"id",
		valueField:"name",
		padding_left:15,
		change:null
	},
	
	_create: function() {
		var self = this,
			o = this.options;
		
		this._radioControls = [];
		this._starDiv = $.dtui.util.createStarElem(this.options.required);
		 
		var setElement = $('<fieldset></fieldset>').addClass('dtui-radiogroup-fieldset').addClass('ui-corner-all').append($('<legend></legend>').text(o.title));
		//setElement.css('float','left');
		
		if ($.isArray(o.items)) {
			$.each(o.items, function(i, item){
				self._createRadio(setElement, i, item[self.options.keyField], item[self.options.valueField]);
			});
		}
		else {
			var i=0;
			$.each(o.items, function(value, text){
				self._createRadio(setElement, i++, value, text);
			});
		}
		
		if (o.height) {
			//this.element.height(o.height);
			setElement.width(o.height);
		}
		if (o.width) {
			//this.element.width(o.width);
			setElement.width(o.width);
		}
		/*
		if (o.required) {
			this._starElem.css({'display':'inline-table'});
		}
		*/
		this.element.addClass('dtui-radiogroup-container');
//		setElement.addClass('dtui-radiogroup');
//		setElement.css({'width':'100%', 'height': '100%'});
		this.element.append(setElement);
		$("<label></label>")
		.text(this.options.label)
		.insertBefore(this.element);
		
		/*把radiogroup转换成 内联块级元素*/
		this.element.css("display","inline-block");
		
		
		/*用div 把控件和 星号 封装起来 同时把块级元素改成内联元素  */
		
		
		//this.element.addClass("input-init");
		var container=$("<div style='display:inline-block'></div>")
						.css({"height":this.element.height,"width":this.element.width,"margin":"0 auto"})
						.insertAfter(this.element);
		this._starDiv.appendTo(container)
				.css("padding-top",this.options.height/2);//set star middle
		var divinput=$("<div></div>").append(this.element).appendTo(container);   //set corner all
		if(this.options.required)
			//this.element.parent().css("margin-right",this.element.parent().siblings().outerWidth());
			divinput.css("margin-right",this._starDiv.width());		
	},

	_createRadio: function(setElement, i, value, text) {
		var self = this,
			o = this.options;
		var radioClick = function(event, ui) {
			self._trigger('change', event, $(this));
		};
		
		var radioControl = $('<div></div').addClass('dtui-radiogroup-item').prop('id', self.element.prop('id')+'_'+value).dtradio({
			value:value,
			checked: o.value && o.value ===value,
			name:o.name,
			text:text,
			click: radioClick
		});
		
		
		if ((i+1)%o.col !==1)
			radioControl.css('padding-left', o.padding_left);
		
		this._radioControls.push(radioControl);
		setElement.append(radioControl);
		if ((i+1)%o.col ===0)
			setElement.append('<p></p>');
	},

	widget: function() {
		return this.element;
	},
	
	value: function(v){
		if (arguments.length ===0) {
			var result = null;
			$.each(this._radioControls, function(i, radioControl){
				if (radioControl.dtradio('check')){
					result = radioControl.dtradio('value');
					return ;
				}
			});
			return result;
		}
		else {
			$.each(this._radioControls, function(i, radioControl){
				if (radioControl.dtradio('value') === v){
					radioControl.dtradio('check',true);
					return;
				}
			});
		}
	},

	_destroy: function() {
		this.element.empty();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "disabled" ) {
			if ( value ) {
				$('*', this.element).attr("disabled", true );
			} else {
				$('*', this.element).attr("disabled", false );
			}
			return;
		}
	}


});


})( jQuery );//如果 要添加新的提示消息 如 很好 好 一般 差 很差等 选择星星后的提示信息
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
	
})(jQuery);/*!
 * jQuery UI Slider 1.10.1
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/slider/
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.mouse.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

// number of pages in a slider
// (how many times can you page up/down to go through the whole range)
var numPages = 5;

$.widget( "dtui.dtslider", $.ui.mouse, {
	version: "1.10.1",
	widgetEventPrefix: "slide",

	options: {
		animate: false,
		distance: 0,
		max: 100,
		min: 0,
		orientation: "horizontal",
		range: "false",
		step: 1,
		value: 0,
		ovalues: null,
		height:"",
		width:"",

		// callbacks
		change: null,
		slide: null,
		start: null,
		stop: null
	},

	_create: function() {
		this._keySliding = false;
		this._mouseSliding = false;
		this._animateOff = true;
		this._handleIndex = null;
		this._detectOrientation();
		this._mouseInit();

		this.element
			.addClass( "ui-slider" +
				" ui-slider-" + this.orientation +
				" ui-widget" +
				" ui-widget-content" +
				" ui-corner-all");
		if(this.options.width!="")
		    this.element.css("width",this.options.width);
		if(this.options.height!="")
			this.element.css("height",this.options.height);

		this._refresh();
		this._setOption( "disabled", this.options.disabled );

		this._animateOff = false;
	},

	_refresh: function() {
		this._createRange();
		this._createHandles();
		this._setupEvents();
		this._refreshValue();
	},

	_createHandles: function() {
		var i, handleCount,
			options = this.options,
			existingHandles = this.element.find( ".ui-slider-handle" ).addClass( "ui-state-default ui-corner-all" ),
			handle = "<a class='ui-slider-handle ui-state-default ui-corner-all' href='#' style='outline:none;'></a>",
			handles = [];

		handleCount = ( options.ovalues && options.ovalues.length ) || 1;

		if ( existingHandles.length > handleCount ) {
			existingHandles.slice( handleCount ).remove();
			existingHandles = existingHandles.slice( 0, handleCount );
		}

		for ( i = existingHandles.length; i < handleCount; i++ ) {
			handles.push( handle );
		}

		this.handles = existingHandles.add( $( handles.join( "" ) ).appendTo( this.element ) );

		this.handle = this.handles.eq( 0 );

		this.handles.each(function( i ) {
			$( this ).data( "ui-slider-handle-index", i );
		});
	},

	_createRange: function() {
		var options = this.options,
			classes = "";

		if (options.range=="true")  options.range=true;
		if (options.range=="false") options.range=false;
		if ( options.range ) {
			if ( options.range === true ) {
				if ( !options.ovalues ) {
					options.ovalues = [ this._valueMin(), this._valueMin() ];
				} else if ( options.ovalues.length && options.ovalues.length !== 2 ) {
					options.ovalues = [ options.ovalues[0], options.ovalues[0] ];
				} else if ( $.isArray( options.ovalues ) ) {
					options.ovalues = options.ovalues.slice(0);
				}
			}

			if ( !this.range || !this.range.length ) {
				this.range = $( "<div></div>" )
					.appendTo( this.element );

				classes = "ui-slider-range" +
				// note: this isn't the most fittingly semantic framework class for this element,
				// but worked best visually with a variety of themes
				" ui-widget-header ui-corner-all";
			} else {
				this.range.removeClass( "ui-slider-range-min ui-slider-range-max" )
					// Handle range switching from true to min/max
					.css({
						"left": "",
						"bottom": ""
					});
			}

			this.range.addClass( classes +
				( ( options.range === "min" || options.range === "max" ) ? " ui-slider-range-" + options.range : "" ) );
		} else {
			this.range = $([]);
		}
	},

	_setupEvents: function() {
		var elements = this.handles.add( this.range ).filter( "a" );
		this._off( elements );
		this._on( elements, this._handleEvents );
		this._hoverable( elements );
		this._focusable( elements );
	},

	_destroy: function() {
		this.handles.remove();
		this.range.remove();

		this.element
			.removeClass( "ui-slider" +
				" ui-slider-horizontal" +
				" ui-slider-vertical" +
				" ui-widget" +
				" ui-widget-content" +
				" ui-corner-all" );

		this._mouseDestroy();
	},

	_mouseCapture: function( event ) {
		var position, normValue, distance, closestHandle, index, allowed, offset, mouseOverHandle,
			that = this,
			o = this.options;

		if ( o.disabled ) {
			return false;
		}

		this.elementSize = {
			width: this.element.outerWidth(),
			height: this.element.outerHeight()
		};
		this.elementOffset = this.element.offset();

		position = { x: event.pageX, y: event.pageY };
		normValue = this._normValueFromMouse( position );
		distance = this._valueMax() - this._valueMin() + 1;
		this.handles.each(function( i ) {
			var thisDistance = Math.abs( normValue - that.values(i) );
			if (( distance > thisDistance ) ||
				( distance === thisDistance &&
					(i === that._lastChangedValue || that.values(i) === o.min ))) {
				distance = thisDistance;
				closestHandle = $( this );
				index = i;
			}
		});

		allowed = this._start( event, index );
		if ( allowed === false ) {
			return false;
		}
		this._mouseSliding = true;

		this._handleIndex = index;

		closestHandle
			.addClass( "ui-state-active" )
			.focus();

		offset = closestHandle.offset();
		mouseOverHandle = !$( event.target ).parents().addBack().is( ".ui-slider-handle" );
		this._clickOffset = mouseOverHandle ? { left: 0, top: 0 } : {
			left: event.pageX - offset.left - ( closestHandle.width() / 2 ),
			top: event.pageY - offset.top -
				( closestHandle.height() / 2 ) -
				( parseInt( closestHandle.css("borderTopWidth"), 10 ) || 0 ) -
				( parseInt( closestHandle.css("borderBottomWidth"), 10 ) || 0) +
				( parseInt( closestHandle.css("marginTop"), 10 ) || 0)
		};

		if ( !this.handles.hasClass( "ui-state-hover" ) ) {
			this._slide( event, index, normValue );
		}
		this._animateOff = true;
		return true;
	},

	_mouseStart: function() {
		return true;
	},

	_mouseDrag: function( event ) {
		var position = { x: event.pageX, y: event.pageY },
			normValue = this._normValueFromMouse( position );

		this._slide( event, this._handleIndex, normValue );

		return false;
	},

	_mouseStop: function( event ) {
		this.handles.removeClass( "ui-state-active" );
		this._mouseSliding = false;

		this._stop( event, this._handleIndex );
		this._change( event, this._handleIndex );

		this._handleIndex = null;
		this._clickOffset = null;
		this._animateOff = false;

		return false;
	},

	_detectOrientation: function() {
		this.orientation = ( this.options.orientation === "vertical" ) ? "vertical" : "horizontal";
	},

	_normValueFromMouse: function( position ) {
		var pixelTotal,
			pixelMouse,
			percentMouse,
			valueTotal,
			valueMouse;

		if ( this.orientation === "horizontal" ) {
			pixelTotal = this.elementSize.width;
			pixelMouse = position.x - this.elementOffset.left - ( this._clickOffset ? this._clickOffset.left : 0 );
		} else {
			pixelTotal = this.elementSize.height;
			pixelMouse = position.y - this.elementOffset.top - ( this._clickOffset ? this._clickOffset.top : 0 );
		}

		percentMouse = ( pixelMouse / pixelTotal );
		if ( percentMouse > 1 ) {
			percentMouse = 1;
		}
		if ( percentMouse < 0 ) {
			percentMouse = 0;
		}
		if ( this.orientation === "vertical" ) {
			percentMouse = 1 - percentMouse;
		}

		valueTotal = this._valueMax() - this._valueMin();
		valueMouse = this._valueMin() + percentMouse * valueTotal;

		return this._trimAlignValue( valueMouse );
	},

	_start: function( event, index ) {
		var uiHash = {
			handle: this.handles[ index ],
			value: this.value()
		};
		if ( this.options.ovalues && this.options.ovalues.length ) {
			uiHash.value = this.values( index );
			uiHash.values = this.values();
		}
		return this._trigger( "start", event, uiHash );
	},

	_slide: function( event, index, newVal ) {
		var otherVal,
			newValues,
			allowed;

		if ( this.options.ovalues && this.options.ovalues.length ) {
			otherVal = this.values( index ? 0 : 1 );

			if ( ( this.options.ovalues.length === 2 && this.options.range === true ) &&
					( ( index === 0 && newVal > otherVal) || ( index === 1 && newVal < otherVal ) )
				) {
				newVal = otherVal;
			}

			if ( newVal !== this.values( index ) ) {
				newValues = this.values();
				newValues[ index ] = newVal;
				// A slide can be canceled by returning false from the slide callback
				allowed = this._trigger( "slide", event, {
					handle: this.handles[ index ],
					value: newVal,
					values: newValues
				} );
				otherVal = this.values( index ? 0 : 1 );
				if ( allowed !== false ) {
					this.values( index, newVal, true );
				}
			}
		} else {
			if ( newVal !== this.value() ) {
				// A slide can be canceled by returning false from the slide callback
				allowed = this._trigger( "slide", event, {
					handle: this.handles[ index ],
					value: newVal
				} );
				if ( allowed !== false ) {
					this.value( newVal );
				}
			}
		}
	},

	_stop: function( event, index ) {
		var uiHash = {
			handle: this.handles[ index ],
			value: this.value()
		};
		if ( this.options.ovalues && this.options.ovalues.length ) {
			uiHash.value = this.values( index );
			uiHash.values = this.values();
		}

		this._trigger( "stop", event, uiHash );
	},

	_change: function( event, index ) {
		if ( !this._keySliding && !this._mouseSliding ) {
			var uiHash = {
				handle: this.handles[ index ],
				value: this.value()
			};
			if ( this.options.ovalues && this.options.ovalues.length ) {
				uiHash.value = this.values( index );
				uiHash.values = this.values();
			}

			//store the last changed value index for reference when handles overlap
			this._lastChangedValue = index;

			this._trigger( "change", event, uiHash );
		}
	},

	value: function( newValue ) {
		if ( arguments.length ) {
			this.options.value = this._trimAlignValue( newValue );
			this._refreshValue();
			this._change( null, 0 );
			return;
		}

		return this._value();
	},

	values: function( index, newValue ) {
		var vals,
			newValues,
			i;

		if ( arguments.length > 1 ) {
			this.options.ovalues[ index ] = this._trimAlignValue( newValue );
			this._refreshValue();
			this._change( null, index );
			return;
		}

		if ( arguments.length ) {
			if ( $.isArray( arguments[ 0 ] ) ) {
				vals = this.options.ovalues;
				newValues = arguments[ 0 ];
				for ( i = 0; i < vals.length; i += 1 ) {
					vals[ i ] = this._trimAlignValue( newValues[ i ] );
					this._change( null, i );
				}
				this._refreshValue();
			} else {
				if ( this.options.ovalues && this.options.ovalues.length ) {
					return this._values( index );
				} else {
					return this.value();
				}
			}
		} else {
			return this._values();
		}
	},

	_setOption: function( key, value ) {
		var i,
			valsLength = 0;

		if ( key === "range" && this.options.range === true ) {
			if ( value === "min" ) {
				this.options.value = this._values( 0 );
				this.options.ovalues = null;
			} else if ( value === "max" ) {
				this.options.value = this._values( this.options.ovalues.length-1 );
				this.options.ovalues = null;
			}
		}

		if ( $.isArray( this.options.ovalues ) ) {
			valsLength = this.options.ovalues.length;
		}

		$.Widget.prototype._setOption.apply( this, arguments );

		switch ( key ) {
			case "orientation":
				this._detectOrientation();
				this.element
					.removeClass( "ui-slider-horizontal ui-slider-vertical" )
					.addClass( "ui-slider-" + this.orientation );
				this._refreshValue();
				break;
			case "value":
				this._animateOff = true;
				this._refreshValue();
				this._change( null, 0 );
				this._animateOff = false;
				break;
			case "values":
				this._animateOff = true;
				this._refreshValue();
				for ( i = 0; i < valsLength; i += 1 ) {
					this._change( null, i );
				}
				this._animateOff = false;
				break;
			case "min":
			case "max":
				this._animateOff = true;
				this._refreshValue();
				this._animateOff = false;
				break;
			case "range":
				this._animateOff = true;
				this._refresh();
				this._animateOff = false;
				break;
		}
	},

	//internal value getter
	// _value() returns value trimmed by min and max, aligned by step
	_value: function() {
		var val = this.options.value;
		val = this._trimAlignValue( val );

		return val;
	},

	//internal values getter
	// _values() returns array of values trimmed by min and max, aligned by step
	// _values( index ) returns single value trimmed by min and max, aligned by step
	_values: function( index ) {
		var val,
			vals,
			i;

		if ( arguments.length ) {
			val = this.options.ovalues[ index ];
			val = this._trimAlignValue( val );

			return val;
		} else if ( this.options.ovalues && this.options.ovalues.length ) {
			// .slice() creates a copy of the array
			// this copy gets trimmed by min and max and then returned
			vals = this.options.ovalues.slice();
			for ( i = 0; i < vals.length; i+= 1) {
				vals[ i ] = this._trimAlignValue( vals[ i ] );
			}

			return vals;
		} else {
			return [];
		}
	},

	// returns the step-aligned value that val is closest to, between (inclusive) min and max
	_trimAlignValue: function( val ) {
		if ( val <= this._valueMin() ) {
			return this._valueMin();
		}
		if ( val >= this._valueMax() ) {
			return this._valueMax();
		}
		var step = ( this.options.step > 0 ) ? this.options.step : 1,
			valModStep = (val - this._valueMin()) % step,
			alignValue = val - valModStep;

		if ( Math.abs(valModStep) * 2 >= step ) {
			alignValue += ( valModStep > 0 ) ? step : ( -step );
		}

		// Since JavaScript has problems with large floats, round
		// the final value to 5 digits after the decimal point (see #4124)
		return parseFloat( alignValue.toFixed(5) );
	},

	_valueMin: function() {
		return this.options.min;
	},

	_valueMax: function() {
		return this.options.max;
	},

	_refreshValue: function() {
		var lastValPercent, valPercent, value, valueMin, valueMax,
			oRange = this.options.range,
			o = this.options,
			that = this,
			animate = ( !this._animateOff ) ? o.animate : false,
			_set = {};

		if ( this.options.ovalues && this.options.ovalues.length ) {
			this.handles.each(function( i ) {
				valPercent = ( that.values(i) - that._valueMin() ) / ( that._valueMax() - that._valueMin() ) * 100;
				_set[ that.orientation === "horizontal" ? "left" : "bottom" ] = valPercent + "%";
				$( this ).stop( 1, 1 )[ animate ? "animate" : "css" ]( _set, o.animate );
				if ( that.options.range === true ) {
					if ( that.orientation === "horizontal" ) {
						if ( i === 0 ) {
							that.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { left: valPercent + "%" }, o.animate );
						}
						if ( i === 1 ) {
							that.range[ animate ? "animate" : "css" ]( { width: ( valPercent - lastValPercent ) + "%" }, { queue: false, duration: o.animate } );
						}
					} else {
						if ( i === 0 ) {
							that.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { bottom: ( valPercent ) + "%" }, o.animate );
						}
						if ( i === 1 ) {
							that.range[ animate ? "animate" : "css" ]( { height: ( valPercent - lastValPercent ) + "%" }, { queue: false, duration: o.animate } );
						}
					}
				}
				lastValPercent = valPercent;
			});
		} else {
			value = this.value();
			valueMin = this._valueMin();
			valueMax = this._valueMax();
			valPercent = ( valueMax !== valueMin ) ?
					( value - valueMin ) / ( valueMax - valueMin ) * 100 :
					0;
			_set[ this.orientation === "horizontal" ? "left" : "bottom" ] = valPercent + "%";
			this.handle.stop( 1, 1 )[ animate ? "animate" : "css" ]( _set, o.animate );

			if ( oRange === "min" && this.orientation === "horizontal" ) {
				this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { width: valPercent + "%" }, o.animate );
			}
			if ( oRange === "max" && this.orientation === "horizontal" ) {
				this.range[ animate ? "animate" : "css" ]( { width: ( 100 - valPercent ) + "%" }, { queue: false, duration: o.animate } );
			}
			if ( oRange === "min" && this.orientation === "vertical" ) {
				this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( { height: valPercent + "%" }, o.animate );
			}
			if ( oRange === "max" && this.orientation === "vertical" ) {
				this.range[ animate ? "animate" : "css" ]( { height: ( 100 - valPercent ) + "%" }, { queue: false, duration: o.animate } );
			}
		}
	},

	_handleEvents: {
		keydown: function( event ) {
			/*jshint maxcomplexity:25*/
			var allowed, curVal, newVal, step,
				index = $( event.target ).data( "ui-slider-handle-index" );

			switch ( event.keyCode ) {
				case $.ui.keyCode.HOME:
				case $.ui.keyCode.END:
				case $.ui.keyCode.PAGE_UP:
				case $.ui.keyCode.PAGE_DOWN:
				case $.ui.keyCode.UP:
				case $.ui.keyCode.RIGHT:
				case $.ui.keyCode.DOWN:
				case $.ui.keyCode.LEFT:
					event.preventDefault();
					if ( !this._keySliding ) {
						this._keySliding = true;
						$( event.target ).addClass( "ui-state-active" );
						allowed = this._start( event, index );
						if ( allowed === false ) {
							return;
						}
					}
					break;
			}

			step = this.options.step;
			if ( this.options.ovalues && this.options.ovalues.length ) {
				curVal = newVal = this.values( index );
			} else {
				curVal = newVal = this.value();
			}

			switch ( event.keyCode ) {
				case $.ui.keyCode.HOME:
					newVal = this._valueMin();
					break;
				case $.ui.keyCode.END:
					newVal = this._valueMax();
					break;
				case $.ui.keyCode.PAGE_UP:
					newVal = this._trimAlignValue( curVal + ( (this._valueMax() - this._valueMin()) / numPages ) );
					break;
				case $.ui.keyCode.PAGE_DOWN:
					newVal = this._trimAlignValue( curVal - ( (this._valueMax() - this._valueMin()) / numPages ) );
					break;
				case $.ui.keyCode.UP:
				case $.ui.keyCode.RIGHT:
					if ( curVal === this._valueMax() ) {
						return;
					}
					newVal = this._trimAlignValue( curVal + step );
					break;
				case $.ui.keyCode.DOWN:
				case $.ui.keyCode.LEFT:
					if ( curVal === this._valueMin() ) {
						return;
					}
					newVal = this._trimAlignValue( curVal - step );
					break;
			}

			this._slide( event, index, newVal );
		},
		click: function( event ) {
			event.preventDefault();
		},
		keyup: function( event ) {
			var index = $( event.target ).data( "ui-slider-handle-index" );

			if ( this._keySliding ) {
				this._keySliding = false;
				this._stop( event, index );
				this._change( event, index );
				$( event.target ).removeClass( "ui-state-active" );
			}
		}
	}

});

}(jQuery));
(function( $, undefined ) {
$.widget( "dtui.dtspinner", $.ui.spinner, {
	options: {
		culture: null,
		incremental: true,
		max: null,
		min: null,
		numberFormat: null,
		page: 10,
		step: 1,
		value:"",

		change: null,
		spin: null,
		start: null,
		stop: null
	},
	_create: function() {
		this.element.val(this.options.value);
		this._super();
	},

	_getCreateOptions:function(){
		this._super();
	},
	
	_draw:function(){
		this.element.css("outline","none");
		this._super();
	},
	
	_keydown:function(event){
		this._super(event);
	},
	
	_start:function(event){
		this._super(event);
	},
	
	_repeat:function(i, steps, event){
		this._super(i, steps, event);
	},
	
	_spin:function( step, event ){
		this._super( step, event );
	},
	
	_stop:function(event){
		this._super(event);
	},
	
	_setOption:function(key, value){
		this._super(key, value);
	},
	
	_refresh:function(){
		this._super();
	},
	
	_value:function(value, allowAny){
		this._super(value, allowAny);
	},
	
	_destroy: function() {
		this._super();
	},
	
	_stepUp:function(steps){
		this._super(steps);
	},
	
	_stepDown:function(steps){
		this._super(steps);
	},
	_uiSpinnerHtml:function(){
		return this._super();
	},

	_buttonHtml:function(){
		return this._super();
	},

	_increment:function(i){
		return this._super(i);
	},
	
	_precision:function(){
		return this._super();
	},
	
	
	_precisionOf:function(num){
		return this._super(num);
	},
	
	_adjustValue:function(value){
		return this._super(value);
	},
	
	_parse:function(val){
		return this._super(val);
	},

	_format:function(value){
	    return this._super(value);
	},
	
	widget:function(){
		return this._super();
	}
	

});

})( jQuery );
/*
 * jQuery switchbutton
 *
 * Based on work by tdreyno on iphone-style-checkboxes for events management
 * (https://github.com/tdreyno/iphone-style-checkboxes)
 * 
 * Copyright 2011, L.STEVENIN
 * Released under the MIT license.
 *
 * Depends:
 *	jquery.ui.widget.js (jQuery UI Widget Factory - http://wiki.jqueryui.com/w/page/12138135/Widget%20factory)
 * 	jquery.tmpl.js (jQuery Templates - http://api.jquery.com/category/plugins/templates/)
 */

(function($, undefined){
	
	$.widget('dtui.dtswitchbutton', {
		options: {
			//add new 
			checked:true,
			disabled:false,
			
			classes: '',
			duration: 200,
			dragThreshold: 5,
			autoResize: true,
			disabledClass: 'ui-switchbutton-disabled ui-state-disabled'
		},
		_create: function() {
			if(!this.element.is(':checkbox')) {
				return;
			}
			this._wrapCheckboxInContainer();//组装 switchbutton
			this._attachEvents();
			this._globalEvents();
			this._disableTextSelection();
			//alert(this.element.prop('checked'));
			if(this.options.checked) {
				this.$container.toggleClass('ui-switchbutton-active', this.options.checked);
			}
			if(this.options.autoResize) {
				this._autoResize();
			}
			this._initialPosition();
		},
		_wrapCheckboxInContainer: function() {
			this.$container=$('<div class="ui-switchbutton ui-switchbutton-default"></div>');
			if(this.options.classes!=''){
				this.$container.addClass(this.options.classes);
			}
			this.$disabledLabel = $('<label class="ui-switchbutton-disabled"></label>');
			this.$disabledSpan  = $('<span>OFF</span>');
			this.$enabledLabel  = $('<label class="ui-switchbutton-enabled"></label>');
			this.$enabledSpan   = $('<span>ON</span>');
			this.$handle = $('<div class="ui-switchbutton-handle"></div>');
			this.$container.append(this.$disabledLabel);
			this.$container.append(this.$enabledLabel);
			this.$container.append(this.$handle);
			this.$disabledLabel.append(this.$disabledSpan);
			this.$enabledLabel.append(this.$enabledSpan);
			this.element.after(this.$container);
			this.element.remove();
			this.$container.append(this.element);
			
		},
		_attachEvents: function() {
			var obj = this;
			this.$container
				// A mousedown anywhere in the control will start tracking for dragging
				.bind('mousedown touchstart', function(event) {
					event.preventDefault();
					if(obj.options.disabled) { return; }
					var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
					$.currentlyClicking	= obj.$handle;
					$.dragStartPosition	= x;
					$.handleLeftOffset	= parseInt(obj.$handle.css('left'), 10) || 0;
					$.dragStartedOn		= obj.element;
				})
				// Utilize event bubbling to handle drag on any element beneath the container
				.bind('iPhoneDrag', function(event, x) {
					event.preventDefault();
					if(obj.options.disabled) { return; }
					if(obj.element != $.dragStartedOn) { return; }
					var p = (x + $.handleLeftOffset - $.dragStartPosition) / obj.rightSide;
					if(p < 0) { p = 0; }
					if(p > 1) { p = 1; }
					obj.$handle.css({ 'left': p * obj.rightSide });
					obj.$enabledLabel.css({ 'width': p * obj.rightSide });
					obj.$disabledSpan.css({ 'margin-right': -p * obj.rightSide });
					obj.$enabledSpan.css({ 'margin-left': -(1 - p) * obj.rightSide });
				})
				// Utilize event bubbling to handle drag end on any element beneath the container
				.bind('iPhoneDragEnd', function(event, x) {
					if(obj.options.disabled) { return; }
					var willChangeEvent = jQuery.Event('willChange');
					obj.element.trigger(willChangeEvent);
					if(willChangeEvent.isDefaultPrevented()) {
						checked = obj.options.checked;
					}
					else {
						var checked;
						if($.dragging) {
							var p = (x - $.dragStartPosition) / obj.rightSide;
							checked = (p < 0) ? Math.abs(p) < 0.5 : p >= 0.5;
						}
						else {
							checked = !obj.options.checked;
						}
					}
					$.currentlyClicking = null;
					$.dragging = null;
					obj.options.checked=checked;
					obj.$container.toggleClass('ui-switchbutton-active', checked);
					obj.element.change();
					obj.element.trigger('didChange');
				});
			// Animate when we get a change event
			this.element.change(function() {
				obj.refresh();
				var new_left = obj.options.checked ? obj.rightSide : 0;
				obj.$handle.animate({ 'left': new_left }, obj.options.duration);
				obj.$enabledLabel.animate({ 'width': new_left+14 }, obj.options.duration);   //modify
				obj.$disabledSpan.animate({ 'margin-right': -new_left }, obj.options.duration);
				obj.$enabledSpan.animate({ 'margin-left': new_left - obj.rightSide }, obj.options.duration);
			});
		},
		_globalEvents: function() {
			if($.initComplete) {
				return;
			}
			var opt = this.options;
			$(document)
				// As the mouse moves on the page, animate if we are in a drag state
				.bind('mousemove touchmove', function(event) {
					if(!$.currentlyClicking) { return; }
					event.preventDefault();
					var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
					if(!$.dragging && (Math.abs($.dragStartPosition - x) > opt.dragThreshold)) { 
						$.dragging = true; 
					}
					$(event.target).trigger('iPhoneDrag', [x]);
				})
				// When the mouse comes up, leave drag state
				.bind('mouseup touchend', function(event) {
					if(!$.currentlyClicking) { return; }
					event.preventDefault();
					var x = event.pageX || event.originalEvent.changedTouches[0].pageX;
					$($.currentlyClicking).trigger('iPhoneDragEnd', [x]);
				});
		},
		_disableTextSelection: function() {
			// Disable IE text selection, other browsers are handled in CSS
			/*if (!$.browser.msie) { return; }*/
			// Elements containing text should be unselectable
			$([this.$handle, this.$disabledLabel, this.$enabledLabel, this.$container]).attr('unselectable', 'on');
		},
		_autoResize: function() {
			var	onLabelWidth	= this.$enabledLabel.width(),
				offLabelWidth	= this.$disabledLabel.width(),
				spanPadding		= this.$disabledSpan.innerWidth() - this.$disabledSpan.width()
				handleMargins	= this.$handle.outerWidth() - this.$handle.innerWidth();
			var containerWidth = handleWidth = (onLabelWidth > offLabelWidth) ? onLabelWidth : offLabelWidth;
			this.$handle.css({ 'width': handleWidth });
			handleWidth = this.$handle.width();
			containerWidth += handleWidth + 6;
			spanWidth = containerWidth - handleWidth - spanPadding - handleMargins;
			this.$container.css({ 'width': containerWidth });
			this.$container.find('span').width(spanWidth);
		},
		_initialPosition: function() {
			this.$disabledLabel.css({ width: this.$container.width() - 5 });
			this.rightSide = this.$container.width() - this.$handle.outerWidth();
			if(this.options.checked) {
				this.$handle.css({ 'left': this.rightSide });
				this.$enabledLabel.css({ 'width': this.rightSide+14});
				this.$disabledSpan.css({ 'margin-right': -this.rightSide });
			}
			else {
				this.$enabledLabel.css({ 'width': 0 });
				this.$enabledSpan.css({ 'margin-left': -this.rightSide });
			}
			this.refresh();
		},
		check: function() {
			this.options.checked=true;
			this.refresh();
			return this._setOption('checked', true);
		},
		uncheck: function() {
			this.options.checked=false;
			this.refresh();
			return this._setOption('checked', false);
		},
		enable: function() {
			this.options.disabled=false;
			this.refresh();
			return this._setOption('disabled', false);
		},
		disable: function() {
			this.options.disabled=true;
			this.refresh();
			return this._setOption('disabled', true);
		},
		getChecked:function(){
			return this.options.checked;
		},
		getDisabled:function(){
			return this.options.disabled;
		},
		widget: function() {
			return this.$container;
		},
		refresh: function() {
			if(this.options.disabled) {
				this.$container.addClass(this.options.disabledClass);
				return false;
			}
			else {
				this.$container.removeClass(this.options.disabledClass);
			}
		}
	});
})(jQuery);/*!
 * jQuery UI Tabs 1.10.1
 * http://jqueryui.com
 *
 * Copyright 2013 jQuery Foundation and other contributors
 * Released under the MIT license.
 * http://jquery.org/license
 *
 * http://api.jqueryui.com/tabs/
 *
 * Depends:
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 */
(function( $, undefined ) {

var tabId = 0,
	rhash = /#.*$/;

function getNextTabId() {
	return ++tabId;
}

function isLocal( anchor ) {
	//modify by xychun 2013-05-17
	//return anchor.hash.length > 1 &&
	//	decodeURIComponent( anchor.href.replace( rhash, "" ) ) ===
	//		decodeURIComponent( location.href.replace( rhash, "" ) );
	return anchor.hash && anchor.hash.charAt(0) ==='#';
}

$.widget( "dtui.dttabs", {
	version: "1.10.1",
	delay: 300,
	options: {
		active: null,
		collapsible: false,
		event: "click",
		heightStyle: "content",
		hide: null,
		show: null,
		
		maxTabs: -1,
		canClose: false,
		canDup: false,
		useIframe: false,
		alwaysReload: false,
		showPopupMenu: false,
		
		urlField: 'url',
		captionField: 'name',

		// callbacks
		activate: null,
		beforeActivate: null,
		beforeLoad: null,
		load: null
	},

	_create: function() {
		var that = this,
			options = this.options;

		this.running = false;

		this.element
			.addClass( "ui-tabs ui-widget ui-widget-content ui-corner-all" )
			.toggleClass( "ui-tabs-collapsible", options.collapsible )
			// Prevent users from focusing disabled tabs via click
			.delegate( ".ui-tabs-nav > li", "mousedown" + this.eventNamespace, function( event ) {
				if ( $( this ).is( ".ui-state-disabled" ) ) {
					event.preventDefault();
				}
			})
			// support: IE <9
			// Preventing the default action in mousedown doesn't prevent IE
			// from focusing the element, so if the anchor gets focused, blur.
			// We don't have to worry about focusing the previously focused
			// element since clicking on a non-focusable element should focus
			// the body anyway.
			.delegate( ".ui-tabs-anchor", "focus" + this.eventNamespace, function() {
				if ( $( this ).closest( "li" ).is( ".ui-state-disabled" ) ) {
					this.blur();
				}
			})
			.delegate( ".ui-tabs-anchor", "contextmenu" + this.eventNamespace, function(event) {
				var tab = $( this ).closest( "li" ),
					disabled = tab.is( ".ui-state-disabled" ),
					hasClose = tab.find("span.ui-icon-close").length>0;
				if ( disabled || !that.options.showPopupMenu) {
					return ;
				}
				that._menuElem.data('tab', tab);
				that._menuElem.show().position({
					my: "left top",
					at: "center center",
					of: this
				});
				that._updatePopupMenuState(tab);
				$( document ).one( "click", function() {
					if (that._menuElem && that._menuElem.length>0){
						that._menuElem.hide();
					}
				});
				return false;
			});
		
		// close icon: removing the tab on click
		this.element.delegate( "span.ui-icon-close", "click", function() {
			var tab = $(this).closest("li");
			that.close(tab);
		});

		this._initTabs();
		this._processTabs();
		options.active = this._initialActive();

		// Take disabling tabs via class attribute from HTML
		// into account and update option properly.
		if ( $.isArray( options.disabled ) ) {
			options.disabled = $.unique( options.disabled.concat(
				$.map( this.tabs.filter( ".ui-state-disabled" ), function( li ) {
					return that.tabs.index( li );
				})
			) ).sort();
		}

		// check for length avoids error when initializing empty list
		if ( this.options.active !== false && this.anchors.length ) {
			this.active = this._findActive( options.active );
		} else {
			this.active = $();
		}

		this._refresh();

		if ( this.active.length ) {
			that.load( options.active );
		}
		
		if (this.options.heightStyle === 'fill'){
			//console.log('parent');
			//console.log(this.element.parent());
			$(this.element.parent()).resize(function(){
				//console.log('parent resize');
				//console.log(that.element.parent());
				//console.log(that.element.parent().height());
				that._setupHeightStyle(that.options.heightStyle);

				/*
				var $panel = self._getActivePanel();
				if ($panel.length > 0){
					var ifrm = $panel.children('iframe');
					if (ifrm.length>0){
						var h = self._calcIframeHeight($panel);
						console.log('panelresize:'+h);
						$(ifrm).height(h);
					}
				}
				*/
			});
			
		}
		if (this.options.showPopupMenu) {
			this._createPopupMenu();
		}
	},
	
	_createPopupMenu: function() {
		var that = this;
		this._menuElem = $('<ul>').hide().appendTo(document.body);
		this._closeMenu = $('<li><a href="#">关闭当前页</a></li>').appendTo(this._menuElem);
		this._closeOtherMenu = $('<li><a href="#">关闭其它页</a></li>').appendTo(this._menuElem);
		this._closeAllMenu = $('<li><a href="#">关闭所有页</a></li>').appendTo(this._menuElem);
		this._refreshMenu = $('<li><a href="#">刷新</a></li>').appendTo(this._menuElem);
		this._menuElem.width(80);
		this._menuElem.menu();
		this._closeMenu.click(function(){
			if ($(this).prop('disabled'))
				return false;
			that.close(that._menuElem.data('tab'));
		});
		this._closeOtherMenu.click(function(){
			if ($(this).prop('disabled'))
				return false;
			that.closeOther(that._menuElem.data('tab'));
		});
		this._closeAllMenu.click(function(){
			if ($(this).prop('disabled'))
				return false;
			that.closeAll();
		});
		this._refreshMenu.click(function(event){
			if ($(this).prop('disabled'))
				return false;
			that._loadTab(that._menuElem.data('tab'), event, true);
		});
	},
	
	_updatePopupMenuState: function(tab){
		var hasClose = tab.find("span.ui-icon-close").length>0,
			tabCount = this.tabs.length;
		function disableMenu(menu, disabled){
			menu.prop('disabled', disabled);
			disabled?menu.addClass('ui-state-disabled'): menu.removeClass('ui-state-disabled');
		}
		disableMenu(this._closeMenu, !hasClose);
		disableMenu(this._closeOtherMenu, tabCount===1);
		disableMenu(this._closeAllMenu, (tabCount===1) && !hasClose);
		//this._refreshMenu = $('<li><a href="#">刷新</a></li>').appendTo(this._menuElem);
	},

	_initialActive: function() {
		var active = this.options.active,
			collapsible = this.options.collapsible,
			locationHash = location.hash.substring( 1 );

		if ( active === null ) {
			// check the fragment identifier in the URL
			if ( locationHash ) {
				this.tabs.each(function( i, tab ) {
					if ( $( tab ).attr( "aria-controls" ) === locationHash ) {
						active = i;
						return false;
					}
				});
			}

			// check for a tab marked active via a class
			if ( active === null ) {
				active = this.tabs.index( this.tabs.filter( ".ui-tabs-active" ) );
			}

			// no active tab, set to false
			if ( active === null || active === -1 ) {
				active = this.tabs.length ? 0 : false;
			}
		}

		// handle numbers: negative, out of range
		if ( active !== false ) {
			active = this.tabs.index( this.tabs.eq( active ) );
			if ( active === -1 ) {
				active = collapsible ? false : 0;
			}
		}

		// don't allow collapsible: false and active: false
		if ( !collapsible && active === false && this.anchors.length ) {
			active = 0;
		}

		return active;
	},

	_getCreateEventData: function() {
		return {
			tab: this.active,
			panel: !this.active.length ? $() : this._getPanelForTab( this.active )
		};
	},

	_tabKeydown: function( event ) {
		/*jshint maxcomplexity:15*/
		var focusedTab = $( this.document[0].activeElement ).closest( "li" ),
			selectedIndex = this.tabs.index( focusedTab ),
			goingForward = true;

		if ( this._handlePageNav( event ) ) {
			return;
		}

		switch ( event.keyCode ) {
			case $.ui.keyCode.RIGHT:
			case $.ui.keyCode.DOWN:
				selectedIndex++;
				break;
			case $.ui.keyCode.UP:
			case $.ui.keyCode.LEFT:
				goingForward = false;
				selectedIndex--;
				break;
			case $.ui.keyCode.END:
				selectedIndex = this.anchors.length - 1;
				break;
			case $.ui.keyCode.HOME:
				selectedIndex = 0;
				break;
			case $.ui.keyCode.SPACE:
				// Activate only, no collapsing
				event.preventDefault();
				clearTimeout( this.activating );
				this._activate( selectedIndex );
				return;
			case $.ui.keyCode.ENTER:
				// Toggle (cancel delayed activation, allow collapsing)
				event.preventDefault();
				clearTimeout( this.activating );
				// Determine if we should collapse or activate
				this._activate( selectedIndex === this.options.active ? false : selectedIndex );
				return;
			default:
				return;
		}

		// Focus the appropriate tab, based on which key was pressed
		event.preventDefault();
		clearTimeout( this.activating );
		selectedIndex = this._focusNextTab( selectedIndex, goingForward );

		// Navigating with control key will prevent automatic activation
		if ( !event.ctrlKey ) {
			// Update aria-selected immediately so that AT think the tab is already selected.
			// Otherwise AT may confuse the user by stating that they need to activate the tab,
			// but the tab will already be activated by the time the announcement finishes.
			focusedTab.attr( "aria-selected", "false" );
			this.tabs.eq( selectedIndex ).attr( "aria-selected", "true" );

			this.activating = this._delay(function() {
				this.option( "active", selectedIndex );
			}, this.delay );
		}
	},

	_panelKeydown: function( event ) {
		if ( this._handlePageNav( event ) ) {
			return;
		}

		// Ctrl+up moves focus to the current tab
		if ( event.ctrlKey && event.keyCode === $.ui.keyCode.UP ) {
			event.preventDefault();
			this.active.focus();
		}
	},

	// Alt+page up/down moves focus to the previous/next tab (and activates)
	_handlePageNav: function( event ) {
		if ( event.altKey && event.keyCode === $.ui.keyCode.PAGE_UP ) {
			this._activate( this._focusNextTab( this.options.active - 1, false ) );
			return true;
		}
		if ( event.altKey && event.keyCode === $.ui.keyCode.PAGE_DOWN ) {
			this._activate( this._focusNextTab( this.options.active + 1, true ) );
			return true;
		}
	},

	_findNextTab: function( index, goingForward ) {
		var lastTabIndex = this.tabs.length - 1;

		function constrain() {
			if ( index > lastTabIndex ) {
				index = 0;
			}
			if ( index < 0 ) {
				index = lastTabIndex;
			}
			return index;
		}

		while ( $.inArray( constrain(), this.options.disabled ) !== -1 ) {
			index = goingForward ? index + 1 : index - 1;
		}

		return index;
	},

	_focusNextTab: function( index, goingForward ) {
		index = this._findNextTab( index, goingForward );
		this.tabs.eq( index ).focus();
		return index;
	},

	_setOption: function( key, value ) {
		if ( key === "active" ) {
			// _activate() will handle invalid values and update this.options
			this._activate( value );
			return;
		}

		if ( key === "disabled" ) {
			// don't use the widget factory's disabled handling
			this._setupDisabled( value );
			return;
		}

		this._super( key, value);

		if ( key === "collapsible" ) {
			this.element.toggleClass( "ui-tabs-collapsible", value );
			// Setting collapsible: false while collapsed; open first panel
			if ( !value && this.options.active === false ) {
				this._activate( 0 );
			}
		}

		if ( key === "event" ) {
			this._setupEvents( value );
		}

		if ( key === "heightStyle" ) {
			this._setupHeightStyle( value );
		}
	},

	_tabId: function( tab ) {
		return tab.attr( "aria-controls" ) || "ui-tabs-" + getNextTabId();
	},

	_sanitizeSelector: function( hash ) {
		return hash ? hash.replace( /[!"$%&'()*+,.\/:;<=>?@\[\]\^`{|}~]/g, "\\$&" ) : "";
	},

	refresh: function() {
		var options = this.options,
			lis = this.tablist.children( ":has(a[href])" );

		// get disabled tabs from class attribute from HTML
		// this will get converted to a boolean if needed in _refresh()
		options.disabled = $.map( lis.filter( ".ui-state-disabled" ), function( tab ) {
			return lis.index( tab );
		});

		this._processTabs();
		// was collapsed or no tabs
		if ( options.active === false || !this.anchors.length ) {
			options.active = false;
			this.active = $();
		// was active, but active tab is gone
		} else if ( this.active.length && !$.contains( this.tablist[ 0 ], this.active[ 0 ] ) ) {
			// all remaining tabs are disabled
			if ( this.tabs.length === options.disabled.length ) {
				options.active = false;
				this.active = $();
			// activate previous tab
			} else {
				this._activate( this._findNextTab( Math.max( 0, options.active - 1 ), false ) );
			}
		// was active, active tab still exists
		} else {
			// make sure active index is correct
			options.active = this.tabs.index( this.active );
		}

		this._refresh();
	},

	_refresh: function() {
		this._setupDisabled( this.options.disabled );
		this._setupEvents( this.options.event );
		this._setupHeightStyle( this.options.heightStyle );

		this.tabs.not( this.active ).attr({
			"aria-selected": "false",
			tabIndex: -1
		});
		this.panels.not( this._getPanelForTab( this.active ) )
			.hide()
			.attr({
				"aria-expanded": "false",
				"aria-hidden": "true"
			});

		// Make sure one tab is in the tab order
		if ( !this.active.length ) {
			this.tabs.eq( 0 ).attr( "tabIndex", 0 );
		} else {
			this.active
				.addClass( "ui-tabs-active ui-state-active" )
				.attr({
					"aria-selected": "true",
					tabIndex: 0
				});
			this._getPanelForTab( this.active )
				.show()
				.attr({
					"aria-expanded": "true",
					"aria-hidden": "false"
				});
		}
	},

	_initTabs: function(){
		var tablist = this.tablist = this._getList();
		
		tablist.wrap('<div class="dtui-tabs-tablist-container"></div>');
		var tablistContainer = this.tablistContainer = this.tablist.parent();
		tablistContainer.wrap('<div class="ui-widget-header ui-corner-all ui-helper-clearfix"></div>');
		var $left = $('<a class="dtui-tab-header-left">Left</a>');
		var $right = $('<a class="dtui-tab-header-right">Right</a>');
		tablistContainer.before($left);
		tablistContainer.before($right);
		$left.dtbutton({
			icons: {
				primary: "ui-icon-arrowthick-1-w"
			},
			text: false,
			click: function(){
				var pos = tablist.position();
				var newLeft = pos.left + 20;
				if(newLeft > 0){
					newLeft = 0;
				}
				tablist.css({left: newLeft + 'px'});
			}
		});
		$right.dtbutton({
			icons: {
				primary: "ui-icon-arrowthick-1-e"
			},
			text: false,
			click: function(){
				var pos = tablist.position();
				var newLeft = pos.left - 20 ;
				var maxLeft = 0 - tablist.width() + tablistContainer.width();
				if(newLeft < maxLeft){
					newLeft = maxLeft;
				}
				tablist.css({left: newLeft+ 'px'});
			}
		});
	},
	_processTabs: function() {
		var that = this;

		this.tablist.addClass( "ui-tabs-nav ui-helper-reset" )
			.attr( "role", "tablist" );
		
		this.tabs = this.tablist.find( "> li:has(a[href])" )
			.addClass( "ui-state-default ui-corner-top" )
			.attr({
				role: "tab",
				tabIndex: -1
			});
		
		this.anchors = this.tabs.map(function() {
				return $( "a", this )[ 0 ];
			})
			.addClass( "ui-tabs-anchor" )
			.attr({
				role: "presentation",
				tabIndex: -1
			});

		this.panels = $();

		this.anchors.each(function( i, anchor ) {
			var selector, panel, panelId,
				anchorId = $( anchor ).uniqueId().attr( "id" ),
				tab = $( anchor ).closest( "li" ),
				originalAriaControls = tab.attr( "aria-controls" );

			// inline tab
			if ( isLocal( anchor ) ) {
				selector = anchor.hash;
				panel = that.element.find( that._sanitizeSelector( selector ) );
			// remote tab
			} else {
				panelId = that._tabId( tab );
				selector = "#" + panelId;
				panel = that.element.find( selector );
				if ( !panel.length ) {
					panel = that._createPanel( panelId );
					panel.insertAfter( that.panels[ i - 1 ] || that.tablistContainer.parent() );
					//----begin----
					//add by xychun 2013-4-25 iframe
					if (that.options.useIframe) {
						that._createIframe(panel);
					}
					//----end----
				}
				panel.attr( "aria-live", "polite" );
			}

			if ( panel.length) {
				that.panels = that.panels.add( panel );
			}
			if ( originalAriaControls ) {
				tab.data( "ui-tabs-aria-controls", originalAriaControls );
			}
			tab.attr({
				"aria-controls": selector.substring( 1 ),
				"aria-labelledby": anchorId
			});
			panel.attr( "aria-labelledby", anchorId );
		});

		this.panels
			.addClass( "ui-tabs-panel ui-widget-content ui-corner-bottom" )
			.attr( "role", "tabpanel" );
		
		//等结构渲染完成之后再计算宽度
		var widthSum = 0;
		$('li', this.tablist).each(function(i){
			var w =  $(this).outerWidth(true);
			widthSum += w ;
		});
		//似乎是由于字体导致的计算不准确，例如：中文设置为英文字体或英文设置为中文字体
		widthSum += 19;
		that.tablist.width(widthSum);
		if(widthSum > that.tablistContainer.width()){
			that.tablistContainer.parent().addClass('dtui-tabs-scroll');
		}
		else{
			that.tablist.css({left: 0});
			that.tablistContainer.parent().removeClass('dtui-tabs-scroll');
		}
	},

	// allow overriding how to find the list for rare usage scenarios (#7715)
	_getList: function() {
		return this.element.find( "ol,ul" ).eq( 0 );
	},

	_createPanel: function( id ) {
		return $( "<div>" )
			.attr( "id", id )
			.addClass( "ui-tabs-panel ui-widget-content ui-corner-bottom" )
			.data( "ui-tabs-destroy", true );
	},
	
	_calcIframeHeight: function($panel){
		//将12px转换成12
		function pxToNum(s){
			if ($.type(s) === 'string') {
				return s.substr(0, s.length-2);
			}
			return s;
		}

		//console.log($panel.css('height'));
		return pxToNum($panel.css('height')) - 3;
	},
	
    _createIframe: function(par) {
    	var self = this;
		var ifrm = $("<iframe frameborder='0' scrolling='auto' allowtransparency='true' width='100%' src=''> </iframe>");
		
		ifrm.addClass("dtui-tabs-iframe").appendTo(par);
		
		//将12px转换成12
		function pxToNum(s){
			if ($.type(s) === 'string') {
				return s.substr(0, s.length-2);
			}
			return s;
		}
		
		function getHostAndPort(url) {
			var index = url.indexOf('/', 8);
			return url.substr(0, index);
		}
		function isCrossDomain(iframe) {
			var mainUrlPart = getHostAndPort(window.location.href);
			var iframeUrlPart = getHostAndPort(iframe.src);
			//console.log(mainUrlPart);
			//console.log(iframeUrlPart);
			return mainUrlPart != iframeUrlPart;
		}
		
		ifrm.load(function(event) {
			/*
			var ifrmdoc = this.Document || this.contentDocument;
			
			//var par_pad_left =  pxToNum(par.css('padding-left')),
			//	par_pad_right =  pxToNum(par.css('padding-right'));
			//$(this).width(par.innerWidth() - par_pad_left - par_pad_right - 10);

			
			var par_pad_top =  pxToNum(par.css('padding-top')),
				par_pad_bottom =  pxToNum(par.css('padding-bottom')),
				par_height = par.innerHeight() - par_pad_top - par_pad_bottom - 5;
			//console.log(par.attr('id'));
			//console.log(ifrmdoc.body);
			//iframe的src不跨域
			//if (ifrmdoc.body){
			if (!isCrossDomain(this)){
				//console.log($(ifrmdoc.body).height()+','+par_height);
				$(this).height(Math.max($(ifrmdoc.body).height()+10, par_height));
			}
			else {
				//iframe的src跨域
				$(this).height(par_height);
			}
			*/
			/*
			var iframe_content = ifrm.contents().find('body');
			// Bind the resize event. When the iframe's size changes, update its height as
			// well as the corresponding info div.
			iframe_content.resize(function(){
				var elem = $(this);
				// Resize the IFrame.
				//console.log(elem.outerHeight( true ));
				ifrm.css({ height: elem.outerHeight( true ) });
				//$(this).outerWidth(par.innerWidth() - par_pad_left - par_pad_right);
			});
			iframe_content.resize();
			*/
			var h = self._calcIframeHeight(par);
			//console.log('load:'+h);
			ifrm.height(h);
			//$console.log(ifrm.contents().find("body"));
			ifrm.contents().find("body").click(function(){
				if (self._menuElem && self._menuElem.length>0){
					self._menuElem.hide();
				}
			}); 
		});
    },	
    
	_setupDisabled: function( disabled ) {
		if ( $.isArray( disabled ) ) {
			if ( !disabled.length ) {
				disabled = false;
			} else if ( disabled.length === this.anchors.length ) {
				disabled = true;
			}
		}

		// disable tabs
		for ( var i = 0, li; ( li = this.tabs[ i ] ); i++ ) {
			if ( disabled === true || $.inArray( i, disabled ) !== -1 ) {
				$( li )
					.addClass( "ui-state-disabled" )
					.attr( "aria-disabled", "true" );
			} else {
				$( li )
					.removeClass( "ui-state-disabled" )
					.removeAttr( "aria-disabled" );
			}
		}

		this.options.disabled = disabled;
	},
	
	_getActivePanel: function(){
		var tab = this._findActive(this.options.active);
		return this._getPanelForTab(tab);
	},

	_setupEvents: function( event ) {
		var events = {
			click: function( event ) {
				event.preventDefault();
			}
		};
		if ( event ) {
			$.each( event.split(" "), function( index, eventName ) {
				events[ eventName ] = "_eventHandler";
			});
		}

		this._off( this.anchors.add( this.tabs ).add( this.panels ) );
		this._on( this.anchors, events );
		this._on( this.tabs, { keydown: "_tabKeydown" } );
		this._on( this.panels, { keydown: "_panelKeydown" } );

		this._focusable( this.tabs );
		this._hoverable( this.tabs );
		/*
		//父容器改变大小时，重新设置高度
		if (this.options.heightStyle === "fill") {
			
			var self = this;
			self.panels.resize(function(){
				var ifrm = $(this).children('iframe');
				if (ifrm.length>0){
					var h = self._calcIframeHeight($(this));
					//console.log($(this));
					//console.log('panelresize:'+h);
					$(ifrm).height(h);
				}				
			});
		}
		*/
	},

	_setupHeightStyle: function( heightStyle ) {
		var maxHeight,
			parent = this.element.parent();

		if ( heightStyle === "fill" ) {
			maxHeight = parent.height();
			maxHeight -= this.element.outerHeight() - this.element.height();

			this.element.siblings( ":visible" ).each(function() {
				var elem = $( this ),
					position = elem.css( "position" );

				if ( position === "absolute" || position === "fixed" ) {
					return;
				}
				maxHeight -= elem.outerHeight( true );
			});

			this.element.children().not( this.panels ).each(function() {
				maxHeight -= $( this ).outerHeight( true );
			});

			var that = this;
			this.panels.each(function() {
				//console.log('setHeightStyle');
				//console.log(that.element);
				//console.log($(this));
				var newHeight = Math.max( 0, maxHeight -
						$( this ).innerHeight() + $( this ).height() );
				var oldHeight = $(this).height();
				//为防止死循环，只有高度改变时才重新设置高度
				if (Math.abs(oldHeight - newHeight) > 3){
					//console.log(oldHeight+','+newHeight);
					$( this ).height( newHeight );
					
					var ifrm = $(this).children('iframe');
					if (ifrm.length>0){
						var h = that._calcIframeHeight($(this));
						//console.log($(this));
						//console.log('panelresize:'+h);
						$(ifrm).height(h);
					}				
					
				}
			});
			//当使用iframe时，让iframe的高度等于panel的高度，隐藏panel的滚动条，使用iframe的滚动条
			if (!this.options.useIframe){
				this.panels.css( "overflow", "auto" );
			}
		} else if ( heightStyle === "auto" ) {
			maxHeight = 0;
			this.panels.each(function() {
				maxHeight = Math.max( maxHeight, $( this ).height( "" ).height() );
			}).height( maxHeight );
		}
	},

	_eventHandler: function( event ) {
		var options = this.options,
			active = this.active,
			anchor = $( event.currentTarget ),
			tab = anchor.closest( "li" ),
			clickedIsActive = tab[ 0 ] === active[ 0 ],
			collapsing = clickedIsActive && options.collapsible,
			toShow = collapsing ? $() : this._getPanelForTab( tab ),
			toHide = !active.length ? $() : this._getPanelForTab( active ),
			eventData = {
				oldTab: active,
				oldPanel: toHide,
				newTab: collapsing ? $() : tab,
				newPanel: toShow
			};

		event.preventDefault();

		if ( tab.hasClass( "ui-state-disabled" ) ||
				// tab is already loading
				tab.hasClass( "ui-tabs-loading" ) ||
				// can't switch durning an animation
				this.running ||
				// click on active header, but not collapsible
				( clickedIsActive && !options.collapsible ) ||
				// allow canceling activation
				( this._trigger( "beforeActivate", event, eventData ) === false ) ) {
			return;
		}

		options.active = collapsing ? false : this.tabs.index( tab );

		this.active = clickedIsActive ? $() : tab;
		if ( this.xhr ) {
			this.xhr.abort();
		}

		if ( !toHide.length && !toShow.length ) {
			$.error( "jQuery UI Tabs: Mismatching fragment identifier." );
		}

		if ( toShow.length ) {
			this.load( this.tabs.index( tab ), event );
		}
		this._toggle( event, eventData );
	},

	// handles show/hide for selecting tabs
	_toggle: function( event, eventData ) {
		var that = this,
			toShow = eventData.newPanel,
			toHide = eventData.oldPanel;

		this.running = true;

		function complete() {
			that.running = false;
/*
			//var tab = $(event.currentTarget).closest( "li" ),
			//	panel = that._getPanelForTab( tab );
			//console.log(that.options.useIframe +','+ that.options.heightStyle);
			if (that.options.useIframe && that.options.heightStyle ==='fill'){
				var ifrm = toShow.children('iframe');
				if (ifrm.length > 0 ){
					var h = that._calcIframeHeight(toShow);
					console.log('active:'+h);
					ifrm.height(h);
				}
			}
			*/
			that._trigger( "activate", event, eventData );
			
		}

		function show() {
			eventData.newTab.closest( "li" ).addClass( "ui-tabs-active ui-state-active" );

			if ( toShow.length && that.options.show ) {
				that._show( toShow, that.options.show, complete );
			} else {
				toShow.show();
				complete();
			}
		}

		// start out by hiding, then showing, then completing
		if ( toHide.length && this.options.hide ) {
			this._hide( toHide, this.options.hide, function() {
				eventData.oldTab.closest( "li" ).removeClass( "ui-tabs-active ui-state-active" );
				show();
			});
		} else {
			eventData.oldTab.closest( "li" ).removeClass( "ui-tabs-active ui-state-active" );
			toHide.hide();
			show();
		}

		toHide.attr({
			"aria-expanded": "false",
			"aria-hidden": "true"
		});
		eventData.oldTab.attr( "aria-selected", "false" );
		// If we're switching tabs, remove the old tab from the tab order.
		// If we're opening from collapsed state, remove the previous tab from the tab order.
		// If we're collapsing, then keep the collapsing tab in the tab order.
		if ( toShow.length && toHide.length ) {
			eventData.oldTab.attr( "tabIndex", -1 );
		} else if ( toShow.length ) {
			this.tabs.filter(function() {
				return $( this ).attr( "tabIndex" ) === 0;
			})
			.attr( "tabIndex", -1 );
		}

		toShow.attr({
			"aria-expanded": "true",
			"aria-hidden": "false"
		});
		eventData.newTab.attr({
			"aria-selected": "true",
			tabIndex: 0
		});
	},

	_activate: function( index ) {
		var anchor,
			active = this._findActive( index );
		
		// trying to activate the already active panel
		if ( active[ 0 ] === this.active[ 0 ] ) {
			return;
		}

		// trying to collapse, simulate a click on the current active header
		if ( !active.length ) {
			active = this.active;
		}

		anchor = active.find( ".ui-tabs-anchor" )[ 0 ];
		this._eventHandler({
			target: anchor,
			currentTarget: anchor,
			preventDefault: $.noop
		});
		

	},

	_findActive: function( index ) {
		return index === false ? $() : this.tabs.eq( index );
	},

	_getIndex: function( index ) {
		// meta-function to give users option to provide a href string instead of a numerical index.
		if ( typeof index === "string" ) {
			index = this.anchors.index( this.anchors.filter( "[href$='" + index + "']" ) );
		}

		return index;
	},

	_destroy: function() {
		if ( this.xhr ) {
			this.xhr.abort();
		}

		this.element.removeClass( "ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible" );

		this.tablist
			.removeClass( "ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" )
			.removeAttr( "role" );

		this.anchors
			.removeClass( "ui-tabs-anchor" )
			.removeAttr( "role" )
			.removeAttr( "tabIndex" )
			.removeUniqueId();

		this.tabs.add( this.panels ).each(function() {
			if ( $.data( this, "ui-tabs-destroy" ) ) {
				$( this ).remove();
			} else {
				$( this )
					.removeClass( "ui-state-default ui-state-active ui-state-disabled " +
						"ui-corner-top ui-corner-bottom ui-widget-content ui-tabs-active ui-tabs-panel" )
					.removeAttr( "tabIndex" )
					.removeAttr( "aria-live" )
					.removeAttr( "aria-busy" )
					.removeAttr( "aria-selected" )
					.removeAttr( "aria-labelledby" )
					.removeAttr( "aria-hidden" )
					.removeAttr( "aria-expanded" )
					.removeAttr( "role" );
			}
		});

		this.tabs.each(function() {
			var li = $( this ),
				prev = li.data( "ui-tabs-aria-controls" );
			if ( prev ) {
				li
					.attr( "aria-controls", prev )
					.removeData( "ui-tabs-aria-controls" );
			} else {
				li.removeAttr( "aria-controls" );
			}
		});

		this.panels.show();

		if ( this.options.heightStyle !== "content" ) {
			this.panels.css( "height", "" );
		}
	},

	enable: function( index ) {
		var disabled = this.options.disabled;
		if ( disabled === false ) {
			return;
		}

		if ( index === undefined ) {
			disabled = false;
		} else {
			index = this._getIndex( index );
			if ( $.isArray( disabled ) ) {
				disabled = $.map( disabled, function( num ) {
					return num !== index ? num : null;
				});
			} else {
				disabled = $.map( this.tabs, function( li, num ) {
					return num !== index ? num : null;
				});
			}
		}
		this._setupDisabled( disabled );
	},

	disable: function( index ) {
		var disabled = this.options.disabled;
		if ( disabled === true ) {
			return;
		}

		if ( index === undefined ) {
			disabled = true;
		} else {
			index = this._getIndex( index );
			if ( $.inArray( index, disabled ) !== -1 ) {
				return;
			}
			if ( $.isArray( disabled ) ) {
				disabled = $.merge( [ index ], disabled ).sort();
			} else {
				disabled = [ index ];
			}
		}
		this._setupDisabled( disabled );
	},
	
	_loadTab: function(tab, event, forceLoad){
		var that = this;
		function getTabIndex(tab){
			for (var i=0; i<that.tabs.length; i++){
				if (that.tabs[i] == tab[0]){
					return i;
				}
			}
			return -1;
		}
		
		var anchor = tab.find( ".ui-tabs-anchor" ),
			panel = this._getPanelForTab( tab ),
			eventData = {
				index: getTabIndex(tab),
				tab: tab,
				panel: panel
			};

		// not remote
		if ( isLocal( anchor[ 0 ] ) ) {
			return;
		}
		
		if (this.options.useIframe) {
			var iframe = panel.find('iframe');
			//即使this.options.useIframe=true,如果是静态创建的tab,panel中将不包含iframe
			if (iframe){
				if (!iframe.attr("src")){
					iframe.attr("src", anchor.attr( "href" ));
				}
				else {
					if (this.options.alwaysReload || forceLoad){
						iframe.attr("src", this._urlAppendTimeTag(anchor.attr( "href" )));
					}
				}
			}
		}
		else {
			this.xhr = $.ajax( this._ajaxSettings( anchor, event, eventData ) );
	
			// support: jQuery <1.8
			// jQuery <1.8 returns false if the request is canceled in beforeSend,
			// but as of 1.8, $.ajax() always returns a jqXHR object.
			if ( this.xhr && this.xhr.statusText !== "canceled" ) {
				tab.addClass( "ui-tabs-loading" );
				panel.attr( "aria-busy", "true" );
	
				this.xhr
					.success(function( response ) {
						// support: jQuery <1.8
						// http://bugs.jquery.com/ticket/11778
						setTimeout(function() {
							panel.html( response );
							that._trigger( "load", event, eventData );
						}, 1 );
					})
					.complete(function( jqXHR, status ) {
						// support: jQuery <1.8
						// http://bugs.jquery.com/ticket/11778
						setTimeout(function() {
							if ( status === "abort" ) {
								that.panels.stop( false, true );
							}
	
							tab.removeClass( "ui-tabs-loading" );
							panel.removeAttr( "aria-busy" );
	
							if ( jqXHR === that.xhr ) {
								delete that.xhr;
							}
						}, 1 );
					});
			}
		}
	},

	load: function( index, event ) {
		index = this._getIndex( index );
		var tab = this.tabs.eq( index );
		this._loadTab(tab, event);
	},
	
	url: function(index, url){
		if (index >=0 && index < this.anchors.length){
			this.anchors[index].href = url;
			$(this.anchors[index]).data('url', url);
		}
		return this;
	},
	
	/**
	 * 增加一个Tab页
	 * @param url	页面链接地址，将该链接指向的页面展示在新的tab中
	 * @param label	tab页头显示的文本
	 * @param isOpen	是否打开新增的tab页 true打开，false不打开
	 * @param notClose	是否显示关闭按钮，true不显示，false显示
	 * @param index	新增tab页的位置
	 * @returns {___anonymous719_29036}
	 */
	add: function( url, label, isOpen, notClose, index) {
		if (this.options.canDup!==true) {
			var anchorIndex = -1;
			this.anchors.each(function(i, a){
				//if ($(a).attr('href') === url) {
				//if (a.hash === url) {
				if ($(a).data('url') === url) {
					anchorIndex = i;
					return false;
				}
			});
			
			if (anchorIndex != -1) {
				//alert('您准备加入的页签已经存在！');
				this._activate(anchorIndex);
				return this;
			}
		}
		
		if (this.options.maxTabs != -1 && this.anchors.length >= this.options.maxTabs) {
			alert('您打开的页签已经达到系统允许的最大值！');
			return this;
		}

		var self = this,
			o = this.options,
			//id = "tabs-" + tabCounter,
			tabTemplate = "<li><a href='#{href}'>#{label}</a> </li>",
			$li = $( tabTemplate.replace( /#\{href\}/g, url ).replace( /#\{label\}/g, label ) );
			$li.find('a').data('url', url);

		if(!notClose){
			$li.append("<span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span>");
		}
		var activeIndex = 0;
		if ( index === undefined ) {
			$li.appendTo( this.tablist );	//this.tablist的长度不会发生变化
			if (isOpen) activeIndex = this.tabs.length;
		} else {
			$li.insertBefore( this.tabs[ index ] );
			if (isOpen) activeIndex = index;
		}
		
		this.refresh();
		
		if (isOpen) {
			//o.active = activeIndex;
			this._activate(activeIndex);
		}
		
		var i = index;
		if (index === undefined)
			i = this.tabs.length - 1;
			
		this._trigger( "add", null, this._ui( this.tabs[ i ], this.panels[ i ] )  );
		return this;
	},
	
	addSheet: function(sheetData, isOpen, notClose, index) {
		var url = sheetData[this.options.urlField],
			label = sheetData[this.options.captionField];
		
		if (this.options.canDup!==true) {
			var anchorIndex = -1;
			this.anchors.each(function(i, a){
				//if ($(a).attr('href') === url) {
				//if (a.hash === url) {
				if ($(a).data('url') === url) {
					anchorIndex = i;
					return false;
				}
			});
			
			if (anchorIndex != -1) {
				//alert('您准备加入的页签已经存在！');
				this._activate(anchorIndex);
				return this;
			}
		}
		
		if (this.options.maxTabs != -1 && this.anchors.length >= this.options.maxTabs) {
			alert('您打开的页签已经达到系统允许的最大值！');
			return this;
		}

		var self = this,
			o = this.options,
			//id = "tabs-" + tabCounter,
			tabTemplate = "<li><a href='#{href}'>#{label}</a> </li>",
			$li = $( tabTemplate.replace( /#\{href\}/g, url ).replace( /#\{label\}/g, label ) );
		
		$li.data('relaObject', sheetData);
		$li.find('a').data('url', url);

		if(!notClose){
			$li.append("<span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span>");
		}
		var activeIndex = 0;
		if ( index === undefined ) {
			$li.appendTo( this.tablist );	//this.tablist的长度不会发生变化
			if (isOpen) activeIndex = this.tabs.length;
		} else {
			$li.insertBefore( this.tabs[ index ] );
			if (isOpen) activeIndex = index;
		}
		
		this.refresh();
		
		if (isOpen) {
			//o.active = activeIndex;
			this._activate(activeIndex);
		}
		
		var i = index;
		if (index === undefined)
			i = this.tabs.length - 1;
			
		this._trigger( "add", null, this._ui( this.tabs[ i ], this.panels[ i ] )  );
		return this;		
	},
	
	getData: function(index){
		if (index>=0 && index < this.tabs.length){
			var o = $(this.tabs[index]).data('relaObject');
			//不能直接返回undefined，undefined经过widget包装后，会返回当前对象
			if (o !== undefined)
				return o;
		}
		return null;
	},
	_getNextVisibleTabPos: function(index) {
		for (var i= index+1; i<this.tabs.length; i++){
			var tab = this.tabs.eq(i);
			if (tab.css('display') != 'none'){
				return i;
			}
		}
		return null;
	},
	_getPrevVisibleTabPos: function(index) {
		//$console.log(this.tabs);
		for (var i= index-1; i>=0; i--){
			var tab = this.tabs.eq(i);

			if (tab.css('display') != 'none'){
				return i;
			}
		}
		return null;
	},
	hide: function ( index ) {
		if (index < 0 || index >=this.tabs.length || this.tabs.length<=1){
			return;
		}
		var tab = this.tabs.eq(index),
			panel = this._getPanelForTab( tab );
		if (index = this.options.active) {
			var nextActive = null;
			if (index == this.tabs.length -1){
				nextActive = this._getPrevVisibleTabPos(index);
				if (nextActive == null)
					nextActive = this._getNextVisibleTabPos(index);
			}
			else {
				nextActive = this._getNextVisibleTabPos(index);
				if (nextActive == null)
					nextActive = this._getPrevVisibleTabPos(index);
			}
			this.options.active = nextActive;
			if (nextActive != null)
				this._activate(nextActive);
		}
		panel.hide();
		tab.hide();
		this.refresh();
	},
	
	show: function ( index ) {
		if (index < 0 || index >=this.tabs.length || this.tabs.length<=1){
			return;
		}
		var tab = this.tabs.eq(index),
			panel = this._getPanelForTab( tab );
		panel.show();
		tab.show();
		this.refresh();
	},

	_ui: function( tab, panel ) {
		return {
			tab: tab,
			panel: panel,
			index: this.anchors.index( tab )
		};
	},

	remove: function( index) {
		this._removeTab(index);
		this.refresh();
		//return this;
	},	
	
	_removeTab: function(index){
		var tab = this.tabs.eq(index);
		this._removeByTab(tab);
	},
	
	_removeByTab: function(tab){
		if (tab.length == 0 || tab.find('span.ui-icon-close').length == 0) 
			return false;
		tab.find("a").removeData('url');
		var panelId = tab.remove().attr( "aria-controls" );
		$( "#" + panelId ).remove();
		return true;
	},
	
	_canClose: function(index){
		return this.tabs.eq(index).find('span.ui-icon-close').length>0;
	},
	
	close: function(tab){
		if (!tab){
			tab = this.active;
		}
		this._removeByTab(tab);		
		this.refresh();
	},
	closeOther: function(activeTab){
		if (!activeTab){
			activeTab = this.active;
		}
		for (var i= this.tabs.length - 1; i>=0; i--){
			if (this.tabs[i] == activeTab[0]) continue;
			this._removeTab(i);
		}
		this.refresh();
		
	},
	closeAll: function(){
		for (var i= this.tabs.length - 1; i>=0; i--){
			this._removeTab(i);
		}
		this.refresh();
	},
	
	/**
	 * 在url后面增加时间戳
	 * @param url 
	 * @returns {String}
	 */
	_urlAppendTimeTag: function(url){
		if (url){			
			var s = url.indexOf("?")>=0?"&":"?";
			return url + s+"_timetag="+ (new Date()).getTime();
		}
		else 
			return "";
	},

	_ajaxSettings: function( anchor, event, eventData ) {
		var that = this,
			url = '';
		if (this.options.alwaysReload){
			url = this._urlAppendTimeTag(anchor.attr( "href" ));
		}
		else {
			url = anchor.attr( "href" );
		}
		return {
			//url: anchor.attr( "href" ),
			url: url,
			beforeSend: function( jqXHR, settings ) {
				return that._trigger( "beforeLoad", event,
					$.extend( { jqXHR : jqXHR, ajaxSettings: settings }, eventData ) );
			}
		};
	},

	_getPanelForTab: function( tab ) {
		var id = $( tab ).attr( "aria-controls" );
		return this.element.find( this._sanitizeSelector( "#" + id ) );
	}
});

})( jQuery );
(function($,undefined) {
	
	$.widget("dtui.dttextarea",{
		options:{
			label:"",
			name:"",
			rows:3,
			cols:20,
			width: null,
			height: null,
			disabled: false,//disabled
			readonly:false,//readonly
			tittle:"",
			required:false,
			immValidate:false,
			prompt:"文本框",
			showWordCount: true,
			//validate condition
			maxwords:99999,//最大字数
			minwords:0,     //最少字数
			//callbacks
			onfocus:null,
			onblur:null, 
			onselect:null, 
			onchange:null,
			onclick:null, 
			ondblclick:null,
			onmousedown:null,
			onmouseup:null,
			onmouseover:null,
			onmousemove:null,
			onmouseout:null,
			onkeypress:null,
			onkeydown:null,
			onkeyup:null
		},
		_createElement:function(){
			var self=this;
			this.oldValue="";
			this.element.css("resize","none");
			//必填属性 rows,cols 有没有值不影响效果的值 name,title
			this.element.attr({cols:this.options.cols,rows:this.options.rows,name:this.options.name,title:this.options.tittle});
			//需要判断 有值 就关联到 对象上 没值就不做处理
			if(this.options.label!=""){
				this._labelElem = $("<label></label>")
				.text(this.options.label)
				.insertBefore(this.element);
			}
			if(this.options.disabled){
				this.element.attr("disabled","disabled");
			}
			if(this.options.readonly){
				this.element.attr("readonly","readonly");
			}
			//用div封装组件 required属性
			this._starDiv = $.dtui.util.createStarElem(this.options.required);
			this._starElem = this._starDiv.find('span.input-star');
			this.element.after(this._starDiv);
			this.element.addClass("input-init ui-textarea");//ui-textarea 用于表单验证 慎重修改之
			if (!this.options.width) {
				this.options.width = this.options.cols*15+10;
			}
			if (!this.options.height) {
				this.options.height = this.options.rows*15;
			}
//			if (this.options.showWordCount){
//				this.options.height += 20;
//			}
			
			var container= this.wrapElem = $("<div style='display:inline-block'></div>")
							.css({"height":this.options.height,"width":this.options.width})
							.insertAfter(this.element);
			this._starDiv.appendTo(container)
							.css("padding-top",(this.options.height-16)/2);//set star middle
			
			var divinput=$("<div></div>").addClass("input-Container").append(this.element).appendTo(container)
							.addClass('ui-corner-all').addClass('ui-widget-content');                       //set corner all
			this.element.css({"width":"100%", "height":"100%"});
			divinput.css("margin-right",this._starDiv.outerWidth());
			this.wordCountDiv = $('<div></div>').insertAfter(container);
			if (this.options.showWordCount){
				this.wordCountDiv.show();
			}
			else {
				this.wordCountDiv.hide();
			}
			this._updateWordCount();
		},
		
		_updateWordCount:function(){
			var n=$.dtutil.strutil.getLength(this.element.val()),
				remainWords = Math.abs(Math.floor((this.options.maxwords - n)/2)),
				hintStr = (this.options.maxwords >= n ? "还可以输入" : "已超出") +remainWords+'个汉字';			
			this.wordCountDiv.html(hintStr);
		},
		
		_validate:function(){
			var self=this;
			if(self.options.immValidate==true){
				self.element.bind("blur",function(){
					 //输入框不能为空
					   if(self.options.required&&self.element.val()==""){
						   $.dtmessagebox.alert(self.options.prompt+ "不能为空！"); 
						   return false;
					   	}
					 //  
					   if(self.options.minwords>0){
						   self._checkLength("min");
					   }
					   if(self.options.maxwords>1){
						   self._checkLength("max");
					   }
				});
			}
		},
		_bindEvent:function(){
			var self=this;
			function handleChange() {
				self._updateWordCount();
			}
			
			if ($.browser.msie){
				//this.element.get(0).onpropertychange = handleChange;
				this.element.get(0).attachEvent('onpropertychange', handleChange);
				//为了支持ie9，ie9中onpropertychange在删除字符是不触发
				this.element.get(0).attachEvent('onkeyup', handleChange);
			}
			else {
				this.element.get(0).addEventListener("input",handleChange, false);
			}

			self.element.attr("onfocus",self.options.onfocus);
			self.element.attr("onblur",self.options.onblur);
			self.element.attr("onselect",self.options.onselect);
			self.element.attr("onchange",self.options.onchange);
			self.element.attr("onclick",self.options.onclick);
			self.element.attr("ondblclick",self.options.ondblclick);
			self.element.attr("onmousedown",self.options.onmousedown);
			self.element.attr("onmouseup",self.options.onmouseup);
			self.element.attr("onmouseover",self.options.onmouseover);
			self.element.attr("onmousemove",self.options.onmousemove);
			self.element.attr("onmouseout",self.options.onmouseout);
			self.element.attr("onkeypress",self.options.onkeypress);
			self.element.attr("onkeydown",self.options.onkeydown);
			self.element.attr("onkeyup",self.options.onkeyup);
			/*
			this.element.on('keyup',function(){
				self._updateWordCount();
			});
			*/
			
		},
		_checkLength:function(mark){
			  var self=this;
			  var setlength=0,valueStr=0;
			  if(mark=="max"){
				  setlength=self.options.maxwords;
				  valueStr=self.element.val();
				  if($.dtutil.strutil.getLength(valueStr)>setlength){
					  var prompt=self.options.prompt;
			    	  $.dtmessagebox.alert(prompt+"长度不能超过"+setlength+"！");
			    	  return false;
				  }
				  return true;
			  }
			  else{
				  setlength=self.options.minwords;
				  valueStr=self.element.val();
				  if($.dtutil.strutil.getLength(valueStr)<setlength){
					  var prompt=self.options.prompt;
			    	  $.dtmessagebox.alert(prompt+"长度不能小于"+setlength+"！");
			    	  return false;
				  }
				  return true;
			  }
		},
		_create:function(){
			this._createElement();
			this._validate();
			this._bindEvent();
		},
		
		_setupDisabled: function(disabled){
			if ( disabled ) {
				$('*', this.wrapElem).prop( "disabled", true );
				//this.element.prop( "disabled", true );
			} else {
				//this.element.prop( "disabled", false );
				$('*', this.wrapElem).prop( "disabled", false );
			}
		},

		_setupWidth: function(width){
			//this._e.selectHead.width(width);
			//this._e.selectOption.width(width);
			this.wrapElem.width(width);
		},
		_setupHeight: function(height){
			this.wrapElem.height(height);
		},
		_setupRequired: function(required){
			if(required){
				this._starElem.show();
			}
			else {
				this._starElem.hide();
			}
		},
		
		_setOption : function(key, value) {
			this._super(key, value);
			if (key === "disabled") {
				this._setupDisabled(value);
				return;
			}
			if (key === "width") {
				this._setupWidth(value);
				return;
			}
			if (key === "height") {
				this._setupHeight(value);
				return;
			}
			if (key === "required") {
				this._setupRequired(value);
				return;
			}
		},		
		value:function(val){
	    	if(val==undefined){
	    		return this.element.val();
	    	}else{
	    		this.element.val(val);
	    	}
	    }
		
	});
})(jQuery);/*
 * 工具条控件
 */
(function( $ ) {

$.widget( "dtui.dttoolbar", {
	options: {
		items: [], //每个按钮的配置项，具体参考按钮组件
		menuSelect : null
   	},
    _create: function(){
    	this.items = this._initItems();
    	this.element
			.addClass('dtui-toolbar ui-widget-header ui-helper-reset ui-helper-clearfix')
			.children('li')
				.addClass('dtui-toolbar-item');
    },
    _initItems: function(){
    	var self = this,
    		$items = this.element.children('li'),
    		eleLen = $items.length,
    		itemsOption = this.options.items,
    		oLen = itemsOption.length,
    		length = eleLen > oLen ? eleLen : oLen,
    		element;
    	
    	for(var i=0; i<length; i++){
    		element = $items[i];
    		element.option = itemsOption[i];
    		this._createItem(element);
    	}
    	$items.on('click', '>:first-child', function(event){
    		self._activeItem($(this).parent(), event);
    	});
    	self._on( {
			keydown: function( event ) {
				if ( event.keyCode == $.ui.keyCode.ESCAPE && self.active && self.active.menu( "left", event ) !== true ) {
					var active = self.active;
					self.active.blur();
					self._close( event );
					active.prev().focus();
				}
			},
			focusin: function( event ) {
				clearTimeout( self.closeTimer );
			},
			focusout: function( event ) {
				self.closeTimer = setTimeout( function() {
					self._close( event );
				}, 100);
			}
		});
    },
    _activeItem : function(item, event){
    	var self = this,
    		$menu = item
				.children(':nth-child(2)');
    	if($menu.length > 0){
    		if($menu.is( ":visible" ) && self.active && self.active[0] == $menu[0]){
    			self._close();
    		}else{
    			self._open(event, $menu)
    		}
    	}else{
    		self._close();
    	}
    },
    _createItem: function(item){
    	var self = this;
    	var $menu = $(item)
    		.children(':nth-child(2)')
    			.addClass('dtui-toolbar-menu');
    	if($menu.length > 0){
    		$menu.menu(item.option.menu).hide();
    	}
    	$(item)
    		.children(':first-child')
    			.dtbutton(item.option.button);
    },
    _open: function( event, menu ) {
		// on a single-button menubar, ignore reopening the same menu
		if ( this.active && this.active[0] == menu[0] ) {
			return;
		}
		// TODO refactor, almost the same as _close above, but don't remove tabIndex
		if ( this.active ) {
			this.active
				.menu( "collapseAll" )
				.hide()
				.attr( "aria-hidden", "true" )
				.attr( "aria-expanded", "false" );
			this.active
				.prev()
				.removeClass( "ui-state-active" );
		}
		// set tabIndex -1 to have the button skipped on shift-tab when menu is open (it gets focus)
		var button = menu.prev().addClass( "ui-state-active" ).attr( "tabIndex", -1 );
		this.active = menu
			.show()
			.position( {
				my: "left top",
				at: "left bottom",
				of: button
			})
			.removeAttr( "aria-hidden" )
			.attr( "aria-expanded", "true" )
			.menu("focus", event, menu.children( "li" ).first() )
			// TODO need a comment here why both events are triggered
			.focus()
			.focusin();
		this.open = true;
	},
    _close: function() {
		if ( !this.active || !this.active.length )
			return;
		this.active
			.menu( "collapseAll" )
			.hide()
			.attr( "aria-hidden", "true" )
			.attr( "aria-expanded", "false" );
		this.active
			.prev()
			.removeClass( "ui-state-active" )
			.removeAttr( "tabIndex" );
		this.active = null;
		this.open = false;
	}
});
}( jQuery ));
/**
 * 
 */

(function( $, undefined ) {
//alert('a');
	
//	$.fn.zTree.__destroy = $.fn.zTree.destroy;
//	$.fn.zTree.destroy = undefined;
//	delete $.fn.zTree.destroy;
	
	var treePrototype = {};
	
	$.extend(treePrototype, {
		options:  {
			/**原生ztree的属性
			treeId : "",
			treeObj : null,
			async : {
				autoParam : [],
				contentType : "application...",
				dataFilter : null,
				dataType : "text",
				enable : false,
				otherParam : [],
				type : "post",
				url : ""
			},
			callback : {
				beforeAsync : null,
				beforeCheck : null,
				beforeClick : null,
				beforeCollapse : null,
				beforeDblClick : null,
				beforeDrag : null,
				beforeDragOpen : null,
				beforeDrop : null,
				beforeEditName : null,
				beforeExpand : null,
				beforeMouseDown : null,
				beforeMouseUp : null,
				beforeRemove : null,
				beforeRename : null,
				beforeRightClick : null,
				onAsyncError : null,
				onAsyncSuccess : null,
				onCheck : null,
				onClick : null,
				onCollapse : null,
				onDblClick : null,
				onDrag : null,
				onDrop : null,
				onExpand : null,
				onMouseDown : null,
				onMouseUp : null,
				onNodeCreated : null,
				onRemove : null,
				onRename : null,
				onRightClick : null
			},
			check : {
				autoCheckTrigger : false,
				chkboxType : {"Y": "ps", "N": "ps"},
				chkStyle : "checkbox",
				enable : false,
				nocheckInherit : false,
				chkDisabledInherit : false,
				radioType : "level"
			},
			data : {
				keep : {
					leaf : false,
					parent : false
				},
				key : {
					checked : "checked",
					children : "children",
					name : "name",
					title : "",
					url : "url"
				},
				simpleData : {
					enable : false,
					idKey : "id",
					pIdKey : "pId",
					rootPId : null
				}
			},
			edit : {
				drag : {
					autoExpandTrigger : true,
					isCopy : true,
					isMove : true,
					prev : true,
					next : true,
					inner : true,
					borderMax : 10,
					borderMin : -5,
					minMoveSize : 5,
					maxShowNodeNum : 5,
					autoOpenTime : 500
				},
				editNameSelectAll : false,
				enable : false,
				removeTitle : "remove",
				renameTitle : "rename",
				showRemoveBtn : true,
				showRenameBtn : true
			},
			view : {
				addDiyDom : null,
				addHoverDom : null,
				autoCancelSelected : true,
				dblClickExpand : true,
				expandSpeed : "fast",
				fontCss : {},
				nameIsHTML : false,
				removeHoverDom : null,
				selectedMulti : true,
				showIcon : true,
				showLine : true,
				showTitle : true
			}
			*/
		},
		
		proxyObj: undefined,
	
		_create: function() {
			this.element.addClass('ztree ztree-ext ui-widget-content');
			if (this.options.width){
				this.element.css('width', this.options.width);
			}
			if (this.options.height){
				this.element.css('height', this.options.height);
			}
			this.proxyObj = $.fn.zTree.init(this.element, this.options, this.options.nodes);
			this.options = $.extend(this.options, this.proxyObj.setting);
		},
		
		option: function( key, value ) {
			this._super( key, value );
			
			if (arguments.length === 2 && typeof key === "string"){
				
				var parts = key.split( "." );
				key = parts.shift();
				if ( parts.length ) {
					var curOption = this.proxyObj.setting[key];
					for ( var i = 0; i < parts.length - 1; i++ ) {
						curOption[ parts[ i ] ] = curOption[ parts[ i ] ] || {};
						curOption = curOption[ parts[ i ] ];
					}
					key = parts.pop();
					if ( value === undefined ) {
						return curOption[ key ] === undefined ? null : curOption[ key ];
					}
					curOption[ key ] = value;
				} else {
					if ( value === undefined ) {
						return this.proxyObj.setting[key] === undefined ? null : this.proxyObj.setting[key];
					}
					this.proxyObj.setting[key] = value;
				}				
				this.proxyObj.setting[key] = value;
			}
		},
		
		
		getTree: function() {
			var treeId = this.element.attr('id');
			return $.fn.zTree.getZTreeObj(treeId);
		},
		
		addNodes: function(parNode, newNode, isSilent){
			return this.proxyObj.addNodes(parNode, newNode, isSilent);
		},
		
		removeNode: function(treeNode, callbackFlag){
			return this.proxyObj.removeNode(treeNode, callbackFlag);
		},
		
		removeChildNodes: function(parNode){
			return this.proxyObj.removeChildNodes(parNode);
		},
		
		expandAll : function(expandFlag){
			return this.proxyObj.expandAll(expandFlag);
		},
		
		expandNode : function(node, expandFlag, sonSign, focus, callbackFlag) {
			return this.proxyObj.expandNode(node, expandFlag, sonSign, focus, callbackFlag);
		},
		
		getNodes : function() {
			return this.proxyObj.getNodes();
		},
		
		getNodeByParam : function(key, value, parentNode) {
			return this.proxyObj.getNodeByParam(key, value, parentNode);
		},
		getNodeByTId : function(tId) {
			return this.proxyObj.getNodeByTId(tId);
		},
		getNodesByParam : function(key, value, parentNode) {
			return this.proxyObj.getNodesByParam(key, value, parentNode);
		},
		getNodesByParamFuzzy : function(key, value, parentNode) {
			return this.proxyObj.getNodesByParamFuzzy(key, value, parentNode);
		},
		getNodesByFilter: function(filter, isSingle, parentNode, invokeParam) {
			return this.proxyObj.getNodesByFilter(filter, isSingle, parentNode, invokeParam);
		},
		
		getNodeIndex : function(node) {
			return this.proxyObj.getNodeIndex(node);
		},
		//返回第一个选中的节点
		getSelectedNode: function() {
			var selNodes = this.getSelectedNodes();
			if (selNodes && selNodes.length>0)
				return selNodes[0];
			return null;
		},
		//返回所有选中的节点
		getSelectedNodes: function() {
			return this.proxyObj.getSelectedNodes();
		},
		
		isSelectedNode : function(node) {
			return this.proxyObj.isSelectedNode(node);
		},
		reAsyncChildNodes : function(parentNode, reloadType, isSilent) {
			this.proxyObj.reAsyncChildNodes(parentNode, reloadType, isSilent);
		},
		refresh : function() {
			this.proxyObj.refresh();
		},
		selectNode : function(node, addFlag) {
			this.proxyObj.selectNode(node, addFlag);
		},
		cancelSelectNode: function(node){
			this.proxyObj.cancelSelectNode(node);
		},
		transformTozTreeNodes : function(simpleNodes) {
			return this.proxyObj.transformTozTreeNodes(simpleNodes);
		},
		transformToArray : function(nodes) {
			return this.proxyObj.transformToArray(nodes);
		},
		updateNode : function(node, checkTypeFlag) {
			this.proxyObj.updateNode(node, checkTypeFlag);
		},
		_destroy: function(){
			var treeId = this.element.attr('id');
//			this.__destroy(treeId);
			$.fn.zTree.destroy(treeId);
		},
		
		//节点编辑的相关操作,来自于ztree.exedit.js
		cancelEditName : function(newName) {
			this.proxyObj.cancelEditName(newName);
		},
		copyNode : function(targetNode, node, moveType, isSilent) {
			return this.proxyObj.copyNode(targetNode, node, moveType, isSilent);
		},
		
		editName: function(node) {
			return this.proxyObj.editName(node);
		},
		
		moveNode : function(targetNode, node, moveType, isSilent) {
			return this.proxyObj.moveNode(targetNode, node, moveType, isSilent);
		},
		setEditable : function(editable) {
			return this.proxyObj.setEditable(editable);
		},
		
		//多选相关方法，来自于ztree.excheck.js
		checkNode : function(node, checked, checkTypeFlag, callbackFlag) {
			this.proxyObj.checkNode(node, checked, checkTypeFlag, callbackFlag);
		},

		checkAllNodes : function(checked) {
			this.proxyObj.checkAllNodes(checked);
		},

		getCheckedNodes : function(checked) {
			return this.proxyObj.getCheckedNodes(checked);
		},

		getChangeCheckedNodes : function() {
			return this.proxyObj.getChangeCheckedNodes();
		},

		setChkDisabled : function(node, disabled, inheritParent, inheritChildren) {
			this.proxyObj.setChkDisabled(node, disabled, inheritParent, inheritChildren);
		},

		updateNode : function(node, checkTypeFlag) {
			this.proxyObj.updateNode(node, checkTypeFlag);
		}		
	});
		
	$.widget( "dtui.dttree", treePrototype);

})( jQuery );/**
 * 此组件在combobox基础上做的升级
 */
(function($,undefined){

	$.widget("dtui.dttreecombobox", $.dtui.dtabscombobox, {
		options: {
			treeId: null,
			setting:{}
		},
		/**
		 * 构建下拉树内容
		 */
		_buildContent: function(){
			var self = this;
			//添加校验时的标识;  组件整体标识-- 文本框标识
			self.totalContainer.addClass("dtui-treecombobox");
			self._e.inputText.addClass("dtui-treecombobox-text");
			//this._e.inputText.attr("readonly",this.options.readonly);
			if(this.options.treeId){
				this._e.tree = $('#'+this.options.treeId);
				this._e.tree.width('auto');
			}
			else {
				//必须指定id属性，否则页面中多个树型下拉框时会有问题
				this._e.tree = $('<ul></ul>').attr('id', this.element.attr('id')+'_tree').dttree(this.options.setting);
			}
			this._e.tree.appendTo(this._e.selectOption);
		},
		
		_bindEvent: function() {
			var self = this;
			this._super();
			this._e.tree.dttree('option','callback.onClick', function(event, treeId, treeNode, clickFlag){
				var text = treeNode[self.options.textField];
				var value = treeNode[self.options.valueField];
				//console.log('value='+value+',text='+text);
				self._e.inputText.val(text);
				//self._e.valueText.data("data", treeNode);
				self._e.valueText.val(value);
				self._hideOption();
				self._trigger('change');
			});
			this._e.tree.dttree('option','callback.onAsyncSuccess', function(event, treeId, treeNode, msg){
				self._refreshTreeNodeState(treeNode);
			});
		},
		
		/**
		 * 根据当前文本框的值，选中树中对应的节点
		 * @param treeNode
		 */
		_refreshTreeNodeState: function(treeNode){
			if (this.value()){
				var nodes = this._e.tree.dttree('getNodesByParam', this.options.valueField, this.value(), treeNode);
				if (nodes.length>0)
					this._e.tree.dttree('selectNode',nodes[0]);
			}
		},
		
        /**
         * 根据当前的值刷新内容区域的选择状态
         */
       _refreshContentState: function() {
    	   this._refreshTreeNodeState(null);
        },
        
        /**
         * 根据值获取对应的文本
         * @param value
         */
        _getText: function(value){
			var nodes = this._e.tree.dttree('getNodesByParam', this.options.valueField, value);
			if (nodes.length>0){
				return nodes[0][this.options.textField];
			}
			return value;
        }	

	});
})(jQuery);/**
 * 下拉框(原来的 没有改动) 与html明显区别 可以接收 json 数据(key,value)
 */
(function($, undefined) {
	$.widget("dtui.dttreemenu", {
		options : {
			width : null,
			height : null,
			menuItems : null,
			url : '',
			nameField: 'name',
			simpleData: true,
			idKey: 'id',
			pIdKey: 'pid',
			childKey: 'children',
			showIcon: true,
			defaultExpand: false,
			click: null,
			INDENT: 20			//每级缩进量
		},
		_create : function() {
			if (!this.element.hasClass('treemenu')){
				this.element.addClass('treemenu');
			}
			if (this.options.menuItems){
				this._createMenuElems();
			}
			else if (this.options.url){
				this._getMenuItemsByUrl();	
				this._createMenuElems();
			}

			this._setupEvent();
			this._applyOption();
		},
		_applyOption: function() {
			var o = this.options;
			this._setupDisabled(o.disabled);
			this._setupWidth(o.width);
			this._setupHeight(o.height);
		},

		_setupDisabled: function(disabled){
			if ( disabled ) {
				this.element.prop( "disabled", true );
			} else {
				this.element.prop( "disabled", false );
			}
		},

		_setupWidth: function(width){
			this.element.width(width);
		},
		_setupHeight: function(height){
			this.element.height(height);
		},
		_setOption : function(key, value) {
			this._super(key, value);
			if (key === "disabled") {
				this._setupDisabled(value);
				return;
			}
			if (key === "width") {
				this._setupWidth(value);
				return;
			}
			if (key === "height") {
				this._setupHeight(value);
				return;
			}
		},
		/**
		 * 设定构建下拉列表的数据
		 * 
		 */
		_getMenuItemsByUrl : function() {
			var that = this;
			$.ajax({
				url : this.options.url,
				type : 'post',
				async : false,
				success : function(data) {
					that.options.menuItems = data;
				}
			});
		},
		_createMenuElem: function(menuItem, level, parMenuElem){
			var $li = $('<li></li>');
			$li.data('menudata', menuItem);
			var $a = $('<a></a>');
			if (level == 0){
				$li.addClass('toplevel ui-widget ui-helper-reset');
				$a.addClass('toplevel-header ui-state-default ui-corner-top');
			}
			var $ecIcon = $('<span class="expand-icon"></span>');//展开收缩图标span
			$a.append($('<span></span>').css({'width':level*this.options.INDENT}))	//缩进span
				.append($ecIcon);			//展开收缩图标span
			if (this.options.showIcon){
				$a.append($('<span class="ui-icon ui-icon-bullet"></span>'));					//节点图标span
			}
			$a.append($('<span></span>').text(menuItem[this.options.nameField]));	//文本span
			
			$li.append($a).appendTo(parMenuElem);
			
			var children = menuItem[this.options.childKey]; 
			if (children && children.length >0 ){
				var $ul = $('<ul></ul>');
				if (this.options.defaultExpand){
					$ecIcon.addClass('ui-icon ui-icon-triangle-1-s');
				}
				else {
					$ecIcon.addClass('ui-icon ui-icon-triangle-1-e');
					$ul.css('display','none');
				}
				
				if (level == 0){
					$ul.addClass('toplevel-content ui-helper-reset ui-widget-content ui-corner-bottom toplevel-content-active');
				}
				else {
					$ul.addClass('toplevel-content-active');
				}
				for (var i=0; i<children.length; i++){
					this._createMenuElem(children[i], level+1, $ul);
				}
				$ul.appendTo($li);
			}
		},
		_setupEvent: function(){
			var that = this;
			this._hoverable(this.element.find('li>a'));
			
			this.element.find('li>a').click(function() {
				that._menuClick(this);
			});
			/*
			this.element.delegate('li>a','click', function() {
				that._menuClick(this);
			});
			*/
		},
		_menuClick: function(itemlink){
			var $item = $(itemlink).parent();
			if (this.hasChild($item)) {
				var expanded = $(itemlink).find('span.expand-icon').hasClass('ui-icon-triangle-1-s');
				this.expandMenu($item, !expanded);
			}
			this._trigger('click', null, {item: $item[0], itemlink:itemlink});
		},
		hasChild: function(menuItem) {
			return menuItem.find('>ul').length>0;
		},
		expandMenu: function(menuItem, flag){
			if (flag) {
				menuItem.find('>ul').slideDown('fast');
				menuItem.find('>a>span.expand-icon')
					.removeClass('ui-icon-triangle-1-e')
					.addClass('ui-icon-triangle-1-s');
			}
			else {
				menuItem.find('>ul').slideUp('fast');
				menuItem.find('>a>span.expand-icon')
					.removeClass('ui-icon-triangle-1-s')
					.addClass('ui-icon-triangle-1-e');
			}
		},
		/**
		 * 创建全部菜单项
		 */
		_createMenuElems : function() {
			var o = this.options;
			this.element.html('');
			if (!o.menuItems || o.menuItems.length == 0)
				return;
			for (var i=0; i<o.menuItems.length; i++){
				this._createMenuElem(o.menuItems[i], 0, this.element);
			}
			
		},
		/**
		 * 依据请求地址重新创建下拉列表项
		 * 
		 * @param url
		 */
		refreshUrl : function(url) {
			this.element.html("");
			this.options.url = url;
			this._getMenuItemsByUrl();
			this._createMenuElems();
			this._setupEvent();
		},
		/**
		 * 依据数据重新创建下拉列表项
		 * 
		 * @param menuItems
		 */
		refreshMenuItems : function(menuItems) {
			this.element.html("");
			this.options.menuItems = menuItems;
			this._createMenuElems();
			this._setupEvent();
		}

	});
})(jQuery);/*
 * @param selector  formID|form Array
 * @param mode      writemode|alertmode
 * @param options   JSON|undefined
 * */ 

$.extend({
	
	
	dtvalidate:function(selector,mode,options){
		
		//默认提示方式为alert
		if (mode ==undefined)
			mode = 'alert';
		
		if(options==undefined){//支持 输入时校验和提交时校验 两种方式  但 校验方式比较单一提示信息不灵活 
			
			return $.checkInput(selector,mode);
		
		}else{//只支持 提交时校验 不支持输入时 校验 但 校验方式比较灵活 提示信息用户自定义 比较灵活
			
			return $.validate(selector,mode,options);
		
		}
		
	},
	
	processHint: function($input, msg, mode){
		if (mode == 'tip'){
			$.dtui.util.createTip($input[0], msg);
		}
		else if(mode=="caption"){
			$input.after("<font color='red'name='warning'>&nbsp;"+msg+"</font>");
		}
		else if(mode=="alert"){
			return msg+"\n";
		}
	},
	
	checkInput:function(selector,mode){
		var flag=true;
		var alertString="";
		$("font[name='warning']").remove();
		$('.dtui-tip').remove();
		
		if ( typeof selector === "string" ) {
			//$(selector+" input.input,"+selector+" textarea").each(function(){
			$(selector).find("input.ui-input").each(function(){
				 var obj$=$(this);
				 var ret=$.check(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			
			
			//校验日期组件
			$(selector).find("input.hasDatepicker").each(function(){
				 var obj$=$(this);//当前下拉框
				 
				 var ret=$.checkDataPicker(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			//校验下拉框组件
			$(selector).find("div.ui-link-combobox").each(function(){
				 var obj$=$(this);
				 var ret=$.checkCombobox(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			
			//校验单选组组件
			$(selector).find("div.dtui-radiogroup-container").each(function(){
				 var obj$=$(this);
				 var ret=$.checkRadiogroup(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			//校验 CKediter组件 
			$(selector).find(".ui-CKediter").each(function(){
				 var obj$=$(this);
				 var ret=$.checkCKEditer(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			//检验 textarea组件
			$(selector).find("textarea.ui-textarea").each(function(){
				 var obj$=$(this);
				 var ret=$.checkTextarea(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			//检验 checkcombobox组件
			$(selector).find(".dtui-checkcombobox").each(function(){
				 var obj$=$(this);
				 obj$.dtcheckcombobox('option','required');
				 var ret=$.checkCheckcombobox(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			//检验 gridcombobox组件
			$(selector).find(".dtui-gridcombobox").each(function(){
				 var obj$=$(this);
				 var ret=$.checkGridcombobox(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
		    //检验treecombobox组件
			$(selector).find(".dtui-treecombobox").each(function(){
				 var obj$=$(this);
				 var ret=$.checkTreecombobox(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
		    //检验monthpicker组件
			$(selector).find(".ui-monthpicker").each(function(){
				 var obj$=$(this);
				 var ret=$.checkMonthPicker(obj$,mode);
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
			
			
		}
		else if ($.isArray(selector)) {
			 $.each(selector, function(i,value){
				 var obj$=$(value);
				 var ret;
				 if(obj$.hasClass("ui-input")){
					 
					 ret=$.check(obj$,mode);
				 }
				 else if(obj$.hasClass("hasDatepicker")){
					 ret=$.checkDataPicker(obj$,mode);
				 }
				 else if(obj$.hasClass("ui-link-combobox")){
					 ret=$.checkCombobox(obj$,mode);
				 }
				 else if(obj$.hasClass("dtui-radiogroup-container")){
					 ret=$.checkRadiogroup(obj$,mode);
				 }
				 //CKEditer
				 else if (obj$.hasClass("ui-CKediter")){
					 ret=$.checkCKEditer(obj$,mode);
				 }
				 //textarea
				 else if (obj$.hasClass("ui-textarea")){
					 ret=$.checkTextarea(obj$,mode);
				 }
				 //checkcombobox
				 else if(obj$.hasClass("dtui-checkcombobox")){
					 ret=$.checkCheckcombobox(obj$,mode);
				 }
				 //gridcombobox
				 else if(obj$.hasClass("dtui-gridcombobox")){
					 ret=$.checkGridcombobox(obj$,mode);
				 }
				 //treecombobox
				 else if(obj$.hasClass("dtui-treecombobox")){
					 ret=$.checkTreecombobox(obj$,mode);
				 }
				 //monthpicker
				 else if(obj$.hasClass("ui-monthpicker")){
					 ret=$.checkMonthPicker(obj$,mode);
				 }
				
				 if(ret.returnFlag==false){
					 flag=false;
				 }
				 alertString+=ret.returnString;
			 });
		}
		if(mode=="alert"&&flag==false){
			alert(alertString);
		}
		return flag;
	},
	checkTextarea:function(obj$,mode){
		var alertString="";
		
		var disabled = obj$.dttextarea('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		
		var required=obj$.dttextarea('option','required');
		var maxwords=obj$.dttextarea('option','maxwords');
		var minwords=obj$.dttextarea('option','minwords');
		var prompt=obj$.dttextarea('option','prompt');
		
		if(required==true){
			if(!$.validate_required(obj$.attr("id"))){
				alertString = $.processHint(obj$.parent().parent(), prompt+"不能为空！", mode);
				return {returnString:alertString,returnFlag:false};
			}
		}
		if($.trim(obj$.val())) {
			if(maxwords!=null){
				if(!$.validate_maxlength(obj$.attr("id"),maxwords)){
					alertString = $.processHint(obj$.parent().parent(), prompt+"长度不能超过"+maxwords+"个字符！", mode);
					return {returnString:alertString,returnFlag:false};
				}
			}
			if(minwords>0){
				if(!$.validate_minlength(obj$.attr("id"),minwords)){
					alertString = $.processHint(obj$.parent().parent(), prompt+"的值不能小于"+minwords, mode);					
					return {returnString:alertString,returnFlag:false};
				}
			}
		}
		return {returnString:alertString,returnFlag:true};
	},
	check:function(obj$,mode){
		var alertString="";
		var disabled = obj$.dtinput('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		
		//alert(obj$[0].nodeType);
		var inputType=obj$.dtinput('option','inputType');
		var required=obj$.dtinput('option','required');
		var length=obj$.dtinput('option','length');
		var maxVal=obj$.dtinput('option','maxVal');
		var minVal=obj$.dtinput('option','minVal');
		var prompt=obj$.dtinput('option','prompt');
		if(required==true){
			if(!$.validate_required(obj$.attr("id"))){
				alertString = $.processHint(obj$.parent().parent(), prompt+"不能为空！", mode);
				return {returnString:alertString,returnFlag:false};
			}
		}
		if($.trim(obj$.val())) {
			
			if(inputType!="text"&&inputType!="ipaddress"){
				if(!$.validate_format(obj$.attr("id"),inputType)){
					alertString = $.processHint(obj$.parent().parent(), prompt+"格式不正确！", mode);
					return {returnString:alertString,returnFlag:false};
				}
			}
			
			if(length!=""){
				if(!$.validate_maxlength(obj$.attr("id"),length)){
					alertString = $.processHint(obj$.parent().parent(), prompt+"长度不能超过"+length+"个字符！", mode);
					return {returnString:alertString,returnFlag:false};
				}
			}
			if(minVal!=null){
				if(!$.validate_min(obj$.attr("id"),minVal)){
					alertString = $.processHint(obj$.parent().parent(), prompt+"的值不能小于"+minVal, mode);					
					return {returnString:alertString,returnFlag:false};
				}
			}
			if(maxVal!=null){
				if(!$.validate_max(obj$.attr("id"),maxVal)){
					alertString = $.processHint(obj$.parent().parent(), prompt+"的值不能超过"+maxVal, mode);					
					return {returnString:alertString,returnFlag:false};
				}
			}
			
		}
		return {returnString:alertString,returnFlag:true};
		
	},
	
	checkCKEditer: function(obj$,mode){
		var alertString="";
		var disabled = obj$.dtckeditor('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		var required=obj$.dtckeditor('option','required');
		var value=obj$.dtckeditor('getData');
		
		if(required==true){
			if(value==""){
				alertString = $.processHint(obj$.next().next().parent(), "富文本框不能为空！", mode);					
				return {returnString:alertString,returnFlag:false};
			}
		}
		
		return {returnString:alertString,returnFlag:true};
	},
	
	checkDataPicker:function(obj$,mode){
		var alertString="";
		var disabled = obj$.dtdatepicker('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		var prompt=obj$.dtdatepicker('option','prompt');
		var required=obj$.dtdatepicker('option','required');
		var formatNeed=obj$.dtdatepicker('option','formatNeed');
		var dateFormat=obj$.dtdatepicker('option','dateFormat');
		if(required==true){
			if(!$.validate_required(obj$.attr("id"))){
				alertString = $.processHint(obj$.parent().parent(), prompt+"不能为空！", mode);
				return {returnString:alertString,returnFlag:false};
			}
		}
		if($.trim(obj$.val())&&formatNeed==true){
			
//			new Date(obj$.val())=="Invalid Date"   判断日期是否合法(是否是个日期字符串)
//			$.datepicker.formatDate( dateFormat, new Date(obj$.val()))!=obj$.val()   若是日期字符串 格式是否符合 日期控件中设置的格式
			if(new Date(obj$.val())=="Invalid Date"||$.datepicker.formatDate( dateFormat, new Date(obj$.val()))!=obj$.val()){
				alertString = $.processHint(obj$.parent().parent(), prompt+"格式不正确！", mode);
				return {returnString:alertString,returnFlag:false};
			}
		}
		return {returnString:alertString,returnFlag:true};
	},
	
	checkCombobox:function(obj$,mode){
		var alertString="";
		
		var disabled = obj$.dtcombobox('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		var prompt=obj$.dtcombobox('option','prompt');
		var required=obj$.dtcombobox('option','required');
		var nullValue=obj$.dtcombobox('option','nullValue');
		//找到 下拉输入框
		var value=obj$.find(".dtui-combobox").val();
		
		if(required==true){
			if(value==""||value==nullValue){
				alertString = $.processHint(obj$, prompt+"不能为空！", mode);
				return {returnString:alertString,returnFlag:false};
			}
		}
		return {returnString:alertString,returnFlag:true};
		
	},
	
	checkCheckcombobox:function(obj$,mode){
		var alertString="";
		var disabled = obj$.dtcheckcombobox('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		var required=obj$.dtcheckcombobox('option','required');
		var prompt=obj$.dtcheckcombobox('option','prompt');
		
		//找到 下拉输入框
		var value=obj$.find(".dtui-checkcombobox-text").val();
		if(required==true&&(value==""||value==null)){
			alertString = $.processHint(obj$, prompt+"不能为空！", mode);
			return {returnString:alertString,returnFlag:false};
		}
		return {returnString:alertString,returnFlag:true};
	},
	
	checkGridcombobox:function(obj$,mode){
		var alertString="";
		
		var disabled = obj$.dtgridcombobox('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		var prompt=obj$.dtgridcombobox('option','prompt');
		var required=obj$.dtgridcombobox('option','required');
		//找到 下拉输入框
		var value=obj$.find(".dtui-gridcombobox-text").val();
		
		if(required==true&&(value==""||value==null)){
			alertString = $.processHint(obj$, prompt+"不能为空！", mode);
			return {returnString:alertString,returnFlag:false};
		}
		
		return {returnString:alertString,returnFlag:true};
	},
	
	checkTreecombobox:function(obj$,mode){
		var alertString="";
		
		var disabled = obj$.dttreecombobox('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		var prompt=obj$.dttreecombobox('option','prompt');
		var required=obj$.dttreecombobox('option','required');
		
		//找到 下拉输入框
		var value=obj$.find(".dtui-treecombobox-text").val();
		
		if(required==true&&(value==""||value==null)){
			alertString = $.processHint(obj$, prompt+"不能为空！", mode);
			return {returnString:alertString,returnFlag:false};
		}
		
		return {returnString:alertString,returnFlag:true};
	},
	
	checkMonthPicker:function(obj$,mode){
		var alertString="";
		var disabled = obj$.dtmonthpicker('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		
		var prompt=obj$.dtmonthpicker('option','prompt');
		var required=obj$.dtmonthpicker('option','required');
		
		//找到 下拉输入框
		var value=obj$.dtmonthpicker('value');
		
		if(required==true&&(value==""||value==null)){
			alertString = $.processHint(obj$, prompt+"不能为空！", mode);
			return {returnString:alertString,returnFlag:false};
		}
		
		return {returnString:alertString,returnFlag:true};
	},
	
	checkRadiogroup:function(obj$,mode){
		var alertString="";
		var disabled = obj$.dtradiogroup('option','disabled');
		if (disabled){
			return {returnString:alertString,returnFlag:true};
		}
		
		var required=obj$.dtradiogroup('option','required');
		
		//找到 下拉输入框
		var value=obj$.find("input[type='radio']:checked").val();
		
		
		if(required==true){
			if(value==undefined){
				alertString = $.processHint(obj$.parent().parent(), "单选按钮组不能为空！", mode);
				return {returnString:alertString,returnFlag:false};
			}
		}
		
		return {returnString:alertString,returnFlag:true};
	},
	
	
	validate:function(selector,mode,options){
		$("font[name='warning']").remove();
		$('.dtui-tip').remove();
		var flag=true;
		var alertString="", msg = "";
		var rules=options.rules;
		var messages=options.messages;
		
		//根据校验规则 字符串字段 校验时不会出现 第三参数值
		for(var i=0;i<rules.length;i++){
			for(var key in rules[i]){
				//alert(key+"kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
				if(typeof(rules[i][key])==='string'){
					//key 是 控件的ID  value 为验证项  第二个为  不符合时输出信息
					//alert("key："+key+",value："+self.options.rules[i][key]);//可以直接处理
					//alert("key："+key+",value："+self.options.messages[i][key]);//可以直接处理
					//alert(!self._validatfunction(key,self.options.rules[i][key]));
					
					if(!$.validatfunction(key,rules[i][key])){
						msg = $.processHint($("#"+key).next(), messages[i][key], mode);
						if(mode=="alert"){
							alertString+=msg;
						}
						flag=false;
					}
					//根据校验规则 对象字段 校验时可能会出现 第三参数值
				}else{
					var newobj=rules[i][key];
					//alert(key+"ddddddddddddddd");//间接处理对象的key
					for(var j=0;j<rules[i][key].length;j++)
					for(var keyj in rules[i][key][j]){
						//alert("key:"+keyj+",value："+self.options.rules[i][key][j][keyj]);//间接 处理对象的校验方式(keyj=required,value=true)
						//alert("key:"+keyj+",value："+self.options.messages[i][key][j][keyj]);//间接处理对象提示方式(keyj=required,value="请输入Email地址")
						
						if(!$.validatfunction(key,keyj,rules[i][key][j][keyj])){//第三字段值 除了 true没用外 其他都有用
							msg = $.processHint($("#"+key).next(), messages[i][key][j][keyj], mode);
							if(mode=="alert"){
								alertString+=msg;
							}
							
							j=rules[i][key].length;//有一个错就跳出外层循环 
							flag=false;
						}
					}
					
				}
			}
		}
		
		if(mode=="alert"&&flag==false){
			alert(alertString);
		}
		
		return flag;
		
	},
	validatfunction:function(key,valid_type,otherparam){
		if(valid_type=="required"){
			return $.validate_required(key);
		}else if(valid_type=="equalTo"){
			return $.validate_equalTo(key,otherparam);
		}else if(valid_type=="maxlength"){
			return $.validate_maxlength(key,otherparam);
		}else if(valid_type=="minlength"){
			return $.validate_minlength(key,otherparam);
		}else if(valid_type=="max"){
			return $.validate_max(key,otherparam);
		}else if(valid_type=="min"){
			return $.validate_min(key,otherparam);
		}else if(valid_type=="custom"){
			return $.validate_custom(key,otherparam);
		}else{
			return $.validate_format(key,valid_type);
		}
		
	},
	
	validate_required:function(id){
		return $.trim($("#"+id).val()).length>0;//不为空 返回true 否则返回false
	},
	validate_equalTo:function(id,toid){
		return $("#"+id).val()==$("#"+toid).val();
	},
	validate_maxlength:function(id,maxLength){
		//alert($("#"+id).val().length<=maxLength);
		return $.dtutil.strutil.getLength($("#"+id).val())<=maxLength;
	},
	validate_minlength:function(id,minLength){
		return  $.dtutil.strutil.getLength($("#"+id).val())>=minLength;
	},
	validate_max:function(id,maxNumber){
		return parseFloat($("#"+id).val())<=parseFloat(maxNumber);
	},
	
	validate_min:function(id,minNumber){
		return parseFloat($("#"+id).val())>=parseFloat(minNumber);
	},
	/**
	 * 用户自定义校验：
	 * @param id
	 * @param re 为用户自定义 正则表达式
	 * @returns
	 */
	validate_custom:function(id,re){
		return re.test($("#"+id).val());
	},
	
	validate_format:function(id,format){
		/*email:true                    必须输入正确格式的电子邮件
		cellphone:true;               手机格式
		phone:true;                   座机格式
		url:true                        必须输入正确格式的网址
		zipcode：true;              邮编格式
		number:true                 必须输入合法的数字(负数，小数)
		identity                    身份证
		digits:true                    必须输入整数*/
		
		var re;
		var JsonObject={
	    	        "email":/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	    	        "mobile":/^[1][358]\d{9}$/,
	    	        "tel":/(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{7,15}$)/,
	    	        "lxdh":/(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{7,15}$)|(^[1][358]\d{9}$)/,
	    	        "url":/(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i,
	    	        "password":/^[a-zA-Z]{1}[0-9a-zA-Z_]{5,17}$/,
	    	        "zipcode":/^\d{6}$/,
	    	        "identity":/^([1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3})|([1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z]))$/,
	    	        "float":/^-?[0-9]*(\d*)*(\.\d*)?(\d*)*$/,
	    	        "floating":/^-?[0-9]*(\d*)*(\.\d*)?(\d*)*$/,
	    	        "int":/^-?[0-9]*(\d*)*$/,
	    	        "digits":/^-?[0-9]*(\d*)*$/
		 };
		
		
		/*var re1;
		if(format=="email"){
			re1=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		}else if(format=="mobile"){//手机
			re1=/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
		}else if(format=="tel"){//座机
			re1=/(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}\-[0-9]{3,4}$)|(^[0-9]{7,15}$)/;
		}else if(format=="url"){
			re1=/(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i;
		}else if(format=="zipcode"){
			re1=/^[1-9][0-9]{5}$/;
		}else if(format=="float"||format=="floating"){
			re1 = /^-?[0-9]*(\d*)*(\.\d*)?(\d*)*$/;
		}else if(format=="identity"){
			 re1=/^([1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3})|([1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z]))$/;
		}else if(format=="int"||format=="digits"){
			re1=/^-?[0-9]*(\d*)*$/;
		}else if(format=="password"){
			re1=/^[a-zA-Z]{1}[0-9a-zA-Z_]{5,17}$/;
		}
		*/
		
	    	  
	    //用户自定义校验方式
	    re= eval('(' + JsonObject[format] + ')');
	   /* alert( JsonObject[format]);
	    alert(re);
	    alert(re1);
	    alert(re==re1);
	    alert(re.test($("#"+id).val()));
	    alert(re1.test($("#"+id).val()));*/
	    if(!re){
	    	  	 re=format;
	       	   }
		
	    if (re)	{
	    			return re.test($("#"+id).val());
	    		}
	    else    {
	    			return true;
	    		}
	}

});/*
 * jQuery验证码插件
 * 功能：提供验证码显示
 * @author whc
 * @version 1.0
 * @date 2011-05-12
 * ---------------------
 * @author wdx
 * @version 1.1
 * @date 2013-4-19 
 * 1.0不没有更改      只在1.0上 添加 新的显示形式
 * newtype 为 true 显示新的形式 默认为false 不改变
 * 加载image时 注意加上时间戳  否则会 缓存原来图片
 *(也可以不用时间戳 加个随机加个随机数也能达到效果)
 */
(function( $, undefined ) {
  var $doc = $(document);
$.widget( "dtui.dtverifycode", {
	options: {
	  src:'',
	  width:'150',
	  height:'40',
	  codecount:'6',
	  codestyle:'mix',
	  newType:false
	},
	_create: function() {
	   if(this.options.newType==true){
	      this._createElement();
		  this._bindEvent(); 
		 
	   }else{
		   
		   obj=this;
		   element=$(this.element);
		   element.attr("src",this.options.src+"?width="+this.options.width+"&height="+ 
		                      this.options.height+"&codecount="+this.options.codecount+
		                      "&codestyle="+this.options.codestyle);
		   element.bind("click",this._changeImg);
		}
	},

	 _changeImg:function(){   
	      var imgSrc = element; 
	      var src = imgSrc.attr("src");
	      imgSrc.attr("src",obj._chgUrl(src));
	    },
    
    //时间戳   
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳   
    _chgUrl:function(url){   
       var timestamp = (new Date()).valueOf();   
       //url = url.substring(0,17); 
       if(url.indexOf("timestamp")>=0){
         url=url.substring(0,url.indexOf("timestamp")-1);
       }  
       if((url.indexOf("&")>=0)){   
          url = url + "&timestamp=" + timestamp;   
       }else{   
          url = url + "?timestamp=" + timestamp;   
       }   
       return url;   
    },
    
	widget: function() {
		return this.element;
	},
	
	_createElement:function(){
		 var e1 = this.element.hide();
		 var head=(this.headContainer=$('<span></span>')).insertAfter(e1),
		 verifyInput=(this.verifyInput=$('<input></input>')).addClass('ui-val-input').addClass('ui-corner-all').appendTo(head),
		 verifybutton=(this.verifybutton=$('<a href="javascript:;">看不清,换一个</a>')).addClass("ui-val-button").appendTo(head),

		 imageContainer=(this.imageContainer=$('<div></div>')).addClass("ui-image-container").insertAfter(head),
		 imageContent=(this.imageContent=$('<span>"输入下图中的字符"<br></span>')).appendTo(imageContainer),
		 val_image=(this.val_img=$('<img />')).appendTo(imageContent);
		 //初始化时加载一张 图片
		 $(this.val_img).attr("src",this.options.src+"?width="+this.options.width+"&height="+ 
		                      this.options.height+"&codecount="+this.options.codecount+
		                      "&codestyle="+this.options.codestyle);		 
	},
	_bindEvent:function(){
		var self=this;
		self.verifyInput.click(function(){
			self.verifyInput.removeClass("ui-corner-all").addClass("ui-corner-top");
			self.imageContainer.addClass("ui-corner-bottom");
			self.imageContainer.css("display","block");
		});
		self.verifybutton.click(function(){
			var src = self.val_img.attr("src");
			self.val_img.attr("src",self._chgUrl(src));
		});
		$doc.click(function(event){
	        var target = event.target;
	        if (!$.contains(self.headContainer[0], target)
	            && !$.contains(self.imageContainer[0], target)
	            && target !== self.headContainer[0]
	            && target !== self.imageContainer[0]){
	               self.imageContainer.css("display","none");
	           }
	        self.verifyInput.addClass("ui-corner-all");
		});
		self.val_img.click(function(){
			var src = self.val_img.attr("src");
			self.val_img.attr("src",self._chgUrl(src));
		});	
	}
});
}( jQuery ) );