<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String id="";
String cmeType="";
if(request.getParameter("id")!=null)id=request.getParameter("id");
if(request.getParameter("cmeType")!=null)cmeType=request.getParameter("cmeType");
%>
<div id="comment_dec">
    
</div>
<div class="loadingbox">
			<div class="page_loading" style="display:none;">加载中…</div>
			<div class="page_nodata" style="display:none;">暂无更多数据</div>
</div>
<script type="text/javascript">
var idc="<%=id %>";
var _cmeType="<%=cmeType %>";

var pageNum=1;
var pageSize=10;
var loadFlag=true;
var isEnd=false;
$(function(){
	load_log();
	$(window).scroll(function(){
	    if($(window).scrollTop() >= $(document).height() - $(window).height()){
	        //alert("滚动到底部啦！");
	    	if(loadFlag){
		        if(!isEnd){
		        	load_log(); 
		        }
	    	}
	    }
	});
});

var load_log=function(){
	//alert(loadFlag);
	loadFlag=false;
	$(".page_loading").show();
	zdy_ajax({
		url:"${path}/m/comment/selComments2.do",
		async:true,
		data:{
			pageNum:pageNum,
			pageSize:pageSize,
			fkId:"<%=id %>",
			cmeType:"<%=cmeType %>"
		},
	    showLoading:false,
		success: function(data,res){
			if(res.code == 0){
				var s='';
				$.each(res.dataobj, function(i, n){
					//alert(n.isThumbup);
					var honor_flag = n.honor_flag,tjIdImg="";
					if(honor_flag=="honor_001"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_002"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_003"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_004"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>'
					}
					var backgrounds='background:url(${path}/resource/img/icons/good.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
					if(n.isThumbup){
						backgrounds='background:url(${path}/resource/img/icons/bad.jpg) no-repeat left center;background-size:0.32rem 0.30rem';
					}
					if(n.industry){
						s += '<div class="infor">';
						s += '<div class="infor-l"  onclick="open_user_center(\''+n.userId+'\')" style=\"border-radius:50%;background:url('+n.imgurl+'\) no-repeat center;background-size:100% 100%"></div>';
						s += '<div class="infor-r">';
						s += '	<div class="rt">';
						s += '		<h2 onclick="open_user_center(\''+n.userId+'\')" class="topicDetails" >'+n.nickname+'</h2>';
						s +=   tjIdImg
						s += '		<div class="tbs">';
						s += '			<span class="active_A" onclick="comment_sub(\''+n.id+'\');">';
						s += '				<b >'+n.subcount+'</b>';
						s += '			</span>';
						s += '			<span style="'+backgrounds+'" isThumbup='+n.isThumbup+' class="active_A givecomment" data-id="'+n.id+'"><b>'+n.count+'</b></span>';
						s += '		</div>';
						s += '	</div>';
						s += '	<div class="rm">';
						s += '		<span class="i-time">'+n.createTime+'</span>';
						s += '		<span class="i-pay">'+n.job+'/'+n.industry+'</span>';
						s += '	</div>';
						s += '	<div class="rb">'+n.cmeDesc+'</div>';
						if(n.imgUrl!=null&&n.imgUrl!=""&&n.imgUrl!="undefined"){
							s += '  <div class="imgBox_1">';
							s += '    <img onclick="showimgs2(this)" src=\"'+n.ossurl+n.imgUrl+'\" style="width:100%"/>';
							s += '   </div>';
						}
						if(n.subCommonCount != 0 && n.subCommonCount != 'undefined' && n.subCommonCount != null){
							s += '	<div class="rt" style="height:0.36rem;line-height:0.36rem;">';
							s += '		<p style="font-size:0.24rem; text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">'+'<b style="font-style:normal;font-size:0.26rem;color:#52a1c0">'+n.subCommonUser.nickname+'</b>'+' 回复  '+'<b style="font-style:normal;font-size:0.26rem;color:#52a1c0">'+n.nickname+'</b>'+'：'+n.subcomment.cmeDesc+'</p>';
							s += '	</div>';
							//s += '	<div class="rb">'+n.subcomment.cmeDesc+'</div>';
							s += '	<div class="rb" style="color:#52a1c0;line-height:0.36rem" onclick="comment_sub(\''+n.id+'\');">查看所有评论</div>';						
						}
						s += '</div>';
						s += '</div>';
					}else{
						
						s += '<div class="infor">';
						s += '<div class="infor-l" onclick="open_user_center(\''+n.userId+'\')"  style=\"border-radius:50%;background:url('+n.imgurl+'\) no-repeat center;background-size:100% 100%"></div>';
						s += '<div class="infor-r">';
						s += '	<div class="rt">';
						s += '		<h2 onclick="open_user_center(\''+n.userId+'\')" >'+n.nickname+'</h2>';
						s += '		<div class="tbs">';
						s += '			<span class="active_A" onclick="comment_sub(\''+n.id+'\');">';
						s += '				<b >'+n.subcount+'</b>';
						s += '			</span>';
						s += '			<span style="'+backgrounds+'"  isThumbup='+n.isThumbup+' class="active_A givecomment"  data-id="'+n.id+'"><b>'+n.count+'</b></span>';
						s += '		</div>';
						s += '	</div>';
						s += '	<div class="rm">';
						s += '		<span class="i-time">'+n.createTime+'</span>';
						s += '		<span class="i-pay">'+n.job+'</span>';
						s += '	</div>';
						s += '	<div class="rb">'+n.cmeDesc+'</div>';
						if(n.imgUrl!=null&&n.imgUrl!=""&&n.imgUrl!="undefined"){
							s += '  <div class="imgBox_1">';
							s += '    <img onclick="showimgs2(this)" src=\"'+n.ossurl+n.imgUrl+'\" style="width:100%"/>';
							s += '   </div>';
						}
						if(n.subCommonCount != 0 && n.subCommonCount != 'undefined' && n.subCommonCount != null){
							s += '	<div class="rt" style="height:0.36rem;line-height:0.36rem;">';
							s += '		<p style="font-size:0.24rem;height:0.36rem;line-height:0.36rem; text-overflow: ellipsis;-o-text-overflow: ellipsis;overflow: hidden;white-space: nowrap;" >'+'<b style="font-style:normal;font-size:0.24rem;color:#52a1c0">'+n.subCommonUser.nickname+'</b>'+' 回复  '+'<b style="font-style:normal;font-size:0.24rem;color:#52a1c0">'+n.nickname+'</b>'+'：'+n.subcomment.cmeDesc+'</p>';
							s += '	</div>';
							//s += '	<div class="rb">'+n.subcomment.cmeDesc+'</div>';
							s += '	<div class="rb" style="color:#52a1c0;line-height:0.36rem" onclick="comment_sub(\''+n.id+'\');">查看所有评论</div>';						
						}
						s += '</div>';
						s += '</div>';
					}
					
				
				});
			/* 	s += '<br>'; */
				if("<%=cmeType %>"=="1"){
					s += '<div class="share" id="share"><div><div class="active_A" style="width:100%;height:100%;background:white" onclick="fx();">分享</div></div><div class="active_A" onclick="comment_add();">评论</div><br class="clear" /></div> <div class="com-backdrop" onclick="clickimg();" style="display: none;"></div><div class="tips-s-img" onclick="clickimg();" style="display: none;"></div>';
				}else{
					s += '<button class="btn active_A" onclick="comment_add();">评论</button>';
				}
				
				$("#comment_dec").append(s);
				
				if(res.dataobj.length < pageSize){
					isEnd=true;
					$(".page_nodata").show();
				}
				$(".page_loading").hide();
				loadFlag=true;
				pageNum++;
	        	///alert(pageNum);
				
			}
			$(".givecomment").on("click",function(){
				console.log(1)
				var id = $(this).attr("data-id")
				thumbup1(this,id)
			})
		}
		
	});
}


