/*
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

});