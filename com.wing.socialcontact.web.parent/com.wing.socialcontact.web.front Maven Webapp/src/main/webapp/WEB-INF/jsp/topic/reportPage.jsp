<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>举报</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css" />
		
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
				<div class="pl">
					<textarea name="content" id="content" rows="" cols="" placeholder="举报内容" onkeyup="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"></textarea>
					
				</div>
			</div>
			<div class="submit active_A" onclick="report_submit();">提交</div>
		</div>
		
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
$(document).ready(function() {
	
});
//点击发布按钮
var report_submit=function(){
	var content=$("#content").val();
	if(isEmpty(content) || content.trim()==""){
		alert_back("举报内容不能为空",function(){
			$("#content").focus();
		});
		return;
	}
	zdy_ajax({
		url: '${path}/m/topic/addReport.do',
		data:{
			fkId:'${fkId}',
			content:content
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("举报成功！",function(){
					self.location=document.referrer;
				});
			}
		},
		error:function(e){
		}
	});
}
</script>

</html>