package com.blast.service.vision.dto;

import java.util.ArrayList;
import java.util.List;

public class VisionResponse {
	private List<AnnotateImageResponse> responses = new ArrayList<>();

	public List<AnnotateImageResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<AnnotateImageResponse> responses) {
		this.responses = responses;
	}


	
}
