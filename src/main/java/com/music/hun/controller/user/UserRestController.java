package com.music.hun.controller.user;

import com.music.hun.controller.network.ApiResult;
import com.music.hun.model.user.User;
import com.music.hun.model.user.UserDto;
import com.music.hun.repository.user.UserRepository;
import com.music.hun.security.JwtTokenProvider;
import com.music.hun.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.music.hun.controller.network.ApiResult.OK;
import static com.music.hun.controller.network.ApiResult.ERROR;

@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> user) {

        User member = userService.findMemberByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다.")); // 가입되지 않은 경우

        if (!userService.matchPassword(user.get("password"), member.getPassword())) { // 비밀번호 틀린 경우
            return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED);
        }

        // 사용자의 이메일, 권한 담긴 토큰 생성
        String jwtToken = jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    /* Jwt 토큰으로부터 사용자 정보 얻기 */
    @GetMapping("/userInfo")
    public ResponseEntity userInfo(@AuthenticationPrincipal User user) { // HttpServletRequest request

        if (user == null) {
            return new ResponseEntity<>("로그인된 사용자가 아닙니다.", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        UserDto userDto = UserDto.builder()
                .id(authenticatedUser.getId())
                .email(authenticatedUser.getEmail())
                .name(authenticatedUser.getName())
                .build();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
