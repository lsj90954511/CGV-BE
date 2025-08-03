package com.example.cgv.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatId implements Serializable {

        @Column(name = "seat_row")
        private Integer seatRow;

        @Column(name = "seat_col")
        private Integer seatCol;

        @Column(name = "theater_id")
        private Long theaterId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SeatId)) return false;
            SeatId that = (SeatId) o;
            return Objects.equals(seatRow, that.seatRow)
                    && Objects.equals(seatCol, that.seatCol)
                    && Objects.equals(theaterId, that.theaterId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(seatRow, seatCol, theaterId);
        }
    }

    /** 행, 열, 상영관ID 복합키 */
    @EmbeddedId
    private SeatId id;

    /** 상영관 */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("theaterId")
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    /** 예매가능 여부 */
    @Column(name = "reservation_yn")
    private String reservationYn;
}
