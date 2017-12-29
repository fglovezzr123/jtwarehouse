<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
        <title id="title">我的动态评论</title>
        <link rel="stylesheet" href="${path}/resource/css/reward-comment.css?v=${sversion}" />
        <style>
		/*文章部分*/
     		.item{
     			padding: 0rem .15rem;
     			background: #F2F3F4;
     			display: flex;
   			    height: 1.05rem;	
     		}
     		.item .it-img{
     			width: 36.23%;
     		}
     		.item .it-img img{
     			width: 100%;
     		}
     		.item .it-cont{
     			/*width: 4.4rem;*/
     			width: 63.77%;
     			font-size: .26rem;
     			padding-left: .15rem;
     			position: relative;
     			box-sizing: border-box;
     		}
     		.item .it-cont h3{
     			font-size: .25rem;
     			font-weight: normal;
     			
			    word-break: break-all;
			     text-overflow: ellipsis;
			     display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
			     -webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
			     -webkit-line-clamp: 2; /** 显示的行数 **/
			     overflow: hidden;  /** 隐藏超出的内容 **/
			     line-height: .27rem;
     		}
     		.item .it-cont .bot{
     			width: 100%;
     			display: flex;
     			justify-content: space-between;
     			font-size: .24rem;
     			position: absolute;
     			/* bottom: 0; */
     			left: 0;
     			padding-left: .15rem;
     			box-sizing: border-box;
     		}
     		.item .it-cont .bot span{
     			line-height: 2.5;
     		}
     		.ul-hide li {
              height:auto;
               }
		</style>
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
                             ${subComment.fkMap.dynamicMap.nickname}
                             <span>${fns:fmt(subComment.fkMap.dynamicMap.issued_date,'yyyy-MM-dd HH:mm:ss')}</span>
                        </h3>
                        <div class="title"> ${subComment.fkMap.dynamicMap.job_name}/ ${subComment.fkMap.dynamicMap.com_name}</div>
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
console.log("${commentList}")
//查看评论内容
function lookCommentInfo(id,dy_type,ad_url,allow_comment){
	if(dy_type==1){
		window.location.href=ad_url;
	}else{
		window.location.href="${path}/m/dynamic/commentPage.do?id="+id;
		
		
	}
	
}
</script>