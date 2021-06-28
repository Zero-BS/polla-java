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
        String messageText = messageSource.getMessage(e.getRuntimeExceptionType().getMessageTextPropertyId(),
                e.getArgs(), locale);

        //keep minimal logging, enable by changing to error level only if needed
        log.info("CustomRuntimeException messageId: {}, internalCode: {}, httpStatusCode: {}",
                e.getRuntimeExceptionType().getMessageTextPropertyId(), e.getRuntimeExceptionType().getInternalCode(),
                e.getRuntimeExceptionType().getHttpStatus(), e);

        return new ResponseEntity<>(new ExceptionResponseBody(null, messageText,
                e.getRuntimeExceptionType().getInternalCode()), e.getRuntimeExceptionType().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseBody> handleException(Exception e, Locale locale) {
        String messageTitle = messageSource.getMessage(CustomException.MESSAGE_TITLE_PROPERTY_ID, null, locale);
        String messageText = messageSource.getMessage(CustomException.MESSAGE_TEXT_PROPERTY_ID, null, locale);

        log.error("Exception message: {}", e.getMessage(), e);

        return new ResponseEntity<>(new ExceptionResponseBody(messageTitle, messageText, CustomException.INTERNAL_CODE),
                CustomException.HTTP_STATUS);
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
