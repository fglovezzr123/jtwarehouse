<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

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
            }
            .greeting-tab div{
            	box-sizing:border-box;
            	width:50%;
            	float:left;
            	text-align:center;
            }
            .noneselected{
            	border:1px solid #e6e6e6;
            }
           .content{
                margin-top:0px;
           }
           .textDate{
              margin-right:0.3rem;
           }
        </style>
    </head>
    
  
    <body>
        <div class="wrapper">
            
             

             
             <div class="greeting-tab">
             	<div id="shou" class="noneselected">收到的打招呼</div>
             	<div id="fa">发送的打招呼</div>
             	<br class="clear"/>
             </div>


             <div id="shous" class="content" >
	             <c:forEach  var="smc_1" items="${smc1}" >
	                 <div class="content-item bottom-border active_A" >
	                     <div class="con-l " >
	                                <div style="float:left">
	                                   <img src="${smc_1.tjyUser.headPortrait}"/>
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
	                                 <div class='textDate'> <fmt:formatDate value='${smc_1.createTime}' pattern='yyyy/MM/dd'/></div>
	                                 <div style="clear:both"></div>
	                     </div>
	                 </div>
                 </c:forEach>
             </div>
             
             <div id="fas" class="content"  style="display:none;">
                 <c:forEach  var="smc_2" items="${smc2}" >
	                 <div class="content-item bottom-border active_A" >
	                     <div class="con-l " >
	                                <div style="float:left">
	                                   <img src="${smc_2.tjyUser.headPortrait}"/>
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
	                                 <div class='textDate'> <fmt:formatDate value='${smc_2.createTime}' pattern='yyyy/MM/dd'/></div>
	                                 <div style="clear:both"></div>
	                     </div>
	                 </div>
                 </c:forEach>
             </div>
        </div>
<script type="text/javascript">
		$(function(){  
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
			});
		});
	
		
		</script>
    </body>
</html>