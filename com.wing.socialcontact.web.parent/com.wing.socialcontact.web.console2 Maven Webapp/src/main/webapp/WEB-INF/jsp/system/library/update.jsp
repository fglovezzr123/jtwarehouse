<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ taglib uri="/WEB-INF/tag/myTag.tld" prefix="my" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>    
<%--
	模块：系统管理-- 资讯分类管理--添加分类
--%>

<div style="width: 800px;margin: 0 auto;" >
	<form  action="library/update.do"  method="post" id="library_form" afterCallback="saveCallback" enctype="multipart/form-data" >
		  <input id="imgpath" name="imgpath" value="${dto.imgpath }"  type="hidden" />
		  <input id="audioFile" name="audioFile" value="${dto.audioFile }"  type="hidden" />
		  <input id="rewardUser" name="rewardUser" value="${wxUser.id }"  type="hidden" />
		  
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">文章标题：</th>
			    	<td >
			     		<input type="text" name="title" class="easyui-validatebox" required="true"   value = '${dto.title }' data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			<tr>
					<th>分类：</th>
					<td>
						<a href="libraryclass/lookUpPage.do?type=1"  lookupGroup="libraryclass" title="分类查询">
							<input type="text" readonly="readonly" id="classname" toName="libraryclass.name"  class="easyui-validatebox" required="true" value='<my:outLibraryClassName id="${dto.classId }"/>'/>
						</a>
						<strong style="color:#F00">请选择二级分类，否则无法保存文章。</strong>
						<input type="hidden" id="classId" name="classId"  toName="libraryclass.id"  value="${param.classId }"/>
						<input type="hidden" id="tag" name="tag"  />
					</td>
			</tr>
			<tr>
				<th>
					权重：
				</th>
				<td>
					<input type="text" name="sort"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" value="${dto.sort}"  class="easyui-numberbox" required="true"    maxlength="3" />
				</td>
			</tr>
			<tr>
				<th>
					阅读人数：
				</th>
				<td>
					<input type="text" name="readtimes" value="${dto.readtimes}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  class="easyui-numberbox" required="true"  maxlength="8" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">发布时间：</th>
				<td>
					<c:choose>
						<c:when test="${published eq 0 }">
							<input type="text" name="createTime" id="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />"  maxlength="50" class="easyui-validatebox" required="true"    onFocus="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</c:when>
						<c:otherwise>
							<input type="text" name="createTime" id="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />"  maxlength="50" class="easyui-validatebox" required="true"  readonly="readonly"   />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
			    	<th style="width: 80px">推荐：</th>
			    	<td>
						<select name="recommend" id="recommend">
							<option value="1"   id ="1">是</option>
							<option value="0"  id="0">否</option>
						</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">文章内容：</th>
			    	<td>
					<textarea id="content" name="content" style="width:320px;height:600px;">${dto.content }</textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">文章图片：</th>
			    	<td >
                		<span id="nimgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPic()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
	                    <strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px。</strong>         	
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">微吼视频：</th>
			    	<td>
			     	<input class="living" readonly="readonly" type="text" id="mau_webinarSubject" name="webinarSubject" value="${dto.webinarSubject}" style="width:200px;"/>
				    <input class="living" type="hidden" id="mau_webinarId" name="webinarId" value="${dto.webinarId}"/>
				    <button class="btn btn-primary btn-small living" type="button" onclick="addVhallDialog()">选择直播</button>&nbsp;
			    	<button class="btn btn-primary btn-small living" type="button" onclick="clearall()">清空视频</button>&nbsp;
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">附件：</th>
			    	<td>
				     		<input type="file" name="file1"  id="file1"><br/><strong style="color:#F00">若已有附件，上传后旧附件将被覆盖,建议文件大小不超过5M。</strong>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">现有附件：</th>
			    	<td id="accessorys">
			    		<c:choose>
						<c:when test="${accessory==null}">
				     		无
						</c:when>
						<c:when test="${accessory.id==null or accessory.id eq ''}">
				     		无
						</c:when>
						<c:otherwise>
							<a href=_oss_url+"${accessory.path }">${accessory.newName }</a>  <button onclick="delaccessory()">删除附件</button>
						</c:otherwise>
					</c:choose>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 100px">上传音频文件：</th>
			    	<td>
			    	<div id= "deladbutton">
			    	<c:if test="${dto.audioFile!=null && dto.audioFile!=''}">
			    	<video  controls="controls" width="320" height="50" id="video">
   						<source src="http://tianjiu.oss-cn-beijing.aliyuncs.com/${dto.audioFile}" />
 					</video>
			    	     <input  onclick="delaudio()"  type="button" value="删除音频文件" />
			    	    </c:if>
			    	     </div>
			    		<input type="file" name="file2"  onchange="clickFileName(this)" ><strong style="color:#F00">请上传.mp3格式音频文件,建议文件大小不超过5M。</strong><br/>
			    	</td>
			  </tr>
			  <tr>
				<th>
					  文章点赞功能：
				</th>
				<td>
					<select name="thumbup" id="thumbup"> 
					<option id="1" value="1" <c:if test="${dto.thumbup==1}">selected</c:if>>开启</option>
					<option id="2" value="2" <c:if test="${dto.thumbup==2}">selected</c:if>>关闭</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					  文章评论功能：
				</th>
				<td>
					<select name="comment" id="comment"> 
					<option id="1" value="1" <c:if test="${dto.comment==1}">selected</c:if>>开启</option>
					<option id="2" value="2" <c:if test="${dto.comment==2}">selected</c:if>>关闭</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					  文章打赏功能：
				</th>
				<td>
					<select name="reward" id="reward"> 
					<option id="1" value="1" <c:if test="${dto.reward==1}">selected</c:if>>开启</option>
					<option id="2" value="2" <c:if test="${dto.reward==2}">selected</c:if>>关闭</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					  打赏默认账户：
				</th>
				<td>
					<input type="text"  class="easyui-validatebox" value="${wxUser.username }"  readonly="true"/>
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
		  <input type="hidden" name="fileId" id="fileId" value="${dto.fileId }" />
		  <%-- <input type="hidden" name="readtimes" value="${dto.readtimes }"/> --%>
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		  <input type="hidden" name="createUserId" value="${dto.createUserId}"/> 
		  <input type="hidden" name="updateTime" value="<fmt:formatDate  value="${dto.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
	</form>
	
	
</div>

<script type="text/javascript">	
//options为编辑配置属性
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=library',
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
	$("#"+${dto.recommend}).attr("selected","selected");
	var ss = "${dto.tag}".split(",");
	for(var item in ss) {
		$("#"+ss[item]).attr("checked","checked");
		}
})
function save(){
	var file = document.getElementById("file1");
	if(file.value==""){
     }else{
	  	if(file.files[0].size>5120000){
		alert("附件大小不能超过5M");
		return;
		}
     }
	editor.sync();//同步编辑器的数据到texterea
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
	var isrec = $("#recommend").val();
	if($.trim(picUrl).length <= 0){
		alert("请上传封面图片");
		return false;
	}
	$("#imgpath").val(picUrl);
	var tag = $("#classname").val();
	$("#tag").val(tag);
	if(flag){
		if(!validateSubmitForm($('#library_form'))){
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
	frameParam.moduleName= "library";
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

function delaccessory(){
	var id = "${accessory.id}";
	$.ajax({
		url : "library/delaccessory.do",
		dataType : "json",
		data : {
			"id" :id 
		},
		async : false,
		success : function(data){
			$("#accessorys").empty();
			$("#accessorys").append("无");
			$("#fileId").val()=="";
		}
	});
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
//上传音频
function clickFileName(upload_field) {
	var filename = upload_field.value;
	var newFileName = filename.split('.');
	newFileName = newFileName[newFileName.length - 1];
	if(!/\.(mp3)$/.test(filename)) {
	    alert("上传的文件格式不支持,仅支持.mp3");
	    upload_field.value=null;
	    return false;
	} 
	var filesize = upload_field.files[0].size;
	if (filesize > 1 * 1024 * 1024*5) {
		alert("上传的文件大小不能超过5M。");
		upload_field.value = null;
		return false;
	}
}

function delaudio(){
	$("#audioFile").val("");
//	$("#video").remove();
	$("#deladbutton").remove();
}
</script>

