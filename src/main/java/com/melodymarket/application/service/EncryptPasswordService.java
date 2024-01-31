package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;

public interface EncryptPasswordService {
    public void encryptPassword(UserDto userDto);
}
