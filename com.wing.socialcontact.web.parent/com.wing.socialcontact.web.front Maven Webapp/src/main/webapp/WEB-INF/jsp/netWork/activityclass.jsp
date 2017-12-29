 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html lang="en">

	<head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">
        <link rel="stylesheet" href="${path}/resource/css/activitylistss.css?v=${sversion}" />
        <title id="title">全部分类</title>

        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}" />
		
        <link rel="stylesheet" href="${path}/resource/css/activitylistss.css?v=${sversion}" />

    </head>
	<body>
		<div class="wrapper">
             <div class="activeClassList" id="activeClassList" >
                
             </div>

        </div>
    </body>
        <script>

        var name ='';
        $(function(){
        	if(${classid}=="1"){
        		name="以玩会友";
        	}else{
        		name="以书会友";
        	}
        });
        
        zdy_ajax({
			   url:'${path}/m/activity/activityTags.do',
			   showLoading:true,
			   data:{
				  "classid":${classid},
				  
			   },
			   success:function(data,res){
				  var str='';
						$.each(data, function(i, n){
							console.log(n)
							str+='<div style="margin-top:0.1rem;;margin-left:0.1rem;background-image:url('+_oss_url+n.imagePath+'); background-size:100%100%; ">'
							+'<a href="${path}/m/activity/activitylistPage.do?id='+n.id+'">'+n.name+'</a></div>';
						});
						$('#activeClassList').append(str);
				  },
			   error:function(data){
				   
			   }
				
			});
        </script>
	
	</body>

</html>