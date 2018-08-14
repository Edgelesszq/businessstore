package com.businessstore.model;

import java.io.Serializable;

public class LoginResult implements Serializable{
    private String sellerId;   //卖家id
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

    private int numActiva;              //账号状态   0-未激活,1-激活,2-冻结

    private String verifiCode;    //验证码

    public LoginResult(String sellerId, String appKey, String sellerName, String sellerHead, String sellerNum, String sellerDomain, String sellerTel, String telOpen, String sellerCountry, String shopName, String stateName, String cityName, String shopTime, String detailedAddress, int numActiva, String verifiCode) {
        this.sellerId = sellerId;
        this.appKey = appKey;
        this.sellerName = sellerName;
        this.sellerHead = sellerHead;
        this.sellerNum = sellerNum;
        this.sellerDomain = sellerDomain;
        this.sellerTel = sellerTel;
        this.telOpen = telOpen;
        this.sellerCountry = sellerCountry;
        this.shopName = shopName;
        this.stateName = stateName;
        this.cityName = cityName;
        this.shopTime = shopTime;
        this.detailedAddress = detailedAddress;
        this.numActiva = numActiva;
        this.verifiCode = verifiCode;
    }

    public String getVerifiCode() {
        return verifiCode;
    }

    public void setVerifiCode(String verifiCode) {
        this.verifiCode = verifiCode;
    }

    public LoginResult() {
    }

    public LoginResult(String sellerId, String sellerNum) {
        this.sellerId = sellerId;
        this.sellerNum = sellerNum;
    }



    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
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
}
