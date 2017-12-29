package com.wing.socialcontact.front.action;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.front.util.ApiCommonUtil;
import com.wing.socialcontact.front.util.ValidateModel;
import com.wing.socialcontact.service.wx.api.ICouponLogService;
import com.wing.socialcontact.service.wx.api.ICouponService;
import com.wing.socialcontact.service.wx.bean.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Devil
 * @description: 优惠券controller
 * @date 2017/12/26 15:52
 */
@Controller
@RequestMapping("/coupon")
public class CouponAction extends BaseAppAction {

    @Autowired
    private ICouponService couponService;
    @Autowired
    private ICouponLogService couponLogService;

    /**
     * @author devil
     * @desicription: 获取会议券接口
     * @date Created in 2017/12/26 16:01
     */
    @RequestMapping("/getMeetingCoupon")
    @ResponseBody
    public ResponseReport getMeetingCoupon(@RequestBody RequestReport rr, HttpServletRequest request) {
        ValidateModel validateModel = ApiCommonUtil.paramValidate(rr);
        if (validateModel.getCode() != 0) {
            return super.getAjaxResult(rr, ResponseCode.NotSupport, validateModel.getMsg(), validateModel.getObject());
        }
        Coupon coupon = new Coupon();
        coupon.setState(1);
        coupon.setCouponType(4);
        List<Coupon> couponList = couponService.selectCoupons(coupon);
        if (!CollectionUtils.isEmpty(couponList)) {
            coupon = couponList.get(0);
        } else {
            coupon = null;
        }
        Map map = new HashMap();
        map.put("data", coupon);
        return super.getSuccessAjaxResult(rr, "操作成功!", map);
    }

    /**
     * @author devil
     * @desicription: 我的卡券接口
     * @date Created in 2017/12/26 16:01
     */
    @RequestMapping(value = "getMyCoupon")
    @ResponseBody
    public ResponseReport getMyCoupon(@RequestBody RequestReport rr, HttpServletRequest request) {
        ValidateModel validateModel = ApiCommonUtil.paramValidate(rr, "pageNum");
        if (validateModel.getCode() != 0) {
            return super.getAjaxResult(rr, ResponseCode.NotSupport, validateModel.getMsg(), validateModel.getObject());
        }
        String userId = rr.getUserProperty().getUserId();
        String pageNum = rr.getDataValue("pageNum");
        List<Map> couponLogList = couponLogService.selectMyCoupons(userId, 4, Integer.parseInt(pageNum), 5);
        Map map = new HashMap();
        map.put("data", couponLogList);
        return super.getSuccessAjaxResult(rr, "操作成功!", map);
    }
}
