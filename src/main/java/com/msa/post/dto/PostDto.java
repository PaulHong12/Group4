package com.msa.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostDto {

    @Schema(description = "게시물 제목", defaultValue = "디폴트 제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자")
    public String username;

    @Schema(description = "id")
    public long postId;
    // Constructors
    public PostDto() {
        // Default constructor
    }

    public PostDto(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public PostDto(String title, String content, String username, long postId) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.postId = postId;
    }

    // Getters
    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getUsername() {
        return this.username;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}