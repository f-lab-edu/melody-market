package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.domain.admin.enums.MembershipLevelEnum;
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
        return userMapper.existByUserId(userId);
    }

    @Override
    public boolean checkNicknameDuplication(String nickname) {
        return userMapper.existByNickname(nickname);
    }

    @Override
    public boolean signUpUser(UserDto userDto) {
        //TODO: 실패일 경우 어떻게 처리할지 void로 변경하고 (Exception)
        initUser(userDto);
        userMapper.saveUser(userDto);
        return true;
    }

    /**
     * 회원 가입 시 가입 날짜는 오늘로, 등급은 BRONZE로 가입 됨
     * 비밀번호는 평문 -> 암호화
     *
     * @param userDto 회원가입을 위한 유저 정보
     */
    private void initUser(UserDto userDto) {
        userDto.setMembershipLevel(MembershipLevelEnum.BRONZE.getLevel());
        userDto.setJoinDate(LocalDate.now());
        encryptPasswordService.encryptPassword(userDto);
        log.info("date=#{}", userDto.getJoinDate());
    }

}
