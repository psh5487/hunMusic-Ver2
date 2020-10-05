package com.music.hun.service.user;

import com.music.hun.model.user.User;
import com.music.hun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* 회원 등록 */
    public int registerMember(String email, String name, String password) {
        // 중복 체크
        if (userRepository.findByEmail(email).isPresent()) {
            return -1;
        }

        // 필수 값 체크
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return -2;
        }

        User user = User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_MEMBER"))
                .build();
        userRepository.save(user);
        return 0;
    }

    /* 이메일로 회원 정보 찾기 */
    public Optional<User> findMemberByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /* 비밀번호 매칭 확인 */
    public Boolean matchPassword(String inputPassword, String savedPassword) {
        return passwordEncoder.matches(inputPassword, savedPassword);
    }
}
