<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="keywords" content="免打扰设置" />
<title>免打扰设置</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/friendInfo.css?v=${sversion}" />
	<style>
		.myKf1{
	width:100%;
	padding: .18rem .4rem;
	background: #fff;
	 display:block;
	font-size: .260rem;
	box-sizing: border-box;
	margin-top: .08rem;
	overflow:hidden;
}
.myKf1 span:nth-of-type(1){
   width:1.4rem;
   float:left;
}
  .myKf1 span:nth-of-type(2){
  	margin-left:.2rem;
	color: #8B8B8B;
	float:left;
	width:4.6rem;
	 white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    overflow: hidden;
}
.myKf1 .iconfont22{
	display: inline-block;
	width: 0.34rem;
	height: 0.34rem;
	background-image: url(${path}/resource/img/icons/like.png);
	background-size: 100%;
	background-repeat: no-repeat;
	float:left;
	margin-top: 0.04rem;

}
#star_friend{
  width: 100%;
    padding: 0 .4rem;
    height: .76rem;
    background: #fff;
    display: flex;
    align-items: center;
    font-size: .26rem;
    box-sizing: border-box;
    margin-top: .08rem;
    position:relative;
}
.switchBtm{
	 position:absolute;
	 top:0.11rem;
	 right:0.4rem;
     width:0.94rem;
     height:0.54rem;
     background:url(${path}/resource/img/icons/OFF.png) no-repeat center;
     background-size:contain;
}
.on{
	background:url(${path}/resource/img/icons/oN.png) no-repeat center;
	 position:absolute;
	 top:0.11rem;
	 right:0.4rem;
     width:0.94rem;
     height:0.54rem;
     background-size:contain;
}
	</style>
</head>
<body>
	<div class="B-friendInfo"
		style="background: #f2f3f4; width: 100%;height: 100%;">
		
	
		<div class="active_A" id="star_friend" >
			<i class="iconfont3"></i> <span class="myKy-d">&nbsp;开启防打扰</span>
			<c:if test="${tjyuser.isdisturb == 0 }">
			<div class="switchBtm" yes='0' id='star'></div>
			</c:if>
			<c:if test="${tjyuser.isdisturb == 1 }">
			<div class="on" yes='1' id='star'></div>
			</c:if>
		</div>
		<div style=" font-size: .26rem;">
		温馨提醒：您开启防打扰功能后，则您不会被平台推荐，防止被人打扰。
		</div>
		
			
	</div>
	<script type="text/javascript">
	    var isdisturb=0;
		$(document).ready(function() {
								
							$('#star').on('click',function(){
					   		 if($(this).hasClass('on')){
					   			  $(this).attr('yes',0)
					   		 }else{
					   			 $(this).attr('yes',1)
					   		 }
					   	     var yes = $(this).attr('yes')
					   	     qxs(yes)
   	                        })
   	                        function qxs(yes){
								zdy_ajax({
									url: "${path}/m/my/UpdateIsdisturb.do",
								    showLoading:false,
									data:{
										isdisturb:yes,
									},
									success: function(data,res){
										if(res.code == 0){
											if(yes==0){
												$('#star').addClass('switchBtm').removeClass('on')
												layer.msg("成功取消免打扰");
											}
											if(yes==1){
												$('#star').addClass('on').removeClass('switchBtm')
												layer.msg("成功设置免打扰")
											}				
										}	 
									},
								    error:function(e){
									   //alert(e);
								    }
								}); 
							}

						});
		
						
		
	</script>
</body>

</html>