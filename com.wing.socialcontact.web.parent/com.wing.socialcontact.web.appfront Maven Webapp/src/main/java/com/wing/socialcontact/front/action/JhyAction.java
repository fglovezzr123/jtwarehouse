package com.wing.socialcontact.front.action;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.wing.socialcontact.front.util.*;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.StringUtil;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 聚合页面控制器
 * 
 * @ClassName: JhyAction
 * @Description:
 * @author: zengmin
 * @date:2017年4月2日 下午5:57:56
 */
@Controller
@RequestMapping("/m/polymerizationPage")
public class JhyAction extends BaseAppAction
{

	@Autowired
	private IPageAggregateService pageAggregateService;

	/**
	 * 获取聚合页面json
	 * 
	 * @Description:
	 * @param request
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月18日 上午9:59:48
	 */
	@RequestMapping(value = "getPage")
	public @ResponseBody ResponseReport getPage( @RequestBody RequestReport rr, HttpServletRequest request) {
        ValidateModel validateModel = ApiCommonUtil.paramValidate(rr, "pageNum", "id");
        if (validateModel.getCode() != 0) {
            return super.getAjaxResult(rr, ResponseCode.NotSupport, validateModel.getMsg(), validateModel.getObject());
        }
        String id = rr.getDataValue("id");
        String dataId = rr.getDataValue("id");
        //先获取页面
        String keyId = ApplicationPath.getParameter("jhy.key." + id);
        if (!StringUtil.isEmpty(keyId)) {
            id = keyId;
        }
        PageAggregate page = pageAggregateService.selectPageById(id);
        if (null == page) {
            return super.getAjaxResult(rr, ResponseCode.NotSupport, "参数错误", "");
        }
        Map data = new HashMap(6);
        OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossurl = ossConfig.getOss_getUrl();
        data.put("ossurl", ossurl);
		if ("index".equals(dataId)) {
            return getIndexData(page, rr, data);
        } else {
            data.put("page", page);
            return super.getSuccessAjaxResult(rr,"操作成功!", data);
		}
	}

