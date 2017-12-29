package util;

import java.util.List;

import com.tojoy.sensitive.SensitiveWordResult;
import com.tojoy.sensitive.SimpleKWSeekerProcessor;
import com.tojoy.sensitive.conf.Config;

/**
 * 
 * 敏感词测试
 *
 */
public class KWSeekerManageTest {

    public static void main(String[] args) {
    	test0();
    }

    /**
     * 代码调用初始化
     */
    public static void test0() {
    	// 搜索器组,构建敏感词管理器,可同时管理多个搜索器，map的key为自定义搜索器标识
    	String wordType1 = "sensitive-word";
    	// 开始测试
    	String text1 = "中2国是一部黄5色电影，也是一部AV3电影";
    	System.out.println("   原文：" + text1);
    	System.out.println("识别结果:");
    	Config.setConfFileName("words.properties");
    	List<SensitiveWordResult> list = SimpleKWSeekerProcessor.newInstance().getKWSeeker(wordType1).findWords(text1);
    	System.out.println("返回敏感词個數：" + list);
    	System.out.println("返回敏感词個數：" + list.size());
    }
}
