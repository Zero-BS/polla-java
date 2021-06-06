package org.zerobs.polla.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomExceptionType {
    //never create the UNEXPECTED_ERROR explicitly
    //never change internal code of existing items
    UNEXPECTED_ERROR("error.unexpected.title", "error.unexpected.text",
            4000, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_ALREADY_EXISTS("error.user.already.exists", 4001),
    EMPTY_USERNAME("error.empty.username", 4002),
    USERNAME_TAKEN("error.username.taken", 4003),
    EMPTY_YEAR_OF_BIRTH("error.empty.year.of.birth", 4004),
    USER_TOO_YOUNG("error.user.too.young", 4005),
    USER_TOO_OLD("error.user.too.old", 4006),
    EMPTY_GENDER("error.empty.gender", 4007);

    private final String messageTitlePropertyId;
    private final String messageTextPropertyId;
    private final int internalCode;
    private final HttpStatus httpStatus;

    CustomExceptionType(String messagePropertyId, int internalCode) {
        this(null, messagePropertyId, internalCode, HttpStatus.BAD_REQUEST);
    }
}
