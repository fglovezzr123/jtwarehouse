package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.RewardAnswerDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IRewardAnswerService;
import com.wing.socialcontact.service.wx.bean.RewardAnswer;

/**
 * 
 * @author zhangfan
 * @date 2017-06-14 06:47:31
 * @version 1.0
 */
@Service
public class RewardAnswerServiceImpl extends BaseServiceImpl<RewardAnswer> implements IRewardAnswerService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:RewardAnswer:\" + ";;
	
	@Resource
	private RewardAnswerDao rewardAnswerDao;

	@Override
	public String addRA(RewardAnswer ra) {
		int res = rewardAnswerDao.insert(ra);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateRA(RewardAnswer ra) {
		if(super.updateByPrimaryKeyCache(ra,ra.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteRAs(String[] ids) {
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					RewardAnswer r = selectByPrimaryKey(string);
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, RewardAnswer.class))count++;
					}
				}
			}
		}
		return count>0;
	}

	@Override
	public RewardAnswer selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, RewardAnswer.class);
	}

	@Override
	public RewardAnswer selectById(String id) {
		return rewardAnswerDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> selectRAByFkId(String fkId, Integer isAccept) {
		Map parm = new HashMap();
		parm.put("fkId", fkId);
		parm.put("isAccept", isAccept);
		List<Map<String, Object>> list = rewardAnswerDao.selectRAByParam(parm);
		return list;
	}

	@Override
	public DataGrid selectAllRA(PageParam param, RewardAnswer ra, String question, String fbUserName, String ydUserName,
			String startTime, String endTime) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("fkId", ra.getFkId());
		parm.put("question", question);
		parm.put("fbUserName",fbUserName);
		parm.put("content", ra.getContent());
		parm.put("ydUserName", ydUserName);
		parm.put("isAccept",ra.getIsAccept());
		parm.put("startTime", startTime);
		parm.put("endTime", endTime);
		parm.put("orderStr", orderStr);
		List lst = rewardAnswerDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public Map<String, Object> selectRAById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = null;
		list = rewardAnswerDao.selectRAById(id);
		if(list!=null&&list.size()>0){
			map = (Map<String, Object>) list.get(0);
		}
		return map;
	}

}