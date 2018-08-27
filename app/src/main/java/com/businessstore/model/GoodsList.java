package com.businessstore.model;

import java.io.Serializable;
import java.util.List;

public class GoodsList implements Serializable{
    private List<Goods> list;

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }
}
