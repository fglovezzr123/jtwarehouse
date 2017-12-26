<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1">
		<meta name="keywords" content="积分明细">
		<title>积分明细</title>
		<style type="text/css">
			.mx{
				width: 100%;
				padding: 0 .30rem;
				box-sizing: border-box;
				background: #fff;
				font-size: .24rem;
				margin-bottom: .1rem;
			}
			.items{
				width: 100%;
				height: .76rem;
				display: flex;
				justify-content: space-between;
				align-items: center;
			}
			.C-footer{
				width: 100%;
				height: 1rem;
				font-size: .30rem;
				background: #0f88eb;
				color: #fff;
				text-align: center;
				line-height: 1rem;
				position: fixed;
				left: 0;
				bottom: 0;
				display:none;
			}
		</style>
	</head>
	<body style="background: #f2f3f4;">
		<div id="result">
		</div>
		<div class="loadingbox" style="display: none;">
			<div class="page_loading" style="display:none;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
		</div>
		<!-- <div class="C-footer" id="cz_button">积分规则</div> -->
		<script type="text/javascript">
			var pageNum=1;
			var pageSize=10;
			var loadFlag=true;
			var isEnd=false;
			$(function(){
				load_log();
			});
			var load_log=function(){
				loadFlag=false;
				$(".page_loading").show().parent().show();
				zdy_ajax({
					url: "${path}/m/my/integral_log_ajax_page.do",
					async:true,
					data:{
						pageNum:pageNum,
						pageSize:pageSize
					},
				    showLoading:false,
					success: function(dataobj,res){
						var str="";
						for(var i=0;i<dataobj.length;i++){
							var obj=dataobj[i];
							str+='<div class="mx">';
							str+='<div class="items">';
							str+='<span>积分</span>';
							str+='<span>'+number_format(obj.integral,0)+'</span>';
							str+='</div>';
							str+='<div class="items">';
							str+='<span>积分累积</span>';
							str+='<span>'+obj.ye_integral+'</span>';
							str+='</div>';
							str+='<div class="items">';
							str+='<span>交易时间</span>';
							str+='<span>'+date_format(obj.create_time,"yyyy-MM-dd HH:mm")+'</span>';
							str+='</div>';
							str+='<div class="items">';
							str+='<span>积分备注说明</span>';
							str+='<span>'+obj.remark+'</span>';
							str+='</div>';
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
						        	load_log(); 
						        }
					        }
					    }
					});
				}
			}
			
			/* $("#cz_button").click(function(){
				self.location.href="${path}/m/my/my_integral_info.do";
			}); */
		</script>
	</body>
</html>