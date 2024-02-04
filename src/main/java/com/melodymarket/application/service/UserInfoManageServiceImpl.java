package com.melodymarket.application.service;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.dto.UserDto;
import com.melodymarket.common.exception.PasswordMismatchException;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.mybatis.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserInfoManageServiceImpl implements UserInfoManageService {

    UserMapper userMapper;

    CryptPasswordService cryptPasswordService;

    @Override
    public UserDto getUserDetails(Long id) {
        log.debug("유저 정보 조회={}", id);
        try {
            return UserDto.from(userMapper.getUserInfo(id));
        } catch (NullPointerException e) {
            throw new DataNotFoundException("유저 정보를 조회할 수 없습니다.");
        }
    }

    @Override
    public void modifyUserPassword(Long id, UpdatePasswordDto updatePasswordDto) {
        try {
            if (!cryptPasswordService.isPasswordMatch(updatePasswordDto.getOldPasswd(),
                    userMapper.getUserInfo(id).getUserPasswd()))
                throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");

            userMapper.updatePassword(id, cryptPasswordService.encryptPassword(updatePasswordDto.getNewPasswd()));
        } catch (NullPointerException e) {
            throw new DataNotFoundException("알 수 없는 유저 정보에 대한 요청 입니다.");
        }
    }

    @Override
    public void modifyUserDetails(Long id, UpdateUserDto userDto) {
        log.debug("유저 정보 수정={}", id);
        try {
            if (userDto.getNickname() != null) {
                userMapper.updateNickname(id, userDto.getNickname());
            }
            if (userDto.getEmail() != null) {
                userMapper.updateEmail(id, userDto.getEmail());
            }
        } catch (NullPointerException e) {
            throw new DataNotFoundException("알 수 없는 유저 정보에 대한 요청 입니다.");
        }
    }

}
