<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">RMB余额充值</title>
    	<style type="text/css">
			 .rmbCharge1{
		     	text-align:left;
		     	font-size:0.3rem;
		     	width:95%;
		     	margin:0 auto;
		     	height:0.7rem;
		     	line-height:0.7rem;
		     }
		     .rmbCharge{
		     	width:90%;
		     	margin:0 auto;
		     	position:relative;
		     	text-align:center;
		     
		     	line-height:0.7rem;
		     }
		     .rmbCharge input{
		     	  width:90%;
		     	  height:0.7rem;
		     	  border-radius:0.1rem;
		     }
             .rmbCharge-btm{
             	  width:3rem;
             	  background:#0f88eb;
             	  color:white;
             	  font-size:0.3rem;
             	  height:0.7rem;
             	  margin:0 auto;
             	  line-height:0.7rem;
             	  text-align:center;
             	  border-radius:0.1rem;
             	  margin-top:0.4rem;
             }
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<div class="rmbCharge1">RMB余额充值</div>
		<div class="rmbCharge"><input type="text" name="je" id="je" value="" placeholder="请输入你要充值金额" onkeyup="clearNoNum2(this);" maxlength="8"/></div>
		<div class="rmbCharge-btm active_A" id="cz_button">充值</div>
		<script type="text/javascript">
			var wxflag=false;
			$(function(){
				
				$("#cz_button").click(function(){
					var czType=1;
					var je=$("#je").val();
					if(isEmpty(je)){
						alert_back("请输入充值金额",function(){
							//$("#je").focus();
						});
						return;
					}
					if(wxflag){
						if(zfflag){
							paywx(je,czType);
    					}else{
    						layer.msg("该功能尚未开通")
    					}
					}
				});
				
				function paywx(je,czType){
					wxflag=false;
					zdy_ajax({
						url :"${path}/m/my/paycwx.do", 
					    type : 'post', 
					    dataType : 'json', 
					    data:{
					    	je:je,
					    	czType:czType
					    },
					    success : function(data){
					    	var obj=data;
					    	if(parseInt(obj.agent)<5){  
				                alert("您的微信版本低于5.0无法使用微信支付");  
				                return;  
				            }  
				            WeixinJSBridge.invoke('getBrandWCPayRequest',{
				                "appId" : obj.appId,                  //公众号名称，由商户传入  
				                "timeStamp":obj.timeStamp,          //时间戳，自 1970 年以来的秒数  
				                "nonceStr" : obj.nonceStr,         //随机串  
				                "package" : obj.packageValue,      //<span style="font-family:微软雅黑;">商品包信息</span>  
				                "signType" : obj.signType,        //微信签名方式:  
				                "paySign" : obj.paySign           //微信签名  
							},function(res){
				                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
				                	//返回我的钱包
				                	self.location.href="${path}/m/my/personal_wallet.do";
				                }else{ 
				                	layer.msg("取消支付",{
				                	    icon: 2,
				                	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
				                	}, function(){
					                	wxflag=true;
					                	self.location.href="${path}/m/my/rmb_cz.do";
				                	});
				                    //window.location.href="/nailart_index";     
				                    //<span style="font-family:微软雅黑;">当失败后，继续跳转该支付页面让用户可以继续付款，贴别注意不能直接调转jsp，</span><span style="font-size:10.5pt">不然会报</span><span style="font-size:12.0pt"> system:access_denied。</span>  
				                }
				            }); 
					    },
					    error : function(){
					    	wxflag=true;
					    }
				   	});
				}
				if (typeof WeixinJSBridge == "undefined"){
		 		   if( document.addEventListener ){
		 		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		 		   }else if (document.attachEvent){
		 		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
		 		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		 		   }
		 		}else{
		 		   onBridgeReady();
		 		}
			});
			
			function onBridgeReady(){
				wxflag=true;
			}
		</script>
	</body>
</html>