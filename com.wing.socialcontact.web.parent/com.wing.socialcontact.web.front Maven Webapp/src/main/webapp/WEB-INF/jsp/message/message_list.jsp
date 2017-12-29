<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title id="title">
        	<c:choose>
        		<c:when test="${type eq 3}">系统消息</c:when>
        		<c:when test="${type eq 4}">活动消息</c:when>
        	</c:choose>
        </title>
        <link rel="stylesheet" href="${path}/resource/css/system-Messages.css" />
        <style>
		a{
			color:blue;
		}
	</style>
    </head>
    <body>
        <div class="wrapper" id="result" style="display: none;">
        </div>
        <div class="loadingbox" style="display: none;">
			<div class="page_loading" style="display:none;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
		
		<script type="text/javascript">
			var page=1;
			var pageSize=10;
			var loadFlag=true;
			var isEnd=false;
			$(function(){
				load_msg();//加载数据
				read_msg();//设已读
			});
			var load_msg=function(){
				loadFlag=false;
				$(".page_loading").show().parent().show();
				zdy_ajax({
					url: "${path}/m/message/message_list_ajax_page.do",
					async:true,
					data:{
						page:page,
						pageSize:pageSize,
						type:'${type}'
					},
				    showLoading:false,
					success: function(dataobj,res){
						var str="";
						for(var i=0;i<dataobj.length;i++){
							var obj=dataobj[i];
							str+='<div class="systemMessage_time">';
							str+='<div><span class="time">';
							str+=date_format(obj.createTime,"yyyy年MM月dd HH:mm");
							str+='</span></div>';
							str+='</div>';
							str+='<div class="systemMessage"><div>'+obj.content+'</div></div>';
						}
						if(dataobj.length > 0){
							$("#result").show();
							$("#result").append(str);
						}
						$(".page_loading").hide().parent().hide();
						if(dataobj.length < pageSize){
							isEnd=true;
							$(".page_nodata").show().parent().show();
						}else{
							bind_event();
						}
						loadFlag=true;
					}
				});
			}
			
			var read_msg=function(){
				zdy_ajax({
					url: "${path}/m/message/update_message_status.do",
					async:true,
					data:{
						type:'${type}'
					},
				    showLoading:false,
					success: function(dataobj,res){
						//批量修改消息状态为已读
					}
				});
			}
			
			var bind_event=function(){
				if(page == 1){
					$(window).scroll(function(){
					    if($(window).scrollTop() >= $(document).height() - $(window).height()){
					        //alert("滚动到底部啦！");
					        if(loadFlag){
						        if(!isEnd){
						        	page++;
						        	load_msg(); 
						        }
					        }
					    }
					});
				}
			}
		</script>
    </body>
</html>