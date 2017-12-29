<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/include.inc.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<meta name="keywords" content="我的好友">
		<title>我的好友</title>
		<link rel="stylesheet" type="text/css" href="${path}/resource/css/businessFriend.css?v=${sversion}"/>
		<style type="text/css">
		.initials {
          top: 2px;
           }
           input[type=checkbox]{
            top:.35rem;
           }
           .sort_box {
           padding-bottom: 0rem;
         }
		</style>
	</head>
	<body>
		<div class="T-talkAbout" style="background: #f2f3f4;width: 100%; height: 100%;">
			<div class="wrapper" id="wrapper" style="margin-bottom:1rem">
				<div class="scrollbar" id="scrollbar">
				<div class="search-box">
			       <input type="text" name="search" id="search" placeholder="搜索" style="width:6.7rem;background-position-x: 2.9rem;"/>
		        </div>
		           <div class="search-list">
		          </div>
					<div id="letter"></div>
					<div class="sort_box">
					</div> 
					<div class="initials">
						<ul>
							<li><img src="${path}/resource/img/icons/068.png"></li>
						</ul>
					</div>
					<div class="M-footer active_A">
			                                         完成
		            </div>
				</div>
			</div>
		</div>
		<script src="${path}/resource/js/libs/jquery.charfirst.pinyin.js?v=${sversion}" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		 var qid = window.location.href.split("=")[1]
		 var qidStatus = window.location.href.split("=")[0].split("?")[1]//判断是否删除或添加
		 //搜索我的好友
		 var cccc = true;
		 $("#search").val('')
		 $("#search").on('focus',function(){
			 //失去焦点好友列表显示
			 $(this).css({'backgroundPositionX':'.2rem'})
			 $('.sort_box').hide();
			 $('.search-list').empty().show();
				$("#search").on("input",function(){
					   var value = $("#search").val();
					   var les = $("#search").val().length;
						if(les>0){
							//console.log(value)
							zdy_ajax({
								url: _path+"/im/m/selMyFriendList.do",
							    showLoading:false,
							    data:{
							    	'pageNum':'1',
							    	'pageSize':1000,
							    	 nickname:value,
							    	 'isAll':1
							    }, 
								success: function(data,res){
									if(res.dataobj.length == 0){
										//layer.msg("没有该好友")
										$(".search-list").empty();
										$(".search-list").html('<div style="background:#f0f0f0;font-size:0.3rem;color:#808080;text-align:center">无相关数据</div>');
									}else{
										$(".search-list").empty();
										$.each(data,function(index){
										    $(".sort_box .sort_list").each(function(k,n){
												 if(data[index].friend_user==$(this).attr('follow_user')){
												   if($(this).find('input').prop('disabled')){
												     T ='disabled'
													 /* console.log(1)  */
												    }else{
												      T = ""
												    }	
												 }
						                     }) 
						                    var honor_flag = data[index].honor_flag,tjIdImg="";
											if(honor_flag=="honor_001"){
												tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>'
											}else if(honor_flag=="honor_002"){
												tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>'
											}else if(honor_flag=="honor_003"){
												tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>'
											}else if(honor_flag=="honor_004"){
												tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>'
											}
											var str = '<div class="sort_list active_A" data_check="0" id="'+data[index].id+'" user_id="'+data[index].user_id+'" follow_user="'+data[index].friend_user+'">'+
									          '<div class="num_logo">'+
								                '<img src="'+data[index].head_portrait+'" />'+
						                      '</div>'+
						                      '<div class="num_name">'+data[index].friend_memo+'</div>'+
						                      tjIdImg+
						                      '<input type="checkbox" name="jq" id="jq" value="" '+T+' />'+
					                       '</div>'	
									 
											  $('.search-list').append(str);
											  
										});
										
										 var les = $("#search").val().length;
										 if(les == 0){
											 $(".search-list").empty();
										 }
										 $(".search-list .sort_list").on('click',function(){
											 var that=this
											/*  console.log($(this).attr('follow_user')) */
											var followUserId=$(that).attr("follow_user");
											if($(that).attr('data_check')=='0'){
												$(that).attr('data_check',1).find('input').prop('checked',true);
												 $(".sort_box .sort_list").each(function(k,n){
														/*  console.log($(this).attr('follow_user')) */
														 if($(this).attr('follow_user')==$(that).attr('follow_user')&&!$(this).find('input').prop('checked')){
															 $(this).attr('data_check',1)
															 $(this).find('input').prop('checked',true)
														 }
								                     }) 
											}else{
												$(that).attr('data_check',0).find('input').prop('checked',false);
												$(".sort_box .sort_list").each(function(k,n){
													/*  console.log($(this).attr('follow_user')) */
													 if($(this).attr('follow_user')==$(that).attr('follow_user')){
														 $(this).attr('data_check','0')
														 $(this).find('input').prop('checked',false)
													 }
							                     }) 
											}
											
											
											 //$('.search-list').hide();
											 //$('.sort_box').show()
										})
												
									}	
								},
							    error:function(e){
								   //alert(e);
							    }
							});
						}else{
							$(".search-list").empty();
						}
				}); 
				$("#search").on('blur',function(){
				   $(this).css({'backgroundPositionX':'2.7rem'})
				   if($("#search").val()==''){
					   $('.sort_box').show()
				   }
				   if($(".search-list div").html()=="无相关数据"){
					   $('.sort_box').show()
				   }
				   
			  });
				 
		   })
		  
		 zdy_ajax({
			url: _path+"/im/m/selMyFriendList.do",
		    showLoading:true,
		    data:{
		    	'pageNum':1,
		    	'pageSize':1000,
		    	'isAll':1
		    },
			success: function(data,res){
			   if(data.length==0){
			      alert_back("请添加好友在创建群聊",function(){
			        window.location.href=_path+"/wx/businessFriend/businessFriend.do"
			      })
			   }
			   console.log(data)
				var FriendUserId =[]
				$.each(data,function(index){
					FriendUserId.push(data[index].friend_user)
					var honor_flag = data[index].honor_flag,tjIdImg="";
					if(honor_flag=="honor_001"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjjr.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_002"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjyq.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_003"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjhb.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_004"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="${path}/resource/img/icons/tjfws.png"  class="tj" /></div>'
					}
					var str = '<div class="sort_list active_A" data_check="0" id="'+data[index].id+'" user_id="'+data[index].user_id+'" follow_user="'+data[index].friend_user+'">'+
						          '<div class="num_logo">'+
					                '<img src="'+data[index].head_portrait+'" />'+
			                      '</div>'+
			                      '<div class="num_name">'+data[index].friend_memo+'</div>'+
			                      tjIdImg+
			                      '<input type="checkbox" name="jq" id="jq" value="" />'+
		                       '</div>'	
				   $('.sort_box').append(str);
				});
				if(qidStatus=="qid1"){
					var arrUserId = getSessionStorageValue("jr")
				    var len = arrUserId.length
					var len1 = FriendUserId.length
					for(var k =0;k<len;k++ ){
						for(var i =0;i<len1;i++ ){
							if(arrUserId[k]==FriendUserId[i]){
								$('input[type=checkbox]').eq(i).prop('checked',true);
								$('input[type=checkbox]').eq(i).prop('disabled',true)
								 break;
							}
						}
					}	
    	    	}
				 var value = sessionStorage.getItem('qunxx');
				 var  obj = JSON.parse(value)
				 if(obj){
					 follow_id =obj.follow_id;
					 if(follow_id){
						var len = FriendUserId.length
							for(var i =0;i<len;i++ ){
								if(follow_id==FriendUserId[i]){
									$('input[type=checkbox]').eq(i).prop('checked',true);
									$('input[type=checkbox]').eq(i).prop('disabled',true)
									 break;
								}
							}
					} 
				 } 
				
				$(".sort_box .sort_list").on('click',function(){	
					if($(this).attr('data_check')==0){
						/* console.log(1) */
						if($(this).find('input').attr('disabled')!="disabled"){
							$(this).attr('data_check',1)
							$(this).find('input').prop('checked',true)
						}
					}else{
						$(this).attr('data_check',0)
						$(this).find('input').prop('checked',false)
					}
				})
				yxgz();	
			},
		    error:function(e){
			   //alert(e);
		    }
		});
		 $('.M-footer').on('click', function() {
		     var  strId = '';
		     var input= $(".sort_box .sort_list").find('input')
		     var  len = input.length;
		     $.each(input,function(index){
		    	 if(input.eq(index).prop('checked')){
				 	  var follow_user = $(this).parents('.sort_list').attr('follow_user');  
				 	  strId+=follow_user+','
		    	 }
		     });
		     if(strId){
		    	 var len = strId.length
					var str1= strId.slice(0,len-1);
		    	    /* console.log(str1) */
		    	    if(!qid){
		    	    	cjq(str1)
		    	    }else{
		    	    	if(qidStatus=="qid1"){
		    	    		/* console.log($(".sort_box .sort_list[data_check='1']").length) */
		    	    		if($(".sort_box .sort_list[data_check='1']").length){
		    	    			insertGroupUsers(str1)
		    	    		}else{
		   			    	 layer.msg("请选择好友")
		   			       }	
		    	    	}				    	    	
		    	    }
		     }else{
		    	 layer.msg("请选择好友")
		     }
	  }); 
		 //创建群
		 function cjq(str1){
			  var value = sessionStorage.getItem('qunxx');
			  var  obj = JSON.parse(value)
			  var qm =obj.qm;
			  var yes = obj.yes;
			  /* console.log(yes) */
			  var label = kkk(obj.label)
				    zdy_ajax({
						url: _path+"/im/m/insertMyGroupInfo.do",
					    showLoading:true,
					    data:{
					    	groupName:qm,
					    	/* groupDesc:qms, */
					    	groupType:yes,
					    	userIds:str1,
					    	isTop:"0",
					    	disturb:0,
					    	favIds:label
					    	//"headPortrait":"http://t1.mmonly.cc/uploads/tu/201607/175/mbdtc1zyh2s.jpg"
					    },
					    success: function(data,res){
							if(res.code==0){
								    layer.msg(res.msg,{shift: -1},function(){
									    var objId = new Object();
										objId.nickName=qm
										var objStr = JSON.stringify(objId);
										sessionStorage.setItem('user_info',objStr);
										sessionStorage.setItem('groupNo163',data.tid);
									    groupInfo(data.groupId);
								    
								    })
								    
							}	
						},
					    error:function(e){
						   //alert(e);
					    }
					}); 
		 }
		 //获取群信息
		 function groupInfo(qid){
				window.location.href=_path+"/wx/businessFriend/talkAboutQl.do?qid="+qid;
		 }
		 function kkk(label){
			 var k =''
			 $.each(label,function(i){
				 k+=label[i].id+','
				})
			 var len = k.length
				var str2= k.slice(0,len-1);
	    	   /*  console.log(str2) */
	    	    return str2
		 }
		 //邀请人
		 function insertGroupUsers(str1){
			  /* console.log(1) */
			  qid= qid.split("_")[2];
				    zdy_ajax({
						url: _path+"/im/m/insertGroupUsers.do",
					    showLoading:true,
					    data:{
					    	 groupId:qid,
					    	 userIds:str1,	    	
					    },
					    success: function(data,res){
							layer.msg(res.msg,{shift: -1},function(){
							 window.location.href=_path+"/wx/businessFriend/talkAboutQl.do?qid="+qid;
							})
							
						},
					    error:function(e){
						   //alert(e);
					    }
					}); 
		 }	 	 
		 //运行工总好
		 function yxgz(){
			  var Initials = $('.initials');
				var LetterBox = $('#letter');
				Initials.find('ul').append('<li>A</li><li>B</li><li>C</li><li>D</li><li>E</li><li>F</li><li>G</li><li>H</li><li>I</li><li>J</li><li>K</li><li>L</li><li>M</li><li>N</li><li>O</li><li>P</li><li>Q</li><li>R</li><li>S</li><li>T</li><li>U</li><li>V</li><li>W</li><li>X</li><li>Y</li><li>Z</li><li>#</li>');
				initials();

				$(".initials ul li").click(function() {
					var _this = $(this);
					var LetterHtml = _this.html();
					LetterBox.html(LetterHtml).fadeIn();

					Initials.css('background', 'rgba(145,145,145,0.6)');

					setTimeout(function() {
						Initials.css('background', 'rgba(145,145,145,0)');
						LetterBox.fadeOut();
					}, 1000);

					var _index = _this.index()
					if (_index == 0) {
						$('html,body').animate({
							scrollTop: '0px'
						}, 300); //点击第一个滚到顶部
					} else if (_index == 27) {
						var DefaultTop = $('#default').position().top;
						$('html,body').animate({
							scrollTop: DefaultTop + 'px'
						}, 300); //点击最后一个滚到#号
					} else {
						var letter = _this.text();
						if ($('#' + letter).length > 0) {
							var LetterTop = $('#' + letter).position().top;
							$('html,body').animate({
								scrollTop: LetterTop - 45 + 'px'
							}, 300);
						}
					}
				})
				var windowHeight = $(window).height();
				var InitHeight = windowHeight - 45;
				Initials.height(InitHeight);
				var LiHeight = InitHeight / 28;
				Initials.find('li').height(LiHeight);
				
		 }
		 
		function initials() { //公众号排序
			var SortList = $(".sort_list");
			var SortBox = $(".sort_box");
			SortList.sort(asc_sort).appendTo('.sort_box'); //按首字母排序
			function asc_sort(a, b) {
				return makePy($(b).find('.num_name').text().charAt(0))[0].toUpperCase() < makePy($(a).find('.num_name').text().charAt(0))[0].toUpperCase() ? 1 : -1;
			}

			var initials = [];
			var num = 0;
			SortList.each(function(i) {
				var initial = makePy($(this).find('.num_name').text().charAt(0))[0].toUpperCase();
				if (initial >= 'A' && initial <= 'Z') {
					if (initials.indexOf(initial) === -1)
						initials.push(initial);
				} else {
					num++;
				}

			});

			$.each(initials, function(index, value) { //添加首字母标签
				SortBox.append('<div class="sort_letter" id="' + value + '">' + value + '</div>');
			});
			if (num != 0) {
				SortBox.append('<div class="sort_letter" id="default">#</div>');
			}

			for (var i = 0; i < SortList.length; i++) { //插入到对应的首字母后面
				var letter = makePy(SortList.eq(i).find('.num_name').text().charAt(0))[0].toUpperCase();
				switch (letter) {
					case "A":
						$('#A').after(SortList.eq(i));
						break;
					case "B":
						$('#B').after(SortList.eq(i));
						break;
					case "C":
						$('#C').after(SortList.eq(i));
						break;
					case "D":
						$('#D').after(SortList.eq(i));
						break;
					case "E":
						$('#E').after(SortList.eq(i));
						break;
					case "F":
						$('#F').after(SortList.eq(i));
						break;
					case "G":
						$('#G').after(SortList.eq(i));
						break;
					case "H":
						$('#H').after(SortList.eq(i));
						break;
					case "I":
						$('#I').after(SortList.eq(i));
						break;
					case "J":
						$('#J').after(SortList.eq(i));
						break;
					case "K":
						$('#K').after(SortList.eq(i));
						break;
					case "L":
						$('#L').after(SortList.eq(i));
						break;
					case "M":
						$('#M').after(SortList.eq(i));
						break;
					case "O":
						$('#O').after(SortList.eq(i));
						break;
					case "P":
						$('#P').after(SortList.eq(i));
						break;
					case "Q":
						$('#Q').after(SortList.eq(i));
						break;
					case "R":
						$('#R').after(SortList.eq(i));
						break;
					case "S":
						$('#S').after(SortList.eq(i));
						break;
					case "T":
						$('#T').after(SortList.eq(i));
						break;
					case "U":
						$('#U').after(SortList.eq(i));
						break;
					case "V":
						$('#V').after(SortList.eq(i));
						break;
					case "W":
						$('#W').after(SortList.eq(i));
						break;
					case "X":
						$('#X').after(SortList.eq(i));
						break;
					case "Y":
						$('#Y').after(SortList.eq(i));
						break;
					case "Z":
						$('#Z').after(SortList.eq(i));
						break;
					default:
						$('#default').after(SortList.eq(i));
						break;
				}
			};
		}

		</script>
	</body>

</html>
