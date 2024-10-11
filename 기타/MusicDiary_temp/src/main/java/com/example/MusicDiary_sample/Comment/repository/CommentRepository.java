package com.example.MusicDiary_sample.Comment.repository;
import com.example.MusicDiary_sample.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Post, Long> {

}