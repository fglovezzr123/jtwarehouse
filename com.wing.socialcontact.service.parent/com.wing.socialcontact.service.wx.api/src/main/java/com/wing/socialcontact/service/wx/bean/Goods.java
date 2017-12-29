package com.wing.socialcontact.service.wx.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author devil
 */
public class Goods implements Serializable {

    private static final long serialVersionUID = -4644364148699883829L;

    private Integer id;

    private Date addTime;

    private boolean deleteStatus;

    private String seo_keywords;

    private String seo_description;

    private String goods_name;

    private String activity_title;

    private String goodsBrand;                     //给手机端展示品牌

    private String inventory_type;

    private String goods_serial;

    private String goods_fee;

    private String goods_details;

    private BigDecimal goods_price;                             //商品原价

    private BigDecimal store_price;                             //会员价格

    private int goods_inventory;

    private int goods_salenum;

    private BigDecimal goods_weight;

    private BigDecimal goods_volume;

    private boolean store_recommend;

    private Date store_recommend_time;

    private boolean goods_recommend;

    private int goods_click;

    private int goods_collect;

    private int goods_status;                           //-2 违规下架     0上架商品   1仓库商品

    private Date goods_seller_time;

    private int goods_transfee;                         //运费：1 卖家承担 0 买家承担

    private String transport_id;                        //运费模版ID

    private String goods_property;

    private String goods_inventory_detail;

    private int ztc_status;

    private int ztc_pay_status;

    private int ztc_price;

    private BigDecimal goods_length;                //商品长度

    private BigDecimal goods_width;                 //商品宽度

    private BigDecimal goods_height;                //商品高度

    private BigDecimal goods_volume_weight;         //商品体积重量

    private BigDecimal east_postage;                //到东马邮费

    private BigDecimal west_postage;                //到西马邮费

    private BigDecimal abroad_postage;              //到国外重量

    private int lines_witch;                        //是否开通线下支付 1 是  0 否

    private int favorite;                           //是否关注

    private BigDecimal activity_price;              //活动价格

    private int ztc_dredge_price;

    private Date ztc_apply_time;

    private Date ztc_begin_time;

    private int ztc_gold;

    private int ztc_click_num;

    private int group_buy;

    private int activity_status;

    private BigDecimal goods_current_price;

    private int bargain_status;

    private int delivery_status;

    private int combin_status;

    private Date combin_begin_time;

    private Date combin_end_time;

    private BigDecimal combin_price;

    private int goods_choice_type;                              //0：实物商品，1：虚拟商品

    private Integer goods_main_photo_id;

    private String imgName;
    private String imgPath;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getSeo_keywords() {
        return seo_keywords;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }

    public String getSeo_description() {
        return seo_description;
    }

    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public String getInventory_type() {
        return inventory_type;
    }

    public void setInventory_type(String inventory_type) {
        this.inventory_type = inventory_type;
    }

    public String getGoods_serial() {
        return goods_serial;
    }

    public void setGoods_serial(String goods_serial) {
        this.goods_serial = goods_serial;
    }

    public String getGoods_fee() {
        return goods_fee;
    }

    public void setGoods_fee(String goods_fee) {
        this.goods_fee = goods_fee;
    }

    public String getGoods_details() {
        return goods_details;
    }

    public void setGoods_details(String goods_details) {
        this.goods_details = goods_details;
    }

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public BigDecimal getStore_price() {
        return store_price;
    }

    public void setStore_price(BigDecimal store_price) {
        this.store_price = store_price;
    }

    public int getGoods_inventory() {
        return goods_inventory;
    }

    public void setGoods_inventory(int goods_inventory) {
        this.goods_inventory = goods_inventory;
    }

    public int getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(int goods_salenum) {
        this.goods_salenum = goods_salenum;
    }

    public BigDecimal getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(BigDecimal goods_weight) {
        this.goods_weight = goods_weight;
    }

    public BigDecimal getGoods_volume() {
        return goods_volume;
    }

    public void setGoods_volume(BigDecimal goods_volume) {
        this.goods_volume = goods_volume;
    }

    public boolean isStore_recommend() {
        return store_recommend;
    }

    public void setStore_recommend(boolean store_recommend) {
        this.store_recommend = store_recommend;
    }

    public Date getStore_recommend_time() {
        return store_recommend_time;
    }

    public void setStore_recommend_time(Date store_recommend_time) {
        this.store_recommend_time = store_recommend_time;
    }

    public boolean isGoods_recommend() {
        return goods_recommend;
    }

    public void setGoods_recommend(boolean goods_recommend) {
        this.goods_recommend = goods_recommend;
    }

    public int getGoods_click() {
        return goods_click;
    }

    public void setGoods_click(int goods_click) {
        this.goods_click = goods_click;
    }

    public int getGoods_collect() {
        return goods_collect;
    }

    public void setGoods_collect(int goods_collect) {
        this.goods_collect = goods_collect;
    }

    public int getGoods_status() {
        return goods_status;
    }

    public void setGoods_status(int goods_status) {
        this.goods_status = goods_status;
    }

    public Date getGoods_seller_time() {
        return goods_seller_time;
    }

    public void setGoods_seller_time(Date goods_seller_time) {
        this.goods_seller_time = goods_seller_time;
    }

    public int getGoods_transfee() {
        return goods_transfee;
    }

    public void setGoods_transfee(int goods_transfee) {
        this.goods_transfee = goods_transfee;
    }

