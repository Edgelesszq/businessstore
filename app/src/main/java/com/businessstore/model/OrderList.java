package com.businessstore.model;

import java.io.Serializable;
import java.util.List;
/**
 * OrderList class
 *
 * @author Wuxi
 * @date 2018/9/5
 */
public class OrderList implements Serializable {
    private List<Order> list;

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }
}
