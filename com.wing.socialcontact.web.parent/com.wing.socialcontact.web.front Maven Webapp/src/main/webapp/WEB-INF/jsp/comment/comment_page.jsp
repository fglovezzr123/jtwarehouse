<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="资讯">
		<title>information.html</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/informationDetail.css" />
		<script src="${path}/resource/js/libs/jquery-2.2.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
	
	<!-- 
	   <jsp:include page="../commons/include_comment.jsp" >
			<jsp:param name="id" value="" />
			<jsp:param name="cmeType" value="" />
		</jsp:include>
	-->
		<div id="comment_dec">
			
		 	<div class="infor">
				<div class="infor-l"><img alt="" src="${path}/resource/img/images/book.png"></div>
				<div class="infor-r">
					<div class="rt">
						<h2>刘子健</h2>
						<div class="tb">
							<span class="active_A">
								
								<b>21</b>
							</span>
														
							<span class="active_A"><b>32</b></span>
						</div>
					</div>
					<div class="rm">
						<span class="i-time">10小时前</span>
						<span class="i-pay">CEO/北京环保科技有限公司</span>
					</div>
					<div class="rb">营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟</div>
					<div class="rt">
						<h2>刘子健</h2>
					</div>
					<div class="rb">营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟营销联盟</div>
				</div>
			</div>
			 
			<br>
			<button class="btn active_A">评论资讯</button>
		</div>
		<script src="${path}/resource/js/libs/public.js" type="text/javascript" charset="utf-8"></script>
		<script src="${path}/resource/js/libs/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${path}/resource/js/layer-v2.1/layer/layer.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		var _id="${id}";
		var _cmeType="${cmeType}";
		$(document).ready(function() {
			zdy_ajax({
				url: "${path}/m/comment/selComments.do",
				data:{
					fkId:"${fkId}",
					cmeType:"${cmeType}",
					lastUrl:"${lastUrl}"
				},
				success: function(data,res){
					if(res.code == 0){
						var s='';
						$.each(res.dataobj, function(i, n){
							s += '<div class="infor">';
							s += '<div class="infor-l"></div>';
							s += '<div class="infor-r">';
							s += '	<div class="rt">';
							s += '		<h2>'+n.user.tureName+'</h2>';
							s += '		<div class="tb">';
							s += '			<span class="active_A" onclick="comment_sub(\''+n.id+'\');">';
							s += '				<b >'+n.subcount+'</b>';
							s += '			</span>';
							s += '			<span class="active_A" onclick="thumbup(this)"><b>'+n.count+'</b></span>';
							s += '		</div>';
							s += '	</div>';
							s += '	<div class="rm">';
							s += '		<span class="i-time">10小时前</span>';
							s += '		<span class="i-pay">CEO/北京环保科技有限公司</span>';
							s += '	</div>';
							s += '	<div class="rb">'+n.cmeDesc+'</div>';
							s += '</div>';
							s += '</div>';
						
						});
						s += '<br>';
						s += '<button class="btn active_A" onclick="comment_add();">评论资讯</button>';
						
						
						$("#comment_dec").append(s);
						
					}
				},
			    error:function(e){
				   //alert(e);
			    }
			}); 
			
			
		});
		
		 var comment_sub=function(_id){
		    	layer.open({
			        type: 2,
			        //skin: 'layui-layer-lan',
			        title: '回复评论',
			        fix: true,
			        shadeClose: true,
			        maxmin: false,
			        area: ['100%', '100%'],
			        content: '${path}/m/comment/selCommentByPid.do?parentId='+_id,
			        shift: 2,
			        scrollbar: false,
			        success: function(layero, index){
			        	isonline();
			        	show_zzc(1);
			        	
			        },
			        end: function(){
			        	hide_zzc(1);
			        },
			        cancel: function(cancel){
			        }
			    });
		    }
		 
		 var comment_add=function(){
		    	layer.open({
			        type: 2,
			        //skin: 'layui-layer-lan',
			        title: '评论',
			        fix: true,
			        shadeClose: true,
			        maxmin: false,
			        area: ['100%', '100%'],
			        content: '${path}/m/comment/comment_add.do?fkId='+_id+'&cmeType='+_cmeType,
			        shift: 2,
			        scrollbar: false,
			        success: function(layero, index){
			        	isonline();
			        	show_zzc(1);
			        },
			        end: function(){
			        	hide_zzc(1);
			        },
			        cancel: function(cancel){
			        }
			    });
		    }
			
		    var show_zzc=function(t){
				$("body").bind("touchmove",function(event){
					event.preventDefault();
				});
				if(t && t == 1){
					scrollTop=$(document).scrollTop();
					$(document).scrollTop(0);
					$(window).bind("scroll",function(){
						$(document).scrollTop(0);
					});
				}
				
			}
			
			var hide_zzc=function(t){
				$("body").unbind("touchmove");
				if(t && t == 1){
					$(window).unbind("scroll");
					$(document).scrollTop(scrollTop);
				}
				
			}
			
			var reload=function(){
				self.location.href=self.location.href;
			}
		    
			var thumbup=function(obj){
				var a=$(obj).find("b").html();
			/*	$.ajax({
					url: "${path}/m/comment/Thumbup.do",
					type: 'post',
					dataType:"json",
					data:{
						id:"${fkId}",
					},
					success: function(res){
						if(res.code == 0){
							if(res.dataobj){
								$(obj).find("b").html(a*1+1);
							}
						}
					},
				    error:function(e){
					   //alert(e);
				    }
				}); */
				
				$(obj).find("b").html(a*1+1);
			}
		
		
		</script>
	</body>

</html>