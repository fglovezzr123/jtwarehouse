package com.tojoy.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.tojoy.common.model.Member;
import com.tojoy.util.ServletUtil;
public class DubboConsumerFilter implements Filter {  
	private static Logger logger = LoggerFactory.getLogger(DubboConsumerFilter.class);
	
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    	String curUserId = "";
    	Member m = null;
    	try {
    		//向dubbo请求中设置当前用户ID
    		Subject subject = SecurityUtils.getSubject();
    		if(subject!=null&&subject.getPrincipal() != null&&subject.isAuthenticated()){
    			curUserId = (String)subject.getPrincipal();//获取用户id
    		}
    		m = ServletUtil.getMember();
		} catch (Exception e) {
//			logger.error("向dubbo请求中设置当前用户ID失败", e);
			logger.error("向dubbo请求中设置当前用户ID失败");
		}
    	RpcContext.getContext().setAttachment("userId", curUserId);
    	RpcContext.getContext().setAttachment("minfo", JSON.toJSONString(m==null?new Member():m));
    	return invoker.invoke(invocation);
    }  
}  