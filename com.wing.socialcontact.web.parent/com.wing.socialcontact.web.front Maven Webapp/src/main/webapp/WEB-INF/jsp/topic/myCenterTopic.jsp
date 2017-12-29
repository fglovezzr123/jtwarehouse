<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_recon.jsp"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>个人中心-话题</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/myfootPrint-topic.css?v=${sversion}" />
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/talkPk.css?v=${sversion}" />
	</head>

	<body>
		<div class="T-infor" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="wrapper" id="wrapper">
				<div class="tlist">
					<span class="show-active" type="1" >我参与的话题</span>
					<span type="2" >我发布的话题</span>
					<span type="3" >我收藏的话题</span>
				</div>
				
				<div id="topiclist1" ></div>	
				
			</div>
		</div>
	</body>
<script type="text/javascript">
var types = "1";
$(document).ready(function() {
	content($(this).attr("type"));
	$('.tlist span').click(function(e){
		var index = $(this).index();
		$(this).addClass('show-active').siblings().removeClass('show-active');
		content($(this).attr("type"));
		types = $(this).attr("type");
    })
});
function content(_type){
	$("#contentdiv").html("");
	var titles = $("#search").val();
	if(_type==""||_type==null){
		_type = "1";
	}
	zdy_ajax({
		url: "${path}/m/topic/selMyCenterTopicList.do",
		data:{
			types:_type,
			titles:titles
		},
		success: function(data,res){
			$("#topiclist1").empty();
			if(res.code == 0){
				var s='';
				if(!res.dataobj.topicList.length){
					 $('#topiclist1').html('<div  class="searchInfo">暂无更多数据</div>');
				}
				$.each(res.dataobj.topicList, function(i, n){
					var honor_flag = n.honor_flag,tjIdImg="";
					/*  console.log(honor_flag)  */
					if(honor_flag=="honor_001"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_002"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_003"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_004"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>'
					}
					s+='<div class="print-conetnt" id="contentdiv"><div class="print-conetnt-head"><div class="info-l"><img src="'+n.headPortrait+'" class="p-img" /></div>'
						+'<div>'
						+'<h1><span style="font-size:0.32rem">'+n.nickname+'</span>'+tjIdImg +'<span>'+n.createTime+'</span></h1>'
						+' <p>'+n.job+'/'+n.comName+'</p>'
						+'</div><br class="clear"/></div><div class="topic-discussion"><h2 onclick="openurl(\'${path}/m/topic/detailPage.do?id='+n.id+'\')">'+n.topicDesc+'</h2>'
						+'<div><div>红方观点 : </div><div>'+n.redPoint+'</div><br class="clear"/></div><div>'
						+'<div class="blue-side">蓝方观点 : </div><div>'+n.bluePoint+'</div><br class="clear"/></div>'
						+'<div><div class="black-side">我的观点 : </div><div>';
						if(n.voteType=='1'){
							s += '红方观点';
						}else if(n.voteType=='2'){
							s += '蓝方观点';
						}else{
							s += '无站队观点';
						}
						s += '</div></div>';
						if(_type=='3'){
							s += '<div><span data-id="'+n.id+'" onclick="attentionDel(this)" class="active_A">取消收藏</span></div>';
						}
						s += '</div></div>';
				});
				$("#topiclist1").append(s);
			}
		}
	}); 
	
}

function openurl(url){
	document.location.href=url;
}
function attentionDel(obj){
	zdy_ajax({
  		url: "${path}/m/topic/addAttention.do",
  		data:{id: $(obj).attr("data-id")},
  		success: function(data,output){
			alert_back(output.msg,function(){
				if(output.msg=="取消收藏"){
					$(obj).parent().parent().parent().remove();
				}
			});
		},
  		complete:function(){
  		}
  	})
}
</script>

</html>