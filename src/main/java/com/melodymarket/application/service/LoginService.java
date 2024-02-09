package com.melodymarket.application.service;

import com.melodymarket.domain.user.model.UserModel;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import com.melodymarket.infrastructure.security.MelodyUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LoginService implements UserDetailsService {

    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        UserModel userModel = userMapper.findUser(loginId);
        if (userModel == null) {
            throw new UsernameNotFoundException("ID 또는 비밀번호를 바르게 입력해주세요.");
        }
        return new MelodyUserDetails(userModel);
    }
}
