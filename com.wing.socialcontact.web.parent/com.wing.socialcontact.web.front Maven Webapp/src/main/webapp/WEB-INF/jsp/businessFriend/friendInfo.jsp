<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="keywords" content="好友信息" />
<title>好友信息</title>
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
body{
  background: #f2f3f4;
}
	</style>
</head>
<body>
	
		<div class="info">
			<div class="info-l active_A">
				<img src="" class="tx" />
				<div class="person">
					<p>
						<span class="p-name" style="word-break: break-all;text-overflow: ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;max-width:1.6rem;"></span> <span class="profession"></span>
						<span style="margin-left: .1rem;"><img style="width:1.34rem;height:0.36rem;border-radius:0%;display:none;" src=""  class="tjbz" /></span>	
					</p>
					<p class="company"style="word-break: break-all;text-overflow: ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;"></p>
				</div>
			</div>
		</div>
		<div class="per contentPer active_A">
			<div class="per-l">
				<i class="iconfont1"></i> <span class="per-index">个人主页</span>
			</div>
			<div class="per-r">
				<!-- <span class="per-index">访问量900</span> --> <i class="iconfont">&#xe605;</i>
			</div>
		</div>
		<div class="active_A" id="star_friend" style="display: none">
			<i class="iconfont3"></i> <span class="myKy-d">&nbsp;设为心标好友</span>
			<!-- <span class="myky-x ">
				<input type="radio"  name="sf" value="0" />非星标
	            <input type="radio" name="sf" value="1" />星标
			</span> -->
			<div class="switchBtm" yes='0' id='star'></div>
		</div>
		<div class="myKf contentPer active_A">
			<i class="iconfont2"></i> <span class="myKy-d">&nbsp;地区</span>
			<span class="myky-x diqu"></span>
		</div>
		<div class="myKf contentPer active_A">
			<i class="iconfont3"></i> <span class="myKy-d">&nbsp;行业</span>
			<span class="myky-x hy"></span>
		</div>
		<div class="myKf1 contentPer active_A">
			<i class="iconfont22"></i> <span class="myKy-d">&nbsp;个人简介</span>
			<span class="myky-x jj"></span>
		</div>
		<div class="myKf contentPer active_A" style="height:auto;padding-top:0.2rem;padding-bottom:0.2rem;">
			<i class="iconfont5"></i> <span class="myKy-d">&nbsp;爱好</span>
			<span class="myky-x like"></span>
		</div>
		<button class="active_A send">发消息</button>
		<button class="active_A del">删除好友</button>
		<button class="active_A add">添加</button>
	
	<script type="text/javascript">
	    var star_flag=0;
		$(document).ready(function() {
		                             
		                   
			                var fromUserId = ${sessionScope.me.id };
							var tjyUserId = window.location.href.split("=")[1]
							console.log(tjyUserId)
							var friend = false;
							function del() {
					 			var datas='';
								 var myStorage=localStorage.getItem("recentContacts");
								 if(myStorage){
									datas=JSON.parse(myStorage);
									if(datas[tjyUserId]){
									    delete	datas[tjyUserId];
										localStorage.setItem("recentContacts",JSON.stringify(datas));
									}
									
								 }
					 		}
								
							if(tjyUserId == fromUserId ){
								
							}else{
								zdy_ajax({
									url : _path + "/im/m/selMyFriendList.do",
									showLoading : false,
									data : {
										'pageNum' : 1,
										'pageSize' : 100,
										'isAll':1
									},
									success : function(data, res) {
										console.log(data);
										$.each(data,function(index) {
											if (tjyUserId == data[index].friend_user) {
											    friend = true;
											    star_flag = data[index].star_flag;
											    if(star_flag==1){
											    	$('#star').addClass('on').removeClass('switchBtm')
											    }
												console.log(data[index].friend_user)
												return;
											}
										})
										console.log(friend);
										iSfriend(friend)	    		

									},
									error : function(e) {
										//alert(e);
									}
								});
							}						
							function iSfriend(friend){
								
								if (friend) {
									$('.send').css({
										'display' : "block"
									})
									$('.del').css({
										'display' : "block"
									})
								    $("#star_friend").show();
								} else {
									$('.add').css({
										'display' : "block"
									})
									$('.add').on("click",function(){
										window.location.href = _path+ "/wx/businessFriend/applyToAddGroup.do?follow_user="+ tjyUserId;
									})

								}
							}
							//个人信息
							zdy_ajax({
								url : _path
										+ "/im/m/selTjyUserInfoByTyjUserid.do",
								showLoading : true,
								data : {
									"tjyUserId" : tjyUserId
								},
								success : function(data, res) {
									console.log(data);
									$(".tx").attr("src",data.head_portrait)
									$(".info .p-name").html(data.nickname);
									if(data.level==undefined||data.level==''){
										$(".info .profession").html(data.job_name);
									}else{
										$(".info .profession").html(data.job_name+" "+data.level);
									}
								 	
									$(".info .company").html(data.com_name);
								 	if(data.honor_flag=='honor_004'){
								 		$(".tjbz").attr("src","../../resource/img/icons/tjfws.png");
								 		$(".tjbz").show();
								 	}else if(data.honor_flag=='honor_003'){								 		
								 		$(".tjbz").attr("src","../../resource/img/icons/tjhb.png");
								 		$(".tjbz").show();
								 	}else if(data.honor_flag=='honor_002'){								 		
								 		$(".tjbz").attr("src","../../resource/img/icons/tjyq.png");
								 		$(".tjbz").show();
								 	}else if(data.honor_flag=='honor_001'){
								 		$(".tjbz").attr("src","../../resource/img/icons/tjjr.png");
								 		$(".tjbz").show();
								 	}else{
								 		$(".tjbz").hide();
								 		$(".tjbz").attr("src","")
								 	}
								 	var k1='', k2='',k3='';
								 	if(data.province_name){
								 		var k1 = data.province_name;
								 	}
								 	if(data.province_name){
								 		var k2 = data.city_name;
								 	}
								 	if(data.province_name){
								 		var k3 = data.county_name;
								 	}
									$(".diqu").html(
											k1 + "&nbsp;&nbsp;"
													+ k2
													+ "&nbsp;&nbsp;"
													+ k3);
									$(".jj").html(data.user_profile)
									$(".hy").html(data.industry_name)
									var like = ''
									$.each(data.favList,function(index){
										like+= data.favList[index].fav_name+"&nbsp;&nbsp;"
									})
									$(".like").html(like)
									$(".per").on("click",function() {
										window.location.href = _path+ "/m/my/personal_home2.do?userId="+ tjyUserId;
													})
									var follow_user = data.id;
									var head_portrait = data.head_portrait;
									var nickName = data.nickname;
									var objId = new Object()
									$(".send").on("click",function() {
														objId.follow_user = follow_user;
														objId.head_portrait = head_portrait;
														objId.nickName=nickName;
														var objStr = JSON.stringify(objId);
														sessionStorage.setItem('user_info',objStr);
														console.log(objStr);
														window.location.href = _path+ "/wx/businessFriend/talkAbout.do"+window.location.search;
													})
									$(".del").on("click",function() {
														zdy_ajax({
															url : _path+ "/im/m/delMyFriendByFriendUserid.do",
															showLoading : false,
															data : {
																friendUserId : tjyUserId
															},
															success : function(data, res) {
																del();
																layer.msg(res.msg)
																
																 setTimeout(function(){
																	 window.location.href = _path+ "/wx/businessFriend/businessFriend.do"
						                                          },1500)
															},
															error : function(e) {
																
															}
														});
													}) 

								},
								error : function(e) {
									//alert(e);
								}
							});
							$('.per-index').on("click",function(){
								
							})
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
									url: "${path}/im/m/UpdateStarFlagByFriendUserid.do",
								    showLoading:false,
									data:{
										friendUserId : tjyUserId,
										starFlag:yes,
									},
									success: function(data,res){
										if(res.code == 0){
											if(yes==0){
												$('#star').addClass('switchBtm').removeClass('on')
												layer.msg("成功取消心标好友");
											}
											if(yes==1){
												$('#star').addClass('on').removeClass('switchBtm')
												layer.msg("成功设置心标好友")
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