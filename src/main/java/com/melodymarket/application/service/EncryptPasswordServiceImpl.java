package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EncryptPasswordServiceImpl implements EncryptPasswordService {

    PasswordEncoder passwordEncoder;

    @Override
    public void encryptPassword(UserDto userDto) {
        userDto.setUserPasswd(passwordEncoder.encode(userDto.getUserPasswd()));
    }
}
