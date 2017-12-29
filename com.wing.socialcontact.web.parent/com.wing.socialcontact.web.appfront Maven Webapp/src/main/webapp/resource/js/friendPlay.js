$(document).ready(function(){
    /*置顶效果*/
    $(window).scroll(function(){
		var m = $(document).scrollTop();//判断滑动高度
		console.log(m)
		if(m>=200){
			$(".tlist").css({marginTop:'0'})//置顶部分是否与上部分有间距
			$(".tlist").addClass("fixed");
		}else{
			$(".tlist").css({marginTop:'.08rem'})//置顶部分 间距复原
			$(".tlist").removeClass("fixed");
		}
	})
})
