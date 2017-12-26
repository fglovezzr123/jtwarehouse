package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ITemplateService;
import com.wing.socialcontact.service.wx.bean.Template;
import com.wing.socialcontact.service.wx.dao.TemplateDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 11:22:42
 * @version 1.0
 */
@Service
public class TemplateServiceImpl extends BaseServiceImpl<Template> implements ITemplateService {
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Template:\" + ";

	@Resource
	private TemplateDao templateDao;

	@Override
	public List selectAllTemplate() {
		return templateDao.selectAllTemplateMap();
	}

	@Override
	public DataGrid selectTemplates(PageParam param, Template template) {
		DataGrid data = new DataGrid();

		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		/// parm.put("tagName", template.getTagName());
		parm.put("orderStr", orderStr);
		List lst = templateDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}

	@Override
	public String addTemplate(Template template) {
		template.setIsOpen(0);
		int res = templateDao.insert(template);
		if (res > 0) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateTemplate(Template template) {
		if (super.updateByPrimaryKeyCache(template, template.getId())) {
			return MsgConfig.MSG_KEY_SUCCESS;
		} else {
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public boolean deleteTemplates(String[] ids) {
		// 等待删除的对象集合
		int count = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				String[] myids = id.split(",");
				for (String string : myids) {
					Template r = selectByPrimaryKey(string);
					if (r != null) {

						if (super.deleteByPrimaryKeyCache(string, Template.class))
							count++;
					}
				}
			}
		}
		return count > 0;
	}

	@Override
	public boolean updateTemplateByIsopen(String[] ids, int isopen) {
		// 等待删除的对象集合
		int count = 0;
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				String[] myids = id.split(",");
				for (String string : myids) {
					Template r = selectByPrimaryKey(string);
					r.setIsOpen(isopen);
					if (r != null) {
						if (super.updateByPrimaryKeyCache(r, r.getId()))
							count++;
					}
				}
			}
		}
		return count > 0;
	}

	@Override
	public Template selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Template.class);
	}

	@Override
	public Template selectById(String id) {
		return templateDao.selectByPrimaryKey(id);
	}

}