/*!
 * 
 * 
 */
(function( $, undefined ) {

	var serializeArray  = $.fn.serializeArray;
	$.fn.serializeArray = function(){
		this.trigger('dt-form-pre-serialize');
		return serializeArray.call(this);
	};
	
var 
	slice = Array.prototype.slice;

$.widget('dtui.dtckeditor', {
	version:'1.0.1',
	options: {
		autoUpdateElement: true,
		baseFloatZIndex: 10000,
		baseHref: '',
		bodyClass: '',
		bodyId: '', 
		browserContextMenuOnCtrl: true,	//内容区上下文菜单是否显示
		clipboard_defaultContentType: 'html', //剪贴板默认格式，html 或者是 text
		contentsCss: CKEDITOR.basePath + 'contents.css', //内容区样式
		customConfig: CKEDITOR.basePath + 'config.js',		//自定义config文件地址
		devtools_styles: '<!DOCTYPE html>',			//开发工具样式
		enterMode: CKEDITOR.ENTER_BR, // 回车键模式 取值：CKEDITOR.ENTER_P ,CKEDITOR.ENTER_BR ,CKEDITOR.ENTER_DIV (3)
		devtools_textCallback:  false,   //返回一个字符串，显示在开发者工具中
		docType: '',
		filebrowserBrowseUrl: false, //文件浏览url，如：browse.jsp
	    filebrowserUploadUrl: false, //文件上传url，如：upload.jsp
	    filebrowserWindowHeight: '70%',
	    filebrowserWindowWidth: '80%',
	    htmlEncodeOutput: false,
	    newpage_html: '<p>请输入内容</p>',
	    readOnly: false,
	    resize_dir: 'vertical',   //调整大小的方向，取值：both, vertical,和 horizontal.
	    resize_enabled: false,
	    
	    toolbar: 'Basic', //设置工具条， 字符串或者是数组
	    /*使用数组设置
		 *		toolbar: [
		 *			[ 'Source', '-', 'Bold', 'Italic' ]
		 *		]
		 * 
		 *使用字符串设置
		 *      先定义一个名称
		 *	 	toolbar_Basic: [
		 *			[ 'Source', '-', 'Bold', 'Italic' ]
		 *		],
		 *      使用名称定义
		 * 		toolbar: 'Basic'
		 */
		
		toolbarGroups:'',
		/*设置例子
		toolbarGroups = [
		    { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
		    { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		    { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		    { name: 'forms' },
		    '/',
		    { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		    { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
		    { name: 'links' },
		    { name: 'insert' },
		    '/',
		    { name: 'styles' },
		    { name: 'colors' },
		    { name: 'tools' },
		    { name: 'others' },
		    { name: 'about' }
		]
		*/
		toolbarLocation: 'top', //工具条的位置 top 或者是 bottom
    	uiColor: '',
    	templates_replaceContent: false,
    	disableAutoInline: false,		//默认为false，为true时，editor为行内元素
    	required: false,
    	height: '100px',				//高度，单位像素或者是百分比
    	width: '100%'					//宽度，单位像素或者是百分比
	},
	_create: function(){
		//在element外增加新的包装层，以便添加starElem
		this.element.addClass('ui-CKediter');
		this.element.wrap("<div style='width:"+this.options.width+";display:inline-block'></div>");
		this.wrapper = this.element.parent();
		
		var element = this.element[0],
			options = this.options,
			editor = CKEDITOR.replace(element, options),
			$element = $(element);
		
		if (this.options.name)
			this.element.find('textarea').attr('name', this.options.name);
		
		this.editor = editor;
		
		var onSubmit = function(){
			editor.updateElement();
		};

		$element.parents( 'form' ).submit( onSubmit );
		$element.parents( 'form' ).bind( 'dt-form-pre-serialize', onSubmit );

		$element.bind( 'destroy.ckeditor', function()		{
			$element.parents( 'form' ).unbind( 'submit', onSubmit );
			$element.parents( 'form' ).unbind( 'form-pre-serialize', onSubmit );
		});
		
		var that = this;
		function _createStarElem() {
			that._starDiv = $.dtui.util.createStarElem(that.options.required);
			//原ckeditor的最外层
			var $cke = $('#cke_'+that.element[0].id);
			$cke.removeAttr('style');
			//设置为左浮动
			//$('<div></div>').append(that._starDiv).insertBefore($cke).css('float','right');
			that._starDiv.insertBefore($cke);
			
			//if(that.options.required){
				$cke.css('margin-right','8px');
			//}
		}
		//ckeditor控件的创建过程似乎是异步执行，如果不使用延迟，会出错
		setTimeout(_createStarElem, 2000);		
	},
	_destroy: function(){
		
	},
	_callMethod: function(action){
		var
			input = slice.call( arguments, 1 ),
			editor = this.editor;
		return editor[action].apply(editor, input);
	},
	getData: function(){
		return this._callMethod('getData');
	},
	setData: function(data, callback){
		this._callMethod('setData', data, callback);
	},
	//根据option的name返回options的值
	getOption: function(name){
		return this.editor.config[name];
	},
	setReadOnly: function(isReadOnly){
		this._callMethod('setReadOnly', isReadOnly);
	},
	/**
	 * mode取值： html  unfiltered_html 或者 text
	 * 
	 */
	insertHtml: function(html, mode){
		this._callMethod('insertHtml', html, mode);
	},
	resize: function(width, height){
		this._callMethod('resize', width, height);
	},
	updateElement: function(){
		this._callMethod('updateElement');
	}
});

})( jQuery );
