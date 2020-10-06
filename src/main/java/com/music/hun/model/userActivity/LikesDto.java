package com.music.hun.model.userActivity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LikesDto {

    private String action;

    private String username;

    private Long musicId;

    private LocalDateTime createdAt;
}
