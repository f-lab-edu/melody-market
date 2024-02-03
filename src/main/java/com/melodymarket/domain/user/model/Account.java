package com.melodymarket.domain.user.model;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.util.DateFormattingUtil;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

    public static Account withUserDto(UserDto userDto) {
        return Account
                .builder()
                .loginId(userDto.getLoginId())
                .userPasswd(userDto.getUserPasswd())
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .birthDate(DateFormattingUtil
                        .dataFormatter(userDto.getBirthDate(), "yyyyMMdd", "yyyy-MM-dd"))
                .membershipLevel(userDto.getMembershipLevel())
                .build();
    }

}
