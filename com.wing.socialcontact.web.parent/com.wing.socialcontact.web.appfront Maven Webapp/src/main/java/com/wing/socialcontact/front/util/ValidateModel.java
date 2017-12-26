package com.wing.socialcontact.front.util;

/**
 * 参数校验返回实体
 *
 * @author Devil
 * @date 2017/11/2 13:42
 */
public class ValidateModel {

    private int code;
    private String msg;
    private Object object;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
