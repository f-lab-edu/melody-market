package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.user.dto.UpdatePasswordDto;
import com.melodymarket.application.user.dto.UpdateUserDto;
import com.melodymarket.application.user.service.UserInfoManageService;
import com.melodymarket.application.user.service.UserInfoManageServiceImpl;
import com.melodymarket.common.dto.ResponseDto;
import com.melodymarket.presentation.admin.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class ManageUserController {

    UserInfoManageService userInfoManageService;

    @Autowired
    public ManageUserController(UserInfoManageServiceImpl userInfoManageServiceImpl) {
        this.userInfoManageService = userInfoManageServiceImpl;
    }

    @GetMapping("/details/{user-id}")
    public ResponseDto<UserResponseDto> getUserInfo(@PathVariable("user-id") Long id) {
        log.debug("[getUserInfo] request user id={}", id);

        return ResponseDto.of(HttpStatus.OK, "유저 정보 조회에 성공했습니다.",
                userInfoManageService.getUserDetails(id));
    }

    @PostMapping("/details/{user-id}/update-password")
    public ResponseDto<String> modifyUserPassword(@PathVariable("user-id") Long id,
                                                  @Validated @RequestBody UpdatePasswordDto updatePasswordDto) {
        log.debug("[modifyUserPassword] request user id={}", id);
        userInfoManageService.modifyUserPassword(id, updatePasswordDto);

        return ResponseDto.of(HttpStatus.OK, "비밀번호가 변경되었습니다.", null);
    }

    @PostMapping("/details/{user-id}/update-user-info")
    public ResponseDto<String> modifyUserInfo(@PathVariable("user-id") Long id,
                                              @Validated @RequestBody UpdateUserDto updateUserDto) {
        log.debug("[modifyUserInfo] request user id={}", id);
        userInfoManageService.modifyUserDetails(id, updateUserDto);

        return ResponseDto.of(HttpStatus.OK, "변경이 완료되었습니다.", null);
    }

    @PostMapping("/delete/{user-id}")
    public ResponseDto<String> deleteUsert(@PathVariable("user-id") Long id,
                                           @RequestBody String password,
                                           HttpServletRequest request) {
        log.debug("[deleteUser] request user id={}", id);
        userInfoManageService.deleteUser(id, password);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseDto.of(HttpStatus.OK, "회원 탈퇴에 성공했습니다.", null);
    }


}
