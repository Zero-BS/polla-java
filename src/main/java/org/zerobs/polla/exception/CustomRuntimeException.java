package org.zerobs.polla.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomRuntimeException extends RuntimeException {
    private final CustomExceptionType customExceptionType;
    private final String[] args;

    public CustomRuntimeException(CustomExceptionType customExceptionType) {
        this(customExceptionType, null);
    }
}
