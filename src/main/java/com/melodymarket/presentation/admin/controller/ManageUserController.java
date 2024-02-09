package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.service.UserInfoManageService;
import com.melodymarket.application.service.UserInfoManageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/v1/user")
public class ManageUserController {

    UserInfoManageService userInfoManageService;

    @Autowired
    public ManageUserController(UserInfoManageServiceImpl userInfoManageServiceImpl) {
        this.userInfoManageService = userInfoManageServiceImpl;
    }

    @GetMapping("/details/{user-id}")
    public ResponseEntity<Object> getUserInfo(@PathVariable("user-id") Long id) {
        log.debug("[getUserInfo] request user id={}", id);
        return ResponseEntity.ok(userInfoManageService.getUserDetails(id));
    }

    @PostMapping("/details/{user-id}/update-password")
    public ResponseEntity<Object> modifyUserPassword(@PathVariable("user-id") Long id,
                                                     @Validated @RequestBody UpdatePasswordDto updatePasswordDto) {
        log.debug("[modifyUserPassword] request user id={}", id);
        userInfoManageService.modifyUserPassword(id, updatePasswordDto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @PostMapping("/details/{user-id}/update-user-info")
    public ResponseEntity<Object> modifyUserInfo(@PathVariable("user-id") Long id,
                                                 @Validated @RequestBody UpdateUserDto updateUserDto) {
        log.debug("[modifyUserInfo] request user id={}", id);
        userInfoManageService.modifyUserDetails(id, updateUserDto);
        return ResponseEntity.ok("변경이 완료되었습니다.");
    }

    @PostMapping("/delete/{user-id}")
    public ResponseEntity<Object> deleteUsert(@PathVariable("user-id") Long id,
                                              @RequestBody String password,
                                              HttpServletRequest request) {
        log.debug("[deleteUser] request user id={}", id);
        userInfoManageService.deleteUser(id, password);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("회원 탈퇴에 성공했습니다.");
    }


}
