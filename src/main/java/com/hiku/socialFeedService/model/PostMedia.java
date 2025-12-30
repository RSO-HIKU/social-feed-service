package com.hiku.socialFeedService.model;

import javax.persistence.*;

@Entity
@Table(name = "post_media",schema = "social_feed_service")
public class PostMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String mediaUrl; // could be S3 path or server path

    @Enumerated(EnumType.STRING)
    private MediaType type;

    public enum MediaType {
        IMAGE,
        VIDEO
    }

    // getters and setters
}
