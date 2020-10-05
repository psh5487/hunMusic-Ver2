package com.music.hun.model.user;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


//    private final Long id;
//    private final String name;
//    private final Email email;
//    private String password;
//
//    private String profileImageUrl;
//    private int loginCount;
//    private LocalDateTime lastLoginAt;
//    private final LocalDateTime createAt;
//
//    public User(String name, Email email, String password) {
//        this(name, email, password, null);
//    }
//
//    public User(String name, Email email, String password, String profileImageUrl) {
//        this(null, name, email, password, profileImageUrl, 0, null, null);
//    }
//
//    public User(Long id, String name, Email email, String password, String profileImageUrl,
//                int loginCount, LocalDateTime lastLoginAt, LocalDateTime createAt) {
//        checkArgument(isNotEmpty(name), "name must be provided.");
//        checkArgument(
//                name.length() >= 1 && name.length() <= 10,
//                "name length must be between 1 and 10 characters."
//        );
//        checkNotNull(email, "email must be provided.");
//        checkNotNull(password, "password must be provided.");
//        checkArgument(
//                profileImageUrl == null || profileImageUrl.length() <= 255,
//                "profileImageUrl length must be less than 255 characters."
//        );
//
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.profileImageUrl = profileImageUrl;
//        this.loginCount = loginCount;
//        this.lastLoginAt = lastLoginAt;
//        this.createAt = defaultIfNull(createAt, LocalDateTime.now());
//    }
//
//    public void login(PasswordEncoder passwordEncoder, String credentials) {
//
//    }

}
