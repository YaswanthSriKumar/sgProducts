package com.sgcore.sgProducts.dto;

public class ProductDto {

	private int productId;
	private String productName;
	private String productImage;
	private String productDescription;
	private boolean productShow;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public boolean isProductShow() {
		return productShow;
	}
	public void setProductShow(boolean productShow) {
		this.productShow = productShow;
	}
	
}
