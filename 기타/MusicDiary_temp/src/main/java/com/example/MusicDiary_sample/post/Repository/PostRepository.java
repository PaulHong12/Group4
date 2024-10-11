package com.example.MusicDiary_sample.post.Repository;

import com.example.MusicDiary_sample.post.domain.Music;
import com.example.MusicDiary_sample.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);
    List<Post> findById(int id);
    List<Post> findByDate(LocalDate date);

    //메인 페이지에 썸네일 로딩하기 위해서 필요

    //MONTH(m.addedAt) extracts the month from the addedAt field in the Music entity.
    //YEAR(m.addedAt) extracts the year from the addedAt field in the Music entity.
    // = : param을 나타냄
    @Query("SELECT m FROM Music m WHERE MONTH(m.addedAt) = :month AND YEAR(m.addedAt) = :year")
    List<Music> findAllMusicAddedInMonth(@Param("month") int month, @Param("year") int year);
}