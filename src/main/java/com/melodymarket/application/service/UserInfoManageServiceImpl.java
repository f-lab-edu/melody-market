package com.melodymarket.application.service;

import com.melodymarket.application.dto.UpdatePasswordDto;
import com.melodymarket.application.dto.UpdateUserDto;
import com.melodymarket.application.dto.UserDto;
import com.melodymarket.common.exception.PasswordMismatchException;
import com.melodymarket.domain.user.entity.UserEntity;
import com.melodymarket.infrastructure.exception.DataNotFoundException;
import com.melodymarket.infrastructure.repository.UserRepository;
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

    UserRepository userRepository;
    CryptPasswordService cryptPasswordService;

    @Override
    public UserDto getUserDetails(Long id) {
        log.debug("유저 정보 조회={}", id);
        return UserDto.from(getUserEntity(id));
    }

    @Override
    public void modifyUserPassword(Long id, UpdatePasswordDto updatePasswordDto) {
        UserEntity userEntity = getUserEntity(id);
        if (!cryptPasswordService.isPasswordMatch(updatePasswordDto.getOldPassword(),
                userEntity.getUserPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        userEntity.setUserPassword(cryptPasswordService.encryptPassword(updatePasswordDto.getNewPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public void modifyUserDetails(Long id, UpdateUserDto updateUserDto) {
        log.debug("유저 정보 수정={}", id);
        userRepository.save(getUserEntity(id).modifyValueSetUserEntity(updateUserDto));
    }

    @Override
    public void deleteUser(Long id, String password) {
        UserEntity userEntity = getUserEntity(id);
        if (!cryptPasswordService.isPasswordMatch(password,
                userEntity.getUserPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        userRepository.deleteById(id);
    }

    public UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("유저 정보를 조회할 수 없습니다."));
    }

}
