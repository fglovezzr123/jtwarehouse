<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<%@ taglib prefix="tojo" uri="/WEB-INF/tlds/tojo.tld" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">会议详情</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/meeting-detail.css?v=${sversion}" />
 <style>
     .activePoster{
          height:3.56rem;
     }
     .playings{
          background:url(${path}/resource/img/icons/playing.png) no-repeat center;
          background-size:1.28rem 1.22rem;
          width:100%;
          height:100%;
          position:absolute;
          left:0;
          top:0;
     }
 </style>
</head>
<body>
    <div class="wrapper">
         <div class="activeDetail-box" style="display: block;height:3.75rem;position:relative">
         	  <img class="activePoster" style="height:3.75rem;" src="${obj.coverImg}">
         	  <c:if test="${obj.types ne 2}">
	         	  <div class="playings" onclick="enterRoom('${obj.id}','${obj.types}','${obj.status }','${signupStatus }')"></div>
         	  </c:if>
         </div>
         <div class="sub-header">
         	${obj.titles}
         	<c:set value="${obj.calcStatus() }" var="userName"/>
             <button>${obj.statusDesc}</button>
         </div> 
         <div class="sub-header">
             <img src="${path}/resource/img/icons/onmeeting.jpg"/><span>会议时间</span>：
             <i>${fns:fmt(obj.startTime,'yy/MM/dd HH:mm')}-${fns:fmt(obj.endTime,'yy/MM/dd HH:mm')}</i>
         </div> 
         <div class="sub-header">
            <img src="${path}/resource/img/icons/onmeeting1.png"/><span>会议地点</span>：
            <i>${obj.pname}${obj.cname}${obj.coname}${obj.place}</i>
         </div> 
         <div class="sub-header">
             <img src="${path}/resource/img/icons/onmeeting.jpg"/><span>报名时间</span>：
             <i>${fns:fmt(obj.startSignupTime,'yy/MM/dd HH:mm')}-${fns:fmt(obj.endSignupTime,'yy/MM/dd HH:mm')}</i>
         </div>
         <div class="sub-header">
             <img src="${path}/resource/img/icons/onmeeting.jpg"/><span>报名截止</span>：
             <i id="remind"></i>
         </div>
         <div class="sub-header"  id="meetingprice" style="dispaly:none">
             <img src="${path}/resource/img/icons/onmeeting3.jpg"/><span>门票价格</span>：
             <c:choose><c:when test="${empty obj.ticketPrice or obj.ticketPrice le 0}"><i>免费</i></c:when><c:otherwise><i>${fns:fixed(obj.ticketPrice)}元</i></c:otherwise></c:choose>
         </div> 
         <div class="sub-header">
             <img src="${path}/resource/img/icons/onmeeting2.jpg"/><span>参会方式</span>：
             <c:choose>
             	<c:when test="${obj.types ==1}"><i>线上会议</i></c:when>
             	<c:when test="${obj.types ==2}"><i>线下会议</i></c:when>
             	<c:otherwise><i>线上会议+线下会议</i></c:otherwise>
             </c:choose>
         </div> 
         <c:if test="${not empty obj.upperlimit and obj.upperlimit gt 0}">
         <div class="sub-header">
             <img src="${path}/resource/img/icons/onmeeting2.jpg"/><span>人数限制</span>：<i>${obj.upperlimit}人</i>
         </div>
         </c:if>
         <div class="sub-header margin-bottoms">
             <img src="${path}/resource/img/icons/onmeeting4.jpg"/><span>主办方</span>：<i>${obj.sponsor}</i>
         </div>
         <c:if test="${obj.meetingGuests.size() >0}">
         <div class="sub-header margin-bottoms">
             <span style="margin-left:0">会议嘉宾</span>
             <div class="meetingguest">
                  <c:forEach items="${obj.meetingGuests}" var="item">
                  <div class="guestIcon">
                  	  <img src="${item.imgUrl}"/>
                  	   <div>${item.name}</div>
                  </div>
                  </c:forEach>
                  <br class="clear"/>
             </div>
         </div> 
         </c:if> 
         <c:choose>
         	<c:when test="${obj.types eq 1 or obj.types eq 3}">
         		<c:choose>
         			 <c:when test="${obj.status eq 4 or obj.status eq 5}">
				         <c:if test="${signupStatus eq 1}">
					         <div id="commentOna" class="active_A sub-header margin-bottoms" data-id="${obj.id}" onclick="toLiveRoom(this)">
					            <div style="color:#FF801B;"><b>进入会议直播/点播视频<b></div>   
					         </div>
				         </c:if>
				         <c:if test="${signupStatus ne 1}">
					         <div id="commentOna" class="active_A sub-header margin-bottoms">
					            <div><b>进入会议直播/点播视频<b>(<i style="color:red;font-size:0.2rem;">未报名</i>)</div>   
					         </div>
				         </c:if>
			         </c:when>
			         <c:otherwise>
				         <div id="commentOna" class="active_A sub-header margin-bottoms">
				            <div><b>进入会议直播/点播视频<b>(<i style="color:red;font-size:0.2rem;">未开始</i>)</div>   
				         </div>
			         </c:otherwise>
         		</c:choose>
         	</c:when>
         </c:choose>
         <div class="sub-header"><b>会议介绍</b> </div> 
         <div class="actIntroduction margin-bottoms">
         	<div class="actIntroduction_detail"  >
         	 	${obj.contents}
	        </div>
         </div>
         <c:if test="${obj.meetingProjects.size() >0}">
         <div class="interestBox">
         	 <div class="sub-header" style="padding-left:0"><b>相关项目推荐</b></div> 
         	 <c:forEach items="${obj.meetingProjects}" var="item">
         	 <p>
         	 	 <div class="activePoster" style="background:url(${item.coverImg}) no-repeat center;background-size:100% auto" src="${item.coverImg}" data-id="${item.id}" onclick="showPrjDetail(this)"></div>
         	 	 <div class="sub-header" style="padding-left:0;border:none">
         	 	 	${item.titles}
                   <time>${fns:fmt(obj.createTime,'MM/dd')}</time>
                </div>
         	 </p>
         	 </c:forEach>
         </div>
    	 </c:if>
    </div>
    <div style="height:.2rem"></div>
    <tojo:mtg type="2" meeting="${obj}"/>
