package com.music.controller.user;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.music.controller.network.ApiResult;
import com.music.error.UnauthorizedException;
import com.music.model.user.*;
import com.music.service.aws.S3Service;
import com.music.service.user.UserService;
import com.music.util.AttachedFile;
import com.music.security.AuthenticationRequest;
import com.music.security.AuthenticationResult;
import com.music.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.music.controller.network.ApiResult.OK;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final S3Service s3Service;
    private final Logger log = LoggerFactory.getLogger(getClass());

    /* 이메일 중복 확인 */
    @PostMapping("/exists")
    public ApiResult<Boolean> checkEmail(@RequestBody Map<String, String> request) {
        Email email = new Email(request.get("address"));
        return OK(
             userService.findMemberByEmail(email).isPresent()
        );
    }

    /* 회원 가입 */
    @PostMapping(path = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<JoinResult> join(@ModelAttribute JoinRequest joinRequest,
                                      @RequestPart(required = false) MultipartFile file) {
        User user = userService.join(
            new Email(joinRequest.getEmail()),
            joinRequest.getName(),
            joinRequest.getPassword()
        );

        // 프로필 이미지 업로드
        supplyAsync(() ->
            uploadProfileImage(AttachedFile.toAttachedFile(file))
        ).thenAccept(opt ->
            opt.ifPresent(profileImageUrl ->
                // 이미지가 정상적으로 업로드가 완료된 경우, profileImageUrl != null
                userService.updateProfileImage(user.getId(), profileImageUrl)
            )
        );

        // supplyAsync 실행 완료 여부와 관계 없이 리턴한다.
        String jwtToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return OK(
              new JoinResult(jwtToken, user)
        );
    }

    public Optional<String> uploadProfileImage(AttachedFile profileFile) {
        String profileImageUrl = null;
        if (profileFile != null) {
            String key = profileFile.randomName("profiles", "jpeg");
            try {
                profileImageUrl = s3Service.upload(profileFile, key, null);
            } catch (AmazonS3Exception | IOException e) {
                log.warn("Amazon S3 error (key: {}): {}", key, e.getMessage(), e);
            }
        }
        return ofNullable(profileImageUrl);
    }

    /* 로그인 */
    @PostMapping("/login")
    public ApiResult<AuthenticationResult> login(@RequestBody AuthenticationRequest request) {
        Email email = new Email(request.getEmail());
        String password = request.getPassword();

        try {
            User user = userService.login(email, password);
            String jwtToken = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
            return OK(
                new AuthenticationResult(jwtToken, user)
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    /* Jwt 토큰으로부터 사용자 정보 얻기 */
    @GetMapping("/info")
    public ResponseEntity userInfo(@AuthenticationPrincipal User user) {

        if (user == null) {
            return new ResponseEntity<>("Not Logged User", HttpStatus.UNAUTHORIZED);
        }

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .profileImgUrl(user.getProfileImgUrl())
                .roles(user.getRoles())
                .build();

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
