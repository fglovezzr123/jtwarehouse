<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.getSession().setAttribute("path", path);
%>
<style>
	* { margin:0; padding:0;}
	.hyjb_box {}
	.hyjb_dl {float:left; margin: 12px 12px; width:125px; list-style:none; }
	.hyjb_dl dl { position:relative; width:125px; height:105px; }
	.hyjb_dl dl dt { width:120px; height:100px; border:1px solid #dddddd;}
	.hyjb_dl dl dd { position:absolute; right:-10px; top:-10px; }
	.hyjb_dl .hyjb_shuru { width:125px; margin-top:-22px;}
	.hyjb_dl .hyjb_shuru input { border:none; width:110px; height:26px; background:#f7f7f7; text-align:center;}
	.hyjb_tj { float:left; margin:5px 0px 0 5px; }
	#dataForm img{width:100%;height:100%;}
	.ke-icon-tel {
        background-image: url(resource/js/editor/themes/default/timg.jpg);
        width: 16px;
        height: 16px;
	}
	.distant1 {
	    color: green;
	    font-weight: bold;
	    text-shadow: 0 0 1px currentColor,-1px -1px 1px #030,0 -1px 1px #030,1px -1px 1px #030,1px 0 1px #030,1px 1px 1px #030,0 1px 1px #030,-1px 1px 1px #030,-1px 0 1px #030;
	}
</style>

<div style="width:100%;">						
<form  id="dataForm">

	<input type="hidden" name="id" value="${obj.id}"/>
	<input type="hidden" name="createTime"  value="${fns:fmt(obj.createTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="createUserId"  value="${obj.createUserId}">
	<input type="hidden" name="updateTime" value="${fns:fmt(obj.updateTime,'yyyy-MM-dd HH:mm:ss')}">
	<input type="hidden" name="updateUserId"  value="${obj.updateUserId}">
	<input type="hidden" name="deleted"  value="${obj.deleted}">
	<input type="hidden" name="voiceUrl" id="voiceUrl" value="${obj.voiceUrl}">
	<input type="hidden" name="imageUrl" id="imageUrl" value="${obj.imageUrl}">

	<table class="table table-bordered" style="margin-top:0px;width:99%;">

		<tr>
			<th style="width:15%;">服务标题<font color="red">*</font>：</th>
			<td colspan=3>
				<input type="text" maxlength="100" id="titles" name="title" value="${obj.title}" onkeyup="this.value=this.value.replace(/(^\s*)|(\s*$)/g, '')"   class="span2" style="width:98%"/>
			</td>
		</tr>
		<tr>
			<th>权重<font color="red">*</font>：</th>
			<td>
				<input type="text" id="weight" name="weight"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  class="easyui-numberbox" id="sort"  value="${obj.weight}"   maxlength="3" />
			</td>
		</tr>
		<tr>
			<th>服务内容<font color="red">*</font>：</th>
			<td colspan=3>
				<textarea id="content"  name="content" style="width:700px;height:100px;">${obj.content}</textarea>
			</td>
		</tr>
	   <tr>
			<th style="width: 15%;vertical-align:middle">服务封面<font color="red">*</font>：</th>
			<td colspan=3>
				<div class="hyjb_box"  style="margin:0 10px;height:110px;">
					<ul class="hyjb_dl" style="margin:0px;">
						<li>
							<dl style="margin:0px;">
								<dt>
									<a href="javascript:void(0)">
										<c:choose>
											<c:when test="${not empty obj.imageUrl and obj.imageUrl!=''}">
												<img status="1" style="width:100%;height:100%;" name = "imageUrl"
													 src="${obj.imageUrl}" id="hb"/>
											</c:when>
											<c:otherwise>
												<img status="0" style="width:100%;height:100%;"
													 src="${path}/resource/images/tjy/djsc.png" name = "imageUrl" id="hb" onclick="uploadImg('hb')" />
											</c:otherwise>
										</c:choose>
									</a>
								</dt>
								<dd>
									<a href="javascript:void(0)">
										<img id="deleteHb"  <c:if test="${empty obj.imageUrl or obj.imageUrl==''}">style="display:none;"</c:if> onclick="removeHb(this)" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" />
									</a>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
				<div style="color:gray;"><i>请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384</i></div>
			</td>
		</tr>
		<tr>
			<th>视频：</th>
			<td>
				<input class="living" readonly="readonly" type="text" id="mau_webinarSubject" name="webinarSubject" value="${obj.webinarSubject}" style="width:200px;"/>
				<input class="living" type="hidden" id="mau_webinarId" name="webinarId" value="${obj.webinarId}"/>
				<button class="btn btn-primary btn-small living" type="button" onclick="addVhallDialog()">选择直播</button>&nbsp;
				<button class="btn btn-primary btn-small living" type="button" onclick="clearall()">清空视频</button>&nbsp;
			</td>
		</tr>
		<tr>
			    	<th style="width: 100px">上传音频文件：</th>
			    	<td>
			    	<div id= "deladbutton">
						<c:if test="${obj.voiceUrl!=null && obj.voiceUrl!=''}">
							<video  controls="controls" width="320" height="50">
								<source src="${ossUrl}${obj.voiceUrl}" />
							</video>
							 <input  onclick="delaudio()"  type="button" value="删除音频文件" />
						</c:if>
			    	</div>
						<div id="addFile1">
							<c:if test="${obj==null || obj.voiceUrl==null||obj.voiceUrl==''}">
								<input type="file" name="voiceFile"  onchange="clickFileName(this)" >
								<strong style="color:#F00">请上传.mp3格式音频文件,建议文件大小不超过5M。</strong><br/>
							</c:if>
						</div>
						<div id="addFile2" style="display: none">
								<input type="file" name="voiceFile"  onchange="clickFileName(this)" >
								<strong style="color:#F00">请上传.mp3格式音频文件,建议文件大小不超过5M。</strong><br/>
						</div>
			    	</td>
			  </tr>
		<tr>
			<th>是否显示：</th>
			<td>
				<select class="span2" name="isShow">
					<option value="0" <c:if test="${0==obj.isShow}">selected</c:if>>否</option>
					<option value="1" <c:if test="${1==obj.isShow}">selected</c:if>>是</option>
				</select>
			</td>
		</tr>

		<tr>
			<th></th>
			<td colspan=3>
				<div  style="margin-top: 10px;margin-bottom: 10px;">
					  <button type="button" onclick="save()" class="btn btn-primary" >提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  <%--<button type="button" onclick="cancel()" class="btn clear" >取消</button>--%>
				</div>
			</td>
		 </tr>
	</table>
</form>
<span class="typefile" id="filePicker"></span>
</div>
<script src="http://malsup.github.io/jquery.form.js"></script>
<script type="text/javascript">

	//options为编辑器配置属性
	var options = {
		cssPath : 'resource/js/editor/plugins/code/prettify.css',
		filterMode : false,
		uploadJson:'news/upload.do?ysStyle=YS640&moduleName=personalCustomization',
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
		initWebUpload();
	})
//保存
function save(){
	editor.sync();//同步编辑器的数据到texterea
	$("#imageUrl").val($("#hb").attr("src"));

	if($("#titles").val() == ""){
		alert("请填写服务标题");
		return;
	}

	if($("#weight").val() == ""){
		alert("请填写权重");
		return;
	}

	if($("#content").val() == ""){
		alert("请填写服务内容");
		return;
	}
	if($("#imageUrl").val() == "/resource/images/tjy/djsc.png"){
		alert("请选择服务封面");
		return;
	}

	$("#dataForm").ajaxSubmit({
		url:"personalCustomization/saveOrUpdate.do",
		type: 'post',	
		data: {},
		dataType:"json",
		success:function(json){
			if(json&&json["code"]==="0"){
				$("#datagrid").datagrid('reload');
				$("#addPage").dialog("destroy");
				$("#updatePage").dialog("destroy");
			}else{
				alert("操作失败");
			}
		}
	});
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
		$("#voiceUrl").val("");
		$("#audioFile").val("");
		$("#addFile2").css('display','block');
		$("#deladbutton").remove();
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

///////////////////上传图片//////////////////////
	function delImg(){
		$(".removePic").hide();
		$("#uploadPic").empty();
		$("#uploadPic").append('<span style="display:block;width:80px;height:12px;padding:50px 40px;">点击上传图片</span>');
	}
	var imgType = "";
	function uploadImg(type){
		imgType = type;
		$("#filePicker :file").click();
	}

	var uploader = null;
	function initWebUpload(){
		uploader = WebUploader.create({
			auto: true,
			server: "personalCustomization/uploadPic.do",
			pick:  {id : '#filePicker',multiple: false},
			fileNumLimit: 5000,//一次最多上传多少张照片
			fileSingleSizeLimit: 2 * 1024 * 1024*100,
			duplicate : true,
			accept: {
				title: "Images",
				extensions: "gif,jpg,jpeg,bmp,png",
				mimeTypes: 'image/jpg,image/jpeg,image/png'
			},
			formData: {
			}
		});
		uploader.on("uploadSuccess", function( file, response ) {
			if(response.code==0){
				if(imgType=="hb"){
					$("#hb").attr("src",response.dataobj.img_url);
					$("#hb").attr("status","1");
					$("#hb").removeAttr("onclick")
					$("#deleteHb").show();
				}
			}else{
				alert(response.msg)
			}
		});
		uploader.on( 'uploadError', function( file ) {
			alert("上传失败")
		});
		uploader.on('error', function(handler) {
			if(handler=="F_EXCEED_SIZE"){
				alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384")
				return;
			}else if(handler=="Q_TYPE_DENIED"){
				alert("请上传小于2M的PNG、JPG、JPEG图片，建议尺寸为640*384")
				return;
			}
			alert("上传失败")
		});
	}

	function removeHb(){
		$("#hb").attr("src","${path}/resource/images/tjy/djsc.png");
		$("#hb").attr("status","0");
		$("#hb").attr("onclick","uploadImg('hb')");
		$("#deleteHb").hide();
	}
	///////////////////////////////////////
</script>