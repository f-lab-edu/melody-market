package com.melodymarket.admin.service;

import com.melodymarket.admin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserJoinService {
    private final UserMapper userMapper;

    @Autowired
    public UserJoinService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public boolean checkUserIdDuplication(String userId){
        return userMapper.existByUserId(userId);
    }
}
