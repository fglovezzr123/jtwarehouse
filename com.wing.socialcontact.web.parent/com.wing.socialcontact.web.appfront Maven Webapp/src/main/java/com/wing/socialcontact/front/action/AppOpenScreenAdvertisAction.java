package com.wing.socialcontact.front.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IOpenScreenAdvertisService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.UserAppointmentRecord;
import com.wing.socialcontact.util.StringUtil;
import com.wing.socialcontact.util.UUIDGenerator;
/**
 * app开屏广告
 * 
 * @param
 * @return
 */
@Controller
@RequestMapping("/m/app/open")
public class AppOpenScreenAdvertisAction extends BaseAppAction
{
	@Autowired
	private IOpenScreenAdvertisService openScreenAdvertisService;
	
	@RequestMapping("openScreenAdvertis")
	public @ResponseBody ResponseReport getOpenScreenAdvertis(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		//获取分辨率
		String resolvingPowerWidth = rr.getDataValue("resolvingPowerWidth");
		String resolvingPowerHeight = rr.getDataValue("resolvingPowerHeight");
		if (StringUtil.isEmpty(resolvingPowerWidth))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		if (StringUtil.isEmpty(resolvingPowerHeight))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", null);
		}
		long resolvingPower = Integer.parseInt(resolvingPowerWidth)*Integer.parseInt(resolvingPowerHeight);
		//List resolvingPowerList = new ArrayList();
		//获取已启用的数据
		List list = openScreenAdvertisService.selectAdvertisList();
		String str[]=new String[list.size()];
		for(int i=0;i<list.size();i++) {
			Map map = (HashMap)list.get(i);
			String resolvingpower = map.get("resolvingpower")==null?"":map.get("resolvingpower").toString();
			//resolvingPowerList.add(resolvingpower);
			str[i] = resolvingpower+"*"+map.get("id").toString();
		}
		//冒泡升序排列
		for(int i = 0;i<str.length-1;i++){
			for(int j = 0;j<str.length-1-i;j++){
				String s[] = str[j].split("\\*");
				long rlp = Integer.parseInt(s[0])*Integer.parseInt(s[1]);
				String s1[] = str[j+1].split("\\*");
				long rlp1 = Integer.parseInt(s1[0])*Integer.parseInt(s1[1]);
				if(Math.abs(rlp-resolvingPower)>Math.abs(rlp1-resolvingPower)){
					String temp = str[j];
					str[j] = str[j+1];
					str[j+1] = temp;
				}
			}
		}
		Map relMap = new HashMap();
		String id = str[0].split("\\*")[2];
		for(int i=0;i<list.size();i++) {
			Map map = (HashMap)list.get(i);
			if(map.get("id").toString().equals(id)) {
				relMap.put("id", id);
				relMap.put("img_url", map.get("img_url")==null?"":map.get("img_url").toString());
				relMap.put("img_name", map.get("img_name")==null?"":map.get("img_name").toString());
				relMap.put("img_link", map.get("img_link")==null?"":map.get("img_link").toString());
				relMap.put("STATUS", map.get("STATUS")==null?"":map.get("STATUS").toString());
				relMap.put("create_user_id", map.get("create_user_id")==null?"":map.get("create_user_id").toString());
				relMap.put("create_time", map.get("create_time")==null?"":map.get("create_time").toString());
				relMap.put("order_num", map.get("order_num")==null?"":map.get("order_num").toString());
				relMap.put("resolvingpower", map.get("resolvingpower")==null?"":map.get("resolvingpower").toString());
			}
		}
		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", relMap);
	}
	
}
