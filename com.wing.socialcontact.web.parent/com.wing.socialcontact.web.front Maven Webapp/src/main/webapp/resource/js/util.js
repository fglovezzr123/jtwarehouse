jQuery.prototype.serializeObject=function(){  
    var obj=new Object();  
    $.each(this.serializeArray(),function(index,param){  
        if(!(param.name in obj)){  
            obj[param.name]=param.value;  
        }  
    });  
    return obj;  
};  

//文件上传验证
/*
function fileChange(target) {   
	    var fileSize = 0;   
	    
	    if (isIE && !target.files) {    
		      var filePath = target.value;   
		      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		      
			  if(!fileSystem.FileExists(filePath)){
			     alert("附件不存在，请重新输入！");
				 var file=document.getElementById(id); 
		       	 file.outerHTML=file.outerHTML;
		       	 return;
			  }
		      var file = fileSystem.GetFile (filePath);
		      fileSize = file.Size;   //ie安全问题
    	} else {   
	     	  fileSize = target.files[0].size; 
	    }  
	   
        var size = fileSize / 1024;   
        
        if(size>100000){ 
       		 alert("附件大小不能大于100M！"); 
       		 //var file=document.getElementById(id); 
       		// file.outerHTML=file.outerHTML
        }  
        if(size<=0){
        	alert("附件大小不能为0M！"); 
        	//var file=document.getElementById(id); 
       		// file.outerHTML=file.outerHTML
        } 
} 
*/


//获取页面传值 ?传值的参数
function Utilrequest(paras){    
		var url = location.href;     
		var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");     
		var paraObj = {}     
		for (i=0; j=paraString[i]; i++){     
			paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);     
		}     
		var returnValue = paraObj[paras.toLowerCase()];     
		if(typeof(returnValue)=="undefined"){     
			return "";     
		}else{     
			return returnValue;    
		}  
}
//时钟
function Clock() {
	var date = new Date();
	this.year = date.getFullYear();
	this.month = date.getMonth() + 1;
	this.date = date.getDate();
	this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
	this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	this.toString = function() {
		return this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day; 
	};
	
	this.toSimpleDate = function() {
		return this.year + "-" + this.month + "-" + this.date;
	};
	
	this.toDetailDate = function() {
		return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
	};
	
	this.display = function(ele) {
		var clock = new Clock();
		ele.innerHTML = clock.toString();
		window.setTimeout(function() {clock.display(ele);}, 1000);
	};
}
//时间人性格式化
function changetime(dateTimeStamp){
	var minute = 1000 * 60;
	var hour = minute * 60;
	var day = hour * 24;	 	
	var now = new Date();
	var old;
	var diffValue;
	if(!+[1,]){
		//"ie浏览器";
		dateTimeStampt=dateTimeStamp.replace("T"," ");
		
		old= new Date(Date.parse(dateTimeStamp.replace(/-/g,   "/")));
		diffValue = now.getTime() - (old.getTime()-3600000);
	}else{
		old=new Date(dateTimeStamp);
		diffValue = now.getTime() - old.getTime();
	}
	var dayC =diffValue/day;
	var hourC =diffValue/hour;
	var minC =diffValue/minute;
	 	
	 if(dayC>=3){
		  
		 if(now.getFullYear()==old.getFullYear()){
			 	time= dateTimeStamp.replace("T"," ");
				return time.substring(time.indexOf("-")+1,time.length);
			
			 
		 }else{
			 return dateTimeStamp.replace("T"," ");
		 }
	 }else if(dayC>=2){
		 return "前天 "+dateTimeStamp.split("T")[1];
	 }else if(dayC>=1){
		 return "昨天 "+dateTimeStamp.split("T")[1];
	 }else if(hourC>=1){
	 	return parseInt(hourC) +"小时前";
	 }else if(minC>=1){
	 	return parseInt(minC) +"分钟前";
	 }else{
	 	return Math.floor(diffValue/1000)+"秒前";
	 }	 
}


//重载  setTimeout 方法，提供参数传递, 定时提醒需要修改
/*var __sto = setTimeout;
window.setTimeout = function(callback,timeout,param){
	var args = Array.prototype.slice.call(arguments,2);
	var _cb = function(){
		callback.apply(null,args);
	}
	__sto(_cb,timeout);
}*/

/**
 * 自定义单选
 * @param input	单选元素this
 */
function myRadio(input){
	var $in=$(input);
	var $form=$in.closest("form");
	$("input[radioType='"+$in.attr("radioType")+"']",$form).prop("checked",false);
	$in.prop("checked",true);
}

