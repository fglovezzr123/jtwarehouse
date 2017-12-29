<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">投资意向记录</title>
        <link rel="stylesheet" type="text/css" href="${path}/resource/css/libs/public.css?v=${sversion}"/>
		  
        <link rel="stylesheet" href="${path}/resource/css/hava-intention.css?v=${sversion}" />
       
        <script src="${path}/resource/js/libs/jquery-2.2.3.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
        <script src="${path}/resource/js/libs/zepto.min.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
          
        <style>
             .banner_ul img{
                 width:100%;
                 height:100%;
             }
        </style>
    </head>
    <body>
        <div class="wrapper" id="showInfo">
       </div>
       <div class="loadingbox">
			<div class="page_loading" style="display:block;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
    <script type="text/javascript">
            
            var page=1;
            var pageSize=5;
            var end=false;
            $(function(){
           	 uploaddata();
           //滚动加载
			  $(window).scroll(function(){
			       var scrollTop=$(this).scrollTop();
			        var scrollHeight = $(document).height();
			            var windowHeight = $(this).height();
			            if (scrollTop+windowHeight==scrollHeight) {
			            	uploaddata(); 
			            };
			    })
           	 
           	  });
             function uploaddata(){
            	 if(!end){
           		 zdy_ajax({
       				showLoading:false,
            		url: '${path}/investment/m/intentionList.do',
     				data:{
     					"page":page,
     					"size":pageSize
     				},
     				
     				success: function(data1,data){
     					var str='';
     					if(data.code == 0){
     						if(page==1 && !data.dataobj.length){
     						  // $('#rmhd').html('<div  class="searchInfo">没有数据</div>');
     						   $(".page_loading").hide();
     						   $(".page_nodata").show();
     					    }else if(data.dataobj.length==0 || data.dataobj.length<pageSize){
     							$(".page_loading").hide();
     							$(".page_nodata").show();
     						    end=true;
     					    };
     						$.each(data.dataobj, function(i, n){
     						if(isEmpty(n.answer)){
     							n.answer="暂未回复";
     						}
     						var status = "待联系";//1-4   待联系   待考虑  已投资  已拒绝
     						
     						if(n.status ==2){
     							status = "待考虑";
     						}
     						if(n.status ==3){
     							status = "已投资";
     						}
     						if(n.status ==4){
     							status = "已拒绝";
     						}
        						str+=    '<div class="inList"><div class="invest-form2"><div>投资兴趣</div><div>'+n.className
		        						+'</div><br class="clear"/></div><div class="invest-form2"><div>投资额度</div><div>'+n.position
		        						+'  RMB(元)</div><br class="clear"/></div><div class="invest-form2"><div>投资状态</div><div>'+status
		        						+'</div><br class="clear"/></div><div class="invest-form2"><div>提交时间</div><div>'+formatDate(new Date(n.createTime))
		        						+'</div> <br class="clear"/></div><div class="invest-form2"><div>提交意向</div><div class="intention-text"><textarea readonly="readonly">'
		        						+n.message+'</textarea> </div> <br class="clear"/></div><div class="invest-form2" style="border:none;margin-bottom:0.2rem;">'
		        				         +'<div>客服回复</div><div>'+n.answer+'</div><br class="clear"/></div></div>';  
     						});
    					$("#showInfo").append(str);
    					page++;
     					}
    				},
    				error:function(e){
    					//alert(e);
    				}
            	 });
            	 }
             }
             
             function formatDate(now) { 
         		var year=now.getFullYear(); 
         		var month=now.getMonth()+1;
         		if(month<10){
         			month="0"+month;
         		}
         		var day=now.getDate();
         		if(day<10){
         			day="0"+day;
         		}
         		var hour=now.getHours();
         		if(hour<10){
         			hour="0"+hour;
         		}
         		var minute=now.getMinutes();
         		if(minute<10){
         			minute="0"+minute;
         		}
         		var second=now.getSeconds();
         		if(second<10){
         			second="0"+second;
         		}
         		return year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second; 
         		} 
    </script>
   
    </body>
</html>