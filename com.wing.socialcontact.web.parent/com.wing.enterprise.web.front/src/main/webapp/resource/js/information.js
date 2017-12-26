$(document).ready(function(){
	$('.title .title-t').on('click', function() {
		var index = $(this).index();
		$(this).addClass('title-t-active').siblings().removeClass('title-t-active')
	})
	//swiper初始化
	var mySwiper = new Swiper('.swiper-container', {
	autoplay: 5000,//可选选项，自动滑动
	pagination : '.swiper-pagination'
    })
})
