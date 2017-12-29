<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<%@ taglib prefix="tojo" uri="/WEB-INF/tlds/tojo.tld" %>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title id="title">直播详情</title>
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
     .buttonss {
		width: 100%;
		position: relative;
		height: 1rem;
		line-height: 1rem;
		text-align: center;
		box-shadow: -2px -5px 5px #e6e6e6;
		position: fixed;
		left: 0px;
		bottom: 0px;
		/* box-shadow: 0px 6px 4px 5px #808080; */
	}
	
	.buttonss div:nth-child(1) {
		width: 50%;
		height: 100%;
		float: left;
		font-size: 0.3rem;
		background: white;
	}
	
	.buttonss div:nth-child(2) {
		width: 50%;
		float: right;
		font-size: 0.3rem;
		height: 100%;
		background: #1087eb;
		color: white
	}
	
	.buttonss img {
		height: 0.3rem;
		width: 0.3rem;
	}
 </style>
</head>
<body>
    <div class="wrapper">
         <div class="activeDetail-box" style="display: block;height:3.75rem;position:relative">
         	  <img class="activePoster" style="height:3.75rem;" src="${obj.imagepath}">
	         	  <div class="playings" onclick="enterRoom('${obj.id}','${obj.status }','${signupStatus }','${obj.isEnd}','${obj.ticketPrice }')"></div>
         </div>
         <div class="sub-header">
         	${obj.title}&nbsp;&nbsp;<c:if test="${obj.status eq 3}"><font color="red"><i>已结束</i></font></c:if>
         	<%-- <c:set value="${obj.calcStatus() }" var="userName"/>
             <button>${obj.statusDesc}</button> --%>
         </div> 
         <div class="sub-header">
             <img src="${path}/resource/img/icons/onmeeting.jpg"/><span>直播时间</span>：
             <i>${fns:fmt(obj.startTime,'yy/MM/dd HH:mm')}-${fns:fmt(obj.endTime,'yy/MM/dd HH:mm')}</i>
         </div> 
         <div class="sub-header">
             <img src="${path}/resource/img/icons/onmeeting3.jpg"/><span>门票价格</span>：
             <c:choose><c:when test="${empty obj.ticketPrice or obj.ticketPrice le 0}"><i>免费</i></c:when><c:otherwise><i>${obj.ticketPrice}  J币</i></c:otherwise></c:choose> &nbsp;&nbsp;<c:if test="${signupStatus eq 1}"><c:if test="${obj.ticketPrice gt 0}"><font color="red"><i>已支付</i></font></c:if></c:if>
         </div>
         <c:if test="${obj.bookname ne '' }">
	         <div class="sub-header">
	             <img src="${path}/resource/img/icons/onmeeting2.jpg"/><span>书籍名称</span>：
	             <i>${obj.bookname}</i>
	         </div>
         
         </c:if> 
         <div class="sub-header">
            <img src="${path}/resource/img/icons/onmeeting2.jpg"/><span>直播类型</span>：
             <c:choose>
             	<c:when test="${obj.type ==1}"><i>俊卿解惑</i></c:when>
             	<c:when test="${obj.type ==2}"><i>与总统谈心</i></c:when>
             	<c:when test="${obj.type ==3}"><i>商界冠军直播秀</i></c:when>
             	<c:otherwise><i>总裁读书会</i></c:otherwise>
             </c:choose>
         </div> 
         <c:if test="${obj.liveGuests.size() >0}">
         <div class="sub-header margin-bottoms">
             <span style="margin-left:0">直播嘉宾</span>
             <div class="meetingguest">
                  <c:forEach items="${obj.liveGuests}" var="item">
                  <div class="guestIcon">
                  	  <img src="${item.imgUrl}"/>
                  	   <div>${item.name}</div>
                  </div>
                  </c:forEach>
                  <br class="clear"/>
             </div>
         </div> 
         </c:if> 
         <c:if test="${obj.ticketPrice eq 0 and obj.status ne 1}">
       		<div id="commentOna" class="active_A sub-header margin-bottoms" data-id="${obj.id}" onclick="toLiveRoom(this)">
			       <div style="color:#FF801B;"><b>进入直播/点播视频<b></div>   
		    </div>
       	</c:if>
         <c:if test="${obj.ticketPrice eq 0  and obj.status eq 1}">
       		<div id="commentOna" class="active_A sub-header margin-bottoms"  onclick="wkshint();">
		        <div><b>进入直播/点播视频<b>(<i style="color:red;font-size:0.2rem;">未开始</i>)</div>   
		    </div>
       	</c:if>
       	<c:if test="${obj.ticketPrice ne 0 }">
			<c:choose>
			  <c:when test="${ obj.status eq 3}">
				   <c:if test="${signupStatus eq 1 or obj.isEnd eq 0}">
					    <div id="commentOna" class="active_A sub-header margin-bottoms" data-id="${obj.id}" onclick="toLiveRoom(this)">
					       <div style="color:#FF801B;"><b>进入直播/点播视频<b></div>   
					    </div>
				   </c:if>
				   <c:if test="${signupStatus ne 1 and obj.isEnd eq 1}">
					    <div id="commentOna" class="active_A sub-header margin-bottoms" onclick="wbmhint()">
					       <div><b>进入直播/点播视频<b>(<i style="color:red;font-size:0.2rem;">未报名</i>)</div>   
					    </div>
				   </c:if>
			  </c:when>
			  <c:when test="${ obj.status eq 2}">
			  	<c:if test="${signupStatus eq 1 }">
				    <div id="commentOna" class="active_A sub-header margin-bottoms" data-id="${obj.id}" onclick="toLiveRoom(this)">
				       <div style="color:#FF801B;"><b>进入直播/点播视频<b></div>   
				    </div>
			   </c:if>
			   <c:if test="${signupStatus ne 1 }">
				    <div id="commentOna" class="active_A sub-header margin-bottoms"  onclick="wbmhint()">
				       <div><b>进入直播/点播视频<b>(<i style="color:red;font-size:0.2rem;">未报名</i>)</div>   
				    </div>
			   </c:if>
			  </c:when>
			  <c:otherwise>
			  		<c:if test="${signupStatus eq 1 }">
					    <div id="commentOna" class="active_A sub-header margin-bottoms"  onclick="wkshint();">
					      <div><b>进入直播/点播视频<b>(<i style="color:red;font-size:0.2rem;">未开始</i>)</div>   
					   </div>
				   </c:if>
				   <c:if test="${signupStatus ne 1 }">
					    <div id="commentOna" class="active_A sub-header margin-bottoms"  onclick="wbmhint()">
					       <div><b>进入直播/点播视频<b>(<i style="color:red;font-size:0.2rem;">未报名</i>)</div>   
					    </div>
				   </c:if>
			  </c:otherwise>
			</c:choose>
       	</c:if>
         <div class="sub-header"><b>直播介绍</b> </div> 
         <div class="actIntroduction margin-bottoms">
         	<div class="actIntroduction_detail"  >
         	 	${obj.content}
	        </div>
         </div>
    </div>
    <div style="height:.2rem"></div>
    <div class="buttonss">
		<div  id="collectss" onclick="toCollect()">
       	</div>
       	<c:if test="${obj.ticketPrice eq 0 }">
       		<div id="baoming" class="baomingbg" onclick="enterRoom('${obj.id}','${obj.status }','${signupStatus }','${obj.isEnd}')">进入直播间
			</div>
       	</c:if>
       	<c:if test="${obj.ticketPrice ne 0 }">
	       	<c:choose>
	       		<c:when test="${obj.status eq 3 }">
	       			<c:if test="${signupStatus eq 1 or obj.isEnd eq 0}">
	       				<div id="baoming" class="baomingbg" onclick="enterRoom('${obj.id}','${obj.status }','${signupStatus }','${obj.isEnd}')">进入直播间
	       				</div>
	       			</c:if>
	       			<c:if test="${signupStatus ne 1 and obj.isEnd eq 1}">
					    <div id="baoming" class="baomingbg" onclick="signup()">立即报名
	       				</div>
				   </c:if>
	       		</c:when>
	       		<c:when test="${obj.status eq 2 }">
	       			<c:if test="${signupStatus eq 1 }">
	       				<div id="baoming" class="baomingbg" onclick="enterRoom('${obj.id}','${obj.status }','${signupStatus }','${obj.isEnd}')">进入直播间
	       				</div>
	       			</c:if>
	       			<c:if test="${signupStatus ne 1}">
					    <div id="baoming" class="baomingbg" onclick="signup()">立即报名
	       				</div>
				   </c:if>
	       		</c:when>
	       		<c:otherwise>
	       			<c:if test="${signupStatus eq 1}">
	       				<div id="baoming" class="baomingbg">您已报名
	       				</div>
	       			</c:if>
	       			<c:if test="${signupStatus ne 1}">
	       				<div id="baoming" class="baomingbg" onclick="signup()">立即报名
	       				</div>
	       			</c:if>
	       		</c:otherwise>
	       	</c:choose>
       	</c:if>
        	<br class="clear"/>
    </div>
