package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryClass;

/**
 * 企服分类管理
 * 
 * 
 * @ClassName: IEntryClassService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月7日
 */
public interface IEntryClassService {

    String addEntryClass(EntryClass entryClass);
    
    List<Map> selClassesByEntryId(String entryId);
}
