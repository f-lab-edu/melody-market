package com.melodymarket.presentation.admin.dto;

import com.melodymarket.domain.user.entity.User;
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

    public static UserResponseDto from(User user) {
        return UserResponseDto
                .builder()
                .loginId(user.getLoginId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .membershipLevel(user.getMembershipLevel())
                .build();
    }
}
