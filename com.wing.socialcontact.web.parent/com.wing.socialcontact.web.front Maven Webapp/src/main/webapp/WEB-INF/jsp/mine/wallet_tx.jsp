<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">提现</title>
    	<style type="text/css">
			.cz{
				width: 100%;
				padding: 0 .30rem;
				box-sizing: border-box;
				background: #fff;
				font-size: .24rem;
				/*margin-top: .1rem;*/
			}
			.items{
				width: 100%;
				height: .76rem;
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
				<h4>钱包余额</h4>
				<input type="text" class="input" value='<fmt:formatNumber type="number" value="${user.availablebalance}" maxFractionDigits="2"></fmt:formatNumber>' readonly="readonly"/>
			</div>
			<div class="items">
				<h4>提现金额</h4>
				<input type="tel" class="input" name="je" id="je" value="" placeholder="请输入你要提现金额" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');"/>
			</div>
		</div>
		<div class="C-footer" id="cz_button">提现</div>
		<script type="text/javascript">
			$(function(){
				$("#cz_button").click(function(){
					var je=$("#je").val()*1;
					if(isEmpty(je)){
						alert_back("请输入提现金额",function(){
							//$("#"+je_id).focus();
						});
						return;
					}
					if(je > '${user.availablebalance }'*1){
						alert_back("提现金额不能大于钱包余额",function(){
							//$("#"+je_id).focus();
						});
						return;
					}
					if(je < 1  || je > 20000){
						alert_back("提现金额范围1-20000",function(){
							//$("#"+je_id).focus();
						});
						return;
					}
					tixian(je);
				});
				
				function tixian(je){
					wxflag=false;
					zdy_ajax({
						url :"${path}/m/my/tixian.do", 
					    type : 'post', 
					    dataType : 'json', 
					    data:{
					    	je:je
					    },
					    success : function(data){
					    	alert_back("提现申请已提交",function(){
			                	self.location.href="${path}/m/my/personal_wallet.do";
					    	});
					    }
				   	});
				}
			});
		</script>
	</body>
</html>