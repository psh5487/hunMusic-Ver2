package com.music.controller.user;

import com.music.model.user.User;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@ToString
public class JoinResult {

    private final String jwtToken;

    private final User user;

    public JoinResult(String jwtToken, User user) {
        checkNotNull(jwtToken, "jwtToken must be provided");
        checkNotNull(user, "user must be provided");

        this.jwtToken = jwtToken;
        this.user = user;
    }

}
