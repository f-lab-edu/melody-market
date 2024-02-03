package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserInfoManageServiceImpl implements UserInfoManageService {

    UserMapper userMapper;

    @Override
    public UserDto getUserDetails(Long userId) {
        log.debug("유저 정보 조회");
        try {
            return UserDto.from(userMapper.getUserInfo(userId));
        } catch (NullPointerException e) {
            throw new DataNotFoundException("유저 정보를 조회할 수 없습니다.");
        }
    }

}
