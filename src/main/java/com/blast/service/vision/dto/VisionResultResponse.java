package com.blast.service.vision.dto;

import java.util.ArrayList;
import java.util.List;

public class VisionResultResponse {
	private List<EntityAnnotation> data = new ArrayList<>();

	public List<EntityAnnotation> getData() {
		return data;
	}

	public void setData(List<EntityAnnotation> data) {
		this.data = data;
	}

	
}
