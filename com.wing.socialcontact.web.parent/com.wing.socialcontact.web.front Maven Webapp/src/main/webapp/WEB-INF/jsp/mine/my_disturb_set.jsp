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
<style>

#star_friend{
   width:100%;
	padding: 0 .3rem;
	background: #fff;
	display:flex;
	font-size: .30rem;
	box-sizing: border-box;
	height: .8rem;
    justify-content: space-between;
    align-items: center;
}
#star_friend span{
    background: url(/wxfront/resource/img/icons/hy.png) no-repeat center;
    background-size: 0.27rem 0.31rem;
    background-position-x: 0;
    padding-left: .45rem;
}
.switchBtm{
     width:0.94rem;
     height:0.54rem;
     background:url(${path}/resource/img/icons/OFF.png) no-repeat center;
     background-size:100%;
}
.on{
	 background:url(${path}/resource/img/icons/oN.png) no-repeat center;
     width:0.94rem;
     height:0.54rem;
     background-size:100%;
}
.tjts{
   font-size: .30rem;
   color:#0f88eb;
   padding:.3rem;
   box-sizing: border-box;
}
	</style>


	
</head>
<body>
	<div class="B-friendInfo"
		style="background: #f2f3f4; width: 100%;height: 100%;">
		<div class="active_A" id="star_friend" >
			<span class="myKy-d">&nbsp;开启防打扰</span>
			<c:choose>
				<c:when test="${tjyuser.isdisturb == 1 }"><div class="on" yes='1' id='star'></div></c:when>
				<c:otherwise><div class="switchBtm" yes='0' id='star'></div></c:otherwise>
			</c:choose>
			<%-- <c:if test="${tjyuser.isdisturb == 0 }">
			   <div class="switchBtm" yes='0' id='star'></div>
			</c:if>
			<c:if test="${tjyuser.isdisturb == 1 }">
			  <div class="on" yes='1' id='star'></div>
			</c:if> --%>
		</div>
		<div class="tjts">
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