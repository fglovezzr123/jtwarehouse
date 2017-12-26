<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
        <meta name="keywords" content="注册绑定">
        <title>注册绑定</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/register.css"/>
		<style type="text/css">
		 
		</style>
    </head>
    <body>
    	<div class="Z-register-login" style="background: #f2f3f4;width: 100%;height: 100%;">
	       	<div class="Z-content">
	       		<!-- 
	       		<div class="cphone">
	       			<img src="${path}/resource/img/icons/rs_03.png"/>
	       			<input type="text" id="nickName" name="nickName" value="" maxlength="20" class="phone pz" placeholder="请输入昵称"/>
	       		</div>
	       		 -->
	       		<div class="cphone">
	       			<img src="${path}/resource/img/icons/rs_03.png"/>
	       			<input type="number" id="mobile" name="mobile" onkeyup="value=value.replace(/[^\d]/g,'')" value="" maxlength="11" class="phone pz" placeholder="请输入常用的手机号注册"/>
	       		</div>
	       		<%-- <div class="cphone">
	       			<img src="${path}/resource/img/icons/r2_03.png"/>
	       			<div class="zr pz">
	       				<input type="text" id="yz" value="" class="yz" placeholder="请输入验证码" style="width:4.5rem"/>
	       				<img id="img_vercode" src="${path}/m/sys/imgNum.do?_t='+new Date()" onclick="this.src='${path}/m/sys/imgNum.do?_t='+new Date()"  alt="验证码"/>
	       			</div>
	       		</div> --%>
	       		<div class="cphone">
	       			<img src="${path}/resource/img/icons/r3_03.png"/>
	       			<div class="zr pz">
	       				<input type="text" id="dyz" value="" class="dyz" placeholder="请输入短信验证码"/>
	       				<span class="active_A" id="send_code">获取验证码</span>
	       			</div>
	       		</div>
	       		<!-- 
	       		<div class="cphone">
	       			<img src="${path}/resource/img/icons/r4_03.png"/>
	       			<input type="password" id="pwd" name="pwd" value="" class="psd pz" placeholder="设置密码&nbsp;(6-12位)"/>
	       		</div>
	       		<div class="cphone">
	       			<img src="${path}/resource/img/icons/r5_03.png"/>
	       			<input type="password" id="qpwd" name="qpwd" value="" class="cpsd pz" placeholder="重复输入确认密码"/>
	       		</div>
	       		-->
	       	</div>
	       	
	       	<button class="btn-z active_A" onclick="reg();">立即注册</button>
     		<div class="btn btnnobg" style="display: none;"><a href="${path}/m/sys/loginPage.do">已有账号登录>></a></div>
     		<div class="tip" ><input type="checkbox" name="jq" id="jq" value="" checked>我已同意并阅读<a href="${path}/wx/register/userLog.do">《用户注册协议》</a></div>
	    </div>
	    <script type="text/javascript">
	      	 $(function(){
	    		 if(!is_weixn()){
	    			alert_back("请在微信中完成注册",function(){
		    			self.location.href="${path}/m/sys/index.do";
	    			});
	    		}  
	    	});  
	    	$('#jq').on('click',function(){
	    	
	    	    if($(this).prop('checked')){
	    	      $('button').attr('disabled',false)
	    	       $('button').css({'background':'#0f88eb','color':'#fff'})
	    	    }else{
	    	     $('button').attr('disabled',true)
	    	     $('button').css({'background':'#ccc','color':'#999'})
	    	    }
	    	
	    	})
	    	
	    	var send_code_flag=true;
			var timer;
			var reg=function(){
				var nickName="";
				//var nickName=$("#nickName").val();
				//if(isEmpty(nickName)){
				//	alert_back("昵称不能为空",function(){
				//		$("#nickName").focus();
				//	});
				//	return;
				//}
				var mobile=$("#mobile").val();
				if(isEmpty(mobile)){
					alert_back("手机号不能为空",function(){
						$("#mobile").focus();
					});
					return;
				}
				
				if(!check_mobile(mobile)){
					alert_back("手机号格式不正确",function(){
						$("#mobile").focus();
					});
					return;
				}
				
				//var yz=$("#yz").val();
				//if(isEmpty(yz)){
				//	alert_back("验证码不能为空",function(){
				//		$("#yz").focus();
				//	});
				//	return;
				//}
				
				var dyz=$("#dyz").val();
				if(isEmpty(dyz)){
					alert_back("手机验证码不能为空",function(){
						$("#dyz").focus();
					});
					return;
				}
				
				//var pwd=$("#pwd").val();
				//if(isEmpty(pwd)){
				//	alert_back("密码不能为空",function(){
				//		$("#pwd").focus();
				//	});
				//	return;
				//}
				
				//var qpwd=$("#qpwd").val();
				//if(pwd != qpwd){
				//	alert_back("两次密码不一致",function(){
				//		$("#pwd").focus();
				//	});
				//	return;
				//}
			
				zdy_ajax({
					url: '${path}/m/sys/regSave.do',
					type: 'post',
					dataType: 'json',
					data:{
						nickName:nickName,
						mobile:mobile,
						//yz:yz,
						//pwd:pwd,
						dyz:dyz
					},
					success: function(output){
						alert_back("注册绑定成功",function(){
							var last_url="${last_url}";
							if(isEmpty(last_url)){
								last_url="${path}/m/sys/index.do";
							}
							top.location.href=last_url;
						});
					}
				});
			}
			$(function(){
				$("#send_code").click(function(){
					if(send_code_flag){
						var obj=$(this);
						var mobile=$("#mobile").val();
						if(isEmpty(mobile)){
							alert_back("请输入您的手机号码",function(){
								$("#mobile").focus();
							});
							return;
						}
						if(!check_mobile(mobile)){
							alert_back("手机号格式不正确",function(){
								$("#mobile").focus();
							});
							return;
						}
						/* var yz=$("#yz").val();
						if(isEmpty(yz)){
							alert_back("验证码不能为空",function(){
								$("#yz").focus();
							});
							return;
						} */
						zdy_ajax({
							url:"${path}/m/sys/send_code.do",
							data:{
								mobile:mobile,
								//yz:yz,
								type:'reg'
							},
							dataType:"json",
							type:"post",
							success:function(data,res){
								send_code_flag=false;
								//obj.addClass("btn-yzm-gray");//改变样式
								obj.html("<label id='timer_num'>60</label>s后重新获取");//改变文字
								//开启倒计时定时器
								timer=setInterval("changeTimer();",1000);
							}
						});
					}
				});
			});
			var changeTimer=function(){
				var timer_num=$("#timer_num").text();
				if(timer_num*1 <= 0){
					clearInterval(timer);
					send_code_flag=true;
					$("#send_code").text("获取验证码");
					return;
				}
				$("#timer_num").text(timer_num*1-1);
			}
		</script>
    </body>
</html>