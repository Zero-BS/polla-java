package org.zerobs.polla.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerobs.polla.entities.db.User;
import org.zerobs.polla.repositories.UserRepository;

@Service
public class DefaultUserManager implements UserManager {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User add(User user) {
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        System.out.println("a");
        return userRepository.save(user);
    }
}
