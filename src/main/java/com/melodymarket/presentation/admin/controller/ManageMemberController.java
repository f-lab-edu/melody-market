package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.service.UserInfoManageService;
import com.melodymarket.application.service.UserInfoManageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@Controller
@RequestMapping("/v1/member")
public class ManageMemberController {

    UserInfoManageService userInfoManageService;

    @Autowired
    public ManageMemberController(UserInfoManageServiceImpl userInfoManageServiceImpl) {
        this.userInfoManageService = userInfoManageServiceImpl;
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getUserInfo(@RequestParam("user-id") Long userId) {
        log.debug("[getUserInfo] request user id={}", userId);
        return ResponseEntity.ok(userInfoManageService.getUserDetails(userId));
    }

    @PutMapping("/details/{user-id}/update-password")
    public ResponseEntity<Object> modifyUserPassword(@PathVariable("user-id") Long userId,
                                                     @Validated @RequestBody UpdatePasswordDto updatePasswordDto) {
        log.debug("[modifyUserPassword] request user id={}", userId);
        userInfoManageService.modifyUserPassword(userId, updatePasswordDto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @PatchMapping("/details/{user-id}")
    public ResponseEntity<Object> modifyUserInfo(@PathVariable("user-id") Long userId,
                                                 @Validated @RequestBody UpdateUserDto userDto) {
        log.debug("[modifyUserInfo] request user id={}", userId);
        userInfoManageService.modifyUserDetails(userId, userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/v1/member/details")
                .queryParam("user-id", userId)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }


}
