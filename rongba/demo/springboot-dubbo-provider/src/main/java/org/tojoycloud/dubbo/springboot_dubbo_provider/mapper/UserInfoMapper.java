/**
 * 
 */
package org.tojoycloud.dubbo.springboot_dubbo_provider.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.tojoycloud.dubbo.springboot_dubbo_api.entity.UserInfo;

/**
 * @author zhangpengzhi
 *
 */

@Mapper
public interface UserInfoMapper {

	public UserInfo getUserInfoById(int id);

	public List<UserInfo> getAllUsers();

	public int insertOne(UserInfo userInfo);

	public int editOne(UserInfo userInfo);
}
