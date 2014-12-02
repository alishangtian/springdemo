/*
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
})(jQuery);