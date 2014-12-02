(function($,undefined){

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
})(jQuery);