package com.hiku.socialFeedService.model;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to user from User Service
    @Column(nullable = false)
    private Long userId;
    private String username;

    @Column(length = 2000)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostMedia> media; // images/videos

    private LocalDateTime createdAt;

    private boolean automated; // for achievements or automated news posts

    // getters and setters
}
