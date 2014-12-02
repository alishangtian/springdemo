/*
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