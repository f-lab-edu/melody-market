package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptPasswordServiceImpl implements EncryptPasswordService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EncryptPasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void encryptPassword(UserDto userDto) {
        userDto.setUserPasswd(passwordEncoder.encode(userDto.getUserPasswd()));
    }
}
