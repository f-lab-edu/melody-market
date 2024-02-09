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
public class UserModel {
    private Long id;
    private String loginId;
    private String username;
    private String userPassword;
    private String nickname;
    private String email;
    private String birthDate;
    private String joinDate;
    private Integer membershipLevel;



    public static UserModel from(UserDto userDto, String encryptPassword) {
        return UserModel
                .builder()
                .loginId(userDto.getLoginId())
                .userPassword(encryptPassword)
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
