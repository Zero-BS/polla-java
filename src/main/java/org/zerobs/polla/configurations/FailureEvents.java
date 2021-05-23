package org.zerobs.polla.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FailureEvents {
    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent badCredentials) {
        if (badCredentials.getAuthentication() instanceof BearerTokenAuthenticationToken) {
            log.error("jwt validation failed. message: {}, localizedMessage: {}, details: {}",
                    badCredentials.getException().getMessage(), badCredentials.getException().getLocalizedMessage(),
                    badCredentials.getAuthentication().getDetails().toString());
            //get error code from
//            if (badCredentials.getException().getCause().getClass().equals(JwtValidationException.class)
//            && ((JwtValidationException)badCredentials.getException().getCause()).getErrors().stream().map(e -> e.getErrorCode()))
            //ignoring throwable contained in badCredentials.getException().getCause() as stack trace is not useful
        }
    }
}