package com.businessstore.model;

public class JsonLogin {
    private int sellerId;
    private String sellerName;
    private String sellerNum;
    private String sellerHead;
    private String sellerDomain;
    private String sellerCountry;
    private String sellerTel;
    private String sellerToken;

    private String appKey;
    private String shopName;
    private String shopTime;
    private String stateName;
    private String cityName;
    private String detailedAddress;
    private int telOpen;
    private String zipCode;
    private String numActiva;


    public String getSellerNum() {
        return sellerNum;
    }

    public void setSellerNum(String sellerNum) {
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

    public String getSellerDomain() {
        return sellerDomain;
    }

    public void setSellerDomain(String sellerDomain) {
        this.sellerDomain = sellerDomain;
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

    public String getSellerTel() {
        return sellerTel;
    }

    public void setSellerTel(String sellerTel) {
        this.sellerTel = sellerTel;
    }

    public String getShopTime() {
        return shopTime;
    }

    public void setShopTime(String shopTime) {
        this.shopTime = shopTime;
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

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getSellerToken() {
        return sellerToken;
    }

    public void setSellerToken(String sellerToken) {
        this.sellerToken = sellerToken;
    }

    public int getTelOpen() {
        return telOpen;
    }

    public void setTelOpen(int telOpen) {
        this.telOpen = telOpen;
    }
}
