package com.example.cgv.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MovieImgRepository extends JpaRepository<MovieImg, Long> {
    /** 영화별 이미지 url 조회 */
    List<MovieImgUrlOnly> findByMovie_MovieId(Long movieId);
}
