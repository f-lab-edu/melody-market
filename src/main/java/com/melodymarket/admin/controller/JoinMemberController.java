package com.melodymarket.admin.controller;

import com.melodymarket.admin.service.UserJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class JoinMemberController {

    private final UserJoinService userJoinService;

    /**
     * 아이디 중복 체크
     *
     * @param userId 중복되는지 체크하려는 id
     * @return User exists or User not exists
     */
    @GetMapping("/join/{user_id}/exists")
    @ResponseBody
    public ResponseEntity<String> isUserIdAvailable(@PathVariable("user_id") String userId) {

        return ResponseEntity.ok(userJoinService.checkUserIdDuplication(userId) ?
                "User exists" : "User not exists");
    }

}
