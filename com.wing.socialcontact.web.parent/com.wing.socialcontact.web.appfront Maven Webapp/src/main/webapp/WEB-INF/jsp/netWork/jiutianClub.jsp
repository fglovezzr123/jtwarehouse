<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>天九会所</title>

<link rel="stylesheet" href="${path}/resource/css/jiutian.css?v=${sversion}" />

<style>
.banner_ul img {
	width: 100%;
	height: 100%;
}
.wrapper{
   padding-top:0.75rem;
}

#banner1 .banner_ul img {
	width: 92%;
}
.search-box{
	box-sizing:border-box;
     width:100%;
     padding-left:0.13rem;
     padding-right:0.13rem;
     background:#f3f4f6;
     height:0.78rem;
     padding-left:5%;
     padding-right:5%;
     line-height:0.78rem;
     text-align:center;
     position:fixed;
     top:0;
     left:0;
     z-index:10;   
}
.search-box div{
     margin:0 auto;
     border-radius:0.15rem;
     background:white;
}
.search-box input{
     height:0.5rem;
     width:95%;
     border-radius:0.15rem;
     border:none;
     text-align:center;  
     
}
 #searchingBTN {
	width: 16%;
	font-size: 0.3rem;
	border-radius: 0.1rem;
	background: #0f88eb;
	color: white;
	display: inline-block;
	height: 0.56rem;
	line-height: 0.56rem;
	text-align: center;
}
 .search-box #searching{
          width:80%;
          margin:0;
         background-image: url(${path}/resource/img/icons/len.png);
	    background-repeat: no-repeat;
	    background-position-x: 2rem;
	    background-position-y: center;
	    background-size: 4.7%;
 }
 .search-box{
      padding:0 0;
     padding-left: 3%;
   padding-right: 3%;
   display: flex;
   justify-content: space-between;
   align-items: center;
   }	
