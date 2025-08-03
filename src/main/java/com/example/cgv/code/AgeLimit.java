package com.example.cgv.code;

import lombok.Getter;

/**
 * ALL : 전체관람가<br>
 * AGE_12 : 12세 관람가<br>
 * AGE_15 : 15세 관람가<br>
 * AGE_18 : 청소년 관람불가<br>
 * RE : 제한상영가
 */
public enum AgeLimit {
    ALL("전체관람가"),
    AGE_12("12세 관람가"),
    AGE_15("15세 관람가"),
    AGE_18("청소년 관람불가"),
    RE("제한상영가");


    @Getter
    private final String codeName;

    AgeLimit(String codeName) {
        this.codeName = codeName;
    }

    public String getCode() {
        return name();
    }
}