	/**
	 * @author devil
	 * @desicription: 获取首页聚合页数据
	 * @date Created in 2017/12/12 9:44
	 */
	private ResponseReport getIndexData(PageAggregate page, RequestReport rr, Map resultMap) {
		try {
			String userId = rr.getUserProperty().getUserId();
			String pageNum = rr.getDataValue("pageNum");
			//第一页需要返回页面快捷入口、banner等内容
			if ("1".equals(pageNum)) {
				//获取快捷入口
				List<ReturnElement> returnPageQuickEntryList = setQuickEntryList(page.getPageQuickEntryList());
				resultMap.put("pageQuickEntryList", returnPageQuickEntryList);
				//获取banner
				List<ReturnElement> returnPageBannerList = setBannerList(page.getBannerList());
				resultMap.put("pageBannerList", returnPageBannerList);
				//获取快速导航
				List<ReturnElement> returnNaviList = setNaviList(page.getPageColumnList());
				resultMap.put("naviList", returnNaviList);
			}
			//拼装page数据
			page.setBannerList(null);
			page.setPageQuickEntryList(null);
			page.setPageColumnJsonStr(null);
			List<PageColumn> pageColumnList = page.getPageColumnList();
			page.setPageColumnList(null);
			resultMap.put("page", page);
			//获取栏目数据
			List<Map<String, Object>> pageColumnMapList = new ArrayList<>();
			for (PageColumn pageColumn : pageColumnList) {
				if (pageColumn.getColumnStatus() == 0) {
					List<ReturnElement> returnElementList = new ArrayList<>();
					Map<String, Object> pageColumnMap = new HashMap<>(4);
					pageColumnMap.put("columnName", pageColumn.getColumnName());
					//普通栏目为首页key
					if (pageColumn.getColumnType() == 1) {
						pageColumnMap.put("elementKey", "homePage");
					} else {
						pageColumnMap.put("elementKey", pageColumn.getElementKey());
					}
					pageColumnMap.put("id", pageColumn.getId());
					pageColumnMap.put("jumpType", "native");
					pageColumnMap.put("moreUrl", pageColumn.getElementKey());
					if (!CollectionUtils.isEmpty(pageColumn.getElementList())) {
						for (PageElement element : pageColumn.getElementList()) {
							ReturnElement returnElement = new ReturnElement();
							returnElement.setTitleOne(element.getTitleOne());
							returnElement.setTitleTwo(element.getTitleTwo());
							returnElement.setId(element.getContentId());
							returnElement.setImgUrl(element.getImgUrl());
							returnElement.setElementKey(element.getElementKey());
							returnElement.setJumpType("1".equals(element.getJumpType()) ? "native" : "h5");
							returnElementList.add(returnElement);
						}
					}
					pageColumnMap.put("elementList", returnElementList);
					pageColumnMapList.add(pageColumnMap);
				}
			}
			resultMap.put("pageColumnList", pageColumnMapList);
			return super.getSuccessAjaxResult(rr, "获取成功!", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return super.getAjaxResult(rr, ResponseCode.Error, "获取首页聚合页数据失败！", null);
		}
	}

	/**
	 * @author devil
	 * @desicription: 设置快速入口集合
	 * @date Created in 2017/12/12 14:32
	 */
	private List<ReturnElement> setQuickEntryList(List<PageQuickEntry> quickEntryList) throws MalformedURLException {
		List<ReturnElement> returnPageQuickEntryList = new ArrayList<>();
		URL hostUrl = new URL(ConfigUtil.DOMAIN);
		String h5Url =  "https://" + hostUrl.getHost() + "/phoneh5_zh/";
		for (PageQuickEntry pageQuickEntry : quickEntryList) {
			ReturnElement pageQuick = new ReturnElement();
			pageQuick.setTitleOne(pageQuickEntry.getName());
			if ("买吧".equals(pageQuickEntry.getName()) || "卖吧".equals(pageQuickEntry.getName())) {
				pageQuick.setJumpType("h5");
				pageQuick.setElementUrl(h5Url + pageQuickEntry.getLinkUrl());
			} else {
				pageQuick.setJumpType("native");
				pageQuick.setElementUrl(pageQuickEntry.getLinkUrl());
			}
			pageQuick.setImgUrl(pageQuickEntry.getImgUrl());
			returnPageQuickEntryList.add(pageQuick);
		}
		return returnPageQuickEntryList;
	}

    /**
     * @author devil
     * @desicription: 设置banner集合
     * @date Created in 2017/11/14 10:39
     */
    private List<ReturnElement> setBannerList(List<Banner> bannerList) {
        List<ReturnElement> returnPageBannerList = new ArrayList<>();
        for (Banner banner : bannerList) {
            ReturnElement returnBanner = new ReturnElement();
            if (banner.getJumpType()==null || "0".equals(banner.getJumpType())) {
            } else if ("1".equals(banner.getJumpType())) {
                returnBanner.setJumpType("h5");
            } else if ("2".equals(banner.getJumpType())) {
                returnBanner.setJumpType("native");
            }
            returnBanner.setElementUrl(banner.getJumpUrl());
            returnBanner.setImgUrl(banner.getPicPath());
            returnPageBannerList.add(returnBanner);
        }
        return returnPageBannerList;
    }

    /**
     * @author devil
     * @desicription: 设置快速导航集合
     * @date Created in 2017/12/12 14:36
     */
	private List<ReturnElement> setNaviList(List<PageColumn> pageColumnList) {
		List<ReturnElement> returnNaviList = new ArrayList<>();
		for (PageColumn pageColumn : pageColumnList) {
			if (pageColumn.getColumnStatus() == 1) {
				ReturnElement returnElement = new ReturnElement();
				returnElement.setId(pageColumn.getId());
				returnElement.setTitleOne(pageColumn.getColumnName());
				returnElement.setJumpType("native");
				returnNaviList.add(returnElement);
			}
		}
		return returnNaviList;
	}

}
