package org.zerobs.polla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
    public User add(@RequestBody User user) {
        System.out.println(LocaleContextHolder.getLocale());
        return userManager.add(user);
    }
}