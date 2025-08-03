package com.example.cgv.movie;

import com.example.cgv.code.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {
    /** 영화관 고유키 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_id")
    private Long cinemaId;

    /** 영화관 이름 */
    @Column(name = "name", nullable = false)
    private String name;

    /** 위치 */
    @Column(name = "location", nullable = false)
    private String location;

    /** 보유 상영관 갯수 */
    @Column(name = "total_theater")
    private Integer totalTheater;

    /** 지역구분코드 */
    @Enumerated(EnumType.STRING)
    @Column(name = "location_code", nullable = false)
    private Region locationCode;
}
