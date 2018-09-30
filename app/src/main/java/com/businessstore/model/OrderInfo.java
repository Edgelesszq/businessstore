package com.businessstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Wuxi
 * @date 2018/9/28
 * 订单信息实体类
 */

public class OrderInfo implements Parcelable{
    private int orderId;
    private String buyerNum;
    private String buyerName;
    private String buyerHead;
    private String orderNumber;
    private int goodsNum;
    private Double orderTotal;
    private String buyerRealName;
    private String buyerContact;
    private String buyerRemarks;
    private int sellerState;
    private String createdAt;
    private String goodsName;
    private String goodsInfo;
    private List<PictureInfo> pictureInfo;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBuyerNum() {
        return buyerNum;
    }

    public void setBuyerNum(String buyerNum) {
        this.buyerNum = buyerNum;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerHead() {
        return buyerHead;
    }

    public void setBuyerHead(String buyerHead) {
        this.buyerHead = buyerHead;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public List<PictureInfo> getPictureInfo() {
        return pictureInfo;
    }

    public void setPictureInfo(List<PictureInfo> pictureInfo) {
        this.pictureInfo = pictureInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.orderId);
        dest.writeString(this.buyerNum);
        dest.writeString(this.buyerName);
        dest.writeString(this.buyerHead);
        dest.writeString(this.orderNumber);
        dest.writeInt(this.goodsNum);
        dest.writeValue(this.orderTotal);
        dest.writeString(this.buyerRealName);
        dest.writeString(this.buyerContact);
        dest.writeString(this.buyerRemarks);
        dest.writeInt(this.sellerState);
        dest.writeString(this.createdAt);
        dest.writeString(this.goodsName);
        dest.writeString(this.goodsInfo);
        dest.writeTypedList(this.pictureInfo);
    }

    public OrderInfo() {
    }

    protected OrderInfo(Parcel in) {
        this.orderId = in.readInt();
        this.buyerNum = in.readString();
        this.buyerName = in.readString();
        this.buyerHead = in.readString();
        this.orderNumber = in.readString();
        this.goodsNum = in.readInt();
        this.orderTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.buyerRealName = in.readString();
        this.buyerContact = in.readString();
        this.buyerRemarks = in.readString();
        this.sellerState = in.readInt();
        this.createdAt = in.readString();
        this.goodsName = in.readString();
        this.goodsInfo = in.readString();
        this.pictureInfo = in.createTypedArrayList(PictureInfo.CREATOR);
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
