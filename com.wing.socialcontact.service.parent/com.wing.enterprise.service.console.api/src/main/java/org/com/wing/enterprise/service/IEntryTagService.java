package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryTags;

/**
 * 企服标签管理
 * 
 * 
 * @ClassName: IEntryTagService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月7日
 */
public interface IEntryTagService {
    
    String addEntryTag(EntryTags entryTag);
    
    List<Map> selTagsByEntryId(String entryId);
}
