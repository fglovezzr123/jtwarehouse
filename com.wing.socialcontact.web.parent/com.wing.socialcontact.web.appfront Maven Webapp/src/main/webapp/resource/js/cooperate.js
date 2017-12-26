
	$('.content span').on('click', function() {
		var index = $(this).index();
		$(this).addClass('show-active').siblings().removeClass('show-active')
	})
	$(window).scroll(function(){
		var m = $(document).scrollTop();
		var n = $('.tlist').offset().top
		if(m>=200){
			$(".tlist").css({marginTop:'0'})
			$(".tlist").addClass("fixed");
		}else{
			$(".tlist").css({marginTop:'.08rem'})
			$(".tlist").removeClass("fixed");
		}
	})
	