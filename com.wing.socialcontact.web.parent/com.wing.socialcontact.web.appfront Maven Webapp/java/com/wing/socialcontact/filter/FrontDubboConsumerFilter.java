package com.wing.socialcontact.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.utils.CtxHolder;

/**
 * wxfront端dubbo参数设置（当前用户信息设置）
 * @author liangwj
 *
 */
public class FrontDubboConsumerFilter implements Filter {  
	private static Logger logger = LoggerFactory.getLogger(FrontDubboConsumerFilter.class);
	
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    	String curUserId = "";
    	Member m = null;
    	try {
			curUserId = CtxHolder.getUserId();
    		m = CtxHolder.getMember();
		} catch (Exception e) {
			logger.error("向dubbo请求中设置当前用户ID失败", e);
		}
    	RpcContext.getContext().setAttachment("userId", curUserId);
    	RpcContext.getContext().setAttachment("minfo", JSON.toJSONString(m==null?new Member():m));
    	return invoker.invoke(invocation);
    }  
}  