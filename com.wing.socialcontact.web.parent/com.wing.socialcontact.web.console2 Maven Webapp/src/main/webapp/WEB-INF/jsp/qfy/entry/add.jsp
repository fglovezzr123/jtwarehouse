<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%  String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%>
<%--
	模块：
--%>

	<form  action="qfy/entry/add.do"  id="entryPrise_form"  method="post" onsubmit="return validateSubmitForm(this)">
		 <input id="logoImgPath" name="logoImgPath" value=""  type="hidden" />
		 <input id="reconImg" name="reconImg" value=""  type="hidden" />
		 <input id="bannerImg" name="bannerImg" value=""  type="hidden" />
		 <input id="entryClass" name="entryClass" value=""  type="hidden" />
		 <input id="entryTags" name="entryTags" value=""  type="hidden" />
		 <input id="phone" name="phone" value=""  type="hidden" />
		<div class="divider"></div>
		  <table class="table table-nobordered " style="margin-top: 10px;">
			 <tr>
		    	<th style="width: 80px">企业名称：</th>
		    	<td>
		     		<input type="text" name="name" class="easyui-validatebox" required="true"  data-options="validType:['length[1,100]']"   maxlength="100" />
		    	</td>
			  </tr>
			  <tr>
			  	<th style="width: 80px">联盟标题：</th>
		    	<td>
		     		<input type="text" name="title" class="easyui-validatebox" required="true"  data-options="validType:['length[1,100]']"   maxlength="100" />
		    	</td>
			  </tr>
			 <tr>
		    	<th style="width: 80px">已服务数量：</th>
		    	<td>
		     		<input type="text" name="serviceCount" class="easyui-numberbox" required="true" min="1" max="99999999"/>&nbsp;&nbsp;家
		    	</td>
			  </tr>
			  <tr>
			  	<th style="width: 80px">序号：</th>
		    	<td>
		     		<input type="text" name="sortNum"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  class="easyui-numberbox" required="true" min="1" max="99999999" value="9999"/>
		    	</td>
			  </tr>
			  <tr>
				<th style="width: 80px">所属用户ID：</th>
		    	<td>
		     		<input type="text" name="ssUserId" id="ssUserId" class="easyui-numberbox" required="true" /><input type="button" onclick="validUserId()" value="校验ID"></input>
		    	</td>
			  </tr>
			  <tr>
				<th style="width: 80px">选择分类：</th>
		    	<td>
		     		<span id="entryPrise_sel_Class_content" title="点击删除" ></span>
		     		<button type="button" class="btn"  onclick="selectClass2()">选择分类</button>
		    	</td>
			  </tr>
			  <tr>
				<th style="width: 80px">选择标签：</th>
		    	<td>
		    		<span id="entryPrise_sel_tags_content" title="点击删除" ></span>
		     		<button type="button" class="btn"  onclick="selectTags()">选择标签</button>
		    	</td>
			  </tr>
			 <tr>
				 <th style="width: 80px">logo图片：</th>
			    	<td>
                		<span id="entryLogoImgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPicLogo()"  type="button" value="上传图片" id="picturefileLogo" class="form_shangchuan" />
	                    <iframe id="uploadPicFrameLogo" src="" style="display:none;"></iframe>  
						<strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸122*122px，大小限制2M。</strong>
                	</td>
		    	
			  </tr>
		 	  <tr>
			    	<th style="width: 80px">认证图片：</th>
			    	<td>
                		<span id="reconImgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPicRecon()"  type="button" value="上传图片" id="picturefileRecon" class="form_shangchuan" />
	                    <iframe id="uploadPicFrameRecon" src="" style="display:none;"></iframe>  
						<strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸48*10px，大小限制2M。</strong>
                	</td>
			  </tr>
		 	  <tr>
			    	<th style="width: 80px">Banner图片：</th>
			    	<td>
                		<span id="bannerImgs" title="点击删除" ></span>
                	   	<input  onclick="doSelectPicBanner()"  type="button" value="上传图片" id="picturefileBanner" class="form_shangchuan" />
	                    <iframe id="uploadPicFrameBanner" src="" style="display:none;"></iframe>  
						<strong style="color:#F00">请上传jpg,jpeg,png格式图片,建议尺寸640*320px，大小限制2M。</strong>
                	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">企业简介：</th>
			    	<td>
			    		<textarea  id="entryDescContent" name="entryDesc" class="easyui-validatebox" required="true"  data-options="validType:['length[1,500]']"   maxlength="500"></textarea>
			    		<p> 
						   <span class="word_surplus"></span> 
						</p>
