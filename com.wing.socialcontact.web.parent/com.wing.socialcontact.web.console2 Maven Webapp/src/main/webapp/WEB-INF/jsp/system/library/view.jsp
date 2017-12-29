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
		  <table class="table table-nobordered " style="margin-top: 25px;">
		 
			  <tr>
			    	<th style="width: 80px">文章标题：</th>
			    	<td >
			     		<input type="text" name="title"   readonly="readonly" class="easyui-validatebox" required="true"   value='${dto.title }' data-options="validType:['length[1,20]']"   maxlength="20" />
			    	</td>
			  </tr>
			<tr>
					<th>分类：</th>
					<td>
						<a href="libraryclass/lookUpPage.do?type=1"  lookupGroup="libraryclass" title="分类查询">
							<input type="text" readonly="readonly"    toName="libraryclass.name"  class="easyui-validatebox" required="true" value='<my:outLibraryClassName id="${dto.classId }"/>'/>
						</a>
						<input type="hidden" name="classId"  toName="libraryclass.id"  value="${param.classId }"/>
					</td>
			</tr>
			<tr>
				<th>
					权重：
				</th>
				<td>
					<input type="text" name="sort"  readonly="readonly" value="${dto.sort}"  class="easyui-numberbox" required="true"    maxlength="3" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">发布时间：</th>
				<td>
					<input type="text" name="createTime" id="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />"  maxlength="50" class="easyui-validatebox" required="true"  readonly="readonly" />
				</td>
			</tr>
			<%-- <tr>
				<th>
					文库标签：
				</th>
				<td>
						<input type="text" name="tag" value="${dto.tag }"  readonly="readonly"> 
				</td>
			</tr> --%>
			<tr>
			    	<th style="width: 80px">推荐：</th>
			    	<td>
						<select name="recommend">
							<option value="1"   id ="1">是</option>
							<option value="0"  id="0">否</option>
						</select>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">文章内容：</th>
			    	<td>
					<textarea id="content" name="content"  readonly="readonly" style="width:320px;height:600px;">${dto.content }</textarea>
			    	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">文章图片：</th>
			    	<td >
                		<span id="nimgs"  ></span>
                	   	<!-- <input  onclick="doSelectPic()"  type="button" value="上传图片" id="picturefile" class="form_shangchuan" /> -->
	                    <iframe id="uploadPicFrame" src="" style="display:none;"></iframe>         	
                	</td>
			  </tr>
			   <tr>
			    	<th style="width: 80px">微吼视频：</th>
			    	<td>
			     	<input class="living" readonly="readonly" type="text" id="mau_webinarSubject" name="webinarSubject" value="${dto.webinarSubject}" style="width:200px;"/>
				    <input class="living" type="hidden" id="mau_webinarId" name="webinarId" value="${dto.webinarId}"/>
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
			    	<th style="width: 100px">音频文件：</th>
			    	<td>
			    	<div id= "deladbutton">
			    	<c:if test="${dto.audioFile!=null && dto.audioFile!=''}">
			    	<video  controls="controls" width="320" height="50" id="video">
   						<source src="http://tianjiu.oss-cn-beijing.aliyuncs.com/${dto.audioFile}" />
 					</video>
			    	    </c:if>
			    	     </div>
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
				  <th>内容可视范围：</th>
				  <td>
					  <input type="checkbox" <c:if test="${contentYunYou==true}">checked</c:if> id="contentYunYou" name="contentYunYou" value="1"/>云友
					  <input type="checkbox" <c:if test="${contentYunQin==true}">checked</c:if> id="contentYunQin" name="contentYunQin" value="2"/>云亲
					  <input type="checkbox" <c:if test="${contentYunShang==true}">checked</c:if> id="contentYunShang" name="contentYunShang" value="4"/>云商
				  </td>
			  </tr>
			  <tr>
				  <th>视频可视范围</th>
				  <td>
					  <input type="checkbox" <c:if test="${videoYunYou==true}">checked</c:if>  id="videoYunYou" name="videoYunYou" value="1"/>云友
					  <input type="checkbox" <c:if test="${videoYunQin==true}">checked</c:if> id="videoYunQin" name="videoYunQin" value="2"/>云亲
					  <input type="checkbox" <c:if test="${videoYunShang==true}">checked</c:if> id="videoYunShang" name="videoYunShang" value="4"/>云商
				  </td>
			  </tr>
		  </table>
		  <input type="hidden" name="id" value="${dto.id }"/>
		  <input type="hidden" name="datagridId" value="${param.rel }_datagrid" />	
		  <input type="hidden" name="currentCallback" value="close" />
		<input type="hidden" name="createTime" value="<fmt:formatDate  value="${dto.createTime}" pattern="yyyy-MM-dd hh:mm:ss" />"/>
	</form>
	
	
</div>

<script type="text/javascript">	
//options为编辑配置属性
var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640',
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
	/* for(var item in ss) {
		$("#"+ss[item]).attr("checked","checked");
		} */
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
	var imgHtml = "<img   srcpath='"+picUrl+"' src='"+oss+picUrl+"' class='up_pic_img' style='width:240px; height:160px;'/>";
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

