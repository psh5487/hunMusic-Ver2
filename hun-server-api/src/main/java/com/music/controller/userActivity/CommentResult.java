package com.music.controller.userActivity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResult {

    private String userId;

    private String username;

    private String profileImgUrl;

    private String content;

    private LocalDateTime registeredAt;

}
