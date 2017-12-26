<!-- 必须登录 -->
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.wing.socialcontact.common.model.Member"%>
<%@page import="com.wing.socialcontact.util.Constants"%>
<% 
	String path2 = request.getContextPath(); 
	Member me=(Member)request.getSession().getAttribute("me");
	String userid = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_ID);
	String headpic = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_HDPIC);
	String nickname = (String)request.getSession().getAttribute(Constants.SESSION_WXUSER_NICKNAME);
	if(null == me){
		response.sendRedirect(path2+"/m/sys/loginPage.do");
	}
%> 
<script type="text/javascript" src="${path}/resource/js/jquery/jquery-1.10.2.min.js?v=${sversion}"></script>

<script>
localStorage.setItem("currentUserId","<%=userid %>");
	var _path="${path}";
	$.ajax({
			url: _path+"/m/app1/get_imtoken.do",
			data:{},
			success: function(data,datas){
				 localStorage.setItem("mytoken",data.dataobj);
			},
			error:function(m){
				alert(m);
			}
		});
</script>
<%-- <script src="${path}/resource/js/libs/outOfChating.js?v=${sversion}" type="text/javascript" charset="utf-8"></script> --%>
