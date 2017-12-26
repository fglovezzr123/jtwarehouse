<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="商友">
		<title>商友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/businessFriend.css?v=${sversion}"/>
	</head>
	<body style="background: #f2f3f4;" id='body'>
	                 <div class='zhezhao'></div>
	                 <div class="hided">
	                      <%--   <span class="active_A"><a style="color:white" href="${path}/wx/businessFriend/talk-chatRoom.do">霍建超群聊专用</a></span>
	                         <span class="active_A"><a style="color:white" href="${path}/wx/businessFriend/talk-huo.do">霍建超单聊专用</a></span> --%>
			                <span class="active_A">创建群聊</span>
			                <span class="active_A">添加好友</span>
			                <span class="active_A">添加群聊</span>
			               <%--  <span class="active_A"><a style="color:white" href="${path}/wx/businessFriend/talkP2P.do">接入网易IM</a></span>
			                 <span class="active_A"><a style="color:white" href="${path}/wx/businessFriend/talk-huojianchao.do">蒙广专用群聊</a></span> --%>
		              </div>
					 <div class="search-box" style="position:fixed;top:0;left:0;z-index:1;">
						<div id="search">商友搜索</div>
						<i class="iconfont icon-add">&#xe60b;</i>	
					</div>
					<div class="info active_A newF" style="margin-top: .90rem;">
						<div class="info-l ">
							<img src="${path}/resource/img/icons/xi_03.png" class="p-img"/>
							<div class="person">
								<span class="p-name">新的朋友</span>
							</div>
						</div>
						<div class="info-r">
							<span class="newCount" style="display:none"></span>
							<i class="iconfont info-R" style="margin-right:0.1rem;">&#xe605;</i>
						</div>
				   </div>
				   <div class="myFoucs active_A myF">
				   		<div class="myfoucs-l">
				   			<img src="${path}/resource/img/icons/myf_03.png" class="p-img"/>
				   			<span><i>我的粉丝/&nbsp;</i><em>我的关注</em></span>
				   		</div>
				   		<i class="iconfont info-R" style="margin-right:0.1rem;">&#xe605;</i>
				   </div>
				   <div class="info active_A myQz">
						<div class="info-l">
							<img src="${path}/resource/img/icons/myq_03.png" class="p-img"/>
							<div class="person">
								<span class="p-name">我的群组</span>
							</div>
						</div>
						<i class="iconfont info-R" style="margin-right:0.1rem;">&#xe605;</i>
				   </div>
				   <div id="reconSH" style="display:none">
					   <div class="foucsF">
					   		 <p class="foucsFriend" >心标好友</p>
					   </div>	   
					    <div id="letter"></div>
						<div class="sort_box"></div>
						<div class="initials">
							<ul>
								<li><img src="${path}/resource/img/icons/068.png"></li>
							</ul>
						</div>
					</div>	
				     <jsp:include page="../commons/include_footer.jsp" >
						<jsp:param name="menuid" value="2" />
					</jsp:include>
		<script src="${path}/resource/js/libs/jquery.charfirst.pinyin.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
	    <script src="${path}/resource/js/businessFriend.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
	</body>

</html>
