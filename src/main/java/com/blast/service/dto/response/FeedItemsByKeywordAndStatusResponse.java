package com.blast.service.dto.response;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;

import com.blast.service.dto.FeedItemDTO;
import com.blast.service.dto.KeywordDTO;

/**
 * A DTO for the Keyword entity.
 */
public class FeedItemsByKeywordAndStatusResponse implements Serializable {

    private KeywordDTO keyword;
    
    private List<FeedItemDTO> items;
    
	public FeedItemsByKeywordAndStatusResponse(KeywordDTO keyword, List<FeedItemDTO> items) {
		super();
		this.keyword = keyword;
		this.items = items;
	}

	public KeywordDTO getKeyword() {
		return keyword;
	}

	public void setKeyword(KeywordDTO keyword) {
		this.keyword = keyword;
	}

	public List<FeedItemDTO> getItems() {
		return items;
	}

	public void setItems(List<FeedItemDTO> items) {
		this.items = items;
	}

    
}
