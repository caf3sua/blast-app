package com.blast.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the SocialFollow entity.
 */
public class SocialAccountDTO implements Serializable {

	private static final long serialVersionUID = -30684385883288031L;

    @NotNull
    private String providerId;

    @NotNull
    private String providerUserId;
    
    private String displayName;
    
    private String imageUrl;
    
    private List<String> lstProviderUserId;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getLstProviderUserId() {
		return lstProviderUserId;
	}

	public void setLstProviderUserId(List<String> lstProviderUserId) {
		this.lstProviderUserId = lstProviderUserId;
	}
}
