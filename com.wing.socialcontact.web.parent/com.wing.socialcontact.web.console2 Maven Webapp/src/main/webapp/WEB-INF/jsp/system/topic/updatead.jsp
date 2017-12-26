<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理
--%>
<div style="width: 600px;margin: 0 auto;" >
	<form  action="topic/updateAd.do"   method="post" id="news_form" afterCallback="saveCallback">
		 <input id="createTime" name="createTime" value="<fmt:formatDate  value='${t.createTime}' pattern='yyyy-MM-dd HH:mm:ss' />"  type="hidden" />
		 <input id="createUserName" name="createUserName" value="${t.createUserName}"  type="hidden" />
		 <input id="createUserId" name="createUserId" value="${t.createUserId}"  type="hidden" />
		 <input id="redCount" name="redCount" value="${t.redCount}"  type="hidden" />
		 <input id="blueCount" name="blueCount" value="${t.blueCount}"  type="hidden" />
		 <input id="imagePath" name="imagePath" value="${t.imagePath}"  type="hidden" />
		 <input id="isAd" name="isAd" value="${t.isAd}"  type="hidden" />
		 
		 <table class="table table-nobordered " style="margin-top: 25px;">
			   <tr>
			    	<th style="width: 100px">发布人：</th>
			    	<td >
			     		<div id="fbr"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">创建时间：</th>
			    	<td >
			     		<div id="cjsj"></div>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">标题：</th>
			    	<td >
			     		<input type="text" name="topicDesc" id="topicDesc" value="${t.topicDesc}" class="easyui-validatebox" required="true"  data-options="validType:['length[1,100]']"   maxlength="100" style="width: 400px;"/>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">标题链接：</th>
			    	<td>
			     		<input type="text" name="url"  id="url" value="${t.url}" class="easyui-validatebox"  data-options="validType:['length[1,100]']"   maxlength="100" style="width:350px;"/>
			    	    <strong style="color:#F00">http:// 开头</strong> 
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 100px">话题图片：</th>
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
						  <button type="button" class="btn btn-primary" onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <button type="button"  onclick="clear1()">清空</button>
					</div>
				 </td>
			  </tr>
		</table>	 
		  
		  <input type="hidden" name="id" value="${t.id }"/>
		  <input id="isRecommend" name="isRecommend" value="${t.isRecommend}"  type="hidden" />
		  <input id="isShow" name="isShow" value="${t.isShow}"  type="hidden" />
		  <input id="allowComment" name="allowComment" value="${t.allowComment}"  type="hidden" />
		  <input id="status" name="status" value="${t.status}"  type="hidden" />
		  <input id="sort" name="sort" value="${t.sort}"  type="hidden" />
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		   
	</form>
</div>

<script type="text/javascript">	
$(function() {
//	var cj = new Date($("#createTime").val()).format("yyyy-MM-dd HH:mm:ss");
	var createUserName = $("#createUserName").val();
	if(createUserName!=null&&createUserName!=""){
		$("#fbr").html(createUserName);
	}else{
		$("#fbr").html("管理员");
	}
	$("#cjsj").html($("#createTime").val());
});
function save(){
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	if($.trim(picUrl).length <= 0){
		alert("请上传图片");
		return false;
	}
	$("#imagePath").val(picUrl);
	var jumpUrl = $.trim($("#url").val());
	if(jumpUrl!=''){
		if(jumpUrl.indexOf("http://")!=0){
			alert("幻灯片链接以 http:// 开头");
			return false;
		}
	}
	if(flag){
		if(!validateSubmitForm($('#news_form'))){
			return;
		}
	}
}
function clear1(){
	$("#topicDesc").val('${t.topicDesc}');
	$("#redPoint").val('${t.redPoint}');
	$("#bluePoint").val('${t.bluePoint}');
	$("#isShow option[value=" + '${t.isShow}' + "]").attr("selected", true); 
	$("#isRecommend option[value=" + '${t.isRecommend}' + "]").attr("selected", true); 
	$("#allowComment option[value=" + '${t.allowComment}' + "]").attr("selected", true); 
	$("#status option[value=" + '${t.status}' + "]").attr("selected", true); 
	
	$("#remark").val('${t.remark}');
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
	frameParam.moduleName= "topic";
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
</script>
