<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">共同好友</title>
       <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
        
        <link rel="stylesheet" href="${path}/resource/css/latestVistor.css" />

    </head>
    
  
    <body>
        <div class="wrapper" >
            
             <div class="content" id="commfriend">
                
                

             </div>
             <div class="loadingbox">
					<div class="page_loading" style="display:block;">加载中…</div>
					<div class="page_nodata" style="display:none;">暂无更多数据</div>
			</div>
        </div>

    </body>
 <script type="text/javascript">

 var page=1;
 var pageSize=5;
 var end=false;
var aUserId='${userId}';
$(function(){
	showInfo();
	//滚动加载
	$(window).scroll(function(){
	      var scrollTop=$(this).scrollTop();
	       var scrollHeight = $(document).height();
	           var windowHeight = $(this).height();
	           if (scrollTop+windowHeight==scrollHeight) {
		           	curPageNum++;
		           	showInfo(false); 
	           };
	   });
});



var showInfo = function(){
	
	var url = "${path}/im/m/selCommonFriendList.do";
	if(!end){
			zdy_ajax({
				url: url,
				data:{
					aUserId:aUserId,
					pageNum:page,
					pageSize:15
				},
			    showLoading:false,
				success: function(dataobj,res){
					if(page==1 && !res.dataobj.length){
					  // $('#rmhd').html('<div  class="searchInfo">没有数据</div>');
					   $(".page_loading").hide();
					   $(".page_nodata").show();
				    }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
						$(".page_loading").hide();
						$(".page_nodata").show();
					    end=true;
				    };
					if(res.code == 0){
						console.log(dataobj);
						var str = "";
						$.each(dataobj, function(i, n){
							 str += '<div class="content-item bottom-border active_A" >'+
					                  '  <div class="con-l " style="background:none;padding-left:0.2rem;padding-right:0.2rem;">'+
					                  '             <div style="float:left" onclick="friend_info('+n.friend_user+');">'+
					                  '                <img src=\"'+n.head_portrait +'\"/>'+
					                  '             </div>'+
					                  '              <div class="positionCom" style="float:left">'+n.nickname+'/'+n.job_name+'/'+n.industry_name+
					                  '             </div>'+
					                  '             <div class="textDate"></div>'+
					                  '              <div style="clear:both"></div>'+
					                  ' </div>'+
					                  '</div>'
						});	
						$("#commfriend").html(str);
					};
					page++;
					
				},
			    error:function(e){
				   //alert(e);
			    }
		 }); 
	}
}


var friend_info =  function(fid){
	 window.location.href = _path+ "/wx/businessFriend/friendInfo.do?follow_user="+fid;
};

</script>
</html>