<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">项目自荐</title>

<link rel="stylesheet" href="${path}/resource/css/project-selfRecon.css?v=${sversion}" />

<script src="${path}/resource/js/upload.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
	   input[type=checkbox] {
	           -webkit-appearance: none;
	           width:.5rem;
	           height:.5rem;
	           border-radius:100%;
	           background:url(${path}/resource/img/icons/check1.png) no-repeat;
	           background-size: 100%;
	           position:absolute;
	           left:0.4rem;
	           top:0.25rem;
           }
           input[type=checkbox]:checked {
               background:url(${path}/resource/img/icons/check2.png) no-repeat;
               background-size: 100%
           }
			.s-value{
			display:block;
			margin-left:0.8rem;
			}

</style>
</head>
<body>
<form id="dataForm">
    <div class="wrapper">
         <div class="xiaobiaoti" style="display:none">企业信息</div>
         <div class="info-forms" style="display:none">
              <div class="info-forms-name">企业名称</div>
              <input type="text" placeholder="必填项" name="comName" id="comName" maxlength="50" value="${user.comName }"
              	onkeyup="textCheck(this)" />
         </div>
         <div class="info-forms" style="display:none">
              <div class="info-forms-name">注册资本</div>
              <input type="hidden" placeholder="注册资本在0.01-9999.99之间" name="registeredCapital" id="registeredCapital"value="${user.reconCapital}" /> 万
         </div>
         <div class="xiaobiaoti">项目类型</div>
         <c:forEach var="item"  items="${list}" varStatus="varStatus">
         <div class="info-forms" style="position: relative;" data_check="0">
               <input type="checkbox" name="prjType" value="${item.id }" <c:if test="${varStatus.index == 0}">checked="checked"</c:if> /><span class="s-value">${item.listValue}</span >
         </div>
         </c:forEach>
         <div class="xiaobiaoti">项目详情</div>
           <div class="info-forms">
              <div class="info-forms-name">项目名称</div>
              <input type="text" placeholder="请输入项目全称" name="prjName" id="prjName" maxlength="30"/>
         </div>
         <div class="info-forms">
             <div class="info-forms-name">项目描述</div>
             <input type="text" placeholder="请输入项目描述" name="prjDesc" id="prjDesc" maxlength="50" onblur="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" />
         </div>
         <div class="projectDpict">
              <div>项目介绍</div>
             <textarea placeholder="请输入项目介绍" name="prjProfile" id="prjProfile" maxlength="200" onblur="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" ></textarea>
             <br class="clear"/>
         </div>
         <div class="xiaobiaoti">上传照片</div>
         <div class="photo">
         <div class="addPhoto active_A" id="addImges" onclick="choosePic()" ></div>
         <br class="clear"/>
         </div>
         <div class="Done-it active_A">
             	 完成并提交
              <div>之后会有工作人员与你联系</div>
         </div>
         
   </div>
