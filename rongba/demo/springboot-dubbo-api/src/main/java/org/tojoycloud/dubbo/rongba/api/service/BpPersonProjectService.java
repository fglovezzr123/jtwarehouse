package org.tojoycloud.dubbo.rongba.api.service;

import java.util.List;

import org.tojoycloud.dubbo.rongba.api.entity.BpPersonProject;

public interface BpPersonProjectService {

	    public BpPersonProject get(String id);

	    /**
	     * 获取单条数据
	     *
	     * @param entity
	     * @return
	     */
	    public BpPersonProject get(BpPersonProject entity);

	    /**
	     * 查询列表数据
	     *
	     * @param entity
	     * @return
	     */
	    public List<BpPersonProject> findList(BpPersonProject entity);

	    
	    
	    /**
	     * 保存数据（插入)
	     *
	     * @param entity
	     */
	    public void insert(BpPersonProject entity);
	    
	    
	    /**
	     * 保存数据（更新)
	     *
	     * @param entity
	     */
	    public void update(BpPersonProject entity);
	    
	    

	    /**
	     * 删除数据
	     *
	     * @param entity
	     */
	    public void delete(BpPersonProject entity);

}
