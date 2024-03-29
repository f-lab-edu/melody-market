package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.user.dto.UserDto;
import com.melodymarket.application.user.service.UserJoinService;
import com.melodymarket.application.user.service.UserJoinServiceImpl;
import com.melodymarket.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseDto<String> isUserIdAvailable(@RequestParam("login-id") String loginId,
                                                 HttpServletRequest request) {
        log.debug("[isUserIdAvailable] [Session_id={}] loginId={}", request.getSession(false).getId(), loginId);
        userJoinService.checkUserIdDuplication(loginId, request.getSession(false).getId());

        return ResponseDto.of(HttpStatus.OK, "사용 가능한 아이디 입니다.", null);
    }

    /**
     * 닉네임 중복 체크
     *
     * @param nickname 중복되는지 체크하려는 nickname
     * @return User exists or User not exists
     */
    @GetMapping("/check-nickname")
    public ResponseDto<String> isNicknameAvailable(@RequestParam("nickname") String nickname,
                                                   HttpServletRequest request) {
        log.debug("[isNicknameAvailable] [Session_id={}] nickname={}", request.getSession(false).getId(), nickname);
        userJoinService.checkNicknameDuplication(nickname, request.getSession(false).getId());

        return ResponseDto.of(HttpStatus.OK, "사용 가능한 닉네임 입니다.", null);
    }

    /**
     * 유저 정보 저장
     *
     * @param userDto 회원가입 할 유저 정보
     * @return User exists or User not exists
     */
    @PostMapping("/save")
    public ResponseDto<String> createUser(@RequestBody @Validated UserDto userDto,
                                          HttpServletRequest request) {
        log.debug("[CreateUser] [Session_id={}] user info={}", request.getSession(false).getId(), userDto.toString());
        userJoinService.signUpUser(userDto, request.getSession(false).getId());

        return ResponseDto.of(HttpStatus.OK, "유저 생성에 성공했습니다.", null);
    }

}
