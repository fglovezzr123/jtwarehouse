package com.tojoy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtil {

	public static Map<String, Object> parseJSON2Map(String rs) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONParser parser = new JSONParser();
		// 最外层解析
		JSONObject json;
		try {
			json = (JSONObject) parser.parse(rs);
			for (Object k : json.keySet()) {
				Object v = json.get(k);
				// 如果内层还是数组的话，继续解析
				if (v instanceof JSONArray) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Iterator it = ((JSONArray) v).iterator();
					while (it.hasNext()) {
						Object obj = it.next();
						if (obj instanceof JSONObject) {
							JSONObject json2 = (JSONObject) obj;
							list.add(parseJSON2Map(json2.toString()));
						}
					}
					if(list.size() == 0){
						map.put(k.toString(), v);
					}else{
						map.put(k.toString(), list);
					}
				} else {
					map.put(k.toString(), v);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	public static void main(String[] args) {
		String rs="{\"openid\":\"oBhgswpjxXHZgGTq92C-AycL5brI\",\"nickname\":\"乐在其中\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"深圳\",\"province\":\"广东\",\"country\":\"中国\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmopen\\/vi_32\\/x1N6TWiapBwpO7ubeNhJ5bFA0ia1sia9uZfiaUb4AqouVXyrnSk002ma7ciaAdZ0PqLazFslNYe6Poow2kzA9Oog98w\\/0\",\"privilege\":[\"1111\"]}";
		Map<String, Object> rsmap = JsonUtil.parseJSON2Map(rs);
		System.out.println(rsmap.get("privilege"));
	}

}
