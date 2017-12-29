<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">报名成功</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
        <link rel="stylesheet" href="${path}/resource/css/name-suc.css?v=${sversion}" />
        <script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
        <style>
           .right-signal{
            color:#F00;
           }
        </style>
    </head>
    
  
    <body>
    	
        <div class="wrapper">
             <div class="right-signal">
                  	报名成功</br>
             </div></br></br>

                  <div class="name-head1">
                       	<span id="sp">3秒后进入我报名的直播列表 </span>
                  </div></br>
             <%-- <div class="active_A name-btn" onclick="jumpurl('${path}/wx/netWork/netWork.do');">返回人脉圈</div></br> --%>
             <div class="active_A name-btn" onclick="jumpurl('${path}/library/m/live/myfootPrint_live.do');">查看</div>
         </div>
    </body>
    <script type="text/javascript">
            $(document).ready(function() {
              var deviceWidth = document.documentElement.clientWidth;
              document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
            })
            onload=function(){ 
            	//window.parent.main.document.location.reload();
		        setInterval(go, 1000);  
		    };  
		    var x=3; //利用了全局变量来执行  
		    function go(){  
		    	x--;  
		        if(x>0){  
		        	document.getElementById("sp").innerHTML=x+"秒后进入我报名的直播列表 ";  //每次设置的x的值都不一样了。  
		        }else{  
		        	self.location.href="${path}/library/m/live/myfootPrint_live.do"; 
		        }  
		    }  
		    
		    function jumpurl(url){
		    	self.location.href=url;
		    }
    </script>
</html>