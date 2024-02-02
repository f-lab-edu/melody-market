package com.melodymarket.domain.user.model;

import com.melodymarket.application.dto.UserDto;
import lombok.Data;

@Data
public class Account {
    private Long userId;
    private String loginId;
    private String username;
    private String userPasswd;
    private String nickname;
    private String email;
    private String birthDate;
    private String joinDate;
    private Integer membershipLevel;

    public UserDto convertToUserDto() {
        UserDto userDto = new UserDto();
        userDto.setLoginId(loginId);
        userDto.setUsername(username);
        userDto.setNickname(nickname);
        userDto.setEmail(email);
        userDto.setBirthDate(birthDate);
        userDto.setMembershipLevel(membershipLevel);
        return userDto;
    }

}
