package com.melodymarket.application.service;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.dto.UserDto;

public interface UserInfoManageService {
    UserDto getUserDetails(Long userId);
    void modifyUserPassword(Long userId, UpdatePasswordDto updatePasswordDto);
    void modifyUserDetails(Long userId, UpdateUserDto userDto);
}
