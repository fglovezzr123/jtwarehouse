<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">充值</title>
    	<style type="text/css">
			.cz{
				width: 100%;
				padding: 0 .30rem;
				box-sizing: border-box;
				background: #fff;
				font-size: .24rem;
			}
			.items{
				width: 100%;
				height: .96rem;
				line-height: .96rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
				box-sizing: border-box;
				border-bottom: 1px #F2F3F4 solid;
			}
			.items .input{
				font-size: .24rem;
				color: #8B8B8B;
				width: 5.2rem;
			}
			.C-footer{
				width: 100%;
				height: 1rem;
				font-size: .30rem;
				background: #0f88eb;
				color: #fff;
				text-align: center;
				line-height: 1rem;
				position: fixed;
				left: 0;
				bottom: 0;
			}
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<div class="cz">
			<div class="items">
				<h4><input type="radio" name="czType" value="1"/>充值金额</h4>
				<input type="text" class="input" name="je" id="je1" value="" placeholder="请输入你要充值金额" disabled="disabled" onkeyup="clearNoNum(this);" maxlength="8"/>
			</div>
			<div class="items">
				<h4><input type="radio" name="czType" value="2"/>充值J币</h4>
				<input type="tel" class="input" name="je" id="je2" value="" placeholder="请输入你要充值金额" disabled="disabled" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');" maxlength="8"/>
				<br/>
				J币人民币兑换比例为（${jb_rmb_show }）
			</div>
			<div class="items">
				<h4><input type="radio" name="czType" value="3"/>互助宝充值</h4>
				<input type="text" class="input" name="je" id="je3" value="" placeholder="请输入你要充值金额" disabled="disabled" onkeyup="clearNoNum(this);" maxlength="8"/>
			</div>
		</div>
		<div class="C-footer" id="cz_button">充值</div>
		<script type="text/javascript">
			var wxflag=false;
			$(function(){
				$(".items").click(function(){
					$(this).find("input[name='czType']")[0].click();
				});
				
				$("input[name='czType']").click(function(){
					$("input[name='je']").attr("disabled",true);
					$(this).parent().parent().find("input[name='je']").attr("value","").removeAttr("disabled");
				});
				
				
				$("#cz_button").click(function(){
					var czTypeObj=$("input[name='czType']:checked");
					if(czTypeObj.length == 0){
						alert_back("请选择充值类型",function(){
						});
						return;
					}
					var czType=czTypeObj.val();
					var je="";
					var je_id="";
					if(czType == 1){
						je=$("#je1").val();
						je_id="je1";
					}else if(czType == 2){
						je=$("#je2").val();
						je_id="je2";
					}else{
						je=$("#je3").val();
						je_id="je3";
					}
					if(isEmpty(je)){
						alert_back("请输入充值金额",function(){
							//$("#"+je_id).focus();
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
					                	self.location.href="${path}/m/my/wallet_cz.do";
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