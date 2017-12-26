
	$('.content span').on('click', function() {
		var index = $(this).index();
		$(this).addClass('show-active').siblings().removeClass('show-active')
	})
	//swiper初始化
	var mySwiper = new Swiper('.swiper-container', {
	   autoplay: 5000,//可选选项，自动滑动
	   pagination : '.swiper-pagination'
    })
	$(window).scroll(function(){
		var m = $(document).scrollTop();
		var n = $('.tlist').offset().top
		console.log(m)
		console.log(n)
		if(m>=200){
			$(".tlist").css({marginTop:'0'})
			$(".tlist").addClass("fixed");
		}else{
			$(".tlist").css({marginTop:'.08rem'})
			$(".tlist").removeClass("fixed");
		}
	})
	/*$(window).scroll(function(){
		 menuPosition()
	})
	 function menuPosition() {
        var m = $(window).scrollTop(), n = $(".tlist").offset().top,l = $(".tlist");
        console.log(m)
        console.log( n)
        if (m >=n) {
            if (!l.hasClass("fixed")) {
            	l.css({marginTop:'0'})
                l.addClass("fixed")
            }
        } else {
        	l.css({marginTop:'.08rem'})
            l.removeClass("fixed")
        }
    }
*/