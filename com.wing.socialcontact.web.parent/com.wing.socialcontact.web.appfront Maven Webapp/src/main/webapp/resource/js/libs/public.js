$(document).ready(function() {
	var deviceWidth = document.documentElement.clientWidth;
	
		document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
});

function imgReplaceStyle(imgpath,style){
	if(imgpath.indexOf('?')>-1){
		imgpath=imgpath.substring(0,imgpath.indexOf('?'));
	}
	return imgpath+'?x-oss-process=style/'+style;
}