package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.ReportDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IReportService;
import com.wing.socialcontact.service.wx.bean.Report;

/**
 * 
 * @author zhangfan
 * @date 2017-03-30 15:05:41
 * @version 1.0
 */
@Service
public class ReportServiceImpl extends BaseServiceImpl<Report> implements IReportService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:report:\" + ";
	
	@Resource
	private ReportDao reportDao;

	@Override
	public DataGrid selectAllTopicReport(PageParam param, String title,String type,String startTime,String endTime,String isShow) {
		DataGrid data=new DataGrid();
		List lst = null;
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("title", title);
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("isShow", isShow);
		parm.put("orderStr", orderStr);
		if(type=="1"||"1".equals(type)){//话题
			lst = reportDao.selectTopicReportByParam(parm);
		}
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addReport(Report report) {
		int res = reportDao.insert(report);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		
	}

	@Override
	public String updateReport(Report report) {
		if(super.updateByPrimaryKeyCache(report,report.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteReport(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Report r=selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Report.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public Report selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Report.class);
	}

	@Override
	public Report selectById(String id) {
		return reportDao.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> selectReportById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = reportDao.selectReportById(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}

}