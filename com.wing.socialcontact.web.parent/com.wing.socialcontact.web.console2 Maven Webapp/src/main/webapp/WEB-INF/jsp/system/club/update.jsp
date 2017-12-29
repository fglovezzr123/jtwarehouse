<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 资讯分类管理--添加分类
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="club/update.do"  method="post" id="club_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <input id="imgpath" name="imgpath" value="${dto.imgpath }"  type="hidden" />
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">会所名称：</th>
			    	<td >
			     		<input type="text" name="title" value="${dto.title }" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			<tr>
					<th>分类：</th>
					<td>
						<a href="clubclass/lookUpPage.do?type=1"  lookupGroup="clubclass" title="分类查询">
							<input type="text" readonly="readonly"  toName="clubclass.name"  class="easyui-validatebox" required="true" value='<my:outClubClassName id="${dto.classId }"/>'/>
						</a>
						<input type="hidden" id="classId" name="classId"  toName="clubclass.id"  value="${param.classId }"/>
					</td>
			</tr>
			  <tr>
			    	<th style="width: 80px">会所地址：</th>
			    	<td >
			     		<input type="text" name="address"   value="${dto.address }"  class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">会所电话：</th>
			    	<td >
			     		<input type="text" name="phone"  value="${dto.phone }" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">会所介绍：</th>
			    	<td>
					<textarea id="content" name="content" style="width:320px;height:600px;">${dto.content }</textarea>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">会所图片：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px。</strong>         	
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
		  
		  <input type="hidden" name="id" value="${dto.id }"/>
		  <input type="hidden" name="deleted" value="${dto.deleted }" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
		  <input type="hidden" name="createUserId" value="${dto.createUserId}"/>
	</form>
	
	
</div>

<script type="text/javascript">	

var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=club',
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
	$("#classId").val("${dto.classId}");
})

function save(){
	editor.sync();//同步编辑器的数据到texterea
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		alert("请上封面图片");
		return false;
	}
	$("#imgpath").val(picUrl);
	if(flag){
		if(!validateSubmitForm($('#club_form'))){
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
var picUrl = $("#imgpath").val();
var oss = '${ossurl}';
if($.trim(picUrl).length > 0){
	$("#nimgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+picUrl+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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
	frameParam.moduleName= "club";
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
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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
