package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryStaticClick;

/**
 * 企服点击管理
 * 
 * 
 * @ClassName: IEntryStaticClickService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月13日
 */
public interface IEntryStaticClickService {

    boolean addEntryStaticClick(EntryStaticClick entryStaticClick);
    
    boolean updateEntryStaticClick(EntryStaticClick entryStaticClick);
    
    List<EntryStaticClick> selStaticClick(String entryId,String userId);
}
