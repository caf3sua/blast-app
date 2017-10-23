package com.blast.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SocialFollow.
 */
@Entity
@Table(name = "social_follow")
public class SocialFollow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @NotNull
    @Column(name = "provider_user_id", nullable = false)
    private String providerUserId;

    @NotNull
    @Column(name = "follow_user_id", nullable = false)
    private String followUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderId() {
        return providerId;
    }

    public SocialFollow providerId(String providerId) {
        this.providerId = providerId;
        return this;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public SocialFollow providerUserId(String providerUserId) {
        this.providerUserId = providerUserId;
        return this;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getFollowUserId() {
        return followUserId;
    }

    public SocialFollow followUserId(String followUserId) {
        this.followUserId = followUserId;
        return this;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SocialFollow socialFollow = (SocialFollow) o;
        if (socialFollow.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialFollow.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialFollow{" +
            "id=" + getId() +
            ", providerId='" + getProviderId() + "'" +
            ", providerUserId='" + getProviderUserId() + "'" +
            ", followUserId='" + getFollowUserId() + "'" +
            "}";
    }
}
