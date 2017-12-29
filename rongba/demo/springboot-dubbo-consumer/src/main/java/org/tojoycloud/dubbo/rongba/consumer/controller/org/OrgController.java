/**
 * 
 */
package org.tojoycloud.dubbo.rongba.consumer.controller.org;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tojoycloud.dubbo.rongba.api.entity.Org;
import org.tojoycloud.dubbo.rongba.api.service.OrgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author zhangpengzhi
 *
 */
@Api(tags = "机构信息接口")
@Controller
@RequestMapping(value = "/org/")
public class OrgController {

	@Autowired
	private OrgService orgService;

	/**
	* 机构详情
	* @return
	*/
	@ApiOperation(value = "机构详情展现", notes = "机构详情展现", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> detail(
			@ApiParam(required = true, name = "orgNo", value = "机构编号") @RequestParam(value = "orgNo") String orgNo
			) {
			Map<String, Object> map = new HashMap<String, Object>();
			Org object =orgService.get(orgNo);
			map.put("code", "0");
			map.put("message", "获取机构详情成功");
			map.put("content", object);
			return map;
	}
	
	
}
