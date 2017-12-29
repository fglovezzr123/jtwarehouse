<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">立刻报名</title>
<link rel="stylesheet" href="${path}/resource/css/instantlyEnterName.css?v=${sversion}" />

<style>
</style>
</head>
<body>
  <div class="wrapper">
        <div class="meeting-head">
             <div>
             	<img alt="" style="width: 100%;height:100%;" src="${obj.coverImg}">
             </div>
             <div>
                  <h2>${obj.titles}</h2>
                  <span>${obj.place}</span><br>
                  <span>${fns:fmt(obj.startTime,'MM月dd日 HH:mm')}-${fns:fmt(obj.endTime,'MM月dd日 HH:mm')}</span>
             </div>
             <br class="clear"/>
        </div>
        <div class="meeting2">
            <div>
                <div style="width:auto;">门票</div>
                <div style="width:auto;float:right;">
                     <button class="active_A">一张</button>
             <c:choose>
             	<c:when test="${not empty obj.ticketPrice and obj.ticketPrice gt 0}">
                     <span>￥${fns:fixed(obj.ticketPrice)}</span>元
             	</c:when>
             	<c:otherwise>
                     <span>免费</span>
             	</c:otherwise>
             </c:choose>
                </div>
                <br class="clear"/>
            </div>
        </div>
        <div class="meeting2 ">
            <div style="border:none">
                <div>${tjyUser.trueName}</div>
                <div class='editor active_A'>
                     <input style="height:100%;width:100%;font-size:0.3rem;font-family:'微软雅黑';" maxlength="11" 
                     	type="text" value="${tjyUser.mobile}" id="mobile"/>
                </div>
                <br class="clear"/>
            </div>
        </div>
        <c:if test="${ obj.ticketPrice > 0 }">
            <div class="meeting2">
                 <div>
                     <div>优惠券</div> 
                     <input type="hidden" id="couponamount"  value="0">
                     <div  id="coupondetail"  onclick="opencoupon('${obj.ticketPrice}')" >
							不使用优惠券
                     </div>
                     <br class="clear"/>
                 </div>
             </div>
			</c:if>
  <c:choose>
   	<c:when test="${not empty obj.ticketPrice and obj.ticketPrice gt 0}">
       <div class="meeting-redund">
            <div>退款条款</div>
            <p>
            ${instruction }
            </p>
        </div>
        <div class="meeting3">选择支付方式</div>
        <div class="pay-meet active_A">
             <div>
                  <label for="wechats">微信支付</label>
                  <input type="radio" id="wechats" checked="checked"/>
                  <br class="clear"/>
             </div>
        </div>
        <div class="meetingBtm">
	     	<div class="active_A"  id="totalPrice" >合计 : ￥${fns:fixed(obj.ticketPrice)}</div>
	     	<div class="active_A" onclick="payOrderConfirm('${obj.id}')">确认支付</div>
	     	<br class="clear"/>
	   	</div>
   	</c:when>
   	<c:otherwise>
        <div class="meetingBtm">
	     	<div class="active_A" style="width:0px;">确认</div>
	     	<div class="active_A" style="width:100%;" onclick="payOrderConfirm('${obj.id}')">报名</div>
	     	<br class="clear"/>
	   	</div>
   	</c:otherwise>
   </c:choose>
  </div>
   <form id="dataForm">
	   	<input type="hidden" name="meetingId" value="${obj.id}">   
	   	<input type="hidden" name="mainBusiness" value="${signupObj.mainBusiness}">   
	   	<input type="hidden" name="regCapital" value="${signupObj.regCapital}">   
	   	<input type="hidden" name="payCapital" value="${signupObj.payCapital}">   
	   	<input type="hidden" name="totalAssets" value="${signupObj.totalAssets}">   
	   	<input type="hidden" name="annualSales" value="${signupObj.annualSales}">   
	   	<input type="hidden" name="attendType" value="${signupObj.attendType}">   
	   	<input type="hidden" name="otherReq" value="${signupObj.otherReq}">   
	   	<input type="hidden" name="tjLinkMan" value="${signupObj.tjLinkMan}">   
	   	<input type="hidden" name="recLinkMan" value="${signupObj.recLinkMan}">   
	   	<input type="hidden" name="attendType" value="${signupObj.attendType}">   
	   	<input type="hidden" name="mobile" id="p_mobile" value="">   
	   	<input type="hidden" name="couponlogid" id="couponlogid" value="">   
   </form>
