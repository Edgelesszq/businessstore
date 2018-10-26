package com.businessstore.model;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable{
    private int sellerId;
    private int goodsId;
    private String goodsName;//标题
    private String goodsInfo;//内容
    private String tradPosition;//地址
    private Double minPrice;//优惠价
    private Double maxPrice;//原价
    private int goodsStock;//数量
    private int priceOpen;//价格可见
    private int stockOpen;//数量可见
    private List<PictureInfo> pictureInfo;//图片
    private int createdAt;//创建时间
    private int updateAt;//更新时间
    private String shareAddress;//分享地址

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getShareAddress() {
        return shareAddress;
    }

    public void setShareAddress(String shareAddress) {
        this.shareAddress = shareAddress;
    }

    public Goods(String title, String content, Double price, int number, int pubPrice, int pubNumber) {
        this.goodsName = title;
        this.goodsInfo = content;
        this.maxPrice = price;
        this.goodsStock = number;
        this.priceOpen = pubPrice;
        this.stockOpen = pubNumber;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getTradPosition() {
        return tradPosition;
    }

    public void setTradPosition(String tradPosition) {
        this.tradPosition = tradPosition;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxprice() {
        return maxPrice;
    }

    public void setMaxprice(Double maxprice) {
        this.maxPrice = maxprice;
    }

    public int getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(int goodsStock) {
        this.goodsStock = goodsStock;
    }

    public int getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(int priceOpen) {
        this.priceOpen = priceOpen;
    }

    public int getStockOpen() {
        return stockOpen;
    }

    public void setStockOpen(int stockOpen) {
        this.stockOpen = stockOpen;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public List<PictureInfo> getPictureInfo() {
        return pictureInfo;
    }

    public void setPictureInfo(List<PictureInfo> pictureInfo) {
        this.pictureInfo = pictureInfo;
    }

    public int getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(int updateAt) {
        this.updateAt = updateAt;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }
}
