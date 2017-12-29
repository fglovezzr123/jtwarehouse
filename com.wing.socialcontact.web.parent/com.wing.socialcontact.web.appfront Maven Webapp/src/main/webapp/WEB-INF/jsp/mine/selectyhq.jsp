<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="quheye">
		<title>可用优惠券</title>
		<style type="text/css">
		/*公共样式部分*/
		   body{
       		width: 100%;
      		background: #F2F3F4;
           }
           .y-tit{
           	width: 100%;
           	height: .86rem;
           	padding: 0 .30rem;
           	box-sizing: border-box;
           	background: #fff;
           	display: flex;
           	justify-content: space-between;
           	align-items: center;
           	font-size: .28rem;
           }
           .y-tit span{
           width: 2.0rem;
           	height: .86rem;
           	line-height: .86rem;
           	text-align: center;
           	box-sizing: border-box;
           }
           .y-tit span.bott{
           	color: #0F88EB;
           }
           /*优惠券*/
           .y-item{
           	width: 6.9rem;
           	height: 1.86rem;
           	margin: 0 auto;
           	margin-top: .20rem;
           	display: flex;
           }
           .y-item  .y-imgBox{
           	width: 2.19rem;
           	height: 1.86rem;
           	position: relative;
           }
           .y-item  .y-imgBox img{
           	width: 100%;
           }
           .y-item  .y-imgBox .y-maney{
           	width: 2.19rem;
           	height: 1.86rem;
           	position: absolute;
           	top: 0;
           	left: 0;
           	font-size: .30rem;
           	color: #fff;
           }
           .y-item  .y-imgBox .y-maney p:nth-of-type(1){
           	  margin-top: .40rem;
           	  margin-left: .35rem;
           }
           .y-item  .y-imgBox .y-maney p:nth-of-type(1) span{
           	 font-size: .64rem;
           }
           .y-item  .y-imgBox .y-maney p:nth-of-type(2){
           	font-size: .22rem;
           	margin-left: .60rem;
           }
           .y-itemRight{
           	width: 4.71rem;
           	height: 1.86rem;
           	background: #fff;
           	font-size: .20rem;
           	position: relative;
           }
          .y-itemRight .top{
           	padding-left: .20rem;
           	min-height: .70rem;
           	display: flex;
           }
            .y-itemRight .top span:nth-of-type(1){
            	width: .66rem;
            	height: .30rem;
            	background: #0F88EB;
            	text-align: center;
            	line-height: .30rem;
            	color: #fff;
            	border-radius: .05rem;
            	margin-top: .22rem;
            }
            .y-itemRight .top span:nth-of-type(2){
            	font-size: .26rem;
            	flex: 1;
			    padding: .18rem 0;
			    box-sizing: border-box;
			    margin-left: 0.06rem;
            }
            .y-itemRight .btm{
             	position: absolute;
             	left: 0;
             	bottom: 0;
             }
            .y-itemRight p:nth-of-type(1){
            	line-height: .50rem;
            	margin-top: .1rem;
            	border-bottom: 1px #f2f3f4 solid;
            	padding-left: .20rem;
            	box-sizing: border-box;
            	color: #808080;
            }
            .y-itemRight p:nth-of-type(2){
            	line-height: .56rem;
            	padding-left: .20rem;
            	box-sizing: border-box;
            	color: #808080;
            	white-space: nowrap;
            	overflow: hidden;
            	text-overflow: ellipsis;
            }
            .y-itemRight .sy{
            	position: absolute;
            	top: .75rem;
            	right: .14rem;
            	background: #fff;
            	font-size: .22rem;
            	width: 1.15rem;
            	height: .42rem;
            	border: 1px #0F88EB solid;
            	box-sizing: border-box;
            	color: #0F88EB;
            	border-radius: .2rem;
            	text-align: center;
            	line-height: .38rem;
            }
            .y-itemRight .sybox{
            	position: absolute;
            	top: .35rem;
            	right: .19rem;
            	width: 1.28rem;
            	height: 1.28rem;
            	z-index: 100;
            }
            .y-itemRight .sybox img{
            	width: 100%;
            }
            .y-itemRight .top span.ygq{
            	background: #c2c2c2;
            }
            input[type=radio] {
	     -webkit-appearance: none;
	     position:absolute;
	     right:.4rem;
	     width:.39rem;
	     height:.39rem;
	     border-radius:100%;
	     background:url(${path}/resource/img/icons/radio.png) no-repeat;
	     background-size: 100%;
  }
  input[type=radio]:checked {
      background:url(${path}/resource/img/icons/radiocheck.png) no-repeat;
      background-size: 100%
  }
            
		</style>
	</head>
	<body >
		<div class="y-tit">
			<span class="bott" >不使用优惠券</span>
			<input type="radio" name="jq" id="jq" value="" onclick="unusecoupon1()"/>
		</div>
		<div class="yhq-item" id="contentdiv">	
		</div>
	</body>
	
	
