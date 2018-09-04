package com.businessstore.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PictureInfo implements Parcelable{
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.urllarge);
        dest.writeString(this.urlsmall);
    }

    public PictureInfo() {
    }

    protected PictureInfo(Parcel in) {
        this.width = in.readInt();
        this.height = in.readInt();
        this.urllarge = in.readString();
        this.urlsmall = in.readString();
    }

    public static final Creator<PictureInfo> CREATOR = new Creator<PictureInfo>() {
        @Override
        public PictureInfo createFromParcel(Parcel source) {
            return new PictureInfo(source);
        }

        @Override
        public PictureInfo[] newArray(int size) {
            return new PictureInfo[size];
        }
    };
}
