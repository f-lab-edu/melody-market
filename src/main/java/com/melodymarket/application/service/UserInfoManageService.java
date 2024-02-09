package com.melodymarket.application.service;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.dto.UserDto;

public interface UserInfoManageService {
    UserDto getUserDetails(Long id);
    void modifyUserPassword(Long id, UpdatePasswordDto updatePasswordDto);
    void modifyUserDetails(Long id, UpdateUserDto updateUserDto);
    void deleteUser(Long id, String password);
}
