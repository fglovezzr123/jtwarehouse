package com.wing.socialcontact.service.wx.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IInvestmentIntentionService;
import com.wing.socialcontact.service.wx.bean.InvestmentIntention;
import com.wing.socialcontact.service.wx.dao.InvestmentIntentionDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.POIUtil;
/**
 * 投资意向管理
 * @author zhangzheng
 *
 */
@Service
public class InvestmentIntentionServiceImpl extends BaseServiceImpl<InvestmentIntention>
		implements IInvestmentIntentionService {
		private static final String cache_name = "\"DB:InvestmentIntention:\" + ";
	
		private static final int MAX_SIZE=100;
	@Resource
	private InvestmentIntentionDao InvestmentIntentionDao;
	
	/**
	 * 获取投资意向列表
	 */
	@Override
	public Object selectinvestmentintention(PageParam param,
			InvestmentIntention investmentIntention,Date stime,Date etime) {
		DataGrid data=new DataGrid();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("status", investmentIntention.getStatus());
		parm.put("className", investmentIntention.getClassName());
		parm.put("stime", stime);
		parm.put("etime", etime);
		List<Map<String,Object>> lst=InvestmentIntentionDao.getintentionMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	/**
	 * 添加
	 */
	@Override
	public boolean  addInvestment(InvestmentIntention dto) {
		
		int res = InvestmentIntentionDao.insert(dto);
		if (res>0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据用户id获取投资意向列表
	 */
	@Override
	public List<Map> getinvestmentList(String userId,Integer page,Integer size) {
		
		Map parm = new HashMap();
		parm.put("userId", userId);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		return InvestmentIntentionDao.getinvestmentList(parm);
	}

	/**
	 * 更新
	 */
	@Override
	public String updateInvestmentIntention(InvestmentIntention dto) {
		if(super.updateByPrimaryKeyCache(dto,dto.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	/**
	 * 根据id获取详情
	 */
	@Override
	public InvestmentIntention getInvestmentIntentionByid(String id) {
		return super.selectByPrimaryKeyCache(id, InvestmentIntention.class);
	}

	/**
	 * 导出报表数据查询
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> exportinvestmentintention(String className1, int status1,
			Date stime1, Date etime1)  {
		
		Map parm = new HashMap();
		parm.put("status", status1);
		parm.put("className", className1);
		parm.put("stime", stime1);
		parm.put("etime", etime1);
		List<Map<String,Object>> lst=InvestmentIntentionDao.getintentionMap(parm);
		
		return lst;
		
	}

}
