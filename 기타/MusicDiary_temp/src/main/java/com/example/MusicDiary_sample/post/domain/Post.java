package com.example.MusicDiary_sample.post.domain;

import com.example.MusicDiary_sample.Comment.domain.Comment;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String content;
    private Date date;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

}