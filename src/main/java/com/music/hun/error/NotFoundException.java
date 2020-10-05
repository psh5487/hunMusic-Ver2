package com.music.hun.error;

import com.music.hun.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class NotFoundException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.notfound";

    static final String MESSAGE_DETAILS = "error.notfound.details";

    public NotFoundException(String targetName, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
    }

    public NotFoundException(Class<?> cls, Object... values) {
        this(cls.getSimpleName(), values);
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
