function is_weixn(){  
    var ua = navigator.userAgent.toLowerCase();  
    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
        return true;  
    } else {  
        return false;  
    }  
}

function gotologin(){
	localStorage.setItem("lastPage", self.location.href);
	var mId = localStorage.getItem("mId");
	if(mId==null || mId==''){
		if(!is_weixn()){
			if(_path){
				self.location.href= _path + "/m/app/loginPage.do?last_url="+self.location.href+"";
			}else{
				self.location.href="/qfyfront/m/app/loginPage.do?last_url="+self.location.href+"";
			}
		}else{
			if(_path){
				self.location.href= _path + "/m/app/loginPage.do?last_url="+self.location.href+"";
			}else{
				self.location.href="/qfyfront/m/app/loginPage.do?last_url="+self.location.href+"";
			}
		}
	}else{
		qfy_login(mId);
	}
}
//TODO app调用js
function qfy_login(m){
	var url = _path+"/m/app/val_login.do";
	zdy_ajax({
		url: url,
		showLoading:false,
		data:{
			m:m
		},
		success: function(data,res){
			location.href = localStorage.getItem("lastPage");
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
}

/***全部替换***/
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}
