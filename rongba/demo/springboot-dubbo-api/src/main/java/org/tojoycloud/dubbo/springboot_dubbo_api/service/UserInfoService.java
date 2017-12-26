package org.tojoycloud.dubbo.springboot_dubbo_api.service;

import java.util.List;

import org.tojoycloud.dubbo.springboot_dubbo_api.entity.UserInfo;

public interface UserInfoService {

	public UserInfo getUserInfoById(int it);

	public List<UserInfo> getAllUsers();

	public int insertOne(UserInfo userInfo);

	public UserInfo editOne(UserInfo userInfo);
	
	/**
	 * demo
	 * @param name
	 * @return
	 */
	public String sayHello(String name);

}
