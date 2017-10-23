package com.blast.service.vision.dto;

import java.util.ArrayList;
import java.util.List;

public class AnnotateImageRequest {
	private Image image;
	
	private List<Feature> features = new ArrayList<>();

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	
}
