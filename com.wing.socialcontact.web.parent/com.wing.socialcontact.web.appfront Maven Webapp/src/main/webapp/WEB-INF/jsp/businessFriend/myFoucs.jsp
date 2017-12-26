<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="关注" />
		<title>关注</title>
		
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/myFoucs.css?v=${sversion}" />
		
	</head>

	<body>
		<div class="B-myFoucs" style="background: #f2f3f4; width: 100%;height: 100%;">
			<div class="myFoucs">
				<span class="mF mF-active my1" data-id="1">我的关注</span>
				<span style="color:#e3e3e3">|</span>
				<span class="mF my2" data-id="2">我的粉丝</span>
			</div>
			<div class="myF my show-active">
			</div>
			<div class="myFs my ">
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('.myFoucs').find($('.mF')).on('click', function() {
					var index = $(this).index();
					if(index == 2){
						index = index - 1;
					}
					$(this).addClass('mF-active').siblings().removeClass('mF-active');
					$('.my').eq(index).addClass('show-active').siblings().removeClass('show-active');
				});
				/*我的关注数量  */
				zdy_ajax({
					url: _path+"/im/m/selMyFollowCount.do",
				    showLoading:false,
					success: function(data,res){
						console.log(res); 
						var num =res.dataobj;
						$('.myFoucs').find($('.my1')).html('我的关注('+num+')');
					},
				    error:function(e){
					   //alert(e);
				    }
				});
				//我的关注列表
				zdy_ajax({
					url: _path+"/im/m/selMyFollowList.do",
				    showLoading:true,
				    data:{
				    	'pageNum':1,
				    	'pageSize':100
				    },
					success: function(data,res){
						/* console.log(data) */
						$.each(data,function(index){
							var str = '<div class="info active_A infoId " data_id="'+data[index].id+'" data_user="'+data[index].follow_user+'"><div class="info-l"><img src="'+data[index].head_portrait+'" class="p-img" /><div class="person"><span class="p-name">'+data[index].nickname+'</span><span class="company">'+data[index].job_name+'&nbsp;/'+data[index].com_name+'</span></div></div><i class="iconfont info-R">&#xe60f;</i></div>';	
							$('.myF').append(str);
        				});
						$('.info-l').on('click',function(){
							var follow_user= $(this).parents('.info').attr("data_user")
							window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+follow_user;
						})
						 $('.info-R').on('click',function(){
							var follow_user= $(this).parents('.info').attr("data_user")
							 zdy_ajax({
								url: _path+"/im/m/delMyFollowUser.do",
							    showLoading:true,
							    data:{
							    	followUser:follow_user,
							    },
								success: function(data,res){
									layer.msg(res.msg)
									$('.infoId').each(function(){
										if($(this).attr("data_user")==follow_user){
											$(this).remove()
											var num = $('.myFoucs').find($('.my1')).html()
											var num = sliceNum(num)-1
											$('.myFoucs').find($('.my1')).html('我的关注('+num+')')
										}
									})
								},
							    error:function(e){
							    }
							}); 
						})
						
					},
				    error:function(e){
					   //alert(e);
				    }
				});
				function sliceNum(num){
					var c=num.indexOf('(');
					var n =num.indexOf(')');
					var num = num.slice(c+1,n)
					return num;
				}
				/*我的粉丝数量  */
				zdy_ajax({
					url: _path+"/im/m/selMyFansCount.do",
				    showLoading:false,
					success: function(data,res){
						var num =res.dataobj;
						$('.myFoucs').find($('.my2')).html('我的粉丝('+num+')');
					},
				    error:function(e){
					   //alert(e);
				    }
				});
				/*我的粉丝列表  */
				zdy_ajax({
					url: _path+"/im/m/selMyFansList.do",
				    showLoading:true,
				    data:{
				    	'pageNum':1,
				    	'pageSize':10
				    },
					success: function(data,res){
						/* console.log(data.length);
						console.log(res); */
						$.each(data,function(index){
							var str = '<div class="info active_A" data_id="'+data[index].id+'" data_user="'+data[index].user_id+'"><div class="info-l"><img src="'+data[index].head_portrait+'" class="p-img" /><div class="person"><span class="p-name">'+data[index].nickname+'</span><span class="company">'+data[index].job_name+'&nbsp;/'+data[index].com_name+'</span></div></div></div>';
							$('.myFs').append(str);
        				});
						$('.info-l').on('click',function(){
							var follow_user= $(this).parents('.info').attr("data_user")
							window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+follow_user;
						})
					},
				    error:function(e){
					   //alert(e);
				    }
				});
			});
				
		</script>
	</body>

</html>
