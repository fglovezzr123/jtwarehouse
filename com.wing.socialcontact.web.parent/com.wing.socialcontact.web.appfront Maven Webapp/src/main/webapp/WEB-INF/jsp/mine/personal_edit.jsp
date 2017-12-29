<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="留言">
		<title>个人设置</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/leaveMsg.css" />
	</head>
	<!-- onkeyup="Check(this) " onKeyDown="Check(this)" -->
	<body style="background: #f2f3f4;">
		<div class="msg">
			<textarea id ="personal_info" name="" rows="" cols="" placeholder="请输入..."  maxLength=200 onkeyup="value=clearYEmoil1(value)" onpaste="value=clearYEmoil1(value)" oncontextmenu="value=clearYEmoil1(value)" ><c:if test="${flag==1}">${user.nickname}</c:if><c:if test="${flag==2}">${user.mobile}</c:if><c:if test="${flag==3}">${user.user_profile}</c:if><c:if test="${flag==4}">${user.user_signature}</c:if></textarea>
			<p  id="dyContentLength">0/200</p>
		</div>
		<div class="pst">
			<div class="M-footer active_A" onclick="add_info('${flag}');">
				完成
			</div>
		</div>
		
		<script type="text/javascript">
		   $(document).ready(function(){
			   
			   $("#dyContentLength").text($("#personal_info").val().length+'/200')
		   })
		
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
		   
			var add_info=function(flag){
				var nickname='';
				var mobile='';
				var user_profile='';
				var user_signature='';
				var temp=$.trim($("#personal_info").val());
				
				if(flag==1){
					nickname=temp;
					if(nickname.length>20) 
			        { 
			           alert_back("昵称的长度不能超过20！",function(){
							$("#personal_info").focus();
						});
			           return false;
			        } 
				}else if(flag==2){
				   mobile=temp;
				   if(mobile.length==0) 
			       { 
			          alert_back("请输入手机号码！",function(){
							$("#personal_info").focus();
						});
					   return false;
			       }     
			       if(mobile.length!=11) 
			       { 
			           alert_back("请输入有效的手机号码！",function(){
							$("#personal_info").focus();
						});
			           return false;
			       } 
			        
			       if(!check_mobile(mobile)) 
			       { 
			           alert_back("请输入有效的手机号码！",function(){
							$("#personal_info").focus();
						});
			           return false;
			       } 
				}else if(flag==3){
					user_profile=temp;
					if(user_profile == null || user_profile == undefined || user_profile == ''){
						
						alert_back("个人简介不能为空",function(){
							$("#personal_info").focus();
						});
						return;
					}
					
					if(isNull(user_profile)){
						alert_back("个人简介不能为空",function(){
							$("#personal_info").focus();
						});
						return;
					}
					
					if(isEmojiCharacter(user_profile)){
						alert_back("个人简介不能含表情",function(){
							$("#personal_info").focus();
						});
						return;
					}
				}else if(flag==4){
					user_signature=temp;
				}
				
				zdy_ajax({
					url: "${path}/m/my/add/addusers.do",
				    showLoading:false,
					data:{
						nickname:nickname,
						mobile:mobile,
						user_profile:user_profile,
						user_signature:user_signature,
						flag:flag,
					},
					success: function(data,res){
						if(res.code == 0){
						/*	if(parent){
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								var temp=$("#personal_info").val();
								if(flag==1){
									parent.$("#nickname").text(temp);
								}else if(flag==2){
									parent.$("#mobile").text(temp);
								}else if(flag==3){
									parent.$("#user_profile").text(temp);
								}else if(flag==4){
									parent.$("#user_signature").text(temp);
								}
								//parent.reload();
								parent.layer.close(index);
								*/
						   self.location=document.referrer;
							//}
						}else{
							alert(output.msg);
						}
					},
				    error:function(e){
					   //alert(e);
				    }
				}); 
			}
			
			function validatemobile(mobile){ 
			       
			} 
			
			
			$("#personal_info").change( function() {
				var len = getByteLen($(this).val());
				$("#dyContentLength").text(len+'/200');
			});
			
			function getByteLen(val) {
	            var len = 0;
	            for (var i = 0; i < val.length; i++) {
	                var a = val.charAt(i);
	                if (a.match(/[^\x00-\xff]/ig) != null) {
	                    len += 1;
	                }
	                else {
	                    len += 1;
	                }
	            }
	            return len;
	        }
			
			
			function Check(txt)
			{
			    TextCount=txt.val().length;  
			    if(TextCount>=200){
			    	alert("最长输入200个字符！");
			    }
			//获取文本框的长度
			    $("#dyContentLength").text(TextCount+'/200');   
			//将长度显示在div中
			}
			
			$("#personal_info").bind('input',function(){
				Check($(this));
			})
		</script>
	</body>

</html>