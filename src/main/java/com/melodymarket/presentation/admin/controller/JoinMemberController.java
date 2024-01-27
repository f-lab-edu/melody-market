package com.melodymarket.presentation.admin.controller;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.application.service.UserJoinServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

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
    @GetMapping("/check-userid")
    public ResponseEntity<String> isUserIdAvailable(@RequestParam("user-id") String userId) {

        return ResponseEntity.ok(userJoinServiceImpl.checkUserIdDuplication(userId) ? "이미 사용중인 아이디 입니다." : "사용 가능한 아이디 입니다.");
    }

    /**
     * 닉네임 중복 체크
     *
     * @param nickname 중복되는지 체크하려는 nickname
     * @return User exists or User not exists
     */
    @GetMapping("/check-nickname")
    public ResponseEntity<String> isNicknameAvailable(@RequestParam("nickname") String nickname) {

        return ResponseEntity.ok(userJoinServiceImpl.checkNicknameDuplication(nickname) ? "이미 사용중인 닉네임 입니다." : "사용 가능한 닉네임 입니다.");
    }

    /**
     * 유저 정보 저장
     *
     * @param userDto       회원가입 할 유저 정보
     * @param bindingResult 유효성 검사 중 에러가 발생한 변수에 대한 결과 값
     * @return User exists or User not exists
     */
    @PostMapping("/save")
    public ResponseEntity<Object> createAccount(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) {
        log.info("##Entered Save");
        if (bindingResult.hasErrors()) {//TODO: 다른 방식으로 개선해 볼 것
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        log.info("##### user info={}",userDto.toString());



        return ResponseEntity.ok(userJoinServiceImpl.signUpUser(userDto) ? "유저 생성에 성공하였습니다." : "유저 생성에 실패하였습니다.");
    }

}
