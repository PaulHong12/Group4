package com.msa.comment.domain;

import com.msa.comment.dto.CommentDto;
import com.msa.post.domain.Post;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column
    private String nickName;

    @Column(name = "content")
    private String content;

    //many comments to one post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();


    public void setPost(Post post) {
        this.post = post;
    }

    public Comment(String content) {
        this.content = content;
    }

    public Comment() {

    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object convertToDTO() {
        CommentDto dto = new CommentDto();
        dto.setId(this.id);       // Set the id from Comment to CommentDto
        dto.setContent(this.content); // Set the content from Comment to CommentDto
        return dto;
    }
}