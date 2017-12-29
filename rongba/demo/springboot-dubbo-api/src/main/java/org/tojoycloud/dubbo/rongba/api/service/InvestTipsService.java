package org.tojoycloud.dubbo.rongba.api.service;

import java.util.List;

import org.tojoycloud.dubbo.rongba.api.entity.InvestTips;

public interface InvestTipsService {

	    public InvestTips get(String id);

	    /**
	     * 获取单条数据
	     *
	     * @param entity
	     * @return
	     */
	    public InvestTips get(InvestTips entity);

	    /**
	     * 查询列表数据
	     *
	     * @param entity
	     * @return
	     */
	    public List<InvestTips> findList(InvestTips entity);

	    
	    
	    /**
	     * 保存数据（插入)
	     *
	     * @param entity
	     */
	    public void insert(InvestTips entity);
	    
	    
	    /**
	     * 保存数据（更新)
	     *
	     * @param entity
	     */
	    public void update(InvestTips entity);
	    
	    

	    /**
	     * 删除数据
	     *
	     * @param entity
	     */
	    public void delete(InvestTips entity);

}
