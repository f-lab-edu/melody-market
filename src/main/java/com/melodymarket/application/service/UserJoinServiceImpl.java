package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.domain.user.entity.UserEntity;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import com.melodymarket.infrastructure.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserJoinServiceImpl implements UserJoinService {
    UserRepository userRepository;
    CryptPasswordService cryptPasswordService;

    @Override
    public void checkUserIdDuplication(String loginId) {
        log.debug("유저 중복 체크");
        if (userRepository.existsByLoginId(loginId)) {
            throw new DataDuplicateKeyException("이미 존재하는 아이디 입니다.");
        }
    }

    @Override
    public void checkNicknameDuplication(String nickname) {
        log.info("닉네임 중복체크");
        if (userRepository.existsByNickname(nickname)) {
            throw new DataDuplicateKeyException("이미 존재하는 닉네임 입니다.");
        }
    }

    @Override
    public void signUpUser(UserDto userDto) {
        UserEntity userEntity = UserEntity.from(userDto, cryptPasswordService.encryptPassword(userDto.getUserPassword()));

        if (userRepository.existsByNickname(userEntity.getNickname())) {
            throw new DataDuplicateKeyException("이미 존재하는 닉네임 입니다.");
        }else if (userRepository.existsByLoginId(userEntity.getLoginId())) {
            throw new DataDuplicateKeyException("이미 존재하는 아이디 입니다.");
        }

        try {
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            log.error("중복 데이터 회원가입 시도 ={}", userDto);
            throw new DataDuplicateKeyException("이미 가입 된 회원 정보 입니다.");
        }
    }

}
