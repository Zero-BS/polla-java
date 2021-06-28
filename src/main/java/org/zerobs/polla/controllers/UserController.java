package org.zerobs.polla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.zerobs.polla.entities.db.User;
import org.zerobs.polla.services.UserManager;

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
        return userManager.get(principal);
    }
}