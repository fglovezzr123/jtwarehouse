package com.wing.socialcontact.front.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 
 * @author please  write your big name
 * 
 */
 
@Controller
@RequestMapping(value = "/wx")
public class FrontSiteController {

	

	/**
	 * 获得列表数据
	 * @throws IOException 
	 * @throws ServletException 
	 *  
	 */
	@RequestMapping(value = "/index")
	public String home(HttpServletRequest request,
			HttpServletResponse response, Model model) throws ServletException, IOException {
		return "index";
	}
	
	/**
	 * 快速定位
	 * @throws IOException 
	 * @throws ServletException 
	 *  
	 */
	@RequestMapping(value = "/{file}")
	public String list(@PathVariable String file,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return file;
	}
	
	/**
	 * 快速定位
	 * @throws IOException 
	 * @throws ServletException 
	 *  
	 */
	@RequestMapping(value = "/{file}/{file2}")
	public String list2(@PathVariable String file,@PathVariable String file2,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return file+"/"+file2;
	}
}

