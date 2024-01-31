package com.melodymarket.application.service;

import com.melodymarket.domain.user.model.Account;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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
@Slf4j
public class LoginService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = new Account();
        account.setUserId(username);
        account = userMapper.findUser(account);
//        log.debug("login user={}", account.toString());
        if (account.getUsername() != null && account.getPassword() != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new User(account.getUsername(), account.getPassword(), authorities);
        }

        return null;
    }
}
