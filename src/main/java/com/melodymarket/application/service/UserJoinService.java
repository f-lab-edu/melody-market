package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;

public interface UserJoinService {
    void checkUserIdDuplication(String loginId);

    void checkNicknameDuplication(String nickname);

    void signUpUser(UserDto userDto);
}
