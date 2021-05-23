package org.zerobs.polla.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import static org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure;
import static org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.success;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String GOOGLE_ISSUER_URI = "https://accounts.google.com";
    @Value("${android.google.client.id}")
    private String androidGoogleClientId;
    @Value("${web.google.client.id}")
    private String webGoogleClientId;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(a -> a.anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromIssuerLocation(GOOGLE_ISSUER_URI);

        jwtDecoder.setJwtValidator(token -> {
            if (!token.getAudience().contains(webGoogleClientId))
                return failure(new OAuth2Error("invalid-aud", "invalid aud in token", null));
            if (!token.getClaimAsString("azp").equals(androidGoogleClientId))
                return failure(new OAuth2Error("invalid-azp", "invalid azp in token", null));
            if (!GOOGLE_ISSUER_URI.equals(token.getIssuer().toString()))
                return failure(new OAuth2Error("invalid-issuer", "invalid issuer", null));
            return success();
        });

        return jwtDecoder;
    }
}