package com.music.service.user;

import com.google.common.eventbus.EventBus;
import com.music.error.AlreadyExistException;
import com.music.error.NotFoundException;
import com.music.event.JoinEvent;
import com.music.model.user.Email;
import com.music.model.user.User;
import com.music.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventBus eventBus;

    /* 회원 등록 */
    @Transactional
    public User join(Email email, String name, String password) {
        checkArgument(isNotEmpty(name), "name must be provided");
        checkArgument(isNotEmpty(password), "password must be provided");

        // 중복 체크
        findMemberByEmail(email).ifPresent((user) -> {
            throw new AlreadyExistException(user.getEmail());
        });

        User user = User.builder()
                .email(email.getAddress())
                .name(name)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        User saved = userRepository.save(user);

        // Join event 발생 시키기
        eventBus.post(new JoinEvent(saved));

        return saved;
    }

    /* 로그인 */
    @Transactional
    public User login(Email email, String password) {
        checkNotNull(password, "password must be provided");

        // 해당 이메일 가입 여부 확인
        User user = findMemberByEmail(email)
                .orElseThrow(() -> new NotFoundException(User.class, email));

        // 비밀번호 일치 여부 확인
        if (!matchPassword(password, user.getPassword())) {
            throw new NotFoundException(User.class, email);
        }

        return user;
    }

    /* 프로필 이미지 등록 */
    @Transactional
    public User updateProfileImage(Long userId, String profileImageUrl) {
        User user = findMemberById(userId)
                .orElseThrow(() -> new NotFoundException(User.class, userId));

        user.setProfileImgUrl(profileImageUrl);
        return userRepository.save(user);
    }

    /* 이메일로 회원 정보 찾기 */
    @Transactional(readOnly = true)
    public Optional<User> findMemberByEmail(Email email) {
        checkNotNull(email, "email must be provided.");

        return userRepository.findByEmail(email.getAddress());
    }

    /* ID로 회원 정보 찾기 */
    @Transactional(readOnly = true)
    public Optional<User> findMemberById(Long userId) {
        checkNotNull(userId, "userId must be provided.");

        return userRepository.findById(userId);
    }

    /* 비밀번호 매칭 확인 */
    public Boolean matchPassword(String inputPassword, String savedPassword) {
        return passwordEncoder.matches(inputPassword, savedPassword);
    }
}
