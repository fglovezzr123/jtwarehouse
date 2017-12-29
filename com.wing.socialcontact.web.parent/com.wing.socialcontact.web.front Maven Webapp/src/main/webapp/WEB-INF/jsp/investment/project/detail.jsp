<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/swiper-3.3.1.min.css" />
<script src="${path }/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">项目详情</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/project-detail-new.css?v=${sversion}" />
<link rel="stylesheet" href="${path}/resource/css/living.css?v=${sversion}" />
<script type="text/javascript"
	src="${http}://cnstatic01.e.vhall.com/jssdk/dist/2.3.0/vhallSDK.js"></script>
<style>
      #descTexts{
            overflow: hidden;
			text-overflow: ellipsis;
			line-height:0.4rem;
      }
      #descTexts1{
            overflow: hidden;
			text-overflow: ellipsis;
			line-height:0.4rem;
			padding:0 .3rem;
			box-sizing:border-box;
			background:#fff;
      }
      .swiper-container-horizontal>.swiper-pagination-bullets .swiper-pagination-bullet{
         background:white;
      }
      .tel1,.tel2{
        width:3.15rem!important;
      }
</style>
</head>
<body>
  <div class="wrapper">
     <div class="warmTip">温馨提示 : 所有联营项目均已通过天九集团认证</div>
     <div class="project-small-poster">
          <img src="${obj.coverImg}"/>
          <div class="project-poster-right">
              <ul >
                 <li>${obj.titles}&nbsp;</li>
                 <li>${obj.titles2}&nbsp;</li>
                 <li>
                     <span>${fns:fmt(obj.startTime,'yyyy-MM-dd') }</span>
                     <span><i>${obj.extProps.willCount}人</i>有合作意向</span>
                     <span><i id="counts">${obj.extProps.attentionCount}人</i>收藏</span>
                    
                     <br class="clear"/>
                 </li>
              </ul>
          </div> 
          <br class="clear"/>
     </div> 
<c:choose>
	<c:when test="${not empty obj.projectImages and obj.projectImages.size() gt 0}">
		<div class="enrolling">
		     <script>
		          var src=[];
		     </script>
			 <div class="theTip">${obj.statusDesc}</div>
			 <c:forEach items="${obj.projectImages}" var="item">
       	 	   <%--  <img src="${item.imageUrl}" style="width:100%;"/> --%>
       	 	    <script>
       	 	         src.push('${item.imageUrl}');
       	 	    </script> 
			 </c:forEach>
			 
			 <div class="swipercontainer" style="height:4.14rem;position:relative;overflow:hidden">
			    	<ul id="bannerul" class="swiper-wrapper" >
                        
			    	</ul>
			    	<div class="swiper-pagination"></div>
			 </div> 
			 
			 <script>
			     
			     swipers(src);   
			     function swipers(src){
			    	if(!src){
			    		$('.swipercontainer').css('display','none');
			    		return;
			    	}
			    	 var str='';
			    	 for(var i=0;i<src.length;i++){
			    		 str += '<li class="swiper-slide"><img src="'+src[i]+'"/></li>';
			    	 }
			    	  $('#bannerul').append(str);
			    	 var swiper = new Swiper('.swipercontainer', {
						    pagination: '.swiper-pagination',
						    paginationClickable: true,
						    spaceBetween: 30,
						    autoplayDisableOnInteraction : false,
						    autoplay: 4000
						});
			     }
			 </script>
		</div>  
     </c:when>
    <c:otherwise>
     <div class="enrolling">
        <div class="theTip">${obj.statusDesc}</div>
        <img src="${obj.coverImg}"/>
     </div>
     </c:otherwise>
</c:choose>
<div class="enrolling">
    <div class="theTip">项目介绍</div>
    <div id="descTexts">
         
    </div>
   <!--  <a style="color:#0066ff" onclick="showmore($(this).text())" class="mores" href="javascript:void(0)">更多</a> -->
