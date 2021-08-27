package org.zerobs.polla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.zerobs.polla.entities.db.User;
import org.zerobs.polla.exception.CustomRuntimeException;
import org.zerobs.polla.services.UserManager;

import static org.zerobs.polla.exception.RuntimeExceptionType.INVALID_USER;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserManager userManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody(required = false) User user, @AuthenticationPrincipal Jwt principal) {
        userManager.add(user, principal);
    }

    @GetMapping
    public User get(@AuthenticationPrincipal Jwt principal) {
        var user = userManager.get(principal);
        if (user == null) throw new CustomRuntimeException(INVALID_USER);
        return user;
    }

    @DeleteMapping
    public void delete(@AuthenticationPrincipal Jwt principal) {
        userManager.delete(principal);
    }
}