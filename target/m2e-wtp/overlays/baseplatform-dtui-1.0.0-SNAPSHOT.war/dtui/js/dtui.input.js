/**
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
})(jQuery); 