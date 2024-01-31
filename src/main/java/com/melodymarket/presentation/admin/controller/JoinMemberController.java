package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.application.service.UserJoinServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/member/join")
public class JoinMemberController {

    private final UserJoinServiceImpl userJoinServiceImpl;

    @Autowired
    public JoinMemberController(UserJoinServiceImpl userJoinServiceImpl) {
        this.userJoinServiceImpl = userJoinServiceImpl;
    }


    /**
     * 아이디 중복 체크
     *
     * @param userId 중복되는지 체크하려는 id
     * @return User exists or User not exists
     */
    @GetMapping("/check-user-id")
    public ResponseEntity<String> isUserIdAvailable(@RequestParam("user-id") String userId) {
        log.debug("[isUserIdAvailable] userId={}", userId);
        userJoinServiceImpl.checkUserIdDuplication(userId);
        return ResponseEntity.ok( "사용 가능한 아이디 입니다.");
    }

    /**
     * 닉네임 중복 체크
     *
     * @param nickname 중복되는지 체크하려는 nickname
     * @return User exists or User not exists
     */
    @GetMapping("/check-nickname")
    public ResponseEntity<String> isNicknameAvailable(@RequestParam("nickname") String nickname) {
        log.debug("[isNicknameAvailable] nickname={}", nickname);
        userJoinServiceImpl.checkNicknameDuplication(nickname);
        return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
    }

    /**
     * 유저 정보 저장
     *
     * @param userDto       회원가입 할 유저 정보
     * @return User exists or User not exists
     */
    @PostMapping("/save")
    public ResponseEntity<Object> createAccount(@RequestBody @Validated UserDto userDto) {
        log.debug("[CreateAccount] user info={}", userDto.toString());
        userJoinServiceImpl.signUpUser(userDto);
        return ResponseEntity.ok("유저 생성에 성공했습니다.");
    }

}
