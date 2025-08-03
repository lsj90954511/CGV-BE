package com.example.cgv.movie;

import com.example.cgv.code.AgeLimit;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    /** 영화 고유키 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    /** 영화명 */
    @Column(name = "name", nullable = false)
    private String name;

    /** 상세설명 */
    @Column(name = "description", nullable = false)
    private String description;

    /** 상영시작일자 */
    @Column(name = "screen_str_ymd", nullable = false)
    private Timestamp screenStrYmd;

    /** 상영종료일자 */
    @Column(name = "screen_end_ymd", nullable = false)
    private Timestamp screenEndYmd;

    /** 연령제한*/
    @Enumerated(EnumType.STRING)
    @Column(name = "age_limit", nullable = false)
    private AgeLimit ageLimit;

    /** 러닝타임 */
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /** 등록일자 */
    @Column(name = "created_ymd", nullable = false)
    private Timestamp createdYmd;

    /** 제작국 */
    @Column(name = "country")
    private String country;

    /** 제작사 */
    @Column(name = "company")
    private String company;

    /** 누적관객수 */
    @Column(name = "total_audience")
    private Long totalAudience;
}