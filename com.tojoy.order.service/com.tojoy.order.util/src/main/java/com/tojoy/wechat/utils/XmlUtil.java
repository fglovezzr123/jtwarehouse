package com.tojoy.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import com.tojoy.wechat.resp.UnifiedorderResult;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XmlUtil {

    @SuppressWarnings("rawtypes")
	static GenericKeyedObjectPool<Class, Marshaller> marshallerPool = new GenericKeyedObjectPool<Class, Marshaller>(new MarshallerFactory());
    @SuppressWarnings("rawtypes")
	static GenericKeyedObjectPool<Class, Unmarshaller> unmarshallerPool = new GenericKeyedObjectPool<Class, Unmarshaller>(new UnmarshallerFactory());

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
     *
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
    @SuppressWarnings("unchecked")
    public static <T> T convertToObject(Class<T> clazz, Reader reader) {
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = unmarshallerPool.borrowObject(clazz);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to borrow unmarshaller from pool", e);
        } catch (Exception e) {
            // ignored
        } finally {
            try {
                if (null != unmarshaller) {
                    unmarshallerPool.returnObject(clazz, unmarshaller);
                }
            } catch (Exception e) {
                // ignored
            }
        }
        return null;
    }

    /**
     * Object to XML
     *
     * @param object
     * @return
     */
    public static String convertToXML(Object object) {
        Marshaller marshaller = null;
        try {
            marshaller = marshallerPool.borrowObject(object.getClass());
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(object, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to borrow unmarshaller from pool", e);
        } catch (Exception e) {
            // ignored
        } finally {
            try {
                if (null != marshaller) {
                    marshallerPool.returnObject(object.getClass(), marshaller);
                }
            } catch (Exception e) {
                // ignored
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
	private static class MarshallerFactory extends BaseKeyedPooledObjectFactory<Class, Marshaller> {
        @SuppressWarnings("rawtypes")
        public Marshaller create(Class clazz) throws Exception {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
                    writer.write(ac, i, j);
                }
            });
            return marshaller;
        }


        public PooledObject<Marshaller> wrap(Marshaller marshaller) {
            return new DefaultPooledObject<>(marshaller);
        }
    }

    @SuppressWarnings("rawtypes")
	private static class UnmarshallerFactory extends BaseKeyedPooledObjectFactory<Class, Unmarshaller> {

        public Unmarshaller create(Class clazz) throws Exception {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            return jaxbContext.createUnmarshaller();
        }


        public PooledObject<Unmarshaller> wrap(Unmarshaller unmarshaller) {
            return new DefaultPooledObject<>(unmarshaller);
        }
    }

    public static void main(String[] args) {
    	UnifiedorderResult r = new UnifiedorderResult();
    	r.setResult_code("SUCCESS");
    	r.setAppid("1");
    	r.setTrade_type("1");
    	r.setPrepay_id("1");
    	r.setSign("1");

    	String xml = XMLConverUtil.convertToXML(r);
    	System.out.println(xml);
    	UnifiedorderResult r2 = convertToObject(UnifiedorderResult.class, xml);
    	System.out.println(JSON.toJSONString(r2));
	}
}
