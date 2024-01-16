package com.melodymarket.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserDto {
    private Long id;
    private String userId;
    private String userPasswd;
    private String nickname;
    private String email;
    private LocalDate birthDate;
    private LocalDate joinDate;
    private Integer membershipLevel;
}
