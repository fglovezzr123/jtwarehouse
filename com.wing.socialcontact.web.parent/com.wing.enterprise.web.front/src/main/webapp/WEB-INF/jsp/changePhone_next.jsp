<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title>变更手机号</title>
    <style>
         strong,b{font-weight:normal;}
    </style>
</head>
<body>
<div class="wrap">
	<header>
    	<a href="${path}/m/qfy/changePhone.do"><span class="header_return"><img src="${path}/resource/images/return.png"></span></a>
        <h1>变更手机号</h1>
        
    </header>
	<section>
    	<h3 class="bgsjh_h3">更改手机号后请使用新手机号登录</h3>
    	<div class="ggsjh_box">
        	<div class="ggsjh_sjh"><span>+86</span><input id="mobile" name="mobile" type="text" placeholder="请填写手机号" class="ggsjh_input"></div>
            <div class="ggsjh_yzm"><a id="send_code">获取验证码</a><input id="dyz" name="dyz" type="text" placeholder="请输入验证码" class="ggsjh_input"></div>
        </div>
        <div class="button_box"><button type="button" onclick="sub();">立即提交</button></div>
  </section>
</div>
</body>
</html>
<script>
var send_code_flag=true;
var timer;
var sub = function(){
	var mobile=$("#mobile").val();
	if(isEmpty(mobile)){
		layer.msg("请输入你要变更的手机号");
		$("#mobile").focus();
		return;
	}
	
	if(mobile.length != 11){
		layer.msg("常用手机号格式不正确");
		$("#mobile").focus();
		return;
	}
	
	var dyz=$("#dyz").val();
	if(isEmpty(dyz)){
		layer.msg("请输入手机验证码");
		$("#dyz").focus();
		return;
	}
	
	layer.confirm('确定修改手机号吗?', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			changeMobilePhone(mobile,dyz);
		}, function(){
		  
		});
// 	layer.confirm('确定修改手机号吗，如果之前在天九云商城注册过，天九云商城手机号会同步修改!', {
// 		  btn: ['确定','取消'] //按钮
// 		}, function(){
// 			changeMobilePhone(mobile,dyz);
// 		}, function(){
		  
// 		});
}
function changeMobilePhone(mobile,dyz){
	zdy_ajax({
		url: "${path}/m/qfy/user/mmobile.do",
		showLoading:false,
		data:{
			mmobile:mobile,
			vcode:dyz
		},
		success: function(data,res){
			layer.alert('修改手机号成功', function(index){
				   top.location.href="${path}/m/qfy/changePhone.do";
				  layer.close(index);
				}); 
		},
	    error:function(e){
	    }
	}); 
}
$(function(){
	$("#send_code").click(function(){
		if(send_code_flag){
			var obj=$(this);
			var mobile=$("#mobile").val();
			if(isEmpty(mobile)){
				layer.msg("请输入你要变更的手机号");
				$("#mobile").focus();
				return;
			}
			if(mobile.length != 11){
				layer.msg("常用手机号格式不正确");
				$("#mobile").focus();
				return;
			}
			zdy_ajax({
				url: "${path}/m/qfy/user/validPhone.do",
				showLoading:false,
				data:{
					phone:mobile
				},
				success: function(data,res){
					if(res.code == 0){
						if(res.dataobj=="h"){
							layer.msg("该手机号已被绑定，请重新输入");
							$("#mobile").focus();
							return;
						}else{
							zdy_ajax({
								url:"${path}/m/app/send_code.do",
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
					}
				},
			    error:function(e){
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