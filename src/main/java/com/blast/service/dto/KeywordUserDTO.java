package com.blast.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the KeywordUser entity.
 */
public class KeywordUserDTO implements Serializable {

    private Long id;

    private Long keywordId;

    private Long userId;

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

    public void setKeywordId(Long keywordId) {
        this.keywordId = keywordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
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

        KeywordUserDTO keywordUserDTO = (KeywordUserDTO) o;
        if(keywordUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keywordUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KeywordUserDTO{" +
            "id=" + getId() +
            ", keywordId='" + getKeywordId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
