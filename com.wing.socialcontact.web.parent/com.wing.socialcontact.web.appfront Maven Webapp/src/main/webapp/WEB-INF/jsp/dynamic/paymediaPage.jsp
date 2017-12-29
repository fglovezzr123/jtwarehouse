<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="语音支付">
		<title>语音支付</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css?v=${sversion}"/>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?s=3?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/issuePk.css?v=${sversion}" />
		
	</head>

	<body>
		<body style="background: #f2f3f4;">
		<div class="I-Pk" style="background: #f2f3f4;width: 100%;">
			<div class="p-content show">
				<div class="pk-con">
					<p><span>收听该语音需要支付     </span><span style="color:red">${dynamicObj.mediaPrice}J币</span></p>
					<input type="hidden" name="jcount" id="jcount" value="${dynamicObj.mediaPrice}"  />
				</div>
				<div class="pk-con">
					<p><span>您的账户J币余额为     </span><span style="color:red">${jbAmount}J币</span></p>
				</div>
				
			</div>
			<div class="submit active_A" onclick="reward_submit();" id="reward">支付</div>
			<div class="submit active_A" onclick="recharge();" id="recharge" >充值</div>
			
		</div>
		
		<script src="${path}/resource/js/libs/public.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
	</body>
<script type="text/javascript">
$(function(){
	
	if(parseFloat("${dynamicObj.mediaPrice}") <= parseFloat("${jbAmount}") ){
		$("#reward").show();
		$("#recharge").hide();
	}else{
		$("#reward").hide();
		$("#recharge").show();
	}
});
//点击打赏按钮
var reward_submit=function(){
	var jcount=$("#jcount").val();
	if(isEmpty(jcount)){
		alert_back("支付数量不能为空",function(){
			$("#jcount").focus();
		});
		return;
	}
	
	 if(jcount < ${dynamicObj.mediaPrice}){
		 alert_back("支付值不能小于该语音的价格!",function(){
			$("#jcount").focus();
		});
		return;
	  }
	 zdy_ajax({
			url: "${path}/m/dynamic/isReward.do",
			data:{
				dynamicId:'${dynamicObj.id}',
			},
			success: function(data,res){
				if(res.code == 0){
					if(res.msg==0){
						reward_submit1();
					}else{
						alert("你已打赏过此动态");
					}
				}
			},
		    error:function(e){
			   //alert(e);
		    }
		});
}
//点击打赏按钮
var reward_submit1=function(){
	var jcount=$("#jcount").val();
	if(isEmpty(jcount)){
		alert_back("支付数量不能为空",function(){
			$("#jcount").focus();
		});
		return;
	}
	
	 if(jcount < ${dynamicObj.mediaPrice}){
		 alert_back("支付值不能小于该语音的价格!",function(){
			$("#jcount").focus();
		});
		return;
	  }
	 zdy_ajax({
		url: '${path}/m/dynamic/paymedia.do',
		data:{
			dynamicId:'${dynamicObj.id}',
			jcount:jcount
		},
		success: function(data,output){
			if(output.code == 0){
				alert_back("支付成功",function(){
					/*if(parent){
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						//parent.location.reload(); // 父页面刷新  
						parent.layer.close(index);
					}*/
					window.self.location = "${path}/m/sys/index.do";
				});
				
			}
		},
		error:function(e){
			//alert(e);
		}
	});
}
//判断是数字
function isNumberCount(obj){
	  var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
	  if(obj.value.indexOf(" ")!= -1){
		  alert("您输入的值存在空格，请检查！");
		  obj.value="1";
		  return null;
	  }
	  if((!reg.test(obj.value))){
	   alert("请输入正确的数值!");
	   obj.value="1";
	   return null;
	  }
	  
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