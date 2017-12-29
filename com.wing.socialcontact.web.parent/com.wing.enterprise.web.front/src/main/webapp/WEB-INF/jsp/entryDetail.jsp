<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=1, initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
    <title></title>
    <style>
    
     strong,b{font-weight:bold;}
    header{
	         position: fixed;
		    top: 0;
		    left: 0;
		    z-index: 600;
         }
         section{
        	 margin-top:.83rem;
         }
    </style>
</head>
<body>
<div class="wrap">
	<header>
    	<span class="header_return"><img src="${path }/resource/images/return.png" onclick="backForAndroid()"></span>
        <h1 id="h1"></h1>
    </header>
	<section>
    	<div class="lineheight0">
    		<jsp:include page="commons/include_entryDetail_banner.jsp">
				<jsp:param name="entryId" value="${entryId}" />
			</jsp:include>
    	</div>
    	<div class="lmhy_box">
       	  <dl>
            	<dt id="logoImg"></dt>
                <dd id="qy_1"></dd>
                <div class="clear"></div>
           </dl>
           <div class="lmhy_article"></div>
        </div>
        <div class="lmhy_renzheng" style="display:flex"></div>
        <div class="lmhy_miaoshu box_box">
            <ul>
                <li><span class="lmhy_tabhv" onclick="changeDetail(this,1)">详情介绍</span></li>
                <li><span onclick="changeDetail(this,2)">服务案例</span></li>
                <div class="clear"></div>
            </ul>
            <div class="lmhy_js_pp" id="detailDesc"  style="display: block;">
            </div>
            <div class="lmhy_js_pp" id="serviceCase" style="display: none;">
            </div>
        </div>
        
        <div style=" height:.88rem; margin-top:.15rem;"></div>
        <div class="bottom_button">
        	<ul>
            	<li id="phoneLine"></li>
                <li class="bj_blue" onclick="goMsg()">在线咨询</li>
            </ul>
        </div>
    </section>
</div>
</body>
</html>
<script type="text/javascript">

//var m = "${me.getId()}";

var toUid = '';
var flag = '${flag}';
var tel="";
function loadDetail(){
	zdy_ajax({
		url: "${path}/m/qfy/entryDetail.do",
		showLoading:false,
		data:{
			//m:m,
			entryId:"${entryId}"
		},
		success: function(data,res){
			console.log(res);
			if(res.code == 0){
				tel = data.phone;
				$("#phoneLine").html('<a onclick="telPhone()">电话咨询</a>');
// 				$("#phoneLine").html('<a href="tel:'+data.phone+'">电话咨询</a>');
				toUid = data.toUid;
				$("#h1").html(data.entryPrise.name);
				$("#logoImg").html('<img src="'+_oss_url+data.entryPrise.logoImgPath+'">');
				$("#qy_1").html('<h2><span>已服务<i>'+data.entryPrise.serviceCount+'</i>家</span>'+data.entryPrise.title+'</h2><p>'+data.entryPrise.titleDesc+'</p>');
				$(".lmhy_article").html(data.entryPrise.entryDesc);
				$("#detailDesc").html(data.entryPrise.detailDesc);
				$("#serviceCase").html(data.entryPrise.serviceCase);
				var reconImgString = '';
				if(data.reconImgs){
					reconImgString += '<p>认证：</p>';
				}
				$.each(data.reconImgs , function(i, n){
					reconImgString += '<img style="padding:0 .14rem; height:.55rem;margin-top:10px;" src="'+_oss_url+n.imgPath+'"/>';
// 					reconImgString += '<span><img src="'+_oss_url+n.imgPath+'"/></span>';
				});
				$(".lmhy_renzheng").html(reconImgString);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
}
$(document).ready(function() {
	loadDetail();
});

function changeDetail(t,flag){
	if(!$(t).hasClass("lmhy_tabhv")){
	    $(".lmhy_tabhv").removeClass("lmhy_tabhv");
	    $(t).addClass("lmhy_tabhv");
	 }
	if(flag == 1){
		//详情
		$("#detailDesc").css('display','block'); 
		$("#serviceCase").css('display','none'); 
	}else{
		//案例
		$("#detailDesc").css('display','none'); 
		$("#serviceCase").css('display','block');
	}
}
function goMsg(){
	if(toUid == '' || toUid == undefined){
		layer.msg("该企业暂未开启在线咨询服务!");
		//alert("该企业暂未开启客服服务!",function(){});
	}else{
		zdy_ajax({
			url: "${path}/m/qfy/staticClick.do",
			showLoading:false,
			data:{
				entryId:"${entryId}",
				type:2
			},
			success: function(data,res){
			},
		    error:function(e){
		    }
		}); 
		document.location.href="${path}/m/qfy/goMsg.do?uid="+toUid;
	}
}
function telPhone(){
	if(tel == '' || tel == undefined){
		layer.msg("该企业暂未开启电话咨询服务!");
		//alert("该企业暂未开启客服服务!",function(){});
	}
	zdy_ajax({
		url: "${path}/m/qfy/staticClick.do",
		showLoading:false,
		data:{
			entryId:"${entryId}",
			type:1
		},
		success: function(data,res){
		},
	    error:function(e){
	    }
	}); 
	document.location.href="tel:"+tel;
}
function goToBack(){
	document.location.href= '${page}';
	//javascript:history.back(-1);
}
function backForAndroid(){
	document.location.href= '${page}';
}
</script>