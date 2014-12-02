(function($,undefined){

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
})(jQuery);