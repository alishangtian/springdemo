/*
 * @description  登录所涉及的js操作 
 * 
 *@author yangzhuang
 *@date 2014-01-05
 */
(function(w){
	if(!w.login) w.login = {};
	//初始化页面
	login.init=function(){
		this.initDisplay(); 
		w.onresize = this.initDisplay;
		//初始化验证码
		window['verifycodeId'] = $('#verifycodeId').dtverifycode({height: '30',width: '70',src:path+'/verifycode.do',codecount: 4,codestyle : 'digit'});
		//绑定事件
		$('#loginForm').bind('keydown',this.keyDownHander);
		$('#btnLogin').bind('click',this.loginSubmit);
		$('#refreshVefy').bind('click',this.refreshVerify);
	}
	login.initDisplay = function(){
		var leftWidth = (2000-$('body').width())/2,topHeight = (1200-$('body').height())/2;
		$('.login-bg').css({'background-position':'-'+(leftWidth+10)+'px -'+topHeight+'px'});
		$('.login-form').css({'top':460-topHeight,'left':850-leftWidth});
	}
	login.keyDownHander = function(event){
		var event = window.event||event;
		var targetEle = event.target||event.srcElement,targetTag = targetEle.tagName.toLowerCase();
		if ((targetTag == 'input'||targetTag == 'button')&&event.keyCode == 13) {
			if(targetTag == 'button') login.loginSubmit();
			if(inputEles){
				for(var i=0;i<inputEles.length;i++){
					if(inputEles.length-1 == i) {
						document.getElementById('btnLogin').focus();
						break;
					}
					if(inputEles[i].id == targetEle.id){
						inputEles[i+1].focus();
						break;
					}
				}
			}
		}
	}
	login.refreshVerify = function(event){
		var e = event || window.event;
		$('#verifycodeId').trigger('click');
		login.preventDefault(e);
	}
	login.preventDefault = function(e){
	    e.cancelBubble = true;
	    e.returnValue = false;
	    if (e.stopPropagation) {
	        e.stopPropagation();
	        e.preventDefault();
	    }
	}
	login.loginSubmit = function(){
		var message='',userName = $.trim($('#userName').val()),password=new $.Md5().hex_md5($.trim($('#password').val())),verifyCode=$.trim($('#verifyCode').val());
        if(!userName) message+='用户名不能为空';
        if(!password) message+='密码不能为空'; 
        if(!verifyCode) message+='验证码不能为空';
		if(message){
			alert(message);
			return ;
		}
		$.ajax({url:path+'/Home.action',
				type:'post',
				cache:false,
	            data:{'userName':userName,'password':password,'verifyCode':verifyCode},
				success:function(responseText){
					if(responseText)
						alert(responseText);
					else
						window.location.reload();
				},
				error: function(e){
					alert(e);
				}
		});
	}
})(window)