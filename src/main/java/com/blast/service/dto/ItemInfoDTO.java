package com.blast.service.dto;


import java.io.Serializable;
import java.util.List;

import com.blast.service.shoppingapi.dto.AmazonImageInfo;
import com.blast.service.shoppingapi.dto.AmazonOfferDTO;
import com.blast.service.shoppingapi.dto.SimilarProductDTO;

/**
 * A DTO for the WikiInfoDTO entity.
 */
public class ItemInfoDTO implements Serializable {

	private static final long serialVersionUID = -30474375883288031L;
	
	private String asin;
	
	private String detailPageURL;
	
	private String brand;
	
	private String title;
	
	private AmazonImageInfo smallImage;
	
	private AmazonImageInfo mediumImage;
	
	private AmazonImageInfo largeImage;
	
	private String description;
	
	private AmazonOfferDTO offer;
	
	private List<SimilarProductDTO> similarProducts;

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public AmazonImageInfo getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(AmazonImageInfo smallImage) {
		this.smallImage = smallImage;
	}

	public AmazonImageInfo getMediumImage() {
		return mediumImage;
	}

	public void setMediumImage(AmazonImageInfo mediumImage) {
		this.mediumImage = mediumImage;
	}

	public AmazonImageInfo getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(AmazonImageInfo largeImage) {
		this.largeImage = largeImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AmazonOfferDTO getOffer() {
		return offer;
	}

	public void setOffer(AmazonOfferDTO offer) {
		this.offer = offer;
	}

	public String getDetailPageURL() {
		return detailPageURL;
	}

	public void setDetailPageURL(String detailPageURL) {
		this.detailPageURL = detailPageURL;
	}

	public List<SimilarProductDTO> getSimilarProducts() {
		return similarProducts;
	}

	public void setSimilarProducts(List<SimilarProductDTO> similarProducts) {
		this.similarProducts = similarProducts;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
