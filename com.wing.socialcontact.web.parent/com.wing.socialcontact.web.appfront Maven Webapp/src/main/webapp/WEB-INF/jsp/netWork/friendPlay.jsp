<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>
<%@ include file="/WEB-INF/jsp/commons/include_mobiscroll.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/include_login.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="keywords" content="以玩会友">
		<title>以玩会友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/friendPlay.css?v=${sversion}" />
		<style>
		  
		    .dww .dw-w-gr .dw-i{
		        color:#292829;
		    }
		</style>
	</head>

	<body>
		<div class="T-fPlay" style="background: #f2f3f4;width: 100%; height:100%;">
			<div class="search-box">
				      	<div id="search">搜索</div>
			</div>
			<jsp:include page="/WEB-INF/jsp/commons/include_banner.jsp" >
				<jsp:param name="bannerid" value="cb692aecc45f47efb7f2fe9999854582" />
			</jsp:include>
			
			<!-- 城会玩 -->
			<div class="cPlay">
				<div class="c-t">
					<h2>城会玩</h2>
					<a class="active_A" href="${path}/m/activity/activityTagsPage.do?classid=1"><i class="iconfont">&#xe605;</i></a>
				</div>
				
				<div class="swiper-container1" >
					<div class="swiper-wrapper b-imgBox" id="palyActivityTags">
						
					</div>
				</div>
				 
				 <!-- <div class="b-imgBox"  id="palyActivityTags">
				</div>  -->
			</div>
			
			<!-- 搜索条件 -->
			<ul class="hList tlist">
				<li>
				<%-- <div class="Z-item">
				<span>地区</span>
				<input type="hidden" id="province" name="province"/>
				<input type="hidden" id="city" name="city"/>
				<input type="hidden" id="area" name="region"/>
				<script type="text/javascript">
					var l_index = layer.load(0,{type:3,shade:[0.8,'#393D49'],scrollbar:false});
				</script>
				<ul id="demo2" style="display: none;"><c:forEach items="${provinceList}" var="province"><li data-val="${province.disName}">${province.disName}<ul><c:forEach items="${cityList }" var="city"><c:if test="${city.superId eq province.id}"><li data-val="${city.disName}">${city.disName}<ul><c:forEach items="${areaList}" var="area"><c:if test="${area.superId eq city.id }"><li data-val="${area.disName}" provinceid="${province.id}" cityid="${city.id}" areaid="${area.id}" searchName="${province.disName} ${city.disName} ${area.disName}">${area.disName}</li></c:if></c:forEach></ul></li></c:if></c:forEach></ul></li></c:forEach></ul></div>
			 --%>
					<select  id="citys" >
						
					  
					    <optgroup label="中国">  
					        <option value="">所有城市</option>     
					    </optgroup> 
					</select>
				</li>
				<li>
					<!-- <span id="classId">城会玩</span>
					
					<i class="iconfont">&#xe638;</i> -->
					<select name="tag" id="tag">
						
					</select>
				</li>
				<li>
					<select name="status" id="status" >
						<option value="0">所有状态</option>
						<option value="2">报名中</option>
						<option value="3">报名结束</option>
						<option value="4">进行中</option>
						<option value="5">活动结束</option>
					</select>
					<!-- <i class="iconfont">&#xe638;</i> -->
				</li>
				<li>
					<!-- <span>活动方式</span>
					<i class="iconfont">&#xe638;</i> -->
					<select name="pattern" id="pattern" >
						<option value="0">活动方式</option>
						<option value="1">线下参与</option>
						<option value="2">线上参与</option>
					</select>
				</li>
				<div style="clear:both"></div>
			</ul>
			<!-- 活动列表 -->
			<div id="activityContent" ></div>
