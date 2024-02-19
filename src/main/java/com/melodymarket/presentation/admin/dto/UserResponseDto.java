package com.melodymarket.presentation.admin.dto;

import com.melodymarket.domain.user.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String loginId;
    private String username;
    private String nickname;
    private String email;
    private String birthDate;
    private Integer membershipLevel;

    public static UserResponseDto from(UserEntity userEntity) {
        return UserResponseDto
                .builder()
                .loginId(userEntity.getLoginId())
                .username(userEntity.getUsername())
                .nickname(userEntity.getNickname())
                .birthDate(userEntity.getBirthDate())
                .email(userEntity.getEmail())
                .membershipLevel(userEntity.getMembershipLevel())
                .build();
    }
}
