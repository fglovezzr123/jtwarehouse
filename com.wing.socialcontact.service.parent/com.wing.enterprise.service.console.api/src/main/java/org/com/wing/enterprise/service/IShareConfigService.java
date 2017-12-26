package org.com.wing.enterprise.service;

import org.com.wing.enterprise.bean.ShareConfig;

/**
 * 
 * <p>Title:分享页配置 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 下午2:31:46
 */
public interface IShareConfigService {

	String updateShareConfig(ShareConfig dto);

	ShareConfig getById(String qfyShareConfig);

}
