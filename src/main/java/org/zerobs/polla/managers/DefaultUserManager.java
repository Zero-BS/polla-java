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
        return userRepository.save(user);
    }

    public boolean uselessMethodWoCoverage1(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage2(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage3(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage4(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage5(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage6(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage7(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage8(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage9(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage0(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage10(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage11(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage12(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage13(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage14(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage15(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage16(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage17(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage18(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage19(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
    public boolean uselessMethodWoCoverage20(String s) {
        if (s== null)
            return true;
        if (s.isEmpty())
            return true;
        return false;
    }
}
