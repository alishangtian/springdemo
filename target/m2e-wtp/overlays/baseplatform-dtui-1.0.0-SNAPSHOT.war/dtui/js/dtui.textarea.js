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
})(jQuery);