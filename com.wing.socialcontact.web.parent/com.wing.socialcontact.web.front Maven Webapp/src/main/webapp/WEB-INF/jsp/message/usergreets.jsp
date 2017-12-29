<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
 <%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%> 

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">打招呼</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css"/>
		  
        <link rel="stylesheet" href="${path}/resource/css/messages.css" />
        <style>
            .greeting-tab{
            	  width:100%;
            	  background:white;
            	  height:1rem;
            	  line-height:1rem;
            	  box-sizing:border-box;
            	  font-size:.28rem;
            	  justify-content: space-between;
				   padding: 0 .3rem;
				   display: flex;
            }
            .greeting-tab div{
            	box-sizing:border-box;
            	width:2.4rem;
            	text-align:center;
            }
            .noneselected{
            	border-bottom:2px solid #0f88eb;
            	color:#0f88eb;
            }
           .content{
                margin-top:0.1rem;
           }
           .textDate{
             margin-right: 0.5rem;
             margin-top: 0.34rem;
           }
           /* 打招呼样式修改 */
         .imgGreet{
         	margin-top:.16rem;
         }
         
         .layui-layer-setwin .layui-layer-close2 {
				    position: absolute;
				    right: -0.25rem;
				    top: -0.2rem;
				    width: 30px;
				    height: 30px;
				    margin-left: 0;
				    background-position: -149px -31px;
				    _display: none;
}
        </style>
    </head>
    <body>
        <div class="wrapper">   
             <div class="greeting-tab">
             	<div id="shou" class="noneselected" >收到的打招呼</div>
             	<div id="fa">发送的打招呼</div>
             </div>
             <div id="shous" class="content" >
	             <c:forEach  var="smc_1" items="${smc1}" >
	                 <div class="content-item bottom-border active_A" >
	                     <div class="con-l ">
	                                <div style="float:left"  class="imgGreet" >
	                                   <img src="${smc_1.tjyUser.headPortrait}" follow_user="${smc_1.userId}"/>
	                                </div>
	                               <div class="sysMes">
	                                       <ul>
	                                                <li style="display:block;height:50%">
	                                                   ${smc_1.tjyUser.nickname}
	                                                   
	                                                </li>
	                                                <li class="sign-text clear" style="display:block;height:50%;">
	                                                      ${smc_1.askmessage}
	                                                </li>
	                                        </ul>
	                                 </div>
	                                 <div class='textDate' onclick="answer_hello('${smc_1.id}')"> <fmt:formatDate value='${smc_1.createTime}' pattern='yyyy/MM/dd'/></div>
	                                 <div style="clear:both" onclick="answer_hello('${smc_1.id}')"></div>
	                     </div>
	                 </div>
                 </c:forEach>
                  <c:if test="${empty smc1}">
		          	 <div class="jz">  暂无收到的打招呼!</div> 
		          </c:if>
             </div>
             
             <div id="fas" class="content"  style="display:none;">
                 <c:forEach  var="smc_2" items="${smc2}" >
	                 <div class="content-item bottom-border active_A" >
	                     <div class="con-l " >
	                                <div style="float:left" class="imgGreet">
	                                   <img src="${smc_2.tjyUser.headPortrait}"  follow_user="${smc_2.friendUser}"/>
	                                </div>
	                                 
	                               <div class="sysMes">
	                                       <ul>
	                                                <li style="display:block;height:50%">
	                                                   ${smc_2.tjyUser.nickname}
	                                                   
	                                                </li>
	                                                <li class="sign-text clear" style="display:block;height:50%;">
	                                                      ${smc_2.askmessage}
	                                                </li>
	                                        </ul>
	                                </div>
	                                 <div class='textDate'  onclick="answer_hello2('${smc_2.id}')"> <fmt:formatDate value='${smc_2.createTime}' pattern='yyyy/MM/dd'/></div>
	                                 <div style="clear:both" onclick="answer_hello2('${smc_2.id}')"></div>
	                     </div>
	                 </div>
                 </c:forEach>
                 <c:if test="${empty smc2}">
		          	 <div class="jz">  暂无发送的打招呼!</div> 
		          </c:if>
             </div>
        </div>
        <script type="text/javascript">
		$(function(){  
		 $(".imgGreet").on('click',function(){
			 var follow_user= $(this).find('img').attr('follow_user')
			 window.location.href = _path+ "/wx/businessFriend/friendInfo.do?follow_user="+follow_user;
		 })
		 
		 
		$(".greeting-tab div").click(function(){
			   $(this).addClass('noneselected').siblings().removeClass("noneselected");
		});
		
		$("#shou").click(function(){
			$("#shous").show();
			$("#fas").hide();
		});
		$("#fa").click(function(){
			$("#fas").show();
			$("#shous").hide();
		});
		
		userGreetsReaded();
		
		
		 
		 
		/* $(".textDate").on('click',function(){
			 var follow_user= $(this).parents('.content-item').find('img').attr('follow_user')
			 var head_portrait = $(this).parents('.content-item').find('img').attr('src')
			 var objId = new Object()
			 objId.follow_user = follow_user;
			 objId.head_portrait = head_portrait;
			 var objStr = JSON.stringify(objId);
			 sessionStorage.setItem('user_info',objStr);
			 window.location.href = _path+ "/wx/businessFriend/talkAbout.do";
		 })
		
			function ckInfo(friendUser){
				console.log(1)
			}
			$(".greeting-tab div").click(function(){
				   $(this).addClass("noneselected").siblings().removeClass('noneselected');
			});
			$("#shou").click(function(){
				$("#shous").show();
				$("#fas").hide();
			});
			$("#fa").click(function(){
				$("#fas").show();
				$("#shous").hide();
			});*/
		});
		
		var answer_hello= function(ugId) {
			
			layer.open({
				type : 2,
				//skin: 'layui-layer-lan',
				title: false,
  				closeBtn: 0, //不显示关闭按钮
				fix : true,
				shadeClose : true,
				maxmin : false,
				area : [ '100%', '100%' ],
				content : '${path}/m/my/answer_hello.do?ugId=' + ugId,
				shift : 2,
				scrollbar : false,
				success : function(layero, index) {
					show_zzc(1);

				},
				end : function() {
					hide_zzc(1);
				},
				cancel : function(cancel) {
				}
			});
				
	   }
		
		var answer_hello2= function(ugId) {
			
			layer.open({
				type : 2,
				//skin: 'layui-layer-lan',
				title: false,
				fix : true,
				shadeClose : true,
				maxmin : false,
				area : [ '100%', '100%' ],
				content : '${path}/m/my/answer_hello2.do?ugId=' + ugId,
				shift : 2,
				scrollbar : false,
				success : function(layero, index) {
					show_zzc(1);

				},
				end : function() {
					hide_zzc(1);
				},
				cancel : function(cancel) {
				}
			});
				
	   }
		
		var userGreetsReaded = function(){
			var url = "${path}/m/message/toGreetingsReaded.do";
			
			zdy_ajax({
				url: url,
				data:{},
			    showLoading:false,
				success: function(dataobj,res){
					var data = dataobj;
					if(res.code == 0){
						
					};
				},
			    error:function(e){
				   //alert(e);
			    }
		 }); 
			
		}
	
		
		var show_zzc = function(t) {
			$("body").bind("touchmove", function(event) {
				event.preventDefault();
			});
			if (t && t == 1) {
				scrollTop = $(document).scrollTop();
				$(document).scrollTop(0);
				$(window).bind("scroll", function() {
					$(document).scrollTop(0);
				});
			}

		}

		var hide_zzc = function(t) {
			$("body").unbind("touchmove");
			if (t && t == 1) {
				$(window).unbind("scroll");
				$(document).scrollTop(scrollTop);
			}

		}

		var reload = function() {
			self.location.href = self.location.href;
		}
		
		</script>
    </body>
</html>