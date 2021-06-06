package org.zerobs.polla.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zerobs.polla.entities.ExceptionResponseBody;

import java.util.Locale;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ExceptionResponseBody> handleCustomRuntimeException(CustomRuntimeException e, Locale locale) {
        String messageTitle = null;
        if (e.getCustomExceptionType().getMessageTitlePropertyId() != null)
            messageTitle = messageSource.getMessage(e.getCustomExceptionType().getMessageTitlePropertyId(), null, locale);
        String messageText = messageSource.getMessage(e.getCustomExceptionType().getMessageTextPropertyId(), e.getArgs(), locale);

        //keep minimal logging, enable by changing to error level only if needed
        log.info("CustomRuntimeException messageId: {}, messageTitle: {}, internalCode: {}, httpStatusCode: {}",
                e.getCustomExceptionType().getMessageTitlePropertyId(), messageTitle, e.getCustomExceptionType().getInternalCode(),
                e.getCustomExceptionType().getHttpStatus().value(), e);

        return new ResponseEntity<>(new ExceptionResponseBody(messageTitle, messageText, e.getCustomExceptionType().getInternalCode()),
                e.getCustomExceptionType().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBody> handleException(Exception e, Locale locale) {
        var customExceptionType = CustomExceptionType.UNEXPECTED_ERROR;
        String messageTitle = messageSource.getMessage(customExceptionType.getMessageTitlePropertyId(), null, locale);
        String messageText = messageSource.getMessage(customExceptionType.getMessageTextPropertyId(), null, locale);

        log.error("Exception message: {}", e.getMessage(), e);

        return new ResponseEntity<>(new ExceptionResponseBody(messageTitle, messageText, customExceptionType.getInternalCode()),
                customExceptionType.getHttpStatus());
    }

    @EventListener
    public void onAuthenticationFailureBadCredentials(AuthenticationFailureBadCredentialsEvent badCredentials) {
        if (badCredentials.getAuthentication() instanceof BearerTokenAuthenticationToken) {
            //logging can cause problem if bad actor keep on calling the api with invalid token
            log.info("jwt validation failed. message: {}, details: {}",
                    badCredentials.getException().getMessage(),
                    badCredentials.getAuthentication().getDetails().toString());
        }
    }

}