<form id="dataForm1">
   	<input type="hidden" name="meetingId" value="${obj.id}">   
</form>   
<script type="text/javascript">

var couponlogid= "";
var orderId="";
$(document).ready(function() {
  	var deviceWidth = document.documentElement.clientWidth;
  	document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
})

function validatemobile(mobile) 
{ 
    if(mobile.length==0) 
    { 
       alert('请输入手机号码！'); 
       $("#mobile").focus(); 
       return false; 
    }     
    if(mobile.length!=11) 
    { 
        alert('请输入有效的手机号码！'); 
        $("#mobile").focus(); 
        return false; 
    } 
     
    var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
    if(!myreg.test(mobile)) 
    { 
        alert('请输入有效的手机号码！'); 
        $("#mobile").focus(); 
        return false; 
    } 
    $("#p_mobile").val($("#mobile").val());
    return true;
}  
var wxflag=false;
function payOrderConfirm(id){
	if(new Date().getTime()>=  '${endSignupTime}'){
		alert("报名期限已过，禁止报名")
	}else{
	return;
		if(validatemobile($("#mobile").val())){
			confirm("是否确认报名",function(t){
				if(t==1){
					payOrder(id);
				}
			});
		}
	}
}

function payOrder(id){
	if(wxflag) return;
	couponlogid= $("#couponlogid").val();
	zdy_ajax({
		url :"${path}/m/my/meeting/signup/prepay.do", 
	    type : 'post', 
	    dataType : 'json', 
	    data: $("#dataForm").serializeObject(),
	    success : function(dataobj){
	    	var isSuccess = "0"===dataobj["result_code"]?true:false;
	    	orderId=dataobj.orderId;
	    	if(!isSuccess){
		    	alert(dataobj.result_msg)
	    		return;
	    	}else{
	    		if("0"===dataobj["pay_status"]){
	    			usercoupon();
	    			$("#dataForm1").attr("action","${path}/m/meeting/signupok.do?id="+dataobj["id"]);
	    			$("#dataForm1").submit();
	    			return;
	    		}
	    	}
	    	wxflag=false;
            WeixinJSBridge.invoke('getBrandWCPayRequest',{  
            	"appId" : dataobj.appId,                  //公众号名称，由商户传入  
                "timeStamp":dataobj.timeStamp,          //时间戳，自 1970 年以来的秒数  
                "nonceStr" : dataobj.nonceStr,         //随机串  
                "package" : dataobj.packageValue,      //<span style="font-family:微软雅黑;">商品包信息</span>  
                "signType" : dataobj.signType,        //微信签名方式:  
                "paySign" : dataobj.paySign           //微信签名  
                },function(res){
               		if(res.err_msg == "get_brand_wcpay_request:ok" ) {
               			usercoupon();
               			zdy_ajax({
               				url :"${path}/m/my/meeting/signup/changepaystatus.do", 
               			    type : 'post', 
               			    dataType : 'json',
               			    data: {id: dataobj.id},
               			 	success: function(data){
	               			 	$("#dataForm1").attr("action","${path}/m/meeting/signupok.do?id="+dataobj.id);
	        	    			$("#dataForm1").submit();
               			    }
               			});  
                	}else{ 
                		layer.msg("取消支付",{
	                	    icon: 2,
	                	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
	                	}, function(){
		                	wxflag=true;
		                	//self.location.href="${path}/m/investment/index.do";
		                	location.reload([true]);
	                	});
	                }  
            	}
            ); 
	    }
   	});
	if (typeof WeixinJSBridge == "undefined"){
	   if( document.addEventListener ){
	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	   }else if (document.attachEvent){
	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	   }
	}else{
	   onBridgeReady();
	}
	function onBridgeReady(){
		wxflag=true;
	}
}

function opencoupon(price){
	zdy_ajax({
		url: "${path}/m/coupon/selCanUseCouponList.do",
		showLoading:false,
		data:{
			orderMinBuy:price,
			useRange:2,
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
		content : '${path}/m/coupon/selectPage.do?orderMinBuy='+price+'&useRange='+2+'&currency='+2,
		shift : 2,
		scrollbar : false,
		success : function(layero, index) {
		},
		end : function() {
			var couponamount = $("#couponamount").val();
			
			var ticketPrice = ${obj.ticketPrice} ;
			
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