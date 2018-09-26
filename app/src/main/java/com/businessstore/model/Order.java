package com.businessstore.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Order class
 *
 * @author Wuxi
 * @date 2018/9/5
 */
public class Order implements Parcelable{
    private int orderId;
    private String orderNumber;
    private int buyerId;
    private int goodsId;
    private int goodsNum;
    private double orderTotal;
    private String buyerRealName;
    private String buyerContact;
    private String buyerRemarks;
    private int sellerState;
    private int buyerState;
    private String createdAt;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String sellerId) {
        this.orderNumber = sellerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        goodsId = goodsId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        goodsNum = goodsNum;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getBuyerRealName() {
        return buyerRealName;
    }

    public void setBuyerRealName(String buyerRealName) {
        this.buyerRealName = buyerRealName;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }

    public String getBuyerRemarks() {
        return buyerRemarks;
    }

    public void setBuyerRemarks(String buyerRemarks) {
        this.buyerRemarks = buyerRemarks;
    }

    public int getSellerState() {
        return sellerState;
    }

    public void setSellerState(int sellerState) {
        this.sellerState = sellerState;
    }

    public int getBuyerState() {
        return buyerState;
    }

    public void setBuyerState(int buyerState) {
        this.buyerState = buyerState;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.orderId);
        dest.writeString(this.orderNumber);
        dest.writeInt(this.buyerId);
        dest.writeInt(this.goodsId);
        dest.writeInt(this.goodsNum);
        dest.writeDouble(this.orderTotal);
        dest.writeString(this.buyerRealName);
        dest.writeString(this.buyerContact);
        dest.writeString(this.buyerRemarks);
        dest.writeInt(this.sellerState);
        dest.writeInt(this.buyerState);
        dest.writeString(this.createdAt);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.orderId = in.readInt();
        this.orderNumber = in.readString();
        this.buyerId = in.readInt();
        this.goodsId = in.readInt();
        this.goodsNum = in.readInt();
        this.orderTotal = in.readDouble();
        this.buyerRealName = in.readString();
        this.buyerContact = in.readString();
        this.buyerRemarks = in.readString();
        this.sellerState = in.readInt();
        this.buyerState = in.readInt();
        this.createdAt = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
