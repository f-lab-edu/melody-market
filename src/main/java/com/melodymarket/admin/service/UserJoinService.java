package com.melodymarket.admin.service;

import com.melodymarket.admin.dto.UserDto;

public interface UserJoinService {
    public boolean checkUserIdDuplication(String userId);

    public boolean checkNicknameDuplication(String nickname);

    public boolean signUpUser(UserDto userDto);
}
