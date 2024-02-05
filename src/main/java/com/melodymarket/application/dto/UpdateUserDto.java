package com.melodymarket.application.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserDto {

    @Nullable
    @Pattern(regexp = "(?=\\S+$)[\\w가-힣]{2,10}",
            message = "닉네임에는 공백이나 특수문자를 사용할 수 없으며, 2자 이상 10자 이하여야 합니다.")
    private String nickname;

    @Nullable
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

}

