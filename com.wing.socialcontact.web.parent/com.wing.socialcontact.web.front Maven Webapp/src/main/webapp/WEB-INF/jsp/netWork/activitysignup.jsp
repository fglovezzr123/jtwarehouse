<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">活动报名</title>
        <link rel="stylesheet" href="${path}/resource/css/instantlyEnterName.css" />
        <style>
        
         .meeting2>div div {
		    width: 35%;
		    text-align: right;
           }
        .meeting-head div:nth-child(1) img {
           width:100%;height:100%;
          }
        </style>
    </head>

    <body>
    	
        <div class="wrapper">
             
             <div class="meeting-head">
                  <div><img alt="" id="activityurl"></div>
                  <div>
                       <h2>${detail.titles }</h2>
                       <span>${detail.cityName } | ${detail.startTime }</span>
                  </div>
                  <br class="clear"/>
             </div>
             <div class="meeting2" id="activitysignup" style="display:none;">
                 <div>
                     <div>门票</div> 
                     <div>
                          <button class="active_A">一张</button>
                           <c:if test="${ detail.ticketPrice > 0 }">
					         <span  ><fmt:formatNumber type="number" value="${detail.ticketPrice}" pattern="0.00" maxFractionDigits="2"/></span>元
					       </c:if>
					       <c:if test="${ detail.ticketPrice == 0 }">
					        <span>免费</span>
					       </c:if>
                     </div>
                     <br class="clear"/>
                 </div>
             </div>

             <div class="meeting3 ">
                 <div style="border:none">
                     <div class='editor active_A' onclick="foucsfunction('name')">联系人：<input id="name" value="${user.nickname }"/> </div>
                     <%-- <div class='editor active_A' >
                     	联系电话：<input id="mobile" value=" ${user.mobile }"/>
                     </div> --%>
                     <br class="clear"/>
                 </div>
             </div>
             <div class="meeting3 ">
                 <div style="border:none">
                     <%-- <div >联系人：<input id="name" value="${user.nickname }"/> </div> --%>
                     <div class='editor active_A' onclick="foucsfunction('mobile')" >
                     	联系电话：<input id="mobile" value="${user.mobile }"  placeholder="限制11位电话字符"/>
                     </div>
                     <br class="clear"/>
                 </div>
             </div>
             
			<c:if test="${ detail.ticketPrice > 0 }">
            <div class="meeting2">
                 <div>
                     <div>优惠券</div> 
                     <input type="hidden" id=couponlogid value="">
                     <input type="hidden" id="couponamount"  value="0">
                     <div  id="coupondetail"  onclick="opencoupon('${detail.ticketPrice}')" >
							不使用优惠券
                     </div>
                     <br class="clear"/>
                 </div>
             </div>
			</c:if>
             <div class="meeting-redund">
                 <div>退款条款</div>
                 <p>
                   ${instruction}
                 </p>
             </div>

			<c:if test="${ detail.ticketPrice > 0 }">
             <div class="meeting3">选择支付方式</div>
             <div class="pay-meet active_A">
                  <div>
                       <label for="wechats">微信支付</label>
                       <input type="radio" id="wechats" checked="checked"/>
                       <br class="clear"/>
                  </div>
             </div>
			</c:if>
       </div>
       <div class="meetingBtm">
       <c:if test="${ detail.ticketPrice > 0 }">
         <div class="active_A"  id="totalPrice">合计 : ￥<fmt:formatNumber type="number" value="${detail.ticketPrice}" pattern="0.00" maxFractionDigits="2"/></div>
       </c:if>
       <c:if test="${ detail.ticketPrice == 0 }">
         <div class="active_A" id="totalPrice">免费</div>
       </c:if>
        <%--  <c:choose>
         	<c:when test="${issignup eq  1 }">
         		<div class="active_A" >您已报名</div>
         	</c:when>
         	<c:otherwise>
         		<!-- <div class="active_A" >确认支付</div> -->
         	</c:otherwise> 
         	         </c:choose>--%>

   		<div class="active_A" onclick="payOrder('${detail.id}')">确认支付</div>
         
         <br class="clear"/>
       </div>
        <script type="text/javascript">
        var couponlogid= "";
        var orderId="";
            $(document).ready(function() {
           	  $("#activityurl").attr("src",_oss_url+'${detail.imagePath}');
              var deviceWidth = document.documentElement.clientWidth;
              document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
              
              if(zfflag){
            	  $("#activitysignup").show();
              }else{
            	  $("#activitysignup").hide();
            	  
              }
            })
            function foucsfunction(str){
            	var t=$("#"+str).val(); 
            	$("#"+str).val("").focus().val(t);
            }
			var wxflag=false; 
            
            function payOrder(id){
            	if(!is_weixn()){
            		layer.msg("请在微信中报名！");
            		return;
            	} ;
            	var name=$("#name").val();
            	if(isEmpty(name)){
					alert_back("姓名不能为空",function(){
						$("#name").focus();
					});
					return;
				}
            	
            	if(name.length>10){
            		alert_back("姓名不能超过10个字",function(){
						$("#name").focus();
					});
					return;
            	}
            	var mobile=$("#mobile").val();
            	if(isEmpty(mobile)){
					alert_back("电话不能为空",function(){
						$("#mobile").focus();
					});
					return;
				}

            	if(isMobile(mobile)){
					alert_back("电话格式有误",function(){
						var t=$("#mobile").val(); 
		            	$("#mobile").val("").focus().val(t);
					});
					return;
				}
            	
            	couponlogid= $("#couponlogid").val();
            	zdy_ajax({
     			    showLoading:false,
            		url :"${path}/m/my/activityprepay.do", 
            	    data: { id: id,
            	    		name:name,
            	    		mobile:mobile,
            	    		couponlogid:couponlogid
            	    },
            	    success : function(dataobj,data){
            	    	var obj=dataobj;
            	    	var isfree = obj.free;
            	    	var signuped = obj.signuped;
						orderId=obj.orderId;
            	    	if(signuped==1){
            	    		layer.msg(data.msg);
            	    		return;
     			    	}else{
	            	    	if(isfree==1){
	            	    		self.location.href="${path}/m/activity/signupsuccess.do";
	            	    		usercoupon();
	            	    		return;
	            	    	}else{
	            	    		if(!zfflag){
	                        		layer.msg("支付功能尚未开通");
	                        		return;
	                        	}
		            	    	wxflag=false;
								 if(parseInt(obj.agent)<5){  
								     alert("您的微信版本低于5.0无法使用微信支付");  
								     return;  
								 } 
		                        WeixinJSBridge.invoke('getBrandWCPayRequest',{  
		                        	"appId" : obj.appId,                  //公众号名称，由商户传入  
		                            "timeStamp":obj.timeStamp,          //时间戳，自 1970 年以来的秒数  
		                            "nonceStr" : obj.nonceStr,         //随机串  
		                            "package" : obj.packageValue,      //<span style="font-family:微软雅黑;">商品包信息</span>  
		                            "signType" : obj.signType,        //微信签名方式:  
		                            "paySign" : obj.paySign           //微信签名  
		                            },function(res){  
		                           		if(res.err_msg == "get_brand_wcpay_request:ok" ) {
		                           			//使用优惠券
		                           			usercoupon();
		                           			self.location.href="${path}/m/activity/signupsuccess.do";
		                            	}else{ 
		                            		layer.msg("取消支付",{
		            	                	    icon: 2,
		            	                	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
		            	                	}, function(){
		            	                		wxflag=true;
		            	                		location.reload([true]);
		            	                	});
		            	                }  
		                        	}
		                        ); 
	            	    	}
            	    	}
            	    }
               	});
            }
            
            
            
            function opencoupon(price){
            	zdy_ajax({
        			url: "${path}/m/coupon/selCanUseCouponList.do",
        			showLoading:false,
        			data:{
        				orderMinBuy:price,
        				useRange:3,
        				currency:2
        			},
        			success: function(data,res){
        				if(res.code == 0){
        					var con = "";
        					if(res.dataobj.clist.length==0){
        						alert("暂无可用优惠券！",function(){
        						});
        				    }else{
        				    	opencoupon1(price);
        				    }
        				}
        			}
        		});
            }
            function opencoupon1(price){
	            	layer.open({
	        			type : 2,
	        			//skin: 'layui-layer-lan',
	        			title: false,
	        				closeBtn: 0, //不显示关闭按钮
	        			fix : true,
	        			shadeClose : true,
	        			maxmin : false,
	        			area : [ '100%', '60%' ],
	        			content : '${path}/m/coupon/selectPage.do?orderMinBuy='+price+'&useRange='+3+'&currency='+2,
	        			shift : 2,
	        			scrollbar : false,
	        			success : function(layero, index) {
	        			},
	        			end : function() {
	        				var couponamount = $("#couponamount").val();
	        				
	        				var ticketPrice = ${detail.ticketPrice} ;
	        				
	        				if(couponamount>=ticketPrice){
	        					$("#totalPrice").text('合计 : ￥0.00');
	        				}else{
	        					var shiji= ticketPrice - couponamount;
	        					$("#totalPrice").text('合计 : ￥'+shiji.toFixed(2));
	        				}
	        			},
	        			cancel : function(cancel) {
	        			}
	        		});
            }
            
            function usercoupon(){
            	var id = couponlogid;
            	if(isEmpty(id)){
            		return;
            	}
            	zdy_ajax({
     			    showLoading:false,
            		url :"${path}/m/coupon/useCoupon.do", 
            	    data: { id: couponlogid,
            	    		orderId:orderId
            	    },
            	    success : function(dataobj,data){
            	    }
               	});
            }
            
           
    </script>
    </body>
</html>