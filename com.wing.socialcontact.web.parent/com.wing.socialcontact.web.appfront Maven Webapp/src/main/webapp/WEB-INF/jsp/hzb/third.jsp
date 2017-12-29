<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_upload.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">互助宝会员</title>
        <style type="text/css">
           body{
           		width: 100%;
          		background: #F2F3F4;
           }
          	.hzb-header{
          		width: 100%;
          		height: .90rem;
          		background: #fff;
          		padding: 0 .3rem;
          		font-size: .30rem;
          		display: flex;
          		justify-content: space-between;
          		align-items: center;
          		margin-bottom: .20rem;
          		box-sizing:border-box;
          	}
          	.hzb-header span:nth-of-type(2){
          		color: #0F88EB;
          	}
			._rank{
				width: 100%;
				background: #fff;
				font-size: .28rem;
			}
			._rank .kt{
				padding: .1rem .3rem;
				display: flex;
				justify-content: space-between;
				border-bottom: 1px solid #f2f3f4;
				box-sizing:border-box;
			}
			.kt ._left{
				display: block;
				line-height: .60rem;
			}
			._cont{
				width: 5.3rem;
				color: #808080;
			}
			._cont p{
				line-height: .50rem;
			}
			._cont p:nth-of-type(1){
				line-height: .60rem;
			}
			.r-item{
				width: 100%;
				height: .87rem;
				border-bottom: solid 1px #F2F3F4;
				font-size: .28rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding: 0 .3rem;
				box-sizing:border-box;
			}
			.r-item .item_right{
				width: 5.3rem;
				color: #808080;
			}
			.zczf{
				width: 100%;
				padding: 0 .3rem;
				height: .84rem;
				background: #fff;
				font-size: .28rem;
				border-bottom: #F2F3F4 solid 1px;
				line-height: .84rem;
				margin-top: 0.2rem;
				box-sizing:border-box;
			}
			.check{
				width: 100%;
				padding-left: .30rem;
				box-sizing:border-box;
				
				background: #fff;
			}
			.check .c-bottom{
				padding-right: .30rem;
				font-size: .28rem;
				height: 1rem;
				border-bottom: #F2F3F4 solid 1px;
				display: flex;
				align-items: center;
				box-sizing:border-box;
			}
			input[type="radio"]{
				  -webkit-appearance: none;
				width: .39rem;
				height: 0.39rem;
				border-radius:100%;
	           background:url(${path}/resource/img/icons/radio.png) no-repeat;
	           background-size: 100%;
			}
			input[type="radio"]:checked{
	           background:url(${path}/resource/img/icons/radiocheck.png) no-repeat;
	        	 background-size: 100%;
			}
			.check span{
				margin-left: .25rem;
			}
			.zfmaney{
				width: 100%;
				background:#fff;
				padding: .1rem .30rem .50rem .30rem;
				font-size: .30rem;
				box-sizing:border-box;
			}
			.zfmaney p{
				line-height: .90rem;
			}
			.zfmaney input{
				width: 5.8rem;
				height: .65rem;
				background: #ececec;
				padding-left: .1rem;
				box-sizing:border-box;
				
			}
			input{
			  font-size:.28rem;
			}
			.zfmaney label{
				margin-left:.2rem ;
			}
			.fumaney{
				width: 100%;
				background: #fff;
				font-size: .28rem;
				margin-top: 0.2rem;
				padding-bottom: .9rem;
				box-sizing:border-box;
			}
			.fumaney .fuk{
				width: 100%;
				height: .84rem;
				background: #fff;
				font-size: .30rem;
				border-bottom: #F2F3F4 solid 1px;
				line-height: .84rem;
				padding:0 .3rem;
				box-sizing:border-box;
				
			}
			.fumaney .fklx{
				width: 100%;
				padding-left: 0.3rem;
				padding-top: .20rem;
				font-size: .28rem;
				color: #8B8B8B;
				box-sizing:border-box;
			}
			.fumaney .fklx p{
				width: 100%;
				line-height: .56rem;
				padding-right: .30rem;
				display: flex;
				justify-content: space-between;
				box-sizing:border-box;
			}
			.fumaney .fklx p:nth-of-type(3){
				border-bottom: #F2F3F4 solid 1px;
				padding-bottom: 0.2rem;
				box-sizing:border-box;
			}
			.zfpz{
				display: flex;
				justify-content: space-between;
				padding-right: .30rem;
				box-sizing:border-box;
			}
			.zfpz span:nth-of-type(2){
				display: block;
				width: 2.28rem;
				/* height: 1.4rem; */
				background: #eaeaea;
			}
			.zfq{
				width: 100%;
			   padding: .30rem .30rem .26rem .3rem;
			   background: #fff;
			   font-size: .28rem;
			   display: none;
			   box-sizing:border-box;
			}
			.zfq .item{
				display: flex;
				align-items: center;
				height: .70rem;
				justify-content: space-between;
				margin-top: .18rem;
			}
			.zfq .item input{
				width: 5.35rem;
				background: #ececec;
				height: .70rem;
				font-size: .28rem;
				padding-left: .1rem;
				box-sizing:border-box;
				
			}
			.zfq .item:nth-of-type(1){
				margin-top: 0;
			}
			.zfq .item p{
				width: 2.25rem;
				min-height: .6rem;
				line-height: .60rem;
				background: #ececec;
				font-size: .28rem;
				text-align: center;
				margin-right: 3.1rem;
				color: #808080;
			}
			.zfq .item.zfpzc{
			  height: auto;
			} 
			.imagess i{
			position: absolute;
			left: 2.09rem;
			top: -.16rem;
			width: .40rem;
			height: .40rem;
			background-image:url(${path}/resource/img/images/delete.png);
			background-size:100% ;
			border-radius: 100%;
          } 
          </style>
    </head>
    <body>
		 	<div class="hzb-header">
		 		<span>互助宝状态：</span>
		 		<span>
		 			<c:choose>
		 				<c:when test="${order.status eq 0}">未开通</c:when>
		 				<c:when test="${order.status eq 1}">待支付</c:when>
		 				<c:when test="${order.status eq 2}">待支付尾款</c:when>
		 				<c:when test="${order.status eq 3}">已付尾款</c:when>
		 				<c:otherwise>未知</c:otherwise>
		 			</c:choose>
		 		</span>
		 	</div>
		 	<div class="_rank">
		 		<div class="kt">
		 			<span class="_left">开通等级:</span>
		 			<div class="_cont">
		 				<c:choose>
			 				<c:when test="${order.level eq 1}">
				 				<p>黄金等级</p>
		 						<p>获得方式：</p>
		 						<p>1、单笔互助宝充值20W，承诺年度消费累计20W</p>
			 				</c:when>
			 				<c:when test="${order.level eq 2}">
			 					<p>白金等级</p>
		 						<p>获得方式：</p>
		 						<p>1、年度累计互助宝充值50W，承诺年度消费累计40W</p>
			 				</c:when>
			 				<c:otherwise>
			 					<p>钻石等级</p>
		 						<p>获得方式：</p>
		 						<p>1、年度累计互助宝充值100W，承诺年度消费累计80W</p>
			 				</c:otherwise>
		 				</c:choose>
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">已支付:</span>
		 			<div class="item_right">
		 				<fmt:formatNumber type="number" value="${order.yfk}" maxFractionDigits="0"></fmt:formatNumber>
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">待支付:</span>
		 			<div class="item_right">
		 				<fmt:formatNumber type="number" value="${order.dfk}" maxFractionDigits="0"></fmt:formatNumber>
		 			</div>
		 		</div>
		 		<div class="r-item">
		 			<span class="item_left">对公账号:</span>
		 			<div class="item_right">
		 				<c:forEach items="${dgzhList}" var="dgzh">
		 					${dgzh.listValue }
		 				</c:forEach>
		 			</div>
		 		</div>
		 	</div>
		 	<c:choose>
		 		<c:when test="${order.status lt 2 }">
		 			<p class="zczf">首次支付</p>
		 		</c:when>
			 	<c:otherwise>
			 		<p class="zczf">再次支付</p>
			 	</c:otherwise>
		 	</c:choose>
		 	<div class="check">
		 		<div class="c-bottom">
		 			<input type="radio" name="fkType" value="1" checked="checked"/><span >线上支付</span>
		 		</div>
		 	</div>
		 	<div class="check">
		 		<div class="c-bottom">
		 			<input type="radio" name="fkType" value="2"/><span >线下支付</span>
		 		</div>
		 	</div>
		 	<div class="zfmaney">
		 		<p>请填写本次支付金额：</p>
		 		<input type="text" id="fkMoney_1" name="fkMoney" placeholder="请输入支付金额，单位元" onkeyup="clearNoNum2(this);" maxlength="8"/>
		 	</div>
		 	<div class="zfq">
		 		<div class="item">
		 			<span>支付金额:</span>
		 			<input type="text" name="fkMoney" id="fkMoney_2" placeholder="请输入支付金额，单位元" onkeyup="clearNoNum2(this);" maxlength="8"/>
		 		</div>
		 		<div class="item">
		 			<span>支付时间:</span>
		 			<input type="text" name="fkTime" id="fkTime" readonly="readonly" placeholder="请输入支付时间"/>
		 		</div>
		 		<div class="item active_A zfpzc">
		 			<span>支付凭证:</span>
		 			<p style='position: relative;'><span id="upload_img">上传图片</span></p>
		 		</div>
		 	</div>
		 	<div class="fumaney">
		 		<p class="fuk">付款记录</p>
		 		<c:forEach items="${order.payLogList}" var="payLog">
		 			<c:choose>
		 				<c:when test="${payLog.fkType eq 1 }">
		 					<div class="fklx">
					 			<p>
					 				<span>线上支付:</span>
					 				<c:choose>
					 					<c:when test="${payLog.fkStatus eq 0 }">
					 						<span>待支付</span>
					 					</c:when>
					 					<c:when test="${payLog.fkStatus eq 1 }">
					 						<span>支付成功</span>
					 					</c:when>
					 					<c:otherwise>支付失败</c:otherwise>
					 				</c:choose>
					 			</p>
					 			<p><span>支付金额:</span><span><fmt:formatNumber type="number" value="${payLog.fkMoney}" maxFractionDigits="0"></fmt:formatNumber></span></p>
					 			<p><span>支付时间:</span><span><fmt:formatDate value="${payLog.fkTime }" pattern="yyyy-MM-dd HH:mm"/></span></p>
				 			</div>
		 				</c:when>
		 				<c:otherwise>
		 					<div class="fklx">
					 			<p>
					 				<span>线下支付:</span>
					 				<c:choose>
					 					<c:when test="${payLog.shStatus eq 0 }">
					 						<span>待审核</span>
					 					</c:when>
					 					<c:when test="${payLog.shStatus eq 1 }">
					 						<span>已确认</span>
					 					</c:when>
					 					<c:otherwise>被驳回</c:otherwise>
					 				</c:choose>
					 			</p>
					 			<p><span>支付金额:</span><span><fmt:formatNumber type="number" value="${payLog.fkMoney}" maxFractionDigits="0"></fmt:formatNumber></span></p>
					 			<div class="zfpz"><span>支付凭证:</span><span><img src="${ossurl}${payLog.fkPz}" width="100%"/></span></div>
					 			<p><span>支付时间:</span><span><fmt:formatDate value="${payLog.fkTime }" pattern="yyyy-MM-dd HH:mm"/></span></p>
					 		</div>
		 				</c:otherwise>
		 			</c:choose>
		 			
		 		</c:forEach>
		 	</div>
	        <div class="in-cz">
	        	确认支付
	        </div>
	        <script type="text/javascript">
	        	var wxflag=false;
	        	$(function(){
		        	$('.check').on('click',function(e){
		        		$(this).find('input[type=radio]').prop('checked',true)
		        		if($(this).find('span').text()=='线上支付'){
		        			$('.zfmaney').show()
		        			$('.zfq').hide()
		        		}else{
		        			$('.zfmaney').hide()
		        			$('.zfq').show()
		        		}
	             	});
		        	$("#upload_img").click(function(){
		        		var obj=$(this);
		        		zdy_chooseImg(function(data,res){
				    	 	if(res.code == 0){
				    	 		var imgHtml = "<img srcpath='"+data.pic_path+"' src='"+data.img_url+"' class='up_pic_img' style='width:100%;'/>";
								var str='<div class="imagess">'+imgHtml+'<i onclick="delPic(this);"></i></div>';
								$(str).insertBefore(obj);
								obj.hide();
				    	 	}
		        		});
		        	});
		        	
		        	$('#fkTime').mobiscroll().datetime({
					    theme: mobile_type||'',
						preset: 'datetime',
					    lang: 'zh',
					    display: 'bottom',
					 	dateFormat: 'yy-mm-dd', //日期格式
					 	showLabel:true,
					 	mode:'scroller',
					    maxDate: new Date()
					});
		        	
		        	$(".in-cz").click(function(){
		        		var fkType = $(":radio[name='fkType']:checked").prop("value");
		        		var fkMoney=$("#fkMoney_"+fkType).val();
		        		var dfk="${order.dfk}"*1;
		        		if(isEmpty(fkMoney)){
		        			alert("请填写支付金额");
		        			return;
		        		}
		        		if(fkMoney*1 < 1){
		        			alert("支付金额必须大于1");
		        			return;
		        		}
		        		if(fkMoney*1 > dfk){
		        			alert("支付金额不能大于待付款金额");
		        			return;
		        		}
		        		var data={};
		        		if(fkType == 1){
		        			data={
		        				orderId:"${order.id}",
		        				fkType:fkType,
		        				fkMoney:fkMoney,
		        				xsFkType:1,
		        				logType:1,
		        				level:"${order.level}"
		        			};
		        		}else{
			        		var fkTime=$("#fkTime").val();
			        		if(isEmpty(fkTime)){
			        			alert("请输入付款时间");
			        			return;
			        		}
			        		var fkpz_img=$("#upload_img").parent().find("img");
			        		if(fkpz_img.length == 0){
			        			alert("请上传线下支付凭证");
			        			return;
			        		}
			        		var fkPz=fkpz_img.eq(0).attr("srcpath");
			        		//var fkPz="tianjiu/wx/2017/5/11/306c2787-6210-4a96-9b5a-edb8a779099e.png";
			        		data={
			        			orderId:"${order.id}",
		        				fkType:fkType,
		        				fkMoney:fkMoney,
		        				fkTime:fkTime,
		        				fkPz:fkPz,
		        				logType:1,
		        				level:"${order.level}"
			        		};
		        		}
		        		zdy_ajax({
		        			url : "${path}/m/hzb/savePayLog.do",
		        			type : "post",
		        			dataType : "json",
		        			data : data,
		        			success : function(result){
		        				if(fkType == 1){
		        					if(zfflag){
			        					paywx(fkMoney,result);
		        					}else{
		        						layer.msg("该功能尚未开通")
		        					}
		        				}else{
		        					alert_back("线下充值申请已提交，待审核",function(){
		        						self.location.href="${path}/m/hzb/third.do";
		        					});
		        				}
		        			}
		        		});
		        		
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
	        	});
	        	
	        	function paywx(je,logId){
					wxflag=false;
					zdy_ajax({
						url :"${path}/m/hzb/paycwx.do", 
					    type : 'post', 
					    dataType : 'json', 
					    data:{
					    	logId:logId,
					    	je:je
					    },
					    success : function(data){
					    	var obj=data;
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
				                	//返回我的钱包
				                	alert_back("充值成功",function(){
				                		self.location.href="${path}/m/hzb/third.do";
				                	});
				                }else{ 
				                	layer.msg("取消支付",{
				                	    icon: 2,
				                	    time: 2000 //2秒关闭（如果不配置，默认是3秒）
				                	}, function(){
					                	wxflag=true;
					                	self.location.href="${path}/m/hzb/third.do";
				                	});
				                    //window.location.href="/nailart_index";     
				                    //<span style="font-family:微软雅黑;">当失败后，继续跳转该支付页面让用户可以继续付款，贴别注意不能直接调转jsp，</span><span style="font-size:10.5pt">不然会报</span><span style="font-size:12.0pt"> system:access_denied。</span>  
				                }
				            }); 
					    },
					    error : function(){
					    	wxflag=true;
					    }
				   	});
				}
	        	
	        	function delPic(obj){
					$(obj).parent().parent().find("#upload_img").show();
					$(obj).parent().remove();
	        	}
	        	
	        	function onBridgeReady(){
					wxflag=true;
				}
	        </script>
    </body>
</html>