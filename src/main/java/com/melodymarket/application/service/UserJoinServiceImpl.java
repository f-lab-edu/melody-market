package com.melodymarket.application.service;

import com.melodymarket.application.dto.UserDto;
import com.melodymarket.domain.user.enums.MembershipLevelEnum;
import com.melodymarket.domain.user.model.Account;
import com.melodymarket.infrastructure.mybatis.exception.MybatisDuplicateKeyException;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserJoinServiceImpl implements UserJoinService {
    UserMapper userMapper;
    EncryptPasswordService encryptPasswordService;

    @Override
    public void checkUserIdDuplication(String userId) {
        log.debug("유저 중복 체크");
        if (userMapper.existByUserId(userId)) {
            throw new MybatisDuplicateKeyException("이미 존재하는 아이디 입니다.");
        }
    }

    @Override
    public void checkNicknameDuplication(String nickname) {
        log.info("###닉네임 중복체크");
        if (userMapper.existByNickname(nickname)) {
            throw new MybatisDuplicateKeyException("이미 존재하는 닉네임 입니다.");
        }
    }

    @Override
    public void signUpUser(UserDto userDto) {
        log.info("###회원가입");
        initUser(userDto);
        Account account = convertDtoToModel(userDto);
        try {
            userMapper.saveUser(account);
        } catch (DuplicateKeyException e) {
            log.error("중복 데이터 회원가입 시도 ={}", userDto);
            throw new MybatisDuplicateKeyException("이미 가입 된 회원 정보 입니다.");
        }
    }

    /**
     * 비밀번호는 평문 -> 암호화
     *
     * @param userDto 회원가입을 위한 유저 정보
     */
    private void initUser(UserDto userDto) {
        encryptPasswordService.encryptPassword(userDto);
    }

    /**
     * 회원 가입 시 가입 날짜는 오늘로, 등급은 BRONZE로 가입 됨
     *
     * @param userDto user 모델로 전환할 객체
     */
    public Account convertDtoToModel(UserDto userDto) {
        Account account = new Account();
        account.setUserId(userDto.getUserId());
        account.setNickname(userDto.getNickname());
        account.setUserPasswd(userDto.getUserPasswd());
        account.setEmail(userDto.getEmail());
        account.setBirthDate(userDto.getBirthDate());
        account.setJoinDate(LocalDate.now());
        account.setMembershipLevel(MembershipLevelEnum.BRONZE.getLevel());
        return account;
    }

}
