<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="resource/js/mobiscroll-master/js/mobiscroll.core.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.scroller.js"></script>

<script src="resource/js/mobiscroll-master/js/mobiscroll.util.datetime.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.datetimebase.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.datetime.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.select.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.listbase.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.image.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.treelist.js"></script>

<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.android.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.android-holo.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.ios-classic.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.ios.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.jqm.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.sense-ui.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.frame.wp.js"></script>

<script src="resource/js/mobiscroll-master/js/mobiscroll.android-holo-light.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.wp-light.js"></script>
<script src="resource/js/mobiscroll-master/js/mobiscroll.mobiscroll-dark.js"></script>
<!-- 
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.cs.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.de.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.en-UK.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.es.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.fa.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.fr.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.hu.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.it.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.ja.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.nl.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.no.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.pl.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.pt-BR.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.pt-PT.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.ro.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.ru.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.ru-UA.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.sk.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.sv.js"></script>
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.tr.js"></script>
-->
<script src="resource/js/mobiscroll-master/js/i18n/mobiscroll.i18n.zh.js"></script>
<link href="resource/js/mobiscroll-master/css/mobiscroll.animation.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.icons.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.android.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.android-holo.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.ios-classic.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.ios.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.jqm.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.sense-ui.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.frame.wp.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.android.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.android-holo.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.ios-classic.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.ios.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.jqm.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.sense-ui.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.scroller.wp.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.image.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.android-holo-light.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.wp-light.css" rel="stylesheet" type="text/css" />
<link href="resource/js/mobiscroll-master/css/mobiscroll.mobiscroll-dark.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
<!--
var mobile_type="ios";
var UA = navigator.userAgent;
if(!UA.match(/iPad/) && !UA.match(/iPhone/) && !UA.match(/iPod/)){
	mobile_type="android";
}
//-->
</script>
