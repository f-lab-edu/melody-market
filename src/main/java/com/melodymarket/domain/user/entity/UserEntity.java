package com.melodymarket.domain.user.entity;

import com.melodymarket.application.user.dto.UpdateUserDto;
import com.melodymarket.application.user.dto.UserDto;
import com.melodymarket.domain.user.enums.MembershipLevelEnum;
import com.melodymarket.util.DateFormattingUtil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "melody_user")
public class UserEntity {
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

    @PrePersist
    private void prePersist() {
        if (this.membershipLevel == null) {
            this.membershipLevel = MembershipLevelEnum.BRONZE.getLevel();
        }
    }

    public static UserEntity from(UserDto userDto, String encryptPassword) {
        return UserEntity
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

    public UserEntity modifyValueSetUserEntity(UpdateUserDto updateUserDto) {
        if (updateUserDto.getNickname() != null) {
            this.setNickname(updateUserDto.getNickname());
        }
        if (updateUserDto.getEmail() != null) {
            this.setEmail(updateUserDto.getEmail());
        }
        return this;
    }
}