function showimgs2(obj){	
		primgs = [];
		primgs.push($(obj).attr("src"));
		wx.previewImage({
		    current: $(obj).attr("src"), // 当前显示图片的http链接
		    urls: primgs // 需要预览的图片http链接列表
		});
}


 var comment_sub=function(idc){
    	self.location.href='${path}/m/comment/selCommentByPid.do?parentId='+idc;
    }
 
 var comment_add=function(){
    	
    	self.location.href='${path}/m/comment/comment_add.do?fkId='+idc+'&cmeType='+_cmeType;
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
	
	function thumbup1(obj,idc){
		var a=$(obj).find("b").html();
		zdy_ajax({
			url: "${path}/m/comment/Thumbup.do",
			data:{
				id:idc,
			},
			success: function(data,res){
				if(res.code == 0){
					if(res.msg==0){
						$(obj).css({"background":"url(${path}/resource/img/icons/bad.jpg) no-repeat left center","background-size":"0.32rem 0.30rem"}); 
						$(obj).find("b").html(a*1+1);
						alert("点赞成功！");
						$(obj).attr('isThumbup',true);
					}else{
								zdy_ajax({
									url: "${path}/m/comment/cancelThumbup.do",
									data:{
										id:idc,
									},
									success: function(data,res){
										if(res.code == 0){
											if(res.msg==0){
												$(obj).css({"background":"url(${path}/resource/img/icons/good.jpg) no-repeat left center","background-size":"0.32rem 0.30rem"});
												$(obj).find("b").html(a*1-1);
												alert("点赞已取消！");
												$(obj).attr('isThumbup',false);
											}else{
												
											}
										}
									},
								    error:function(e){
									   //alert(e);
								    }
								}); 	
						
						//alert("你已点赞");
					}
				}
			},
		    error:function(e){
			   //alert(e);
		    }
		}); 
		
		//$(obj).find("b").html(a*1+1);
	}
	
	//查看他人主页
	var open_user_center=function(userId){
		self.location.href="${path}/m/my/personal_home2.do?userId="+userId;
	}

	//查看评论内容
	function lookCommentInfo(id){
		window.location.href="${path}/m/dynamic/commentPage.do?id="+id;
	}

</script>
	