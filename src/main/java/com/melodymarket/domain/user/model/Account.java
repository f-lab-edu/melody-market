package com.melodymarket.domain.user.model;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.domain.user.enums.MembershipLevelEnum;
import com.melodymarket.util.DateFormattingUtil;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

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



    public static Account from(UserDto userDto, String encryptPassword) {
        return Account
                .builder()
                .loginId(userDto.getLoginId())
                .userPasswd(encryptPassword)
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .joinDate(LocalDate.now().toString())
                .birthDate(DateFormattingUtil
                        .dataFormatter(userDto.getBirthDate(), "yyyyMMdd", "yyyy-MM-dd"))
                .membershipLevel(Optional.ofNullable(userDto.getMembershipLevel()).orElse(MembershipLevelEnum.BRONZE.getLevel()))
                .build();
    }

}
