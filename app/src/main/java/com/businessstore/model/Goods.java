package com.businessstore.model;

import java.io.Serializable;

public class Goods implements Serializable{
    private int sellerId;
    private String goodsName;
    private String goodsInfo;
    private String tradPosition;
    private Double minPrice;
    private Double maxprice;
    private int goodsStock;
    private int priceOpen;
    private int stockOpen;
    private String pictureInfo;
    private int createdAt;

    public Goods(String title, String content, Double price, int number, int pubPrice, int pubNumber) {
        this.goodsName = title;
        this.goodsInfo = content;
        this.maxprice = price;
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
        return maxprice;
    }

    public void setMaxprice(Double maxprice) {
        this.maxprice = maxprice;
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

    public String getPictureInfo() {
        return pictureInfo;
    }

    public void setPictureInfo(String pictureInfo) {
        this.pictureInfo = pictureInfo;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }
}
