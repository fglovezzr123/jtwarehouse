function is_weixn(){  
    var ua = navigator.userAgent.toLowerCase();  
    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
        return true;  
    } else {  
        return false;  
    }  
}
function gotologin(t){
	if(!is_weixn()){//开发调试时放开，发布到服务器上禁用
		if(self.location.href.indexOf("last_url")<=0){
			if(_path){
				self.location.href= _path + "/m/sys/loginPage.do?last_url="+self.location.href+"";
			}else{
				self.location.href="/wxfront/m/sys/loginPage.do?last_url="+self.location.href+"";
			}
		}
	}else{
		var appid = null;
		//var appid = localStorage.appid;
		if(null == appid || appid.length == 0){
			$.ajax({
				url: _path + '/m/sys/getWxConfig.do',
				type: 'post',
				dataType: 'json',
				success: function(output){
					if(output.code == 0){
						localStorage.appid=output.dataobj.wx_appid;
						logincc(output.dataobj.wx_appid);
					}else{
						alert(output.description);
					}
				},
				error:function(m){
					alert(m);
				}
			});
		}else{
			logincc(appid);
		}
	}
}

function logincc(appid){
	var url = home_path+_path+"/m/sys/mlogin.do";
	var rurl = self.location.href;
	rurl=rurl.replaceAll(home_path+_path,"").replaceAll("&", "@@2@@");
	rurl=encodeURIComponent(rurl);
	if(window.parent==window){
		self.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid
			+"&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state="+rurl+"#wechat_redirect";
	}else{
		rurl = parent.window.location.href;
		rurl=rurl.replaceAll(home_path+_path,"").replaceAll("&", "@@2@@");
		rurl=encodeURIComponent(rurl);
		parent.window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid
			+"&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state="+rurl+"#wechat_redirect";
	}
}

/***全部替换***/
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}
