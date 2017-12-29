package com.wing.socialcontact.service.wx.dao;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.service.wx.bean.ProjectSupermarket;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.poi.ss.formula.functions.T;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

public interface ProjectSupermarketDao extends Mapper<ProjectSupermarket> {

    /**
     * 分页查询
     * @param t
     * @return
     * @author wangyansheng
     */
    List<ProjectSupermarket> selectByParam(ProjectSupermarket t);

    /**
     * @author devil
     * @desicription: 查询项目超市图片
     * @date Created in 2017/11/14 16:10
     */
    List<Map> selectImagesByParam(Map paraMap);
}