/**
 * 对form表单需要加密的内容进行加密
 * 
 * @param {} $form form jquery对象
 */
function encryptePassword($form){
	var key = RSAUtils.getKeyPair($("#exponent",$form).val(), '', $("#modulus",$form).val());  
	
	$("[encryptTo]",$form).each(function(){
		var enName=$(this).attr("encryptTo");
		var password = RSAUtils.encryptedString(key, encodeURIComponent($(this).val())); 
		if($("input[name='"+enName+"']",$form).length>0){
			$("input[name='"+enName+"']",$form).val(password);
		}else{
			$(this).after('<input type="hidden" name="'+enName+'" value="'+password+'" />');
		}
		
		$(this).removeAttr("name");
	});
	return true;
}

/**
 * 共用提示框
 */
var tiper = (function(){
	var wrapEle = $('<div class="widget-tiper"><div class="widget-tiper-inner"><div class="tiper-head"></div><div class="tiper-body"></div><div class="tiper-foot"></div></div></div>');
	var maskEle = $('<div class="widget-tiper-mask"></div>');
	var init = false;
	return function(title, content, button, callback){
		init || wrapEle.appendTo($('body')) && maskEle.appendTo($('body'));
		wrapEle.addClass('widget-tiper-active');
		maskEle.addClass('widget-tiper-active');
		wrapEle.find('.tiper-head').html(title);
		wrapEle.find('.tiper-body').html(content);
		wrapEle.find('.tiper-foot').empty();
		if($.isArray(button)){
			for(var i = 0; i < button.length; i++){
				$('<span class="tiper-button '+button[i].style+'">'+button[i].text+'</span>').click(function(){
					wrapEle.removeClass('widget-tiper-active');
					maskEle.removeClass('widget-tiper-active');
					$.isFunction(callback) && callback($(this).index());
				}).appendTo(wrapEle.find('.tiper-foot'));
			}
		}
		wrapEle.css({
			'left' : ($(window).width() - wrapEle.width())/2,
			'top' : ($(window).height() - wrapEle.height())/3
		});
		init = true;
	}
})();
var loadingElement = $('<div class="scroll-loading" style="display: none;"><span id="loadingElementContent"></span></div>');
loadingElement.appendTo($('body'));
window.hideloading = function(){
	loadingElement.hide();
}
window.showloading = function(time,message){
	if(message){
		$("#loadingElementContent").html(message);
	}else{
		$("#loadingElementContent").html("数据加载中...");
	}
	loadingElement.show();
	if(time>0){
		setTimeout("hideloading()",time);
	}
}

window.alert = function(message, callback){
	if(typeof(message) == 'string'){
		tiper('提示', message, [{style: 'alert_t', text: '确定'}], callback);
	}else{
		if(message&&message.title){
			tiper('提示', message.title, [{style: 'alert_t', text: '确定'}], message.callback);
		}else{
			tiper('提示', JSON.stringify(message), [{style: 'alert_t', text: '确定'}], callback);
		}
	}
}
window.confirm = function(message, callback){
	tiper('确定', message, [{style: 'cancal_t', text: '取消'},{style: 'define_t', text: '确定'}], callback);
}
/**
 * 带回调的提示框
 */
var alert_back=function(message,callback){
	alert({
		title:message,
		callback:callback
	});
}
/***全部替换11***/
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}
var isEmpty=function(mixed_var){
	 var key;  
    if (mixed_var == "" || mixed_var == null ||mixed_var == undefined || typeof mixed_var == 'undefined') {  
        return true;  
    }  
   
    if (typeof mixed_var == 'object') {  
        for (key in mixed_var) {  
            return false;  
        }  
        return true;  
    }  
   
    return false; 
}

var isMobile=function(phone){
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/; 
	if(myreg.test(phone)) 
	{ 
	    return false; 
	} 
	return true;
}
function getParam(){
	var url = window.location.search.substr(1);
	// console.log(url);
	var obj = {};
	if(!url) return false; 

	var arr = url.split("&");
	// console.log(arr);
	for(var i = 0,len = arr.length; i<len; i++){
		var params = arr[i].split("=");
		// console.log(params);  
		obj[params[0]] = decodeURI(params[1]);
	}
	// console.log(obj);
	return obj;
	
}

//app专用开始
function initWebViewJavascriptBridge(callback) {
    if (window.WebViewJavascriptBridge) {
        callback(WebViewJavascriptBridge)
    } else {
        document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function() {
                callback(WebViewJavascriptBridge)
            },false);
    }
}

