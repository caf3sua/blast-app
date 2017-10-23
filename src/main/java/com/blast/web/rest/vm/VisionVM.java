package com.blast.web.rest.vm;

/**
 * View Model object for storing a user's credentials.
 */
public class VisionVM {

	/** LABEL_DETECTION */
	private String type;

	private String content;
	
	private String imageUri;
	
	private int maxResults = 5;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
	
}
