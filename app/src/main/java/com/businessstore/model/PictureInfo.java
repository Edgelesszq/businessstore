package com.businessstore.model;

import java.io.Serializable;

public class PictureInfo implements Serializable{
    private int width;
    private int height;
    private String urllarge;
    private String urlsmall;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrllarge() {
        return urllarge;
    }

    public void setUrllarge(String urllarge) {
        this.urllarge = urllarge;
    }

    public String getUrlsmall() {
        return urlsmall;
    }

    public void setUrlsmall(String urlsmall) {
        this.urlsmall = urlsmall;
    }
}
