var myIdstatus=false;
$(document).ready(function(){
	search.style.backgroundPositionX="2.5rem";
	zdy_ajax({
		url: _path+"/m/sys/getTjyUserByid.do",
	    showLoading:false,
	    data:{"uid":localStorage.getItem("currentUserId")},
		success: function(data,res){
		      if(data.reconStatus!=2){
		    	  $('#reconSH').hide();
		      }else if(data.reconStatus==2){
		    	  $('#reconSH').show();
		    	  myIdstatus=true;
		      }
		      
		}
	});
	//最近联系人目录  
	var refreshLater = JSON.parse(localStorage.getItem("recentContacts"));
	console.log(refreshLater);
	//页面跳转
	  $('#search').click(function(){
		  if(myIdstatus){
			  window.location.href = _path+ "/wx/businessFriend/searchMyFriend.do";
			}else{
				window.location.href=_path+"/wx/commons/to_recon.do";
			}
      })
	//创建群聊
	$('.icon-add').on('click', function(event) {
		 event.stopPropagation();//阻止冒泡行为
		if($(this).hasClass('on')){
			$(this).removeClass('on')
			$('.hided').fadeOut(200)
		}else{
			$(this).addClass('on')
			$('.hided').fadeIn(200)
			//hided 出现禁止滑动
			$('.zhezhao').show()
			
		}
		$('.hided span').on('click',function(){
			if($(this).html() == "创建群聊"){
				window.location.href = _path+"/wx/businessFriend/szq.do?kkkk=3333"
			}else if($(this).html() == "添加好友"){
				if(navigator.onLine){
					$.ajax({
						url: _path+'/m/sys/is_recon.do?s='+Date.parse(new Date()),
						type: 'post',
						dataType: 'json',
						showLoading:false,
						success: function(data){
							if(data.code == 600){
								confirm("您还未认证，请认证后继续此操作。 是否马上认证？",function(t){
									if(t == 1){
										self.location.href=_path+"/m/my/reconPage.do";
									}else{
										if(isEmpty(document.referrer)){//不存在父页面
											self.location.href=_path+"/m/sys/index.do";
										}else{
										}
									}
								});
							}else{
								localStorage.setItem("currentUserId","<%=userid %>");
								window.location.href= _path+"/wx/businessFriend/addFriend.do";
							}
						}
					});
				}else{
						offlinedeal();
				}
			
			}else{
				window.location.href= _path+"/wx/businessFriend/serqun.do";
			}
			$('.hided').hide()
		})
		
	});
	//点击页面  hided 隐藏
	$('.zhezhao').on('touchstart',function(){	
		$('.icon-add').removeClass('on')
		$('.hided').fadeOut(200,function(){
			$('.zhezhao').hide()
		});
	})
	$('.myF').on('click', function() {
		if(myIdstatus){
			 window.location.href=_path+"/wx/businessFriend/myFoucs.do";
		}else{
			window.location.href=_path+"/wx/commons/to_recon.do";
		}
		
	});
   //新好友数量
	zdy_ajax({
		url: _path+"/im/m/selMyNewFriendCount.do",
	    showLoading:false,
		success: function(data,res){
			//console.log("dd")
			if(data){
				$('.info .info-r').find('span').css({"display":"block"})
				$('.info .info-r').find('span').html(data);
			}
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	$('.newF').on('click', function() {
		//http://localhost:8080/wxfront/
		
		 if(myIdstatus){
			 window.location.href=_path+"/wx/businessFriend/newFriend.do";
		}else{
			window.location.href=_path+"/wx/commons/to_recon.do";
		}
	});
	/*我的粉丝数量  */
	zdy_ajax({
		url: _path+"/im/m/selMyFansCount.do",
	    showLoading:false,
		success: function(data,res){
			//console.log(data)
			$('.myFoucs .myfoucs-l').find('span>i').html('我的粉丝('+data+')&nbsp;');
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	/*我的关注数量  */
	zdy_ajax({
		url: _path+"/im/m/selMyFollowCount.do",
	    showLoading:false,
		success: function(data,res){
			$('.myFoucs .myfoucs-l').find('span>em').html('/&nbsp;我的关注('+data+')');	
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	//我的群组数量
	zdy_ajax({
		url: _path+"/im/m/selMyGroupInfoList.do",
	    showLoading:false,
	    data:{
	    	'pageNum':1,
	    	'pageSize':100
	    },
		success: function(data,res){
			 //console.log(data)
			 $(".myQz").find(".p-name").html('我的群组('+data.groupInfoList.length+')');
			 $('.myQz').on('click', function() {
				 if(myIdstatus){
					 window.location.href=_path+"/wx/businessFriend/myTalk.do";
				}else{
					window.location.href=_path+"/wx/commons/to_recon.do";
				}
			  
			});
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	//星标好友
	zdy_ajax({
		url: _path+"/im/m/selMyFriendList2.do",
	    showLoading:false,
	    data:{
	    	'pageNum':0,
	    	'pageSize':0
	    },
		success: function(data,res){
			console.log(data);
			if(data.length == 0){
				$('.foucsF').css({"display":"none"});
			}else{
				$('.foucsF').css({'display':'block'});
				$.each(data,function(index){
				  if(refreshLater){	
					if(refreshLater[data[index].friend_user]){
						refreshLater[data[index].friend_user]["lastMsg"]["fps"]=data[index].head_portrait;
						refreshLater[data[index].friend_user]["lastMsg"]["fns"]=data[index].friend_memo;
						localStorage.setItem("recentContacts",JSON.stringify(refreshLater));
					};
				  }
				  var honor_flag =data[index].honor_flag,tjIdImg="";
					if(honor_flag=="honor_001"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjjr.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_002"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjyq.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_003"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjhb.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_004"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjfws.png"  class="tj" /></div>'
					}
					var str = ' <div class="myFoucs fF active_A" user_id="'+data[index].id+'" follow_user="'+data[index].friend_user+'">'+
						   		'<div class="myfoucs-l">'+
						   			'<img src="'+data[index].head_portrait+'" class="p-img p-imgS"/>'+
						   			'<span>'+data[index].friend_memo+'</span>'+
						   		  tjIdImg+
						   		'</div>'+
					   		   '<i class="iconfont mg">&#xe60f;</i>'+
					        '</div>';
					
				   $('.foucsF').append(str);
				});
			}
			 $('.foucsF .fF').on('click', function() {
				 	  var follow_user = $(this).attr('follow_user');
				      window.location.href=_path+"/wx/businessFriend/friendInfo.do?follow_user="+follow_user;
			 });
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	
	//我的好友列表
	zdy_ajax({
		url: _path+"/im/m/selMyFriendList1.do",
	    showLoading:false,
	    data:{
	    	'pageNum':1,
	    	'pageSize':50,
	    	'isAll':1
	    },
		success: function(data,res){
			console.log(res);
			$.each(data,function(index){
			  if(refreshLater){	
				if(refreshLater[data[index].friend_user]){
					refreshLater[data[index].friend_user]["lastMsg"]["fps"]=data[index].head_portrait;
					refreshLater[data[index].friend_user]["lastMsg"]["fns"]=data[index].friend_memo;
					localStorage.setItem("recentContacts",JSON.stringify(refreshLater));
				};
			  }
			  
			        var honor_flag = data[index].honor_flag,tjIdImg="";
					if(honor_flag=="honor_001"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjjr.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_002"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjyq.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_003"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjhb.png"  class="tj" /></div>'
					}else if(honor_flag=="honor_004"){
						tjIdImg = '<div style="flex: 1; margin-left: .1rem;"><img src="'+_path+'/resource/img/icons/tjfws.png"  class="tj" /></div>'
					}
			  
				var str = '<div class="sort_list active_A" id="'+data[index].id+'" user_id="'+data[index].user_id+'" follow_user="'+data[index].friend_user+'">'+
					          '<div class="num_logo">'+
				                '<img src="'+data[index].head_portrait+'" />'+
		                      '</div>'+
		                      '<div class="num_name">'+data[index].friend_memo+'</div>'+
		                      tjIdImg+
	                       '</div>';
				
			   $('.sort_box').append(str);
			});
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
			 $('.sort_list').on('click', function() {
				 	  var follow_user = $(this).attr('follow_user');
				 	 window.location.href=_path+"/wx/businessFriend/friendInfo.do?follow_user="+follow_user;
			 });
			
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	
});

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

function offlinedeal(){
	layer.open({
		type : 1,
		//skin: 'layui-layer-lan',
		title: false,
	    closeBtn: 0, //不显示关闭按钮
		fix : true,
		shadeClose : true,
		maxmin : false,
		area : [ '100%', '100%' ],
		content : '<div class="wrapper1"><div class="right-signal1">网络异常，请检查手机网络</div><div class="active_A name-btn1" onclick="reloadjsp()">刷新</div></div>',
		shift : 2,
		scrollbar : false,
		success : function(layero, index) {
		},
		end : function() {
		},
		cancel : function(cancel) {
		}
	});
}
