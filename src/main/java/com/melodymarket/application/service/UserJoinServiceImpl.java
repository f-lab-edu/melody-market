package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.domain.user.enums.MembershipLevelEnum;
import com.melodymarket.domain.user.model.Account;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class UserJoinServiceImpl implements UserJoinService {
    private final UserMapper userMapper;
    private final EncryptPasswordService encryptPasswordService;

    @Autowired
    public UserJoinServiceImpl(UserMapper userMapper, EncryptPasswordService encryptPasswordService) {
        this.userMapper = userMapper;
        this.encryptPasswordService = encryptPasswordService;
    }


    @Override
    public boolean checkUserIdDuplication(String userId) {
        log.info("###유저중복체크");
        return userMapper.existByUserId(userId);
    }

    @Override
    public boolean checkNicknameDuplication(String nickname) {
        log.info("###닉네임 중복체크");
        return userMapper.existByNickname(nickname);
    }

    @Override
    public boolean signUpUser(UserDto userDto) {
        //TODO: 실패일 경우 어떻게 처리할지 void로 변경하고 (Exception)
        log.info("###회원가입");
        initUser(userDto);
        Account account = convertDtoToModel(userDto);
        userMapper.saveUser(account);
        return true;
    }

    /**
     * 비밀번호는 평문 -> 암호화
     *
     * @param userDto 회원가입을 위한 유저 정보
     */
    private void initUser(UserDto userDto) {
        encryptPasswordService.encryptPassword(userDto);
    }

    /**
     * 회원 가입 시 가입 날짜는 오늘로, 등급은 BRONZE로 가입 됨
     *
     * @param userDto user 모델로 전환할 객체
     */
    public Account convertDtoToModel(UserDto userDto) {
        Account account = new Account();
        account.setUserId(userDto.getUserId());
        account.setNickname(userDto.getNickname());
        account.setUserPasswd(userDto.getUserPasswd());
        account.setEmail(userDto.getEmail());
        account.setBirthDate(userDto.getBirthDate());
        account.setJoinDate(LocalDate.now());
        account.setMembershipLevel(MembershipLevelEnum.BRONZE.getLevel());
        return account;
    }

}
