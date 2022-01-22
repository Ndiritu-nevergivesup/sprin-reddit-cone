package com.ndirituedwin.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Subreddit name cannot be blank")
    private String name;
    @NotBlank(message = "Subreddit description cannot be blank")
    @Lob
    private String description;
     @OneToMany(fetch = LAZY)
     @JoinColumn(name = "subreddit_id")
     private List<Post> posts=new ArrayList<>(0);
     private Instant createdDate;
     @ManyToOne(fetch = LAZY)
    private User user;

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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Subreddit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", posts=" + posts +
                ", user=" + user +
                '}';
    }
}
