package com.blast.web.rest.vm;


import java.io.Serializable;

/**
 * A DTO for the SocialFollow entity.
 */
public class SocialFollowVM implements Serializable {

	private static final long serialVersionUID = -30685375883288031L;

    private String providerId;

    private String providerUserId;

    private String followUserId;
    
    public SocialFollowVM() {
	}
    
    public SocialFollowVM(String providerId, String providerUserId, String followUserId) {
		super();
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.followUserId = followUserId;
	}

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

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }
}
