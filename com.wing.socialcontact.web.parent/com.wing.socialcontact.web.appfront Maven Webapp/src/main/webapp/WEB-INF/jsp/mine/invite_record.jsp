<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="邀请好友列表">
		<title>邀请好友列表</title>
		<style type="text/css">
         	.wrapper{
          		width: 100%;
          		padding-left: .30rem;
          		box-sizing: border-box;
          		background:#fff;
          	}
          	.item{
          		width: 100%;
          		height: .90rem;
          		box-sizing: border-box;
          		padding-right: .30rem;
          		background: #fff;
          		display: flex;
          		font-size: .24rem;
          		justify-content: space-between;
          		align-items: center;
          		border-bottom: 1px solid #f2f3f4;
          		color: #808080;
          	}
          	.item span:nth-of-type(1){
          		color: #3B3B3B;
          		font-size: .30rem;
          		width:50%;
          		white-space: nowrap;
            	overflow: hidden;
            	text-overflow: ellipsis;
          	}
          	.item span:nth-of-type(2){
          		/* margin-left: 2rem; */
          	}
         </style>
	</head>
	<body style="background: #f2f3f4;">
	    <div style="padding-bottom:0.9rem ;">
	    <div class="wrapper" id="result">
		 	
	 	</div>
	 	<div class="loadingbox" style="display: none;">
			<div class="page_loading" style="display:none;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
	    </div>
		
        <div class="in-cz" onclick="open_fxyl();">
        	发送邀请
        </div>
		
		<script type="text/javascript">
			var pageNum=1;
			var pageSize=10;
			var loadFlag=true;
			var isEnd=false;
			$(function(){
				load_invite_record();
			});
			var open_fxyl=function(){
				self.location.href="${path}/m/my/fxyl.do";
			}
			var load_invite_record=function(){
				loadFlag=false;
				$(".page_loading").show().parent().show();
				zdy_ajax({
					url: "${path}/m/my/invite_record_ajax_page.do",
					async:true,
					data:{
						page:pageNum,
						pageSize:pageSize
					},
				    showLoading:false,
					success: function(dataobj,res){
						var str="";
						for(var i=0;i<dataobj.length;i++){
							var obj=dataobj[i];
							var userName='??';
							var status='已关注';
							if(!isEmpty(obj.uid)){
								userName=obj.nickName;
								if(obj.reconStatus == 2){
									status='已认证';
								}else{
									status='已注册';
								}
							}else{
								if(!isEmpty(obj.byqNickName)){
									userName=obj.byqNickName;
								}
							}
							str+='<div class="item">';
							str+='<span>'+userName+'</span>';
							str+='<span>'+date_format(obj.createTime,"yyyy-MM-dd HH:mm")+'</span>';
							str+='<span>'+status+'</span>';
							str+='</div>';
						}
						$("#result").append(str);
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
			
			var bind_event=function(){
				if(pageNum == 1){
					$(window).scroll(function(){
					    if($(window).scrollTop() >= $(document).height() - $(window).height()){
					        //alert("滚动到底部啦！");
					        if(loadFlag){
						        if(!isEnd){
						        	pageNum++;
						        	load_invite_record(); 
						        }
					        }
					    }
					});
				}
			}
		</script>
	</body>
</html>