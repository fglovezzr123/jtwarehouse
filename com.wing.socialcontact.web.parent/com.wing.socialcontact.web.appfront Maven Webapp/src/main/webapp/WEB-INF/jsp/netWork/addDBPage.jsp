<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="èµè®¯">
		<title>发布商洽</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css" />
		
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
				<div class="pl">
					<textarea name="content" id="content" rows="" cols="" placeholder="请输入您要商洽的内容" 
					onkeyup="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')" 
					onpaste="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"
					oncontextmenu="value=value.replace(/[^(\`~!#$\^&@%*+\-\(\)=\|\{\}\':;\',\\.\<\>\/?~！#￥……&*（）——{}【】‘；：”“'。，、？\]\[‘'《》·_)|(a-zA-Z0-9\u4E00-\u9FA5)]/g,'')"></textarea>
					
				</div>
			</div>
			<div class="submit active_A" onclick="db_submit();">提交</div>
		</div>
		
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
$(document).ready(function() {
	
});
//点击发布按钮
var db_submit=function(){
	var content=$("#content").val();
	if(isEmpty(content)){
		alert_back("商洽内容不能为空",function(){
			$("#content").focus();
		});
		return;
	}
	if(isEmojiCharacter(content)){
		alert_back("商洽内容不能含表情",function(){
			$("#content").focus();
		});
		return;
	}
//	content = firstCharacter(content);
	zdy_ajax({
		url: '${path}/add/m/business/addBD.do',
		data:{
			fkId:'${fkId}',
			content:content
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("发布成功！",function(){
					/* if(parent){
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.location.reload(); // 父页面刷新  
						parent.layer.close(index);
					} */
					self.location=document.referrer;
				});
			}
		},
		error:function(e){
		}
	});
}
//判断是否有emoji
function isEmojiCharacter(substring) {  
    for ( var i = 0; i < substring.length; i++) {  
        var hs = substring.charCodeAt(i);  
        if (0xd800 <= hs && hs <= 0xdbff) {  
            if (substring.length > 1) {  
                var ls = substring.charCodeAt(i + 1);  
                var uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;  
                if (0x1d000 <= uc && uc <= 0x1f77f) {  
                    return true;  
                }  
            }  
        } else if (substring.length > 1) {  
            var ls = substring.charCodeAt(i + 1);  
            if (ls == 0x20e3) {  
                return true;  
            }  
        } else {  
            if (0x2100 <= hs && hs <= 0x27ff) {  
                return true;  
            } else if (0x2B05 <= hs && hs <= 0x2b07) {  
                return true;  
            } else if (0x2934 <= hs && hs <= 0x2935) {  
                return true;  
            } else if (0x3297 <= hs && hs <= 0x3299) {  
                return true;  
            } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030  
                    || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b  
                    || hs == 0x2b50) {  
                return true;  
            }  
        }  
    }  
}

//首字符串为特殊符号
function firstCharacter(substring) {
	if(substring.indexOf("<")==0){
		substring = substring.replace("<","");
	}
	if(substring.indexOf(">")==0){
		substring = substring.replace(">","");
	}
	return substring;
}
</script>

</html>