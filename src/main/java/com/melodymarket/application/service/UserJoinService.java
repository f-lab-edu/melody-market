package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;

public interface UserJoinService {
    public boolean checkUserIdDuplication(String userId);

    public boolean checkNicknameDuplication(String nickname);

    public boolean signUpUser(UserDto userDto);
}
