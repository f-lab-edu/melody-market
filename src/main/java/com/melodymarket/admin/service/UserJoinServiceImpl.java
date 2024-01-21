package com.melodymarket.admin.service;

import com.melodymarket.admin.dto.MembershipLevelEnum;
import com.melodymarket.admin.dto.UserDto;
import com.melodymarket.admin.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class UserJoinServiceImpl implements UserJoinService {
    private final UserMapper userMapper;

    @Autowired
    public UserJoinServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
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
     *
     * @param userDto
     */
    private void initUser(UserDto userDto) {
        userDto.setMembershipLevel(MembershipLevelEnum.BRONZE.getLevel());
        userDto.setJoinDate(LocalDate.now());
        log.info("date=#{}", userDto.getJoinDate()); //TODO: log 형식 다르게 지정할 수 있음
    }

}