<!-- 			    		<p> 您当前输入了 <span class="word_count1">0</span> 个文字。（字数统计包含HTML代码。）<br /> -->
<!-- 						  您当前输入了 <span class="word_count2">0</span> 个文字。（字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字。）<br> -->
<!-- 						   <span class="word_surplus"></span>  -->
<!-- 						</p> -->
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">标题简介：</th>
			    	<td>
			     		<textarea  id="titleDescContent" name="titleDesc" rows="3" cols="" class="easyui-validatebox" required="true"  data-options="validType:['length[1,500]']"   maxlength="500"></textarea>
			     		<p> 
						   <span class="word_surplus1"></span> 
						</p>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">详情介绍：</th>
			    	<td>
			     		<textarea  id="detailDescContent" name="detailDesc" rows="3" cols=""></textarea>
			    	</td>
			  </tr>
			  <tr>
			    	<th style="width: 80px">服务案例：</th>
			    	<td>
			     		<textarea  id="serviceCaseContent" name="serviceCase" rows="3" cols=""></textarea>
			    	</td>
			  </tr>
			  <tr>
				<th style="width: 80px">客服电话：</th>
		    	<td>
		    		<table class="table table-nobordered " style="margin-top: 10px;">
		    			<tr>
		    				<td class="phoneClass">
		    					<input type="text" name="1" id="1" class="easyui-validatebox" required="true" /><img id="1" src="<%=basePath%>/resource/images/qfy/jia32.png" onclick="addTr(this)"/>
		    				</td>
		    			</tr>
		    		</table>
		    	</td>
			  </tr>
			  <tr>
				 <th></th>
				 <td>
					<div  style="margin-top: 10px;margin-bottom: 10px;">
						  <button type="button" class="btn btn-primary"  onclick="saveEntryPrise()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
						  <!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
				 </td>
			  </tr>
		  
		  </table>
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
<script type="text/javascript">

var validateMobileFlag = false;

function addTr(t){
	var num = $(t).attr("id");
	num++;
	var htm = "<tr><td class='phoneClass'><input type='text' class='easyui-validatebox' required='true' /><img id='"+num+"' src='<%=basePath%>/resource/images/qfy/jian32.png' onclick='delTr(this)'/></td></tr>";
	$(t).parent().parent().parent().append(htm); 
}
function delTr(t){
	$(t).parent().parent().remove();
}

var options = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'qfy/entry/upload.do?ysStyle=YS640&moduleName=entryPrise',
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
				$('.word_count1').html(this.count()); //字数统计包含HTML代码
		      	$('.word_count2').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
			      //限制字数
			      
			      var limitNum = 500;  //设定限制字数
			      var pattern = '还可以输入' + limitNum + '字'; 
			      $('.word_surplus').html(pattern); //输入显示
			      if(this.count('text') > limitNum) {
			       	pattern = ('字数超过限制，请适当删除部分内容');
				       //超过字数限制自动截取
				       var strValue = this.text();
				       strValue = strValue.substring(0,limitNum);
				       this.text(strValue); 
			       } else {
				       //计算剩余字数
				       var result = limitNum - this.count('text'); 
				       pattern = '还可以输入' +  result + '字'; 
			       }
			       $('.word_surplus').html(pattern); //输入显示
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

var options1 = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'qfy/entry/upload.do?ysStyle=YS640',
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
			      //限制字数
			      var limitNum = 500;  //设定限制字数
			      var pattern = '还可以输入' + limitNum + '字'; 
			      $('.word_surplus1').html(pattern); //输入显示
			      if(this.count('text') > limitNum) {
			       	pattern = ('字数超过限制，请适当删除部分内容');
				       //超过字数限制自动截取
				       var strValue = this.text();
				       strValue = strValue.substring(0,limitNum);
				       this.text(strValue); 
			       } else {
				       //计算剩余字数
				       var result = limitNum - this.count('text'); 
				       pattern = '还可以输入' +  result + '字'; 
			       }
			       $('.word_surplus1').html(pattern); //输入显示
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
var options2 = {
        cssPath : 'resource/js/editor/plugins/code/prettify.css',
        filterMode : false,
		uploadJson:'qfy/entry/upload.do?ysStyle=YS640',
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
	entryDescEditor = KindEditor.create('#entryDescContent',options);
	titleDescEditor = KindEditor.create('#titleDescContent',options1);
	detailDescEditor = KindEditor.create('#detailDescContent',options2);
	serviceCaseEditor = KindEditor.create('#serviceCaseContent',options2);
})

