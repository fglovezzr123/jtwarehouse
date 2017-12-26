package com.wing.enterprise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import com.wing.socialcontact.sys.service.TjrbInvestProductInsureService;



@Controller
public class TjrbInvestProductInsureAction {

	@Resource
	private TjrbInvestProductInsureService tjrbInvestProductInsureService;


	@RequestMapping(value="/sys/sel",method=RequestMethod.GET)
	public String select(Model model) {
		List<TjrbInvestProductInsure> list=tjrbInvestProductInsureService.selectTjrbInvestProductInsure();
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).getProductStatus());
		}
		model.addAttribute("TIList", list);
		return null;
	}
	

	@RequestMapping(value="/sys/add",method=RequestMethod.GET)
	public String add(@RequestParam Map<String, Object> map) {
		map.put("productSatus", 123);
		Integer flag=tjrbInvestProductInsureService.saveTjrbInvestProductInsure(map);
		System.out.println(flag);
		return null;
	}
	

	@RequestMapping(value="/sys/selid",method=RequestMethod.GET)
	public String selectId(Model model,@RequestParam("id") int id) {
		TjrbInvestProductInsure ins=tjrbInvestProductInsureService.selectTjrbInvestProductInsureById(1);
		System.out.println(ins.getProductStatus());
		return null;
	}
	

	@RequestMapping(value="/sys/update",method=RequestMethod.GET)
	public String update(Model model,@RequestParam("id") int id,@RequestParam Map<String,Object> map) {
		map.put("id", 1);
		map.put("productSatus", 123);
		boolean flag=tjrbInvestProductInsureService.updateTjrbInvestProductInsure(map);
		System.out.println(flag);
		return null;
	}
	

	@RequestMapping(value="/sys/delete",method=RequestMethod.GET)
	public String delete(@RequestParam("id") int id) {
		boolean flag=tjrbInvestProductInsureService.deleteTjrbInvestProductInsureById(2);
		System.out.println(flag);
		return null;
	}
	

	@RequestMapping(value="/sys/selecta",method=RequestMethod.GET)
	public String selecta() {
		Map<String , String> map=new HashMap<>();
		map.put("orgName", "s");
		List<TjrbInvestProductInsure> list=tjrbInvestProductInsureService.selectTjrbInvestProductInsureLike(map);
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).getProductName());
		}
		return null;
	}
}
