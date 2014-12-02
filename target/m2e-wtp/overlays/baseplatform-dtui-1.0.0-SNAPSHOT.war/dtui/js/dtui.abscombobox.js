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
			//$console.log("starWidth:"+marginRight);
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
})(jQuery);