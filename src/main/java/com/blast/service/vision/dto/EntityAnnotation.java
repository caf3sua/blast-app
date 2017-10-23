package com.blast.service.vision.dto;

import java.util.Comparator;

public class EntityAnnotation {
	private String mid;
	private String locale;
	private String description;
	private Double score;
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
	public static Comparator<EntityAnnotation> EntityAnnotationComparator = new Comparator<EntityAnnotation>() {
        @Override
        public int compare(EntityAnnotation e1, EntityAnnotation e2) {
        	if (e1.getScore() == null || e2.getScore() == null) return 0;
            return (int) (e1.getScore() - e2.getScore());
        }
    };
}
