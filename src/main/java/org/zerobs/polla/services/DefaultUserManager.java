package org.zerobs.polla.services;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.zerobs.polla.entities.db.User;
import org.zerobs.polla.exception.CustomRuntimeException;
import org.zerobs.polla.repositories.UserRepository;

import java.time.Year;
import java.time.ZoneId;

import static java.lang.System.currentTimeMillis;
import static org.zerobs.polla.exception.CustomExceptionType.*;

@Service
public class DefaultUserManager implements UserManager {
    private static final String EMAIL_CLAIM = "email";
    private static final String LOCALE_CLAIM = "locale";
    private static final String EMAIL_VERIFIED_CLAIM = "email_verified";
    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(User user, Jwt principal) {
        if (get(principal) != null)
            throw new CustomRuntimeException(USER_ALREADY_EXISTS);
        if (StringUtils.isBlank(user.getUsername()))
            throw new CustomRuntimeException(EMPTY_USERNAME, new String[]{getUsernameSuggestion()});

        user.setUsername(user.getUsername().trim());
        if (userRepository.usernameExists(user.getUsername()))
            throw new CustomRuntimeException(USERNAME_TAKEN, new String[]{getUsernameSuggestion()});

        if (user.getYearOfBirth() == null)
            throw new CustomRuntimeException(EMPTY_YEAR_OF_BIRTH);

        int currentYear = Year.now(ZoneId.of("UTC")).getValue();
        int age = currentYear - user.getYearOfBirth();
        if (age < 3)
            throw new CustomRuntimeException(USER_TOO_YOUNG);
        if (age > 130)
            throw new CustomRuntimeException(USER_TOO_OLD);

        if (user.getGender() == null)
            throw new CustomRuntimeException(EMPTY_GENDER);


        user.setId(principal.getSubject());
        user.setLocale(principal.getClaimAsString(LOCALE_CLAIM));
        user.setEmail(principal.getClaimAsString(EMAIL_CLAIM));
        user.setEmailVerified(principal.getClaimAsBoolean(EMAIL_VERIFIED_CLAIM));
        user.setCreatedOn(currentTimeMillis());
        user.setUpdatedOn(user.getCreatedOn());
        userRepository.save(user);
    }

    @Override
    public User get(Jwt principal) {
        var user = userRepository.get(User.getPk(principal.getSubject()));
        if (user == null)
            return null;
        if (!user.getEmail().equals(principal.getClaimAsString(EMAIL_CLAIM))) {
            user.setEmail(principal.getClaimAsString(EMAIL_CLAIM));
            user.setUpdatedOn(currentTimeMillis());
            userRepository.save(user);
        }
        return user;
    }

    private String getUsernameSuggestion() {
        var faker = new Faker(LocaleContextHolder.getLocale());
        return faker.superhero().name().replace(" ", "") + "_" + faker.random().hex(4).toLowerCase();
    }
}
