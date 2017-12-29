<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>发布话题PK</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css" />
		 <style type="text/css">
	.pl textarea{
	width: 100%;
	height: 2.5rem;
	border: 0;
	border-bottom: 1px #f2f3f4 solid;
	resize: none;
	font-size: .26rem;
	}
	 </style>
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
			 <input id="allowComment" name="allowComment" value="1"  type="hidden" />
			 <input id="isShow" name="isShow" value="2"  type="hidden" />
				<div class="pk-con">
					<p>PK话题：</p>
					<textarea name="topicDesc" id="topicDesc" rows="" cols="" maxlength="100"placeholder="抛出本次PK话题，不超过100汉字" onkeyup="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"></textarea>
					<p>红方观点：</p>
					<input type="text" name="redPoint" id="redPoint" value=""maxlength="50"  placeholder="不得超过50汉字" onkeyup="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" />
					<p>蓝方观点：</p>
					<input type="text" name="bluePoint" id="bluePoint" value="" maxlength="50" placeholder="不得超过50汉字" onkeyup="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"/>
					<p>话题阐述：</p>
					<div class="pl">
					<textarea name="topicExplain" id="topicExplain" rows="" cols="" placeholder="请输入您要阐述的内容" onkeyup="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"></textarea>
					</div>
				</div>
				<div class="cx">
					<div class="no">
						<p>仅对好友可见</p>
						<img id="off" src="${path}/resource/img/icons/OFF.png" onclick="isShow('1')"/>
						<img id="on" src="${path}/resource/img/icons/oN.png" onclick="isShow('2')"/>
					</div>
				</div>
			</div>
			<div class="submit active_A" onclick="topic_submit();">发布</div>
		</div>
		
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
$(document).ready(function() {
	$("#on").hide();
});
function isShow(type){
	if(type=="1"){
		$("#on").show();
		$("#off").hide();
	}else if(type=="2"){
		$("#off").show();
		$("#on").hide();
	}
	$("#isShow").val(type);
}
var topic_submit=function(){
	var topicDesc=$.trim($("#topicDesc").val());
	if(isEmpty(topicDesc)){
		alert_back("PK话题不能为空",function(){
			$("#topicDesc").focus();
		});
		return;
	}
	var redPoint=$.trim($("#redPoint").val());
	if(isEmpty(redPoint)){
		alert_back("红方观点不能为空",function(){
			$("#redPoint").focus();
		});
		return;
	}
	var bluePoint=$.trim($("#bluePoint").val());
	if(isEmpty(bluePoint)){
		alert_back("蓝方观点不能为空",function(){
			$("#bluePoint").focus();
		});
		return;
	}
	var allowComment=$("#allowComment").val();
	if ($("#topicDesc").val().length > 100) { 
        alert("PK话题最多为100汉字！");
        return ;
     } 
	if ($("#redPoint").val().length > 50) { 
        alert("红方观点最多为50汉字！");
        return ;
     }
	if ($("#bluePoint").val().length > 50) { 
        alert("蓝方观点最多为50汉字！");
        return ;
     }
	var topicExplain = $.trim($("#topicExplain").val());
	if ($("#topicExplain").val().length> 500) { 
        alert_back("阐述内容为1-500汉字！",function(){
			$("#topicExplain").focus();
		});
        return ;
     }
	var isShow = $("#isShow").val();
	zdy_ajax({
		url: '${path}/m/topic/add/addTopic.do',
		data:{
			topicDesc:topicDesc,
			redPoint:redPoint,
			bluePoint:bluePoint,
			allowComment:allowComment,
			topicExplain:topicExplain,
			isShow :isShow
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("发布成功！",function(){
				 var history_url = localStorage.getItem("history_url");
				 top.location.href=history_url;
				 localStorage.removeItem("history_url");
			  });
			}
		},
		error:function(e){
		}
	});
}
var getLength = function (str) {
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};
</script>

</html>