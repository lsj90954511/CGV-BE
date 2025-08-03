package com.example.cgv.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 이메일 중복확인 및 인증코드 전송
    @PostMapping("/email-certification")
    public ResponseEntity<? super UserDto> emailCertification(@RequestBody UserDto requestBody) {
        return userService.emailCertification(requestBody);
    }

    // 인증코드 확인
    @PostMapping("/check-certification")
    public ResponseEntity<? super UserDto> checkCertification(@RequestBody UserDto requestBody) {
        return userService.checkCertification(requestBody);
    }

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<? super UserDto> signUp(@RequestBody UserDto requestBody) {
        return userService.signUp(requestBody);
    }

    // 로그인
    @PostMapping("/sign-in")
    public ResponseEntity<? super UserDto> signIn(@RequestBody UserDto requestBody) {
        return userService.signIn(requestBody);
    }

    // AT 재발급
    @PostMapping("/reissue")
    public ResponseEntity<? super UserDto> reissueToken(@RequestHeader(value = "refreshToken") String refreshToken) {
        return userService.reissue(refreshToken);
    }
}
