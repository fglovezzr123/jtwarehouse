package org.com.wing.enterprise.service;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import org.com.wing.enterprise.bean.FinanceProduct;
import org.com.wing.enterprise.bean.QuickDetailBanner;

import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/25/17.
 *
 * @author fenggang
 * @date 12/25/17
 */
public interface FinanceProductService {

    List<FinanceProduct> findAll();

    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    DataGrid selectFinanceProduct(PageParam param, FinanceProduct financeProduct);

    /**
     *
     * //TODO 获取所有的幻灯片所属的快捷入口
     * @return
     */
    List selectAllFinanceProduct();
    /**
     *
     * //TODO 新增
     * @param QuickDoor
     * @return
     */
    String addFinanceProduct(FinanceProduct financeProduct);

    /**
     *
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    String updateFinanceProduct(FinanceProduct financeProduct);

    /**
     *
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    FinanceProduct selectByPrimaryKey(Long key);

    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteFinanceProduct(String[] ids);

}
