package org.zerobs.polla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerobs.polla.entities.db.User;
import org.zerobs.polla.managers.UserManager;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserManager userManager;

    @PostMapping
    public User add(@RequestBody User user, @AuthenticationPrincipal Jwt principal) {
        //todo: fix this
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == principal);
        System.out.println(principal.getSubject());
        System.out.println(principal);
        System.out.println(principal.getClaims());
        System.out.println(LocaleContextHolder.getLocale());
        throw new NullPointerException("yoo null pointer, aw!");
        //throw new CustomRuntimeException(USER_ALREADY_EXISTS);
        //return userManager.add(user);
    }
}