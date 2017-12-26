package com.wing.socialcontact.front.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.tojoy.service.wx.api.IAppointmentPayService;
import com.tojoy.service.wx.bean.AppointmentPay;
import com.tojoycloud.common.report.base.BaseAppAction;
/**
 * im回调
 * */
@Controller
@RequestMapping("m/app/app/iMCallBackAction")
public class AppointmentPayIMCallBackAction extends BaseAppAction
{
	
	@Autowired
	private IAppointmentPayService appointmentPayService;
	
	
	@RequestMapping("iMCallBack")
	@ResponseBody
	public String iMCallBack(HttpServletRequest request,HttpServletResponse response) {
		try
		{
			
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String s = "";
			while((s=br.readLine())!=null){
			    sb.append(s);
			}
			if(sb.toString().length()>0){
				Gson gson = new Gson();
				Map<String, Object> map = new HashMap<String, Object>();
				map = gson.fromJson(sb.toString(), map.getClass());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//开始时间
				String start_time = map.get("start_time")==null?"":map.get("start_time").toString();
				//结束时间
				String duration = map.get("duration")==null?"":map.get("duration").toString();
				//结束时间
				String actualEnd_time = map.get("actualEnd_time")==null?"":map.get("actualEnd_time").toString();
				//id
				String id = map.get("id")==null?"":map.get("id").toString();
				AppointmentPay appointmentPay = new AppointmentPay();
				appointmentPay.setStart_Time(sdf.parse(start_time));
				appointmentPay.setDuration(Long.parseLong(duration));
				appointmentPay.setActualEnd_Time(sdf.parse(actualEnd_time));
				appointmentPay.setId(Long.parseLong(id));
				int count = appointmentPayService.updateAppointmentPay(appointmentPay);
				if(count>0)
					return "成功";
				else
					return "失败！";
	        }else {
	            return "失败！";
	        }
		}
		catch (IOException e1)
		{
			return "失败！";
		}catch (ParseException e)
		{
			return "失败！";
		}
	}
}
