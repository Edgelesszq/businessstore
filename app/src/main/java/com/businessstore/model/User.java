package com.businessstore.model;


public class User {

    private String id;
    private String seller_num;
    private String seller_pwd;
    private String created_at;
    private String updatad_at;
    private String countries;
    private String ukey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeller_num() {
        return seller_num;
    }

    public void setSeller_num(String seller_num) {
        this.seller_num = seller_num;
    }

    public String getSeller_pwd() {
        return seller_pwd;
    }

    public void setSeller_pwd(String seller_pwd) {
        this.seller_pwd = seller_pwd;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatad_at() {
        return updatad_at;
    }

    public void setUpdatad_at(String updatad_at) {
        this.updatad_at = updatad_at;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }



    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

}