//JSBridge初始化：固定代码
initWebViewJavascriptBridge(function(bridge) {
    bridge.init();
    //声明Native 调用 JS 的方法
    bridge.registerHandler("functionInJs", function(data, responseCallback) {
        document.getElementById("show").innerHTML = (data);
        var responseData = "从JS传来的数据";
        responseCallback(responseData);
    });
})


function isInApp(){
	/*var ua = navigator.userAgent.toLowerCase();
	if(/(iphone|ipad|ipod|ios)/i.test(ua) || /(android)/i.test(ua)){
		return true;
	}
	return false;*/
	var params = getParam(),
    h5token=params.h5token;
	if(!isEmpty(h5token)){
		return true;	
	}
	return false;
}
//app专用结束

/*
 * 自定义方法
 * 封装ajax，添加加载中效果、统一错误处理、证书等 
 */
$.ajaxSettings_zdy = {
    type: 'POST',
    dataType:'json',
    timeout : 60000,
    showLoading:true,
    index:[]
}

var showerror=false;

function zdy_ajax(options){
	
	var params = getParam(),
	_h5token=params.h5token;
	
	var _options = jQuery.extend($.ajaxSettings_zdy, options);
    if(_options.showLoading == true){
    	//_index_zdy_layter = layer.load(0,{type:3,shade:[0.8,'#393D49'],scrollbar:false});
    	var _index = layer.load(1, {shade: [0.1,'#393D49']});
    	console.log(_options.index);
    	if(_options.index==undefined){
    		_options.index=[];
    	}
    	_options.index.push(_index);
    }
    if(!isEmpty(_h5token)){
	    if(!isEmpty(_options.data)){
	    	_options.data.h5token=_h5token;
	    }else{
	    	_options.data={"h5token":_h5token};
	    }
    }
	var _seccess = _options.success;
	var _error = _options.error;
	var _beforeSend = _options.beforeSend;
	var _complete = _options.complete;
	_options.beforeSend = function(){
		jQuery.isFunction(_beforeSend) && _beforeSend();
	}
	_options.error = function(XMLHttpRequest, textStatus, errorThrown){
			for(i=0;i<_options.index.length;i++){
				layer.close(_options.index[i]);
			}
	    
		if(textStatus == 'error' && XMLHttpRequest.status == 0){
			//alert("网络异常，请检查手机网络"+XMLHttpRequest.status);
			//layer.msg("网络异常，请检查手机网络");
			$(".page_loading").hide();
		}else if(textStatus == 'timeout'){
			//layer.msg("网络不给力，请点击屏幕重试");
		}
		jQuery.isFunction(_error) && _error(XMLHttpRequest, textStatus, errorThrown);
	}
	_options.success = function(data,textStatus, jqXHR){
	    if(_options.showLoading == true){
			for(i=0;i<_options.index.length;i++){
				layer.close(_options.index[i]);
			}
			_options.index=[];
	    }
		var contentType = jqXHR.getResponseHeader("Content-Type");
		if(contentType.indexOf("json")>-1){
			if(data.code == 0){
				jQuery.isFunction(_seccess) && _seccess(data.dataobj,data);
			}else if(data.code == 407){
				alert_back("注册超时，请重试",function(){
					self.location.href=_path+"/m/sys/index.do"; 
				});
			}else if(data.code == 600){
				confirm("您还未认证，请认证后继续此操作。</br>是否马上认证？",function(t){
					if(t == 1){
						self.location.href=_path+"/m/my/reconPage.do";
					}
				});
			}else if(data.code == 700){
				self.location.href=_path+"/m/sys/blackPage.do";
			}else{
				if(data.code == 302){
					if(isInApp()){
						window.location.href="/tj/token/invalid";
					}else{
						gotologin();
					}
				}else{
					alert(data.msg);
				}
			}
		}else{
			var timeout   = jqXHR.getResponseHeader("timeout");
			var exception = jqXHR.getResponseHeader("exception");
			if(1==timeout&&false){
				gotologin();
			}else if(1==exception){
				alert("服务器异常，请稍后重试");
			}else{
				jQuery.isFunction(_seccess) && _seccess(data,textStatus,jqXHR);
			}
		}
	}
	_options.complete = function(){
		jQuery.isFunction(_complete) && _complete();
	}
	if(navigator.onLine){
		showerror=false;
		jQuery.ajax(_options);
	}else{
		//layer.msg("网络异常，请检查手机网络");
		$(".page_loading").hide();
		if(!showerror){
			offlinedeal();
		}
		showerror=true;
	}
}


