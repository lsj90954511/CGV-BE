package com.example.cgv.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // 영화 전체 목록 조회
    @GetMapping("")
    public ResponseEntity<? super MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }
}
