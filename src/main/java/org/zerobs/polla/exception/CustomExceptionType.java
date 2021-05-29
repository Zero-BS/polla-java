package org.zerobs.polla.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomExceptionType {
    //never create the UNEXPECTED_ERROR explicitly
    UNEXPECTED_ERROR("error.unexpected.title", "error.unexpected.text",
            4002, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_ALREADY_EXISTS("error.user.already.exists.title", "error.user.already.exists.text",
            4001, HttpStatus.BAD_REQUEST);

    private final String messageTitlePropertyId;
    private final String messageTextPropertyId;
    private final int internalCode;
    private final HttpStatus httpStatus;

    CustomExceptionType(String messageTitlePropertyId, String messageTextPropertyId, int internalCode, HttpStatus httpStatus) {
        this.messageTitlePropertyId = messageTitlePropertyId;
        this.messageTextPropertyId = messageTextPropertyId;
        this.internalCode = internalCode;
        this.httpStatus = httpStatus;
    }
}
