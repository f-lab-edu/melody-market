package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.domain.user.model.UserModel;
import com.melodymarket.infrastructure.exception.DataDuplicateKeyException;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserJoinServiceImpl implements UserJoinService {
    UserMapper userMapper;

    CryptPasswordService cryptPasswordService;

    @Override
    public void checkUserIdDuplication(String loginId) {
        log.debug("유저 중복 체크");
        if (userMapper.existByLoginId(loginId)) {
            throw new DataDuplicateKeyException("이미 존재하는 아이디 입니다.");
        }
    }

    @Override
    public void checkNicknameDuplication(String nickname) {
        log.info("닉네임 중복체크");
        if (userMapper.existByNickname(nickname)) {
            throw new DataDuplicateKeyException("이미 존재하는 닉네임 입니다.");
        }
    }

    @Override
    public void signUpUser(UserDto userDto) {
        UserModel userModel = UserModel.from(userDto, cryptPasswordService.encryptPassword(userDto.getUserPassword()));
        try {
            userMapper.saveUser(userModel);
        } catch (DuplicateKeyException e) {
            log.error("중복 데이터 회원가입 시도 ={}", userDto);
            throw new DataDuplicateKeyException("이미 가입 된 회원 정보 입니다.");
        }
    }

}
