/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

KindEditor.plugin('preview', function(K) {
	var self = this, name = 'preview', undefined;
	self.clickToolbar(name, function() {
		var lang = self.lang(name + '.'),
			html = '' +
				'<iframe frameborder="0" style="width:320px;height:500px;"></iframe>' +
				'',
			dialog = self.createDialog({
				name : name,
				width : 320,
				title : self.lang(name),
				body : html
			}),
			iframe = K('iframe', dialog.div),
			doc = K.iframeDoc(iframe);
		doc.open();
		var htmlstr =   
//						'<!DOCTYPE html>'+ 
//						'<html> '+
//						'<head> '+
//						'<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0"/>'+
//						'<meta name="format-detection" content="telephone=no"/>'+
//						'<meta name="apple-mobile-web-app-capable" content="yes" />'+
//						'<title></title>'+
//						'<style type="text/css">'+
//						'body { '+
//						'	background:#red;'+
//						'	color:#666;'+
//						'	margin: 0px; font-size:14px;'+
//						'	line-height:30px;'+
//						'}'+
//						'img{width:100%}'+
//						'.content{margin:10px;}'+
//						'</style>'+
//						'</head>'+
//						'<body>'+
						'	<div style="width:100%">'+
						'		<div style="" style="margin:10px;" class="content">'+self.fullHtml()+'</div>'+
						'    </div>';
//						'</body>'+
//						'</html>';
		doc.write(htmlstr);
		doc.close();
		K(doc.body).css('background-color', '#FFF');
		K(doc.body).css('color', '#666');
		K(doc.body).css('font-size', '13px');
		K(doc.body).css('line-height', '20px'); 
		K(doc.body).css('margin', '0'); 
		iframe[0].contentWindow.focus();
	});
});
