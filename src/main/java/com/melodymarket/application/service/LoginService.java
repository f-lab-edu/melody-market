package com.melodymarket.application.service;

import com.melodymarket.domain.user.model.Account;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Account account = new Account();
        account.setUserId(userId);
        account = userMapper.findUser(account.getUserId());
        if (account.getUserId() != null && account.getPassword() != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new User(account.getUserId(), account.getPassword(), authorities);
        }

        return null;
    }
}
