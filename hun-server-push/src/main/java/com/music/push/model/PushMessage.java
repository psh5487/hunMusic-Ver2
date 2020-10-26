package com.music.push.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class PushMessage {

    private final String title;

    private final String clickTarget;

    private final String message;
}
