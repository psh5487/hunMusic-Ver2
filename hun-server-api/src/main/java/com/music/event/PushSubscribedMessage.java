package com.music.event;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class PushSubscribedMessage {

    private Long id;

    private Long userId;

    private String notificationEndpoint;

    private String publicKey;

    private String auth;

    private LocalDateTime createdAt;

}
