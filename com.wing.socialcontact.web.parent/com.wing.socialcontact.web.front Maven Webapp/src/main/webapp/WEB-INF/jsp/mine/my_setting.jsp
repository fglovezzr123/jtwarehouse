<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="keywords" content="设置" />
<title>设置</title>
<style>
	.mdr{
		width:100%;
		padding: 0 .3rem;
		background: #fff;
		display:flex;
		font-size: .30rem;
		box-sizing: border-box;
		height: .98rem;
	    justify-content: space-between;
	    align-items: center;
	    border-bottom: 1px solid #ccc;
	}
	.mdr span.mdr{
	    background: url(/wxfront/resource/img/icons/mdr.png) no-repeat center;
	    background-size: 0.34rem;
	    background-position-x: 0;
	    padding-left: .45rem;
	}
	
	.mdr span.xy{
	    background: url(/wxfront/resource/img/icons/xy.png) no-repeat center;
	    background-size: 0.34rem;
	    background-position-x: 0;
	    padding-left: .45rem;
	}
	
	.mdr span.bz{
	    background: url(/wxfront/resource/img/icons/bz.png) no-repeat center;
	    background-size: 0.34rem;
	    background-position-x: 0;
	    padding-left: .45rem;
	}
	
	.btn-z{
		display: block;
		width: 6.9rem;
		height: .80rem;
		color: #fff;
		background: #FF0000;
		text-align: center;
		line-height: .80rem;
		border-radius: .40rem;
		margin: .55rem 0 0 .30rem;
		font-size: .30rem;
	}
	
	.btn-z.active_A:before{
		border-radius: .40rem;
	}
</style>
</head>
<body>
	<div class="B-friendInfo"style="background: #f2f3f4; width: 100%;">
		<div class="active_A mdr" onclick="my_disturb_set();">
			<span class="mdr">防打扰设置</span>
		 	<i class="iconfont">&#xe605;</i>
		</div>
		<div class="active_A mdr" onclick="zcxy();">
			<span class="xy">用户注册协议</span>
		 	<i class="iconfont">&#xe605;</i>
		</div>
		<a href="http://sms.tojoycloud.com/help/index.html">
		<div class="active_A mdr">
			<span class="bz">使用帮助</span>
		 	<i class="iconfont">&#xe605;</i>
		</div>
		</a>
	</div>
	<button class="btn-z active_A" onclick="logout();">退出登录</button>
	
		
	<script type="text/javascript">
	   	var my_disturb_set=function(){
			self.location.href="${path}/m/my/my_disturb_set.do";
		}
	   	var zcxy=function(){
	   		self.location.href="${path}/wx/register/userLog.do";
	   	}
	   	var sybz=function(){
	   		self.location.href="http://sms.tojoycloud.com/help/index.html";
/* 	   		self.location.href="${path}/m/sys/wz_show.do"; */
	   	}
	   	var logout=function(){
	   		confirm("确认要退出登录吗？",function(t){
	   			if(t == 1){
	   				zdy_ajax({
	   					url: '${path}/m/sys/logout.do',
	   					type: 'post',
	   					dataType: 'json',
	   					success: function(output){
	   						self.location.href="${path}/m/sys/index.do";
	   					}
	   		   		});
	   			}
	   		});
	   	}
	</script>
</body>

</html>