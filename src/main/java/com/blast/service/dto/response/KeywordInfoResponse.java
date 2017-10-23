package com.blast.service.dto.response;


import java.io.Serializable;
import java.util.List;

import com.blast.service.dto.KeywordDTO;
import com.blast.service.dto.UserDTO;

/**
 * A DTO for the Keyword entity.
 */
public class KeywordInfoResponse implements Serializable {

    private KeywordDTO keyword;

    private List<UserDTO> lstUserLike;
    
    private List<UserDTO> lstUserHate;

	public KeywordDTO getKeyword() {
		return keyword;
	}

	public void setKeyword(KeywordDTO keyword) {
		this.keyword = keyword;
	}

	public List<UserDTO> getLstUserLike() {
		return lstUserLike;
	}

	public void setLstUserLike(List<UserDTO> lstUserLike) {
		this.lstUserLike = lstUserLike;
	}

	public List<UserDTO> getLstUserHate() {
		return lstUserHate;
	}

	public void setLstUserHate(List<UserDTO> lstUserHate) {
		this.lstUserHate = lstUserHate;
	}

    
}
