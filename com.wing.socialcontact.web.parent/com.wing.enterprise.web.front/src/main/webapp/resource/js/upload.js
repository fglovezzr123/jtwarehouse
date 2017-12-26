var upload_load=0;
function initUploadPicFrame(frameId,picId,formId){
	var frameSrc = _path+"/m/upload/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= formId;
	frameParam.inputId= picId;
	frameParam.inputOnChange = "parent.picChange";
	frameParam.jsonp = "parent.picUploadCallback";
	frameParam.frameId=frameId;
	frameSrc += "?"+parseParam(frameParam);
	$("#"+frameId).attr("src", frameSrc);
}
//公共方法,用来将对象转化为URL参数
function parseParam(param, key){
  	var paramStr="";
  	if(param instanceof String||param instanceof Number||param instanceof Boolean){
    	paramStr+="&"+key+"="+encodeURIComponent(param);
  	}else{
    	jQuery.each(param,function(i){
          	var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
          	paramStr+='&'+parseParam(this, k);
    	});
  	}
  	return paramStr.substr(1);
}
function picChange(inputFile,frameId,picId,formId){
	var fileSize = 0;
	var filename = "";
	if (navigator.userAgent.indexOf('MSIE') >= 0){
	}else{
		var files = inputFile.files;
		if (files.length>0){
			var targetFile = files[0];
			fileSize = targetFile.size;
			filename = targetFile.name;
		}
		if(files.length > 1){
			alert("请单张上传");
    		initUploadPicFrame();
		}
	}
	if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename))
    {
      alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
      initUploadPicFrame(frameId,picId,formId);
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrame(frameId,picId,formId);
	}else{
		//upload_load=layer.load(1, {shade: [0.1,'#393D49']});
		$("#"+formId, $("#"+frameId)[0].contentWindow.document).submit();
	}
}