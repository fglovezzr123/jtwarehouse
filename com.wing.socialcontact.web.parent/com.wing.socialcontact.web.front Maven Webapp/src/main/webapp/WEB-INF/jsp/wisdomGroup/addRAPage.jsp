<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>应答</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css" />
	 <style type="text/css">
	.pl textarea{
	width: 100%;
	height: 5.2rem;
	border: 0;
	border-bottom: 1px #f2f3f4 solid;
	resize: none;
	font-size: .26rem;
	}
	.show p{
           	font-size: .30rem;
          	line-height: .80rem;
          	box-sizing: border-box;
           	padding-right: .30rem;
           	margin-left: .20rem;
    }	
    .p-content {margin:0;}
	 </style>
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
			<p>${reward.question}</p>
				<div class="pl">
					<textarea name="content" id="content" rows="" cols="" placeholder="请输入您要应答的内容" onkeyup="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" ></textarea>
					
				</div>
			</div>
			<div class="submit active_A" onclick="ra_submit();">提交</div>
		</div>
		
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
$(document).ready(function() {
	
});
//点击发布按钮
var ra_submit=function(){
	var content=$("#content").val();
	if(isEmpty(content)){
		alert_back("应答内容不能为空",function(){
			$("#content").focus();
		});
		return;
	}
	if ($("#content").val().length> 500) { 
        alert_back("应答内容为1-500汉字！",function(){
			$("#content").focus();
		});
        return ;
     }
	zdy_ajax({
		url: '${path}/m/reward/addRA.do',
		data:{
			fkId:'${fkId}',
			content:content
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("发布成功！",function(){
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