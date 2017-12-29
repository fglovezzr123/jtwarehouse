<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 800px;margin: 0 auto;" >
	<form  action="banner/update.do"   method="post" id="news_form"  afterCallback="saveCallback" enctype="multipart/form-data" >
		  <input id="picPath" name="picPath" value="${b.picPath}"  type="hidden" />
		  <input id="createUserId" name="createUserId" value="${b.createUserId}"  type="hidden" />
		 <input id="createUserName" name="createUserName" value="${b.createUserName}"  type="hidden" />
		 <table class="table table-nobordered " style="margin-top: 25px;">
			  <tr>
			    	<th style="width: 80px">引导页封面：</th>
			    	<td >
                		<span id="nimgs"  ></span>
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">名称：</th>
			    	<td >
			     		<input type="text" name="title" value="${b.title}"  class="easyui-validatebox" readonly="readonly"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">排序：</th>
			    	<td >
			     		<input type="text" name="orderNum" value="${b.orderNum}" class="easyui-numberbox" readonly="readonly"   maxlength="20" />
			    	</td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${b.id }"/>
		  <input type="hidden" name="createTime" id ="createTime" value="<fmt:formatDate  value='${b.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="isguide" value="1" />
		  <input type="hidden" name="userRange" value="1" />
		   
	</form>
</div>

<script type="text/javascript">	
function save(){
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		alert("请上封面图片");
		return false;
	}
	$("#picPath").val(picUrl);
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
var saveCallback=function(obj,msg){
	if(msg.statusCode == 200){
		$("#${param.rel}_datagrid").datagrid('reload');
		$("#${param.rel}_update").dialog("close");
	}else{
		Msg.alert("提示",msg.message,"error");
	}
}
var picUrl = $("#picPath").val();
var oss = '${ossurl}';
if($.trim(picUrl).length > 0){
	$("#nimgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+picUrl+"' src='"+oss+picUrl+"' class='up_pic_img' style='width:240px; height:450px;'/>";
	$("#nimgs").append(imgHtml);
}
function doSelectPic() {
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
initUploadPicFrame();
function initUploadPicFrame(){
	var frameSrc = "<%=basePath%>news/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.moduleName= "banner";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChange";
	frameParam.jsonp = "parent.picUploadCallback";
	frameParam.ysStyle = "YS640320";
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
      initUploadPicFrame();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrame();
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
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+data.img_url+"' class='up_pic_img' style='width:240px; height:450px;'/>";
    		$("#nimgs").append(imgHtml);
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
	initUploadPicFrame();
}
</script>
