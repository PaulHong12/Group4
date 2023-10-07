package com.example.MusicDiary_sample.post.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "added_at")
    private Date addedAt;

    private String title;

    @Column(name = "thumbnail_link")
    private String thumbnailLink;

    @Column(name = "music_link")
    private String musicLink;

    // Constructors, getters, and setters...

    // Default constructor
    public Music() {
    }

    // Parameterized constructor
    public Music(Date addedAt, String title, String thumbnailLink, String musicLink) {
        this.addedAt = addedAt;
        this.title = title;
        this.thumbnailLink = thumbnailLink;
        this.musicLink = musicLink;
    }
}