    public String getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(String transport_id) {
        this.transport_id = transport_id;
    }

    public String getGoods_property() {
        return goods_property;
    }

    public void setGoods_property(String goods_property) {
        this.goods_property = goods_property;
    }

    public String getGoods_inventory_detail() {
        return goods_inventory_detail;
    }

    public void setGoods_inventory_detail(String goods_inventory_detail) {
        this.goods_inventory_detail = goods_inventory_detail;
    }

    public int getZtc_status() {
        return ztc_status;
    }

    public void setZtc_status(int ztc_status) {
        this.ztc_status = ztc_status;
    }

    public int getZtc_pay_status() {
        return ztc_pay_status;
    }

    public void setZtc_pay_status(int ztc_pay_status) {
        this.ztc_pay_status = ztc_pay_status;
    }

    public int getZtc_price() {
        return ztc_price;
    }

    public void setZtc_price(int ztc_price) {
        this.ztc_price = ztc_price;
    }

    public BigDecimal getGoods_length() {
        return goods_length;
    }

    public void setGoods_length(BigDecimal goods_length) {
        this.goods_length = goods_length;
    }

    public BigDecimal getGoods_width() {
        return goods_width;
    }

    public void setGoods_width(BigDecimal goods_width) {
        this.goods_width = goods_width;
    }

    public BigDecimal getGoods_height() {
        return goods_height;
    }

    public void setGoods_height(BigDecimal goods_height) {
        this.goods_height = goods_height;
    }

    public BigDecimal getGoods_volume_weight() {
        return goods_volume_weight;
    }

    public void setGoods_volume_weight(BigDecimal goods_volume_weight) {
        this.goods_volume_weight = goods_volume_weight;
    }

    public BigDecimal getEast_postage() {
        return east_postage;
    }

    public void setEast_postage(BigDecimal east_postage) {
        this.east_postage = east_postage;
    }

    public BigDecimal getWest_postage() {
        return west_postage;
    }

    public void setWest_postage(BigDecimal west_postage) {
        this.west_postage = west_postage;
    }

    public BigDecimal getAbroad_postage() {
        return abroad_postage;
    }

    public void setAbroad_postage(BigDecimal abroad_postage) {
        this.abroad_postage = abroad_postage;
    }

    public int getLines_witch() {
        return lines_witch;
    }

    public void setLines_witch(int lines_witch) {
        this.lines_witch = lines_witch;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public BigDecimal getActivity_price() {
        return activity_price;
    }

    public void setActivity_price(BigDecimal activity_price) {
        this.activity_price = activity_price;
    }

    public int getZtc_dredge_price() {
        return ztc_dredge_price;
    }

    public void setZtc_dredge_price(int ztc_dredge_price) {
        this.ztc_dredge_price = ztc_dredge_price;
    }

    public Date getZtc_apply_time() {
        return ztc_apply_time;
    }

    public void setZtc_apply_time(Date ztc_apply_time) {
        this.ztc_apply_time = ztc_apply_time;
    }

    public Date getZtc_begin_time() {
        return ztc_begin_time;
    }

    public void setZtc_begin_time(Date ztc_begin_time) {
        this.ztc_begin_time = ztc_begin_time;
    }

    public int getZtc_gold() {
        return ztc_gold;
    }

    public void setZtc_gold(int ztc_gold) {
        this.ztc_gold = ztc_gold;
    }

    public int getZtc_click_num() {
        return ztc_click_num;
    }

    public void setZtc_click_num(int ztc_click_num) {
        this.ztc_click_num = ztc_click_num;
    }

    public int getGroup_buy() {
        return group_buy;
    }

    public void setGroup_buy(int group_buy) {
        this.group_buy = group_buy;
    }

    public int getActivity_status() {
        return activity_status;
    }

    public void setActivity_status(int activity_status) {
        this.activity_status = activity_status;
    }

    public BigDecimal getGoods_current_price() {
        return goods_current_price;
    }

    public void setGoods_current_price(BigDecimal goods_current_price) {
        this.goods_current_price = goods_current_price;
    }

    public int getBargain_status() {
        return bargain_status;
    }

    public void setBargain_status(int bargain_status) {
        this.bargain_status = bargain_status;
    }

    public int getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(int delivery_status) {
        this.delivery_status = delivery_status;
    }

    public int getCombin_status() {
        return combin_status;
    }

    public void setCombin_status(int combin_status) {
        this.combin_status = combin_status;
    }

    public Date getCombin_begin_time() {
        return combin_begin_time;
    }

    public void setCombin_begin_time(Date combin_begin_time) {
        this.combin_begin_time = combin_begin_time;
    }

    public Date getCombin_end_time() {
        return combin_end_time;
    }

    public void setCombin_end_time(Date combin_end_time) {
        this.combin_end_time = combin_end_time;
    }

    public BigDecimal getCombin_price() {
        return combin_price;
    }

    public void setCombin_price(BigDecimal combin_price) {
        this.combin_price = combin_price;
    }

    public int getGoods_choice_type() {
        return goods_choice_type;
    }

    public void setGoods_choice_type(int goods_choice_type) {
        this.goods_choice_type = goods_choice_type;
    }

    public Integer getGoods_main_photo_id() {
        return goods_main_photo_id;
    }

    public void setGoods_main_photo_id(Integer goods_main_photo_id) {
        this.goods_main_photo_id = goods_main_photo_id;
    }
}
