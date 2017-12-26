<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="新的朋友">
		<title>新的朋友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/newFriend.css?v=${sversion}" />
		<style>
		 .info {
		 height:auto;
           min-height: 1rem;
            }
			.company{
			  width:4.7rem;
			}
		.msg{
           width:100%;
           line-height:.40rem;
           font-size:.24rem;
           background:#fff;
           padding:0 .2rem 0 1.2rem;
           box-sizing:border-box;
         }
         .info {
           margin-bottom: 0;
          }
         .bottom-border {
           border-bottom: 1px solid #efeef4;
        }
         .info .info-l .person .profession {
		    width: 4.7rem;
		    }
		    
		</style>
	</head>
	<body>
		<div class="T-talkAbout" style="background: #f2f3f4;width: 100%; height: 100%;">
			<div class="wrapper" id="wrapper">
				<div class="scrollbar" id="scrollbar">
			
				</div>
			</div>
		</div>
		<script type="text/javascript">
		$(document).ready(function(){
			zdy_ajax({
				url: _path+"/im/m/selMyNewFriendList.do",
			    showLoading:false,
			    data:{
			    	'pageNum':1,
			    	'pageSize':100
			    },
				success: function(data,res){
					console.log(data)
					if(!data.length){
						$('.scrollbar').html("暂无更多数据");
						$('.scrollbar').css({"font-size":"0.3rem","text-align":"center","color":"#808080"});
					}
					$.each(data,function(index){
						//根据status来判断
						if(data[index].status == 1){
							   var  str1= '<span class="accept active_A onC" status="'+data[index].status+'">添加</span>';
						   }else if(data[index].status == 2){
							   var str1 = '<span class="info-add " status="'+data[index].status+'">发消息</span>';
						   }else{
							   var str1 = '<span class="add " status="data[index].status">已拒绝</span>'
						   }
						  var str = '<div class="info" id="'+data[index].id+'" user_id="'+data[index].user_id+'" follow_user="'+data[index].friend_user+'">'+
							          '<div class="info-l">'+
						                '<img src="'+data[index].head_portrait+'" />'+
						                '<div class="person">'+
							             ' <p>'+ 
								            '<span class="profession">'+'<span class="p-name">'+data[index].nickname+'</span>'+'   '+data[index].job_name+' / '+data[index].com_name+'</span>'+
							              '</p>'+
							             '<p class="company">'+data[index].askmessage+'</p>'+
						                '</div>'+
					                  '</div>'+
					                   str1+
				                    '</div> '
					   $('.scrollbar').append(str);
					});
					$('.info').on('click',function(){
						 var user_id = $(this).attr('user_id');
						 window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+user_id;
					});
                    $('.onC').on('click',function(event){
                    	stopEvent(event)
						var id = $(this).parents('.info').attr('id');
						$(this).addClass("ydj").siblings().removeClass('ydj')
						var user_id = $(this).parents('.info').attr('user_id')
						
						 zdy_ajax({
							url: _path+"/im/m/updateFriendRequestStatus.do",
						    showLoading:false,
						    data:{
						    	'requestId':id,
						    	'status':2,
						    	'friendMemo':""
						    },
							success: function(data,res){
								layer.msg(res.msg)
								if($('.onC').hasClass('ydj')){
									console.log("ww")
									$(".ydj").html("已添加")
									$(".ydj").css({"background":"#fff","color":"#777"})
									
						            window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+user_id;
								}
							},
						    error:function(e){
							   //alert(e);
						    }
						});
								
						
					});
				},
			    error:function(e){
				   //alert(e);
			    }
			});
			
			
			
			
			function stopEvent(event){ //阻止冒泡事件
			     //取消事件冒泡
			     var e=arguments.callee.caller.arguments[0]||event; //若省略此句，下面的e改为event，IE运行可以，但是其他浏览器就不兼容
			     if (e && e.stopPropagation) {
			     // this code is for Mozilla and Opera
			     e.stopPropagation();
			     } else if (window.event) {
			     // this code is for IE
			      window.event.cancelBubble = true;
			     }
			    }
		});
  
		</script>
		
	</body>

</html>