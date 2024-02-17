package com.melodymarket.application.user.service;

import com.melodymarket.infrastructure.jpa.user.repository.UserRepository;
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

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return new MelodyUserDetails(userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("ID 또는 비밀번호를 바르게 입력해주세요.")));
    }
}
