package com.music.hun.model.userActivity;

import com.music.hun.model.music.Music;
import com.music.hun.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long userId;

    @Setter
    private Long musicId;

    private LocalDateTime createdAt;
}
