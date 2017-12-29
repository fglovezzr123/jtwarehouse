<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<%--
	模块：
--%>

	<form  action="investProduct/add.do"  id="financeProduct_form"  method="post"   onsubmit="return validateSubmitForm(this)">
 	 <%----%><input value="${bean.id}" name="id" type="hidden" />
		<table class="table table-nobordered " style="margin-top: 25px;">
			<tr>
				<input id="consultantImg" name="consultantImg" value="" type="hidden" />
				<th style="width: 120px">头像：</th>
				<td>
					<span id="addDetailBannerImgs" title="点击删除"></span>
					<input onclick="doSelectPic1()" type="button" value="上传图片" id="picturefile" class="form_shangchuan" />
					<iframe id="uploadPicFrame" src="" style="display:none;"></iframe>
					<strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px，大小限制2M。</strong>
				</td>
			</tr>
			<tr>
				<th style="width: 120px">姓名：</th>
				<td>
					<input type="text" name="productName" class="easyui-validatebox" required="true" data-options="validType:['length[1,30]']"
					 maxlength="30" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">机构：</th>
				<td>
					<select name="orgNo" id="orgNo" required="true" style="width: 120px;">
						<c:forEach items="${org}" var="item">
							<option value="${item.id}">${item.orgName}</option>
						</c:forEach>
					</select>
				</td>
				<!-- <th style="width: 120px">是否显示：</th>
						<td>
							<select name="isShow" id="isShow" required="true" style="width: 120px;">
								<option  value="1">是</option>
								<option  value="2">否</option>
							</select>
						</td> -->
			</tr>
			<tr>
				<tr>
					<th style="width: 120px">职务：</th>
					<td>
						<input type="text" name="consultant" required="true" min="1" max="9999" />
					</td>
				</tr>
				<!-- <th style="width: 120px">是否推荐：</th>
						<td>
							<select name="isRecommend" id="isRecommend" required="true" style="width: 120px;">
								<option  value="1">是</option>
								<option  value="2">否</option>
							</select>
						</td> -->
			</tr>
		
			<tr>
				<th style="width: 120px">地域</th>
				<td>
					<input type="text" name="expectedRate" style="width: 40px;" />%
				</td>
			</tr>
			<tr>
				<th style="width: 120px">所在行业</th>
				<td>
					<!-- <select name="productStatus" id="productStatus" required="true" style="width: 120px;">
									<option value="1" <c:if test="${bean.productStatus==1}">selected</c:if>>预售</option>
									<option value="2" <c:if test="${bean.productStatus==2}">selected</c:if>>在售</option>
								</select> -->
				</td>
			</tr>
			<tr>
				<th style="width: 120px">联系电话</th>
				<td>
					<input type="text" name="startPoint" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">关注行业</th>
				<td>
					<input type="text" name="placeDate" />
				</td>
			</tr>
			<tr>
				<th style="width: 120px">倾向轮次</th>
				<td>
					<textarea id="introduce" name="introduce" rows="5" cols="35"></textarea>
				</td>
			</tr>
		
			<tr>
				<th style="width: 80px">投资人详情及成功投资的项目</th>
				<td>
					<textarea name="productDetail" style="width:320px;height:600px;" id="productDetail"></textarea>
				</td>
			</tr>
		
			<tr>
				<th></th>
				<td></td>
				<div style="margin-top: 10px;margin-bottom: 10px;">
					<button type="button" class="btn btn-primary" onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn clear">清空</button>
				</div>
				</td>
			</tr>
		
		</table>
		<div class="divider"></div>
		<input type="hidden" name="id" value="${bean.id}" />
		<input type="hidden" name="currentCallback" value="close" />
		</form>
<script type="text/javascript">

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
editor = KindEditor.create('#productDetail',options);
})
function save(){
editor.sync();//同步编辑器的数据到texterea
	var flag = true;
	var picUrl = "";
	$('.up_pic_img').each(function(){
		picUrl = $(this).attr("srcpath");
	});
//	if($.trim(picUrl).length <= 0){
//		Msg.alert("提示","请上传幻灯片封面！","warning",null);
//		return false;
//	}
	$("#consultantImg").val(picUrl);
	if(flag){
		if(!validateSubmitForm($('#financeProduct_form'))){
			return;
		}
	}
}

var picUrl = $("#consultantImg").val();
if($.trim(picUrl).length > 0){
	$("#addDetailBannerImgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+picUrl+"' src='"+picUrl+"' class='up_pic_img' style='width:375px; height:180px;'/>";
	$("#addDetailBannerImgs").append(imgHtml);
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
initUploadTagPicFrame();
function initUploadTagPicFrame(){
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
      initUploadTagPicFrame();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadTagPicFrame();
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
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img' style='width:375px; height:180px;'/>";
    		$("#addDetailBannerImgs").append(imgHtml);
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
	initUploadTagPicFrame();
}
</script>
