package com.wing.socialcontact.front.action;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.IpInfo;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.front.util.ConstantDefinition;
import com.wing.socialcontact.front.util.WxAutoReplyUtil;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.synchronization.TjySign;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.*;
import com.wing.socialcontact.util.im.IMUtil;
import com.wing.socialcontact.util.pojo.AccessToken;
import com.wing.socialcontact.util.wxres.AesException;
import com.wing.socialcontact.util.wxres.WXBizMsgCrypt;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 三维码功能
 *
 * @author wangyansheng
 */
@Controller
@RequestMapping("/m/threeDCode")
public class ThreeDCodeAction extends BaseAction {

    @Autowired
    private ITjyUserService tjyUserService;

    @Autowired
    private IListValuesService listValuesService;

    @RequestMapping("index")
    public String index(HttpServletRequest req) {
        if (!ServletUtil.isLogin(req)) {
            return "login";
        }
        Member member = (Member) req.getSession().getAttribute("me");
        TjyUser tjyUser = tjyUserService.selectByPrimaryKey(member.getId() + "");
        // 获取职位
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tjyUser.getJob())) {
            ListValues job = listValuesService.selectByPrimaryKey(tjyUser.getJob());
            tjyUser.setJob(job != null ? job.getListValue() : tjyUser.getJob());
        }

        String phone = tjyUser.getMobile() == null ? "" : tjyUser.getMobile();
        String token = phone + tjyUser.getReconStatus() + tjyUser.getId() + String.valueOf(System.currentTimeMillis()).substring(0, 10) + "X4F4S1S34FDFSFSF";
        String url = ApplicationPath.getParameter("3weima_url");

        try {
            return "redirect:"+ url +"?name=" + URLEncoder.encode(tjyUser.getTrueName(), "UTF-8") + "&phone=" + phone + "&company=" + URLEncoder.encode(tjyUser.getComName(), "UTF-8")
                    + "&position=" + URLEncoder.encode(tjyUser.getJob(), "UTF-8") + "&user_id=" + tjyUser.getId() + "&user_status=" +
                    tjyUser.getReconStatus() + "&time=" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + "&token=" + MD5Util.to_MD5(token);
        } catch (UnsupportedEncodingException e) {
            return "redirect:" + url + "?name=&phone=" + phone + "&company=&position=&user_id=" + tjyUser.getId() + "&user_status=" +
                    tjyUser.getReconStatus() + "&time=" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + "&token=" + MD5Util.to_MD5(token);
        }

    }
}
