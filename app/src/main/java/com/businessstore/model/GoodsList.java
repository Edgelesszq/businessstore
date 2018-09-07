package com.businessstore.model;

import java.io.Serializable;
import java.util.List;

public class GoodsList implements Serializable{
    private List<Goods> list;
    private int count;
    private int page;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }
}
