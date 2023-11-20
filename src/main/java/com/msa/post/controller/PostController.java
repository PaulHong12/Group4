package com.msa.post.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.msa.comment.dto.CommentDto;
import com.msa.post.domain.Post;
import com.msa.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.msa.post.dto.PostDto;
import com.msa.post.dto.ResultDto;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


// TO DO:URI 수정
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /*
    @GetMapping("/{username}/{date}")
    public ResponseEntity<ResultDto<List<PostDto>>> getTodayPost(@PathVariable String username, @PathVariable("date") String date) {
        //LocalDate localDate = LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.parse(date, formatter);

        List<PostDto> postDtos = postService.getPostsByDateAndMember(localDate, username).stream()
                .map(Post::convert2DTO)
                .collect(Collectors.toList());

        if (postDtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        return ResponseEntity.ok(new ResultDto<>(200, "ok", postDtos));
    } */

    @PostMapping("/addPost") // No need for "/addPost" since we're posting to "/posts"
    public ResponseEntity<ResultDto<PostDto>> addPost(@RequestBody PostDto dto) {
        Post newPost = postService.addPost(dto.getTitle(), dto.getContent(), dto.getUsername());
        String formattedDate = LocalDate.now().format(dateFormatter);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{date}")
                .buildAndExpand(formattedDate)
                .toUri();
        return ResponseEntity.created(location).body(new ResultDto<>(201, "Post added", newPost.convert2DTO()));
    }

    //PUT posts/{postId}
    @PutMapping("/{postId}")
    public ResponseEntity<ResultDto<PostDto>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostDto dto,
            Authentication authentication) { // Include Authentication object
        String currentUsername = authentication.getName();
        Post post = postService.findById(postId).get();

        if (post.getCreator().equals(currentUsername)) {
            // User is the creator, proceed with the update
            Post updatedPost = postService.updatePost(postId, dto.getTitle(), dto.getContent());
            return ResponseEntity.ok(new ResultDto<>(200, "Post updated", updatedPost.convert2DTO()));
        } else {
            // User is not the creator, return a forbidden response
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResultDto<>(403, "Access Denied: You are not the creator of this post", null));
        }
    }

    //POST posts/{postId}/comments
    @PostMapping("/{postId}/comments")
    public ResponseEntity<ResultDto<PostDto>> addComment(
            @PathVariable long postId,
            @RequestBody CommentDto dto,
            @RequestParam(required = false) String content) {
        String commentContent = (content != null) ? content : dto.getContent();

        Post updatedPost = postService.addComment(postId, String.valueOf(dto.getContent()));
        return ResponseEntity.ok(new ResultDto<>(200, "Comment added", updatedPost.convert2DTO()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResultDto<PostDto>> getPost(@PathVariable("postId") long postId) {
        return postService.getPost(postId)
                .map(post -> ResponseEntity.ok(new ResultDto<>(200, "ok", post.convert2DTO())))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResultDto<Void>> deletePost(@PathVariable("postId") long postId) {
        return postService.getPost(postId)
                .map(post -> {
                    postService.deletePost(postId);
                    return new ResponseEntity<ResultDto<Void>>(new ResultDto<>(200, "Post deleted", null), HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }


}


