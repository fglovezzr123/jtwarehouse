<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="添加好友" />
		<title>添加好友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/addFriend.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/search-meeting.css?v=${sversion}" />
		<style type="text/css">
			.sear{
			font-size:.30rem;
			}
			.meeting-search{
				font-size:0;
			}
			.search-list{
				margin-top:.1rem;
			}
		</style>
	</head>
	<body style="background: #f2f3f4; width: 100%;height: 100%;">
	 <div class="meeting-search">
				<div>
					<input type="text" placeholder="搜索好友" id="keywords" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
					<img src="${path}/resource/img/icons/lens.png" />
				</div>
				<div class="sear">
					<span id="search" class="active_A">搜索</span>&nbsp;&nbsp;
					<span id="cancel">取消</span>
				</div>
				<br class="clear" />
			</div>
			<div class="hot-search">历史搜索</div>
            <div class="search-history"></div>
            <div class="active_A modify-btn" onclick="clearHis()">清空历史搜索</div>
		<div class="search-list">
		</div>
	</body>
	<script type="text/javascript">
    $("#keywords").val('');
	$("#cancel").click(function() {
		history.back(-1);
	});
	 $("#search").on("click",function(){
		    var value = $("#keywords").val();
	        if(!$("#keywords").val()){
	        	layer.msg("请输入关键词")
	        	 $('.search-list').empty();
	        	 return;
	        } 
	        var kk = JSON.parse(localStorage.getItem("Friend"))||[];
	        var arr = [];
	        arr.push(value);
	        for(var i=0;i<kk.length&&i<7;i++){
	      	  if(keywords!=kk[i]){
	  	    	  arr.push(kk[i]);
	      	  }
	        }
	        localStorage.setItem("Friend", JSON.stringify(arr));     
	        searRes(value)
	}); 
	 if (window.localStorage) {
	        var kk = JSON.parse(localStorage.getItem("Friend"))
	        console.log(kk)
	      if(kk){
	    	  for(var i=0;i<kk.length;i++){
	      	      $(".search-history").append("<div class=\"active_A\" onclick=\"setKeywords(this)\">"+kk[i]+"</div>");
	            }
	      }  
	     } 
	    function clearHis(){
	   	 $(".search-history").empty();
	   	 localStorage.removeItem("Friend");
	   }
	    function setKeywords(obj){
	   	$("#keywords").val($(obj).text().trim());
	   	if($("#keywords").val()!=""){
	   		searRes($("#keywords").val());
	   	} 
	   	}
	 function searRes(value){
		   $('.hot-search').hide()
			$('.search-history').hide()
			$('.modify-btn').hide()
		 zdy_ajax({
				url: _path+"/im/m/selMyNoFriendList.do",
			    showLoading:false,
			    data:{
			    	 pageNum:'1',
			    	 pageSize:20,
			    	 trueName:value
			    }, 
				success: function(data,res){
					console.log(data)
					if(res.dataobj.length == 0){
						$(".search-list").empty();
						$(".search-list").html('<div style="background:#f0f0f0;font-size:0.3rem;color:#808080;text-align:center">无相关数据</div>');
					}else{
						$(".search-list").empty();
						$.each(data,function(index){
							  var job='';
							  var com='';
							  if(data[index].job_name !=undefined){
								  job=data[index].job_name+'/';
							  };
							  if(data[index].com_name !=undefined){
								  com=data[index].com_name;
							  };
							  var str = '<div class="info" id="'+data[index].id+'" user_id="'+data[index].user_id+'" follow_user="'+data[index].friend_user+'">'+
								          '<div class="info-l">'+
							                '<img src="'+data[index].head_portrait+'" />'+
							                '<div class="person">'+
								             ' <p>'+
									            '<span class="p-name">'+data[index].nickname+'</span>'+
									            '<span class="profession">' +job+''+com+'</span>'+
								              '</p>'+
								             '<p class="company">'+data[index].industry_name+'</p>'+
							                '</div>'+
						                  '</div>'+
						                  '<span class="accept active_A onC" status="'+data[index].status+'">添加</span>'+
					                    '</div> ';
							  $('.search-list').append(str);
						 });
						 var les = $("#keywords").val().length;
						 if(les == 0){
							 $(".search-list").empty();
						 }
						$('img').on('click',function(){
							var id = $(this).parents('.info').attr('id');
							window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+id;
						});
	                    $('.onC').on('click',function(){
	                        var id = $(this).parents('.info').attr('id');
							window.location.href = _path+ "/wx/businessFriend/applyToAddGroup.do?follow_user="+ id;
						});
					}	
				},
			    error:function(e){
				   //alert(e);
			    }
			});
	 }
   </script>
	
</html>