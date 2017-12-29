<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">群通知</title>

		<link rel="stylesheet" href="${path}/resource/css/messages.css?v=${sversion}" />

		<style>
			.content {
				margin-top: 0px;
			}
			.textDate{
			margin-top: .33rem;
			}
			.textDate button {
				width: 0.75rem;
				height: 0.5rem;
				color: white;
				font-size: 0.24rem;
				border-radius: 0.1rem;
				background: gray;
			}
			
			.textDate .agrees {
				background:#0f88eb;
			}
			  /* 打招呼样式修改 */
         .imgGreet{
         	margin-top:.16rem;
         }
         .sysMes ul li{
           display:block;
           height:50%;
           width:4.5rem;
           white-space: nowrap;
           overflow: hidden;
           text-overflow: ellipsis;
         }
         .msg{
           width:100%;
           line-height:.40rem;
           font-size:.24rem;
           background:#fff;
           padding:0 .2rem 0 1.2rem;
           box-sizing:border-box;
         }
		</style>
	</head>

	<body>
		<div class="wrapper">
			<div class="content">
			</div>
			<div class="loadingbox">
						<div class="page_loading" style="display:block;">加载中…</div>
						<div class="page_nodata" style="display:none;">暂无更多数据</div>
					</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				zdy_ajax({
					url: _path+"/im/m/selMyGroupRequest.do",
				    showLoading:false,
				    data:{
				    	'pageNum':1,
				    	'pageSize':50
				    },
					success: function(data,res){
						console.log(data);
						if(data.length==0){
							$(".page_nodata").show();
							$(".page_loading").hide();
							return;
						}else{
							$(".page_nodata").show();
							$(".page_loading").hide();
						}
						$.each(data,function(index){
							if(data[index].status == 1){
								var str1 = '<button class="agrees active_A">同意</button>'
							}else if(data[index].status == 2){
								var str1 = '<button class="agrees" style="background:#fff;color:#808080">已处理</button>'
							}
							 var str = '<div class="content-item ">'+
								'<div class="con-l " style="background:none;padding-left:0.2rem;padding-right:0.2rem">'+
								'<div style="float:left" class="imgGreet" >'+
									'<img src="'+data[index].head_portrait+'" id="'+data[index].request_id+'"/>'+
								'</div>'+
								'<div class="sysMes">'+
									'<ul>'+
										'<li>'+data[index].nickname+'\\'+data[index].job_name+'\\'+data[index].com_name+'</li>'+
										'<li class="sign-text clear" >'+
											'申请加入 '+data[index].group_name+' 群'+
										'</li>'+
									'</ul>'+
								'</div>'+
								'<div class="textDate">'+str1+
									/* '<button class="active_A">拒绝</button>'+ */
								'</div>'+
								'<div style="clear:both"></div>'+
							'</div>'+
						'</div>'+
						'<div class="msg bottom-border">'+data[index].nickname+':'+data[index].askmessage+'</div>'
        				$('.content').append(str) 
						});
						$('.img').on('click',function(){
							var follow_user = $(this).attr("follow_user")
							window.location.href = "${path}/wx/businessFriend/talkAboutQl.do?follow_user="+follow_user
						})
						$('.textDate .agrees').on('click',function(){
							var id = $(this).parents('.content-item').find("img").attr("id")
							var  isAgeet = $(this).html()
							var index = $(this).index()
							console.log(isAgeet)
							if(isAgeet == "同意"){
								 sqjq(id,2)
								 $(this).html("已处理")
								 $(this).css({"background":"#fff"})
								 $(this).css({"color":"#808080"})
							}/* else{
								sqjq(id,3)
							} */
						})
					},
				    error:function(e){
					   //alert(e);
				    }
				});
				function sqjq(id,status){
					console.log(id)
					zdy_ajax({
						url: _path+"/im/m/updateGroupRequest.do",
					    showLoading:false,
					    data:{
					    	requestId:id,
					    	status:status
					    },
						success: function(data,res){
							layer.msg(res.msg)
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