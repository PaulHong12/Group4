package com.msa.post.repository;

import com.msa.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	//List<Post> findByOrderByIdDesc();

	//List<Post> findByIdInOrderByIdDesc(List<Long> postIdList);
	List<Post> findAllByDate(LocalDate date);
	List<Post> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

	//List<Post> findByUserAndDateRange(String user, LocalDate start, LocalDate end);

	List<Post> findByCreatorAndDateBetween(String creator, LocalDate startDate, LocalDate endDate);

    List<Post> findByCreatorAndDate(LocalDate date, String username);
}
