<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">投资意向表单</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
		  
        <link rel="stylesheet" href="${path}/resource/css/invest-intention.css?v=${sversion}" />
       
        <script src="${path}/resource/js/libs/jquery-2.2.3.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
 
        <style>
             .banner_ul img{
                 width:100%;
                 height:100%;
             }
        </style>
    </head>
    
  
  <body>
  <form id="formData" action="${path}/im/m/upload.do" enctype="multipart/form-data" method="post" >
  <input type="file" id="licensefile" name="imgurl_file"  multiple="multiple" >
  <input type="button"  onclick="uploadpic()" value="保存">
  <input type="button"  onclick="uploadpic1()" value="base保存">
  <input type="button"  onclick="save()" value="提交表单">
  </form>
  </body>
</html>
<script>

function uploadpic1(){
    $.ajax({  
  		url: '${path}/im/m/baseupload.do',  
        type: 'POST',  
        data: {
        	imgstr:"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",
        	ext:"jpeg"
        },  
        success: function (json) {
        console.log(json)
        	//alert(JSON.stringify(json));
        },  
        error: function (json) {  
        	//alert(JSON.stringify(json));  
                           }  
         });  
}
function uploadpic(){
	var formData = new FormData($("#formData"));
	var fileUpload = $('#licensefile')[0].files[0];
	var formData = new FormData();
	var reader = new FileReader();
	reader.readAsDataURL(fileUpload);
	formData.append("imgurl_file",fileUpload);
	//formData.append('imgurl_file', $('#licensefile')[0].files[0]);
	//formData.append('type',"license");
	
    $.ajax({  
  		url: '${path}/im/m/upload.do',  
        type: 'POST',  
        data: formData,  
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,  
        success: function (json) {
        	alert(JSON.stringify(json));
        },  
        error: function (json) {  
        	alert(JSON.stringify(json));  
                           }  
         });  
}
function save(){
	$("#formData").submit();
}
</script>