package com.example.cgv.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    /** 영화별, 날짜별 예매 건수 */
    Long countBySchedule_Movie_MovieIdAndRegDateBetween(Long movieId, Timestamp start, Timestamp end);


    /** 날짜별 예매 건수 */
    Long countByRegDateBetween(Timestamp start, Timestamp end);
}
