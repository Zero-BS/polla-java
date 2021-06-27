package org.zerobs.polla.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomRuntimeException extends RuntimeException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private final RuntimeExceptionType runtimeExceptionType;
    private final String[] args;

    public CustomRuntimeException(RuntimeExceptionType runtimeExceptionType) {
        this(runtimeExceptionType, null);
    }
}
