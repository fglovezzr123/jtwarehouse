<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%-- <%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%> --%>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0"
	name="viewport">
<title id="title">同城商友</title>
<link rel="stylesheet" type="text/css" href="${path}/resource/css/index.css?v=${sversion}" />
<link rel="stylesheet" type="text/css" href="${path}/resource/css/friendPlay.css?v=${sversion}" />
  <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		
        <link rel="stylesheet" href="${path}/resource/css/printActiveComment.css" />
		<style>
		  
		    .dww .dw-w-gr .dw-i{
		        color:#292829;
		    }
		    
		     .jz2{
				position: absolute;
			       left: 50%;
			        top: 50%;
			        width:4rem;
			        height:2rem;
			        margin-left:-2rem;
			       margin-top:-1rem;
			        font-size:0.36rem;
			        text-align:center;
			}
		</style>

</head>
<body>
	<div class="wrapper">
	
	       <div class="jz2">  请在“我的”个人信息编辑完善城市资料以便添加好友!</div> 
                                 
	
			<div class="c-footer">
				<span class="want active_A"><a href="javascript:_back()" class="anniu">返  回</a></span>
				<div class="active_A" >
				<a href="javascript:add_address()" style="width:100%;height:100%;display:block">
					<i class="iconfont">&#xe637;</i>
					<span>完善信息</span>
				</a>
				</div>
			</div>
	</div>
	<script type="text/javascript">
		
		function _back(){
			self.location.href = '${path}/wx/netWork/friendRecommend.do';
		}
		function add_address(){
			self.location.href = '${path}/m/my/person_setting.do';
		}
	</script>
</body>
</html>