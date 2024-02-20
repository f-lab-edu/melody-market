package com.melodymarket.application.user.service;

import com.melodymarket.application.user.dto.UserDto;
import com.melodymarket.domain.user.entity.User;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import com.melodymarket.infrastructure.redis.RedisService;
import com.melodymarket.infrastructure.jpa.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserJoinServiceImpl implements UserJoinService {
    UserRepository userRepository;
    CryptPasswordService cryptPasswordService;

    RedisService redisService;

    @Override
    public void checkUserIdDuplication(String loginId, String sessionId) {
        log.debug("유저 중복 체크");
        String keyType = "id:";

        if (userRepository.existsByLoginId(loginId) || isIdentifierInCheckProgress(keyType + loginId, sessionId)) {
            throw new DataDuplicateKeyException("이미 존재하는 아이디 입니다.");
        }
        redisService.setValue(keyType + loginId, sessionId, 3, TimeUnit.MINUTES);
    }

    @Override
    public void checkNicknameDuplication(String nickname, String sessionId) {
        log.info("닉네임 중복체크");
        String keyType = "nickname:";

        if (userRepository.existsByNickname(nickname) || isIdentifierInCheckProgress(keyType + nickname, sessionId)) {
            throw new DataDuplicateKeyException("이미 존재하는 닉네임 입니다.");
        }
        redisService.setValue(nickname, "nickname", 3, TimeUnit.MINUTES);
    }

    @Override
    public void signUpUser(UserDto userDto, String sessionId) {
        User user = User.from(userDto, cryptPasswordService.encryptPassword(userDto.getUserPassword()));

        checkUserIdDuplication(userDto.getLoginId(), sessionId);
        checkNicknameDuplication(userDto.getNickname(), sessionId);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error("중복 데이터 회원가입 시도 ={}", userDto);
            throw new DataDuplicateKeyException("이미 가입 된 회원 정보 입니다.");
        }
    }

    public boolean isIdentifierInCheckProgress(String key, String sessionId) {
        return redisService.getValue(key)
                .map(value -> !value.equals(sessionId))
                .orElse(false);
    }

}
