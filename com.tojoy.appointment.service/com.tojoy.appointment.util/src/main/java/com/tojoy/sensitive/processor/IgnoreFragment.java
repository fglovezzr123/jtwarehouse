package com.tojoy.sensitive.processor;

import com.tojoy.sensitive.KeyWord;

/**
 * 
 * 替换内容的片段处理方式
 */
public class IgnoreFragment extends AbstractFragment {

    private String ignore = "";

    public IgnoreFragment() {
    }

    public IgnoreFragment(String ignore) {
        this.ignore = ignore;
    }

    @Override
    public String format(KeyWord word) {
        return ignore;
    }

}
