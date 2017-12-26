/**
 * 
 */
package org.tojoycloud.dubbo.springboot_dubbo_provider.service.impl;

import java.util.List;

import org.tojoycloud.dubbo.springboot_dubbo_api.entity.UserInfo;
import org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService;

/**
 * @author zhangpengzhi
 *
 */
public class UserInfoServiceMock implements UserInfoService {

	/* (non-Javadoc)
	 * @see org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService#getUserInfoById(int)
	 */
	@Override
	public UserInfo getUserInfoById(int it) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService#getAllUsers()
	 */
	@Override
	public List<UserInfo> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService#insertOne(org.tojoycloud.dubbo.springboot_dubbo_api.entity.UserInfo)
	 */
	@Override
	public int insertOne(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService#editOne(org.tojoycloud.dubbo.springboot_dubbo_api.entity.UserInfo)
	 */
	@Override
	public UserInfo editOne(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService#sayHello(java.lang.String)
	 */
	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "服务提供异常哦~";
	}

}
