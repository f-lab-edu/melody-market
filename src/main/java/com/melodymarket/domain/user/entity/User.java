package com.melodymarket.domain.user.entity;

import com.melodymarket.application.user.dto.UpdateUserDto;
import com.melodymarket.application.user.dto.UserDto;
import com.melodymarket.application.user.service.CryptPasswordService;
import com.melodymarket.domain.user.enums.MembershipLevelEnum;
import com.melodymarket.util.DateFormattingUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "melody_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String loginId;
    @Column
    private String username;
    @Column
    private String userPassword;
    @Column
    private String nickname;
    @Column
    private String email;
    @Column
    private String birthDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private String joinDate;
    @Column
    private Integer membershipLevel;

    @Builder
    public User(String loginId, String username, String userPassword, String nickname, String email, String birthDate, String joinDate, Integer membershipLevel) {
        this.loginId = loginId;
        this.username = username;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.email = email;
        this.birthDate = birthDate;
        this.joinDate = joinDate;
        this.membershipLevel = membershipLevel;
    }

    @PrePersist
    private void prePersist() {
        if (this.membershipLevel == null) {
            this.membershipLevel = MembershipLevelEnum.BRONZE.getLevel();
        }
    }

    public void changePassword(String newPassword, CryptPasswordService cryptPasswordService) {
        this.userPassword = cryptPasswordService.encryptPassword(newPassword);
    }

    public static User from(UserDto userDto, String encryptPassword) {
        return User
                .builder()
                .loginId(userDto.getLoginId())
                .userPassword(encryptPassword)
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .joinDate(LocalDate.now().toString())
                .birthDate(DateFormattingUtil
                        .dataFormatter(userDto.getBirthDate(), "yyyyMMdd", "yyyy-MM-dd"))
                .build();
    }

    public User modifyValueSetUserEntity(UpdateUserDto updateUserDto) {
        if (updateUserDto.getNickname() != null) {
            this.nickname = updateUserDto.getNickname();
        }
        if (updateUserDto.getEmail() != null) {
            this.email = updateUserDto.getEmail();
        }
        return this;
    }
}