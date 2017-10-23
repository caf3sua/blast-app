package com.blast.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A DTO for the StatusItem entity.
 */
@JsonInclude(Include.NON_EMPTY)
public class StatusItemDTO implements Serializable {

    private Long id;

    private Long itemId;

    private Long userId;

    private Integer status;

    private String description;

    private LocalDate createdDate;
    
    // More property
    private Boolean isLike;
    
    private Boolean isHate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatusItemDTO statusItemDTO = (StatusItemDTO) o;
        if(statusItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statusItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatusItemDTO{" +
            "id=" + getId() +
            ", itemId='" + getItemId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }

	public Boolean isLike() {
		return isLike;
	}

	public void setLike(Boolean isLike) {
		this.isLike = isLike;
	}

	public Boolean isHate() {
		return isHate;
	}

	public void setHate(Boolean isHate) {
		this.isHate = isHate;
	}
}
