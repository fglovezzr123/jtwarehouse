<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="新闻支付">
		<title>新闻支付</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css?v=${sversion}"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css?v=${sversion}" />
		
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
				<div class="pk-con">
					<p><span>查看该文章需要支付     </span><span style="color:red">${tjyNews.charge}J币</span></p>
					<input type="hidden" name="jcount" id="jcount" value="${tjyNews.charge}"  />
				</div>
				<div class="pk-con">
					<p><span>您的账户J币余额为     </span><span style="color:red">${jbAmount}J币</span></p>
				</div>
				
			</div>
			<div class="submit active_A" onclick="reward_submit();" id="reward">支付</div>
			<div class="submit active_A" onclick="recharge();"  id="recharge"  >充值</div>
			
		</div>
		
		<script src="${path}/resource/js/libs/public.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
$(function(){
	
	if(parseFloat("${tjyNews.charge}") <= parseFloat("${jbAmount}") ){
		$("#reward").show();
		$("#recharge").hide();
	}else{
		$("#reward").hide();
		$("#recharge").show();
	}
});
//点击支付按钮
var reward_submit=function(){
	var jcount=$("#jcount").val();
	if(isEmpty(jcount)){
		alert_back("支付数量不能为空",function(){
			$("#jcount").focus();
		});
		return;
	}
	
	 if(parseInt(jcount) < parseInt('${tjyNews.charge}')){
		 alert_back("支付值不能小于该文章的价格!",function(){
			$("#jcount").focus();
		});
		return;
	  }
	 zdy_ajax({
		url: '${path}/m/news/rewardJb.do',
		data:{
			fkId:'${tjyNews.id}',
			jcount:jcount
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("支付成功",function(){
					self.location=document.referrer;
				});
				
			}
		},
		error:function(e){
			//alert(e);
		}
	});
}

function recharge(){
	if(zfflag){
		window.self.location = "${path}/m/my/wallet_cz.do";
	}else{
		layer.msg("该功能尚未开通")
	}
}
</script>

</html>