function saveEntryPrise(){
	
	entryDescEditor.sync();//同步编辑器的数据到texterea
	titleDescEditor.sync();//同步编辑器的数据到texterea
	detailDescEditor.sync();//同步编辑器的数据到texterea
	serviceCaseEditor.sync();//同步编辑器的数据到texterea
	
	var flag = true;
	
	//logo图片
	var logoPicUrl = "";
	$('.up_pic_img_logo').each(function(){
		logoPicUrl = $(this).attr("srcpath");
	});
	if($.trim(logoPicUrl).length <= 0){
		Msg.alert("提示","请上传logo图片！","warning",null);
		return false;
	}
	$("#logoImgPath").val(logoPicUrl);
// 	认证图片
	var reconPicUrl = "";
	$('.up_pic_img_recon').each(function(){
		reconPicUrl += $(this).attr("srcpath")+",";
	});
	if($.trim(reconPicUrl).length <= 0){
		Msg.alert("提示","请上传认证图片！","warning",null);
		return false;
	}
	$("#reconImg").val(reconPicUrl);
// 	banner图片
	var bannerPicUrl = "";
	$('.up_pic_img_banner').each(function(){
		bannerPicUrl += $(this).attr("srcpath")+",";
	});
	if($.trim(bannerPicUrl).length <= 0){
		Msg.alert("提示","请上传Banner图片！","warning",null);
		return false;
	}
	$("#bannerImg").val(bannerPicUrl);
// 	选择分类
	var mySelClassIds = "";
	$('.up_sel_class').each(function(){
		mySelClassIds += $(this).attr("id")+",";
	});
	if($.trim(mySelClassIds).length <= 0){
		Msg.alert("提示","请选择分类！","warning",null);
		return false;
	}
	$("#entryClass").val(mySelClassIds);
// 	选择标签
	var mySelTagIds = "";
	$('.up_sel_tag').each(function(){
		mySelTagIds += $(this).attr("id")+",";
	});
	if($.trim(mySelTagIds).length <= 0){
		Msg.alert("提示","请选择标签！","warning",null);
		return false;
	}
	$("#entryTags").val(mySelTagIds);
	//封装客服电话
	var myPhones = "";
	$('.phoneClass .easyui-validatebox').each(function(){
		myPhones += $(this).val()+",";
	});
	/* if($.trim(myPhones).length <= 0){
		Msg.alert("提示","请选择标签！","warning",null);
		return false;
	} */
	$("#phone").val(myPhones);
	
	if(!validateMobileFlag){
		Msg.alert("提示","请校验所属用户ID！","warning",null);
		return false;
	}
	
	 if(flag){
		if(!validateSubmitForm($('#entryPrise_form'))){
			return;
		}
	}
}

