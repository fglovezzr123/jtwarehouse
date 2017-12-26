package com.wing.socialcontact.service.wx.dao;

import java.util.List;

import com.wing.socialcontact.service.wx.bean.ProjectSupermarketImages;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.poi.ss.formula.functions.T;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

public interface ProjectSupermarketImagesDao extends Mapper<ProjectSupermarketImages> {

    /**
     * 分页查询
     * @param t
     * @return
     * @author wangyansheng
     */
    List<ProjectSupermarketImages> selectByParam(ProjectSupermarketImages t);
}