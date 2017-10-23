package com.blast.service.dto;


import java.io.Serializable;

/**
 * A DTO for the WikiInfoDTO entity.
 */
public class WikiInfoDTO implements Serializable {

	private static final long serialVersionUID = -30454375883288031L;
	
	private Long pageId;
	private String title;
	private String extract;
	private String contentmodel;
	private String fullurl;
	private String canonicalurl;
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExtract() {
		return extract;
	}
	public void setExtract(String extract) {
		this.extract = extract;
	}
	public String getContentmodel() {
		return contentmodel;
	}
	public void setContentmodel(String contentmodel) {
		this.contentmodel = contentmodel;
	}
	public String getFullurl() {
		return fullurl;
	}
	public void setFullurl(String fullurl) {
		this.fullurl = fullurl;
	}
	public String getCanonicalurl() {
		return canonicalurl;
	}
	public void setCanonicalurl(String canonicalurl) {
		this.canonicalurl = canonicalurl;
	}
	
	
	
}
