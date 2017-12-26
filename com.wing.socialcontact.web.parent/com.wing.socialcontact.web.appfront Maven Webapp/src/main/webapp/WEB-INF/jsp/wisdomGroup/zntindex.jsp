<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title id="title">智囊团</title>
		<link rel="stylesheet" href="${path}/resource/css/wisdom-group.css" />
		<link rel="stylesheet" href="${path}/resource/css/boss-articles.css" />
		<style>
			.banner_ul img {
				width: 100%;
				height: 100%;
			}
		</style>
	</head>
	<body>
		<div class="wrapper">

			<jsp:include page="../commons/include_banner.jsp" >
						<jsp:param name="bannerid" value="2142b885a7654fc391167350a2f5b125" />
		   </jsp:include>
			<div class="menu">
				<a class="active_A" href="${path}/wx/wisdomGroup/zsmsindex.do"> 知识秘书</a>
				<a class="active_A" href="${path}/library/m/live/listPage.do?type=2" style="display:none;" id="yzttxatag" > 与总统谈心</a>
				<a class="active_A" href="${path}/m/news/htmlDetailPage.do?id=6441e97b64c84c21b8499f3681f759c3"> 咨询联盟</a>
				<a class="active_A" href="${path}/m/reward/indexPage.do">诸葛解惑</a> 
			</div>
			<div class="zhuge-Top"  id="toplist">
			     <!-- <div class="zhuge-head">
					<ul>
						<li>诸葛榜</li>
						<li class="active_A">更多<span>></span></li>
						<br class="clear" />
					</ul>
				</div>
				<div id="sameFriends">
					<div class="friendBox active_A">
						<ul>
							<li></li>
							<li>王网</li>
							<li>北京网购CEO</li>
						</ul>
					</div>
					<div class="friendBox active_A">
						<ul>
							<li></li>
							<li>王网</li>
							<li>北京网购CEO</li>
						</ul>
					</div>

					<br style="clear:both" />
				</div>
				<div class="zhuge-head">
					<ul>
						<li>最热悬赏</li>
						<li class="active_A">更多<span>></span></li>
						<br class="clear" />
					</ul>
				</div>

				<div class="list-box">
					<div>
						<p>1. 企业应如何管理？</p>
						<p class="tip">悬赏<span>300</span>J币</p>
					</div>
					<div>1分钟前</div>
					<br class="clear" />
				</div>

				<div class="list-box">
					<div>
						<p>2. 企业应如何管理？</p>
						<p class="tip">悬赏<span>300</span>J币</p>
					</div>
					<div>1分钟前</div>
					<br class="clear" />
				</div>

				<div class="list-box">
					<div>
						<p>3. 公司上市是好是坏？</p>
						<p class="tip">悬赏<span>300</span>J币</p>
					</div>
					<div>2分钟前</div>
					<br class="clear" />
				</div>  -->
				<div class="zhuge-head" id="yzttxid"  style="display:none;">
					<ul>
						<li >与总统谈心</li>
						<li class="active_A" id="yzztx"  onclick="jumpurl('${path}/library/m/live/listPage.do?type=2')">更多<span>></span></li>
						<br class="clear" />
					</ul>
				</div>
				<div class="book-box"  id ="book-box1" style="display:none;">
				</div>
				<div class="zhuge-head">
					<ul>
						<li >优文推读</li>
						<li class="active_A" id="newArticle"  onclick="jumpurl('${path}/wx/wisdomGroup/zsmsindex.do')">更多<span>></span></li>
						<br class="clear" />
					</ul>
				</div>
				<div class="book-box" id ="book-box">
				</div>
			</div>
		</div>
		<script type="text/javascript">
		 var imgpathss=_oss_url;
		 $(function(){
			 if(screenflag){
					$("#yzttxid").hide();
					$("#yzttxatag").hide();
				}else{
					$("#yzttxid").show();
					$("#yzttxatag").show();
				} 
		 });
		 
		 //最新文库
		 zdy_ajax({
			   url:_path+"/library/m/live/list.do",
			   data:{
				    type:2,
	 			    page:1,
	 				size:3
			   },
			   showLoading:false,
			   success:function(data,abc){
				   console.log(data)
			    var str='';
			    var showlength='';
			    if(data.length>3){
			    	    showlength=3;
			    }else{
			    	 showlength=data.length;
			    }
			 
			  for(var i=0;i<showlength;i++){
			    	str += '<div class="book-item book-item2  active_A" id="'+data[i].id+'">' +
								'<div>'+'<img  src="'+imgReplaceStyle(data[i].imgpath,'YS640320')+'" />'+'</div>'+
								'<div>'+
									'<h1>'+data[i].title+'</h1>'+
									'<p>'+
										/* data[i].content+ */
									'</p>'+
								'</div>'+
								'<br class="clear" />'+
							'</div>';
			  }
			         $('#book-box1').append(str);
				     $('.book-item2').bind('click',function(){
				    	 document.location.href="${path}/library/m/live/detail.do?id="+jQuery(this).attr('id');
				     });
				     $('#newArticle').bind('click',function(){
				    	 //window.location.href="${path}/wx/wisdomGroup/zsmsindex.do";
				    	 //window.location.href="${path}/library/m/all-classes.do";
				     });
			   },
			   error:function(data){
			   }
			})
		 //优文推读
		 zdy_ajax({
			   url:_path+"/library/m/list.do",
			   data:{
			   },
			   showLoading:false,
			   success:function(data,abc){
			    var str='';
			    var showlength='';
			    if(data.length>3){
			    	    showlength=3;
			    }else{
			    	 showlength=data.length;
			    }
			 
			  for(var i=0;i<showlength;i++){
			    	str += '<div class="book-item book-item1  active_A" id="'+data[i].id+'">' +
								'<div>'+'<img  src="'+imgpathss+data[i].imgpath+'" />'+'</div>'+
								'<div>'+
									'<h1>'+data[i].title+'</h1>'+
									'<p>'+
										/* data[i].content+ */
									'</p>'+
								'</div>'+
								'<br class="clear" />'+
							'</div>';
			  }
			         $('#book-box').append(str);
				     $('.book-item1').bind('click',function(){
				    	 document.location.href="${path}/library/m/librarydetail.do?type=1&id="+jQuery(this).attr('id');
				     });
				     $('#newArticle').bind('click',function(){
				    	 //window.location.href="${path}/wx/wisdomGroup/zsmsindex.do";
				    	 //window.location.href="${path}/library/m/all-classes.do";
				     });
			   },
			   error:function(data){
			   }
			})
			
			//查询前两个智囊团一级分类
				zdy_ajax({
					   url:_path+"/library/m/onelevelclass.do",
					   data:{
						   position:'1'
					   },
					   showLoading:false,
					   success:function(data,abc){
						   var a =1;
						   var j =1;
						   var str="";
						   $.each(data, function(i, n){
		  						if(a<3){
		  							str+='<div class="headline"><a href="javascript:void(0)">'+n.name+'</a>'+
	  							 	  '<a href="${path}/library/m/all-classes.do?id='+n.id+'&level=1">更多 <span>></span></a>'+
	  							 	  '<br class="clear" /></div><div class="content" id="list'+
	  							 	  i+'"><br class="clear"/></div>';
		  						}
		  						a++;
							});
						   $("#toplist").before(str);
						   $.each(data, function(i, n){
		  						if(j<3){
	  							 	  uploadarticle(i,n.id);
		  						}
		  						j++;
							});
					   },
					   error:function(data){
						   
					   }
					});
		 
		 function uploadarticle(a,id){
			 zdy_ajax({
				   url:_path+"/library/m/selbyonelevelid.do",
				   data:{
					   'classid':id
				   },
				   showLoading:false,
				   success:function(data,abc){
					   var str='';
					   var dataLen=2;
					   //限制显示最多2个
					   for(var i=0;i<dataLen;i++){
						   if(i<2){
						   str += '<a href="${path}/library/m/librarydetail.do?type=1&id='+data[i].id+'">'+
							              '<img src="'+_oss_url+data[i].imgpath+'" />'+
                                        '<p>'+data[i].title+'</p>'+
						          '</a>';
						   }
					   }
					 $("#list"+a).prepend(str);
				   },
				   error:function(data){
				   }
				});
		 }
		 
		 
		 function  jumpurl(url){
			 window.location.href= url; 
		 };
			
		</script>
		
	</body>

</html>