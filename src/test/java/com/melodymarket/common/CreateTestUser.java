package com.melodymarket.common;

import com.melodymarket.application.dto.UserDto;

public class CreateTestUser {
    public static UserDto getTestUser() {
        return UserDto.builder()
                .loginId("testuser")
                .username("테스트")
                .userPasswd("test123!")
                .nickname("imtest")
                .email("test@example.com")
                .birthDate("19970908")
                .build();
    }

}
