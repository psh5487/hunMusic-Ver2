package com.music.hun.model.userActivity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private String username;

    private String profileImgUrl;

    private String content;

    private LocalDateTime registeredAt;
}