</body>
<script type="text/javascript">

function enterRoom(id,type,status,signupStatus){
	
	if(type==1||type==3){
		if(status==4||status==5){
			if(signupStatus==1){
				self.location.href = "${path}/m/meeting/liveuseweb.do?id="+id;
			}else{
				layer.msg("会议未报名");
			}
		}else{
			layer.msg("会议未开始");
		}
	}
}



//匿名函数
$(function(){
   window.addEventListener('popstate',function(e){
	   var url = '${path}/m/meeting/index.do?s='+Math.round();
	   window.history.pushState({title:'',url:url},'',url);
	},false);    
   showCountDown();
   window.setInterval(function(){showCountDown();}, 1000);
   sharefun();
   if(zfflag){
	   $("#meetingprice").show();
   }else{
	   $("#meetingprice").hide();
   }
})
	
function sharefun(){
	//分享设置
	var _title = "会议详情";
	var _imgUrl = "";
	if("${obj.titles}".length > 0){
		_title = "${obj.titles}";
	}
	if("${obj.coverImg}".length > 0){
		_imgUrl ="${obj.coverImg}";
	}
	var _link = home_path+_path+"/m/meeting/detail/index.do?id=${obj.id}";
	wxsharefun(_link,_title,_imgUrl);
}
function signup(){
	self.location.href = "${path}/m/my/meeting/signup/index.do?id=${obj.id}"
}
function footMeeting(){
	self.location.href = "${path}/m/my/myfootPrint_meeting.do";
}
function attentionMeeting(obj){
	zdy_ajax({
  		url: "${path}/m/meeting/attention.do",
  		showLoading:false,
  		data:{id: $(obj).attr("data-id")},
  		success:function(dataobj){
  			if("0"===dataobj["result_code"]){
  				$(obj).find("img").attr("src","${path}/resource/img/icons/gzsuccess.png");
  				$(obj).find("span").text("取消收藏");
  				layer.msg("收藏成功");
  				$(obj).attr("onclick","attentionDelMeeting(this)");
  			}else{
  				alert(dataobj["result_msg"]||"收藏失败")
  			}
  		},
  		complete:function(){
  		}
  	})
}
function attentionDelMeeting(obj){
	zdy_ajax({
  		url: "${path}/m/meeting/removeattention.do",
  		data:{id: $(obj).attr("data-id")},
  		showLoading:false,
  		success:function(dataobj){
  			if("0"===dataobj["result_code"]){
  				$(obj).find("img").attr("src","${path}/resource/img/icons/gz.png");
  				$(obj).find("span").text("收藏");
  				layer.msg("已取消收藏");
  				$(obj).attr("onclick","attentionMeeting(this)");
  			}else{
  				alert(dataobj["result_msg"]||"取消收藏失败")
  			}
  		},
  		complete:function(){
  		}
  	})
}
function toLiveRoom(obj){
	self.location.href = "${path}/m/meeting/liveuseweb.do?id="+$(obj).attr("data-id");
}
function showPrjDetail(obj){
	window.location.href =  "${path}/m/project/detail/index.do?id="+$(obj).attr("data-id");
}
var year  = "${fns:fmt(obj.endSignupTime,'yyyy')}";
var month = "${fns:fmt(obj.endSignupTime,'MM')}";
var day   = "${fns:fmt(obj.endSignupTime,'dd')}";
var hour  = "${fns:fmt(obj.endSignupTime,'HH')}";
var minis = "${fns:fmt(obj.endSignupTime,'mm')}";

