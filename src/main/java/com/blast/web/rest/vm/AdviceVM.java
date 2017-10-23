package com.blast.web.rest.vm;

/**
 * View Model object for storing a user's credentials.
 */
public class AdviceVM {

	private String keyword;
	
	private String brand;
	
	private String itemPage;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getItemPage() {
		return itemPage;
	}

	public void setItemPage(String itemPage) {
		this.itemPage = itemPage;
	}
	
}
