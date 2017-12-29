package org.tojoycloud.dubbo.rongba.api.service;

import java.util.List;

import org.tojoycloud.dubbo.rongba.api.entity.FinanceProductStock;

public interface FinanceProductStockService {

	    public FinanceProductStock get(String id);

	    /**
	     * 获取单条数据
	     *
	     * @param entity
	     * @return
	     */
	    public FinanceProductStock get(FinanceProductStock entity);

	    /**
	     * 查询列表数据
	     *
	     * @param entity
	     * @return
	     */
	    public List<FinanceProductStock> findList(FinanceProductStock entity);

	    
	    
	    /**
	     * 保存数据（插入)
	     *
	     * @param entity
	     */
	    public void insert(FinanceProductStock entity);
	    
	    
	    /**
	     * 保存数据（更新)
	     *
	     * @param entity
	     */
	    public void update(FinanceProductStock entity);
	    
	    

	    /**
	     * 删除数据
	     *
	     * @param entity
	     */
	    public void delete(FinanceProductStock entity);

}
