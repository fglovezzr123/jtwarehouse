package com.wing.socialcontact.front.util;

import java.io.Serializable;

/**
 *
 * @author devil
 * @desicription: 返回元素
 * @date Created in 2017/11/1 13:18
 *
 */
public class ReturnElement implements Serializable {

    private String id;

    /**
     * 一级标题
     */
    private String titleOne;

    /**
     * 二级标题
     */
    private String titleTwo;

    /**
     * 三级标题
     */
    private String titleThree;

    /**
     * 四级标题
     */
    private String titleFour;

    /**
     * 内容
     */
    private String content;

    /**
     * 人数
     */
    private String countNum;

    /**
     * 悬赏j币
     */
    private String reward;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 元素url
     */
    private String elementUrl;

    /**
     * 元素key
     */
    private String elementKey;

    /**
     * h5, native
     */
    private String jumpType;

    /**
     * 是否收藏
     */
    private int flag;

    /**
     * 热度
     */
    private String hotDegree;

    /**
     * 支付金额
     */
    private String payAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleOne() {
        return titleOne;
    }

    public void setTitleOne(String titleOne) {
        this.titleOne = titleOne;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getElementUrl() {
        return elementUrl;
    }

    public void setElementUrl(String elementUrl) {
        this.elementUrl = elementUrl;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

	public String getTitleTwo()
	{
		return titleTwo;
	}

	public void setTitleTwo(String titleTwo)
	{
		this.titleTwo = titleTwo;
	}

	public String getTitleThree()
	{
		return titleThree;
	}

	public void setTitleThree(String titleThree)
	{
		this.titleThree = titleThree;
	}

	public String getTitleFour()
	{
		return titleFour;
	}

	public void setTitleFour(String titleFour)
	{
		this.titleFour = titleFour;
	}

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getHotDegree() {
        return hotDegree;
    }

    public void setHotDegree(String hotDegree) {
        this.hotDegree = hotDegree;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getElementKey() {
        return elementKey;
    }

    public void setElementKey(String elementKey) {
        this.elementKey = elementKey;
    }
}