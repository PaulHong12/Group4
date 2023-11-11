package com.msa.post.repository;

import com.msa.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	//List<Post> findByOrderByIdDesc();

	//List<Post> findByIdInOrderByIdDesc(List<Long> postIdList);
	List<Post> findAllByDate(LocalDate date);
	List<Post> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