var startSignupTime = new Date(parseInt("${fns:fmt(obj.startSignupTime,'yyyy')}"), 
		   parseInt("${fns:fmt(obj.startSignupTime,'MM')}")-1, 
		   parseInt("${fns:fmt(obj.startSignupTime,'dd')}"),
		   parseInt("${fns:fmt(obj.startSignupTime,'HH')}"),
		   parseInt("${fns:fmt(obj.startSignupTime,'mm')}"),
		   parseInt("${fns:fmt(obj.startSignupTime,'ss')}"));
var endSignupTime = new Date(parseInt("${fns:fmt(obj.startSignupTime,'yyyy')}"), 
		   parseInt("${fns:fmt(obj.endSignupTime,'MM')}")-1, 
		   parseInt("${fns:fmt(obj.endSignupTime,'dd')}"),
		   parseInt("${fns:fmt(obj.endSignupTime,'HH')}"),
		   parseInt("${fns:fmt(obj.endSignupTime,'mm')}"),
		   parseInt("${fns:fmt(obj.endSignupTime,'ss')}")); 
function showCountDown() 
{ 
	if($("#remind").length==1){
		var now = new Date(); 
		if(now.getTime()-startSignupTime.getTime()<0){
			$("#remind").html("未开始");
			return;
		}
		
		if(now.getTime()-endSignupTime.getTime()>0){
			$("#remind").html("报名结束");
			return;
		}
		var leftTime=endSignupTime.getTime()-now.getTime(); 
		var leftsecond = parseInt(leftTime/1000); 
		
		var day1=Math.floor(leftsecond/(60*60*24)); 
		var hour=Math.floor((leftsecond-day1*24*60*60)/3600); 
		var minute=Math.floor((leftsecond-day1*24*60*60-hour*3600)/60); 
		var second=Math.floor(leftsecond-day1*24*60*60-hour*3600-minute*60); 
		
		if(leftsecond>0){
			if(day1>0){
				$("#remind").html("剩余"+day1+"天"+hour+"小时"+minute+"分"+second+"秒")
			}else{
				$("#remind").html("剩余"+hour+"小时"+minute+"分"+second+"秒")
			}
		}else{
			$("#remind").html("报名结束");
		}
	}
} 
function signupRemind(obj){
	zdy_ajax({
		url :"${path}/m/meeting/signupremind.do?id="+$(obj).attr("data-id"), 
	    type : 'post', 
	    showLoading:false,
	    dataType : 'json', 
	    success : function(dataobj){
	    	var isSuccess = "0"===dataobj["result_code"]?true:false;
	    	if(!isSuccess){
		    	alert(dataobj.result_msg)
	    		return;
	    	}else{
	    		$(obj).text("已设置提醒");
	    		$(obj).css("background-color","gray");
	    		$(obj).removeAttr("onclick");
	    		alert("恭喜您预报名成功！");
	    	}
	    }
	});
}
</script>
</html>