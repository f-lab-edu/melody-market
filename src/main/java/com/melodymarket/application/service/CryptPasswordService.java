package com.melodymarket.application.service;

public interface CryptPasswordService {
    String encryptPassword(String password);

    boolean isPasswordMatch(String inputPasswd, String storedPasswd);
}
