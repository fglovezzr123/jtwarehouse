package com.wing.socialcontact.service.wx.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IProjectSupermarketService;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarket;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarketImages;
import com.wing.socialcontact.service.wx.dao.ProjectSupermarketDao;
import com.wing.socialcontact.service.wx.dao.ProjectSupermarketImagesDao;
import com.wing.socialcontact.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目超市
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
@Service
public class ProjectSupermarketServiceImpl implements IProjectSupermarketService {

	@Resource
	private ProjectSupermarketDao projectSupermarketDao;

	@Resource
	private ProjectSupermarketImagesDao projectSupermarketImagesDao;

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int insert(ProjectSupermarket t) {

		t.setId(UUIDGenerator.getUUID());
		int n = projectSupermarketDao.insert(t);

		List<ProjectSupermarketImages> images =  new ArrayList<>();
		if(t.getImages()!=null){
			images = JSON.parseArray(t.getImages(), ProjectSupermarketImages.class);
			for(int i=0;i<images.size();i++){
				images.get(i).setId(UUIDGenerator.getUUID());
				images.get(i).setProjectId(t.getId());
				images.get(i).setCreateTime(new Date());
				images.get(i).setSort(i+1);
				projectSupermarketImagesDao.insert(images.get(i));
			}
		}

		if(t.getProjectSupermarketImagesList()!=null){
			for(int i=0;i<t.getProjectSupermarketImagesList().size();i++){
				t.getProjectSupermarketImagesList().get(i).setId(UUIDGenerator.getUUID());
				t.getProjectSupermarketImagesList().get(i).setProjectId(t.getId());
				t.getProjectSupermarketImagesList().get(i).setSort(i+1);
				projectSupermarketImagesDao.insert(t.getProjectSupermarketImagesList().get(i));
			}
		}

		return n;
	}

	/**
	 * 修改
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int update(ProjectSupermarket t) {

		ProjectSupermarketImages supermarketImages = new ProjectSupermarketImages();
		supermarketImages.setProjectId(t.getId());
		projectSupermarketImagesDao.delete(supermarketImages);
		List<ProjectSupermarketImages> images =  new ArrayList<>();
		if(t.getImages()!=null){
			images = JSON.parseArray(t.getImages(), ProjectSupermarketImages.class);
			for(int i=0;i<images.size();i++){
				images.get(i).setId(UUIDGenerator.getUUID());
				images.get(i).setProjectId(t.getId());
				images.get(i).setCreateTime(new Date());
				images.get(i).setSort(i+1);
				projectSupermarketImagesDao.insert(images.get(i));
			}
		}

		int n = projectSupermarketDao.updateByPrimaryKey(t);
		return n;
	}

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public int logicalDelete(ProjectSupermarket t) {
		return projectSupermarketDao.updateByPrimaryKey(t);
	}

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public DataGrid selectByParam(PageParam param, ProjectSupermarket t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<ProjectSupermarket> list = projectSupermarketDao.selectByParam(t);
		PageInfo<ProjectSupermarket> page = new PageInfo<ProjectSupermarket>(list);
		return new DataGrid(page);
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	@Override
	public ProjectSupermarket selectById(String id) {

		//基本信息
		ProjectSupermarket projectSupermarket =  new ProjectSupermarket();
		ProjectSupermarket supermarket = new ProjectSupermarket();
		supermarket.setId(id);
		List<ProjectSupermarket> projectSupermarketList = this.projectSupermarketDao.selectByParam(supermarket);
		if(projectSupermarketList.size()>0){
			projectSupermarket = projectSupermarketList.get(0);
		}
		if(projectSupermarket!=null){
			//图片
			ProjectSupermarketImages projectSupermarketImages = new ProjectSupermarketImages();
			projectSupermarketImages.setProjectId(id);
			List<ProjectSupermarketImages> projectSupermarketImagesList = projectSupermarketImagesDao.select(projectSupermarketImages);
			projectSupermarket.setProjectSupermarketImagesList(projectSupermarketImagesList);
		}
		return projectSupermarket;
	}

	@Override
	public List<Map> selectImagesByParam(Map paraMap) {
		if (paraMap.get("pageNum") == null || paraMap.get("pageSize") == null) {
			return null;
		}
		PageHelper.startPage(Integer.valueOf(paraMap.get("pageNum").toString()),
				Integer.valueOf(paraMap.get("pageSize").toString()));
		return projectSupermarketDao.selectImagesByParam(paraMap);
	}
}