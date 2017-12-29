<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%--
	模块：系统管理-- 聚合页面管理--快捷入口添加
--%>
<div style="width: 98%;margin: 0 auto;" >
	<form  action="pageConfig/pageQuickEntry/update.do"  method="post" id="pageQuickEntry_form" enctype="multipart/form-data" >
		<table class="table table-bordered">
			<tr>
				<th width="15%">入口名称:</th>
				<td width="80%"><input type="text" name="name" class="easyui-validatebox" required="true" data-options="validType:['length[1,20]']"  maxlength="20" value="${b.name }"/></td>
			</tr>
			<tr>
				<th>图片:</th>
				<td>
					<div style="position:relative;">
						<img id="upload_img" src="${ossurl}${b.imgUrl}" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;"/>
						<img id="addGuest" onclick="uploadImg('kjrk',this);" src="${path}/resource/images/tjy/jia_03.png" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;display: none;" title="上传入口图片"/>
						<img onclick="removeGuest(this)" id="removeButton" src="${path}/resource/images/tjy/close_04.gif" width="25" height="25" style="position: absolute;left: 80px;top:0;"/>
						<input type="hidden" name="imgUrl" id="imgUrl" value="${b.imgUrl }"/>
						<span style="position: absolute;top:40px;margin-left: 20px;color: red;">请上传jpg,jpeg,png格式图片,建议尺寸(76*76)</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>链接地址:</th>
				<td><input type="text" name="linkUrl" class="easyui-validatebox" required="true" data-options="validType:['length[1,150]']"  maxlength="150" value="${b.linkUrl }"/></td>
			</tr>
			<tr>
				<th>排序:</th>
				<td><input type="text" name="orderNum" class="easyui-validatebox" required="true" maxlength="5" onkeyup="clearNoNum2(this);" value="${b.orderNum }"/></td>
			</tr>
			<tr>
				<th></th>
				<td colspan="2">
					<div  style="margin-top: 10px;margin-bottom: 10px;">
					  	<button type="button" class="btn btn-primary"  onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
					  	<!-- <button type="button" class="btn clear" >清空</button> -->
					</div>
			 	</td>
		  	</tr>
		</table>
		<input type="hidden" name="id" value="${b.id }"/>
		<input type="hidden" name="pageId" value="${b.pageId }"/>
		<input type="hidden" name="datagridId" value="${param.rel }_datagrid" />
		<input type="hidden" name="currentCallback" value="close" />
	</form>
	<span class="typefile" id="filePicker"></span>
</div>

<script type="text/javascript">	
var uploader = null;
$(function() {
	initWebUpload();
});
function delImg(){
	$(".removePic").hide();
	$("#uploadPic").empty();
	$("#uploadPic").append('<span style="display:block;width:80px;height:12px;padding:50px 40px;">点击上传图片</span>');
}
function uploadImg(type,obj){
	$("#filePicker :file").click();
}
function initWebUpload(){
	uploader = WebUploader.create({
		auto: true,
	    server: "dynamic/uploadpic.do",
	    pick: "#filePicker",
	    fileNumLimit: 5000,//一次最多上传多少张照片
	    fileSingleSizeLimit: 2 * 1024 * 1024*100,
	    duplicate : true,
	    accept: {
	        title: "Images",
	        extensions: "jpg,jpeg,png",
	        mimeTypes: 'image/jpg,image/jpeg,image/png'
	    },
	    formData: {  
	    }
	});
	uploader.on("uploadSuccess", function( file, response ) {
		if(response.code==0){
			var str='<img id="upload_img" src="'+response.dataobj.img_url+'" picPath="'+response.dataobj.picPath+'" style="cursor:pointer;margin-top:12px;margin-left:12px;height:80px;width:80px;"/>';
			$("#addGuest").before(str);
			$("#addGuest").hide();
			$("#removeButton").show();
			$("#imgUrl").val(response.dataobj.picPath);
			
		}else{
			alert(response.msg)
		}
	});
	uploader.on( 'uploadError', function( file ) {
		alert("error");
	});
	uploader.on('error', function(handler) {
		alert("上传失败");
	});
}

function cancel(){
	$("#dynamicaddpage").dialog("destroy");
}
function removeGuest(obj){
	$("#upload_img").remove();
	$("#addGuest").show();
	$("#removeButton").hide();
	$("#imgUrl").val("");
}

function save(){
	var imgUrl=$("#imgUrl").val();
	if(imgUrl.length == 0){
		Msg.alert("提示","请选择图片","warning");
		return;
	}
	if(!validateSubmitForm($('#pageQuickEntry_form'))){
		return;
	}
}
</script>