.jiutianSearch{
  position:fixed;
  top:0;
  left:0;
  z-index:1000
}
.fixed{
  position:fixed;
  top:0.78rem;
  left:0;
  background:#fff;
  width:100%;
  z-index:10;
}
</style>
</head>
<body>
        <div class="search-box">
			<input type="text" name="search" id="searching"  placeholder="搜索" style="background-position-x: 2.0rem;"/>
			<span id="searchingBTN">搜索</span>
		</div>
	<div class="wrapper">
		<jsp:include page="../commons/include_banner.jsp">
			<jsp:param name="bannerid" value="436dfd203fdb4f70aed1775c91f178e5" />
		</jsp:include>
		<div class="club-content">
			<div class="club-bar">
				<div class="current changeP active_A" id="allClubs">全部会所</div>
				<div class="active_A changeP" id="havaCollected">收藏会所</div>
				<br class="clear" />
			</div>
		</div>
		<div id="club-list"></div>
		<div class="club-tab-content" id="selecteArea" style="display:none">
		</div>
	</div>

	<script>
		//默认获取全部分类
		(function() {
			var currentId = '';
			var keyword = '';
			var imgpathss = _oss_url;
			var CollectioncPage = '1';
			var classPage = '1';
			var runsearch = false;
			getDatas();
			getClasses();
			$('#allClubs').click(function() {
				classPage = '1';
				keyword = $('#searching').val();
				if ($('#selecteArea').css('display') == 'none') {
					$('#selecteArea').css('display', 'block');
					$('#club-list').empty();
				} else if ($('#selecteArea').css('display') == 'block') {
					$('#selecteArea').css('display', 'none');

					getDatas()
				}
				;
			});
			$('#searching').on('keyup',function(){
				var valLen = $('#searching').val().trim().length
				if(valLen>0){
					 $(this).css({backgroundImage:'url()'})
				}else{
					$(this).css({backgroundImage:'url(${path}/resource/img/icons/len.png)'})
				}
			})
			$('.changeP').click(function() {
				classPage = '1';
				runsearch = false;
				$(this).addClass('current').siblings().removeClass('current');
				//$('#searching').val('');

			});

			$('#havaCollected').bind('click', function() {
				classPage = '1';
				//keyword = '';
				$('#club-list').empty();
				$('#selecteArea').css('display', 'none');
				getColected();
			});

			function getDatas() {
				CollectioncPage = 1;
				zdy_ajax({
					url : _path + "/club/m/list.do",
					showLoading : false,
					data : {
						'classid' : currentId,
						'key' : keyword,
						'page' : classPage,
						'size' : 5
					},
					success : function(data) {

						if (!data.length) {
							layer.msg('无更多数据了');
						}
						;
						layer.close(layer.load(0, {
							type : 3,
							shade : [ 0.8, '#393D49' ],
							scrollbar : false
						}));
						var str1 = '';
						for (var i = 0; i < data.length; i++) {
							str1 += '<div class="club-present" id="'+data[i].id+'">'
									+ '<h1>'
									+ data[i].title
									+ '</h1>'
									+ '<img src="'+imgpathss+data[i].imgpath+'"/>'
									+ '<div>联系方式:'
									+ data[i].phone
									+ '</div>'
									+ '</div>';
						}
						$('#club-list').append(str1);
						$('.club-present')
								.bind(
										'click',
										function() {
											window.location.href = "${path}/club/m/dePage.do?id="
													+ $(this).attr('id');
										})
					},
					error : function(data) {

					}

				});
			};

			function getClasses() {
				var str1 = '';
				classPage = '1';

				zdy_ajax({
					url : _path + "/club/m/classList.do",
					showLoading : false,
					data : {

					},
					success : function(data) {

						layer.close(layer.load(0, {
							type : 3,
							shade : [ 0.8, '#393D49' ],
							scrollbar : false
						}));

						var str2 = '';
						for (var i = 0; i < data.length; i++) {
							str2 = '';
							for (var b = 0; b < data[i].son.length; b++) {
								str2 += '<div class="smallClass active_A" id="'+data[i].son[b].id+'">'
										+ data[i].son[b].name + '</div>';
							}
							;
							str1 += '<div class="club-head">' + data[i].name
									+ '</div>' + '<div class="club-position">'
									+ str2 + '<br class="clear"/>' + '</div>';
						}

						$('#selecteArea').append(str1);
						$('#selecteArea')
								.prepend(
										'<button class="active_A" id="returnToAll">全部会所</button>');

						$('.smallClass').bind('click', function() {
							classPage = '1';
							currentId = $(this).attr('id');
							$('#selecteArea').css('display', 'none');
							getDatas();
							$('#allClubs').attr('title', currentId);
							$('#allClubs').text($(this).text());
						});

						$('#returnToAll').bind('click', function() {
							classPage = '1';
							currentId = '';
							$('#selecteArea').css('display', 'none');
							getDatas();
							$('#allClubs').attr('title', currentId);
							$('#allClubs').text($(this).text());
						});
					},
					error : function(data) {

					}

				});
			}
			;

			function getColected() {

				zdy_ajax({
					url : _path + "/mycollection/m/mycollection.do",
					showLoading : false,
					data : {
						type : 2,
						size : 5,
						page : CollectioncPage
					},
					success : function(data, bc) {
						//console.log(data);
						if (!data.length) {
							layer.msg('无更多数据了');
							return false;
						}
						;
						layer.close(layer.load(0, {
							type : 3,
							shade : [ 0.8, '#393D49' ],
							scrollbar : false
						}));
						var str1 = '';
						for (var i = 0; i < data.length; i++) {
							str1 += '<div class="club-present" id="'+data[i].id+'">'
									+ '<h1>'
									+ data[i].title
									+ '</h1>'
									+ '<img src="'+imgpathss+data[i].imgpath+'"/>'
									+ '<div>联系方式:'
									+ data[i].phone
									+ '</div>'
									+ '</div>';
						}
						$('#club-list').append(str1);
						$('.club-present')
								.bind(
										'click',
										function() {
											window.location.href = "${path}/club/m/dePage.do?id="
													+ $(this).attr('id');
										});
					},
					error : function(data) {

					}

				});
			};
			var height = $('.club-bar').offset().top
			$(window).scroll(function() {
				var m = $(document).scrollTop();
				console.log(m)
				 if(m>height){
					$(".club-bar").addClass("fixed");
				}else{
					$(".club-bar").removeClass("fixed");
				} 
				runsearch = false;
                if($("#selecteArea").css("display")=="block"){
                	  return;
                } 
				var scrollTop = $(this).scrollTop();
				var scrollHeight = $(document).height();
				var windowHeight = $(this).height();
				if (scrollTop + windowHeight == scrollHeight) {
					if ($('#havaCollected').hasClass('current')) {
						CollectioncPage++;
						getColected();
					} else {
						classPage++;
						getDatas();
					}

				}
				;
			});
			$('#searchingBTN').on(
					'click',
					function() {
						$('#allClubs').addClass('current').siblings()
								.removeClass('current');
						$('#selecteArea').css('display', 'none');
						$('#club-list').empty();
						classPage = '1';
						CollectioncPage = '1';
						keyword = $('#searching').val();
						getDatas();
					})
		  $('#searching').on('input',function(){
			  if(!$(this).val()){
				    keyword = '';
				    classPage='1';
					CollectioncPage='1';
				  $('#club-list').empty();
				  getDatas();
			  }
			  
		  })			

		}());
	</script>
</body>

</html>