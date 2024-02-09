package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.application.service.UserJoinService;
import com.melodymarket.application.service.UserJoinServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/user/join")
public class JoinUserController {

    UserJoinService userJoinService;

    @Autowired
    public JoinUserController(UserJoinServiceImpl userJoinServiceImpl) {
        this.userJoinService = userJoinServiceImpl;
    }


    /**
     * 아이디 중복 체크
     *
     * @param loginId 중복되는지 체크하려는 id
     * @return User exists or User not exists
     */
    @GetMapping("/check-login-id")
    public ResponseEntity<String> isUserIdAvailable(@RequestParam("login-id") String loginId) {
        log.debug("[isUserIdAvailable] loginId={}", loginId);
        userJoinService.checkUserIdDuplication(loginId);
        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
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
        userJoinService.checkNicknameDuplication(nickname);
        return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
    }

    /**
     * 유저 정보 저장
     *
     * @param userDto 회원가입 할 유저 정보
     * @return User exists or User not exists
     */
    @PostMapping("/save")
    public ResponseEntity<Object> createUser(@RequestBody @Validated UserDto userDto) {
        log.debug("[CreateUser] user info={}", userDto.toString());
        userJoinService.signUpUser(userDto);
        return ResponseEntity.ok("유저 생성에 성공했습니다.");
    }

}
