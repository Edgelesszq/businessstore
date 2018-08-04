package com.businessstore.model;

public class City {
	public String cityCode;
	public String name;
	public String pinyi;


	public City(String cityCode, String name, String pinyi) {
		this.cityCode = cityCode;
		this.name = name;
		this.pinyi = pinyi;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public City() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyi() {
		return pinyi;
	}

	public void setPinyi(String pinyi) {
		this.pinyi = pinyi;
	}

	@Override
	public String toString() {
		return getName();
	}
}
