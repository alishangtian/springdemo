/*
 * @description  动态加载模块 
 *@author yangzhuang
 *@date 2014-03-10
 */
//检测浏览器版本以及类别
void function(w){
	w.Sys = {};
	var s,ua = w.navigator.userAgent.toLowerCase();
    (s = ua.match(/msie ([\d.]+)/)) ? 
    		Sys.ie = s[1] : (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua.match(/chrome\/([\d.]+)/)) ? 
    		Sys.chrome = s[1] : (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua.match(/version\/([\d.]+).*safari/)) ? 
    		Sys.safari = s[1] : 0;
}(window);
(function(w){
//动态加载模块 需要 path
function moduleDefine(){
	/**
	 * 增加模块
	 * @parame moduleName:模块名字
	 * @parame prefix:基于根目录的前置路径
	 * @parame callBack:加载完成之后执行的方法
	 */
	//是否为开发版本
	if(!path) w.path = '';
	var isDev = true,scriptType = 'script',cssType = 'css',bothSpace = /^\s+|\s+$/g,moduleArray = [],i=0;
	this.callBackIn = null;
	//模块与路径的映射
	//根目录
	if(isDev){
		this.loaderConfig={
			'jquery':{'url':path+'/dtui/js/jquery/jquery.js','type':scriptType},
			'jqueryUi':{'url':path+'/dtui/js/jqueryui/jquery-ui.min.js','type':scriptType},
			'dtCombobox':{'url':path+'/dtui/js/dtui.combobox.js','type':scriptType},
			'dtDialog':{'url':path+'/dtui/js/dtui.dialog.js','type':scriptType},
			'dtUtil':{'url':path+'/dtui/js/dtui.util.js','type':scriptType},
			'dtDatepicker':{'url':path+'/dtui/js/dtui.datepicker.js','type':scriptType},
			'dtTab':{'url':path+'/dtui/js/dtui.tabs.js','type':scriptType},
			'verifycode':{'url':path+'/dtui/js/dtui.verifycode.js','type':scriptType},
			'dtNav':{'url':path+'/dtui/js/dtui.navbar.js','type':scriptType},
			'dtButton':{'url':path+'/dtui/js/dtui.button.js','type':scriptType},
			'md5':{'url':path+'/js/func.md5.js','type':scriptType},
			'login':{'url':path+'/js/func.login.js','type':scriptType},
			'main':{'url':path+'/js/func.main.js','type':scriptType}
		};
	}else {
		this.loaderConfig={};
	}
	this.define = function(moduleNames,callBack,isPath,type){
		if(!moduleNames) return false;
		this.callBackIn = callBack;
		if(!isPath){
			i=0,moduleArray = moduleNames.split(',');
			if(moduleArray&&moduleArray.length<=0) return false;
			var moduleName = moduleArray[i], moduleInfo = this.makeDir(moduleName),cb = this.runCb;
			if(!moduleInfo) return false;
			if(i >= moduleArray.length-1){
				cb = this.callBackIn;
			}
			i++;
			this.importOper(moduleInfo.url,cb,'utf-8',moduleInfo.type);
		}else
			this.importOper(moduleNames,callBack,'utf-8',type);
	}
	this.runCb = function(){
		dtloader.callBack.apply(dtloader);
	}
	this.callBack = function(){
		if(i<moduleArray.length){
			var moduleName = moduleArray[i], moduleInfo = this.makeDir(moduleName),cb = this.runCb;
			if(!moduleInfo) {
				alert(moduleName+'文件不存在，请配置文件路径');
				return false;
			}
			if(i >= moduleArray.length-1){
				cb = this.callBackIn;
			}
			i++;
			this.importOper(moduleInfo.url,cb,'utf-8',moduleInfo.type);
		}
	}
	this.cb  =function(){
		window[moduleName]._init();
	}
	this.makeDir  =function(moduleName){
		moduleName = moduleName.replace(bothSpace,'');
		return this.loaderConfig[moduleName]?this.loaderConfig[moduleName]:null;
	}
	this.importOper =  function(url, cb, charset,type){
		if(!type) type=scriptType;
		var cflag = false;
		cflag = this.check(url,type);
		if (cflag){
			if(cb) cb(true);
			return false;
		}
		var s = document.createElement(type);
		if (charset) {
			s.charset = charset;
		}
		if(type.toLowerCase()==cssType){
			s.type = 'text/css';
			s.rel='stylesheet';
			s.href=url;
		}else{
			s.type = 'text/javascript';
			s.src = url;
		}
		if (Sys.ie && Sys.ie < 9) {
			s.onreadystatechange = function(){
				if (this.readyState == 'loaded' || this.readyState == 'complete') {
					s.onreadystatechange = null;
					if (cb) {
						cb(true);
					}
				}
			}
		}else{
			s.onload = function(callBack){
				this.onload = this.onerror = null;
				if (cb) {
					cb(true);
				}
			};
			s.onerror = function(){
				this.onload = this.onerror = null;
				this.parentNode.removeChild(this);
				if (cb) {
					cb(false);
				}
			};
		}
		document.getElementsByTagName('head')[0].appendChild(s);
	};
	this.check = function(type,url){
		var cflag = false,eles = document.getElementsByTagName(type);
		if(eles){
			var l = eles.length;
			for(var i=0;i<l;i++){
				if(eles[i].src == url){
					cflag =true;
					break;
				}
			}
		}
		return cflag;
	};
}
w.dtloader = new moduleDefine();
})(window);