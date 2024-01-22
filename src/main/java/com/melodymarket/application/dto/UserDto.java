package com.melodymarket.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    private String userId;
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}"
            , message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 조합하여 작성해야 합니다.")
    private String userPasswd;
    @NotBlank(message = "닉네임은 필수 입력 값 입니다.")
    @Pattern(regexp = "(?=\\S+$)[\\w가-힣]{2,10}"
            , message = "닉네임에는 공백이나 특수문자를 사용할 수 없으며, 2자 이상 10자 이하여야 합니다.")
    private String nickname;
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}", message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "생년월일을 입력하세요.")
    @Pattern(regexp = "\\d{8}", message = "생년월일을 8자리로 입력해주세요.")
    private String birthDate;
    private LocalDate joinDate;
    private Integer membershipLevel;
}
