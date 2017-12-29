<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">商友搜索列表</title>
        <link rel="stylesheet" type="text/css" href="../../css/libs/public.css"/>
		  
        <link rel="stylesheet" href="${path}/resource/css/messages.css" />
        <style>
         
           .content{
                margin-top:0.7rem;
           }
           .textDate button{
            width:1.5rem;
            height:0.56rem;
            color:white;
            font-size:0.3rem;
            border-radius:0.1rem;
            background:gray;
            margin-top:-0.2rem;
            line-heihgt:0.56rem; 
           } 
           .textDate .havaAdded{
            background:#1aad19;
           } 
         .textDate  .toAddBF {
             background:white;
             color:#808080;
         }
       
       
         .con-l img{
            margin-top:0.17rem;
         }
          .dww .dw-w-gr .dw-i{
		        color:#292829;
		    }
		 #cityAndIndustry input{
		     width:50%;
		     float:left
		 }
		 #cityAndIndustry div{
		     width:50%;
		     float:left;
		     position:relative;
		      height:0.7rem;
		 }
		 #cityAndIndustry div input{
		    width:100%;
		    height:0.7rem;
		    line-height:0.7rem;
		    text-align:center;
		    background: url(${path}/resource/img/icons/truangles.png) no-repeat right center;
            background-size: 0.15rem;
            background-position-x:95%
		 }
		 #cityAndIndustry div:nth-child(1){
		   box-sizing:border-box;
		   border-right:1px solid #f0f0f0;
		 }
		 #cityAndIndustry{
		   position:fixed;
		   top:0;
		   width:100%;
		   background:white;
		   left:0;
		   border-bottom:1px solid #f0f0f0;
		   z-index:100
		 }
		 .content{
		   background:none;
		   text-align:center;
		 }
		 .loadingbox{
		     background:#f0f0f0
		 }
		 .content .content-item{
		    background:white;
		 }    
        </style>
    </head>
    
  
    <body>
        <div class="wrapper">
            
               
             <!-- <div class="search-box">
				<input type="text" name="search" id="search" placeholder="商友搜索" />
			</div> -->
			<div id="cityAndIndustry" style="display:none;">
			  
			  <div> 
					   <select id="whichcitys">
					            <optgroup label="中国">  
							        <option value="" selected="selected">所在地区</option>     
							    </optgroup>
					   </select>
			  </div> 
			  
			  <div> 
					   <select id="whichindusty"> 
							        <option value="" selected="selected">所属行业</option>     
					   </select>
		      </div>			   
			</div>
          


             <div class="content" id="content">
                   
             </div>
             
             <div class="loadingbox">
			     <div class="page_loading" style="display:none;">加载中…</div>
			     <div class="page_nodata" style="display:none;">暂无更多数据</div>
		     </div>        
        </div>
    <script>
 $(document).ready(function(){
	                 $('#content').text('暂无更多数据');
	                // $('#content').text('请选择查询条件');
	                 var selectedcityId='';
	                 var selectedindustryId='';
	                 var pageNumber=1;
					 getCityByProvince();
					 getAllIndusty();
					 
					 $('#whichcitys').change(function(){
						 
						 $('.page_nodata').css('display','none');
						 $('.page_loading').css('display','none');
						 pageNumber=1;
						 lessThanPageSize=false;
						 if(!$(this).val()){
							 selectedcityId='';
						 }else{
							 selectedcityId=$(this).val();
						 }
						 if(!$('#whichcitys').val() && !$('#whichindusty').val()){
	                    	   return
	                       }
						 $("#content").empty();
						 searcgingBytag();
					 })
					 
					 $('#whichindusty').change(function(){
						
						 $('.page_nodata').css('display','none');
						 $('.page_loading').css('display','none');
						 pageNumber=1;
						 lessThanPageSize=false;
						 if(!$(this).val()){
							 selectedindustryId='';
						 }else{
							 selectedindustryId=$(this).val();
						 }
						 if(!$('#whichcitys').val() && !$('#whichindusty').val()){
	                    	   return
	                       }
						 $("#content").empty();
						 searcgingBytag();
					 })
					 
					 $(window).scroll(function(){	
				               
		                       if(!$('#whichcitys').val() && !$('#whichindusty').val()){
		                    	   return
		                       }
						       var scrollTop=$(this).scrollTop();
						        var scrollHeight = $(document).height();
					            var windowHeight = $(window).height();
					            if (scrollTop+windowHeight==scrollHeight) {
					            	$('.page_nodata').css('display','none');
					            	  $('.page_loading').css('display','block');
					            	  
					            	
					            	if(lessThanPageSize){
					            		setTimeout(function(){
					            			$('.page_loading').css('display','none');
					            			$('.page_nodata').css('display','block');
					            		},1000)
	                                	return;
	                                }
					            	pageNumber++;
					            	searcgingBytag(); 
					            	
					            };
					            
						    }) ;
					
					 var lessThanPageSize=false;			
					 function searcgingBytag(){
								zdy_ajax({
									url: _path+"/im/m/selMyNoFriendList.do",
								    showLoading:false,
								    data:{
								    	 pageNum:pageNumber,
								    	 pageSize:20,
								    	 trueName:'',
								    	 cityId:selectedcityId,
								    	 industryId:selectedindustryId
								    }, 
									success: function(data,res){
										
										if(res.dataobj.length == 0){
										    layer.msg('无更多数据');
											return
											/* $("#content").empty() */;
											//$("#content").html('<div style="background:#f0f0f0;text-align:center">无相关数据</div>');
										}else{
											//$("#content").empty();
											$.each(data,function(index){
												  var job='';
												  var com='';
												  if(data[index].job_name !=undefined){
													  job=data[index].job_name+'/';
												  };
												  if(data[index].com_name !=undefined){
													  com=data[index].com_name;
												  };
												   var str =  '<div class="content-item bottom-border " id="'+data[index].id+'" >'+
													                     '<div class="con-l "  style="background:none;padding-left:0.2rem;padding-right:0.2rem">'+
											                                '<div style="float:left">'+
											                                   '<img class="userIcon" src="'+data[index].head_portrait+'"/>'+
											                                '</div>'+
											                               '<div class="sysMes">'+
											                                       '<ul>'+
											                                                '<li style="display:block;height:50%">'+
											                                                 data[index].nickname+
											                                                '</li>'+
											                                                '<li class="sign-text clear" style="display:block;height:50%;">'+
											                                                job+''+com+
											                                                '</li>'+
											                                        '</ul>' +    
											                                '</div>'+
											                                 '<div class="textDate">' +
											                                      '<button userId="'+data[index].id+'" class="havaAdded active_A">添加好友</button>'+
											                                 '</div>'+
											                                 '<div style="clear:both"></div>'+
											                     '</div>'+
											                      '<div style="clear:both"></div>'+
											                 '</div>';
											                 $('#content').append(str);
											                 $('.havaAdded').bind('click',function(){
											                	 window.location.href = _path+"/wx/businessFriend/applyToAddGroup.do?follow_user="+$(this).attr('userId');
											                 });
											});
											
											$('.userIcon').on('click',function(){
												var id = $(this).parents('.content-item').attr('id');
												window.location.href = _path+"/wx/businessFriend/friendInfo.do?follow_user="+id;
											});
						                   
										}
										if(res.dataobj.length<20){
											lessThanPageSize=true;
											$('.page_nodata').css('display','block');
										}else{
											lessThanPageSize=false;
										};
									},
								    error:function(e){
									 
								    }
								});
							
					    };
	 //行业
	 
	                   $('#whichindusty').mobiscroll().select({  
						    theme: 'android-ics light',  
						    display: 'bottom',  
						    label: 'City',
						    mode:'scroller',
						    group: true,  
						    groupLabel: false,  
						    setText:'确定',
						    cancelText:'取消',
						    //选择表单展现的样式  
						    inputClass:"all_width text-right clear_border"  
						//  fixedWidth: [100, 170]  
						});
	 
	//选择城市
						$('#whichcitys').mobiscroll().select({  
						    theme: 'android-ics light',  
						    display: 'bottom',  
						    label: 'City',  
						    group: true,  
						    groupLabel: true,  
						    //选择表单展现的样式  
						     mode:'scroller',
						    setText:'确定',
							cancelText:'取消',
						    inputClass:"all_width text-right clear_border"  
						//  fixedWidth: [100, 170]  
						});
						function getCityByProvince(){
							
							
							 zdy_ajax({
								   url:_path+"/m/activity/getproandcity.do",
								   showLoading:false,
								   data:{
									
								   },
								   success:function(data,bc){
									  var str='';
									  var str2='';
									  for(var i=0;i<data.length;i++){
										  var str2='';
										  for(var b=0;b<data[i].city.length;b++){
											  str2 += '<option value="'+data[i].city[b].id+'">'+data[i].city[b].disName+'</option>';
										  }
										  str += '<optgroup label="'+data[i].disName+'">'+  
										                                            str2+
									             '</optgroup>';
									  }
									  $('#whichcitys').append(str);
										$('#whichcitys').mobiscroll().select({  
										    theme: 'android-ics light',  
										    display: 'bottom',  
										    label: 'City',
										    mode:'scroller',
										    group: true,  
										    groupLabel: false,  
										    setText:'确定',
										    cancelText:'取消',
										    //选择表单展现的样式  
										    inputClass:"all_width text-right clear_border"  
										//  fixedWidth: [100, 170]  
										});
								   },
								   error:function(data){
									   
								   }
									
								});
							 
						};
						
						function getAllIndusty(){
							 zdy_ajax({
								   url:_path+"/im/m/selAllIndustry.do",
								   showLoading:false,
								   data:{
									
								   },
								   success:function(data,bc){
									  var str2='';
									  //console.log(data);
									   for(var i=0;i<data.length;i++){
											  str2 += '<option value="'+data[i].id+'">'+data[i].listDesc+'</option>'; 
									  }
									  
									 $('#whichindusty').append(str2);
									 
									 $('#whichindusty').mobiscroll().select({  
										    theme: 'android-ics light',  
										    display: 'bottom',  
										    label: 'City',
										    mode:'scroller',
										    group: true,  
										    groupLabel: false,  
										    setText:'确定',
										    cancelText:'取消',
										    //选择表单展现的样式  
										    inputClass:"all_width text-right clear_border"  
										//  fixedWidth: [100, 170]  
										});
								   },
								   error:function(data){
									   
								   }
									
								});
						};
					    
	    
     })   			
    </script>
    </body>
</html>