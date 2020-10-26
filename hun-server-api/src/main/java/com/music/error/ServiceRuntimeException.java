package com.music.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceRuntimeException extends RuntimeException {
    private final String messageKey;

    private final String detailKey;

    private final Object[] params;
}
