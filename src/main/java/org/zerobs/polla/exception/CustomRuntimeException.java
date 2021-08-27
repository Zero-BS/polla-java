package org.zerobs.polla.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomRuntimeException extends RuntimeException {
    private final RuntimeExceptionType runtimeExceptionType;
    private final String[] args;

    public CustomRuntimeException(RuntimeExceptionType runtimeExceptionType) {
        this.runtimeExceptionType = runtimeExceptionType;
        args = null;
    }

    public CustomRuntimeException(RuntimeExceptionType runtimeExceptionType, Throwable e) {
        super(runtimeExceptionType.getMessageTextPropertyId(), e);
        this.runtimeExceptionType = runtimeExceptionType;
        args = null;
    }
}
