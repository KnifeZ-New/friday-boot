package com.knifez.fridayboot.security.utils;

import com.knifez.fridayboot.entity.AppUser;
import com.knifez.fridayboot.service.IAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zhang
 */
@Component
@RequiredArgsConstructor
public class CurrentUserUtils {
    private final IAppUserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public AppUser getCurrentUser(){
        return userService.findByAccount(getCurrentUserName());
    }
    public String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public boolean checkLogin(String currentPassword,String password){
        return this.bCryptPasswordEncoder.matches(currentPassword, password);
    }
}
