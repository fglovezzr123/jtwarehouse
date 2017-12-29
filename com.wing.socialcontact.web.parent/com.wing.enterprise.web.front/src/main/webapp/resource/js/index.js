$(document).ready(function() {
	$('.dc .dc-t span').on('click', function() {
		var index = $(this).index();
		$(this).addClass('dc-t-active').siblings().removeClass('dc-t-active');
	});
	$('.dc-news li').on('click', function() {
		var index = $(this).index();
		$(this).addClass('dc-news-active').siblings().removeClass('dc-news-active');
		$('.ul-hide li').eq(index).addClass('show-active').siblings().removeClass('show-active');
	});
});