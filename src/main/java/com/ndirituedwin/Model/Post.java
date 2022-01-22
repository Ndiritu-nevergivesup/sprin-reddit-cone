package com.ndirituedwin.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

//@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "post name may not be null")
    private String postName;
    @Nullable
    private String url;

    @Nullable
    private String description;
    private Integer voteCount=0;

    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    private Instant createdDate;

    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "id",referencedColumnName = "id")
    private Subreddit subreddit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

}
