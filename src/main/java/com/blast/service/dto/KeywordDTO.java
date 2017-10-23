package com.blast.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

/**
 * A DTO for the Keyword entity.
 */
public class KeywordDTO implements Serializable {

    private Long id;

    private String name;

    private Long feedId;
    
    private Long numberLike;
    
    private Long numberHate;
    
    private String moreKeyword;
    
    // More attribute
    private FeedItemDTO representationFeedItem;

    public Long getNumberLike() {
		return numberLike;
	}

	public void setNumberLike(Long numberLike) {
		this.numberLike = numberLike;
	}

	public Long getNumberHate() {
		return numberHate;
	}

	public void setNumberHate(Long numberHate) {
		this.numberHate = numberHate;
	}

	public String getMoreKeyword() {
		return moreKeyword;
	}

	public void setMoreKeyword(String moreKeyword) {
		this.moreKeyword = moreKeyword;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KeywordDTO keywordDTO = (KeywordDTO) o;
        if(keywordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keywordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KeywordDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", feedId='" + getFeedId() + "'" +
            "}";
    }

	public FeedItemDTO getRepresentationFeedItem() {
		return representationFeedItem;
	}

	public void setRepresentationFeedItem(FeedItemDTO representationFeedItem) {
		this.representationFeedItem = representationFeedItem;
	}
}
