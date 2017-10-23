package com.blast.service.vision.dto;

import java.util.ArrayList;
import java.util.List;

public class AnnotateImageResponse {
	private List<EntityAnnotation> labelAnnotations = new ArrayList<>();
	
	private List<EntityAnnotation> landmarkAnnotations = new ArrayList<>();
	
	private List<EntityAnnotation> logoAnnotations = new ArrayList<>();
	
	private List<EntityAnnotation> textAnnotations = new ArrayList<>();

	public List<EntityAnnotation> getLabelAnnotations() {
		return labelAnnotations;
	}

	public void setLabelAnnotations(List<EntityAnnotation> labelAnnotations) {
		this.labelAnnotations = labelAnnotations;
	}

	public List<EntityAnnotation> getLandmarkAnnotations() {
		return landmarkAnnotations;
	}

	public void setLandmarkAnnotations(List<EntityAnnotation> landmarkAnnotations) {
		this.landmarkAnnotations = landmarkAnnotations;
	}

	public List<EntityAnnotation> getLogoAnnotations() {
		return logoAnnotations;
	}

	public void setLogoAnnotations(List<EntityAnnotation> logoAnnotations) {
		this.logoAnnotations = logoAnnotations;
	}

	public List<EntityAnnotation> getTextAnnotations() {
		return textAnnotations;
	}

	public void setTextAnnotations(List<EntityAnnotation> textAnnotations) {
		this.textAnnotations = textAnnotations;
	}

	
}
