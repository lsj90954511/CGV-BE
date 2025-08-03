package com.example.cgv.code;

import lombok.Getter;

/**
 * SEOUL : 서울<br>
 * GYEONGGI : 경기<br>
 * INCHEON : 인천<br>
 * GANGWON : 강원<br>
 * CHUNGCHEONG : 대전/충청<br>
 * DAEGU : 대구<br>
 * BUSAN_ULSAN : 부산/울산<br>
 * GYEONGSANG : 경상<br>
 * JEOLLA : 광주/전라/제주
 */
public enum Region {
    SEOUL("서울"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    GANGWON("강원"),
    CHUNGCHEONG("대전/충청"),
    DAEGU("대구"),
    BUSAN_ULSAN("부산/울산"),
    GYEONGSANG("경상"),
    JEOLLA("광주/전라/제주");


    @Getter
    private final String codeName;

    Region(String codeName) {
        this.codeName = codeName;
    }

    public String getCode() {
        return name();
    }
}
