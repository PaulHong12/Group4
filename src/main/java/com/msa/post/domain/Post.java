package com.msa.post.domain;

import com.msa.comment.domain.Comment;
import com.msa.post.dto.PostDto;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private long id;

	@Column(name="title")
	private String title;

	@Column(name="content", columnDefinition="TEXT")
	private String content;

	@Column(name = "date")
	private LocalDate date;

	@Column(name="thumbnail")
	private String thumbnail; // URL or path of the thumbnail image

	@CreatedDate
	private LocalDateTime createdAt = LocalDateTime.now();

	@LastModifiedDate
	private LocalDateTime updatedAt = LocalDateTime.now();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private Set<Comment> comments;
	
	public Post() {
		super();
	}

	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}
	public Post(String title, String content, String thumbnail) {
		this.title = title;
		this.content = content;
		this.thumbnail = thumbnail;
	}
	public Post(String title, String content, Set<Comment> comments) {
		this.title = title;
		this.content = content;
		this.comments = comments;
	}

	public PostDto convert2DTO() {
		return new PostDto(this.getTitle(), this.getContent());
	}

	public LocalDate getDate() {
		return this.createdAt.toLocalDate(); //debug
	}

	public void addComment(Comment newComment) {

	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
