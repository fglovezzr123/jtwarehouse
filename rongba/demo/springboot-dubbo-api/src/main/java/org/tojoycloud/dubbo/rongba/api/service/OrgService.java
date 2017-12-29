package org.tojoycloud.dubbo.rongba.api.service;

import java.util.List;

import org.tojoycloud.dubbo.rongba.api.entity.Org;

public interface OrgService {

	    public Org get(String id);

	    /**
	     * 获取单条数据
	     *
	     * @param entity
	     * @return
	     */
	    public Org get(Org entity);

	    /**
	     * 查询列表数据
	     *
	     * @param entity
	     * @return
	     */
	    public List<Org> findList(Org entity);

	    
	    
	    /**
	     * 保存数据（插入)
	     *
	     * @param entity
	     */
	    public void insert(Org entity);
	    
	    
	    /**
	     * 保存数据（更新)
	     *
	     * @param entity
	     */
	    public void update(Org entity);
	    
	    

	    /**
	     * 删除数据
	     *
	     * @param entity
	     */
	    public void delete(Org entity);

}
