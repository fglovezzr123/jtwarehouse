package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryImgs;

/**
 * 
 * <p>Title:集团图片管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 下午3:39:16
 */
public interface IEntryImgsService {
    
    String addEntryImg(EntryImgs entryImg);
    
    List<Map> selectByParam(String entryId,int type);
}
