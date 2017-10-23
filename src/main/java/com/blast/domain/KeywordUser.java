package com.blast.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A KeywordUser.
 */
@Entity
@Table(name = "keyword_user")
public class KeywordUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "keyword_id")
    private Long keywordId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "updated_date")
    private Instant updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKeywordId() {
        return keywordId;
    }

    public KeywordUser keywordId(Long keywordId) {
        this.keywordId = keywordId;
        return this;
    }

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public Long getUserId() {
        return userId;
    }

    public KeywordUser userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public KeywordUser updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeywordUser keywordUser = (KeywordUser) o;
        if (keywordUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keywordUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KeywordUser{" +
            "id=" + getId() +
            ", keywordId='" + getKeywordId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
