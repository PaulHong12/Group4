package com.msa.comment.controller;

import com.msa.comment.CommentService.CommentService;
import com.msa.comment.domain.Comment;
import com.msa.post.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.msa.comment.dto.CommentDto;

@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // ... other methods ...

    // Update a comment
    @PutMapping("/{commentId}")
    public ResponseEntity<ResultDto<Object>> updateComment(
            @PathVariable int commentId,
            @RequestBody CommentDto dto) {
        Comment updatedComment = commentService.updateComment(commentId, dto.getContent());
        if (updatedComment != null) {
            return ResponseEntity.ok(new ResultDto<>(200, "Comment updated", updatedComment.convertToDTO()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDto<>(404, "Comment not found", null));
        }
    }

    // Delete a comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResultDto<Void>> deleteComment(
            @PathVariable int commentId) {
        boolean deleted = commentService.deleteComment(commentId);
        if (deleted) {
            return ResponseEntity.ok(new ResultDto<>(200, "Comment deleted", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDto<>(404, "Comment not found", null));
        }
    }
}
