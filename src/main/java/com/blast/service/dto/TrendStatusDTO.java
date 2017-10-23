package com.blast.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A DTO for the StatusItem entity.
 */
@JsonInclude(Include.NON_EMPTY)
public class TrendStatusDTO implements Serializable {

    private Long id;

    private Long itemId;

    private Long userId;

    private Long numberLike;
    
    private Long numberHate;
    
    private Date date;
    
    private Long number;
    
    private Integer status;
    
    private List<UserDTO> lstUserLike;
    
    private List<UserDTO> lstUserUnlike;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<UserDTO> getLstUserLike() {
		return lstUserLike;
	}

	public void setLstUserLike(List<UserDTO> lstUserLike) {
		this.lstUserLike = lstUserLike;
	}

	public List<UserDTO> getLstUserUnlike() {
		return lstUserUnlike;
	}

	public void setLstUserUnlike(List<UserDTO> lstUserUnlike) {
		this.lstUserUnlike = lstUserUnlike;
	}


}
