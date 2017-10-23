package com.blast.service.vision.dto;

import java.util.ArrayList;
import java.util.List;

public class VisionRequest {
	private List<AnnotateImageRequest> requests = new ArrayList<>();

	public List<AnnotateImageRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<AnnotateImageRequest> requests) {
		this.requests = requests;
	}
	
}
