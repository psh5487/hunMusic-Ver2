package com.music.error;

import com.music.util.MessageUtils;

public class AlreadyExistException extends ServiceRuntimeException {
    static final String MESSAGE_KEY = "error.alreadyExist";

    static final String MESSAGE_DETAIL = "error.alreadyExist.details";

    public AlreadyExistException(String message) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new Object[]{message});
    }

    @Override
    public String getMessage() {
        return MessageUtils.getMessage(getDetailKey(), getParams());
    }

    @Override
    public String toString() {
        return MessageUtils.getMessage(getMessageKey());
    }
}
