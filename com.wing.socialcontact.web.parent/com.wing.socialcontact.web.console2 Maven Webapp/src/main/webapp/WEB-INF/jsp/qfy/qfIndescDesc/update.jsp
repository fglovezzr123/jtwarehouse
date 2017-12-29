<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="entryDescConfig/update.do"  id="entryDescConfig_form"  method="post"   onsubmit="return validateSubmitForm(this)">
	    <input id="imgPath" name="imgPath" value=""  type="hidden" />
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
		 	  <tr>
			    	<th style="width: 120px">图片：</th>
			    	<td >
                		<span id="qfIndexDescImgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic1()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>  
	                    <strong style="color:#F00">尺寸750*195px</strong>       	
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">链接：</th>
			    	<td>
			     		<input type="text" name="link" class="easyui-validatebox" value="${dto.link}" required="true"  data-options="validType:['length[1,255]']"   maxlength="255" />
			     		 <strong style="color:#F00">http://开头</strong>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		  
		  </table>
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
		  <input type="hidden" name="createUserId" value="${dto.createUserId}"/>
		  <input type="hidden" name="id" value="${dto.id}"/>
		  <input type="hidden" name="status" value="${dto.status}"/>
		  <input type="hidden" name="type" value="${dto.type}"/>
	</form>
<script type="text/javascript">
function save(){
	var qfyIndexDescFlag = true;
	var qfyIndexDescPicUrl = "";
	$('.up_pic_img').each(function(){
		qfyIndexDescPicUrl = $(this).attr("srcpath");
	});
	if($.trim(qfyIndexDescPicUrl).length <= 0){
		Msg.alert("提示","请上传标签图片！","warning",null);
		return false;
	}else{
		$("#imgPath").val(qfyIndexDescPicUrl);
	}
	if(qfyIndexDescFlag){
		if(!validateSubmitForm($('#entryDescConfig_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_add").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}

var qfyIndexDescPicUrl = '${dto.imgPath}';
if($.trim(qfyIndexDescPicUrl).length > 0){
	$("#qfIndexDescImgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+qfyIndexDescPicUrl+"' src='"+_oss_url+qfyIndexDescPicUrl+"' class='up_pic_img' style='width:375px; height:180px;'/>";
	$("#qfIndexDescImgs").append(imgHtml);
}
function doSelectPic1() {
	
	var imgSize = 0;
	$('.up_pic_img').each(function(){
		imgSize = imgSize+1;
	});
	if(imgSize <= 0 ){
		$("#pic", $("#uploadPicFrame")[0].contentWindow.document).click();
	}else{
		alert("仅能上传一张图片");
	}
	return false;
}
initUploadPicFrame1();
function initUploadPicFrame1(){
	var frameSrc = "<%=basePath %>qfyBaseUploadPic/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChange";
	frameParam.jsonp = "parent.picUploadCallback";
	frameParam.picRepository = "entryDescConfig";
	frameSrc += "?"+parseParam(frameParam);
	$("#uploadPicFrame").attr("src", frameSrc);
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
function picChange(inputFile){
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
      initUploadPicFrame1();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrame1();
	}else{
		$("#upload", $("#uploadPicFrame")[0].contentWindow.document).submit();
	}
}
function delPic(data){
	$(data).remove();
}
//上传完成后回调的方法
function picUploadCallback(data){
	if (data.returnCode == "1"){
		var qfyIndexDescPicUrl = data.picPath;
		if(qfyIndexDescPicUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+qfyIndexDescPicUrl+"' class='up_pic_img' style='width:375px; height:180px;'/>";
    		$("#qfIndexDescImgs").append(imgHtml);
		}else{
			alert("上传失败,请稍后再试");
		}
	}else{
		if(data.msg != ""){
			alert(data.msg);
		}else{
			alert("上传失败,请稍后再试");
		}
	}
	initUploadPicFrame1();
}
</script>
