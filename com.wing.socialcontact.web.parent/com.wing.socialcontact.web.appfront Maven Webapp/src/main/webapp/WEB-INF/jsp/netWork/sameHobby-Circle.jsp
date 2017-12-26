<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">同好圈子</title>
		<link rel="stylesheet" href="${path}/resource/css/tonghang.css?v=${sversion}" /> 
		<style>
			.banner_ul img {
				width: 100%;
				height: 100%;
			}
			#banner1 .banner_ul img{
				  width:92%;
			}
			.addinggg{
			    line-height:0.56rem;
			    width:1.5rem;
			}
			.belongItem>div h1{
			   margin-top:0.3rem;
			}
			body{
			 height:auto;
			}
			 .search-box{
			box-sizing:border-box;
		     width:100%;
		     background:#f3f4f6;
		     height:0.78rem;
		     line-height:0.78rem;
		     text-align:center;
        }
	      #searchBTN {
				width: 16%;
				font-size: 0.3rem;
				border-radius: 0.1rem;
				background: #0f88eb;
				color: white;
				display: inline-block;
				height: 0.56rem;
				line-height: 0.56rem;
				text-align: center;
			   }
			  .search-box #search{
			       width:80%;
			       margin:0;
			  }
			  .search-box{
			    padding:0;
			    padding-left:0.3rem;
			    padding-right:0.3rem;
			    display: flex;
			    justify-content: space-between;
			    align-items: center;
			  }		
	    
		</style> 
	</head>

	<body>
		<div id="header">
			<div class="search-box">
				<input type="text" name="search" id="search"  placeholder="搜索" style="background-position-x: 2.4rem;"/>
				<span id="searchBTN">搜索</span>
			</div>
		    <div class="belongTo">
		    	<span>爱好筛选</span>
		    	<select id="occupation">	  
		    	</select>
		    	<br class="clear"/>
		    </div> 
		</div>
		<div id="wrapper">
			<div id="scroller">
				 <div id="sameIndustry"></div>
		         <div id="showSearResult"  style="width:100%;text-align:center;display:none;position:fixed;top:3rem;left:0"></div>	
			</div>
		</div>
		<script>
		var u = navigator.userAgent;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		    $(document).ready(function(){
		    	var curPageNum=1;
		    	var industryId='';
		    	var keyword='';
		    	var issearch=false;
		    	getIndustry();	
		    	function getData(){		
		    		 zdy_ajax({
		  				   url:_path+"/im/m/selIdenticalHobbyWorld.do",
		  				   showLoading:false,
		  				   data:{
		  					 pageNum:curPageNum,
		  					 pageSize:20,
		  					 hobby:industryId,
		  					 groupName:keyword
		  				   },
		  				   success:function(data,bc){
		  					   console.log(data)
		  					   if(!data.length && issearch){
		  						  $('#showSearResult').css('display','block');
		  						 $('#showSearResult').text('暂无   "'+$('#search').val()+' "相关数据');
		  					  }else{
		  						 $('#showSearResult').css('display','none');
		  					  };
		  					  if(!data.length && !issearch){
		  						  layer.msg('暂无更多数据');
		  					  }
		  					   
		  					  var str='';
		  					 for(var i=0;i<data.length;i++){
		  						 if(!data[i].head_portrait){
				  							str += '<div class="belongItem">'+
					  				    	'<div>'+
					  			    		'<div class="iconsssss active_A" groupId="'+data[i].group_id+'" style="background:url(${path}/resource/img/images/qun.png) no-repeat center;background-size:0.7rem 0.7rem;">'+'</div>'+
					  			    	'</div>'+
					  			    	'<div>'+
					  			    		'<h1>'+data[i].group_name+'</h1>'+
					  			    		
					  			    		'<button class="addinggg" id="'+data[i].group_id+'" class="addinggg active_A"  >申请加入</button>'+
					  			    	'</div>'+
					  			    	'<br class="clear"/>'+
					  			  '</div>';
		  						 }else{
		  							
		  							str += '<div class="belongItem">'+
							  				    	'<div>'+
							  			    		'<div class="iconsssss active_A" groupId="'+data[i].group_id+'" style="background:url('+data[i].head_portrait+') no-repeat center;background-size:0.7rem 0.7rem;">'+'</div>'+
							  			    	'</div>'+
							  			    	'<div>'+
							  			    		'<h1>'+data[i].group_name+'</h1>'+
							  			    		
							  			    		'<button class="addinggg" id="'+data[i].group_id+'" class="addinggg active_A"  >申请加入</button>'+
							  			    	'</div>'+
							  			    	'<br class="clear"/>'+
							  			  '</div>';
		  						 }
		  					  }
		  					    $('#sameIndustry').append(str); 
		  					    $('.addinggg').bind('click',function(){
		  					    	window.location.href="${path}/wx/businessFriend/applyToAddGroup.do?groupId="+$(this).attr('id');
		  					    });
		  					  $('.iconsssss').bind('click',function(){
		  					    	window.location.href="${path}/wx/businessFriend/group-detail.do?follow_user="+$(this).attr('groupId');
		  					    });
		  				   }, 
		  				   error:function(data){
		  					   
		  				   }
		  					
		  				});	
		     } ;
		     
		   //获取爱好
		  function getIndustry(){   
		   zdy_ajax({
				   url:_path+"/im/m/selhobby.do",
				   showLoading:false,
				   data:{
					  
				   },
				   success:function(data){
					
					 var str=''
					 for(var i=0;i<data.length;i++){
						 str +='<option value="'+data[i].id+'" >'+data[i].listValue+'</option>';
					 }
					 $('#occupation').append(str);
					 industryId= $('#occupation').val();
					 getData();
					 $('#occupation').bind('change',function(){
						
						 changeIndustry();
					 });
				   },
				   error:function(data){
					   
				   }
					
				});
		   };	   
		 //改变行业
		   function changeIndustry(){
			   keyword='';
			   $('#search').val('');
			  /*  $('#lens').css('display','block'); */
			   $('#search').css({"background-image":"url(${path}/resource/img/icons/len.png)","background-repeat":"no-repeat","background-position-x":"2rem","background-position-y":"center","background-size":"4.7%"});
			   $('#showSearResult').css('display','none');
			   issearch=false;
			   industryId= $('#occupation').val();
			   console.log(industryId);
			   $('#sameIndustry').empty();
			    curPageNum=1;
			    getData(); 
		   }
		 
		   $('#search').on('keyup',function(){
				var valLen = $('#search').val().trim().length
				if(valLen>0){
					 $(this).css({backgroundImage:'url()'})
				}else{
					$(this).css({backgroundImage:'url(${path}/resource/img/icons/len.png)'})
				}
			})
			//搜索 
			 $('#searchBTN').on('click',function(){
					if($('#search').val().trim()==''){
						 layer.msg("请输入关键词");
						 return;
					 }; 
					 curPageNum=1;
					 issearch=true;
					   $('#sameIndustry').html(''); 
					   keyword=$('#search').val();
					   getData(); 
				});
		/* //实时搜索 解决输入中文
    	 $('#search').on({
		     input:function(e){
		    	   curPageNum=1;
				   issearch=true;
				    if($(this).val()==''){
					   issearch=false;
					   $('#search').css({"background-image":"url(${path}/resource/img/icons/len.png)","background-repeat":"no-repeat","background-position-x":"2.4rem","background-position-y":"center","background-size":"4.7%"});
					   $('#showSearResult').css('display','none');
					   $('#showSearResult').text('');
					   $('#sameIndustry').html('');
					   keyword='';
					   getData(); 
				  }else{
					   $('#search').css("background-image","none");
					   $('#sameIndustry').html(''); 
					   keyword=$(this).val();
					   getData(); 
				  }   
              }, 
             compositionstart:function(){
            	 $('#search').unbind('input')
             },
             compositionend:function(){
            	 if(isiOS){
            		 $('#search').on('input',function(){
						   curPageNum=1;
						   issearch=true;
						    if($(this).val()==''){
							   issearch=false;
							   $('#search').css({"background-image":"url(${path}/resource/img/icons/len.png)","background-repeat":"no-repeat","background-position-x":"2.4rem","background-position-y":"center","background-size":"4.7%"});
							   $('#showSearResult').css('display','none');
							   $('#showSearResult').text('');
							   $('#sameIndustry').html('');
							   keyword='';
							   getData(); 
						  }else{
							   $('#search').css("background-image","none");
							   $('#sameIndustry').html(''); 
							   keyword=$(this).val();
							  getData();
						  }   
					  })  
            	 }else{
            		  $('#search').css("background-image","none");
					  $('#sameIndustry').html('');
					  keyword=$(this).val();
					  getData(); 
					  $('#search').on('input',function(){
						  curPageNum=1;
						   issearch=true;
						    if($(this).val()==''){
							   issearch=false;
							   $('#search').css({"background-image":"url(${path}/resource/img/icons/len.png)","background-repeat":"no-repeat","background-position-x":"2.4rem","background-position-y":"center","background-size":"4.7%"});
							   $('#showSearResult').css('display','none');
							   $('#showSearResult').text('');
							   $('#sameIndustry').html('');
							   keyword='';
							   getData(); 
						  }else{
							   $('#search').css("background-image","none");
							   $('#sameIndustry').html(''); 
							   keyword=$(this).val();
							  getData();
						  }   
					  })  
            	 }
            	  		  
             } 
         })     */
			    /* //滚动加载
			     $('#wrapper').scroll(function(){
			    	       $('#search').blur()
				            var scrollTop=$(this).scrollTop();
			    	        if(isiOS){
			    	    	   var scrollHeight = $(this).height();
			    	       }else{
			    	    	   var scrollHeight = $(this).height()+curPageNum;
			    	       }
				           
				            var windowHeight = $('#scroller').height();
				         
				            if (scrollTop+scrollHeight==windowHeight) {
				            	console.log('ok')
				            	curPageNum++;
				            	issearch=false;
				            	getData(); 
				            };
				    }) */
				     $('#wrapper').scroll(function(){
		    	       $('#search').blur()
			            var scrollTop=$(this).scrollTop();
		    	        if(isiOS){
		    	    	   var scrollHeight = $(this).height();
		    	       }else{
		    	    	   var scrollHeight = $(this).height()+curPageNum;
		    	       }
			            var windowHeight = $('#scroller').height();
			           /* alert("scrollTop:"+scrollTop+'---------'+"scrollHeight:"+scrollHeight+"-----------"+"windowHeight:"+windowHeight) */
			            if (scrollTop+scrollHeight==windowHeight) {
			            	console.log('ok')
			            	curPageNum++;
			            	issearch=false;
			            	getData(); 
			            };
			    })
		  })
		</script>
	</body>

</html>