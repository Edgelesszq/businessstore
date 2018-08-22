package com.businessstore.model;

import java.io.Serializable;

public class LoginResult implements Serializable{
    private int sellerId;   //卖家id
    private String appKey;      //appkey
    private String sellerName;      //卖家名字
    private String sellerHead;      //卖家头像
    private String sellerNum;       //卖家账号
    private String sellerDomain;     //卖家域名
    private String sellerTel;       //卖家电话
    private String telOpen;         //电话是否公开
    private String sellerCountry;       //卖家国家
    private String shopName;        //店铺名称
    private String stateName;       //州名
    private String cityName;        //城市名字
    private String shopTime;        //工作时间
    private String detailedAddress;     //详细地址
    private String zipCode;         //zipCode
    private String createAt;        //创建时间
    private String updateAt;        //更新时间
    private int numActiva;         //账号状态   0-未激活,1-激活,2-冻结
    private String verifiCode;    //验证码





    public String getVerifiCode() {
        return verifiCode;
    }

    public void setVerifiCode(String verifiCode) {
        this.verifiCode = verifiCode;
    }

    public LoginResult() {
    }

    public LoginResult(int sellerId, String sellerNum) {
        this.sellerId = sellerId;
        this.sellerNum = sellerNum;
    }



    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerHead() {
        return sellerHead;
    }

    public void setSellerHead(String sellerHead) {
        this.sellerHead = sellerHead;
    }

    public String getSellerNum() {
        return sellerNum;
    }

    public void setSellerNum(String sellerNum) {
        this.sellerNum = sellerNum;
    }

    public String getSellerDomain() {
        return sellerDomain;
    }

    public void setSellerDomain(String sellerDomain) {
        this.sellerDomain = sellerDomain;
    }

    public String getSellerTel() {
        return sellerTel;
    }

    public void setSellerTel(String sellerTel) {
        this.sellerTel = sellerTel;
    }

    public String getTelOpen() {
        return telOpen;
    }

    public void setTelOpen(String telOpen) {
        this.telOpen = telOpen;
    }

    public String getSellerCountry() {
        return sellerCountry;
    }

    public void setSellerCountry(String sellerCountry) {
        this.sellerCountry = sellerCountry;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShopTime() {
        return shopTime;
    }

    public void setShopTime(String shopTime) {
        this.shopTime = shopTime;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public int getNumActiva() {
        return numActiva;
    }

    public void setNumActiva(int numActiva) {
        this.numActiva = numActiva;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }


}
