package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.service.UserInfoManageService;
import com.melodymarket.application.service.UserInfoManageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<Object> getUserInfo(@RequestParam("user-id") String userId) {
        log.debug("[getUserInfo] request user id={}", userId);
        return ResponseEntity.ok(userInfoManageService.getUserDetails(userId));
    }


}
