package com.sillycat.cygnus.model;

import java.util.Date;

public class Shop {

	private String shopId;

	private String shopName;

	private String shopDescription;

	private Date createDate;

	private String shopType;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopDescription() {
		return shopDescription;
	}

	public void setShopDescription(String shopDescription) {
		this.shopDescription = shopDescription;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String toString() {
		return "Shop [createDate=" + createDate + ", shopDescription="
				+ shopDescription + ", shopId=" + shopId + ", shopName="
				+ shopName + ", shopType=" + shopType + "]";
	}

}
