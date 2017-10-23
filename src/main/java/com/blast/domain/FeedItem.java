package com.blast.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * A FeedItem.
 */
@Entity
@Table(name = "feed_item")
public class FeedItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data")
    private String data;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "jhi_share")
    private Boolean share;
    
    @Column(name = "filename")
    private String filename;
    
    @Column(name = "keywords")
    private String keywords;
    
    @Column(name = "status")
    private Integer status;

    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "main_keyword")
    private String mainKeyword;
    
    @Column(name = "created_date")
    private Date createdDate = null;
    
    @Column(name = "image_thumb_url")
    private String imageThumbUrl;

    public String getImageThumbUrl() {
		return imageThumbUrl;
	}

	public void setImageThumbUrl(String imageThumbUrl) {
		this.imageThumbUrl = imageThumbUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getMainKeyword() {
		return mainKeyword;
	}

	public void setMainKeyword(String mainKeyword) {
		this.mainKeyword = mainKeyword;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public FeedItem data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FeedItem imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean isShare() {
        return share;
    }

    public FeedItem share(Boolean share) {
        this.share = share;
        return this;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Integer getStatus() {
        return status;
    }

    public FeedItem status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKeywords() {
        return keywords;
    }

    public FeedItem keywords(String keywords) {
        this.keywords = keywords;
        return this;
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
        FeedItem feedItem = (FeedItem) o;
        if (feedItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeedItem{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", share='" + isShare() + "'" +
            ", status='" + getStatus() + "'" +
            ", keywords='" + getKeywords() + "'" +
            "}";
    }
}
