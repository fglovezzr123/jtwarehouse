package com.tojoy.wechat.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * XML 数据接收对象转换工具类
 * @author liangwj
 */
public class XMLConverUtil {
    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param xml
     * @return
     */
    public static <T> T convertToObject(Class<T> clazz, String xml) {
        return convertToObject(clazz, new StringReader(xml));
    }
    /**
     * XML to Object
     * @param <T>
     * @param clazz
     * @param inputStream
     * @return
     */
    public static <T> T convertToObject(Class<T> clazz, InputStream inputStream) {
        return convertToObject(clazz, new InputStreamReader(inputStream));
    }

    /**
     * XML to Object
     *
     * @param <T>
     * @param clazz
     * @param reader
     * @return
     */
    public static <T> T convertToObject(Class<T> clazz, Reader reader) {
        return XmlUtil.convertToObject(clazz, reader);
    }
    /**
     * Object to XML
     *
     * @param object
     * @return
     */
    public static String convertToXML(Object object) {
        return XmlUtil.convertToXML(object);
    }
}
