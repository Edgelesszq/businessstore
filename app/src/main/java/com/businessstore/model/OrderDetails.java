package com.businessstore.model;

import java.util.List;

public class OrderDetails {
    private OrderInfo orderInfo;
    private List<Reply> comment;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<Reply> getComment() {
        return comment;
    }

    public void setComment(List<Reply> comment) {
        this.comment = comment;
    }
}
