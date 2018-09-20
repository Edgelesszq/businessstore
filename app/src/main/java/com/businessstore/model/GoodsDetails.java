package com.businessstore.model;

import java.io.Serializable;
import java.util.List;

public class GoodsDetails implements Serializable {
    private Goods goodsInfo;
    private List<Reply> comment;

    public Goods getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(Goods goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public List<Reply> getComment() {
        return comment;
    }

    public void setComment(List<Reply> comment) {
        this.comment = comment;
    }
}
