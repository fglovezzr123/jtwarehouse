<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="quheye">
		<title>项目联营</title>
		<style type="text/css">
		  /*公共样式部分*/
		   body{
       		width: 100%;
      		background: #F2F3F4;
           }
			/* #banner{
				height: 3rem;
			} */
			.nav{
				width: 100%;
				padding: 0 0.3rem;
				height: 2.06rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
				background: #fff;
				box-sizing:border-box;
			}
			.nav a{
				font-size: .28rem;
				display: flex;
                flex-direction: column;	
                align-items: center;
                flex-grow: 1;	
             	}
             	.nav a img{
             		width: .88rem;
             		height: .88rem;
             		margin-bottom: .20rem;
             	}
           	/*公共样式部分end*/
           	.items{
           		padding-bottom: 0rem;
           		box-sizing:border-box;
           	}
           	.loadingbox{
           		padding-bottom: 0.9rem;
           	}
     		.item{
     			margin-top: .20rem;
     			width: 100%;
     			padding: 0 .3rem;
     			background: #fff;
     			display: flex;
     			box-sizing:border-box;
     			
     		}
     		.item .it-img{
     			width: 2.5rem;
     			padding: 0.15rem 0;
     			position: relative;
     			height: 1.8rem;
     			box-sizing:border-box;
     		}
     		.item .it-img img{
     			width: 100%;
     		}
     		.item .it-img .jpIcon{
     			position: absolute;
     			top: 0;
     			left: 0;
     			width: .30rem;
     			height: .55rem;
     		}
     		
     		.item .it-cont{
     			width: 4.4rem;
     			font-size: .26rem;
     			padding-left: .15rem;
     			box-sizing:border-box;
     		}
     		.item .it-cont h3{
     			font-size: .30rem;
     			line-height: .6rem;
     			font-weight: normal;
     			white-space: nowrap;
			    overflow: hidden;
			    text-overflow: ellipsis;
     		}
     		.item .it-cont .redu{
     			width: 100%;
     			height: .30rem;
     			display: flex;
     			align-items: center;
     			color: #808080;
     			margin-top: .08rem;
     		}
     		.item .it-cont .progress{
     			width: 2.5rem;
     			height: .1rem;
     			background: #ececec;
     			border-radius: .1rem;
     			margin-left: .1rem;
     		}
     		.item .it-cont .progress .progress-bar{
     			height: .1rem;
     			background: #e93131;
     			border-bottom-left-radius: 0.5rem;
     			border-top-left-radius: 0.5rem;
     			
     		}
     		.item .it-cont .bot{
     			display: flex;
     			justify-content: space-between;
     			margin-top: .15rem;
     		}
     		.item .it-cont .bot span{
     			line-height: 2.5;
     		}
     		.item .it-cont .bot span:nth-last-of-type(1){
     			width: 1.30rem;
     			height: .50rem;
     			background: #0F88EB;
     			border-radius: .1rem;
     			font-size: .24rem;
     			color: #fff;
     			text-align: center;
     			line-height: .50rem;
     		}
		</style>
	</head>
	<body>
	    <div class="search-box">
			<div id="search">搜索</div>
		</div> 
		<div class="swiper-container banner" id="banner">
			<jsp:include page="../../commons/include_banner.jsp" >
				<jsp:param name="bannerid" value="a535b07256c54ff9ba26431d9fa59a6c" />
			</jsp:include>
		</div>
		<div class="nav">
			<a href="http://www.tojoycloud.com/introduce.html">
				<img src="${path }/resource/img/icons/msjs.png"/>
				<span>模式介绍</span>
			</a>
			<a href="http://www.tojoycloud.com/case.html">
				<img src="${path }/resource/img/icons/cgal.png"/>
				<span>成功案例</span>
			</a>
			<a href="http://www.tojoycloud.com/process.html">
				<img src="${path }/resource/img/icons/hzlc.png"/>
				<span>合作流程</span>
			</a>
		</div>
		<!--公共区域banner和nav快速导航                       end-->
			<div class="items" id="projectlist">
			</div>
				<div class="loadingbox">
					<div class="page_loading" style="display:block;">加载中…</div>
					<div class="page_nodata" style="display:none;">暂无更多数据</div>
				</div>
		<div class="in-cz" id="recommend">
	                	  项目自荐
	    </div>
		<script type="text/javascript">
				var page = 1;
				var size = 10;
				var end = false;
				 $(function(){
					 
					 uploadlist();
					 if(!end){
						    //滚动加载
								  $(window).scroll(function(){
								       var scrollTop=$(this).scrollTop();
								        var scrollHeight = $(document).height();
								            var windowHeight = $(this).height();
								            if (scrollTop+windowHeight==scrollHeight) {
								            	uploadlist(); 
								            };
								    })
							}
				 });
				 function uploadlist(){
					 if(!end){
						 $(".page_loading").show();
						 zdy_ajax({
						 	showLoading:false,
						 	url:'${path}/m/project/project/list.do',
						 	data:{
						 		size:size,
						 		page:page
						 	},
						 	success: function(dataObj,data){
								var str='';
								if(data.code == 0){
									   $(".page_loading").hide();
								    if(page==1 && !data.dataobj.length){
									   $(".page_nodata").show();
								    }else if(data.dataobj.length==0 || data.dataobj.length<size){
										$(".page_nodata").show();
									    end=true;
								    };
									$.each(data.dataobj, function(i, n){
										var redu = n.cou;
										var gz=n.cou;
										var titles = n.titles;
										var attention = '';
										if (titles.length>15){
											titles=titles.substring(0,14)+'...';
										}
										if(isEmpty(redu)){
											redu = 0;
											gz=0;
										}else if(redu>100){
											redu=100;
										}
				  						str +='<div class="item"><a href="${path}/m/project/detail/index.do?id='+n.id+'"><div class="it-img"><img src="'+imgReplaceStyle(n.coverImg,'YS640384')+'"/></div></a>'+
					  						'<div class="it-cont"><a href="${path}/m/project/detail/index.do?id='+n.id+'"><h3>'+titles+'</h3></a><div class="redu"><span>热度</span><div class="progress">'+
					  						'<div class="progress-bar" redu-id="'+n.id+'" style="width:'+redu+'%;"></div></div></div>'+
					  						'<div class="bot"><span gz-id="'+n.id+'">收藏('+gz+')</span>';
					  						if(isEmpty(n.attId)){
					  							str += '<span onclick="attentionPrj(this)"    style="background: url('+_path+'/resource/img/icons/gz.png) no-repeat center;background-size: .25rem;padding-left: .35rem;background-position-x: 0;"  data-id="'+n.id+'">收藏</span>';
					  						}else {
					  							str += '<span onclick="attentionDelPrj(this)" style="background: url('+_path+'/resource/img/icons/gzsuccess.png) no-repeat center;background-size: .25rem;padding-left: .35rem;background-position-x: 0;"  data-id="'+n.id+'">取消收藏</span>';
					  						}
					  						str += '<span class="detailpro" id="'+n.id+'">立即进入</span></div></div></div>';
									});
								$("#projectlist").append(str);
								addclick();
								page++;
								}
							},
							error:function(e){
							}
						 });
					 }
				 };
				 $(function(){
					 $("#recommend").click(function(){
							window.location.href = "${path}/m/project/recommend/index.do";
					});
					 $("#search").bind('touchstart',function(){
							window.location.href = "${path}/m/project/search.do";
					});
				 });
				 function addclick(){
					 $(".detailpro").on('click', function(){ 
						 var id = $(this).attr('id');
						 window.location.href =  "${path}/m/project/detail/index.do?id="+id;
					 });
				 }
				 var isrun = true;
				 function attentionPrj(obj){
					 if(isrun){
						 isrun=false;
						zdy_ajax({
					  		url: "${path}/m/project/attention.do",
					  		data:{id: $(obj).attr("data-id")},
					  		success:function(dataobj){
					  			if("0"===dataobj["result_code"]){
					  				var redu = dataobj["counts"];
					  				if(redu>100){
					  					redu =100;
					  				}
					  				$(obj).text("取消收藏");
					  				$(obj).attr("onclick","attentionDelPrj(this)");
					  				$('[redu-id="'+$(obj).attr("data-id")+'"]').attr("style","width:"+redu+"%");
					  				$('[gz-id="'+$(obj).attr("data-id")+'"]').text("收藏("+dataobj["counts"]+")"); 
					  				$(obj).attr("style","background: url("+_path+"/resource/img/icons/gzsuccess.png) no-repeat center;background-size: .25rem;padding-left: .35rem;background-position-x: 0;");
					  			}else{
					  				alert(dataobj["result_msg"]||"收藏失败")
					  			};
					  			isrun = true;
					  		},
					  		complete:function(){
					  		}
					  	})
					 }
					}

					function attentionDelPrj(obj){
						zdy_ajax({
					  		url: "${path}/m/project/removeattention.do",
					  		data:{id: $(obj).attr("data-id")},
					  		success:function(dataobj){
					  			if("0"===dataobj["result_code"]){
					  				var redu = dataobj["counts"];
					  				if(redu>100){
					  					redu =100;
					  				}
					  				$(obj).text("收藏");
					  				$(obj).attr("onclick","attentionPrj(this)");
					  				$('[redu-id="'+$(obj).attr("data-id")+'"]').attr("style","width:"+redu+"%");
					  				$('[gz-id="'+$(obj).attr("data-id")+'"]').text("收藏("+dataobj["counts"]+")");
					  				$(obj).attr("style","background: url("+_path+"/resource/img/icons/gz.png) no-repeat center;background-size: .25rem;padding-left: .35rem;background-position-x: 0;");
					  			}else{
					  				alert(dataobj["result_msg"]||"取消收藏失败")
					  			}
					  		},
					  		complete:function(){
					  		}
					  	})
					}
		</script>
	</body>

</html>