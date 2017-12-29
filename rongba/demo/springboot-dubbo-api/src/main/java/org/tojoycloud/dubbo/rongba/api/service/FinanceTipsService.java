package org.tojoycloud.dubbo.rongba.api.service;

import java.util.List;

import org.tojoycloud.dubbo.rongba.api.entity.FinanceTips;

public interface FinanceTipsService {

	    public FinanceTips get(String id);

	    /**
	     * 获取单条数据
	     *
	     * @param entity
	     * @return
	     */
	    public FinanceTips get(FinanceTips entity);

	    /**
	     * 查询列表数据
	     *
	     * @param entity
	     * @return
	     */
	    public List<FinanceTips> findList(FinanceTips entity);

	    
	    
	    /**
	     * 保存数据（插入)
	     *
	     * @param entity
	     */
	    public void insert(FinanceTips entity);
	    
	    
	    /**
	     * 保存数据（更新)
	     *
	     * @param entity
	     */
	    public void update(FinanceTips entity);
	    
	    

	    /**
	     * 删除数据
	     *
	     * @param entity
	     */
	    public void delete(FinanceTips entity);

}
