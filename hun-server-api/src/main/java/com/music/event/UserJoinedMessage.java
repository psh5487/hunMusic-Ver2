package com.music.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinedMessage {
    private Long userId;

    private String name;

    public UserJoinedMessage(JoinEvent event) {
        this.userId = event.getUserId();
        this.name = event.getName();
    }
}
