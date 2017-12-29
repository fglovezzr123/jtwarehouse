package org.com.wing.enterprise.service;

import org.com.wing.enterprise.bean.QfyConfig;


/**
 * 企服云--通讯录开关配置
 * 
 * 
 * @ClassName: IQfyConfigService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月7日
 */
public interface IQfyConfigService {
    
    QfyConfig selConfig(String id);
    
    String updateDescConfig(QfyConfig c);
}
