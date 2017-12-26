<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">最近访客</title> 
        <link rel="stylesheet" href="${path}/resource//css/latestVistor.css" />
        <style>
        
        	.imggeeet{
        		margin-top:.16rem;
        	}
        	.positionCom{
        		margin-left:0.2rem;
        	    width: 40%;
            overflow: hidden;
            white-space: nowrap;
           text-overflow: ellipsis;
        	}
        
        </style>

    </head>
    
  
    <body>
        <div class="wrapper">

             <div class="content" id="lasetVisitor">

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
$(function(){
	showInfo();
	latestVistorsReaded();
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

var latestVistorsReaded = function(){
	var url = "${path}/m/message/latestVistorsReaded.do";
	zdy_ajax({
		url: url,
		data:{},
	    showLoading:false,
		success: function(dataobj,res){
			var data = dataobj;
			if(res.code == 0){
				
			};
		},
	    error:function(e){
		   //alert(e);
	    }
 }); 
	
}



var showInfo = function(){
	if(!end){
		var url = "${path}/m/message/selectLatestVistors.do";	
		zdy_ajax({
			url: url,
			data:{
				pageNum:page,
				pageSize:15
			},
		    showLoading:false,
			success: function(dataobj,res){
				var data = dataobj.latestVistorList;
				if(res.code == 0){
					if(page==1 && !res.dataobj.length){
					   $(".page_loading").hide();
					   $(".page_nodata").show();
				    }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
						$(".page_loading").hide();
						$(".page_nodata").show();
					    end=true;
				    };
					console.log(data);
					var str = "";
					$.each(data, function(i, n){
						if(n.job_name){
							 job = '/'+n.job_name
						}else{
							job=''
						}
						if(n.industry){
							industry = '/'+n.industry
						}else{
							industry=''
						}
						if(n.nickname){
							nickname = n.nickname
						}else{
							nickname=''
						}
						 str += '<div class="content-item bottom-border active_A" >'+
				                  '  <div class="con-l " style="background:none;padding-left:0.2rem;padding-right:0.2rem;">'+
				                  '             <div style="float:left" class="imggeeet">'+
				                  '                <img src=\"'+n.head_portrait +'\" follow_user="'+n.vistor_user_id+'"/>'+
				                  '             </div>'+
				                  '              <div class="positionCom" style="float:left">'+nickname+job+industry+
				                  '             </div>'+
				                  '             <div class="textDate">'+n.updateTime+'</div>'+
				                  '              <div style="clear:both"></div>'+
				                  ' </div>'+
				                  '</div>'
					});	
					$("#lasetVisitor").append(str);
					
					$('.content-item').on('click',function(){
						var follow_user = $(this).find('img').attr("follow_user")	
						window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+follow_user;
					})
					page++;
				};	
			},
		    error:function(e){
			   //alert(e);
		    }
	 }); 
	
	}
	
}

</script>
</html>