<script> 

function  unusecoupon1(){
	parent.$("#couponlogid").val("");
	parent.$("#couponamount").val("0");
	parent.$("#coupondetail").text("不使用优惠券");
	if(parent){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		setTimeout(function(){parent.layer.close(index);},500)
	}
}


var orderMinBuy="${orderMinBuy}";
var useRange="${useRange}";
var currency=${currency};
$(document).ready(function() {
	content();
});

function content(){
	var tid='1';
		zdy_ajax({
			url: "${path}/m/coupon/selCanUseCouponList.do",
			showLoading:false,
			data:{
				orderMinBuy:orderMinBuy,
				useRange:useRange,
				currency:currency
			},
			success: function(data,res){
				console.log(data);
				if(res.code == 0){
					var con = "";
					if(res.dataobj.clist.length==0){
						alert("暂无可用优惠券！",function(){
							if(parent){
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);
							}
						});
				    };
					$.each(res.dataobj.clist, function(i, n){
						var amount=n.couponAmount; 
						con += '<div class="y-item selcoupon"  id="'+n.id+'"  amount="'+amount+'" "><div class="y-imgBox">';
						con += '<img src="${path}/resource/img/icons/yhqyes.png"/>';
						console.log(String(n.couponAmount))
						console.log(String(n.couponAmount).length)
						var _s = String(n.couponAmount).length
						if(_s==4){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.56rem">'+n.couponAmount+'</span></p><p> ';
						}else if(_s==5){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.48rem">'+n.couponAmount+'</span></p><p> ';
						}else if(_s==6){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.40rem">'+n.couponAmount+'</span></p><p> ';
						}else if(_s==7){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.32rem">'+n.couponAmount+'</span></p><p> ';
						}else if(_s==8){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.32rem">'+n.couponAmount+'</span></p><p> ';
						}else if(_s>8){
							con += '<div class="y-maney"><p><em>￥</em><span style="font-size:.20rem">'+n.couponAmount+'</span></p><p> ';
						}else {
							con += '<div class="y-maney"><p><em>￥</em><span>'+n.couponAmount+'</span></p><p> ';
						}
						if(n.couponType=='1'){
							con += '代金券';
						}else if(n.couponType=='2'){
							con += '满'+n.orderMinBuy+'可用';
						}
						con +='</p></div></div><div class="y-itemRight"><div class="top">';
						if(n.couponType=='1'){
							con += '代金';
						}else if(n.couponType=='2'){
							con += '满减';
						}
						con +='<span>'+n.cgName+'</span></div><div class="btm"><p>'+formatDate(new Date(n.startTime))+'至'+formatDate(new Date(n.endTime))+'</p><p>使用范围：';
						if(n.useRange=='1'){
							con += '全平台';
						}else if(n.useRange=='2'){
							con += '会议';
						}else if(n.useRange=='3'){
							con += '活动';
						}else if(n.useRange=='4'){
							con += '商城';
						}
						con += '</div>';
						con += '<div class="sy">立即使用</div>';
						con += '</div></div>';
					});
					$("#contentdiv").append(con);
					
					$('.selcoupon').bind('click',function() {
								var id = $(this).attr('id');
								var amount = $(this).attr('amount');
								parent.$("#couponlogid").val(id);
								parent.$("#couponamount").val(amount);
								if(useRange=='1'){
									parent.$("#coupondetail").text("已优惠"+amount+"J币");
								}else{
									parent.$("#coupondetail").text("已优惠"+amount+"RMB");
								}
								if(parent){
									var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
									parent.layer.close(index);
								}
					});
				}
			}
		});
}
function openurl(url){
	document.location.href=url;
}
function formatDate(now) { 
	var year=now.getFullYear(); 
	var month=now.getMonth()+1;
	if(month<10){
		month="0"+month;
	}
	var day=now.getDate();
	if(day<10){
		day="0"+day;
	}
	return year+"-"+month+"-"+day; 
} 
	
	
</script> 
</html>