package com.example.cgv.user;

import com.example.cgv.common.ResponseCode;
import com.example.cgv.common.ResponseDto;
import com.example.cgv.common.ResponseMessage;
import com.example.cgv.provider.EmailProvider;
import com.example.cgv.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepo;
    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RedisTemplate<String, String> redisTemplate;

    // 이메일 중복확인 및 인증코드 전송
    public ResponseEntity<? super UserDto> emailCertification(UserDto dto) {
        try {
            String email = dto.getEmail();
            boolean isExistEmail = userRepo.existsByEmail(email);
            if (isExistEmail)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL));

            String certificationNumber = generateValidationCode();

            boolean isSucceed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSucceed)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(ResponseCode.MAIL_FAIL, ResponseMessage.MAIL_FAIL));

            // redis에 저장
            redisTemplate.opsForValue().set(
                    email,
                    certificationNumber,
                    3,
                    TimeUnit.MINUTES);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
    }

    // 인증코드 확인
    public ResponseEntity<? super UserDto> checkCertification(UserDto dto) {
        try {
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            String dbCertiNum = redisTemplate.opsForValue().get(email);

            if (dbCertiNum == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(ResponseCode.CERTIFICATE_FAIL, ResponseMessage.CERTIFICATE_FAIL));

            if (!dbCertiNum.equals(certificationNumber))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(ResponseCode.CERTIFICATE_FAIL, ResponseMessage.CERTIFICATE_FAIL));

            redisTemplate.delete(email);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        ResponseDto responseBody = new ResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 회원가입
    public ResponseEntity<? super UserDto> signUp(UserDto dto) {

        // 1. validation check
        if (dto == null) return ResponseDto.validationFail();

        User isDuplicateId = userRepo.findByLoginId(dto.getLoginId());
        if (isDuplicateId != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID));

        User isDuplicateNickname = userRepo.findByNickname(dto.getNickname());
        if (isDuplicateNickname != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(ResponseCode.DUPLICATE_NICKNAME, ResponseMessage.DUPLICATE_NICKNAME));

        // 2. password encoding & setting value
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setDeletedYn("N");
        dto.setRole("USER");

        // 3. save User
        try {
            User user = new User(dto);
            userRepo.save(user);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto());
    }

    // 로그인
    public ResponseEntity<? super UserDto> signIn(UserDto dto) {

        // 1. validation check
        if (dto == null) return ResponseDto.validationFail();

        String accessToken = null;
        String refreshToken = null;
        String email = null;
        String loginId = dto.getLoginId();

        try {
            User user = userRepo.findByLoginId(loginId);
            if (user == null || "Y".equals(user.getDeletedYn()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new ResponseDto(ResponseCode.SIGN_IN_FAILED, ResponseMessage.SIGN_IN_FAILED));

            String password = dto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new ResponseDto(ResponseCode.SIGN_IN_FAILED, ResponseMessage.SIGN_IN_FAILED));

            accessToken = jwtProvider.createAccessToken(user.getEmail());
            refreshToken = jwtProvider.createRefreshToken(user.getEmail());
            email = user.getEmail();

            dto.setAccessToken(accessToken);
            dto.setRefreshToken(refreshToken);
            dto.setEmail(email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    public ResponseEntity<? super UserDto> reissue(String refreshToken) {
        String accessToken = null;
        String newRefreshToken = null;
        List<String> tokens = new ArrayList<>();

        try {
            tokens = jwtProvider.reissue(refreshToken);
            if (tokens == null) {
                log.info("tokens get null");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(ResponseCode.EXPIRED_TOKEN, ResponseMessage.EXPIRED_TOKEN));
            }

            accessToken = tokens.get(0);
            newRefreshToken = tokens.get(1);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.validationFail();
        }
        UserDto resultDto = new UserDto();
        resultDto.setAccessToken(accessToken);
        resultDto.setRefreshToken(newRefreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(resultDto);
    }

    // 6자리 인증코드 생성
    private String generateValidationCode() {
        Random rand = new Random();
        int number = rand.nextInt(999999);

        return String.format("%06d", number);
    }

}
