<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" />
		<title>商友搜索</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/addFriend.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/search-meeting.css?v=${sversion}" />
		<style type="text/css">
			.ts{
			  width:100%;
			  height:.50rem;
			  line-height:.50rem;
			  background:#f2f3f4;
			  font-size:.30rem;
			  text-align:center;
			  display:none;
			}
			.sear{
			font-size:.30rem;
			}
			.meeting-search{
				font-size:0;
			
			}
			.search-list{
				margin-top:.1rem;
			}
			 .c{
			 width:100%;
			 height:.5rem;
			 text-align:center;
			 line-height:.5rem;
			 font-size:.36rem;
			 display:none;
			 background:#f2f3f4;
			}
		</style>
		
	</head>
	<body style="background: #f2f3f4; width: 100%;height: 100%;">
	          <div class="meeting-search">
				<div>
					<input type="text" placeholder="商友搜索" id="keywords" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"  oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"/>
					<img src="${path}/resource/img/icons/lens.png" />
				</div>
				<div class="sear">
					<span id="search" class="active_A">搜索</span>&nbsp;&nbsp;
					<span id="cancel"class="active_A">取消</span>
				</div>
				<br class="clear" />
			</div>
			<div class="hot-search">历史搜索</div>
            <div class="search-history"></div>
            <div class="active_A modify-btn" onclick="clearHis()">清空历史搜索</div>
		<div class="search-list">
		    <div class="group">
		     <div class="group1"></div>
		     <div class="c">查看更多</div>
		    </div>
		     <div class="friend">
		     <div class="friend1"></div>
		     <div class="c">查看更多</div>
		    </div>
		</div>
		<div class="ts">暂无更多信息</div>
	</body>
	<script type="text/javascript">
	$("#keywords").val('');
	$("#cancel").click(function() {
		history.back(-1);
	});
	//历史搜索记录
	    $("#search").on("click",function(){
		        var value = $("#keywords").val();
		        if(!$("#keywords").val()){
		        	layer.msg("请输入关键词")
		        	 $('.search-list').empty();
		        	 return;
		        } 
		        var kk = JSON.parse(localStorage.getItem("newFriend"))||[];
		        var arr = [];
		        arr.push(value);
		        console.log(arr)
		        var arr=arr.unique3()
		        for(var i=0;i<kk.length&&i<7;i++){
		      	  if(keywords!=kk[i]){
		  	    	  arr.push(kk[i]);
		      	  }
		        }
		        localStorage.setItem("newFriend", JSON.stringify(arr));     
		        searchRes(value)
			
	});
    if (window.localStorage) {
        var kk = JSON.parse(localStorage.getItem("newFriend"))
        console.log(kk) 
      if(kk){
    	  var kk=kk.unique3()
    	  for(var i=0;i<kk.length;i++){
      	      $(".search-history").append("<div class=\"active_A\" onclick=\"setKeywords(this)\">"+kk[i]+"</div>");
            }
      }  
     }
    function clearHis(){
   	 $(".search-history").empty();
   	 localStorage.removeItem("newFriend");
   }
    function setKeywords(obj){
   	$("#keywords").val($(obj).text().trim());
   	if($("#keywords").val()!=""){
   		searchRes($("#keywords").val());
   	}
   } 
	function searchRes(value){
		$('.hot-search').hide()
		$('.search-history').hide()
		$('.modify-btn').hide()
		zdy_ajax({
			url: _path+"/im/m/selMyFriendAndGroupList.do",
		    showLoading:false,
		    data:{
		    	 nickname:value,
		    }, 
			success: function(data,res){
				 $('.group1').empty()
			     $('.friend1').empty()
				if(data.groupInfoList.length == 0){
					$('.ts').show()
				} else{
					$('.ts').hide()
					if(data.groupInfoList.length>3){
						$('.group1').height('3rem')
						$('.group1').css({'overflow':'hidden'})
						 $('.group .c').show()
					} 
					$.each(data.groupInfoList,function(index){
						 var str = '<div class="info active_A" id="'+data.groupInfoList[index].group_id+'">'+
							          '<div class="info-l">'+
						                '<img src="'+data.groupInfoList[index].head_portrait+'" />'+
						                '<div class="person">'+
							             ' <p>'+
								            '<span class="p-name"></span>'+
								            '<span class="profession">'+data.groupInfoList[index].group_name+'</span>'+
							              '</p>'+
							             '<p class="company" style="display:none"></p>'+
						                '</div>'+
					                  '</div>'+
					                  
				                    '</div> ';
						  $('.group1').append(str);
					});
					clickQid()
				}
				
				if(data.friendList.length == 0){
					$('.ts').show()
				} else{
					$('.ts').hide()
					if(data.friendList.length>3){
						$('.friend1').height('3rem')
						$('.friend1').css({'overflow':'hidden'})
						 $('.friend .c').show()
					}else{
						$('.friend1').height('auto')
					}
					$.each(data.friendList,function(index){
						if(data.friendList[index].friend_memo){
							friend_memo = data.friendList[index].friend_memo
						}else{
							friend_memo=''
						}
						if(data.friendList[index].job_name){
							job_name = '/'+data.friendList[index].job_name
						}else{
							job_name=''
						}
						if(data.friendList[index].com_name){
							com_name = '/'+data.friendList[index].com_name
						}else{
							com_name=''
						}
						 var str = '<div class="info active_A" id="'+data.friendList[index].friend_user+'" user_id="'+data.friendList[index].user_id+'" follow_user="'+data.friendList[index].friend_user+'">'+
							          '<div class="info-l">'+
						                '<img src="'+data.friendList[index].head_portrait+'" />'+
						                '<div class="person">'+
							             ' <p>'+
								            '<span class="p-name">'+friend_memo+'</span>'+
								            '<span class="profession">'+job_name+com_name+'</span>'+
							              '</p>'+
							             '<p class="company" style="display:none">'+data.friendList[index].industry_name+'</p>'+
						                '</div>'+
					                  '</div>'+
					                  
				                    '</div> ';
						  $('.friend1').append(str);
					});
					clickFriend()
				} 
			},
		    error:function(e){
			   //alert(e);
		    }
		});
	}
	$('.group .c').on("click",function(){
		$('.group1').height('auto')
		$('.group .c').hide()
	})
	$('.friend .c').on("click",function(){
		$('.friend1').height('auto')
		$('.friend .c').hide()
	})
	//好友点击
	function clickFriend(){
		$('.friend .info').on('click',function(){
			var id = $(this).attr('id');
			window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+id;
		});
	}
    function clickQid(){
    	$('.group .info').on('click',function(){
			var id = $(this).attr('id');
			groupInfo(id)
		});
    }
    //获取群信息
	   function groupInfo(qid){
						zdy_ajax({
	                         url : _path + "/im/m/selGroupInfoById.do",
							showLoading : false,
							data : {
								groupId : qid
							},
							success : function(data, res) {
								var objId = new Object();
								objId.nickName=data.groupInfo.groupName
								var objStr = JSON.stringify(objId);
								sessionStorage.setItem('user_info',objStr); 
								var arrUser = [];
								$.each(data.users,function(k){
									var user_id = data.users[k].user_id;
									var head_portrait = data.users[k].head_portrait;
								 	var objUsers = {user_id:user_id,head_portrait:head_portrait}
									arrUser.push(objUsers)
								})
								setSessionStorageValue("usersInfo",arrUser)
								setTimeout(function(){
								window.location.href=_path+"/wx/businessFriend/talkAboutQl.do?qid="+qid;
						             },1500)
							
							},
							error : function(e) {
								//alert(e);
							}
                     });
	 }
   </script>
	
</html>