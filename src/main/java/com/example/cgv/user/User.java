package com.example.cgv.user;

import jakarta.persistence.Entity;
import lombok.Getter;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long userId;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "prof_img")
    private String profImg;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "reg_ymd")
    private LocalDateTime regYmd;

    @Column(name = "mod_ymd")
    private String modYmd;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted_yn", length = 1)
    private String deletedYn;

    @Column(name = "discount")
    private String discount;

    @Column(name = "role")
    private String role;

    public User(UserDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.loginId = dto.getLoginId();
        this.regYmd = LocalDateTime.now();
        this.profImg = dto.getProfImg();
        this.phone = dto.getPhone();
        this.modYmd = dto.getModYmd();
        this.name = dto.getName();
        this.deletedYn = dto.getDeletedYn();
        this.discount = dto.getDiscount();
        this.role = dto.getRole();
    }
}

