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
})(jQuery);