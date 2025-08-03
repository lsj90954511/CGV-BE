package com.example.cgv.user;

import com.example.cgv.common.ResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends ResponseDto {
    private Long userId;
    private String loginId;
    private String email;
    private String nickname;
    private String profImg;
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private String discount;
    private String role;

    @JsonIgnore
    private String deletedYn;

    @JsonIgnore
    private String modYmd;
    private String certificationNumber;

    // response
    private String accessToken;
    private String refreshToken;
}


