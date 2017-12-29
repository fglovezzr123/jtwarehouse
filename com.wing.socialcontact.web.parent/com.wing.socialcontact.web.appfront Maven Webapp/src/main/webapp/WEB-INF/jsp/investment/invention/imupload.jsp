<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
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
  <input type="button"  onclick="save()" value="提交表单">
  </form>
  </body>
</html>
<script>

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