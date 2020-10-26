package com.music.event;

import com.music.model.user.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class JoinEvent {
    private final Long userId;

    private final String name;

    public JoinEvent(User user) {
        this.userId = user.getId();
        this.name = user.getName();
    }
}
