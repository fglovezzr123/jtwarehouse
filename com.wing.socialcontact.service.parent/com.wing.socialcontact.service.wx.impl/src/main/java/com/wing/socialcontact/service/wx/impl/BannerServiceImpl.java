package com.wing.socialcontact.service.wx.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.BannerDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Banner;
import com.wing.socialcontact.service.wx.bean.IndexAd;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;

/**
 * 
 * @author zhangfan
 * @date 2017-03-31 17:43:02
 * @version 1.0
 */
@Service
public class BannerServiceImpl extends BaseServiceImpl<Banner>  implements IBannerService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:banner:\" + ";
	
	@Resource
	private BannerDao bannerDao;
	@Resource
	private RedisCache redisCache;
	@Resource
	private IWxUserService wxUserService;

	@Resource
	private ITjyUserService tjyUserService;

	@Override
	public DataGrid selectAllBanner(PageParam param, Banner banner) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("columnType", banner.getColumnType());
		parm.put("isguide", banner.getIsguide());
		parm.put("orderStr", orderStr);
		List lst = bannerDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addBanner(Banner banner) {
		//清楚缓存
		String str = "";
		int res = bannerDao.insert(banner);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateBanner(Banner banner) {
		//清楚缓存
		redisCache.removeall("selBannerList_"+banner.getColumnType());
		if(super.updateByPrimaryKeyCache(banner,banner.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteBanner(String[] ids) {
		String columnType = "";
		//等待删除的对象集合
		int count = 0;
		for(String id:ids){
			if(StringUtils.isNotBlank(id)){
				String[] myids = id.split(",");
				for (String string : myids) {
					Banner r=selectByPrimaryKey(string);
					columnType = r.getColumnType();
					if(r!=null){
						if(super.deleteByPrimaryKeyCache(string, Banner.class))count++;
					}
				}
			}
		}
		//清楚缓存
		redisCache.removeall("selBannerList_"+columnType);
		return count>0;
	}

	@Override
	public Banner selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Banner.class);
	}

	@Override
	public Banner selectById(String id) {
		return bannerDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Banner> selectByColumnType(String columnType) {
		Map parm = new HashMap();
		parm.put("columnType", columnType);
		return bannerDao.selectByType(parm);
	}

	@Override
	public List<Banner> selectBannerByUserId(String userId,String columnType) {
		List<Banner> blist = new ArrayList<Banner>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("columnType", columnType);
		List<Banner> lst = bannerDao.selectByType(param);
		if (null == lst || lst.isEmpty()) {
			return null;
		}
		WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		if (null == tjyUser) {
			return null;
		}
		int reconUserFlag = 0;
		for (Banner banner : lst) {
			// 全部
			if (banner.getUserRange() == 1) {
				blist.add(banner);
			}
			// 用户等级
			if (StringUtils.isNotEmpty(wxUser.getLevel())) {
				if (StringUtils.isNotEmpty(banner.getUserLevelName())) {
					if (banner.getUserLevelName().indexOf(wxUser.getLevel()) != -1) {
						// 认证用户可见
						reconUserFlag = banner.getReconUserFlag()==null?0:banner.getReconUserFlag();
						if (reconUserFlag == 1) {
							if (tjyUser.getReconStatus() == 2) {
								blist.add(banner);
							}
						} else {
							// 非认证用户可见
							if (tjyUser.getReconStatus() != 2) {
								blist.add(banner);
							}
						}
					}
				}
			}
			
		}
		return blist;
	}

	@Override
	public List selBannerListFords(String columnType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("columnType", columnType);
		List<Banner> lst = bannerDao.selectByType(param);
		return lst;
	}

}