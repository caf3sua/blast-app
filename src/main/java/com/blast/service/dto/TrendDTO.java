package com.blast.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Trend entity.
 */
public class TrendDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Long interval;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrendDTO trendDTO = (TrendDTO) o;
        if(trendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrendDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", interval='" + getInterval() + "'" +
            "}";
    }
}
