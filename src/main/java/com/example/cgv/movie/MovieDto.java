package com.example.cgv.movie;

import com.example.cgv.code.AgeLimit;
import com.example.cgv.common.ResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto extends ResponseDto {
    /** 영화 고유키 */
    private Long movieId;
    /** 영화명 */
    private String name;
    /** 상세설명 */
    private String description;
    /** 상영시작일자 */
    private Timestamp screenStrYmd;
    /** 상영종료일자 */
    private Timestamp screenEndYmd;
    /** 연령제한*/
    private AgeLimit ageLimit;
    /** 러닝타임 */
    private Integer duration;
    /** 등록일자 */
    private Timestamp createdYmd;
    /** 제작국 */
    private String country;
    /** 제작사 */
    private String company;
    /** 누적관객수 */
    private Long totalAudience;
    /** 예매율 */
    private Double ticketSales;
    /** 영화 이미지 url*/
    private List<MovieImgUrlOnly> urls;
}
