package com.businessstore.model;

public class City {
	public String zipCode;
	public String stateName;
	public String cityName;

	public City(String zipCode, String stateName, String cityName) {
		this.zipCode = zipCode;
		this.stateName = stateName;
		this.cityName = cityName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
