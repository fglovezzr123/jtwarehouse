<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 800px;margin: 0 auto;" >
	<form  action="news/updateview.do"   method="post"   id="news_form" afterCallback="saveCallback" enctype="multipart/form-data" enctype="multipart/form-data">
		 <input id="imagePath" name="imagePath" value="${n.imagePath}"  type="hidden" />
		 <input id="createUserId" name="createUserId" value="${n.createUserId}"  type="hidden" />
		 <input id="createUserName" name="createUserName" value="${n.createUserName}"  type="hidden" />
		 <table class="table table-nobordered " style="margin-top: 25px;">
			   <tr>
			    	<th style="width: 80px">专访人：</th>
			    	<td >
			     		<input type="text" name="viewUser" value="${n.viewUser}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,10]']"   maxlength="10" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">专访标题：</th>
			    	<td>
			     		<input type="text" name="newsTitle" value="${n.newsTitle}" class="easyui-validatebox" data-options="required:true" style="width:480px;" data-options="validType:['length[1,50]']" maxlength="50" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">专访人封面：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px。</strong>         	
                	</td>
			  </tr>
			    <tr>
			    	<th style="width: 80px">是否推荐：</th>
			    	<td >
                		<select name="isRecommend" >
						<option  value="1" <c:if test="${n.isRecommend==1}">selected</c:if>>推荐</option>
						<option  value="2"  <c:if test="${n.isRecommend==2}">selected</c:if>>不推荐</option>
					    </select>   	
                	</td>
			  </tr>
			  <tr>
				<th>
					权重：
				</th>
				<td>
					<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" value="${n.sort}"  class="easyui-numberbox" required="true"    maxlength="3" />
				</td>
			  </tr>
			    <tr>
			    	<th style="width: 80px">来源：</th>
			    	<td>
			     		<input type="text" name="source" value="${n.source}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']" maxlength="20" />
			    	</td>
			    	<td>&nbsp;</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">摘要：</th>
			    	<td>
			    	<input type="text" name="summary" id="summary" value="${n.summary}" class="easyui-validatebox" style="width:480px;"  required="true" data-options="validType:['length[1,50]']"   maxlength="50" />
			    	</td>
			    	<td>&nbsp;</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">微吼视频：</th>
			    	<td>
			    	<input class="living" readonly="readonly" type="text" id="mau_webinarSubject" name="webinarSubject" value="${n.webinarSubject}" style="width:200px;"/>
				    <input class="living" type="hidden" id="mau_webinarId" name="webinarId" value="${n.webinarId}"/>
				    <button class="btn btn-primary btn-small living" type="button" onclick="addVhallDialog()">选择直播</button>&nbsp;
			    	<button class="btn btn-primary btn-small living" type="button" onclick="clearall()">清空视频</button>&nbsp;
			    	</td>
			    	<td>&nbsp;</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">正文：</th>
			    	<td>
			    	<textarea name="content" style="width:320px;height:600px;" id="content">${n.content}</textarea>
			    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary" onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${n.id }"/>
		  <input type="hidden" name="createTime" id ="createTime" 
		   value="<fmt:formatDate  value='${n.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"/>
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="classRoot" value="${n.classRoot}"/>
	</form>
</div>

<script type="text/javascript">	
//options为编辑配置属性
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=news',
		width : '320px',
		height:'600px',
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
		items:['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
			'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			'superscript', '|', 'selectall', 'clearhtml','quickformat','|',
			'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image','flash', 'media', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about']
}; 
$(document.body).ready(function(){
	editor = KindEditor.create('#content',options);
})
function save(){
	editor.sync();//同步编辑器的数据到texterea
//	$("#createTime").val(new Date('${n.createTime}'));
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		alert("请上封面图片");
		return false;
	}
	$("#imagePath").val(picUrl);
	var flag = true;
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
var picUrl = $("#imagePath").val();
var oss = '${ossurl}';
if($.trim(picUrl).length > 0){
	$("#nimgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+picUrl+"' src='"+oss+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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
	frameParam.moduleName= "news";
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
	if(!/\.(jpg|jpeg|png|JPG|PNG)$/.test(filename))
    {
      alert("图片类型必须是jpeg,jpg,png中的一种");
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
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+data.img_url+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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
//选择直播视频
function addVhallDialog(){
	var params = {closed: false,cache: false,modal:true,width:700,height:400,collapsible:false,minimizable:false,maximizable:false};
	MUI.openDialog('直播选择','news/vhallindex.do',"vhallindexfornews",params)
}
function clearall(){
	$("#mau_webinarSubject").val("");
	$("#mau_webinarId").val("");
}
</script>
