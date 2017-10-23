package com.blast.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Keyword.
 */
@Entity
@Table(name = "keyword")
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "feed_id")
    private Long feedId;

    @Column(name = "number_like")
    private Long numberLike;
    
    @Column(name = "number_hate")
    private Long numberHate;
    
    @Column(name = "more_keyword")
    private String moreKeyword;
    
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

    public Keyword name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFeedId() {
        return feedId;
    }

    public Keyword feedId(Long feedId) {
        this.feedId = feedId;
        return this;
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
        Keyword keyword = (Keyword) o;
        if (keyword.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), keyword.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Keyword{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", feedId='" + getFeedId() + "'" +
            "}";
    }
}
