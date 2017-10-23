package com.blast.service.dto;


import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A DTO for the FeedItem entity.
 */
@JsonInclude(Include.NON_EMPTY)
public class FeedItemDTO implements Serializable {

    private Long id;

    private String data;

    private String imageUrl;

    private Boolean share;

    private Integer status;

    private String keywords;
    
    private String contentType;
    
    private String filename;
    
    private Long userId;
    
    private String mainKeyword;
    
    private String imageThumbUrl;
    
    // More properties
    private TrendStatusDTO statusItem;
    

    public String getImageThumbUrl() {
		return imageThumbUrl;
	}

	public void setImageThumbUrl(String imageThumbUrl) {
		this.imageThumbUrl = imageThumbUrl;
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeedItemDTO feedItemDTO = (FeedItemDTO) o;
        if(feedItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeedItemDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", share='" + isShare() + "'" +
            ", status='" + getStatus() + "'" +
            ", keywords='" + getKeywords() + "'" +
            "}";
    }

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public TrendStatusDTO getStatusItem() {
		return statusItem;
	}

	public void setStatusItem(TrendStatusDTO statusItem) {
		this.statusItem = statusItem;
	}

	public String getMainKeyword() {
		return mainKeyword;
	}

	public void setMainKeyword(String mainKeyword) {
		this.mainKeyword = mainKeyword;
	}
}
