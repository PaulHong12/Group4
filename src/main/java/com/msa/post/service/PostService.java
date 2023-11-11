package com.msa.post.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.msa.post.domain.Post;

public interface PostService {
	
	Post addPost(String title, String content);
	
	Optional<Post> getPost(long id);
	
	List<Post> getPostListByUserId();

	List<Post> getPostList();

	void deletePost(long id);

  //  Post updatePost(String title, String content);

	Post addComment(long i, String content);

    List<Post> getPostsByDate(LocalDate parsedDate);

	List<Post> getPostsByDateRange(LocalDate start, LocalDate end);

	Post updatePost(long postId, String title, String content);
}
