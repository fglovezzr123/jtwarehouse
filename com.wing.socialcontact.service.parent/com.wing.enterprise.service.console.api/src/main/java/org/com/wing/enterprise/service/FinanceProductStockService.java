package org.com.wing.enterprise.service;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import org.com.wing.enterprise.bean.FinanceProductStock;

import java.util.List;

/**
 * Created by fenggang on 12/25/17.
 *
 * @author fenggang
 * @date 12/25/17
 */
public interface FinanceProductStockService {

    List<FinanceProductStock> findAll();

    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    DataGrid selectFinanceProductStock(PageParam param, FinanceProductStock financeProductStock);

    /**
     *
     * //TODO 获取所有的幻灯片所属的快捷入口
     * @return
     */
    List selectAllFinanceProductStock();
    /**
     *
     * //TODO 新增
     * @return
     */
    String addFinanceProductStock(FinanceProductStock financeProductStock);

    /**
     *
     * //TODO 更新
     * @return
     */
    String updateFinanceProductStock(FinanceProductStock financeProductStock);

    /**
     *
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    FinanceProductStock selectByPrimaryKey(Long key);

    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteFinanceProductStock(String[] ids);

}
