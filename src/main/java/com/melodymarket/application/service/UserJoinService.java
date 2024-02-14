package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;

public interface UserJoinService {
    void checkUserIdDuplication(String loginId, String sessionId);

    void checkNicknameDuplication(String nickname, String sessionId);

    void signUpUser(UserDto userDto, String sessionId);
}