function isonline(){
	if(!navigator.onLine){
		offlinedeal();
	}
}

function offlinedeal(){
	layer.open({
		type : 1,
		//skin: 'layui-layer-lan',
		title: false,
		closeBtn: 0, //不显示关闭按钮
		fix : true,
		shadeClose : true,
		maxmin : false,
		area : [ '100%', '100%' ],
		content : '<div class="wrapper1"><div class="right-signal1">网络异常，请检查手机网络</div><div class="active_A name-btn1" onclick="reloadjsp()">刷新</div></div>',
		shift : 2,
		scrollbar : false,
		success : function(layero, index) {
		},
		end : function() {
			
		},
		cancel : function(cancel) {
		}
	});
}

function reloadjsp(){
	location.reload();
}

function getUrlParam1(url,name){
	if(url.match(new RegExp("(^|&|\\?)"+ name +"=([^&]*)(&|$)"))){
		return RegExp.$2.split('#')[0];
	}
}

function setSessionStorageValue(key,value){
	var smap = {"zhuanhuankey":value};
	var valstr = JSON.stringify(smap);
	sessionStorage.setItem(key,valstr);
}

function getSessionStorageValue(key){
	var objstr=sessionStorage.getItem(key);
	if(objstr){
		var obj = JSON.parse(objstr);
		return obj.zhuanhuankey;
	}else{
		return objstr;
	}
}

function setLocalStorageValue(key,value){
	var smap = {"zhuanhuankey_local":value};
	var valstr = JSON.stringify(smap);
	localStorage.setItem(key,valstr);
}

function getLocalStorageValue(key){
	var objstr=localStorage.getItem(key);
	if(objstr){
		var obj = JSON.parse(objstr);
		return obj.zhuanhuankey_local;
	}else{
		return objstr;
	}
}

function clearNoNum(obj){
	 obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	 obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
	 obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
	 obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	 obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

function clearNoNum2(obj){
	 obj.value = obj.value.replace(/[^\d]/g,""); //清除"数字"和"."以外的字符
}

function date_format(now,mask){
	var d = null;
	if(typeof(now) == 'date'){
		d = now;
	}else{
		d = new Date(now);
	}
    var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++)
        {
            zeros += '0';
        }
        return zeros + value;
    };
 
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
    {
        switch ($0)
        {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
            // Return quoted strings with the surrounding quotes removed
            default: return $0.substr(1, $0.length - 2);
        }
    });
};

var layer_msg=function(title){
	if(layer){
		layer.msg(title);
	}else{
		alert(title);
	}
}

var check_mobile=function(mobile){
	if(!(/^1[34578]\d{9}$/.test(mobile))){
		return false;
	}
	return true;
}

function clearEmojiCharacter(obj) {  
	obj.value = obj.value.replace(/[^\u4e00-\u9fa5|\u0000-\u00ff|\u3002|\uFF1F|\uFF01|\uff0c|\u3001|\uff1b|\uff1a|\u3008-\u300f|\u2018|\u2019|\u201c|\u201d|\uff08|\uff09|\u2014|\u2026|\u2013|\uff0e]/g,"");
}  

function number_format(number, decimals, dec_point, thousands_sep) {
    /*
    * 参数说明：
    * number：要格式化的数字
    * decimals：保留几位小数
    * dec_point：小数点符号
    * thousands_sep：千分位符号
    * */
    number = (number + '').replace(/[^0-9+-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.ceil(n * k) / k;
        };
 
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    var re = /(-?\d+)(\d{3})/;
    while (re.test(s[0])) {
        s[0] = s[0].replace(re, "$1" + sep + "$2");
    }
 
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

//数组去重
Array.prototype.unique3 = function(){
 var res = [];
 var json = {};
 for(var i = 0; i < this.length; i++){
  if(!json[this[i]]){
   res.push(this[i]);
   json[this[i]] = 1;
  }
}
return res;
}

function clearYEmoil(value){
	var value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':\";\',\\.\>\\</?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')
    return value
}
//禁止输入表情
function clearYEmoil1(value){
	var value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':\";\',\\.\>\\</?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')
    return value
}
function firstCharacter(substring) {
	substring =substring.replace(/&amp;lt&#x3b;/, "&lt;");
	/* substring = substring.replace(/&gt;/g, '>'); */
	console.log(1)
    return substring;
}


