package com.beingjavaguys.bean.cmsmenu;

import java.util.List;

public class CMSMenuBean {

	private int id;

	private String itemName;

	private String description;

	private int cooksId;

	private String cookName;

	private int menuCatagoryId;

	private String menuCatagoryName;

	private String menuImagePath;

	private String cookImage;

	private byte[] image;

	private int unit;

	private List<CMSMenuPriceBean> cmsMenuPriceBeanList;

	private String imageURL;

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCooksId() {
		return cooksId;
	}

	public void setCooksId(int cooksId) {
		this.cooksId = cooksId;
	}

	public int getMenuCatagoryId() {
		return menuCatagoryId;
	}

	public void setMenuCatagoryId(int menuCatagoryId) {
		this.menuCatagoryId = menuCatagoryId;
	}

	public String getMenuImagePath() {
		return menuImagePath;
	}

	public void setMenuImagePath(String menuImagePath) {
		this.menuImagePath = menuImagePath;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<CMSMenuPriceBean> getCmsMenuPriceBeanList() {
		return cmsMenuPriceBeanList;
	}

	public void setCmsMenuPriceBeanList(List<CMSMenuPriceBean> cmsMenuPriceBeanList) {
		this.cmsMenuPriceBeanList = cmsMenuPriceBeanList;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getCookName() {
		return cookName;
	}

	public void setCookName(String cookName) {
		this.cookName = cookName;
	}

	public String getMenuCatagoryName() {
		return menuCatagoryName;
	}

	public void setMenuCatagoryName(String menuCatagoryName) {
		this.menuCatagoryName = menuCatagoryName;
	}

	public String getCookImage() {
		return cookImage;
	}

	public void setCookImage(String cookImage) {
		this.cookImage = cookImage;
	}

}