<!-- 			<div class="h-cont" id="activityContent"></div> -->
			<div class="loadingbox">
				<div class="page_loading" style="display:block;">加载中…</div>
				<div class="page_nodata" style="display:none;">暂无更多数据</div>
			</div>
			<div style="height:1rem"></div>
			<div class="c-footer">
				<span class="want active_A"><a href="${path}/m/my/myfootPrint_activity.do" class="anniu">我的赴约</a></span>
				<div class="active_A" >
				<a href="${path}/m/activity/addActivityPage.do" style="width:100%;height:100%;display:block">
					<i class="iconfont">&#xe637;</i>
					<span>约会召集</span>
				</a>
				</div>
			</div>
		</div>
	</body>
</html>
<script>
var page = 1;
var pageSize = 10;
var end=false;
$(document).ready(function() {
	getCityByProvince();
	loadActivityTags(1,1,3);
	getTags();
	initloadpage();
	localStorage.activityurl="${path}/m/activity/friendPlayPage.do";
	
	
	$("#search").bind('touchstart',function(){
		window.location.href = "${path}/m/activity/activitysearch.do?classid=1";
	});
	//滚动加载
	  $(window).scroll(function(){
	       var scrollTop=$(this).scrollTop();
	        var scrollHeight = $(document).height();
            var windowHeight = $(window).height();
            if (scrollTop>=scrollHeight-windowHeight) {
            	loadActivitys(); 
            };
            if(scrollTop>=200){
				$(".tlist").css({marginTop:'0'})//置顶部分是否与上部分有间距
				$(".tlist").addClass("fixed");
			}else{
				$(".tlist").css({marginTop:'.08rem'})//置顶部分 间距复原
				$(".tlist").removeClass("fixed");
			}
	    }) ;
	

});

	function getTags(){
		zdy_ajax({
			   showLoading:false,
			   url:'${path}/m/activity/activityTags.do',
			   data:{
				  "classid":'1'
			   },
			   success:function(data1,data){
				  var str='<option value="">所有分类</option>';
						$.each(data.dataobj, function(i, n){
							str+='<option value="'+n.id+'">'+n.name+'</option>';
						});
						$('#tag').prepend(str);
			   },
			   error:function(data){
			   }
		});
	}
	function loadActivityTags(recommnend,classId,topNum){
		
		zdy_ajax({
			showLoading:false,
			url: "${path}/m/activity/selTagsList.do",
			data:{
				classId:classId
			},
			success: function(data,res){
				console.log(res);
				if(res.code == 0){
					var s='';
					var i = 0;
					$.each(res.dataobj, function(i, n){
						if(n.recommend==1){
							s += '<div class="swiper-slide" id="'+n.id+'"><a href="${path}/m/activity/activitylistPage.do?id='+n.id+'"><img src="'+imgReplaceStyle(_oss_url+n.imagePath,'YS300200')+'" alt="'+n.name+'" /><span>'+n.name+'</span><span class="covers"></span></a></div>';
						}
					});
					$("#palyActivityTags").append(s);
					 var swiper = new Swiper('.swiper-container1', {
				        spaceBetween:-80,
				        slidesPerView: 2.3,
				        slidesOffsetBefore :0,
				   }); 
					/* $('.swiper-slide').css({"width":"2.24rem"}) */
					$('.covers').css({"width":"2.24rem","height":"1.54rem"})
				}
			}
		}); 
	}


	var initflag = true;
	function initloadpage(){
		page = 1;
		end=false;
		$("#activityContent").html('');
		loadActivitys();
	}
	
	// type 1：上拉加载   2：条件查询 
	function loadActivitys(){
		
		var city=$("#citys").val();
		var tag=$("#tag").val();
		var status=$("#status").val();
		var pattern=$("#pattern").val();
		//var titles = $("#search").val();
		
		/* if($('#search').val()==''){
			$('#search').css('background-image','url(${path}/resource/img/icons/len.png)');
		}else{
			$('#search').css('background-image','none');
		} */
		if(!end&&initflag){
			$(".page_nodata").hide();
			$(".page_loading").show();
			initflag=false;
			zdy_ajax({
				url: "${path}/m/activity/selActivityList.do",
			    showLoading:false,
				data:{
					city:city,
					tag:tag,
					status:status,
					pattern:pattern,
					page : page,
					classId:'1',
					size:pageSize,
					recommendList:1
				},
				success: function(data,res){
					
					if(res.code == 0){
					    if(page==1 && !res.dataobj.length){
						   //$('#activityContent').html('<div  class="searchInfo">未搜索到相关内容</div>');
						   $(".page_loading").hide();
						   $(".page_nodata").show();
					    }else if(res.dataobj.length==0 || res.dataobj.length<pageSize){
							$(".page_loading").hide();
							$(".page_nodata").show();
						    end=true;
					    };
						var s='';
						var statusName = '';
						
						$.each(res.dataobj, function(i, n){
							s+= '<div class="h-cont">';
							s+='<a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><div class="cont-l"><img src="'+imgReplaceStyle(_oss_url+n.imagePath,'YS200200')+'" /></div></a>';
							if(n.status == 1){
								statusName = '待审核';
							}else if(n.status == 2){
								statusName = '报名中';
							}else if(n.status == 3){
								statusName = '报名结束';
							}else if(n.status == 4){
								statusName = '进行中';
							}else if(n.status == 5){
								statusName = '已结束';
							}
							s+='<div class="cont-r"><a href="${path}/m/activity/activityDetailPage.do?id='+n.id+'"><h2><b>['+statusName+']</b>'+n.titles+'</h2>';
							s+='<div class="icontt">';
							s+='<div class="cc"><i class="iconfont">&#xe602;</i><span>'+n.cityName+'</span></div>';
							s+='<div class="cc"><i class="iconfont">&#xe639;</i><span>'+formatDate(new Date(n.startTime))+'</span></div>';
							s+='<div class="cc"><i class="iconfont">&#xe61c;</i><span><b>'+n.cou+'人</b>报名</span></div>';
							s+='</div></a>';
							if(n.status==2){
							s+='<a href="${path}/m/my/signupPage.do?id='+n.id+'"><button class="active_A">赴约</button></a>';
							}
							s+='</div>';
							s+='</div>';
						});
						page++;
						$("#activityContent").append(s);
						initflag=true;
					}
				},
				error:function(e){
					//layer.msg('网络异常，请检查手机网络连接');
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
		return year+"/"+month+"/"+day; 
		} 
	
	//选择城市
	$('#citys').mobiscroll().select({  
	    theme: 'android-ics light',  
	    display: 'bottom',  
	    label: 'City',  
	    group: true,  
	    groupLabel: true,  
	    //选择表单展现的样式  
	     mode:'scroller',
	    setText:'确定',
		cancelText:'取消',
	    inputClass:"all_width text-right clear_border"  
	//  fixedWidth: [100, 170]  
	});
	
	function getCityByProvince(){
	
	
		 zdy_ajax({
			   url:_path+"/m/activity/getproandcity.do",
			   showLoading:false,
			   data:{
				
			   },
			   success:function(data,bc){
				  var str='';
				  var str2='';
				  for(var i=0;i<data.length;i++){
					  var str2='';
					  for(var b=0;b<data[i].city.length;b++){
						  str2 += '<option value="'+data[i].city[b].id+'">'+data[i].city[b].disName+'</option>';
					  }
					  str += '<optgroup label="'+data[i].disName+'">'+  
					                                            str2+
				             '</optgroup>';
				  }
				  $('#citys').append(str);
					$('#citys').mobiscroll().select({  
					    theme: 'android-ics light',  
					    display: 'bottom',  
					    label: 'City',
					    mode:'scroller',
					    group: true,  
					    groupLabel: false,  
					    setText:'确定',
					    cancelText:'取消',
					    //选择表单展现的样式  
					    inputClass:"all_width text-right clear_border"  
					//  fixedWidth: [100, 170]  
					});
			   },
			   error:function(data){
				   
			   }
				
			});
		 
	}
	
	$('#citys').change(function(){
		initloadpage(); 
	});
	$('#tag').change(function(){
		initloadpage(); 
	});
	$('#status').change(function(){
		initloadpage(); 
	});
	$('#pattern').change(function(){
		initloadpage(); 
	});
</script>