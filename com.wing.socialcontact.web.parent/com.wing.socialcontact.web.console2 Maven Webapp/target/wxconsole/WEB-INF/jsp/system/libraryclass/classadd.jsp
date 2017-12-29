<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：系统管理-- --添加分类
--%>

	<form  action="libraryclass/classadd.do"  id="libraryclass_form"  method="post"   onsubmit="return validateSubmitForm(this)">
	 <input id="imagePath" name="imagePath" value=""  type="hidden" />
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">分类名称：</th>
			    	<td>
			     		<input type="text" name="name" class="easyui-validatebox" required="true"  data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			 <tr>
			    	<th style="width: 80px">序号：</th>
			    	<td>
			     		<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" class="easyui-numberbox" required="true"   maxlength="3" />
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">分类图片：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic1()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>  
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸200*200px。</strong>       	
                	</td>
			  </tr>
			  <tr>
				<th>
					推荐：
				</th>
				<td>
					<select name="recommend" > 
					<option id="1" value="1">是</option>
					<option id="0" value="0">否</option>
					</select>
				</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">位置：</th>
			    	<td>
						<select name="position">
							<option value="3"   id ="position3">列表</option>
							<option value="2"   id ="position2">知识秘书</option>
							<option value="1"   id ="position1">智囊团</option>
						</select>
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
		  
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
		<input type="hidden" name="position" value="3"/>
	</form>
<script type="text/javascript">
<!--

function save(){
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		alert("请上传分类图片");
		return false;
	}
	$("#imagePath").val(picUrl);
	if(flag){
		if(!validateSubmitForm($('#libraryclass_form'))){
			return;
		}
	}
}


var picUrl = $("#imagePath").val();
if($.trim(picUrl).length > 0){
	$("#nimgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+picUrl+"' src='"+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
	$("#nimgs").append(imgHtml);
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
	var frameSrc = "<%=basePath%>news/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.moduleName= "library";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChange";
	frameParam.jsonp = "parent.picUploadCallback";
	frameParam.ysStyle = "YS200200";
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
	initUploadPicFrame1();
}
//-->
</script>