//初始logo图片
var logoPicUrl = $("#logoImgPath").val();
if($.trim(logoPicUrl).length > 0){
	$("#entryLogoImgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+logoPicUrl+"' src='"+_oss_url+logoPicUrl+"' class='up_pic_img_logo' style='width:100px; height:100px;'/>";
	$("#entryLogoImgs").append(imgHtml);
}
//logo图片上传
function doSelectPicLogo() {
	var imgSize = 0;
	$('.up_pic_img_logo').each(function(){
		imgSize = imgSize+1;
	});
	if(imgSize <= 0 ){
		$("#pic", $("#uploadPicFrameLogo")[0].contentWindow.document).click();
	}else{
		alert("logo仅能上传一张图片");
	}
	return false;
}
initUploadPicFrameLogo();
function initUploadPicFrameLogo(){
	var frameSrc = "<%=basePath %>qfyBaseUploadPic/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.formId= "entryPrise";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChangeLogo";
	frameParam.jsonp = "parent.picUploadCallbackLogo";
	frameParam.picRepository = "entryPrise";
	frameSrc += "?"+parseParam(frameParam);
	$("#uploadPicFrameLogo").attr("src", frameSrc);
}
function picChangeLogo(inputFile){
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
    		initUploadPicFrameLogo();
		}
	}
	if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename))
    {
      alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
      initUploadPicFrameLogo();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrameLogo();
	}else{
		$("#upload", $("#uploadPicFrameLogo")[0].contentWindow.document).submit();
	}
}
//上传完成后回调的方法
function picUploadCallbackLogo(data){
	if (data.returnCode == "1"){
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img_logo' style='width:100px; height:100px;'/>";
    		$("#entryLogoImgs").append(imgHtml);
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
	initUploadPicFrameLogo();
}


//初始recon认证图片
var reconPicUrl = $("#reconImg").val();
if($.trim(reconPicUrl).length > 0){
	$("#reconImgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+reconPicUrl+"' src='"+_oss_url+reconPicUrl+"' class='up_pic_img_recon' style='width:55px; height:10px;'/>";
	$("#reconImgs").append(imgHtml);
}
//认证图片上传
function doSelectPicRecon() {
	
	var imgSize = 0;
	$('.up_pic_img_recon').each(function(){
		imgSize = imgSize+1;
	});
	if(imgSize < 4 ){
		$("#pic", $("#uploadPicFrameRecon")[0].contentWindow.document).click();
	}else{
		alert("最多上传四张图片");
	}
	return false;
}
initUploadPicFrameRecon();
function initUploadPicFrameRecon(){
	var frameSrc = "<%=basePath %>qfyBaseUploadPic/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChangeRecon";
	frameParam.jsonp = "parent.picUploadCallbackRecon";
	frameParam.picRepository = "entryPrise";
	frameSrc += "?"+parseParam(frameParam);
	$("#uploadPicFrameRecon").attr("src", frameSrc);
}
function picChangeRecon(inputFile){
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
    		initUploadPicFrameRecon();
		}
	}
	if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename))
    {
      alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
      initUploadPicFrameRecon();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrameRecon();
	}else{
		$("#upload", $("#uploadPicFrameRecon")[0].contentWindow.document).submit();
	}
}
//上传完成后回调的方法
function picUploadCallbackRecon(data){
	if (data.returnCode == "1"){
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img_recon' style='width:55px; height:10px;'/>";
    		$("#reconImgs").append(imgHtml);
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
	initUploadPicFrameRecon();
}



//初始Banner图片
var bannerPicUrl = $("#bannerImg").val();
if($.trim(bannerPicUrl).length > 0){
	$("#bannerImgs").html("");
	var imgHtml = "<img title='点击删除' onclick='delPic(this)'   srcpath='"+bannerPicUrl+"' src='"+_oss_url+bannerPicUrl+"' class='up_pic_img_banner' style='width:375px; height:180px;'/>";
	$("#bannerImgs").append(imgHtml);
}
//logo图片上传
function doSelectPicBanner() {
	var imgSize = 0;
	$('.up_pic_img_banner').each(function(){
		imgSize = imgSize+1;
	});
	if(imgSize < 4 ){
		$("#pic", $("#uploadPicFrameBanner")[0].contentWindow.document).click();
	}else{
		alert("最多上传四张图片");
	}
	return false;
}
initUploadPicFrameBanner();
function initUploadPicFrameBanner(){
	var frameSrc = "<%=basePath %>qfyBaseUploadPic/getUploadPicForm.do";
	var frameParam = new Object();
	frameParam.formId= "upload";
	frameParam.inputId= "pic";
	frameParam.inputOnChange = "parent.picChangeBanner";
	frameParam.jsonp = "parent.picUploadCallbackBanner";
	frameParam.picRepository = "entryPrise";
	frameSrc += "?"+parseParam(frameParam);
	$("#uploadPicFrameBanner").attr("src", frameSrc);
}
function picChangeBanner(inputFile){
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
    		initUploadPicFrameBanner();
		}
	}
	if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(filename))
    {
      alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
      initUploadPicFrameBanner();
    } else
	if (fileSize>2097152){
		alert("上传图片大小超过2M");
		initUploadPicFrameBanner();
	}else{
		$("#upload", $("#uploadPicFrameBanner")[0].contentWindow.document).submit();
	}
}
//上传完成后回调的方法
function picUploadCallbackBanner(data){
	if (data.returnCode == "1"){
		var picUrl = data.picPath;
		if(picUrl.length > 0){
			var imgHtml = "<img title='点击删除' onclick='delPic(this)' srcpath='"+data.picPath+"' src='"+_oss_url+picUrl+"' class='up_pic_img_banner' style='width:375px; height:180px;'/>";
    		$("#bannerImgs").append(imgHtml);
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
	initUploadPicFrameBanner();
}

//选择分类
function selectClass2(){
	MUI.openDialog('选择分类','qfy/entry/queryClass.do?rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_selclass_add',{width:300,height:400});
}
//选择标签
function selectTags(){
	MUI.openDialog('选择标签','qfy/entry/queryTags.do?rel=<%=request.getParameter("rel")%>','<%=request.getParameter("rel")%>_seltags_add',{width:300,height:400});
}



function validUserId(){
	var ssUserId = $("#ssUserId").val();
	if(ssUserId == null || ssUserId == undefined || ssUserId == ''){
		alert("请输入所属用户ID");
		return;
	}
	$.ajax({
		url:"qfy/entry/validUserId.do",
		cache: false,
		dataType:"json",
		data:{
			ssUserId:ssUserId
		},
		success:function(data){
			if(data.code == 0){
				if(data.flag == 1){
					validateMobileFlag = true;
					Msg.alert("提示","校验成功","info",null);
					return;
				}else{
					validateMobileFlag = false;
					Msg.alert("提示","用户手机号不存在，请重新输入","warning",null);
					return;
				}
			}else{
				validateMobileFlag = true;
			}
			
		}
	});
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
function delPic(data){
	$(data).remove();
}
</script>
