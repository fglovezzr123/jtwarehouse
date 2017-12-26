<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>
<div style="height: 400px;width: 500px;float:left;">
		  <table class="table table-nobordered " id ="${param.rel}_datagrid" style="margin-top: 25px;">
		 	  <tr>
			    	<th style="width: 120px">标题：</th>
			    	<td>
			     		<input type="text" name="title1"  readonly="readonly"  value="${dto.title}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,30]']"   maxlength="30" />
			    	</td>
			  </tr>
		 	  <tr>
			    	<th style="width: 120px">图片：</th>
			    	<td >
			    		<img alt="" id="imagepath" style="width:30px;height:30px">      	
                	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">内容：</th>
			    	<td>
					<textarea id="contents" name="contents" readonly="readonly" style="width:320px;height:100px;">${dto.content }</textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">链接：</th>
			    	<td>
			     		<input type="text" name="link1" value="${dto.link}" readonly="readonly" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="showupdate()">编辑</button>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				 </td>
			  </tr>
		  </table>
</div>
<div style="height: 400px; width: 500px;float: left;display:none;" id="updatediv">
<form  action="qfy/shareconfig/update.do"  id="shareconfig_form"   method="post"   onsubmit="return validateSubmitForm(this)">
	    <input id="imagePath" name="imagePath" value="${dto.imagePath}"  type="hidden" />
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 	  <tr>
			    	<th style="width: 120px">标题：</th>
			    	<td>
			     		<input type="text" name="title"  value="${dto.title}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,30]']"   maxlength="30" />
			    	</td>
			  </tr>
		 	  <tr>
			    	<th style="width: 120px">图片：</th>
			    	<td >
			    		<span id="updateDetailBannerImgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic1()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>  
<!-- 	                    <strong style="color:#F00">尺寸*px。</strong>       	 -->
                	</td>
			  </tr>
			  
			   <tr>
			    	<th style="width: 80px">内容：</th>
			    	<td>
					<textarea id="content" name="content" style="width:320px;height:100px;">${dto.content }</textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 120px">链接：</th>
			    	<td>
			     		<input type="text" name="link" value="${dto.link}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"   onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				 </td>
			  </tr>
		  
		  </table>
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="qfy/shareconfig/load.do?shareconfig_load" />
	  	<input type="hidden" name="id" value="${dto.id}"/>
	</form>
</div>
<script type="text/javascript">
$(function(){
	$("#imagepath").attr("src",_oss_url+"${dto.imagePath}");
});


function update(){
		MUI.openDialog('编辑分享配置','qfy/shareconfig/updatePage.do?rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_update',{width:800,height:600});
}

function showupdate(){
	$("#updatediv").show();
}
function save(){
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		Msg.alert("提示","请上传图片！","warning",null);
		return false;
	}
	$("#imagePath").val(picUrl);
	if(flag){
		$("#shareconfig_form").submit();
		$("#updatediv").hide();
	}
}

/* 
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640',
		width : '320px',
		height:'400px',
		resizeType : 1,
		allowImageUpload : true,
		allowFlashUpload : false,
		allowMediaUpload : false,
		allowFileManager : false,
		syncType:"form",
		afterCreate : function() {
							var self = this;
							self.sync();
						},
		afterChange : function() {
							var self = this;
							self.sync();
						},
		afterBlur : function() {
							var self = this;
							self.sync();
						},
		items:['undo', 'redo', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent',  '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough',  '/', 'image', 
		        'table', 'hr']
}; 
$(document.body).ready(function(){
	editor = KindEditor.create('#content',options);
});
 */


var picUrl = $("#imagePath").val();
if($.trim(picUrl).length > 0){
	$("#updateDetailBannerImgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+picUrl+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:30px; height:30px;'/>";
	$("#updateDetailBannerImgs").append(imgHtml);
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
	frameParam.picRepository = "qfyQuickDetailBanner";
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
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:30px; height:30px;'/>";
    		$("#updateDetailBannerImgs").append(imgHtml);
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
