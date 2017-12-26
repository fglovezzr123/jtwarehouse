package com.wing.socialcontact.front.util;

import com.tojoycloud.report.RequestReport;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

/**
 * Api公共方法类
 *
 * @author Devil
 * @date 2017/11/2 13:22
 */
public class ApiCommonUtil {

    /**
     * @author devil
     * @desicription: 参数判断
     * @date Created in 2017/11/2 13:25
     */
    public static ValidateModel paramValidate(RequestReport rr, String... params) {
        ValidateModel validateModel = new ValidateModel();
        String userId = rr.getUserProperty().getUserId();
        if (StringUtil.isEmpty(userId)) {
            validateModel.setCode(1);
            validateModel.setMsg("未登录");
            validateModel.setObject(userId);
            return validateModel;
        }
        if (params.length > 0) {
            for (String param : params) {
                String paramStr = rr.getDataValue(param);
                if (StringUtils.isEmpty(paramStr)) {
                    validateModel.setCode(1);
                    validateModel.setMsg("参数错误");
                    validateModel.setObject(param);
                    return validateModel;
                }
            }
        }
        validateModel.setCode(0);
        return validateModel;
    }

}
