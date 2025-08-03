package com.example.cgv.movie;

import com.example.cgv.common.ResponseCode;
import com.example.cgv.common.ResponseDto;
import com.example.cgv.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;
    private final ReservationRepository reservationRepository;
    private final MovieImgRepository movieImgRepository;

    /**
     * 영화 전체 목록 조회
     * @return
     */
    public ResponseEntity<? super MovieDto> getAllMovies() {
        List<MovieDto> rtn = new ArrayList<MovieDto>();
        try {
            List<Movie> movies = movieRepository.findAll();
            for (Movie m : movies) {
                //예매율 구하기
                LocalDate today = LocalDate.now(ZoneId.systemDefault());

                LocalDateTime startOfDay = today.atStartOfDay();
                LocalDateTime endOfDay = today.atTime(23, 59, 59, 999_999_999);
                Timestamp startTimestamp = Timestamp.valueOf(startOfDay);
                Timestamp endTimestamp = Timestamp.valueOf(endOfDay);

                long movieCount = reservationRepository.countBySchedule_Movie_MovieIdAndRegDateBetween(m.getMovieId(), startTimestamp, endTimestamp);
                long totalCount = reservationRepository.countByRegDateBetween(startTimestamp, endTimestamp);

                double sales = 0.0;
                if (totalCount != 0) {
                    sales = ((double) movieCount / totalCount) * 100;
                }

                //영화 이미지 조회
                List<MovieImgUrlOnly> imgs = movieImgRepository.findByMovie_MovieId(m.getMovieId());

                //DTO 생성
                MovieDto movie = new MovieDto(
                        m.getMovieId(),
                        m.getName(),
                        m.getDescription(),
                        m.getScreenStrYmd(),
                        m.getScreenEndYmd(),
                        m.getAgeLimit(),
                        m.getDuration(),
                        m.getCreatedYmd(),
                        m.getCountry(),
                        m.getCompany(),
                        m.getTotalAudience(),
                        sales,
                        imgs
                );

                rtn.add(movie);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(rtn);
    }
}
