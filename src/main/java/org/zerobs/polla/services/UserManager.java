package org.zerobs.polla.services;

import org.springframework.security.oauth2.jwt.Jwt;
import org.zerobs.polla.entities.db.User;

public interface UserManager {
    void add(User user, Jwt principal);

    User get();
}
