package com.melodymarket.application.user.service;

public interface CryptPasswordService {
    String encryptPassword(String password);

    boolean isPasswordMatch(String inputPasswd, String storedPasswd);
}
