<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="客服留言">
		<title>留言</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css?v=${sversion}" />	
	</head>
	<body style="background: #f2f3f4;">
		<div class="msg">
			<textarea name="content" id="content" rows="" cols="" placeholder="请输入你要留言的内容" onpaste="Check(this)" oninput="Check(this)" onchange="Check(this)"  onafterpaste="Check(this)"  onkeyup="Check(this) " onKeyDown="Check(this)" maxLength=200 onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"></textarea>
			<p><label id="num">0</label>/200</p>
		</div>
		<div class="pst">
			<%-- <p class="kf">客服电话为：<a href="tel:${kf_tel}">${kf_tel}</a></p> --%>
			<div class="M-footer active_A" id="save">
				完成
			</div>
		</div>
		<script type="text/javascript">
		function isNull( str ){
			if ( str == "" ) return true;
			var regu = "^[ ]+$";
			var re = new RegExp(regu);
			return re.test(str);
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
		
			$(document).ready(function() {
				$("#save").click(function(){
					var content=$("#content").val();
					if(isEmpty(content)){
						alert_back("留言内容不能为空",function(){
							$("#content").focus();
						});
						return;
					}
					
					if(content == null || content == undefined || content == ''){
						
						alert_back("留言内容不能为空",function(){
							$("#content").focus();
						});
						return;
					}
					
					if(isNull(content)){
						alert_back("留言内容不能为空",function(){
							$("#content").focus();
						});
						return;
					}
					
					if(isEmojiCharacter(content)){
							alert_back("留言内容不能含表情",function(){
								$("#content").focus();
							});
							return;
					}
					
					zdy_ajax({
						url: "${path}/m/my/leaveMsgSave.do",
					    data:{
					    	content:content,
					    	type:2
					    },
						success: function(data,res){
							alert_back("留言成功",function(){
								/* self.location.href="${path}/m/my/my_center.do"; */
								setTimeout(function(){
							   history.back(-1);
						       },1500)
							});
						}
					}); 
				});
			});
			var changeContent=function(obj){
				var c=$(obj).val().length;
				$("#num").text(c);
			}
			
			function Check(txt)
			{
			    TextCount=txt.value.length;  
			    $("#num").text(TextCount);   
			    if(TextCount>=200){
			    	alert("留言内容不能大于200字");
			    	return;
			    }
			}
		</script>
	</body>
</html>