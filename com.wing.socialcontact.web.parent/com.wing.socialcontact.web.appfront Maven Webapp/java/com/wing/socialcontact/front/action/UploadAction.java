/**  
 * @Project: tjy
 * @Title: DeptAction.java
 * @Package com.oa.manager.system.action
 * @date 2016-4-2 下午4:11:32
 * @Copyright: 2016 
 */
package com.wing.socialcontact.front.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.wx.bean.UploadResultVo;
import com.wing.socialcontact.util.FileUploadUtil;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;
import com.wing.socialcontact.util.WeixinUtil;
import com.wing.socialcontact.util.pojo.AccessToken;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

/**
 * 图片控制器
 * 
 * @ClassName: SysAction
 * @Description: TODO
 * @author: zengmin
 * @date:2017年4月2日 下午5:57:56
 */
@Controller
@RequestMapping("/m/upload")
public class UploadAction extends BaseAction {
	@Resource
	protected RedisCache redisCache;

	@RequestMapping("getUploadPicForm")
	public ModelAndView getUploadPicForm(HttpServletRequest request, String formId, String inputId, String frameId,
			String inputOnChange, String jsonp, String itemPicParam) {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null != me) {
			ModelAndView modelAndView = new ModelAndView("upload/uploadPic");
			modelAndView.addObject("formId", formId);
			modelAndView.addObject("inputId", inputId);
			modelAndView.addObject("frameId", frameId);
			modelAndView.addObject("inputOnChange", inputOnChange);
			modelAndView.addObject("jsonp", jsonp);
			modelAndView.addObject("itemPicParam", itemPicParam);
			return modelAndView;
		}
		return new ModelAndView("exception");

	}

	@RequestMapping("uploadPic")
	public ModelAndView uploadPic(HttpServletRequest request, String jsonp, String frameId, String inputId,
			String formId, String module, @RequestParam(value = "pic") MultipartFile pic) {
		ModelAndView modelAndView = new ModelAndView("upload/uploadCallBack");
		modelAndView.addObject("callback", jsonp);
		modelAndView.addObject("inputId", inputId);
		modelAndView.addObject("formId", formId);
		modelAndView.addObject("frameId", frameId);
		UploadResultVo resultVo = new UploadResultVo(1, "系统异常,请稍后再试");
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		if (null == pic) {
			resultVo.setMsg("请重新选择上传图片");
			modelAndView.addObject("data", JSON.toJSONString(resultVo));
			return modelAndView;
		}
		try {
			if (pic.getSize() > 2097152) {
				resultVo.setMsg("请重新选择上传图片:图片最大支持2M大小");
				modelAndView.addObject("data", JSON.toJSONString(resultVo));
				return modelAndView;
			}
			InputStream io = pic.getInputStream();
			String uploadFilePath = StringUtils.isEmpty(module) ? "wx" : module;
			String ext = "." + FilenameUtils.getExtension(pic.getOriginalFilename());
			String picPath = FileUploadUtil.uploadFileInputStream(io, ext, uploadFilePath);
			if (picPath != "" && !"".equals(picPath)) {
				resultVo.setReturnCode(0);
				resultVo.setPicPath(picPath);
				resultVo.setMsg(picPath);
				resultVo.setImg_url(ossurl + picPath);
			} else {
				resultVo.setMsg("上传图片信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVo.setMsg("上传图片信息失败");
		}
		modelAndView.addObject("data", JSON.toJSONString(resultVo).replaceAll("\"", "'"));
		return modelAndView;
	}

	@RequestMapping("wxUploadPic")
	public @ResponseBody Map wxUploadPic(HttpServletRequest request, String serverId) {
		if (StringUtils.isEmpty(serverId)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		Map rsmap = new HashMap();
		String wx_secret = ApplicationPath.getParameter("wx_secret");
		String wx_appid = ApplicationPath.getParameter("wx_appid");
		AccessToken a = WeixinUtil.getAccessToken(wx_appid, wx_secret);

		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + a.getToken() + "&media_id="
				+ serverId;
		System.out.println("url+:" + url);
		BufferedInputStream in = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			in = new BufferedInputStream(http.getInputStream());

			// 获取所有响应头字段
			Map<String, List<String>> map = http.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			/*
			 * List<String> contentDisposition =
			 * (List<String>)map.get("Content-disposition"); String fileName =
			 * contentDisposition.get(0); fileName =
			 * fileName.substring(fileName.indexOf("filename=")+10); fileName =
			 * fileName.substring(0,fileName.indexOf("\"")); String ext =
			 * fileName.substring(fileName.lastIndexOf(".")+1);
			 */

			// 定义 BufferedReader输入流来读取URL的响应

			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();

			String picPath = FileUploadUtil.uploadFileInputStream(in, "jpg", "wx");
			if (picPath != "" && !"".equals(picPath)) {
				Map dataobj = new HashMap();
				dataobj.put("pic_path", picPath);
				dataobj.put("img_url", ossurl + picPath);

				rsmap.put("code", "0");
				rsmap.put("msg", "上传成功！");
				rsmap.put("dataobj", dataobj);
			} else {
				rsmap.put("code", "999");
				rsmap.put("msg", "上传图片信息失败");
				rsmap.put("dataobj", "");
			}
			http.disconnect();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();

			rsmap.put("code", "999");
			rsmap.put("msg", "发送GET请求出现异常！");
			rsmap.put("dataobj", "");
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rsmap;
	}

	@RequestMapping("wxUploadVoice")
	public @ResponseBody Map wxUploadVoice(HttpServletRequest request, String serverId) {
		if (StringUtils.isEmpty(serverId)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		/*Member me = ServletUtil.getMember(request);
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}*/
		Map<String, Object> rsmap = new HashMap<String, Object>();
		String wx_secret = ApplicationPath.getParameter("wx_secret");
		String wx_appid = ApplicationPath.getParameter("wx_appid");
		AccessToken a = WeixinUtil.getAccessToken(wx_appid, wx_secret);

		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + a.getToken() + "&media_id="
				+ serverId;
		System.out.println("url+:" + url);
		BufferedInputStream in = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			in = new BufferedInputStream(http.getInputStream());

			// 获取所有响应头字段
			Map<String, List<String>> map = http.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			List<String> contentDisposition = (List<String>) map.get("Content-disposition");
			String fileName = contentDisposition.get(0);
			fileName = fileName.substring(fileName.indexOf("filename=") + 10);
			fileName = fileName.substring(0, fileName.indexOf("\""));
			String ext = fileName.substring(fileName.lastIndexOf("."));

			String savePath = request.getSession().getServletContext().getRealPath("/") + "upload/wx/yp/"
					+ UUID.randomUUID() + ext;
			String targetPath = request.getSession().getServletContext().getRealPath("/") + "upload/wx/yp/"
					+ UUID.randomUUID() + ".mp3";
			UploadAction.saveFile(savePath, in);
			// 转换类型
			boolean bo = UploadAction.change(savePath, targetPath);
			if(!bo){
				rsmap.put("code", "999");
				rsmap.put("msg", "上传语音信息失败[格式转换]");
				rsmap.put("dataobj", "");
			}
			InputStream in2 = new FileInputStream(new File(targetPath));
			// 定义 BufferedReader输入流来读取URL的响应

			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();

			String picPath = FileUploadUtil.uploadFileInputStream(in2, ".mp3", "wx/yp");
			if (picPath != "" && !"".equals(picPath)) {
				Map<String, Object> dataobj = new HashMap<String, Object>();
				dataobj.put("pic_path", picPath);
				dataobj.put("img_url", ossurl + picPath);
				UploadAction.deleteFile(savePath);
				UploadAction.deleteFile(targetPath);
				rsmap.put("code", "0");
				rsmap.put("msg", "上传成功！");
				rsmap.put("dataobj", dataobj);
				//删除临时文件
				
				
			} else {
				rsmap.put("code", "999");
				rsmap.put("msg", "上传语音信息失败");
				rsmap.put("dataobj", "");
			}
			http.disconnect();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();

			rsmap.put("code", "999");
			rsmap.put("msg", "发送GET请求出现异常！");
			rsmap.put("dataobj", "");
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rsmap;
	}

	private static boolean change(String sourcePath, String targetPath) {
		File source = new File(sourcePath);
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();

		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		try {
			encoder.encode(source, target, attrs);
			return true;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static InputStream getInputStream(String accessToken, String mediaId) {
		InputStream is = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id="
				+ mediaId;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			// 获取文件转化为byte流
			is = http.getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;

	}

	public static void main(String[] args) {
		Map rsmap = new HashMap();
		String wx_secret = ApplicationPath.getParameter("wx_secret");
		String wx_corpid = ApplicationPath.getParameter("wx_corpid");
		AccessToken a = WeixinUtil.getAccessToken(wx_corpid, wx_secret);
		String serverId = "0V5oAKc1ny7Na6AWOyF5R_89AszP9gPoiNQ2yLwkX5YZ-qph3WjixMq5Vrk2XGtj";
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + a.getToken() + "&media_id="
				+ serverId;
		BufferedInputStream in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			List<String> contentDisposition = (List<String>) map.get("Content-disposition");
			String fileName = contentDisposition.get(0);
			fileName = fileName.substring(fileName.indexOf("filename=") + 10);
			fileName = fileName.substring(0, fileName.indexOf("\""));
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

			// 定义 BufferedReader输入流来读取URL的响应

			OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
			String ossurl = ossConfig.getOss_getUrl();

			in = new BufferedInputStream(conn.getInputStream());
			String picPath = FileUploadUtil.uploadFileInputStream(in, ext, "wx");
			if (picPath != "" && !"".equals(picPath)) {
				Map dataobj = new HashMap();
				dataobj.put("pic_path", picPath);
				dataobj.put("img_url", ossurl + picPath);

				rsmap.put("code", "0");
				rsmap.put("msg", "上传成功！");
				rsmap.put("dataobj", dataobj);
			} else {
				rsmap.put("code", "999");
				rsmap.put("msg", "上传图片信息失败");
				rsmap.put("dataobj", "");
			}
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();

			rsmap.put("code", "999");
			rsmap.put("msg", "发送GET请求出现异常！");
			rsmap.put("dataobj", "");
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void saveFile(String path, InputStream inputStream) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		if (!file.getParentFile().exists()) {
			mkdir(file.getParentFile());
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			byte buffer[] = new byte[4 * 1024];
			while ((inputStream.read(buffer)) != -1) {
				fos.write(buffer);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 递归创建目录
	 * 
	 * @param file
	 */
	public static void mkdir(File file) {
		if (file.exists()) {
			return;
		}
		if (!file.getParentFile().exists()) {
			mkdir(file.getParentFile());
		}
		file.mkdir();
	}
	
	/**
	 * 删除文件
	 * @param path
	 */
	public static void deleteFile(String path) {
		// System.out.println("delete-------------------"+path);
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}
}
