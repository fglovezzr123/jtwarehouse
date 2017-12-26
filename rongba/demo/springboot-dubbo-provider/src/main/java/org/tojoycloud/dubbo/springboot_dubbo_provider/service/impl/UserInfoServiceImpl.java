package org.tojoycloud.dubbo.springboot_dubbo_provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tojoycloud.dubbo.springboot_dubbo_api.entity.UserInfo;
import org.tojoycloud.dubbo.springboot_dubbo_api.service.UserInfoService;
import org.tojoycloud.dubbo.springboot_dubbo_provider.mapper.UserInfoMapper;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo getUserInfoById(int id) {
		// TODO Auto-generated method stub
		return userInfoMapper.getUserInfoById(id);
	}

	@Override
	public List<UserInfo> getAllUsers() {
		// TODO Auto-generated method stub
		return userInfoMapper.getAllUsers();
	}

	@Override
	public int insertOne(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return userInfoMapper.insertOne(userInfo);
	}

	@Override
	public UserInfo editOne(UserInfo userInfo) {
		// TODO Auto-generated method stub
		int i = userInfoMapper.editOne(userInfo);

		return userInfo;
	}

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "Hello " + name;
	}

}
