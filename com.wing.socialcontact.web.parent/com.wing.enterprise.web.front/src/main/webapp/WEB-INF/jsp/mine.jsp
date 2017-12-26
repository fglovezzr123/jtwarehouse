<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no,initial-scale=1,maximum-scale=1, minimum-scale=1, width=device-width, height=device-height"/>
    <title>我的</title>
	<%@ include file="/WEB-INF/jsp/commons/include.com.jsp" %>
	<%@ include file="/WEB-INF/jsp/commons/include_login.jsp" %>
	<style type="text/css">
	    .wode_box dl{
	        width:80%;
	    }
		.wode_ul a li span {
			line-height:1;
		}
         strong,b{font-weight:normal;}
         .wode_box dl dd h3{
            width:72%;
            float:right;
            line-height:.4rem;
         }
         .wode_box dl dd h3{
            width:72%;
            float:right;
         }
         .wode_box dl dd p{
         	font-size: .28rem;
		    color: #808080;
		    line-height: .4rem;
		    width: 72%;
		    float: right;
		    margin-top:0.1rem;
         }
	</style>
</head>
<body>
<div class="wrap">
	<header>
<!--     	<span class="header_return"><img src="${path}/resource/images/return.png" onclick="openNewUrl('indexPage.do')"></span> -->
        <h1>我的</h1>
        
    </header>
	<section> 
    	<div class="wode_box box_box">
        	<dl>
        		<c:if test="${tjyuser.headPortrait != null}">
            		<dt><img src="${tjyuser.headPortrait}"></dt>
            	</c:if>
            	<c:if test="${tjyuser.headPortrait == null}">
            		<dt><img src="${path}/resource/images/default_head.png"></dt>
            	</c:if>
                <dd>
                    <h3>${tjyuser.nickname}</h3>
                    <p>${place}</p>
                </dd>
                
            </dl>
           <a href="${path}/m/qfy/person_setting.do?m=${me.getId()}"> <span>编辑</span></a>
            <div class="clear"></div>
        </div>
        <ul class="wode_ul box_box">
        	<a href="${path}/m/qfy/newMessage.do"><li style="border-bottom:1px solid #e7e7e7;"><span><img src="${path}/resource/images/wode_07.png"></span>新消息通知</li></a>
            <a href="${path}/m/qfy/share.do"><li><span><img src="${path}/resource/images/wode_10.png"></span>推荐分享</li></a>
        </ul>
        <ul class="wode_ul box_box">
        	<a href="${path}/m/qfy/mine/security.do"><li><span><img src="${path}/resource/images/wode_12.png"></span>账号安全</li></a>
        </ul>
        <ul class="wode_ul box_box">
        	<a  id="trouble" href=""><li style="border-bottom:1px solid #e7e7e7;"><span><img src="${path}/resource/images/cjwt.png"></span>常见问题</li></a><!-- 常见问题直接打开链接 -->
            <a href="${path}/m/qfy/leavemsg.do"><li style="border-bottom:1px solid #e7e7e7;"><span><img src="${path}/resource/images/yjfk.png"></span>意见反馈</li></a>
           	<a href="${path}/m/qfy/aboutqfy.do"><li><span><img src="${path}/resource/images/wode_22.png"></span>关于企服联盟</li></a>
        </ul>
        
        <div class="button_box" onclick="openNewUrl('logOut.do')"><button type="button">退出登录</button></div>
    
    </section>
    

</div>
</body>
</html>
<script>

//获取常见问题链接

$(function(){
	zdy_ajax({
		url: "${path}/m/qfy/trouble.do",
		showLoading:false,
		data:{
		},
		success: function(data,res){
			console.log(data);
			if(res.code == 0){
				$("#trouble").attr("href",data[0].link);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	}); 
})
function openNewUrl(url){
	window.location.href=url;
}
</script>