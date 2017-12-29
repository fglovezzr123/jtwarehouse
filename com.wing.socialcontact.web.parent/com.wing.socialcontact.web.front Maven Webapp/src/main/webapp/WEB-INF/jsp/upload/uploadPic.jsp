<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上传图片</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
</head>
<body>
	<form id="${formId}" action="${path}/m/upload/uploadPic.do" method="post" enctype="multipart/form-data">
	<input name="jsonp" type="hidden" value="${jsonp}" />
	<input name="frameId" type="hidden" value="${frameId}" />
	<input name="moduleName" type="hidden" value="${moduleName}" />
	<input name="inputId" type="hidden" value="${inputId}" />
	<input name="formId" type="hidden" value="${formId}" />
	<input id="${inputId}" name="pic" onchange="${inputOnChange}(this,'${frameId}','${inputId}','${formId }');" type="file" style="opacity:0;width:0px;height:0px;overflow:hidden;" />
</form>
</body>
</html>
