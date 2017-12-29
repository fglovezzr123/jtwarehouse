package com.wing.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryClass;
import org.com.wing.enterprise.bean.EntryImgs;
import org.com.wing.enterprise.bean.EntryPrise;
import org.com.wing.enterprise.bean.EntryTags;
import org.com.wing.enterprise.service.IEntryPriseService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryClassDao;
import com.wing.enterprise.service.dao.EntryImgsDao;
import com.wing.enterprise.service.dao.EntryPriseDao;
import com.wing.enterprise.service.dao.EntryTagsDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.util.QfyConstants;
import com.wing.socialcontact.util.ServletUtil;
/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 下午5:02:13
 */
@Service
public class EntryPriseServiceImpl extends BaseServiceImpl<EntryPrise> implements IEntryPriseService {

	@Resource
    private EntryPriseDao entryPriseDao;
	@Resource
    private EntryTagsDao entryTagsDao;
	@Resource
    private EntryClassDao entryClassDao;
	@Resource
	private EntryImgsDao entryImgsDao;
	
	

    @Override
    public DataGrid selEntryPrises(PageParam param, EntryPrise entryPrise,String searchName) {
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("name", entryPrise.getName());
        parm.put("title", entryPrise.getTitle());
        parm.put("ssUserId", entryPrise.getSsUserId());
        parm.put("createTime", entryPrise.getCreateTime());
        parm.put("isGood", entryPrise.getIsGood());
        parm.put("searchName", searchName);
        
        List lst = entryPriseDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        return data;
    }
    public List selEntryPrises(EntryPrise entryPrise){
        Map parm = new HashMap();
        parm.put("name", entryPrise.getName());
        parm.put("title", entryPrise.getTitle());
        parm.put("ssUserId", entryPrise.getSsUserId());
        parm.put("createTime", entryPrise.getCreateTime());
        parm.put("isGood", entryPrise.getIsGood());
        
        List lst = entryPriseDao.selectByParam(parm);
        return lst;
    }
    public DataGrid selStaticEntry(PageParam param,EntryPrise entryPrise){
        
        DataGrid data= new DataGrid();
        
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("name", entryPrise.getName());
        parm.put("title", entryPrise.getTitle());
        parm.put("ssUserId", entryPrise.getSsUserId());
        
        List lst = entryPriseDao.selStaticEntry(parm);
        
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        return data;
    }
    public List<Map> selStaticEntryExport(EntryPrise entryPrise){
        Map parm = new HashMap();
        parm.put("name", entryPrise.getName());
        parm.put("title", entryPrise.getTitle());
        parm.put("ssUserId", entryPrise.getSsUserId());
        
        List lst = entryPriseDao.selStaticEntry(parm);
        return lst;
    }
    @Override
    public String addEntryPrise(EntryPrise dto,String reconImg,String bannerImg,String entryClass,String entryTags) {
        String[] reconImgs = reconImg.split(",");
        String[] bannerImgs = bannerImg.split(",");
        String[] entryClasss = entryClass.split(",");
        String[] entryTagss = entryTags.split(",");
        
        String entryPriseId = UUID.randomUUID().toString().replace("-" , "");
        Date currentDate = new Date();
        String currentUserId = ServletUtil.getMember().getId();
        
        //保存企服标签
        EntryTags tag = null;
        for (String tagid : entryTagss) {
            tag = new EntryTags();
            tag.setCreateUserId(currentUserId);
            tag.setCreateTime(currentDate);
            tag.setTagId(tagid);
            tag.setEntryId(entryPriseId);
            entryTagsDao.insert(tag);
        }
        //保存企服分类
        EntryClass ec = null;
        for (String classId : entryClasss) {
            ec = new EntryClass();
            ec.setClassId(classId);
            ec.setCreateTime(currentDate);
            ec.setCreateUserId(currentUserId);
            ec.setEntryId(entryPriseId);
            entryClassDao.insert(ec);
        }
        
        //保存认证图片
        EntryImgs img = null;
        for (String imgPath : reconImgs) {
            img = new EntryImgs();
            img.setCreateTime(currentDate);
            img.setCreateUserId(currentUserId);
            img.setEntryId(entryPriseId);
            img.setType(QfyConstants.QFY_ENTRY_IMG_RECON);
            img.setImgPath(imgPath);
            entryImgsDao.insert(img);
        }
        //保存banner图片
        for(int i=0;i<bannerImgs.length;i++){
            img = new EntryImgs();
            img.setCreateTime(currentDate);
            img.setCreateUserId(currentUserId);
            img.setEntryId(entryPriseId);
            img.setType(QfyConstants.QFY_ENTRY_IMG_BANNER);
            img.setImgPath(bannerImgs[i]);
            img.setSortNum(i);
            if(i==0){
                dto.setBannerPath(bannerImgs[i]);
            }
            entryImgsDao.insert(img);
        }
            
//        //保存banner图片
//        for (String imgPath : bannerImgs) {
//            img = new EntryImgs();
//            img.setCreateTime(currentDate);
//            img.setCreateUserId(currentUserId);
//            img.setEntryId(entryPriseId);
//            img.setType(QfyConstants.QFY_ENTRY_IMG_BANNER);
//            img.setImgPath(imgPath);
//            entryImgsDao.insert(img);
//        }
        //保存企服
        dto.setId(entryPriseId);
        dto.setCreateTime(currentDate);
        dto.setCreateUserId(currentUserId);
        int res = entryPriseDao.insert(dto);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public EntryPrise getEntryPriseByid(String id) {
        return super.selectByPrimaryKeyCache(id, EntryPrise.class);
    }

    @Override
    public String updateEntryPrise(EntryPrise dto,String reconImg,String bannerImg,String entryClass,String entryTags) {
        
        Map bannerMap = new HashMap();
        bannerMap.put("entryId", dto.getId());
        bannerMap.put("type", QfyConstants.QFY_ENTRY_IMG_BANNER);
        List<Map> banners = entryImgsDao.selectByParam(bannerMap);
        for (Map map : banners) {
            entryImgsDao.deleteByPrimaryKey(map.get("id"));
        }
        
        Map reconMap = new HashMap();
        reconMap.put("entryId", dto.getId());
        reconMap.put("type", QfyConstants.QFY_ENTRY_IMG_RECON);
        List<Map> recons = entryImgsDao.selectByParam(reconMap);
        for (Map map : recons) {
            entryImgsDao.deleteByPrimaryKey(map.get("id"));
        }
        
        Map classMap = new HashMap();
        classMap.put("entryId",dto.getId());
        List<Map> entryClasses = entryClassDao.selClassesByEntryId(classMap);
        for (Map map : entryClasses) {
            entryClassDao.deleteByPrimaryKey(map.get("id"));
        }
        
        Map tagMap = new HashMap();
        tagMap.put("entryId",dto.getId());
        List<Map> haveTags = entryTagsDao.selTagsByEntryId(tagMap);
        for (Map map : haveTags) {
            entryTagsDao.deleteByPrimaryKey(map.get("id"));
        }
        
        String[] reconImgs = reconImg.split(",");
        String[] bannerImgs = bannerImg.split(",");
        String[] entryClasss = entryClass.split(",");
        String[] entryTagss = entryTags.split(",");
        
        //保存企服标签
        EntryTags tag = null;
        for (String tagid : entryTagss) {
            tag = new EntryTags();
            tag.setCreateUserId(dto.getCreateUserId());
            tag.setCreateTime(dto.getCreateTime());
            tag.setTagId(tagid);
            tag.setEntryId(dto.getId());
            entryTagsDao.insert(tag);
        }
        //保存企服分类
        EntryClass ec = null;
        for (String classId : entryClasss) {
            ec = new EntryClass();
            ec.setClassId(classId);
            ec.setCreateTime(dto.getCreateTime());
            ec.setCreateUserId(dto.getCreateUserId());
            ec.setEntryId(dto.getId());
            entryClassDao.insert(ec);
        }
        
        //保存认证图片
        EntryImgs img = null;
        for (String imgPath : reconImgs) {
            img = new EntryImgs();
            img.setCreateTime(dto.getCreateTime());
            img.setCreateUserId(dto.getCreateUserId());
            img.setEntryId(dto.getId());
            img.setType(QfyConstants.QFY_ENTRY_IMG_RECON);
            img.setImgPath(imgPath);
            entryImgsDao.insert(img);
        }
       /* //保存banner图片
        for (String imgPath : bannerImgs) {
            img = new EntryImgs();
            img.setCreateTime(dto.getCreateTime());
            img.setCreateUserId(dto.getCreateUserId());
            img.setEntryId(dto.getId());
            img.setType(QfyConstants.QFY_ENTRY_IMG_BANNER);
            img.setImgPath(imgPath);
            entryImgsDao.insert(img);
        }*/
        //保存banner图片
        for(int i=0;i<bannerImgs.length;i++){
            img = new EntryImgs();
            img.setCreateTime(dto.getCreateTime());
            img.setCreateUserId(dto.getCreateUserId());
            img.setEntryId(dto.getId());
            img.setType(QfyConstants.QFY_ENTRY_IMG_BANNER);
            img.setImgPath(bannerImgs[i]);
            img.setSortNum(i);
            if(i==0){
                dto.setBannerPath(bannerImgs[i]);
            }
            entryImgsDao.insert(img);
        }
        //保存企服
        //dto.setBannerPath(bannerImgs[0]);
        
        if(super.updateByPrimaryKeyCache(dto,dto.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }
    @Override
    public String updateEntryPriseByDto(EntryPrise dto) {
        
        if(super.updateByPrimaryKeyCache(dto,dto.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public boolean deleteEntryPrise(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            EntryPrise entryPrise=entryPriseDao.selectByPrimaryKey(id);
            if(entryPrise!=null){
                entryPrise.setStatus(QfyConstants.QFY_STATUS_ISDELETED);
                 if(super.updateByPrimaryKeyCache(entryPrise, id)){
                     
                 }else{
                    flag=false;
                 };
            }
        }
        return flag;
    }
    public DataGrid selEntryPrise(PageParam param,String timeSort,String serviceCountSort,String classId,EntryPrise entryPrise,String prov,String city,String searchName){
        DataGrid data= new DataGrid();
        
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("timeSort", timeSort);
        parm.put("serviceCountSort", serviceCountSort);
        parm.put("classId", classId);
        parm.put("prov", prov);
        parm.put("city", city);
        parm.put("searchName", searchName);
        
        List lst = entryPriseDao.selEntryPrise(parm);
        
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        return data;
        
    }
    public DataGrid selQuickEntrys(PageParam param,String quickDoorId,String classId,String searchName){
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("classId", classId);
        parm.put("quickDoorId", quickDoorId);
        parm.put("searchName", searchName);
        
        List lst = entryPriseDao.selQuickEntrys(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        return data;
    }
    public String[] selCustomer(String id){
        Map parm = new HashMap();
        parm.put("id", id);
        
        List<Map> list = entryPriseDao.selCustomer(parm);
        if(CollectionUtils.isEmpty(list)){
            return new String[]{};
        }else{
            String[] strs = new String[list.size()];
            for(int i=0;i<list.size();i++){
                Map map = list.get(i);
                strs[i] = (String) map.get("id");
            }
            return strs;
        }
        
    }
    
    public List<EntryPrise> selAll(){
        return entryPriseDao.selectAll();
    }
}
