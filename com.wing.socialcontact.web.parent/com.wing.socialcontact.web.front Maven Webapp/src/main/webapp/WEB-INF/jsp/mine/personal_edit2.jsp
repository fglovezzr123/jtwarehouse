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
			<textarea id ="personal_info" name="" rows="" cols="" placeholder="请输入..."  maxLength=10><c:if test="${flag==1}">${user.nickname}</c:if><c:if test="${flag==2}">${user.mobile}</c:if><c:if test="${flag==3}">${user.user_profile}</c:if><c:if test="${flag==4}">${user.user_signature}</c:if></textarea>
			<p  id="dyContentLength">0/10</p>
		</div>
		<div class="pst">
			<div class="M-footer active_A" onclick="add_info('${flag}');">
				完成
			</div>
		</div>
		
		<script type="text/javascript">
			var add_info=function(flag){
				var nickname='';
				var mobile='';
				var user_profile='';
				var user_signature='';
				var temp=$("#personal_info").val();
				if(flag==1){
					nickname=temp;
					if(nickname.length>20) 
			        { 
			           alert_back("昵称的长度不能超过10！",function(){
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
			        
			       var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
			       if(!myreg.test(mobile)) 
			       { 
			           alert_back("请输入有效的手机号码！",function(){
							$("#personal_info").focus();
						});
			           return false;
			       } 
				}else if(flag==3){
					user_profile=temp;
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
					},
					success: function(data,res){
						if(res.code == 0){
							if(parent){
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
							}
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
				$("#dyContentLength").text(len+'/10');
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
			    if(TextCount>=10){
			    	alert("最长输入10个字符！");
			    }
			//获取文本框的长度
			    $("#dyContentLength").text(TextCount+'/10');   
			//将长度显示在div中
			}
			
			$("#personal_info").bind('input',function(){
				Check($(this));
			})
		</script>
	</body>

</html>