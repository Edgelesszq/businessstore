package com.businessstore.model;

import java.io.Serializable;
import java.util.List;

public class GoodsDetails implements Serializable {
    private Goods goodsInfo;
    private List<Comment> comment;

    public Goods getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(Goods goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
