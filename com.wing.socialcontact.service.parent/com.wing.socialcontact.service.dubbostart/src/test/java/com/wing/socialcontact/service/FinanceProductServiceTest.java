package com.wing.socialcontact.service;

import com.wing.socialcontact.sys.bean.FinanceProduct;
import com.wing.socialcontact.sys.service.FinanceProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by fenggang on 12/26/17.
 *
 * @author fenggang
 * @date 12/26/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class FinanceProductServiceTest {

    @Autowired
    private FinanceProductService financeProductService;

    @Test
    public void save(){
        FinanceProduct financeProduct = new FinanceProduct();
        financeProduct.setProductName("test");
        financeProductService.addFinanceProduct(financeProduct);
    }

    @Test
    public void list(){
        List list = financeProductService.findAll();
        System.out.println(list.size());
    }
}
