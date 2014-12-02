/**
 * 
 */

(function($, undefined) {
	$.dtmessagebox = {
			show: function(msg, title, icon, buttons, callback, defaultButton) {
				var msgHtml = '<div><div style="margin:15px">';
				if (icon) {
					//icon可以是样式或url
					if (icon.charAt(0) == '.' && (icon.charAt(1) != '.' ||icon.charAt(1) != '/'))
						msgHtml += '<div style="float:left; width:48px; height:48px;" class="'+ icon.slice(1)+'"></div>';
					else
						//msgHtml += '<img style="float:left;" src="'+ icon+'"/>';
						msgHtml += '<div style="float:left; width:48px; height:48px;background:url('+icon+') no-repeat;"></div>';
						//msgHtml += '<div style="float:left; width:48px; height:48px;" class="ui-messagebox-image-warn"></div>';
				}
				msgHtml += '<div style="margin-left:5px; float:left;">'+msg+'</div>';
				msgHtml += '</div>';		
				msgHtml += 	'</div>';
				
//				var d=dt$.dialog(dt$.obj(msgHtml).jqobj, {"title":title, modal:true, resizable:false, "buttons": buttons, close: function(){
//					if (callback) callback(dt$.dialog(this).option('dialogResult'));
//				}});
//				d.showModal();
				$(msgHtml).dtdialog({"title":title, modal:true, resizable:false, "buttons": buttons, close: function(){
					if (callback) callback($(this).dtdialog('option','dialogResult'));
				}});
			},
			alert: function(msg, title, callback) {
				this.show(msg, title||'提示', null, {'确定': function(){
					$(this).dtdialog('close');			
				}}, callback);
			},
			warn: function(msg, title, callback) {
				this.show(msg, title||'警告', '.ui-messagebox-image-warn', {'确定': function(){
					$(this).dtdialog('close');	
				}}, callback);
			},
			error: function(msg, title, callback) {
				this.show(msg, title||'错误', '.ui-messagebox-image-error', {'确定': function(){
					$(this).dtdialog('close');	
				}}, callback);
			},
			success: function(msg, title, callback) {
				this.show(msg, title||'成功', '.ui-messagebox-image-donne', {'确定': function(){
					$(this).dtdialog('close');	
				}},callback);
			},
			confirm: function(msg, title, callback) {
				this.show(msg, title||'询问', '.ui-messagebox-image-question', {
					'确定': function(){
						$(this).dtdialog('option', 'dialogResult', $.dtdialog.DR_OK);	
						$(this).dtdialog('close');	
					},
					'取消': function() {
						$(this).dtdialog('option', 'dialogResult', $.dtdialog.DR_CANCEL);	
						$(this).dtdialog('close');	
					}
				}, callback);
			}
		};
})(jQuery);