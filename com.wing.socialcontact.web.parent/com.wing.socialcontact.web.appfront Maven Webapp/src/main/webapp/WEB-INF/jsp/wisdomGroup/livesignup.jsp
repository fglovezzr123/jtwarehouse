<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">直播报名</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css"/>
        <link rel="stylesheet" href="${path}/resource/css/instantlyEnterName.css" />
        <script src="${path}/resource/js/libs/zepto.min.js" type="text/javascript" charset="utf-8"></script>
        <style>
           .pay-meet1{
			  width:100%;
			     box-sizing:border-box;
			     padding-left:0.4rem;
			    padding-right:0.4rem;
			    height:0.8rem;
			    line-height:0.80rem;
			    background:white;
			   
			}
			.pay-meet1 div{
			    width:100%;
			     height:0.8rem;
			     background-size:0.51rem 0.43rem;
			}
			
			.pay-meet1 div input{
			    float:right;
			    margin-top:0.3rem;
			}
			.pay-meet1 div label{
			    float:left;
			    width:70%;
			}
			.C-z{
			    font-size: .30rem;
			    background: #0f88eb;
			    color: #fff;
			    border-radius: 5px;
			    opacity: 0.8;
			}
			#chongzhi{
			  width:1.6rem;
			  float:right;
			}
        </style>
    </head>
    <body>
        <div class="wrapper">
             <div class="meeting-head">
                  <div><img alt="" height="65" width="130" src="${detail.imagepath }"></div>
                  <div>
                       <h2>${detail.title }</h2>
                       <span>${fns:fmt(detail.startTime,'MM月dd日 HH:mm')}-${fns:fmt(detail.endTime,'MM月dd日  HH:mm')}</span>
                  </div>
                  <br class="clear"/>
             </div>
             <div class="meeting2">
                 <div>
                     <div>价格</div> 
                     <div>
                          <button class="active_A">一张</button>
                           <c:if test="${ detail.ticketPrice > 0 }">
					         <span>${detail.ticketPrice}</span>J币
					       </c:if>
					       <c:if test="${ detail.ticketPrice == 0 }">
					        <span>免费</span>
					       </c:if>
                     </div>
                     <br class="clear"/>
                 </div>
             </div>
             <div class="meeting2">
                 <div>
                     <div>当前余额</div> 
                     <div>
					         <span>${jbAmount}</span>
					         J币
                     </div>
                     <br class="clear"/>
                 </div>
             </div>
             
             <div class="meeting2 ">
	            <div style="border:none">
	                <div>${user.trueName}</div>
	                <div class='active_A'>
	                     	${user.mobile}
	                </div>
	                <br class="clear"/>
	            </div>
	        </div>
            <%--  <div class="meeting-redund">
                 <div>退款条款</div>
                 <p>
                   ${instruction}
                 </p>
             </div> --%>
				<div class="meeting2">
					<div >
			             <div >支付方式</div>
			             <div>
			                       J币支付
			             </div>
					</div>
				</div>
		         <%-- <c:choose>
		         	<c:when test="${jbAmount < detail.ticketPrice}"> --%>
						<div class="meeting2" id="livesignup" style="display:none;">
			                 <div>
			                     <div> 操作 </div> 
			                     <div class="C-z active_A" style="text-align:center;" id="chongzhi" onclick="chongzhiPage()">充值</div>
			                     <br class="clear"/>
			                 </div>
			             </div>
		         	<%-- </c:when>
		         </c:choose> --%>
       </div>
       <div class="meetingBtm">
       <c:if test="${ detail.ticketPrice > 0 }">
         <div class="active_A">合计 : ${detail.ticketPrice} J币</div>
       </c:if>
       <c:if test="${ detail.ticketPrice == 0 }">
         <div class="active_A">免费</div>
       </c:if>
         <c:choose>
         	<c:when test="${signupStatus eq  1 }">
         		<div class="active_A" >您已报名</div>
         	</c:when>
         	<c:otherwise>
         		<div class="active_A" id="baoming" onclick="payOrder('${detail.id}')">确认支付</div>
         	</c:otherwise> 
         </c:choose>

         
         <br class="clear"/>
       </div>
        <script type="text/javascript">
        
        var chongzhiPage = function(){
        	if(zfflag){
	        	window.self.location='${path}/m/my/jb_cz.do';
    		}else{
    			layer.msg("该功能尚未开通")
    		}
        }
        
            $(document).ready(function() {
            	if(zfflag){
        			$("#livesignup").show();
        		}else{
        			$("#livesignup").hide();
        		}
              var deviceWidth = document.documentElement.clientWidth;
              document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
            })
            /* function foucsfunction(str){
            	var t=$("#"+str).val(); 
            	$("#"+str).val("").focus().val(t);
            } */
            
            function payOrder(id){
            	/* if(!is_weixn()){
            		layer.msg("请在微信中报名！");
            		return;
            	} ; */
            	
            	var price =${detail.ticketPrice};
            	var jbAmount = ${jbAmount};
            	if(jbAmount<price){
            		layer.msg("J币不足，请充值！");
            		return;
            	}else{
            		zdy_ajax({
         			   url:_path+"/library/m/live/payorder.do",
         			   showLoading:false,
         			   data:{
         				 'id':id
         			   },
         			   success:function(data,res){
         				  console.log(res);
         				  if(res.code==0){
         					  //跳转到报名成功页面
         					  $("baoming").removeAttr("onclick");
         					  $("baoming").text("您已报名");
         					 self.location.href="${path}/library/m/live/signupsuccess.do";
         				  }else{
         					  layer.msg(res.msg);
         				  }
         			   },
         			   error:function(data){
         			   }
         			}); 
            	}
            	
            }
            
    </script>
      
    </body>
</html>