</form>
<span class="typefile" id="filePicker"></span>
</body>
<script>
$(function(){
	/* $(".info-forms").on('click',function(){
		if($(this).find('input[type="checkbox"]')){
				$(this).find('input[type="checkbox"]').prop('checked',true)
		}
	}) */
	$(".info-forms").on('click',function(){
		if($(this).attr('data_check')==0){
			$(this).attr('data_check',1)
			$(this).find('input').prop('checked',true)
		}else{
			$(this).attr('data_check',0)
			$(this).find('input').prop('checked',false)
		}
	})
	$(".Done-it").click(function(){
		//处理图片
		var len = $(".pppppp img").length;
		
		//var prjType=$('input:radio[name="prjType"]:checked').val();
		var params = $("#dataForm").serializeObject();
		if(params["comName"].trim()==""){
			alert("请输入企业名称");
			$("#comName").val("");
			return;
		}
		if(params["registeredCapital"]==0){
			$("#registeredCapital").val("");
		}
		if(params["registeredCapital"]==""){
			alert("请输入注册资本");
			$("#registeredCapital").val("");
			return;
		}
		var   registeredCapital  =params["registeredCapital"];
		
		if(registeredCapital>9999.99||registeredCapital<0.01){
			alert("注册资本范围输入有误");
			$("#registeredCapital").val("");
			return;
		}
		
		var obj=document.getElementsByName('prjType'); //选择所有name="'test'"的对象，返回数组 
		//取到对象数组后，我们来循环检测它是不是被选中 
		var s=''; 
		for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) s+=obj[i].value+','; //如果选中，将value添加到变量s中 
		} 
		if(s==''){
			alert("您还没有选择任何分类！");
			return;
		}
		var  prjType = s.substring(0, s.length-1);
		
		if(params["prjName"]==""){
			alert("请输入项目名称");
			$("#prjName").val("").val("");
			return;
		}
		var prjName = $("#prjName").val();
		if(isEmpty($.trim(prjName))){
			alert("请输入项目名称");
			$("#prjName").val("").val("");
			return;
		}
		var prjName = $("#prjName").val();
		if(isEmpty($.trim(prjName))){
			alert("请输入项目名称");
			$("#prjName").val("").val("");
			return;
		}
		var prjDesc = $("#prjDesc").val();
		if(isEmpty($.trim(prjDesc))){
			alert("请输入项目描述");
			$("#prjDesc").val("").val("");
			return;
		}
		var prjProfile = $("#prjProfile").val();
		if(isEmpty($.trim(prjProfile))){
			alert("请输入项目介绍");
			$("#prjProfile").val("").val("");
			return;
		}
		if(params["prjDesc"]==""){
			alert("请输入项目描述");
			$("#prjDesc").val("").val("");
			return;
		}
		if(params["prjProfile"]==""){
			alert("请输入项目介绍").val("");
			$("#prjProfile").val("").val("");
			return;
		}
		if(len==0){
			alert("请上传项目照片");
			return;
		}
		var images = [];
		$(".pppppp img").each(function(){
			var imgUrl =$(this).attr("src");
			images.push({imageUrl: imgUrl});
		});
		params.images = JSON.stringify(images);
		params.prjType = prjType;
		zdy_ajax({
			url: "${path}/m/project/recommend/saveorupdate.do",
			data: params,
			success:function(data){
				layer.open({
					  type: 1,
					  title: false,
					  closeBtn: 0,
					  shadeClose: true,
					  skin: 'yourclass',
					  content: '<div style="text-align:center;font-size:0.4rem;padding:0.5rem;"><img style="width:1rem;height:1rem;margin-bottom:0.5rem;" src="${path}/resource/img/icons/projectSubmitOk.png"/><br/>已提交成功，平台服务人员将尽快联系您</div>'
				});
				
				setTimeout(function(){
					 window.location.href = "${path}/m/project/collect/index.do?type=2";
				},1500)
				
			}
		})
	});
	initWebUpload();
})
var uploader;
function deleteImg(obj){
	$(obj).parent().remove();
	if($(".pppppp img").length>=5){
		$("#addImges").hide();
	}else{
		$("#addImges").show();
	}
}


function choosePic(){
	 zdy_chooseImg(function(data,res){
	 	if(res.code == 0){
	 		
	 		if($(".pppppp img").length<5){
				var htmlStr = 
					'<div class="pppppp"><img src="'+data.img_url+'"><i onclick="deleteImg(this)"></i></div>';
				$("#addImges").before(htmlStr);
				if($(".pppppp img").length>=5){
					$("#addImges").hide();
				}else{
					$("#addImges").show();
				}
			}
		}
	 		
	 },"project")
}


function uploadImg(type){
	$("#filePicker :file").click();
}
var _index;
function initWebUpload(){
	uploader = WebUploader.create({
		auto: true,
	    server: "${path}/m/project/uploadpic.do",
	    pick:  {id : '#filePicker',multiple: false},
	    fileNumLimit: 5000,//一次最多上传多少张照片
	    fileSingleSizeLimit: 2 * 1024 * 1024*100,
	    duplicate : true,
	    accept: {
	        title: "Images",
	        extensions: "gif,jpg,jpeg,bmp,png",
	        mimeTypes: 'image/jpg,image/jpeg,image/png'
	    },
	    formData: { }
	});
	uploader.on("uploadSuccess", function( file, response ) {
		if(response.code==0){
			if($(".pppppp img").length<5){
				var htmlStr = 
					'<div class="pppppp"><img src="'+response.dataobj.img_url+'"><i onclick="deleteImg(this)"></i></div>';
				$("#addImges").before(htmlStr);
				if($(".pppppp img").length>=5){
					$("#addImges").hide();
				}else{
					$("#addImges").show();
				}
			}
		}else{
			alert(response.msg)
		}
	});
	uploader.on( 'startUpload', function() {
		_index = layer.load(1, {shade: [0.1,'#393D49']});
	});
	uploader.on( 'uploadComplete', function() {
		layer.close(_index);
	});
	uploader.on( 'uploadError', function( file,reason) {
		//reason timeout:请求超时
		if("timeout"==reason){
			alert("网络不给力，请点击屏幕重试");
		}else{
			alert("网络不给力，请点击屏幕重试");
		}
	});
	uploader.on('error', function(handler) {
		alert("网络不给力，请点击屏幕重试")
	});
}
function num(obj){
	//obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	//obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
	//obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
	//obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	//obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
	if(isNaN(obj.value)){
		obj.value = '';
	}else{
		//obj.value =obj.value;
	}
}
function textCheck(obj){
	var val = $(obj).val().replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5\\]/g,'')	
	$(obj).val(val);
}
</script>
</html>