</body>
<script type="text/javascript">
var id = '${obj.id}';
var isEnd = ${obj.isEnd};

$(function(){
	
	var title='${obj.title}';
	var imgUrl='${obj.imagepath}';
	//分享设置
	var _title = "直播详情";
	var _imgUrl = "http://tianjiu.oss-cn-beijing.aliyuncs.com/upload/system/a3a14eac-c89a-4e6a-bccd-99602c3f74ad.png";
	if(title.length > 0){
		_title = title;
	}
	if(imgUrl.length > 0){
		_imgUrl = imgUrl;
	}
	var _link = home_path+_path+"/library/m/live/detail.do?id="+id;
	wxsharefun(_link,_title,_imgUrl);
	
});

function wkshint(){
	layer.msg("直播未开始");
}
function wbmhint(){
	layer.msg("直播未报名");
}

function toCollect(){
	var collect = $("#collectss").text().replace(/[\r\n]/g,"").replace(/\ +/g,"");
	   if(collect=='取消收藏'){
		   zdy_ajax({
			   url:_path+"/mycollection/m/del.do",
			   showLoading:false,
			   data:{
				 'id':id,
				 'type':4
			   },
			   success:function(data,be){
				layer.msg('已取消收藏');
				$('#collectss').text('');
				$('#collectss').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
			   },
			   error:function(data){
			   }
			}); 
	   }else{
		   zdy_ajax({
			   url:_path+"/mycollection/m/add.do",
			   showLoading:false,
			   data:{
				 'id':id,
				 'type':4
			   },
			   success:function(data,be){
				layer.msg("收藏成功");
				$('#collectss').text('');
				$('#collectss').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
			   },
			   error:function(data){
			   }
			}); 
	   };
}

