package com.hiku.socialFeedService.model;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "posts", schema = "social_feed_service")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to user from User Service
    @Column(nullable = false)
    private Long userId;
    private String username;

    private String title;

    @Column(length = 2000)
    private String content;

    // @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<PostMedia> media; // images/videos

    private LocalDateTime createdAt;

    private boolean automated; // for achievements or automated news posts

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    // public List<PostMedia> getMedia() {
    //     return media;
    // }
    // public void setMedia(List<PostMedia> media) {
    //     this.media = media;
    // }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public boolean isAutomated() {
        return automated;
    }
    public void setAutomated(boolean automated) {
        this.automated = automated;
    }

}
