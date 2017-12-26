<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">充值类型选择</title>
    	<style type="text/css">
    	      body{
    	        
    	      }
			 .chargeWay{
		     	 width:35%;
		     	 height:0.7rem;
		     	 line-height:0.7rem;
		     	 font-size:0.3rem;
		     	 margin:0 auto;
		     	 text-align:center;
		     	 background:#0f88eb;
		     	 color:white;
		     	 border-radius:0.1rem;
		     	 margin-top:1rem;
		     }
		</style>
	</head>

	<body style="background: #f2f3f4;">
		<div class="chargeWay active_A" type="1">RMB余额充值</div>
		<div class="chargeWay active_A" type="2">J币充值</div>
		<div class="chargeWay active_A" type="3">互助宝充值</div>
		<script type="text/javascript">
			$(function(){
				$(".chargeWay").click(function(){
					var type=$(this).attr("type");
					if(type == 1){
						self.location.href="${path}/m/my/rmb_cz.do";
					}else if(type == 2){
						self.location.href="${path}/m/my/jb_cz.do";
					}else{
	       				var hzbOpenFlag="${user.hzbOpenFlag}";
	       				if(isEmpty(hzbOpenFlag) || hzbOpenFlag == 0 || hzbOpenFlag == 3){
	           				self.location.href="${path}/m/hzb/first.do";
	           				return;
	           			}else if(hzbOpenFlag == 2){
	           				alert("您的互助宝账户已停用，请联系客服");
	           				return;
	           			}
						self.location.href="${path}/m/my/hzb_cz.do";
					}
				});
			});
		</script>
	</body>
</html>