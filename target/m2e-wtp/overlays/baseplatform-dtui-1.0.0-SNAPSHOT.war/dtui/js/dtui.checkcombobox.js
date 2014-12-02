/*----------------------------------------------
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
})(jQuery);