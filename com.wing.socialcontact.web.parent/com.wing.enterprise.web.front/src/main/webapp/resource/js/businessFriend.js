$(document).ready(function(){
	//创建群聊
	$('.icon-add').on('click', function() {
		$('.hided').fadeIn()
		$('.hided span').on('click',function(){
			if($(this).html() == "创建群聊"){
				window.location.href = _path+"/wx/businessFriend/szq.do"
			}else{
				window.location.href= _path+"/wx/businessFriend/addFriend.do";
			}
		})
	});		
	$('.myF').on('click', function() {
		 window.location.href=_path+"/wx/businessFriend/myFoucs.do";
	});
   //新好友数量
	zdy_ajax({
		url: _path+"/im/m/selMyNewFriendCount.do",
	    showLoading:false,
		success: function(data,res){
			$('.info .info-r').find('span').html(data);
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	$('.newF').on('click', function() {
		//http://localhost:8080/wxfront/
		 window.location.href=_path+"/wx/businessFriend/newFriend.do";
	});
	/*我的粉丝数量  */
	zdy_ajax({
		url: _path+"/im/m/selMyFansCount.do",
	    showLoading:false,
		success: function(data,res){
			console.log(data)
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
			   window.location.href=_path+"/wx/businessFriend/myTalk.do";
			});
		},
	    error:function(e){
		   //alert(e);
	    }
	});
	//心标好友
	zdy_ajax({
		url: _path+"/im/m/selTopFriendListByUserId.do",
	    showLoading:true,
	    data:{
	    	'pageNum':1,
	    	'pageSize':20
	    },
		success: function(data,res){
			console.log(res);
			if(data.length == 0){
				/*$('.foucsF').remove();*/
			}else{
				$('.foucsFriend').html("心标好友")
				$.each(data,function(index){
					var str = ' <div class="myFoucs fF active_A" user_id="'+data[index].subject_id+'" follow_user="'+data[index].relat_user+'">'+
						   		'<div class="myfoucs-l">'+
						   			'<img src="'+data[index].head_portrait+'" class="p-img p-imgS"/>'+
						   			'<span>'+data[index].friend_memo+'</span>'+
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
		url: _path+"/im/m/selMyFriendList.do",
	    showLoading:true,
	    data:{
	    	'pageNum':1,
	    	'pageSize':50,
	    	'isAll':0
	    },
		success: function(data,res){
			console.log(res);
			$.each(data,function(index){
				var str = '<div class="sort_list active_A" id="'+data[index].id+'" user_id="'+data[index].user_id+'" follow_user="'+data[index].friend_user+'">'+
					          '<div class="num_logo">'+
				                '<img src="'+data[index].head_portrait+'" />'+
		                      '</div>'+
		                      '<div class="num_name">'+data[index].friend_memo+'</div>'+
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
