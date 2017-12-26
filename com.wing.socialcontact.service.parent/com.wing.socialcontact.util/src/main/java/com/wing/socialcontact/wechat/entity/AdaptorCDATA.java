package com.wing.socialcontact.wechat.entity;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdaptorCDATA extends XmlAdapter<String, String> {

    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }

    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }
}
