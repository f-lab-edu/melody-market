package com.melodymarket.util;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 로그인 여부 확인
 */
public class LoginUtil {
    public static boolean isLogin() {
        boolean result = true;

        Object pricipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(pricipal instanceof String){
            result = false;
        }

        return result;
    }
}