</div>
<script type="text/javascript">
   var proMent = "${obj.prjPres}";
   console.log(proMent.length)
   if(proMent.length>100){
		$("#descTexts").append(proMent.substring(0,100)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
	}else{
		$("#descTexts").append(proMent);
	}
  function stretch(text){
	if(text=="更多"){
		$("#descTexts").html(proMent+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">收起</span>');
	}else{
		$("#descTexts").html(proMent.substring(0,100)+'...'+'<span class="more" onclick="stretch($(this).text())" style="margin-left:0.1rem;color:blue">更多</span>');
	}
 }
</script>
<c:if test="${not empty signObj }">
	<div class="enrolling">
	   <div class="theTip">推荐视频</div>
	   <%-- <iframe frameborder="0" border="0" src="${url}" width="100%" height="180">
	   </iframe> --%>
	   
	   <div class="vimg" id="vedios"></div>
	</div>
</c:if>
<div class="enrolling1">
    <div class="theTip1" id="prodetail">项目详情</div>
    <div id="descTexts1" >
          ${obj.prjDesc}
    </div>
</div>
<c:choose>
	<c:when test="${'招募中' eq obj.statusDesc }">
		<div class="project-detail-submit">
			<c:choose>
				<c:when test="${1 eq obj.extProps.attentiond}">
					<div class="active_A" data-id="${obj.id}" style="background-image:url(${path}/resource/img/icons/gzsuccess.png);" onclick="attentionDelPrj(this)">取消收藏</div>
				</c:when>
				<c:otherwise>
					<div class="active_A" data-id="${obj.id}" style="background-image:url(${path}/resource/img/icons/gz.png);" onclick="attentionPrj(this)">收藏</div>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${1 eq obj.extProps.willed}">
					<div class="active_A" style="background-color:gray;" onclick="footPrj()">我已发送意向</div>
				</c:when>
				<c:otherwise>
					<div class="active_A" onclick="signupPrj('${obj.id}')">我有意向</div>
				</c:otherwise>
			</c:choose>
			<br class="clear" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="project-detail-submit1">
			<c:choose>
				<c:when test="${1 eq obj.extProps.attentiond}">
					<div class="active_A" data-id="${obj.id}"  onclick="attentionDelPrj(this)">取消收藏</div>
				</c:when>
				<c:otherwise>
					<div class="active_A" data-id="${obj.id}"  onclick="attentionPrj(this)">收藏</div>
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>

<input type="hidden" id="signObj" value="${signObj}">
 </div>
</body>
<script type="text/javascript">

function signupPrj(id){
	self.location.href = "${path}/m/project/signup/index.do?id="+id;
}
function footPrj(){
	self.location.href = "${path}/m/my/myfootPrint_project.do?tab=2";
}
function attentionPrj(obj){
	zdy_ajax({
  		url: "${path}/m/project/attention.do",
  		data:{id: $(obj).attr("data-id")},
  		success:function(dataobj){
  			if("0"===dataobj["result_code"]){
  				/* $(obj).css("background-image","url(${path}/resource/img/icons/doublessss.jpg)");
  				$(obj).css("background-size","0.3rem 0.3rem");
  				$(obj).text("取消收藏");
  				$(obj).attr("onclick","attentionDelPrj(this)");
  				$("#counts").text(dataobj["counts"]+"人"); */
  				location.reload([true]);
  			}else{
  				alert(dataobj["result_msg"]||"收藏失败")
  			}
  		},
  		complete:function(){
  		}
  	})
}

function attentionDelPrj(obj){
	zdy_ajax({
  		url: "${path}/m/project/removeattention.do",
  		data:{id: $(obj).attr("data-id")},
  		success:function(dataobj){
  			if("0"===dataobj["result_code"]){
  				/* $(obj).css("background-image","url(${path}/resource/img/icons/doublessss.jpg)");
  				$(obj).css("background-size","0.3rem 0.3rem");
  				$(obj).text("收藏");
  				$(obj).attr("onclick","attentionPrj(this)");
  				$("#counts").text(dataobj["counts"]+"人"); */
  				location.reload([true]);
  			}else{
  				alert(dataobj["result_msg"]||"取消收藏失败")
  			}
  		},
  		complete:function(){
  		}
  	})
}
$(function(){
	sharefun();
	/* if($("#descTexts").attr("heights")=='1.2rem'){
		$('.mores').css('display','block');
	}else{
		$('.mores').css('display','none');
	} */
	
	
	if (!isEmpty(${signObj.account})) {
	var account='${signObj.account}';
	var app_key='${signObj.app_key}';
	var email='${signObj.email}';
	var roomid='${signObj.roomid}';
	var sign='${signObj.sign}';
	var signedat='${signObj.signedat}';
	var username='${signObj.username}';
	
		VHALL_SDK.init({
			account : account,
			email : email,
			username : username,
			roomid : roomid,
			app_key : app_key,
			signedat : signedat,
			sign : sign,
			facedom : '',
			textdom : '',
			videoContent : '#vedios',
		});
		VHALL_SDK.on('vhall_record_history_chat_msg',
				function(msg) {
					alert(JSON.stringify(msg))
				});
		VHALL_SDK.on('ready', function() {
		});
		VHALL_SDK.on('error', function(msg) {
		});
		VHALL_SDK.on("playerReady", function() {
		});
	} else {
		$("#vedios").hide();
	}
	
	
});

function sharefun(){
	//分享设置
	var _title = "项目详情";
	var _imgUrl = "";
	if("${obj.titles}".length > 0){
		_title = "${obj.titles}";
		console.log(_title)
	}
	if("${obj.coverImg}".length > 0){
		_imgUrl ="${obj.coverImg}";
	}
	var _link = home_path+_path+"/m/project/detail/index.do?id=${obj.id}";
	wxsharefun(_link,_title,_imgUrl);
}
/* function showmore(text){
	if(text=="更多"  ){
		$('.mores').text('收起');
		$("#descTexts").css("height","auto");
		$("#descTexts").attr("heights","auto")
	}else{
		$('.mores').text('更多');
		$("#descTexts").css("height","1.2rem");
		$("#descTexts").attr("heights","1.2rem")
	}
} */

</script>
</html>