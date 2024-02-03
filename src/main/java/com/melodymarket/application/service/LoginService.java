package com.melodymarket.application.service;

import com.melodymarket.domain.user.model.Account;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import com.melodymarket.infrastructure.security.MelodyUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LoginService implements UserDetailsService {

    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Account account = userMapper.findUser(loginId);
        if (account.getUserId() != null && account.getUserPasswd() != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new MelodyUserDetails(account, authorities);
        }

        return null;
    }
}
