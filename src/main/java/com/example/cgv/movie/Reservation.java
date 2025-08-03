package com.example.cgv.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    /** 예매 고유키 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    /** 상영시간표 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    //TODO 결제 참조


    /** 좌석 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "seat_row", referencedColumnName = "seat_row", nullable = false),
            @JoinColumn(name = "seat_col", referencedColumnName = "seat_col", nullable = false),
            @JoinColumn(name = "theater_id", referencedColumnName = "theater_id", nullable = false)
    })
    private Seat seat;

    /** 등록일(예매일) */
    @Column(name = "reg_date", nullable = false)
    private Timestamp regDate;
}