function toLiveRoom (){
	self.location.href = "${path}/library/m/liveuseweb.do?id="+id;
}

function enterRoom(id,status,signupStatus,isend,price){
	if(signupStatus==1){
		if(status==2||status==3){
			self.location.href = "${path}/library/m/liveuseweb.do?id="+id;
		}else{
			layer.msg("直播未开始");
		}
	}else{
		if(price>0){
			if(isEnd==1){
				//未报名  不免费  结束收费
				self.location.href = "${path}/library/m/live/signupPage.do?id=${obj.id}";
			}else{
				//未报名  不免费 结束不收费  
				if(status==3){
					//已结束
					self.location.href = "${path}/library/m/liveuseweb.do?id="+id;
				}else{
					self.location.href = "${path}/library/m/live/signupPage.do?id=${obj.id}";
				}
			}
		}else{
			if(status==2||status==3){
				self.location.href = "${path}/library/m/liveuseweb.do?id="+id;
			}else{
				layer.msg("直播未开始");
			}
		}
	}
	
	/* if(price>0){
		if(status==2||status==3){
			if(isend==1){
				if(signupStatus==1){
					self.location.href = "${path}/library/m/liveuseweb.do?id="+id;
				}else{
					//layer.msg("直播未报名");
					self.location.href = "${path}/library/m/live/signupPage.do?id=${obj.id}"
				}
			}else{
				self.location.href = "${path}/library/m/liveuseweb.do?id="+id;
			}
		}else{
			
			layer.msg("直播未开始");
		}
	}else{
		if(status==2||status==3){
			self.location.href = "${path}/library/m/liveuseweb.do?id="+id;
		}else{
			layer.msg("直播未开始");
		}
	} */
}



//匿名函数
$(function(){
	
	if(${iscollection} ==1){
	$('#collectss').text('');
	$('#collectss').append('<img src="${path }/resource/img/icons/gzsuccess.png"><span>取消收藏</span>');
	}else{	
		$('#collectss').text('');
		$('#collectss').append('<img src="${path }/resource/img/icons/gz.png"><span>收藏</span>');
	}
   //sharefun();
})
	
/**
 * 报名
 */
function signup(){
	//self.location.href = "${path}/m/my/meeting/signup/index.do?id=${obj.id}"
	self.location.href = "${path}/library/m/live/signupPage.do?id=${obj.id}"
}
</script>
</html>