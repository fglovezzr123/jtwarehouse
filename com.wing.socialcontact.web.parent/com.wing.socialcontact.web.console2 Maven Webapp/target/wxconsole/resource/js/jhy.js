//展示模板下拉选项配置
var show_model_json = [
	{"key":"1","value":"模板一"},
	{"key":"2","value":"模板二"},
	{"key":"3","value":"模板三"},
	{"key":"4","value":"模板四"},
	{"key":"5","value":"模板五"},
	{"key":"6","value":"模板六"},
	{"key":"7","value":"模板七"}
];

//动态栏目元素内容配置
var element_content_json = [
	{"key":"meetingRecommend","value":"峰会推荐"},
	{"key":"projectRecommend","value":"精选项目"},
	{"key":"projectList","value":"项目联营"},
	{"key":"explosiveRecommend","value":"爆品推荐"},
	{"key":"mutualGroup","value":"互助团购"},
	{"key":"zhuGeExplanation","value":"诸葛解惑"},
	{"key":"personalCustomization","value":"个性定制"},
	{"key":"starProject","value":"明星项目"},
	{"key":"investMarket","value":"项目超市"},
    {"key":"commonGoal","value":"志同道合"},
    {"key":"friendsMien","value":"商友风采"},
    {"key":"twoConnections","value":"二度人脉"},
    {"key":"connectionsRecommend","value":"人脉推荐"},
    {"key":"bossVoice","value":"老板悄悄话"},
    {"key":"onlineClass","value":"在线课程"},
    {"key":"seminar","value":"首页专题"},
    {"key":"financialMarket","value":"金融超市"},
    {"key":"fundInstitution","value":"融资机构"}
];

/**
 * 根据key返回value，列表中使用
 * type:1展示模板2元素内容
 */
var getValueByKey = function(type,key){
	var result = "";
	if(type == 1){
		for(var i = 0;i < show_model_json.length; i++){
			if(key == show_model_json[i].key){
				result = show_model_json[i].value;
				break;
			}
		}
	}else{
		for(var i = 0;i < element_content_json.length; i++){
			if(key == element_content_json[i].key){
				result = element_content_json[i].value;
				break;
			}
		}
	}
	return result;
}

var getOptionsByType = function(type){
	var result = "<option value='0'>请选择</option>";
	if(type == 1){
		for(var i = 0;i < show_model_json.length; i++){
			var obj = show_model_json[i];
			result += "<option value='"+obj.key+"'>"+obj.value+"</option>";
		}
	}else{
		for(var i = 0;i < element_content_json.length; i++){
			var obj = element_content_json[i];
			result += "<option value='"+obj.key+"'>"+obj.value+"</option>";
		}
	}
	return result;
}