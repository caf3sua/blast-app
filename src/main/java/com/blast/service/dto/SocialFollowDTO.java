package com.blast.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SocialFollow entity.
 */
public class SocialFollowDTO implements Serializable {

	private static final long serialVersionUID = -30684375883288031L;

	private Long id;

    private String providerId;

    private String providerUserId;

    private String followUserId;
    
    private String displayName;
    
    private String imageUrl;
    
    public SocialFollowDTO() {
		super();
	}

    public SocialFollowDTO(String providerId, String providerUserId, String followUserId) {
		super();
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.followUserId = followUserId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SocialFollowDTO socialFollowDTO = (SocialFollowDTO) o;
        if(socialFollowDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialFollowDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialFollowDTO{" +
            "id=" + getId() +
            ", providerId='" + getProviderId() + "'" +
            ", providerUserId='" + getProviderUserId() + "'" +
            ", followUserId='" + getFollowUserId() + "'" +
            "}";
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
}
