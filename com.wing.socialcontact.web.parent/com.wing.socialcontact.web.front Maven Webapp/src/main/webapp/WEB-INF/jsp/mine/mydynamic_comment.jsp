<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
        <title id="title">我的动态评论</title>
        <link rel="stylesheet" href="${path}/resource/css/reward-comment.css?v=${sversion}" />
    </head>
    <body>
        <div class="wrapper"> 
           <c:forEach  var="subComment" items="${commentList}" >
            <c:if test="${!empty subComment.fkMap}">
              <div class="reward-commentBox">
                   <div>
                        <div class="userIcon"  style="background-image:url('${subComment.fkMap.dynamicMap.head_portrait}')"></div>
                   </div>
                   <div onclick=lookCommentInfo("${subComment.fkMap.dynamicMap.id}")>
                        <h3>
                             <span>${subComment.fkMap.dynamicMap.nickname}</span>
                             <script type="text/javascript">
                             console.log("${subComment.fkMap.dynamicMap.honor_flag}")
                             </script>
                             <c:choose>
									<c:when test="${subComment.fkMap.dynamicMap.honor_flag == 'honor_001' }">
									   <div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>
									</c:when>
									<c:when test="${subComment.fkMap.dynamicMap.honor_flag == 'honor_002' }">
										<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>
									</c:when>
									<c:when test="${subComment.fkMap.dynamicMap.honor_flag == 'honor_003'}">
										<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>
									</c:when>
									<c:when test="${subComment.fkMap.dynamicMap.honor_flag == 'honor_004'}">
										<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>
									</c:when>
								</c:choose>	 
                             <em style="">${fns:fmt(subComment.fkMap.dynamicMap.issued_date,'yyyy-MM-dd HH:mm:ss')}</em>
                        </h3>
                        <div class="title" style="margin-top:0.1rem;line-height:normal"> ${subComment.fkMap.dynamicMap.job_name}/ ${subComment.fkMap.dynamicMap.com_name}</div>
                        <p> ${subComment.fkMap.dynamicMap.dy_content}
	                        <c:if test="${!empty subComment.fkMap.dynamicMap.articleid}">
		                        <div  class="item">
									<div class="it-img"><img src="${ossurl}${subComment.fkMap.dynamicMap.aimgpath}"/></div>
									<div class="it-cont">
										<h3>${subComment.fkMap.dynamicMap.atitle}</h3>
										<div class="bot"><span><fmt:formatDate value="${subComment.fkMap.dynamicMap.adate}"/></span><span>${subComment.fkMap.dynamicMap.aclassname}</span></div>
									</div>
								</div>
	                        </c:if>
                           <c:if test="${!empty subComment.fkMap.picList[0].picUrl}">
                           	 <img src=" ${ossurl}${subComment.fkMap.picList[0].picUrl}"/>
                           </c:if> 
                        </p>
                        <div class="reward-comment-cotent">
                           <span>评论内容 : </span> ${subComment.cmeDesc}
                           </br><font color="#52a1c0">查看详情</font>
                        </div>
                   </div>
                   <br class="clear"/>
              </div>
 			</c:if>
         </c:forEach>
          <c:if test="${empty commentList}">
          	 <div class="jz">  暂无动态评论!</div> 
         </c:if>
        </div>

    </body>
</html>
<script type="text/javascript">
//查看评论内容
function lookCommentInfo(id,dy_type,ad_url,allow_comment){
	if(dy_type==1){
		window.location.href=ad_url;
	}else{
		window.location.href="${path}/m/dynamic/commentPage.do?id="+id;
		
		
	}
	
}
</script>