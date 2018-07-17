package com.businessstore.model;

public class Goods {
    private String title;
    private String content;
    private int price;
    private int number;
    private boolean pubPrice;
    private boolean pubNumber;

    public Goods(String title, String content, int price, int number, boolean pubPrice, boolean pubNumber) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.number = number;
        this.pubPrice = pubPrice;
        this.pubNumber = pubNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isPubPrice() {
        return pubPrice;
    }

    public void setPubPrice(boolean pubPrice) {
        this.pubPrice = pubPrice;
    }

    public boolean isPubNumber() {
        return pubNumber;
    }

    public void setPubNumber(boolean pubNumber) {
        this.pubNumber = pubNumber;
    }
}
