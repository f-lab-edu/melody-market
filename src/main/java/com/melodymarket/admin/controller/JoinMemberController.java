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
    @GetMapping("/join/check-userid")
    @ResponseBody
    public ResponseEntity<String> isUserIdAvailable(@RequestParam("user-id") String userId) {

        return ResponseEntity.ok(userJoinService.checkUserIdDuplication(userId) ? "이미 사용중인 아이디 입니다." : "사용 가능한 아이디 입니다.");
    }

    /**
     * 닉네임 중복 체크
     *
     * @param nickname 중복되는지 체크하려는 nickname
     * @return User exists or User not exists
     */
    @GetMapping("/join/check-nickname")
    @ResponseBody
    public ResponseEntity<String> isNicknameAvailable(@RequestParam("nickname") String nickname) {

        return ResponseEntity.ok(userJoinService.checkNicknameDuplication(nickname) ? "이미 사용중인 닉네임 입니다." : "사용 가능한 닉네임 입니다.");
    }

}
