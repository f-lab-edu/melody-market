package com.melodymarket.application.user.service;

import com.melodymarket.application.user.dto.UpdatePasswordDto;
import com.melodymarket.application.user.dto.UpdateUserDto;
import com.melodymarket.application.user.dto.UserDto;

public interface UserInfoManageService {
    UserDto getUserDetails(Long id);
    void modifyUserPassword(Long id, UpdatePasswordDto updatePasswordDto);
    void modifyUserDetails(Long id, UpdateUserDto updateUserDto);
    void deleteUser(Long id, String password);
}
