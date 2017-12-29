$(document).ready(function(){
	$('.tlist span').on('click', function() {
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
		console.log(m)
		if(m>=200){
			$(".tlist").addClass("fixed");
		}else{
			$(".tlist").removeClass("fixed");
		}
	})
})
