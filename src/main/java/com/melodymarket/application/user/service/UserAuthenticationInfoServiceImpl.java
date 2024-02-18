package com.melodymarket.application.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationInfoServiceImpl implements UserAuthenticationInfoService {
    @Override
    public String getCurrentUserLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                return (String) principal;
            }
        }
        return null;
    }
}
