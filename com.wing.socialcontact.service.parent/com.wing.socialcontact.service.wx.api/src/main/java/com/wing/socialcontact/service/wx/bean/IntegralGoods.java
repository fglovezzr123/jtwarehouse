 package com.wing.socialcontact.service.wx.bean;

 import java.io.Serializable;
 import java.math.BigDecimal;
 import java.util.Date;

 /**
  * 爆品
  * @author devil
  */
 public class IntegralGoods implements Serializable {

    private Integer id;
    private Date addTime;
    private boolean deleteStatus;
    private String ig_goods_name;
    private BigDecimal ig_goods_price;
    private int ig_goods_integral;
    private String ig_goods_sn;
    private int ig_goods_count;
    private String ig_goods_tag;
    private boolean ig_limit_type;
    private int ig_limit_count;
    private int ig_transfee_type;
    private BigDecimal ig_transfee;
    private boolean ig_time_type;
    private Date ig_begin_time;
    private Date ig_end_time;
    private boolean ig_show;
    private boolean ig_recommend;
    private int ig_sequence;
    private String ig_seo_keywords;
    private String ig_seo_description;
    private String ig_content;
    private int ig_exchange_count;
    private int ig_click_count;
    private Integer ig_goods_img_id;
    private String url;
    //图片
    private String imgName;
    private String imgPath;

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

     public String getIg_goods_name() {
         return ig_goods_name;
     }

     public void setIg_goods_name(String ig_goods_name) {
         this.ig_goods_name = ig_goods_name;
     }

     public BigDecimal getIg_goods_price() {
         return ig_goods_price;
     }

     public void setIg_goods_price(BigDecimal ig_goods_price) {
         this.ig_goods_price = ig_goods_price;
     }

     public int getIg_goods_integral() {
         return ig_goods_integral;
     }

     public void setIg_goods_integral(int ig_goods_integral) {
         this.ig_goods_integral = ig_goods_integral;
     }

     public String getIg_goods_sn() {
         return ig_goods_sn;
     }

     public void setIg_goods_sn(String ig_goods_sn) {
         this.ig_goods_sn = ig_goods_sn;
     }

     public int getIg_goods_count() {
         return ig_goods_count;
     }

     public void setIg_goods_count(int ig_goods_count) {
         this.ig_goods_count = ig_goods_count;
     }

     public String getIg_goods_tag() {
         return ig_goods_tag;
     }

     public void setIg_goods_tag(String ig_goods_tag) {
         this.ig_goods_tag = ig_goods_tag;
     }

     public boolean isIg_limit_type() {
         return ig_limit_type;
     }

     public void setIg_limit_type(boolean ig_limit_type) {
         this.ig_limit_type = ig_limit_type;
     }

     public int getIg_limit_count() {
         return ig_limit_count;
     }

     public void setIg_limit_count(int ig_limit_count) {
         this.ig_limit_count = ig_limit_count;
     }

     public int getIg_transfee_type() {
         return ig_transfee_type;
     }

     public void setIg_transfee_type(int ig_transfee_type) {
         this.ig_transfee_type = ig_transfee_type;
     }

     public BigDecimal getIg_transfee() {
         return ig_transfee;
     }

     public void setIg_transfee(BigDecimal ig_transfee) {
         this.ig_transfee = ig_transfee;
     }

     public boolean isIg_time_type() {
         return ig_time_type;
     }

     public void setIg_time_type(boolean ig_time_type) {
         this.ig_time_type = ig_time_type;
     }

     public Date getIg_begin_time() {
         return ig_begin_time;
     }

     public void setIg_begin_time(Date ig_begin_time) {
         this.ig_begin_time = ig_begin_time;
     }

     public Date getIg_end_time() {
         return ig_end_time;
     }

     public void setIg_end_time(Date ig_end_time) {
         this.ig_end_time = ig_end_time;
     }

     public boolean isIg_show() {
         return ig_show;
     }

     public void setIg_show(boolean ig_show) {
         this.ig_show = ig_show;
     }

     public boolean isIg_recommend() {
         return ig_recommend;
     }

     public void setIg_recommend(boolean ig_recommend) {
         this.ig_recommend = ig_recommend;
     }

     public int getIg_sequence() {
         return ig_sequence;
     }

     public void setIg_sequence(int ig_sequence) {
         this.ig_sequence = ig_sequence;
     }

     public String getIg_seo_keywords() {
         return ig_seo_keywords;
     }

     public void setIg_seo_keywords(String ig_seo_keywords) {
         this.ig_seo_keywords = ig_seo_keywords;
     }

     public String getIg_seo_description() {
         return ig_seo_description;
     }

     public void setIg_seo_description(String ig_seo_description) {
         this.ig_seo_description = ig_seo_description;
     }

     public String getIg_content() {
         return ig_content;
     }

     public void setIg_content(String ig_content) {
         this.ig_content = ig_content;
     }

     public int getIg_exchange_count() {
         return ig_exchange_count;
     }

     public void setIg_exchange_count(int ig_exchange_count) {
         this.ig_exchange_count = ig_exchange_count;
     }

     public int getIg_click_count() {
         return ig_click_count;
     }

     public void setIg_click_count(int ig_click_count) {
         this.ig_click_count = ig_click_count;
     }

     public String getUrl() {
         return url;
     }

     public void setUrl(String url) {
         this.url = url;
     }

     public Integer getIg_goods_img_id() {
         return ig_goods_img_id;
     }

     public void setIg_goods_img_id(Integer ig_goods_img_id) {
         this.ig_goods_img_id = ig_goods_img_id;
     }

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
 }




