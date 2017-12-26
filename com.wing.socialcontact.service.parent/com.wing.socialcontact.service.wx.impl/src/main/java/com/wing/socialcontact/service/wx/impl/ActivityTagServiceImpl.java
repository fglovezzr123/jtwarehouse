package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IActivityTagService;
import com.wing.socialcontact.service.wx.bean.Activity;
import com.wing.socialcontact.service.wx.bean.ActivityTag;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.service.wx.dao.ActivityDao;
import com.wing.socialcontact.service.wx.dao.ActivityTagDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
/**
 * 
 * <p>Title: 活动标签管理</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年4月18日 下午3:19:40
 */
@Service
public class ActivityTagServiceImpl extends BaseServiceImpl<ActivityTag> implements
		IActivityTagService {

	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:ActivityTag:\" +";
	
	@Resource
	private ActivityTagDao	activityTagDao;
	
	@Resource
	private ActivityDao	activityDao;

    @Override
    public List<ActivityTag> selTags(String classId) {
        
        Map parm = new HashMap();
        parm.put("classId", classId);
        return activityTagDao.selTags(parm);
    }
    
	/**
	 * 活动标签列表查询
	 * @param param
	 * @param activitytag
	 * @return
	 */
	@Override
	public Object selectactivitytag(PageParam param, ActivityTag activitytag) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("name", activitytag.getName());
		parm.put("classId", activitytag.getClassId());
		List<Map<String,Object>> lst=activityTagDao.getclassMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	/**
	 * 
	 * @param dto
	 * @return
	 * * 活动标签新增
	 */
	@Override
	public String addActivityTag(ActivityTag dto) {
		ActivityTag parm = new ActivityTag();
		parm.setName(dto.getName());
		List lst = activityTagDao.select(parm);
		if(lst.size()==0){
			int res = activityTagDao.insert(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//
		}
	}

	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	@Override
	public ActivityTag getActivityTagByid(String id) {
		return super.selectByPrimaryKeyCache(id, ActivityTag.class);
	}

	/**
	 * 
	 * @param dto
	 * @return
	 * * 活动标签修改
	 */
	@Override
	public String updateActivityTag(ActivityTag dto) {
		ActivityTag parm = new ActivityTag();
		parm.setName(dto.getName());
		ActivityTag obj = activityTagDao.selectOne(parm);
		if(obj==null || obj.getId().equals(dto.getId())){
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//
		}
		//updateByPrimaryKey(dto);
	}
	
	/**
	 * 根据活动分类id查询活动标签
	 * @param cid
	 * @return
	 */
	@Override
	public List<Map<String,Object>> activityTag(String cid) {
		Map parm = new HashMap();
		parm.put("classId", cid);
		parm.put("name", "");
		List<Map<String,Object>> lst=activityTagDao.getclassMap(parm);
		return lst;
	}

	@Override
	public String deleteactivitytag(String id) {
		
		Map parm = new HashMap();
		parm.put("tagid", id);
		
		List list =activityDao.selectbytag(parm);
		if(list.size()>0){
			return MsgConfig.MSG_KEY_ERROR_NODEL;
		}
		if(super.deleteByPrimaryKeyCache(id, ActivityTag.class)){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public List selectalltag() {
		// TODO Auto-generated method stub
		return activityTagDao.selectAll();
	}
}
